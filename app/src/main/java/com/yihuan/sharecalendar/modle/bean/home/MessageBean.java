package com.yihuan.sharecalendar.modle.bean.home;

import android.text.TextUtils;

/**
 * Created by Ronny on 2017/9/30.
 * 消息列表 --
 */

public class MessageBean {
    /**
     * activeId : 27
     * userId : 8
     * contents : 哈
     * type : 2
     * attachField : 34
     * status : 0
     * senderId : 9
     * nickname : 德玛西亚
     * headerImage : permanently/bd9/361/b2a/izwz9dc6tjfwoo65crzoadz_1510019991846_99_a634e7960e04b1633c103382004fe526.jpg
     * inviteStatus : 0
     */

    private int id;
    private int userId;
    private String contents;
    //todo type类型， 1：通知信息, 2: 邀请信息, 3: 反馈回复信息, 4: 随意铃, 5:定制化提醒分享
    private String type;
    private String attachField;
    private String bizId;//业务id
    private String status;//todo 0 未读 1 已读
    private int senderId;
    private String nickname;
    private String createTime;
    private String headerImage;
    //todo inviteStatus : 当type为2（邀请信息）的时候，这个字段属性才有值 -1: 拒绝活动、1: 接受活动、0: 未处理、2: 活动已取消
    private String inviteStatus;
    private String reminderStatus;//todo 提醒状态 0 正常 1 不在提醒

    public boolean isSelect;//todo 是否是选中状态

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getContents() {
        return contents;
    }

    public String getBizId() {
        return bizId;
    }

    public void setBizId(String bizId) {
        this.bizId = bizId;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAttachField() {
        return attachField;
    }

    public void setAttachField(String attachField) {
        this.attachField = attachField;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getSenderId() {
        return senderId;
    }

    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getHeaderImage() {
        return headerImage;
    }

    public void setHeaderImage(String headerImage) {
        this.headerImage = headerImage;
    }

    public String getReminderStatus() {
        return reminderStatus;
    }

    public void setReminderStatus(String reminderStatus) {
        this.reminderStatus = reminderStatus;
    }

    public String getInviteStatus() {
        if(TextUtils.isEmpty(inviteStatus)){
            return "0";
        }
        return inviteStatus;
    }

    public void setInviteStatus(String inviteStatus) {
        this.inviteStatus = inviteStatus;
    }
    //------------------------------------------------

    //        1：通知信息, 2: 邀请信息, 3: 反馈回复信息, 4: 随意铃, 5:定制化提醒分享
    public String getTypeName() {
        if(TextUtils.isEmpty(type))
            return "";
        switch (type){
            case "1":
                return "通知信息";
            case "2":
                return "邀约提醒";
            case "3":
                return "反馈回复信息";
            case "4":
                return "随意铃";
            case "5":
                return "定制化提醒";
            default:
                return "";
        }
    }


}
