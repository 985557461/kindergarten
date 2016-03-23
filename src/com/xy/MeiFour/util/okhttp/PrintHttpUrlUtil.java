package com.xy.MeiFour.util.okhttp;

import android.util.Log;

import java.util.Map;
import java.util.Set;

/**
 * Created by xiaoyu on 2016/3/23.
 */
public class PrintHttpUrlUtil {
    private static final String TAG = "httpUrl";

    public static void printUrl(String url, Map<String, String> params) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(url);
        int paramsCount = params.size();
        if (paramsCount != 0) {
            stringBuilder.append("?");
            Set<Map.Entry<String, String>> set = params.entrySet();
            int index = 0;
            for (Map.Entry<String, String> entry : set) {
                stringBuilder.append(entry.getKey()).append("=").append(entry.getValue());
                if (index != paramsCount - 1) {
                    stringBuilder.append("&");
                }
            }
            Log.d(TAG, stringBuilder.toString());
        } else {
            Log.d(TAG, stringBuilder.toString());
        }
    }
}
