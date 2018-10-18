package com.yihuan.sharecalendar.ui.activity.setting;

import android.widget.ImageView;
import android.widget.TextView;

import com.yihuan.sharecalendar.R;
import com.yihuan.sharecalendar.global.App;
import com.yihuan.sharecalendar.global.DataUtils;
import com.yihuan.sharecalendar.global.base.BaseActivity;
import com.yihuan.sharecalendar.global.base.BasePresenter;
import com.yihuan.sharecalendar.modle.bean.settting.AboutMeBean;
import com.yihuan.sharecalendar.modle.calendar.ActiveBean;
import com.yihuan.sharecalendar.modle.db.DBDao;
import com.yihuan.sharecalendar.presenter.AboutMePresenter;
import com.yihuan.sharecalendar.presenter.contract.AboutMeContract;
import com.yihuan.sharecalendar.ui.view.TitleView;
import com.yihuan.sharecalendar.utils.StringUtils;
import com.yihuan.sharecalendar.utils.UIUtils;

import java.util.List;

import butterknife.BindView;

/**
 * Created by Ronny on 2017/9/11.
 * 关于我们
 */

public class AboutMeActivity extends BaseActivity<AboutMePresenter> implements AboutMeContract.View {

    @Override
    protected BasePresenter<AboutMeActivity> setPresenter() {
        return new AboutMePresenter(this);
    }

    @Override
    protected void initTitleView(TitleView titleView) {
        titleView.setCenterText("联系我们");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_about_me;
    }

    @Override
    protected void initView() {
        refreshData();
    }

    @Override
    protected void refreshData() {
//        mPresenter.getAboutMe();
    }

    @Override
    public void onGetAboutMeOK(AboutMeBean bean) {
    }
}
