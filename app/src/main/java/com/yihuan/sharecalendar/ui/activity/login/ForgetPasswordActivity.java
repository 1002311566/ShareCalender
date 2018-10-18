package com.yihuan.sharecalendar.ui.activity.login;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.yihuan.sharecalendar.R;
import com.yihuan.sharecalendar.global.Constants;
import com.yihuan.sharecalendar.global.base.BaseActivity;
import com.yihuan.sharecalendar.global.base.BasePresenter;
import com.yihuan.sharecalendar.presenter.ForgetPwdPresenter;
import com.yihuan.sharecalendar.presenter.contract.ForgetPwdContract;
import com.yihuan.sharecalendar.ui.view.TitleView;
import com.yihuan.sharecalendar.utils.CountDownUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Ronny on 2017/9/7.
 */

public class ForgetPasswordActivity extends BaseActivity<ForgetPwdPresenter> implements ForgetPwdContract.View {
    @BindView(R.id.et_cellphone)
    EditText etCellphone;
    @BindView(R.id.et_phone_code)
    EditText etPhoneCode;
    @BindView(R.id.tv_get_code)
    TextView tvGetCode;
    @BindView(R.id.btn_next)
    Button btnNext;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.et_password_comfim)
    EditText etPasswordComfim;
    private CountDownUtils countDownUtils;

    @Override
    protected BasePresenter setPresenter() {
        return new ForgetPwdPresenter(this);
    }

    @Override
    protected void initTitleView(TitleView titleView) {
        titleView.setCenterText("忘记密码");
    }

    @Override
    protected void initView() {
        countDownUtils = new CountDownUtils(60000, 1000, tvGetCode);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_forget_password;
    }

    @Override
    protected void refreshData() {

    }


    @OnClick({R.id.tv_get_code, R.id.btn_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_get_code:
                getCode();
                break;
            case R.id.btn_next:
                resetPwd();
                break;
        }
    }

    private void resetPwd() {
        String phone = etCellphone.getText().toString().trim();
        String code = etPhoneCode.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String passwordConfim = etPasswordComfim.getText().toString().trim();
        mPresenter.checkResetPwdCode(phone, code, password, passwordConfim);
    }

    private void getCode() {
        String phone = etCellphone.getText().toString().trim();
        mPresenter.getResetPasswordCode(phone);
    }

    public void startTime() {
        countDownUtils.start();
    }

    public void stopTime() {
        countDownUtils.stop();
    }

    @Override
    public void getCodeOK() {

    }

    @Override
    public void checkCodeOK() {
        Intent intent = new Intent(this, CompleteActivity.class);
        intent.putExtra(Constants.INTENT_COMPELETED_TYPE, Constants.INTENT_COMPELETED_TYPE_SET_NEWPASWORD);
        startActivity(intent);
        finish();
    }
}
