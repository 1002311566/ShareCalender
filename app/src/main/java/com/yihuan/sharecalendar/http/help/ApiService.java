package com.yihuan.sharecalendar.http.help;


import com.yihuan.sharecalendar.modle.bean.AdvertisingBean;
import com.yihuan.sharecalendar.modle.bean.BaseBean;
import com.yihuan.sharecalendar.modle.bean.active.ActiveDetailsBean;
import com.yihuan.sharecalendar.modle.bean.friend.FriendListBean;
import com.yihuan.sharecalendar.modle.bean.friend.MyFriendDetailBean;
import com.yihuan.sharecalendar.modle.bean.friend.NewApplyListBean;
import com.yihuan.sharecalendar.modle.bean.friend.SearchUserBean;
import com.yihuan.sharecalendar.modle.bean.friend.TagListBean;
import com.yihuan.sharecalendar.modle.bean.home.MessageBean;
import com.yihuan.sharecalendar.modle.bean.home.ScheduleListBean;
import com.yihuan.sharecalendar.modle.bean.home.WeekMoodBean;
import com.yihuan.sharecalendar.modle.bean.login.CityBean;
import com.yihuan.sharecalendar.modle.bean.login.ConstellationBean;
import com.yihuan.sharecalendar.modle.bean.login.PhoneBean;
import com.yihuan.sharecalendar.modle.bean.login.ProvinceBean;
import com.yihuan.sharecalendar.modle.bean.mine.MyMoodBean;
import com.yihuan.sharecalendar.modle.bean.settting.AboutMeBean;
import com.yihuan.sharecalendar.modle.bean.settting.AutoRemindListBean;
import com.yihuan.sharecalendar.modle.bean.settting.ThemeListBean;
import com.yihuan.sharecalendar.modle.bean.settting.UserBean;
import com.yihuan.sharecalendar.ui.activity.setting.RemindDetails;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by Ronny on 2017/9/6.
 */

public interface ApiService<T> {



    //获取注册验证码
    @GET(Urls.GET_REGISTER_CODE)
    Observable<BaseBean<Object>> getRegisterCode(@QueryMap HashMap<String, String> map);

    //注册
    @POST(Urls.REGISTER)
    Observable<BaseBean<String>> register(@Body RequestBody body);

    //获取星座列表
    @GET(Urls.GET_CONSTELLATION_LIST)
    Observable<BaseBean<List<ConstellationBean>>> getConstellationList();

    //获取省份列表
    @GET(Urls.GET_PROVINCE_LIST)
    Observable<BaseBean<List<ProvinceBean>>> getProvienceList();

    //城市列表
    @GET(Urls.GET_CITY_LIST)
    Observable<BaseBean<List<CityBean>>> getCityList(@QueryMap HashMap<String, String> map);

    //登录
    @POST(Urls.LOGIN)
    Observable<BaseBean<String>> login(@Body RequestBody body);

    //注销
    @POST(Urls.LOGOUT)
    Observable<BaseBean> logout();

    //重置密码验证码
    @GET(Urls.RESET_PASSWORD_CODE)
    Observable<BaseBean<Object>> getResetPsdCode(@QueryMap HashMap<String, String> map);


    //重置密码
    @POST(Urls.RESET_PASSWORD)
    Observable<BaseBean> resetPassword(@Body RequestBody body);

    //获取个人资料
    //修改资料
    @GET(Urls.USER_INFO)
    Observable<BaseBean<UserBean>> getUserInfo();

    //修改资料
    @POST(Urls.EDIT_USER_INFO)
    Observable<BaseBean<Object>> editUserInfo(@Body RequestBody body);

    @Multipart
    @POST(Urls.UPLOAD_HEADER)
    Observable<BaseBean<Object>> uploadHeader(@Part MultipartBody.Part file);

    //留言板
    @POST(Urls.MESSAGE_BOARD)
    Observable<BaseBean<Object>> messageBoard(@Body RequestBody body);


    //根据手机号搜索好友
    @GET(Urls.SEARCH_CONTACT_BY_CELLPHONE)
    Observable<BaseBean<SearchUserBean>> searchFriendByPhone(@Query("bindPhone") String bindPhone);

    //根据昵称搜索好友
    @POST(Urls.SEARCH_FRIEND_BY_NICKNAME)
    Observable<BaseBean<SearchUserBean>> searchFriendByNickName(@Body RequestBody body);

    //申请好友
    @POST(Urls.APPLY_ADD_FRIEND)
    Observable<BaseBean<Object>> applyFriend(@Body RequestBody body);

    //获取新的朋友列表
    @POST(Urls.NEW_APPLY_LIST)
    Observable<BaseBean<List<NewApplyListBean>>> getNewApplyList(@Body RequestBody body);

    @POST(Urls.DISPOSE_APPLY)
    Observable<BaseBean<Object>> disposeApply(@Body RequestBody body);

    //获取全部好友列表
    @GET(Urls.FRIEND_LIST)
    Observable<BaseBean<List<FriendListBean>>> getFriendList();


    //获取新好友申请的数量
    @GET(Urls.GET_NEW_APPLY_COUNT)
    Observable<BaseBean<Integer>> getNewFriendApplyCount();

    //获取好友详情
    @GET(Urls.MY_FRIEND_DETAILS)
    Observable<BaseBean<MyFriendDetailBean>> getFriendDetails(@Query("userFriendId") String friendId);

    //获取标签列表
    @GET(Urls.TAG_LIST)
    Observable<BaseBean<List<TagListBean>>> getTagList();

    @GET(Urls.TAG_LIST)
    Observable<BaseBean<List<TagListBean>>> getTagList(@Query("friendId") String friendId);

    //新建标签
    @POST(Urls.ADD_TAG)
    Observable<BaseBean<Object>> createTag(@Body RequestBody body);

    //删除好友
    @GET(Urls.DELETE_FRIEND)
    Observable<BaseBean<Object>> deleteFriend(@Query("userFriendId") String friendId);

    //添加好友查看心情权限
    @POST(Urls.ADD_MOOD_LOOK_PERMISSION)
    Observable<BaseBean<Object>> addFriendLookMoodPermission(@Body RequestBody body);

    //获取定制化列表
    @POST(Urls.REMIND_NAME_LIST)
    Observable<BaseBean<List<AutoRemindListBean>>> getAutoRemindList(@Body RequestBody body);

    //删除定制化提醒
    @POST(Urls.DELETE_REMIND)
    Observable<BaseBean<Object>> deleteRemind(@Body RequestBody body);

    //查询好友心情指数
    @GET(Urls.LOOK_FRIEND_MOOD)
    Observable<BaseBean<List<MyMoodBean>>> queryFriendMood(@Query("userFriendId") String userFriendId);

    //消息列表
    @GET(Urls.MESSAGE_LIST)
    Observable<BaseBean<List<MessageBean>>> getMessageList(@QueryMap HashMap<String, Integer> map);

    //关于我们
    @GET(Urls.ABOUT_ME)
    Observable<BaseBean<AboutMeBean>> getAboutMe();

    //设置好友标签
    @POST(Urls.MOVE_FRIEND_TO_TAG)
    Observable<BaseBean<Object>> setFriendTag(@Body RequestBody body);

    //设置好友标签
    @GET(Urls.MY_FRIEND_LIST_BY_TAG)
    Observable<BaseBean<List<FriendListBean>>> getFriendListByTag(@Query("groupId") String groupId);

    //新建活动
    @POST(Urls.NEW_CREATE_ACTIVE)
    Observable<BaseBean<Integer>> createActive(@Body RequestBody body);

    //获取某日日程列表
    @POST(Urls.SCHDULE_DAY)
    @FormUrlEncoded
    Observable<BaseBean<ScheduleListBean>> getScheduleListByDay(@FieldMap Map<String, String> map);

    //获取某月日程列表
    @POST(Urls.SCHDULE_MONTH)
    @FormUrlEncoded
    Observable<BaseBean<ScheduleListBean>> getScheduleListByMonth(@FieldMap Map<String, String> map);

    //搜索日程
    @POST(Urls.SEARCH_SCHEDULE)
    @FormUrlEncoded
    Observable<BaseBean<ScheduleListBean>> searchSchedule(@FieldMap Map<String, String> map);

    //活动详情
    @GET(Urls.ACTIVE_DETAILS)
    Observable<BaseBean<ActiveDetailsBean>> getActiveDetails(@QueryMap Map<String, String> map);

    //接受活动
    @GET(Urls.AGREE_ACTIVE)
    Observable<BaseBean<Object>> agreeActive(@QueryMap Map<String, String> map);

    //拒绝活动
    @GET(Urls.REFUSE_ACTIVE)
    Observable<BaseBean<Object>> refuseActive(@QueryMap Map<String, String> map);

    //删除活动
    @GET(Urls.DELETE_ACTIVE)
    Observable<BaseBean<Object>> deleteActive(@QueryMap Map<String, String> map);

    //我的心情
    @GET(Urls.GET_MY_MOOD)
    Observable<BaseBean<MyMoodBean>> getMyMood();

    //设置我的心情
    @POST(Urls.SET_MY_MOOD)
    Observable<BaseBean<Object>> setMyMood(@Body RequestBody body);

    //获取我的本周心情
    @GET(Urls.GET_MY_WEEK_MOOD)
    Observable<BaseBean<WeekMoodBean>> getMyWeekMood();

    //活动随意铃
    @GET(Urls.ACTIVE_BELL)
    Observable<BaseBean<Object>> resendBell(@Query("activityId") int activityId);

    //消息重发
    @POST(Urls.RESEND_MSG)
    Observable<BaseBean<Object>> resendMsg(@Body RequestBody body);

    //退出活动
    @POST(Urls.EXIT_ACTIVE)
    Observable<BaseBean<Object>> exitActive(@Body RequestBody body);

    //屏蔽活动随意铃
    @GET(Urls.CLOSE_ACTIVE_BELL)
    Observable<BaseBean<Object>> closeActiveBell(@Field("id") String activityId);

    //新增提醒
    @POST(Urls.ADD_REMIND)
    Observable<BaseBean<Integer>> addRemind(@Body RequestBody body);

    //备注好友生日
    @POST(Urls.BACKUP_FRIEND_BIRTHDAY)
    Observable<BaseBean<Object>> setFriendBirthday(@Body RequestBody body);

    //不再提醒
    @GET(Urls.CLOSE_ACTIVE_BELL)
    Observable<BaseBean<Object>> dontRemind(@Query("id") int msgId);

    //邀请好友到某个活动
    @POST(Urls.ADD_FRIEND_BY_ACTIVE)
    Observable<BaseBean<Object>> addFriendsByActive(@Body RequestBody body);

    //取消某人在某活动种
    @POST(Urls.CANCLE_INVITE_BY_ACTIVE)
    Observable<BaseBean<Object>> cancleFriendByActive(@Body RequestBody map);

    //重新申请
    @POST(Urls.REAPPLY_ACTIVE)
    Observable<BaseBean<Object>> reApply(@Body RequestBody map);

    //获取好友一周心情
    @GET(Urls.GET_MY_FRIEND_WEEK_MOOD)
    Observable<BaseBean<WeekMoodBean>> queryFriendWeekMood(@Query("userFriendId") String userFriendId);

    //获取最新消息数量
    @GET(Urls.GET_NEW_MSG_COUNT)
    Observable<BaseBean<Integer>> getNewMsgCount();

    //读消息
    @GET(Urls.READ_MSG)
    Observable<BaseBean> readMsg(@Query("id") int id);

    //接受重新申请
    @POST(Urls.AGREE_APPLY)
    Observable<BaseBean<Object>> agreeApply(@Body RequestBody body);

    //拒绝重新申请
    @POST(Urls.REFUSE_APPLY)
    Observable<BaseBean<Object>> refuseApply(@Body RequestBody body);

    //获取活动详情
    @GET(Urls.GET_REMIND_DETAILS)
    Observable<BaseBean<RemindDetails>> getRemindDetails(@Query("taskReminderId") Integer taskReminderId);

    //删除标签
    @GET(Urls.DELETE_TAG)
    Observable<BaseBean<Object>> deleteTag(@Query("groupId") Integer groupId);

    //皮肤列表
    @GET(Urls.THEME_LIST)
    Observable<BaseBean<List<ThemeListBean>>> getThemeList(@Query("id") Integer lastId);

    //拥有查看心情权限的好友
    @POST(Urls.GET_PERMISSION_FRIENDS)
    @FormUrlEncoded
    Observable<BaseBean<List<FriendListBean>>> getPermissionFriend(@Field("permission") String permission);

    //编辑活动
    @POST(Urls.EDIT_ACTIVE)
    Observable<BaseBean<List<FriendListBean>>> editActive(@Body RequestBody body);

    //注册协议
    @GET(Urls.REGISTER_PROTOCOL)
    Observable<BaseBean<String>> getRegisterProtocol();

    //获取活动分享id
    @GET(Urls.GET_ACTIVE_SHARE_ID)
    Observable<BaseBean<Integer>> get_active_share_id(@Query("id") Integer id);

    //编辑标签
    @POST(Urls.EDIT_TAG)
    Observable<BaseBean<Object>> editTag(@Body RequestBody body);

    //选择皮肤
    @GET(Urls.SELECT_THEME)
    Observable<BaseBean<Object>> selectTheme(@Query("skinId") String skinId);


    //上传皮肤
    @Multipart
    @POST(Urls.UPLOAD_THEME)
    Observable<BaseBean<Object>> uploadTheme(@Part MultipartBody.Part file);

    //获取广告
    @POST(Urls.GET_ADVERTISING)
    Observable<BaseBean<List<AdvertisingBean>>> getAdvertising(@Body RequestBody body);

    //获取广告策略
    @GET(Urls.GET_ADVERTISINGTS)
    Observable<BaseBean<Object>> getAdvertisingTs();

    //更新设备号
    @GET(Urls.UPDATE_DEVICE_ID)
    Observable<BaseBean<Object>> updateDeviceID(@Query("deviceNumber") String deviceNumber);

    //删除皮肤
    @GET(Urls.DELETE_THEME)
    Observable<BaseBean<Object>> deleteTheme(@Query("id") int id);

    //删除消息
    @POST(Urls.DELETE_MESSAGE)
    Observable<BaseBean<Object>> deleteMessage(@Body RequestBody body);

    //获取好友空闲状态
    @POST(Urls.GET_FRIEND_CALENDAR)
    Observable<BaseBean<List<String>>> getFriendCalendar(@Body RequestBody body);

    //获取全部日程
    @POST(Urls.GET_ALL_ACTIVE)
    Observable<BaseBean<ScheduleListBean>> getAllSchedule(@Body RequestBody body);

    //搜索标签
    @POST(Urls.SEARCH_TAG)
    Observable<BaseBean<List<TagListBean>>> searchTag(@Body RequestBody body);

    @Multipart
    @POST(Urls.UPLOAD_ACTIVE_IMG)
    Observable<BaseBean<String>> uploadActiveImg(@Part MultipartBody.Part filePart);

//==========================================

    //微信登录
    @GET(Urls.WX_LOGIN)
    Observable<BaseBean<String>> loginByWX(@Query("code") String code);

    //获取完善资料验证码
    @GET(Urls.GET_BIND_CODE)
    Observable<BaseBean<Object>> getBindCode(@Query("bindPhone") String bindPhone);

    //绑定微信
    @POST(Urls.BIND_WX)
    Observable<BaseBean<String>> bindWX(@Body RequestBody body);

    //校验手机号是否已注册
    @GET(Urls.CHECK_PHONE)
    Observable<BaseBean<PhoneBean>> checkPhone(@Query("bindPhone") String cellphone);

//    @Multipart
//    @POST("{url}")
//    Observable<ResponseBody> uploadFile(@Part("image\";filename=\"image.jpg")RequestBody body);
//
//    @POST("{url}")
//    Observable<ResponseBody> json(@Path("url") String url, @Body RequestBody jsonStr);


}
