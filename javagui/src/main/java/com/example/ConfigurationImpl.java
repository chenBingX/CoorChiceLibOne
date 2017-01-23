package com.example;


import android.support.annotation.CallSuper;

/**
 * Project Name:IceWeather
 * Author:IceChen
 * Date:2016/11/19
 * Notes:
 */

public abstract class ConfigurationImpl {
  private int sceneWidth = 1366;
  private int sceneHeight = 768;
  private String appTitle = "Demo";
  private Activity activity;

  public ConfigurationImpl() {

  }

  int getSceneWidth() {
    return sceneWidth;
  }

  int getSceneHeight() {
    return sceneHeight;
  }

  String getAppTitle() {
    return appTitle;
  }

  Activity getActivity() {
    activity = configureActivity();
    if (activity == null){
      throw new NullPointerException(
          "Activity can't be null. Please make sure you have configured Activity through override configureActivity.");
    }
    return activity;
  }


  /**
   * 设置应用窗口的width。
   * 尽管有默认值：1366px，但你可以在构造函数中调用该方法来自定义width。
   */
  public void setSceneWidth(int sceneWidth) {
    this.sceneWidth = sceneWidth;
  }

  /**
   * 设置应用窗口的height。
   * 尽管有默认值：768px，但你可以在构造函数中调用该方法来自定义height。
   */
  public void setSceneHeight(int sceneHeight) {
    this.sceneHeight = sceneHeight;
  }

  /**
   * 设置应用窗口的标题。
   * 尽管有默认值：Demo，但你可以在构造函数中调用该方法来自定义标题。
   */
  public void setAppTitle(String appTitle) {
    this.appTitle = appTitle;
  }

  /**
   * 这是一个必须重写的方法，并且需要返回一个不为null的Activity。
   */
  @CallSuper
  public abstract Activity configureActivity();

}
