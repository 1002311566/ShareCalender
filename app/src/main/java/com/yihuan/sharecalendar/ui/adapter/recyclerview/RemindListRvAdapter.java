package com.yihuan.sharecalendar.ui.adapter.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.yihuan.sharecalendar.R;
import com.yihuan.sharecalendar.modle.bean.home.MessageBean;
import com.yihuan.sharecalendar.modle.bean.settting.AutoRemindListBean;
import com.yihuan.sharecalendar.ui.adapter.recyclerview.listener.OnRvItemClickListeners;
import com.yihuan.sharecalendar.utils.BeanToUtils;
import com.yihuan.sharecalendar.utils.StringUtils;

import butterknife.BindView;

/**
 * Created by Ronny on 2018/1/14.
 */

public class RemindListRvAdapter extends BaseRvAdapter<AutoRemindListBean> {

    private OnRvItemClickListeners listeners;

    private boolean isShow;//显示多选框
    private boolean isAllSelect; //全选

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_layout_remind;
    }

    @Override
    protected RecyclerView.ViewHolder onCreateItemHolder(View view) {
        ItemViewHolder h = new ItemViewHolder(view);
        h.cbSelect = (CheckBox) view.findViewById(R.id.cb_select);
        h.tvRemindTitle = (TextView) view.findViewById(R.id.tv_remind_title);
        h.ivRemindType = (ImageView) view.findViewById(R.id.iv_remind_type);
        h.tvRemindFrom = (TextView) view.findViewById(R.id.tv_remind_from);
        h.tvTime = (TextView) view.findViewById(R.id.tv_time);
        return h;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final ItemViewHolder h = (ItemViewHolder) holder;
        //todo 初始化控件
        h.cbSelect.setVisibility(isShow ? View.VISIBLE : View.GONE);

        final AutoRemindListBean bean = mList.get(position);
        h.itemView.setSelected(bean.isSelect);

        h.tvRemindTitle.setText(bean.getName());
        h.ivRemindType.setImageResource(BeanToUtils.getRemindTypeImg(bean.getTaskType()));
        h.tvRemindFrom.setText(BeanToUtils.getRemindTypeFrom(bean.getTaskType()));
        h.tvTime.setText(BeanToUtils.getRemindTime(bean.getStartTime(), bean.getEndTime()));

        if(isShow){
            h.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    bean.isSelect = !bean.isSelect;
                    h.itemView.setSelected(bean.isSelect);
                }
            });
        }else{
            //todo 当不是编辑状态时才去设置其它按钮监听
           setItemClickListener(h, position);
        }
    }

    private void setItemClickListener(ItemViewHolder h, final int position) {
        h.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listeners != null){
                    listeners.onItemClick(position, mList);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    public static class ItemViewHolder extends ViewHolder {
        CheckBox cbSelect;
        TextView tvRemindTitle;
        ImageView ivRemindType;
        TextView tvRemindFrom;
        TextView tvTime;

        public ItemViewHolder(View itemView) {
            super(itemView);
        }
    }

    public void setOnRvItemClickListeners(OnRvItemClickListeners<AutoRemindListBean> listeners){
        this.listeners = listeners;
    }

    /**
     * 更改编辑状态
     * @param isShow
     */
    public void setShow(boolean isShow){
        this.isShow = isShow;
        notifyDataSetChanged();
    }

    /**
     * 获取当前是否是编辑状态
     * @return
     */
    public boolean isShow() {
        return isShow;
    }

    public void selectAll(){
        if(mList != null && mList.size() > 0){
            isAllSelect = !isAllSelect;
            for (AutoRemindListBean bean : mList){
                bean.isSelect = isAllSelect;
            }
            notifyDataSetChanged();
        }
    }

    public boolean isAllSelect(){
        return isAllSelect;
    }
}
