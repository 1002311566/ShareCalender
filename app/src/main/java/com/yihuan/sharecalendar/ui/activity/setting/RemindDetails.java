package com.yihuan.sharecalendar.ui.activity.setting;

import com.yihuan.sharecalendar.modle.bean.friend.FriendListBean;

import java.util.List;

/**
 * Created by Ronny on 2017/12/13.
 * 提醒详情
 */

public class RemindDetails {
    /**
     * id : 3
     * name : 喝水提醒
     * startTime : 2018年01月18日
     * endTime : 2018年01月18日
     * userId : 0
     * days : 1,2,3
     * createtime : 2018-01-18日 18:12:51
     * taskType : 1
     * dayType : 1
     * reminderTimeList : [{"id":10,"remindHour":"12","taskRemindId":3,"remindMinute":"00"},{"id":11,"remindHour":"13","taskRemindId":3,"remindMinute":"00"},{"id":12,"remindHour":"14","taskRemindId":3,"remindMinute":"00"}]
     * shareFriendList : null
     */

    private int id;
    private String name;
    private String startTime;
    private String endTime;
    private int userId;
    private String days;
    private String createtime;
    private String taskType;
    private String dayType;
    private List<FriendListBean> shareFriendList;
    private List<ReminderTimeListBean> reminderTimeList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public String getDayType() {
        return dayType;
    }

    public void setDayType(String dayType) {
        this.dayType = dayType;
    }

    public List getShareFriendList() {
        return shareFriendList;
    }

    public void setShareFriendList(List<FriendListBean> shareFriendList) {
        this.shareFriendList = shareFriendList;
    }

    public List<ReminderTimeListBean> getReminderTimeList() {
        return reminderTimeList;
    }

    public void setReminderTimeList(List<ReminderTimeListBean> reminderTimeList) {
        this.reminderTimeList = reminderTimeList;
    }

    public static class ReminderTimeListBean {
        /**
         * id : 10
         * remindHour : 12
         * taskRemindId : 3
         * remindMinute : 00
         */

        private int id;
        private String remindHour;
        private int taskRemindId;
        private String remindMinute;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getRemindHour() {
            return remindHour;
        }

        public void setRemindHour(String remindHour) {
            this.remindHour = remindHour;
        }

        public int getTaskRemindId() {
            return taskRemindId;
        }

        public void setTaskRemindId(int taskRemindId) {
            this.taskRemindId = taskRemindId;
        }

        public String getRemindMinute() {
            return remindMinute;
        }

        public void setRemindMinute(String remindMinute) {
            this.remindMinute = remindMinute;
        }
    }
}
