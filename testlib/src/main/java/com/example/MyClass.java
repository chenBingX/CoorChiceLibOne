package com.example;

import java.lang.reflect.Method;
import java.util.List;

public class MyClass {
  public static void main(String[] args){
    MyClass obj = new MyClass();

    CustomClassLoader classLoader = new CustomClassLoader("");
    try {
      classLoader.setClassPath("/Users/chenbing/Documents/test/TestClass.class");
      Class clazz = classLoader.loadClass("TestClass");
      Method method = ReflectUtils.getMethod(clazz,"doSomething");
      System.out.println(clazz.getSimpleName());
      System.out.println("result = " + method.invoke(clazz.newInstance()));
    } catch (Exception e) {
      e.printStackTrace();
    }

    try {
      List list = obj.getList();
    } catch (NullPointerException e){
      e.printStackTrace();
    }

    System.out.println("a" +
      "aaaa" +
      "aaaaaaaaaaaaaa");
  }


  public List getList(){
    throw new NullPointerException("....");
  }

  public void testTryCacte(){



  }
}
