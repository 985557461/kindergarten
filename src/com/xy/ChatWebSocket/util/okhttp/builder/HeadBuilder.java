package com.xy.ChatWebSocket.util.okhttp.builder;

import com.xy.ChatWebSocket.util.okhttp.OkHttpUtils;
import com.xy.ChatWebSocket.util.okhttp.request.OtherRequest;
import com.xy.ChatWebSocket.util.okhttp.request.RequestCall;

/**
 * Created by zhy on 16/3/2.
 */
public class HeadBuilder extends GetBuilder
{
    @Override
    public RequestCall build()
    {
        return new OtherRequest(null, null, OkHttpUtils.METHOD.HEAD, url, tag, params, headers).build();
    }
}
