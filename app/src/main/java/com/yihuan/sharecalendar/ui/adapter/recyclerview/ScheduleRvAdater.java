package com.yihuan.sharecalendar.ui.adapter.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yihuan.sharecalendar.R;
import com.yihuan.sharecalendar.modle.bean.active.TimeBean;
import com.yihuan.sharecalendar.modle.bean.home.ScheduleListBean;
import com.yihuan.sharecalendar.modle.calendar.ActiveBean;
import com.yihuan.sharecalendar.ui.adapter.recyclerview.listener.OnRvItemLongCllickListener;
import com.yihuan.sharecalendar.ui.adapter.recyclerview.listener.OnScheduleClickListener;
import com.yihuan.sharecalendar.utils.StringUtils;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Ronny on 2017/9/21.
 * 首页==》日程
 */

public class ScheduleRvAdater extends BaseRvAdapter<ActiveBean> {

    private OnScheduleClickListener listener;
    private OnRvItemLongCllickListener longListener;

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
        holder.tvFullday  = (TextView) view.findViewById(R.id.tv_fullday);
        holder.tvTime = (TextView) view.findViewById(R.id.tv_time);
        holder.tvState = (TextView) view.findViewById(R.id.tv_state);
        holder.llDateLayout = (LinearLayout) view.findViewById(R.id.ll_date);
        holder.llItem = (LinearLayout) view.findViewById(R.id.ll_item);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemViewHolder h = (ItemViewHolder) holder;
        setListener(h, position);
        ActiveBean b = mList.get(position);

        if(b.is_share_schdule()){
            h.ivDot.setImageResource(R.drawable.calendar_shape_orange_dot);
        }else{
            h.ivDot.setImageResource(R.drawable.calendar_shape_blue_dot);
        }

        //todo 先获取时间，再查到时间第一次出现的位置
        int firstPos = getFirstDatePosition(b.getStart_time().toYMD());
        //todo 显示日期
        h.llDateLayout.setVisibility(firstPos == position ? View.VISIBLE : View.GONE);
        //todo 填充其它数据 7月25日周三 农历 闰六月初四
//        TimeBean timeBean = new TimeBean(b.getStartTime());
        String s = StringUtils.nullToStr(b.getStart_time().toDateAndWeek() +" 农历 " + StringUtils.nullToStr(b.getStart_time().getLunar().toString()));
        h.tvDate.setText(s);
        h.tvName.setText(b.getTitle());
        h.tvFullday.setVisibility(b.isFullday()? View.VISIBLE : View.GONE);
        h.tvTime.setText(b.getStart_time().toHM());

    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    private void setListener(final ItemViewHolder holder, final int position) {
        holder.llItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                        listener.onScheduleClick(mList.get(position).getPublish_user_id(), mList.get(position).getActive_id());//发起者id，活动id
                }
            }
        });

        holder.llItem.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(longListener != null){
                    longListener.onItemLongClick( position, mList);
                }
                return true;
            }
        });
    }


    public static class ItemViewHolder extends BaseRvAdapter.ViewHolder {
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

    public void setOnScheduleClickListener(OnScheduleClickListener listener) {
        this.listener = listener;
    }

    public void setOnRvItemLongClickListener(OnRvItemLongCllickListener<ScheduleListBean.ActivityListBean> listener){
        this.longListener = listener;
    }

    private int getFirstDatePosition(String date) {
        for (int i = 0; i < mList.size(); i++) {
            if(mList.get(i).getStart_time().toYMD().equals(date)){
                return i;
            }
        }
        return -1;
    }
}
