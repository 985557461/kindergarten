package com.xy.MeiFour.ui.home;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by xiaoyu on 2016/3/23.
 */
public class HomeInfoModel {
    @SerializedName("message")
    public String message;
    @SerializedName("result")
    public String result;
    @SerializedName("advertising")
    public List<HomeBannerModel> advertising;
    @SerializedName("banner_posts")
    public List<HomeCategoryModel> banner_posts;
}
