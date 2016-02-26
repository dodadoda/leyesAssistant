package com.leyes.leyesassistant.http;

import android.content.Context;

import com.hehao.hehaolibrary.http.HHHttpClient;
import com.hehao.hehaolibrary.http.HHHttpListener;

/**
 * @author Hehao
 * @time 2015/12/24
 */
public class Http extends HHHttpClient{
    /**
     * 构造函数
     *
     * @param context  上下文参数
     * @param listener 监听器
     */
    public Http(Context context, HHHttpListener listener) {
        super(context, listener);
    }
}
