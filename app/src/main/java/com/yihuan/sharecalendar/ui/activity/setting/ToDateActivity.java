package com.yihuan.sharecalendar.ui.activity.setting;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.yihuan.sharecalendar.R;
import com.yihuan.sharecalendar.global.base.BaseActivity;
import com.yihuan.sharecalendar.global.base.BasePresenter;
import com.yihuan.sharecalendar.modle.bean.active.TimeBean;
import com.yihuan.sharecalendar.presenter.ToDatePresenter;
import com.yihuan.sharecalendar.presenter.contract.SelectTimeContract;
import com.yihuan.sharecalendar.ui.activity.active.SelectTimeActivity;
import com.yihuan.sharecalendar.ui.view.SelectorView;
import com.yihuan.sharecalendar.ui.view.TitleView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Ronny on 2017/11/3.
 * 跳转到指定日期
 */

public class ToDateActivity extends BaseActivity<ToDatePresenter> implements SelectTimeContract.View, SelectorView.OnSelectListener {

    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.sv_1)
    SelectorView sv1;
    @BindView(R.id.sv_2)
    SelectorView sv2;
    @BindView(R.id.sv_3)
    SelectorView sv3;
    @BindView(R.id.tv_cancel)
    TextView tvCancel;
    @BindView(R.id.tv_enter)
    TextView tvEnter;

    private TimeBean mSelectTime;

    @Override
    protected BasePresenter setPresenter() {
        return new ToDatePresenter(this);
    }

    @Override
    protected void initTitleView(TitleView titleView) {
        titleView.setCenterText("跳转到指定日期");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_to_date;
    }

    @Override
    protected void initView() {
        mSelectTime = mPresenter.getCurrentTimeBean();
        refreshDate();

        sv1.setData(mPresenter.getYearData(), mPresenter.getCurrentYearPosition());
        sv2.setData(mPresenter.getMonthData(), mPresenter.getCurrentMonthPosition());
        sv3.setData(mPresenter.getDayData(mSelectTime.getYear(), mSelectTime.getMonth()), mPresenter.getCurrentDayPosition(mSelectTime));

        sv1.setOnSelectListener(this);
        sv2.setOnSelectListener(this);
        sv3.setOnSelectListener(this);
    }


    @Override
    protected void refreshData() {
    }

    private void refreshDay() {
        sv3.setData(mPresenter.getDayData(Integer.valueOf(mSelectTime.getYear()), Integer.valueOf(mSelectTime.getMonth())), 0);
        Integer currentData = sv3.getCurrentData();
        mSelectTime.setDay(currentData);
        refreshDate();
    }

    private void refreshDate() {
        tvTime.setText(mSelectTime.toDateAndWeek());
    }

    @Override
    public void onSelect(View view, Integer s) {
        switch (view.getId()) {
            case R.id.sv_1:
                tvTime.setText(s.toString());
                mSelectTime.setYear(s);
                refreshDay();
                break;
            case R.id.sv_2:
                mSelectTime.setMonth(s - 1);
                refreshDay();
                break;
            case R.id.sv_3:
                mSelectTime.setDay(s);
                refreshDate();
                break;
        }
    }

    @OnClick({R.id.tv_cancel, R.id.tv_enter})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_cancel:
                finish();
                break;
            case R.id.tv_enter:
                enter();
                break;
        }
    }

    private void enter() {
        Intent intent = new Intent();
        intent.putExtra(SelectTimeActivity.SELECT_TIME, mSelectTime);
        setResult(RESULT_OK, intent);
        sendHomeRefreshBroadCast();
        finish();
    }

    public static void startSelf(Activity activity, int requestCode){
        activity.startActivityForResult(new Intent(activity, ToDateActivity.class), requestCode);
    }
}
