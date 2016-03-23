package com.xy.MeiFour.util;

import com.google.gson.Gson;

/**
 * Created by xiaoyu on 2016/3/23.
 */
public class GsonUtil {
    private static final Gson gson = new Gson();

    public static <T> T transModel(String result,Class<T> classOfT) {
        T resultModel = null;
        try {
            resultModel = gson.fromJson(result, classOfT);
        } catch (Exception e) {
            resultModel = null;
        }
        return resultModel;
    }
}
