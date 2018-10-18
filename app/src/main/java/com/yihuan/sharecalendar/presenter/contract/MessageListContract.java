package com.yihuan.sharecalendar.presenter.contract;

import com.yihuan.sharecalendar.global.base.BaseView;
import com.yihuan.sharecalendar.global.base.OtherPresenter;
import com.yihuan.sharecalendar.modle.bean.home.MessageBean;

import java.util.List;

/**
 * Created by Ronny on 2017/9/26.
 * 消息列表
 */

public interface MessageListContract {
    interface View extends BaseView{
        void onGetMessageListOK(List<MessageBean> beanList);
        void onDontRemindOK(int pos);
        void onDeleteOK();
        void onGetNewMsgCount(Integer count);
        void onReadMsgOK();
    }

    interface Presenter extends OtherPresenter {

        void getMessageList(int type);
        void dontRemind(int id, int pos);
        void deleteMessage(List<MessageBean> list);
        void getNewMsgCount();
        void readMsg(int id);
    }
}
