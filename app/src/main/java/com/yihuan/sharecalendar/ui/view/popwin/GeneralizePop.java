package com.yihuan.sharecalendar.ui.view.popwin;

import android.content.Context;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.yihuan.sharecalendar.R;
import com.yihuan.sharecalendar.utils.UIUtils;

/**
 * Created by Ronny on 2017/12/14.
 */

public class GeneralizePop extends BasePopWindow {
    private TextView textView;

    public GeneralizePop(Context context, View clickView) {
        super(context, clickView, R.layout.pop_generalize);
    }

    @Override
    protected int getWidth() {
        return WindowManager.LayoutParams.MATCH_PARENT;
    }

    @Override
    protected int getHeight() {
        return UIUtils.getScreenHeight(mContext)/2;
    }

    @Override
    protected int getAnimationStyle() {
        return R.style.pop_anim_bottom_to_top;
    }

    @Override
    protected void setOutClickClose(View view) {
        //todo 这里空代表不点击外部不消失
    }

    @Override
    protected boolean isOpenGrayBG() {
        return true;
    }

    @Override
    protected void initPopView(View view) {
        textView = (TextView) view.findViewById(R.id.tv_more);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
}
