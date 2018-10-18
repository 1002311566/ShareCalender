package com.yihuan.sharecalendar.presenter.contract;

import com.yihuan.sharecalendar.global.base.BaseView;
import com.yihuan.sharecalendar.global.base.OtherPresenter;
import com.yihuan.sharecalendar.modle.bean.login.CityBean;
import com.yihuan.sharecalendar.modle.bean.login.ConstellationBean;
import com.yihuan.sharecalendar.modle.bean.login.ProvinceBean;

import java.util.List;

/**
 * Created by Ronny on 2017/9/9.
 */

public interface SetInfo_Select_Contract {
    interface View extends BaseView{
        void getConstellationListOK(List<ConstellationBean> list);
        void getAddressProvinceListOK(List<ProvinceBean> provinceBeen);
        void getAddressCityListOK(List<CityBean> list);
    }

    interface Presenter extends OtherPresenter {
        void getConstellationList();
        void getAddressProvinceList();
        void getAddressCityList(String provinceId);
    }
}
