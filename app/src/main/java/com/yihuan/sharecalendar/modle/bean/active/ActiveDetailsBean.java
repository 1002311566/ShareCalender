package com.yihuan.sharecalendar.modle.bean.active;

import android.text.TextUtils;

import java.util.List;

/**
 * Created by Ronny on 2017/11/7.
 * 活动详情
 */

public class ActiveDetailsBean {


    /**
     * id : 847
     * title : 谢谢
     * address :
     * startTime : 2018年01月19日 15:25
     * endTime : 2018年01月19日 16:25
     * userId : 54
     * longitude : 0.0
     * latitude : 0.0
     * isRepeadRemind : 0
     * repeatPeriod : -1
     * comments :
     * type : 1
     * isRemindAllUser : 0
     * createtime : 2018年01月18日 15:25
     * toggle : 1
     * fullDay : 0
     * activityInviteList : [{"id":700,"activityId":847,"inviteUser":34,"status":"0","dealTime":null,"isRead":"0","inviteUserHeadImg":"permanently/dbc/aa9/6b5/izwz9dc6tjfwoo65crzoadz_1515744915622_73_47f409718928fd05a33e890579ec393f.jpg","nickname":"四大皆空三十多流量监控看"}]
     * activityPartinList : []
     * activityAlarms : [{"id":413,"activityId":847,"alarmTime":5}]
     * activityInviteNumber : 1
     * activityPartinNumber : 0
     * activityImages : []
     */

    private int id;
    private String title;
    private String address;
    private String startTime;
    private String endTime;
    private int userId;
    private String longitude;
    private String latitude;
    private String isRepeadRemind;
    private String repeatPeriod;
    private String comments;
    private String type;
    private String isRemindAllUser;
    private String createtime;
    private String toggle;
    private String fullDay;
    private String region;
    private int activityInviteNumber;
    private int activityPartinNumber;
    private List<ActivityInviteListBean> activityInviteList;
    private List<?> activityPartinList;
    private List<ActivityAlarmsBean> activityAlarms;
    private String inviteStatus;//邀请状态
    private List<String> activityImages;//活动图片

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getRepeatPeriod() {
        if (repeatPeriod == null)
            return "0";
        if (repeatPeriod.equals("-1"))
            return "0";
        return repeatPeriod;
    }

    public String getInviteStatus() {
        return inviteStatus;
    }

    public void setInviteStatus(String inviteStatus) {
        this.inviteStatus = inviteStatus;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getIsRepeadRemind() {
        return isRepeadRemind;
    }

    public void setIsRepeadRemind(String isRepeadRemind) {
        this.isRepeadRemind = isRepeadRemind;
    }

    public void setRepeatPeriod(String repeatPeriod) {
        this.repeatPeriod = repeatPeriod;
    }

    public String getFullDay() {
        return fullDay == null ? "" : fullDay;
    }


    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIsRemindAllUser() {
        return isRemindAllUser;
    }

    public void setIsRemindAllUser(String isRemindAllUser) {
        this.isRemindAllUser = isRemindAllUser;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getToggle() {
        return toggle;
    }

    public void setToggle(String toggle) {
        this.toggle = toggle;
    }

    public void setFullDay(String fullDay) {
        this.fullDay = fullDay;
    }

    public int getActivityInviteNumber() {
        return activityInviteNumber;
    }

    public void setActivityInviteNumber(int activityInviteNumber) {
        this.activityInviteNumber = activityInviteNumber;
    }

    public int getActivityPartinNumber() {
        return activityPartinNumber;
    }

    public void setActivityPartinNumber(int activityPartinNumber) {
        this.activityPartinNumber = activityPartinNumber;
    }

    public List<ActivityInviteListBean> getActivityInviteList() {
        return activityInviteList;
    }

    public void setActivityInviteList(List<ActivityInviteListBean> activityInviteList) {
        this.activityInviteList = activityInviteList;
    }

    public List<?> getActivityPartinList() {
        return activityPartinList;
    }

    public void setActivityPartinList(List<?> activityPartinList) {
        this.activityPartinList = activityPartinList;
    }

    public List<ActivityAlarmsBean> getActivityAlarms() {
        return activityAlarms;
    }

    public void setActivityAlarms(List<ActivityAlarmsBean> activityAlarms) {
        this.activityAlarms = activityAlarms;
    }

    public static class ActivityInviteListBean {
        /**
         * id : 700
         * activityId : 847
         * inviteUser : 34
         * status : 0
         * dealTime : null
         * isRead : 0
         * inviteUserHeadImg : permanently/dbc/aa9/6b5/izwz9dc6tjfwoo65crzoadz_1515744915622_73_47f409718928fd05a33e890579ec393f.jpg
         * nickname : 四大皆空三十多流量监控看
         */

        private int id;
        private int activityId;
        private int inviteUser;
        private String status;
        private Object dealTime;
        private String isRead;
        private String inviteUserHeadImg;
        private String nickname;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getActivityId() {
            return activityId;
        }

        public void setActivityId(int activityId) {
            this.activityId = activityId;
        }

        public int getInviteUser() {
            return inviteUser;
        }

        public void setInviteUser(int inviteUser) {
            this.inviteUser = inviteUser;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public Object getDealTime() {
            return dealTime;
        }

        public void setDealTime(Object dealTime) {
            this.dealTime = dealTime;
        }

        public String getIsRead() {
            return isRead;
        }

        public void setIsRead(String isRead) {
            this.isRead = isRead;
        }

        public String getInviteUserHeadImg() {
            return inviteUserHeadImg;
        }

        public void setInviteUserHeadImg(String inviteUserHeadImg) {
            this.inviteUserHeadImg = inviteUserHeadImg;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getStatusName() {
            if (TextUtils.isEmpty(status))
                return "";
            switch (status) {
//                -1: 拒绝活动、1: 接受活动、0: 未处理、2: 已取消 3 重新申请
                case "-1":
                    return "已拒绝";
                case "0":
                    return "等待中";
                case "1":
                    return "已接受";
                case "2":
                    return "已取消";
                case "3":
                    return "重新申请";
                default:
                    return "";
            }
        }
    }

    public static class ActivityAlarmsBean {
        /**
         * id : 413
         * activityId : 847
         * alarmTime : 5
         */

        private int id;
        private int activityId;
        private int alarmTime;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getActivityId() {
            return activityId;
        }

        public void setActivityId(int activityId) {
            this.activityId = activityId;
        }

        public int getAlarmTime() {
            return alarmTime;
        }

        public void setAlarmTime(int alarmTime) {
            this.alarmTime = alarmTime;
        }
    }

    public List<String> getActivityImages() {
        return activityImages;
    }

    public void setActivityImages(List<String> activityImages) {
        this.activityImages = activityImages;
    }
}
