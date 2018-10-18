package com.yihuan.sharecalendar.presenter.contract;

import com.yihuan.sharecalendar.global.base.BaseView;
import com.yihuan.sharecalendar.global.base.OtherPresenter;
import com.yihuan.sharecalendar.modle.bean.settting.ThemeListBean;

import java.util.List;

/**
 * Created by Ronny on 2017/9/26.
 */

public interface ChangeThemeContract {
    interface View extends BaseView{
        void onGetThemeList(List<ThemeListBean> bean);
        void onSelectThemeOK();
        void onUploadThemeOK();
        void onDeleteThemeOK();
    }

    interface Presenter extends OtherPresenter {
        void getThemeList(int typeRefresh);
        void selectTheme(String skinId);//皮肤id
        void uploadTheme(String filePath);
        void deleteTheme(int id);
    }
}
