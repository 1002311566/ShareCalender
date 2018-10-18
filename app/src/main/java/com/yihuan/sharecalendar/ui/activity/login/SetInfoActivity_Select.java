package com.yihuan.sharecalendar.ui.activity.login;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.widget.LinearLayout;

import com.yihuan.sharecalendar.R;
import com.yihuan.sharecalendar.global.Constants;
import com.yihuan.sharecalendar.global.base.BaseActivity;
import com.yihuan.sharecalendar.global.base.BasePresenter;
import com.yihuan.sharecalendar.modle.bean.login.CityBean;
import com.yihuan.sharecalendar.modle.bean.login.ConstellationBean;
import com.yihuan.sharecalendar.modle.bean.login.ProvinceBean;
import com.yihuan.sharecalendar.modle.bean.login.SetInfoBean;
import com.yihuan.sharecalendar.presenter.SetInfo_Select__Presenter;
import com.yihuan.sharecalendar.presenter.contract.SetInfo_Select_Contract;
import com.yihuan.sharecalendar.ui.adapter.recyclerview.listener.OnRvItemClickListener;
import com.yihuan.sharecalendar.ui.adapter.recyclerview.SetInfo_Select_RvAdapter;
import com.yihuan.sharecalendar.ui.view.other.RvDividerItem;
import com.yihuan.sharecalendar.ui.view.TitleView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Ronny on 2017/9/9.
 * TODO 注册成功——>完善信息——>选择界面（性别、星座、年代、所在地）
 */

public class SetInfoActivity_Select extends BaseActivity<SetInfo_Select__Presenter> implements SetInfo_Select_Contract.View {

    @BindView(R.id.rv_select)
    RecyclerView rvSelect;
    private String info_type;
    private TitleView titleView;
    private List<String> mList;
    private SetInfo_Select_RvAdapter mAdapter;
    private List<String> mSexList;
    private List<String> mYearList;
    private List<ConstellationBean> mConstellationList;
    private List<ProvinceBean> mProvinceList;
    private List<CityBean> mCityList;
    private boolean isCityUI;//是否是城市选择界面
    private SetInfoBean mInfoBean;//装有选择的信息

    @Override
    protected BasePresenter setPresenter() {
        return new SetInfo_Select__Presenter(this);
    }

    @Override
    protected void initTitleView(TitleView titleView) {
        this.titleView = titleView;
        Intent intent = getIntent();
        if (intent != null) {
            info_type = intent.getStringExtra(Constants.INTENT_INFO_SELECT_TYPE);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_set_info_select;
    }

    @Override
    protected void initView() {
        mInfoBean = new SetInfoBean();
        mList = new ArrayList<>();
        mAdapter = new SetInfo_Select_RvAdapter(mList);
        rvSelect.setAdapter(mAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvSelect.setLayoutManager(linearLayoutManager);
        rvSelect.addItemDecoration(new RvDividerItem(this, LinearLayout.VERTICAL));

        //首先判断要加载什么信息
        if (info_type != null) {
            switch (info_type) {
                case Constants.INTENT_INFO_SELECT_TYPE_SEX:
                    titleView.setCenterText("性别");
                    mSexList = getSexList();
                    mAdapter.setDataList(mSexList);
                    break;
                case Constants.INTENT_INFO_SELECT_TYPE_YEAR:
                    titleView.setCenterText("年龄");
                    mYearList = getYearList();
                    mAdapter.setDataList(mYearList);
                    break;
                case Constants.INTENT_INFO_SELECT_TYPE_CONSTELLATION:
                    titleView.setCenterText("星座");
                    mPresenter.getConstellationList();
                    break;
                case Constants.INTENT_INFO_SELECT_TYPE_ADDRESS:
                    titleView.setCenterText("省");
                    mPresenter.getAddressProvinceList();
                    break;
            }
        }
        setListener();
    }

    private void setListener() {
        mAdapter.setOnRvItemClickListener(new OnRvItemClickListener<SetInfo_Select_RvAdapter.ItemViewHolder, String>() {


            @Override
            public void onItemClick(SetInfo_Select_RvAdapter.ItemViewHolder holder, int position, List<String> mList) {

                //todo 省份信息
                if (info_type.equals(Constants.INTENT_INFO_SELECT_TYPE_ADDRESS) && !isCityUI) {
                    if (mProvinceList != null && mProvinceList.size() > position && mProvinceList.get(position) != null) {
                        titleView.setCenterText("市");
                        mInfoBean.provinceName = mProvinceList.get(position).getName();
                        mInfoBean.provinceId = mProvinceList.get(position).getId();
                        mPresenter.getAddressCityList(mInfoBean.provinceId + "");
                        return;
                    }

                }
                //todo 城市信息
                if (isCityUI) {//点击了某个城市
                    mInfoBean.cityId = mCityList.get(position).getId();
                    mInfoBean.cityName = mCityList.get(position).getName();
                } else {
                    //todo 性别，星座，年龄在这里赋值
                    String str = holder.tvContent.getText().toString().trim();
                    switch (info_type) {
                        case Constants.INTENT_INFO_SELECT_TYPE_SEX://todo 性别
                            mInfoBean.sex = str;
                            break;
                        case Constants.INTENT_INFO_SELECT_TYPE_YEAR://todo 年代
                            mInfoBean.year = str;
                            break;
                        case Constants.INTENT_INFO_SELECT_TYPE_CONSTELLATION://todo 星座
                            mInfoBean.constellationDetail = str;
                            if (mConstellationList != null && mConstellationList.size() > position) {
                                mInfoBean.constellationId = mConstellationList.get(position).id;
                                mInfoBean.constellationName = mConstellationList.get(position).name;
                            }
                            break;
                    }
                }
                Intent intent = new Intent();
                intent.putExtra(Constants.INTENT_INFO_SELELCT_DATA, mInfoBean);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    @Override
    protected void refreshData() {

    }

    @Override
    public void getConstellationListOK(List<ConstellationBean> list) {
        mConstellationList = list;
        mList.clear();
        for (ConstellationBean bean : list) {
            mList.add(bean.getString());
        }
        mAdapter.setDataList(mList);
    }

    @Override
    public void getAddressProvinceListOK(List<ProvinceBean> list) {
        mProvinceList = list;
        mList.clear();
        for (ProvinceBean bean : list) {
            mList.add(bean.getName());
        }
        mAdapter.setDataList(mList);
        isCityUI = false;
    }

    @Override
    public void getAddressCityListOK(List<CityBean> list) {
        mCityList = list;
        mList.clear();
        for (CityBean bean : list) {
            mList.add(bean.getName());
        }
        mAdapter.setDataList(mList);
        isCityUI = true;
    }

    private List<String> getYearList() {
        ArrayList yearList = new ArrayList<>();
        for (int i = 2020; i >= 1940; i-=5) {
            yearList.add(String.format("%02d",i%100) + "后");
        }
        return yearList;
    }

    private List<String> getSexList() {
        ArrayList sexList = new ArrayList<>();
        sexList.add("男");
        sexList.add("女");
        sexList.add("保密");

        return sexList;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && isCityUI) {
            mPresenter.getAddressProvinceList();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
