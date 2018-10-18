package com.yihuan.sharecalendar.ui.activity.active;


import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.baidu.mapapi.search.sug.SuggestionResult;
import com.yihuan.sharecalendar.R;
import com.yihuan.sharecalendar.global.App;
import com.yihuan.sharecalendar.global.Constants;
import com.yihuan.sharecalendar.global.base.BaseActivity;
import com.yihuan.sharecalendar.global.base.BasePresenter;
import com.yihuan.sharecalendar.modle.bean.active.LocationBean;
import com.yihuan.sharecalendar.ui.adapter.recyclerview.BDPoiRvAdapter;
import com.yihuan.sharecalendar.ui.adapter.recyclerview.listener.OnRvItemClickListeners;
import com.yihuan.sharecalendar.ui.view.TitleView;
import com.yihuan.sharecalendar.utils.UIUtils;

import java.util.List;


/**
 * Created by Ronny on 2017/12/12.
 */

public class BDMapActivity extends BaseActivity implements View.OnClickListener, BaiduMap.OnMapStatusChangeListener, OnGetGeoCoderResultListener {
    public static final String LOCATIONBEAN = "location";
    TextView tvSearch;
    ImageView ivTo;
    MapView mapBaidu;
    RecyclerView recyclerView;

    private BaiduMap mBaiduMap;
    private LocationClient mLocationClient;
    private MyLocationListener listener;
    private boolean isFirstLocation = true;
    private LocationBean locationBean;//位置参数
    private LocationBean myLocationBean;//我的位置
    private ImageView ivCenter;
    private BDPoiRvAdapter bdPoiRvAdapter;
    private GeoCoder mSearch;

    @Override
    protected BasePresenter setPresenter() {
        return null;
    }

    @Override
    protected void initTitleView(TitleView titleView) {
        titleView.setCenterText("活动位置");
        titleView.setRightText("保存");
        titleView.setOnRightClickListener(new TitleView.OnRightClickListener() {
            @Override
            public void onRightListener(View v) {
                Intent intent = new Intent();
                intent.putExtra(LOCATIONBEAN, locationBean);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_baidu_map;
    }

    @Override
    protected void initView() {
        checkPermission();

        locationBean = new LocationBean();
        myLocationBean = new LocationBean();

        tvSearch = (TextView) findViewById(R.id.tv_search);
        mapBaidu = (MapView) findViewById(R.id.map_baidu);
        ivTo = (ImageView) findViewById(R.id.iv_to_location);
        ivCenter = (ImageView) findViewById(R.id.iv_center);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        bdPoiRvAdapter = new BDPoiRvAdapter();
        UIUtils.initRecyclerView(this, recyclerView, bdPoiRvAdapter, true);

        tvSearch.setOnClickListener(this);
        ivTo.setOnClickListener(this);
        setViewListener();

        //todo 地图类
        mBaiduMap = mapBaidu.getMap();
        mBaiduMap.setMapStatus(MapStatusUpdateFactory.zoomTo(18));
        //todo 开启定位
        mBaiduMap.setMyLocationEnabled(true);
        setLocationParams();
        //todo 逆向编码
        mSearch = GeoCoder.newInstance();
        mSearch.setOnGetGeoCodeResultListener(this);
        //todo 注册地图状态改变
        mBaiduMap.setOnMapStatusChangeListener(this);
    }

    private void setViewListener() {
        bdPoiRvAdapter.setOnRvItemClickListeners(new OnRvItemClickListeners<PoiInfo>() {
            @Override
            public void onItemClick(int position, List<PoiInfo> mList) {
                locationBean.addressStr = mList.get(position).name;
                locationBean.area = mList.get(position).address;
                tvSearch.setText(locationBean.addressStr);
                if (mList.get(position).location != null) {
                    locationBean.latitude = mList.get(position).location.latitude;
                    locationBean.longitude = mList.get(position).location.longitude;
                    moveToLocation(locationBean.latitude, locationBean.longitude);
                }
                bdPoiRvAdapter.notifyDataSetChanged();
            }
        });
    }


    private void setLocationParams() {
        listener = new MyLocationListener();
        mLocationClient = new LocationClient(App.getInstanse());
        mLocationClient.registerLocationListener(listener);

        LocationClientOption option = new LocationClientOption();//todo 定位参数：定位模式，经纬度类型，定位次数
        option.setLocationMode(LocationMode.Hight_Accuracy);//todo 可选，设置定位模式，默认高精度
        option.setCoorType("bd09ll");//todo 可选，设置返回经纬度坐标类型，默认gcj02
        option.setScanSpan(5000);//todo 可选，设置发起定位请求的间隔，int类型，单位ms
        option.setOpenGps(true);//todo 可选，设置是否使用gps，默认false
        option.setLocationNotify(true);//todo 可选，设置是否当GPS有效时按照1S/1次频率输出GPS结果，默认false
        option.setIgnoreKillProcess(false);//todo 可选，定位SDK内部是一个service，并放到了独立进程。
        option.SetIgnoreCacheException(false);//todo 可选，设置是否收集Crash信息，默认收集，即参数为false
        option.setWifiCacheTimeOut(5 * 60 * 1000);//todo 可选，7.2版本新增能力 如果设置了该接口，首次启动定位时，会先判断当前WiFi是否超出有效期，若超出有效期，会先重新扫描WiFi，然后定位
        option.setEnableSimulateGps(false);//todo 可选，设置是否需要过滤GPS仿真结果，默认需要，即参数为false
        option.setIsNeedAddress(true);//todo 允许获取地址信息
        option.setIsNeedLocationPoiList(true);//todo 允许获取周斌poi
        mLocationClient.setLocOption(option);

        //todo 开启
        mLocationClient.start();
    }


    @Override
    protected void refreshData() {
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapBaidu.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapBaidu.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSearch.destroy();
        mapBaidu.onDestroy();
    }

    //todo----------------------地图状态改变监听--------------------------
    @Override
    public void onMapStatusChangeStart(MapStatus mapStatus) {

    }

    @Override
    public void onMapStatusChangeStart(MapStatus mapStatus, int i) {

    }

    @Override
    public void onMapStatusChange(MapStatus mapStatus) {

    }

    @Override
    public void onMapStatusChangeFinish(MapStatus mapStatus) {
        LatLng target = mapStatus.target;//todo 地图中心点
        //todo 逆向编码
        mSearch.reverseGeoCode(new ReverseGeoCodeOption().location(target));
    }
    //todo ---------------------------------end------------------------------

    //todo --------------定位监听类------------------------
    class MyLocationListener extends BDAbstractLocationListener {
        /**
         * 异步定位监听
         *
         * @param location
         */
        @Override
        public void onReceiveLocation(BDLocation location) {
            // 构造定位数据
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius()) // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(0).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            myLocationBean.addressStr = location.getAddrStr();
            myLocationBean.area = location.getAddress().address;
            myLocationBean.latitude = location.getLatitude();
            myLocationBean.longitude = location.getLongitude();
            //todo 获取周边poi
            // 设置定位数据
            mBaiduMap.setMyLocationData(locData);
            //todo 第一次定位移动到自己位置
            if (isFirstLocation) {
                isFirstLocation = false;
                moveToLocation(location.getLatitude(), location.getLongitude());
                //todo 逆向编码
                LatLng point = new LatLng(location.getLatitude(), location.getLongitude());
                mSearch.reverseGeoCode(new ReverseGeoCodeOption().location(point));
            }
        }
    }
    //todo ---------------------------------end------------------------------

    //todo -----------------地理编码监听类---------------------------------
    @Override
    public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {
        //todo 正向编码
    }

    @Override
    public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
        //todo 逆向编码
        if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
            //没有检索到结果
            showToast("没有搜索到结果");
            return;
        }
        List<PoiInfo> poiList = result.getPoiList();
//        if (poiList != null && poiList.size() > 0 && poiList.get(0) != null) {
//            PoiInfo poiInfo = poiList.get(0);
//            locationBean.addressStr = poiInfo.name;
//            if (poiInfo.location != null) {
//                locationBean.latitude = poiInfo.location.latitude;
//                locationBean.longitude = poiInfo.location.longitude;
//            }
//        }
        bdPoiRvAdapter.setDataList(poiList);
    }

    //todo ----------------------------end------------------------------------
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_search:
                toSearch();
                break;
            case R.id.iv_to_location:
                toMyLocation();
                break;
        }
    }

    private void toSearch() {
        startActivityForResult(new Intent(this, BDSearchActivity.class), Constants.REQUEST_CODE_1);
        openAnim();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.REQUEST_CODE_1 && resultCode == RESULT_OK) {
            if (data != null && data.hasExtra("bean")) {
                SuggestionResult.SuggestionInfo bean = data.getParcelableExtra("bean");
                String address = bean.city + bean.district + bean.key;
                locationBean.area = bean.city + bean.district;
                locationBean.addressStr = bean.key;
                tvSearch.setText(locationBean.addressStr);
                if (bean.pt != null) {
                    locationBean.latitude = bean.pt.latitude;
                    locationBean.longitude = bean.pt.longitude;
                    //todo 定义Maker坐标点
                    LatLng point = new LatLng(bean.pt.latitude, bean.pt.longitude);
                    //todo 构建Marker图标
                    BitmapDescriptor bitmap = BitmapDescriptorFactory
                            .fromResource(R.mipmap.icon_marker);
                    //todo 构建MarkerOption，用于在地图上添加Marker
                    OverlayOptions option = new MarkerOptions()
                            .position(point)
                            .icon(bitmap);
                    //todo 在地图上添加Marker，并显示
                    TextView textView = new TextView(BDMapActivity.this);
                    textView.setText(address);
                    textView.setMaxWidth(UIUtils.getScreenWidth(BDMapActivity.this) * 1 / 4);
                    textView.setBackgroundResource(R.drawable.shape_blue_r_bg);
                    textView.setPadding(30, 0, 30, 0);
                    InfoWindow infoWindow = new InfoWindow(textView, point, -bitmap.getBitmap().getHeight());
                    mBaiduMap.addOverlay(option);
                    mBaiduMap.showInfoWindow(infoWindow);

                    //todo 移动到
                    MapStatus.Builder builder = new MapStatus.Builder();
                    builder.target(new LatLng(bean.pt.latitude,bean.pt.longitude));
                    mBaiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));

                    //todo 逆向编码
                    mSearch.reverseGeoCode(new ReverseGeoCodeOption().location(point));
                }
            }
        }
    }


    private void toMyLocation() {
        if (myLocationBean != null) {
            LatLng ll = new LatLng(myLocationBean.latitude, myLocationBean.longitude);
            MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);
            mBaiduMap.animateMapStatus(u);
        }
    }

    /**
     * 移动到指定位置
     *
     * @param lat
     * @param lon
     */
    private void moveToLocation(Double lat, Double lon) {
        LatLng point = new LatLng(lat, lon);
        //设定中心点坐标
        MapStatus mMapStatus = new MapStatus.Builder()
                .target(point)//要移动的点
                .zoom(20)//放大地图到20倍
                .build();

        //定义MapStatusUpdate对象，以便描述地图状态将要发生的变化
        MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);

        //改变地图状态
        mBaiduMap.animateMapStatus(mMapStatusUpdate);
    }


    /**
     * 请求权限后重启地图
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1111) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mLocationClient.restart();
                new Handler(getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        toMyLocation();
                    }
                }, 1000);
            } else {
                showToast("请开启权限");
            }
        }
    }
}
