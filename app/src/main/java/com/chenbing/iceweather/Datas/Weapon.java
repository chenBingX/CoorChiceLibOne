package com.chenbing.iceweather.Datas;

/**
 * Project Name:IceWeather
 * Author:IceChen
 * Date:2016/11/7
 * Notes:
 */

public class Weapon {
  int damage = 10;

  public void attack(){
    System.out.println(String.format("对目标造成 %d 点伤害",damage));
  }
}
