package com.yihuan.sharecalendar.modle.bean.mine;

/**
 * Created by Ronny on 2017/11/23.
 */

public class MyMoodBean {
    /**
     * activeId : 15
     * userId : null
     * emotion : 2
     * pubTime : 2017-11-23
     */

    private Integer id;
    private Integer userId;
    private String emotion;
    private String pubTime;

    public int getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getEmotion() {
        return emotion;
    }

    public void setEmotion(String emotion) {
        this.emotion = emotion;
    }

    public String getPubTime() {
        return pubTime;
    }

    public void setPubTime(String pubTime) {
        this.pubTime = pubTime;
    }
}
