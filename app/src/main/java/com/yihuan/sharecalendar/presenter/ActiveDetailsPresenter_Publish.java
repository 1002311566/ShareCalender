package com.yihuan.sharecalendar.presenter;

import android.os.Build;
import android.support.annotation.RequiresApi;

import com.yihuan.sharecalendar.aidl.Alarm;
import com.yihuan.sharecalendar.alarm.AlarmSendBroadcastUtils;
import com.yihuan.sharecalendar.global.base.BasePresenter;
import com.yihuan.sharecalendar.http.Api;
import com.yihuan.sharecalendar.http.help.MyObserver;
import com.yihuan.sharecalendar.modle.bean.active.ActiveDetailsBean;
import com.yihuan.sharecalendar.modle.bean.active.InviteFriendBean;
import com.yihuan.sharecalendar.modle.bean.friend.FriendListBean;
import com.yihuan.sharecalendar.modle.calendar.ActiveBean;
import com.yihuan.sharecalendar.modle.db.ScheduleManager;
import com.yihuan.sharecalendar.presenter.contract.ActiveDetailsContract_Publish;
import com.yihuan.sharecalendar.ui.activity.active.ActiveDetailsActivity_Publish;

import java.util.List;

/**
 * Created by Ronny on 2017/11/7.
 */

public class ActiveDetailsPresenter_Publish extends BasePresenter<ActiveDetailsActivity_Publish> implements ActiveDetailsContract_Publish.Presenter {


    public ActiveDetailsPresenter_Publish(ActiveDetailsActivity_Publish activeDetailsActivityPublish) {
        super(activeDetailsActivityPublish);
    }

    /**
     * 获取活动详情
     *
     * @param activityId
     */
    @Override
    public void getActiveDetails(int activityId) {
        if (activityId == -1) return;

        Api.getActiveDetails(activityId, bind(new MyObserver<ActiveDetailsBean>() {
            @Override
            protected void onFailure(int code, String msg) {
                mView.showToast(msg);
            }

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            protected void onSucceed(ActiveDetailsBean activeDetailsBean) {
                mView.onGetActiveDetailsOK(activeDetailsBean);
            }
        }));
    }

    /**
     * 删除活动
     */
    @Override
    public void deleteSchedule(final ActiveBean activeBean) {
        if (activeBean.getActive_id() == -1) return;

        mView.showLoaddingView(true);
        Api.deleteActive(activeBean.getActive_id(), bind(new MyObserver<Object>() {
            @Override
            protected void onFailure(int code, String msg) {
                mView.showLoaddingView(false);
                mView.showToast(msg);
            }

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            protected void onSucceed(Object o) {
                mView.showLoaddingView(false);
                //todo 删除数据库活动
                ScheduleManager.getInstance().deleteSchdule(activeBean);
                //todo 取消闹钟
                AlarmSendBroadcastUtils.sendDeleteActiveAlarm(mView, new Alarm(activeBean));
                mView.showToast("删除成功");
                mView.onDeleteScheduleOK();
            }
        }));
    }

    /**
     * 随意铃
     */
    @Override
    public void resendBell(int activityId) {
        if (activityId == -1) return;

        mView.showLoaddingView(true);
        Api.resendBell(activityId, bind(new MyObserver<Object>() {
            @Override
            protected void onFailure(int code, String msg) {
                mView.showLoaddingView(false);
                mView.showToast(msg);
            }

            @Override
            protected void onSucceed(Object o) {
                mView.showLoaddingView(false);
                mView.showToast("发送成功！");
                mView.onResendBellOK();
            }
        }));

    }

    /**
     * 重发消息
     *
     * @param activityId
     */
    @Override
    public void resendMsgAll(int activityId) {
        resendMsg(activityId, null);
    }

    @Override
    public void resendMsg(int activityId, String inviteUser) {
        if (activityId == -1) return;

        Api.resendMsg(activityId, inviteUser, bind(new MyObserver<Object>() {
            @Override
            protected void onFailure(int code, String msg) {
                mView.showToast(msg);
            }

            @Override
            protected void onSucceed(Object o) {
                mView.showToast("消息已发送！");
                mView.onResendMsgOK();
            }
        }));
    }

    /**
     * 在某个活动邀请好友
     *
     * @param activeBean 该活动
     * @param listBeen 新添加的好友
     */
    @Override
    public void addFriendsByActive(final ActiveBean activeBean, List<FriendListBean> listBeen) {
        if(activeBean == null )return;

        Integer id = activeBean.getActive_id();
        if(listBeen == null || id == -1)return;
        if(listBeen.size() <= 0)return;

        InviteFriendBean inviteFriendBean = new InviteFriendBean();
        inviteFriendBean.activeId = id;
        for (FriendListBean bean : listBeen){
            inviteFriendBean.inviteUserIds.add(bean.getFriendId());
        }

        Api.addFriendsByActive(inviteFriendBean, bind(new MyObserver<Object>() {
            @Override
            protected void onFailure(int code, String msg) {
                mView.showToast(msg);
            }

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            protected void onSucceed(Object o) {
                mView.showToast("添加成功");
                mView.onAddFriendsOK();
            }
        }));
    }


    /**
     * 取消某个好友
     * @param activeBean 该活动
     * @param inviteUser 被取消好友的id
     */
    @Override
    public void cancleFriendByActive(final ActiveBean activeBean, int inviteUser) {
        if(activeBean == null)return;
        int activityId = activeBean.getActive_id();
        Api.cancleFriendByActive(activityId, inviteUser, bind(new MyObserver<Object>() {
            @Override
            protected void onFailure(int code, String msg) {
                mView.showToast(msg);
            }

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            protected void onSucceed(Object o) {
                mView.showToast("取消成功");
                mView.onCancleFriendByActiveOK();
            }
        }));
    }

    /**
     * 拒绝重新申请
     * @param inviteId
     */
    @Override
    public void refuseApply(String inviteId) {
        Api.refuseApply(inviteId, bind(new MyObserver<Object>() {
            @Override
            protected void onFailure(int code, String msg) {
                mView.showToast(msg);
            }

            @Override
            protected void onSucceed(Object o) {
                mView.showToast("已拒绝");
                mView.onRefuseApplyOK();
            }
        }));
    }

    /**
     * 同意重新申请
     * @param inviteId
     */
    @Override
    public void agreeApply(String inviteId) {
        Api.agreeApply(inviteId, bind(new MyObserver<Object>() {
            @Override
            protected void onFailure(int code, String msg) {
                mView.showToast(msg);
            }

            @Override
            protected void onSucceed(Object o) {
                mView.showToast("已接受");
                mView.onAgreeApplyOK();
            }
        }));
    }

    /**
     * 获取活动分享id
     * @param activeId
     */
    @Override
    public void get_active_share_id(Integer activeId) {
        if(activeId == null)return;

        mView.showLoaddingView(true);
        Api.get_active_share_id(activeId, bind(new MyObserver<Integer>() {
            @Override
            protected void onFailure(int code, String msg) {
                mView.showToast(msg);
                mView.showLoaddingView(false);
            }

            @Override
            protected void onSucceed(Integer id) {
                mView.showLoaddingView(false);
                mView.get_active_share_id_OK(id);
            }
        }));
    }
}
