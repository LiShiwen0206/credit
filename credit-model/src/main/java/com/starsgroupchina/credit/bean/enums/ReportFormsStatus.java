package com.starsgroupchina.credit.bean.enums;

/**
 * Created by gexiaoshan on 2018/9/3.
 */
public enum ReportFormsStatus {

    EXPORT_ING(0,"生成中"),
    EXPORT_SUCCESS(1,"生成成功"),
    EXPORT_FAIL(2,"生成失败");

    private Short key;

    private String value;

    ReportFormsStatus(Integer key, String value) {
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
