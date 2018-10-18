package com.yihuan.sharecalendar.http.help;

import android.text.TextUtils;

import com.yihuan.sharecalendar.global.App;
import com.yihuan.sharecalendar.global.DataUtils;
import com.yihuan.sharecalendar.utils.LogUtils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Ronny on 2017/9/8.
 */

public class TokenIntercept implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        String token = DataUtils.getToken();
        if (TextUtils.isEmpty(token)) {
            token = "";
        }
        LogUtils.e("token--------------------"+ token);
        Request request = chain.request().newBuilder()
                .header("Content-Type", "application/json;charset=UTF-8")
                .header("accessToken", token)
                .build();
        return chain.proceed(request);
    }
}
