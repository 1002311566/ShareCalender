package com.yihuan.sharecalendar.ui.adapter.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.baidu.mapapi.search.sug.SuggestionResult;
import com.yihuan.sharecalendar.R;
import com.yihuan.sharecalendar.ui.adapter.recyclerview.listener.OnRvItemClickListener;
import com.yihuan.sharecalendar.utils.StringUtils;

import butterknife.BindView;

/**
 * Created by Ronny on 2017/12/14.
 */

public class BDSearchRvAdapter extends BaseRvAdapter<SuggestionResult.SuggestionInfo> {


    private OnRvItemClickListener listener;

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_baidu_search;
    }

    @Override
    protected RecyclerView.ViewHolder onCreateItemHolder(View view) {
        ItemViewHolder h = new ItemViewHolder(view);
        h.tvAddress = (TextView) view.findViewById(R.id.tv_address);
        h.tvLocation = (TextView) view.findViewById(R.id.tv_location);
        return h;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        ItemViewHolder h = (ItemViewHolder) holder;
        SuggestionResult.SuggestionInfo bean = mList.get(position);
        h.tvAddress.setText(StringUtils.nullToStr(bean.key));
        h.tvLocation.setText(StringUtils.nullToStr(bean.city) + StringUtils.nullToStr(bean.district));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClick(holder, position, mList);
                }
            }
        });
    }

    public static class ItemViewHolder extends ViewHolder {
        TextView tvAddress;
        TextView tvLocation;

        public ItemViewHolder(View itemView) {
            super(itemView);
        }
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    public void setOnRvItemClickListener(OnRvItemClickListener<ItemViewHolder, SuggestionResult.SuggestionInfo> listener) {
        this.listener = listener;
    }
}
