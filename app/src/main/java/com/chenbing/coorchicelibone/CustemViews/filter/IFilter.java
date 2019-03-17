package com.chenbing.coorchicelibone.CustemViews.filter;

import java.util.List;
import java.util.Map;

/**
 * Created by coorchice on 2017/11/15.
 */

public interface IFilter<T> {

    /**
     * 设置过滤器的数据。
     *
     * @param datas
     */
    void setDatas(Map<MagicData<String>, List<MagicData<T>>> datas);

    /**
     * 设置过滤器监听。监听取消和确定事件。
     *
     * @param onClickListener
     */
    public void setOnClickListener(OnClickListener onClickListener);

    public static interface OnClickListener<T>{
        /**
         * 点击取消按钮。
         */
        void onCancel();

        /**
         * 点击确定按钮。
         * @param result 筛选条件结果。
         */
        void onConfirm(Map<MagicData<String>, List<MagicData<T>>> result);
    }
}
