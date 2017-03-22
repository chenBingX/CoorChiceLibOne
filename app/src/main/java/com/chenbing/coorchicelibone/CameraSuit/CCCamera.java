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

  void setOnResultListener(OnResultListener onResultListener);

  public static interface OnPreTakePictureListener {
    void onPreTakePicture();
  }

  public static interface OnResultListener {
    void onResult(byte[] data);
  }
}
