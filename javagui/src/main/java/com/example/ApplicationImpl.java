package com.example;

import com.example.Views.View;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Project Name:IceWeather
 * Author:IceChen
 * Date:2016/11/16
 * Notes: 由于Java特性，必须在创建的Application中添加main()函数，否则会崩溃。格式固定如下:
 * public static void main(String[] args) {
 *    launch(args);
 * }
 */
abstract class ApplicationImpl extends Application {
  private Stage primaryStage;
  private ConfigurationImpl configurationImpl;
  private Activity activity;
  private Scene scene;
  private int sceneWidth;
  private int sceneHeight;
  private String appTitle;

  @Override
  public void start(Stage primaryStage) throws Exception {
    loadConfiguration();
    this.primaryStage = primaryStage;
    initSceneAndShowStage();
    activity.setRootLayout((View) scene.getRoot());
  }

  private void loadConfiguration() {
    configurationImpl = getConfigurationImpl();
    if (configurationImpl == null) {
      throw new NullPointerException(
          "You must be override getConfigurationImpl() in your Application, and the returned configuration can't be null!");
    }
    activity = configurationImpl.getActivity();
    sceneWidth = configurationImpl.getSceneWidth();
    sceneHeight = configurationImpl.getSceneHeight();
    appTitle = configurationImpl.getAppTitle();
  }

  private void initSceneAndShowStage() {
    scene = initScene(sceneWidth, sceneHeight);
    setupAndShowStage(scene, appTitle);
  }

  private Scene initScene(int sceneWidth, int sceneHeight) {
    View rootPane = new View();
    return new Scene(rootPane, sceneWidth, sceneHeight);
  }

  private void setupAndShowStage(Scene scene, String title) {
    primaryStage.setResizable(true); // 设置是否可以调整大小
    primaryStage.setTitle(title);
    primaryStage.setScene(scene);
    primaryStage.show();
  }

  public abstract ConfigurationImpl getConfigurationImpl();
}
