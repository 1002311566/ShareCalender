package com.yihuan.sharecalendar.presenter;

import android.text.TextUtils;

import com.yihuan.sharecalendar.global.base.BasePresenter;
import com.yihuan.sharecalendar.http.Api;
import com.yihuan.sharecalendar.http.help.MyObserver;
import com.yihuan.sharecalendar.modle.bean.friend.FriendListBean;
import com.yihuan.sharecalendar.modle.bean.friend.TagListBean;
import com.yihuan.sharecalendar.modle.db.DBDao;
import com.yihuan.sharecalendar.presenter.contract.SelectContactBySearchContract;
import com.yihuan.sharecalendar.ui.activity.active.SelectContactBySearchActivity;

import java.util.List;

/**
 * Created by Ronny on 2017/10/3.
 */

public class SelectContactBySearchPresenter extends BasePresenter<SelectContactBySearchActivity> implements SelectContactBySearchContract.Presenter {
    public SelectContactBySearchPresenter(SelectContactBySearchActivity selectContactBySearchActivity) {
        super(selectContactBySearchActivity);
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
                mView.onGetTagListOK(listBeen);
            }
        }));
    }

    @Override
    public void searchFriendByNickname(String nickname) {
        if(TextUtils.isEmpty(nickname)){
            return;
        }
        //通过数据库搜索
        List<FriendListBean> listBeen = DBDao.getDao().queryFriendListByNickname(nickname);
        if(mView != null && listBeen != null){
            mView.onSearchFriendByNicknameOK(listBeen);
        }
    }

    /**
     * 搜索标签
     * @param title
     */
    @Override
    public void searchTag(String title) {
        if(TextUtils.isEmpty(title))return;

        Api.searchTag(title,bind( new MyObserver<List<TagListBean>>() {
            @Override
            protected void onFailure(int code, String msg) {
//                mView.showToast(msg);
            }

            @Override
            protected void onSucceed(List<TagListBean> listBeen) {
                mView.onSearchTagOK(listBeen);
            }
        }));
    }
}
