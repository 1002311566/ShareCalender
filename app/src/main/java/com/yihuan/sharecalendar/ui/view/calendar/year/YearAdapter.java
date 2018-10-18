package com.yihuan.sharecalendar.ui.view.calendar.year;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.PagerAdapter;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import com.yihuan.sharecalendar.R;
import com.yihuan.sharecalendar.ui.view.calendar.week.WeekCalendarView;
import com.yihuan.sharecalendar.ui.view.calendar.week.WeekView;

import org.joda.time.DateTime;

/**
 * Created by Ronny on 2017/12/18.
 */

public class YearAdapter extends PagerAdapter {

    private SparseArray<YearView> mViews;
    private Context mContext;
    private TypedArray mArray;
    private YearCalendarView mYearCalendarView;
    private DateTime mStartDate;
    private int mCount = 200;

    public YearAdapter(Context context, TypedArray array, YearCalendarView yearCalendarView) {
        mContext = context;
        mArray = array;
        mYearCalendarView = yearCalendarView;
        mViews = new SparseArray<>();
        mCount = array.getInteger(R.styleable.WeekCalendarView_week_count, 200);
    }

    @Override
    public int getCount() {
        return mCount;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        int year = getYearData(position);
        YearView yearView = mViews.get(year);
        if(yearView == null){
            yearView = new YearView(mYearCalendarView.getContext(), year);
            mViews.put(position, yearView);
        }
        container.addView(mViews.get(position));
        return mViews.get(position);
    }

    private int getYearData(int position) {
        DateTime dateTime = new DateTime();
        int year = dateTime.getYear();
        return year+(position - mCount/2);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    public SparseArray<YearView> getViews() {
        return mViews;
    }

    public int getCurrentYear(int position){
        return getYearData(position);
    }
}
