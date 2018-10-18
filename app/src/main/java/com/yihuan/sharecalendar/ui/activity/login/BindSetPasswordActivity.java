package com.yihuan.sharecalendar.ui.activity.login;

import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;

import com.yihuan.sharecalendar.R;
import com.yihuan.sharecalendar.global.Constants;
import com.yihuan.sharecalendar.global.base.BaseActivity;
import com.yihuan.sharecalendar.global.base.BasePresenter;
import com.yihuan.sharecalendar.presenter.BindSetPasswordPresenter;
import com.yihuan.sharecalendar.presenter.contract.BindSetPasswordContract;
import com.yihuan.sharecalendar.ui.activity.MainActivity;
import com.yihuan.sharecalendar.ui.view.TitleView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Ronny on 2018/1/16.
 * 绑定第三方时，对未注册的手机号进行密码设置
 */

public class BindSetPasswordActivity extends BaseActivity<BindSetPasswordPresenter> implements BindSetPasswordContract.View {

    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.et_password_comfim)
    EditText etPasswordComfim;
    @BindView(R.id.btn_ok)
    Button btnOk;
    private String type;
    private String phone;
    private String code;
    private String phoneCode;

    @Override
    protected BasePresenter setPresenter() {
        return new BindSetPasswordPresenter(this);
    }

    @Override
    protected void initTitleView(TitleView titleView) {
        titleView.setCenterText("绑定手机");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_bindwx_setpassword;
    }

    @Override
    protected void initView() {
        if (getIntent() != null) {
            Intent intent = getIntent();
            type = intent.getStringExtra("type");
            phone = intent.getStringExtra("phone");
            code = intent.getStringExtra("code");
            phoneCode = intent.getStringExtra("phoneCode");
        }
    }

    @Override
    protected void refreshData() {

    }

    /**
     * @param bindPhoneActivity
     * @param type              设置类型 微信 & 微博
     * @param phone             手机号
     * @param code              第三方验证code
     * @param phoneCode         手机验证码
     * @param requestCode1
     */
    public static void startSelf(BindPhoneActivity bindPhoneActivity, String type, String phone, String code, String phoneCode, int requestCode1) {
        Intent intent = new Intent(bindPhoneActivity, BindSetPasswordActivity.class);
        intent.putExtra("type", type);
        intent.putExtra("phone", phone);
        intent.putExtra("code", code);
        intent.putExtra("phoneCode", phoneCode);
        bindPhoneActivity.startActivityForResult(intent,requestCode1);
    }

    @OnClick(R.id.btn_ok)
    public void onViewClicked() {
        String pwd = etPassword.getText().toString().trim();
        String comfimPwd = etPasswordComfim.getText().toString().trim();
        if(type.equals(Constants.BIND_WX)){//todo 微信
            mPresenter.bindWX(phone, pwd, comfimPwd, code, phoneCode);
        }else if(type.equals(Constants.BIND_SINA)){

        }
    }

    @Override
    public void onBindWXOK() {
        startActivity(new Intent(this, MainActivity.class));
        sendHomeRefreshBroadCast();
        setResult(RESULT_OK);
        finish();
    }
}
