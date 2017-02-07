package com.chenbing.coorchicelibone.Datas;

/**
 * Project Name:IceWeather
 * Author:IceChen
 * Date:2016/11/7
 * Notes:
 */

public class WeaponHook extends Weapon{
  private OnUseWeaponAttackListener onUseWeaponAttackListener;

  @Override
  public void attack(){
    super.attack();
    if (onUseWeaponAttackListener != null){
      onUseWeaponAttackListener.onUseWeaponAttack(damage);
    }
  }

  public void setOnUseWeaponAttackListener(OnUseWeaponAttackListener onUseWeaponAttackListener) {
    this.onUseWeaponAttackListener = onUseWeaponAttackListener;
  }

  public static interface OnUseWeaponAttackListener {
    int onUseWeaponAttack(int damage);
  }
}
