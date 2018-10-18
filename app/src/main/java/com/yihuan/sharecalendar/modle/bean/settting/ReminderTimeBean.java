package com.yihuan.sharecalendar.modle.bean.settting;

import android.text.TextUtils;

/**
 * Created by Ronny on 2017/12/8.
 */

public class ReminderTimeBean {
    private String remindHour;
    private String remindMinute;

    public ReminderTimeBean(){}
    public ReminderTimeBean(String remindHour, String remindMinute) {
        this.remindHour = remindHour;
        this.remindMinute = remindMinute;
    }

    public String getRemindHour() {
        return remindHour;
    }

    public void setRemindHour(String remindHour) {
        this.remindHour = remindHour;
    }

    public String getRemindMinute() {
        return remindMinute;
    }

    public void setRemindMinute(String remindMinute) {
        this.remindMinute = remindMinute;
    }
}
