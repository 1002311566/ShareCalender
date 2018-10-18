package com.yihuan.sharecalendar.ui.activity.setting;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yihuan.sharecalendar.R;
import com.yihuan.sharecalendar.global.DataUtils;
import com.yihuan.sharecalendar.global.base.BaseActivity;
import com.yihuan.sharecalendar.global.base.BasePresenter;
import com.yihuan.sharecalendar.presenter.SettingPresenter;
import com.yihuan.sharecalendar.presenter.contract.SettingContract;
import com.yihuan.sharecalendar.ui.activity.login.LoginActivity;
import com.yihuan.sharecalendar.ui.view.TitleView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Ronny on 2017/9/7.
 */

public class SettingActivity extends BaseActivity<SettingPresenter> implements SettingContract.View {
    @BindView(R.id.tv_tuiguang)
    TextView tvTuiguang;
    @BindView(R.id.tv_about_me)
    TextView tvAboutMe;
    @BindView(R.id.tv_reset_password)
    TextView tvResetPassword;
    @BindView(R.id.tv_change_theme)
    TextView tvChangeTheme;
    @BindView(R.id.tv_cache_size)
    TextView tvCacheSize;
    @BindView(R.id.ll_clean_cache)
    LinearLayout llCleanCache;
    @BindView(R.id.btn_logout)
    Button btnLogout;

    @Override
    protected BasePresenter setPresenter() {
        return new SettingPresenter(this);
    }

    @Override
    protected void initTitleView(TitleView titleView) {
        titleView.setCenterText("设置");
    }

    @Override
    protected void initView() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void refreshData() {

    }

    @OnClick({R.id.tv_tuiguang, R.id.tv_about_me, R.id.tv_reset_password, R.id.tv_change_theme, R.id.ll_clean_cache, R.id.btn_logout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_tuiguang://todo 分享大使
                toGeneralize();
                break;
            case R.id.tv_about_me://todo 关于我们
                toAboutMe();
                break;
            case R.id.tv_reset_password://todo 修改没密码
                toResetPwd();
                break;
            case R.id.tv_change_theme://todo 更改皮肤
                toChangeTheme();
                break;
            case R.id.ll_clean_cache://todo 清除缓存
                break;
            case R.id.btn_logout:
                logout();
                break;
        }
    }

    private void toChangeTheme() {
        startActivity(new Intent(this, ChangeThemeActivity.class));
    }

    private void toGeneralize() {
        startActivity(new Intent(this, GeneralizeActivity.class));
    }

    private void toResetPwd() {
        startActivity(new Intent(this, SetNewPasswordActivity.class));
    }

    private void toAboutMe() {
        startActivity(new Intent(this, AboutMeActivity.class));
    }

    private void logout() {
        mPresenter.logout();
        //todo 成功后才执行
        DataUtils.clearUserCache();
        startActivity(new Intent(this, LoginActivity.class));
        this.finish();
    }

    @Override
    public void logOutOK() {
        DataUtils.clearUserCache();
        //todo 暂时先移除任务
//        CalendarUtils.getInstance(this).removeAllTask();
    }
}
