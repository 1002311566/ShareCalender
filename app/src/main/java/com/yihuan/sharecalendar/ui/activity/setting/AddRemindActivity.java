package com.yihuan.sharecalendar.ui.activity.setting;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.IdRes;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yihuan.sharecalendar.R;
import com.yihuan.sharecalendar.global.App;
import com.yihuan.sharecalendar.global.Constants;
import com.yihuan.sharecalendar.global.base.BaseActivity;
import com.yihuan.sharecalendar.global.base.BasePresenter;
import com.yihuan.sharecalendar.modle.bean.active.TimeBean;
import com.yihuan.sharecalendar.modle.bean.friend.FriendListBean;
import com.yihuan.sharecalendar.modle.bean.settting.ReminderTimeBean;
import com.yihuan.sharecalendar.modle.calendar.RemindBean;
import com.yihuan.sharecalendar.modle.data.TimeUtils;
import com.yihuan.sharecalendar.presenter.AddRemindPresenter;
import com.yihuan.sharecalendar.presenter.contract.AddRemindContract;
import com.yihuan.sharecalendar.ui.activity.active.SelectContactActivity;
import com.yihuan.sharecalendar.ui.activity.active.SelectTimeActivity;
import com.yihuan.sharecalendar.ui.adapter.recyclerview.CustomMonthDayRvAdapter;
import com.yihuan.sharecalendar.ui.adapter.recyclerview.CustomTimeRvAdapter;
import com.yihuan.sharecalendar.ui.adapter.recyclerview.ShareFriendRvAdapter;
import com.yihuan.sharecalendar.ui.adapter.recyclerview.listener.OnRvItemAddAndDeleteListener;
import com.yihuan.sharecalendar.ui.view.CheckBoxLayout;
import com.yihuan.sharecalendar.ui.view.TitleView;
import com.yihuan.sharecalendar.ui.view.TouchLineanrLayout;
import com.yihuan.sharecalendar.ui.view.pickerview.TimePickerDialog;
import com.yihuan.sharecalendar.ui.view.pickerview.data.Type;
import com.yihuan.sharecalendar.ui.view.pickerview.listener.OnDateSetListener;
import com.yihuan.sharecalendar.utils.BeanToUtils;
import com.yihuan.sharecalendar.utils.UIUtils;

import org.joda.time.DateTime;
import org.joda.time.Minutes;

import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Ronny on 2017/9/26.
 * 新增提醒
 */

public class AddRemindActivity extends BaseActivity<AddRemindPresenter> implements AddRemindContract.View, OnDateSetListener {
    public static final String REMIND_ID = "remind_id";

    @BindView(R.id.et_remind_name)
    EditText etRemindName;
    @BindView(R.id.tv_start_time)
    TextView tvStartTime;
    @BindView(R.id.ll_start_time)
    LinearLayout llStartTime;
    @BindView(R.id.tv_end_time)
    TextView tvEndTime;
    @BindView(R.id.ll_end_time)
    LinearLayout llEndTime;
    @BindView(R.id.rg_type)
    RadioGroup rgType;
    @BindView(R.id.rb_week)
    RadioButton rbWeek;
    @BindView(R.id.rb_month)
    RadioButton rbMonth;
    @BindView(R.id.tv_add_month_day)
    TextView tvAddMonthDay;
    @BindView(R.id.rv_month)
    RecyclerView rvMonth;
    @BindView(R.id.checkbox_layout)
    CheckBoxLayout cbLayoutWeek;
    @BindView(R.id.tv_add_time)
    TextView tvAddTime;
    @BindView(R.id.rv_add_time)
    RecyclerView rvAddTime;
    @BindView(R.id.rv_add_share_friend)
    RecyclerView rvAddShareFriend;
    @BindView(R.id.ll_content_root)
    TouchLineanrLayout llContentRoot;
    @BindView(R.id.ll_share1)
    LinearLayout llshare1;
    @BindView(R.id.rl_share2)
    RelativeLayout rlShare2;

    private ShareFriendRvAdapter adapter;//todo 共享好友适配器
    private RemindBean mRemindBean;//todo 请求参数
    private SelectFriendReceiver receiver;//todo 选择好友的广播接收器
    private CustomTimeRvAdapter customTimeRvAdapter;//todo 添加提醒自定义间隔时间适配器
    private CustomMonthDayRvAdapter customMonthDayRvAdapter;//todo 月选择
    private TimePickerDialog mTimePickerDialog;
    private boolean clickEndTime;

    @Override
    protected BasePresenter setPresenter() {
        return new AddRemindPresenter(this);
    }

    @Override
    protected void initTitleView(TitleView titleView) {
        titleView.setCenterText("新增提醒");
        titleView.setRightText("保存");
        titleView.setOnRightClickListener(new TitleView.OnRightClickListener() {
            @Override
            public void onRightListener(View v) {
                //todo save
                getParams();
            }
        });
    }

    /**
     * 取得相关参数
     */
    private void getParams() {
        mRemindBean.setName(etRemindName.getText().toString().trim());

        mRemindBean.setDayType(rbWeek.isChecked() ? "1" : "2");//todo 时间类型
        if(rbWeek.isChecked()){
            //todo 星期数据
            List<Boolean> selectedList = cbLayoutWeek.getSelectedList();
            mRemindBean.setDays(BeanToUtils.getRemindDays(selectedList));
        }else{
            //todo 每月数据
            List<String> dayList = customMonthDayRvAdapter.getDataList();
            mRemindBean.setDays(BeanToUtils.getRemindMonthDays(dayList));
        }

        //todo 提醒时间点
        List<ReminderTimeBean> reminderTimeBeen = BeanToUtils.remindTimeNull(customTimeRvAdapter.getlistData());
        mRemindBean.setReminderTimeList(reminderTimeBeen);

        //todo 提交数据
        mPresenter.addRemind(mRemindBean);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_remind;
    }

    @Override
    protected void initView() {
        mRemindBean = new RemindBean();

        //todo 初始化星期选择
        cbLayoutWeek.setText(R.array.week);
        //todo 初始化日期选择
        customMonthDayRvAdapter = new CustomMonthDayRvAdapter();
        rvMonth.setAdapter(customMonthDayRvAdapter);
        GridLayoutManager customManager = new GridLayoutManager(this, 2);
        rvMonth.setLayoutManager(customManager);
        customManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return 1;
            }
        });

        //todo 初始化自定义间隔时间适配器
        customTimeRvAdapter = new CustomTimeRvAdapter();
        UIUtils.initRecyclerView(this, rvAddTime, customTimeRvAdapter, false);

        //todo 初始化共享好友的适配器
        adapter = new ShareFriendRvAdapter();
        rvAddShareFriend.setAdapter(adapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 5);
        rvAddShareFriend.setLayoutManager(gridLayoutManager);

        //todo 注册选中好友的广播
        registSelectFriendReceiver();
        setListener();
        if (getIntent() != null && getIntent().hasExtra(REMIND_ID)) {
            //todo 编辑提醒 ------start ----------
            mRemindBean.setRemind_id(getIntent().getIntExtra(REMIND_ID, -1));
            mPresenter.getRemindDetails(mRemindBean.getRemind_id());//todo 获取定制化详情
            //todo 编辑提醒----------end------------------
        }else{
            initBaseData();
        }
    }

    private void initTimeSelectDialog(long current) {
        mTimePickerDialog = new TimePickerDialog.Builder()
                .setCallBack(this)
                .setCyclic(false)
                .setType(Type.YEAR_MONTH_DAY)
                .setMinMillseconds(System.currentTimeMillis())
                .setMaxMillseconds(TimeUtils.getNext50Year())
                .setCurrentMillseconds(current)
                .setThemeColor(getResources().getColor(R.color.timepicker_dialog_bg))
                .setWheelItemTextNormalColor(getResources().getColor(R.color.color_text_black_999))
                .setWheelItemTextSelectorColor(getResources().getColor(R.color.color_blue_00baff))
                .setWheelItemTextSize(12)
                .build();
    }

    private void initBaseData() {
        //todo 初始化开始结束时间
        TimeBean currentTimeBean = mPresenter.getCurrentTimeBean();
//        currentTimeBean.setHour(0);currentTimeBean.setMinute(0);
        TimeBean nextDayTimeBean = mPresenter.getNextDayTimeBean();
//        nextDayTimeBean.setHour(0);nextDayTimeBean.setMinute(0);
        mRemindBean.setStartTime(currentTimeBean);
        mRemindBean.setEndTime(nextDayTimeBean);

        //todo 初始界面显示周
        refreshTimeUI();
    }

    private void refreshTimeUI() {
        tvStartTime.setText(mRemindBean.getStartTime().toYMD());
        tvEndTime.setText(mRemindBean.getEndTime().toYMD());
    }


    private void registSelectFriendReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constants.ACTION_SELECT_FRIEND);
        receiver = new SelectFriendReceiver();
        registerReceiver(receiver, intentFilter);
    }

    private void setListener() {
        //todo 重复模式 每周 & 每月
        rgType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId){
                    case R.id.rb_week://todo 每周
                        cbLayoutWeek.setVisibility(View.VISIBLE);
                        rvMonth.setVisibility(View.GONE);
                        tvAddMonthDay.setVisibility(View.GONE);
                        break;
                    case R.id.rb_month://todo 每月
                        cbLayoutWeek.setVisibility(View.GONE);
                        rvMonth.setVisibility(View.VISIBLE);
                        tvAddMonthDay.setVisibility(View.VISIBLE);
                        break;
                }
            }
        });

        //todo 添加共享好友监听
        adapter.setOnRvItemClickListener3(new OnRvItemAddAndDeleteListener<FriendListBean>() {
            @Override
            public void onAddClick() {
                SelectContactActivity.startSelfForResult(AddRemindActivity.this, mRemindBean.getShareFriendList(), Constants.REQUEST_CODE_1);
            }

            @Override
            public void onDeleteClick(List<FriendListBean> mList, int position) {
                mRemindBean.getShareFriendList().remove(position);
                adapter.setDataList(mRemindBean.getShareFriendList());
            }
        });
    }

    @Override
    protected void refreshData() {

    }


    @OnClick({R.id.tv_add_month_day, R.id.tv_start_time, R.id.tv_end_time, R.id.tv_add_time})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_start_time://todo 选择开始时间
//                SelectTimeActivity.startForResult(this, SelectTimeActivity.INTO_START, mRemindBean.getStartTime(), Constants.REQUEST_CODE_2);
                initTimeSelectDialog(mRemindBean.getStartTime().getTimeInMillis());
                clickEndTime = false;
                mTimePickerDialog.show(getSupportFragmentManager(), "year_month_day");
                break;
            case R.id.tv_end_time://todo 选择结束时间
//                SelectTimeActivity.startForResult(this, SelectTimeActivity.INTO_END, mRemindBean.getEndTime(), Constants.REQUEST_CODE_3);
                initTimeSelectDialog(mRemindBean.getEndTime().getTimeInMillis());
                clickEndTime = true;
                mTimePickerDialog.show(getSupportFragmentManager(), "year_month_day");
                break;
            case R.id.tv_add_time://todo 添加间隔时间
                addTime();
                break;
            case R.id.tv_add_month_day://todo 添加重复日期
                addDay();
                break;
        }
    }

    private void addDay() {
        customMonthDayRvAdapter.add();
    }

    private void addTime() {
        customTimeRvAdapter.add();
    }

    @Override
    public void onAddRemindOK() {
//        sendRefreshBroadCast();
        this.finish();
    }

    /**
     * 取得活动详情
     */
    @Override
    public void onGetRemindDetails(RemindDetails remindDetails) {
        mRemindBean.setPublish_user_id(remindDetails.getUserId());//todo 发起者id
        mRemindBean.setName(remindDetails.getName());
        mRemindBean.setStartTime(new TimeBean(remindDetails.getStartTime()));
        mRemindBean.setEndTime(new TimeBean(remindDetails.getEndTime()));
        mRemindBean.setDays(remindDetails.getDays());//todo 固定提醒周
        mRemindBean.setDayType(remindDetails.getDayType());
        mRemindBean.setTaskType(remindDetails.getTaskType());
        mRemindBean.setReminderTimeLists(remindDetails.getReminderTimeList());
        mRemindBean.setShareFriendList(remindDetails.getShareFriendList());
        //todo 共享好友
        refreshUI();
    }

    /**
     * 刷新UI
     */
    private void refreshUI() {
        //todo 判断是否可编辑
        if(mRemindBean.getPublish_user_id() == App.getInstanse().getUserId()){
            //todo 可编辑
            llContentRoot.setIntercpter(false);
            etRemindName.setSelection(0);
        }else{//todo 不可编辑
            llContentRoot.setIntercpter(true);
            etRemindName.setEnabled(false);
            customMonthDayRvAdapter.setDoEdit(false);
            customTimeRvAdapter.setDoEdit(false);

        }

        //todo 更换标题栏文字
        if(mRemindBean.getTaskType().equals("1")){
            getTitleView().setCenterText("系统提醒");
            getTitleView().setRightText("");
            llshare1.setVisibility(View.GONE);
            rlShare2.setVisibility(View.GONE);
        }else if(mRemindBean.getTaskType().equals("2")){
            getTitleView().setCenterText("编辑提醒");
        }else if(mRemindBean.getTaskType().equals("3")){
            getTitleView().setCenterText("分享提醒");
            getTitleView().setRightText("");
            llshare1.setVisibility(View.GONE);
            rlShare2.setVisibility(View.GONE);
        }

        etRemindName.setText(mRemindBean.getName());
        tvStartTime.setText(mRemindBean.getStartTime().toYMD());
        tvEndTime.setText(mRemindBean.getEndTime().toYMD());

        //todo 根据重复类型显示不同的布局
        if(mRemindBean.getDayType() != null && mRemindBean.getDayType().equals("2")){ //  1 周 2 月
           //todo 取月的数据，放到days
            rbMonth.setChecked(true);
            customMonthDayRvAdapter.setDataList(BeanToUtils.parseRemindMonthDays(mRemindBean.getDays()));
        }else{
            //todo 周
            List<Integer> posList = BeanToUtils.parseRemindDays(mRemindBean.getDays());
            cbLayoutWeek.setSelectList(posList);
        }
        List<ReminderTimeBean> reminderTimeList = mRemindBean.getReminderTimeList();
        customTimeRvAdapter.setDataList(reminderTimeList);
        adapter.setDataList(mRemindBean.getShareFriendList());
    }


    public class SelectFriendReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.hasExtra(Constants.INTENT_FRIEND_LIST)) {
                List<FriendListBean> listBeen = intent.getParcelableArrayListExtra(Constants.INTENT_FRIEND_LIST);
                if (listBeen != null && listBeen.size() > 0) {
                    mRemindBean.getShareFriendList().clear();
                    for (FriendListBean bean : listBeen) {
                        if (bean.isSelect) {
                            mRemindBean.getShareFriendList().add(bean);
                        }
                    }
                    adapter.setDataList(mRemindBean.getShareFriendList());
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.REQUEST_CODE_2 && resultCode == RESULT_OK) {
            //todo 开始时间
//            mRemindBean.setStartTime((TimeBean) data.getParcelableExtra(SelectTimeActivity.SELECT_TIME));
//            showTime(tvStartTime, mRemindBean.getStartTime());
        } else if (requestCode == Constants.REQUEST_CODE_3 && resultCode == RESULT_OK) {
            //todo 结束时间
//            mRemindBean.setEndTime((TimeBean) data.getParcelableExtra(SelectTimeActivity.SELECT_TIME));
//            showTime(tvEndTime, mRemindBean.getEndTime());
        }
    }

    private void showTime(TextView tv, TimeBean timeBean) {
        tv.setText(timeBean.toYMD());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }

    @Override
    public void onDateSet(TimePickerDialog timePickerView, long millseconds) {
        TimeBean timeBean = new TimeBean(millseconds);
        if(clickEndTime){
            clickEndTime(timeBean);
        }else{
            clickStartTime(timeBean);
        }
    }

    private void clickEndTime(TimeBean timeBean) {
        if(timeBean.toTime().compareTo(mRemindBean.getStartTime().toTime()) < 0){
            showToast("结束时间不能小于开始时间！");
            return;
        }
        mRemindBean.setEndTime(timeBean);
        showTime(tvEndTime, timeBean);
    }

    private void clickStartTime(TimeBean timeBean){
        if(timeBean.toTime().compareTo(mRemindBean.getEndTime().toTime()) > 0){
            //todo 如果选择的时间大于结束时间，取原先时间跨度，在新的时间上顺推
            TimeBean start_time = mRemindBean.getStartTime();
            TimeBean end_time = mRemindBean.getEndTime();
            DateTime start = new DateTime(start_time.getYear(), start_time.getMonth()+1, start_time.getDay(), start_time.getHour(), start_time.getMinute());
            DateTime end = new DateTime(end_time.getYear(), end_time.getMonth()+1, end_time.getDay(), end_time.getHour(), end_time.getMinute());
            Minutes minutes = Minutes.minutesBetween(start, end);
            DateTime current_start = new DateTime(timeBean.getYear(), timeBean.getMonth()+1, timeBean.getDay(), timeBean.getHour(), timeBean.getMinute());
            DateTime current_end = current_start.plusMinutes(minutes.getMinutes());
            mRemindBean.setStartTime(timeBean);
            mRemindBean.setEndTime(new TimeBean(current_end.getYear(), current_end.getMonthOfYear()-1, current_end.getDayOfMonth(), current_end.getHourOfDay(), current_end.getMinuteOfHour(), current_end.getWeekOfWeekyear()));
            showTime(tvStartTime, mRemindBean.getStartTime());
            showTime(tvEndTime, mRemindBean.getEndTime());
            return;
        }
        mRemindBean.setStartTime(timeBean);
        showTime(tvStartTime, timeBean);
    }
}
