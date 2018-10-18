package com.yihuan.sharecalendar.ui.adapter.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yihuan.sharecalendar.R;
import com.yihuan.sharecalendar.modle.bean.friend.SearchUserBean;
import com.yihuan.sharecalendar.ui.adapter.recyclerview.listener.OnRvItemClickListener;
import com.yihuan.sharecalendar.utils.UIUtils;

/**
 * Created by Ronny on 2017/9/25.
 * 搜索用户的适配器
 */

public class UserSearchRvAdapter extends BaseRvAdapter<SearchUserBean> {

    private OnRvItemClickListener listener;

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_add_friend_search_list;
    }

    @Override
    protected RecyclerView.ViewHolder onCreateItemHolder(View view) {
        ViewHolder holder = new ViewHolder(view);
        holder.ivHeader = (ImageView) view.findViewById(R.id.iv_header);
        holder.tv_name = (TextView) view.findViewById(R.id.tv_nickname);
        return holder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        final ViewHolder holder1 = (ViewHolder) holder;
        SearchUserBean bean = mList.get(position);
        UIUtils.displayHeader(holder.itemView.getContext(), bean.getHeaderImage(), holder1.ivHeader, R.mipmap.logo);
        holder1.tv_name.setText(mList.get(position).getNickname());
//        holder.


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

    public static class ViewHolder extends BaseRvAdapter.ViewHolder {
        public ImageView ivHeader;
        public TextView tv_name;

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

    public void setOnRvItemClickListener(OnRvItemClickListener<UserSearchRvAdapter.ViewHolder, SearchUserBean> listener) {
        this.listener = listener;
    }
}
