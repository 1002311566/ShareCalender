package com.yihuan.sharecalendar.global.base;

import com.yihuan.sharecalendar.http.help.MyObserver;
import com.yihuan.sharecalendar.http.help.ObserverManager;
import com.yihuan.sharecalendar.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ronny on 2017/9/8.
 * Presenter基类，主要用于绑定View和解绑View
 */

public abstract class BasePresenter<V extends BaseView> implements ObserverManager {
    public V mView;
    private List<MyObserver> list = new ArrayList<>();

    private Integer lastId;

    public BasePresenter(V v) {
        this.mView = v;
    }

    public void attachView(V view) {
        mView = view;
        LogUtils.i("attach------" + this.getClass().getName());
    }

    public void dettachView() {
        mView = null;
        unBind();
        LogUtils.i("dettach------" + this.getClass().getName());
    }

    /**
     * 绑定观察者，每个请求必须调用该方法
     *
     * @param observer
     * @return
     */
    @Override
    public MyObserver bind(MyObserver observer) {
        if (!list.contains(observer)) {
            list.add(observer);
            observer.setBind(true);
        }
        return observer;
    }

    @Override
    public void unBind() {
        for (MyObserver observer : list) {
            observer.setBind(false);
        }
    }

    public void setLastId(Integer lastId) {
        this.lastId = lastId;
    }

    public void resetLastId() {
        this.lastId = null;
    }

    public Integer getLastId(){
        return lastId;
    }

    public void setNoMore(){
        if(mView != null){
            mView.onNoMore();
        }
    }


}
