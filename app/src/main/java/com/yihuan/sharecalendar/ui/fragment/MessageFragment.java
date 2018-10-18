package com.yihuan.sharecalendar.ui.fragment;

import android.content.Intent;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.yihuan.sharecalendar.R;
import com.yihuan.sharecalendar.global.Constants;
import com.yihuan.sharecalendar.global.base.BaseFragment;
import com.yihuan.sharecalendar.global.base.BasePresenter;
import com.yihuan.sharecalendar.modle.bean.home.MessageBean;
import com.yihuan.sharecalendar.presenter.MessageListPresenter;
import com.yihuan.sharecalendar.presenter.contract.MessageListContract;
import com.yihuan.sharecalendar.ui.activity.MainActivity;
import com.yihuan.sharecalendar.ui.activity.active.ActiveDetailsActivity_Receiver;
import com.yihuan.sharecalendar.ui.activity.hometitle.MessageActivity;
import com.yihuan.sharecalendar.ui.activity.setting.AddRemindActivity;
import com.yihuan.sharecalendar.ui.adapter.recyclerview.MessageRvAdapter;
import com.yihuan.sharecalendar.ui.adapter.recyclerview.listener.OnRvItemClickListener;
import com.yihuan.sharecalendar.ui.view.TitleView;
import com.yihuan.sharecalendar.ui.view.dialog.AdvertisingDialog;
import com.yihuan.sharecalendar.utils.UIUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static android.app.Activity.RESULT_CANCELED;

/**
 * Created by Ronny on 2018/1/26.
 */

public class MessageFragment extends BaseFragment<MessageListPresenter> implements MessageListContract.View {
    @BindView(R.id.rv_recyclerview)
    LRecyclerView rvRecyclerview;
    @BindView(R.id.ll_bottom_layout)
    LinearLayout llBottomLayout;
    @BindView(R.id.tv_select)
    TextView tvSelect;
    @BindView(R.id.tv_delete)
    TextView tvDelete;
    @BindView(R.id.title_view)
    TitleView titleView;
    @BindView(R.id.fl_content)
    FrameLayout flRootLayout;
    private MessageRvAdapter messageRvAdapter;

    @Override
    protected BasePresenter setPresenter() {
        return new MessageListPresenter(this);
    }

    protected void initTitleView(final TitleView titleView) {
        int stateBarHight = UIUtils.getStateBarHight(getActivity());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0, stateBarHight, 0, 0);
        titleView.setLayoutParams(layoutParams);

        titleView.setCenterText("消息");
        titleView.setRightText("编辑");//todo 编辑 & 完成
        titleView.setOnRightClickListener(new TitleView.OnRightClickListener() {
            @Override
            public void onRightListener(View v) {
                refreshUI(!messageRvAdapter.isShow());
            }
        });
        titleView.setLeftImage(R.mipmap.icon_home_menu);

        titleView.setOnLeftClickListener(new TitleView.OnLeftClickListener() {
            @Override
            public void onLeftClick() {
                ((MainActivity) getActivity()).toggle();
            }
        });
    }

    private void showBottomLayout(boolean show) {
        llBottomLayout.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_message;
    }

    @Override
    protected void initView() {
        initTitleView(titleView);
        messageRvAdapter = new MessageRvAdapter();
        rvRecyclerview.setItemAnimator(new DefaultItemAnimator());
        UIUtils.initLRecyclerView(getActivity(), rvRecyclerview, messageRvAdapter, true);
        setViewListener();
        refreshData();
    }

    @Override
    public void onResume() {
        super.onResume();
        //todo 暂时在这里先显示皮肤
        UIUtils.showTheme(getActivity(), flRootLayout);
        mPresenter.getMessageList(Constants.TYPE_REFRESH);
        resetSelecter();
    }

    private void resetSelecter() {
        tvSelect.setText("全选");
        refreshUI(false);
    }


    private void setViewListener() {
        messageRvAdapter.setOnRvItemClickListener(new OnRvItemClickListener<MessageRvAdapter.ItemViewHolder, MessageBean>() {
            @Override
            public void onItemClick(MessageRvAdapter.ItemViewHolder holder, int position, List<MessageBean> mList) {
                //todo 点击消息列表进行跳转  type类型， 1：通知信息, 2: 邀请信息, 3: 反馈回复信息, 4: 随意铃, 5:定制化提醒分享
                MessageBean messageBean = mList.get(position);
                if (messageBean == null) return;
                //todo 读消息
                mPresenter.readMsg(messageBean.getId());

                String attachField = messageBean.getBizId();
                int id = -1;
                if (attachField != null) {
                    id = Integer.parseInt(attachField);
                }
                String msgType = messageBean.getType();
                if (messageBean.getType() != null) {
                    if (msgType != null && msgType.equals("2")) {
                        //todo 接受者界面
                        ActiveDetailsActivity_Receiver.startForResult(getActivity(), id, Constants.REQUEST_CODE_1);
                    } else if (msgType != null && msgType.equals("5")) {
                        //todo 定制化详情
                        Intent intent = new Intent(getActivity(), AddRemindActivity.class);
                        if (id != -1) {
                            intent.putExtra(AddRemindActivity.REMIND_ID, id);
                        }
                        startActivity(intent);
                    }else if (msgType != null && msgType.equals("4")) {
                        //todo 随意令
                        ActiveDetailsActivity_Receiver.startForResult(getActivity(), id, Constants.REQUEST_CODE_1);
                    }
                }
            }
        });

        rvRecyclerview.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                mPresenter.getMessageList(Constants.TYPE_LOADMORE);
            }
        });

        rvRecyclerview.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData();
            }
        });

        messageRvAdapter.setOnDontRemindListener(new MessageRvAdapter.OnDontRemindListener() {
            @Override
            public void onDontRemind(int pos, List<MessageBean> list) {
                //todo 不再提醒
                mPresenter.dontRemind(list.get(pos).getId(), pos);
            }
        });
    }

    @Override
    protected void refreshData() {
        mPresenter.getMessageList(Constants.TYPE_REFRESH);
        mPresenter.getNewMsgCount();
    }


    @Override
    public void onGetMessageListOK(List<MessageBean> beanList) {
        messageRvAdapter.setDataList(beanList);
        rvRecyclerview.refreshComplete(20);
    }

    @Override
    public void onDontRemindOK(int pos) {
        messageRvAdapter.getDataList().get(pos).setReminderStatus("1");
        messageRvAdapter.notifyItemChanged(pos);
    }

    @Override
    public void onNoMore() {
        rvRecyclerview.setNoMore(true);
        rvRecyclerview.refreshComplete(Constants.PAGE_SIZE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.REQUEST_CODE_1 && resultCode == RESULT_CANCELED) {
            //todo 消息广告
            AdvertisingDialog dialog = new AdvertisingDialog(getActivity());
            dialog.show("2", "2", "1", "2");
        }
    }

    @OnClick({R.id.tv_select, R.id.tv_delete})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_select://todo 全选 & 取消全选
                messageRvAdapter.selectAll();
                tvSelect.setText(messageRvAdapter.isAllSelect() ? "取消全选" : "全选");
                break;
            case R.id.tv_delete://todo 删除
                deleteMessage();
                break;
        }
    }

    private void deleteMessage() {
        mPresenter.deleteMessage(messageRvAdapter.getDataList());
    }

    @Override
    public void onDeleteOK() {
        resetSelecter();
        showBottomLayout(false);
        refreshData();
    }

    @Override
    public void onGetNewMsgCount(Integer count) {
        ((MainActivity) getActivity()).refreshMsgCount(count.intValue());
    }

    @Override
    public void onReadMsgOK() {
        refreshData();
    }

    private void refreshUI(boolean isShow) {
        messageRvAdapter.setShow(isShow);
        titleView.setRightText(isShow ? "完成" : "编辑");
        showBottomLayout(isShow);
    }

}
