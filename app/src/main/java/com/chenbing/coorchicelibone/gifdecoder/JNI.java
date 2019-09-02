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
     * 获取帧数
     * @param ptr
     * @return
     */
    public static native int getFrameCount(long ptr);


    /**
     * 获取当前帧间隔
     *
     * @param ptr
     * @return
     */
    public static native int getFrameDuration(long ptr);

    /**
     * 修改帧间隔
     *
     * @param ptr
     * @param duration
     */
    public static native void setFrameDuration(long ptr, int duration);


    /**
     * 获取当前帧位置
     *
     * @param ptr
     * @return
     */
    public static native int getCurrentFrame(long ptr);

    /**
     * 跳到指定帧
     *
     * @param ptr
     * @param frame
     */
    public static native void gotoFrame(long ptr, int frame);


    /**
     * 获取指定帧图像
     *
     * @param ptr
     * @param frame
     * @param bitmap
     */
    public static native void getFrame(long ptr, int frame, Bitmap bitmap);


    /**
     * 销毁
     * @param ptr
     * @return
     */
    public static native void destroy(long ptr);

}
