package com.chenbing.coorchicelibone.Utils;

import android.app.Activity;

/**
 * Project Name:IceWeather
 * Author:CoorChice
 * Date:2017/1/23
 * Notes:
 */

public class DataCache {

  private Activity activity;

  private DataCache() {

  }

  public static DataCache getInstance() {
    return DataCacheHolder.instance;
  }

  private static class DataCacheHolder {
    private static final DataCache instance = new DataCache();
  }

  public void setActivity(Activity activity){
    this.activity = activity;
  }

  public Activity getActivity(){
    return activity;
  }
}
