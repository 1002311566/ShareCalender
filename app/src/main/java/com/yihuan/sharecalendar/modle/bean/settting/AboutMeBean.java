package com.yihuan.sharecalendar.modle.bean.settting;

/**
 * Created by Ronny on 2017/10/2.
 */

public class AboutMeBean {

    /**
     * logo : /a/b/logo.jpg
     * version : 共享日历V1.0
     * content : 共享日历，共享你活动，你的工作，你的乐趣。共享日历，共享你活动，你的工作，你的乐趣。共享日历，共享你活动，你的工作，你的乐趣。
     */

    private String logo;
    private String version;
    private String content;

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
