package com.chenbing.coorchicelibone.CustemViews.artist;

import android.content.res.AssetManager;
import android.graphics.Typeface;

/**
 * Created by coorchice on 2017/10/24.
 */

public class FontIconText extends ArtText {


    public FontIconText(String text) {
        super(text);
    }

    public FontIconText(int stringId) {
        super(stringId);
    }

    @Override
    public void onAttach() {
        super.onAttach();
        AssetManager assets = getParent().getContext().getAssets();
        Typeface typeface = Typeface.createFromAsset(assets, "trip.ttf");
        setTextStyle(typeface);
        setTextPaddingEnable(false);
    }
}
