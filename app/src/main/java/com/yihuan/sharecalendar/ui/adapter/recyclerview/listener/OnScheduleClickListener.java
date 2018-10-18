package com.yihuan.sharecalendar.ui.adapter.recyclerview.listener;

/**
 * Created by Ronny on 2017/11/7.
 */

public interface OnScheduleClickListener {
    /**
     * 去活动详情页
     * @param userId 两种类型：个人日程， 共享活动
     * @param activeId 活动id
     */
    void onScheduleClick(int userId, int activeId);
}
