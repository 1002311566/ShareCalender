package com.yihuan.sharecalendar.ui.view.popwin;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.yihuan.sharecalendar.R;
import com.yihuan.sharecalendar.modle.bean.active.TimeBean;
import com.yihuan.sharecalendar.modle.data.TimeUtils;
import com.yihuan.sharecalendar.ui.view.SelectorView;
import com.yihuan.sharecalendar.utils.UIUtils;

import java.util.List;


/**
 * Created by Ronny on 2017/12/12.
 */

public class SolarPop extends BasePopWindow {
    //todo------------------------注意：月份转换日历都需要-1-----------------
    private SelectorView sv1;
    private SelectorView sv2;
    private Button btnCancle;
    private Button btnEnter;
    private OnSelectDateListener listener;
    private TimeBean timeBean;//当前选择的时间

    public SolarPop(Context context, View clickView) {
        super(context, clickView, R.layout.pop_solar_birthday);
    }

    @Override
    protected int getWidth() {
        return UIUtils.getScreenWidth(mContext)*9/10;
    }

    @Override
    protected int getHeight() {
        return WindowManager.LayoutParams.WRAP_CONTENT;
    }

    @Override
    protected int getAnimationStyle() {
        return R.style.pop_anim_scale;
    }

    @Override
    protected void initPopView(View view) {
        sv1 = (SelectorView) view.findViewById(R.id.sv_1);
        sv2 = (SelectorView) view.findViewById(R.id.sv_2);
        btnCancle = (Button) view.findViewById(R.id.btn_cancle);
        btnEnter = (Button) view.findViewById(R.id.btn_enter);
        btnCancle.setOnClickListener(this);
        btnEnter.setOnClickListener(this);

        initData();
    }

    private void initData() {
        timeBean = new TimeBean();
        timeBean.setYMD(TimeUtils.getCurrentYear(), 0, 1);

        List<Integer> month = TimeUtils.getMonthData();
        sv1.setData(month, 0, "月");
        final List<Integer> days = TimeUtils.getDays(timeBean.getYear(), timeBean.getMonth());
        sv2.setData(days,0, "日");

        sv1.setOnSelectListener(new SelectorView.OnSelectListener() {
            @Override
            public void onSelect(View view, Integer text) {
                //todo 月份选择
                timeBean.setMonth(text-1);
                List<Integer> days1 = TimeUtils.getDays(timeBean.getYear(), timeBean.getMonth());
                sv2.setData(days1,0, "日");
            }
        });

        sv2.setOnSelectListener(new SelectorView.OnSelectListener() {
            @Override
            public void onSelect(View view, Integer text) {
                //todo 日选择
                timeBean.setDay(text);
            }
        });
    }

    @Override
    protected boolean isOpenGrayBG() {
        return true;
    }

    public void show(){
        popupWindow.showAtLocation(mClickView, Gravity.CENTER, 0, 0);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.btn_cancle:
                dismiss();
                break;
            case R.id.btn_enter:
                if(listener != null){
                    listener.onSelect(timeBean);
                }
                break;
        }
    }

    public interface OnSelectDateListener{
        void onSelect(TimeBean timeBean);
    }
    public void setOnSelectDateListener(OnSelectDateListener listener){
        this.listener = listener;
    }

}
