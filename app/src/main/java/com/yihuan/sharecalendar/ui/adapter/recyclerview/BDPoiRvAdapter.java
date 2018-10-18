package com.yihuan.sharecalendar.ui.adapter.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.mapapi.search.core.PoiInfo;
import com.yihuan.sharecalendar.R;
import com.yihuan.sharecalendar.ui.adapter.recyclerview.listener.OnRvItemClickListeners;

/**
 * Created by Ronny on 2017/12/21.
 */

public class BDPoiRvAdapter extends BaseRvAdapter<PoiInfo> {
    private OnRvItemClickListeners listener;
    private String lastUid;

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_baidu_poi;
    }

    @Override
    protected RecyclerView.ViewHolder onCreateItemHolder(View view) {
        ItemViewHolder h = new ItemViewHolder(view);
        h.tvAddress = (TextView) view.findViewById(R.id.tv_address);
        h.tvAddressDes = (TextView) view.findViewById(R.id.tv_address_des);
        h.ivSelect = (ImageView) view.findViewById(R.id.iv_select);
        return h;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ItemViewHolder h = (ItemViewHolder) holder;
        PoiInfo poiInfo = mList.get(position);

        h.tvAddress.setText(poiInfo.name);
        h.tvAddressDes.setText(poiInfo.address);
        h.ivSelect.setVisibility(poiInfo.uid.equals(lastUid) ? View.VISIBLE : View.INVISIBLE);
        h.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lastUid = mList.get(position).uid;
                notifyDataSetChanged();
                if(listener != null){
                    listener.onItemClick(position, mList);
                }
            }
        });
    }

    static class ItemViewHolder extends ViewHolder {
        TextView tvAddress;
        TextView tvAddressDes;
        ImageView ivSelect;

        public ItemViewHolder(View itemView) {
            super(itemView);
        }
    }
    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    public void setOnRvItemClickListeners(OnRvItemClickListeners<PoiInfo> listener){
        this.listener = listener;
    }
}
