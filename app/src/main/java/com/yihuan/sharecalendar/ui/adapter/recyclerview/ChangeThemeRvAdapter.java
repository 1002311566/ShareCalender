package com.yihuan.sharecalendar.ui.adapter.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.yihuan.sharecalendar.R;
import com.yihuan.sharecalendar.global.Constants;
import com.yihuan.sharecalendar.modle.bean.settting.ThemeListBean;
import com.yihuan.sharecalendar.ui.adapter.recyclerview.listener.OnRvItemAddAndDeleteAndClickListener;
import com.yihuan.sharecalendar.utils.UIUtils;

/**
 * Created by Ronny on 2017/9/26.
 */

public class ChangeThemeRvAdapter extends BaseRvAdapter<ThemeListBean> {
    private int TYPE_DEFAULT = 3;
    private OnRvItemAddAndDeleteAndClickListener listener;
    private View.OnClickListener defaultListener;
    private int lastPos = -1;


    @Override
    protected int getItemLayoutId() {
        return R.layout.item_change_theme_item;
    }

    @Override
    protected RecyclerView.ViewHolder onCreateItemHolder(View view) {
        ItemViewHolder itemViewHolder = new ItemViewHolder(view);
        itemViewHolder.ivTheme = (ImageView) view.findViewById(R.id.iv_theme);
        itemViewHolder.ivSeleted = (ImageView) view.findViewById(R.id.iv_seleted);
        itemViewHolder.ivDelete = (ImageView) view.findViewById(R.id.iv_delete);
        return itemViewHolder;
    }

    @Override
    protected int getFootLayoutId() {
        return R.layout.item_change_theme_foot;
    }

    @Override
    protected RecyclerView.ViewHolder onCreateFootHolder(View view) {
        return new BaseRvAdapter.ViewHolder(view);
    }

    @Override
    protected RecyclerView.ViewHolder onOtherHolder(ViewGroup parent, int viewType) {
        ViewHolder holder = null;
        if(viewType == TYPE_DEFAULT){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_change_theme_foot_default, parent, false);
            holder = new BaseRvAdapter.ViewHolder(view);
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == Constants.TYPE_ITEM) {
            ItemViewHolder h = (ItemViewHolder) holder;
            UIUtils.displayImgBg(getContext(), mList.get(position).getImage(), h.ivTheme, R.mipmap.icon_change_theme_add);
            h.itemView.setSelected(mList.get(position).isSelected());
            if(lastPos == -1 && mList.get(position).isSelected()){
                lastPos = position;
            }
            h.ivDelete.setVisibility(mList.get(position).getPublisher() == 0 ? View.GONE : View.VISIBLE);
            setDeleteListener(h, position);
        }
        setListener(holder, position);
    }

    private void setDeleteListener(ItemViewHolder h, final int position) {
        h.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener != null){
                    listener.onDeleteClick(position, mList);
                }
            }
        });
    }

    private void setListener(final RecyclerView.ViewHolder holder, final int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getItemViewType(position) == Constants.TYPE_ITEM) {//todo 图片点击
                    if (listener != null) {
                        //todo 刷新这一次的点击项
                        ThemeListBean bean = mList.get(position);
                        bean.setSelected(true);
                        holder.itemView.setSelected(true);
                        //todo 刷新上一次的点击项
                        if(lastPos != position && lastPos != -1){
                            mList.get(lastPos).setSelected(false);
                            notifyItemChanged(lastPos);
                        }
                        listener.onItemClick(position, mList);
                        lastPos = position;
                    }
                } else if (getItemViewType(position) == Constants.TYPE_FOOT) {
                    if (listener != null) {
                        listener.onAddClick();
                    }
                }else if(getItemViewType(position) == TYPE_DEFAULT){
                    if(defaultListener != null){
                        if(lastPos != position && lastPos != -1){
                            mList.get(lastPos).setSelected(false);
                            notifyItemChanged(lastPos);
                        }
                        v.setSelected(true);
                        defaultListener.onClick(v);
                    }
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return mList == null ? 2 : mList.size() + 2;
    }

    @Override
    public int getItemViewType(int position) {

        if (position == getItemCount() - 1) {
            return Constants.TYPE_FOOT;
        }else if(position == getItemCount() - 2){
            return TYPE_DEFAULT;
        }
        return Constants.TYPE_ITEM;
    }

    static class ItemViewHolder extends BaseRvAdapter.ViewHolder {
        public ImageView ivTheme;
        public ImageView ivSeleted;
        public ImageView ivDelete;

        public ItemViewHolder(View itemView) {
            super(itemView);
        }
    }

    public void setOnRvItemClickAndAddListener(OnRvItemAddAndDeleteAndClickListener<ThemeListBean> listener) {
        this.listener = listener;
    }

    public void dataRefresh(){
        lastPos = -1;
    }

    public void setOnDefaultListener(View.OnClickListener listener){
        this.defaultListener = listener;
    }
}
