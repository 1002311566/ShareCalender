package com.yihuan.sharecalendar.ui.adapter.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;

import com.yihuan.sharecalendar.R;
import com.yihuan.sharecalendar.modle.bean.friend.TagListBean;
import com.yihuan.sharecalendar.ui.adapter.recyclerview.listener.OnRvItemClickListener;

import java.util.List;

/**
 * Created by Ronny on 2017/10/3.
 * 好友详情-》设置-》设置标签
 */

public class SetFriendTagRvAdapter extends BaseRvAdapter<TagListBean> {
    private OnRvItemClickListener listener;
    @Override
    protected int getItemLayoutId() {
        return R.layout.item_set_friend_tag_item;
    }

    @Override
    protected RecyclerView.ViewHolder onCreateItemHolder(View view) {
        ItemViewHolder holder = new ItemViewHolder(view);
        holder.checkBox = (CheckBox) view.findViewById(R.id.cb_select);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemViewHolder h  = (ItemViewHolder) holder;
        h.checkBox.setText(mList.get(position).getGroupName());
        h.checkBox.setChecked(mList.get(position).isInGroup());
        setListener(h, position);
    }

    private void setListener(final ItemViewHolder holder, final int position) {

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener != null){
                    mList.get(position).setInGroup(!mList.get(position).isInGroup());
                    notifyItemChanged(position);
                    listener.onItemClick(holder, position, mList);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    public static class ItemViewHolder extends BaseRvAdapter.ViewHolder{
        public CheckBox checkBox;

        public ItemViewHolder(View itemView) {
            super(itemView);
        }
    }

    public void setOnRvItemClickListener(OnRvItemClickListener<ItemViewHolder, TagListBean> listener){
        this.listener = listener;
    }

    public List<TagListBean> getDataList(){
        return mList;
    }

}
