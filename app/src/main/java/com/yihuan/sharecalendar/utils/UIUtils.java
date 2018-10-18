package com.yihuan.sharecalendar.utils;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.ViewTarget;
import com.github.jdsjlzx.ItemDecoration.DividerDecoration;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.ProgressStyle;
import com.yihuan.sharecalendar.R;
import com.yihuan.sharecalendar.global.DataUtils;
import com.yihuan.sharecalendar.http.help.Urls;
import com.yihuan.sharecalendar.ui.view.other.NoScrollGridManager;
import com.yihuan.sharecalendar.ui.view.other.RvDividerItem;

/**
 * Created by Ronny on 2017/9/6.
 */

public class UIUtils {

    /**
     * @param context
     * @return 获取状态栏高度
     */
    public static int getStateBarHight(Context context) {
        int statusBarHeight1 = -1;
        //获取status_bar_height资源的ID
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            //根据资源ID获取响应的尺寸值
            statusBarHeight1 = context.getResources().getDimensionPixelSize(resourceId);
        }
        return statusBarHeight1;
    }

    public static int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }
    public static int getScreenHeight(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.heightPixels;
    }


    /**
     * 显示图片
     * @param context
     * @param view
     */
    public static void displayImgBg(Context context, String path, ImageView view) {
        Glide.with(context).load(path).dontAnimate().into(view);
    }


    /**
     * 显示本地域名的图片
     * @param context
     * @param uri
     * @param view
     * @param defaultImg
     */
    public static void displayHeader(Context context, String uri, ImageView view, int defaultImg) {
        Glide.with(context).load(getImgUrl(uri)).placeholder(defaultImg).dontAnimate().into(view);
    }

    public static void displayHeader(Context context, String uri, FrameLayout view, int defaultImg) {
        Glide.with(context).load(uri).placeholder(defaultImg).dontAnimate().into(new ViewTarget<FrameLayout, GlideDrawable>(view) {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                view.setBackground(resource.getCurrent());
            }
        });
    }

    public static void displayImgBg(Context context, String uri, View view, int defaultImg) {
        Glide.with(context).load(getImgUrl(uri)).dontAnimate().placeholder(defaultImg).into(new ViewTarget<View, GlideDrawable>(view) {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                view.setBackground(resource.getCurrent());
            }
        });
    }

    public static void displayImgBg(String path, Context context, View view, int defaultImg) {
        Glide.with(context).load(path).dontAnimate().placeholder(defaultImg).into(new ViewTarget<View, GlideDrawable>(view) {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                view.setBackground(resource.getCurrent());
            }
        });
    }

    public static void displayImgCrop(Context context, String path, ImageView view, int defaultImg) {
        Glide.with(context).load(path).placeholder(defaultImg).dontAnimate().into(view);
    }
    public static void displayImgBg(Context context, Uri uri, ImageView view, int defaultImg) {
        Glide.with(context).load(uri).placeholder(defaultImg).dontAnimate().into(view);
    }
    public static void displayImgCrop(Context context, Uri uri, ImageView view, int defaultImg) {
        Glide.with(context).load(uri).centerCrop().placeholder(defaultImg).dontAnimate().into(view);
    }
    public static String getImgUrl(String path){
        return Urls.IMG_HEAD+path;
    }

    /**
     * 初始化LrecyclerView
     * @param context
     * @param recyclerview
     * @param adapter
     * @param hasDecoration
     */
    public static void initLRecyclerView(Context context, LRecyclerView recyclerview, RecyclerView.Adapter adapter, boolean hasDecoration) {
        LRecyclerViewAdapter lRecyclerViewAdapter = new LRecyclerViewAdapter(adapter);
        recyclerview.setAdapter(lRecyclerViewAdapter);
        recyclerview.setFooterViewColor(R.color.color_gray_split, R.color.color_text_black_666, R.color.calendar_bg_color);
        recyclerview.setLayoutManager(new LinearLayoutManager(context));
        recyclerview.setLoadingMoreProgressStyle(ProgressStyle.BallSpinFadeLoader);//设置统一的加载更多动画
        if(hasDecoration){
//            recyclerview.addItemDecoration(new DividerItemDecoration(context, LinearLayoutManager.VERTICAL));
            add(context, lRecyclerViewAdapter, recyclerview);
        }
    }

    public static void initRecyclerView(Context context, RecyclerView recyclerView, RecyclerView.Adapter adapter, boolean hasDecoration){
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        if(hasDecoration){
            recyclerView.addItemDecoration(new RvDividerItem(context, LinearLayoutManager.VERTICAL));
        }
    }

    public static void initNoScrollRecyclerView(Context context, RecyclerView recyclerView, RecyclerView.Adapter adapter, boolean hasDecoration){
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new NoScrollGridManager(context, 1));
        if(hasDecoration){
            recyclerView.addItemDecoration(new RvDividerItem(context, GridLayoutManager.VERTICAL));
        }
    }


    /**
     * recyclerview添加分割线
     * @param context
     * @param lRecyclerViewAdapter
     * @param recyclerview
     */
    public static void add(Context context, LRecyclerViewAdapter lRecyclerViewAdapter, LRecyclerView recyclerview){
        DividerDecoration divider = new DividerDecoration.Builder(context)
                .setHeight(1f)
                .setPadding(1f)
                .setColorResource(R.color.color_gray_split)
                .build();
        recyclerview.addItemDecoration(divider);
    }

    //todo 加载背景皮肤
    public static void showTheme(Context context, final View view){
        String themePath = DataUtils.getThemePath();
        if(TextUtils.isEmpty(themePath)){
            view.setBackgroundColor(Color.WHITE);
            return;
        }
        Glide.with(context).load(DataUtils.getThemePath()).into(new ViewTarget<View, GlideDrawable>(view) {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                view.setBackground(resource.getCurrent());
            }
        });
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 滑动到指定位置
     *
     * @param mRecyclerView
     * @param position
     */
    public static void smoothMoveToPosition(RecyclerView mRecyclerView, final int position) {
        try {
            // 第一个可见位置
            int firstItem = mRecyclerView.getChildLayoutPosition(mRecyclerView.getChildAt(0));
            // 最后一个可见位置
            int lastItem = mRecyclerView.getChildLayoutPosition(mRecyclerView.getChildAt(mRecyclerView.getChildCount() - 1));

            if (position < firstItem) {
                // 如果跳转位置在第一个可见位置之前，就smoothScrollToPosition可以直接跳转
                mRecyclerView.smoothScrollToPosition(position);
            } else if (position <= lastItem) {
                // 跳转位置在第一个可见项之后，最后一个可见项之前
                // smoothScrollToPosition根本不会动，此时调用smoothScrollBy来滑动到指定位置
                int movePosition = position - firstItem;
                if (movePosition >= 0 && movePosition < mRecyclerView.getChildCount()) {
                    int top = mRecyclerView.getChildAt(movePosition).getTop();
                    mRecyclerView.smoothScrollBy(0, top);
                }
            } else {
                // 如果要跳转的位置在最后可见项之后，则先调用smoothScrollToPosition将要跳转的位置滚动到可见位置
                // 再通过onScrollStateChanged控制再次调用smoothMoveToPosition，执行上一个判断中的方法
                mRecyclerView.smoothScrollToPosition(position);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
