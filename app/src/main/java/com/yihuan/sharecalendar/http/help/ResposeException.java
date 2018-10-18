package com.yihuan.sharecalendar.http.help;

/**
 * Created by Ronny on 2017/9/7.
 */

public class ResposeException extends RuntimeException {
    public int code;
    public String msg;


    public ResposeException(int code, String msg){
        this.code = code;
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "ResposeException{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                '}';
    }

}
