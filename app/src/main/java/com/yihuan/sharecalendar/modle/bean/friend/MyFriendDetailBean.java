package com.yihuan.sharecalendar.modle.bean.friend;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Ronny on 2017/9/28.
 */

public class MyFriendDetailBean implements Serializable{
    /**
     * activeId : null
     * userId : 8
     * friendId : 9
     * groupId : 22
     * cnCalendarBirthday : null
     * birthday : null
     * isPermenentRemind : 0
     * emotionPermission : 0
     * createtime : 2017年11月04日
     * headerImage : permanently/bd9/361/b2a/izwz9dc6tjfwoo65crzoadz_1510019991846_99_a634e7960e04b1633c103382004fe526.jpg
     * nickname : 德玛西亚
     * currentMood : 3
     * sex : 1
     * signature : asd
     * groupName : null
     * city : 长治市
     * province : 山西省
     * activityList : [{"activeId":46,"title":"打球","startTime":"2017年11月13日 16:11"},{"activeId":44,"title":"微微一笑","startTime":"2017年11月13日 14:23"}]
     */

    private Object id;
    private int userId;
    private int friendId;
    private int groupId;
    private Object cnCalendarBirthday;
    private String birthday;
    private String isPermenentRemind;
    private String emotionPermission;
    private String createtime;
    private String headerImage;
    private String nickname;
    private String currentMood;
    private String sex;
    private String signature;
    private Object groupName;
    private String city;
    private String province;
    private List<ActivityListBean> activityList;

    public Object getId() {
        return id;
    }

    public void setId(Object id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getFriendId() {
        return friendId;
    }

    public void setFriendId(int friendId) {
        this.friendId = friendId;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public Object getCnCalendarBirthday() {
        return cnCalendarBirthday;
    }

    public void setCnCalendarBirthday(Object cnCalendarBirthday) {
        this.cnCalendarBirthday = cnCalendarBirthday;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getIsPermenentRemind() {
        return isPermenentRemind;
    }

    public void setIsPermenentRemind(String isPermenentRemind) {
        this.isPermenentRemind = isPermenentRemind;
    }

    public String getEmotionPermission() {
        return emotionPermission;
    }

    public void setEmotionPermission(String emotionPermission) {
        this.emotionPermission = emotionPermission;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getHeaderImage() {
        return headerImage;
    }

    public void setHeaderImage(String headerImage) {
        this.headerImage = headerImage;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getCurrentMood() {
        return currentMood;
    }

    public void setCurrentMood(String currentMood) {
        this.currentMood = currentMood;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public Object getGroupName() {
        return groupName;
    }

    public void setGroupName(Object groupName) {
        this.groupName = groupName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public List<ActivityListBean> getActivityList() {
        return activityList;
    }

    public void setActivityList(List<ActivityListBean> activityList) {
        this.activityList = activityList;
    }


    public static class ActivityListBean implements Serializable{
        /**
         * id : 849
         * title : 莫
         * address :
         * startTime : 2018年01月18日 17:42
         * endTime : 2018年01月18日 18:42
         * userId : 34
         * longitude : 0.0
         * latitude : 0.0
         * isRepeadRemind : 0
         * repeatPeriod : -1
         * comments :
         * type : 1
         * isRemindAllUser : 0
         * createtime : 2018年01月18日 17:42
         * toggle : 1
         * fullDay : 0
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
    }
}
