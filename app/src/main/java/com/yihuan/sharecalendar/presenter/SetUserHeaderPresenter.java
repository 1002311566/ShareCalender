package com.yihuan.sharecalendar.presenter;

import android.text.TextUtils;

import com.yihuan.sharecalendar.global.base.BasePresenter;
import com.yihuan.sharecalendar.http.Api;
import com.yihuan.sharecalendar.http.help.MyObserver;
import com.yihuan.sharecalendar.presenter.contract.SetUserHeaderContract;
import com.yihuan.sharecalendar.ui.activity.setting.SetHeaderActivity;

import java.io.File;

/**
 * Created by Ronny on 2017/9/11.
 * 修改个人信息
 */

public class SetUserHeaderPresenter extends BasePresenter<SetHeaderActivity> implements SetUserHeaderContract.Presenter {
    public SetUserHeaderPresenter(SetHeaderActivity setHeaderActivity) {
        super(setHeaderActivity);
    }

    @Override
    public void uploadHeader(String filePath) {
        if(TextUtils.isEmpty(filePath)){
            mView.showToast("图片未修改");
            return;
        }
        mView.showLoaddingView(true);
        Api.uploadHeadImg(new File(filePath), bind(new MyObserver<Object>() {
            @Override
            protected void onFailure(int code, String msg) {
                mView.showLoaddingView(false);
                mView.showToast(msg);
            }

            @Override
            protected void onSucceed(Object o) {
                mView.showLoaddingView(false);
                mView.showToast("上传成功");
                mView.onUploadOk();
            }
        }));
    }
}
