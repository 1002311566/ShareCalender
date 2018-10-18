package com.yihuan.sharecalendar.modle.bean.friend;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Ronny on 2017/9/26.
 * 全部好友列表
 */

public class FriendListBean implements Parcelable{
        /**
         * id : null
         * userId : 54
         * friendId : 34
         * groupId : null
         * cnCalendarBirthday : 04月01日
         * birthday : 02月20日
         * isPermenentRemind : 1
         * emotionPermission : 0
         * createtime : 2018年01月18日
         * headerImage : permanently/dbc/aa9/6b5/izwz9dc6tjfwoo65crzoadz_1515744915622_73_47f409718928fd05a33e890579ec393f.jpg
         * nickname : 四大皆空三十多流量监控看
         * currentMood : 4
         * sex : null
         * signature : null
         * groupName : null
         * city : null
         * province : null
         * activityList : null
         * nicknameInitial : S
         */

        private Object id;
        private int userId;
        private int friendId;
        private Object groupId;
        private String cnCalendarBirthday;
        private String birthday;
        private String isPermenentRemind;
        private String emotionPermission;
        private String createtime;
        private String headerImage;
        private String nickname;
        private String currentMood;
        private String sex;
        private String signature;
        private String groupName;
        private String city;
        private String province;
        private Object activityList;
        private String nicknameInitial;

    public boolean isSelect;//是否选择

    public FriendListBean(){}

    public FriendListBean(int friendId, String nickname, String headerImage){
        this.friendId = friendId;
        this.nickname = nickname;
        this.headerImage = headerImage;
    }

    public FriendListBean(int friendid, String nickName, String nicknameLetter, int mood, String headerImage, String birthday){
        this.friendId = friendid;
        this.nickname = nickName;
        this.nicknameInitial = nicknameLetter;
        this.currentMood = String.valueOf(mood);
        this.headerImage = headerImage;
        this.birthday = birthday;
    }

    protected FriendListBean(Parcel in) {
        isSelect = in.readByte() != 0;
        userId = in.readInt();
        friendId = in.readInt();
        cnCalendarBirthday = in.readString();
        birthday = in.readString();
        isPermenentRemind = in.readString();
        emotionPermission = in.readString();
        createtime = in.readString();
        headerImage = in.readString();
        nickname = in.readString();
        currentMood = in.readString();
        sex = in.readString();
        signature = in.readString();
        groupName = in.readString();
        city = in.readString();
        province = in.readString();
        nicknameInitial = in.readString();
    }

    public static final Creator<FriendListBean> CREATOR = new Creator<FriendListBean>() {
        @Override
        public FriendListBean createFromParcel(Parcel in) {
            return new FriendListBean(in);
        }

        @Override
        public FriendListBean[] newArray(int size) {
            return new FriendListBean[size];
        }
    };

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

    public Object getGroupId() {
        return groupId;
    }

    public void setGroupId(Object groupId) {
        this.groupId = groupId;
    }

    public String getCnCalendarBirthday() {
        return cnCalendarBirthday;
    }

    public void setCnCalendarBirthday(String cnCalendarBirthday) {
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

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
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

    public Object getActivityList() {
        return activityList;
    }

    public void setActivityList(Object activityList) {
        this.activityList = activityList;
    }

    public String getNicknameInitial() {
        return nicknameInitial;
    }

    public void setNicknameInitial(String nicknameInitial) {
        this.nicknameInitial = nicknameInitial;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (isSelect ? 1 : 0));
        dest.writeInt(userId);
        dest.writeInt(friendId);
        dest.writeString(cnCalendarBirthday);
        dest.writeString(birthday);
        dest.writeString(isPermenentRemind);
        dest.writeString(emotionPermission);
        dest.writeString(createtime);
        dest.writeString(headerImage);
        dest.writeString(nickname);
        dest.writeString(currentMood);
        dest.writeString(sex);
        dest.writeString(signature);
        dest.writeString(groupName);
        dest.writeString(city);
        dest.writeString(province);
        dest.writeString(nicknameInitial);
    }
}
