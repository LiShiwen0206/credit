package com.starsgroupchina.credit.bean.enums;

/**
 * @Author: QinHaoHao
 * @Description:
 * @Date: Created in 15:07 2018/11/12
 * @Modifed By:
 */
public enum RiskModelType {
    LOGISTI_REGRESSION(0,"逻辑回归"),
    DECISION_TREE(1,"决策数");
    private Short key;

    private String value;

    RiskModelType(Integer key, String value) {
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
