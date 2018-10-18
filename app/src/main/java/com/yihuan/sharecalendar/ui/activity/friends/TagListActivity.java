package com.yihuan.sharecalendar.ui.activity.friends;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;

import com.yihuan.sharecalendar.R;
import com.yihuan.sharecalendar.global.base.BaseActivity;
import com.yihuan.sharecalendar.global.base.BasePresenter;
import com.yihuan.sharecalendar.modle.bean.friend.TagListBean;
import com.yihuan.sharecalendar.presenter.TagListPresenter;
import com.yihuan.sharecalendar.presenter.contract.TagListContract;
import com.yihuan.sharecalendar.ui.adapter.recyclerview.TagListRvAdapter;
import com.yihuan.sharecalendar.ui.adapter.recyclerview.listener.OnRvItemClickAndAddListener;
import com.yihuan.sharecalendar.ui.view.TitleView;
import com.yihuan.sharecalendar.utils.UIUtils;

import java.util.List;

import butterknife.BindView;

/**
 * Created by Ronny on 2017/9/25.
 * 标签列表
 */

public class TagListActivity extends BaseActivity<TagListPresenter> implements TagListContract.View {
    @BindView(R.id.rv_recyclerview)
    RecyclerView rvRecyclerview;
    private TagListRvAdapter tagListRvAdapter;

    @Override
    protected BasePresenter setPresenter() {
        return new TagListPresenter(this);
    }

    @Override
    protected void initTitleView(TitleView titleView) {
        titleView.setCenterText("标签");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_recyclerview;
    }

    @Override
    protected void initView() {
        tagListRvAdapter = new TagListRvAdapter();
        UIUtils.initRecyclerView(this, rvRecyclerview, tagListRvAdapter, true);
        rvRecyclerview.setBackgroundColor(getResources().getColor(R.color.color_gray_split_f5));

        setListener();
        refreshData();
    }

    private void setListener() {
        tagListRvAdapter.setOnRvItemClickListener2(new OnRvItemClickAndAddListener<TagListBean>() {

            @Override
            public void onItemClick(int position, List<TagListBean> list) {
                //todo 点击进入编辑标签页面
                CreateTagActivity.startSelf(list.get(position).getGroupName(), list.get(position).getId(), TagListActivity.this);
            }

            @Override
            public void onAddClick() {
                //todo 新增标签
                startActivity(new Intent(TagListActivity.this, CreateTagActivity.class));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshData();
    }

    @Override
    protected void refreshData() {
        mPresenter.getTagList();
    }

    @Override
    public void onGetListOK(List<TagListBean> listBeen) {
        tagListRvAdapter.setDataList(listBeen);
    }
}
