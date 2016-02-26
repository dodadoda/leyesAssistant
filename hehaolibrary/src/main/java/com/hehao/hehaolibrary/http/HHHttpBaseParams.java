package com.hehao.hehaolibrary.http;

import android.content.Context;

import com.hehao.hehaolibrary.common.BaseApplication;
import com.hehao.hehaolibrary.util.InitUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 基本参数
 * @author Hehao
 * @time 2015/12/24
 */
public class HHHttpBaseParams {

    private HHHttpBaseParams INSTANCE = null;
    private Map<String, String> baseParams;

    private HHHttpBaseParams(Context context) {
        baseParams = new HashMap<>();
        /*baseParams.put("imsi", InitUtils.getIMSI(context));
        baseParams.put("imei", InitUtils.getIMEI(context));
        baseParams.put("modelName", android.os.Build.MODEL);// 手机类型
        baseParams.put("clientVersion","android-" + InitUtils.getVersionName(context));
        baseParams.put("channel", "91");
        baseParams.put("type", "1");
        baseParams.put("validId", BaseApplication.getValue("").toString());*/
    }

    /**
     * 获取参数
     * @param context
     * @return
     */
    public HHHttpBaseParams getInstance(Context context) {
        if( INSTANCE == null)
            INSTANCE = new HHHttpBaseParams(context);
        return INSTANCE;
    }

    /**
     * 设置参数
     * @param params
     */
    public void setBaseParams(Map<String, String> params) {
        this.baseParams = params;
    }
}
