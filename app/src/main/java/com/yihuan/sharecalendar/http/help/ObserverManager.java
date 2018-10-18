package com.yihuan.sharecalendar.http.help;

/**
 * Created by Ronny on 2017/9/21.
 */

public interface ObserverManager {

    MyObserver bind(MyObserver observer);
    void unBind();
}
