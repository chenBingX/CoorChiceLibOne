package com.chenbing.iceweather.Views;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import com.chenbing.iceweather.R;
import com.chenbing.iceweather.AdaptersAndItemViews.Adapters.TextSetAdapter;
import com.chenbing.iceweather.Datas.TextProperty;
import com.chenbing.iceweather.Helpers.DrawTextBmpHelper;
import com.chenbing.iceweather.Utils.DisplayUtils;
import com.chenbing.iceweather.Utils.ReflectUtils;
import com.chenbing.iceweather.Views.BaseView.BaseActivity;

import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DrawTextActivity extends BaseActivity {
  @BindView(R.id.tv_text_info)
  TextView textInfo;
  @BindView(R.id.iv_display_text)
  ImageView displayText;
  @BindView(R.id.rv_text_set_list)
  RecyclerView textSetList;
  @BindView(R.id.et_input_string)
  EditText input;

  private List<TextProperty> datas = new ArrayList<>();
  private TextSetAdapter adapter;
  private DrawTextBmpHelper drawTextBmpHelper;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_draw_text);
    ButterKnife.bind(this);
    initData();
    initView();
    addListener();
  }

  @Override
  protected void initData() {
    drawTextBmpHelper = new DrawTextBmpHelper();

    TextProperty data = null;

    data = new TextProperty("setTextSize", TextProperty.TextPropertyType.INPUT, Float.class);
    datas.add(data);
    data = new TextProperty("setTextScaleX", TextProperty.TextPropertyType.INPUT, Float.class);
    datas.add(data);
    data = new TextProperty("setTextSkewX", TextProperty.TextPropertyType.INPUT, Float.class);
    datas.add(data);

    data = new TextProperty("setStrikeThruText", TextProperty.TextPropertyType.SELECTE, Boolean.class);
    datas.add(data);
    data = new TextProperty("setUnderlineText", TextProperty.TextPropertyType.SELECTE, Boolean.class);
    datas.add(data);
    data = new TextProperty("setFakeBoldText", TextProperty.TextPropertyType.SELECTE, Boolean.class);
    datas.add(data);
  }

  @Override
  protected void initView() {
    textInfo.setMovementMethod(ScrollingMovementMethod.getInstance());

    textSetList
        .setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    textSetList.setItemAnimator(new DefaultItemAnimator());
    textSetList.addItemDecoration(new RecyclerView.ItemDecoration() {
      @Override
      public void getItemOffsets(Rect outRect, int itemPosition, RecyclerView parent) {
        if (itemPosition != 0) {
          outRect.top = (int) DisplayUtils.dipToPx(0.5f);
        }
        if (itemPosition != parent.getLayoutManager().getChildCount() - 1){
          outRect.bottom = (int) DisplayUtils.dipToPx(0.5f);
        }
      }
    });
    adapter = new TextSetAdapter(this, datas);
    textSetList.setAdapter(adapter);
  }

  @Override
  protected void addListener() {
    adapter.setOnDataChangedListener(datas -> {
//      textInfo.setText(GsonUtils.getSingleInstance().toJson(datas));
      refreshText(datas);
    });
  }

  private void refreshText(List<TextProperty> datas) {
    Paint paint = drawTextBmpHelper.getPaint();
    for (TextProperty data : datas) {
      if (data.getValue() == null || data.getValue().equals("")) continue;
      try {
        ReflectUtils.invokeApiMethod(paint, data.getMethod(),
            changeValueType(data.getValue(), data.getValueClass()), data.getValueClass());
      } catch (IllegalAccessException e) {
        e.printStackTrace();
      } catch (InstantiationException e) {
        e.printStackTrace();
      } catch (InvocationTargetException e) {
        e.printStackTrace();
      } catch (NoSuchMethodException e) {
        e.printStackTrace();
      }
    }
    String drawString;
    if (input.getText().toString().equals("")){
      drawString = "Hello World!";
    } else {
      drawString = input.getText().toString();
    }
    Bitmap bmp = drawTextBmpHelper.drawBitmap(drawString);
    displayText.setImageBitmap(bmp);
    // bmp.recycle();
    textInfo.setText("Ascent = " + paint.ascent() + ", "
      + "Top = " + paint.getFontMetrics().top + "\n"
      + "Descent = " + paint.descent() + ", "
      + "Bottom = " + paint.getFontMetrics().bottom + "\n"
    );
  }

  private <T> T changeValueType(String value, Class<T> clazz) {
    if (clazz.isAssignableFrom(Boolean.class)){
      return (T) Boolean.valueOf(value);
    } else if (clazz.isAssignableFrom(Integer.class)){
      return (T) Integer.valueOf(value);
    } else {
      return (T) Float.valueOf(value);
    }
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
  }
}
