package com.yihuan.sharecalendar.ui.activity.setting;

import android.Manifest;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;

import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.ProgressStyle;
import com.yihuan.sharecalendar.R;
import com.yihuan.sharecalendar.global.App;
import com.yihuan.sharecalendar.global.Constants;
import com.yihuan.sharecalendar.global.base.BaseActivity;
import com.yihuan.sharecalendar.global.base.BasePresenter;
import com.yihuan.sharecalendar.modle.bean.settting.ThemeListBean;
import com.yihuan.sharecalendar.presenter.ChangeThemePresenter;
import com.yihuan.sharecalendar.presenter.contract.ChangeThemeContract;
import com.yihuan.sharecalendar.ui.adapter.recyclerview.ChangeThemeRvAdapter;
import com.yihuan.sharecalendar.ui.adapter.recyclerview.listener.OnRvItemAddAndDeleteAndClickListener;
import com.yihuan.sharecalendar.ui.adapter.recyclerview.listener.OnRvItemClickAndAddListener;
import com.yihuan.sharecalendar.ui.view.TitleView;
import com.yihuan.sharecalendar.ui.view.dialog.DeleteThemeDialog;
import com.yihuan.sharecalendar.ui.view.dialog.listener.OnEnterAndCancelListener;
import com.yihuan.sharecalendar.utils.FileUtils;
import com.yihuan.sharecalendar.utils.UIUtils;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;

import java.util.List;

import butterknife.BindView;

import static com.yihuan.sharecalendar.ui.activity.setting.SetHeaderActivity.REQUEST_CODE_CHOOSE;
import static com.yihuan.sharecalendar.ui.activity.setting.SetHeaderActivity.REQUEST_PERMISSION_READ;

/**
 * Created by Ronny on 2017/9/26.
 * 更换皮肤
 */

public class ChangeThemeActivity extends BaseActivity<ChangeThemePresenter> implements ChangeThemeContract.View {
    @BindView(R.id.rv_recyclerview)
    LRecyclerView recyclerView;
    private ChangeThemeRvAdapter changeThemeRvAdapter;

    private String filepath;//图片路径
    List<Uri> mSelected;//选中的图片集合
    private String selectId;//选择的皮肤id
    private String selectUrl;//保存图片的url


    @Override
    protected BasePresenter setPresenter() {
        return new ChangeThemePresenter(this);
    }

    @Override
    protected void initTitleView(TitleView titleView) {
        titleView.setCenterText("更换皮肤");
        titleView.setRightText("保存");
        titleView.setOnRightClickListener(new TitleView.OnRightClickListener() {
            @Override
            public void onRightListener(View v) {
                //todo 保存
                mPresenter.selectTheme(selectId);
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_l_recyclerview;
    }

    @Override
    protected void initView() {
        changeThemeRvAdapter = new ChangeThemeRvAdapter();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        final LRecyclerViewAdapter lRecyclerViewAdapter = new LRecyclerViewAdapter(changeThemeRvAdapter);
        recyclerView.setAdapter(lRecyclerViewAdapter);
        recyclerView.setFooterViewColor(R.color.color_gray_split, R.color.color_text_black_666, R.color.calendar_bg_color);
        recyclerView.setLayoutManager(gridLayoutManager);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (position == 0 || position == lRecyclerViewAdapter.getItemCount() - 1)//todo 用于展示加载动画布局
                    return 2;
                return 1;
            }
        });
        recyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallSpinFadeLoader);//设置统一的加载更多动画

        setListener();
        refreshData();
    }

    private void setListener() {
        changeThemeRvAdapter.setOnDefaultListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo 默认皮肤
                selectId = "-1";
            }
        });

        changeThemeRvAdapter.setOnRvItemClickAndAddListener(new OnRvItemAddAndDeleteAndClickListener<ThemeListBean>() {
            @Override
            public void onItemClick(int position, List<ThemeListBean> list) {
                //todo 选择皮肤
                selectId = list.get(position).getId() + "";
                selectUrl = list.get(position).getImage();
            }

            @Override
            public void onAddClick() {
                //todo 上传皮肤
                toSelectPhoto();
            }

            @Override
            public void onDeleteClick(final int position, final List<ThemeListBean> mList) {
                //todo 删除皮肤
                DeleteThemeDialog dialog = new DeleteThemeDialog(ChangeThemeActivity.this);
                dialog.show();
                dialog.setOnEnterAndCancelListener(new OnEnterAndCancelListener() {
                    @Override
                    public void onCancel() {
                    }

                    @Override
                    public void onEnter() {
                        mPresenter.deleteTheme(mList.get(position).getId());
                    }
                });

            }
        });

        recyclerView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData();
            }
        });

        recyclerView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                mPresenter.getThemeList(Constants.TYPE_LOADMORE);
            }
        });
    }

    /**
     * 去选择图片
     */
    private void toSelectPhoto() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            //todo request permission
            ActivityCompat.requestPermissions(this, new String[]
                    {Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PERMISSION_READ);
        } else {
            toPhotos();
        }
    }

    @Override
    protected void refreshData() {
        changeThemeRvAdapter.dataRefresh();
        mPresenter.getThemeList(Constants.TYPE_REFRESH);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PERMISSION_READ:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    toPhotos();
                } else {
                    showToast("请开启权限！");
                }
                break;
        }
    }

    private void toPhotos() {
        Matisse.from(ChangeThemeActivity.this)
                .choose(MimeType.of(MimeType.JPEG, MimeType.PNG)) // 选择 mime 的类型
                .countable(true)
                .maxSelectable(1) // 图片选择的最多数量
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                .thumbnailScale(0.85f) // 缩略图的比例
                .imageEngine(new GlideEngine()) // 使用的图片加载引擎
                .forResult(REQUEST_CODE_CHOOSE); // 设置作为标记的请求码
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {
            mSelected = Matisse.obtainResult(data);
            if (mSelected != null && mSelected.size() > 0) {
                Uri uri = mSelected.get(0);
                filepath = FileUtils.uriToPath(ChangeThemeActivity.this, uri);
                CropActivity.show(this, filepath, UIUtils.getScreenWidth(this)*3/5, (UIUtils.getScreenHeight(this)*3/5) * 1134/1334);
            }
        }else
        if (resultCode == Constants.REQUEST_CODE_3 && requestCode == CropActivity.REQUEST_CODE_CROP) {
            filepath = data.getStringExtra("crop_path");
            if (mPresenter != null) {
                //绑定View
                mPresenter.attachView(this);
                mPresenter.uploadTheme(filepath);
            }
        }
    }

    @Override
    public void onGetThemeList(List<ThemeListBean> list) {
        changeThemeRvAdapter.setDataList(list);
        recyclerView.refreshComplete(Constants.PAGE_SIZE);
    }

    @Override
    public void onSelectThemeOK() {
        App.getInstanse().setThemePath(selectUrl);
        finish();
    }

    @Override
    public void onUploadThemeOK() {
        recyclerView.setNoMore(false);
        refreshData();
    }

    @Override
    public void onDeleteThemeOK() {
        recyclerView.setNoMore(false);
        refreshData();
    }

    @Override
    public void onNoMore() {
        recyclerView.setNoMore(true);
    }
}
