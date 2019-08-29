package com.chenbing.coorchicelibone.gifdecoder;

/**
 * Project Name:CoorChiceLibOne
 * Author:CoorChice
 * Date:2019/8/29
 * Notes:
 */
public class JNI {

  static {
    System.loadLibrary("GifLib");
  }

  public static native long openFile(String path);
}
