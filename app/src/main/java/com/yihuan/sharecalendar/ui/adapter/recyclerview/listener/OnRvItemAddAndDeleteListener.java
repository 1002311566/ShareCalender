package com.yihuan.sharecalendar.ui.adapter.recyclerview.listener;

import android.support.v7.widget.RecyclerView;

import java.util.List;

/**
 * Created by Ronny on 2017/9/26.
 * 添加，删除
 */

public interface OnRvItemAddAndDeleteListener<T> {
    void onAddClick();
    void onDeleteClick(List<T> mList, int position);
}
