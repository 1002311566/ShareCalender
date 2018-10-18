package com.yihuan.sharecalendar.ui.adapter.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.yihuan.sharecalendar.R;
import com.yihuan.sharecalendar.ui.adapter.recyclerview.listener.OnRvItemClickListeners;
import com.yihuan.sharecalendar.utils.UIUtils;

/**
 * Created by Ronny on 2018/1/29.
 * 活动详情的图片列表适配器
 */

public class ActiveDetailsImgRvAdapter extends BaseRvAdapter<String> {

    private OnRvItemClickListeners listener;

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_active_details_imglist;
    }

    @Override
    protected RecyclerView.ViewHolder onCreateItemHolder(View view) {
        ItemViewHolder h = new ItemViewHolder(view);
        h.imageView = (ImageView) view.findViewById(R.id.iv_img);
        return h;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ItemViewHolder h = (ItemViewHolder) holder;
        UIUtils.displayImgBg(getContext(), mList.get(position), h.imageView, R.mipmap.logo);
        h.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener != null){
                    listener.onItemClick(position, mList);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }
    static class ItemViewHolder extends BaseRvAdapter.ViewHolder{

        ImageView imageView;
        public ItemViewHolder(View itemView) {
            super(itemView);
        }
    }

    public void setOnRvItemClickListeners(OnRvItemClickListeners<String> listener){
        this.listener = listener;
    }
}
