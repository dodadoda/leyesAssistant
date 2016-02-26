package com.hehao.hehaolibrary.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author HeHao
 * @time 2015/11/19 10:38
 * @email 139940512@qq.com
 */
public class HHCheckUtils {
    /**
     * 检查字符串是否全为字母或数字
     * @param str   待检查的字符串
     * @return  true or false
     */
    public static boolean isNumOrLetter(String str) {
        if (str == null)
            return false;
        String ch = "^[A-Za-z0-9]{1,16}";
        Pattern p = Pattern.compile(ch);
        Matcher m = p.matcher(str);
        return m.matches();
    }

    /**
     * 检查密码是否合法
     * @param pass
     * @return
     */
    public static boolean isPassword(String pass) {
        if (pass == null || pass.trim()=="")
            return false;
        String ch = "[0-9a-zA-Z]{6,20}";
        Pattern p = Pattern.compile(ch);
        Matcher m = p.matcher(pass);
        return m.matches();
    }

    /**
     * 验证手机号
     * @param str
     * @return
     */
    public static boolean isMobile(String str) {
        if (str == null || str.trim()=="")
            return false;
        return Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$").matcher(str).matches(); // 验证手机号
    }

    /**
     * 是否是中文
     * @param str
     * @return
     */
    public static boolean isChinese(String str) {
        if (str == null || str.trim()=="")
            return false;
        return Pattern.compile("^[\u4e00-\u9fa5]+$").matcher(str).matches();
    }

    /**
     * 是否是IP地址
     * @param str
     * @return
     */
    public static boolean isIpAddress(String str) {
        if (str == null || str.trim()=="")
            return false;
        return Pattern.compile("(25[0-5]|2[0-4]\\d|1\\d{2}|[1-9]?\\d)(\\.(25[0-5]|2[0-4]\\d|1\\d{2}|[1-9]?\\d)){3}").matcher(str).matches();
    }

    /**
     * 是否是身份证
     * @param str
     * @return
     */
    public static boolean isIdentity(String str) {
        if (str == null || str.trim()=="")
            return false;
        if (str.length() == 15)
            return Pattern.compile("^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$").matcher(str).matches();
        if (str.length() == 18)
            return Pattern.compile("^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9]|X)$").matcher(str).matches();
        return false;
    }

    /**
     * 座机号码验证
     * @param str
     * @return
     */
    public static boolean isPhone(String str) {
        if (str == null || str.trim() == "")
            return false;
        Pattern p1 = null, p2 = null;
        Matcher m = null;
        boolean b = false;
        str = str.replaceAll("-", "");
        // p1 = Pattern.compile("^[0][1-9]{2,3}-[0-9]{5,10}$"); // 验证带区号的
        if (str.length() == 11) {
            p1 = Pattern.compile("^[0][1-9]{2,3}[0-9]{5,10}$"); // 验证带区号的
            m = p1.matcher(str);
            b = m.matches();
        } else if (str.length() <= 9) {
            p2 = Pattern.compile("^[1-9]{1}[0-9]{5,8}$"); // 验证没有区号的
            m = p2.matcher(str);
            b = m.matches();
        }
        if (!b)
            return isMobile(str);
        return b;
    }

    /**
     * 邮箱验证
     * @param str
     * @return
     */
    public static boolean isEmail(String str) {
        if (str == null)
            return false;
        return Pattern
                .compile("^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$")
                .matcher(str).matches();
    }
}
