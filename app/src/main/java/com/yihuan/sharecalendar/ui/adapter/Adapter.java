package com.yihuan.sharecalendar.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.yihuan.sharecalendar.R;
import com.yihuan.sharecalendar.ui.adapter.recyclerview.BaseRvAdapter;

public class Adapter extends BaseRvAdapter {
    @Override
    protected int getItemLayoutId() {
        return R.layout.item_tv_channel;
    }

    @Override
    protected RecyclerView.ViewHolder onCreateItemHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }
}
