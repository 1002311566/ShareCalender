package com.yihuan.sharecalendar.presenter;

import android.text.TextUtils;

import com.yihuan.sharecalendar.global.Constants;
import com.yihuan.sharecalendar.global.base.BasePresenter;
import com.yihuan.sharecalendar.http.Api;
import com.yihuan.sharecalendar.http.help.MyObserver;
import com.yihuan.sharecalendar.modle.bean.settting.ThemeListBean;
import com.yihuan.sharecalendar.presenter.contract.ChangeThemeContract;
import com.yihuan.sharecalendar.ui.activity.setting.ChangeThemeActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ronny on 2017/12/9.
 */

public class ChangeThemePresenter extends BasePresenter<ChangeThemeActivity> implements ChangeThemeContract.Presenter {

    private List<ThemeListBean> mList;

    public ChangeThemePresenter(ChangeThemeActivity changeThemeActivity) {
        super(changeThemeActivity);
        mList = new ArrayList<>();
    }

    @Override
    public void getThemeList(final int type) {
        mView.showLoaddingView(true);
        if (type == Constants.TYPE_REFRESH) {
            resetLastId();
        }

        Api.getThemeList(getLastId(), bind(new MyObserver<List<ThemeListBean>>() {
            @Override
            protected void onFailure(int code, String msg) {
                mView.showLoaddingView(false);
                mView.showToast(msg);
            }

            @Override
            protected void onSucceed(List<ThemeListBean> beanList) {
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
                mView.onGetThemeList(mList);
            }
        }));
    }

    /**
     * 选择皮肤
     *
     * @param skinId
     */
    @Override
    public void selectTheme(String skinId) {
        if (TextUtils.isEmpty(skinId)) return;

        Api.selectTheme(skinId, bind(new MyObserver<Object>() {
            @Override
            protected void onFailure(int code, String msg) {
                mView.showToast(msg);
            }

            @Override
            protected void onSucceed(Object o) {
                mView.showToast("保存成功！");
                mView.onSelectThemeOK();
            }
        }));
    }

    /**
     * 上传皮肤
     *
     * @param filePath
     */
    @Override
    public void uploadTheme(String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            mView.showToast("请选择图片");
            return;
        }

        mView.showLoaddingView(true, "上传中...");
        Api.uploadTheme(new File(filePath), bind(new MyObserver() {
            @Override
            protected void onFailure(int code, String msg) {
                mView.showLoaddingView(false);
                mView.showToast(msg);
            }

            @Override
            protected void onSucceed(Object o) {
                mView.showLoaddingView(false);
                mView.onUploadThemeOK();
            }
        }));
    }

    @Override
    public void deleteTheme(int id) {
        mView.showLoaddingView(true,"正在删除...");
        Api.deleteTheme(id, bind(new MyObserver() {
            @Override
            protected void onFailure(int code, String msg) {
                mView.showLoaddingView(false);
                mView.showToast(msg);
            }

            @Override
            protected void onSucceed(Object o) {
                mView.showLoaddingView(false);
                mView.showToast("删除成功");
                mView.onDeleteThemeOK();
            }
        }));
    }
}
