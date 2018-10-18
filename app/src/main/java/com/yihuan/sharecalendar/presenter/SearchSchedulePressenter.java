package com.yihuan.sharecalendar.presenter;

import android.text.TextUtils;

import com.yihuan.sharecalendar.global.base.BasePresenter;
import com.yihuan.sharecalendar.http.Api;
import com.yihuan.sharecalendar.http.help.MyObserver;
import com.yihuan.sharecalendar.modle.bean.home.ScheduleListBean;
import com.yihuan.sharecalendar.modle.calendar.ActiveBean;
import com.yihuan.sharecalendar.modle.db.DBDao;
import com.yihuan.sharecalendar.presenter.contract.ScheduleSearchContract;
import com.yihuan.sharecalendar.ui.activity.hometitle.ScheduleSearchActivity;

import java.util.List;

/**
 * Created by Ronny on 2017/11/7.
 */

public class SearchSchedulePressenter extends BasePresenter<ScheduleSearchActivity> implements ScheduleSearchContract.Presenter {


    public SearchSchedulePressenter(ScheduleSearchActivity scheduleSearchActivity) {
        super(scheduleSearchActivity);
    }

    @Override
    public void searchSchedule(String title) {
        if(TextUtils.isEmpty(title)){
            List<ActiveBean> list = DBDao.getDao().getAllSchedule(0, 0);
            mView.onSearchScheduleOK(list);
        }else{
            List<ActiveBean> list = DBDao.getDao().searchSchedule(title);
            mView.onSearchScheduleOK(list);
        }

//        Api.searchSchedule(title, bind(new MyObserver<ScheduleListBean>() {
//            @Override
//            protected void onFailure(int code, String msg) {
//                mView.showToast(msg);
//            }
//
//            @Override
//            protected void onSucceed(ScheduleListBean bean) {
//                mView.onSearchScheduleOK(bean);
//            }
//        }));
    }
}
