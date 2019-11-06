package com.chenbing.coorchicelibone.gifdecoder;

import android.support.annotation.DrawableRes;

import com.chenbing.coorchicelibone.IceApplication;
import com.coorchice.library.utils.STVUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author coorchice
 * @date 2019/11/05
 */
public class GifCache {

    private Map<String, GifDrawable> map = new HashMap<>();

    private GifCache() {

    }

    private static final class Holder {
        private static final GifCache INSTANCE = new GifCache();
    }

    public static GifCache getInstance() {
        return Holder.INSTANCE;
    }

    public GifDrawable get(@DrawableRes int id) {
        if (!map.containsKey(String.valueOf(id))) {
            byte[] bytes = STVUtils.getResBytes(IceApplication.getAppContext(), id);
            if (bytes != null && GifDecoder.isGif(bytes)) {
                GifDrawable gifDrawable = GifDrawable.createDrawable(bytes);
                map.put(String.valueOf(id), gifDrawable);
                return GifDrawable.copy(gifDrawable.getPtr());
//                return gifDrawable;
            }
        } else {
            return GifDrawable.copy(map.get(String.valueOf(id)).getPtr());
//            return map.get(String.valueOf(id));
        }
        return null;
    }


}
