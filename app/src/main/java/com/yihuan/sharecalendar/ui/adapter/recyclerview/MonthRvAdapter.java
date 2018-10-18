package com.yihuan.sharecalendar.ui.adapter.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yihuan.sharecalendar.R;
import com.yihuan.sharecalendar.modle.bean.home.ScheduleListBean;
import com.yihuan.sharecalendar.modle.calendar.ActiveBean;
import com.yihuan.sharecalendar.ui.adapter.recyclerview.listener.OnRvItemClickListener;

/**
 * Created by Ronny on 2017/9/22.
 */

public class MonthRvAdapter extends BaseRvAdapter<ActiveBean> {

    private OnRvItemClickListener listener;

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_schedule_type_item;
    }

    @Override
    protected RecyclerView.ViewHolder onCreateItemHolder(View view) {
        ItemViewHolder holder = new ItemViewHolder(view);
        holder.tvDate = (TextView) view.findViewById(R.id.tv_date);
        holder.ivDot = (ImageView) view.findViewById(R.id.iv_sschedule_dot);
        holder.tvName = (TextView) view.findViewById(R.id.tv_schedule_name);
        holder.tvFullday = (TextView) view.findViewById(R.id.tv_fullday);
        holder.tvTime = (TextView) view.findViewById(R.id.tv_time);
        holder.tvState = (TextView) view.findViewById(R.id.tv_state);
        holder.llDateLayout = (LinearLayout) view.findViewById(R.id.ll_date);
        holder.llItem = (LinearLayout) view.findViewById(R.id.ll_item);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemViewHolder h = (ItemViewHolder) holder;
        ActiveBean bean = mList.get(position);
        if(bean.is_share_schdule()){
            h.ivDot.setImageResource(R.drawable.calendar_shape_orange_dot);
        }else{
            h.ivDot.setImageResource(R.drawable.calendar_shape_blue_dot);
        }
        h.tvName.setText(bean.getTitle());
        h.tvFullday.setVisibility(bean.isFullday() ? View.VISIBLE : View.GONE);
        h.tvTime.setText(bean.getStart_time().toHM());

        setListener(h, position);
    }

    private void setListener(final ItemViewHolder h, final int position) {
        h.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClick(h, position, mList);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    public static class ItemViewHolder extends ViewHolder {
        public ImageView ivDot;
        public TextView tvDate;
        public TextView tvName;
        public TextView tvFullday;
        public TextView tvTime;
        public TextView tvState;//状态（开始）
        public LinearLayout llDateLayout;
        public LinearLayout llItem;

        public ItemViewHolder(View itemView) {
            super(itemView);
        }
    }


    public void setOnRvItemClickListener(OnRvItemClickListener<ItemViewHolder, ActiveBean> listener) {
        this.listener = listener;
    }
}
