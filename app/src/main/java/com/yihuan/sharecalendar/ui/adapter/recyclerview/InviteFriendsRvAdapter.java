package com.yihuan.sharecalendar.ui.adapter.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yihuan.sharecalendar.R;
import com.yihuan.sharecalendar.modle.bean.active.ActiveDetailsBean;
import com.yihuan.sharecalendar.utils.BeanToUtils;
import com.yihuan.sharecalendar.utils.UIUtils;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;


/**
 * Created by Ronny on 2017/9/21.
 */

public class InviteFriendsRvAdapter extends BaseRvAdapter<ActiveDetailsBean.ActivityInviteListBean> {
    private boolean isPublish;//是否是发起者

    public InviteFriendsRvAdapter() {
    }

    public InviteFriendsRvAdapter(boolean isPublish) {
        this.isPublish = isPublish;
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_active_details_invite;
    }

    @Override
    protected RecyclerView.ViewHolder onCreateItemHolder(View view) {
        ItemViewHolder itemViewHolder = new ItemViewHolder(view);
        itemViewHolder.ivHeader = (ImageView) view.findViewById(R.id.iv_header);
        itemViewHolder.tvState = (TextView) view.findViewById(R.id.tv_state);
        itemViewHolder.tvName = (TextView) view.findViewById(R.id.tv_name);
        itemViewHolder.tvResend = (TextView) view.findViewById(R.id.tv_resend);
        itemViewHolder.tvCancle = (TextView) view.findViewById(R.id.tv_cancel);
        itemViewHolder.ll_btn_layout = (LinearLayout) view.findViewById(R.id.ll_btn_layout);
        itemViewHolder.ll_btn_layout2 = (LinearLayout) view.findViewById(R.id.ll_btn_layout2);
        itemViewHolder.tvRefuse = (TextView) view.findViewById(R.id.tv_refuse);
        itemViewHolder.tvAgree = (TextView) view.findViewById(R.id.tv_agree);
        return itemViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemViewHolder h = (ItemViewHolder) holder;
        ActiveDetailsBean.ActivityInviteListBean activityInviteListBean = mList.get(position);

        h.ll_btn_layout.setVisibility(View.GONE);
        h.tvResend.setVisibility(View.GONE);
        h.tvCancle.setVisibility(View.GONE);
        h.ll_btn_layout2.setVisibility(View.GONE);
        h.tvAgree.setVisibility(View.GONE);
        h.tvRefuse.setVisibility(View.GONE);

        if (isPublish) {
            //todo 发起者逻辑--------------------------

            if (activityInviteListBean != null && activityInviteListBean.getStatus() != null) {
                //todo 根据状态来显示不同的按钮
                //todo -1: 拒绝活动、1: 接受活动、0: 未处理、2: 已取消 3 重新申请
                String status = activityInviteListBean.getStatus();
                switch (status) {
                    case "3":
                        h.ll_btn_layout2.setVisibility(View.VISIBLE);//todo 接受&拒绝
                        h.tvAgree.setVisibility(View.VISIBLE);
                        h.tvRefuse.setVisibility(View.VISIBLE);
                        break;
                    case "0":
                        h.ll_btn_layout.setVisibility(View.VISIBLE);//todo 重发&取消
                        h.tvResend.setVisibility(View.VISIBLE);
                        h.tvCancle.setVisibility(View.VISIBLE);
                        break;

                    case "1":
                        h.ll_btn_layout.setVisibility(View.VISIBLE);//todo 重发&取消
                        h.tvResend.setVisibility(View.GONE);//todo 隐藏重发
                        break;
                    case "-1":
                    case "2":
                        break;
                }
            }
        }
        setItemData(h, position);
        setItemListener(h, position);
    }

    private void setItemListener(ItemViewHolder h, final int position) {
        h.tvResend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemChildClickListener != null) {
                    itemChildClickListener.onResend(position, mList);
                }
            }
        });
        h.tvCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemChildClickListener.onCancle(position, mList);
            }
        });
        h.tvRefuse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemChildClickListener != null) {
                    itemChildClickListener.onRefuse(position, mList);
                }
            }
        });
        h.tvAgree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemChildClickListener != null) {
                    itemChildClickListener.onAgree(position, mList);
                }
            }
        });
    }

    private void setItemData(ItemViewHolder h, int i) {
        UIUtils.displayHeader(getContext(), mList.get(i).getInviteUserHeadImg(), h.ivHeader, R.mipmap.logo);
        h.tvName.setText(mList.get(i).getNickname());
        h.tvState.setText(mList.get(i).getStatusName());
        h.tvState.setTextColor(BeanToUtils.getInviteStatusColor(mList.get(i).getStatus()));
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {
        ImageView ivHeader;
        TextView tvState;
        TextView tvName;
        LinearLayout ll_btn_layout;
        TextView tvResend;
        TextView tvCancle;
        LinearLayout ll_btn_layout2;
        TextView tvRefuse;
        TextView tvAgree;

        public ItemViewHolder(View itemView) {
            super(itemView);
            AutoUtils.auto(itemView);
        }
    }

    private OnItemChildClickListener itemChildClickListener;

    public interface OnItemChildClickListener {
        void onResend(int position, List<ActiveDetailsBean.ActivityInviteListBean> mList);

        void onCancle(int position, List<ActiveDetailsBean.ActivityInviteListBean> mList);

        void onRefuse(int position, List<ActiveDetailsBean.ActivityInviteListBean> mList);

        void onAgree(int position, List<ActiveDetailsBean.ActivityInviteListBean> mList);
    }

    public void setOnItemChildClickListener(OnItemChildClickListener listener) {
        this.itemChildClickListener = listener;
    }
}
