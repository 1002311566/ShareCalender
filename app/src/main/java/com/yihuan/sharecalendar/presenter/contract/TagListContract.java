package com.yihuan.sharecalendar.presenter.contract;

import com.yihuan.sharecalendar.global.base.BaseView;
import com.yihuan.sharecalendar.global.base.OtherPresenter;
import com.yihuan.sharecalendar.modle.bean.friend.TagListBean;

import java.util.List;

/**
 * Created by Ronny on 2017/9/26.
 */

public interface TagListContract {
    interface View extends BaseView{
        void onGetListOK(List<TagListBean> listBeen);
    }

    interface Presenter extends OtherPresenter {
        void getTagList();
    }
}
