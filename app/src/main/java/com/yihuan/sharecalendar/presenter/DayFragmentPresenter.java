package com.yihuan.sharecalendar.presenter;

import android.text.TextUtils;

import com.yihuan.sharecalendar.global.base.BasePresenter;
import com.yihuan.sharecalendar.modle.bean.active.TimeBean;
import com.yihuan.sharecalendar.modle.calendar.ActiveBean;
import com.yihuan.sharecalendar.modle.db.DBDao;
import com.yihuan.sharecalendar.presenter.contract.DayFragmentContract;
import com.yihuan.sharecalendar.ui.fragment.DayFragment;

import java.util.List;

/**
 * Created by Ronny on 2017/10/8.
 */

public class DayFragmentPresenter extends BasePresenter<DayFragment> implements DayFragmentContract.Presenter {
    public DayFragmentPresenter(DayFragment dayFragment) {
        super(dayFragment);
    }

    @Override
    public void getScheduleListByDay(TimeBean timeBean) {
        if(TextUtils.isEmpty(timeBean.toYMD())){
            return;
        }
        //日期格式  yyyy年MM月dd日 按时间排序
        List<ActiveBean> list = DBDao.getDao().getScheduleByYMDSort(timeBean.toYMD());
        mView.onGetScheduleListByDayOK(list);

//        mView.showLoaddingView(true);
//        Api.getScheduleListByDay(timeBean.toYMD(), bind(new MyObserver<ScheduleListBean>() {
//            @Override
//            protected void onFailure(int code, String msg) {
//                mView.showToast(msg);
////                mView.showLoaddingView(false);
//            }
//
//            @Override
//            protected void onSucceed(ScheduleListBean bean) {
////                mView.showLoaddingView(false);
//                mView.onGetScheduleListByDayOK(bean);
//            }
//        }));
    }
}
