package com.yihuan.sharecalendar.presenter;

import com.yihuan.sharecalendar.global.Constants;
import com.yihuan.sharecalendar.global.base.BasePresenter;
import com.yihuan.sharecalendar.http.Api;
import com.yihuan.sharecalendar.http.help.MyObserver;
import com.yihuan.sharecalendar.modle.bean.home.ScheduleListBean;
import com.yihuan.sharecalendar.modle.calendar.ActiveBean;
import com.yihuan.sharecalendar.modle.db.DBDao;
import com.yihuan.sharecalendar.presenter.contract.ScheduleContract;
import com.yihuan.sharecalendar.ui.fragment.ScheduleFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ronny on 2017/11/4.
 */

public class SchedulePresenter extends BasePresenter<ScheduleFragment> implements ScheduleContract.Presenter {
    private int pageSize = 50;
    private List<ActiveBean> mList;
    public SchedulePresenter(ScheduleFragment scheduleFragment) {
        super(scheduleFragment);
        mList = new ArrayList<>();
    }

    @Override
    public void getScheduleList(int type) {
        if(type == Constants.TYPE_REFRESH){
            resetLastId();
            mList.clear();
            List<ActiveBean> list = DBDao.getDao().getAllSchedule(pageSize, 0);
            mList.addAll(list);
            setLastId(1);
        }else if(type == Constants.TYPE_LOADMORE){
            List<ActiveBean> list = DBDao.getDao().getAllSchedule(pageSize, getLastId());
            mList.addAll(list);
            setLastId(getLastId()+1);
            if(list.size() < pageSize){
                setNoMore();
            }
        }
        mView.onGetScheduleListOK(mList);

//        Api.searchSchedule("", bind(new MyObserver<ScheduleListBean>() {
//            @Override
//            protected void onFailure(int code, String msg) {
//                mView.showToast(msg);
//                mView.onCloseRefresh();
//            }
//
//            @Override
//            protected void onSucceed(ScheduleListBean bean) {
//                mView.onGetScheduleListOK(bean);
//            }
//        }));
    }

    @Override
    public void deleteSchedule(int activeId) {
        if(activeId == -1)return;

        mView.showLoaddingView(true);
        Api.deleteActive(activeId, bind(new MyObserver<Object>() {
            @Override
            protected void onFailure(int code, String msg) {
                mView.showLoaddingView(false);
                mView.showToast(msg);
            }

            @Override
            protected void onSucceed(Object o) {
                mView.showLoaddingView(false);
                mView.showToast("删除成功");
            }
        }));
    }
}
