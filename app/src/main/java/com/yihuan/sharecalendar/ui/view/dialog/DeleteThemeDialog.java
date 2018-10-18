package com.yihuan.sharecalendar.ui.view.dialog;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.yihuan.sharecalendar.R;
import com.yihuan.sharecalendar.ui.view.dialog.listener.OnEnterAndCancelListener;

/**
 * Created by Ronny on 2018/1/10.
 */

public class DeleteThemeDialog extends BaseDialog implements View.OnClickListener{

    private TextView tvCancel;
    private TextView tvEnter;
    private OnEnterAndCancelListener listener;

    public DeleteThemeDialog(Context context) {
        super(context, R.style.dialog);
    }

    @Override
    protected int setDialogHeight() {
        return 0;
    }

    @Override
    protected int setDialogWidth() {
        return 0;
    }

    @Override
    protected int setLayoutId() {
        return R.layout.dialog_delete_theme;
    }

    @Override
    protected void initView() {
        tvCancel = (TextView) findViewById(R.id.tv_cancel);
        tvEnter = (TextView) findViewById(R.id.tv_enter);
        tvCancel.setOnClickListener(this);
        tvEnter.setOnClickListener(this);
        setCanceledOnTouchOutside(false);
    }

    @Override
    protected void setListener() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_cancel:
                if(listener != null){
                    listener.onCancel();
                }
                dismiss();
                break;
            case R.id.tv_enter:
                if(listener != null){
                    listener.onEnter();
                }
                dismiss();
                break;
        }
    }

    public void setOnEnterAndCancelListener(OnEnterAndCancelListener listener){
        this.listener = listener;
    }
}
