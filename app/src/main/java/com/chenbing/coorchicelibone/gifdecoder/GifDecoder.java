package com.chenbing.coorchicelibone.gifdecoder;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
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
    private boolean canPlay = true;
    private OnFrameListener onFrameListener;

    private Handler handler = new Handler(Looper.getMainLooper());
    private Runnable playRunnable = () -> {
        if (isDestroy() || !canPlay) return;
        int d = updateFrame();
        if (onFrameListener != null) {
            onFrameListener.onFrame(this, getBitmap());
        }
        innerPlay(d);
    };

    public static GifDecoder openFile(String filePtah) {
        return new GifDecoder(filePtah);
    }

    public static GifDecoder openBytes(byte[] bytes) {
        return new GifDecoder(bytes);
    }

    private GifDecoder(String filePtah) {
        if (!TextUtils.isEmpty(filePtah)) {
            ptr = JNI.openFile(filePtah);
        } else {
            throw new IllegalArgumentException("File path can not be null or empty!");
        }
        init();
    }

    private GifDecoder(byte[] bytes) {
        if (bytes != null) {
            ptr = JNI.openBytes(bytes);
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

    public int getFrameCount() {
        check();
        return JNI.getFrameCount(ptr);
    }

    public int getFrameDuration() {
        check();
        return JNI.getFrameDuration(ptr);
    }

    public void setFrameDuration(int duration) {
        check();
        JNI.setFrameDuration(ptr, duration);
    }

    public int getCurrentFrame() {
        check();
        return JNI.getCurrentFrame(ptr);
    }


    public void gotoFrame(int frame) {
        check();
        JNI.gotoFrame(ptr, frame);
    }

    public Bitmap getFrame(int frame) {
        check();
        JNI.getFrame(ptr, frame, frameCanvas);
        return frameCanvas;
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

    public void play() {
        if (isDestroy()) return;
        canPlay = true;
        innerPlay(0);
    }

    private void innerPlay(int delay) {
        handler.postDelayed(playRunnable, delay);
    }

    public boolean isPlaying() {
        return canPlay;
    }

    public void stop() {
        canPlay = false;
    }

    public boolean isDestroy() {
        return ptr == 0;
    }

    public void destroy() {
        canPlay = false;
        handler.removeCallbacksAndMessages(null);
        check();
        JNI.destroy(ptr);
        ptr = 0;
        frameCanvas.recycle();
        frameCanvas = null;
    }

    public void setOnFrameListener(OnFrameListener onFrameListener) {
        this.onFrameListener = onFrameListener;
    }

    public static interface OnFrameListener {
        void onFrame(GifDecoder gd, Bitmap bitmap);
    }
}
