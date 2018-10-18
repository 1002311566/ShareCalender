package com.yihuan.sharecalendar.ui.adapter.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yihuan.sharecalendar.R;
import com.yihuan.sharecalendar.global.Constants;
import com.yihuan.sharecalendar.modle.bean.active.AlarmTime;
import com.yihuan.sharecalendar.ui.adapter.recyclerview.listener.OnRvItemAddAndDeleteListener;
import com.yihuan.sharecalendar.ui.adapter.recyclerview.listener.OnRvItemClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ronny on 2017/11/2.
 */

public class NewActiveRemindRvAdapter extends BaseRvAdapter<AlarmTime> {

    private OnRvItemClickListener listener;

    public NewActiveRemindRvAdapter() {
        //todo 初始化一些操作
        mList = new ArrayList<AlarmTime>();
        mList.add(new AlarmTime("5"));
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_new_active_remind;
    }

    @Override
    protected RecyclerView.ViewHolder onCreateItemHolder(View view) {
        ItemViewHolder holder = new ItemViewHolder(view);
        holder.tvRemindTime = (TextView) view.findViewById(R.id.tv_remind_time);
        holder.ivDeleteRemind = (ImageView) view.findViewById(R.id.iv_delete_remind);
        return holder;
    }

    @Override
    protected int getFootLayoutId() {
        return R.layout.foot_new_active_remind;
    }

    @Override
    protected RecyclerView.ViewHolder onCreateFootHolder(View view) {
        FootViewHolder holder = new FootViewHolder(view);
        holder.llAddRemind = (LinearLayout) view.findViewById(R.id.ll_add_remind);
        holder.ivAddRemind = (ImageView) view.findViewById(R.id.iv_add_remind);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(position != getItemCount() -1){
            ((ItemViewHolder)holder).tvRemindTime.setText(mList.get(position).getAlarmTimeStr());
        }
        setListener(holder, position);
    }

    private void setListener(RecyclerView.ViewHolder holder, final int position) {
        if (position == getItemCount() - 1 && mList.size() < 5) {
            FootViewHolder fh = (FootViewHolder) holder;
            //todo 添加按钮
            fh.ivAddRemind.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mList.add(new AlarmTime("5"));//todo 这里添加默认5分钟
                    notifyDataSetChanged();
                }
            });
        } else {
            final ItemViewHolder h = (ItemViewHolder) holder;
            h.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //todo 选择操作
                    if (listener != null) {
                        listener.onItemClick(h, position, mList);
                    }
                }
            });
            h.ivDeleteRemind.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //todo 删除操作
                    notifyDataSetChanged();
                    mList.remove(position);
                }
            });

        }
    }

    @Override
    public int getItemCount() {
        return mList.size() < 5 ? mList.size() + 1 : 5;
    }


    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1 && mList.size() != 5) {
            return Constants.TYPE_FOOT;
        }
        return Constants.TYPE_ITEM;
    }

    public static class ItemViewHolder extends BaseRvAdapter.ViewHolder {
        public TextView tvRemindTime;
        public ImageView ivDeleteRemind;

        public ItemViewHolder(View itemView) {
            super(itemView);
        }
    }

    static class FootViewHolder extends BaseRvAdapter.ViewHolder {
        LinearLayout llAddRemind;
        ImageView ivAddRemind;

        public FootViewHolder(View itemView) {
            super(itemView);
        }
    }

    public void setOnRvItemClickListener(OnRvItemClickListener<ItemViewHolder, AlarmTime> listener) {
        this.listener = listener;
    }
    public List<AlarmTime> getAlarmTimeList() {
        return mList;
    }

    @Override
    public void setDataList(List<AlarmTime> list) {
        if(list == null)return;
        mList.clear();
        mList.addAll(list);
        notifyDataSetChanged();
    }
}
