package com.JavaGuiDemo;

import com.JavaGuiDemo.Activities.DrawActivity;
import com.example.Activity;
import com.example.ConfigurationImpl;

/**
 * Project Name:IceWeather
 * Author:CoorChice
 * Date:2017/1/23
 * Notes:
 */

public class Configuration extends ConfigurationImpl {

  public Configuration(){

  }

  @Override
  public Activity configureActivity() {
    return new DrawActivity();
  }
}
