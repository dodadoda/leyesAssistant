package com.hehao.hehaolibrary.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 自定义SharedPreference操作类
 * @author HeHao
 * @time 2015/11/4 09:45
 * @email 139940512@qq.com
 */
public class HHShare {
    private static HHShare instance ;
    private SharedPreferences share ;

    private static final String SHARE_DEFAULT = "defult";
    private static final String SHARE_CACHE = "cache" ;

    protected HHShare(Context context, boolean isCache) {
        if(isCache)
            share = context.getSharedPreferences(SHARE_CACHE, Context.MODE_PRIVATE);
        else
            share = context.getSharedPreferences(SHARE_DEFAULT,Context.MODE_PRIVATE);
    }

    /**
     * 单例模式 获取SharedPreference
     * @param context   上下文参数
     * @return  一个该类的实例
     */
    public static HHShare getInstance(Context context){
        if(instance == null)
            instance = new HHShare(context, false) ;
        return instance;
    }

    /**
     * 获取键对应的String值
     * @param key
     * @return
     */
    public String getString(String key) {
        return share.getString(key, null);
    }

    /**
     * 获取键对应的int值
     * @param key
     * @return
     */
    public int getInt(String key){
        return share.getInt(key, 0);
    }

    /**
     * 获取键对应的long值
     * @param key
     * @return
     */
    public long getLong(String key) {
        return share.getLong(key, 0) ;
    }

    /**
     * 获取键对应的float值
     * @param key
     * @return
     */
    public float getFloat(String key){
        return share.getFloat(key, 0);
    }

    /**
     * 获取键对应的key值
     * @param key
     * @return
     */
    public boolean getBoolean(String key){
        return share.getBoolean(key, false) ;
    }

    /**
     * 检查share中是否存在该键
     * @param key   待检查的键
     * @return  true or false
     */
    public boolean isContain(String key){
        return share.contains(key) ;
    }

    /**
     * 将String放入share中
     * @param key   键
     * @param value 值
     */
    public void putString(String key, String value){
        share.edit().putString(key, value).commit() ;
    }

    /**
     * 将Int放入share中
     * @param key   键
     * @param value 值
     */
    public void putInt(String key, int value){
        share.edit().putInt(key, value).commit();
    }

    /**
     * 将boolean放入share中
     * @param key   键
     * @param value 值
     */
    public void putBoolean(String key, boolean value){
        share.edit().putBoolean(key, value).commit();
    }

    /**
     * 将float放入share中
     * @param key   键
     * @param value 值
     */
    public void putFloat(String key, float value){
        share.edit().putFloat(key, value).commit();
    }

    /**
     * 将Long放入share中
     * @param key   键
     * @param value 值
     */
    public void putLong(String key, long value){
        share.edit().putLong(key, value).commit();
    }

    /**
     * 移除指定的键
     * @param key   待移除的键
     */
    public void remove(String key) {
        share.edit().remove(key).commit();
    }

    /**
     * 清空share
     */
    public void clear(){
        share.edit().clear().commit();
    }
}
