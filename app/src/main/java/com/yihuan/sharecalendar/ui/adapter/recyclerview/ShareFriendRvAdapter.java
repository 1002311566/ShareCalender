package com.yihuan.sharecalendar.ui.adapter.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yihuan.sharecalendar.R;
import com.yihuan.sharecalendar.global.Constants;
import com.yihuan.sharecalendar.modle.bean.friend.FriendListBean;
import com.yihuan.sharecalendar.ui.adapter.recyclerview.listener.OnRvItemAddAndDeleteListener;
import com.yihuan.sharecalendar.utils.UIUtils;

import java.util.List;

/**
 * Created by Ronny on 2017/9/10.
 * 添加活动==》添加共享好友
 */

public class ShareFriendRvAdapter extends BaseRvAdapter<FriendListBean> {

    private boolean isShowDeleteImg = true; //todo 是否显示删除按钮
    private OnRvItemAddAndDeleteListener listener;

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_add_active_share_friend_item;
    }

    @Override
    protected RecyclerView.ViewHolder onCreateItemHolder(View view) {
        ItemViewHolder holder = new ItemViewHolder(view);
        holder.ivHeader = (ImageView) view.findViewById(R.id.iv_header);
        holder.tvNickname = (TextView) view.findViewById(R.id.tv_nickname);
        holder.ivDelete = (ImageView) view.findViewById(R.id.iv_delete);
        return holder;
    }

    @Override
    protected int getFootLayoutId() {
        return R.layout.item_new_active_share_friend;
    }

    @Override
    protected RecyclerView.ViewHolder onCreateFootHolder(View view) {
        return new BaseRvAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if(position != getItemCount()-1){
            final ItemViewHolder h = (ItemViewHolder) holder;
            if(!isShowDeleteImg){
                h.ivDelete.setVisibility(View.GONE);
            }
            UIUtils.displayHeader(getContext(), mList.get(position).getHeaderImage(), h.ivHeader, R.mipmap.logo);
            h.tvNickname.setText(mList.get(position).getNickname());
            h.ivDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        listener.onDeleteClick(mList, position);
                    }
                }
            });
        }
        setListener(holder, position);
    }

    private void setListener(final RecyclerView.ViewHolder holder, final int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    if (getItemViewType(position) == Constants.TYPE_FOOT) {
                        listener.onAddClick();
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList == null ? 1 : mList.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (mList != null && position != mList.size()) {
            return Constants.TYPE_ITEM;
        }
        return Constants.TYPE_FOOT;
    }

    static class ItemViewHolder extends BaseRvAdapter.ViewHolder{
        public ImageView ivHeader;
        public TextView tvNickname;
        public ImageView ivDelete;

        public ItemViewHolder(View itemView) {
            super(itemView);
        }
    }

    public void setOnRvItemClickListener3(OnRvItemAddAndDeleteListener<FriendListBean> listener) {
        this.listener = listener;
    }

    /**
     * 隐藏删除按钮
     * @param beanList
     */
    public void setDataListHideDelete(List<FriendListBean> beanList){
        this.isShowDeleteImg = false;
        setDataList(beanList);
    }
}
