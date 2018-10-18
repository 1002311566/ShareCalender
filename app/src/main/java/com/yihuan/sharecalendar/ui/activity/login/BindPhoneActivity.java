package com.yihuan.sharecalendar.ui.activity.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.yihuan.sharecalendar.R;
import com.yihuan.sharecalendar.global.Constants;
import com.yihuan.sharecalendar.global.base.BaseActivity;
import com.yihuan.sharecalendar.global.base.BasePresenter;
import com.yihuan.sharecalendar.modle.bean.login.PhoneBean;
import com.yihuan.sharecalendar.presenter.BindPhonePresenter;
import com.yihuan.sharecalendar.presenter.contract.BindPhoneContract;
import com.yihuan.sharecalendar.ui.activity.MainActivity;
import com.yihuan.sharecalendar.ui.view.CircleImageView;
import com.yihuan.sharecalendar.ui.view.TitleView;
import com.yihuan.sharecalendar.utils.CountDownUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Ronny on 2017/12/13.
 */

public class BindPhoneActivity extends BaseActivity<BindPhonePresenter> implements BindPhoneContract.View {

    @BindView(R.id.iv_header)
    ImageView ivHeader;
    @BindView(R.id.et_cellphone)
    EditText etCellphone;
    @BindView(R.id.et_phone_code)
    EditText etPhoneCode;
    @BindView(R.id.tv_get_code)
    TextView tvGetCode;
    @BindView(R.id.btn_ok)
    Button btnOk;

    private CountDownUtils countDownUtils;

    private String code;
    private String type;//todo 绑定类型 微信 & 微博

    @Override
    protected BasePresenter setPresenter() {
        return new BindPhonePresenter(this);
    }

    @Override
    protected void initTitleView(TitleView titleView) {
        titleView.setCenterText("绑定手机");
    }

    public static void startSelf(Activity activity, String code, String type) {
        Intent intent = new Intent(activity, BindPhoneActivity.class);
        intent.putExtra("code", code);
        intent.putExtra("type", type);
        activity.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_bindphone;
    }

    @Override
    protected void initView() {
        if (getIntent() != null) {
            code = getIntent().getStringExtra("code");
            type = getIntent().getStringExtra("type");
        }
        countDownUtils = new CountDownUtils(60000, 1000, tvGetCode);
    }

    @Override
    protected void refreshData() {

    }

    @OnClick({R.id.tv_get_code, R.id.btn_ok})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_get_code:
                getCode();
                break;
            case R.id.btn_ok://todo 下一步
                bind();
                break;
        }
    }

    public void timeStart(){
        countDownUtils.start();
    }

    public void timeEnd(){
        countDownUtils.stop();
    }

    private void bind() {
        String phone = etCellphone.getText().toString().trim();
//        String phoneCode = etPhoneCode.getText().toString().trim();
//        String pwd = etPassword.getText().toString().trim();
//        String comfimPwd = etPasswordComfim.getText().toString().trim();
//        mPresenter.bind(phone, pwd, comfimPwd, code, phoneCode);
        mPresenter.checkPhone(phone);
    }

    private void getCode() {
        String phone = etCellphone.getText().toString().trim();
        mPresenter.getCode(phone);
    }

    @Override
    public void onGetCode() {
    }

    @Override
    public void onBindWXOK() {
        startActivity(new Intent(this, MainActivity.class));
        sendHomeRefreshBroadCast();
        finish();
    }

    @Override
    public void onCheckPhoneOK(PhoneBean bean) {
        if(bean == null)return;

        String phone = etCellphone.getText().toString().trim();
        String phoneCode = etPhoneCode.getText().toString().trim();

        //todo 根据手机号注册情况进行分类处理
        if(type.equals(Constants.BIND_WX)){//todo 微信
            if(bean.isLocal() && !bean.isWeixin()){
                mPresenter.bindWX(phone, null, null, code, phoneCode);
            }else{
                //todo 去密码设置界面
                BindSetPasswordActivity.startSelf(BindPhoneActivity.this, type, phone, code, phoneCode, Constants.REQUEST_CODE_1);
            }

        }else if(type.equals(Constants.BIND_SINA)){//todo 微博
            if(bean.isLocal() && !bean.isWeibo()){

            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == Constants.REQUEST_CODE_1 && resultCode == RESULT_OK){
            finish();
        }
    }
}
