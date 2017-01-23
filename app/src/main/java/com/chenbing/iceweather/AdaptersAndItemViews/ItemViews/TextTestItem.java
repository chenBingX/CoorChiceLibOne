package com.chenbing.iceweather.AdaptersAndItemViews.ItemViews;

import com.chenbing.iceweather.R;
import com.chenbing.iceweather.Views.BaseView.BaseRelativeLayout;
import com.chenbing.iceweather.interfaces.ItemViewSetDataFunction;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import butterknife.BindView;

/**
 * Project Name:IceWeather
 * Author:IceChen
 * Date:2016/10/17
 * Notes:
 */

public class TextTestItem extends BaseRelativeLayout implements ItemViewSetDataFunction {
  @BindView(R.id.tv)
  TextView tv;

  private String data;

  public TextTestItem(Context context) {
    super(context);
  }

  public TextTestItem(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public TextTestItem(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  @Override
  protected int getLayout() {
    return R.layout.item_test;
  }

  @Override
  public <T> void setData(T t) {
    data = (String) t;
    tv.setText(data);
  }
}
