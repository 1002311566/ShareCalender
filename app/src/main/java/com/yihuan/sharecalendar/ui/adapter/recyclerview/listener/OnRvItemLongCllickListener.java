package com.yihuan.sharecalendar.ui.adapter.recyclerview.listener;

import java.util.List;

/**
 * Created by Ronny on 2017/12/15.
 */

public interface OnRvItemLongCllickListener<T> {
    void onItemLongClick(int position, List<T> list);
}
