package com.yihuan.sharecalendar.ui.activity.setting;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yihuan.sharecalendar.R;
import com.yihuan.sharecalendar.global.App;
import com.yihuan.sharecalendar.global.Constants;
import com.yihuan.sharecalendar.global.base.BaseActivity;
import com.yihuan.sharecalendar.global.base.BasePresenter;
import com.yihuan.sharecalendar.modle.bean.login.SetInfoBean;
import com.yihuan.sharecalendar.modle.bean.settting.UserBean;
import com.yihuan.sharecalendar.presenter.EditUserInfoPresenter;
import com.yihuan.sharecalendar.presenter.contract.EditUserInfoContract;
import com.yihuan.sharecalendar.ui.activity.login.SetInfoActivity_Select;
import com.yihuan.sharecalendar.ui.view.CircleImageView;
import com.yihuan.sharecalendar.ui.view.TitleView;
import com.yihuan.sharecalendar.utils.BeanToUtils;
import com.yihuan.sharecalendar.utils.StringUtils;
import com.yihuan.sharecalendar.utils.UIUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Ronny on 2017/9/11.
 * 修改资料
 */

public class EditUserInfoActivity extends BaseActivity<EditUserInfoPresenter> implements EditUserInfoContract.View {

    @BindView(R.id.iv_header)
    CircleImageView ivHeader;
    @BindView(R.id.ll_edit_header)
    LinearLayout llEditHeader;
    @BindView(R.id.tv_singture)
    TextView tvSingture;
    @BindView(R.id.ll_edit_singture)
    LinearLayout llEditSingture;
    @BindView(R.id.tv_nickname)
    TextView tvNikename;
    @BindView(R.id.ll_edit_nickname)
    LinearLayout llEditNickname;
    @BindView(R.id.tv_sex)
    TextView tvSex;
    @BindView(R.id.ll_edit_sex)
    LinearLayout llEditSex;
    @BindView(R.id.tv_age)
    TextView tvAge;
    @BindView(R.id.ll_edit_age)
    LinearLayout llEditAge;
    @BindView(R.id.tv_constellation)
    TextView tvConstellation;
    @BindView(R.id.ll_edit_constellation)
    LinearLayout llEditConstellation;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.ll_edit_address)
    LinearLayout llEditAddress;
    @BindView(R.id.tv_cellphone)
    TextView tvCellphone;
    @BindView(R.id.ll_edit_cellphone)
    LinearLayout llEditCellphone;

    private String clickType;
    private String editType;
    private SetInfoBean selectInfo = new SetInfoBean();

    @Override
    protected BasePresenter setPresenter() {
        return new EditUserInfoPresenter(this);
    }

    @Override
    protected void initTitleView(TitleView titleView) {
        titleView.setCenterText("修改个人资料");
        titleView.setRightText("保存");
        titleView.setOnRightClickListener(new TitleView.OnRightClickListener() {
            @Override
            public void onRightListener(View v) {
                save();
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_edit_userinfo;
    }

    @Override
    protected void initView() {
        refreshData();
    }

    @Override
    protected void refreshData() {
        mPresenter.getUserInfo();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onGetUserInfoOK(UserBean userBean) {
        //todo 设置信息
        App.getInstanse().setUser(userBean);
        UIUtils.displayHeader(this, userBean.getHeaderImage(), ivHeader,R.mipmap.logo );
        tvSingture.setText(userBean.getSignature());
        tvNikename.setText(userBean.getNickname());
        tvSex.setText(BeanToUtils.getSexName(userBean.getSex()));
        if(userBean.getPeriod() != null){
            tvAge.setText(StringUtils.nullToStr(userBean.getPeriod())+"后");
        }
        tvConstellation.setText(userBean.getConstellationName());
        tvAddress.setText(new StringBuffer().append(StringUtils.nullToStr(userBean.getProvince())).append(" ").append(StringUtils.nullToStr(userBean.getCity())).toString());
        tvCellphone.setText(userBean.getBindPhone());
    }

    @Override
    public void editInfoOK() {
//        refreshData();
        finish();
    }

    @OnClick({R.id.ll_edit_header, R.id.ll_edit_singture, R.id.ll_edit_nickname, R.id.ll_edit_sex, R.id.ll_edit_age, R.id.ll_edit_constellation, R.id.ll_edit_address, R.id.ll_edit_cellphone})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_edit_header:
                toSetHeader();
                break;
            case R.id.ll_edit_singture:
                editType = Constants.INTENT_EDIT_TYPE_SIGNATURE;
                toEdit(editType);
                break;
            case R.id.ll_edit_nickname:
                editType = Constants.INTENT_EDIT_TYPE_NICKNAME;
                toEdit(editType);
                break;
            case R.id.ll_edit_sex:
                clickType = Constants.INTENT_INFO_SELECT_TYPE_SEX;
                toSelect(clickType);

                break;
            case R.id.ll_edit_age:
                clickType = Constants.INTENT_INFO_SELECT_TYPE_YEAR;
                toSelect(clickType);

                break;
            case R.id.ll_edit_constellation:
                clickType = Constants.INTENT_INFO_SELECT_TYPE_CONSTELLATION;
                toSelect(clickType);

                break;
            case R.id.ll_edit_address:
                clickType = Constants.INTENT_INFO_SELECT_TYPE_ADDRESS;
                toSelect(clickType);

                break;
            case R.id.ll_edit_cellphone:
                break;
        }
    }

    private void toSetHeader() {
        startActivityForResult(new Intent(this, SetHeaderActivity.class), Constants.REQUEST_CODE_2);
    }

    private void toEdit(String editType) {
        Intent intent = new Intent(this, SetInfo_SetEditActivity.class);
        intent.putExtra(Constants.INTENT_EDIT_TYPE, editType);
        if(editType.equals(Constants.INTENT_EDIT_TYPE_NICKNAME)){
           intent.putExtra(Constants.INTENT_EDIT_TYPE_NICKNAME, tvNikename.getText().toString().trim());
        }else if(editType.equals(Constants.INTENT_EDIT_TYPE_SIGNATURE)){
            intent.putExtra(Constants.INTENT_EDIT_TYPE_SIGNATURE, tvSingture.getText().toString().trim());
        }
        startActivityForResult(intent, Constants.REQUEST_CODE_1);
    }

    private void toSelect(String clickType) {
        Intent intent = new Intent(this, SetInfoActivity_Select.class);
        intent.putExtra(Constants.INTENT_INFO_SELECT_TYPE, clickType);
        startActivityForResult(intent, Constants.REQUEST_CODE_1);
    }

    private void save() {
        String signature = tvSingture.getText().toString().trim();
        String nickName = tvNikename.getText().toString().trim();
        String sex = tvSex.getText().toString().trim();
        String age = tvAge.getText().toString().trim();
        String constellation = selectInfo.constellationId;
        String cityId = selectInfo.cityId+"";
        mPresenter.editInfo(signature, nickName, sex, age, constellation, cityId);

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
                        tvAge.setText(selectInfo.year);
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
            }else{
                switch (editType){
                    case Constants.INTENT_EDIT_TYPE_SIGNATURE:
                        selectInfo.singnature = data.getStringExtra(Constants.INTENT_EDIT_RESULT);
                        tvSingture.setText(selectInfo.singnature);
                        break;
                    case Constants.INTENT_EDIT_TYPE_NICKNAME:
                        selectInfo.nickName = data.getStringExtra(Constants.INTENT_EDIT_RESULT);
                        tvNikename.setText(selectInfo.nickName);
                        break;
                }
            }

        }else if(requestCode == Constants.REQUEST_CODE_2 && resultCode == RESULT_OK){//todo 更改头像成功
            refreshData();
        }
    }
}
