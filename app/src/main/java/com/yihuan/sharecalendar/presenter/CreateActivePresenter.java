package com.yihuan.sharecalendar.presenter;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;

import com.yihuan.sharecalendar.R;
import com.yihuan.sharecalendar.aidl.Alarm;
import com.yihuan.sharecalendar.alarm.AlarmSendBroadcastUtils;
import com.yihuan.sharecalendar.global.App;
import com.yihuan.sharecalendar.global.base.BasePresenter;
import com.yihuan.sharecalendar.http.Api;
import com.yihuan.sharecalendar.http.help.MyObserver;
import com.yihuan.sharecalendar.modle.bean.active.InviteUser;
import com.yihuan.sharecalendar.modle.bean.active.TimeBean;
import com.yihuan.sharecalendar.modle.bean.friend.FriendListBean;
import com.yihuan.sharecalendar.modle.calendar.ActiveBean;
import com.yihuan.sharecalendar.modle.data.TimeUtils;
import com.yihuan.sharecalendar.modle.db.ScheduleManager;
import com.yihuan.sharecalendar.presenter.contract.CreateActiveContract;
import com.yihuan.sharecalendar.ui.activity.active.CreateActiveActivity;
import com.yihuan.sharecalendar.utils.BeanToUtils;
import com.yihuan.sharecalendar.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ronny on 2017/10/8.
 */

public class CreateActivePresenter extends BasePresenter<CreateActiveActivity> implements CreateActiveContract.Presenter {

    public CreateActivePresenter(CreateActiveActivity createActiveActivity) {
        super(createActiveActivity);
    }

    @Override
    public void createActive(final ActiveBean activeBean) {

        if (TextUtils.isEmpty(activeBean.getTitle())) {
            mView.showToast(App.getInstanse().getResources().getString(R.string.active_title_null));
            return;
        }

        mView.showLoaddingView(true);
        ArrayList<InviteUser> activityInviteList = new ArrayList<>();
        List<FriendListBean> share_friend = activeBean.getShare_friend();
        if (share_friend != null && share_friend.size() > 0) {
            for (FriendListBean bean : share_friend) {
                activityInviteList.add(new InviteUser(bean.getFriendId() + ""));
            }
            activeBean.setIs_share_schdule(true);
        } else {
            activeBean.setIs_share_schdule(false);
        }

        //todo create active
        Api.createActive(activeBean, activityInviteList, bind(new MyObserver<Integer>() {
            @Override
            protected void onFailure(int code, String msg) {
                mView.showLoaddingView(false);
                mView.showToast(msg);
            }

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            protected void onSucceed(Integer activeId) {
                mView.showLoaddingView(false);
                mView.showToast("创建成功！");
                activeBean.setActive_id(activeId);
                ScheduleManager.getInstance().addSchdule(activeBean);

//                        AlarmManagerUtil.addRemind(new Alarm(activeBean), mView);
                AlarmSendBroadcastUtils.sendAddAlarm(mView, new Alarm(activeBean));
                mView.onCreateActiveOK();
            }
        }));
    }

    @Override
    public TimeBean getCurrentTimeBean() {
        if (App.getInstanse().getCurrentTime() != null) {
            TimeBean currentTime = App.getInstanse().getCurrentTime();
            //todo 防止该引用被多次传递导致数据错乱，重新再创建一个
            return new TimeBean(currentTime.toTime());
        }
        return TimeUtils.getCurrentTimeBean();
    }

    @Override
    public TimeBean getNextHourTimeBean() {
        return TimeUtils.getNextHourTimeBean(getCurrentTimeBean());
    }

    @Override
    public void editActive(final ActiveBean activeBean) {
        if (activeBean.getActive_id() == -1) return;

        if (activeBean.getTitle() == null) {
            mView.showToast(App.getInstanse().getResources().getString(R.string.active_title_null));
            return;
        }

        mView.showLoaddingView(true);
        ArrayList<InviteUser> activityInviteList = new ArrayList<>();
        List<FriendListBean> share_friend = activeBean.getShare_friend();
        if (share_friend != null && share_friend.size() > 0) {
            for (FriendListBean bean : share_friend) {
                activityInviteList.add(new InviteUser(bean.getFriendId() + ""));
            }
            activeBean.setIs_share_schdule(true);
        } else {
            activeBean.setIs_share_schdule(false);
        }
        //todo create active
        Api.editActive(activeBean, activityInviteList, bind(new MyObserver<Object>() {
                    @Override
                    protected void onFailure(int code, String msg) {
                        mView.showLoaddingView(false);
                        mView.showToast(msg);
                    }

                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    protected void onSucceed(Object o) {
                        mView.showLoaddingView(false);
                        //todo 修改数据库日程
                        ScheduleManager.getInstance().editSchdule(activeBean);
                        //todo 修改闹钟
                        AlarmSendBroadcastUtils.sendUpdateActiveAlarm(mView, new Alarm(activeBean));
                        mView.showToast("修改活动成功！");
                        mView.onCreateActiveOK();
                    }
                }));
    }

    static volatile int count = 0;
    static volatile int errorCount = 0;
    /**
     * 上传图片
     */
    @Override
    public void uploadImg(final List<String> pathList) {
        if (pathList == null) return;

        mView.showLoaddingView(true);
        final ArrayList<String> imgUrlList = new ArrayList<>();
        for (int i = 0; i < pathList.size(); i++) {
            Api.uploadActiveImg(pathList.get(i), bind(new MyObserver<String>() {
                @Override
                protected void onFailure(int code, String msg) {
                    count++;
                    errorCount++;
                    if (count == pathList.size()) {
                        mView.showLoaddingView(false);
                        mView.showToast(errorCount + "张上传失败");
                        mView.onUploadImgOK(imgUrlList);
                        count = 0;
                        errorCount = 0;
                    }
                }

                @Override
                protected void onSucceed(String s) {
                    imgUrlList.add(s);
                    count++;
                    LogUtils.e("---- count=" + count);
                    if (errorCount > 0) {
                       LogUtils.e(errorCount + "个上传失败");
                    }
                    if (count == pathList.size()) {
                        mView.showLoaddingView(false);
                        mView.showToast(errorCount + "张上传失败");
                        mView.onUploadImgOK(imgUrlList);
                        count = 0;
                        errorCount = 0;
                    }
                }
            }));
        }
    }
}
