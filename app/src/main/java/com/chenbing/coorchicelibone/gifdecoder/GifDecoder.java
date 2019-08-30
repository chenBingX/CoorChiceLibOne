package com.chenbing.coorchicelibone.gifdecoder;

import android.graphics.Bitmap;
import android.text.TextUtils;

/**
 * Project Name:CoorChiceLibOne
 * Author:CoorChice
 * Date:2019/8/30
 * Notes:
 */
public class GifDecoder {

    private long ptr;
    private Bitmap frameCanvas;

    public static GifDecoder openFile(String filePtah) {
        return new GifDecoder(filePtah);
    }


    private GifDecoder(String filePtah) {
        if (!TextUtils.isEmpty(filePtah)) {
            ptr = JNI.openFile(filePtah);
        } else {
            throw new IllegalArgumentException("FilePath can not be null or empty!");
        }
        init();
    }

    private void init() {
        if (ptr == 0){
            throw new NullPointerException("Init Failure！");
        }
        frameCanvas = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
    }

    public int getWidth() {
        return JNI.getWidth(ptr);
    }

    public int getHeight() {
        return JNI.getHeight(ptr);
    }

    public int updateFrame() {
        return JNI.updateFrame(ptr, frameCanvas);
    }

    public long getPtr() {
        return ptr;
    }

    public Bitmap getBitmap(){
        return frameCanvas;
    }
}