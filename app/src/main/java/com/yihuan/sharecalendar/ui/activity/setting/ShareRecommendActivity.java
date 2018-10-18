package com.yihuan.sharecalendar.ui.activity.setting;

import android.support.v7.widget.GridLayoutManager;

import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.ProgressStyle;
import com.yihuan.sharecalendar.R;
import com.yihuan.sharecalendar.global.Constants;
import com.yihuan.sharecalendar.global.base.BaseActivity;
import com.yihuan.sharecalendar.global.base.BasePresenter;
import com.yihuan.sharecalendar.modle.bean.AdvertisingBean;
import com.yihuan.sharecalendar.presenter.ShareCommendPresenter;
import com.yihuan.sharecalendar.presenter.contract.ShareRecommendContract;
import com.yihuan.sharecalendar.ui.adapter.recyclerview.ShareRecommendRvAdapter;
import com.yihuan.sharecalendar.ui.adapter.recyclerview.listener.OnRvItemClickListeners;
import com.yihuan.sharecalendar.ui.view.TitleView;

import java.util.List;

import butterknife.BindView;

/**
 * Created by Ronny on 2018/1/15.
 * 分享推荐
 */

public class ShareRecommendActivity extends BaseActivity<ShareCommendPresenter> implements ShareRecommendContract.View {
    @BindView(R.id.rv_recyclerview)
    LRecyclerView rvRecyclerview;
    private ShareRecommendRvAdapter shareRecommendRvAdapter;

    @Override
    protected BasePresenter setPresenter() {
        return new ShareCommendPresenter(this);
    }

    @Override
    protected void initTitleView(TitleView titleView) {
        titleView.setCenterText("分享推荐");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_share_recommend;
    }

    @Override
    protected void initView() {
        shareRecommendRvAdapter = new ShareRecommendRvAdapter();
        final LRecyclerViewAdapter lRecyclerViewAdapter = new LRecyclerViewAdapter(shareRecommendRvAdapter);
        rvRecyclerview.setAdapter(lRecyclerViewAdapter);
        rvRecyclerview.setFooterViewColor(R.color.color_gray_split, R.color.color_text_black_666, R.color.calendar_bg_color);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        rvRecyclerview.setLayoutManager(gridLayoutManager);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (position == 0 || position == lRecyclerViewAdapter.getItemCount() - 1)//todo 用于展示加载动画布局
                    return 3;
                return 1;
            }
        });
        rvRecyclerview.setLoadingMoreProgressStyle(ProgressStyle.BallSpinFadeLoader);//设置统一的加载更多动画

        setListener();

        refreshData();
    }

    private void setListener() {
        rvRecyclerview.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData();
            }
        });

        rvRecyclerview.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                mPresenter.getAllShareRecommend(Constants.TYPE_LOADMORE);
            }
        });

        shareRecommendRvAdapter.setOnRvItemClickListeners(new OnRvItemClickListeners<AdvertisingBean>() {
            @Override
            public void onItemClick(int position, List<AdvertisingBean> mList) {
                AdvertisingDetailsActivity.startSelf(ShareRecommendActivity.this, mList.get(position));
            }
        });
    }

    @Override
    protected void refreshData() {
        //todo 获取广告列表
        mPresenter.getAllShareRecommend(Constants.TYPE_REFRESH);
    }

    @Override
    public void onGetAllShareRecommend(List<AdvertisingBean> mList) {
        shareRecommendRvAdapter.setDataList(mList);
        rvRecyclerview.refreshComplete(Constants.PAGE_SIZE);
    }

    @Override
    public void onNoMore() {
        super.onNoMore();
        rvRecyclerview.setNoMore(true);
        rvRecyclerview.refreshComplete(Constants.PAGE_SIZE);
    }
}
