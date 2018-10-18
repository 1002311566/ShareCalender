package com.yihuan.sharecalendar.presenter;

import com.yihuan.sharecalendar.global.base.BasePresenter;
import com.yihuan.sharecalendar.http.Api;
import com.yihuan.sharecalendar.http.help.MyObserver;
import com.yihuan.sharecalendar.modle.bean.friend.TagListBean;
import com.yihuan.sharecalendar.presenter.contract.TagListContract;
import com.yihuan.sharecalendar.ui.activity.friends.TagListActivity;

import java.util.List;

/**
 * Created by Ronny on 2017/9/28.
 */

public class TagListPresenter extends BasePresenter<TagListActivity> implements TagListContract.Presenter {
    public TagListPresenter(TagListActivity tagListActivity) {
        super(tagListActivity);
    }

    @Override
    public void getTagList() {
        mView.showLoaddingView(true);
        Api.getTagList(bind(new MyObserver<List<TagListBean>>() {
            @Override
            protected void onFailure(int code, String msg) {
                mView.showLoaddingView(false);
                mView.showToast(msg);
            }

            @Override
            protected void onSucceed(List<TagListBean> listBeen) {
                mView.showLoaddingView(false);
                mView.onGetListOK(listBeen);
            }
        }));
    }
}
