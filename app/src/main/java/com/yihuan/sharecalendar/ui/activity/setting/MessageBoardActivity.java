package com.yihuan.sharecalendar.ui.activity.setting;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.yihuan.sharecalendar.R;
import com.yihuan.sharecalendar.global.base.BaseActivity;
import com.yihuan.sharecalendar.global.base.BasePresenter;
import com.yihuan.sharecalendar.presenter.MessageBoardPresenter;
import com.yihuan.sharecalendar.presenter.contract.MessageBoardContract;
import com.yihuan.sharecalendar.ui.view.TitleView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Ronny on 2017/9/15.
 * 留言板
 */

public class MessageBoardActivity extends BaseActivity<MessageBoardPresenter> implements MessageBoardContract.View {


    @BindView(R.id.ll_up)
    LinearLayout llUp;
    @BindView(R.id.ll_down)
    LinearLayout llDown;
    @BindView(R.id.et_content)
    EditText etContent;
    @BindView(R.id.btn_commit)
    Button btnCommit;

    @Override
    protected BasePresenter setPresenter() {
        return new MessageBoardPresenter(this);
    }

    @Override
    protected void initTitleView(TitleView titleView) {
        titleView.setCenterText("留言板");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_message_board;
    }

    @Override
    protected void initView() {
        refreshUI(0);
    }

    @Override
    protected void refreshData() {

    }

    @Override
    public void commitOK() {
        this.finish();
    }

    @OnClick({R.id.ll_up, R.id.ll_down, R.id.btn_commit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_up:
                refreshUI(0);
                break;
            case R.id.ll_down:
                refreshUI(1);
                break;
            case R.id.btn_commit:
                commit();
                break;
        }
    }

    private void commit() {
        String content = etContent.getText().toString().trim();
        String type = llUp.isSelected() ? "1" : "2";
        mPresenter.commit(type, content);
    }

    private void refreshUI(int i) {
        llUp.setSelected(i == 0);
        llDown.setSelected(i == 1);
    }
}
