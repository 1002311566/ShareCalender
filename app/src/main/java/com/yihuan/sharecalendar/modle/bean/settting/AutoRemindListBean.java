package com.yihuan.sharecalendar.modle.bean.settting;

import java.util.List;

/**
 * Created by Ronny on 2017/9/29.
 * 定制化提醒列表响应
 */

public class AutoRemindListBean {

    /**
     * id : 13
     * name : 喝水提醒
     * startTime : 2018年01月01日
     * endTime : 2018年01月31日
     * userId : 0
     * days : 0,1,2,3,4,5,6
     * createtime : 2018-01-20 15:39:01
     * taskType : 1
     * dayType : 1
     * reminderTimeList : [{"id":73,"remindHour":"12","taskRemindId":13,"remindMinute":"00"},{"id":74,"remindHour":"13","taskRemindId":13,"remindMinute":"00"},{"id":75,"remindHour":"14","taskRemindId":13,"remindMinute":"00"},{"id":76,"remindHour":"09","taskRemindId":13,"remindMinute":"00"}]
     * shareFriendList : null
     * weekDays : null
     * monthDays : null
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
    private Object shareFriendList;
    private Object weekDays;
    private Object monthDays;
    private List<ReminderTimeListBean> reminderTimeList;

    public boolean isSelect;

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

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

    public Object getShareFriendList() {
        return shareFriendList;
    }

    public void setShareFriendList(Object shareFriendList) {
        this.shareFriendList = shareFriendList;
    }

    public Object getWeekDays() {
        return weekDays;
    }

    public void setWeekDays(Object weekDays) {
        this.weekDays = weekDays;
    }

    public Object getMonthDays() {
        return monthDays;
    }

    public void setMonthDays(Object monthDays) {
        this.monthDays = monthDays;
    }

    public List<ReminderTimeListBean> getReminderTimeList() {
        return reminderTimeList;
    }

    public void setReminderTimeList(List<ReminderTimeListBean> reminderTimeList) {
        this.reminderTimeList = reminderTimeList;
    }

    public static class ReminderTimeListBean {
        /**
         * id : 73
         * remindHour : 12
         * taskRemindId : 13
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
