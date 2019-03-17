/*
 * Copyright (C) 2019 CoorChice <icechen_@outlook.com>
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 * <p>
 * Last modified 2/18/19 4:56 PM
 */

package com.chenbing.iceweather;

import android.support.constraint.solver.Goal;

import com.chenbing.coorchicelibone.Datas.Test;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

/**
 * @author coorchice
 * @date 2019/02/18
 */
public class TestClass {


    public static class Test1<W> {
        public W data;

        public W getData() {
            return data;
        }
    }

    public static class T1 {
        public String p;
        public String pp;
    }

    public static <T> void test(Listener<T> listener) {
//        Test1<T> test1 = new Test1<T>(){
//            public T data;
//        };
//        String json = "{\"data\":{\"p\":\"Hahaha!\", \"pp\":\"Come!\"}}";
//
//        Test1<T> r = (Test1<T>) new Gson().fromJson(json, new Test1<T>().getClass());
//
//        if (listener != null){
////            listener.on(new Gson().fromJson(new Gson().toJson(r.getData()), new TypeToken<T>(){}.getType()));
//            T data = (T)r.data;
//            listener.on(data);
//        }
////        Test1 r = new Gson().fromJson(json, Test1.class);
//        System.out.println(new Gson().toJson(r.getData()));

        Object obj = new Test1();
        System.out.println(obj.toString());
    }

    public static interface Listener<T> {
        void on(T data);
    }
}
