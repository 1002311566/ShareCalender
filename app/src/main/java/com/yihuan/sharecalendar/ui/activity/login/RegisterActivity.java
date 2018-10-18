package com.yihuan.sharecalendar.ui.activity.login;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.yihuan.sharecalendar.R;
import com.yihuan.sharecalendar.global.DataUtils;
import com.yihuan.sharecalendar.global.base.BaseActivity;
import com.yihuan.sharecalendar.global.base.BasePresenter;
import com.yihuan.sharecalendar.presenter.RegisterPresenter;
import com.yihuan.sharecalendar.presenter.contract.RegisterContract;
import com.yihuan.sharecalendar.ui.view.TitleView;
import com.yihuan.sharecalendar.ui.view.popwin.ProtocolPop;
import com.yihuan.sharecalendar.utils.CountDownUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Ronny on 2017/9/6.
 */

public class RegisterActivity extends BaseActivity<RegisterPresenter> implements RegisterContract.View {

    @BindView(R.id.et_cellphone)
    EditText etCellphone;
    @BindView(R.id.et_phone_code)
    EditText etPhoneCode;
    @BindView(R.id.tv_get_code)
    TextView tvGetCode;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.et_password_comfim)
    EditText etPasswordComfim;
    @BindView(R.id.btn_register)
    Button btnRegister;
    @BindView(R.id.tv_protocol)
    TextView tvProtocol;
    @BindView(R.id.cb_check)
    CheckBox checkBox;

    private CountDownUtils countDownUtils;

    @Override
    protected BasePresenter setPresenter() {
        return new RegisterPresenter(this);
    }

    @Override
    protected void initTitleView(TitleView titleView) {
        titleView.setCenterText("注册");
    }

    @Override
    protected void initView() {
        countDownUtils = new CountDownUtils(60000, 1000, tvGetCode);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    protected void refreshData() {

    }


    @OnClick({R.id.tv_get_code, R.id.btn_register, R.id.tv_protocol})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_get_code:
                 getCode();
                break;
            case R.id.btn_register:
                register();
                break;
            case R.id.tv_protocol://协议
                showProtocolPop(view);
                break;
        }
    }

    private void showProtocolPop(View view) {
        ProtocolPop protocolPop = new ProtocolPop(this, view);
        protocolPop.show();
    }

    private void getCode() {
        String phone = etCellphone.getText().toString().trim();
        mPresenter.getCode(phone);
    }

    public void timeStart(){
        countDownUtils.start();
    }
    public void timeEnd(){
        countDownUtils.stop();
    }

    private void register() {
        String phone = etCellphone.getText().toString().trim();
        String code = etPhoneCode.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String passwrod2 = etPasswordComfim.getText().toString().trim();
        boolean isCheck = checkBox.isChecked();
        mPresenter.register(phone, code, password, passwrod2, isCheck);
    }

    /**
     * 注册成功后
     */
    @Override
    public void registerSuccessed(String token) {
        Intent intent = new Intent(RegisterActivity.this, SetInfoActivity.class);
        //todo add intent params
        DataUtils.saveToken(token);
        startActivity(intent);
        finish();
    }
}
