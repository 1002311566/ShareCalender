package com.yihuan.sharecalendar.ui.adapter.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yihuan.sharecalendar.global.Constants;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by Ronny on 2017/9/26.
 */

public abstract class BaseRvAdapter<T> extends RecyclerView.Adapter {

    protected List<T> mList;
    private Context context;


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        RecyclerView.ViewHolder holder = null;
        View view =null;
        if(viewType == Constants.TYPE_ITEM){
            view = inflater.inflate(getItemLayoutId(), parent, false);
           holder =  onCreateItemHolder(view);
        }else if(viewType == Constants.TYPE_HEAD){
            view = inflater.inflate(getHeaderLayoutId(), parent, false);
            holder = onCreateHeaderHolder(view);
        }else if(viewType == Constants.TYPE_FOOT){
            view = inflater.inflate(getFootLayoutId(), parent, false);
            holder = onCreateFootHolder(view);
        }else{
            holder = onOtherHolder(parent, viewType);
        }
        return holder;
    }

    protected  RecyclerView.ViewHolder onOtherHolder(ViewGroup context, int viewType){
        return null;
    }


    public Context getContext(){
        return context;
    }

    @Override
    public int getItemViewType(int position) {
        return Constants.TYPE_ITEM;
    }

    protected abstract int getItemLayoutId();

    protected abstract RecyclerView.ViewHolder onCreateItemHolder(View view);

    /**
     * 如果有头布局需要重写改方法
     * @return
     */
    protected int getHeaderLayoutId() {
        return 0;
    }

    /**
     * 如果有头布局需要重写改方法
     * @return
     */
    protected RecyclerView.ViewHolder onCreateHeaderHolder(View view) {
        return null;
    }


    protected int getFootLayoutId() {
        return 0;
    }

    protected RecyclerView.ViewHolder onCreateFootHolder(View view) {
        return null;
    }

    /**
     * 当不需要对控件做操作时使用该holder
     */
    protected static class ViewHolder extends RecyclerView.ViewHolder{

        public ViewHolder(View itemView) {
            super(itemView);
            AutoUtils.auto(itemView);

        }


    }

    public void setDataList(List<T> list){
        this.mList = list;
        this.notifyDataSetChanged();
    }

    public List<T> getDataList(){
        return mList;
    }

}
