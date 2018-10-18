package com.yihuan.sharecalendar.ui.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yihuan.sharecalendar.R;
import com.yihuan.sharecalendar.utils.BeanToUtils;
import com.yihuan.sharecalendar.utils.UIUtils;

/**
 * Created by Ronny on 2017/9/7.
 */

public class HomeTitleView extends LinearLayout implements View.OnClickListener {

    private boolean isCenterClickable = true;
    private Context context;
    private ImageView iv_home_title_menu;
    private ImageView iv_home_title_msg;
    private TextView tv_home_title_msg_count;
    private FrameLayout fl_home_title_msg;
    private TextView tv_home_title_center_content;
    private ImageView iv_home_title_xinqing;
    private ImageView iv_home_title_search;

    private OnClickListener menuClickListener;
    private OnClickListener leftMsgClickListener;
    private OnClickListener rigthMoodListener;
    private OnClickListener rigthSearchListener;
    private OnClickListener centerClickListener;
    private LinearLayout ll_msg_count;

    public HomeTitleView(Context context) {
        this(context, null);
    }

    public HomeTitleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HomeTitleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    private void init() {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_home_title_view, this, false);
        iv_home_title_menu = (ImageView) view.findViewById(R.id.iv_home_title_menu);
        iv_home_title_msg = (ImageView) view.findViewById(R.id.iv_home_title_msg);
        tv_home_title_msg_count = (TextView) view.findViewById(R.id.tv_home_title_msg_count);
        ll_msg_count = (LinearLayout) view.findViewById(R.id.ll_msg_count);
        fl_home_title_msg = (FrameLayout) view.findViewById(R.id.fl_home_title_msg);
        tv_home_title_center_content = (TextView) view.findViewById(R.id.tv_home_title_name);
        iv_home_title_xinqing = (ImageView) view.findViewById(R.id.iv_home_title_xinqing);
        iv_home_title_search = (ImageView) view.findViewById(R.id.iv_home_title_search);

        int stateBarHight = UIUtils.getStateBarHight(context);
        setPadding(0, stateBarHight, 0, 0);

//        LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//        params.height = 100 * (UIUtils.getScreenWidth(context)/ 750);
        addView(view);

        iv_home_title_menu.setOnClickListener(this);
        fl_home_title_msg.setOnClickListener(this);
        iv_home_title_xinqing.setOnClickListener(this);
        iv_home_title_search.setOnClickListener(this);
        tv_home_title_center_content.setOnClickListener(this);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int stateBarHight = UIUtils.getStateBarHight(context);
        int mode = MeasureSpec.getMode(heightMeasureSpec);
        int size = MeasureSpec.getSize(heightMeasureSpec);
        if (mode == MeasureSpec.EXACTLY) {
            size += stateBarHight;
        }
        int h = MeasureSpec.makeMeasureSpec(size, mode);
        super.onMeasure(widthMeasureSpec, h);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_home_title_menu:
                if (menuClickListener != null) {
                    menuClickListener.onClick(v);
                }
                break;
            case R.id.fl_home_title_msg:
                if (leftMsgClickListener != null) {
                    leftMsgClickListener.onClick(v);
                }
                break;
            case R.id.iv_home_title_xinqing:
                if (rigthMoodListener != null) {
                    rigthMoodListener.onClick(v);
                }
                break;
            case R.id.iv_home_title_search:
                if (rigthSearchListener != null) {
                    rigthSearchListener.onClick(v);
                }
                break;
            case R.id.tv_home_title_name:
                if(centerClickListener != null && isCenterClickable){
                    centerClickListener.onClick(v);
                }
                break;

        }
    }

    //---------------------------

    public void setOnMenuClickListener(OnClickListener listener) {
        this.menuClickListener = listener;
    }


    public void setCenterText(String str) {
        tv_home_title_center_content.setText(str);
    }

    /**
     * 设置左边消息数量
     *
     * @param count
     */
    public void setLeftMsgCount(Integer count) {
        if(count <= 0){
            ll_msg_count.setVisibility(View.GONE);
            return;
        }
        if(count > 99){
            tv_home_title_msg_count.setTextSize(TypedValue.COMPLEX_UNIT_PX,15);
            tv_home_title_msg_count.setText("99+");
        }else{
            tv_home_title_msg_count.setTextSize(TypedValue.COMPLEX_UNIT_PX,20);
            tv_home_title_msg_count.setText(count.toString());
        }
        ll_msg_count.setVisibility(View.VISIBLE);
    }

    public void setMyMoodImg(String currentMood){
        iv_home_title_xinqing.setImageResource(BeanToUtils.getMoodResouceId(currentMood));
    }

    public void setOnLeftMsgClickListener(OnClickListener listener) {
        this.leftMsgClickListener = listener;
    }

    /**
     * 设置心情按钮点击监听
     *
     * @param listener
     */
    public void setOnRightMoodClickListener(OnClickListener listener) {
        this.rigthMoodListener = listener;
    }


    public void setOnRightSearchClickListener(OnClickListener listener) {
        this.rigthSearchListener = listener;
    }

    public void setOnCenterClickListener(OnClickListener listener) {
        this.centerClickListener = listener;
    }

    public void setCenterClickable(boolean isCenterClickable){
        this.isCenterClickable = isCenterClickable;
    }

}
