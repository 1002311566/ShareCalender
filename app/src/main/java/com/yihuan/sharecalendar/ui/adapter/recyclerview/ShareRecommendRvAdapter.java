package com.yihuan.sharecalendar.ui.adapter.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yihuan.sharecalendar.R;
import com.yihuan.sharecalendar.modle.bean.AdvertisingBean;
import com.yihuan.sharecalendar.ui.adapter.recyclerview.listener.OnRvItemClickListeners;
import com.yihuan.sharecalendar.utils.BeanToUtils;
import com.yihuan.sharecalendar.utils.StringUtils;
import com.yihuan.sharecalendar.utils.UIUtils;

/**
 * Created by Ronny on 2018/1/15.
 * 分享推荐
 */

public class ShareRecommendRvAdapter extends BaseRvAdapter<AdvertisingBean> {

    private OnRvItemClickListeners listener;
    @Override
    protected int getItemLayoutId() {
        return R.layout.item_share_recommend;
    }

    @Override
    protected RecyclerView.ViewHolder onCreateItemHolder(View view) {
        ItemViewHolder h = new ItemViewHolder(view);
        h.ivImg = (ImageView) view.findViewById(R.id.iv_img);
        h.tvTime = (TextView) view.findViewById(R.id.tv_time);
        return h;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        ItemViewHolder h = (ItemViewHolder) holder;
        //todo------------广告图片设置start------------
        AdvertisingBean b1 = mList.get(position);
        if(b1 != null && b1.getAdImages() != null && b1.getAdImages().size() > 0){
            AdvertisingBean.AdImagesBean adImagesBean = b1.getAdImages().get(0);
            if(adImagesBean != null){
                UIUtils.displayHeader(getContext(), adImagesBean.getImage(), h.ivImg, 0);
            }
        }
        //todo --------------广告图片设置end------------------
        h.tvTime.setText(StringUtils.nullToStr(mList.get(position).getActTimeBegin())+"至"+
                        StringUtils.nullToStr(mList.get(position).getActTimeEnd()));


        holder.itemView.setOnClickListener(new View.OnClickListener() {
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
        ImageView ivImg;
        TextView tvTime;
        public ItemViewHolder(View itemView) {
            super(itemView);
        }
    }

    public void setOnRvItemClickListeners(OnRvItemClickListeners<AdvertisingBean> listener){
        this.listener = listener;
    }
}
