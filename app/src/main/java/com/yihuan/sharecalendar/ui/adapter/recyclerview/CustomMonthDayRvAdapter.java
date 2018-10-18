package com.yihuan.sharecalendar.ui.adapter.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.yihuan.sharecalendar.R;
import com.yihuan.sharecalendar.modle.bean.active.TimeBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ronny on 2017/12/8.
 * 定制化提醒》填写日期
 */

public class CustomMonthDayRvAdapter extends BaseRvAdapter<String> {

    public static final int MAX_COUNT = 31;//todo 最大项数
    private boolean doEdit = true;

    public CustomMonthDayRvAdapter() {
        super();
        mList = new ArrayList<>();
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_custom_day;
    }

    @Override
    protected RecyclerView.ViewHolder onCreateItemHolder(View view) {
        ItemViewHolder holder = new ItemViewHolder(view);
        holder.etDay = (EditText) view.findViewById(R.id.et_day);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemViewHolder h = (ItemViewHolder) holder;
        if(position == getItemCount() -1 && doEdit){
            h.etDay.requestFocus();
        }
        if(h.etDay.getTag() != null && h.etDay.getTag() instanceof TextWatcher){
            h.etDay.removeTextChangedListener((TextWatcher) h.etDay.getTag());
        }
        h.etDay.setText(mList.get(position));

        setListener(h, position);
    }

    private void setListener(final ItemViewHolder h, final int position) {

        //todo 日期
        TextWatcher textWatcherHour =  new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (!TextUtils.isEmpty(s.toString())) {
                        Integer day = Integer.valueOf(s.toString());
                        if (day > 31) {
                            h.etDay.setText("");
                            return;
                        }
                        mList.set(position, s.toString());
                    }
                }
            };
        h.etDay.addTextChangedListener(textWatcherHour);
        h.etDay.setTag(textWatcherHour);

    }

    static class ItemViewHolder extends ViewHolder {
        EditText etDay;

        public ItemViewHolder(View itemView) {
            super(itemView);
        }
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    public void add() {
        if (mList.size() >= MAX_COUNT) return;
        mList.add("");
        notifyDataSetChanged();
    }

    @Override
    public void setDataList(List<String> list) {
        if(list == null || (list != null && list.size() <= 0))return;

        for (String s : list){
            mList.add(s);
        }
        notifyDataSetChanged();
    }


    //todo 是否可编辑
    public void setDoEdit(boolean b) {
        this.doEdit = b;
        notifyDataSetChanged();
    }
}
