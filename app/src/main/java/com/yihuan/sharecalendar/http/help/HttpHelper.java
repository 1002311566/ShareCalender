package com.yihuan.sharecalendar.http.help;

import com.yihuan.sharecalendar.modle.bean.BaseBean;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Ronny on 2017/9/6.
 */

public class HttpHelper<T> {
    public static final int TIMEOUT = 10;

    private  Retrofit retrofit;
    private ApiService<T> mService;
    private static HttpHelper mInstance;
    private final OkHttpClient build;

    private HttpHelper(){

        build = new OkHttpClient().newBuilder()
                .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(new HttpLogIntercepter())
                .addInterceptor(new TokenIntercept())
                .build();

        retrofit=  new Retrofit.Builder()
                .baseUrl(Urls.DOMAIN)
                .client(build)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mService = retrofit.create(ApiService.class);

    }

    /**
     * 处理本应用接口
     * @return
     */
    public ApiService getmService(){
        return mService;
    }

    /**
     * 处理其它类接口
     * @return
     */
    public ApiService getOtherService(){
        Retrofit r = new Retrofit.Builder()
                .client(this.build)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return r.create(ApiService.class);
    }

    public static HttpHelper getInstance(){
        if(mInstance == null){
            synchronized (HttpHelper.class){
                if(mInstance == null){
                    mInstance = new HttpHelper();
                }
            }
        }
        return mInstance;
    }

    /**
     * 处理本应用接口
     * @return
     */
    public void request(Observable<BaseBean<T>> observable, Observer<T> subscriber){
        observable
                .map(new HttpRequestFunc())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 处理其它类接口
     * @return
     */
    public void requestOther(Observable<T> observable, Observer<T> subscriber){
        observable
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    private class HttpRequestFunc implements Function<BaseBean<T>, T>{

        @Override
        public T apply(@NonNull BaseBean<T> tBaseBean) throws Exception {

            if(tBaseBean.code != 200 || tBaseBean.data == null){
                throw new ResposeException(tBaseBean.code, tBaseBean.message);
            }
            return tBaseBean.data;
        }
    }

}
