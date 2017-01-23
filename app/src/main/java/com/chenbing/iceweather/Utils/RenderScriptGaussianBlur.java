package com.chenbing.iceweather.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;

/**
 * Project Name:IceWeather
 * Author:CoorChice
 * Date:2016/12/28
 * Notes:
 */

public class RenderScriptGaussianBlur {
  private RenderScript rs;

  public RenderScriptGaussianBlur(Context context) {
    this.rs = RenderScript.create(context);
  }

  public Bitmap blur(float radius, Bitmap bitmapOriginal) {
    final Allocation input = Allocation.createFromBitmap(rs, bitmapOriginal);
    final Allocation output = Allocation.createTyped(rs, input.getType());
    final ScriptIntrinsicBlur script = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
    script.setRadius(radius);
    script.setInput(input);
    script.forEach(output);
    output.copyTo(bitmapOriginal);
    return bitmapOriginal;
  }
}
