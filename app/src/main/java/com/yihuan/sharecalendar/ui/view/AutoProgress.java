package com.yihuan.sharecalendar.ui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class AutoProgress extends View {

    private float maxSize = 100;
    private float currentSize;

    private Paint mPaint;
    private Paint mTextPaint;

    private int mWidth, mHeight;
    private int round;
    private int progressColor = Color.GREEN;
    private int outerColor = Color.RED;
    private int textColor = Color.BLACK;
    private int textSize = 50;

    private Context context;

    public AutoProgress(Context context) {
        this(context, null);
    }
    public AutoProgress(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0 );
    }

    public AutoProgress(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
//        context.obtainStyledAttributes(attrs, R.stylea)
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);

        mTextPaint = new Paint();
        mTextPaint.setColor(textColor);
        mTextPaint.setAntiAlias(true);
        mTextPaint.setTextSize(50);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int wMode = MeasureSpec.getMode(widthMeasureSpec);
        int wSize = MeasureSpec.getSize(widthMeasureSpec);
        int hMode = MeasureSpec.getMode(heightMeasureSpec);
        int hSize = MeasureSpec.getSize(heightMeasureSpec);

        if(wMode == MeasureSpec.EXACTLY || wMode == MeasureSpec.AT_MOST){
            mWidth = wSize;
        }else{
            mWidth = MeasureSpec.makeMeasureSpec(wSize, MeasureSpec.AT_MOST);
        }

        if(hMode == MeasureSpec.EXACTLY || hMode == MeasureSpec.AT_MOST){
            mHeight = hSize;
        }else{
            mHeight = 50;
        }
        round = mHeight/2;
        setMeasuredDimension(mWidth, mHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPaint.setColor(outerColor);
        RectF oRf = new RectF(0, 0, mWidth, mHeight);
        canvas.drawRoundRect(oRf, round, round, mPaint);

        mPaint.setColor(progressColor);
        float scale = currentSize / maxSize;
        float w = mWidth * scale;
        RectF iRf = new RectF(0, 0, w, mHeight);
        canvas.drawRoundRect(iRf, round, 0, mPaint);

        String s = String.format("%d", Math.round(scale * 100)) + "%";
        Rect tf = new Rect();
        mTextPaint.getTextBounds(s, 0, s.length(),tf);
        int tx = mWidth/2 - tf.width()/2;
        canvas.drawText(s, tx, mHeight/2+ textSize/2, mTextPaint);
    }

    public void setMaxSize(float maxSize){
        this.maxSize = maxSize;
    }

    public void setCurrentSize(float size){
        this.currentSize = size;
        invalidate();
    }
}
