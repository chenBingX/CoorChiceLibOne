package com.CSDNUtil;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * Project Name:IceWeather
 * Author:CoorChice
 * Date:2017/1/23
 * Notes: 用于统一添加内容到已有的文件中。
 *        支持对文件夹遍历。
 */

public class WriteToFile {

  String path = "/Users/chenbing/Pictures/博客用图/公共文案";
  String content =
      "\n\n\n![CoorChice的公众号](http://ogemdlrap.bkt.clouddn.com/%E5%85%AC%E4%BC%97%E5%8F%B7%E4%BA%8C%E7%BB%B4%E7%A0%81.jpg)  \n";

  public static void main(String[] args) {
    WriteToFile instance = new WriteToFile();
    instance.appendContentToBlogs(instance.path);
  }

  public void appendContentToBlogs(String rootFilePath) {
    File rootFile = new File(rootFilePath);
    scanFile(rootFile);
  }

  private void scanFile(File file) {
    if (checkFileExists(file)) {
      if (file.isDirectory()) {
        File[] firstFileList = file.listFiles();
        if (firstFileList != null && firstFileList.length > 0) {
          for (File childFile : firstFileList) {
            if (file.isDirectory()) {
              scanFile(childFile);
            } else {
              appendContent(childFile, content);
            }
          }
        }
      } else {
        appendContent(file, content);
      }
    }
  }

  private void appendContent(File file, String content) {
    if (isValidFile(file)) {
      try {
        String src = "";
        src = readFile(file);
        writerFile(file, src, content);
        readFile(file);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  private boolean isValidFile(File file) {
    return file.getName().contains(".md");
  }

  public String readFile(File file) throws IOException {
    String content = "";

    FileInputStream fileInputStream = null;
    InputStreamReader inputStreamReader = null;
    BufferedReader bufferedReader = null;

    StringBuffer stringBuffer = new StringBuffer();

    if (checkFileExists(file) && file.canRead() && file.isFile()) {
      fileInputStream = new FileInputStream(file);
      inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");
      bufferedReader = new BufferedReader(inputStreamReader);
      int c;
      while ((c = bufferedReader.read()) != -1) {
        stringBuffer.append((char) c);
      }
      content = stringBuffer.toString();
      bufferedReader.close();
      inputStreamReader.close();
      fileInputStream.close();
    }
    System.out.println("读取到文件内容：\n" + content);
    return content;
  }


  public String readFile(String filePath) throws IOException {
    // String filePath = "/Users/chenbing/chenbingx.github.io/source/_posts/其它/test.md";
    File file = new File(filePath);
    return readFile(file);
  }

  public void writerFile(File file, String src, String content) throws IOException {
    FileOutputStream fileOutputStream = null;
    OutputStreamWriter outputStreamWriter = null;
    BufferedWriter bufferedWriter = null;
    if (checkFileExists(file) && file.canRead() && file.isFile()) {
      fileOutputStream = new FileOutputStream(file);
      outputStreamWriter = new OutputStreamWriter(fileOutputStream, "UTF-8");
      bufferedWriter = new BufferedWriter(outputStreamWriter);
      bufferedWriter.write((src + content));
      bufferedWriter.close();
      outputStreamWriter.close();
      fileOutputStream.close();
    }
  }

  public void writerFile(String filePath, String src, String content) throws IOException {
    File file = new File(filePath);
    writerFile(file, src, content);
  }

  private boolean checkFileExists(File file) {
    if (file.exists()) {
      String format = "找到目标文件!\n绝对路径：%s\n是否是文件夹：%s, size: %s";
      String path = file.getAbsolutePath();
      String isDirectory = file.isDirectory() + "";
      String size = file.length() / 1024 + "k";
      System.out.println(String.format(format, path, isDirectory, size));
      return true;
    } else {
      System.out.println("未找到目标文件!");
      return false;
    }
  }


}
