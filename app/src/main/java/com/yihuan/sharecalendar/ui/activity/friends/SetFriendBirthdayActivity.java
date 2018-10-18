package com.yihuan.sharecalendar.ui.activity.friends;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.yihuan.sharecalendar.R;
import com.yihuan.sharecalendar.global.Constants;
import com.yihuan.sharecalendar.global.base.BaseActivity;
import com.yihuan.sharecalendar.global.base.BasePresenter;
import com.yihuan.sharecalendar.modle.bean.active.TimeBean;
import com.yihuan.sharecalendar.modle.bean.friend.MyFriendDetailBean;
import com.yihuan.sharecalendar.presenter.SetFriendBirthdayPresenter;
import com.yihuan.sharecalendar.presenter.contract.SetFriendBirthdayContract;
import com.yihuan.sharecalendar.ui.activity.MainActivity;
import com.yihuan.sharecalendar.ui.view.TitleView;
import com.yihuan.sharecalendar.ui.view.pickerview.TimePickerDialog;
import com.yihuan.sharecalendar.ui.view.pickerview.data.Type;
import com.yihuan.sharecalendar.ui.view.pickerview.listener.OnDateSetListener;
import com.yihuan.sharecalendar.ui.view.popwin.SolarPop;
import com.yihuan.sharecalendar.utils.BeanToUtils;

import org.joda.time.DateTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Ronny on 2017/12/9.
 * 备注好友生日
 */

public class SetFriendBirthdayActivity extends BaseActivity<SetFriendBirthdayPresenter> implements SetFriendBirthdayContract.View, OnDateSetListener {
    @BindView(R.id.tv_solar_birthday)
    TextView tvSolarBirthday;
    @BindView(R.id.tv_lunar_birthday)
    TextView tvLunarBirthday;
    @BindView(R.id.cb_always_remind)
    CheckBox cbAlwaysRemind;//0: 否, 1: 是

    private String isAlways;//是否永久提醒
    private MyFriendDetailBean myFriendDetailBean;
    private String friendId= "";
    private TimePickerDialog mTimePickerDialog;

    @Override
    protected BasePresenter setPresenter() {
        return new SetFriendBirthdayPresenter(this);
    }

    @Override
    protected void initTitleView(TitleView titleView) {
        titleView.setCenterText("备注好友生日");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_set_friend_birthday;
    }

    @Override
    protected void initView() {
        myFriendDetailBean = (MyFriendDetailBean) getIntent().getSerializableExtra(Constants.INTENT_MY_FRIEND_DETAILS);
        if(myFriendDetailBean != null){
            friendId = myFriendDetailBean.getFriendId()+"";
            if(myFriendDetailBean.getIsPermenentRemind() != null){
                cbAlwaysRemind.setSelected(myFriendDetailBean.getIsPermenentRemind().equals("1"));
                isAlways = myFriendDetailBean.getIsPermenentRemind();
            }
        }
        setListener();
        initTimeSelectDialog();
    }

    private void initTimeSelectDialog() {
        Date date = new Date();
        String birthday = null;
        if (myFriendDetailBean != null){
            birthday = myFriendDetailBean.getBirthday();
        }
        SimpleDateFormat md = new SimpleDateFormat("MM月dd日");
        try {
            date = md.parse(birthday);
        } catch (Exception e) {
            e.printStackTrace();
            date = new Date();
        }
        mTimePickerDialog = new TimePickerDialog.Builder()
                .setType(Type.MONTH_DAY)
                .setCallBack(SetFriendBirthdayActivity.this)
                .setCyclic(false)
                .setCurrentMillseconds(new DateTime(date).getMillis())
                .setWheelItemTextNormalColor(getResources().getColor(R.color.color_text_black_999))
                .setWheelItemTextSelectorColor(getResources().getColor(R.color.color_blue_00baff))
                .build();
    }

    private void setListener() {
        cbAlwaysRemind.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                cbAlwaysRemind.setSelected(isChecked);
                isAlways = isChecked ? "1" : "0";
                mPresenter.setFriendBirthday(friendId, null, null, isAlways);
            }
        });
    }

    @Override
    protected void refreshData() {

    }

    public static void start(Activity activity, MyFriendDetailBean friendDetailBean){
        Intent intent = new Intent(activity, SetFriendBirthdayActivity.class);
        intent.putExtra(Constants.INTENT_MY_FRIEND_DETAILS, friendDetailBean);
        activity.startActivity(intent);
    }


    @OnClick({R.id.tv_solar_birthday, R.id.tv_lunar_birthday})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_solar_birthday://todo 新历生日
                showSolarBirthdayPop(view);
                break;
            case R.id.tv_lunar_birthday://todo 农历生日
                showLunarBirthdayPop(view);
                break;
        }
    }

    /**
     * 新历生日
     * @param view
     */
    private void showSolarBirthdayPop(final View view) {
//        final SolarPop solarPop = new SolarPop(this, view);
//        solarPop.show();
//        solarPop.setOnSelectDateListener(new SolarPop.OnSelectDateListener() {
//            @Override
//            public void onSelect(TimeBean timeBean) {
//                mPresenter.setFriendBirthday(friendId,timeBean.toMD(), null, isAlways);
//                solarPop.dismiss();
//            }
//        });
        mTimePickerDialog.show(getSupportFragmentManager(), "month_day");
        view.setEnabled(false);
        view.postDelayed(new Runnable() {
            @Override
            public void run() {
                view.setEnabled(true);
            }
        },1000);
    }

    /**
     * 农历生日
     * @param view
     */
    private void showLunarBirthdayPop(final View view) {
//        final SolarPop solarPop = new SolarPop(this, view);
//        solarPop.show();
//        solarPop.setOnSelectDateListener(new SolarPop.OnSelectDateListener() {
//            @Override
//            public void onSelect(TimeBean timeBean) {
//                mPresenter.setFriendBirthday(friendId,null, timeBean.toMD(), isAlways);
//                solarPop.dismiss();
//            }
//        });
        mTimePickerDialog.show(getSupportFragmentManager(), "month_day");
        view.setEnabled(false);
        view.postDelayed(new Runnable() {
            @Override
            public void run() {
                view.setEnabled(true);
            }
        },1000);
    }

    @Override
    public void onSetFriendBirthdayOK() {

    }

    @Override
    public void onDateSet(TimePickerDialog timePickerView, long millseconds) {
        TimeBean timeBean = new TimeBean(millseconds);
        mPresenter.setFriendBirthday(friendId,timeBean.toMD(), null, isAlways);
    }
}
