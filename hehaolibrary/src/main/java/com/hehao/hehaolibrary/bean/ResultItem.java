package com.hehao.hehaolibrary.bean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author HeHao
 * @time 2015/11/5 16:43
 * @email 139940512@qq.com
 */
public class ResultItem {
    protected Map<String ,Object> map;

    /**
     * 构造方法
     */
    public ResultItem(){
        map = new HashMap<>();
    }

    /**
     * 构造方法
     * @param value 用来创建ResultItem的Map
     */
    public ResultItem(Map<String, Object> value){
        map = value;
    }

    public void addValue(String key, Object obj){
        map.put(key, obj);
    }

    /**
     * 获取键对应的值
     * @param key   键
     * @return
     */
    public Object getValue(String key){
        if(map == null)
            return null;
        else if(map.containsKey(key))
            return map.get(key);
        else
            return null;
    }

    /**
     * 通过键获取String
     * @param key   键
     * @return
     */
    public String getString(String key){
        Object value = getValue(key);
        if(value == null)
            return null;
        return value.toString();
    }

    /**
     * 获取键对应类型为ResultItem的值
     * @param key   键
     * @return
     */
    public ResultItem getItem(String key){
        ResultItem item = null;
        try{
            item = (ResultItem)getValue(key);
        }catch (Exception e){}
        return item;
    }

    /**
     * 获取键对应类型为List<ResultItem>的值
     * @param key   键
     * @return
     */
    public List<ResultItem> getItems(String key){
        List<ResultItem> items = null;
        try{
            Object value = getValue(key);
            if(value instanceof List)
                items = (List<ResultItem>)value;
        }catch (Exception e){}
        return items;
    }
}
