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
            throw new IllegalArgumentException("File path can not be null or empty!");
        }
        init();
    }

    private void init() {
        if (ptr == 0) {
            throw new NullPointerException("Init Failure！");
        }
        frameCanvas = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
    }

    public int getWidth() {
        check();
        return JNI.getWidth(ptr);
    }

    public int getHeight() {
        check();
        return JNI.getHeight(ptr);
    }

    public int updateFrame() {
        check();
        int r = 1;
        if (frameCanvas != null) {
            r = JNI.updateFrame(ptr, frameCanvas);
        }
        return r;
    }

    public long getPtr() {
        return ptr;
    }

    public Bitmap getBitmap() {
        return frameCanvas;
    }

    private void check() {
        if (ptr == 0) {
            try {
                throw new IllegalStateException("GifDecoder has not been created or destroyed!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public boolean isDestroy() {
        return ptr == 0;
    }

    public void destroy() {
        check();
        JNI.destroy(ptr);
        ptr = 0;
        frameCanvas.recycle();
        frameCanvas = null;
    }

}
