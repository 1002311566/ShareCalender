package com.yihuan.sharecalendar.ui.adapter.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yihuan.sharecalendar.R;
import com.yihuan.sharecalendar.modle.calendar.ActiveBean;
import com.yihuan.sharecalendar.ui.adapter.recyclerview.listener.OnScheduleClickListener;
import com.yihuan.sharecalendar.utils.StringUtils;


/**
 * Created by Ronny on 2017/9/22.
 * 日——》日程管理
 * 从0：00到23：00
 */

public class DayRvAdapter extends BaseRvAdapter<ActiveBean> {

    private OnScheduleClickListener listener;

    public DayRvAdapter() {
        super();
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_day_times;
    }

    @Override
    protected RecyclerView.ViewHolder onCreateItemHolder(View view) {
        ItemViewHolder holder = new ItemViewHolder(view);
        holder.tvTime = (TextView) view.findViewById(R.id.tv_time);
        holder.ivDot = (ImageView) view.findViewById(R.id.iv_dot);
        holder.tvTitle = (TextView) view.findViewById(R.id.tv_title);
        return holder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        ItemViewHolder h = (ItemViewHolder) holder;
        ActiveBean bean = mList.get(position);

        if(bean.is_share_schdule()){
            h.ivDot.setImageResource(R.drawable.calendar_shape_orange_dot);
        }else{
            h.ivDot.setImageResource(R.drawable.calendar_shape_blue_dot);
        }

        if(mList.get(position).isFullday()){
            h.tvTime.setText("全天");
        }else {
            h.tvTime.setText(mList.get(position).getStart_time().toHour()+":00");
        }
        h.tvTitle.setText(StringUtils.nullToStr(mList.get(position).getTitle()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onScheduleClick(mList.get(position).getPublish_user_id(), mList.get(position).getActive_id());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    public static class ItemViewHolder extends ViewHolder {
        TextView tvTime;
        ImageView ivDot;
        TextView tvTitle;

        public ItemViewHolder(View itemView) {
            super(itemView);
        }
    }

    public void setOnRvItemClickListener(OnScheduleClickListener listener) {
        this.listener = listener;
    }

//    @Override
//    public void setDataList(List<ActiveBean> list) {
//        super.setDataList(list);
//        sort();
//    }

//    private void sort() {
//        Collections.sort(mList, new Comparator<ScheduleListBean.ActivityListBean>() {
//            @Override
//            public int compare(ScheduleListBean.ActivityListBean o1, ScheduleListBean.ActivityListBean o2) {
//                return o1.getStartTimeToHourInt().compareTo(o2.getStartTimeToHourInt());
//            }
//        });
//    }
}
