package com.yihuan.sharecalendar.modle.bean.friend;

import com.yihuan.sharecalendar.R;

import java.io.Serializable;

/**
 * Created by Ronny on 2017/9/26.
 * 搜索到用户
 */

public class SearchUserBean implements Serializable {
    /**
     * activeId : 9
     * headerImage : permanently/361/b2a/ee5/izwz9dc6tjfwoo65crzoadz_1513907337592_89_a634e7960e04b1633c103382004fe526.jpg
     * signature :
     * nickname : @@@@
     * period : 2005
     * constellation : 3
     * location : 41
     * loginName : null
     * openId : null
     * uid : null
     * deviceNumber : null
     * bindPhone : 13427585580
     * currentMood : 5
     * skin : null
     * qrcode : null
     * registerTime : null
     * status : null
     * sex : 0
     * lastLoginTime : 2018-01-05 10:47:25
     * city : 丹东市
     * province : 辽宁省
     * constellationName : null
     * hasFriend : true
     */

    private int id;
    private String headerImage;
    private String signature;
    private String nickname;
    private String period;
    private int constellation;
    private int location;
    private Object loginName;
    private Object openId;
    private Object uid;
    private Object deviceNumber;
    private String bindPhone;
    private String currentMood;
    private Object skin;
    private Object qrcode;
    private Object registerTime;
    private Object status;
    private String sex;
    private String lastLoginTime;
    private String city;
    private String province;
    private Object constellationName;
    private boolean hasFriend;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHeaderImage() {
        return headerImage;
    }

    public void setHeaderImage(String headerImage) {
        this.headerImage = headerImage;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public int getConstellation() {
        return constellation;
    }

    public void setConstellation(int constellation) {
        this.constellation = constellation;
    }

    public int getLocation() {
        return location;
    }

    public void setLocation(int location) {
        this.location = location;
    }

    public Object getLoginName() {
        return loginName;
    }

    public void setLoginName(Object loginName) {
        this.loginName = loginName;
    }

    public Object getOpenId() {
        return openId;
    }

    public void setOpenId(Object openId) {
        this.openId = openId;
    }

    public Object getUid() {
        return uid;
    }

    public void setUid(Object uid) {
        this.uid = uid;
    }

    public Object getDeviceNumber() {
        return deviceNumber;
    }

    public void setDeviceNumber(Object deviceNumber) {
        this.deviceNumber = deviceNumber;
    }

    public String getBindPhone() {
        return bindPhone;
    }

    public void setBindPhone(String bindPhone) {
        this.bindPhone = bindPhone;
    }

    public String getCurrentMood() {
        return currentMood;
    }

    public void setCurrentMood(String currentMood) {
        this.currentMood = currentMood;
    }

    public Object getSkin() {
        return skin;
    }

    public void setSkin(Object skin) {
        this.skin = skin;
    }

    public Object getQrcode() {
        return qrcode;
    }

    public void setQrcode(Object qrcode) {
        this.qrcode = qrcode;
    }

    public Object getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Object registerTime) {
        this.registerTime = registerTime;
    }

    public Object getStatus() {
        return status;
    }

    public void setStatus(Object status) {
        this.status = status;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(String lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
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

    public Object getConstellationName() {
        return constellationName;
    }

    public void setConstellationName(Object constellationName) {
        this.constellationName = constellationName;
    }

    public boolean isHasFriend() {
        return hasFriend;
    }

    public void setHasFriend(boolean hasFriend) {
        this.hasFriend = hasFriend;
    }
}
