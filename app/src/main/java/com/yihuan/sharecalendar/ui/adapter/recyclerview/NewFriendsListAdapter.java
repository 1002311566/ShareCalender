package com.yihuan.sharecalendar.ui.adapter.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yihuan.sharecalendar.R;
import com.yihuan.sharecalendar.global.App;
import com.yihuan.sharecalendar.modle.bean.friend.NewApplyListBean;
import com.yihuan.sharecalendar.ui.adapter.recyclerview.listener.OnRvItemClickListener;
import com.yihuan.sharecalendar.utils.UIUtils;

/**
 * Created by Ronny on 2017/9/25.
 */

public class NewFriendsListAdapter extends BaseRvAdapter<NewApplyListBean> {
    private OnRvItemClickListener listener;
    private OnAgreeClickListener agreeClickListener;

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_new_friends_list;
    }

    @Override
    protected RecyclerView.ViewHolder onCreateItemHolder(View view) {
        ItemViewHolder itemViewHolder = new ItemViewHolder(view);
        itemViewHolder.llFriends = (LinearLayout) view.findViewById(R.id.ll_friends);
        itemViewHolder.ivHeader = (ImageView) view.findViewById(R.id.iv_header);
        itemViewHolder.tvNickname = (TextView) view.findViewById(R.id.tv_nickname);
        itemViewHolder.tvInviteState = (TextView) view.findViewById(R.id.tv_invite_state);
        itemViewHolder.tvInviteState_ok = (TextView) view.findViewById(R.id.tv_invite_state_ok);
        itemViewHolder.tv_apply_msg = (TextView) view.findViewById(R.id.tv_msg);
        return itemViewHolder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        ItemViewHolder holder1 = (ItemViewHolder) holder;
        //todo 头像昵称
        holder1.tvNickname.setText(mList.get(position).getNickname());
        UIUtils.displayHeader(holder1.itemView.getContext(),mList.get(position).getHeaderImage(), holder1.ivHeader, R.mipmap.logo);
        holder1.tv_apply_msg.setText(mList.get(position).getAttachMessage());
//        0: 未处理, 1: 接受, 2: 拒绝
        String proStatus = mList.get(position).getProStatus();
        holder1.tvInviteState.setVisibility(View.GONE);
        holder1.tvInviteState_ok.setVisibility(View.GONE);

        if(proStatus != null){
            if(proStatus.equals("0")){
                //显示接受按钮 & 自己申请的列表等待接受
                if(mList.get(position).getUserId() == App.getInstanse().getUserId()){
                    holder1.tvInviteState_ok.setVisibility(View.VISIBLE);
                    holder1.tvInviteState_ok.setText("等待通过");
                    holder1.tvInviteState.setVisibility(View.GONE);
                }else{
                    holder1.tvInviteState.setVisibility(View.VISIBLE);
                }
            }else if(proStatus.equals("1") || proStatus.equals("2")){
                //显示已接受
                holder1.tvInviteState_ok.setVisibility(View.VISIBLE);
                holder1.tvInviteState_ok.setText(proStatus.equals("1")? "已添加":"已拒绝");
                holder1.tvInviteState.setVisibility(View.GONE);
            }
        }
        setListener(holder, position);
    }

    private void setListener(final RecyclerView.ViewHolder holder, final int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClick(holder, position, mList);
                }
            }
        });

        ((ItemViewHolder)holder).tvInviteState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(agreeClickListener != null){
                    agreeClickListener.onAgreeClick(mList.get(position).getUserId());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    public static class ItemViewHolder extends BaseRvAdapter.ViewHolder {
        public ImageView ivHeader;
        public TextView tvNickname;
        public TextView tvInviteState;
        public TextView tvInviteState_ok;
        public LinearLayout llFriends;
        public TextView tv_apply_msg;

        public ItemViewHolder(View itemView) {
            super(itemView);
        }
    }

    public void setOnRvItemClickListener(OnRvItemClickListener<ItemViewHolder, NewApplyListBean> listener) {
        this.listener = listener;
    }


    public interface OnAgreeClickListener{
        void onAgreeClick(int friendId);
    }

    public void setOnAgreeClickListener(OnAgreeClickListener listener){
        this.agreeClickListener = listener;
    }
}
