package com.yihuan.sharecalendar.ui.activity.setting;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.yihuan.sharecalendar.R;
import com.yihuan.sharecalendar.global.Constants;
import com.yihuan.sharecalendar.global.base.BaseActivity;
import com.yihuan.sharecalendar.global.base.BasePresenter;
import com.yihuan.sharecalendar.ui.view.TitleView;

import butterknife.BindView;

/**
 * Created by Ronny on 2017/9/22.
 * 设置昵称，签名等
 */

public class SetInfo_SetEditActivity extends BaseActivity {

    @BindView(R.id.et_content)
    EditText etContent;
    private String intoType;
    private String content;

    @Override
    protected BasePresenter setPresenter() {
        return null;
    }

    @Override
    protected void initTitleView(TitleView titleView) {
        final Intent intent = getIntent();
        if(intent  != null ){
            intoType = intent.getStringExtra(Constants.INTENT_EDIT_TYPE);
            if(!TextUtils.isEmpty(intoType)){
                if(intoType.equals(Constants.INTENT_EDIT_TYPE_NICKNAME)){
                    titleView.setCenterText("昵称");
                    etContent.setHint("请输入12位以内的昵称");
                    etContent.setText(intent.getStringExtra(Constants.INTENT_EDIT_TYPE_NICKNAME));
                }else if(intoType.equals(Constants.INTENT_EDIT_TYPE_SIGNATURE)){
                    titleView.setCenterText("我的签名");
                    etContent.setHint("请输入30位以内的签名");
                    etContent.setText(intent.getStringExtra(Constants.INTENT_EDIT_TYPE_SIGNATURE));
                }
                etContent.setSelection(etContent.getText().length());
                showSoftKey();
            }
        }
        titleView.setRightText("保存");
        titleView.setOnRightClickListener(new TitleView.OnRightClickListener() {
            @Override
            public void onRightListener(View v) {
                String s = etContent.getText().toString().trim();
                if(intoType.equals(Constants.INTENT_EDIT_TYPE_NICKNAME)){
                    if(TextUtils.isEmpty(s)){
                        showToast("内容不能为空！");
                        return;
                    }
                    if(s.length() > 12){
                        showToast("昵称长度过长！");
                        return;
                    }
                }else if(intoType.equals(Constants.INTENT_EDIT_TYPE_SIGNATURE)){
                    if(s != null && s.length() > 30){
                        showToast("签名内容过长！");
                        return;
                    }
                }
                Intent intent1 = new Intent();
                intent1.putExtra(Constants.INTENT_EDIT_RESULT, s);
                setResult(RESULT_OK, intent1);
                finish();
            }
        });

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_setinfo_setedit;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void refreshData() {

    }
}
