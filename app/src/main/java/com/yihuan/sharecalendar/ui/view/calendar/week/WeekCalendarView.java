package com.yihuan.sharecalendar.ui.view.calendar.week;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.SparseArray;

import com.yihuan.sharecalendar.R;
import com.yihuan.sharecalendar.ui.view.calendar.CalendarUtils;
import com.yihuan.sharecalendar.ui.view.calendar.OnCalendarClickListener;


public class WeekCalendarView extends ViewPager implements OnWeekClickListener {

    private OnCalendarClickListener mOnCalendarClickListener;
    private WeekAdapter mWeekAdapter;

    public WeekCalendarView(Context context) {
        this(context, null);
    }

    public WeekCalendarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttrs(context, attrs);
        addOnPageChangeListener(mOnPageChangeListener);
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        initWeekAdapter(context, context.obtainStyledAttributes(attrs, R.styleable.WeekCalendarView));
    }

    private void initWeekAdapter(Context context, TypedArray array) {
        mWeekAdapter = new WeekAdapter(context, array, this);
        setAdapter(mWeekAdapter);
        setCurrentItem(mWeekAdapter.getWeekCount() / 2, false);
    }

    @Override
    public void onClickDate(int year, int month, int day) {
        if (mOnCalendarClickListener != null) {
            mOnCalendarClickListener.onClickDate(year, month, day);
        }
    }

    private OnPageChangeListener mOnPageChangeListener = new OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(final int position) {
            WeekView weekView = mWeekAdapter.getViews().get(position);
            if (weekView != null) {
                if (mOnCalendarClickListener != null) {
                    mOnCalendarClickListener.onPageChange(weekView.getSelectYear(), weekView.getSelectMonth(), weekView.getSelectDay());
                }
                weekView.clickThisWeek(weekView.getSelectYear(), weekView.getSelectMonth(), weekView.getSelectDay());
            } else {
                WeekCalendarView.this.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        onPageSelected(position);
                    }
                }, 50);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    /**
     * 设置点击日期监听
     * @param onCalendarClickListener
     */
    public void setOnCalendarClickListener(OnCalendarClickListener onCalendarClickListener) {
        mOnCalendarClickListener = onCalendarClickListener;
    }

    public SparseArray<WeekView> getWeekViews() {
        return mWeekAdapter.getViews();
    }

    public WeekAdapter getWeekAdapter() {
        return mWeekAdapter;
    }

    public WeekView getCurrentWeekView() {
        return getWeekViews().get(getCurrentItem());
    }


    /**
     * 跳转到指定日期
     * @param year
     * @param month
     * @param day
     */
    public void toYMD(int year, int month, int day) {
        WeekView w = getCurrentWeekView();
        if (w == null)return;
        int weeks = CalendarUtils.getWeeksAgo(w.getSelectYear(), w.getSelectMonth(), w.getSelectDay(), year, month, day);
        int position = getCurrentItem() + weeks;
        if (weeks != 0) {
            setCurrentItem(position, false);
        }
        WeekView weekView = getCurrentWeekView();
        if (weekView != null) {
            weekView.setSelectYearMonth(year, month, day);
            weekView.invalidate();
        } else {
            WeekView newWeekView = getWeekAdapter().instanceWeekView(position);
            newWeekView.setSelectYearMonth(year, month, day);
            newWeekView.invalidate();
            setCurrentItem(position);
        }
        onClickDate(year, month, day);
    }
}
