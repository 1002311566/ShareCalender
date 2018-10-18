package com.yihuan.sharecalendar.presenter.contract;

import com.yihuan.sharecalendar.global.base.BaseView;
import com.yihuan.sharecalendar.global.base.OtherPresenter;
import com.yihuan.sharecalendar.modle.bean.active.AlarmTime;
import com.yihuan.sharecalendar.modle.bean.active.TimeBean;
import com.yihuan.sharecalendar.modle.bean.friend.FriendListBean;
import com.yihuan.sharecalendar.modle.calendar.ActiveBean;

import java.util.List;

/**
 * Created by Ronny on 2017/9/26.
 * 新建活动
 */

public interface CreateActiveContract {
    interface View extends BaseView{
        void onCreateActiveOK();
        void onUploadImgOK(List<String> imgUrlList);
    }

    interface Presenter extends OtherPresenter {
        void createActive(ActiveBean activeBean);
        TimeBean getCurrentTimeBean();
        TimeBean getNextHourTimeBean();
        void editActive(ActiveBean activeBean);
        void uploadImg(List<String> path);
    }
}
