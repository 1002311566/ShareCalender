package com.yihuan.sharecalendar.presenter.contract;

import com.yihuan.sharecalendar.global.base.BaseView;
import com.yihuan.sharecalendar.global.base.OtherPresenter;
import com.yihuan.sharecalendar.modle.bean.home.WeekMoodBean;
import com.yihuan.sharecalendar.modle.bean.mine.MyMoodBean;

/**
 * Created by Ronny on 2017/9/26.
 */

public interface MyMoodContract {
    interface View extends BaseView{
        void onGetMyMoodOK(MyMoodBean moodBean);
        void onGetMyWeekMood(WeekMoodBean weekMoodBean);
        void onSetMoodOK();
    }

    interface Presenter extends OtherPresenter {
        void getMyMood();
        void setMyMood(String mood);
        void getMyWeekMood();
    }
}
