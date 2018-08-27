package com.chenbing.coorchicelibone.Utils;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;

/**
 * 根据设计自动适配Android屏幕碎片化，避免屏幕差异所带来的视觉差，
 * 可以页面级别的启用或者停用。
 *
 * @author coorchice
 * @date 2018/08/22
 */
public class DensityBoss {

    private float sysDensity;
    private int sysDensityDpi;
    private float sysScaleDensity;

    private float newDensity;
    private float newDensityDpi;
    private float newScaleDensity;

    private DensityBoss() {
        DisplayMetrics displayMetrics = Resources.getSystem().getDisplayMetrics();
        sysDensity = displayMetrics.density;
        sysDensityDpi = displayMetrics.densityDpi;
        sysScaleDensity = displayMetrics.scaledDensity;

        newDensity = sysDensity;
        newScaleDensity = sysScaleDensity;
        newDensityDpi = sysDensityDpi;
    }

    private static class Holder {
        private static final DensityBoss instance = new DensityBoss();
    }

    /**
     * 获取 DensityBoss，用于管全局像素
     *
     * @return
     */
    public static DensityBoss get() {
        return Holder.instance;
    }

    /**
     * 配置像素设置，会根据比例自动计算，确保所有屏幕展示效果的一致性
     *
     * @param context            环境，用于获取当前环境的资源
     * @param relativeScreenSize 根据设计图的设备屏幕尺寸，你规定在Android设备上对应的dp尺寸。
     *                           比如，设计图屏幕宽度为 750px，那么你规定对应到Android上宽度为 375dp
     * @param width              用于计算比例的是宽吗？不是就是高喽
     * @return
     */
    public DensityBoss config(Context context, float relativeScreenSize, boolean width) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float realSize = width ? displayMetrics.widthPixels : displayMetrics.heightPixels;
        newDensity = (realSize / relativeScreenSize);
        newScaleDensity = (newDensity);
        newDensityDpi = ((int) (displayMetrics.density * 160f + 0.5f));
        return this;
    }

    /**
     * 可以在 Activity / Application 的 onCreate() 中调用，
     * 打开自动尺寸计算，一般情况下会影响其它页面。
     *
     * @param context
     * @return
     */
    public DensityBoss open(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        displayMetrics.density = newDensity;
        displayMetrics.scaledDensity = newScaleDensity;
        displayMetrics.densityDpi = (int) newDensityDpi;
        return this;
    }

    /**
     * 可以在 Activity / Application 的 onCreate() 中调用，
     * 关闭自动尺寸计算，一般情况下会影响其它页面。
     *
     * @param context
     * @return
     */
    public DensityBoss close(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        displayMetrics.density = sysDensity;
        displayMetrics.scaledDensity = sysScaleDensity;
        displayMetrics.densityDpi = sysDensityDpi;
        return this;
    }
}
