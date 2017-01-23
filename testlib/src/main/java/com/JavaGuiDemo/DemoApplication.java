package com.JavaGuiDemo;

import com.example.Application;
import com.example.ConfigurationImpl;

/**
 * Project Name:IceWeather
 * Author:CoorChice
 * Date:2017/1/23
 * Notes:
 */

public class DemoApplication extends Application {

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public ConfigurationImpl getConfigurationImpl() {
    return new Configuration();
  }
}
