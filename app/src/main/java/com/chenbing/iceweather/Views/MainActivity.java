package com.chenbing.iceweather.Views;

import java.util.ArrayList;
import java.util.List;

import com.chenbing.iceweather.R;
import com.chenbing.iceweather.SsoAuth;
import com.chenbing.iceweather.AdaptersAndItemViews.Adapters.NavigatorAdapter;
import com.chenbing.iceweather.CustemViews.CustomDialog.CenterRVDialog;
import com.chenbing.iceweather.CustemViews.Titanic.Titanic;
import com.chenbing.iceweather.CustemViews.Titanic.TitanicTextView;
import com.chenbing.iceweather.Datas.Navigator;
import com.chenbing.iceweather.Utils.AppUtils;
import com.chenbing.iceweather.Utils.BitmapUtils;
import com.chenbing.iceweather.Utils.DisplayUtils;
import com.chenbing.iceweather.Utils.LogUtils;
import com.chenbing.iceweather.Utils.RenderScriptGaussianBlur;
import com.chenbing.iceweather.Views.BaseView.BaseActivity;
import com.chenbing.iceweather.Views.fragments.OneFragment;

import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends BaseActivity implements LocationListener {

  @BindView(R.id.titanic_options)
  TitanicTextView btnOptions;
  @BindView(R.id.bt1)
  Button btn1;
  @BindView(R.id.bt2)
  Button btn2;
  @BindView(R.id.bt3)
  Button btn3;
  @BindView(R.id.iv_blur)
  ImageView iv;


  SsoAuth ssoAuth;
  private Dialog optionsDialog;
  private final List<Navigator> navigators = new ArrayList<>();
  private Bitmap mBitmap;
  private float blurRadius = 1f;
  private RenderScriptGaussianBlur blur;
  private CompositeDisposable disposable = new CompositeDisposable();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);
    initData();
    initView();
    addListener();
  }

  @Override
  protected void initData() {
    initActivities();

    blur = new RenderScriptGaussianBlur(this);
    mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.test);
    mBitmap = BitmapUtils.changeBitmapSize(mBitmap, 300, 300);
    LogUtils.e("" + mBitmap);
  }

  private void initActivities() {
    addActivityToNavigator(TableActivity.class.getName(), getString(R.string.name_draw_activity));

    addActivityToNavigator(SideSlipListActivity.class.getName(),
        getString(R.string.name_side_slip_list_activity));

    addActivityToNavigator(DrawTextActivity.class.getName(),
        getString(R.string.name_draw_text_activity));

    addActivityToNavigator(HeaderFooterRecyclerViewActivity.class.getName(),
        getString(R.string.header_footer_activity));

    addActivityToNavigator(CrackPasswordActivity.class.getName(),
        getString(R.string.track_password));

    addActivityToNavigator(CustomLayoutManagerActivity.class.getName(),
      getString(R.string.custom_layout_manager));

    addActivityToNavigator(DraggerRecyclerViewActivity.class.getName(),
      getString(R.string.dragger_rv));

    addActivityToNavigator(WindowActivity.class.getName(),
      getString(R.string.window_demo));

    addActivityToNavigator(TableActivity.class.getName(),
        getString(R.string.table_activity));

    addActivityToNavigator(WebActivity.class.getName(),
        getString(R.string.web_view));

    addActivityToNavigator(HorizontalPinterestActivity.class.getName(),
        getString(R.string.horizontal_pinterest_activity));

    addActivityToNavigator(TabActivity.class.getName(),
        "官方TabActivity");

    addActivityToNavigator(RxJavaActivity.class.getName(),
        "RxJavaActivity");

    addActivityToNavigator(DrawActivity.class.getName(),
      "各种自定义View");

    addActivityToNavigator(TaskActivity_A.class.getName(),
      "测试Activity栈");


  }

  private void addActivityToNavigator(String activityName, String dispalyName) {
    Navigator navigator;
    navigator = new Navigator(activityName);
    navigator.setPageName(dispalyName);
    navigators.add(navigator);
  }


  @Override
  protected void initView() {
    new Titanic().start(btnOptions);

    // LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
    // if (ActivityCompat.checkSelfPermission(this,
    // Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
    // && ActivityCompat.checkSelfPermission(this,
    // Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
    // // TODO: Consider calling
    // // ActivityCompat#requestPermissions
    // // here to request the missing permissions, and then overriding
    // // public void onRequestPermissionsResult(int requestCode, String[] permissions,
    // // int[] grantResults)
    // // to handle the case where the user grants the permission. See the documentation
    // // for ActivityCompat#requestPermissions for more details.
    // return;
    // }
//
    // if (locationManager.getProvider(LocationManager.NETWORK_PROVIDER) != null) {
    // locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5, 0, this);
    // } else if (locationManager.getProvider(LocationManager.GPS_PROVIDER) != null) {
    // locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
    // } else {
    // ToastUtil.showShortToast("定位失败...");
    // }


    btn2.setText("Blur");
    iv.setImageBitmap(mBitmap);
  }

  @Override
  protected void addListener() {
    btnOptions.setOnClickListener(v -> {
      // if (ssoAuth == null) {
      // bindSsoAuthService();
      // } else {
      // doSsoAuth();
      // }
//      finish();
    });

    btnOptions.setOnLongClickListener(view -> {
      createOrShowOptionsDialog();
      return true;
    });

    listenBtn1();
    listenBtn2();
  }

  private void listenBtn1() {
    btn1.setOnClickListener((view) -> {
      getSupportFragmentManager().beginTransaction()
          .replace(R.id.fragmentContainer, OneFragment.getInstance())
          .addToBackStack(null)
          .commit();
//      LogUtils.e("Button1被点击！");
    });
  }

  private void listenBtn2() {
    btn2.setOnClickListener(v -> {
      if (blurRadius <= 25) {
        Disposable d = Observable.create(new ObservableOnSubscribe<Bitmap>() {
          @Override
          public void subscribe(ObservableEmitter<Bitmap> e) throws Exception {
            LogUtils.e("当前blurRadius = " + blurRadius);
            Bitmap bitmap = blur.blur(blurRadius, mBitmap);
            blurRadius++;
            if (blurRadius == 25){
              blurRadius = 1;
            }
            e.onNext(bitmap);
          }
        })
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(new DisposableObserver<Bitmap>() {
              @Override
              public void onNext(Bitmap bitmap) {
                iv.setImageBitmap(bitmap);
              }

              @Override
              public void onError(Throwable e) {}

              @Override
              public void onComplete() {}
            });
        disposable.add(d);
      }
    });
  }

  private void createOrShowOptionsDialog() {
    if (optionsDialog == null) {
      createOptionsDialog();
    }
    optionsDialog.show();
  }

  private void createOptionsDialog() {
    optionsDialog = new CenterRVDialog(this);
    optionsDialog.setCancelable(true); // back按键是否能够取消
    optionsDialog.setCanceledOnTouchOutside(true);
    initNavigatorList();
  }

  private void initNavigatorList() {
    RecyclerView navigatorList = ((CenterRVDialog) optionsDialog).getNavigatorList();
    navigatorList
        .setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    navigatorList.setItemAnimator(new DefaultItemAnimator());
    navigatorList.addItemDecoration(createItemDecoration());
    NavigatorAdapter adapter = new NavigatorAdapter(this, navigators);
    navigatorList.setAdapter(adapter);
  }

  @NonNull
  private RecyclerView.ItemDecoration createItemDecoration() {
    return new RecyclerView.ItemDecoration() {

      @Override
      public void getItemOffsets(Rect outRect, int itemPosition, RecyclerView parent) {
        if (itemPosition != parent.getChildCount()) {
          outRect.bottom = (int) DisplayUtils.dipToPx(0.5f);
        }
      }

      @Override
      public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        Drawable drawable = getResources().getDrawable(R.drawable.divider_shape);

        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();
        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount - 1; i++) {
          final View childView = parent.getChildAt(i);
          final RecyclerView.LayoutParams lp =
              (RecyclerView.LayoutParams) childView.getLayoutParams();
          final int top = childView.getBottom() + lp.bottomMargin
              + Math.round(ViewCompat.getTranslationY(childView));
          final int bottom = top + (int) DisplayUtils.dipToPx(0.5f);
          drawable.setBounds(left, top, right, bottom);
          drawable.draw(c);
        }
      }

      @Override
      public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {}
    };
  }

  private void bindSsoAuthService() {
    if (AppUtils.PackageIsExist("com.chenbing.iceweatherserver")){
      Intent intent = new Intent();
      intent.setPackage(getPackageName());
      intent.setComponent(new ComponentName("com.chenbing.iceweatherserver",
        "com.chenbing.iceweatherserver.SsoAuthService"));
      bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }
  }

  private ServiceConnection mConnection = new ServiceConnection() {
    @Override
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
      ssoAuth = SsoAuth.Stub.asInterface(iBinder); // 把iBinder转化为实例
      doSsoAuth();
    }

    @Override
    public void onServiceDisconnected(ComponentName componentName) {
      ssoAuth = null;
    }
  };

  private void doSsoAuth() {
    try {
      // 执行登陆操作
      LogUtils.e(ssoAuth + "--------");
      ssoAuth.ssoAuth("xxy", "18202902002");
      LogUtils.e(ssoAuth + "");
    } catch (RemoteException e) {
      e.printStackTrace();
    }
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    LogUtils.e("onDestroy");
    if (ssoAuth != null){
      unbindService(mConnection);
    }
    if (disposable != null) {
      disposable.dispose();
    }
  }

  @Override
  public void onLocationChanged(Location location) {
    LogUtils.e("onLocationChanged");
    double longitude = location.getLongitude();
    double latitude = location.getLatitude();
    LogUtils.e(String.format("longitude = %s, latitude = %s", longitude, latitude));
    recreate();
  }

  @Override
  public void onStatusChanged(String provider, int status, Bundle extras) {

    LogUtils.e("onStatusChanged");

  }

  @Override
  public void onProviderEnabled(String provider) {
    LogUtils.e("onProviderEnabled, provider = " + provider);

  }

  @Override
  public void onProviderDisabled(String provider) {
    LogUtils.e("onProviderDisabled, provider = " + provider);
  }
}
