package com.hehao.hehaolibrary.util;

import android.util.Log;

/**
 * 自定义日志类
 * @author HeHao
 * @time 2015/11/4 09:30
 * @email 139940512@qq.com
 */
public class HHLog {

    private static final String TAG = "HHLog" ;

    public static void i(Object msg) {
        Log.i(TAG, msg.toString()) ;
    }

    public static void i(Class<?> cls, Object msg) {
        Log.i(cls.getSimpleName(), msg.toString());
    }

    public static void e(Object msg) {
        Log.e(TAG, msg.toString()) ;
    }

    public static void e(Class<?> cls, Object msg) {
        Log.e(cls.getSimpleName(), msg.toString());
    }

    public static void d(Object msg) {
        Log.d(TAG, msg.toString()) ;
    }

    public static void d(Class<?> cls, Object msg) {
        Log.d(cls.getSimpleName(), msg.toString());
    }

    public static void v(Object msg) {
        Log.v(TAG, msg.toString()) ;
    }

    public static void v(Class<?> cls, Object msg) {
        Log.v(cls.getSimpleName(), msg.toString());
    }
}
