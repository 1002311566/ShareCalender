package com.yihuan.sharecalendar.modle.bean.friend;

import java.io.Serializable;

/**
 * Created by Ronny on 2017/9/26.
 * 新的朋友，申请列表响应体
 */

public class NewApplyListBean implements Serializable{
        /**
         * activeId : 76
         * userId : 8
         * friendId : 9
         * addTime : 2018-01-03 15:10:15
         * attachMessage : 没有
         * proStatus : 0
         * headerImage : permanently/361/b2a/ee5/izwz9dc6tjfwoo65crzoadz_1513907337592_89_a634e7960e04b1633c103382004fe526.jpg
         * nickname : @@@@
         */

        private int id;
        private int userId;
        private int friendId;
        private String addTime;
        private String attachMessage;
        private String proStatus;
        private String headerImage;
        private String nickname;

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

        public int getFriendId() {
            return friendId;
        }

        public void setFriendId(int friendId) {
            this.friendId = friendId;
        }

        public String getAddTime() {
            return addTime;
        }

        public void setAddTime(String addTime) {
            this.addTime = addTime;
        }

        public String getAttachMessage() {
            return attachMessage;
        }

        public void setAttachMessage(String attachMessage) {
            this.attachMessage = attachMessage;
        }

        public String getProStatus() {
            return proStatus;
        }

        public void setProStatus(String proStatus) {
            this.proStatus = proStatus;
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
}

