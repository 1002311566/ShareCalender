package com.yihuan.sharecalendar.presenter;

import android.os.Build;
import android.support.annotation.RequiresApi;

import com.yihuan.sharecalendar.global.base.BasePresenter;
import com.yihuan.sharecalendar.http.Api;
import com.yihuan.sharecalendar.http.help.MyObserver;
import com.yihuan.sharecalendar.modle.bean.active.TimeBean;
import com.yihuan.sharecalendar.modle.bean.settting.UserBean;
import com.yihuan.sharecalendar.modle.calendar.ActiveBean;
import com.yihuan.sharecalendar.modle.db.DBDao;
import com.yihuan.sharecalendar.presenter.contract.MonthFragmentContract;
import com.yihuan.sharecalendar.ui.fragment.MonthFragment;

import java.util.List;

/**
 * Created by Ronny on 2017/10/2.
 */

public class MonthFragmentPresenter extends BasePresenter<MonthFragment> implements MonthFragmentContract.Presenter {
    public MonthFragmentPresenter(MonthFragment monthFragment) {
        super(monthFragment);
    }

    @Override
    public void getUserInfo() {
        Api.getUserInfo(bind(new MyObserver<UserBean>() {
            @Override
            protected void onFailure(int code, String msg) {
                mView.showLoaddingView(false);
                mView.showToast(msg);
            }

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            protected void onSucceed(UserBean userBean) {
                mView.showLoaddingView(false);
                mView.onGetUserInfoOK(userBean);
            }
        }));
    }


    /**
     * 获取指定日期的日程
     * @param timeBean
     */
    @Override
    public void getScheduleByDay(TimeBean timeBean) {
        //日期格式  yyyy年MM月dd日
        List<ActiveBean> list = DBDao.getDao().getScheduleByYMD(timeBean.toYMD());
        mView.onGetScheduleByDayOk(list);

//        Api.getScheduleListByDay(timeBean.toYMD(), bind(new MyObserver<ScheduleListBean>() {
//            @Override
//            protected void onFailure(int code, String msg) {
//                mView.showToast(msg);
//            }
//
//            @Override
//            protected void onSucceed(ScheduleListBean bean) {
//                if(bean != null){
//                    mView.onGetScheduleByDayOk(bean);
//                }
//            }
//        }));
    }

    /**
     * 获取某月的所有日程
     * @param date
     */
//    @Override
//    public void getScheduleByMonth(final TimeBean date) {
//        Api.getScheduleListByMonth(date.toYM(), bind(new MyObserver<ScheduleListBean>() {
//            @Override
//            protected void onFailure(int code, String msg) {
//                mView.showToast(msg);
//            }
//
//            @Override
//            protected void onSucceed(ScheduleListBean bean) {
//                if(bean != null && bean.getActivityList() != null){
//                    List<ScheduleListBean.ActivityListBean> beanList = bean.getActivityList();
////                    //todo 将日程保存到数据库
//                    DBDao dao = DBDao.getDao();
//                    for (ScheduleListBean.ActivityListBean b : beanList){
//                        if(dao.isHas(b.getId())){
//                            dao.updateSchdule(b);
//                        }else{
//                            dao.addSchedule(b);
//                        }
//                    }
//                    ScheduleManager.getInstance().resetCurrentMonth(date.toYM());
//                    mView.onGetScheduleByMonthOk(bean);
//                }
//            }
//        }));
//    }
}
