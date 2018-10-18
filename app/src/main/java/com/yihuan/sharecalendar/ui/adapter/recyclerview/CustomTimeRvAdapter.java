package com.yihuan.sharecalendar.ui.adapter.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.yihuan.sharecalendar.R;
import com.yihuan.sharecalendar.modle.bean.active.TimeBean;
import com.yihuan.sharecalendar.modle.bean.settting.ReminderTimeBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ronny on 2017/12/8.
 * 新增提醒》自定义间隔时间
 */

public class CustomTimeRvAdapter extends BaseRvAdapter<ReminderTimeBean> {

    public static final int MAX_COUNT = 30;//todo 最大项数
    private boolean doEdit = true;

    public CustomTimeRvAdapter() {
        super();
        mList = new ArrayList<>();
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_custom_time;
    }

    @Override
    protected RecyclerView.ViewHolder onCreateItemHolder(View view) {
        ItemViewHolder holder = new ItemViewHolder(view);
        holder.etHour = (EditText) view.findViewById(R.id.et_hour);
        holder.etMinute = (EditText) view.findViewById(R.id.et_minute);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemViewHolder h = (ItemViewHolder) holder;
        if(position == getItemCount() -1 && doEdit){
            h.etHour.requestFocus();
        }
        if(h.etHour.getTag() != null && h.etHour.getTag() instanceof TextWatcher){
            h.etHour.removeTextChangedListener((TextWatcher) h.etHour.getTag());
        }
        if(h.etMinute.getTag() != null && h.etMinute.getTag() instanceof TextWatcher){
            h.etMinute.removeTextChangedListener((TextWatcher) h.etMinute.getTag());
        }

        h.etHour.setText(mList.get(position).getRemindHour());
        h.etMinute.setText(mList.get(position).getRemindMinute());

        setListener(h, position);
    }

    private void setListener(final ItemViewHolder h, final int position) {

        //todo 小時輸入监听
        TextWatcher textWatcherHour = new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (!TextUtils.isEmpty(s.toString())) {
                        Integer time = Integer.valueOf(s.toString());
                        if(time > 23){
                            h.etHour.setText("");
                            return;
                        }
                        mList.get(position).setRemindHour(String.format("%02d", time));
                    }
                }
            };
        h.etHour.addTextChangedListener(textWatcherHour);
        h.etHour.setTag(textWatcherHour);

        //todo 分钟输入监听
        TextWatcher textWatcherMinute= new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (!TextUtils.isEmpty(s.toString())) {
                        Integer time = Integer.valueOf(s.toString());
                        if(time > 59){
                            h.etMinute.setText("");
                            return;
                        }
                        mList.get(position).setRemindMinute(String.format("%02d", time));
                    }
                }
            };
        h.etMinute.addTextChangedListener(textWatcherMinute);
        h.etMinute.setTag(textWatcherMinute);
    }


    static class ItemViewHolder extends ViewHolder {
        EditText etHour;
        EditText etMinute;

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

        mList.add(new ReminderTimeBean());
        notifyDataSetChanged();
    }

    public List<ReminderTimeBean> getlistData() {
        return mList;
    }

    @Override
    public void setDataList(List<ReminderTimeBean> list) {
        if(list == null)return;
        for (ReminderTimeBean b : list){
            mList.add(b);
        }
        notifyDataSetChanged();
    }

    public void setDoEdit(boolean b) {
        this.doEdit = b;
    }
}
