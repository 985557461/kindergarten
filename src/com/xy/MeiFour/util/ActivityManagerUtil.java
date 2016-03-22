package com.xy.MeiFour.util;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liangyu on 2016/3/18.
 */
public class ActivityManagerUtil {
    private static ActivityManagerUtil instance;
    private List<Activity> activityList = new ArrayList<>();

    private ActivityManagerUtil() {
    }

    /**
     * 懒汉模式*
     */
    public static ActivityManagerUtil getInstance() {
        if (instance == null) {
            synchronized (ActivityManagerUtil.class) {
                if (instance == null) {
                    instance = new ActivityManagerUtil();
                }
            }
        }
        return instance;
    }

    public void addActivity(Activity activity) {
        if (activityList == null) {
            activityList = new ArrayList<>();
        }
        activityList.add(activity);
    }

    public void removeActivity(Activity activity) {
        if (activityList != null) {
            activityList.remove(activity);
        }
    }

    public void killActivity() {
        if (activityList != null) {
            for (; 0 < activityList.size(); ) {
                Activity activity = activityList.remove(0);
                if (activity != null) {
                    activity.finish();
                }
            }
        }
    }
}
