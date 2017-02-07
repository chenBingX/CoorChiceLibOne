package com.chenbing.coorchicelibone.Datas;

/**
 * Project Name:IceWeather
 * Author:IceChen
 * Date:2016/10/10
 * Notes:
 */
public class TextProperty {

  private String method;
  private String value;
  private String displayContent;

  private TextPropertyType type;

  private Class valueClass;

  public TextProperty(String method, TextPropertyType type, Class valueClass) {
    this.method = method;
    this.type = type;
    this.valueClass = valueClass;
    displayContent = method + "()";
  }

  public String getMethod() {
    return method;
  }

  public void setMethod(String method) {
    this.method = method;
    displayContent = method + "()";
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
    displayContent = String.format(method + "(%s)", value);
  }

  public TextPropertyType getType() {
    return type;
  }

  public void setType(TextPropertyType type) {
    this.type = type;
  }

  public String getDisplayContent() {
    return displayContent;
  }

  public Class getValueClass() {
    return valueClass;
  }

  public enum TextPropertyType {
    INPUT, SELECTE
  }
}
