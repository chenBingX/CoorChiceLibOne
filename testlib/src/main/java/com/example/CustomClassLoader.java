package com.example;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;


public class CustomClassLoader extends ClassLoader {

  private String classPath;

  public CustomClassLoader(String classPath) {
    super(CustomClassLoader.class.getClassLoader());
    this.classPath = classPath;
  }

  @Override
  public Class<?> findClass(String name) throws ClassNotFoundException {
    if (classPath == null || classPath.equals("")) {
      throw new IllegalArgumentException("Please set classPath first.");
    }
    byte[] classData = loadClassData(classPath);
    if (classData == null) {
      throw new NullPointerException(
          "Try to get the byte[] that read from class file, but mate some problem. Please check class file path.");
    }
    return defineClass(name, classData, 0, classData.length); // 将class的字节数组解码为Class实例
  }

  /**
   * 读取Class文件
   */
  private byte[] loadClassData(String path) {
    byte[] bytes = new byte[1024];
    int length = 0;
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    File classFile = new File(path);
    FileInputStream fis = null;
    try {
      fis = new FileInputStream(classFile);
      while ((length = fis.read(bytes)) != -1) {
        baos.write(bytes, 0, length);
        baos.flush();
      }
      return baos.toByteArray();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        if (fis == null) {
          throw new NullPointerException(
              "Can not create FileInputStream, please check the file path.");
        }
        fis.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return null;
  }

  public void setClassPath(String classPath) {
    this.classPath = classPath;
  }

}
