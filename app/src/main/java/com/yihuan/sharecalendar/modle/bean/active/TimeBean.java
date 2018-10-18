package com.yihuan.sharecalendar.modle.bean.active;

import android.os.Parcel;
import android.os.Parcelable;

import com.yihuan.sharecalendar.ui.view.calendar.Lunar;
import com.yihuan.sharecalendar.utils.LogUtils;

import org.joda.time.DateTime;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Ronny on 2017/11/5.
 */

public class TimeBean implements Parcelable {
    private int year;
    private int month;//todo 月份会比实际月份小1
    private int day;
    private int week;
    private int hour;
    private int minute;
    private Lunar lunar;//农历

    public TimeBean() {
    }

    //todo yyyy年MM月dd日 HH:mm
    public TimeBean(String timeStr) {
        if (timeStr == null) return;
        strToTime(timeStr);
    }

    public TimeBean(long timess) {
        DateTime dateTime = new DateTime(new Date(timess));
        this.year = dateTime.getYear();
        this.month = dateTime.getMonthOfYear() - 1;
        this.day = dateTime.getDayOfMonth();
        this.hour = dateTime.getHourOfDay();
        this.minute= dateTime.getMinuteOfHour();
    }

    public Lunar getLunar() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        lunar = new Lunar(calendar);
        return lunar;
    }

    public void setLunar(Lunar lunar) {
        this.lunar = lunar;
    }

    private void strToTime(String timeStr) {
        SimpleDateFormat simpleDateFormat;
        if(timeStr.contains(":")){
            if(timeStr.contains("-")){
                simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            }else{
                simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
            }
        }else{
            if(timeStr.contains("-")){
                simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            }else{
                simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日");
            }
        }
        try {
            Date date = simpleDateFormat.parse(timeStr);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            setYMDHM(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH),
                    calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE));
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.e("时间格式异常---------");
        }
    }


    public TimeBean(int year, int month, int day, int hour, int minute, int week) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.week = week;
        this.hour = hour;
        this.minute = minute;
    }

    protected TimeBean(Parcel in) {
        year = in.readInt();
        month = in.readInt();
        day = in.readInt();
        week = in.readInt();
        hour = in.readInt();
        minute = in.readInt();
    }

    public static final Creator<TimeBean> CREATOR = new Creator<TimeBean>() {
        @Override
        public TimeBean createFromParcel(Parcel in) {
            return new TimeBean(in);
        }

        @Override
        public TimeBean[] newArray(int size) {
            return new TimeBean[size];
        }
    };

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getWeek() {
        return week;
    }


    public void setWeek(int week) {
        this.week = week;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(year);
        dest.writeInt(month);
        dest.writeInt(day);
        dest.writeInt(week);
        dest.writeInt(hour);
        dest.writeInt(minute);
    }

    public void cleanHour() {
        hour = 0;
        minute = 0;
    }

    public String toYM() {
        return formatStr("yyyy年MM月");
    }

    public String toMD() {
        return formatStr("MM月dd日");
    }

    public String toYMD() {
        return formatStr("yyyy年MM月dd日");
    }

    public String toHM(){
        return formatStr("HH:mm");
    }

    public String toHour(){return formatStr("HH");}

    public String toTime() {
        return formatStr("yyyy年MM月dd日 HH:mm");
    }

    public String toDateAndWeek() {
        return formatStr("yyyy年MM月dd日 EE");
    }

    public String formatStr(String formatText) {
        SimpleDateFormat s = new SimpleDateFormat(formatText);
        Calendar c = Calendar.getInstance();
        c.set(year, month, day, hour, minute);
        return s.format(c.getTime());
    }

    public long getTimeInMillis(){
        Calendar instance = Calendar.getInstance();
        instance.set(year,month,day,hour,minute);
        return instance.getTimeInMillis();
    }

    public void setYMD(int year, int month, int day) {
        Calendar instance = Calendar.getInstance();
        int hour = instance.get(Calendar.HOUR_OF_DAY);
        int minute = instance.get(Calendar.MINUTE);
        setYMDHM(year, month, day, hour, minute);
    }

    public void setYMDHM(int year, int month, int day, int hour, int minute) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.minute = minute;
    }

    public boolean equalsYMD(TimeBean timeBean) {
        if (timeBean == null) return false;

        if (timeBean.getYear() == this.year &&
                timeBean.getMonth() == this.month &&
                timeBean.getDay() == this.day)
            return true;

        return false;
    }

    public DateTime getDateTime(){
        return new DateTime(year, month+1, day, hour, minute);
    }

    @Override
    public String toString() {
        return "TimeBean{" +
                "year=" + year +
                ", month=" + month +
                ", day=" + day +
                ", week=" + week +
                ", hour=" + hour +
                ", minute=" + minute +
                ", lunar=" + lunar +
                '}';
    }
}
