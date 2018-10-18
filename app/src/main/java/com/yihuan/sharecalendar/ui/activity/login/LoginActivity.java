package com.yihuan.sharecalendar.ui.activity.login;

import android.app.Activity;
import android.content.Intent;
import android.os.SystemClock;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.yihuan.sharecalendar.R;
import com.yihuan.sharecalendar.global.App;
import com.yihuan.sharecalendar.global.base.BaseActivity;
import com.yihuan.sharecalendar.global.base.BasePresenter;
import com.yihuan.sharecalendar.presenter.LoginPresenter;
import com.yihuan.sharecalendar.presenter.contract.LoginContract;
import com.yihuan.sharecalendar.ui.activity.MainActivity;
import com.yihuan.sharecalendar.ui.view.CircleImageView;
import com.yihuan.sharecalendar.ui.view.TitleView;
import com.yihuan.sharecalendar.wxapi.WXEntryActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Ronny on 2017/9/6.
 */

public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginContract.View {
    //
    @BindView(R.id.civ_logo)
    ImageView civLogo;
    @BindView(R.id.et_cellphone)
    EditText etCellphone;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.tv_register)
    TextView tvRegister;
    @BindView(R.id.tv_forget_psd)
    TextView tvForgetPsd;
    @BindView(R.id.iv_wechat)
    ImageView ivWechat;
    @BindView(R.id.iv_sina)
    ImageView ivSina;


    @Override
    protected BasePresenter setPresenter() {
        return new LoginPresenter(this);
    }

    @Override
    protected void initTitleView(TitleView titleView) {
        titleView.setVisibility(View.GONE);
    }

    @Override
    protected void initView() {
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void refreshData() {

    }

    @OnClick({R.id.btn_login, R.id.tv_register, R.id.tv_forget_psd, R.id.iv_wechat, R.id.iv_sina})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                login();
                break;
            case R.id.tv_register:
                toRegisterActivity();
                break;
            case R.id.tv_forget_psd:
                toForgetPasswordActivity();
                break;
            case R.id.iv_wechat:
                toWXLogin();
                break;
            case R.id.iv_sina:
                toSinaLogin();
                break;
        }
    }

    private void toSinaLogin() {
//        BindPhoneActivity.startSelf(LoginActivity.this, "");
    }

    private void toWXLogin() {
        SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "my_wx_login";
        if (App.api.isWXAppInstalled()) {
            App.api.sendReq(req);
        } else {
            showToast("未安装微信客户端！");
        }
    }

    private void login() {
        String phone = etCellphone.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        mPresenter.login(phone, password);
    }

    private void toForgetPasswordActivity() {
        startActivity(new Intent(this, ForgetPasswordActivity.class));
    }

    private void toRegisterActivity() {
        startActivity(new Intent(this, RegisterActivity.class));
    }


    @Override
    public void loginSuccesed() {
        startActivity(new Intent(this, MainActivity.class));
        setResult(RESULT_OK);
        sendHomeRefreshBroadCast();
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //todo 如果按返回键则直接关闭App
            MainActivity mainActivity = App.getInstanse().getMainActivity();
            if (mainActivity != null) {
                mainActivity.finish();
            }
            this.finish();
        }
        return true;
    }
}
