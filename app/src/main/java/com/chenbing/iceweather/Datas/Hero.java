package com.chenbing.iceweather.Datas;

/**
 * Project Name:IceWeather
 * Author:IceChen
 * Date:2016/11/7
 * Notes:
 */

public class Hero {
  private Weapon weaponMain;
  private final int weaponMainId;

  public Hero(Weapon weaponMain) {
    this.weaponMain = weaponMain;
    weaponMainId = this.weaponMain.hashCode();
  }

  public void attack() {
    if (this.weaponMain.hashCode() != weaponMainId) {
      throw new IllegalAccessError(String.format("警告！遭到入侵！入侵者身份:%d", this.weaponMain.hashCode()));
    }
    weaponMain.attack();
  }
}
