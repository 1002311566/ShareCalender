package com.yihuan.sharecalendar.modle.bean.settting;

/**
 * Created by Ronny on 2017/12/14.
 * 皮肤列表
 */

public class ThemeListBean {

    /**
     * activeId : 20
     * content :
     * publisher : 8
     * priority : 100
     * image : permanently/cb1/e14/69e/izwz9dc6tjfwoo65crzoadz_1514369148477_1_9a114c8253c9374ac0bdd3086bd95c90.jpg
     * selected : true
     */

    private int id;
    private String content;
    private int publisher;
    private int priority;
    private String image;
    private boolean selected;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getPublisher() {
        return publisher;
    }

    public void setPublisher(int publisher) {
        this.publisher = publisher;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
