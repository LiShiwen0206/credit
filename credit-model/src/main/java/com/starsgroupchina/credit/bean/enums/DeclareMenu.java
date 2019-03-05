package com.starsgroupchina.credit.bean.enums;

/**
 * @Author: QinHaoHao
 * @Description:
 * @Date: Created in 17:43 2018/8/20
 * @Modifed By:
 */
public enum DeclareMenu {
    COMMIT_WAIT(0,"风险客户申报"),
    FIRST_ASSIGN(1, "一级待分配"),
    FIRST_INVESTIGATE(2, "一级调查"),
    TWO_INVESTIGATE(3, "二级调查"),
    OVER(4, "风险客户调查表");
    private Short key;

    private String value;

    DeclareMenu(Integer key, String value) {
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
