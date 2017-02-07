package com.chenbing.coorchicelibone;

import android.content.Context;
import android.support.multidex.MultiDexApplication;

import com.chenbing.coorchicelibone.Utils.CrashHandler;

/**
 * Project Name:IceWeather
 * Author:IceChen
 * Date:16/8/27
 * Notes:
 */
public class IceApplication extends MultiDexApplication {
  private static Context context;
  @Override
  public void onCreate() {
    super.onCreate();
    context = this;
    //初始化异常处理类
    CrashHandler.getInstance().init(context);
  }

  public static Context getAppContext(){
    return context;
  }

  public static int getResColor(int resId){
    return getAppContext().getResources().getColor(resId);
  }

  public static String getResString(int resId){
    return getAppContext().getResources().getString(resId);
  }
}
