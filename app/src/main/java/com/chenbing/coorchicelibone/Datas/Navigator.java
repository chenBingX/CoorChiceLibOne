package com.chenbing.coorchicelibone.Datas;

/**
 * Project Name:IceWeather
 * Author:IceChen
 * Date:2016/10/9
 * Notes:
 */
public class Navigator {
  private String pageClassName;
  private String pageName;

  public Navigator(String pageName) {
    this.pageClassName = pageName;
  }

  public String getPageClassName() {
    return pageClassName;
  }

  public void setPageClassName(String pageClassName) {
    this.pageClassName = pageClassName;
  }

  public String getPageName() {
    return pageName;
  }

  public void setPageName(String pageName) {
    this.pageName = pageName;
  }
}
