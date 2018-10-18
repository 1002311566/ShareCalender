package com.yihuan.sharecalendar.ui.activity.login;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yihuan.sharecalendar.R;
import com.yihuan.sharecalendar.global.Constants;
import com.yihuan.sharecalendar.global.base.BaseActivity;
import com.yihuan.sharecalendar.global.base.BasePresenter;
import com.yihuan.sharecalendar.modle.bean.login.SetInfoBean;
import com.yihuan.sharecalendar.presenter.SetInfoPresenter;
import com.yihuan.sharecalendar.presenter.contract.SetInfoContract;
import com.yihuan.sharecalendar.ui.view.TitleView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Ronny on 2017/9/9.
 */

public class SetInfoActivity extends BaseActivity<SetInfoPresenter> implements SetInfoContract.View{
    @BindView(R.id.tv_sex)
    TextView tvSex;
    @BindView(R.id.ll_sex)
    LinearLayout llSex;
    @BindView(R.id.tv_year)
    TextView tvYear;
    @BindView(R.id.ll_year)
    LinearLayout llYear;
    @BindView(R.id.tv_constellation)
    TextView tvConstellation;
    @BindView(R.id.ll_constellation)
    LinearLayout llConstellation;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.ll_address)
    LinearLayout llAddress;
    @BindView(R.id.btn_next)
    Button btnNext;

    private String clickType;
    private SetInfoBean selectInfo = new SetInfoBean();

    @Override
    protected BasePresenter setPresenter() {
        return new SetInfoPresenter(this);
    }

    @Override
    protected void initTitleView(TitleView titleView) {
        titleView.setCenterText("基本信息");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_set_info;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void refreshData() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.REQUEST_CODE_1 && resultCode == RESULT_OK) {
            SetInfoBean infoBean =  data.getParcelableExtra(Constants.INTENT_INFO_SELELCT_DATA);
            if (infoBean != null) {
                switch (clickType) {
                    case Constants.INTENT_INFO_SELECT_TYPE_SEX:
                        selectInfo.sex = infoBean.sex;
                        tvSex.setText(selectInfo.sex);
                        break;
                    case Constants.INTENT_INFO_SELECT_TYPE_YEAR:
                        selectInfo.year = infoBean.year;
                        tvYear.setText(selectInfo.year);
                        break;
                    case Constants.INTENT_INFO_SELECT_TYPE_CONSTELLATION:
                        selectInfo.constellationId = infoBean.constellationId;
                        selectInfo.constellationName = infoBean.constellationName;
                        selectInfo.constellationDetail = infoBean.constellationDetail;
                        tvConstellation.setText(selectInfo.constellationDetail);
                        break;
                    case Constants.INTENT_INFO_SELECT_TYPE_ADDRESS:
                        selectInfo.provinceId = infoBean.provinceId;
                        selectInfo.provinceName = infoBean.provinceName;
                        selectInfo.cityId = infoBean.cityId;
                        selectInfo.cityName = infoBean.cityName;
                        tvAddress.setText(selectInfo.provinceName+" " +selectInfo.cityName);
                        break;
                }
            }
        }
    }

    @OnClick({R.id.ll_sex, R.id.ll_year, R.id.ll_constellation, R.id.ll_address, R.id.btn_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_sex://性别
                clickType = Constants.INTENT_INFO_SELECT_TYPE_SEX;
                toSelect(clickType);
                break;
            case R.id.ll_year://年代
                clickType = Constants.INTENT_INFO_SELECT_TYPE_YEAR;
                toSelect(clickType);
                break;
            case R.id.ll_constellation://星座
                clickType = Constants.INTENT_INFO_SELECT_TYPE_CONSTELLATION;
                toSelect(clickType);
                break;
            case R.id.ll_address://地址
                clickType = Constants.INTENT_INFO_SELECT_TYPE_ADDRESS;
                toSelect(clickType);
                break;
            case R.id.btn_next://下一步
                next();
                break;
        }
    }

    private void toSelect(String clickType) {
        Intent intent = new Intent(this, SetInfoActivity_Select.class);
        intent.putExtra(Constants.INTENT_INFO_SELECT_TYPE, clickType);
        startActivityForResult(intent, Constants.REQUEST_CODE_1);
    }

    private void next() {
        mPresenter.next(selectInfo.sex, selectInfo.year, selectInfo.constellationId, selectInfo.cityId);
    }

    @Override
    public void setCompleted() {
        Intent intent = new Intent(this, CompleteActivity.class);
        intent.putExtra(Constants.INTENT_COMPELETED_TYPE, Constants.INTENT_COMPELETED_TYPE_REGISTER);
        startActivity(intent);
        finish();
    }
}
