package com.yihuan.sharecalendar.ui.adapter.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yihuan.sharecalendar.R;
import com.yihuan.sharecalendar.ui.adapter.recyclerview.BaseRvAdapter;
import com.yihuan.sharecalendar.ui.adapter.recyclerview.listener.OnScheduleClickListener;


/**
 * Created by Ronny on 2017/11/7.
 * 日程搜索适配器,先放置，暂使用ScheduleRvAdapter
 */

public class SearchScheduleRvAdapter extends BaseRvAdapter {

    private OnScheduleClickListener listener;

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_schedule_type_item;
    }

    @Override
    protected RecyclerView.ViewHolder onCreateItemHolder(View view) {
        ItemViewHolder holder = new ItemViewHolder(view);
        holder.tvDate = (TextView) view.findViewById(R.id.tv_date);
        holder.ivSscheduleDot = (ImageView) view.findViewById(R.id.iv_sschedule_dot);
        holder.tvScheduleName = (TextView) view.findViewById(R.id.tv_schedule_name);
        holder.tvTime = (TextView) view.findViewById(R.id.tv_time);
        holder.tvState = (TextView) view.findViewById(R.id.tv_state);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemViewHolder h = (ItemViewHolder) holder;
//        h.tvDate.setText();//格式： 7月25日周三 农历 闰六月初四
//        h.tvScheduleName.setText();
//        h.tvTime.setText();
//        h.tvState.setText();

        setListener(h, position);



    }

    private void setListener(ItemViewHolder h, int position) {
        h.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener != null){
//                    listener.onScheduleClick();//日程类型（个人和共享）活动id
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    static class ItemViewHolder extends BaseRvAdapter.ViewHolder {
        TextView tvDate;
        ImageView ivSscheduleDot;
        TextView tvScheduleName;
        TextView tvTime;
        TextView tvState;

        public ItemViewHolder(View itemView) {
            super(itemView);
        }
    }

    public void setOnScheduleClickListener(OnScheduleClickListener listener){
        this.listener = listener;
    }
}
