package com.yihuan.sharecalendar.ui.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.view.Gravity;
import android.view.WindowManager;

import com.yihuan.sharecalendar.R;
import com.yihuan.sharecalendar.utils.UIUtils;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Ronny on 2017/12/14.
 */

public abstract class BaseDialog extends Dialog {


    private Unbinder bind;

    public BaseDialog(Context context) {
        super(context);
    }

    public BaseDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    protected BaseDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setLayoutId());
        bind = ButterKnife.bind(this);
        initView();
        setListener();
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.height = setDialogHeight() == 0 ? WindowManager.LayoutParams.WRAP_CONTENT : setDialogHeight();
        params.width = setDialogWidth() == 0 ? WindowManager.LayoutParams.WRAP_CONTENT : setDialogWidth();
        getWindow().setAttributes(params);
    }

    protected abstract int setDialogHeight();

    protected abstract int setDialogWidth();

    protected abstract int setLayoutId();

    protected abstract void initView();

    protected abstract void setListener();

    @Override
    public void dismiss() {
        super.dismiss();
        bind.unbind();
    }
}
