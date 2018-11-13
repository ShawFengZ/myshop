package com.zxf.my.shop.commons.utils;

/**
 * @author zxf
 * @date 2018/10/29 18:43
 */
public class RegexpUtils {

    /**
     * 验证手机号
     * */
    public static final String PHONE = "^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";

    /**
     * 验证邮箱地址
     * */
    public static final String EMAIL = "^[A-Za-z\\d]+([-_.][A-Za-z\\d]+)*@([A-Za-z\\d]+[-.])+[A-Za-z\\d]{2,4}$/";

    /**
     * 验证手机号
     * */
    public static boolean checkPhone(String phone) {
        return phone.matches(PHONE);
    }

    /**
     * 验证邮箱
     * */
    public static boolean checkEmail(String email){
        return email.matches(EMAIL);
    }
}
