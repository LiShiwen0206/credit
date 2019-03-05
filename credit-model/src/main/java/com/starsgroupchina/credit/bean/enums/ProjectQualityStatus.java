package com.starsgroupchina.credit.bean.enums;

/** 
 * @Description: 
 * @author: gexiaoshan
 * @Date: 2018/8/22
 */ 
public enum ProjectQualityStatus {

    QUALITY_NOT(1, "未质检"),
    QUALITY_ING(2, "质检中"),
    QUALITY_ED(999, "已质检");

    private Short key;
    private String value;

    ProjectQualityStatus(Integer key, String value) {
        this.key = key.shortValue();
        this.value = value;
    }

    public Short key() {
        return key;
    }

    public String value() {
        return value;
    }

}
