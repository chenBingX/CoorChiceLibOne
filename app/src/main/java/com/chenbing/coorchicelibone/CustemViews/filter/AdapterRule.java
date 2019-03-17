package com.chenbing.coorchicelibone.CustemViews.filter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

/**
 * Created by coorchice on 2017/11/20.
 */

public abstract class AdapterRule<T> implements IAdapterRule<T> {

    @Override
    public int itemCount(List<T> datas){
        return datas.size();
    }

    public abstract int layout(int position, List<T> datas);

    public abstract void bindData(RecyclerView.Adapter adapter, View itemView, List<T> datas, int position);
}
