package com.yihuan.sharecalendar.ui.activity.active;

import android.Manifest;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yihuan.sharecalendar.R;
import com.yihuan.sharecalendar.global.App;
import com.yihuan.sharecalendar.global.Constants;
import com.yihuan.sharecalendar.global.base.BaseActivity;
import com.yihuan.sharecalendar.global.base.BasePresenter;
import com.yihuan.sharecalendar.http.help.Urls;
import com.yihuan.sharecalendar.modle.bean.active.AlarmTime;
import com.yihuan.sharecalendar.modle.bean.active.LocationBean;
import com.yihuan.sharecalendar.modle.bean.active.TimeBean;
import com.yihuan.sharecalendar.modle.bean.friend.FriendListBean;
import com.yihuan.sharecalendar.modle.calendar.ActiveBean;
import com.yihuan.sharecalendar.modle.data.TimeUtils;
import com.yihuan.sharecalendar.presenter.CreateActivePresenter;
import com.yihuan.sharecalendar.presenter.contract.CreateActiveContract;
import com.yihuan.sharecalendar.ui.adapter.recyclerview.ActiveImgListRvAdapter;
import com.yihuan.sharecalendar.ui.adapter.recyclerview.NewActiveRemindRvAdapter;
import com.yihuan.sharecalendar.ui.adapter.recyclerview.ShareFriendRvAdapter;
import com.yihuan.sharecalendar.ui.adapter.recyclerview.listener.OnRvItemAddAndDeleteListener;
import com.yihuan.sharecalendar.ui.adapter.recyclerview.listener.OnRvItemClickListener;
import com.yihuan.sharecalendar.ui.view.TitleView;
import com.yihuan.sharecalendar.ui.view.other.GridDividerItem;
import com.yihuan.sharecalendar.ui.view.pickerview.TimePickerDialog;
import com.yihuan.sharecalendar.ui.view.pickerview.data.Type;
import com.yihuan.sharecalendar.ui.view.pickerview.listener.OnDateSetListener;
import com.yihuan.sharecalendar.ui.view.popwin.CreateActivePeriodPop;
import com.yihuan.sharecalendar.ui.view.popwin.adapter.PeriodRvAdapter;
import com.yihuan.sharecalendar.utils.BeanToUtils;
import com.yihuan.sharecalendar.utils.FileUtils;
import com.yihuan.sharecalendar.utils.UIUtils;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;

import org.joda.time.DateTime;
import org.joda.time.Minutes;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Ronny on 2017/9/7.
 * 新建活动
 */

public class CreateActiveActivity extends BaseActivity<CreateActivePresenter> implements CreateActiveContract.View , OnDateSetListener {


    @BindView(R.id.et_title)
    EditText etTitle;
    @BindView(R.id.et_address)
    EditText etAddress;
    @BindView(R.id.iv_into_baidu)
    ImageView ivIntoBaidu;
    @BindView(R.id.ll_address)
    LinearLayout llAddress;
    @BindView(R.id.tv_all_day)
    TextView tvAllDay;
    @BindView(R.id.tv_start_time)
    TextView tvStartTime;
    @BindView(R.id.ll_start_time)
    LinearLayout llStartTime;
    @BindView(R.id.tv_end_time)
    TextView tvEndTime;
    @BindView(R.id.ll_end_time)
    LinearLayout llEndTime;
    @BindView(R.id.tv_period)
    TextView tvPeriod;
    @BindView(R.id.ll_period)
    LinearLayout llPeriod;
    @BindView(R.id.rv_add_remind)
    RecyclerView rvAddRemind;
    @BindView(R.id.et_other)
    EditText etOther;
    @BindView(R.id.rv_recyclerview)
    RecyclerView rvRecyclerview;
    @BindView(R.id.rv_imglist)
    RecyclerView rvImgList;
    private TimePickerDialog mDialogAll;

    private SelectFriendReceiver receiver;//todo 选择好友广播
    private ShareFriendRvAdapter mShareFriendAdapter;//todo 分享好友的适配器
    private NewActiveRemindRvAdapter mRemindAdapter;//todo 提醒的适配器
    private ActiveImgListRvAdapter mActiveImgListRvAdapter;//todo 添加图片的适配器

    private ActiveBean mActiveBean;
    private boolean clickEndTime;
    private List<String> toUploadImgList = new ArrayList<>();//todo 需要上传的图片
    private List<String> allImgList = new ArrayList<>();//todo 适配器显示的所有图片


    @Override
    protected BasePresenter setPresenter() {
        return new CreateActivePresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_create_active;
    }


    public static void startSelf(Activity activity, ActiveBean activeBean){
        Intent intent = new Intent(activity, CreateActiveActivity.class);
        intent.putExtra("activeBean", activeBean);
        activity.startActivity(intent);
    }

    @Override
    protected void initTitleView(TitleView titleView) {
        titleView.setCenterText("新建活动");
        titleView.setRightText("保存");
        titleView.setOnRightClickListener(new TitleView.OnRightClickListener() {
            @Override
            public void onRightListener(View v) {
                //todo 先判断有无添加图片
                if(toUploadImgList != null && toUploadImgList.size() > 0){
                    mPresenter.uploadImg(toUploadImgList);
                }else{
                    createNewActive();
                }
            }
        });
    }

    @Override
    protected void initView() {
        //todo 添加提醒
        mRemindAdapter = new NewActiveRemindRvAdapter();
        UIUtils.initRecyclerView(this, rvAddRemind, mRemindAdapter, false);

        //todo 共享好友
        mShareFriendAdapter = new ShareFriendRvAdapter();
        rvRecyclerview.setAdapter(mShareFriendAdapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 5);
        rvRecyclerview.setLayoutManager(gridLayoutManager);

        //todo 添加图片的适配器
        mActiveImgListRvAdapter = new ActiveImgListRvAdapter();
        rvImgList.setAdapter(mActiveImgListRvAdapter);
        GridLayoutManager gridLayoutManager1 = new GridLayoutManager(this, 5);
        GridDividerItem itemDecoration = new GridDividerItem(10, 5);
        rvImgList.setLayoutManager(gridLayoutManager1);
        rvImgList.addItemDecoration(itemDecoration);

        setListener();
        registSelectFriendReceiver();

        //todo 编辑活动-------start----------
        mActiveBean = getIntent().getParcelableExtra("activeBean");
        //todo ------------end--------------
        if(mActiveBean == null){//todo 新建
            mActiveBean = new ActiveBean();
            initBaseData();
        }else{//todo 编辑
            refreshAllUI();
        }

    }

    private void initTimeSelectDialog(long current) {
        mDialogAll = new TimePickerDialog.Builder()
                .setCallBack(this)
                .setCyclic(false)
                .setMinMillseconds(System.currentTimeMillis())
                .setMaxMillseconds(TimeUtils.getNext50Year())
                .setCurrentMillseconds(current)
                .setThemeColor(getResources().getColor(R.color.timepicker_dialog_bg))
                .setType(Type.ALL)
                .setWheelItemTextNormalColor(getResources().getColor(R.color.color_text_black_999))
                .setWheelItemTextSelectorColor(getResources().getColor(R.color.color_blue_00baff))
                .setWheelItemTextSize(12)
                .build();
    }

    /**
     * 编辑活动时调用
     */
    private void refreshAllUI() {
        getTitleView().setCenterText("编辑活动");
        mActiveBean.setPublish_user_id(App.getInstanse().getUserId());
        etTitle.setText(mActiveBean.getTitle());
        etAddress.setText(mActiveBean.getLocation());
        tvAllDay.setSelected(mActiveBean.isFullday());
        tvStartTime.setText(mActiveBean.getStart_time().toTime());
        tvEndTime.setText(mActiveBean.getEnd_time().toTime());
//        String c = TimeUtils.getCycle().get(Integer.parseInt(mActiveBean.getCycle()));
//        tvPeriod.setText(c);
        mRemindAdapter.setDataList(mActiveBean.getRemindList());
        etOther.setText(mActiveBean.getDes());
        mShareFriendAdapter.setDataList(mActiveBean.getShare_friend());
        List<String> imgList = mActiveBean.getImgList();
        if(imgList != null){
            refreshImgAdapter();
        }
    }

    private void registSelectFriendReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constants.ACTION_SELECT_FRIEND);
        receiver = new SelectFriendReceiver();
        registerReceiver(receiver, intentFilter);
    }

    private void initBaseData() {
        //todo 初始化开始结束时间
        mActiveBean.setStart_time(mPresenter.getCurrentTimeBean());
        mActiveBean.setEnd_time(mPresenter.getNextHourTimeBean());
        mActiveBean.setLon(0.0);
        mActiveBean.setLat(0.0);
        mActiveBean.setPublish_user_id(App.getInstanse().getUserId());
        refreshTimeUI();
        //todo 初始化周期
        mActiveBean.setCycle("0");
    }

    private void setListener() {
        //todo 添加共享好友监听
        mShareFriendAdapter.setOnRvItemClickListener3(new OnRvItemAddAndDeleteListener<FriendListBean>() {
            @Override
            public void onAddClick() {
                SelectContactActivity.startSelfForResult(CreateActiveActivity.this, mActiveBean.getShare_friend(), Constants.REQUEST_CODE_1);
            }

            @Override
            public void onDeleteClick(List<FriendListBean> mList, int position) {
                mActiveBean.getShare_friend().remove(position);
                mShareFriendAdapter.setDataList(mActiveBean.getShare_friend());
            }
        });

        mRemindAdapter.setOnRvItemClickListener(new OnRvItemClickListener<NewActiveRemindRvAdapter.ItemViewHolder, AlarmTime>() {
            @Override
            public void onItemClick(NewActiveRemindRvAdapter.ItemViewHolder holder, int position, List<AlarmTime> mList) {
                //todo 这里只需处理点击后选择提醒时间，添加和删除封装在适配器中
                showRemindPop(holder, position, mList);
            }
        });

        //todo 添加图片监听器
        mActiveImgListRvAdapter.setOnRvItemAddAndDeleteListener(new OnRvItemAddAndDeleteListener<String>() {
            @Override
            public void onAddClick() {
                //todo 检查权限
                boolean b = checkPermission(111, Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE);
                if(b){
                    try {
                        toSelectImg();
                    }catch (Exception e){
                        e.printStackTrace();
                        showToast("请检查权限");
                    }
                }
            }

            @Override
            public void onDeleteClick(List<String> mList, int position) {
                String path = mList.get(position);
                List<String> imgList = mActiveBean.getImgList();
                if(imgList != null && imgList.contains(path.replace(Urls.IMG_HEAD, ""))){
                    imgList.remove(path.replace(Urls.IMG_HEAD, ""));
                }
                if(toUploadImgList.contains(path)){
                    toUploadImgList.remove(path);
                }
                refreshImgAdapter();
            }
        });
    }

    /**
     * 添加提醒
     */
    private void showRemindPop(final NewActiveRemindRvAdapter.ItemViewHolder h, final int p, List list) {
        final CreateActivePeriodPop pop = new CreateActivePeriodPop(this, h.itemView, mActiveBean.getRemindLastPosition()[p]);
        pop.showRemind();
        pop.setOnRvItemClickListener(new OnRvItemClickListener<PeriodRvAdapter.ItemViewHolder, String>() {
            @Override
            public void onItemClick(PeriodRvAdapter.ItemViewHolder holder, int position, List<String> mList) {
                h.tvRemindTime.setText(mList.get(position));
                mActiveBean.getRemindLastPosition()[p] = position;
                mRemindAdapter.getAlarmTimeList().get(p).setAlarmTime(position);
                pop.dismiss();
            }
        });
    }

    private void createNewActive() {
        mActiveBean.setTitle(etTitle.getText().toString().trim());
        mActiveBean.setLocation(etAddress.getText().toString().trim());
        mActiveBean.setDes(etOther.getText().toString().trim());//说明
        List<AlarmTime> alarmTimeList = mRemindAdapter.getAlarmTimeList();
        mActiveBean.setRemindList(alarmTimeList);//提醒
        if(mActiveBean.getActive_id() == -1){
            mPresenter.createActive(mActiveBean);
        }else{
            mPresenter.editActive(mActiveBean);
        }
    }


    @Override
    protected void refreshData() {

    }

    @OnClick({R.id.iv_into_baidu, R.id.tv_all_day, R.id.ll_start_time, R.id.ll_end_time, R.id.ll_period,})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_into_baidu://todo 百度地图
                startActivityForResult(new Intent(this, BDMapActivity.class), Constants.REQUEST_CODE_1);
                break;
            case R.id.tv_all_day://todo 全天
                tvAllDay.setSelected(!tvAllDay.isSelected());
                mActiveBean.setFullday(tvAllDay.isSelected());
                refreshTimeUI();
                break;
            case R.id.ll_start_time://todo 开始时间
                //todo 弹出时间跳转
                initTimeSelectDialog(mActiveBean.getStart_time().getTimeInMillis());
                clickEndTime = false;
                mDialogAll.show(getSupportFragmentManager(), "all");
//                SelectTimeActivity.startForResult(this, SelectTimeActivity.INTO_START,mActiveBean.getStart_time(), Constants.REQUEST_CODE_2);
                break;
            case R.id.ll_end_time://todo 结束时间
                initTimeSelectDialog(mActiveBean.getEnd_time().getTimeInMillis());
                clickEndTime = true;
                mDialogAll.show(getSupportFragmentManager(), "all");

//                SelectTimeActivity.startForResult(this, SelectTimeActivity.INTO_END,mActiveBean.getEnd_time(),Constants.REQUEST_CODE_3);
                break;
            case R.id.ll_period://todo 周期
                showPeriodPop(view);
                break;
        }
    }

    private void refreshTimeUI() {
        tvStartTime.setText(mActiveBean.isFullday() ? mActiveBean.getStart_time().toYMD() : mActiveBean.getStart_time().toTime());
        tvEndTime.setText(mActiveBean.isFullday() ? mActiveBean.getEnd_time().toYMD() : mActiveBean.getEnd_time().toTime());
    }

    /**
     * 周期
     *
     * @param view
     */
    private void showPeriodPop(View view) {
        final CreateActivePeriodPop pop = new CreateActivePeriodPop(this, view, mActiveBean.getCycleLastPosition());
        pop.showPeriod();
        pop.setOnRvItemClickListener(new OnRvItemClickListener() {
            @Override
            public void onItemClick(RecyclerView.ViewHolder holder, int position, List mList) {
                mActiveBean.setCycle(position + "");
                mActiveBean.setCycleLastPosition(position);
                tvPeriod.setText(mList.get(position).toString());
                pop.dismiss();
            }
        });
    }

    @Override
    public void onCreateActiveOK() {
        sendHomeRefreshBroadCast();
        this.finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.REQUEST_CODE_1 && resultCode == RESULT_OK) {
            //todo 位置信息
            LocationBean locationBean = data.getParcelableExtra(BDMapActivity.LOCATIONBEAN);
            if(locationBean != null){
                mActiveBean.setLocation(locationBean.addressStr);
                mActiveBean.setArea(locationBean.area);
                mActiveBean.setLat(locationBean.latitude);
                mActiveBean.setLon(locationBean.longitude);
                etAddress.setText(locationBean.addressStr);
            }

        }else if (requestCode == 111 && resultCode == RESULT_OK) {
            List<Uri> uris = Matisse.obtainResult(data);
            for (Uri uri : uris){
                String path = FileUtils.uriToPath(CreateActiveActivity.this, uri);
                if(!toUploadImgList.contains(path)){
                    toUploadImgList.add(path);
                }
            }
            refreshImgAdapter();

        }
    }

    private void refreshImgAdapter() {
        allImgList.clear();
        if(mActiveBean.getImgList() != null){
            allImgList.addAll(BeanToUtils.addImgUrlHeader(mActiveBean.getImgList()));
        }
        allImgList.addAll(toUploadImgList);
        mActiveImgListRvAdapter.setDataList(allImgList);
    }

    private void showTime(TextView tv, TimeBean timeBean) {
        //todo 如果结束时间小于开始时间 则不去设置
        if (mActiveBean.isFullday()) {
            //todo 全天
            timeBean.cleanHour();
            tv.setText(timeBean.toYMD());
            refreshTimeUI();
        } else {
            tv.setText(timeBean.toTime());
        }
    }

    protected class SelectFriendReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.hasExtra(Constants.INTENT_FRIEND_LIST)) {
                List<FriendListBean> listBeen = intent.getParcelableArrayListExtra(Constants.INTENT_FRIEND_LIST);
                if (listBeen != null && listBeen.size() > 0) {
                    mActiveBean.getShare_friend().clear();
                    for (FriendListBean bean : listBeen) {
                        if (bean.isSelect) {
                                mActiveBean.getShare_friend().add(bean);
                        }
                    }
                    mShareFriendAdapter.setDataList(mActiveBean.getShare_friend());
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 111){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                toSelectImg();
            } else {
                showToast("请开启权限！");
            }
        }
    }

    private void toSelectImg() {
        Matisse.from(CreateActiveActivity.this)
                .choose(MimeType.of(MimeType.JPEG, MimeType.PNG)) // 选择 mime 的类型
                .countable(true)
                .maxSelectable(9-mActiveImgListRvAdapter.getItemCount()+1) // 图片选择的最多数量
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                .thumbnailScale(0.85f) // 缩略图的比例
                .imageEngine(new GlideEngine()) // 使用的图片加载引擎
                .forResult(111); // 设置作为标记的请求码
    }

    @Override
    public void onUploadImgOK(List<String> imgUrlList) {
        //todo 图片上传成功之后
        if(mActiveBean.getImgList() == null){
            mActiveBean.setImgList(new ArrayList<String>());
        }
        mActiveBean.getImgList().addAll(imgUrlList);
        createNewActive();
    }

    @Override
    public void onDateSet(com.yihuan.sharecalendar.ui.view.pickerview.TimePickerDialog timePickerView, long millseconds) {
        TimeBean timeBean = new TimeBean(millseconds);
        if(clickEndTime){
            clickEndTime(timeBean);
        }else{
            clickStartTime(timeBean);
        }
    }

    private void clickEndTime(TimeBean timeBean) {
        if(timeBean.toTime().compareTo(mActiveBean.getStart_time().toTime()) < 0){
            showToast("结束时间不能小于开始时间！");
            return;
        }
        mActiveBean.setEnd_time(timeBean);
        showTime(tvEndTime, timeBean);
    }

    private void clickStartTime(TimeBean timeBean){
        if(timeBean.toTime().compareTo(mActiveBean.getEnd_time().toTime()) > 0){
//            //todo 如果选择的时间大于结束时间，取原先时间跨度，在新的时间上顺推
//            TimeBean start_time = mActiveBean.getStart_time();
//            TimeBean end_time = mActiveBean.getEnd_time();
//            DateTime start = new DateTime(start_time.getYear(), start_time.getMonth()+1, start_time.getDay(), start_time.getHour(), start_time.getMinute());
//            DateTime end = new DateTime(end_time.getYear(), end_time.getMonth()+1, end_time.getDay(), end_time.getHour(), end_time.getMinute());
//            Minutes minutes = Minutes.minutesBetween(start, end);
//            DateTime current_start = new DateTime(timeBean.getYear(), timeBean.getMonth()+1, timeBean.getDay(), timeBean.getHour(), timeBean.getMinute());
//            DateTime current_end = current_start.plusMinutes(minutes.getMinutes());
//            mActiveBean.setStart_time(timeBean);
//            mActiveBean.setEnd_time(new TimeBean(current_end.getYear(), current_end.getMonthOfYear()-1, current_end.getDayOfMonth(), current_end.getHourOfDay(), current_end.getMinuteOfHour(), current_end.getWeekOfWeekyear()));
//            showTime(tvStartTime, mActiveBean.getStart_time());
//            showTime(tvEndTime, mActiveBean.getEnd_time());
            //todo 顺推一个小时
            mActiveBean.setStart_time(timeBean);
            DateTime start = timeBean.getDateTime();
            DateTime end = start.plusHours(1);
            mActiveBean.setEnd_time(new TimeBean(end.getMillis()));
            showTime(tvStartTime, mActiveBean.getStart_time());
            showTime(tvEndTime, mActiveBean.getEnd_time());
            return;
        }
        mActiveBean.setStart_time(timeBean);
        showTime(tvStartTime, timeBean);
    }
}
