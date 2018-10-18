package com.yihuan.sharecalendar.ui.activity.hometitle;


import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yihuan.sharecalendar.R;
import com.yihuan.sharecalendar.global.App;
import com.yihuan.sharecalendar.global.Constants;
import com.yihuan.sharecalendar.global.base.BaseActivity;
import com.yihuan.sharecalendar.global.base.BasePresenter;
import com.yihuan.sharecalendar.modle.bean.home.WeekMoodBean;
import com.yihuan.sharecalendar.modle.bean.mine.MyMoodBean;
import com.yihuan.sharecalendar.modle.bean.settting.UserBean;
import com.yihuan.sharecalendar.presenter.MyMoodPresenter;
import com.yihuan.sharecalendar.presenter.contract.MyMoodContract;
import com.yihuan.sharecalendar.ui.view.CircleImageView;
import com.yihuan.sharecalendar.ui.view.TitleView;
import com.yihuan.sharecalendar.utils.BeanToUtils;
import com.yihuan.sharecalendar.utils.UIUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Ronny on 2017/9/21.
 * 我的心情
 */

public class MyMoodActivity extends BaseActivity<MyMoodPresenter> implements MyMoodContract.View {

    @BindView(R.id.iv_header)
    CircleImageView ivHeader;
    @BindView(R.id.tv_nickname)
    TextView tvNickName;
    @BindView(R.id.iv_sex)
    ImageView ivSex;
    @BindView(R.id.iv_1)
    ImageView iv1;
    @BindView(R.id.iv_2)
    ImageView iv2;
    @BindView(R.id.iv_3)
    ImageView iv3;
    @BindView(R.id.iv_4)
    ImageView iv4;
    @BindView(R.id.iv_5)
    ImageView iv5;
    @BindView(R.id.tv_1)
    TextView tv1;
    @BindView(R.id.tv_2)
    TextView tv2;
    @BindView(R.id.tv_3)
    TextView tv3;
    @BindView(R.id.tv_4)
    TextView tv4;
    @BindView(R.id.tv_5)
    TextView tv5;

    private int mSelectMoodPosition;
    private ImageView lastIv;
    private String currentMood;

    @Override
    protected BasePresenter setPresenter() {
        return new MyMoodPresenter(this);
    }

    @Override
    protected void initTitleView(TitleView titleView) {
        titleView.setCenterText("我的心情");
        titleView.setRightText("保存");
        titleView.setOnRightClickListener(new TitleView.OnRightClickListener() {
            @Override
            public void onRightListener(View v) {
                //todo  保存心情
                mPresenter.setMyMood(mSelectMoodPosition + "");
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_mood;
    }

    @Override
    protected void initView() {
        setUserInfo();

        refreshData();
        setListener();
    }

    private void setUserInfo() {
        UserBean user = App.getInstanse().getUser();
        if (user == null) return;

        if (user != null && user.getHeaderImage() != null) {
            UIUtils.displayHeader(this, user.getHeaderImage(), ivHeader, R.mipmap.logo);
        }
        currentMood = user.getCurrentMood();
        tvNickName.setText(user.getNickname());
        ivSex.setImageResource(BeanToUtils.getSexResouceId(user.getSex()));
    }

    private void setListener() {
    }

    @Override
    protected void refreshData() {
        mPresenter.getMyMood();
//        mPresenter.getMyWeekMood();
    }

    @Override
    public void onGetMyMoodOK(MyMoodBean moodBean) {

        try{
            if(moodBean == null){
                //todo 当前没有设置，将上一次的心情
               mSelectMoodPosition = Integer.parseInt(currentMood);
                refreshUI(mSelectMoodPosition);
                return;
            }
            mSelectMoodPosition = Integer.parseInt(moodBean.getEmotion());
            refreshUI(mSelectMoodPosition);
        }catch (Exception e){
            e.printStackTrace();
        }
//        String emotion = moodBean.getEmotion();
//        try {
//            Integer i = Integer.valueOf(emotion);
//            checkBoxLayout.setCurrent(5 -i.intValue());
//        } catch (Exception e) {
//        }
    }

    @Override
    public void onGetMyWeekMood(WeekMoodBean weekMoodBean) {
        ArrayList<Integer> list = new ArrayList<>();
        setMyWeekData(list, weekMoodBean.get_$1());
        setMyWeekData(list, weekMoodBean.get_$2());
        setMyWeekData(list, weekMoodBean.get_$3());
        setMyWeekData(list, weekMoodBean.get_$4());
        setMyWeekData(list, weekMoodBean.get_$5());
        setMyWeekData(list, weekMoodBean.get_$6());
        setMyWeekData(list, weekMoodBean.get_$7());
//        moodView.setMoodData(list);
    }

    @Override
    public void onSetMoodOK() {
//        refreshData();
        setResult(Constants.REQUEST_CODE_2);
        finish();
    }

    private void setMyWeekData(ArrayList<Integer> list, String s) {
        if (s != null) {
            try {
                list.add(Integer.valueOf(s));
            } catch (Exception e) {

            }
        }
    }

    @OnClick({R.id.iv_1, R.id.iv_2, R.id.iv_3, R.id.iv_4, R.id.iv_5})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_1:
                refreshUI(1);
                break;
            case R.id.iv_2:
                refreshUI(2);
                break;
            case R.id.iv_3:
                refreshUI(3);
                break;
            case R.id.iv_4:
                refreshUI(4);
                break;
            case R.id.iv_5:
                refreshUI(5);
                break;
        }
    }

    private void refreshUI(int i) {
        mSelectMoodPosition = i;
        if(lastIv != null){
            lastIv.setSelected(false);
        }
        //点击图标
        ImageView last = null;
        switch (i){
            case 1:
                iv1.setSelected(true);
                last = iv1;
                break;
            case 2:
                iv2.setSelected(true);
                last = iv2;
                break;
            case 3:
                iv3.setSelected(true);
                last = iv3;
                break;
            case 4:
                iv4.setSelected(true);
                last = iv4;
                break;
            case 5:
                iv5.setSelected(true);
                last = iv5;
                break;
        }
        lastIv = last;
    }
}
