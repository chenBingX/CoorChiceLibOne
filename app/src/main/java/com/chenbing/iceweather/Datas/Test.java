package com.chenbing.iceweather.Datas;

import com.chenbing.iceweather.Utils.LogUtils;

/**
 * Project Name:IceWeather
 * Author:IceChen
 * Date:2016/10/20
 * Notes:
 */

public class Test extends Object{

  @Override
  protected void finalize() throws Throwable {
    super.finalize();
    LogUtils.e("finalize执行————————");
  }
}
