package com.chenbing.iceweather.AdaptersAndItemViews.ItemViews;

import com.chenbing.iceweather.R;
import com.chenbing.iceweather.Datas.TextProperty;
import com.chenbing.iceweather.Utils.LogUtils;
import com.chenbing.iceweather.Views.BaseView.BaseRelativeLayout;
import com.chenbing.iceweather.interfaces.ItemViewSetDataFunction;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import butterknife.BindView;

/**
 * Project Name:IceWeather
 * Author:IceChen
 * Date:2016/10/10
 * Notes:
 */
public class TextSetSelectItemView extends BaseRelativeLayout implements ItemViewSetDataFunction {
  @BindView(R.id.tv_display_method)
  TextView displayMethod;
  private TextProperty data;
  private OnValueChangedListener onValueChangedListener;
  private boolean cache = false;

  public TextSetSelectItemView(Context context) {
    super(context);
  }

  public TextSetSelectItemView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public TextSetSelectItemView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  @Override
  protected int getLayout() {
    return R.layout.item_text_set_select;
  }

  @Override
  public <T> void setData(T t) {
    data = (TextProperty) t;
    displayMethod.setText(data.getDisplayContent());
    addListener();
  }

  private void addListener() {
    displayMethod.setOnClickListener(v -> {
      cache = !cache;
      data.setValue(String.valueOf(cache));
      if (onValueChangedListener != null) {
        onValueChangedListener.onValueChanged();
      }
      displayMethod.setText(data.getDisplayContent());
    });
  }

  public void setOnValueChangedListener(OnValueChangedListener onValueChangedListener) {
    this.onValueChangedListener = onValueChangedListener;
  }

  public interface OnValueChangedListener {
    void onValueChanged();
  }
}
