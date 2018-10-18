package com.yihuan.sharecalendar.ui.view.popwin;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.yihuan.sharecalendar.R;
import com.yihuan.sharecalendar.modle.data.TimeUtils;
import com.yihuan.sharecalendar.ui.adapter.recyclerview.listener.OnRvItemClickListener;
import com.yihuan.sharecalendar.ui.view.popwin.adapter.PeriodRvAdapter;
import com.yihuan.sharecalendar.utils.UIUtils;

import java.util.List;

/**
 * Created by Ronny on 2017/11/6.
 * 活动周期 & 提醒
 */

public class CreateActivePeriodPop extends BasePopWindow {
    TextView tvTitle;
    RecyclerView recyclerView;
    Button btnCancle;

    private OnRvItemClickListener listener;
    private PeriodRvAdapter adapter;
    private int lastClickPosition;//上一次点击的位置

    public CreateActivePeriodPop(Context context, View clickView, int lastClickPosition) {
        super(context, clickView, R.layout.pop_active_period);
        this.lastClickPosition = lastClickPosition;
    }

    protected void init() {
        View view = createPop();
        setParams();
        setOutClickClose(view);
        initPopView(view);
        setPopDismissBackground();
    }

    @Override
    protected int getWidth() {
        return UIUtils.getScreenWidth(mContext)*9/10;
    }

    @Override
    protected int getHeight() {
        return UIUtils.getScreenHeight(mContext)*2/3;
    }

    @Override
    protected int getAnimationStyle() {
        return R.style.pop_anim_alpha;
    }

    @Override
    protected void initPopView(View view) {
        tvTitle = (TextView) view.findViewById(R.id.tv_title);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        btnCancle = (Button) view.findViewById(R.id.btn_cancle);
        adapter = new PeriodRvAdapter();
        UIUtils.initRecyclerView(mContext, recyclerView, adapter, true);

        btnCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        adapter.setOnRvItemClickListener(new OnRvItemClickListener() {
            @Override
            public void onItemClick(RecyclerView.ViewHolder holder, int position, List mList) {
                if(listener != null){
                    listener.onItemClick(holder, position, mList);
                    dismiss();
                }
            }
        });

    }

    private void show(){
        popupWindow.showAtLocation(mClickView, Gravity.CENTER, 0, 0);
        setPopShowBackground();
    }

    /**
     * 显示周期
     */
    public void showPeriod() {
        this.show();
        tvTitle.setText("周期");
        List<String> list = TimeUtils.getCycle();
        adapter.setDataList(list, lastClickPosition);
    }

    /**
     * 显示提醒
     */

    public void showRemind(){
        this.show();
        tvTitle.setText("添加提醒");
        List<String> list = TimeUtils.getRemind();
        adapter.setDataList(list, lastClickPosition);
    }

    public void setOnRvItemClickListener(OnRvItemClickListener<PeriodRvAdapter.ItemViewHolder, String> listener){
      if(adapter != null){
          adapter.setOnRvItemClickListener(listener);
      }
    }
}
