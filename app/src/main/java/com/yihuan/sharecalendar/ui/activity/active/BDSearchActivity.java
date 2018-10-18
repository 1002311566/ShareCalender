package com.yihuan.sharecalendar.ui.activity.active;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.WindowManager;
import android.widget.EditText;

import com.baidu.mapapi.search.core.CityInfo;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.geocode.GeoCodeOption;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiAddrInfo;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.baidu.mapapi.search.sug.OnGetSuggestionResultListener;
import com.baidu.mapapi.search.sug.SuggestionResult;
import com.baidu.mapapi.search.sug.SuggestionSearch;
import com.baidu.mapapi.search.sug.SuggestionSearchOption;
import com.yihuan.sharecalendar.R;
import com.yihuan.sharecalendar.global.base.BaseActivity;
import com.yihuan.sharecalendar.global.base.BasePresenter;
import com.yihuan.sharecalendar.ui.adapter.recyclerview.BDSearchRvAdapter;
import com.yihuan.sharecalendar.ui.adapter.recyclerview.listener.OnRvItemClickListener;
import com.yihuan.sharecalendar.ui.view.TitleView;
import com.yihuan.sharecalendar.utils.UIUtils;

import java.util.List;

import butterknife.BindView;

/**
 * Created by Ronny on 2017/12/14.
 */

public class BDSearchActivity extends BaseActivity implements OnGetGeoCoderResultListener, OnGetSuggestionResultListener, OnGetPoiSearchResultListener {
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;

    private BDSearchRvAdapter bdSearchRvAdapter;
    private SuggestionSearch mSuggestionSearch;
//    private PoiSearch mPoiSearch;

    @Override
    protected BasePresenter setPresenter() {
        return null;
    }

    @Override
    protected void initTitleView(TitleView titleView) {
        titleView.setCenterText("搜索位置");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_baidu_search;
    }

    @Override
    protected void initView() {
        showSoftKey();
        bdSearchRvAdapter = new BDSearchRvAdapter();
        UIUtils.initRecyclerView(this, recyclerview, bdSearchRvAdapter, false);
        setListener();

        //todo 地理编码
//        mGeoCoder = GeoCoder.newInstance();
//        mGeoCoder.setOnGetGeoCodeResultListener(this);

        //todo 建议搜索
        mSuggestionSearch = SuggestionSearch.newInstance();
        mSuggestionSearch.setOnGetSuggestionResultListener(this);

        //todo poi检索
//        mPoiSearch = PoiSearch.newInstance();
//        mPoiSearch.setOnGetPoiSearchResultListener(this);

    }

    private void setListener() {
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!TextUtils.isEmpty(s.toString())){
//                    mGeoCoder.geocode(new GeoCodeOption()
//                    .city("广州")
//                    .address(s.toString()));

                    mSuggestionSearch.requestSuggestion(new SuggestionSearchOption()
                    .keyword(s.toString())
                    .city("广州"));

//                    mPoiSearch.searchInCity((new PoiCitySearchOption())
//                            .city("广州")
//                            .keyword(s.toString())
//                            .pageNum(10));
                }
            }
        });

        bdSearchRvAdapter.setOnRvItemClickListener(new OnRvItemClickListener<BDSearchRvAdapter.ItemViewHolder, SuggestionResult.SuggestionInfo>() {
            @Override
            public void onItemClick(BDSearchRvAdapter.ItemViewHolder holder, int position, List<SuggestionResult.SuggestionInfo> mList) {

                //todo 地址点击
                Intent intent = new Intent();
                intent.putExtra("bean", mList.get(position));
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    @Override
    protected void refreshData() {
    }

    @Override
    public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {
        //todo 正向地理编码
        String address = geoCodeResult.getAddress();
    }

    @Override
    public void onGetReverseGeoCodeResult(ReverseGeoCodeResult reverseGeoCodeResult) {
        //todo 反向地理编码
        String address = reverseGeoCodeResult.getAddress();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSuggestionSearch.destroy();
//        mPoiSearch.destroy();
    }

    //todo 建议搜索
    @Override
    public void onGetSuggestionResult(SuggestionResult suggestionResult) {
        List<SuggestionResult.SuggestionInfo> allSuggestions = suggestionResult.getAllSuggestions();
        bdSearchRvAdapter.setDataList(allSuggestions);
    }

    //todo ----------------------------------poi 检索--------------

    @Override
    public void onGetPoiResult(PoiResult poiResult) {
        //获取POI检索结果
        List<PoiAddrInfo> allAddr = poiResult.getAllAddr();
        List<PoiInfo> allPoi = poiResult.getAllPoi();
        List<CityInfo> suggestCityList = poiResult.getSuggestCityList();
    }

    @Override
    public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {
        //获取Place详情页检索结果
    }

    @Override
    public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {

    }
}
