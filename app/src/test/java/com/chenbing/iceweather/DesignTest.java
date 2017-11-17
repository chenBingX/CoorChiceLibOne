package com.chenbing.iceweather;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.chenbing.coorchicelibone.Datas.Hero;
import com.chenbing.coorchicelibone.Datas.Weapon;
import com.chenbing.coorchicelibone.Datas.WeaponHook;
import com.chenbing.coorchicelibone.Utils.ReflectUtils;

/**
 * Project Name:IceWeather
 * Author:IceChen
 * Date:2016/11/7
 * Notes:
 */

public class DesignTest {

  @Test
  public void testHook() {
    Hero hero = new Hero(new Weapon());
    try {
      Field weapon = ReflectUtils.getVariable(hero.getClass(), "weaponMain");
      weapon.setAccessible(true);
      Weapon weaponHook = new WeaponHook();
      ((WeaponHook) weaponHook).setOnUseWeaponAttackListener(damage -> {
          assertEquals(100, damage);
          return damage;
      });
      weapon.set(hero, weaponHook);
      hero.attack();
    } catch (NoSuchFieldException e) {
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void test(){
    List<Object> objectList = new ArrayList<>();
    Object o = new Object();
    objectList.remove(null);
  }
}
