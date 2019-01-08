package com.chenbing.coorchicelibone.Utils;

import android.app.Activity;

import java.lang.reflect.Field;

/**
 * @author coorchice
 * @date 2019/01/08
 */
public class ButterKnife {
    public static void bind(Activity act){
        if (act != null){
            Class<? extends Activity> cls = act.getClass();
            // 获取Activity的所有成员变量
            Field[] fields = cls.getDeclaredFields();
            for (Field f:fields){
                // 尝试取出 BindView 类型的注解，如果没有就会为 null
                BindView annotation = f.getAnnotation(BindView.class);
                if (annotation != null){
                    // 取出注解的值
                    int id = annotation.value();
                    f.setAccessible(true);
                    try {
                        // 获得属性的类型
                        Class viewClass = f.getType();
                        // 根据id获取view对象，然后强制转换，设置属性的值
                        f.set(act, viewClass.cast(act.findViewById(id)));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

        }
    }
}
