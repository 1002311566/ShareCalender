package com.yihuan.sharecalendar.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.yihuan.sharecalendar.R;
import com.yihuan.sharecalendar.global.App;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Ronny on 2017/10/8.
 * 滑动选择器
 */

public class SelectorView extends View implements View.OnFocusChangeListener{


    public static final float SPEED = 2;//速度=》每次移动的距离
    public static final float MARGIN_ALPHA = 4f;//text之间间距和minTextSize之比

    private Paint mCirclePaint;//圆圈的画笔
    private int mCircleColor = Color.GRAY;
    private int mCircleRedius = 140;//半径


    private Paint mTextPaint;
    private int mTextColor = Color.BLACK;
    private float mMaxTextSize = 80;
    private float mMinTextSize = 40;
    private float mMaxTextAlpha = 255;
    private float mMinTextAlpha = 120;

    private int mWidth;
    private int mHeight;

    private OnSelectListener mSelectListener;
    private Timer timer;
    private MyTimerTask mTask;
    private float mLastDownY;
    private float mMoveLen = 0;//滑动的距离
    private boolean isInit = false;
    private List<Integer> mDataList;
    private String mFillStr = "";
    private int mCurrentSelected;

    Handler updateHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            if (Math.abs(mMoveLen) < SPEED) {
                mMoveLen = 0;
                if (mTask != null) {
                    mTask.cancel();
                    mTask = null;
                    performSelect();
                }
            } else
                // 这里mMoveLen / Math.abs(mMoveLen)是为了保有mMoveLen的正负号，以实现上滚或下滚
                mMoveLen = mMoveLen - mMoveLen / Math.abs(mMoveLen) * SPEED;
            invalidate();
        }

    };


    public SelectorView(Context context) {
        this(context, null);
    }

    public SelectorView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SelectorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.SelectorView);
        int count = ta.getIndexCount();
        for (int i = 0; i < count; i++) {
            switch (ta.getIndex(i)) {
                case R.styleable.SelectorView_redius:
//                    mCircleRedius = ta.getInt(R.styleable.SelectorView_redius, 140);
                    break;
                case R.styleable.SelectorView_circle_color:
                    mCircleColor = ta.getColor(R.styleable.SelectorView_circle_color, Color.BLACK);
                    break;
            }
        }
        ta.recycle();
        init();
    }

    private void init() {
        mCircleColor= App.getInstanse().getResources().getColor(R.color.color_blue_64d5ff);

        timer = new Timer();

        mCirclePaint = new Paint();
        mCirclePaint.setColor(mCircleColor);
        mCirclePaint.setStyle(Paint.Style.STROKE);
        mCirclePaint.setStrokeWidth(5);

        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor(mTextColor);
        mTextPaint.setTextAlign(Paint.Align.CENTER);

        setFocusable(true);
        setFocusableInTouchMode(true);
        setOnFocusChangeListener(this);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();

        //todo 以高度为标准
        mMaxTextSize = mHeight / 7;
        mMinTextSize = mMaxTextSize * 4 / 5;
        mCircleRedius = mHeight / 4;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!isInit) return;

        //画圈
        drawCircle(canvas);
        //画文字
        drawText(canvas);

    }

    private void drawCircle(Canvas canvas) {
        canvas.save();
        //计算圆心坐标
        float ox = (mWidth - getPaddingLeft() - getPaddingRight()) / 2f;
        float oy = (mHeight - getPaddingTop() - getPaddingBottom()) / 2f;
        canvas.drawCircle(ox, oy, mCircleRedius, mCirclePaint);
        canvas.restore();
    }

    private void performSelect() {
        if (mSelectListener != null)
            mSelectListener.onSelect(this, mDataList.get(mCurrentSelected));
    }


    private void drawText(Canvas canvas) {
        canvas.save();
        //画中间文字
        float scale = parabola(mHeight / 4f, mMoveLen);
        float size = (mMaxTextSize - mMinTextSize) * scale + mMinTextSize;
        mTextPaint.setTextSize(size);
        mTextPaint.setAlpha((int) ((mMaxTextAlpha - mMinTextAlpha) * scale + mMinTextAlpha));
        // text居中绘制，注意baseline的计算才能达到居中，y值是text中心坐标
        float x = (float) (mWidth / 2.0);
        float y = (float) (mHeight / 2.0 + mMoveLen);
        Paint.FontMetricsInt fmi = mTextPaint.getFontMetricsInt();
        float baseline = (float) (y - (fmi.bottom / 2.0 + fmi.top / 2.0));
        canvas.drawText(String.format("%02d",mDataList.get(mCurrentSelected))+ mFillStr, x, baseline, mTextPaint);

        //画上面文字
        for (int i = 1; (mCurrentSelected - i) > 0; i++) {
            drawOtherText(canvas, i, -1);
        }

        for (int i = 1; (mCurrentSelected + i) < mDataList.size(); i++) {
            drawOtherText(canvas, i, 1);
        }

        canvas.restore();
    }

    private void drawOtherText(Canvas canvas, int position, int type) {
        float d = (float) (MARGIN_ALPHA * mMinTextSize * position + type
                * mMoveLen);
        float scale = parabola(mHeight / 4.0f, d);
        float size = (mMaxTextSize - mMinTextSize) * scale + mMinTextSize;
        mTextPaint.setTextSize(size);
        mTextPaint.setAlpha((int) ((mMaxTextAlpha - mMinTextAlpha) * scale + mMinTextAlpha));
        float y = (float) (mHeight / 2.0 + type * d);
        Paint.FontMetricsInt fmi = mTextPaint.getFontMetricsInt();
        float baseline = (float) (y - (fmi.bottom / 2.0 + fmi.top / 2.0));
        canvas.drawText(String.format("%02d",mDataList.get(mCurrentSelected + type * position)) + mFillStr,
                (float) (mWidth / 2.0), baseline, mTextPaint);
    }

    /**
     * 抛物线
     *
     * @param zero 原点
     * @param x    偏移量
     * @return
     */
    private float parabola(float zero, float x) {
        float f = (float) (1 - Math.pow(x / zero, 2));
        return f < 0 ? 0 : f;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!isInit) return true;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                doDown(event);
                break;
            case MotionEvent.ACTION_MOVE:
                doMove(event);
                break;
            case MotionEvent.ACTION_UP:
                doUp(event);
                break;
        }
        return true;
    }

    private void doDown(MotionEvent event) {
        if (mTask != null) {
            mTask.cancel();
            mTask = null;
        }
        mLastDownY = event.getY();
    }

    private void doMove(MotionEvent event) {

        mMoveLen += (event.getY() - mLastDownY);

        if (mMoveLen > MARGIN_ALPHA * mMinTextSize / 2) {
            // 往下滑超过离开距离
            moveTailToHead();
            mMoveLen = mMoveLen - MARGIN_ALPHA * mMinTextSize;
        } else if (mMoveLen < -MARGIN_ALPHA * mMinTextSize / 2) {
            // 往上滑超过离开距离
            moveHeadToTail();
            mMoveLen = mMoveLen + MARGIN_ALPHA * mMinTextSize;
        }

        mLastDownY = event.getY();
        invalidate();
    }

    private void doUp(MotionEvent event) {
        // 抬起手后mCurrentSelected的位置由当前位置move到中间选中位置
        if (Math.abs(mMoveLen) < 0.0001) {
            mMoveLen = 0;
            return;
        }
        if (mTask != null) {
            mTask.cancel();
            mTask = null;
        }
        mTask = new MyTimerTask(updateHandler);
        timer.schedule(mTask, 0, 10);
    }

    private void moveHeadToTail() {
        Integer head = mDataList.get(0);
        mDataList.remove(0);
        mDataList.add(head);
    }

    private void moveTailToHead() {
        Integer tail = mDataList.get(mDataList.size() - 1);
        mDataList.remove(mDataList.size() - 1);
        mDataList.add(0, tail);
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if(hasFocus){
            mCircleColor= App.getInstanse().getResources().getColor(R.color.color_blue_007dff);
        }else{
            mCircleColor = Color.GRAY;
        }
    }

    class MyTimerTask extends TimerTask {
        Handler handler;

        public MyTimerTask(Handler handler) {
            this.handler = handler;
        }

        @Override
        public void run() {
            handler.sendMessage(handler.obtainMessage());
        }

    }

    public interface OnSelectListener {
        void onSelect(View view, Integer text);
    }

    //todo ------------------------外部调用方法------------------------------------


    public void setOnSelectListener(OnSelectListener listener) {
        mSelectListener = listener;
    }

    public void setData(List<Integer> data) {
        setData(data, -1);
    }

    public void setData(List<Integer> data, String fillStr) {
        this.mFillStr = fillStr;
        setData(data, -1);
    }

    public void setData(List<Integer> data, int currentSelected, String fillStr) {
        this.mFillStr = fillStr;
        setData(data,currentSelected);
    }

    public void setData(List<Integer> data, int currentSelected) {
        if (data == null || data.size() <= 0) return;

        if (currentSelected == -1) {
            this.mDataList = data;
        } else {
            setCurrrentSelected(data, currentSelected);
        }
        mCurrentSelected = data.size() / 2;
        isInit = true;
        invalidate();
    }

    private void setCurrrentSelected(List<Integer> list, int currentSelected) {
        ArrayList<Integer> datas = new ArrayList<>();
        if (currentSelected >= list.size() / 2) {
            for (int i = currentSelected - list.size() / 2 + list.size() % 2; i < currentSelected + list.size() % 2; i++) {
                datas.add(list.get(i));
            }
            for (int i = currentSelected + list.size() % 2; i < list.size(); i++) {
                datas.add(list.get(i));
            }
            for (int i = 0; i < currentSelected - list.size() / 2 + list.size() % 2; i++) {
                datas.add(list.get(i));
            }
        } else {
            for (int i = currentSelected + list.size() / 2 + list.size() % 2; i < list.size(); i++) {
                datas.add(list.get(i));
            }
            for (int i = 0; i < currentSelected + list.size() % 2; i++) {
                datas.add(list.get(i));
            }
            for (int i = currentSelected + list.size() % 2; i < currentSelected + list.size() / 2 + list.size() % 2; i++) {
                datas.add(list.get(i));
            }
        }

        this.mDataList = datas;
    }

    public Integer getCurrentData(){
        if(mDataList != null && mDataList.size() > mCurrentSelected){
            return mDataList.get(mCurrentSelected);
        }
        return 0;
    }

    public void setmDataList(List<String> list){

    }
}
