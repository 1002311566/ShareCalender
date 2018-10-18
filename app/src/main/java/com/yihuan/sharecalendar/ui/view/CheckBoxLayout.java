package com.yihuan.sharecalendar.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yihuan.sharecalendar.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ronny on 2017/9/29.
 */

public class CheckBoxLayout extends LinearLayout {

    private boolean isSingle = true;//是否是单选

    private Context mContext;
    private int itemCount;//子项的数量
    private int layoutId;//子项资源ID
    private List<ViewGroup> childViewList;
    private OnSingleCheckedListener listener;
    private int lastPosition = 0;//上一次选择的项
    private List<Boolean> mListData;//多选时用到

    public CheckBoxLayout(Context context) {
        this(context, null);
    }

    public CheckBoxLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CheckBoxLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.CheckBoxLayout);
        layoutId = ta.getResourceId(R.styleable.CheckBoxLayout_layout_item, 0);
        itemCount = ta.getInteger(R.styleable.CheckBoxLayout_item_count, 0);
        isSingle = ta.getBoolean(R.styleable.CheckBoxLayout_is_single, true);
        ta.recycle();

        init();
    }

    private void init() {
        mListData = new ArrayList<>();
        if (itemCount > 0 && childViewList == null) {
            childViewList = new ArrayList<>();
        }

        LayoutInflater inflater = LayoutInflater.from(mContext);
        LayoutParams lp = new LayoutParams(0, LayoutParams.MATCH_PARENT, 1);
        for (int i = 0; i < itemCount; i++) {
            mListData.add(false);
            View childView = inflater.inflate(layoutId, this, false);
            if (!(childView instanceof ViewGroup)) {
                return;
            }
            if (i == 0 && isSingle) {
                //初始化默认选择第一项
                setChecked((ViewGroup) childView, true);
            }
            childViewList.add((ViewGroup) childView);
            addView(childView, lp);

            final int finalI = i;
            childView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isSingle) {
                        refreshLayout(finalI);
                        lastPosition = finalI;
                    } else {
                        v.setSelected(!v.isSelected());
                        mListData.set(finalI, v.isSelected());
                        setChecked((ViewGroup) v, v.isSelected());
                    }
                    if (listener != null) {
                        listener.onChecked(finalI);
                    }
                }
            });
        }


    }

    private void refreshLayout(int finalI) {
        if (finalI == lastPosition) {
            return;
        }
        setChecked(childViewList.get(lastPosition), false);
        setChecked(childViewList.get(finalI), true);
    }

    private void setChecked(ViewGroup v, boolean checked) {
        ViewGroup view = (ViewGroup) v;
        for (int i = 0; i < view.getChildCount(); i++) {
            if (view.getChildAt(i) instanceof CheckBox) {
                ((CheckBox) view.getChildAt(i)).setChecked(checked);
            }
        }
    }

    public interface OnSingleCheckedListener {
        void onChecked(int position);
    }

    public void setOnSingleCheckedListener(OnSingleCheckedListener listener) {
        this.listener = listener;
    }

    public void setText(int arrayId) {
        String[] strings = getResources().getStringArray(arrayId);
        for (int j = 0; j < childViewList.size(); j++) {
            ViewGroup viewGroup = childViewList.get(j);
            int childCount = viewGroup.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View view = viewGroup.getChildAt(i);
                if (!(view instanceof CheckBox) && view instanceof TextView) {
                    ((TextView) view).setText(strings[j]);
                }
            }
        }
    }

    /**
     * 我的心情
     */
    public void setMoodImg(int arrayId) {
        TypedArray ta = getResources().obtainTypedArray(arrayId);
        int[] imgs = new int[ta.length()];
        for (int i = 0; i < ta.length(); i++) {
            imgs[i] = ta.getResourceId(i, 0);
        }
        for (int j = 0; j < childViewList.size(); j++) {
            ViewGroup viewGroup = childViewList.get(j);
            int childCount = viewGroup.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View view = viewGroup.getChildAt(i);
                if (!(view instanceof CheckBox) && view instanceof ImageView) {
                    ((ImageView) view).setImageResource(imgs[j]);
                }
            }
        }
    }

    //todo 单选时使用
    public void setCurrent(int position) {
        if (position < 0) return;

        int childCount = getChildCount();
        if (position >= childCount) return;

        for (int i = 0; i < getChildCount(); i++) {
            View childView = getChildAt(i);
            if (!(childView instanceof ViewGroup)) return;
            ViewGroup v = (ViewGroup) childView;
            if (i == position) {
                setChecked(v, true);
                lastPosition = i;
                continue;
            }
            setChecked(v, false);
        }
    }

    //todo 多选时用到
    public List<Boolean> getSelectedList() {
        return mListData;
    }

    public void setSelectList(List<Integer> list){
        if(list == null)return;
        for (int i = 0; i< itemCount; i++){
            if(list.contains(i)){
                ViewGroup child = (ViewGroup) getChildAt(i);
                child.setSelected(true);
                mListData.set(i,true);
                setChecked(child, true);
            }
        }
    }
}
