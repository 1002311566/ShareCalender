package com.yihuan.sharecalendar.presenter.contract;

import com.yihuan.sharecalendar.global.base.BaseView;
import com.yihuan.sharecalendar.global.base.OtherPresenter;
import com.yihuan.sharecalendar.modle.bean.AdvertisingBean;
import com.yihuan.sharecalendar.modle.bean.settting.AboutMeBean;

import java.util.List;

/**
 * Created by Ronny on 2017/9/26.
 */

public interface ShareRecommendContract {
    interface View extends BaseView{
        void onGetAllShareRecommend(List<AdvertisingBean> mList);
    }

    interface Presenter extends OtherPresenter {
        void getAllShareRecommend(int type);
    }
}
