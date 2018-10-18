package com.yihuan.sharecalendar.ui.view;

import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.yihuan.sharecalendar.R;
import com.yihuan.sharecalendar.global.base.BaseActivity;
import com.yihuan.sharecalendar.global.base.BasePresenter;
import com.yihuan.sharecalendar.ui.adapter.Adapter;

import java.util.ArrayList;


public class TActivity extends BaseActivity {

//    private AutoProgress progress;
//    private Handler handler = new Handler();
    private RecyclerView recyclerView;
    private Adapter adapter;

    @Override
    protected BasePresenter setPresenter() {
        return null;
    }

    @Override
    protected void initTitleView(TitleView titleView) {
        titleView.setCenterText("测试");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activit_test;
    }

    int i = 0;
    @Override
    protected void initView() {
//        progress = (AutoProgress) findViewById(R.id.progress);
//        handler.post(new Runnable() {
//            @Override
//            public void run() {
//                progress.setCurrentSize(i++);
//                if(i < 100){
//                    handler.postDelayed(this, 1000);
//                }
//            }
//        });
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        adapter = new Adapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        test();
    }

    private void test() {
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i< 10;i++){
            list.add("");
        }
        adapter.setDataList(list);
    }

    @Override
    protected void refreshData() {

    }
}
