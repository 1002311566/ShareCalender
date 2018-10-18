package com.yihuan.sharecalendar.modle.bean.active;

import android.graphics.Bitmap;

/**
 * Created by Ronny on 2017/12/16.
 * 分享对象
 */

public class ShareBean {
    public String toWhere;
    public ShareType type;
    public String text;//文字
    public Bitmap bitmap;//图片

    //todo 分享网址
    public String url;
    public String title;
    public String des;

    public ShareBean(){}

    public ShareBean(ShareType type, String text, Bitmap bitmap, String url, String title, String des) {
        this.toWhere = toWhere;
        this.type = type;
        this.text = text;
        this.bitmap = bitmap;
        this.url = url;
        this.title = title;
        this.des = des;
    }
}
