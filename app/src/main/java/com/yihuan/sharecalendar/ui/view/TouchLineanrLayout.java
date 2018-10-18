package com.yihuan.sharecalendar.ui.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * Created by Ronny on 2018/1/18.
 */

public class TouchLineanrLayout extends LinearLayout {

    private boolean isIntercpter;//是否拦截所有操作

    public TouchLineanrLayout(Context context) {
        super(context);
    }

    public TouchLineanrLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TouchLineanrLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return isIntercpter ? isIntercpter : super.dispatchTouchEvent(ev);
    }

    public void setIntercpter(boolean isIntercpter){
        this.isIntercpter = isIntercpter;
    }
}
