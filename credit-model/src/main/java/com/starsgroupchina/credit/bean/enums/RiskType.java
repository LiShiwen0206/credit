package com.starsgroupchina.credit.bean.enums;

/**
 * 风险点类型
 */
public enum  RiskType {
    //风险点来源：网查，电核，资料审查，关联报告
    RISK_WEBSITE("WEBSITE","网查"),
    RISK_PHONE("PHONE","电核"),
    RISK_FILE("FILE","资料审查"),
    RISK_RELEVANCE("RELEVANCE","关联报告");

    private String key;

    private String value;

     RiskType(String key, String value) {
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
