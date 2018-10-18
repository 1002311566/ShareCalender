package com.yihuan.sharecalendar.ui.view.dialog;

import android.content.Context;
import android.text.TextUtils;
import android.widget.TextView;

import com.yihuan.sharecalendar.R;
import com.yihuan.sharecalendar.utils.UIUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Ronny on 2018/1/12.
 * 带说明文字和确定按钮的dialog
 */

public class SimpleDialog extends BaseDialog {

    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.tv_btn_content)
    TextView tvBtnContent;

    private String mContent;
    private String btnContent;


    public SimpleDialog(Context context, String content) {
        super(context, R.style.dialog);
        mContent = content;
    }

    public SimpleDialog(Context context, String content, String btnContent) {
        super(context,R.style.dialog);
        this.btnContent = btnContent;
    }

    @Override
    protected int setDialogHeight() {
        return 0;
    }

    @Override
    protected int setDialogWidth() {
        return UIUtils.getScreenWidth(getContext()) * 3/4;
    }

    @Override
    protected int setLayoutId() {
        return R.layout.dialog_simple;
    }

    @Override
    protected void initView() {
        tvContent.setText(TextUtils.isEmpty(mContent) ? "暂无说明！": mContent);
        if(!TextUtils.isEmpty(btnContent)){
            tvBtnContent.setText(btnContent);
        }
    }

    @Override
    protected void setListener() {

    }

    @OnClick(R.id.tv_btn_content)
    public void onViewClicked() {
        dismiss();
    }
}
