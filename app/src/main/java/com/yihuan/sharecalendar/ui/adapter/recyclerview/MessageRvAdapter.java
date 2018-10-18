package com.yihuan.sharecalendar.ui.adapter.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.yihuan.sharecalendar.R;
import com.yihuan.sharecalendar.modle.bean.home.MessageBean;
import com.yihuan.sharecalendar.modle.data.TimeUtils;
import com.yihuan.sharecalendar.ui.adapter.recyclerview.listener.OnRvItemClickListener;
import com.yihuan.sharecalendar.utils.BeanToUtils;
import com.yihuan.sharecalendar.utils.StringUtils;
import com.yihuan.sharecalendar.utils.UIUtils;

import java.util.List;

/**
 * Created by Ronny on 2017/9/21.
 */

public class MessageRvAdapter extends BaseRvAdapter<MessageBean> {

    private OnRvItemClickListener listener;
    private boolean isShow;//显示多选框
    private boolean isAllSelect; //全选

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_message;
    }

    @Override
    protected RecyclerView.ViewHolder onCreateItemHolder(View view) {
        ItemViewHolder holder = new ItemViewHolder(view);
        holder.ivHeader = (ImageView) view.findViewById(R.id.iv_header);
        holder.tvNickname = (TextView) view.findViewById(R.id.tv_nickname);
        holder.tvType = (TextView) view.findViewById(R.id.tv_type);
        holder.tvContent = (TextView) view.findViewById(R.id.tv_content);
        holder.tvBtnDontRemind = (TextView) view.findViewById(R.id.tv_btn_dont_remind);
        holder.tvState = (TextView) view.findViewById(R.id.tv_state);
        holder.cbSelect = (CheckBox) view.findViewById(R.id.cb_select);
        holder.tvTime = (TextView) view.findViewById(R.id.tv_create_time);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final ItemViewHolder h = (ItemViewHolder) holder;
        //todo 初始化控件
        h.cbSelect.setVisibility(isShow ? View.VISIBLE : View.GONE);
        final MessageBean bean = mList.get(position);
        h.itemView.setSelected(bean.isSelect);

        h.tvTime.setText(bean.getCreateTime());
        UIUtils.displayHeader(getContext(), bean.getHeaderImage(), h.ivHeader, R.mipmap.logo);
        h.tvNickname.setText(StringUtils.nullToStr(bean.getNickname()));
        h.tvType.setText(StringUtils.nullToStr(bean.getTypeName()));
        h.tvContent.setText("邀请你参加“" + StringUtils.nullToStr(bean.getContents()) + "”");

        //todo 显示或隐藏活动活动状态和提醒按钮 type类型， 1：通知信息, 2: 邀请信息, 3: 反馈回复信息, 4: 随意铃, 5:定制化提醒分享
        if (bean.getType() != null && bean.getType().equals("4")) {
            h.tvBtnDontRemind.setVisibility(BeanToUtils.isShowDontRemind(bean.getReminderStatus()) ? View.VISIBLE : View.GONE);//todo 显示不再提醒按钮
//            h.tvContent.setText("邀请你参加“" + StringUtils.nullToStr(bean.getContents()) + "”离活动开始还剩"++ "天哦");
        } else {
            h.tvBtnDontRemind.setVisibility(View.GONE);
        }
        ////当type为2（邀请信息）的时候，这个字段属性才有值 -1: 拒绝活动、1: 接受活动、0: 未处理、2: 活动已取消
        String inviteStatus = bean.getInviteStatus();
        if (bean.getType() != null && bean.getType().equals("2")) {
            h.tvState.setText(BeanToUtils.getInviteStatusStr(inviteStatus));
            h.tvState.setTextColor(BeanToUtils.getInviteStatusColor(inviteStatus));
        } else {
            h.tvState.setText("");
        }

        if (isShow) {
            h.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    bean.isSelect = !bean.isSelect;
                    h.itemView.setSelected(bean.isSelect);
                }
            });
        } else {
            //todo 当不是编辑状态时才去设置其它按钮监听
            setViewListener(h, position, inviteStatus);
        }

    }

    private void setViewListener(final ItemViewHolder h, final int position, final String inviteStatus) {
        h.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null && inviteStatus != null) {
                    listener.onItemClick(h, position, mList);
                }
            }
        });

        h.tvBtnDontRemind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo 不再提醒
                if (dontRemindListener != null) {
                    dontRemindListener.onDontRemind(position, mList);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    public static class ItemViewHolder extends ViewHolder {
        ImageView ivHeader;
        TextView tvNickname;
        TextView tvType;
        TextView tvContent;
        TextView tvBtnDontRemind;
        TextView tvState;
        CheckBox cbSelect;//多选框
        TextView tvTime;

        public ItemViewHolder(View itemView) {
            super(itemView);
        }
    }

    private OnDontRemindListener dontRemindListener;

    public interface OnDontRemindListener {
        void onDontRemind(int pos, List<MessageBean> list);
    }

    public void setOnDontRemindListener(OnDontRemindListener listener) {
        this.dontRemindListener = listener;
    }

    public void setOnRvItemClickListener(OnRvItemClickListener<ItemViewHolder, MessageBean> listener) {
        this.listener = listener;
    }

    /**
     * 更改编辑状态
     *
     * @param isShow
     */
    public void setShow(boolean isShow) {
        this.isShow = isShow;
        notifyDataSetChanged();
    }

    /**
     * 获取当前是否是编辑状态
     *
     * @return
     */
    public boolean isShow() {
        return isShow;
    }

    public void selectAll() {
        if (mList != null && mList.size() > 0) {
            isAllSelect = !isAllSelect;
            for (MessageBean bean : mList) {
                bean.isSelect = isAllSelect;
            }
            notifyDataSetChanged();
        }
    }

    public boolean isAllSelect() {
        return isAllSelect;
    }
}
