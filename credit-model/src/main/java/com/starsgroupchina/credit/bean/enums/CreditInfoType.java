package com.starsgroupchina.credit.bean.enums;

/**
 * 征信信息类型
 */
public enum  CreditInfoType {
    //征信信息类型： 1-详版征信，2-简版征信
    CREDIT_DETAIL(1,"详版征信"),
    CREDIT_SIMPLE(2,"简版征信");


    private Integer key;
    private String value;

    CreditInfoType(Integer key, String value) {
        this.key = key;
        this.value = value;
    }
    public Integer key() {
        return key;
    }

    public String value() {
        return value;
    }
}
