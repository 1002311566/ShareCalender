package com.yihuan.sharecalendar.ui.adapter.recyclerview;

import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.yihuan.sharecalendar.R;
import com.yihuan.sharecalendar.global.Constants;
import com.yihuan.sharecalendar.ui.adapter.recyclerview.listener.OnRvItemAddAndDeleteListener;
import com.yihuan.sharecalendar.utils.UIUtils;

import java.util.Iterator;
import java.util.List;


/**
 * Created by Ronny on 2018/1/22.
 * 活动--》添加图片
 */

public class ActiveImgListRvAdapter extends BaseRvAdapter<String> {

    private OnRvItemAddAndDeleteListener listener;

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_active_img;
    }

    @Override
    protected RecyclerView.ViewHolder onCreateItemHolder(View view) {
        ItemViewHolder h = new ItemViewHolder(view);
        h.ivImg = (ImageView) view.findViewById(R.id.iv_img);
        h.ivDelete = (ImageView) view.findViewById(R.id.iv_delete);
        return h;
    }

    @Override
    protected int getFootLayoutId() {
        return R.layout.item_active_add_img;
    }

    @Override
    protected RecyclerView.ViewHolder onCreateFootHolder(View view) {
        return new BaseRvAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (getItemViewType(position) == Constants.TYPE_ITEM) {
            ItemViewHolder h = (ItemViewHolder) holder;
            UIUtils.displayImgBg(mList.get(position),getContext(),  h.ivImg, 0);
//            Glide.with(getContext()).load(mList.get(position)).placeholder(R.mipmap.logo).into(h.ivImg);
            h.ivDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //todo 删除某张图片
//                    mList.remove(position);
//                    notifyDataSetChanged();
                    if(listener != null){
                        listener.onDeleteClick(mList,position);
                    }
                }
            });
        } else if (getItemViewType(position) == Constants.TYPE_FOOT) {
            //todo 添加图片
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onAddClick();
                    }
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {
            return Constants.TYPE_FOOT;
        }
        return Constants.TYPE_ITEM;
    }

    @Override
    public int getItemCount() {
        return mList == null ? 1 : mList.size() + 1;
    }

    static class ItemViewHolder extends ViewHolder {
        ImageView ivImg;
        ImageView ivDelete;

        public ItemViewHolder(View itemView) {
            super(itemView);
        }
    }

    public void setOnRvItemAddAndDeleteListener(OnRvItemAddAndDeleteListener<String> listener) {
        this.listener = listener;
    }

//    public void setNetImgList(List<String> list){
//        if(list == null)return;
//        for (int i = 0; i< list.size(); i++){
//            list.set(i, UIUtils.getImgUrl(list.get(i)));
//        }
//        setDataList(list);
//    }
}
