package com.xy.ChatWebSocket.chat.model;

/**
 * Created by liangyu on 2016/3/8.
 */

import com.google.gson.annotations.SerializedName;

import java.util.Map;

/**登陆登出的model**/
public class LoginOutModel {
    @SerializedName("type")
    public String type;
    @SerializedName("client_id")
    public String client_id;
    @SerializedName("client_name")
    public String client_name;
    @SerializedName("time")
    public String time;
    @SerializedName("client_list")
    public Map<String,String> client_list;
}
