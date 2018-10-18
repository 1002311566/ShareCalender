package com.yihuan.sharecalendar.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.yihuan.sharecalendar.R;
import com.yihuan.sharecalendar.utils.LogUtils;
import com.yihuan.sharecalendar.utils.UIUtils;

import java.lang.reflect.TypeVariable;

/**
 * Created by Ronny on 2017/9/7.
 */

public class SlidingMenu extends HorizontalScrollView {

    /**
     * 屏幕宽度
     */
    private int mScreenWidth;
    /**
     * dp
     */
    private int mMenuRightPadding;
    /**
     * 菜单的宽度
     */
    private int mMenuWidth;

    private boolean once;
    private boolean isOpen;
    private int scaledTouchSlop;//系统识别的滑动最小距离
    private ViewGroup mContentView;
    private View viewUp;//灰色背景
    private DisplayMetrics mDisplayMetrics;
    private GestureDetector mGestureDetector;

    public SlidingMenu(Context context) {
        this(context, null);
    }

    public SlidingMenu(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlidingMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mScreenWidth = UIUtils.getScreenWidth(context);

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.SlidingMenu, defStyleAttr, 0);
        mMenuRightPadding = (int) a.getInteger(R.styleable.SlidingMenu_rightPadding, 50);
        a.recycle();
        mDisplayMetrics = getResources().getDisplayMetrics();
        scaledTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();

        mGestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                LogUtils.e("-------------- velocityX-- " + velocityX + "------- ----- ----- ---");
                if(velocityX > 0){
                    openMenu();
                }else{
                    closeMenu();
                }
                return true;
            }

        });
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        /**
         * 显示的设置一个宽度
         */
        if (!once) {
            //获取整体布局
            LinearLayout wrapper = (LinearLayout) getChildAt(0);

            //从整体布局中获取第一个子View，即菜单布局
            ViewGroup menu = (ViewGroup) wrapper.getChildAt(0);
            //主要布局
            mContentView = (ViewGroup) wrapper.getChildAt(1);

//            mMenuWidth = (int) (mScreenWidth - mMenuRightPadding * mDisplayMetrics.density);
//            mHalfMenuWidth = mMenuWidth / 2;//菜单布局的一半
            mMenuWidth = UIUtils.getScreenWidth(getContext())*4/5;
            menu.getLayoutParams().width = mMenuWidth;
            mContentView.getLayoutParams().width = mScreenWidth;

        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (changed) {
            // 将菜单隐藏
            this.scrollTo(mMenuWidth, 0);
            once = true;
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        viewUp = this.findViewById(R.id.view_up);
        viewUp.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                closeMenu();
            }
        });
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    float ox, oy;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startRawX = (int) ev.getRawX();
                //todo 如果按下位置在从屏幕左边到两个单位内则拦截事件 || 菜单栏是打开状态
                if (Math.abs(startRawX) <= scaledTouchSlop)
                    return true;
            case MotionEvent.ACTION_MOVE:
                if (isOpen && Math.abs(ev.getRawX() - startRawX) > scaledTouchSlop) {
                    return true;
                }
        }
        return false;
    }

    private int startX,cStartX, startRawX;

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        mGestureDetector.onTouchEvent(ev);
        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                startX = (int) ev.getX();
                cStartX = startX;
                break;
            case MotionEvent.ACTION_MOVE:
                int distance = (int) (ev.getX() - cStartX);
                this.scrollBy(-distance, 0);
                cStartX = (int) ev.getX();
                break;
            case MotionEvent.ACTION_UP:
                if (isOpen) {//打开状态
                    if (ev.getRawX() < startRawX && Math.abs(startRawX - ev.getRawX()) > scaledTouchSlop * 2) {
                        closeMenu();
                    }
                } else {//关闭状态
                    int endX = (int) ev.getX();
                    if(Math.abs(endX - startX) >= mMenuWidth/2){
                        openMenu();
                    }else{
                        this.smoothScrollTo(mMenuWidth, 0);
                    }
//                    int endX = (int) ev.getRawX();
//                    if (endX > scaledTouchSlop * 2) {
//                        openMenu();
//                    } else {
//                        closeMenu();
//                    }
                    startX = 0;
                }

                break;
        }
        return true;
    }

    /**
     * 打开菜单
     */
    public void openMenu() {
        if (isOpen)
            return;
        this.smoothScrollTo(0, 0);
        viewUp.setVisibility(View.VISIBLE);
        isOpen = true;
        changeRightBg(isOpen);
    }

    /**
     * 关闭菜单
     */
    public void closeMenu() {
        if (isOpen) {
            this.smoothScrollTo(mMenuWidth, 0);
            isOpen = false;
            changeRightBg(isOpen);
        }
    }

    private void changeRightBg(boolean isOpen) {
        if (viewUp != null) {
            viewUp.setVisibility(isOpen ? View.VISIBLE : View.GONE);
        }
    }

    /**
     * 切换菜单状态
     */
    public void toggle() {
        if (isOpen) {
            closeMenu();
        } else {
            openMenu();
        }
    }

    public boolean isOpen() {
        return isOpen;
    }


    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
//        int offset = oldl - l;
//        if(offset > 0){
//            if(offset >= mMenuWidth) {
//                offset = mMenuWidth;
//            }
//            offset = (int)(offset/(float)mMenuWidth);
//        }else {
//            if(Math.abs(offset) > mMenuWidth) {
//                offset = mMenuWidth;
//            }
//            offset = (int)(Math.abs(offset)/(float)mMenuWidth);
//        }
//        mContentView.setBackgroundColor(Color.argb(/*currentBg = getOffset(ev.getX() - startX)*/ 100*offset,102,102,102));
    }

    private int currentBg;

    private int getOffset(float x) {
//        if(x > 0){
//            return currentBg + (int) (255 * (x / mMenuWidth));
//        }else {
        return currentBg + (int) (255 * (x / mMenuWidth));
//        }
    }


    /**
     * 防止子View滚动时侧滑菜单会弹出
     *
     * @param rect
     * @return
     */
    @Override
    protected int computeScrollDeltaToGetChildRectOnScreen(Rect rect) {
        return 0;
    }
}
