package com.yihuan.sharecalendar.ui.view.popwin;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.yihuan.sharecalendar.R;
import com.yihuan.sharecalendar.modle.bean.active.ShareBean;
import com.yihuan.sharecalendar.modle.bean.active.ShareType;
import com.yihuan.sharecalendar.wxapi.WXShareHelper;

import butterknife.BindView;

/**
 * Created by Ronny on 2017/12/5.
 * 分享
 */

public class ActiveSharePop extends BasePopWindow {

    @BindView(R.id.tv_wechat)
    TextView tvWechat;
    @BindView(R.id.tv_wechat_circle)
    TextView tvWechatCircle;
    @BindView(R.id.tv_qq)
    TextView tvQq;
    @BindView(R.id.tv_sina)
    TextView tvSina;

    private ShareBean shareBean;

    public ActiveSharePop(Context context, View clickView) {
        super(context, clickView, R.layout.pop_active_share);
    }

    @Override
    protected int getWidth() {
        return WindowManager.LayoutParams.MATCH_PARENT;
    }

    @Override
    protected int getHeight() {
        return WindowManager.LayoutParams.WRAP_CONTENT;
    }

    @Override
    protected int getAnimationStyle() {
        return 0;
    }

    @Override
    protected void initPopView(View view) {
        setPopShowBackground();
        setPopDismissBackground();
        TextView tvWecha = (TextView) view.findViewById(R.id.tv_wechat);
        TextView tvWechatCircle = (TextView) view.findViewById(R.id.tv_wechat_circle);
        TextView tvQq = (TextView) view.findViewById(R.id.tv_qq);
        TextView tvSina = (TextView) view.findViewById(R.id.tv_sina);

        tvWecha.setOnClickListener(this);
        tvWechatCircle.setOnClickListener(this);
        tvQq.setOnClickListener(this);
        tvSina.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        dismiss();
        switch (v.getId()) {
            case R.id.tv_wechat://todo 微信
                shareWX(WXShareHelper.TO_FRIEND);
                break;
            case R.id.tv_wechat_circle://todo 朋友圈
                shareWX(WXShareHelper.TO_CIRCLE_FRIEND);
                break;
            case R.id.tv_qq://todo qq
                shareQQ();
                break;
            case R.id.tv_sina://todo 微博
                shareSina();
                break;
        }
    }


    private void shareWX(String toWhere) {
        if(shareBean == null)return;

        shareBean.toWhere = toWhere;
        if(shareBean.type == ShareType.URL){
            WXShareHelper.shareUrl(shareBean.url, shareBean.title, shareBean.des, shareBean.bitmap, shareBean.toWhere);
        }if(shareBean.type == ShareType.TEXT){
            WXShareHelper.shareText(shareBean.text, shareBean.toWhere);
        }
    }

    private void shareQQ() {

    }
    private void shareSina() {

    }

   public void setShareBean(ShareBean shareBean){
       this.shareBean = shareBean;
   }

}
