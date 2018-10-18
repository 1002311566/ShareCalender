package com.yihuan.sharecalendar.ui.activity;

import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.pgyersdk.update.PgyUpdateManager;
import com.yihuan.sharecalendar.R;
import com.yihuan.sharecalendar.global.App;
import com.yihuan.sharecalendar.global.Constants;
import com.yihuan.sharecalendar.global.DataUtils;
import com.yihuan.sharecalendar.global.base.BaseActivity;
import com.yihuan.sharecalendar.global.base.BasePresenter;
import com.yihuan.sharecalendar.modle.bean.active.TimeBean;
import com.yihuan.sharecalendar.modle.data.TimeUtils;
import com.yihuan.sharecalendar.presenter.MainPresenter;
import com.yihuan.sharecalendar.presenter.contract.MainContract;
import com.yihuan.sharecalendar.ui.activity.active.CreateActiveActivity;
import com.yihuan.sharecalendar.ui.activity.hometitle.MessageActivity;
import com.yihuan.sharecalendar.ui.activity.hometitle.MyMoodActivity;
import com.yihuan.sharecalendar.ui.activity.hometitle.ScheduleSearchActivity;
import com.yihuan.sharecalendar.ui.activity.setting.AutoRemindActivity;
import com.yihuan.sharecalendar.ui.activity.setting.EditUserInfoActivity;
import com.yihuan.sharecalendar.ui.activity.setting.MessageBoardActivity;
import com.yihuan.sharecalendar.ui.activity.setting.MoodLookListActivity;
import com.yihuan.sharecalendar.ui.activity.setting.SettingActivity;
import com.yihuan.sharecalendar.ui.activity.setting.ShareRecommendActivity;
import com.yihuan.sharecalendar.ui.activity.setting.ToDateActivity;
import com.yihuan.sharecalendar.ui.activity.setting.YearCalendarActivity;
import com.yihuan.sharecalendar.ui.adapter.viewpager.SimpleFragmentVpAdapter;
import com.yihuan.sharecalendar.ui.fragment.FriendsFragment;
import com.yihuan.sharecalendar.ui.fragment.MessageFragment;
import com.yihuan.sharecalendar.ui.fragment.MonthFragment;
import com.yihuan.sharecalendar.ui.fragment.ScheduleFragment;
import com.yihuan.sharecalendar.ui.view.HomeTitleView;
import com.yihuan.sharecalendar.ui.view.RadioButtonNum;
import com.yihuan.sharecalendar.ui.view.SlidingMenu;
import com.yihuan.sharecalendar.ui.view.TitleView;
import com.yihuan.sharecalendar.ui.view.dialog.AdvertisingDialog;
import com.yihuan.sharecalendar.ui.view.pickerview.TimePickerDialog;
import com.yihuan.sharecalendar.ui.view.pickerview.data.Type;
import com.yihuan.sharecalendar.ui.view.pickerview.listener.OnDateSetListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.View, OnDateSetListener {


    @BindView(R.id.vp_main)
    ViewPager vpMain;
    @BindView(R.id.rb_main_1)
    RadioButton rbMain1;
    @BindView(R.id.rb_main_2)
    RadioButtonNum rbMain2;
    @BindView(R.id.rb_main_3)
    RadioButtonNum rbMain3;
    @BindView(R.id.rb_main_4)
    RadioButtonNum rbMain4;
    @BindView(R.id.rb_main_5)
    RadioButtonNum rbMain5;
    @BindView(R.id.rg_main)
    RadioGroup rgMain;

    //侧滑菜单
    @BindView(R.id.sm_left_layout)
    SlidingMenu smLeftLayout;
    @BindView(R.id.tv_left_menu_xinqing)
    TextView tvLeftMenuXinqing;
    @BindView(R.id.tv_left_menu_to_date)
    TextView tvLeftMenuToDate;
    @BindView(R.id.tv_left_menu_year)
    TextView tvLeftMenuYear;
    @BindView(R.id.tv_left_menu_edit_info)
    TextView tvLeftMenuEditInfo;
    @BindView(R.id.tv_left_menu_setting)
    TextView tvLeftMenuSetting;
    @BindView(R.id.tv_left_menu_say)
    TextView tvLeftMenuSay;
    @BindView(R.id.tv_left_menu_share)
    TextView getTvLeftMenuShare;
    @BindView(R.id.btn_tixing)
    Button btnTixing;
    //顶部菜单
    @BindView(R.id.htv_title)
    HomeTitleView htvTitle;

    private int currentPosition;//当前页面

    private List<Fragment> fragmentList;
    private TimeBean currentTimeBean;
    private TimePickerDialog mTimePickerDialog;

    @Override
    protected BasePresenter setPresenter() {
        return new MainPresenter(this);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initTitleView(TitleView titleView) {
        titleView.setVisibility(View.GONE);
    }

    @Override
    protected void initView() {
        App.getInstanse().saveMainActivity(this);
        checkPermission();
//蒲公英相关---start---
        PgyUpdateManager.setIsForced(false); //设置是否强制更新。true为强制更新；false为不强制更新（默认值）。
        PgyUpdateManager.register(this);
        //---------end----

        vpMain = (ViewPager) getRootView().findViewById(R.id.vp_main);
        rgMain = (RadioGroup) getRootView().findViewById(R.id.rg_main);

        fragmentList = new ArrayList<>();
        fragmentList.add(new MonthFragment());
//        fragmentList.add(new DayFragment());
        fragmentList.add(new MessageFragment());
        fragmentList.add(new ScheduleFragment());
        fragmentList.add(new FriendsFragment());

        SimpleFragmentVpAdapter adapter = new SimpleFragmentVpAdapter(getSupportFragmentManager(), fragmentList);
        vpMain.setAdapter(adapter);
        vpMain.setOffscreenPageLimit(fragmentList.size());
        setViewListener();
        initTimeSelectDialog();
        initDate();
        mPresenter.getUserInfo();
    }

    private void initTimeSelectDialog() {
        long tenYears = 50L * 365 * 1000 * 60 * 60 * 24L;
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.YEAR, -50);
        long min = instance.getTimeInMillis();
        instance.add(Calendar.YEAR, 100);
        long max = instance.getTimeInMillis();
        mTimePickerDialog = new TimePickerDialog.Builder()
                .setType(Type.YEAR_MONTH_DAY)
                .setCallBack(MainActivity.this)
                .setCyclic(true)
                .setMinMillseconds(min)
                .setMaxMillseconds(max)
                .setWheelItemTextNormalColor(getResources().getColor(R.color.color_text_black_999))
                .setWheelItemTextSelectorColor(getResources().getColor(R.color.color_blue_00baff))
                .build();
    }


    private void initDate() {
        currentTimeBean = TimeUtils.getCurrentTimeBean();
        showTitleDate(currentTimeBean);
    }

    private void setViewListener() {
        //todo viewPager的监听
        vpMain.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                fragmentList.get(position).onResume();
                currentPosition = position;
                if (rgMain.getChildCount() >= position) {
                    ((RadioButton) rgMain.getChildAt(position + 1)).setChecked(true);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        //todo radioGroupd 监听
        rgMain.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.rb_main_1:
                        ((RadioButton) rgMain.getChildAt(currentPosition + 1)).setChecked(true);
                        toCreateActive();
                        break;
                    case R.id.rb_main_2://todo 月
                        currentPosition = 0;
                        htvTitle.setVisibility(View.VISIBLE);
                        htvTitle.setCenterClickable(true);
                        break;
                    case R.id.rb_main_3://todo 日
                        currentPosition = 1;
                        htvTitle.setVisibility(View.GONE);
                        htvTitle.setCenterClickable(false);
                        break;
                    case R.id.rb_main_4://todo 日程
                        currentPosition = 2;
                        htvTitle.setVisibility(View.VISIBLE);
                        htvTitle.setCenterClickable(false);
                        break;
                    case R.id.rb_main_5://todo 好友
                        if (TextUtils.isEmpty(DataUtils.getToken())) {
                            ((RadioButton) rgMain.getChildAt(currentPosition + 1)).setChecked(true);
                            toLoginActivity();
                            return;
                        }
                        htvTitle.setVisibility(View.GONE);
                        currentPosition = 3;
                        break;
                }
                setCurrrentPage(currentPosition);
                showTitleDate(currentTimeBean);
            }
        });

        //todo 侧滑菜单
        htvTitle.setOnMenuClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(DataUtils.getToken())) {
                    toLoginActivity();
                    return;
                }
                toggle();
            }
        });

        //todo 消息
        htvTitle.setOnLeftMsgClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(DataUtils.getToken())) {
                    toLoginActivity();
                    return;
                }
                startActivityAnim(new Intent(MainActivity.this, MessageActivity.class));
            }
        });
        //todo 心情点击
        htvTitle.setOnRightMoodClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(DataUtils.getToken())) {
                    toLoginActivity();
                    return;
                }
                //todo 查看心情前弹出广告
                AdvertisingDialog dialog = new AdvertisingDialog(MainActivity.this);
                dialog.show("1", "2", "1", "1");
                dialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivityForResultAnim(new Intent(MainActivity.this, MyMoodActivity.class), Constants.REQUEST_CODE_2);
                    }
                });
            }
        });

        //todo 搜索
        htvTitle.setOnRightSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(DataUtils.getToken())) {
                    toLoginActivity();
                    return;
                }
                startActivityAnim(new Intent(MainActivity.this, ScheduleSearchActivity.class));
            }
        });

        //todo 标题点击
        htvTitle.setOnCenterClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                //todo 弹出时间选择器
                mTimePickerDialog.show(getSupportFragmentManager(), "year_month_day");
                v.setEnabled(false);
                v.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        v.setEnabled(true);
                    }
                }, 1000);


            }
        });
    }

    private void toCreateActive() {
        if (TextUtils.isEmpty(DataUtils.getToken())) {
            toLoginActivity();
            return;
        }
        startActivityAnim(new Intent(this, CreateActiveActivity.class));
    }

    @Override
    protected void refreshData() {
    }


    @OnClick({R.id.tv_left_menu_xinqing, R.id.tv_left_menu_to_date, R.id.tv_left_menu_year, R.id.tv_left_menu_edit_info, R.id.tv_left_menu_setting, R.id.tv_left_menu_say, R.id.tv_left_menu_share, R.id.btn_tixing})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_left_menu_xinqing://todo 心情指数查看
                toMoodLook();
                break;
            case R.id.tv_left_menu_to_date://todo 跳转到指定日期
                ToDateActivity.startSelf(this, Constants.REQUEST_CODE_1);
//                SelectTimeActivity.startForResult(this, SelectTimeActivity.INTO_SELECT_DATE, Constants.REQUEST_CODE_1);
                break;
            case R.id.tv_left_menu_year://todo 年视图
                toYearView();
                break;
            case R.id.tv_left_menu_edit_info://todo 修改资料
                toEditUserInfo();
                break;
            case R.id.tv_left_menu_setting://todo 设置
                toSettingActivity();
                break;
            case R.id.tv_left_menu_say://todo 留言板
                toMessageBoard();
                break;
            case R.id.tv_left_menu_share://todo 分享推荐
                startActivity(new Intent(this, ShareRecommendActivity.class));
//                startActivity(new Intent(this, AlarmActivity.class));
                break;
            case R.id.btn_tixing://todo 定制化提醒
                toAutoRemind();
                break;
        }
    }

    private void toYearView() {
        startActivityForResult(new Intent(this, YearCalendarActivity.class), Constants.REQUEST_CODE_1);
    }

    private void toMoodLook() {
        startActivity(new Intent(this, MoodLookListActivity.class));
    }

    private void toAutoRemind() {
        startActivity(new Intent(this, AutoRemindActivity.class));
    }

    private void toMessageBoard() {
        startActivity(new Intent(this, MessageBoardActivity.class));
    }

    private void toEditUserInfo() {
        startActivity(new Intent(this, EditUserInfoActivity.class));
    }

    private void toSettingActivity() {
        startActivity(new Intent(this, SettingActivity.class));
    }

    //按两次退出-------------------------
    private long mStartTime;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (smLeftLayout.isOpen()) {
                smLeftLayout.closeMenu();
                return false;
            }
            if (currentPosition != 0) {
                //todo 返回到月
                setCurrrentPage(0);
                return false;
            }
            if (System.currentTimeMillis() - this.mStartTime > 2000L) {
                showToast("再按一次退出程序");
                this.mStartTime = System.currentTimeMillis();
                return false;
            }
            finish();
        }
        return false;
    }

    /**
     * 设置当前fragment页
     *
     * @param position
     */
    public void setCurrrentPage(int position) {
        vpMain.setCurrentItem(position, false);
    }

    /**
     * 关闭侧滑
     */
    @Override
    protected void onStop() {
        super.onStop();
        smLeftLayout.closeMenu();
    }

    @Override
    protected void onDestroy() {
        App.getInstanse().removeMainActivity();
        super.onDestroy();
    }

    /**
     * 刷新标题时间
     *
     * @param timeBean
     */
    public void showTitleDate(TimeBean timeBean) {
        this.currentTimeBean = timeBean;

        if (currentPosition == 0) {
            htvTitle.setCenterText(timeBean.toYM());
        } else if (currentPosition == 1) {
//            htvTitle.setCenterText(timeBean.toYMD());
            htvTitle.setCenterText("我的消息");
        } else if (currentPosition == 2) {
            htvTitle.setCenterText("我的日程");
        }
    }

    public void showMyMood(String currentMood) {
        htvTitle.setMyMoodImg(currentMood);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.REQUEST_CODE_2 && resultCode == Constants.REQUEST_CODE_2) {
            //todo 心情设置成功后的广告
            AdvertisingDialog dialog = new AdvertisingDialog(MainActivity.this);
            dialog.show("2", "2", "1", "1");
        }
    }

    public void toggle() {
        smLeftLayout.toggle();
    }

    public void refreshMsgCount(int count) {
        rbMain3.setMsgCount(count);
    }

    public void refreshFriendCount(int count){
        rbMain5.setMsgCount(count);
    }

    @Override
    protected void refreshHomeData() {
        super.refreshHomeData();
        //todo 初始化到月界面
        currentPosition = 0;
        htvTitle.setVisibility(View.VISIBLE);
        setCurrrentPage(currentPosition);
        showTitleDate(currentTimeBean);
    }

    @Override
    public void onUpDateDeviceIdOK() {
        //todo 执行到这说明数据库已经重新更新
    }

    @Override
    public void refreshBroadCast() {
        sendHomeRefreshBroadCast();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        mPresenter.getUserInfo();
    }

    //todo 时间选择监听
    @Override
    public void onDateSet(TimePickerDialog timePickerView, long millseconds) {
        TimeBean timeBean = new TimeBean(millseconds);
        App.getInstanse().setCurrentTime(timeBean);
        fragmentList.get(currentPosition).onResume();
    }

    @Override
    protected boolean doStartActivityAnim() {
        return true;
    }
}
