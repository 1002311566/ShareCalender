package com.yihuan.sharecalendar.presenter;

import com.yihuan.sharecalendar.global.Constants;
import com.yihuan.sharecalendar.global.base.BasePresenter;
import com.yihuan.sharecalendar.http.Api;
import com.yihuan.sharecalendar.http.help.MyObserver;
import com.yihuan.sharecalendar.modle.bean.home.MessageBean;
import com.yihuan.sharecalendar.presenter.contract.MessageListContract;
import com.yihuan.sharecalendar.ui.activity.hometitle.MessageActivity;
import com.yihuan.sharecalendar.ui.fragment.MessageFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ronny on 2017/9/30.
 */

public class MessageListPresenter extends BasePresenter<MessageFragment> implements MessageListContract.Presenter {
    public MessageListPresenter(MessageFragment messageActivity) {
        super(messageActivity);
        mList = new ArrayList<>();
    }

    private List<MessageBean> mList;

    @Override
    public void getMessageList(final int type) {
        mView.showLoaddingView(true);
        if (type == Constants.TYPE_REFRESH) {
            resetLastId();
        }

        Api.getMessageList(getLastId(), bind(new MyObserver<List<MessageBean>>() {
            @Override
            protected void onFailure(int code, String msg) {
                mView.showLoaddingView(false);
                mView.showToast(msg);
            }

            @Override
            protected void onSucceed(List<MessageBean> beanList) {
                mView.showLoaddingView(false);
                if (beanList == null)return;
                if ((beanList != null && beanList.size() <= 0)) {
                    setNoMore();
                }else{
                    setLastId(beanList.get(beanList.size() - 1).getId());
                }
                if (type == Constants.TYPE_REFRESH) {
                    mList.clear();
                    mList.addAll(beanList);
                } else if (type == Constants.TYPE_LOADMORE) {
                    mList.addAll(beanList);
                }
                mView.onGetMessageListOK(mList);
            }
        }));
    }

    /**
     * 不再提醒
     *  @param id
     * @param pos
     */
    @Override
    public void dontRemind(int id, final int pos) {
        Api.dontRemind(id, bind(new MyObserver<Object>() {
            @Override
            protected void onFailure(int code, String msg) {
                mView.showToast(msg);
            }

            @Override
            protected void onSucceed(Object o) {
                mView.onDontRemindOK(pos);
            }
        }));
    }

    /**
     * 删除消息
     * @param list
     */
    @Override
    public void deleteMessage(List<MessageBean> list) {
        if(list == null){
            mView.onDeleteOK();
            return;
        }

        mView.showLoaddingView(true);
        ArrayList<Integer> ids = new ArrayList<>();
        for (MessageBean bean : list){
            if(bean.isSelect){
                ids.add(bean.getId());
            }
        }
        Api.deleteMessage(ids, bind(new MyObserver<Object>() {
            @Override
            protected void onFailure(int code, String msg) {
                mView.showLoaddingView(false);
                mView.showToast(msg);
            }

            @Override
            protected void onSucceed(Object o) {
                mView.showLoaddingView(false);
                mView.onDeleteOK();
                mList.clear();
            }
        }));
    }

    @Override
    public void getNewMsgCount() {
        Api.getNewMsgCount(bind(new MyObserver<Integer>() {
            @Override
            protected void onFailure(int code, String msg) {
                mView.showToast(msg);
            }

            @Override
            protected void onSucceed(Integer count) {
                mView.onGetNewMsgCount(count);
            }
        }));
    }

    @Override
    public void readMsg(int id) {
        Api.readMsg(id, bind(new MyObserver() {
            @Override
            protected void onFailure(int code, String msg) {

            }

            @Override
            protected void onSucceed(Object o) {
                mView.onReadMsgOK();
            }
        }));
    }
}
