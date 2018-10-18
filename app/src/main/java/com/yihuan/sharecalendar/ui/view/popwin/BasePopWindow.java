package com.yihuan.sharecalendar.ui.view.popwin;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.yihuan.sharecalendar.R;
import com.yihuan.sharecalendar.utils.UIUtils;

/**
 * Created by Ronny on 2017/10/2.
 */

public abstract class BasePopWindow implements View.OnClickListener {

    protected Context mContext;
    protected View mClickView;//弹框触发的View
    private int mLayoutId;
    protected PopupWindow popupWindow;
    private int mWidth;
    private int mHeight;

    protected int maxHeight;//最大高度（从呼出View的底部到屏幕底部）
    protected int popY;//呼出View的底部y坐标

    public BasePopWindow(Context context, View clickView, int popLayout) {
        mContext = context;
        mClickView = clickView;
        mLayoutId = popLayout;
        init();
    }

    /**
     * 子类可以直接复制，添加需要的方法
     */
    protected void init() {
        View view = createPop();

        setParams();

        setOutClickClose(view);

        initPopView(view);

        if(isOpenGrayBG()){
            setPopShowBackground();
            setPopDismissBackground();
        }
    }

    protected boolean isOpenGrayBG() {
        return false;
    }

    /**
     * 设置点击其它地方消失
     * @param view
     */
    protected void setOutClickClose(View view) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    /**
     * 设置最大高度
     */
    protected void setMaxHeight() {
        int[] location = new int[2];
        mClickView.getLocationOnScreen(location);
        popY = location[1] + mClickView.getHeight();
        maxHeight = UIUtils.getScreenHeight(mContext) - popY;
        popupWindow.setHeight(maxHeight);
    }

    /**
     * 设置外部点击可用，背景，动画等
     */
    protected void setParams() {
        popupWindow.setOutsideTouchable(false);
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(mContext.getResources().getDrawable(android.R.color.transparent));
        popupWindow.setAnimationStyle(getAnimationStyle() == 0 ? R.style.pop_anim_bottom_to_top : getAnimationStyle());
    }

    protected View createPop() {
        View view = LayoutInflater.from(mContext).inflate(mLayoutId, null);
        mWidth = getWidth() == 0 ? ViewGroup.LayoutParams.WRAP_CONTENT : getWidth();
        mHeight = getHeight() == 0 ? ViewGroup.LayoutParams.WRAP_CONTENT : getHeight();
        popupWindow = new PopupWindow(view, mWidth, mHeight, true);
        return view;
    }

    protected abstract int getWidth();

    protected abstract int getHeight();

    protected abstract int getAnimationStyle();

    protected abstract void initPopView(View view);

    public void showBottomDefault() {
        popupWindow.showAtLocation(mClickView, Gravity.BOTTOM, 0, 0);
    }

    public void dismiss() {
        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
        }
    }

    @Override
    public void onClick(View v) {

    }

    /**
     * 需要灰色背景时添加
     * 消失时还原背景
     */
    protected void setPopDismissBackground(){
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = ((Activity) mContext).getWindow()
                        .getAttributes();
                lp.alpha = 1f;
                ((Activity) mContext).getWindow().setAttributes(lp);
            }
        });
    }

    /**
     * 需要灰色背景时添加
     * 开启时显示灰色透明背景
     */
    protected void setPopShowBackground(){
        WindowManager.LayoutParams lp = ((Activity) mContext).getWindow()
                .getAttributes();
        lp.alpha = 0.7f;
        ((Activity) mContext).getWindow().setAttributes(lp);
    }

}
