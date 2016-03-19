package com.xy.MeiFour.util.glide;

import android.content.Context;
import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.module.GlideModule;

/**
 * Created by liangyu on 2016/3/18.
 */
public class GlideConfiguration implements GlideModule{
    @Override
    public void applyOptions(Context context, GlideBuilder glideBuilder) {
        //默认使用565来解析，减少内存压力
        glideBuilder.setDecodeFormat(DecodeFormat.PREFER_RGB_565);
    }

    @Override
    public void registerComponents(Context context, Glide glide) {

    }
}
