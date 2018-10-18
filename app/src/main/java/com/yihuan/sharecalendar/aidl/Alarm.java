// Alarm.aidl
package com.yihuan.sharecalendar.aidl;


import android.os.Parcel;
import android.os.Parcelable;

import com.yihuan.sharecalendar.modle.bean.active.AlarmTime;
import com.yihuan.sharecalendar.modle.bean.active.TimeBean;
import com.yihuan.sharecalendar.modle.bean.settting.AutoRemindListBean;
import com.yihuan.sharecalendar.modle.bean.settting.ReminderTimeBean;
import com.yihuan.sharecalendar.modle.calendar.ActiveBean;
import com.yihuan.sharecalendar.modle.calendar.RemindBean;

import java.util.ArrayList;
import java.util.List;

public class Alarm implements Parcelable{
    private int id;//唯一id
    private String title;//提醒名字
    private long start_time;//开始时间
    private long end_time;//结束时间
    private boolean isAutoRemind;//是否是定制化提醒

    private long[] planMinuteList;//计划提前时间:活动提醒：提醒时间戳；定制化提醒：当日00:00起算的毫秒值
    //todo---------活动用到----------------
    private int publsh_user_id;//发起者id

    //todo---------定制化提醒专用-----------
    private String dayType;//提醒类型：1 周 2 月
    private String days;//提醒日期
    private List<String> hourAndMinuteList;//提醒点 hh:mm


    public Alarm(ActiveBean activeBean){
        if(activeBean == null)return;

        this.id = activeBean.getActive_id();
        this.title = activeBean.getTitle();
        this.start_time = activeBean.getStart_time().getDateTime().getMillis();
        this.end_time = activeBean.getEnd_time().getDateTime().getMillis();
        this.isAutoRemind =false;
        this.publsh_user_id = activeBean.getPublish_user_id();
        List<AlarmTime> remindList = activeBean.getRemindList();
        if(remindList != null){
            this.planMinuteList = new long[remindList.size()];
            try {
                for (int i = 0; i< remindList.size(); i++){
                    AlarmTime time = remindList.get(i);
                    planMinuteList[i] = Long.valueOf(time.getAlarmTime());
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public Alarm(RemindBean bean){
        if(bean == null)return;

        this.id = 10000+ bean.getRemind_id();
        this.title = bean.getName();
        this.start_time = bean.getStartTime().getDateTime().getMillis();
        this.end_time = bean.getEndTime().getDateTime().getMillis();
        this.isAutoRemind = true;
        this.dayType = bean.getDayType();
        this.days = bean.getDays();
        if(bean.getReminderTimeList() != null && bean.getReminderTimeList().size() > 0){
            this.hourAndMinuteList = new ArrayList<>();
            List<ReminderTimeBean> reminderTimeList = bean.getReminderTimeList();
            for (ReminderTimeBean b : reminderTimeList){
                String hd = b.getRemindHour() + ":" + b.getRemindMinute();
                if(!hourAndMinuteList.contains(hd)){
                    hourAndMinuteList.add(hd);
                }
            }
        }
        this.publsh_user_id = bean.getPublish_user_id();

//        this.time = new TimeBean(start_time)
    }

    public Alarm(AutoRemindListBean bean){
        if(bean == null)return;

        this.id = 10000+ bean.getId();
        this.title = bean.getName();
        this.start_time = new TimeBean(bean.getStartTime()).getDateTime().getMillis();
        this.end_time = new TimeBean(bean.getEndTime()).getDateTime().getMillis();
        this.isAutoRemind = true;
        this.dayType = bean.getDayType();
        this.days = bean.getDays();
        if(bean.getReminderTimeList() != null && bean.getReminderTimeList().size() > 0){
            this.hourAndMinuteList = new ArrayList<>();
            List<AutoRemindListBean.ReminderTimeListBean> reminderTimeList = bean.getReminderTimeList();
            for (AutoRemindListBean.ReminderTimeListBean b : reminderTimeList){
                String hd = b.getRemindHour() + ":" + b.getRemindMinute();
                if(!hourAndMinuteList.contains(hd)){
                    hourAndMinuteList.add(hd);
                }
            }
        }
        this.publsh_user_id = bean.getUserId();

//        this.time = new TimeBean(start_time)
    }


    protected Alarm(Parcel in) {
        id = in.readInt();
        title = in.readString();
        start_time = in.readLong();
        end_time = in.readLong();
        isAutoRemind = in.readByte() != 0;
        planMinuteList = in.createLongArray();
        publsh_user_id = in.readInt();
        dayType = in.readString();
        days = in.readString();
        hourAndMinuteList = in.createStringArrayList();
    }

    public static final Creator<Alarm> CREATOR = new Creator<Alarm>() {
        @Override
        public Alarm createFromParcel(Parcel in) {
            return new Alarm(in);
        }

        @Override
        public Alarm[] newArray(int size) {
            return new Alarm[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getStart_time() {
        return start_time;
    }

    public void setStart_time(long start_time) {
        this.start_time = start_time;
    }

    public long getEnd_time() {
        return end_time;
    }

    public void setEnd_time(long end_time) {
        this.end_time = end_time;
    }

    public boolean isAutoRemind() {
        return isAutoRemind;
    }

    public void setAutoRemind(boolean autoRemind) {
        isAutoRemind = autoRemind;
    }

    public long[] getPlanMinuteList() {
        return planMinuteList;
    }

    public void setPlanMinuteList(long[] planMinuteList) {
        this.planMinuteList = planMinuteList;
    }

    public int getPublsh_user_id() {
        return publsh_user_id;
    }

    public void setPublsh_user_id(int publsh_user_id) {
        this.publsh_user_id = publsh_user_id;
    }

    public String getDayType() {
        return dayType;
    }

    public void setDayType(String dayType) {
        this.dayType = dayType;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public List<String> getHourAndMinuteList() {
        return hourAndMinuteList;
    }

    public void setHourAndMinuteList(List<String> hourAndMinuteList) {
        this.hourAndMinuteList = hourAndMinuteList;
    }


    @Override
    public String toString() {
        return "Alarm{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", start_time=" + start_time +
                ", end_time=" + end_time +
                ", isAutoRemind=" + isAutoRemind +
                ", planMinuteList=" + planMinuteList +
                ", publsh_user_id=" + publsh_user_id +
                ", dayType='" + dayType + '\'' +
                ", days='" + days + '\'' +
                ", hourAndMinuteList=" + hourAndMinuteList +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeLong(start_time);
        dest.writeLong(end_time);
        dest.writeByte((byte) (isAutoRemind ? 1 : 0));
        dest.writeLongArray(planMinuteList);
        dest.writeInt(publsh_user_id);
        dest.writeString(dayType);
        dest.writeString(days);
        dest.writeStringList(hourAndMinuteList);
    }
}
