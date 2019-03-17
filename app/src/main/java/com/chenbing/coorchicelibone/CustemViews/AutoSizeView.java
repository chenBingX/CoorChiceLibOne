package com.chenbing.coorchicelibone.CustemViews;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

import org.apache.log4j.lf5.util.Resource;

/**
 * @author coorchice
 * @date 2018/08/22
 */
public class AutoSizeView extends View{

    private int relativeWidth = 360;
    private int uiWidth = 720;

    public AutoSizeView(Context context) {
        super(context);
        init();
    }

    public AutoSizeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
//        Resource resource = new Resource();
//        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
//        displayMetrics.density = (float) displayMetrics.widthPixels / 360f;
//        displayMetrics.densityDpi = (int)(displayMetrics.density * 160f);
    }

}
