package com.yihuan.sharecalendar.ui.adapter.viewpager;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.yihuan.sharecalendar.utils.UIUtils;

import java.util.List;

/**
 * Created by Ronny on 2018/1/31.
 */

public class ImgLookVpAdapter extends PagerAdapter {
    private List<String> mList;
    private SparseArray<ImageView> mViews;
    private Context mContext;

    public ImgLookVpAdapter(Context context, List<String> list){
        this.mContext = context;
        this.mList = list;
        mViews = new SparseArray<>();
    }
    @Override
    public int getCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        if (mViews.get(position) == null) {
            ImageView imageView = new ImageView(mContext);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            imageView.setLayoutParams(lp);
            mViews.put(position, imageView);
        }
        ImageView imageView = mViews.get(position);
        UIUtils.displayHeader(mContext, mList.get(position), imageView, 0);
        container.addView(imageView);
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getItemPosition(Object object) {
        return super.getItemPosition(object);
    }
}
