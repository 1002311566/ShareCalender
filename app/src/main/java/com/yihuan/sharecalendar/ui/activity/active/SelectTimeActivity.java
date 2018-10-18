package com.yihuan.sharecalendar.ui.activity.active;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yihuan.sharecalendar.R;
import com.yihuan.sharecalendar.global.base.BaseActivity;
import com.yihuan.sharecalendar.global.base.BasePresenter;
import com.yihuan.sharecalendar.modle.bean.active.TimeBean;
import com.yihuan.sharecalendar.presenter.SelectTimePresenter;
import com.yihuan.sharecalendar.presenter.contract.SelectTimeContract;
import com.yihuan.sharecalendar.ui.view.SelectorView;
import com.yihuan.sharecalendar.ui.view.TitleView;

import butterknife.BindView;

/**
 * Created by Ronny on 2017/11/3.
 * 时间选择
 */

public class SelectTimeActivity extends BaseActivity<SelectTimePresenter> implements SelectTimeContract.View, SelectorView.OnSelectListener {

    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.sv_1)
    SelectorView sv1;
    @BindView(R.id.sv_2)
    SelectorView sv2;
    @BindView(R.id.sv_3)
    SelectorView sv3;
    @BindView(R.id.sv_4)
    SelectorView sv4;
    @BindView(R.id.sv_5)
    SelectorView sv5;
    @BindView(R.id.ll_time_layout)
    LinearLayout llTimeLayout;

    public static final String INTO_START = "startForResult";
    public static final String INTO_END = "end";
    public static final String INTO_SELECT_DATE = "select_date";
    public static final String SELECT_TIME = "select_time";

    private TimeBean mSelectTime;

    @Override
    protected BasePresenter setPresenter() {
        return new SelectTimePresenter(this);
    }

    @Override
    protected void initTitleView(TitleView titleView) {
        String type = getIntent().getStringExtra("type");
        if (type != null) {
            switch (type) {
                case INTO_START:
                    titleView.setCenterText("开始");
                    break;
                case INTO_END:
                    titleView.setCenterText("结束");
                    break;
                case INTO_SELECT_DATE:
                    titleView.setCenterText("跳转到指定日期");
                    llTimeLayout.setVisibility(View.GONE);
                    break;
            }
        }
        titleView.setRightText("保存");
        titleView.setOnRightClickListener(new TitleView.OnRightClickListener() {
            @Override
            public void onRightListener(View v) {
                Intent intent = new Intent();
                intent.putExtra(SELECT_TIME, mSelectTime);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        titleView.setOnLeftClickListener(new TitleView.OnLeftClickListener() {
            @Override
            public void onLeftClick() {
                finish();
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_select_time;
    }

    @Override
    protected void initView() {
        if(getIntent() != null && getIntent().hasExtra("timeBean")){
            mSelectTime = getIntent().getParcelableExtra("timeBean");
        }else{
            mSelectTime = mPresenter.getCurrentTimeBean();
        }
        refreshDate();

        sv1.setData(mPresenter.getYearData(), mPresenter.getYearPosition(mSelectTime.getYear()));
        sv2.setData(mPresenter.getMonthData(), mPresenter.getMonthPosition(mSelectTime.getMonth()));
        sv3.setData(mPresenter.getDayData(mSelectTime.getYear(), mSelectTime.getMonth()), mPresenter.getDayPosition(mSelectTime));
        sv4.setData(mPresenter.getHourData(), mPresenter.getHourPosition(mSelectTime.getHour()));
        sv5.setData(mPresenter.getMinuteData(), mPresenter.getMinutePosition(mSelectTime.getMinute()));

        sv1.setOnSelectListener(this);
        sv2.setOnSelectListener(this);
        sv3.setOnSelectListener(this);
        sv4.setOnSelectListener(this);
        sv5.setOnSelectListener(this);
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

    public static void startForResult(BaseActivity activity, String type, TimeBean lastTimeBean , int requestCode) {
        Intent intent = new Intent(activity, SelectTimeActivity.class);
        intent.putExtra("type", type);
        intent.putExtra("timeBean", lastTimeBean);
        activity.startActivityForResult(intent, requestCode);
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
                mSelectTime.setMonth(s-1);
                refreshDay();
                break;
            case R.id.sv_3:
                mSelectTime.setDay(s);
                refreshDate();
                break;
            case R.id.sv_4:
                mSelectTime.setHour(s);
                break;
            case R.id.sv_5:
                mSelectTime.setMinute(s);
                break;
        }
    }
}
