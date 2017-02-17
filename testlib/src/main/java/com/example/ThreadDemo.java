package com.example;

/**
 * Project Name:CoorChiceLibOne
 * Author:CoorChice
 * Date:2017/2/10
 * Notes:
 */

public class ThreadDemo {

  public static void main(String[] args) {
    ThreadDemo instance = new ThreadDemo();
//    instance.test_1();
//    System.out.println(Thread.currentThread().getName());
    new Thread(Thread.currentThread()).start();
//    System.out.println(Thread.currentThread().getName());

  }

  public void test_1() {
    Thread thread1 = new Thread(() -> {
      System.out.println(Thread.currentThread().getName());
    }, "Thread_1");


    Thread thread2 = new Thread(thread1, "Thread_2");
    thread2.start();
  }
}
