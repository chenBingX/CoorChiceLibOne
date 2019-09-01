package com.chenbing.coorchicelibone.gifdecoder;

import android.graphics.Bitmap;

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

    /**
     * 通过文件路径打开 gif 图
     *
     * @param path
     * @return
     */
    public static native long openFile(String path);

    /**
     * 通过byte数组打开 gif 图
     * @param bytes
     * @return
     */
    public static native long openBytes(byte[] bytes);

    /**
     * 更新一帧
     *
     * @param ptr
     * @param bitmap
     * @return
     */
    public static native int updateFrame(long ptr, Bitmap bitmap);

    /**
     * 取得宽度
     *
     * @param ptr
     * @return
     */
    public static native int getWidth(long ptr);

    /**
     * 取得高度
     *
     * @param ptr
     * @return
     */
    public static native int getHeight(long ptr);

    /**
     * 销毁
     * @param ptr
     * @return
     */
    public static native int destroy(long ptr);

}
