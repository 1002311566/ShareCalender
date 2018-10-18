package com.yihuan.sharecalendar.modle.calendar;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.yihuan.sharecalendar.modle.bean.active.ActiveDetailsBean;
import com.yihuan.sharecalendar.modle.bean.active.AlarmTime;
import com.yihuan.sharecalendar.modle.bean.active.TimeBean;
import com.yihuan.sharecalendar.modle.bean.friend.FriendListBean;
import com.yihuan.sharecalendar.ui.activity.active.ActiveDetailsActivity_Receiver;
import com.yihuan.sharecalendar.utils.BeanToUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ronny on 2017/11/14.
 */

public class ActiveBean implements Parcelable {
    private Integer active_id = -1;//活动id
    private Integer publish_user_id = -1;//活动创建者id
    private String title;//标题
    private String location;//位置
    private String area;//大致区域
    private Double lon = 0.0;//经度
    private Double lat = 0.0;//纬度
    private boolean isFullday;//是否全天
    private TimeBean start_time;//开始时间
    private TimeBean end_time;//结束时间
    private String cycle = "0";//周期
    private List<AlarmTime> remindList = new ArrayList<>();//提醒
    private String remindListStr;//提醒拼接字符串，用英文“,”隔开
    private String des;//说明
    private List<FriendListBean> share_friend = new ArrayList<>();//共享好友
    private boolean is_share_schdule;//是否是共享日程

    private int cycleLastPosition;//记录上一次周期选择的位置
    private int[] remindLastPosition = {1, 1, 1, 1, 1};//记录上一次提醒的选择,默认选择 5分钟前
    private List<String> imgList;//todo 上传的图片

    public ActiveBean() {
    }

    protected ActiveBean(Parcel in) {
        active_id = in.readInt();
        publish_user_id = in.readInt();
        title = in.readString();
        location = in.readString();
        area = in.readString();
        isFullday = in.readByte() != 0;
        start_time = in.readParcelable(TimeBean.class.getClassLoader());
        end_time = in.readParcelable(TimeBean.class.getClassLoader());
        cycle = in.readString();
        remindList = in.createTypedArrayList(AlarmTime.CREATOR);
        remindListStr = in.readString();
        des = in.readString();
        share_friend = in.createTypedArrayList(FriendListBean.CREATOR);
        is_share_schdule = in.readByte() != 0;
        cycleLastPosition = in.readInt();
        remindLastPosition = in.createIntArray();
        imgList = in.createStringArrayList();
    }

    public List<String> getImgList() {
        return imgList;
    }

    public void setImgList(List<String> imgList) {
        this.imgList = imgList;
    }

    public int getCycleLastPosition() {
        return cycleLastPosition;
    }

    public void setCycleLastPosition(int cycleLastPosition) {
        this.cycleLastPosition = cycleLastPosition;
    }

    public int[] getRemindLastPosition() {
        return remindLastPosition;
    }

    public void setRemindLastPosition(int[] remindLastPosition) {
        this.remindLastPosition = remindLastPosition;
    }

    public static final Creator<ActiveBean> CREATOR = new Creator<ActiveBean>() {
        @Override
        public ActiveBean createFromParcel(Parcel in) {
            return new ActiveBean(in);
        }

        @Override
        public ActiveBean[] newArray(int size) {
            return new ActiveBean[size];
        }
    };

    public Integer getActive_id() {
        return active_id;
    }

    public void setActive_id(Integer active_id) {
        this.active_id = active_id;
    }

    public Integer getPublish_user_id() {
        return publish_user_id;
    }

    public void setPublish_user_id(Integer publish_user_id) {
        this.publish_user_id = publish_user_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getArea() {
        return area;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public boolean isFullday() {
        return isFullday;
    }

    public void setFullday(boolean fullday) {
        isFullday = fullday;
    }

    public TimeBean getStart_time() {
        return start_time;
    }

    public void setStart_time(TimeBean start_time) {
        this.start_time = start_time;
    }

    public TimeBean getEnd_time() {
        return end_time;
    }

    public void setEnd_time(TimeBean end_time) {
        this.end_time = end_time;
    }

    public String getCycle() {
        return cycle;
    }

    public void setCycle(String cycle) {
        this.cycle = cycle;
    }

    public List<AlarmTime> getRemindList() {
        return remindList;
    }

    public void setRemindList(List<AlarmTime> remindList) {
        this.remindList = remindList;
    }

    public void setRemindListStr(String remindListStr) {
        this.remindListStr = remindListStr;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public List<FriendListBean> getShare_friend() {
        return share_friend;
    }

    public void setShare_friend(List<FriendListBean> share_friend) {
        this.share_friend = share_friend;
        if (share_friend != null && share_friend.size() > 0) {
            this.is_share_schdule = true;
        } else {
            this.is_share_schdule = false;
        }
    }

    public boolean is_share_schdule() {
        return is_share_schdule;
    }

    public void setIs_share_schdule(boolean is_share_schdule) {
        this.is_share_schdule = is_share_schdule;
    }

    public String getRemindListStr() {
        StringBuffer buffer = new StringBuffer();
        if (remindList != null) {
            for (int i = 0; i < remindList.size(); i++) {
                if (i != remindList.size() - 1) {
                    buffer.append(remindList.get(i).getAlarmTime()).append(",");
                    continue;
                }
                buffer.append(remindList.get(i).getAlarmTime());
            }
        }
        return buffer.toString();
    }

    public boolean getIsShareSchdule() {
        if (share_friend != null && share_friend.size() > 0) {
            return true;
        }
        return false;
    }

    public String getCycleFlag() {
//        if (!TextUtils.isEmpty(cycle) && !cycle.equals("-1")) {
//            return "1";
//        }
        return "0";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(active_id);
        dest.writeInt(publish_user_id);
        dest.writeString(title);
        dest.writeString(location);
        dest.writeString(area);
        dest.writeByte((byte) (isFullday ? 1 : 0));
        dest.writeParcelable(start_time, flags);
        dest.writeParcelable(end_time, flags);
        dest.writeString(cycle);
        dest.writeTypedList(remindList);
        dest.writeString(remindListStr);
        dest.writeString(des);
        dest.writeTypedList(share_friend);
        dest.writeByte((byte) (is_share_schdule ? 1 : 0));
        dest.writeInt(cycleLastPosition);
        dest.writeIntArray(remindLastPosition);
        dest.writeStringList(imgList);
    }

    public void setRemindLists(List<ActiveDetailsBean.ActivityAlarmsBean> activityAlarms) {
        if(activityAlarms != null && activityAlarms.size() > 0){
            for (ActiveDetailsBean.ActivityAlarmsBean b : activityAlarms){
                int alarmTimePosition = BeanToUtils.getAlarmTimePosition(b.getAlarmTime());
                String alarmTimeStr = BeanToUtils.getAlarmTimeStr(alarmTimePosition);
                remindList.add(new AlarmTime(alarmTimePosition, b.getAlarmTime()+"", alarmTimeStr));
            }
        }
    }
}
