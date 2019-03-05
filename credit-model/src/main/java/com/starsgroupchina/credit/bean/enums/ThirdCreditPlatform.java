package com.starsgroupchina.credit.bean.enums;

/**
 * 第三方征信接口
 */
public enum  ThirdCreditPlatform {
    //同盾请求
    PLATFORM_TD_REQUEST("TD-REQUEST","同盾"),
    //同盾响应
    PLATFORM_TD_RESPONSE("TD-RESPONSE","同盾"),
    //大峰请求
    PLATFORM_DF_REQUEST("DF-REQUEST","大蜂"),
    //大峰响应
    PLATFORM_DF_RESPONSE("DF-RESPONSE","大蜂"),
    //前海征信8036
    PLATFORM_QHZX_8036("QHZX-8036","前海征信"),
    //前海征信8107
    PLATFORM_QHZX_8107("QHZX-8107","前海征信");
    private String key;
    private String value;

    ThirdCreditPlatform(String key,String value){
        this.key = key;
        this.value=value;
    }

    public String key() {
        return key;
    }

    public String value() {
        return value;
    }

}
