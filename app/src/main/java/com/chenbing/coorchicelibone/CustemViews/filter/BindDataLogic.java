package com.chenbing.coorchicelibone.CustemViews.filter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

/**
 * Created by coorchice on 2017/11/15.
 */

public interface BindDataLogic<T> {

    /**
     * 数据到UI的映射逻辑。
     *
     * @param parent  itemView所在的RecyclerView。
     * @param itemView  用于展示该条数据的ItemView。
     * @param data  数据集合。
     * @param position  第几条数据。
     * @param state 表示状态。通常，在处理交互时，容器内的一些状态会发生改变，可以用这个参数来表示这些改变。具体类型需要看具体的实现。
     *              也可能为null。所以在使用前需要进行判null。这是最后一个参数群的一个用法示例：
     *
     *              if (state != null){
     *                  for (Object o : state) {
     *                      if (o != null){
     *                          LogUtils.e(o.getClass().getName());
     *                      }
     *                  }
     *              }
     *
     *              重要的提示，该参数第一个不能为null，否则将引起Crash。但可以选择一个参数都不传。
     */
     void bindData(RecyclerView parent, View itemView, List<T> datas, int position, Object...state);
}
