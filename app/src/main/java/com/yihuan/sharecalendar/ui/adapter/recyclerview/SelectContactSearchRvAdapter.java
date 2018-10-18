package com.yihuan.sharecalendar.ui.adapter.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yihuan.sharecalendar.R;
import com.yihuan.sharecalendar.modle.bean.friend.FriendListBean;
import com.yihuan.sharecalendar.utils.UIUtils;

import java.util.List;

/**
 * Created by Ronny on 2017/9/28.
 * 搜索 & 点击标签 好友列表
 */

public class SelectContactSearchRvAdapter extends BaseRvAdapter<FriendListBean> {

    private List<FriendListBean> allFriendList;//todo 所有好友
    private boolean isAll;

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_friend_list;
    }

    @Override
    protected RecyclerView.ViewHolder onCreateItemHolder(View view) {
        ItemViewHolder holder = new ItemViewHolder(view);
        holder.llFriends = (LinearLayout) view.findViewById(R.id.ll_friends);
        holder.ivHeader = (ImageView) view.findViewById(R.id.iv_header);
        holder.tvNickname = (TextView) view.findViewById(R.id.tv_nickname);
        holder.ivMood = (ImageView) view.findViewById(R.id.iv_mood);
        holder.tvLetter = (TextView) view.findViewById(R.id.tv_letter);
        holder.cbSelect = (CheckBox) view.findViewById(R.id.cb_select);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemViewHolder h = (ItemViewHolder) holder;
        h.cbSelect.setVisibility(View.VISIBLE);
        UIUtils.displayHeader(h.itemView.getContext(), mList.get(position).getHeaderImage(), h.ivHeader, R.mipmap.logo);
        h.tvNickname.setText(mList.get(position).getNickname());
        //todo 心情

        h.itemView.setSelected(mList.get(position).isSelect);

        setListener(holder, position);
    }

    private void setListener(final RecyclerView.ViewHolder holder, final int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mList.get(position).isSelect = !mList.get(position).isSelect;
                holder.itemView.setSelected(mList.get(position).isSelect);
                //todo 同步所有好友的数据
                synchronizedData(mList.get(position));
            }
        });
    }

    /**
     * 同步选中好友到所有好友
     *
     * @param friendListBean
     */
    private void synchronizedData(FriendListBean friendListBean) {
        for (FriendListBean allBean : allFriendList) {
            if (allBean.getFriendId() == friendListBean.getFriendId()) {
                allBean.isSelect = friendListBean.isSelect;
            }
        }
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    public static class ItemViewHolder extends BaseRvAdapter.ViewHolder {
        public TextView tvLetter;//首字母
        public CheckBox cbSelect;
        public ImageView ivHeader;
        public TextView tvNickname;
        public ImageView ivMood;
        public LinearLayout llFriends;

        public ItemViewHolder(View itemView) {
            super(itemView);
        }
    }

    private void initSelected() {
        if (mList == null || allFriendList == null) return;
        for (FriendListBean queryBean : mList) {
            for (FriendListBean allBean : allFriendList) {
                if (allBean.getFriendId() == queryBean.getFriendId() &&
                        allBean.isSelect) {
                    queryBean.isSelect = true;
                }
            }
        }
    }

    public void setData(List<FriendListBean> queryFriendList, List<FriendListBean> allFriendList) {
        this.allFriendList = allFriendList;
        this.mList = queryFriendList;
        //todo 初始化选中
        initSelected();
        notifyDataSetChanged();
    }

    //全选
    public void selectAll() {
        if (mList == null || allFriendList == null) return;

        isAll = !isAll;
        for (FriendListBean queryBean : mList) {
            queryBean.isSelect = isAll;
            synchronizedData(queryBean);
        }
        notifyDataSetChanged();
    }

    public boolean isAll(){
        return isAll;
    }
}
