package com.yihuan.sharecalendar.ui.activity.friends;

import android.app.Activity;
import android.content.Intent;
import android.widget.ImageView;
import android.widget.TextView;

import com.yihuan.sharecalendar.R;
import com.yihuan.sharecalendar.global.Constants;
import com.yihuan.sharecalendar.global.base.BaseActivity;
import com.yihuan.sharecalendar.global.base.BasePresenter;
import com.yihuan.sharecalendar.modle.bean.home.WeekMoodBean;
import com.yihuan.sharecalendar.modle.bean.mine.MyMoodBean;
import com.yihuan.sharecalendar.presenter.LookFriendMoodPresenter;
import com.yihuan.sharecalendar.presenter.contract.LookFriendMoodContract;
import com.yihuan.sharecalendar.ui.view.MoodView;
import com.yihuan.sharecalendar.ui.view.TitleView;
import com.yihuan.sharecalendar.utils.BeanToUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Ronny on 2017/9/30.
 * 查看好友心情指数
 */

public class LookFriendMoodActivity extends BaseActivity<LookFriendMoodPresenter> implements LookFriendMoodContract.View {
    @BindView(R.id.iv_mood_img)
    ImageView ivMoodImg;
    @BindView(R.id.tv_mood)
    TextView tvMood;
    @BindView(R.id.moodView)
    MoodView moodView;

    @Override
    protected BasePresenter setPresenter() {
        return new LookFriendMoodPresenter(this);
    }

    public static void startForResult(Activity activity,String friendId, int requestCode){
        Intent intent = new Intent(activity, LookFriendMoodActivity.class);
        intent.putExtra(Constants.INTENT_USER_ID, friendId);
        activity.startActivityForResult(intent, requestCode);
    }

    @Override
    protected void initTitleView(TitleView titleView) {
        titleView.setCenterText("心情指数");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_look_friend_moo;
    }

    @Override
    protected void initView() {
        String friendId = getIntent().getStringExtra(Constants.INTENT_USER_ID);
        mPresenter.queryFriendMood(friendId);
        mPresenter.queryFriendWeekMood(friendId);
    }

    @Override
    protected void refreshData() {

    }

    @Override
    public void onQueryFriendMood(List<MyMoodBean> bean) {
        if(bean.size() > 0 && bean.get(0) != null){
            String emotion = bean.get(0).getEmotion();
            tvMood.setText(BeanToUtils.getMoodStr(emotion));
            ivMoodImg.setImageResource(BeanToUtils.getMoodBigResouceId(emotion));
        }
    }

    @Override
    public void onQueryFriendWeekMood(WeekMoodBean weekMoodBean) {
        ArrayList<Integer> list = new ArrayList<>();
        setMyWeekData(list, weekMoodBean.get_$1());
        setMyWeekData(list, weekMoodBean.get_$2());
        setMyWeekData(list, weekMoodBean.get_$3());
        setMyWeekData(list, weekMoodBean.get_$4());
        setMyWeekData(list, weekMoodBean.get_$5());
        setMyWeekData(list, weekMoodBean.get_$6());
        setMyWeekData(list, weekMoodBean.get_$7());
        moodView.setMoodData(list);
    }

    private void setMyWeekData(ArrayList<Integer> list, String s) {
        if(s != null){
            try {
                list.add(Integer.valueOf(s));
            }catch (Exception e){

            }
        }
    }
}
