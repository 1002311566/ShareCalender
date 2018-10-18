package com.yihuan.sharecalendar.ui.activity.active;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.yihuan.sharecalendar.R;
import com.yihuan.sharecalendar.ui.adapter.viewpager.ImgLookVpAdapter;

import java.util.ArrayList;

/**
 * Created by Ronny on 2018/1/31.
 */

public class ImgLookerActivity extends AppCompatActivity {
    ViewPager viewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        overridePendingTransition(android.R.anim.fade_in,
                android.R.anim.fade_out);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_img_looker);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        initView();
    }

    private void initView() {
        Bundle bundle = getIntent().getBundleExtra("img");
        if(bundle != null){
            ArrayList<String> imgList = bundle.getStringArrayList("list");
            int position = bundle.getInt("position", 0);
            ImgLookVpAdapter imgLookVpAdapter = new ImgLookVpAdapter(this, imgList);
            viewPager.setAdapter(imgLookVpAdapter);
            viewPager.setCurrentItem(position);
        }
    }

    public static void start(Activity activity, ArrayList<String> mList, int position) {
        Intent intent = new Intent(activity, ImgLookerActivity.class);
        Bundle bundle = new Bundle();
        bundle.putStringArrayList("list", mList);
        bundle.putInt("position", position);
        intent.putExtra("img", bundle);
        activity.startActivity(intent);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(android.R.anim.fade_in,
                android.R.anim.fade_out);
    }
}
