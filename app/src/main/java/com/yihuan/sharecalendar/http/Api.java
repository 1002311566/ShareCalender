package com.yihuan.sharecalendar.http;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.yihuan.sharecalendar.http.help.MyObserver;
import com.yihuan.sharecalendar.http.help.HttpHelper;
import com.yihuan.sharecalendar.modle.bean.AdvertisingBean;
import com.yihuan.sharecalendar.modle.bean.AdvertisingParams;
import com.yihuan.sharecalendar.modle.bean.active.ActiveDetailsBean;
import com.yihuan.sharecalendar.modle.bean.active.AlarmTime;
import com.yihuan.sharecalendar.modle.bean.active.InviteFriendBean;
import com.yihuan.sharecalendar.modle.bean.friend.FriendBean;
import com.yihuan.sharecalendar.modle.bean.friend.FriendListBean;
import com.yihuan.sharecalendar.modle.bean.active.InviteUser;
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
import com.yihuan.sharecalendar.modle.calendar.ActiveBean;
import com.yihuan.sharecalendar.modle.calendar.RemindBean;
import com.yihuan.sharecalendar.ui.activity.setting.RemindDetails;
import com.yihuan.sharecalendar.utils.LogUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by Ronny on 2017/9/7.
 * 所有请求都在这里
 * TODO 说明
 * 1.retrofit 参数如是null，不会报错，仅视为没有此参数
 * 2.hashMap允许添加null键值
 */

public class Api {

    private static HttpHelper helper;

    static {
        helper = HttpHelper.getInstance();
    }


    private static void log(HashMap map) {
        String s = new Gson().toJson(map);
        LogUtils.e("请求参数---------------------" + s.toString());
    }

    private static void log(String key, String valus) {
        LogUtils.e("请求参数--------------------- " + key + "=" + valus);
    }

    /**
     * 封装json格式请求体
     */
    private static RequestBody getJsonBody(HashMap<String, String> map) {
        String s = new Gson().toJson(map);
        RequestBody body = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), s);
        LogUtils.e("请求参数---------------------" + s.toString());
        return body;
    }

    private static RequestBody getJsonBodyList(List<String> list) {
        String s = new Gson().toJson(list);
        RequestBody body = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), s);
        LogUtils.e("请求参数---------------------" + s.toString());
        return body;
    }

    private static RequestBody getJsonBodyO(HashMap<String, Object> map) {
        String s = new Gson().toJson(map);
        RequestBody body = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), s);
        LogUtils.e("请求参数---------------------" + s.toString());
        return body;
    }

    //---------------------------------------登录注册类start----------------------------------
    //TODO 登录
    public static void login(String mobile, String password, MyObserver<String> call) {
        HashMap<String, String> map = new HashMap<>();
        map.put("bindPhone", mobile);
        map.put("password", password);
        helper.request(helper.getmService().login(getJsonBody(map)), call);
    }

    //TODO 获取注册验证码
    public static void getRegisterCode(String phone, MyObserver<Object> call) {
        HashMap<String, String> map = new HashMap<>();
        map.put("bindPhone", phone);
        log(map);
        helper.request(helper.getmService().getRegisterCode(map), call);
    }

    //TODO 注册
    public static void register(String bindPhone, String validateCode, String password,
                                MyObserver<String> call) {
        HashMap<String, String> map = new HashMap<>();
        map.put("bindPhone", bindPhone);
        map.put("validateCode", validateCode);
        map.put("password", password);
        helper.request(helper.getmService().register(getJsonBody(map)), call);
    }

    //TODO 获取星座列表
    public static void getConstellationList(MyObserver<ArrayList<ConstellationBean>> call) {
        helper.request(helper.getmService().getConstellationList(), call);
    }

    //TODO 获取省份列表
    public static void getProvienceList(MyObserver<List<ProvinceBean>> call) {
        helper.request(helper.getmService().getProvienceList(), call);
    }

    //TODO 获取城市列表
    public static void getCityList(String id, MyObserver<List<CityBean>> call) {
        HashMap<String, String> map = new HashMap<>();
        map.put("provinceId", id);
        log(map);
        helper.request(helper.getmService().getCityList(map), call);
    }

    //TODO 注销
    public static void logout(MyObserver<Object> call) {
        helper.request(helper.getmService().logout(), call);
    }

    //TODO 发送重置密码验证码
    public static void getResetPsdCode(String bindPhone, MyObserver<Object> call) {
        HashMap<String, String> map = new HashMap<>();
        map.put("bindPhone", bindPhone);
        log(map);
        helper.request(helper.getmService().getResetPsdCode(map), call);
    }

    //TODO 检查重置密码验证码
    public static void checkResetPsdCode(String bindPhone, String validateCode, String password, MyObserver<Object> call) {
        HashMap<String, String> map = new HashMap<>();
        map.put("bindPhone", bindPhone);
        map.put("password", password);
        map.put("validateCode", validateCode);
        helper.request(helper.getmService().resetPassword(getJsonBody(map)), call);
    }

    //TODO 重置密码
    public static void resetPassword(String bindPhone, String password, MyObserver<Object> call) {
        HashMap<String, String> map = new HashMap<>();
        map.put("bindPhone", bindPhone);
        map.put("password", password);
        helper.request(helper.getmService().resetPassword(getJsonBody(map)), call);
    }
//----------------------------------------end------------------------------------------

//---------------------------------用户接口类start-------------------------------------

    //获取个人资料
    public static void getUserInfo(MyObserver<UserBean> call) {
        helper.request(helper.getmService().getUserInfo(), call);
    }

    //修改资料
    public static void editUserInfo(String signature, String nickname, String sex, String period, String constellation, String location, MyObserver<Object> call) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("signature", signature);
        map.put("nickname", nickname);
        if (sex != null) {
            if (sex.equals("保密")) {
                sex = "0";
            } else if (sex.equals("男")) {
                sex = "1";
            } else if (sex.equals("女")) {
                sex = "2";
            }
            map.put("sex", sex);
        }
        if (period != null && period.contains("后")) {
            map.put("period", period.substring(0, period.indexOf("后")));
        }
        map.put("constellation", constellation);
        map.put("location", location);
        helper.request(helper.getmService().editUserInfo(getJsonBodyO(map)), call);
    }

    //上传头像
    public static void uploadHeadImg(File file, MyObserver<Object> call) {
        LogUtils.e("请求参数------------headerImage=" + file.getAbsolutePath());
        RequestBody body =
                RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part filePart = MultipartBody.Part.createFormData("headerImage", file.getName(), body);
        helper.request(helper.getmService().uploadHeader(filePart), call);
    }

    //留言板
    public static void messageBoard(String msgType, String content, MyObserver<Object> call) {
        HashMap<String, String> map = new HashMap<>();
        map.put("msgType", msgType);
        map.put("content", content);
        helper.request(helper.getmService().messageBoard(getJsonBody(map)), call);
    }

    //根据手机号搜索用户
    public static void searchAccountByPhone(String bindPhone, MyObserver<SearchUserBean> call) {
        LogUtils.e("请求参数----------bindPhone:" + bindPhone);
        helper.request(helper.getmService().searchFriendByPhone(bindPhone), call);
    }

    //根据昵称搜索好友
    public static void searchFriendByNickName(String nickname, MyObserver<SearchUserBean> call) {
        HashMap<String, String> map = new HashMap<>();
        map.put("nickname", nickname);
        helper.request(helper.getmService().searchFriendByNickName(getJsonBody(map)), call);
    }

    //申请添加好友
    public static void applyFriend(String id, String msg, MyObserver<Object> call) {
        HashMap<String, String> map = new HashMap<>();
        map.put("friendId", id);
        map.put("attachMessage", msg);
        helper.request(helper.getmService().applyFriend(getJsonBody(map)), call);
    }

    //获取新的朋友列表
    public static void getNewApplyList(Integer lastId, MyObserver<List<NewApplyListBean>> call) {
        HashMap<String, Object> map = new HashMap<>();
        if (lastId != null) {
            map.put("id", lastId);
        }
        helper.request(helper.getmService().getNewApplyList(getJsonBodyO(map)), call);
    }

    //处理新的朋友申请
    public static void disposeNewApply(String userId, String proStatus, MyObserver<Object> call) {
        HashMap<String, String> map = new HashMap<>();
        map.put("userId", userId);
        map.put("proStatus", proStatus);
        helper.request(helper.getmService().disposeApply(getJsonBody(map)), call);
    }

    //获取全部好友列表
    public static void getFriendList(MyObserver<List<FriendListBean>> call) {
        helper.request(helper.getmService().getFriendList(), call);
    }

    //获取新申请好友的数量
    public static void getNewFriendApplyCount(MyObserver<Integer> call) {
        helper.request(helper.getmService().getNewFriendApplyCount(), call);
    }

    //获取好友详情
    public static void getFriendDetails(String friendId, MyObserver<MyFriendDetailBean> call) {
        LogUtils.e("请求参数----------friendId:" + friendId);
        helper.request(helper.getmService().getFriendDetails(friendId), call);
    }

    //获取标签列表
    public static void getTagList(MyObserver<List<TagListBean>> call) {
        helper.request(helper.getmService().getTagList(), call);
    }

    public static void getTagList(String friendId, MyObserver<List<TagListBean>> call) {
        log("friendId", friendId);
        helper.request(helper.getmService().getTagList(friendId), call);
    }

    //新建标签
    public static void createTag(String tagName, List<FriendBean> friendIdList, MyObserver<Object> call) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("groupName", tagName);
        map.put("userFriendList", friendIdList);
        helper.request(helper.getmService().createTag(getJsonBodyO(map)), call);
    }

    //删除好友
    public static void deleteFriend(String friendId, MyObserver<Object> call) {
        LogUtils.e("请求参数----------friendId:" + friendId);
        helper.request(helper.getmService().deleteFriend(friendId), call);
    }

    //添加好友查看心情权限
    public static void addFriendLookMoodPermission(List<String> friendIdList, MyObserver<Object> call) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("friendIdList", friendIdList);
        helper.request(helper.getmService().addFriendLookMoodPermission(getJsonBodyO(map)), call);
    }

    //获取定制化提醒列表
    public static void getAutoRemindList(Integer lastId, MyObserver<List<AutoRemindListBean>> call) {
        HashMap<String, String> map = new HashMap<>();
        if (lastId != null) {
            map.put("id", lastId + "");
        }
        helper.request(helper.getmService().getAutoRemindList(getJsonBody(map)), call);
    }

    //删除定制化提醒
    public static void deleteRemind(ArrayList<Integer> taskRemindIdList, MyObserver<Object> call) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("taskReminderIds", taskRemindIdList);
        helper.request(helper.getmService().deleteRemind(getJsonBodyO(map)), call);
    }

    //查询好友心情指数
    public static void queryFriendMood(String userFriendId, MyObserver<List<MyMoodBean>> call) {
        LogUtils.e("请求参数----------userFriendId:" + userFriendId);
        helper.request(helper.getmService().queryFriendMood(userFriendId), call);
    }

    //获取消息列表
    public static void getMessageList(Integer pageId, MyObserver<List<MessageBean>> call) {
        HashMap<String, Integer> map = new HashMap<>();
        if (pageId != null) {
            map.put("pageId", pageId);
        }
        helper.request(helper.getmService().getMessageList(map), call);
    }

    //关于我们
    public static void getAboutMe(MyObserver<AboutMeBean> call) {
        helper.request(helper.getmService().getAboutMe(), call);
    }

    //设置好友标签
    public static void setFriendTag(String friendId, List<String> groupIds, MyObserver<Object> call) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("friendId", friendId);
        map.put("groupIds", groupIds);
        helper.request(helper.getmService().setFriendTag(getJsonBodyO(map)), call);
    }

    //根据标签获取好友列表
    public static void getFriendListByTag(String groupId, MyObserver<List<FriendListBean>> call) {
        LogUtils.e("请求参数----------groupId:" + groupId);
        helper.request(helper.getmService().getFriendListByTag(groupId), call);
    }

    //新建活动
    public static void createActive(ActiveBean activeBean, ArrayList<InviteUser> activityInviteList, MyObserver<Integer> call) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("title", activeBean.getTitle());//todo 标题
        map.put("address", activeBean.getLocation());//todo 地址
        map.put("longitude", activeBean.getLon());//todo 经度
        map.put("latitude", activeBean.getLat());//todo 纬度
        map.put("startTimeStr", activeBean.getStart_time().toTime());//todo 开始时间
        map.put("endTimeStr", activeBean.getEnd_time().toTime());//todo 结束时间
        map.put("isRepeadRemind", activeBean.getCycleFlag());//todo 是否重复提醒
        String repeatPeriod = activeBean.getCycle();
        map.put("repeatPeriod", repeatPeriod == null ? "-1" : (repeatPeriod.equals("0") ? "-1" : repeatPeriod));//todo 重复周期
        map.put("comments", activeBean.getDes());//todo 说明
        map.put("fullDay", activeBean.isFullday() ? "1" : "0");//todo 是否全天 1：是  、 0：否
        map.put("activityInviteList", activityInviteList);//todo 邀请的用户
        map.put("activityAlarms", activeBean.getRemindList());//todo 活动提醒时间
        map.put("region", activeBean.getArea());
        map.put("activityImages", activeBean.getImgList());
        helper.request(helper.getmService().createActive(getJsonBodyO(map)), call);
    }

    //todo 编辑活动
    public static void editActive(ActiveBean activeBean, List<InviteUser> activityInviteList, MyObserver<Object> call) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", activeBean.getActive_id());
        map.put("title", activeBean.getTitle());//todo 标题
        map.put("address", activeBean.getLocation());//todo 地址
        map.put("longitude", activeBean.getLon());//todo 经度
        map.put("latitude", activeBean.getLat());//todo 纬度
        map.put("startTimeStr", activeBean.getStart_time().toTime());//todo 开始时间
        map.put("endTimeStr", activeBean.getEnd_time().toTime());//todo 结束时间
        map.put("isRepeadRemind", activeBean.getCycleFlag());//todo 是否重复提醒
        String repeatPeriod = activeBean.getCycle();
        map.put("repeatPeriod", repeatPeriod == null ? "-1" : (repeatPeriod.equals("0") ? "-1" : repeatPeriod));//todo 重复周期
        map.put("comments", activeBean.getDes());//todo 说明
        map.put("fullDay", activeBean.isFullday() ? "1" : "0");//todo 是否全天 1：是  、 0：否
        map.put("activityInviteList", activityInviteList);//todo 邀请的用户
        map.put("activityAlarms", activeBean.getRemindList());//todo 活动提醒时间
        map.put("activityImages", activeBean.getImgList());
        helper.request(helper.getmService().editActive(getJsonBodyO(map)), call);
    }

    //获取日程列表 ---根据日期
    public static void getScheduleListByDay(String date, MyObserver<ScheduleListBean> call) {
        HashMap<String, String> map = new HashMap<>();
        if (!TextUtils.isEmpty(date)) {
            map.put("day", date);//todo 日期
        }
        log(map);
        helper.request(helper.getmService().getScheduleListByDay(map), call);
    }

    //获取日程列表 ---根据月份
    public static void getScheduleListByMonth(String date, MyObserver<ScheduleListBean> call) {
        HashMap<String, String> map = new HashMap<>();
        if (!TextUtils.isEmpty(date)) {
            map.put("month", date);//todo
        }
        log(map);
        helper.request(helper.getmService().getScheduleListByMonth(map), call);
    }

    //搜索日程
    public static void searchSchedule(String title, MyObserver<ScheduleListBean> call) {
        HashMap<String, String> map = new HashMap<>();
        map.put("title", title);
        helper.request(helper.getmService().searchSchedule(map), call);
    }

    //活动详情
    public static void getActiveDetails(int activityId, MyObserver<ActiveDetailsBean> call) {
        HashMap<String, String> map = new HashMap<>();
        map.put("activityId", activityId + "");
        helper.request(helper.getmService().getActiveDetails(map), call);
    }

    //接受活动
    public static void agreeActive(int activityId, MyObserver<Object> call) {
        HashMap<String, String> map = new HashMap<>();
        map.put("activityId", activityId + "");
        helper.request(helper.getmService().agreeActive(map), call);
    }

    //拒绝活动
    public static void refuseActive(int activityId, MyObserver<Object> call) {
        HashMap<String, String> map = new HashMap<>();
        map.put("activityId", activityId + "");
        helper.request(helper.getmService().refuseActive(map), call);
    }

    //删除活动
    public static void deleteActive(int activityId, MyObserver<Object> call) {
        HashMap<String, String> map = new HashMap<>();
        map.put("activityId", activityId + "");
        helper.request(helper.getmService().deleteActive(map), call);
    }

    //我的心情
    public static void getMyMood(MyObserver<MyMoodBean> call) {
        helper.request(helper.getmService().getMyMood(), call);
    }

    //设置我的心情
    public static void setMyMood(String emotion, MyObserver<Object> call) {
        HashMap<String, String> map = new HashMap<>();
        map.put("emotion", emotion);
        helper.request(helper.getmService().setMyMood(getJsonBody(map)), call);
    }

    //获取我的本周心情
    public static void getMyWeekMood(MyObserver<WeekMoodBean> call) {
        helper.request(helper.getmService().getMyWeekMood(), call);
    }

    //todo 随意铃
    public static void resendBell(int activityId, MyObserver<Object> call) {
        log("activityId", activityId + "");
        helper.request(helper.getmService().resendBell(activityId), call);
    }

    //todo 重发消息
    public static void resendMsg(int activityId, MyObserver<Object> call) {
        resendMsg(activityId, null, call);
    }

    //todo 重发通知消息
    public static void resendMsg(int id, String inviteUser, MyObserver<Object> call) {
        HashMap<String, String> map = new HashMap<>();
        map.put("id", id + "");
        if (inviteUser != null) {
            map.put("inviteUser", inviteUser);
        }
        helper.request(helper.getmService().resendMsg(getJsonBody(map)), call);
    }

    //todo 退出活动
    public static void exitActive(Integer activityId, MyObserver<Object> call) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("activityId", activityId);
        helper.request(helper.getmService().exitActive(getJsonBodyO(map)), call);
    }

    //todo 新增提醒
    public static void addRemind(RemindBean remindBean, MyObserver<Integer> call) {
        HashMap<String, Object> map = new HashMap<>();
        if (remindBean.getRemind_id() != -1) {
            map.put("id", remindBean.getRemind_id());
        }
        map.put("name", remindBean.getName());
        try {
            map.put("startTime", remindBean.getStartTime().toYMD());
            map.put("endTime", remindBean.getEndTime().toYMD());
        } catch (Exception e) {
            e.printStackTrace();
        }
        map.put("days", remindBean.getDays());//周&月
        map.put("shareFriendList", remindBean.getShareFriendList());//分享好友
        map.put("reminderTimeList", remindBean.getReminderTimeList());//提醒时间
        map.put("dayType", remindBean.getDayType());//1 周 2 月
        //todo 根据间隔时间类型设置参数
        helper.request(helper.getmService().addRemind(getJsonBodyO(map)), call);
    }

    //todo 备注好友生日
    public static void setFriendBirthday(String friendId, String birthdayStr,
                                         String cnCalendarBirthdayStr, String isPermenentRemind, MyObserver<Object> call) {
        HashMap<String, String> map = new HashMap<>();
        map.put("friendId", friendId);
        if (birthdayStr != null) {
            map.put("birthdayStr", birthdayStr);
        }
        if (cnCalendarBirthdayStr != null) {
            map.put("cnCalendarBirthdayStr", cnCalendarBirthdayStr);
        }
        if (isPermenentRemind != null) {
            map.put("isPermenentRemind", isPermenentRemind);
        }

        helper.request(helper.getmService().setFriendBirthday(getJsonBody(map)), call);
    }

    //todo 不再提醒
    public static void dontRemind(int msgId, MyObserver<Object> call) {
        log("id", msgId + "");
        helper.request(helper.getmService().dontRemind(msgId), call);
    }

    //todo 邀请好友到某个活动
    public static void addFriendsByActive(InviteFriendBean inviteFriendBean, MyObserver<Object> call) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", inviteFriendBean.activeId);
        map.put("inviteUserIds", inviteFriendBean.inviteUserIds);
        helper.request(helper.getmService().addFriendsByActive(getJsonBodyO(map)), call);
    }

    //todo 取消邀请
    public static void cancleFriendByActive(int activityId, int inviteUser, MyObserver<Object> call) {
        HashMap<String, String> map = new HashMap<>();
        map.put("activityId", activityId + "");
        map.put("inviteUser", inviteUser + "");
        helper.request(helper.getmService().cancleFriendByActive(getJsonBody(map)), call);
    }

    //todo 重新申请
    public static void reApply(int activityId, MyObserver<Object> call) {
        HashMap<String, String> map = new HashMap<>();
        map.put("activityId", activityId + "");
        helper.request(helper.getmService().reApply(getJsonBody(map)), call);
    }

    //todo 查询好友一周心情
    public static void queryFriendWeekMood(String userFriendId, MyObserver<WeekMoodBean> call) {
        log("userFriendId", userFriendId);
        helper.request(helper.getmService().queryFriendWeekMood(userFriendId), call);
    }

    //todo 获取最新消息数量
    public static void getNewMsgCount(MyObserver<Integer> call) {
        helper.request(helper.getmService().getNewMsgCount(), call);
    }

    //todo 读消息
    public static void readMsg(int id, MyObserver<Integer> call) {
        log("id", id+"");
        helper.request(helper.getmService().readMsg(id), call);
    }

    //todo 拒绝重新申请
    public static void refuseApply(String inviteId, MyObserver<Integer> call) {
        HashMap<String, String> map = new HashMap<>();
        map.put("inviteId", inviteId);
        helper.request(helper.getmService().refuseApply(getJsonBody(map)), call);
    }

    //todo 接受重新申请
    public static void agreeApply(String inviteId, MyObserver<Integer> call) {
        HashMap<String, String> map = new HashMap<>();
        map.put("inviteId", inviteId);
        helper.request(helper.getmService().agreeApply(getJsonBody(map)), call);
    }

    //todo 获取活动详情
    public static void getRemindDetails(Integer taskReminderId, MyObserver<RemindDetails> call) {
        log("taskReminderId", taskReminderId.toString());
        helper.request(helper.getmService().getRemindDetails(taskReminderId), call);
    }

    //todo 删除标签
    public static void deleteTag(Integer groupId, MyObserver<RemindDetails> call) {
        log("groupId", groupId.toString());
        helper.request(helper.getmService().deleteTag(groupId), call);
    }

    //todo 皮肤列表
    public static void getThemeList(Integer lastId, MyObserver<List<ThemeListBean>> call) {
        helper.request(helper.getmService().getThemeList(lastId), call);
    }

    //todo 拥有查看心情权限的好友
    public static void getPermissionFriend(String permission, MyObserver<List<FriendListBean>> call) {
        //1 : 有权限，默认为1  2 : 没权限的好友列表
        log("permission", permission);
        helper.request(helper.getmService().getPermissionFriend(permission), call);
    }

    //todo 注册协议
    public static void getRegisterProtocol(MyObserver<String> call) {
        //1 : 有权限，默认为1  2 : 没权限的好友列表
        helper.request(helper.getmService().getRegisterProtocol(), call);
    }

    //todo 获取活动分享id
    public static void get_active_share_id(Integer activeId, MyObserver<Integer> call) {
        helper.request(helper.getmService().get_active_share_id(activeId), call);
    }

    //todo 编辑标签
    public static void editTag(Integer tagId, String tagName, List<FriendBean> friendIdList, MyObserver<Object> call) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("groupName", tagName);
        map.put("id", tagId);
        map.put("userFriendList", friendIdList);
        helper.request(helper.getmService().editTag(getJsonBodyO(map)), call);
    }

    /**
     * 选择皮肤
     *
     * @param skinId
     * @param myObserver
     */
    public static void selectTheme(String skinId, MyObserver<Object> myObserver) {
        log("skinId", skinId);
        helper.request(helper.getmService().selectTheme(skinId), myObserver);
    }

    //todo 上传皮肤
    public static void uploadTheme(File file, MyObserver<Object> call) {
        LogUtils.e("请求参数------------skinFile=" + file.getAbsolutePath());
        RequestBody body =
                RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part filePart = MultipartBody.Part.createFormData("skinFile", file.getName(), body);
        helper.request(helper.getmService().uploadTheme(filePart), call);
    }

    //todo 获取广告
    public static void getAdvertising(AdvertisingParams bean, int pageSize, Integer lastId, MyObserver<List<AdvertisingBean>> myObserver) {
        HashMap<String, String> map = new HashMap<>();
        if (bean != null) {
            map.put("playType", bean.playType);
            map.put("exeType", bean.exeType);
            map.put("adType", bean.adType);
            map.put("exeLocation", bean.exeLocation);
        }
        map.put("pageSize", pageSize + "");
        if (lastId != null) {
            map.put("id", lastId + "");
        }
        helper.request(helper.getmService().getAdvertising(getJsonBody(map)), myObserver);
    }

    //todo 获取广告策略
    public static void getAdvertisingTs(MyObserver<Object> myObserver) {
        helper.request(helper.getmService().getAdvertisingTs(), myObserver);
    }

    //todo 更新设备号
    public static void updateDeveiceID(String deviceNumber, MyObserver<Object> myObserver) {
        log("deviceNumber", deviceNumber);
        helper.request(helper.getmService().updateDeviceID(deviceNumber), myObserver);
    }

    //todo 删除皮肤
    public static void deleteTheme(int id, MyObserver<Object> myObserver) {
        log("id", id + "");
        helper.request(helper.getmService().deleteTheme(id), myObserver);
    }

    //todo 删除消息
    public static void deleteMessage(List<Integer> ids, MyObserver<Object> myObserver) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("messageIds", ids);
        helper.request(helper.getmService().deleteMessage(getJsonBodyO(map)), myObserver);
    }

    //todo 获取好友空闲状态
    public static void getFriendCalendar(String acceptUser, String queryTime, MyObserver<List<String>> myObserver) {
        HashMap<String, String> map = new HashMap<>();
        map.put("acceptUser", acceptUser);
        map.put("queryTime", queryTime);
        helper.request(helper.getmService().getFriendCalendar(getJsonBody(map)), myObserver);
    }

    //todo 获取所有日程
    public static void getAllSchedule(MyObserver<ScheduleListBean> myObserver) {
        HashMap<String, String> map = new HashMap<>();
        helper.request(helper.getmService().getAllSchedule(getJsonBody(map)), myObserver);
    }

    //todo 搜索标签
    public static void searchTag(String title, MyObserver<List<TagListBean>> myObserver) {
        HashMap<String, String> map = new HashMap<>();
        map.put("groupName", title);
        helper.request(helper.getmService().searchTag(getJsonBody(map)), myObserver);
    }

    //todo 上传活动图片
    public static void uploadActiveImg(String path, MyObserver<String> myObserver) {
        File file = new File(path);
        LogUtils.e("请求参数------------imageFile=" + file.getAbsolutePath());
        RequestBody body =
                RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part filePart = MultipartBody.Part.createFormData("imageFile", file.getName(), body);
        helper.request(helper.getmService().uploadActiveImg(filePart), myObserver);
    }

    //----------------------------------------end------------------------------------------
    //微信登录
    public static void loginByWX(String code, MyObserver<String> call) {
        helper.request(helper.getmService().loginByWX(code), call);
    }

    //获取完善资料验证码
    public static void getBindCode(String bindPhone, MyObserver<String> call) {
        helper.request(helper.getmService().getBindCode(bindPhone), call);
    }

    //绑定微信
    public static void bindWX(String cellphone, String password, String code, String captcha, MyObserver<String> call) {
        HashMap<String, String> map = new HashMap<>();
        map.put("cellphone", cellphone);
        if (!TextUtils.isEmpty(password)) {
            map.put("password", password);
        }
        map.put("code", code);
        map.put("captcha", captcha);
        helper.request(helper.getmService().bindWX(getJsonBody(map)), call);
    }

    /**
     * 校验手机是否注册过
     *
     * @param cellphone
     * @param call
     */
    public static void checkPhone(String cellphone, MyObserver<PhoneBean> call) {
        log("bindPhone", cellphone);
        helper.request(helper.getmService().checkPhone(cellphone), call);
    }

}
