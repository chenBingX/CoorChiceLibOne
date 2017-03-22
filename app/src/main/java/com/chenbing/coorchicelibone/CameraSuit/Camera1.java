package com.chenbing.coorchicelibone.CameraSuit;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.chenbing.coorchicelibone.Utils.DisplayUtils;
import com.chenbing.coorchicelibone.Utils.LogUtils;

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
  private CCCamera.OnResultListener onResultListener;

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
      instance = null;
      instance = Camera.open(i);
      startPreview();
    } else {
      throw new IllegalStateException("SurfaceHolder could not be null!");
    }
  }

  @Override
  public void takePicture() {
    instance.autoFocus((success, camera) -> {
      if (success) {
        configParameters();
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
          if (onResultListener != null) {
            onResultListener.onResult(data);
          }
        });
  }

  private void configParameters() {
    Camera.Parameters parameters = instance.getParameters();
    configPreviewSize(parameters);
    parameters.setRotation(contentRotation);
    parameters.setFocusMode(focusMode);
    instance.setParameters(parameters);
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
    mediaRecorder.setAudioSource(MediaRecorder.AudioSource.CAMCORDER);
    // 设置声音来源为摄像机
    mediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
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
  public void release() {
    instance.release();
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

  private void configPreviewSize(Camera.Parameters parameters) {
    List<Camera.Size> supportedPreviewSizes = parameters.getSupportedPreviewSizes();
    int bestWidth = 0;
    int bestHeight = 0;
    for (Camera.Size size : supportedPreviewSizes) {
      String format =
          String.format("CameraDemo Size.with = %d, Size.height = %d", size.width, size.height);
      LogUtils.e(format);
      // 寻找选择和预览界面相近的大小
      if (previewWidth > size.width && previewHeight > size.height) {
        bestWidth = size.width;
        bestHeight = size.height;
      }
    }
    parameters.setPreviewSize(bestWidth, bestHeight);
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

  public void setOnPreTakePictureListener(OnPreTakePictureListener onPreTakePictureListener) {
    this.onPreTakePictureListener = onPreTakePictureListener;
  }

  public void setOnResultListener(OnResultListener onResultListener) {
    this.onResultListener = onResultListener;
  }
}
