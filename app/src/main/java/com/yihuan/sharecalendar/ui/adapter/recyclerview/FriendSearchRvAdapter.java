package com.yihuan.sharecalendar.ui.adapter.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yihuan.sharecalendar.R;
import com.yihuan.sharecalendar.modle.bean.friend.FriendListBean;
import com.yihuan.sharecalendar.ui.adapter.recyclerview.listener.OnRvItemClickListener;
import com.yihuan.sharecalendar.utils.UIUtils;

/**
 * Created by Ronny on 2017/9/25.
 * 搜索好友的适配器
 */

public class FriendSearchRvAdapter extends BaseRvAdapter<FriendListBean> {

    private OnRvItemClickListener listener;

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_add_friend_search_list;
    }

    @Override
    protected RecyclerView.ViewHolder onCreateItemHolder(View view) {
        ItemViewHolder holder = new ItemViewHolder(view);
        holder.llFriends = (LinearLayout) view.findViewById(R.id.ll_friends);
        holder.ivHeader = (ImageView) view.findViewById(R.id.iv_header);
        holder.tvNickname = (TextView) view.findViewById(R.id.tv_nickname);
        holder.ivMood = (ImageView) view.findViewById(R.id.iv_mood);
        holder.tvLetter = (TextView) view.findViewById(R.id.tv_letter);
        return holder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        final ItemViewHolder h = (ItemViewHolder) holder;
        FriendListBean bean = mList.get(position);
        UIUtils.displayHeader(holder.itemView.getContext(), bean.getHeaderImage(), h.ivHeader, R.mipmap.logo);
        h.tvNickname.setText(mList.get(position).getNickname());

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

    public void setOnRvItemClickListener(OnRvItemClickListener<FriendSearchRvAdapter.ItemViewHolder, FriendListBean> listener) {
        this.listener = listener;
    }

    public static class ItemViewHolder extends BaseRvAdapter.ViewHolder{
        public TextView tvLetter;//首字母
        public ImageView ivHeader;
        public TextView tvNickname;
        public ImageView ivMood;
        public LinearLayout llFriends;

        public ItemViewHolder(View itemView) {
            super(itemView);
        }
    }
}
