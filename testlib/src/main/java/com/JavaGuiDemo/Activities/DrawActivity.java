package com.JavaGuiDemo.Activities;

import java.awt.MouseInfo;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import com.JavaGuiDemo.Enty.DisperseCircle;
import com.example.Activity;
import com.example.Views.View;
import com.example.Views.ViewGroup;


import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * Project Name:IceWeather
 * Author:IceChen
 * Date:2016/11/19
 * Notes:
 */

public class DrawActivity extends Activity {
  private static final double DEFAULT_CIRCLE_RADIUS = 10;
  private static final long DEFAULT_WAIT_TIME = 1000;
  private final ViewGroup rootLayout;
  private Circle circleEffect;
  private double lastMoveX;
  private double lastMoveY;
  private double currentMoveX;
  private double currentMoveY;
  private long waitTime = DEFAULT_WAIT_TIME;

  private AtomicInteger count = new AtomicInteger(0);

  private List<DisperseCircle> circles = new ArrayList<>();
  private int triggerNum;
  private int durationTriggerNum;

  public DrawActivity() {
    rootLayout = new ViewGroup();
  }


  @Override
  public void onCreate() {
    setContentView(rootLayout);
    waitTime = 300;
    triggerNum = (int) (waitTime / 16);
    durationTriggerNum = 800 / 16 + triggerNum;
    initView();
    addListener();
    startThread();
  }

  private void initView() {
    getFirstXY();
    circleEffect = new Circle(DEFAULT_CIRCLE_RADIUS);
    circleEffect.setFill(Color.RED);
    add(circleEffect);
    circleEffect.setVisible(false);

    createAndAddAnimCircle(100);

    add(createRootView());
  }

  private void getFirstXY() {
    Point firstPoint= MouseInfo.getPointerInfo().getLocation();
    lastMoveX = firstPoint.getX();
    lastMoveY = firstPoint.getY();
  }

  private void createAndAddAnimCircle(int num) {
    for (int i = 0; i < num; i++) {
      Circle circle = new Circle();
      circle.setFill(Color.RED);
      circle.setRadius(5);
      circle.setVisible(false);
      circles.add(new DisperseCircle(circle));
      add(circle);
    }

  }

  private void addListener() {
    rootLayout.setOnMouseMoved(event -> {
      if (event.getEventType() == javafx.scene.input.MouseEvent.MOUSE_MOVED) {
        currentMoveX = event.getX();
        currentMoveY = event.getY();
        if (currentMoveX != lastMoveX || currentMoveY != lastMoveY) {
          lastMoveX = currentMoveX;
          lastMoveY = currentMoveY;
          count.set(0);
          hideCircleEffect();
        }
      }
    });
  }

  private void hideCircleEffect() {
    circleEffect.setVisible(false);
    circleEffect.setRadius(DEFAULT_CIRCLE_RADIUS);
  }

  private void startThread() {
    new Thread(() -> {
      while (true) {
        try {
          Thread.sleep(16);
          count.addAndGet(1);
          showCircleEffect(count);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    }).start();
  }

  private void showCircleEffect(AtomicInteger count) {
    if (count.get() == triggerNum) {
      circleEffect.setCenterX(lastMoveX);
      circleEffect.setCenterY(lastMoveY);
      circleEffect.setVisible(true);
    } else if (count.get() > triggerNum && count.get() < durationTriggerNum) {
      circleEffect.setRadius((count.get() - triggerNum) * 0.5 + DEFAULT_CIRCLE_RADIUS);
      startPlayDisperseAnim(count);
    } else if (count.get() >= durationTriggerNum) {
      goOnPlayDisperseAnim();
    }
  }

  private void goOnPlayDisperseAnim() {
    double distance = produceDistance();
    for (int i = 0; i < circles.size(); i++) {
      DisperseCircle circle = circles.get(i);
      double multiplier = produceMultiplier(i);
      double centerX = circle.getCircle().getCenterX();
      double centerY = circle.getCircle().getCenterY();
      double moveToX = multiplier * distance / (Math.sqrt((Math.pow(circle.getK(), 2)) + 1)) + centerX;
      double moveToY = circle.getK() * (moveToX - centerX) + centerY;
      circle.getCircle().setCenterX(moveToX);
      circle.getCircle().setCenterY(moveToY);
    }
  }

  private double produceDistance() {
    double distance = 15 - (count.get() - durationTriggerNum) / 5;
    if(distance < 0){
      distance = 0;
    }
    return distance;
  }

  private double produceMultiplier(int i) {
    double multiplier;
    if (i < circles.size() / 2){
      multiplier = 1;
    } else {
      multiplier = -1;
    }
    return multiplier;
  }

  private void startPlayDisperseAnim(AtomicInteger count) {
    if (count.get() == durationTriggerNum - 1) {
      circleEffect.setVisible(false);
      double radius = circleEffect.getRadius();
      for (DisperseCircle circle : circles) {
        double centerX = circleEffect.getCenterX() + (-1 * Math.random() * radius + radius / 2);
        double centerY = circleEffect.getCenterY() + (-1 * Math.random() * radius + radius / 2);
        circle.getCircle().setCenterX(centerX);
        circle.getCircle().setCenterY(centerY);
        circle.getCircle().setVisible(true);
        double k = (circleEffect.getCenterY() - centerY) / (circleEffect.getCenterX() - centerX);
        circle.setK(k);
      }
    }
  }

  private View createRootView() {
    View view = new View();
    view.setPrefWidth(getWindowWidth());
    view.setPrefHeight(getWindowHeight());
    return view;
  }
}
