package com.starsgroupchina.credit.bean.enums;

/**
 * @Author: QinHaoHao
 * @Description:
 * @Date: Created in 16:06 2018/7/11
 * @Modifed By:
 */
public enum ScoreType {
    CREDIT_SIMPE("简版征信","CS"),
    CREDIT_DETAIL("详版征信","CD"),
    THIRD_TD("同盾","TT"),
    THIRD_QH("前海","TQ");

    private String key;

    private String value;

    ScoreType(String key, String value) {
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
