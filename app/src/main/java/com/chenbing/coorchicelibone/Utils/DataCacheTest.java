package com.chenbing.coorchicelibone.Utils;

import android.app.Activity;

/**
 * Project Name:IceWeather
 * Author:CoorChice
 * Date:2017/1/23
 * Notes:
 */

public class DataCacheTest {

  private Activity activity;

  private DataCacheTest() {

  }

  public static DataCacheTest getInstance() {
    return DataCacheHolder.instance;
  }

  private static class DataCacheHolder {
    private static final DataCacheTest instance = new DataCacheTest();
  }

  public void setActivity(Activity activity){
    this.activity = activity;
  }

  public Activity getActivity(){
    return activity;
  }
}
