package com.yihuan.sharecalendar.ui.adapter.recyclerview.listener;

import java.util.List;

/**
 * Created by Ronny on 2017/9/21.
 * item点击监听，带数据元
 */

public interface OnRvItemClickListeners<T> {
    void onItemClick(int position, List<T> mList);
}
