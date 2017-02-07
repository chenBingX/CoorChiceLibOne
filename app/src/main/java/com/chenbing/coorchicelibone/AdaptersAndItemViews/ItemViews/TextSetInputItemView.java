package com.chenbing.coorchicelibone.AdaptersAndItemViews.ItemViews;

import java.util.regex.Pattern;

import com.chenbing.coorchicelibone.Datas.TextProperty;
import com.chenbing.iceweather.R;
import com.chenbing.coorchicelibone.Views.BaseView.BaseRelativeLayout;
import com.chenbing.coorchicelibone.interfaces.ItemViewSetDataFunction;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;

/**
 * Project Name:IceWeather
 * Author:IceChen
 * Date:2016/10/10
 * Notes:
 */
public class TextSetInputItemView extends BaseRelativeLayout implements ItemViewSetDataFunction {
  @BindView(R.id.et_display_value)
  EditText displayValue;
  @BindView(R.id.tv_display_method)
  TextView displayMethod;

  private TextProperty data;
  private OnValueChangedListener onValueChangedListener;

  public TextSetInputItemView(Context context) {
    super(context);
  }

  public TextSetInputItemView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public TextSetInputItemView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    initView();
  }

  private void initView() {

  }

  @Override
  protected int getLayout() {
    return R.layout.item_text_set_input;
  }

  @Override
  public <T> void setData(T t) {
    data = (TextProperty) t;
    displayMethod.setText(data.getMethod());
    displayValue.setText(data.getValue());
//    if (!(data.getValueClass().isAssignableFrom(String.class))) {
//      displayMethod.setSingleLine();
//      displayMethod.setInputType(InputType.TYPE_CLASS_NUMBER);
//    }
    // displayValue.setFocusable(false);
    addListener();
  }

  private void addListener() {
    displayValue.setOnEditorActionListener((textView, i, keyEvent) -> {
      checkValueThenModifyData();
      return false;
    });
  }

  private void checkValueThenModifyData() {
    String value = displayValue.getText().toString();
    String regEx = "^(-?\\d+)(\\.\\d+)?$";
    Pattern pattern = Pattern.compile(regEx);
    if (pattern.matcher(value).matches()) {
      data.setValue(value);
      if (onValueChangedListener != null) {
        onValueChangedListener.onValueChanged(value);
      }
    }
  }

  public void setOnValueChangedListener(OnValueChangedListener onValueChangedListener) {
    this.onValueChangedListener = onValueChangedListener;
  }


  public interface OnValueChangedListener {
    void onValueChanged(String value);
  }
}
