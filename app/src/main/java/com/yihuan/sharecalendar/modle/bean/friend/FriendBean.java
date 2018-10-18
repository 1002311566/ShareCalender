package com.yihuan.sharecalendar.modle.bean.friend;

/**
 * Created by Ronny on 2017/9/30.
 */

public class FriendBean {
    private String friendId;


    public FriendBean(){
    }
    public FriendBean(String friendId){
        this.friendId = friendId;
    }

    public String getFriendId() {
        return friendId;
    }

    public void setFriendId(String friendId) {
        this.friendId = friendId;
    }
}
