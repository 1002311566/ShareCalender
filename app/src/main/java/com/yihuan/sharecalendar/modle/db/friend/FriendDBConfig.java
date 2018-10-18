package com.yihuan.sharecalendar.modle.db.friend;

/**
 * Created by Ronny on 2017/11/12.
 */

public interface FriendDBConfig {
    String TABBLE_NAME = "friend";

    String FRIEND_ID = "_friendid";
    String NICKNAME_LETTER = "letter";
    String NICKNAME = "nickname";
    String MOOD = "mood";
    String HEADER = "header";
    String BIRTHDAY = "birthday";

    String CREATE_FRIEND_TABLE = "create table " + FriendDBConfig.TABBLE_NAME + "(" +
            FriendDBConfig.FRIEND_ID + " integer primary key , " +
            FriendDBConfig.NICKNAME_LETTER + " text, " +
            FriendDBConfig.NICKNAME + " text, " +
            FriendDBConfig.HEADER + " text, " +
            FriendDBConfig.BIRTHDAY + " text, " +
            FriendDBConfig.MOOD + " integer)";

    String DROP_FRIEND_TABLE = "DROP TABLE " + TABBLE_NAME;
}
