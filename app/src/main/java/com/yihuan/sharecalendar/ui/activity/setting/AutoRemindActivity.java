package com.yihuan.sharecalendar.ui.activity.setting;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.yihuan.sharecalendar.R;
import com.yihuan.sharecalendar.global.App;
import com.yihuan.sharecalendar.global.Constants;
import com.yihuan.sharecalendar.global.base.BaseActivity;
import com.yihuan.sharecalendar.global.base.BasePresenter;
import com.yihuan.sharecalendar.modle.bean.settting.AutoRemindListBean;
import com.yihuan.sharecalendar.presenter.AutoRemindPresenter;
import com.yihuan.sharecalendar.presenter.contract.AutoRemindContract;
import com.yihuan.sharecalendar.ui.adapter.recyclerview.RemindListRvAdapter;
import com.yihuan.sharecalendar.ui.adapter.recyclerview.listener.OnRvItemClickListeners;
import com.yihuan.sharecalendar.ui.view.TitleView;
import com.yihuan.sharecalendar.utils.UIUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Ronny on 2017/9/19.
 * 定制化提醒
 */

public class AutoRemindActivity extends BaseActivity<AutoRemindPresenter> implements AutoRemindContract.View {

    @BindView(R.id.rv_recyclerview)
    LRecyclerView recyclerView;
    @BindView(R.id.iv_add_remind)
    ImageView ivAddRemind;
    @BindView(R.id.ll_bottom_layout)
    LinearLayout llBottomLayout;
    @BindView(R.id.tv_select)
    TextView tvSelect;
    @BindView(R.id.tv_delete)
    TextView tvDelete;

    private RemindListRvAdapter remindListRvAdapter;

    @Override
    protected BasePresenter setPresenter() {
        return new AutoRemindPresenter(this);
    }

    @Override
    protected void initTitleView(TitleView titleView) {
        titleView.setCenterText("定制化提醒");
        titleView.setRightText("编辑");//todo 编辑 & 完成
        titleView.setOnRightClickListener(new TitleView.OnRightClickListener() {
            @Override
            public void onRightListener(View v) {
                refreshUI(!remindListRvAdapter.isShow());
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_auto_remind;
    }

    @Override
    protected void initView() {
        remindListRvAdapter = new RemindListRvAdapter();
        UIUtils.initLRecyclerView(this, recyclerView, remindListRvAdapter, true);
        setListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshData();
    }

    private void setListener() {

        recyclerView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData();
            }
        });

        recyclerView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                mPresenter.getAutoRemindList(Constants.TYPE_LOADMORE);
            }
        });

        remindListRvAdapter.setOnRvItemClickListeners(new OnRvItemClickListeners<AutoRemindListBean>() {
            @Override
            public void onItemClick(int position, List<AutoRemindListBean> mList) {
                //todo 提醒详情
                AutoRemindListBean autoRemindListBean = mList.get(position);
                if (autoRemindListBean != null) {
                    Intent intent = new Intent(AutoRemindActivity.this, AddRemindActivity.class);
                    intent.putExtra(AddRemindActivity.REMIND_ID, autoRemindListBean.getId());
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    protected void refreshData() {
        mPresenter.getAutoRemindList(Constants.TYPE_REFRESH);
    }

    @Override
    public void onGetAutoRemindListOK(List<AutoRemindListBean> beanList) {
        remindListRvAdapter.setDataList(beanList);
        recyclerView.refreshComplete(Constants.PAGE_SIZE);
    }

    @Override
    public void onDeleteRemindOK() {
        refreshData();
    }

    @OnClick({R.id.iv_add_remind, R.id.tv_select, R.id.tv_delete})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_select://todo 全选 & 取消全选
                remindListRvAdapter.selectAll();
                tvSelect.setText(remindListRvAdapter.isAllSelect() ? "取消全选" : "全选");
                break;
            case R.id.tv_delete://todo 删除
                deleteRemind();
                break;
            case R.id.iv_add_remind:
                //todo 新建提醒
                startActivityAnim(new Intent(this, AddRemindActivity.class));
                break;
        }
    }

    private void deleteRemind() {
        List<AutoRemindListBean> dataList = remindListRvAdapter.getDataList();
        mPresenter.deleteRemind(dataList);
    }

    private void refreshUI(boolean isShow) {
        remindListRvAdapter.setShow(isShow);
        getTitleView().setRightText(isShow ? "完成" : "编辑");
        showBottomLayout(isShow);
    }

    private void showBottomLayout(boolean show) {
        llBottomLayout.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onNoMore() {
        super.onNoMore();
        recyclerView.refreshComplete(Constants.PAGE_SIZE);
        recyclerView.setNoMore(true);
    }
}
