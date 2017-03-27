package com.chenbing.coorchicelibone.CameraSuit;

import java.io.IOException;

/**
 * Project Name:CoorChiceLibOne
 * Author:CoorChice
 * Date:2017/3/17
 * Notes:
 */

public interface CCCamera {

  void open();

  void switchCamera() throws IOException;

  void updateParameters();

  void takePicture();

  void startRecording(String savePath, String name) throws IOException;

  void stopRecording();

  void startPreview() throws IOException;

  void stopPreview();

  void release();

  void setDisplayOrientation(int degrees);

  void setPreviewSize(int width, int height);

  void setContentRotation(int degrees);

  void setFocusMode(String focusMode);

  void setOnPreTakePictureListener(OnPreTakePictureListener onPreTakePictureListener);

  void setOnGetPictureDataListener(OnGetPictureDataListener onGetPictureDataListener);

  void setOnFocusListener(OnFocusListener onFocusListener);

  void focusTouchArea(float x, float y, float focusAreaSize);

  public static interface OnPreTakePictureListener {
    void onPreTakePicture();
  }

  public static interface OnGetPictureDataListener {
    void onResult(byte[] data);
  }

  public static interface OnFocusListener{
    void onFocus();
  }
}
