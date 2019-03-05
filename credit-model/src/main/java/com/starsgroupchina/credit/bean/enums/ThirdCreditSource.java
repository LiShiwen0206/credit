package com.starsgroupchina.credit.bean.enums;

/**
 * 第三方征信数据来源
 */
public enum ThirdCreditSource {

    //1、同盾，2、大峰，3、前海征信
    SOURCE_TD(1,"同盾"),
    SOURCE_DF(2,"大蜂"),
    SOURCE_QHZX(3,"前海征信");
    private Integer key;
    private String value;

    ThirdCreditSource(Integer key,String value){
        this.key = key;
        this.value=value;
    }

    public Integer key() {
        return key;
    }

    public String value() {
        return value;
    }

    public static String value(int key) {
        if (key == SOURCE_TD.key)
            return SOURCE_TD.value;
        if (key == SOURCE_DF.key)
            return SOURCE_DF.value;
        if (key == SOURCE_QHZX.key)
            return SOURCE_QHZX.value;
        return SOURCE_TD.value;
    }
}
