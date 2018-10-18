package com.yihuan.sharecalendar.modle.bean.active;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Ronny on 2017/11/4.
 * 创建活动传入的提醒时间参数
 */

public class AlarmTime implements Parcelable{
    /**
     * 0    准时
     * 1    5分钟前
     * 2    10分钟前
     * 3    15分钟前
     * 4    30分钟前
     * 5    1小时前
     * 6    2小时前
     * 7    24小时前
     * 8    2天前
     * 9    1周前
     */
    private int position = 1;//点击位置
    private String alarmTime;//时间值
    private String alarmTimeName;//时间标题

    public AlarmTime(String alarmTime) {
        this.alarmTime = alarmTime;
    }

    public AlarmTime(int position, String alarmTime, String alarmTimeName) {
        this.position = position;
        this.alarmTime = alarmTime;
        this.alarmTimeName = alarmTimeName;
    }

    protected AlarmTime(Parcel in) {
        position = in.readInt();
        alarmTime = in.readString();
        alarmTimeName = in.readString();
    }

    public static final Creator<AlarmTime> CREATOR = new Creator<AlarmTime>() {
        @Override
        public AlarmTime createFromParcel(Parcel in) {
            return new AlarmTime(in);
        }

        @Override
        public AlarmTime[] newArray(int size) {
            return new AlarmTime[size];
        }
    };

    public void setAlarmTime(int position) {
        this.position = position;
        alarmTime = getAlarmTime(position) + "";
    }

    /**
     * 提醒时间 单位 minute
     * @param position
     * @return
     */
    private int getAlarmTime(int position) {
        switch (position) {
            case 0:
                return 0;
            case 1:
                return 5;
            case 2:
                return 10;
            case 3:
                return 15;
            case 4:
                return 30;
            case 5:
                return 60;
            case 6:
                return 60 * 2;
            case 7:
                return 60 * 24;
            case 8:
                return 60 * 24 * 2;
            case 9:
                return 60 * 24 * 7;
            default:
                return 0;
        }
    }

    public String getAlarmTimeStr() {
        switch (position) {
            case 0:
                return "准时";
            case 1:
                return "5分钟前";
            case 2:
                return "10分钟前";
            case 3:
                return "15分钟前";
            case 4:
                return "30分钟前";
            case 5:
                return "1小时前";
            case 6:
                return "2小时前";
            case 7:
                return "24小时前";
            case 8:
                return "2天前";
            case 9:
                return "1周前";
            default:
                return "准时";
        }
    }

    public String getAlarmTime(){
        return alarmTime;
    }

    public int getPosition(){
        return position;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(position);
        dest.writeString(alarmTime);
        dest.writeString(alarmTimeName);
    }
}
