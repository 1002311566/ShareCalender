package com.yihuan.sharecalendar.http.help;

import android.content.Intent;
import android.text.TextUtils;
import android.widget.Toast;

import com.google.gson.JsonSyntaxException;
import com.yihuan.sharecalendar.global.App;
import com.yihuan.sharecalendar.ui.activity.login.LoginActivity;
import com.yihuan.sharecalendar.utils.LogUtils;

import java.net.SocketException;
import java.net.SocketTimeoutException;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;

/**
 * Created by Ronny on 2017/9/7.
 */

public abstract class MyObserver<T> implements Observer<T> {

    public static final String TAG = "MyObserver";
    private boolean isBind;//是否处理接收的事件

    @Override
    public void onSubscribe(@NonNull Disposable d) {
        LogUtils.e(TAG, "接收事件------------onSubscribe------");
        if (!isBind) {
            LogUtils.e("请求被拦截，请在Presenter或UI界面中绑定该类");
            d.dispose();
        }
    }

    //    给子类实现
    @Override
    public void onNext(@NonNull T t) {
        if (!isBind)
            return;

        try{
            onSucceed(t);
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(App.getInstanse(), "数据异常", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 服务端异常状态码：
     * 401  没有授权认证  例如没有登录的情况下访问需要授权的资源, 或用户不存在、token过期
     * 404  请求未找到
     * 500  系统内部错误      例如服务错误
     * 600  重复操作
     * 601  非法操作     无权限的操作
     * 603  认证失败        比如登录密码错误
     * 604  验证码不匹配
     * 700  参数错误
     * 701  业务错误
     */

    @Override
    public void onError(@NonNull Throwable e) {
        if (!isBind)
            return;

        try{

            LogUtils.e(TAG, "异常错误信息------------------" + e.toString());
            if (e instanceof ResposeException) {
                //对服务端异常状态码的处理
                ResposeException ex = (ResposeException) e;
                int code = ex.code;
                String msg = ex.msg;
                if (code == 200) {
                    //todo 说明请求成功，解析对象为空
                    this.onNext(null);
                    return;
                } else if (code == 401) {
//                todo 重新登录
//                    Intent intent = new Intent(App.getInstanse(), LoginActivity.class);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    App.getInstanse().startActivity(intent);
                    on401Failure();
                }
                if (!TextUtils.isEmpty(msg)) {
                    onFailure(code, msg);
                }

            }else if (e instanceof JsonSyntaxException) {
                //连接失败，检查网络
                onFailure(500, "数据解析异常！");

            }  else if (e instanceof SocketException) {
                //连接失败，检查网络
                onFailure(500, "连接失败！");

            } else if (e instanceof SocketTimeoutException) {
                //连接超时
                onFailure(0, "连接超时，请稍后重试！");
            } else if (e instanceof HttpException) {
                //网页错误 404等
                HttpException he = (HttpException) e;
                switch (he.code()) {
                    case 404:
                        onFailure(he.code(), "HTTP-404");
                        break;
                    case 400:
                        onFailure(he.code(), "HTTP-400");
                        break;
                    default: {
                        onFailure(-1, "连接异常");
                    }
                }
            } else {
                onFailure(-1, "连接异常！");
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    protected abstract void onFailure(int code, String msg);

    protected abstract void onSucceed(T t);

    public boolean isBind() {
        return isBind;
    }

    public void setBind(boolean bind) {
        isBind = bind;
    }

    @Override
    public void onComplete() {
    }

    /**
     * 可以重写，对需要做登录操作的逻辑处理
     */
    protected void on401Failure() {
    }
}
