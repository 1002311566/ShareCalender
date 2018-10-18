package com.yihuan.sharecalendar.ui.adapter.recyclerview.listener;

import android.support.v7.widget.RecyclerView;

import java.util.List;

/**
 * Created by Ronny on 2017/9/21.
 * item点击监听，带数据元
 */

public interface OnRvItemClickListener<H extends RecyclerView.ViewHolder, T> {
    void onItemClick(H holder, int position,  List<T> mList);
}
