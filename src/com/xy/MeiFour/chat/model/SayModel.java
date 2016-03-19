package com.xy.MeiFour.chat.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by liangyu on 2016/3/8.
 */
public class SayModel {
    @SerializedName("type")
    public String type;
    @SerializedName("from_client_id")
    public String from_client_id;
    @SerializedName("from_client_name")
    public String from_client_name;
    @SerializedName("to_client_id")
    public String to_client_id;
    @SerializedName("content")
    public String content;
    @SerializedName("time")
    public String time;
}
