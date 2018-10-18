package com.yihuan.sharecalendar.modle.bean.friend;

/**
 * Created by Ronny on 2017/9/26.
 * 标签列表
 */

public class TagListBean {
    /**
     * activeId : 37
     * groupName : 拜拜看看
     * userId : 8
     * inGroup : false
     * userFriendList : null
     * groupFriendNumber : 2
     */

    private int id;
    private String groupName;
    private int userId;
    private Boolean inGroup;
    private Object userFriendList;
    private int groupFriendNumber;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Boolean isInGroup() {
        return inGroup;
    }

    public void setInGroup(Boolean inGroup) {
        this.inGroup = inGroup;
    }

    public Object getUserFriendList() {
        return userFriendList;
    }

    public void setUserFriendList(Object userFriendList) {
        this.userFriendList = userFriendList;
    }

    public int getGroupFriendNumber() {
        return groupFriendNumber;
    }

    public void setGroupFriendNumber(int groupFriendNumber) {
        this.groupFriendNumber = groupFriendNumber;
    }
}
