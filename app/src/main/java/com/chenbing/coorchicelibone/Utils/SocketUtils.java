package com.chenbing.coorchicelibone.Utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;

public class SocketUtils {
    /**
     * 接收数据
     *
     * @param input 输入流
     * @return 接收到的数据，未接收成功返回null
     */
    public static byte[] receiveData(InputStream input) {
        if (input == null) return null;
        synchronized (input) {
            try {
                int length;
                //直到有数据跳出循环，读取数据
                while ((length = input.available()) == 0) {
                    //阻塞中
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        LogUtils.e("接收数据时遇到了错误!");
                    }

                }
                byte[] b = new byte[length];
                input.read(b);
                LogUtils.e("读取一条数据完成！数据大小：" + b.length);
                return b;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static byte[] receiveData(InputStream input, int max) {
        if (input == null) return null;
        synchronized (input) {
            try {
                int length;
                //直到有数据跳出循环，读取数据
                while ((length = input.available()) == 0) {
                    if (length > max) continue;
                    //阻塞中
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        LogUtils.e("接收数据时遇到了错误!");
                    }

                }
                byte[] b = new byte[length];
                input.read(b);
                LogUtils.e("读取一条数据完成！数据大小：" + b.length);
                return b;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 发送数据
     *
     * @param output 输出流
     * @param b      需要发送的数据
     * @return 发送成功，返回 true；发送失败，返回 false
     */
    public static boolean sendData(OutputStream output, byte[] b) {
        if (output == null || b == null || !(b.length > 0)) return false;
        synchronized (output) {
            try {
                LogUtils.e("发送一条数据完成！数据大小：" + b.length);
                output.write(b);
                output.flush();
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                LogUtils.e("发送数据时遇到了错误!");
            }
        }
        return false;
    }
}
