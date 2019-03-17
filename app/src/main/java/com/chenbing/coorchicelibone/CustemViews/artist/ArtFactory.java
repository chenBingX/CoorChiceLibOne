package com.chenbing.coorchicelibone.CustemViews.artist;

import android.graphics.Color;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by coorchice on 2017/10/19.
 */

public class ArtFactory {
    public String view;
    public int width;
    public int height;
    public float x;
    public float y;
    public int left;
    public int top;
    public int right;
    public int bottom;
    public int marginLeft;
    public int marginTop;
    public int marginRight;
    public int marginBottom;
//    public float pivotX = -1;
//    public float pivotY = -1;
//    public float degrees = 0;
//    public float scaleX = 1f;
//    public float scaleY = 1f;

    public String text;
    public int stringId = -1;
    public float textSize = 12f;
    public int textColor = Color.BLACK;

    public String src;
    public int drawableId = -1;

    public int gravity = -1;

    public ArtView build() {
        try {
            ClassLoader classLoader = this.getClass().getClassLoader();
            Class<?> clazz = classLoader.loadClass(this.getClass().getPackage().getName() + "." + this.view);
            final Object artView = clazz.newInstance();
            Method[] declaredMethods = clazz.getMethods();
            Field[] declaredFields = this.getClass().getFields();
            for (Method method : declaredMethods) {
                if (method.getName().contains("set")) {
                    for (Field field : declaredFields) {
                        String fieldName = field.getName();
                        fieldName = fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1, fieldName.length());
                        if (method.getName().equals("set" + fieldName)) {
                            if (field.getType().isAssignableFrom(String.class)) {
                                method.invoke(artView, (String) field.get(this));
                            }
                            if (field.getType().isAssignableFrom(int.class)) {
                                method.invoke(artView, field.getInt(this));
                            }
                            if (field.getType().isAssignableFrom(float.class)) {
                                method.invoke(artView, field.getFloat(this));
                            }
                            break;
                        }

                    }
                }
            }
            return (ArtView) artView;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }
}
