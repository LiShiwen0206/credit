package com.starsgroupchina.credit.bean.enums;

public enum ProjectStatus {

    //-1删除，1进件录入，2交叉质检，3进件查询，4自动审核，5审核件池，6人工审核，7复核件池，8人工复核
    //21交叉质检回退至进件录入，12交叉质检回退件重新提交，61人工审核回退至进件录入，16人工审核回退件重新提交，
    //86人工复核回退至人工审核，68人工复核退回件重新提交,100政策通过，101政策拒绝 ， 999信用报告

    CREDIT_REPORT(999, "信用报告"),

    DELETE(-1, "删除"),
    //进件录入
    ENTRY(1, "进件录入"),
    //交叉质检
    CROSS(2, "交叉互审"),
    //进件查询
    ENTRY_LIST(3, "进件查询"),

    //4自动审核
    AUDIT_AUTO(4, "自动审核"),
    //5审核件池
    AUDIT_LIST(5, "审核件池"),
    //6人工审核
    AUDIT_LABOR(6, "人工审核"),
    //7复核件池
    AUDIT_RECHECK_LIST(7, "复核件池"),
    //8人工复核
    AUDIT_RECHECK_LABOR(8, "人工复核"),
    //9审核列表查询
    AUDIT_LIST_QUERY(9, "审核列表查询"),

    //21交叉质检回退至进件录入
    CROSS_BACK_ENTRY(21, "交叉质检回退至进件录入"),
    //12交叉质检回退件重新提交
    ENTRY_RECOMMIT_CROSS(12, "交叉质检回退件重新提交"),
    //61人工审核回退至进件录入
    AUDIT_LABOR_BACK_ENTRY(61, "人工审核回退至进件录入"),
    //16人工审核回退件重新提交
    ENTRY_RECOMMIT_AUDIT_LABOR(16, "人工审核回退件重新提交"),
    //86人工复核回退至人工审核
    AUDIT_RECHECK_LABOR_BACK_AUDIT_LABOR(86, "人工复核回退至人工审核"),
    //68人工复核回退至人工审核
    AUDIT_LABOR_RECOMMIT_AUDIT_RECHECK_LABOR(68, "人工复核退回件重新提交");

    private Short key;
    private String value;

    ProjectStatus(Integer key, String value) {
        this.key = key.shortValue();
        this.value = value;
    }

//    public short shortKey() {
//        return key.shortValue();
//    }

    public Short key() {
        return key;
    }

    public String value() {
        return value;
    }

//    public static String value(Short key) {
//        if (key == DELETE.key)
//            return DELETE.value;
//
//        if (key == CREDIT_REPORT.key)
//            return CREDIT_REPORT.value;
//
////        if (key == POLICY_ALLOW.key)
////            return POLICY_ALLOW.value;
////        if (key == POLICY_REJECT.key)
////            return POLICY_REJECT.value;
//
//        if (key == ENTRY.key)
//            return ENTRY.value;
//        if (key == CROSS.key)
//            return CROSS.value;
//        if (key == ENTRY_LIST.key)
//            return ENTRY_LIST.value;
//
//        if (key == AUDIT_AUTO.key)
//            return AUDIT_AUTO.value;
//        if (key == AUDIT_LIST.key)
//            return AUDIT_LIST.value;
//        if (key == AUDIT_LABOR.key)
//            return AUDIT_LABOR.value;
//        if (key == AUDIT_RECHECK_LIST.key)
//            return AUDIT_RECHECK_LIST.value;
//        if (key == AUDIT_RECHECK_LABOR.key)
//            return AUDIT_RECHECK_LABOR.value;
//
//        if (key == CROSS_BACK_ENTRY.key)
//            return CROSS_BACK_ENTRY.value;
//        if (key == ENTRY_RECOMMIT_CROSS.key)
//            return ENTRY_RECOMMIT_CROSS.value;
//        if (key == AUDIT_LABOR_BACK_ENTRY.key)
//            return AUDIT_LABOR_BACK_ENTRY.value;
//        if (key == ENTRY_RECOMMIT_AUDIT_LABOR.key)
//            return ENTRY_RECOMMIT_AUDIT_LABOR.value;
//        if (key == AUDIT_RECHECK_LABOR_BACK_AUDIT_LABOR.key)
//            return AUDIT_RECHECK_LABOR_BACK_AUDIT_LABOR.value;
//        if (key == AUDIT_LABOR_RECOMMIT_AUDIT_RECHECK_LABOR.key)
//            return AUDIT_LABOR_RECOMMIT_AUDIT_RECHECK_LABOR.value;
//
//        return DELETE.value;
//    }
}
