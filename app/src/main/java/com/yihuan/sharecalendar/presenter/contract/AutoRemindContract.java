package com.yihuan.sharecalendar.presenter.contract;

import com.yihuan.sharecalendar.global.base.BaseView;
import com.yihuan.sharecalendar.global.base.OtherPresenter;
import com.yihuan.sharecalendar.modle.bean.settting.AutoRemindListBean;

import java.util.List;

/**
 * Created by Ronny on 2017/9/26.
 * 定制化提醒
 */

public interface AutoRemindContract {
    interface View extends BaseView{
        void onGetAutoRemindListOK(List<AutoRemindListBean> beanList);
        void onDeleteRemindOK();
    }

    interface Presenter extends OtherPresenter {
        void getAutoRemindList(int typeRefresh);
        void deleteRemind(List<AutoRemindListBean> taskReminderId);
    }
}
