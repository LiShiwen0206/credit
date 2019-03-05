package com.starsgroupchina.credit.bean.enums;

/**
 * @Author: QinHaoHao
 * @Description:
 * @Date: Created in 15:29 2018/9/5
 * @Modifed By:
 */
public enum AchievementType {

    ALL(0,"未提交"),

    AUDIT(1, "1人工审核-未审核"),

    RECHECK(2, "2人工审核-已审核");

    private Short key;
    private String value;

    AchievementType(Integer key, String value) {
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
