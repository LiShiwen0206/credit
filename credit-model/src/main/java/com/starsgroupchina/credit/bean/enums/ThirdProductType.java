package com.starsgroupchina.credit.bean.enums;

/**
 * 第三方征信产品类别
 */
public enum ThirdProductType {
    //1、前海征信，2、前海征信，3、大蜂，4、同盾
    TYPE_QHZX8036(1,"前海征信"),
    TYPE_QHZX8107(2,"前海征信"),
    TYPE_DF(3,"大蜂"),
    TYPE_TD(4,"同盾");
    private Integer key;
    private String value;

    ThirdProductType(Integer key,String value){
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
        if (key == TYPE_QHZX8036.key)
            return TYPE_QHZX8036.value;
        if (key == TYPE_QHZX8107.key)
            return TYPE_QHZX8107.value;
        if (key == TYPE_DF.key)
            return TYPE_DF.value;
        if (key == TYPE_TD.key)
            return TYPE_DF.value;
        return TYPE_TD.value;
    }
}
