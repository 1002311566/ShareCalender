package com.yihuan.sharecalendar.presenter;

import android.text.TextUtils;

import com.yihuan.sharecalendar.global.base.BasePresenter;
import com.yihuan.sharecalendar.modle.bean.friend.FriendListBean;
import com.yihuan.sharecalendar.modle.db.DBDao;
import com.yihuan.sharecalendar.presenter.contract.FriendSearchContract;
import com.yihuan.sharecalendar.ui.activity.friends.FriendSearchActivity;

import java.util.List;

/**
 * Created by Ronny on 2017/9/30.
 */

public class FriendSearchPresenter extends BasePresenter<FriendSearchActivity> implements FriendSearchContract.Presenter {
    public FriendSearchPresenter(FriendSearchActivity friendSearchActivity) {
        super(friendSearchActivity);
    }


    @Override
    public void searchFriendByNickName( String nickName) {
        if(TextUtils.isEmpty(nickName)){
            return;
        }

        List<FriendListBean> listBeen = DBDao.getDao().queryFriendListByNickname(nickName);
        if(listBeen != null && mView != null){
            mView.onSearchFriendByNickNameOK(listBeen);
        }
//        mView.showLoaddingView(true);
//        Api.searchFriendByNickName(nickName, bind(new MyObserver<SearchUserBean>() {
//            @Override
//            protected void onFailure(int code, String msg) {
//                mView.showLoaddingView(false);
//                mView.showToast(msg);
//            }
//
//            @Override
//            protected void onSucceed(SearchUserBean searchUserBean) {
//                mView.showLoaddingView(false);
//                mView.onSearchFriendByNickNameOK(searchUserBean);
//            }
//        }));
    }
}
