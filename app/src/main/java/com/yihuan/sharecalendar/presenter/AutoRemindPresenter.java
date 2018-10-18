package com.yihuan.sharecalendar.presenter;

import com.yihuan.sharecalendar.alarm.AlarmSendBroadcastUtils;
import com.yihuan.sharecalendar.global.App;
import com.yihuan.sharecalendar.global.Constants;
import com.yihuan.sharecalendar.global.base.BasePresenter;
import com.yihuan.sharecalendar.http.Api;
import com.yihuan.sharecalendar.http.help.MyObserver;
import com.yihuan.sharecalendar.modle.bean.settting.AutoRemindListBean;
import com.yihuan.sharecalendar.presenter.contract.AutoRemindContract;
import com.yihuan.sharecalendar.ui.activity.setting.AutoRemindActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ronny on 2017/9/29.
 */

public class AutoRemindPresenter extends BasePresenter<AutoRemindActivity> implements AutoRemindContract.Presenter{
    private List<AutoRemindListBean> mList;
    public AutoRemindPresenter(AutoRemindActivity autoRemindActivity) {
        super(autoRemindActivity);
        mList = new ArrayList<>();
    }

    @Override
    public void getAutoRemindList(final int type) {
        mView.showLoaddingView(true);
        if (type == Constants.TYPE_REFRESH) {
            resetLastId();
        }

        Api.getAutoRemindList(getLastId(), bind(new MyObserver<List<AutoRemindListBean>>() {
            @Override
            protected void onFailure(int code, String msg) {
                mView.showLoaddingView(false);
                mView.showToast(msg);
            }

            @Override
            protected void onSucceed(List<AutoRemindListBean> beanList) {
                mView.showLoaddingView(false);
                if (beanList == null || (beanList != null && beanList.size() <= 0)) {
                    setNoMore();
                    return;
                }

                setLastId(beanList.get(beanList.size() - 1).getId());
                if (type == Constants.TYPE_REFRESH) {
                    mList.clear();
                    mList.addAll(beanList);
                } else if (type == Constants.TYPE_LOADMORE) {
                    mList.addAll(beanList);
                }
                mView.onGetAutoRemindListOK(mList);
            }

        }));
    }

    @Override
    public void deleteRemind(List<AutoRemindListBean> list) {
        if(list == null || (list != null && list.size() <=0)){
            mView.showToast("请先选择要删除的提醒");
            return;
        }

        final ArrayList<Integer> idList = new ArrayList<>();
        for (AutoRemindListBean b : list){
            if(b.isSelect){
                idList.add(b.getId());
            }
        }
        Api.deleteRemind(idList, bind(new MyObserver<Object>() {
            @Override
            protected void onFailure(int code, String msg) {
                mView.showToast(msg);
                mView.showLoaddingView(false);
            }

            @Override
            protected void onSucceed(Object o) {
                mView.showToast("删除成功");
                mView.showLoaddingView(false);
                //todo 删除闹钟
                int[] ids = new int[idList.size()];
                for (int i = 0; i < ids.length; i++){
                    ids[i] = idList.get(i);
                }
                AlarmSendBroadcastUtils.sendDeleteAlarms(App.getInstanse(), ids);
                mView.onDeleteRemindOK();
            }

        }));

    }
}
