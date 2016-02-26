package com.hehao.hehaolibrary.util;


import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.view.View;
import android.view.WindowManager;

import com.hehao.hehaolibrary.bean.ResultItem;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 各种工具类
 * @author HeHao
 * @time 2015/11/4 16:15
 * @email 139940512@qq.com
 */
public class HHUtils {

    private static long lastTime = 0;

    /**
     * dip 转 px
     * @param context
     * @param dpValue
     * @return
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 是否是快速点击事件
     * @return
     */
    public static boolean isFastClick() {
        long curTime = System.currentTimeMillis();
        if(curTime - lastTime < 500)
            return true;
        lastTime = curTime;
        return false;
    }

    /**
     * 将json字符串转换成ResultItem数组
     * @param content
     * @return
     */
    public static ResultItem getResultItemByJson(String content){
        ResultItem item = new ResultItem();
        try{
            JSONObject jsonObject = new JSONObject(content);
            item = convertJSONObject(jsonObject);
        }catch (Exception e){}

        return item;
    }

    /**将JsonObject转换成ResultItem*/
    @SuppressWarnings("unchecked")
    public static ResultItem convertJSONObject(JSONObject jsonObj){
        ResultItem resultItem = new ResultItem();
        if(jsonObj!=null){
            //遍历所有的KEY值
            Iterator<String> keys = jsonObj.keys();
            while(keys.hasNext()){
                String key = keys.next();
                try{
                    //获取具体对象
                    Object obj = jsonObj.get(key);
                    if(obj!=null){
                        if(obj instanceof JSONObject){
                            //添加属性(递归添加)
                            resultItem.addValue(key,convertJSONObject((JSONObject)obj));

                        }else if(obj instanceof JSONArray){
                            //列表对象
                            List<Object> listItems = new ArrayList<Object>();
                            //将JSONArray足个解析
                            JSONArray tempArray = (JSONArray) obj;
                            for(int i = 0; i < tempArray.length() ; i++){
                                Object itempObj = tempArray.get(i);
                                if(itempObj instanceof JSONObject){
                                    //递归添加
                                    listItems.add(convertJSONObject(tempArray.getJSONObject(i)));
                                }else {
                                    listItems.add(itempObj);
                                }
                            }
                            resultItem.addValue(key,listItems);

                        }else{
                            resultItem.addValue(key,obj.toString());
                        }
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
        return resultItem;
    }

    /**
     * 获取控件
     * @param v
     * @param id
     * @param <T>
     * @return
     */
    public static <T extends View> T getView(View v, int id){
        return (T)v.findViewById(id);
    }

    /**
     * 获取程序版本信息
     * @param context
     * @return
     */
    public static PackageInfo getAppVersion(Context context) {
        try{
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {}
        return null;
    }

    /**获取屏幕宽度*/
    public static int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        return wm.getDefaultDisplay().getWidth();
    }

    /**获取屏幕高度*/
    public static int getScreenHeight(Context context) {
        WindowManager wm = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        return wm.getDefaultDisplay().getHeight();
    }
}
