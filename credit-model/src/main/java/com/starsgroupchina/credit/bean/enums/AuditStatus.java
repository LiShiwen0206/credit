package com.starsgroupchina.credit.bean.enums;

/**
 * Created by zhangfeng on 2018-6-14.
 */
public enum AuditStatus {

    //审核状态： 1人工审核-未审核，2人工审核-已审核，3人工复审-未审核，4人工复审-已审核 , 100政策通过，101政策拒绝

    AUDIT_COMMIT_NO(0,"未提交"),

    AUDIT_LABOR_NO(1, "1人工审核-未审核"),

    AUDIT_LABOR_YES(2, "2人工审核-已审核"),

    AUDIT_RECHECK_LABOR_NO(3, "3人工复审-未审核"),

    AUDIT_RECHECK_LABOR_YES(4, "4人工复审-已审核"),

    AUDIT_AUTO(5,"自动审核"),

    AUDIT_AUTO_BLACKLIST(6,"自动审核-黑名单用户"),

    AUDIT_AUTO_THIRD(7,"自动审核-第三方数据验证未通过"),

    RISK_CUSTOMER(10,"风险客户"),

    POLICY_ALLOW(100, "政策通过"),

    POLICY_REJECT(101, "政策拒绝");

    private Short key;
    private String value;

    AuditStatus(Integer key, String value) {
        this.key = key.shortValue();
        this.value = value;
    }

    public Short key() {
        return key;
    }

    public String value() {
        return value;
    }

    public static String value(int key) {

        if (key == AUDIT_LABOR_NO.key)
            return AUDIT_LABOR_NO.value;
        if (key == AUDIT_LABOR_YES.key)
            return AUDIT_LABOR_YES.value;
        if (key == AUDIT_RECHECK_LABOR_NO.key)
            return AUDIT_RECHECK_LABOR_NO.value;
        if (key == AUDIT_RECHECK_LABOR_YES.key)
            return AUDIT_RECHECK_LABOR_YES.value;

        return AUDIT_LABOR_NO.value;
    }

}
