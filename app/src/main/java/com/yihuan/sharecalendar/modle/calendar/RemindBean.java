package com.yihuan.sharecalendar.modle.calendar;


import com.yihuan.sharecalendar.modle.bean.active.TimeBean;
import com.yihuan.sharecalendar.modle.bean.friend.FriendListBean;
import com.yihuan.sharecalendar.modle.bean.settting.ReminderTimeBean;
import com.yihuan.sharecalendar.ui.activity.setting.RemindDetails;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ronny on 2017/11/27.
 */

public class RemindBean {
    private int publish_user_id = -1;
    private int remind_id = -1;//提醒id
    private String name;//标题
    private TimeBean startTime;//开始时间
    private TimeBean endTime;//结束时间
    private String dayType = "1";//时间类型 1 周 2 月
    private String days;//时间值， 用逗号隔开
    private List<ReminderTimeBean> reminderTimeList = new ArrayList<>();//时间点
    private List<FriendListBean> shareFriendList = new ArrayList<>();//共享好友
    private String taskType="1";//提醒类型 1 系统,  2 用户 3 分享

    public int getPublish_user_id() {
        return publish_user_id;
    }

    public void setPublish_user_id(int publish_user_id) {
        this.publish_user_id = publish_user_id;
    }

    public int getRemind_id() {
        return remind_id;
    }

    public void setRemind_id(int remind_id) {
        this.remind_id = remind_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TimeBean getStartTime() {
        return startTime;
    }

    public void setStartTime(TimeBean startTime) {
        this.startTime = startTime;
    }

    public TimeBean getEndTime() {
        return endTime;
    }

    public void setEndTime(TimeBean endTime) {
        this.endTime = endTime;
    }

    public String getDayType() {
        return dayType;
    }

    public void setDayType(String dayType) {
        this.dayType = dayType;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public List<ReminderTimeBean> getReminderTimeList() {
        return reminderTimeList;
    }

    public void setReminderTimeList(List<ReminderTimeBean> reminderTimeList) {
        this.reminderTimeList = reminderTimeList;
    }

    public void setReminderTimeLists(List<RemindDetails.ReminderTimeListBean> list) {
        for (RemindDetails.ReminderTimeListBean b : list){
            this. reminderTimeList.add(new ReminderTimeBean(b.getRemindHour(), b.getRemindMinute()));
        }
    }

    public List<FriendListBean> getShareFriendList() {
        return shareFriendList;
    }

    public void setShareFriendList(List<FriendListBean> shareFriendList) {
        this.shareFriendList = shareFriendList;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public String getTaskType() {
        return taskType;
    }
    //    public void setWeekDays(List<Boolean> list) {
//        if(list == null)return;
//        if(list.size() <= 0)return;
//
//        StringBuffer sb = new StringBuffer();
//        for (int i = 0; i< list.size(); i++){
//            if(list.get(i)){
//                sb.append(i+1+",");
//            }
//        }
//        if(sb.length() > 0){
//            sb.deleteCharAt(sb.length()  - 1);
//        }
//        setWeekDays(sb.toString());
//    }

}
