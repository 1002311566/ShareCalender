package com.yihuan.sharecalendar.modle.bean.login;

/**
 * Created by Ronny on 2017/9/8.
 * 星座
 */

public class ConstellationBean {
//    {

//    }
//            "name": "摩羯座"
//            "activeId": 12,
//        "detailName": "12月22日─01月19日",
    public String id;
    public String name;
    public String detailName;

    public String getString(){
        return name+" ("+detailName+")";
    }

}
