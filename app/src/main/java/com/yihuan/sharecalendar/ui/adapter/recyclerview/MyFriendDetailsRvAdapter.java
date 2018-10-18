package com.yihuan.sharecalendar.ui.adapter.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yihuan.sharecalendar.R;
import com.yihuan.sharecalendar.global.Constants;
import com.yihuan.sharecalendar.modle.bean.friend.MyFriendDetailBean;
import com.yihuan.sharecalendar.ui.adapter.recyclerview.listener.OnRvItemClickListeners;
import com.yihuan.sharecalendar.ui.view.CircleImageView;
import com.yihuan.sharecalendar.utils.BeanToUtils;
import com.yihuan.sharecalendar.utils.StringUtils;
import com.yihuan.sharecalendar.utils.UIUtils;

import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by Ronny on 2017/9/27.
 */

public class MyFriendDetailsRvAdapter extends BaseRvAdapter<String> {

    private MyFriendDetailBean myFriendDetailBean;
    private View.OnClickListener listener;
    private OnRvItemClickListeners itemClickListeners;

    @Override
    protected int getHeaderLayoutId() {
        return R.layout.item_my_friend_details_head;
    }

    @Override
    protected RecyclerView.ViewHolder onCreateHeaderHolder(View view) {
        HeaderViewHolder headerViewHolder = new HeaderViewHolder(view);
        headerViewHolder.ivHeader = (CircleImageView) view.findViewById(R.id.iv_header);
        headerViewHolder.tvNickname = (TextView) view.findViewById(R.id.tv_nickname);
        headerViewHolder.ivSex = (ImageView) view.findViewById(R.id.iv_sex);
        headerViewHolder.ivMood = (ImageView) view.findViewById(R.id.iv_mood);
        headerViewHolder.tvAddress = (TextView) view.findViewById(R.id.tv_address);
        headerViewHolder.tvSignature = (TextView) view.findViewById(R.id.tv_signature);
        return headerViewHolder;
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_my_friend_details_item;
    }

    @Override
    protected RecyclerView.ViewHolder onCreateItemHolder(View view) {
        ItemViewHolder itemViewHolder = new ItemViewHolder(view);
        itemViewHolder.tvActiveName = (TextView) view.findViewById(R.id.tv_active_name);
        itemViewHolder.tvTime = (TextView) view.findViewById(R.id.tv_time);
        return itemViewHolder;
    }

    @Override
    protected int getFootLayoutId() {
        return R.layout.foot_item_empty_schedule;
    }

    @Override
    protected RecyclerView.ViewHolder onCreateFootHolder(View view) {
        FootViewHolder h = new FootViewHolder(view);
        h.emptyBg = view.findViewById(R.id.v_empty);
        h.tvToCreate = (TextView) view.findViewById(R.id.tv_to_create);
        h.llemptyLayout = (LinearLayout) view.findViewById(R.id.ll_empty_layout);
        return h;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(position == 0){
            setHeaderViewData((HeaderViewHolder) holder);
        }else if(position != getItemCount() -1){
            setItemViewData((ItemViewHolder)holder, position - 1);
        }else if(position == getItemCount() -1){
            FootViewHolder h = (FootViewHolder) holder;
            if(myFriendDetailBean == null || (myFriendDetailBean != null && myFriendDetailBean.getActivityList() != null && myFriendDetailBean.getActivityList().size() <=0)){
                h.llemptyLayout.setVisibility(View.VISIBLE);
                h.tvToCreate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(listener != null){
                            listener.onClick(v);
                        }
                    }
                });
            }else{
                h.llemptyLayout.setVisibility(View.INVISIBLE);
            }
        }
    }

    private void setItemViewData(ItemViewHolder holder, final int i) {
        if(myFriendDetailBean != null && myFriendDetailBean.getActivityList() != null){
            final List<MyFriendDetailBean.ActivityListBean> activityList = myFriendDetailBean.getActivityList();
            holder.tvActiveName.setText(activityList.get(i).getTitle());
            holder.tvTime.setText(activityList.get(i).getStartTime());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(itemClickListeners != null){
                        itemClickListeners.onItemClick(i, activityList);
                    }
                }
            });
        }
    }

    private void setHeaderViewData(HeaderViewHolder holder) {
        if(myFriendDetailBean == null)return;

        UIUtils.displayHeader(getContext(), myFriendDetailBean.getHeaderImage(),holder.ivHeader, R.mipmap.logo);
        holder.tvNickname.setText(myFriendDetailBean.getNickname());
        holder.ivSex.setImageResource(BeanToUtils.getSexResouceId(myFriendDetailBean.getSex()));
        holder.ivMood.setImageResource(BeanToUtils.getMoodResouceId(myFriendDetailBean.getCurrentMood()));
        holder.tvAddress.setText(StringUtils.nullToStr(myFriendDetailBean.getProvince()) +" "+
                                    StringUtils.nullToStr(myFriendDetailBean.getCity()));
        holder.tvSignature.setText(BeanToUtils.getSignature(myFriendDetailBean.getSignature()));
    }


    static class HeaderViewHolder extends ViewHolder {
        public TextView tvNickname;
        public ImageView ivSex;
        public CircleImageView ivHeader;
        public ImageView ivMood;
        public TextView tvAddress;
        public TextView tvSignature;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(itemView);
        }
    }

    static class ItemViewHolder extends ViewHolder {
       public TextView tvActiveName;
       public TextView tvTime;
        public ItemViewHolder(View itemView) {
            super(itemView);
        }
    }

    static class FootViewHolder extends BaseRvAdapter.ViewHolder{
        View emptyBg;
        TextView tvToCreate;
        LinearLayout llemptyLayout;
        public FootViewHolder(View itemView) {
            super(itemView);
        }
    }

    @Override
    public int getItemCount() {
        return myFriendDetailBean == null ? 1 : (myFriendDetailBean.getActivityList() == null ? 2 : myFriendDetailBean.getActivityList().size() + 2);
    }

    @Override
    public int getItemViewType(int position) {//todo 三种类型 1 头布局 2 没有活动的布局 3 有活动的布局
        if (position == 0) {
            return Constants.TYPE_HEAD;
        }else if(position == getItemCount() - 1){
            return Constants.TYPE_FOOT;
        }
        return Constants.TYPE_ITEM;
    }

    public void setMyFriendDetailBean(MyFriendDetailBean bean){
        this.myFriendDetailBean  = bean;
        this.notifyDataSetChanged();
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }

    public void setItemClickListeners(OnRvItemClickListeners<MyFriendDetailBean.ActivityListBean> listeners){
        this.itemClickListeners = listeners;
    }
}
