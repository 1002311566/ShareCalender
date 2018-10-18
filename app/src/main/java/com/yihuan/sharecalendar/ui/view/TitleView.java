package com.yihuan.sharecalendar.ui.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yihuan.sharecalendar.R;
import com.yihuan.sharecalendar.utils.UIUtils;

/**
 * Created by Ronny on 2017/9/6.
 */

public class TitleView extends LinearLayout implements View.OnClickListener {

    private Context context;
    private View titleView;
    private OnLeftClickListener mLeftListener;
    private OnRightClickListener mRightListener;
    private ImageView iv_left;
    private TextView tv_left;
    private ImageView iv_right;
    private TextView tv_title;
    private TextView tv_right;

    public TitleView(Context context) {
        this(context, null);
    }

    public TitleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TitleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        inti();
    }

    private void inti() {
        titleView = LayoutInflater.from(context).inflate(R.layout.layout_title_view, null);
        int stateBarHight = UIUtils.getStateBarHight(context);
        LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//        params.setMargins(0, stateBarHight, 0, 0);
        addView(titleView, params);

        tv_title = (TextView) titleView.findViewById(R.id.tv_title_name);
        iv_left = (ImageView) titleView.findViewById(R.id.iv_back);
        tv_left = (TextView) titleView.findViewById(R.id.tv_left);
        iv_right = (ImageView) titleView.findViewById(R.id.iv_right);
        tv_right = (TextView) titleView.findViewById(R.id.tv_right);
        iv_left.setOnClickListener(this);
        iv_right.setOnClickListener(this);
        tv_right.setOnClickListener(this);
        tv_left.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:
            case R.id.tv_left:
                if(mLeftListener != null){
                    mLeftListener.onLeftClick();
                }
                break;
            case R.id.tv_title_name:

                break;
            case R.id.iv_right:
            case R.id.tv_right:
                if(mRightListener != null){
                    mRightListener.onRightListener(v);
                }
                break;
        }
    }

    public interface OnLeftClickListener{
        void onLeftClick();
    }
    public interface OnRightClickListener{
        void onRightListener(View v);
    }


    /////////////////////////////////////////

    public void setOnLeftClickListener(OnLeftClickListener listener){
        this.mLeftListener = listener;
    }

    public void setOnRightClickListener(OnRightClickListener listener){
        this.mRightListener = listener;
    }

    public void setCenterText(String title){
        tv_title.setText(title);
    }

    public void setLeftImage(int id){
        tv_left.setVisibility(View.GONE);
        iv_left.setImageResource(id);
        iv_left.setVisibility(View.VISIBLE);
    }

    public void setRightImage(int id){
        iv_right.setImageResource(id);
        iv_right.setVisibility(View.VISIBLE);
    }

    public void setRightText(String str){
        tv_right.setVisibility(View.VISIBLE);
        tv_right.setText(str);
    }

    public void setLeftText(String str){
        iv_left.setVisibility(View.GONE);
        tv_left.setText(str);
        tv_left.setVisibility(View.VISIBLE);
    }
}
