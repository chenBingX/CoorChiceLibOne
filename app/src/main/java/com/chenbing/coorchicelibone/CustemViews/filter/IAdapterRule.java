package com.chenbing.coorchicelibone.CustemViews.filter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by coorchice on 2017/11/14.
 */

public interface IAdapterRule<T> {

    int itemCount(List<T> datas);
    int layout(int position, List<T> datas);
    void bindData(RecyclerView.Adapter adapter, View itemView, List<T> datas, int position);
}
