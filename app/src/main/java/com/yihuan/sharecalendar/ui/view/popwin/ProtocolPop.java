package com.yihuan.sharecalendar.ui.view.popwin;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.yihuan.sharecalendar.R;
import com.yihuan.sharecalendar.http.Api;
import com.yihuan.sharecalendar.http.help.MyObserver;
import com.yihuan.sharecalendar.ui.view.TitleView;
import com.yihuan.sharecalendar.utils.ToastUtil;
import com.yihuan.sharecalendar.utils.UIUtils;

import butterknife.BindView;


/**
 * Created by Ronny on 2017/12/15.
 */

public class ProtocolPop extends BasePopWindow {

    TextView tvContent;
    TitleView titleView;
    private CallBack callBack;

    public ProtocolPop(Context context, View clickView) {
        super(context, clickView, R.layout.pop_register_protocol);
    }

    @Override
    protected int getWidth() {
        return WindowManager.LayoutParams.MATCH_PARENT;
    }

    @Override
    protected int getHeight() {
        return WindowManager.LayoutParams.MATCH_PARENT;
    }

    @Override
    protected int getAnimationStyle() {
        return 0;
    }

    @Override
    protected boolean isOpenGrayBG() {
        return true;
    }

    @Override
    protected void initPopView(View view) {
        tvContent = (TextView) view.findViewById(R.id.tv_content);
        titleView = (TitleView) view.findViewById(R.id.title_view);
        titleView.setCenterText("分享日历App服务条款");
        titleView.setOnLeftClickListener(new TitleView.OnLeftClickListener() {
            @Override
            public void onLeftClick() {
                dismiss();
            }
        });
        callBack = new CallBack();
        callBack.setBind(true);
        Api.getRegisterProtocol(callBack);
    }

    @Override
    public void dismiss() {
        super.dismiss();
        callBack.setBind(false);
    }

    private class CallBack extends MyObserver<String>{

        @Override
        protected void onFailure(int code, String msg) {
            if(mContext != null){
                ToastUtil.showShort(mContext, msg);
            }
        }

        @Override
        protected void onSucceed(String s) {
            tvContent.setText(s);
        }
    }

    public void show(){
        popupWindow.showAtLocation(mClickView, Gravity.CENTER, 0, 0);
    }
}
