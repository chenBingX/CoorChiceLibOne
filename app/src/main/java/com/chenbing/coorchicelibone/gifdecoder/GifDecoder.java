package com.chenbing.coorchicelibone.gifdecoder;

import android.text.TextUtils;

/**
 * Project Name:CoorChiceLibOne
 * Author:CoorChice
 * Date:2019/8/30
 * Notes:
 */
public class GifDecoder {

  private long ptr;

  private GifDecoder(String filePtah) {
    if (!TextUtils.isEmpty(filePtah)) {
      ptr = JNI.openFile(filePtah);
    } else {
      throw new IllegalArgumentException("filePath can not be null or empty!");
    }
  }

  public static final GifDecoder openFile(String filePtah) {
      return new GifDecoder(filePtah);
  }

  public long getPtr() {
    return ptr;
  }
}
