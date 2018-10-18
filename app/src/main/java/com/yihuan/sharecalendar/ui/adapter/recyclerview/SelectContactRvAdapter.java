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
 * 选择联系人 =》 所有好友
 */

public class SelectContactRvAdapter extends BaseRvAdapter<FriendListBean> {

    private boolean hideCheckBox;
    private List<FriendListBean> selectedList;
    private boolean isAll;

    public SelectContactRvAdapter() {
    }

    public SelectContactRvAdapter(boolean hideCheckBox) {
        this.hideCheckBox = hideCheckBox;
    }

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
        if (hideCheckBox) {//todo 是否隐藏选择框
            h.cbSelect.setVisibility(View.GONE);
        } else {
            h.cbSelect.setVisibility(View.VISIBLE);
        }
        UIUtils.displayHeader(h.itemView.getContext(), mList.get(position).getHeaderImage(), h.ivHeader, R.mipmap.logo);
        h.tvNickname.setText(mList.get(position).getNickname());
        //todo 心情

        int letterFirstPosition = getLetterFirstPosition(mList.get(position).getNicknameInitial());
        if (letterFirstPosition == position) {
            //todo 首次出现，显示字母
            h.tvLetter.setVisibility(View.VISIBLE);
            h.tvLetter.setText(mList.get(position).getNicknameInitial());
        } else {
            h.tvLetter.setVisibility(View.GONE);
        }
        //todo 选择状态显示
        h.itemView.setSelected(mList.get(position).isSelect);

        setListener(holder, position);
    }


    private void initSelected() {
        if (mList == null || selectedList == null) return;
        for (FriendListBean b : mList) {
            for (int i = 0; i < selectedList.size(); i++) {
                if (selectedList.get(i).getFriendId() == b.getFriendId()) {
                    b.isSelect = true;
                }
            }
        }
    }

    private void setListener(final RecyclerView.ViewHolder holder, final int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mList.get(position).isSelect = !mList.get(position).isSelect;
                holder.itemView.setSelected(mList.get(position).isSelect);
            }
        });
    }

    /**
     * 获取该字母首次出现的位置
     *
     * @param letter
     * @return
     */
    private int getLetterFirstPosition(String letter) {
        for (int i = 0; i < mList.size(); i++) {
            String nicknameInitial = mList.get(i).getNicknameInitial();
            if (nicknameInitial != null && letter != null && nicknameInitial.equals(letter)) {
                return i;
            }
        }
        return -1;
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

    public void setData(List<FriendListBean> allList, List<FriendListBean> selectedList) {
        this.mList = allList;
        this.selectedList = selectedList;
        //todo 初始化选中
        initSelected();
        notifyDataSetChanged();
    }

    //全选
    public void selectAll() {
        if (mList == null || selectedList == null) return;

        isAll = !isAll;
        for (FriendListBean b : mList) {
            b.isSelect = isAll;
        }
        notifyDataSetChanged();
    }
}
