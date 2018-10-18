package com.yihuan.sharecalendar.ui.adapter.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.yihuan.sharecalendar.R;
import com.yihuan.sharecalendar.global.Constants;
import com.yihuan.sharecalendar.modle.bean.friend.TagListBean;
import com.yihuan.sharecalendar.ui.adapter.recyclerview.listener.OnRvItemClickAndAddListener;

/**
 * Created by Ronny on 2017/9/25.
 * 标签列表
 */

public class TagListRvAdapter extends BaseRvAdapter<TagListBean> {

    private OnRvItemClickAndAddListener listener;

    @Override
    protected int getHeaderLayoutId() {
        return R.layout.item_tag_list_head;
    }

    @Override
    protected RecyclerView.ViewHolder onCreateHeaderHolder(View view) {
        return new BaseRvAdapter.ViewHolder(view);
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_tag_list_item;
    }

    @Override
    protected RecyclerView.ViewHolder onCreateItemHolder(View view) {
        ItemViewHolder itemViewHolder = new ItemViewHolder(view);
            itemViewHolder.tv_tag_name = (TextView) view.findViewById(R.id.tv_tag_name);
        return itemViewHolder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        if(position != 0){
            String text = new StringBuffer().append(mList.get(position - 1).getGroupName()).append("(")
                    .append(mList.get(position-1).getGroupFriendNumber())
                    .append(")").toString();
            ((ItemViewHolder)holder).tv_tag_name.setText(text);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener != null){
                    if(position == 0){
                        listener.onAddClick();
                    }else{
                        listener.onItemClick(position - 1,mList);
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList == null ? 1 : mList.size() +1;
    }
    @Override
    public int getItemViewType(int position) {
        if(position == 0){
            return Constants.TYPE_HEAD;
        }
        return Constants.TYPE_ITEM;
    }

    static class ItemViewHolder extends BaseRvAdapter.ViewHolder{
        public TextView tv_tag_name;
        public ItemViewHolder(View itemView) {
            super(itemView);
        }
    }

    public void setOnRvItemClickListener2(OnRvItemClickAndAddListener<TagListBean> listener){
        this.listener = listener;
    }
}
