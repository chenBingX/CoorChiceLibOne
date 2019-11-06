package com.chenbing.coorchicelibone.gifdecoder;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.text.TextUtils;

import com.chenbing.coorchicelibone.IceApplication;
import com.coorchice.library.ImageEngine;
import com.coorchice.library.utils.STVUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author coorchice
 * @date 2019/11/05
 */
public class GifCache {

    private Map<String, GifDrawable> map = new ConcurrentHashMap<>();

    private GifCache() {

    }

    private static final class Holder {
        private static final GifCache INSTANCE = new GifCache();
    }

    public static GifDrawable fromResource(Context context, @DrawableRes int id) {
        if (!Holder.INSTANCE.map.containsKey(String.valueOf(id))) {
            byte[] bytes = STVUtils.getResBytes(context, id);
            if (bytes != null && GifDecoder.isGif(bytes)) {
                GifDrawable gifDrawable = GifDrawable.createDrawable(bytes);
                Holder.INSTANCE.map.put(String.valueOf(id), gifDrawable);
                return GifDrawable.copy(gifDrawable.getPtr());
            }
        } else {
            return GifDrawable.copy(Holder.INSTANCE.map.get(String.valueOf(id)).getPtr());
        }
        return null;
    }

    public static GifDrawable fromFile(String filePath) {
        if (!TextUtils.isEmpty(filePath) && !Holder.INSTANCE.map.containsKey(filePath)) {
            if (GifDecoder.isGif(filePath)) {
                GifDrawable gifDrawable = GifDrawable.createDrawable(filePath);
                Holder.INSTANCE.map.put(filePath, gifDrawable);
                return GifDrawable.copy(gifDrawable.getPtr());
            }
        } else {
            return GifDrawable.copy(Holder.INSTANCE.map.get(filePath).getPtr());
        }
        return null;
    }

    public static GifDrawable fromUrl(String url) {
        if (!TextUtils.isEmpty(url) && !Holder.INSTANCE.map.containsKey(url)) {
            if (GifDecoder.isGif(url)) {
//                ImageEngine.load();
                GifDrawable gifDrawable = GifDrawable.createDrawable(url);
                Holder.INSTANCE.map.put(url, gifDrawable);
                return GifDrawable.copy(gifDrawable.getPtr());
            }
        } else {
            return GifDrawable.copy(Holder.INSTANCE.map.get(url).getPtr());
        }
        return null;
    }




}
