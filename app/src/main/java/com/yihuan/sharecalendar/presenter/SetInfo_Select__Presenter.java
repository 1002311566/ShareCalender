package com.yihuan.sharecalendar.presenter;

import com.yihuan.sharecalendar.global.base.BasePresenter;
import com.yihuan.sharecalendar.http.Api;
import com.yihuan.sharecalendar.http.help.MyObserver;
import com.yihuan.sharecalendar.modle.bean.login.CityBean;
import com.yihuan.sharecalendar.modle.bean.login.ConstellationBean;
import com.yihuan.sharecalendar.modle.bean.login.ProvinceBean;
import com.yihuan.sharecalendar.presenter.contract.SetInfo_Select_Contract;
import com.yihuan.sharecalendar.ui.activity.login.SetInfoActivity_Select;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.annotations.NonNull;

/**
 * Created by Ronny on 2017/9/9.
 */

public class SetInfo_Select__Presenter extends BasePresenter<SetInfoActivity_Select> implements SetInfo_Select_Contract.Presenter {
    public SetInfo_Select__Presenter(SetInfoActivity_Select setInfoActivity_select) {
        super(setInfoActivity_select);
    }

    @Override
    public void getConstellationList() {
        mView.showLoaddingView(true);
        Api.getConstellationList(bind(new MyObserver<ArrayList<ConstellationBean>>() {
            @Override
            protected void onFailure(int code, String msg) {
                mView.showToast(msg);
                mView.showLoaddingView(false);
            }

            @Override
            public void onSucceed(@NonNull ArrayList<ConstellationBean> listBaseBean) {
                mView.getConstellationListOK(listBaseBean);
                mView.showLoaddingView(false);
            }
        }));
    }

    @Override
    public void getAddressProvinceList() {

        mView.showLoaddingView(true);
        Api.getProvienceList(bind(new MyObserver<List<ProvinceBean>>() {
            @Override
            protected void onFailure(int code, String msg) {
                mView.showLoaddingView(false);
                mView.showToast(msg);
            }

            @Override
            public void onSucceed(@NonNull List<ProvinceBean> provinceBeen) {
                mView.showLoaddingView(false);
                mView.getAddressProvinceListOK(provinceBeen);
            }
        }));
    }

    @Override
    public void getAddressCityList(String provinceId) {
        mView.showLoaddingView(true);
        Api.getCityList(provinceId, bind(new MyObserver<List<CityBean>>() {
            @Override
            protected void onFailure(int code, String msg) {
                mView.showLoaddingView(false);
                mView.showToast(msg);
            }

            @Override
            public void onSucceed(@NonNull List<CityBean> list) {
                mView.showLoaddingView(false);
                mView.getAddressCityListOK(list);
            }
        }));
    }

}
