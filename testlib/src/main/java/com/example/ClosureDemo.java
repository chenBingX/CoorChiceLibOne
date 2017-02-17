package com.example;

/**
 * Project Name:CoorChiceLibOne
 * Author:CoorChice
 * Date:2017/2/9
 * Notes:
 */

public class ClosureDemo {

  public static void main(String[] args) {
    ClosureDemo closureDemo = new ClosureDemo();
    ClosureInterface closureInterface = closureDemo.closerFunc();  //相当于获取了函数fun和objDemo的包
    ObjDemo objDemo = closureInterface.func();
    System.out.println(objDemo.hashCode());   //原本的局部变量objDemo被传递到了这里
  }

  public ClosureInterface closerFunc() {
    ObjDemo objDemo = new ObjDemo();          //创建局部变量，它的作用域原本只在这个函数中
    System.out.println(objDemo.hashCode());
    return () -> objDemo;                     //将局部变量objDemo和方法func()打包，传递出去
  }

  public static interface ClosureInterface {
    ObjDemo func();
  }

  public static class ObjDemo {

  }
}
