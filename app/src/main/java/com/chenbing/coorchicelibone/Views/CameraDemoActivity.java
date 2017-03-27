package com.chenbing.coorchicelibone.Views;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.chenbing.coorchicelibone.CameraSuit.CCCamera;
import com.chenbing.coorchicelibone.CameraSuit.Camera1;
import com.chenbing.coorchicelibone.CameraSuit.CameraUtils;
import com.chenbing.coorchicelibone.Utils.DisplayUtils;
import com.chenbing.coorchicelibone.Utils.FileUtils;
import com.chenbing.coorchicelibone.Utils.LogUtils;
import com.chenbing.coorchicelibone.Utils.ToastUtil;
import com.chenbing.coorchicelibone.Views.BaseView.BaseActivity;
import com.chenbing.iceweather.R;

import android.Manifest;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CameraDemoActivity extends BaseActivity implements SurfaceHolder.Callback {
  private static final int PERMISSION_REQUEST_CODE = 100;

  @BindView(R.id.btn_change_lens)
  Button btnChangeLens;
  @BindView(R.id.surface_view)
  SurfaceView surfaceView;
  @BindView(R.id.btn_Pic)
  Button btnPic;
  @BindView(R.id.focus_square)
  TextView focusSquare;

  @BindView(R.id.btn_video)
  Button btnVideo;

  private SurfaceHolder surfaceHolder;
  private boolean isRecording = false;
  private CCCamera camera;
  private List<String> permissions = new ArrayList<>();


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
    setContentView(R.layout.activity_camera_demo);
    ButterKnife.bind(this);
    if (Build.VERSION.SDK_INT >= 23) {
      checkPermissionAndRequest();
    } else {
      init();
    }
  }

  @RequiresApi(api = Build.VERSION_CODES.M)
  private void checkPermissionAndRequest() {
    if (isNeedRequestPermission()) {
      // 发起请求权限
      requestPermissions(permissions.toArray(new String[permissions.size()]),
          PERMISSION_REQUEST_CODE);
    } else {
      init();
    }
  }

  @RequiresApi(api = Build.VERSION_CODES.M)
  private boolean isNeedRequestPermission() {
    boolean camera;
    boolean recordAudio;
    if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
      permissions.add(Manifest.permission.CAMERA);
      camera = false;
    } else {
      camera = true;
    }
    if (checkSelfPermission(
        Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
      permissions.add(Manifest.permission.RECORD_AUDIO);
      recordAudio = false;
    } else {
      recordAudio = true;
    }
    return !(camera && recordAudio);
  }

  private void init() {
    initData();
    initView();
    addListener();
  }

  @Override
  protected void initData() {
    if (CameraUtils.cameraEnable() && camera == null) {
      initSurfaceView();
      initCamera();
    }
  }

  private void initCamera() {
    camera = new Camera1(surfaceHolder);
    configCamera();
  }

  private void initSurfaceView() {
    surfaceHolder = surfaceView.getHolder();
    surfaceHolder.setKeepScreenOn(true);
    // 设置保持屏幕常量
    surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    // 为了实现照片预览功能，需要将SurfaceHolder的类型设置为PUSH
    // 这样，画图缓存就由Camera类来管理，画图缓存是独立于Surface的
    surfaceHolder.addCallback(this);
    // 监听状态回调
  }

  private void configCamera() {
    camera.setDisplayOrientation(90);
    // 设置预览画面方向
    camera.setPreviewSize(DisplayUtils.getScreenWidth(), DisplayUtils.getScreenWidth());
    // 设置预览画面大小
    camera.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
    // 设置对焦模式
    camera.setContentRotation(90);
    // 设置真实画面方向
    camera.updateParameters();
  }

  @Override
  protected void initView() {

  }

  @Override
  protected void addListener() {
    listenScreenRotate();

    camera.setOnPreTakePictureListener(() -> {
      LogUtils.e("CameraDemo--setOnPreTakePictureListener");
    });

    camera.setOnGetPictureDataListener(this::onGetPictureData);

    camera.setOnFocusListener(() -> {
      focusSquare.setVisibility(View.GONE);
    });

    btnPic.setOnClickListener(v -> {
      camera.takePicture();
    });

    btnVideo.setOnClickListener(v -> {
      onBtnVideoClick();
    });

    btnChangeLens.setOnClickListener(v -> {
      try {
        camera.switchCamera();
      } catch (IOException e) {
        e.printStackTrace();
      }
    });

  }

  private void listenScreenRotate() {
    // 精确的监听屏幕旋转
    // 竖直为0，沿顺时真增大
    // new OrientationEventListener(this){
    // @Override
    // public void onOrientationChanged(int orientation) {
    // LogUtils.e("CameraDemo onOrientationChanged = " + orientation);
    // if( orientation > 330 || orientation< 10 ) { //0度
    // orientation = 0;
    // camera.setDisplayOrientation(90);
    // }
    // else if( orientation > 50 &&orientation < 100 ) { //90度
    // orientation= 90;
    // camera.setDisplayOrientation(180);
    // }
    // else if( orientation > 130 &&orientation < 180 ) { //180度
    // orientation= 180;
    // camera.setDisplayOrientation(180);
    // }
    // else if( orientation > 190 &&orientation < 220 ) { //180度
    // orientation= 180;
    // camera.setDisplayOrientation(0);
    // }
    // else if( orientation > 240 &&orientation < 280 ) { //270度
    // orientation= 270;
    // camera.setDisplayOrientation(0);
    // }
    // }
    // }.enable();
  }

  private void onGetPictureData(byte[] data) {
    String s = FileUtils.GetAppPhotoDir();
    LogUtils.e("s：" + s);
    try {
      File file = new File(s, System.currentTimeMillis() + ".jpg");
      FileOutputStream fos = new FileOutputStream(file);
      fos.write(data);
      fos.flush();
      fos.close();
      ToastUtil.showLongToast("保存到：" + file.getAbsolutePath());
      LogUtils.e("保存到：" + file.getAbsolutePath());
    } catch (IOException e) {
      e.printStackTrace();
    }

    try {
      camera.stopPreview();
      camera.startPreview();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void onBtnVideoClick() {
    if (!isRecording) {
      btnVideo.setText("Video Stop");
      camera.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO);
      camera.updateParameters();
      isRecording = true;
      try {
        camera.startRecording(FileUtils.GetAppVideoDir(), System.currentTimeMillis() + ".mp4");
      } catch (IOException e) {
        e.printStackTrace();
      }
    } else {
      btnVideo.setText("Video Start");
      isRecording = false;
      camera.stopRecording();
    }
  }


  @Override
  public void surfaceCreated(SurfaceHolder holder) {
    // 必须在surfaceCreated后才能开始预览界面
    // 否则就黑屏了
    try {
      camera.startPreview();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

  }

  @Override
  public void surfaceDestroyed(SurfaceHolder holder) {
    // SurfaceView销毁时停止预览
    camera.stopPreview();
  }

  @Override
  public void onConfigurationChanged(Configuration newConfig) {
    super.onConfigurationChanged(newConfig);
    int orientation = getWindowManager().getDefaultDisplay().getOrientation();
    LogUtils.e("CameraDemo orientation = " + orientation);
    // 检测屏幕旋转角度
    // 从竖直方向开始，沿逆时针以此为0，90，180，270。
    // 注意，180时不会回调。
    // if (orientation == Surface.ROTATION_270){
    // camera.setDisplayOrientation(180);
    // } else if(orientation == Surface.ROTATION_90){
    // camera.setDisplayOrientation(0);
    // } else {
    // camera.setDisplayOrientation(90);
    // }
  }

  @Override
  protected void onPause() {
    super.onPause();
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    if (camera != null){
      camera.stopPreview();
      camera.release();
      camera = null;
    }
  }

  @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
      @NonNull int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    switch (requestCode) {
      // requestCode即所声明的权限获取码，在checkSelfPermission时传入
      case PERMISSION_REQUEST_CODE:
        for (int result : grantResults) {
          if (result != PackageManager.PERMISSION_GRANTED) {
            // 请一定要对权限被拒绝的情况进行处理
            ToastUtil.showShortToast("需要的权限被拒绝！");
            finish();
            return;
          }
        }
        init();
        break;
      default:
        break;
    }
  }

  @Override
  public boolean onTouchEvent(MotionEvent event) {
    if (camera != null) {
      int width = DisplayUtils.dipToPx(50);
      int height = width;
      camera.focusTouchArea(event.getX(), event.getY(), width);
      ViewGroup.MarginLayoutParams lp =
          (ViewGroup.MarginLayoutParams) focusSquare.getLayoutParams();
      lp.leftMargin = (int) (event.getX() - width / 2);
      lp.topMargin = (int) (event.getY() - height / 2);
      focusSquare.setLayoutParams(lp);
      focusSquare.setVisibility(View.VISIBLE);
    }
    return super.onTouchEvent(event);
  }
}
