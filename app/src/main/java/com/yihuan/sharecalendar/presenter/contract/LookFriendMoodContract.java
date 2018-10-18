package com.yihuan.sharecalendar.presenter.contract;

import com.yihuan.sharecalendar.global.base.BaseView;
import com.yihuan.sharecalendar.global.base.OtherPresenter;
import com.yihuan.sharecalendar.modle.bean.home.WeekMoodBean;
import com.yihuan.sharecalendar.modle.bean.mine.MyMoodBean;

import java.util.List;

/**
 * Created by Ronny on 2017/9/26.
 * 查看好友心情
 */

public interface LookFriendMoodContract {
    interface View extends BaseView{
        void onQueryFriendMood(List<MyMoodBean> bean);
        void onQueryFriendWeekMood(WeekMoodBean bean);
    }

    interface Presenter extends OtherPresenter {
        void queryFriendMood(String friendId);
        void queryFriendWeekMood(String friendId);
    }
}
