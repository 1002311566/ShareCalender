package com.yihuan.sharecalendar.ui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.RadioButton;

/**
 * Created by Ronny on 2018/2/2.
 */

public class RadioButtonNum extends RadioButton {

    private Paint mCyclePaint;
    private int mCycleColor = Color.RED;
    private Paint mTextPaint;
    private int mTextColor = Color.WHITE;
    float width;
    float height;
    float min;

    private int msgCount;

    public RadioButtonNum(Context context) {
        super(context);
        init();
    }

    public RadioButtonNum(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RadioButtonNum(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mCyclePaint = new Paint();
        mCyclePaint.setColor(mCycleColor);
        mCyclePaint.setStyle(Paint.Style.FILL);
        mCyclePaint.setAntiAlias(true);

        mTextPaint = new Paint();
        mTextPaint.setColor(mTextColor);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (msgCount > 0) {
            drawCycle(canvas);
            drawText(canvas);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
        min = Math.min(width, height);
    }

    private void drawText(Canvas canvas) {
        mTextPaint.setTextSize(min / 5);
        String text = "";
        if (msgCount > 99) {
            text = "99+";
            mTextPaint.setTextSize(min / 7);
        } else {
            text = String.valueOf(msgCount);
        }
        canvas.drawText(text, width / 8 * 5, height / 6 + height / 18, mTextPaint);
    }

    private void drawCycle(Canvas canvas) {
        canvas.drawCircle(width / 8 * 5, height / 6, min / 6, mCyclePaint);
    }

    public void setMsgCount(int count) {
        this.msgCount = count;
        invalidate();
    }
}
