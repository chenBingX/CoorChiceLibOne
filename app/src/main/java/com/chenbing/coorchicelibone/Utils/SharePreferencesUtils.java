package com.chenbing.coorchicelibone.Utils;

import java.io.IOException;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

/**
 * Project Name:AnimDveDemo
 * Author:IceChen
 * Date:16/8/25
 * Notes:
 */
public class SharePreferencesUtils {

  public static final void saveObject(@NonNull Context context, String key, @NonNull Object object)
      throws IOException {
    SharedPreferences sp = context.getSharedPreferences(key, Context.MODE_PRIVATE);
    SharedPreferences.Editor editor = sp.edit();
    String base64String = AppUtils.ObjectToBase64String(object);
    editor.putString(key, base64String);
    editor.apply();
  }

  public static final <T> T restoreObject(@NonNull Context context, String key, Class<T> clazz)
      throws IOException, ClassNotFoundException {
    SharedPreferences sp = context.getSharedPreferences(key, Context.MODE_PRIVATE);
    String base64String = sp.getString(key, "");
    T t = null;
    if (!base64String.equals("")){
      t = AppUtils.Base64StringToObject(base64String, clazz);
    }
    return t;
  }
}
