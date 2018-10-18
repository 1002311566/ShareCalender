package com.yihuan.sharecalendar.modle.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.yihuan.sharecalendar.modle.db.active.ActiveDBConfig;
import com.yihuan.sharecalendar.modle.db.friend.FriendDBConfig;

/**
 * Created by Ronny on 2017/10/3.
 */

public class DBHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "sharecalendar.db";
    public static final int VERSION = 3;


    public DBHelper(Context context, SQLiteDatabase.CursorFactory factory) {
        super(context, DB_NAME, factory, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(FriendDBConfig.CREATE_FRIEND_TABLE);
        db.execSQL(ActiveDBConfig.CREATE_ACTIVE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(oldVersion != newVersion){
            db.execSQL(FriendDBConfig.DROP_FRIEND_TABLE);
            db.execSQL(ActiveDBConfig.DROP_ACTIVE_TABLE);
            onCreate(db);
        }
    }
}
