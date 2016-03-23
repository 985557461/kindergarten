package com.xy.MeiFour.common_background;

import android.text.TextUtils;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by xiaoyuPC on 2015/6/7.
 */
public class Account {
    public static final String kUsrInfo = "key_user_info";
    public static final String kPhoneNumber = "key_phone_number";
    public static final String kPassword = "key_password";
    public static final String kUserId = "key_user_id";
    public static final String kAvatar = "key_avatar";
    public static final String kUserName = "key_user_name";

    public String phoneNumber;
    public String password;
    public String userId;
    public String avatar;
    public String userName;

    // 用户选择照片时需要记住用户的偏好的文件夹的key
    public static String kLastSelectDir = "lastSelectDir";
    public String lastSelectDir;

    public static Account loadAccount() {
        String userInfo = CommonPreference.getStringValue(kUsrInfo, "");
        Account account = new Account();
        if (TextUtils.isEmpty(userInfo)) {
            return account;
        }
        try {
            JSONObject user = new JSONObject(userInfo);
            account.phoneNumber = user.optString(kPhoneNumber);
            account.password = user.optString(kPassword);
            account.userId = user.optString(kUserId);
            account.avatar = user.optString(kAvatar);
            account.userName = user.optString(kUserName);
            account.lastSelectDir = user.optString(kLastSelectDir);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return account;
    }

    public void saveMeInfoToPreference() {
        JSONObject info = new JSONObject();
        try {
            info.put(kPhoneNumber, phoneNumber);
            info.put(kPassword, password);
            info.put(kUserId, userId);
            info.put(kAvatar, avatar);
            info.put(kUserName, userName);
            info.put(kLastSelectDir, lastSelectDir);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        CommonPreference.setStringValue(kUsrInfo, info.toString());
    }

    public void clearMeInfo() {
        phoneNumber = "";
        password = "";
        userId = "";
        avatar = "";
        userName = "";
        lastSelectDir = "";
        saveMeInfoToPreference();
    }
}
