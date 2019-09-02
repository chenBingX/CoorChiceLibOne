package com.chenbing.coorchicelibone.Views;

import com.chenbing.iceweather.R;
import com.chenbing.coorchicelibone.Utils.LogUtils;
import com.chenbing.coorchicelibone.Views.BaseView.BaseActivity;

import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import butterknife.ButterKnife;

public class WindowActivity extends BaseActivity  {

  private Window mWindow;
  private WindowManager.LayoutParams windowLayoutParams;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    getWindow().requestFeature(Window.FEATURE_NO_TITLE);
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_window);
    ButterKnife.bind(this);

    initData();
    initView();
    addListener();
  }


  @Override
  protected void initData() {

  }

  @Override
  protected void initView() {
    // 透明状态栏
    getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
    getWindow().setFormat(PixelFormat.TRANSPARENT);

    // 透明导航栏
//    getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//    setupWindowAttributes();
  }

  private void setupWindowAttributes() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      Window window = getWindow();
      window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
          | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

      window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
          | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);

//      window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//      window.setStatusBarColor(Color.YELLOW);
//      window.setNavigationBarColor(Color.YELLOW);
    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
      LogUtils.e("KITKAT");
      // 透明状态栏
      getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
      getWindow().setFormat(PixelFormat.TRANSPARENT);

      // 透明导航栏
      getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

      // 带有actionbar或者标题栏的话要在布局文件里加入android:fitsSystemWindows="true"
      // Theme <item name="android:fitsSystemWindows">true</item>
    }

    // this.onWindowAttributesChanged(getWindow().getAttributes()); //通知Window参数改变
//    mWindow = getWindow();
//    //// mWindow.getDecorView().setSystemUiVisibility(
//    //// View.INVISIBLE
//    //// );
//    // if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
//    // mWindow.setStatusBarColor(Color.YELLOW);
//    // }
//    //
//    windowLayoutParams = mWindow.getAttributes();
//    windowLayoutParams.width = DisplayUtils.dipToPx(300);
//    windowLayoutParams.height = DisplayUtils.dipToPx(300);
//    // windowLayoutParams.gravity = Gravity.CENTER|Gravity.BOTTOM;
//    windowLayoutParams.gravity = Gravity.LEFT;
//    windowLayoutParams.x = DisplayUtils.dipToPx(10);
////    windowLayoutParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND |
////        WindowManager.LayoutParams.FLAG_SHOW_WALLPAPER |
////      WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE|
////      WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON|
////      WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN|
////      WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS|
////      WindowManager.LayoutParams.FLAG_FULLSCREEN
////
////    ;
//    windowLayoutParams.format = PixelFormat.TRANSPARENT;
//    windowLayoutParams.alpha = 0.3f; // 设置窗口透明度
//    windowLayoutParams.dimAmount = 0.9f;
//    windowLayoutParams.screenBrightness = 0.8f;
////    windowLayoutParams.screenOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
//    windowLayoutParams.rotationAnimation = WindowManager.LayoutParams.ROTATION_ANIMATION_CROSSFADE;
//    this.onWindowAttributesChanged(windowLayoutParams);

    // View view = new View(this);
    // view.setBackgroundColor(Color.YELLOW);
    // ViewGroup.LayoutParams lp = new
    // ViewGroup.LayoutParams(DisplayUtils.dipToPx(150),DisplayUtils.dipToPx(150));
    // view.setLayoutParams(lp);
    // WindowManager.LayoutParams params = new WindowManager.LayoutParams(
    // WindowManager.LayoutParams.WRAP_CONTENT,
    // WindowManager.LayoutParams.WRAP_CONTENT,
    // WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY,
    // 0,
    //// WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
    //// | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
    // PixelFormat.TRANSLUCENT);
    // params.gravity = Gravity.RIGHT | Gravity.TOP;
    // params.setTitle("Load Average");
    // WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
    // wm.addView(view, params);
  }

  @Override
  protected void addListener() {

  }
}
