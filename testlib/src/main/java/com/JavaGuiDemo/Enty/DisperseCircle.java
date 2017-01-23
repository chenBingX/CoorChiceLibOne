package com.JavaGuiDemo.Enty;

import javafx.scene.shape.Circle;

/**
 * Project Name:IceWeather
 * Author:IceChen
 * Date:2016/11/20
 * Notes:
 */

public class DisperseCircle {
  private Circle circle;
  private double targetX;
  private double targetY;
  private double moveDistance;
  private double k;

  public DisperseCircle(Circle circle) {
    this.circle = circle;
  }

  public Circle getCircle() {
    return circle;
  }

  public void setCircle(Circle circle) {
    this.circle = circle;
  }

  public double getTargetX() {
    return targetX;
  }

  public void setTargetX(double targetX) {
    this.targetX = targetX;
  }

  public double getTargetY() {
    return targetY;
  }

  public void setTargetY(double targetY) {
    this.targetY = targetY;
  }

  public double getMoveDistance() {
    return moveDistance;
  }

  public void setMoveDistance(double moveDistance) {
    this.moveDistance = moveDistance;
  }

  public double getK() {
    return k;
  }

  public void setK(double k) {
    this.k = k;
  }
}
