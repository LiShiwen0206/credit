package com.starsgroupchina.credit.bean.enums;

/**
 * @Author: QinHaoHao
 * @Description:
 * @Date: Created in 11:51 2018/8/17
 * @Modifed By:
 */
public enum FileUploadResource {
    AUDIT_FILE(0,"审核附件"),
    RISK_DECLARE(1, "风险客户申报"),
    RISK_INVESTIGATION(2, "风险客户调查"),
    QUALITY_AUDIT(3, "合规质检审核"),
    QUALITY_RECHECK(4,"合规质检复核");
    private Short key;

    private String value;

    FileUploadResource(Integer key, String value) {
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
