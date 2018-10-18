package com.yihuan.sharecalendar.ui.view.calendar.year;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;

import com.yihuan.sharecalendar.ui.view.calendar.month.MonthView;

/**
 * Created by Ronny on 2017/12/18.
 */

public class YearView extends ViewGroup {

    private int mCurrentYear;
    private int mMarginSize = 40;//视图之间间距

    public YearView(Context context, int year) {
        this(context, null, year);
    }

    public YearView(Context context, AttributeSet attrs, int year) {
        this(context, attrs, 0, year);
    }

    public YearView(Context context, AttributeSet attrs, int defStyleAttr, int year) {
        super(context, attrs, defStyleAttr);
        this.mCurrentYear = year;
        initData();
    }

    private void initData() {
        for (int i = 0; i < 12; i++) {
//            LinearLayout ll = new LinearLayout(getContext());
//            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
//            ll.setOrientation(LinearLayout.VERTICAL);
//            ll.addView(new WeekBarView(getContext()),lp);
//            ll.addView(new MonthView(getContext(), mCurrentYear, i),lp);
//            addView(ll);

            addView(new MonthView(getContext(), mCurrentYear, i, true));
        }
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childHight = getMeasuredHeight() / 4;
        int childWidth = getMeasuredWidth() / 3;
        for (int i = 0; i < getChildCount(); i++) {
            int left = childWidth * (i % 3);
            int right = left + childWidth;
            int top = childHight * (i / 3);
            int bottom = top + childHight;
            getChildAt(i).layout(left, top, right - mMarginSize, bottom - mMarginSize);
        }
    }

}
