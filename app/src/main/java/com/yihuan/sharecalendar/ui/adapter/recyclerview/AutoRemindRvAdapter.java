package com.yihuan.sharecalendar.ui.adapter.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.yihuan.sharecalendar.R;
import com.yihuan.sharecalendar.global.Constants;
import com.yihuan.sharecalendar.modle.bean.settting.AutoRemindListBean;
import com.yihuan.sharecalendar.ui.adapter.recyclerview.listener.OnRvItemClickAndAddListener;
import com.yihuan.sharecalendar.ui.adapter.recyclerview.listener.OnRvItemLongCllickListener;
import com.yihuan.sharecalendar.utils.StringUtils;

/**
 * Created by Ronny on 2017/9/26.
 * 定制化提醒
 * 这里有三种类型
 */

public class AutoRemindRvAdapter extends BaseRvAdapter<AutoRemindListBean> {

    //    public List<String> mList;
    private OnRvItemClickAndAddListener listener;
    private OnRvItemLongCllickListener longCllickListener;

    @Override
    protected int getHeaderLayoutId() {
        return R.layout.item_auto_remind_head;
    }

    @Override
    protected RecyclerView.ViewHolder onCreateHeaderHolder(View view) {
        return new BaseRvAdapter.ViewHolder(view);
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_auto_remind_item;
    }

    @Override
    protected RecyclerView.ViewHolder onCreateItemHolder(View view) {
        ItemViewHolder itemViewHolder = new ItemViewHolder(view);
        itemViewHolder.tvRemindName = (TextView) view.findViewById(R.id.tv_remind_name);
        return itemViewHolder;
    }

    @Override
    protected int getFootLayoutId() {
        return R.layout.item_auto_remind_foot;
    }

    @Override
    protected RecyclerView.ViewHolder onCreateFootHolder(View view) {
        return new BaseRvAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        if(getItemViewType(position) == Constants.TYPE_ITEM){
            ItemViewHolder h = (ItemViewHolder) holder;
            h.tvRemindName.setText(StringUtils.nullToStr(mList.get(position - 1).getName()));
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getItemViewType(position) == Constants.TYPE_FOOT) {
                    if (listener != null) {
                        listener.onAddClick();
                    }
                } else if (getItemViewType(position) == Constants.TYPE_ITEM) {
                    if (listener != null) {
                        listener.onItemClick(position - 1, mList);
                    }
                }
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (getItemViewType(position) == Constants.TYPE_ITEM) {
                    if (longCllickListener != null) {
                        longCllickListener.onItemLongClick(position - 1, mList);
                    }
                }
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList == null ? 2 : mList.size() + 2;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return Constants.TYPE_HEAD;
        } else if (position != getItemCount() - 1) {
            return Constants.TYPE_ITEM;
        }
        return Constants.TYPE_FOOT;
    }

    static class ItemViewHolder extends BaseRvAdapter.ViewHolder {
        public TextView tvRemindName;

        public ItemViewHolder(View itemView) {
            super(itemView);
        }
    }

    public void setOnItemClickListener2(OnRvItemClickAndAddListener<AutoRemindListBean> listener) {
        this.listener = listener;
    }

    public void setOnRvItemLongCllickListener(OnRvItemLongCllickListener<AutoRemindListBean> listener){
        this.longCllickListener  = listener;
    }

}
