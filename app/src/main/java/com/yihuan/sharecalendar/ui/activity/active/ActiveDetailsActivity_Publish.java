package com.yihuan.sharecalendar.ui.activity.active;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.yihuan.sharecalendar.R;
import com.yihuan.sharecalendar.global.Constants;
import com.yihuan.sharecalendar.global.base.BaseActivity;
import com.yihuan.sharecalendar.global.base.BasePresenter;
import com.yihuan.sharecalendar.http.help.Urls;
import com.yihuan.sharecalendar.modle.bean.active.ActiveDetailsBean;
import com.yihuan.sharecalendar.modle.bean.active.ShareBean;
import com.yihuan.sharecalendar.modle.bean.active.ShareType;
import com.yihuan.sharecalendar.modle.bean.active.TimeBean;
import com.yihuan.sharecalendar.modle.bean.friend.FriendListBean;
import com.yihuan.sharecalendar.modle.calendar.ActiveBean;
import com.yihuan.sharecalendar.modle.db.ScheduleManager;
import com.yihuan.sharecalendar.presenter.ActiveDetailsPresenter_Publish;
import com.yihuan.sharecalendar.presenter.contract.ActiveDetailsContract_Publish;
import com.yihuan.sharecalendar.ui.adapter.recyclerview.ActiveDetailsImgRvAdapter;
import com.yihuan.sharecalendar.ui.adapter.recyclerview.ActiveImgListRvAdapter;
import com.yihuan.sharecalendar.ui.adapter.recyclerview.InviteFriendsRvAdapter;
import com.yihuan.sharecalendar.ui.adapter.recyclerview.ShareFriendRvAdapter;
import com.yihuan.sharecalendar.ui.adapter.recyclerview.listener.OnRvItemAddAndDeleteListener;
import com.yihuan.sharecalendar.ui.adapter.recyclerview.listener.OnRvItemClickListeners;
import com.yihuan.sharecalendar.ui.view.TitleView;
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
 * 活动详情-发起者
 */

public class ActiveDetailsActivity_Publish extends BaseActivity<ActiveDetailsPresenter_Publish> implements ActiveDetailsContract_Publish.View {

    @BindView(R.id.tv_active_name)
    TextView tvActiveName;
    @BindView(R.id.tv_suiyiling)
    TextView tvSuiyiling;
    @BindView(R.id.tv_address_details)
    TextView tvAddressDetails;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.iv_location)
    ImageView ivLocation;
    @BindView(R.id.tv_start_time)
    TextView tvStartTime;
    @BindView(R.id.tv_end_time)
    TextView tvEndTime;
    @BindView(R.id.tv_resend)
    TextView tvResend;
    @BindView(R.id.tv_edit)
    TextView tvEdit;
    @BindView(R.id.tv_invite_count)
    TextView tvInviteCount;
    @BindView(R.id.tv_agree_count)
    TextView tvAgreeCount;
    @BindView(R.id.rv_invite_friends)
    RecyclerView rvInviteFriends;
    @BindView(R.id.rv_invite_status)
    RecyclerView rvInviteStatus;
    @BindView(R.id.btn_delete_active)
    Button btnDeleteActive;
    @BindView(R.id.tv_des)
    TextView tvDes;
    @BindView(R.id.rv_imglist)
    RecyclerView rvImgList;

    private ShareFriendRvAdapter shareFriendRvAdapter;//todo 添加共享好友的适配器
    private InviteFriendsRvAdapter inviteFriendsRvAdapter;//todo 受邀状态列表适配器
    private SelectFriendReceiver receiver;//todo 选择好友广播
    private ArrayList<FriendListBean> friendList;//todo

    private ActiveBean mActiveBean;
    private boolean isSendBell;//是否可以发送随意铃，当没有接受人员时不能发送
    private ActiveDetailsImgRvAdapter activeDetailsImgRvAdapter;//todo 图片适配器

    @Override
    protected BasePresenter setPresenter() {
        return new ActiveDetailsPresenter_Publish(this);
    }

    @Override
    protected void initTitleView(TitleView titleView) {
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
        return R.layout.activity_active_details_publish;
    }

    @Override
    protected void initView() {
        mActiveBean = new ActiveBean();
        //todo 取得id 加载详情
        mActiveBean.setActive_id(getIntent().getIntExtra("activeId", -1));
        if (mActiveBean.getActive_id() != -1) {
           refreshData();
        }

        //todo 初始化添加好友列表适配器
        friendList = new ArrayList<>();
        shareFriendRvAdapter = new ShareFriendRvAdapter();
        rvInviteFriends.setAdapter(shareFriendRvAdapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 5);
        rvInviteFriends.setLayoutManager(gridLayoutManager);
        //todo 初始化邀请好友状态的适配器
        inviteFriendsRvAdapter = new InviteFriendsRvAdapter(true);
        UIUtils.initRecyclerView(this, rvInviteStatus, inviteFriendsRvAdapter, false);
        //todo 初始化图片适配器
        activeDetailsImgRvAdapter = new ActiveDetailsImgRvAdapter();
        rvImgList.setAdapter(activeDetailsImgRvAdapter);
        GridLayoutManager gridLayoutManager1 = new GridLayoutManager(this, 3);
        GridDividerItem itemDecoration = new GridDividerItem(20,3);
        rvImgList.setLayoutManager(gridLayoutManager1);
        rvImgList.addItemDecoration(itemDecoration);

        registSelectFriendReceiver();

        setViewListener();

    }

    private void setViewListener() {
        //todo 添加邀请好友
        shareFriendRvAdapter.setOnRvItemClickListener3(new OnRvItemAddAndDeleteListener<FriendListBean>() {
            @Override
            public void onAddClick() {
                SelectContactActivity.startSelfForResult(ActiveDetailsActivity_Publish.this, friendList, Constants.REQUEST_CODE_1);
            }

            @Override
            public void onDeleteClick(List<FriendListBean> mList, int position) {

            }
        });
        //todo 受邀状态监听
        inviteFriendsRvAdapter.setOnItemChildClickListener(new InviteFriendsRvAdapter.OnItemChildClickListener() {
            @Override
            public void onResend(int position, List<ActiveDetailsBean.ActivityInviteListBean> mList) {
                //todo 重发
                mPresenter.resendMsg(mActiveBean.getActive_id(), mList.get(position).getInviteUser()+"");
            }

            @Override
            public void onCancle(int position, List<ActiveDetailsBean.ActivityInviteListBean> mList) {
                //todo 取消
                mPresenter.cancleFriendByActive(mActiveBean, mList.get(position).getInviteUser());
            }

            @Override
            public void onRefuse(int position, List<ActiveDetailsBean.ActivityInviteListBean> mList) {
                //todo 拒绝
                mPresenter.refuseApply(mList.get(position).getId()+"");
            }

            @Override
            public void onAgree(int position, List<ActiveDetailsBean.ActivityInviteListBean> mList) {
                //todo 接受
                mPresenter.agreeApply(mList.get(position).getId()+"");
            }
        });

        activeDetailsImgRvAdapter.setOnRvItemClickListeners(new OnRvItemClickListeners<String>() {
            @Override
            public void onItemClick(int position, List<String> mList) {
                ImgLookerActivity.start(ActiveDetailsActivity_Publish.this, (ArrayList<String>) mList, position);
            }
        });
    }

    @Override
    protected void refreshData() {
        mPresenter.getActiveDetails(mActiveBean.getActive_id());
    }

    private void agreeClick(View view) {
        ActiveDetailsPop activeDetailsPop = new ActiveDetailsPop(this, view, true);
        activeDetailsPop.show();
    }

    private void refuseClick(View view) {
        ActiveDetailsPop activeDetailsPop = new ActiveDetailsPop(this, view, false);
        activeDetailsPop.show();
    }

    public static void start(Activity activity, int activeId) {
        Intent intent = new Intent(activity, ActiveDetailsActivity_Publish.class);
        intent.putExtra("activeId", activeId);
        activity.startActivity(intent);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onGetActiveDetailsOK(ActiveDetailsBean bean) {
        refreshUI(bean);
    }

    @Override
    public void onDeleteScheduleOK() {
        sendHomeRefreshBroadCast();
        this.finish();
    }

    @Override
    public void onResendBellOK() {

    }

    @Override
    public void onResendMsgOK() {

    }

    @Override
    public void onAddFriendsOK() {
//        shareFriendRvAdapter.setDataList(friendList);
        refreshData();
    }

    @Override
    public void onCancleFriendByActiveOK() {
        refreshData();
    }

    @Override
    public void onRefuseApplyOK() {
        refreshData();
    }

    @Override
    public void onAgreeApplyOK() {
        refreshData();
    }


    @Override
    public void get_active_share_id_OK(Integer shareId) {
        //todo 拼接分享URL
        String url = Urls.DOMAIN + Urls.GET_SHARE_URL + "?shareId="+ shareId;
        ActiveSharePop pop = new ActiveSharePop(ActiveDetailsActivity_Publish.this, getRootView());
        pop.showBottomDefault();
        pop.setShareBean(new ShareBean(ShareType.URL, null, null, url, "活动邀请", "点击查看详情"));
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void refreshUI(ActiveDetailsBean bean) {
        if (bean == null) return;
        //todo 将活动详情保存到mActiveBean中，以便编辑
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
        //todo 是否可以发送随意铃
        isSendBell = bean.getActivityPartinNumber() > 0;

        tvActiveName.setText(bean.getTitle());
        tvAddressDetails.setText(bean.getAddress());
        tvAddress.setText(bean.getRegion());
        boolean isfullday = mActiveBean.isFullday();
        tvStartTime.setText(isfullday ? mActiveBean.getStart_time().toYMD() : mActiveBean.getStart_time().toTime());
        tvEndTime.setText(isfullday ? mActiveBean.getEnd_time().toYMD() : mActiveBean.getEnd_time().toTime());
        tvInviteCount.setText(String.valueOf(bean.getActivityInviteNumber()));
        tvAgreeCount.setText(String.valueOf(bean.getActivityPartinNumber()));
        activeDetailsImgRvAdapter.setDataList(mActiveBean.getImgList());

        if(bean.getActivityInviteList() == null)return;
        List<ActiveDetailsBean.ActivityInviteListBean> list = bean.getActivityInviteList();
        friendList.clear();
        for (ActiveDetailsBean.ActivityInviteListBean b : list){
            friendList.add(new FriendListBean(b.getInviteUser(), b.getNickname(), b.getInviteUserHeadImg()));
        }
        mActiveBean.setShare_friend(friendList);
        inviteFriendsRvAdapter.setDataList(list);

        ScheduleManager.getInstance().editSchdule(mActiveBean);
    }


    @OnClick({R.id.tv_des, R.id.tv_suiyiling, R.id.iv_location, R.id.tv_resend, R.id.tv_edit, R.id.btn_delete_active})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_des://todo 说明
                showDesDialog();
                break;
            case R.id.tv_suiyiling://todo 随意铃
                if(isSendBell){
                    showBellPop();
                }else{
                    showToast("暂时无人参与活动！");
                }

                break;
            case R.id.iv_location://todo 定位
//                startActivityForResult(new Intent(this, BDMapActivity.class), Constants.REQUEST_CODE_2);
                break;
            case R.id.tv_resend://todo 重发
                if(mActiveBean.getShare_friend() != null && mActiveBean.getShare_friend().size() > 0){
                    mPresenter.resendMsgAll(mActiveBean.getActive_id());
                }else{
                    showToast("请先添加分享好友！");
                }
                break;
            case R.id.tv_edit://todo 编辑
                CreateActiveActivity.startSelf(this,mActiveBean);
                finish();
                break;
            case R.id.btn_delete_active://todo 删除活动
                mPresenter.deleteSchedule(mActiveBean);
                break;
        }
    }

    private void showDesDialog() {
        SimpleDialog dialog = new SimpleDialog(this, mActiveBean.getDes());
        dialog.show();
    }

    private void showBellPop() {
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_active_bell, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog alertDialog = builder.setView(view).create();
        view.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo 取消
                alertDialog.dismiss();
            }
        });
        view.findViewById(R.id.tv_enter).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo 确定
                mPresenter.resendBell(mActiveBean.getActive_id());
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }

    private void registSelectFriendReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constants.ACTION_SELECT_FRIEND);
        receiver = new SelectFriendReceiver();
        registerReceiver(receiver, intentFilter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }

    protected class SelectFriendReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.hasExtra(Constants.INTENT_FRIEND_LIST)) {
                List<FriendListBean> listBeen = intent.getParcelableArrayListExtra(Constants.INTENT_FRIEND_LIST);
                if (listBeen != null && listBeen.size() > 0) {
                    for (FriendListBean bean : listBeen) {
                        if (bean.isSelect) {
                            friendList.add(bean);
                        }
                    }
                    //todo 加入新成员到活动
                    mPresenter.addFriendsByActive(mActiveBean, friendList);
                }
            }
        }
    }
}
