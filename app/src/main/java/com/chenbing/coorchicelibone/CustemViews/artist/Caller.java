package com.chenbing.coorchicelibone.CustemViews.artist;

import android.content.Context;
import android.content.Intent;


/**
 * Project Name:GhostApplication
 * Author:CoorChice
 * Date:2017/10/18
 * Notes:
 */

public class Caller {

  private Context context;

  public void call(String s){
    if (s.equals("getContext")){
//      context = StaticContext.context();
    }

    if (s.equals("startActivity")){
      context.startActivity(new Intent());
    }
  }
}
