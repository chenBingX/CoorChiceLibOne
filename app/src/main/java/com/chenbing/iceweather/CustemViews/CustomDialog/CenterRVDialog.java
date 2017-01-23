package com.chenbing.iceweather.CustemViews.CustomDialog;

import com.chenbing.iceweather.R;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Project Name:IceWeather
 * Author:IceChen
 * Date:2016/10/8
 * Notes:
 */
public class CenterRVDialog extends Dialog {
  @BindView(R.id.rv_navigator_list)
  RecyclerView navigatorList;

  public CenterRVDialog(Context context) {
    this(context, R.style.custom_dialog);
  }

  public CenterRVDialog(Context context, int themeResId) {
    super(context, themeResId);
    setContentView(R.layout.dialog_navigator);
    ButterKnife.bind(this);
    initView();
  }

  private void initView() {
    setDialogLayoutParams();
  }

  private void setDialogLayoutParams() {
    Window window = getWindow();
    window.getDecorView().setPadding(0, 0, 0, 0);
    WindowManager.LayoutParams layoutParams = window.getAttributes();
    layoutParams.gravity = Gravity.CENTER;
    layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
    this.onWindowAttributesChanged(layoutParams); //通知Window参数改变
  }

  public RecyclerView getNavigatorList() {
    return navigatorList;
  }
}
