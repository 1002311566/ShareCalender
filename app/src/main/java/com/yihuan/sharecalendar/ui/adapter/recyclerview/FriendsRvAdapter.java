package com.yihuan.sharecalendar.ui.adapter.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yihuan.sharecalendar.R;
import com.yihuan.sharecalendar.global.Constants;
import com.yihuan.sharecalendar.modle.bean.friend.FriendListBean;
import com.yihuan.sharecalendar.modle.data.TimeUtils;
import com.yihuan.sharecalendar.utils.BeanToUtils;
import com.yihuan.sharecalendar.utils.UIUtils;

import java.util.List;

/**
 * Created by Ronny on 2017/9/25.
 */

public class FriendsRvAdapter extends BaseRvAdapter<FriendListBean> implements View.OnClickListener {
    private OnFriendItemClickListener listener;

    private HeaderViewHolder headerViewHolder;

    @Override
    protected int getHeaderLayoutId() {
        return R.layout.head_item_friend;
    }

    @Override
    protected RecyclerView.ViewHolder onCreateHeaderHolder(View view) {
        HeaderViewHolder headerViewHolder = new HeaderViewHolder(view);
        headerViewHolder.llNewFriend = (LinearLayout) view.findViewById(R.id.ll_new_friend);
        headerViewHolder.llTag = (LinearLayout) view.findViewById(R.id.ll_tag_friendpage);
        headerViewHolder.tvNewFriendMsgCount = (TextView) view.findViewById(R.id.tv_new_friend_msg_count);
        headerViewHolder.llNewFriendMsgCoung = (LinearLayout) view.findViewById(R.id.ll_new_friend_msg_count);
        headerViewHolder.llNewFriend.setOnClickListener(this);
        headerViewHolder.llTag.setOnClickListener(this);
        this.headerViewHolder = headerViewHolder;
        return headerViewHolder;
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_friend_list;
    }

    @Override
    protected RecyclerView.ViewHolder onCreateItemHolder(View view) {
        ItemViewHolder itemViewHolder = new ItemViewHolder(view);
        itemViewHolder.llFriends = (LinearLayout) view.findViewById(R.id.ll_friends);
        itemViewHolder.ivHeader = (ImageView) view.findViewById(R.id.iv_header);
        itemViewHolder.tvNickname = (TextView) view.findViewById(R.id.tv_nickname);
        itemViewHolder.ivMood = (ImageView) view.findViewById(R.id.iv_mood);
        itemViewHolder.tvLetter = (TextView) view.findViewById(R.id.tv_letter);
        itemViewHolder.ivBirthday = (ImageView) view.findViewById(R.id.iv_birthday);
        return itemViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder1, int position) {
        if (position != 0) {
            ItemViewHolder holder = (ItemViewHolder) holder1;
            FriendListBean friendListBean = mList.get(position - 1);
            UIUtils.displayHeader(getContext(), friendListBean.getHeaderImage(), holder.ivHeader, R.mipmap.logo);
            holder.tvNickname.setText(friendListBean.getNickname());
            String currentMood = friendListBean.getCurrentMood();
            String birthday = friendListBean.getBirthday();
            String currentMD = TimeUtils.getCurrentTimeBean().toMD();
            if(!TextUtils.isEmpty(birthday) && birthday.equals(currentMD)){
                holder.ivBirthday.setVisibility(View.VISIBLE);
            }else{
                holder.ivBirthday.setVisibility(View.GONE);
            }
            holder.ivMood.setImageResource(BeanToUtils.getMoodMinResouceId(currentMood));
            int letterFirstPosition = getLetterFirstPosition(mList.get(position - 1).getNicknameInitial());
            if (letterFirstPosition == position - 1) {
                //todo 首次出现，显示字母
                holder.tvLetter.setVisibility(View.VISIBLE);
                holder.tvLetter.setText(mList.get(position - 1).getNicknameInitial());
            } else {
                holder.tvLetter.setVisibility(View.GONE);
            }


        }
        setListener(holder1, position);
    }

    @Override
    public int getItemCount() {
        return mList == null ? 1 : mList.size() + 1;
    }


    private void setListener(final RecyclerView.ViewHolder holder, final int position) {
        if (position != 0) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onFriendItemClick(holder, position - 1, mList);
                    }
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0)
            return Constants.TYPE_HEAD;

        return Constants.TYPE_ITEM;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_new_friend:
                if (listener != null) {
                    listener.onNewFriendClick();
                }
                break;
            case R.id.ll_tag_friendpage:
                if (listener != null) {
                    listener.onTagClick();
                }
                break;
        }
    }

    public static class HeaderViewHolder extends BaseRvAdapter.ViewHolder {
        public LinearLayout llNewFriend;
        public LinearLayout llTag;
        public TextView tvNewFriendMsgCount;
        public LinearLayout llNewFriendMsgCoung;

        public HeaderViewHolder(View itemView) {
            super(itemView);
        }
    }

    static class ItemViewHolder extends BaseRvAdapter.ViewHolder {
        public TextView tvLetter;//首字母
        public ImageView ivHeader;
        public TextView tvNickname;
        public ImageView ivBirthday;
        public ImageView ivMood;
        public LinearLayout llFriends;

        public ItemViewHolder(View itemView) {
            super(itemView);
        }
    }

    public interface OnFriendItemClickListener {
        void onNewFriendClick();

        void onTagClick();

        void onFriendItemClick(RecyclerView.ViewHolder holder, int position, List<FriendListBean> mList);
    }

    public void setOnFriendItemClickListener(OnFriendItemClickListener listener) {
        this.listener = listener;
    }

    /**
     * 获取该字母首次出现的位置
     *
     * @param letter
     * @return
     */
    public int getLetterFirstPosition(String letter) {
        for (int i = 0; i < mList.size(); i++) {
            String nicknameInitial = mList.get(i).getNicknameInitial();
            if (nicknameInitial != null && letter != null && nicknameInitial.equals(letter)) {
                return i;
            }
        }
        return -1;
    }

    public HeaderViewHolder getHeaderViewHolder() {
        return headerViewHolder;
    }
}
