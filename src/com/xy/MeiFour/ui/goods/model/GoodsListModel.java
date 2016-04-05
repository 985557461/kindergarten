package com.xy.MeiFour.ui.goods.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by xiaoyu on 2016/4/4.
 */
public class GoodsListModel {
    @SerializedName("result")
    public String result;
    @SerializedName("message")
    public String message;
    @SerializedName("artlist")
    public List<GoodsItemModel> artlist;
}
