// IAlarmControl.aidl
package com.yihuan.sharecalendar.aidl;
import com.yihuan.sharecalendar.aidl.Alarm;

// Declare any non-default types here with import statements

interface IAlarmControl {

    void initAllRemind(in List<Alarm> list);
    void addRemind(in Alarm alarm);
    void deleteRemind(in Alarm alarm);
    void deleteReminds(in int[] idList);
    void updateRemind(in Alarm alarm);
    void removeAllRemind();//todo 移除所有闹钟
    void restart();//todo 重启服务

}
