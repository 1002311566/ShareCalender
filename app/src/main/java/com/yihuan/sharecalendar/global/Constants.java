package com.yihuan.sharecalendar.global;

/**
 * Created by Ronny on 2017/9/5.
 * 存储所有不变属性
 */

public class Constants {
    public static boolean isFirst = false;
    public static final String SP_TOKEN="token";
    public static final String SP_MY_ID = "my_id";
    public static final String SP_IS_FIRST = "is_first";

    //recyclerView的布局分类
    public static final int TYPE_HEAD = 0;
    public static final int TYPE_ITEM = 1;
    public static final int TYPE_FOOT = 2;

    //recyclerView的刷新类型
    public static final int TYPE_REFRESH = 0;
    public static final int TYPE_LOADMORE = 1;

    public static final int PAGE_SIZE = 10;

    public static final String BIND_WX = "bind_wx";
    public static final String BIND_SINA = "bind_sina";

    public static final int REQUEST_CODE_1 = 1;
    public static final int REQUEST_CODE_2 = 2;
    public static final int REQUEST_CODE_3 = 3;
    public static final int REQUEST_CODE_LOGIN = 0x111;

    //首页刷新广播
    public static final String ACTION_REFRESH_HOME = "action_refresh_home";
    //其它页面刷新广播
    public static final String ACTION_REFRESH = "action_refresh";
    //选择好友的广播条件
    public static final String ACTION_SELECT_FRIEND = "action_select_friend";
    //跳转到指定日期的广播
    public static final String ACTION_TO_DATE = "action_to_date";
    public static final String INTENT_TIME_BEAN = "timeBean";

    //跳转到CompleteActivity
    public static final String INTENT_COMPELETED_TYPE = "intent_compeleted_type";//完成类型，作为key
    public static final String INTENT_COMPELETED_TYPE_REGISTER = "register";//注册完成
    public static final String INTENT_COMPELETED_TYPE_SET_NEWPASWORD = "newPassword";//设置新密码

    //注册成功后完善信息界面
    public static final String INTENT_INFO_SELECT_TYPE = "info_select_type";//信息选择类型， 作为key
    public static final String INTENT_INFO_SELECT_TYPE_SEX = "info_select_type_sex";//性别
    public static final String INTENT_INFO_SELECT_TYPE_YEAR = "info_select_type_year";//年代
    public static final String INTENT_INFO_SELECT_TYPE_CONSTELLATION = "info_select_type_constellation";//星座
    public static final String INTENT_INFO_SELECT_TYPE_ADDRESS = "info_select_type_address";//所在地
    public static final String INTENT_INFO_SELELCT_DATA = "info_selelct_data";//选择后的数据

    public static final String MAIN_TO_MONTHFRAGMENT_DATE = "main_to_monthfragment_date";//mainactivity传参MonthFragment的key

    public static final String INTENT_EDIT_TYPE = "edit_type";//编辑类型
    public static final String INTENT_EDIT_TYPE_NICKNAME = "edit_type_nickname";
    public static final String INTENT_EDIT_TYPE_SIGNATURE = "getEdit_type_signature";
    public static final String INTENT_EDIT_RESULT = "edit_result";

    public static final String INTENT_INTO_FRIEND_SEARCH_FROM = "intent_into_friend_search_from";//从哪里跳转到好友搜索界面key
    public static final String INTENT_FROM_MYFRIEND = "intent_from_myfriend";//从我的好友
    public static final String INTENT_FROM_ADDFRIEND = "intent_from_addfriend";//从添加新用户
    public static final String INTENT_ACCOUNTINFO_SEARCH = "accountInfo_search";//搜索到的用户信息key
    public static final String INTENT_USER_ID = "user_id";
    public static final String INTENT_MY_FRIEND_DETAILS = "my_friend_details";

    public static final String INTENT_FRIEND_LIST = "intent_friend_list";//好友列表
    public static final String INTENT_TAG_NAME = "tag_name";
    public static final String INTENT_TAG_ID = "tag_id";

    public static final String ACTION_ACTIVE_ALARM = "action_active_alarm";//活动的闹铃广播
    public static final String ACTION_REMIND_ALARM = "action_remind_alarm";//活动的闹铃广播

    public static final String INTENT_ALARM_BEAN = "alarm_bean";//传输的闹钟信息对象
    public static final String INTENT_ALARM_IDS = "alarm_ids";//删除闹钟用到



}
