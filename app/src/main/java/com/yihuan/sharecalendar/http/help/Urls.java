package com.yihuan.sharecalendar.http.help;

/**
 * Created by Ronny on 2017/9/6.
 * 存储所有请求地址
 */

public class Urls {

    public static final String DOMAIN = "http://cd.gran-data.com:8181/CalendarAPI/";//正式域名
//    public static final String DOMAIN = "http://119.23.246.182:8088/CalendarAPI/";//测试域名
    public static final String IMG_HEAD = DOMAIN + "direct/common/download/userFile?filePath=";//域名

    //-----------------------登录注册部分start----------------------------------------

    public static final String LOGIN = "appDirect/login/doLoginByPassword";

    //注册协议
    public static final String REGISTER_PROTOCOL = "appDirect/signIn/userProtocol";

    //微信登录
    public static final String WX_LOGIN = "wxDirect/login";

    //完善资料验证码
    public static final String GET_BIND_CODE= "appDirect/signIn/sendPrefectInfoCode";

    //完善资料（微信）
    public static final String BIND_WX = "wxDirect/perfectUserInfo";

    //注册验证码
    public static final String GET_REGISTER_CODE = "appDirect/signIn/sendValidateCode";
    //注册
    public static final String REGISTER = "appDirect/signIn/register";
    //    public static final String REGISTER = " appDirect/signIn/register";
    //星座列表
    public static final String GET_CONSTELLATION_LIST = "appDirect/constellation/constellationList";

    //省份
    public static final String GET_PROVINCE_LIST = "appDirect/province/provinceList";
    //城市
    public static final String GET_CITY_LIST = "appDirect/city/cityList";
    //注销
    public static final String LOGOUT = "appDirect/login/doLogout";
    //重置密码验证码
    public static final String RESET_PASSWORD_CODE = "appDirect/login/sendResetPasswordValidateCode";
    //检查重置验证码
    public static final String CHECK_RESET_PSD_CODE = "appDirect/login/validateResetPasswordCode";
    //重置密码
    public static final String RESET_PASSWORD = "appDirect/login/resetPassword";
    //校验手机号是否已注册
    public static final String CHECK_PHONE = "appDirect/signIn/getUserLogin";
    //--------------------------end----------------------------------

    //---------------------------用户类接口start---------------------------
    //获取用户基本资料
    public static final String USER_INFO = "appAuth/user/getInfo";
    //修改用户资料
    public static final String EDIT_USER_INFO = "appAuth/user/updateSelectiveInfo";
    //上传头像
    public static final String UPLOAD_HEADER = "appAuth/user/updateHeadImg";
    //留言板
    public static final String MESSAGE_BOARD = "appAuth/messageBoard/submitMessage";
    //根据手机号搜素用户
    public static final String SEARCH_CONTACT_BY_CELLPHONE = "appAuth/user/friend/searchUserByBindPhone";
    //申请添加新的好友
    public static final String APPLY_ADD_FRIEND = "appAuth/user/friend/addNewFriend";
    //好友列表
    public static final String FRIEND_LIST = "appAuth/user/friend/getAllFriend";
    //标签列表
    public static final String TAG_LIST = "appAuth/user/friendGroup/getAllFriendGroup";
    //新的朋友列表（对方申请列表）
    public static final String NEW_APPLY_LIST = "appAuth/user/findAddUserRecored";
    //处理新的朋友申请
    public static final String DISPOSE_APPLY = "appAuth/user/dealNewFriend";
    //好友详情
    public static final String MY_FRIEND_DETAILS = "appAuth/user/friend/userFriendDetail";
    //通过昵称搜索好友
    public static final String SEARCH_FRIEND_BY_NICKNAME = "appAuth/user/friend/searchFiendByNickname";
    //删除好友
    public static final String DELETE_FRIEND = "appAuth/user/friend/deleteUserFriend";
    //查看好友心情指数
    public static final String LOOK_FRIEND_MOOD = "appAuth/user/friend/userFriendEmotion";
    //某个标签下的好友列表
    public static final String MY_FRIEND_LIST_BY_TAG = "appAuth/user/friend/getFriendsByGroupId";
    //新增标签
    public static final String ADD_TAG = "appAuth/user/friendGroup/addFriendGroup";
    //删除标签
    public static final String DELETE_TAG = "appAuth/user/friendGroup/deleteFriendGroup";
    //移动好友到某些标签下
    public static final String MOVE_FRIEND_TO_TAG = "appAuth/user/friendGroup/markFriendGroups";
    //备注好友生日
    public static final String BACKUP_FRIEND_BIRTHDAY = "appAuth/user/friend/remarkBirthday";
    //添加对该好友心情查看权限
    public static final String ADD_MOOD_LOOK_PERMISSION = "appAuth/user/friend/addFriendsEmotoinPermission";
    //取消对该好友信息查看权限
    public static final String REMOVE_MOOD_LOOK_PERMISSION = "appAuth/user/friend/removeFriendsEmotoinPermission";
    //获取新的好友申请数量
    public static final String GET_NEW_APPLY_COUNT = "appAuth/user/getNewFriendListNumber";
    //获取我的心情
    public static final String GET_MY_MOOD = "appAuth/userEmotion";
    //设置我的心情
    public static final String SET_MY_MOOD = "appAuth/userEmotion/createEmotion";
    //获取我的本周心情
    public static final String GET_MY_WEEK_MOOD = "appAuth/userEmotion/getThisWeekEmotion";
    //查看好友一周心情
    public static final String GET_MY_FRIEND_WEEK_MOOD = "appAuth/user/friend/userFriendWeekEmotion";
    //拥有查看心情权限的好友
    public static final String GET_PERMISSION_FRIENDS = "appAuth/user/friend/emotoinPermissionFriends";
    //编辑标签
    public static final String EDIT_TAG = "appAuth/user/friendGroup/updateFriendGroup";
    //获取好友空闲状态
    public static final String GET_FRIEND_CALENDAR = "appAuth/activity/getFriendActivityPartInForMonth";
    //搜索标签
    public static final String SEARCH_TAG = "appAuth/user/friendGroup/searchGroup";

    //-------------------------------定制化提醒接口---------------------------
    //我的提醒名称列表
    public static final String REMIND_NAME_LIST = "appAuth/taskReminder/findPage";
    //删除定制化提醒
    public static final String DELETE_REMIND = "appAuth/taskReminder/removeTaskReminders";
    //获取定制化提醒
    public static final String GET_REMIND_DETAILS  = "appAuth/taskReminder/getTaskReminder";
    //定制化提醒下分享好友列表
    public static final String GET_REMIND_SHARE_FRIEND_LIST = "appAuth/taskReminder/getTaskReminderShareFriendList";
    //新增定制化提醒
    public static final String ADD_REMIND = "appAuth/taskReminder/createTaskReminder";


    //-------------------------------活动类接口----------------------------------
    //新建活动
    public static final String NEW_CREATE_ACTIVE = "appAuth/activity/createActivity";
    //修改活动
    public static final String EDIT_ACTIVE = "appAuth/activity/updateActivity";
    //活动详情
    public static final String ACTIVE_DETAILS = "appAuth/activity/activityDetail";
    //接受活动
    public static final String AGREE_ACTIVE = "appAuth/activity/acceptActivity";
    //拒绝活动
    public static final String REFUSE_ACTIVE = "appAuth/activity/refuseActivity";
    //删除活动
    public static final String DELETE_ACTIVE = "appAuth/activity/deleteActivity";
    //活动随意铃
    public static final String ACTIVE_BELL = "appAuth/activity/activityReminder";
    //屏蔽活动随意铃
    public static final String CLOSE_ACTIVE_BELL  = "appAuth/activity/remindShield";
    //退出活动
    public static final String EXIT_ACTIVE = "appAuth/activityInvite/exitActivity";
    //重发消息
    public static final String RESEND_MSG = "appAuth/activity/retryNotify";
    //邀请好友到某个活动
    public static final String ADD_FRIEND_BY_ACTIVE = "appAuth/activity/addInviteUser";
    //取消邀请
    public static final String CANCLE_INVITE_BY_ACTIVE ="appAuth/activityInvite/cancelInviteUser";
    //重新申请
    public static final String REAPPLY_ACTIVE = "appAuth/activityReapply/reapply";
    //接受重新申请
    public static final String AGREE_APPLY = "appAuth/activityReapply/acceptReapply";
    //拒绝重新申请
    public static final String REFUSE_APPLY = "appAuth/activityReapply/refuseReapply";
    //获取活动分享id
    public static final String GET_ACTIVE_SHARE_ID = "appAuth/activity/shareActivity";
    //根据id获取url
    public static final String GET_SHARE_URL = "direct/activity/partIn";
    //获取全部日程
    public static final String GET_ALL_ACTIVE = "appAuth/schedule/searchAll";
    //上传活动图片
    public static final String UPLOAD_ACTIVE_IMG = "appAuth/activity/uploadActivityImage";

    //----------------------------日程类接口-----------------------------------
    //日程搜索
    public static final String SEARCH_SCHEDULE = "appAuth/schedule/search";
    //日程（日）
    public static final String SCHDULE_DAY = "appAuth/schedule/listForDay";
    //日程（月）
    public static final String SCHDULE_MONTH = "appAuth/schedule/listForMonth";

    //-------------------------消息类接口---------------------------
    //消息列表
    public static final String MESSAGE_LIST = "appAuth/message/list";
    //获取最新消息数量
    public static final String GET_NEW_MSG_COUNT = "appAuth/message/getUnreadMessageCount";
    //读消息
    public static final String READ_MSG = "appAuth/message/readMessage";
    //删除消息
    public static final String DELETE_MESSAGE = "appAuth/message/removeMessages";
    //-------------------------个人设置》设置----------------------
    //关于我们
    public static final String ABOUT_ME = "appDirect/user/setting/aboutUs";
    //皮肤列表
    public static final String THEME_LIST = "appAuth/userSkin/skinList";
    //选择皮肤
    public static final String SELECT_THEME = "appAuth/user/setting/changeSkin";
    //上传皮肤
    public static final String UPLOAD_THEME = "appAuth/userSkin/uploadSkin";
    //删除皮肤
    public static final String DELETE_THEME = "appAuth/userSkin/removeSkin";
    //---------------------------广告类--------------------------------
    //获取广告
    public static final String GET_ADVERTISING = "appAuth/advertisementPut/findAdvertisement";
    //获取广告策略
    public static final String GET_ADVERTISINGTS = "appAuth/advertisementPut/getAdTactics";

    //----------------------------end---------------------------------------
    //更新设备号
    public static final String UPDATE_DEVICE_ID = "appAuth/user/updateDeviceNumber";
}
