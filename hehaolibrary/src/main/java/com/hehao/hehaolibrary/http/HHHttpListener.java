package com.hehao.hehaolibrary.http;

/**
 * 网络请求回调接口
 * @author HeHao
 * @time 2015/11/5 11:05
 * @email 139940512@qq.com
 */
public interface HHHttpListener {
    /**
     * 网络请求成功回调
     * @param requestCode   请求码
     * @param obj           返回的数据
     * @param msg           成功消息
     */
    void onRequestSuccess(int requestCode, Object obj, String msg);

    /**
     * 网络请求失败回调
     * @param requestCode   请求码
     * @param errorCode     错误码
     * @param msg           错误消息
     */
    void onRequestError(int requestCode, Object errorCode, String msg);
}
