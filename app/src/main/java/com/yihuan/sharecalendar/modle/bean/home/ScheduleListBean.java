package com.yihuan.sharecalendar.modle.bean.home;

import android.text.TextUtils;

import com.yihuan.sharecalendar.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Ronny on 2017/9/21.
 * 首页--》日程列表
 */

public class ScheduleListBean {


    /**
     * activeId : null
     * activityList : [{"activeId":143,"title":"具体","address":"广州市海珠区迪士尼英语(金海湾中心)","startTime":"2017年12月15日 18:04","endTime":"2017年12月15日 19:04","userId":9,"longitude":"113.308048","latitude":"23.109213","isRepeadRemind":"1","repeatPeriod":"-1","comments":"QQ他你脱考虑KTV裤兔拜拜","type":"1","isRemindAllUser":"0","createtime":"2017年12月15日 18:05","toggle":"1","fullDay":"0","activityInviteList":[{"activeId":154,"activityId":143,"inviteUser":13,"status":"0","dealTime":null,"isRead":"0","inviteUserHeadImg":"permanently/b2a/ee5/3fd/izwz9dc6tjfwoo65crzoadz_1512119676591_76_a634e7960e04b1633c103382004fe526.jpg","nickname":"草"}]}]
     */

    private Integer id;
    private List<ActivityListBean> activityList;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<ActivityListBean> getActivityList() {
        return activityList;
    }

    public void setActivityList(List<ActivityListBean> activityList) {
        this.activityList = activityList;
    }

    public static class ActivityListBean {
        /**
         * activeId : 143
         * title : 具体
         * address : 广州市海珠区迪士尼英语(金海湾中心)
         * startTime : 2017年12月15日 18:04
         * endTime : 2017年12月15日 19:04
         * userId : 9
         * longitude : 113.308048
         * latitude : 23.109213
         * isRepeadRemind : 1
         * repeatPeriod : -1
         * comments : QQ他你脱考虑KTV裤兔拜拜
         * type : 1
         * isRemindAllUser : 0
         * createtime : 2017年12月15日 18:05
         * toggle : 1
         * fullDay : 0
         * activityInviteList : [{"activeId":154,"activityId":143,"inviteUser":13,"status":"0","dealTime":null,"isRead":"0","inviteUserHeadImg":"permanently/b2a/ee5/3fd/izwz9dc6tjfwoo65crzoadz_1512119676591_76_a634e7960e04b1633c103382004fe526.jpg","nickname":"草"}]
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
        private Integer activityInviteNumber;
        private List<ActivityInviteListBean> activityInviteList;

        public Integer getActivityInviteNumber() {
            return activityInviteNumber;
        }

        public void setActivityInviteNumber(Integer activityInviteNumber) {
            this.activityInviteNumber = activityInviteNumber;
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

        public String getRepeatPeriod() {
            return repeatPeriod;
        }

        public void setRepeatPeriod(String repeatPeriod) {
            this.repeatPeriod = repeatPeriod;
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

        public String getFullDay() {
            return fullDay;
        }

        public void setFullDay(String fullDay) {
            this.fullDay = fullDay;
        }

        public List<ActivityInviteListBean> getActivityInviteList() {
            return activityInviteList;
        }

        public void setActivityInviteList(List<ActivityInviteListBean> activityInviteList) {
            this.activityInviteList = activityInviteList;
        }

        public static class ActivityInviteListBean {
            /**
             * activeId : 154
             * activityId : 143
             * inviteUser : 13
             * status : 0
             * dealTime : null
             * isRead : 0
             * inviteUserHeadImg : permanently/b2a/ee5/3fd/izwz9dc6tjfwoo65crzoadz_1512119676591_76_a634e7960e04b1633c103382004fe526.jpg
             * nickname : 草
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
        }

        //todo------------------------------------------------
        public boolean isFullDay() {
            //1：是  、 0：否
            if (!TextUtils.isEmpty(fullDay) && fullDay.equals("1")) {
                return true;
            }
            return false;
        }

        /**
         * 获取 hh:mm
         *
         * @return
         */
        public String getHourTime() {
//                SimpleDateFormat format = new SimpleDateFormat("HH:mm");
//                try {
//                    return format.format(startTime);
//                }catch (Exception e){
//                    e.printStackTrace();
//                    return "";
//                }
            if (!TextUtils.isEmpty(startTime) && startTime.contains(":") && startTime.contains(" ")) {
                return startTime.substring(startTime.indexOf(' ') + 1, startTime.length());
            }
            return "";
        }

        /**
         * 获取 yera month day
         *
         * @return
         */
        public String getYMD() {
            if (!TextUtils.isEmpty(startTime) && startTime.contains(" ")) {
                return startTime.substring(0, startTime.indexOf(' '));
            }
            return "";
        }

        public Date getStartDate() {
            Date date = null;
            SimpleDateFormat f = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
            try {
                date = f.parse(startTime);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return date;
        }

        //如果邀请人数大于0,就是共享活动，显示黄点
        public int getSchduleTypeDot() {
            boolean isShare = false;
            if (isShare) {
                return R.drawable.calendar_shape_orange_dot;
            } else {
                return R.drawable.calendar_shape_blue_dot;
            }
        }

        public boolean isShare(){
            if((activityInviteList!= null && activityInviteList.size() > 0) || activityInviteNumber > 0){
                return true;
            }
            return false;
        }


        public String getStartTimeToHourInt() {
            String hourTime = getHourTime();
            return hourTime.substring(0, 2);
        }
    }
}
