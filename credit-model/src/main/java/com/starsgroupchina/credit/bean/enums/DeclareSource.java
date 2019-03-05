package com.starsgroupchina.credit.bean.enums;

/**
 * @Author: QinHaoHao
 * @Description:
 * @Date: Created in 17:06 2018/8/16
 * @Modifed By:
 */
public enum DeclareSource {
    IMPORT(1, "范本导入"),
    DIRECT(2, "直接申请"),
    AUDIT(3, "审核申请"),
    RECHECK(4, "复核申请");
    private Short key;

    private String value;

    DeclareSource(Integer key, String value) {
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
