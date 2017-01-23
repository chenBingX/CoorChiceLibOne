package com.chenbing.iceweather.AdaptersAndItemViews.ItemViews;

import com.chenbing.iceweather.R;
import com.chenbing.iceweather.Datas.Navigator;
import com.chenbing.iceweather.Views.BaseView.BaseRelativeLayout;
import com.chenbing.iceweather.interfaces.ItemViewSetDataFunction;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.widget.TextView;

import butterknife.BindView;

/**
 * Project Name:IceWeather
 * Author:IceChen
 * Date:2016/10/9
 * Notes:
 */
public class NavigatorItemView extends BaseRelativeLayout implements ItemViewSetDataFunction{
  @BindView(R.id.tv_page_name)
  TextView tvPageName;
  private Navigator navigator;

  public NavigatorItemView(Context context) {
    super(context);
  }

  public NavigatorItemView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public NavigatorItemView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  @Override
  protected int getLayout() {
    return R.layout.item_navigator;
  }

  @Override
  public <T> void setData(T t) {
    this.navigator = (Navigator) t;
    tvPageName.setText(navigator.getPageName());
    addListener();
  }

  private void addListener() {
    tvPageName.setOnClickListener(v -> {
      try {
        Intent intent = new Intent(getContext(), Class.forName(navigator.getPageClassName()));
        getContext().startActivity(intent);
      } catch (ClassNotFoundException e) {
        e.printStackTrace();
      }
    });
  }
}
