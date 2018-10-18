package com.yihuan.sharecalendar.presenter;

import android.text.TextUtils;

import com.yihuan.sharecalendar.R;
import com.yihuan.sharecalendar.global.App;
import com.yihuan.sharecalendar.global.base.BasePresenter;
import com.yihuan.sharecalendar.http.Api;
import com.yihuan.sharecalendar.http.help.MyObserver;
import com.yihuan.sharecalendar.modle.bean.friend.SearchUserBean;
import com.yihuan.sharecalendar.presenter.contract.UserSearchContract;
import com.yihuan.sharecalendar.ui.activity.friends.UserSearchActivity;
import com.yihuan.sharecalendar.utils.CheckUtils;

/**
 * Created by Ronny on 2017/9/26.
 */

public class UserSearchPresenter extends BasePresenter<UserSearchActivity> implements UserSearchContract.Presenter {
    public UserSearchPresenter(UserSearchActivity userSearchActivity) {
        super(userSearchActivity);
    }

    @Override
    public void searchFriendByPhone(String bindPhone) {
        if(!TextUtils.isEmpty(bindPhone) && bindPhone.length() != 11){
            return;
        }
        if(!CheckUtils.checkPhone(bindPhone)){
            mView.showToast(App.getInstanse().getString(R.string.phone_num_error));
            return;
        }
        mView.showLoaddingView(true);
        Api.searchAccountByPhone(bindPhone, bind(new MyObserver<SearchUserBean>() {
            @Override
            protected void onFailure(int code, String msg) {
                mView.showLoaddingView(false);
                if(code == 204){
                    mView.onSearchNull();
                }else{
                    mView.showToast(msg);
                }
            }

            @Override
            protected void onSucceed(SearchUserBean searchAccountListBeen) {
                mView.showLoaddingView(false);
                mView.onSearchFriendByPhoneOK(searchAccountListBeen);
            }
        }));

    }
}
