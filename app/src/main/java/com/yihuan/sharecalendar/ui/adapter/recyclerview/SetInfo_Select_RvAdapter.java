package com.yihuan.sharecalendar.ui.adapter.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.yihuan.sharecalendar.R;
import com.yihuan.sharecalendar.ui.adapter.recyclerview.listener.OnRvItemClickListener;

import java.util.List;

/**
 * Created by Ronny on 2017/9/9.
 */

public class SetInfo_Select_RvAdapter extends BaseRvAdapter<String> {

    private OnRvItemClickListener listener;

    public SetInfo_Select_RvAdapter(List<String> list){
        this.mList = list;
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_set_info_select;
    }

    @Override
    protected RecyclerView.ViewHolder onCreateItemHolder(View view) {
        ItemViewHolder viewHolder = new ItemViewHolder(view);
        viewHolder.tvContent = (TextView) view.findViewById(R.id.tv_content);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        final ItemViewHolder holder1 = (ItemViewHolder) holder;
        holder1.tvContent.setText(mList.get(position));

        holder1.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClick(holder1, position, mList);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    public void setOnRvItemClickListener(OnRvItemClickListener<ItemViewHolder, String> listener) {
        this.listener = listener;
    }

    public static class ItemViewHolder extends BaseRvAdapter.ViewHolder {
        public TextView tvContent;

        public ItemViewHolder(View itemView) {

            super(itemView);
        }
    }
}
