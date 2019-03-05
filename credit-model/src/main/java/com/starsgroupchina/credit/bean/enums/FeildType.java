package com.starsgroupchina.credit.bean.enums;

/**
 * Created by zhangfeng on 2018/6/15
 */
public class FeildType {

    //产品
    public static String PRODUCT = "PRODUCT";

    //用户
    public static String USER = "USER";

    //车辆
    public static String CAR = "CAR";

    public static String type(String formType) {
        if (formType.equals(FormType.FORM_USER.key()))
            return USER;

        if (formType.equals(FormType.FORM_CAR.key()))
            return CAR;

        return "";
    }

}
