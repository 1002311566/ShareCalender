package com.yihuan.sharecalendar.ui.view.popwin;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yihuan.sharecalendar.R;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Ronny on 2017/10/7.
 */

public class ActiveDetailsPop extends BasePopWindow {

    TextView tvText1;
    TextView tvText2;
    TextView tvCancle;
    private boolean isAgree;

    public ActiveDetailsPop(Context context, View clickView, boolean isAgree) {
        super(context, clickView, R.layout.pop_active_details);
        this.isAgree = isAgree;
    }

    @Override
    protected int getWidth() {
        return ViewGroup.LayoutParams.MATCH_PARENT;
    }

    @Override
    protected int getHeight() {
        return 0;
    }

    @Override
    protected int getAnimationStyle() {
        return R.style.pop_anim_bottom_to_top;
    }

    @Override
    protected void init() {
        super.init();
        setPopShowBackground();
        setPopDismissBackground();
    }

    @Override
    protected void initPopView(View view) {
        tvText1 = (TextView) view.findViewById(R.id.tv_text1);
        tvText2 = (TextView) view.findViewById(R.id.tv_text2);
        tvCancle = (TextView) view.findViewById(R.id.tv_cancle);

        tvText1.setOnClickListener(this);
        tvText2.setOnClickListener(this);
        tvCancle.setOnClickListener(this);
    }

    public void show() {
        if(isAgree){
            tvText1.setText(mContext.getString(R.string.agree_text1));
            tvText2.setText(mContext.getString(R.string.agree_text2));
        }
        showBottomDefault();
    }

    public void onClick(View view) {
        if(listener != null){
            switch (view.getId()) {
                case R.id.tv_text1:
                    listener.onClick(0);
                    break;
                case R.id.tv_text2:
                    listener.onClick(1);
                    break;
                case R.id.tv_cancle:
                    break;
            }
            dismiss();
        }
    }

    private OnClickListener listener;
    public interface OnClickListener{
        void onClick(int position);
    }
    public void setOnClickListener(OnClickListener listener){
        this.listener = listener;
    }
}
