package com.yihuan.sharecalendar.ui.activity.active;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yihuan.sharecalendar.R;
import com.yihuan.sharecalendar.global.App;
import com.yihuan.sharecalendar.global.base.BaseActivity;
import com.yihuan.sharecalendar.global.base.BasePresenter;
import com.yihuan.sharecalendar.http.help.Urls;
import com.yihuan.sharecalendar.modle.bean.active.ActiveDetailsBean;
import com.yihuan.sharecalendar.modle.bean.active.ShareBean;
import com.yihuan.sharecalendar.modle.bean.active.ShareType;
import com.yihuan.sharecalendar.modle.bean.active.TimeBean;
import com.yihuan.sharecalendar.modle.calendar.ActiveBean;
import com.yihuan.sharecalendar.modle.db.ScheduleManager;
import com.yihuan.sharecalendar.presenter.ActiveDetailsPresenter_Receiver;
import com.yihuan.sharecalendar.presenter.contract.ActiveDetailsContract_Receiver;
import com.yihuan.sharecalendar.ui.adapter.recyclerview.ActiveDetailsImgRvAdapter;
import com.yihuan.sharecalendar.ui.adapter.recyclerview.InviteFriendsRvAdapter;
import com.yihuan.sharecalendar.ui.adapter.recyclerview.listener.OnRvItemClickListeners;
import com.yihuan.sharecalendar.ui.view.TitleView;
import com.yihuan.sharecalendar.ui.view.cropimg.ZoomImageView;
import com.yihuan.sharecalendar.ui.view.dialog.SimpleDialog;
import com.yihuan.sharecalendar.ui.view.other.GridDividerItem;
import com.yihuan.sharecalendar.ui.view.popwin.ActiveDetailsPop;
import com.yihuan.sharecalendar.ui.view.popwin.ActiveSharePop;
import com.yihuan.sharecalendar.utils.BeanToUtils;
import com.yihuan.sharecalendar.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Ronny on 2017/9/21.
 * 活动详情-接受者
 */

public class ActiveDetailsActivity_Receiver extends BaseActivity<ActiveDetailsPresenter_Receiver> implements ActiveDetailsContract_Receiver.View {
    @BindView(R.id.rv_recyclerview)
    RecyclerView rvRecyclerview;
    @BindView(R.id.tv_refuse_btn)
    TextView tvRefuseBtn;
    @BindView(R.id.tv_agree_btn)
    TextView tvAgreeBtn;
    @BindView(R.id.ll_btn_layout)
    LinearLayout llPopLayout;
    @BindView(R.id.tv_active_name)
    TextView tvActiveName;
    @BindView(R.id.tv_receiver_state)
    TextView tvReceiverState;
    @BindView(R.id.tv_address_details)
    TextView tvAddressDetails;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_start_time)
    TextView tvStartTime;
    @BindView(R.id.tv_end_time)
    TextView tvEndTime;
    @BindView(R.id.tv_invite_count)
    TextView tvInviteCount;
    @BindView(R.id.tv_agree_count)
    TextView tvAgreeCount;
    @BindView(R.id.btn_apply)
    Button btnApply;//重新申请&退出活动
    @BindView(R.id.rv_imglist)
    RecyclerView rvImgList;

    private InviteFriendsRvAdapter inviteFriendsRvAdapter;
    private ActiveBean mActiveBean;
    private ActiveDetailsImgRvAdapter activeDetailsImgRvAdapter;//todo 图片适配器

    @Override
    protected BasePresenter setPresenter() {
        return new ActiveDetailsPresenter_Receiver(this);
    }

    @Override
    protected void initTitleView(final TitleView titleView) {
        titleView.setCenterText("活动详情");
        titleView.setRightImage(R.mipmap.icon_share);
        titleView.setOnRightClickListener(new TitleView.OnRightClickListener() {
            @Override
            public void onRightListener(View v) {
                mPresenter.get_active_share_id(mActiveBean.getActive_id());
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_active_details_receiver;
    }

    @Override
    protected void initView() {
        //todo 初始化受邀好友适配器
        inviteFriendsRvAdapter = new InviteFriendsRvAdapter();
        UIUtils.initRecyclerView(this, rvRecyclerview, inviteFriendsRvAdapter, true);

        //todo 初始化图片适配器
        activeDetailsImgRvAdapter = new ActiveDetailsImgRvAdapter();
        rvImgList.setAdapter(activeDetailsImgRvAdapter);
        GridLayoutManager gridLayoutManager1 = new GridLayoutManager(this, 3);
        GridDividerItem itemDecoration = new GridDividerItem(20,3);
        rvImgList.setLayoutManager(gridLayoutManager1);
        rvImgList.addItemDecoration(itemDecoration);

        mActiveBean = new ActiveBean();
        //todo 取得id 加载详情
        mActiveBean.setActive_id(getIntent().getIntExtra("activeId", -1));
        if (mActiveBean.getActive_id() != -1) {
            refreshData();
        }
        setListener();
    }

    private void setListener() {
        activeDetailsImgRvAdapter.setOnRvItemClickListeners(new OnRvItemClickListeners<String>() {
            @Override
            public void onItemClick(int position, List<String> mList) {
                ImgLookerActivity.start(ActiveDetailsActivity_Receiver.this, (ArrayList<String>) mList, position);
            }
        });
    }

    @Override
    protected void refreshData() {
        mPresenter.getActiveDetails(mActiveBean.getActive_id());
    }

    @OnClick({R.id.tv_des, R.id.tv_refuse_btn, R.id.tv_agree_btn, R.id.btn_apply})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_des:
                showDesDialog();
                break;
            case R.id.tv_refuse_btn:
                refuseClick(view);
                break;
            case R.id.tv_agree_btn:
                agreeClick(view);
                break;
            case R.id.btn_apply://todo 重新申请&退出活动
                apply();
                break;
        }
    }

    private void showDesDialog() {
        SimpleDialog dialog = new SimpleDialog(this, mActiveBean.getDes());
        dialog.show();
    }

    private void apply() {
        if(btnApply.isSelected()){//todo 已接受情况为选中
            //todo 退出活动
            mPresenter.exitActive(mActiveBean);
        }else{
            //todo 重新申请
            mPresenter.reApply(mActiveBean.getActive_id());
        }
    }

    //todo 同意参加活动
    private void agreeClick(View view) {
        ActiveDetailsPop activeDetailsPop = new ActiveDetailsPop(this, view, true);
        activeDetailsPop.show();
        activeDetailsPop.setOnClickListener(new ActiveDetailsPop.OnClickListener() {
            @Override
            public void onClick(int position) {
                mPresenter.agreeActive(mActiveBean);
            }
        });
    }

    //todo 拒绝参加活动
    private void refuseClick(View view) {
        final ActiveDetailsPop activeDetailsPop = new ActiveDetailsPop(this, view, false);
        activeDetailsPop.show();
        activeDetailsPop.setOnClickListener(new ActiveDetailsPop.OnClickListener() {
            @Override
            public void onClick(int position) {
                mPresenter.refuseActive(mActiveBean.getActive_id());
            }
        });
    }

    public static void start(Activity activity, int activeId) {
        Intent intent = new Intent(activity, ActiveDetailsActivity_Receiver.class);
        intent.putExtra("activeId", activeId);
        activity.startActivity(intent);
    }

    public static void startForResult(Activity activity, int activeId, int requestCode) {
        Intent intent = new Intent(activity, ActiveDetailsActivity_Receiver.class);
        intent.putExtra("activeId", activeId);
        activity.startActivityForResult(intent, requestCode);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onGetActiveDetailsOK(ActiveDetailsBean bean) {
        refreshUI(bean);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void refreshUI(ActiveDetailsBean bean) {
        if (bean == null) return;

        mActiveBean.setPublish_user_id(bean.getUserId());
        mActiveBean.setTitle(bean.getTitle());
        mActiveBean.setLocation(bean.getAddress());
        mActiveBean.setArea(bean.getRegion());
        mActiveBean.setLat(Double.parseDouble(bean.getLatitude()));
        mActiveBean.setLon(Double.parseDouble(bean.getLongitude()));
        mActiveBean.setStart_time(new TimeBean(bean.getStartTime()));
        mActiveBean.setEnd_time(new TimeBean(bean.getEndTime()));
        mActiveBean.setCycle(bean.getRepeatPeriod());
        mActiveBean.setFullday(BeanToUtils.getFullDay(bean.getFullDay()));
        mActiveBean.setRemindLists(bean.getActivityAlarms());//活动提醒
        mActiveBean.setDes(bean.getComments());
        mActiveBean.setImgList(bean.getActivityImages());
        //todo 受邀方都是共享活动
        mActiveBean.setIs_share_schdule(true);
//        ScheduleManager.getInstance().deleteSchdule(mActiveBean);//todo 该活动可能存在也可能不存在，直接删除再添加
//        ScheduleManager.getInstance().addSchdule(mActiveBean);//todo 重新编辑数据库

        //todo 设置活动详情信息
        activeDetailsImgRvAdapter.setDataList(mActiveBean.getImgList());
        tvActiveName.setText(bean.getTitle());
        tvAddress.setText(bean.getRegion());
        tvAddressDetails.setText(bean.getAddress());
        TimeBean start = new TimeBean(bean.getStartTime());
        TimeBean end = new TimeBean(bean.getEndTime());
        tvStartTime.setText(BeanToUtils.getFullDay(bean.getFullDay()) ? start.toYMD() : start.toTime());
        tvEndTime.setText(BeanToUtils.getFullDay(bean.getFullDay()) ? end.toYMD() : end.toTime());
        tvInviteCount.setText(String.valueOf(bean.getActivityInviteNumber()));
        tvAgreeCount.setText(String.valueOf(bean.getActivityPartinNumber()));
        List<ActiveDetailsBean.ActivityInviteListBean> friendList = bean.getActivityInviteList();
        inviteFriendsRvAdapter.setDataList(friendList);
        //todo 设置接受状态
        String inviteStatus = bean.getInviteStatus();
        if(TextUtils.isEmpty(inviteStatus))return;

        //todo -1: 拒绝活动、1: 接受活动、0: 未处理、2: 已取消 3 重新申请
                String msg = null;
                switch (inviteStatus) {
                    case "-1":
                        msg = "已拒绝";//todo 拒绝后
                        btnApply.setSelected(false);
                        hideBtnPopLayout();
                        ScheduleManager.getInstance().deleteSchdule(mActiveBean);
                        break;
                    case "1":
                        msg = "已参与";
                        btnApply.setSelected(true);
                        hideBtnPopLayout();
                        //todo 已参与，每次进来请求详情都更新一次数据库
                        ScheduleManager.getInstance().deleteSchdule(mActiveBean);
                        ScheduleManager.getInstance().addSchdule(mActiveBean);
                        break;
                    case "0":
                        msg = "待处理";
                        llPopLayout.setVisibility(View.VISIBLE);
                        break;
                    case "3":
                        msg = "重新申请";
                        llPopLayout.setVisibility(View.GONE);
                        btnApply.setVisibility(View.GONE);
                        break;
                    default:
                        msg = "";
                        break;
                }
                tvReceiverState.setText(msg);
                tvReceiverState.setTextColor(BeanToUtils.getInviteStatusColor(inviteStatus));
    }

    @Override
    public void onAgreeActiveOK() {
        hideBtnPopLayout();
        refreshData();
    }


    @Override
    public void onRefuseActiveOK() {
        hideBtnPopLayout();
        refreshData();
    }

    @Override
    public void onExitActiveOK() {
        finish();
    }

    @Override
    public void onReApplyOK() {
        hideBtnPopLayout();
        refreshData();
    }

    @Override
    public void get_active_share_id_OK(Integer shareId) {
        //todo 拼接分享URL
        String url = Urls.DOMAIN + Urls.GET_SHARE_URL + "?shareId="+ shareId;
        ActiveSharePop pop = new ActiveSharePop(ActiveDetailsActivity_Receiver.this, getRootView());
        pop.showBottomDefault();
        pop.setShareBean(new ShareBean(ShareType.URL, null, null, url, "活动邀请", "点击查看详情"));
    }

    private void hideBtnPopLayout() {
        //todo 隐藏接受拒绝按钮， 显示另一个按钮
        btnApply.setVisibility(View.VISIBLE);
        llPopLayout.setVisibility(View.GONE);
        if(btnApply.isSelected()){
            btnApply.setText("退出该活动");
        }else{
            btnApply.setText("重新申请");
        }
    }
}
