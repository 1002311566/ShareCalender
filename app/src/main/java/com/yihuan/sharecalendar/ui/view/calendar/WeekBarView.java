package com.yihuan.sharecalendar.ui.view.calendar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import com.yihuan.sharecalendar.R;
import com.yihuan.sharecalendar.ui.view.calendar.utils.CalendarUtil;

/**
 * Created by Ronny on 2017/9/15.
 */

public class WeekBarView extends View {

    private String[] weekArray = {"日", "一", "二", "三", "四", "五", "六"};
    private int weekSize = 12;//文字尺寸
    private int weekColor = Color.BLACK;//文字颜色
    private int weekColorOther = Color.GRAY;//
    private int defaultHight = 80;//默认高度

    private Paint mPaint;//默认黑色画周一至周五
    private Paint mPaint_gray;//画周六周日灰色
    private Context context;

    public WeekBarView(Context context) {
        this(context, null);
    }

    public WeekBarView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WeekBarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initAttrs(attrs);
        initPaint();
//        setBackgroundColor(Color.TRANSPARENT);
    }

    private void initAttrs(AttributeSet attrs) {
        weekArray = getResources().getStringArray(R.array.calendar_week);
        String weekStr = null;
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.WeekBarView);
        for (int i = 0; i < ta.getIndexCount(); i++) {
            int attr = ta.getIndex(i);
            if (attr == R.styleable.WeekBarView_week_color) {
                weekColor = ta.getColor(R.styleable.WeekBarView_week_color, weekColor);//周一至周五颜色
            } else if (attr == R.styleable.WeekBarView_week_color_other) {
                weekColorOther = ta.getColor(R.styleable.WeekBarView_week_color_other, weekColorOther);
            } else if (attr == R.styleable.WeekBarView_week_size) {
                weekSize = ta.getInteger(R.styleable.WeekBarView_week_size, weekSize);
            } else if (attr == R.styleable.WeekBarView_week_str) {
                weekStr = ta.getString(R.styleable.WeekBarView_week_str);
            }
        }
        ta.recycle();
        //星期串以“.”分割
        if (!TextUtils.isEmpty(weekStr)) {
            String[] weeks = weekStr.split("\\.");
            if (weeks.length != 7) {
                return;
            }
            System.arraycopy(weeks, 0, weekArray, 0, 7);
        }

        weekSize = CalendarUtil.getTextSize1(context, weekSize);//文字大小转换
    }


    private void initPaint() {
        mPaint = new Paint();
        mPaint.setColor(weekColor);
        mPaint.setAntiAlias(true);
        mPaint.setTextSize(weekSize);

        mPaint_gray = new Paint();
        mPaint_gray.setColor(weekColorOther);
        mPaint_gray.setAntiAlias(true);
        mPaint_gray.setTextSize(weekSize);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        if (heightMode == MeasureSpec.AT_MOST) {
            heightSize = CalendarUtil.getPxSize(context, 35);
        }
        if (widthMode == MeasureSpec.AT_MOST) {
            widthSize = CalendarUtil.getPxSize(context, 300);
        }
        setMeasuredDimension(widthSize, heightSize);
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        canvas.drawColor(Color.alpha(255));
//        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        super.onDraw(canvas);
//        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        int width = getWidth();
        int height = getHeight();
        int itemWidth = width / 7;

        for (int i = 0; i < weekArray.length; i++) {
            String text = weekArray[i];
            int textWidth = (int) mPaint.measureText(text);
            int startX = itemWidth * i + (itemWidth - textWidth) / 2;
            int startY = (int) (height / 2 - (mPaint.ascent() + mPaint.descent()) / 2);
            if(i == 0 || i == weekArray.length - 1){
                canvas.drawText(text, startX, startY, mPaint_gray);
            }else{
                canvas.drawText(text, startX, startY, mPaint);
            }
        }
    }

}
