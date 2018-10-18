package com.yihuan.sharecalendar.ui.adapter.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.yihuan.sharecalendar.R;
import com.yihuan.sharecalendar.modle.bean.friend.TagListBean;
import com.yihuan.sharecalendar.ui.adapter.recyclerview.listener.OnRvItemClickListener;

/**
 * Created by Ronny on 2017/9/28.
 * 选择联系人》搜索 --> 标签列表
 */

public class SelectContactBySearchAdapter extends BaseRvAdapter<TagListBean> {

    private OnRvItemClickListener listener;
    @Override
    protected int getItemLayoutId() {
        return R.layout.item_search_grid_item;
    }

    @Override
    protected RecyclerView.ViewHolder onCreateItemHolder(View view) {
        ItemViewHolder holder = new ItemViewHolder(view);
        holder.tvTagName = (TextView) view.findViewById(R.id.tv_tag_name);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemViewHolder h = (ItemViewHolder) holder;
        h.tvTagName.setText(mList.get(position).getGroupName());

        setLIstener(holder, position);
    }

    private void setLIstener(final RecyclerView.ViewHolder holder, final int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener != null){
                    listener.onItemClick(holder, position, mList);
                }
            }
        });
    }

    public static class ItemViewHolder extends BaseRvAdapter.ViewHolder{
        public TextView tvTagName;
        public ItemViewHolder(View itemView) {
            super(itemView);
        }
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    public void setOnRvItemClickListener(OnRvItemClickListener<ItemViewHolder, TagListBean> listener){
        this.listener = listener;
    }
}
