package com.yihuan.sharecalendar.ui.adapter.recyclerview.listener;

import android.support.v7.widget.RecyclerView;

import java.util.List;

/**
 * Created by Ronny on 2017/9/26.
 * item 点击监听 & 添加
 */

public interface OnRvItemClickAndAddListener<T> {
    void onItemClick(int position, List<T> list);
    void onAddClick();
}
