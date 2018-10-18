package com.yihuan.sharecalendar.presenter;

import android.text.TextUtils;

import com.yihuan.sharecalendar.R;
import com.yihuan.sharecalendar.global.App;
import com.yihuan.sharecalendar.global.base.BasePresenter;
import com.yihuan.sharecalendar.http.Api;
import com.yihuan.sharecalendar.http.help.MyObserver;
import com.yihuan.sharecalendar.presenter.contract.MessageBoardContract;
import com.yihuan.sharecalendar.ui.activity.setting.MessageBoardActivity;

/**
 * Created by Ronny on 2017/9/22.
 */

public class MessageBoardPresenter extends BasePresenter<MessageBoardActivity> implements MessageBoardContract.Presenter {
    public MessageBoardPresenter(MessageBoardActivity messageBoardActivity) {
        super(messageBoardActivity);
    }

    @Override
    public void commit(String type, String content) {
        if(TextUtils.isEmpty(content)){
            mView.showToast(App.getInstanse().getString(R.string.message_board));
            return;
        }

        mView.showLoaddingView(true);
        Api.messageBoard(type, content, bind(new MyObserver() {
            @Override
            protected void onFailure(int code, String msg) {
                mView.showToast(msg);
                mView.showLoaddingView(false);
            }

            @Override
            protected void onSucceed(Object o) {
                mView.showToast("提交成功");
                mView.showLoaddingView(false);
                mView.commitOK();
            }
        }));


    }
}
