package com.hehao.hehaolibrary.http;

import java.util.HashMap;
import java.util.Map;

/**
 * 网络请求基础参数
 * @author Hehao
 * @time 2015/12/24
 */
public class HHHttpBaseHeaders {

    /**
     * 请求头
     */
    private Map<String, String> headers;

    private static HHHttpBaseHeaders INSTANCE = null;


    /**
     * 单例模式
     */
    private HHHttpBaseHeaders() {
        headers = new HashMap<String, String>();
        headers.put("Accept", "text/xml,application/json");
        headers.put("Charset", "UTF-8");
        headers.put("Accept-Language", "zh-cn");
    }

    public HHHttpBaseHeaders getInstance() {
        if(INSTANCE == null)
            INSTANCE = new HHHttpBaseHeaders();
        return INSTANCE;
    }

    /**
     * 获取基础头部
     * @return
     */
    public Map<String, String> getHeaders() {
        return headers;
    }

    /**设置头部*/
    public void setHeaders(Map<String ,String> headers) {
        this.headers = headers;
    }
}
