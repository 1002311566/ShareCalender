package com.yihuan.sharecalendar.wxapi;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.yihuan.sharecalendar.R;
import com.yihuan.sharecalendar.global.App;
import com.yihuan.sharecalendar.global.Constants;
import com.yihuan.sharecalendar.global.DataUtils;
import com.yihuan.sharecalendar.global.base.BaseActivity;
import com.yihuan.sharecalendar.global.base.BasePresenter;
import com.yihuan.sharecalendar.http.Api;
import com.yihuan.sharecalendar.http.help.MyObserver;
import com.yihuan.sharecalendar.ui.activity.MainActivity;
import com.yihuan.sharecalendar.ui.activity.login.BindPhoneActivity;
import com.yihuan.sharecalendar.ui.view.TitleView;

/**
 * Created by Ronny on 2017/11/25.
 */

public class WXEntryActivity extends BaseActivity implements IWXAPIEventHandler{

    private MyObserver callback;
    private String wxCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.api.handleIntent(getIntent(), this);
    }

    @Override
    protected BasePresenter setPresenter() {
        return null;
    }

    @Override
    protected void initTitleView(TitleView titleView) {
        titleView.setCenterText("微信登录");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_empty;
    }

    @Override
    protected void initView() {
        callback = new CallBack();
        callback.setBind(true);
    }

    @Override
    protected void refreshData() {

    }

    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp baseResp) {
        int errCode = baseResp.errCode;
        if(errCode == BaseResp.ErrCode.ERR_OK){//用户同意
            if(baseResp instanceof SendAuth.Resp){
                SendAuth.Resp resp = (SendAuth.Resp) baseResp;
                if(resp.state != null && resp.state.equals("my_wx_login") && resp.code != null){//todo 这里区分分享和登录
                    this.wxCode = resp.code;
                    showLoaddingView(true);
                    Api.loginByWX(wxCode, callback);
                }
                return;
            }

        }else if(errCode == BaseResp.ErrCode.ERR_AUTH_DENIED){//用户拒绝
        }else if(errCode == BaseResp.ErrCode.ERR_USER_CANCEL) {//用户取消
        }
        finish();
    }


    private class CallBack extends MyObserver<String> {

        @Override
        protected void onFailure(int code, String msg) {
            showToast(msg);
            showLoaddingView(false);
            if(code == 702 || code == 404) {//todo 绑定手机
                BindPhoneActivity.startSelf(WXEntryActivity.this, wxCode, Constants.BIND_WX);
            }
            finish();
        }

        @Override
        protected void onSucceed(String token) {
            showLoaddingView(false);
            DataUtils.saveToken(token);
            startActivity(new Intent(WXEntryActivity.this, MainActivity.class));
            sendHomeRefreshBroadCast();
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        callback.setBind(false);
    }

}
