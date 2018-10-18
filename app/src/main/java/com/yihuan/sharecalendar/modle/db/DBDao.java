package com.yihuan.sharecalendar.modle.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.yihuan.sharecalendar.global.App;
import com.yihuan.sharecalendar.modle.bean.active.TimeBean;
import com.yihuan.sharecalendar.modle.bean.friend.FriendListBean;
import com.yihuan.sharecalendar.modle.bean.home.ScheduleListBean;
import com.yihuan.sharecalendar.modle.calendar.ActiveBean;
import com.yihuan.sharecalendar.modle.data.TimeUtils;
import com.yihuan.sharecalendar.modle.db.active.ActiveDBConfig;
import com.yihuan.sharecalendar.modle.db.friend.FriendDBConfig;
import com.yihuan.sharecalendar.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ronny on 2017/10/3.
 */

public class DBDao {


    private static DBDao dao;
    private DBHelper helper;

    private DBDao() {
        helper = new DBHelper(App.getInstanse().getApplicationContext(), null);
    }

    public static DBDao getDao() {
        if (dao == null) {
            synchronized (DBDao.class) {
                if (dao == null) {
                    dao = new DBDao();
                }
            }
        }
        return dao;
    }

    /**
     * 保存好友
     *
     * @param bean
     */
    public synchronized void saveFriend(FriendListBean bean) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(FriendDBConfig.NICKNAME_LETTER, bean.getNicknameInitial());
        cv.put(FriendDBConfig.FRIEND_ID, bean.getFriendId());
        cv.put(FriendDBConfig.NICKNAME, bean.getNickname());
        cv.put(FriendDBConfig.MOOD, bean.getCurrentMood());
        cv.put(FriendDBConfig.HEADER, bean.getHeaderImage());
        cv.put(FriendDBConfig.BIRTHDAY, bean.getBirthday());
        db.beginTransaction();
        db.insert(FriendDBConfig.TABBLE_NAME, null, cv);
        db.setTransactionSuccessful();
        db.endTransaction();
    }

    /**
     * 保存列表所有好友
     *
     * @param listBeen
     */
    public synchronized void saveFriendList(List<FriendListBean> listBeen) {

        SQLiteDatabase db = helper.getWritableDatabase();
        db.beginTransaction();
        db.delete(FriendDBConfig.TABBLE_NAME, null, null);
        for (FriendListBean bean : listBeen) {
            ContentValues cv = new ContentValues();
            cv.put(FriendDBConfig.NICKNAME_LETTER, bean.getNicknameInitial());
            cv.put(FriendDBConfig.FRIEND_ID, bean.getFriendId());
            cv.put(FriendDBConfig.NICKNAME, bean.getNickname());
            cv.put(FriendDBConfig.MOOD, bean.getCurrentMood() + "");
            cv.put(FriendDBConfig.HEADER, bean.getHeaderImage());
            cv.put(FriendDBConfig.BIRTHDAY, bean.getBirthday());
            db.insert(FriendDBConfig.TABBLE_NAME, null, cv);
        }
        db.setTransactionSuccessful();
        db.endTransaction();
        db.close();
    }

    /**
     * 获取好友列表，已通过首字母排序
     *
     * @return
     */
    public synchronized List<FriendListBean> getFriendListFromLetterSort() {
        SQLiteDatabase db = helper.getReadableDatabase();
        ArrayList<FriendListBean> list = new ArrayList<>();
        if (db.isOpen()) {
            Cursor cursor = db.rawQuery("select * from " + FriendDBConfig.TABBLE_NAME + " order by case when " +
                    FriendDBConfig.NICKNAME_LETTER + "='#' then 1 else 0 end," +
                    FriendDBConfig.NICKNAME_LETTER + " asc", null);
            while (cursor.moveToNext()) {
                int friend_id = cursor.getInt(cursor.getColumnIndex(FriendDBConfig.FRIEND_ID));
                String nickname_letter = cursor.getString(cursor.getColumnIndex(FriendDBConfig.NICKNAME_LETTER));
                String nickname = cursor.getString(cursor.getColumnIndex(FriendDBConfig.NICKNAME));
                int mood = cursor.getInt(cursor.getColumnIndex(FriendDBConfig.MOOD));
                String headerImg = cursor.getString(cursor.getColumnIndex(FriendDBConfig.HEADER));
                String birthday = cursor.getString(cursor.getColumnIndex(FriendDBConfig.BIRTHDAY));
                FriendListBean bean = new FriendListBean(friend_id, nickname, nickname_letter, mood, headerImg, birthday);
                LogUtils.e("-----------------db---------------" + FriendDBConfig.NICKNAME_LETTER + "=" + nickname_letter);
                list.add(bean);
            }
            cursor.close();
        }
        db.close();
        return list;
    }

    /**
     * 根据昵称模糊查询
     *
     * @param nickname
     * @return
     */
    public synchronized List<FriendListBean> queryFriendListByNickname(String nickname) {
        SQLiteDatabase db = helper.getReadableDatabase();
        ArrayList<FriendListBean> list = new ArrayList<>();
        if (db.isOpen()) {
            Cursor cursor = db.rawQuery("select * from " + FriendDBConfig.TABBLE_NAME + " where " + FriendDBConfig.NICKNAME + " like ? ", new String[]{"%" + nickname + "%"});
            while (cursor.moveToNext()) {
                int friend_id = cursor.getInt(cursor.getColumnIndex(FriendDBConfig.FRIEND_ID));
                String nickname_letter = cursor.getString(cursor.getColumnIndex(FriendDBConfig.NICKNAME_LETTER));
                String name = cursor.getString(cursor.getColumnIndex(FriendDBConfig.NICKNAME));
                int mood = cursor.getInt(cursor.getColumnIndex(FriendDBConfig.MOOD));
                String headerImg = cursor.getString(cursor.getColumnIndex(FriendDBConfig.HEADER));
                String birthday = cursor.getString(cursor.getColumnIndex(FriendDBConfig.BIRTHDAY));
                FriendListBean bean = new FriendListBean(friend_id, name, nickname_letter, mood, headerImg, birthday);
                LogUtils.e("-----------------db---------------" + FriendDBConfig.NICKNAME_LETTER + "=" + nickname_letter);
                list.add(bean);
            }
            cursor.close();
        }
        db.close();
        return list;
    }

    //todo--------------------------------日程相关-----------------------------------------

    public ContentValues getContentValues(ScheduleListBean.ActivityListBean activeBean) {
        ContentValues cv = new ContentValues();
        cv.put(ActiveDBConfig.USER_ID, App.getInstanse().getUserId());
        cv.put(ActiveDBConfig.PUBLISH_USER_ID, activeBean.getUserId());
        cv.put(ActiveDBConfig.TITLE, activeBean.getTitle());
        cv.put(ActiveDBConfig.LOCATION, activeBean.getAddress());
        cv.put(ActiveDBConfig.LON, activeBean.getLongitude());
        cv.put(ActiveDBConfig.LAT, activeBean.getLatitude());
        cv.put(ActiveDBConfig.FULLDAY, activeBean.isFullDay() ? 1 : 0);

        cv.put(ActiveDBConfig.START_YM, new TimeBean(activeBean.getStartTime()).toYM());
        cv.put(ActiveDBConfig.END_YM, new TimeBean(activeBean.getEndTime()).toYM());
        cv.put(ActiveDBConfig.START_YMD, new TimeBean(activeBean.getStartTime()).toYMD());
        cv.put(ActiveDBConfig.END_YMD, new TimeBean(activeBean.getEndTime()).toYMD());
        cv.put(ActiveDBConfig.START_TIME, activeBean.getStartTime());
        cv.put(ActiveDBConfig.END_TIME, activeBean.getEndTime());
        cv.put(ActiveDBConfig.CYCLE, activeBean.getRepeatPeriod());
        cv.put(ActiveDBConfig.DES, activeBean.getComments());
        cv.put(ActiveDBConfig.IS_SHARE_SCHDULE, activeBean.isShare() ? "1" : "0");
        return cv;
    }

    public ArrayList<ActiveBean> setBean(Cursor c) {
        ArrayList<ActiveBean> list = new ArrayList<>();
        Cursor cursor = c;
        while (cursor.moveToNext()) {
            ActiveBean b = new ActiveBean();
            list.add(b);
            b.setActive_id(cursor.getInt(cursor.getColumnIndex(ActiveDBConfig.ACTIVE_ID)));
            b.setPublish_user_id(cursor.getInt(cursor.getColumnIndex(ActiveDBConfig.PUBLISH_USER_ID)));
            b.setTitle(cursor.getString(cursor.getColumnIndex(ActiveDBConfig.TITLE)));
            b.setLocation(cursor.getString(cursor.getColumnIndex(ActiveDBConfig.LOCATION)));
            b.setLon(cursor.getDouble(cursor.getColumnIndex(ActiveDBConfig.LON)));
            b.setLat(cursor.getDouble(cursor.getColumnIndex(ActiveDBConfig.LAT)));
            b.setFullday(cursor.getInt(cursor.getColumnIndex(ActiveDBConfig.FULLDAY)) == 1);
            b.setStart_time(new TimeBean(cursor.getString(cursor.getColumnIndex(ActiveDBConfig.START_TIME))));
            b.setEnd_time(new TimeBean(cursor.getString(cursor.getColumnIndex(ActiveDBConfig.END_TIME))));
            b.setCycle(cursor.getString(cursor.getColumnIndex(ActiveDBConfig.CYCLE)));
            b.setRemindListStr(cursor.getString(cursor.getColumnIndex(ActiveDBConfig.REMIND)));
            b.setDes(cursor.getString(cursor.getColumnIndex(ActiveDBConfig.DES)));
//                b.setShare_friend();
            String isShareStr = cursor.getString(cursor.getColumnIndex(ActiveDBConfig.IS_SHARE_SCHDULE));
            b.setIs_share_schdule(isShareStr != null && isShareStr.equals("1"));
        }
        return list;
    }

    public ContentValues getCv(ActiveBean activeBean) {
        ContentValues cv = new ContentValues();
        cv.put(ActiveDBConfig.USER_ID, App.getInstanse().getUserId());
        cv.put(ActiveDBConfig.PUBLISH_USER_ID, activeBean.getPublish_user_id());
        cv.put(ActiveDBConfig.TITLE, activeBean.getTitle());
        cv.put(ActiveDBConfig.LOCATION, activeBean.getLocation());
        cv.put(ActiveDBConfig.LON, activeBean.getLon());
        cv.put(ActiveDBConfig.LAT, activeBean.getLat());
        cv.put(ActiveDBConfig.FULLDAY, activeBean.isFullday() ? 1 : 0);
        cv.put(ActiveDBConfig.START_YM, activeBean.getStart_time().toYM());
        cv.put(ActiveDBConfig.END_YM, activeBean.getEnd_time().toYM());
        cv.put(ActiveDBConfig.START_YMD, activeBean.getStart_time().toYMD());
        cv.put(ActiveDBConfig.END_YMD, activeBean.getEnd_time().toYMD());
        cv.put(ActiveDBConfig.START_TIME, activeBean.getStart_time().toTime());
        cv.put(ActiveDBConfig.END_TIME, activeBean.getEnd_time().toTime());
        cv.put(ActiveDBConfig.CYCLE, activeBean.getCycle());
        cv.put(ActiveDBConfig.DES, activeBean.getDes());
        cv.put(ActiveDBConfig.IS_SHARE_SCHDULE, activeBean.is_share_schdule() ? "1" : "0");
        return cv;
    }

    /**
     * 保存日程
     *
     * @param activeBean
     */

    public synchronized void addSchedule(ScheduleListBean.ActivityListBean activeBean) {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.beginTransaction();
        ContentValues cv = getContentValues(activeBean);
        cv.put(ActiveDBConfig.ACTIVE_ID, activeBean.getId());
        db.insert(ActiveDBConfig.TABBLE_NAME, null, cv);
        db.setTransactionSuccessful();
        db.endTransaction();
    }


    public synchronized long addSchedule(ActiveBean activeBean) {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.beginTransaction();
        ContentValues cv = getCv(activeBean);
        cv.put(ActiveDBConfig.ACTIVE_ID, activeBean.getActive_id());
        long insert = db.insert(ActiveDBConfig.TABBLE_NAME, null, cv);
        db.setTransactionSuccessful();
        db.endTransaction();
        db.close();
        return insert;
    }

    /**
     * 更新所有日程
     *
     * @param list
     * @return
     */
    public synchronized boolean addScheduleList(List<ScheduleListBean.ActivityListBean> list) {
        if (list == null) return true;

        int count = 0;
        SQLiteDatabase db = helper.getWritableDatabase();
        db.beginTransaction();
//        db.delete(ActiveDBConfig.TABBLE_NAME, null, null);
        //todo 更新该用户的所有日程，避免删除其他用户的信息
        db.delete(ActiveDBConfig.TABBLE_NAME, ActiveDBConfig.USER_ID + "=" + App.getInstanse().getUserId(), null);
        for (ScheduleListBean.ActivityListBean activeBean : list) {
            ContentValues cv = getContentValues(activeBean);
            cv.put(ActiveDBConfig.ACTIVE_ID, activeBean.getId());
            long id = db.insert(ActiveDBConfig.TABBLE_NAME, null, cv);
            if (id != -1) {
                count++;
            }
        }
        db.setTransactionSuccessful();
        db.endTransaction();
        return count == list.size();
    }

    /**
     * 删除某个活动
     */
    public synchronized void deleteSchdule(int id) {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.delete(ActiveDBConfig.TABBLE_NAME, ActiveDBConfig.ACTIVE_ID + "=" + id, null);
        db.close();
    }

    /**
     * 删除多个活动
     *
     * @param list
     */
    public synchronized void deleteSchdule(List<ActiveBean> list) {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.beginTransaction();
        for (ActiveBean activeBean : list) {
            db.delete(ActiveDBConfig.TABBLE_NAME, "where " + ActiveDBConfig.ACTIVE_ID + " =? ", new String[]{activeBean.getActive_id() + ""});
        }
        db.setTransactionSuccessful();
        db.endTransaction();
    }

    /**
     * 更改某个日程
     *
     * @param activeBean
     * @return
     */
    public synchronized int updateSchdule(ScheduleListBean.ActivityListBean activeBean) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues cv = getContentValues(activeBean);
        int result = db.update(ActiveDBConfig.TABBLE_NAME, cv, ActiveDBConfig.ACTIVE_ID + "=?", new String[]{activeBean.getId() + ""});
        return result;
    }

    public synchronized int updateSchdule(ActiveBean activeBean) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues cv = getCv(activeBean);
        int result = db.update(ActiveDBConfig.TABBLE_NAME, cv, ActiveDBConfig.ACTIVE_ID + "=?", new String[]{activeBean.getActive_id() + ""});
        return result;
    }

    /**
     * 查询所有日程
     *
     * @param pageSize  每页数量 ，0为全部
     * @param currentPage 当前页
     * @return
     */
    public synchronized List<ActiveBean> getAllSchedule(int pageSize, int currentPage) {
        ArrayList<ActiveBean> list = new ArrayList<>();
        SQLiteDatabase db = helper.getReadableDatabase();
        String sql = "select * from " + ActiveDBConfig.TABBLE_NAME + " where " + ActiveDBConfig.USER_ID + "=" +
                App.getInstanse().getUserId();
        if(pageSize != 0){
            sql = sql + " order by " + ActiveDBConfig.START_TIME +
                    " desc limit " + pageSize + " offset " + pageSize * currentPage ;
        }

        if (db.isOpen()) {
            Cursor cursor = db.rawQuery(sql, null);
            list = setBean(cursor);
            cursor.close();
        }
        db.close();
        return list;
    }

    /**
     * 查询当前时间之后的所有日程
     * @return
     */
    public synchronized List<ActiveBean> getAllScheduleBeforeCurrentTime() {
        String currentTime = TimeUtils.getCurrentTimeBean().toTime();
        ArrayList<ActiveBean> list = new ArrayList<>();
        SQLiteDatabase db = helper.getReadableDatabase();
        String sql = "select * from " + ActiveDBConfig.TABBLE_NAME + " where " + ActiveDBConfig.USER_ID + "=" +
                App.getInstanse().getUserId() +" and "+ ActiveDBConfig.START_TIME + ">='"+currentTime+"'";

        if (db.isOpen()) {
            Cursor cursor = db.rawQuery(sql, null);
            list = setBean(cursor);
            cursor.close();
        }
        db.close();
        return list;
    }
    /**
     * 模糊搜索
     * @param title
     * @return
     */
    public synchronized List<ActiveBean> searchSchedule(String title) {
        ArrayList<ActiveBean> list = new ArrayList<>();
        SQLiteDatabase db = helper.getReadableDatabase();
        String sql = "select * from " + ActiveDBConfig.TABBLE_NAME + " where " + ActiveDBConfig.USER_ID + "=" +
                App.getInstanse().getUserId() + " and " +ActiveDBConfig.TITLE +
                " like ?";

        if (db.isOpen()) {
            Cursor cursor = db.rawQuery(sql, new String[]{"%" + title + "%"});
            list = setBean(cursor);
            cursor.close();
        }
        db.close();
        return list;
    }

    /**
     * 查询制定日期日程
     *
     * @param ymd
     * @return
     */
    public synchronized List<ActiveBean> getScheduleByYMD(String ymd) {
        ArrayList<ActiveBean> list = new ArrayList<>();
        SQLiteDatabase db = helper.getReadableDatabase();
        String sql = "select * from " + ActiveDBConfig.TABBLE_NAME + " where " + ActiveDBConfig.USER_ID + "=" +
                App.getInstanse().getUserId() + " and  '" + ymd + "'= " + ActiveDBConfig.START_YMD +
                " order by case when " + ActiveDBConfig.FULLDAY + "='1' then 0 else 1 end," +
                ActiveDBConfig.START_TIME + " asc";
        if (db.isOpen()) {
            Cursor cursor = db.rawQuery(sql, null);
            list = setBean(cursor);
            cursor.close();
        }
        db.close();
        return list;
    }

    /**
     * 查询制定日期日程，按时间排序，全天放前面
     *
     * @param ymd
     * @return
     */
    public synchronized List<ActiveBean> getScheduleByYMDSort(String ymd) {
        ArrayList<ActiveBean> list = new ArrayList<>();
        SQLiteDatabase db = helper.getReadableDatabase();
        if (db.isOpen()) {
            Cursor cursor = db.rawQuery(
                    "select * from " + ActiveDBConfig.TABBLE_NAME + " where " + ActiveDBConfig.USER_ID + "=" +
                            App.getInstanse().getUserId() + " and  '" + ymd + "' between " + ActiveDBConfig.START_YMD +
                            " and " + ActiveDBConfig.END_YMD +
                            " order by case when " +
                            ActiveDBConfig.FULLDAY + "='1' then 0 else 1 end," +
                            ActiveDBConfig.START_TIME + " asc", null);
            list = setBean(cursor);
            cursor.close();
        }
        db.close();
        return list;
    }

    /**
     * 查询某个活动是否保存到数据库
     *
     * @param activeId
     * @return
     */
    public synchronized boolean isHas(int activeId) {
        SQLiteDatabase db = helper.getReadableDatabase();
        ArrayList<ActiveBean> list = new ArrayList<>();
        Cursor cursor = db.rawQuery("select * from " + ActiveDBConfig.TABBLE_NAME + " where " + ActiveDBConfig.ACTIVE_ID + "=" + activeId, null);
        int count = cursor.getCount();
        cursor.close();
        db.close();
        return count > 0;
    }

    /**
     * 获取所有周期性日程, 按开始时间排序减少遍历次数
     * -1: 永不, 1: 每天, 2: 每周, 3: 每两周, 4: 每月, 5: 每年
     *
     * @return
     */
    public synchronized List<ActiveBean> getScheduleHasCycle() {
        ArrayList<ActiveBean> list = new ArrayList<>();
        SQLiteDatabase db = helper.getReadableDatabase();
        if (db.isOpen()) {
            Cursor cursor = db.rawQuery(
                    "select * from " + ActiveDBConfig.TABBLE_NAME + " where " + ActiveDBConfig.USER_ID + "=" +
                            App.getInstanse().getUserId() + " and " + ActiveDBConfig.CYCLE + "!=-1 order by " +
                            ActiveDBConfig.START_TIME + " asc", null);
            list = setBean(cursor);
            cursor.close();
        }
        db.close();
        return list;
    }

    /**
     * 获取指定年月的一次性日程 yyyy年MM月
     *
     * @param ym
     * @return
     */
    public synchronized List<ActiveBean> getScheduleByYM(String ym) {
        ArrayList<ActiveBean> list = null;
        SQLiteDatabase db = helper.getReadableDatabase();
        if (db.isOpen()) {
            Cursor cursor = db.rawQuery(
                    "select * from " + ActiveDBConfig.TABBLE_NAME + " where " + ActiveDBConfig.USER_ID + "=" +
                            App.getInstanse().getUserId() + " and " + "'" +
                            ym + "'=" + ActiveDBConfig.START_YM, null
            );
            list = setBean(cursor);
            cursor.close();
        }
        db.close();
        return list;
    }
//    public synchronized List<ActiveBean> getScheduleByYM(String ym) {
//        ArrayList<ActiveBean> list = null;
//        SQLiteDatabase db = helper.getReadableDatabase();
//        if (db.isOpen()) {
//            Cursor cursor = db.rawQuery(
//                    "select * from " + ActiveDBConfig.TABBLE_NAME + " where " + ActiveDBConfig.USER_ID + "=" +
//                            App.getInstanse().getUserId() + " and " +
//                            ActiveDBConfig.CYCLE + "=-1 and  '"+
//                            ym + "' between " + ActiveDBConfig.START_YM +" and " +
//                            ActiveDBConfig.END_YM , null
//            );
//            list= setBean(cursor);
//            cursor.close();
//        }
//        db.close();
//        return list;
//    }

}
