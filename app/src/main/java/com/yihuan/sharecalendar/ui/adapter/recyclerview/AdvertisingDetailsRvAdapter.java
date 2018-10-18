package com.yihuan.sharecalendar.ui.adapter.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.yihuan.sharecalendar.R;
import com.yihuan.sharecalendar.global.Constants;
import com.yihuan.sharecalendar.modle.bean.AdvertisingBean;
import com.yihuan.sharecalendar.utils.UIUtils;

/**
 * Created by Ronny on 2018/1/19.
 */

public class AdvertisingDetailsRvAdapter extends BaseRvAdapter<AdvertisingBean.AdImagesBean> {

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_advertising_details;
    }

    @Override
    protected RecyclerView.ViewHolder onCreateItemHolder(View view) {
        ItemViewHolder h = new ItemViewHolder(view);
        h.iv = (ImageView) view.findViewById(R.id.iv_img);
        return h;
    }

    @Override
    protected int getFootLayoutId() {
        return R.layout.item_button;
    }

    @Override
    protected RecyclerView.ViewHolder onCreateFootHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(getItemViewType(position) == Constants.TYPE_ITEM){
            ItemViewHolder h = (ItemViewHolder) holder;
            UIUtils.displayImgBg(getContext(), mList.get(position).getImage(), h.iv, 0);
        }
    }

    static class ItemViewHolder extends BaseRvAdapter.ViewHolder {
        ImageView iv;

        public ItemViewHolder(View itemView) {
            super(itemView);
        }
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position != getItemCount() - 1) {
            return Constants.TYPE_ITEM;
        }
        return Constants.TYPE_FOOT;
    }
}
