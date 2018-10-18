package com.yihuan.sharecalendar.modle.db.active;


/**
 * Created by Ronny on 2017/11/12.
 */

public interface ActiveDBConfig {
    String TABBLE_NAME = "active";

    String USER_ID = "user_id";//用户id
    String PUBLISH_USER_ID = "publish_user_id";
    String ACTIVE_ID = "active_id";//活动id
    String TITLE = "title";//标题
    String LOCATION = "location";//位置
    String LON = "lon";//经度
    String LAT = "lat";//纬度
    String FULLDAY = "fullDay";//是否全天
    String START_YM = "startYM";//开始的日期
    String END_YM = "endYM";//结束日期
    String START_YMD = "startYMD";
    String END_YMD = "endYMD";
    String START_TIME = "startTime";//开始时间
    String END_TIME = "endTime";//结束时间
    String CYCLE = "cycle";//周期
    String REMIND = "remind";//提醒
    String DES = "des";//说明
    String SHARE_FRIEND = "share_friend";//共享好友
    String IS_SHARE_SCHDULE = "is_share_schdule";//是否是共享日程

    String CREATE_ACTIVE_TABLE = "create table " + TABBLE_NAME + "(" +
            ACTIVE_ID + " integer primary key," +
            USER_ID + " integer, " +
            PUBLISH_USER_ID + " integer," +
            TITLE + " text," +
            LOCATION + " text," +
            LON + " double," +
            LAT + " double," +
            FULLDAY + " integer," +
            START_YM + " text," +
            END_YM + " text," +
            START_YMD + " text," +
            END_YMD + " text," +
            START_TIME + " text," +
            END_TIME + " text," +
            CYCLE + " integer," +
            REMIND + " text," +
            DES + " text," +
            SHARE_FRIEND + " text," +
            IS_SHARE_SCHDULE + " text)";

    String DROP_ACTIVE_TABLE = "DROP TABLE " + TABBLE_NAME;
}
