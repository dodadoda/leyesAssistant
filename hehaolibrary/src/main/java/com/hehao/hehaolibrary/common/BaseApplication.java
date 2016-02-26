package com.hehao.hehaolibrary.common;

import android.app.Activity;
import android.app.Application;

import com.hehao.hehaolibrary.util.HHLog;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Application基类，提供activity的管理，全局数据的储存功能
 * @author HeHao
 * @time 2015/11/4 09:28
 * @email 139940512@qq.com
 */
public class BaseApplication extends Application{
    /**activity栈，用来管理activity*/
    private List<Activity> acts;
    /**存储全局数据的hashmap*/
    private static HashMap<String, Object> hashMap;

    @Override
    public void onCreate() {
        super.onCreate();
        HHLog.i(getClass(), "Application start");

        acts = new LinkedList<Activity>() ;
        hashMap = new HashMap<String, Object>();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        HHLog.i(getClass(), "Application closed");
    }

    /**
     * 添加activity到activity管理栈中
     * @param activity  被添加的activity
     */
    public void addActivity(Activity activity){
        acts.add(activity);
    }

    /**
     * 清理activity栈并退出
     */
    public void exit(){
        clear();
        System.exit(0);
    }

    /**
     * 清理activity栈
     */
    public void clear(){
        if(acts != null && !acts.isEmpty()){
            for(Activity activity : acts){
                activity.finish();
            }
        }
    }

    /**
     * 存全局数据
     * @param key   键
     * @param value 值
     */
    public static void putValue(String key, Object value){
        if(hashMap == null)
            hashMap = new HashMap<String, Object>();
        hashMap.put(key, value);
    }

    /**
     * 取全局数据
     * @param key   键
     * @return  键对应的值
     */
    public static Object getValue(String key){
        if(hashMap == null)
            return null;
        return hashMap.get(key);
    }

}
