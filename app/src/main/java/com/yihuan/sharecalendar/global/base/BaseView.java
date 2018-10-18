package com.yihuan.sharecalendar.global.base;

/**
 * Created by Ronny on 2017/9/5.
 * 视图基本功能接口
 */

public interface BaseView {

    void showLoaddingView(boolean show);
    void showLoadFeilureView();
    void showNoDataView();
    void showNotNetView();
    void showNotLoginView();
    void showToast(String msg);

    void onNoMore();
}
