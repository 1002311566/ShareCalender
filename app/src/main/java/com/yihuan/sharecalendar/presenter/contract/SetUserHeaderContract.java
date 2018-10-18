package com.yihuan.sharecalendar.presenter.contract;

import com.yihuan.sharecalendar.global.base.BaseView;
import com.yihuan.sharecalendar.global.base.OtherPresenter;

/**
 * Created by Ronny on 2017/9/7.
 */

public interface SetUserHeaderContract {

    interface View extends BaseView{
        void onUploadOk();
    }

    interface Presenter extends OtherPresenter {
        void uploadHeader(String filePath);
    }

}
