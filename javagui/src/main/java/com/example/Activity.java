package com.example;

import com.example.Views.View;

import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;

import javafx.collections.ObservableList;
import javafx.scene.Node;

/**
 * Project Name:IceWeather
 * Author:IceChen
 * Date:2016/11/16
 * Notes:
 */

public abstract class Activity {
  private View contentView;
  private View pane;
  private ObservableList<Node> rootContainer;


  protected void setRootLayout(View pane){
    this.pane = pane;
    onCreate();
    if (contentView == null){
      throw new NullPointerException(
          "You must be call setContentView(View contentView) in onCreate(), and the param can't be null!");
    }
  }

  /**
   * 这是必须重写的方法，并且需要在这个方法中调用{@link Activity#setContentView}来设置一个可显示View
   */
  @CallSuper
  public abstract void onCreate();

  public void setContentView(@NonNull View contentView) {
    this.contentView = contentView;
    rootContainer = this.contentView.getChildren();
    pane.getChildren().add(contentView);
  }

  public double getWindowWidth(){
    return pane.getWidth();
  }

  public double getWindowHeight(){
    return pane.getHeight();
  }

  public void add(Node view) {
    rootContainer.add(view);
  }

}
