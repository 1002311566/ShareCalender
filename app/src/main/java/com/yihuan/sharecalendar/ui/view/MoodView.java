package com.yihuan.sharecalendar.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.yihuan.sharecalendar.R;

import java.util.List;

/**
 * Created by Ronny on 2017/9/29.
 * 心情折线图
 */

public class MoodView extends View {
    private Context mContext;

    private Paint mXYPaint;//坐标笔
    private int mXYcolor = Color.BLACK;//坐标笔颜色
    private int mWidth;
    private int mHeight;

    private Paint mLeftTextPaint;//左边文字画笔
    private int mLeftTextColor = Color.BLACK;//左边文字颜色
    private float mLeftTextSize;//左边文字大小
    public static final String[] mLeftTexts = {"5", "4", "3", "2", "1", "0"};

    private Paint mBottomPaint;//左边文字画笔
    private int mBottomTextColor = Color.BLACK;//左边文字颜色
    private float mBottomTextSize;//左边文字大小
    public static final String[] mBottomTexts = {"周一", "周二", "周三", "周四", "周五", "周六", "周日"};

    private Paint mXLinePaint;//左边文字画笔
    private int mXLineColor = Color.BLACK;//左边文字颜色

    private Paint mLinePaint;//折线图
    private int mLineColor = Color.GREEN;

    private Paint mCirclePaint;
    private int mCircleColor = Color.BLUE;

    private List<Integer> mData;

    public MoodView(Context context) {
        this(context, null);
    }

    public MoodView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MoodView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.MoodView);
        int count = ta.getIndexCount();
        for (int i = 0 ; i< count ; i++){
            switch (ta.getIndex(i)){
                case R.styleable.MoodView_xy_color:
                    mXYcolor = ta.getColor(R.styleable.MoodView_xy_color, Color.BLACK);
                    break;
                case R.styleable.MoodView_left_text_color:
                    mLeftTextColor = ta.getColor(R.styleable.MoodView_left_text_color, Color.BLACK);
                    break;
                case R.styleable.MoodView_left_text_size:
                    mLeftTextSize = ta.getDimension(R.styleable.MoodView_left_text_size, 28f);
                    break;
                case R.styleable.MoodView_bottom_text_color:
                    mBottomTextColor = ta.getColor(R.styleable.MoodView_bottom_text_color, Color.BLACK);
                    break;
                case R.styleable.MoodView_bottom_text_size:
                    mBottomTextSize = ta.getDimension(R.styleable.MoodView_bottom_text_size, 28f);
                    break;
                case R.styleable.MoodView_x_line_color:
                    mXLineColor = ta.getColor(R.styleable.MoodView_x_line_color, Color.GRAY);
                    break;
                case R.styleable.MoodView_line_color:
                    mLineColor = ta.getColor(R.styleable.MoodView_line_color, Color.RED);
                    break;
                case R.styleable.MoodView_circle_color:
                    mCircleColor = ta.getColor(R.styleable.MoodView_circle_color, Color.BLUE);
                    break;
            }
        }
        ta.recycle();

        init();

    }

    private void init() {
        mXYPaint  = new Paint();
        mXYPaint.setColor(mXYcolor);
        mXYPaint.setStyle(Paint.Style.STROKE);

        mLeftTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mLeftTextPaint.setColor(mLeftTextColor);
        mLeftTextPaint.setTextSize(mLeftTextSize);

        mBottomPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBottomPaint.setColor(mBottomTextColor);
        mBottomPaint.setTextSize(mBottomTextSize);

        mXLinePaint = new Paint();
        mXLinePaint.setColor(mXLineColor);
        mXLinePaint.setStyle(Paint.Style.STROKE);
        mXLinePaint.setStrokeWidth(1);

        mLinePaint  = new Paint();
        mLinePaint.setColor(mLineColor);
        mLinePaint.setStyle(Paint.Style.STROKE);
        mLinePaint.setStrokeWidth(6);

        mCirclePaint = new Paint();
        mCirclePaint.setColor(mCircleColor);
        mCirclePaint.setStyle(Paint.Style.STROKE);
        mCirclePaint.setStrokeWidth(5);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //当高度为wrap_content时，默认设置为550px, 宽度为占满
        int widthM = MeasureSpec.getMode(widthMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int heightM = MeasureSpec.getMode(heightMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        if(widthM == MeasureSpec.AT_MOST){
            width = MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY);
        }
        if(heightM == MeasureSpec.AT_MOST){
            height = MeasureSpec.makeMeasureSpec((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX,
                    550f, getResources().getDisplayMetrics()), MeasureSpec.EXACTLY);
        }
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawXY(canvas);//画坐标轴
        drawLeftText(canvas);//画左边文字
        drawBottmText(canvas);//画下面的文字
        drawXLine(canvas);
        if(mData != null){
            drawData(canvas);
        }
    }

    private void drawXLine(Canvas canvas) {
        canvas.save();
        PathEffect effects = new DashPathEffect(new float[]{5,5,5,5},1);
        mXLinePaint.setPathEffect(effects);
        mXLinePaint.setAntiAlias(true);

        int lineCount = mLeftTexts.length -1;

        Rect rect = new Rect();
        mLeftTextPaint.getTextBounds(mLeftTexts[0], 0, mLeftTexts[0].length(),rect);
        int textWidth = rect.width();
        int textHeight = rect.height();

        float xStart = mWidth * (1/9f);//同Y坐标轴, 不会变动
        float yStart = mHeight * (2/12f);//变动，同左边字成比例关系
        float xEnd = mWidth;
        float yEnd = yStart;
        float yOff = (mHeight * (8/12f))/lineCount;
        Path path = new Path();
        for (int i = 0; i< lineCount; i++){
            path.moveTo(xStart, yStart);
//            canvas.drawLine(xStart, yStart, xEnd, yEnd, mXLinePaint);
            path.lineTo(xEnd, yStart);
            yStart += yOff;
            yEnd = yStart;
            canvas.drawPath(path, mXLinePaint);
        }

        canvas.restore();
    }

    private void drawBottmText(Canvas canvas) {
        canvas.save();
        Rect rect = new Rect();
        mBottomPaint.getTextBounds(mBottomTexts[0], 0, mBottomTexts[0].length(),rect);
        int textWidth = rect.width();
        int textHeight = rect.height();
        float rectWidth = mWidth * (8/10f);//文字所需矩形区域
        float x = mWidth*(1/9f)+ (rectWidth/mBottomTexts.length - textWidth)/2f;
        float y =mHeight * (11/12f) + textHeight/2 ;
        float xOff =rectWidth/mBottomTexts.length;
        for (int i = 0; i< mBottomTexts.length; i++){
            canvas.drawText(mBottomTexts[i], x, y,mBottomPaint);
            x += xOff;
        }
        canvas.restore();
    }

    private void drawLeftText(Canvas canvas) {
        canvas.save();
        Rect rect = new Rect();
        mLeftTextPaint.getTextBounds(mLeftTexts[0], 0, mLeftTexts[0].length(),rect);
        int textWidth = rect.width();
        int textHeight = rect.height();
        //高度分为12份 ，
        float x = (mWidth * (1/9f) -  textWidth)/2;
        float yOff = mHeight*(10/12f)/(mLeftTexts.length-1) -  textHeight/2;
        for (int i = 0; i< mLeftTexts.length; i++){
            String s = mLeftTexts[mLeftTexts.length-i - 1];
            float y =  mHeight * (2/12f) + (mLeftTexts.length -1 - i)*(mHeight * (8/12f))/(mLeftTexts.length - 1) + textHeight/2;
            canvas.drawText(s,x, y, mLeftTextPaint);
        }
        canvas.restore();
    }

    private void drawXY(Canvas canvas) {
        canvas.save();
        Path path = new Path();
        float left = mWidth * (1/9f);
        float top = mHeight*(1/24f);
        float right = mWidth;
        float bottom = mHeight * (10/12f);
        path.moveTo(left, top);
        path.lineTo(left, bottom);
        path.lineTo(right, bottom);
        canvas.drawPath(path, mXYPaint);
        canvas.restore();
    }

    public void setMoodData(List<Integer> datas){
       this.mData = datas;
        invalidate();
    }

    private void drawData(Canvas canvas) {
        canvas.save();
        Path path = new Path();

// y 的范围： 2 -- 10

        float xOff = mWidth*(8/10f)/mBottomTexts.length;
        for (int i = 0; i< mData.size(); i++){
            if(i == 0){
                float x = mWidth*(1/9f)+ (mWidth * (8/10f)/mBottomTexts.length)/2f;;
                float y =  mHeight * (2/12f) + (mLeftTexts.length -1 - mData.get(0))*(mHeight * (8/12f))/(mLeftTexts.length - 1);
                path.moveTo(x, y);
                canvas.drawCircle(x, y, 10, mCirclePaint);
            }else{

                float x = mWidth*(1/9f)+ (mWidth * (8/10f)/mBottomTexts.length)/2f +xOff* i;
                float y =  mHeight * (2/12f) + (mLeftTexts.length -1 - mData.get(i))*(mHeight * (8/12f))/(mLeftTexts.length - 1);;
                path.lineTo(x, y);
                canvas.drawCircle(x, y, 10, mCirclePaint);
            }

        }
        canvas.drawPath(path, mLinePaint);
        canvas.restore();
    }
}
