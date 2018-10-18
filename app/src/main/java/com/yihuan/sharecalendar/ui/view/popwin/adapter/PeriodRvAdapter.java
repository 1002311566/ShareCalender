package com.yihuan.sharecalendar.ui.view.popwin.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;

import com.yihuan.sharecalendar.R;
import com.yihuan.sharecalendar.ui.adapter.recyclerview.BaseRvAdapter;
import com.yihuan.sharecalendar.ui.adapter.recyclerview.listener.OnRvItemClickListener;

import java.util.List;

/**
 * Created by Ronny on 2017/11/6.
 */

public class PeriodRvAdapter extends BaseRvAdapter<String> {

    private OnRvItemClickListener listener;

    private int clickPosition;

    @Override
    protected int getItemLayoutId() {
        return R.layout.pop_item_select_layout;
    }

    @Override
    protected RecyclerView.ViewHolder onCreateItemHolder(View view) {
        ItemViewHolder holder = new ItemViewHolder(view);
        holder.checkBox = (CheckBox) view.findViewById(R.id.tv_content);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final ItemViewHolder h = (ItemViewHolder) holder;
        h.checkBox.setText(mList.get(position));
        if(clickPosition == position){
            h.checkBox.setChecked(true);
        }else {
            h.checkBox.setChecked(false);
        }

        h.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClick(h, position, mList);
                    clickPosition = position;
                    notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

   public static class ItemViewHolder extends BaseRvAdapter.ViewHolder {
        CheckBox checkBox;

        public ItemViewHolder(View itemView) {
            super(itemView);
        }
    }

    public void setOnRvItemClickListener(OnRvItemClickListener<ItemViewHolder, String> listener) {
        this.listener = listener;
    }

    public void setDataList(List list, int clickPosition){
        this.clickPosition = clickPosition;
        setDataList(list);
    }
}
