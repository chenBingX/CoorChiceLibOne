package com.chenbing.coorchicelibone.CameraSuit;

import com.chenbing.coorchicelibone.IceApplication;

import android.app.admin.DevicePolicyManager;
import android.content.Context;

/**
 * Project Name:CoorChiceLibOne
 * Author:CoorChice
 * Date:2017/3/20
 * Notes:
 */

public class CameraUtils {

  public static boolean cameraEnable() {
    // 获取设备管理器
    DevicePolicyManager dpm = (DevicePolicyManager) IceApplication.getAppContext()
        .getSystemService(Context.DEVICE_POLICY_SERVICE);
    // 检查摄像头是否可用
    // 传空意味着检查是否有任何一个可用的Camera
    if (dpm.getCameraDisabled(null)) {
      return false;
    } else {
      return true;
    }
  }
}
