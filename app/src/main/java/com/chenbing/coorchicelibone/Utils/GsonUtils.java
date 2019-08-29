package com.chenbing.coorchicelibone.Utils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * Notes:
 */
public class GsonUtils {
    private Gson gson;
    private final JsonParser jsonParser;

    private GsonUtils() {
        gson = new Gson();
        jsonParser = new JsonParser();
    }

    private static final class Holder {
        private static final GsonUtils instance = new GsonUtils();
    }

    public static String toJson(Object o) {
        return getSingleInstance().toJson(o);
    }

    public static <T> T fromJson(String json, Class<T> classOfT) {
        return getSingleInstance().fromJson(json, classOfT);
    }

    public static JsonObject fromJson(String json) {
        return Holder.instance.jsonParser.parse(json).getAsJsonObject();
    }

    public static Gson getSingleInstance() {
        return Holder.instance.gson;
    }

    public static Gson newInstance() {
        return new Gson();
    }
}
