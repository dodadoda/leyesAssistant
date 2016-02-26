package com.hehao.hehaolibrary.util;

import android.content.Context;
import android.content.pm.PackageManager;
import android.telephony.TelephonyManager;

/**
 * @author Hehao
 * @time 2015/12/24
 */
public class InitUtils {

    /**
     * 获取IMSI
     * @param context
     * @return
     */
    public static String getIMSI(Context context) {
        TelephonyManager telMgr = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        String imsi = "111111111111111";
        if(telMgr.getSubscriberId() != null && telMgr.getSubscriberId().length() != 0) {
            imsi = telMgr.getSubscriberId();
        }
        return imsi;
    }

    /**
     * 获取IMEI
     * @param context
     * @return
     */
    public static String getIMEI(Context context) {
        TelephonyManager telMgr = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        String imei = "111111111111111";
        if(telMgr.getDeviceId() != null && telMgr.getDeviceId().length() != 0) {
            imei = telMgr.getDeviceId();
        }
        return imei;
    }

    /**
     * 获取版本信息
     * @param context
     * @return
     */
    public static String getVersionName(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("get versionCode Exception(RuntimeException)");
        }
    }
}
