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
 * Date:2016/10/9
 * Notes:
 */
public class HorizontalPinterestItemView extends BaseRelativeLayout
    implements
      ItemViewSetDataFunction {
  @BindView(R.id.circle)
  TextView tvCircle;

  public HorizontalPinterestItemView(Context context) {
    super(context);
  }

  public HorizontalPinterestItemView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public HorizontalPinterestItemView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  @Override
  protected int getLayout() {
    return R.layout.item_horizontal_pinterest;
  }

  @Override
  public <T> void setData(T t) {
    tvCircle.setText((String) t);
  }

  private void addListener() {

  }
}
