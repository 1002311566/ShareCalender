package com.yihuan.sharecalendar.ui.fragment;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.yihuan.sharecalendar.R;
import com.yihuan.sharecalendar.global.App;
import com.yihuan.sharecalendar.global.Constants;
import com.yihuan.sharecalendar.global.base.BaseFragment;
import com.yihuan.sharecalendar.global.base.BasePresenter;
import com.yihuan.sharecalendar.modle.bean.friend.FriendListBean;
import com.yihuan.sharecalendar.modle.bean.settting.UserBean;
import com.yihuan.sharecalendar.presenter.FriendPresenter;
import com.yihuan.sharecalendar.presenter.contract.FriendsContract;
import com.yihuan.sharecalendar.ui.activity.MainActivity;
import com.yihuan.sharecalendar.ui.activity.friends.FriendSearchActivity;
import com.yihuan.sharecalendar.ui.activity.friends.MyFriendDetailsActivity;
import com.yihuan.sharecalendar.ui.activity.friends.NewFriendListActivity;
import com.yihuan.sharecalendar.ui.activity.friends.TagListActivity;
import com.yihuan.sharecalendar.ui.activity.friends.UserSearchActivity;
import com.yihuan.sharecalendar.ui.activity.hometitle.MessageActivity;
import com.yihuan.sharecalendar.ui.activity.hometitle.MyMoodActivity;
import com.yihuan.sharecalendar.ui.adapter.recyclerview.FriendsRvAdapter;
import com.yihuan.sharecalendar.ui.view.SlideBar;
import com.yihuan.sharecalendar.ui.view.dialog.AdvertisingDialog;
import com.yihuan.sharecalendar.utils.BeanToUtils;
import com.yihuan.sharecalendar.utils.UIUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Ronny on 2017/9/19.
 * 好友
 */

public class FriendsFragment extends BaseFragment<FriendPresenter> implements FriendsContract.View {
    @BindView(R.id.ll_title)
    LinearLayout llTitle;
    @BindView(R.id.tv_msg_count)
    TextView tvMsgCount;
    @BindView(R.id.ll_msg_count)
    LinearLayout llMsgCount;
    @BindView(R.id.fl_left_msg)
    FrameLayout flLeftMsg;
    @BindView(R.id.iv_right_add_friend)
    ImageView ivRightAddFriend;
    @BindView(R.id.ll_friend_search)
    LinearLayout llFriendSearch;
    @BindView(R.id.rv_recyclerview)
    LRecyclerView recyclerView;
    @BindView(R.id.iv_left)
    ImageView ivLeft;
    @BindView(R.id.iv_mood)
    ImageView ivMood;
    @BindView(R.id.sb_bar)
    SlideBar slideBar;
    @BindView(R.id.tv_letter)
    TextView tvLetter;
    @BindView(R.id.ll_letter)
    LinearLayout llLetter;
    private FriendsRvAdapter friendsRvAdapter;


    @Override
    protected BasePresenter setPresenter() {
        return new FriendPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_friends;
    }

    @Override
    protected void initView() {
        int stateBarHight = UIUtils.getStateBarHight(getContext());
        llTitle.setPadding(0, stateBarHight, 0, 0);
        friendsRvAdapter = new FriendsRvAdapter();
        UIUtils.initLRecyclerView(getActivity(), recyclerView, friendsRvAdapter, false);
        setListener();
    }

    @Override
    public void onResume() {
        super.onResume();
        UserBean user = App.getInstanse().getUser();
        if (user != null) {
            ivMood.setImageResource(BeanToUtils.getMoodResouceId(user.getCurrentMood()));
        }
        refreshData();
    }

    private void setListener() {
        //todo A-Z索引
        slideBar.setOnTouchLetterChangeListenner(new SlideBar.OnTouchLetterChangeListenner() {
            @Override
            public void onTouchLetterChange(boolean isTouch, String letter) {
                llLetter.setVisibility(isTouch ? View.VISIBLE : View.GONE);
                tvLetter.setText(letter);
                int position = friendsRvAdapter.getLetterFirstPosition(letter);
                if (position != -1) {
                        UIUtils.smoothMoveToPosition(recyclerView, position + 2);//todo 减去一个头布局，一个下拉刷新布局
                }else{
                    if (letter.equals("↑")) {
                        UIUtils.smoothMoveToPosition(recyclerView,0 );
                    }
                }
            }

            @Override
            public void onTouchUp() {
                llLetter.setVisibility(View.GONE);
            }
        });
        friendsRvAdapter.setOnFriendItemClickListener(new FriendsRvAdapter.OnFriendItemClickListener() {
            @Override
            public void onNewFriendClick() {
                //todo 好友申请
                startActivityAnim(new Intent(getActivity(), NewFriendListActivity.class));
            }

            @Override
            public void onTagClick() {
                //todo 标签点击
                startActivityAnim(new Intent(getActivity(), TagListActivity.class));
            }

            @Override
            public void onFriendItemClick(RecyclerView.ViewHolder holder, int position, List<FriendListBean> mList) {
                Intent intent = new Intent(getActivity(), MyFriendDetailsActivity.class);
                //todo 我的好友详情
                intent.putExtra(Constants.INTENT_USER_ID, mList.get(position).getFriendId() + "");
                startActivityAnim(intent);
            }
        });

        recyclerView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData();
            }
        });

        recyclerView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                recyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        recyclerView.setNoMore(true);
                    }
                }, 500);

            }
        });
    }

    @Override
    protected void refreshData() {
        mPresenter.getFriendList();
        mPresenter.getNewFriendApplyCount();
        mPresenter.getNewMsgCount();
    }


    @Override
    protected boolean isRefreshReceiver() {
        return false;
    }

    @OnClick({R.id.fl_left_msg, R.id.iv_right_add_friend, R.id.ll_friend_search, R.id.iv_left, R.id.iv_mood})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fl_left_msg:
                startActivityAnim(new Intent(getActivity(), MessageActivity.class));
                break;
            case R.id.iv_right_add_friend:
                startActivity(new Intent(getActivity(), UserSearchActivity.class));
                break;
            case R.id.ll_friend_search://搜索
                startActivityAnim(new Intent(getActivity(), FriendSearchActivity.class));
                break;
            case R.id.iv_left:
                clickLeftMenu();
                break;
            case R.id.iv_mood:
                clickMood();
                break;
        }
    }

    private void clickLeftMenu() {
        ((MainActivity) getActivity()).toggle();
    }

    private void clickMood() {
        //todo 弹出广告
        AdvertisingDialog dialog = new AdvertisingDialog(getActivity());
        dialog.show("1", "2", "1", "1");
        dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).startActivityForResultAnim(new Intent(getActivity(), MyMoodActivity.class), Constants.REQUEST_CODE_2);
            }
        });
    }

    @Override
    public void onCloseRefresh() {
        super.onCloseRefresh();
        recyclerView.refreshComplete(0);
    }

    @Override
    public void onGetFriendListOK(List<FriendListBean> list) {
        friendsRvAdapter.setDataList(list);
        recyclerView.refreshComplete(100);
    }

    @Override
    public void onGetNewFriendApplyCountOK(Integer count) {
        ((MainActivity)getActivity()).refreshFriendCount(count.intValue());

        FriendsRvAdapter.HeaderViewHolder headerViewHolder = friendsRvAdapter.getHeaderViewHolder();
        if (count.intValue() > 0) {
            headerViewHolder.llNewFriendMsgCoung.setVisibility(View.VISIBLE);
            headerViewHolder.tvNewFriendMsgCount.setText(String.valueOf(count));
        } else {
            headerViewHolder.llNewFriendMsgCoung.setVisibility(View.GONE);
        }

    }

    @Override
    public void onGetNewMsgCount(Integer count) {
        ((MainActivity) getActivity()).refreshMsgCount(count.intValue());
//        if (count <= 0) {
//            llMsgCount.setVisibility(View.GONE);
//            return;
//        }
//        if (count > 99) {
//            tvMsgCount.setText("99+");
//            tvMsgCount.setTextSize(TypedValue.COMPLEX_UNIT_PX, 15);
//        } else {
//            tvMsgCount.setText(count.toString());
//            tvMsgCount.setTextSize(TypedValue.COMPLEX_UNIT_PX, 20);
//        }
//        llMsgCount.setVisibility(View.VISIBLE);
    }

}
