package com.yihuan.sharecalendar.ui.activity.setting;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.yihuan.sharecalendar.R;
import com.yihuan.sharecalendar.global.App;
import com.yihuan.sharecalendar.global.Constants;
import com.yihuan.sharecalendar.global.base.BaseActivity;
import com.yihuan.sharecalendar.global.base.BasePresenter;
import com.yihuan.sharecalendar.modle.bean.settting.UserBean;
import com.yihuan.sharecalendar.presenter.SetNewPasswordPresenter;
import com.yihuan.sharecalendar.presenter.contract.SetNewPaswordContract;
import com.yihuan.sharecalendar.ui.activity.login.CompleteActivity;
import com.yihuan.sharecalendar.ui.view.TitleView;
import com.yihuan.sharecalendar.utils.CountDownUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Ronny on 2017/9/7.
 */

public class SetNewPasswordActivity extends BaseActivity<SetNewPasswordPresenter> implements SetNewPaswordContract.View {
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.et_password_comfim)
    EditText etPasswordComfim;
    @BindView(R.id.btn_enter)
    Button btnEnter;
    @BindView(R.id.et_phone_code)
    EditText etPhoneCode;
    @BindView(R.id.tv_get_code)
    TextView tvGetCode;

    private CountDownUtils countDownUtils;

    @Override
    protected BasePresenter setPresenter() {
        return new SetNewPasswordPresenter(this);
    }

    @Override
    protected void initTitleView(TitleView titleView) {
        titleView.setCenterText("设置新密码");
    }

    @Override
    protected void initView() {
        countDownUtils = new CountDownUtils(60000, 1000, tvGetCode);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_set_new_password;
    }

    @Override
    protected void refreshData() {

    }


    @OnClick({R.id.btn_enter,R.id.tv_get_code})
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.tv_get_code:
                getCode();
                break;
            case R.id.btn_enter:
                enter();
                break;
        }
    }

    private void enter() {
        UserBean user = App.getInstanse().getUser();
        if(user != null){
            String code = etPhoneCode.getText().toString().trim();
            String pwd = etPassword.getText().toString().trim();
            String pwd1 = etPasswordComfim.getText().toString().trim();
            mPresenter.setNewPassword(user.getBindPhone(), code, pwd, pwd1);
        }
    }

    private void getCode() {
        UserBean user = App.getInstanse().getUser();
        if(user != null){
            mPresenter.getCellPhoneCode(user.getBindPhone());
        }
    }

    public void startTime() {
        countDownUtils.start();
    }

    public void stopTime() {
        countDownUtils.stop();
    }

    @Override
    public void onSetNewPasswrodOK() {
        Intent intent = new Intent(this, CompleteActivity.class);
        intent.putExtra(Constants.INTENT_COMPELETED_TYPE, Constants.INTENT_COMPELETED_TYPE_SET_NEWPASWORD);
        startActivity(intent);
        finish();
    }

    @Override
    public void onGetCellPhoneCodeOK() {

    }
}
