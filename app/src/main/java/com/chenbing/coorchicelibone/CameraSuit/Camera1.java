package com.chenbing.coorchicelibone.CameraSuit;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.chenbing.coorchicelibone.Utils.DisplayUtils;
import com.chenbing.coorchicelibone.Utils.LogUtils;

import android.graphics.Rect;
import android.hardware.Camera;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.view.SurfaceHolder;

/**
 * Project Name:CoorChiceLibOne
 * Author:CoorChice
 * Date:2017/3/17
 * Notes:
 */

public class Camera1 implements CCCamera {
  private static final int BACKGROUND_CAMERA = 0;
  private static final int FRONT_CAMERA = 1;

  private Camera instance;
  private int curCameraId = BACKGROUND_CAMERA;
  private SurfaceHolder holder;
  private MediaRecorder mediaRecorder;
  private CCCamera.OnPreTakePictureListener onPreTakePictureListener;
  private OnGetPictureDataListener onGetPictureDataListener;
  private OnFocusListener onFocusListener;
  private int previewWidth = DisplayUtils.dipToPx(720);
  private int previewHeight = DisplayUtils.dipToPx(1280);
  private int contentRotation = 90;
  private int displayOrientation = 90;
  private String focusMode = Camera.Parameters.FOCUS_MODE_AUTO;

  public Camera1(SurfaceHolder holder) {
    if (holder == null) {
      throw new IllegalArgumentException("SurfaceHolder can't be null!");
    }
    this.holder = holder;
    instance = Camera.open();
  }

  @Override
  public void open() {
    instance = Camera.open();
  }

  @Override
  public void switchCamera() throws IOException, IllegalStateException {
    doSwitch();
  }

  private void doSwitch() throws IOException, IllegalStateException {
    int numberOfCameras = Camera.getNumberOfCameras();
    Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
    for (int i = 0; i < numberOfCameras; i++) {
      Camera.getCameraInfo(i, cameraInfo);
      if (curCameraId == BACKGROUND_CAMERA) {
        if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
          preDisplay(i);
          curCameraId = i;
          break;
        }
      } else if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
        preDisplay(i);
        curCameraId = i;
        break;
      }
    }
  }

  private void preDisplay(int i) throws IOException, IllegalStateException {
    if (holder != null) {
      stopPreview();
      release();
      instance = Camera.open(i);
      startPreview();
    } else {
      throw new IllegalStateException("SurfaceHolder could not be null!");
    }
  }

  @Override
  public void startPreview() throws IOException {
    instance.setPreviewDisplay(holder);
    instance.startPreview();
    instance.setDisplayOrientation(displayOrientation);
  }

  @Override
  public void stopPreview() {
    instance.stopPreview();
  }

  @Override
  public void takePicture() {
    configParameters();
    instance.autoFocus((success, camera) -> {
      if (success) {
        take();
      }
    });
  }

  private void take() {
    instance.takePicture(
        () -> {
          if (onPreTakePictureListener != null) {
            onPreTakePictureListener.onPreTakePicture();
          }
        },
        null,
        (data, cam) -> {
          LogUtils.e("data = " + data.length);
          if (onGetPictureDataListener != null) {
            onGetPictureDataListener.onResult(data);
          }
        });
  }


  private void configParameters() {
    Camera.Parameters parameters = instance.getParameters();
    parameters.setRotation(contentRotation);
    parameters.setFocusMode(focusMode);
    configPreviewSize(parameters);
    instance.setParameters(parameters);
  }

  private void configPreviewSize(Camera.Parameters parameters) {
    List<Camera.Size> supportedPreviewSizes = parameters.getSupportedPreviewSizes();
    int bestWidth = 0;
    int bestHeight = 0;
    for (Camera.Size size : supportedPreviewSizes) {
      String format =
          String.format("CameraDemo Size.with = %d, Size.height = %d", size.width, size.height);
      LogUtils.e(format);
      // 寻找选择和预览界面相近的大小
      // 小米从大到小，美图从小到大
      if (previewWidth >= size.width && previewHeight >= size.height) {
        if (bestWidth < size.width && bestHeight < size.height) {
          bestWidth = size.width;
          bestHeight = size.height;
        }
      }
    }
    // parameters.setPictureFormat(ImageFormat.JPEG);
    parameters.setPictureSize(bestWidth, bestHeight);
    parameters.setPreviewSize(bestWidth, bestHeight);
  }

  @Override
  public void startRecording(String savePath, String name) throws IOException {
    instance.unlock();
    initMediaRecorder();
    mediaRecorder.setOutputFile(new File(savePath, name).getPath());
    mediaRecorder.prepare();
    mediaRecorder.start();
  }

  private void initMediaRecorder() {
    mediaRecorder = new MediaRecorder();
    mediaRecorder.setCamera(instance);
    mediaRecorder.setOrientationHint(90);
    mediaRecorder.setAudioSource(MediaRecorder.AudioSource.CAMCORDER);
    // 设置声音来源为摄像机
    mediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
    // mediaRecorder.setVideoEncodingBitRate(1 * 1024 * 1024);
    // 设置视屏来源为摄像机
    CamcorderProfile profile = CamcorderProfile.get(curCameraId, CamcorderProfile.QUALITY_HIGH);
    // 获得相机配置文件
    mediaRecorder.setProfile(profile);
    mediaRecorder.setPreviewDisplay(holder.getSurface());
    // 设置预览
  }

  @Override
  public void stopRecording() {
    if (mediaRecorder == null) {
      throw new NullPointerException("You must call startRecording() first!");
    }
    mediaRecorder.stop();
    mediaRecorder.release();
    mediaRecorder = null;
    instance.lock();
  }

  @Override
  public void release() {
    instance.release();
    instance = null;
    if (mediaRecorder != null) {
      mediaRecorder.stop();
      mediaRecorder.release();
      mediaRecorder = null;
    }
  }

  @Override
  public void setDisplayOrientation(int degrees) {
    displayOrientation = degrees;
  }

  @Override
  public void setPreviewSize(int width, int height) {
    previewWidth = width;
    previewHeight = height;
  }

  @Override
  public void setContentRotation(int degrees) {
    contentRotation = degrees;
  }

  @Override
  public void setFocusMode(String focusMode) {
    this.focusMode = focusMode;
  }

  @Override
  public void updateParameters(){
    configParameters();
  }

  @Override
  public void focusTouchArea(float x, float y, float focusAreaSize) {
    Camera.Parameters parameters = instance.getParameters();
    Rect rect = calculateTapArea(x, y, 1, parameters.getPreviewSize(), focusAreaSize);
    if (parameters.getMaxNumFocusAreas() > 0) {
      List<Camera.Area> focusAreaList = new ArrayList<>();
      Camera.Area area = new Camera.Area(rect, 800);
      focusAreaList.add(area);
      parameters.setFocusAreas(focusAreaList);
      parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_MACRO);
    }

    if (parameters.getMaxNumMeteringAreas() > 0) {
      List<Camera.Area> meteringAreaList = new ArrayList<>();
      Camera.Area area = new Camera.Area(rect, 1000);
      meteringAreaList.add(area);
      parameters.setMeteringAreas(meteringAreaList);
    }

    instance.setParameters(parameters);
    instance.autoFocus((s, c) -> {
      LogUtils.e("对焦完成，rect.left = " + rect.left);
      Camera.Parameters params = instance.getParameters();
      params.setFocusMode(focusMode);
      instance.setParameters(params);
      if (onFocusListener != null) {
        onFocusListener.onFocus();
      }
    });
  }

  private static Rect calculateTapArea(float x, float y, float coefficient,
      Camera.Size previewSize, float focusAreaSize) {
    LogUtils.e("focusAreaSize = " + focusAreaSize);
    int areaSize = Float.valueOf(focusAreaSize * coefficient).intValue();

    int centerX = (int) (x / previewSize.width * 2000 - 1000);
    int centerY = (int) (y / previewSize.height * 2000 - 1000);
    LogUtils.e(String.format("x = %d, y = %d, width = %d, height = %d", (int) x, (int) y,
        previewSize.width,
        previewSize.height));

    int left = clamp(centerX - areaSize / 2, -1000 + areaSize, 1000 - areaSize);
    int right = clamp(left + areaSize, -1000, 1000);
    int top = clamp(centerY - areaSize / 2, -1000 + areaSize, 1000 - areaSize);
    int bottom = clamp(top + areaSize, -1000, 1000);

    return new Rect(left, top, right, bottom);
  }

  private static int clamp(int x, int min, int max) {
    if (x > max) {
      return max;
    }
    if (x < min) {
      return min;
    }
    return x;
  }

  public void setOnPreTakePictureListener(OnPreTakePictureListener onPreTakePictureListener) {
    this.onPreTakePictureListener = onPreTakePictureListener;
  }

  public void setOnGetPictureDataListener(OnGetPictureDataListener onGetPictureDataListener) {
    this.onGetPictureDataListener = onGetPictureDataListener;
  }

  @Override
  public void setOnFocusListener(OnFocusListener onFocusListener) {
    this.onFocusListener = onFocusListener;
  }
}
