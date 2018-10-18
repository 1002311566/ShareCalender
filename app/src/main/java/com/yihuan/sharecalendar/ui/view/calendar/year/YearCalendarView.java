package com.yihuan.sharecalendar.ui.view.calendar.year;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

import com.yihuan.sharecalendar.R;

/**
 * Created by Ronny on 2017/12/18.
 */

public class YearCalendarView extends ViewPager {

    private YearAdapter mYearAdapter;
    private OnYearViewPageChangeListener pageListener;

    public YearCalendarView(Context context) {
        this(context, null);
    }

    public YearCalendarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttrs(context, attrs);
        addOnPageChangeListener(mOnPageChangeListener);
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        initYearAdapter(context, context.obtainStyledAttributes(attrs, R.styleable.YearCalendarView));
    }

    private void initYearAdapter(Context context, TypedArray array) {
        mYearAdapter = new YearAdapter(context, array, this);
        setAdapter(mYearAdapter);
        setCurrentItem(mYearAdapter.getCount() / 2, false);
    }

    private OnPageChangeListener mOnPageChangeListener = new OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {
            int currentYear = mYearAdapter.getCurrentYear(getCurrentItem());
            if(pageListener != null){
                pageListener.onPage(currentYear);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    public interface OnYearViewPageChangeListener{
        void onPage(int currentYear);
    }
    public void setOnYearViewPageChangeListener(OnYearViewPageChangeListener listener){
        this.pageListener = listener;
    }
}
