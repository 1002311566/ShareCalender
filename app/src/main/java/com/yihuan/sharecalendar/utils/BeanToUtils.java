package com.yihuan.sharecalendar.utils;

import android.text.TextUtils;

import com.yihuan.sharecalendar.R;
import com.yihuan.sharecalendar.global.App;
import com.yihuan.sharecalendar.http.help.Urls;
import com.yihuan.sharecalendar.modle.bean.AdvertisingBean;
import com.yihuan.sharecalendar.modle.bean.active.TimeBean;
import com.yihuan.sharecalendar.modle.bean.settting.ReminderTimeBean;
import com.yihuan.sharecalendar.modle.calendar.ActiveBean;

import org.joda.time.DateTime;

import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import retrofit2.http.Body;

/**
 * Created by Ronny on 2017/12/19.
 * 交互码转换（如性别心情的资源文件）
 */

public class BeanToUtils {

    /**
     * * 1: 男, 2: 女 0 保密
     *
     * @param sex
     * @return
     */
    public static String getSexName(String sex) {
        if (sex == null) {
            return "保密";
        }
        switch (sex) {
            case "1":
                return "男";
            case "2":
                return "女";
            case "0":
            default:
                return "保密";
        }

    }

    /**
     * 1: 男, 2: 女 0 保密
     *
     * @return 根据性别值转换图片id
     */
    public static int getSexResouceId(String sex) {
        if (TextUtils.isEmpty(sex))
            return 0;
        switch (sex) {
            case "1":
                return R.mipmap.icon_sex_man;
            case "2":
                return R.mipmap.icon_sex_woman;
            case "0":
                return 0;
        }
        return 0;
    }

    /**
     * 1: 极差 , 2: 不太好 3: 一般 4: 不错 , 5: 极好
     *
     * @return 根据心情值获取图片id todo 大图标
     */
    public static int getMoodBigResouceId(String currentMood) {
        if (TextUtils.isEmpty(currentMood))
            return R.mipmap.icon_smile_face_0_xxx;
        switch (currentMood) {
            case "1":
                return R.mipmap.icon_smile_face_4_xxx;
            case "2":
                return R.mipmap.icon_smile_face_3_xxx;
            case "3":
                return R.mipmap.icon_smile_face_2_xxx;
            case "4":
                return R.mipmap.icon_smile_face_1_xxx;
            case "5":
            default:
                return R.mipmap.icon_smile_face_0_xxx;
        }
    }

    /**
     * 1: 极差 , 2: 不太好 3: 一般 4: 不错 , 5: 极好
     *
     * @return 根据心情值获取图片id todo 中图标
     */
    public static int getMoodMResouceId(String currentMood) {
        if (TextUtils.isEmpty(currentMood))
            return R.mipmap.mood_img_0_xx;
        switch (currentMood) {
            case "1":
                return R.mipmap.mood_img_4_xx;
            case "2":
                return R.mipmap.mood_img_3_xx;
            case "3":
                return R.mipmap.mood_img_2_xx;
            case "4":
                return R.mipmap.mood_img_1_xx;
            case "5":
                return R.mipmap.mood_img_0_xx;
            default:
                return R.mipmap.mood_img_0_xx;
        }
    }

    /**
     * 1: 极差 , 2: 不太好 3: 一般 4: 不错 , 5: 极好
     *
     * @return 根据心情值获取图片id todo 小图标
     */
    public static int getMoodResouceId(String currentMood) {
        if (TextUtils.isEmpty(currentMood))
            return R.mipmap.mood_img_0_x;
        switch (currentMood) {
            case "1":
                return R.mipmap.mood_img_4_x;
            case "2":
                return R.mipmap.mood_img_3_x;
            case "3":
                return R.mipmap.mood_img_2_x;
            case "4":
                return R.mipmap.mood_img_1_x;
            case "5":
                return R.mipmap.mood_img_0_x;
            default:
                return R.mipmap.mood_img_0_x;
        }
    }

    /**
     * 1: 极差 , 2: 不太好 3: 一般 4: 不错 , 5: 极好
     *
     * @return 根据心情值获取图片id todo 最小图标
     */
    public static int getMoodMinResouceId(String currentMood) {
        if (TextUtils.isEmpty(currentMood))
            return R.mipmap.mood_img_0;
        switch (currentMood) {
            case "1":
                return R.mipmap.mood_img_4;
            case "2":
                return R.mipmap.mood_img_3;
            case "3":
                return R.mipmap.mood_img_2;
            case "4":
                return R.mipmap.mood_img_1;
            case "5":
                return R.mipmap.mood_img_0;
            default:
                return R.mipmap.mood_img_0;
        }
    }

    /**
     * 1: 极差 , 2: 不太好 3: 一般 4: 不错 , 5: 极好
     *
     * @param currentMood
     * @return
     */
    public static String getMoodStr(String currentMood) {
        if (TextUtils.isEmpty(currentMood)) return "";
        switch (currentMood) {
            case "1":
                return "极差";
            case "2":
                return "不太好";
            case "3":
                return "一般";
            case "4":
                return "不错";
            case "5":
                return "很好";
            default:
                return "";
        }
    }

    /**
     * 当type为2（邀请信息）的时候，这个字段属性才有值
     * -1: 拒绝活动、1: 接受活动、0: 未处理、2: 活动已取消
     * 消息中心邀请状态
     */
    public static String getInviteStatusStr(String inviteStatus) {
        if (inviteStatus == null) return "";

        switch (inviteStatus) {
            case "-1":
                return "已拒绝";
            case "1":
                return "已接受";
            case "2":
                return "发起者已取消活动";
            case "0":
                return "未处理";
            case "3":
                return "重新申请";
            default:
                return "";
        }
    }

    /**
     * 当type为2（邀请信息）的时候，这个字段属性才有值
     * -1: 拒绝活动、1: 接受活动、0: 未处理、2: 活动已取消 3:重新申请
     * 消息中心邀请状态颜色
     */
    public static int getInviteStatusColor(String inviteStatus) {
        if (inviteStatus == null) return 0;

        switch (inviteStatus) {
            case "-1":
                return App.getInstanse().getResources().getColor(R.color.color_red_fc46);
            case "1":
                return App.getInstanse().getResources().getColor(R.color.color_green_85);
            case "2":
                return App.getInstanse().getResources().getColor(R.color.color_text_black_999);
            case "3"://重新申请
            case "0"://等待
                return App.getInstanse().getResources().getColor(R.color.color_yellow_edc900);
            default:
                return 0;
        }
    }

    /**
     * 是否显示消息列表 不再提醒
     * 提醒状态 0 正常 1 不在提醒
     *
     * @param reminderStatus
     * @return
     */
    public static boolean isShowDontRemind(String reminderStatus) {
        if (reminderStatus != null && reminderStatus.equals("1"))
            return false;
        return true;
    }


    /**
     * 是否全天  1：是  、 0：否
     *
     * @param fullday
     * @return
     */
    public static boolean getFullDay(String fullday) {
        if (fullday == null) return false;

        if (fullday.equals("1"))
            return true;
        return false;
    }

    /**
     * 签名信息
     *
     * @param signature
     * @return
     */
    public static String getSignature(String signature) {
        if (TextUtils.isEmpty(signature)) {
            return "TA很懒，什么也没有留下......";
        }
        return signature;
    }

    /**
     * 是否是自己id
     *
     * @param userId
     * @return
     */
    public static boolean isMeId(int userId) {
        int myId = App.getInstanse().getUserId();
        return userId == myId;
    }


    /**
     * 任务类型 、 1 系统,  2 用户 3 分享
     *
     * @param type
     * @return
     */
    public static String getRemindTypeFrom(String type) {
        if (TextUtils.isEmpty(type))
            return "";

        switch (type) {
            case "1":
                return "系统提醒";
            case "2":
                return "个人提醒";
            case "3":
                return "分享提醒";
            default:
                return "";
        }

    }

    /**
     * 定制化提醒列表， 显示提醒类型
     * 任务类型 、 1 系统,  2 用户 3 分享
     *
     * @param type
     * @return
     */
    public static int getRemindTypeImg(String type) {
        if (TextUtils.isEmpty(type))
            return R.mipmap.icon_naozhong;
        switch (type) {
            case "3":
                return R.mipmap.logo;
            case "1":
            case "2":
            default:
                return R.mipmap.icon_naozhong;
        }

    }


    /**
     * 定制化提醒列表， 显示提醒时间
     *
     * @param startTime
     * @param endTime
     * @return
     */
    public static String getRemindTime(String startTime, String endTime) {
        if (TextUtils.isEmpty(startTime) && TextUtils.isEmpty(endTime))
            return "时间:永久";
        String start = new TimeBean(startTime).toYMD();
        String end = new TimeBean(endTime).toYMD();
        return "时间:" + StringUtils.nullToStr(start) + "至" + StringUtils.nullToStr(end);
    }

    /**
     * [true,false,false]
     * 定制化提醒，星期转化为字符串，以逗号隔开
     *
     * @param selectedList
     * @return
     */
    public static String getRemindDays(List<Boolean> selectedList) {
        if (selectedList == null) return null;

        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < selectedList.size(); i++) {
            if (selectedList.get(i)) {
                buffer.append(i + 1).append(",");
            }
        }
        if (buffer.length() > 0) {
            buffer.substring(0, buffer.length() - 1);
        }
        return buffer.toString();
    }

    /**
     * {"1","2","3"}
     * 定制化提醒， 字符串转成星期位置
     *
     * @param days
     * @return
     */
    public static List<Integer> parseRemindDays(String days) {
        if (days == null) return null;
        ArrayList<Integer> list = new ArrayList<>();
        try {

            if (days.contains(",")) {
                String[] daysArray = days.split(",");
                for (String s : daysArray) {
                    list.add(Integer.parseInt(s) - 1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }


    /**
     * 定制化提醒，每月转化为字符串，以逗号隔开
     *
     * @param dayList
     * @return
     */
    public static String getRemindMonthDays(List<String> dayList) {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < dayList.size(); i++) {
            String s = dayList.get(i);
            if (!TextUtils.isEmpty(s)) {
                if (i != dayList.size() - 1) {
                    buffer.append(s).append(",");
                } else {
                    buffer.append(s);
                }
            }
        }
        return buffer.toString();
    }

    /**
     * 定制化提醒，字符串转换为每月
     *
     * @param days
     * @return
     */
    public static List<String> parseRemindMonthDays(String days) {
        if (days == null) return null;
        ArrayList<String> list = new ArrayList<>();
        if (days.contains(",")) {
            String[] daysArray = days.split(",");
            for (String s : daysArray) {
                list.add(s);
            }
        }
        return list;
    }

    /**
     * 活动提醒 根据位置取得时间值
     *
     * @param position
     * @return
     */
    public static int getAlarmTime(int position) {
        switch (position) {
            case 0:
                return 0;
            case 1:
                return 5;
            case 2:
                return 10;
            case 3:
                return 15;
            case 4:
                return 30;
            case 5:
                return 60;
            case 6:
                return 60 * 2;
            case 7:
                return 60 * 24;
            case 8:
                return 60 * 24 * 2;
            case 9:
                return 60 * 24 * 7;
            default:
                return 0;
        }
    }

    public static int getAlarmTimePosition(int alarmTime) {
        switch (alarmTime) {
            case 0:
                return 0;
            case 5:
                return 1;
            case 10:
                return 2;
            case 15:
                return 3;
            case 30:
                return 4;
            case 60:
                return 5;
            case 120:
                return 6;
            case 1440:
                return 7;
            case 2880:
                return 8;
            case 10080:
                return 9;
            default:
                return 0;
        }
    }

    public static String getAlarmTimeStr(int position) {
        switch (position) {
            case 0:
                return "准时";
            case 1:
                return "5分钟前";
            case 2:
                return "10分钟前";
            case 3:
                return "15分钟前";
            case 4:
                return "30分钟前";
            case 5:
                return "1小时前";
            case 6:
                return "2小时前";
            case 7:
                return "24小时前";
            case 8:
                return "2天前";
            case 9:
                return "1周前";
            default:
                return "准时";
        }
    }


    /**
     * 将广告详情转化为活动参数
     *
     * @param bean
     * @return
     */
    public static ActiveBean advertisingToActive(AdvertisingBean bean) {
        if (bean == null) return null;

        ActiveBean activeBean = new ActiveBean();
        activeBean.setTitle(bean.getActTitle());
        activeBean.setLocation(bean.getActAddress());
        activeBean.setStart_time(new TimeBean(bean.getActTimeBegin()));
        activeBean.setEnd_time(new TimeBean(bean.getActTimeEnd()));
        //todo 广告图片
        ArrayList<String> imgList = new ArrayList<>();
        List<AdvertisingBean.AdImagesBean> adImages = bean.getAdImages();
        if (adImages != null) {
            for (AdvertisingBean.AdImagesBean b : adImages) {
                if (b != null) {
                    imgList.add(b.getImage());
                }
            }
        }
        activeBean.setImgList(imgList);
        return activeBean;
    }

    /**
     * 去除空，null转为"00"
     *
     * @param reminderTimeBeen
     */
    public static List<ReminderTimeBean> remindTimeNull(List<ReminderTimeBean> reminderTimeBeen) {
        for (ReminderTimeBean b : reminderTimeBeen) {
            if (TextUtils.isEmpty(b.getRemindHour())) {
                b.setRemindHour("00");
            }
            if (TextUtils.isEmpty(b.getRemindMinute())) {
                b.setRemindMinute("00");
            }
        }
        return reminderTimeBeen;
    }


    public static List<String> addImgUrlHeader(List<String> imgList) {
        if (imgList == null) return null;
        ArrayList<String> list = new ArrayList<>();
        for (String s : imgList) {
            list.add(Urls.IMG_HEAD + s);
        }
        return list;
    }
}
