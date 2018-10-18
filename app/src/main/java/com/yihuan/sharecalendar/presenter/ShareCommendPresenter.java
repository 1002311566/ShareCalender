package com.yihuan.sharecalendar.presenter;

import com.yihuan.sharecalendar.global.Constants;
import com.yihuan.sharecalendar.global.base.BasePresenter;
import com.yihuan.sharecalendar.http.Api;
import com.yihuan.sharecalendar.http.help.MyObserver;
import com.yihuan.sharecalendar.modle.bean.AdvertisingBean;
import com.yihuan.sharecalendar.modle.bean.home.MessageBean;
import com.yihuan.sharecalendar.modle.bean.settting.AboutMeBean;
import com.yihuan.sharecalendar.presenter.contract.AboutMeContract;
import com.yihuan.sharecalendar.presenter.contract.ShareRecommendContract;
import com.yihuan.sharecalendar.ui.activity.setting.AboutMeActivity;
import com.yihuan.sharecalendar.ui.activity.setting.ShareRecommendActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ronny on 2017/9/30.
 */

public class ShareCommendPresenter extends BasePresenter<ShareRecommendActivity> implements ShareRecommendContract.Presenter {


    public ShareCommendPresenter(ShareRecommendActivity shareRecommendActivity) {
        super(shareRecommendActivity);
        mList = new ArrayList<AdvertisingBean>();
    }

    private List<AdvertisingBean> mList;
    @Override
    public void getAllShareRecommend(final int type) {
        mView.showLoaddingView(true);
        if (type == Constants.TYPE_REFRESH) {
            resetLastId();
        }

        Api.getAdvertising(null, Constants.PAGE_SIZE, getLastId(), bind(new MyObserver<List<AdvertisingBean>>() {
            @Override
            protected void onFailure(int code, String msg) {
                mView.showLoaddingView(false);
                mView.showToast(msg);
            }

            @Override
            protected void onSucceed(List<AdvertisingBean> beanList) {
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
                mView.onGetAllShareRecommend(mList);
            }
        }));
    }
}
