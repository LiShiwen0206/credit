package com.starsgroupchina.credit.bean.enums;

/**
 * Created by zhangfeng on 2018/6/15
 */
public enum FormType {

//    //产品表单
//    public static String FORM_PRODUCT="FORM_PRODUCT";
//
//    //用户表单
//    public static String FORM_USER="FORM_USER";
//
//    //车辆表单
//    public static String FORM_CAR="FORM_CAR";

    FORM_PRODUCT("FORM_PRODUCT", "产品表单"),
    FORM_USER("FORM_USER", "用户表单"),
    FORM_CAR("FORM_CAR", "车辆表单");

    private String key;

    private String value;

    FormType(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String key() {
        return key;
    }

    public String value() {
        return value;
    }

}
