package com.starsgroupchina.credit.bean.enums;


/**
  * @Author: QinHaoHao
  * @Description: 文件枚举
  * @Date: Created in 15:21 2018/6/26 
  * @Modifed By:
  */
public enum  FileType {

    //用于注释审核附件
    AUDIT_FILE(-1000,"审核附件"),

    OTHER_FILE(-999,"其他"),

    DECLARE_FILE(-800,"反欺诈申报资料"),

    INVESTIGATION_FILEA(-801,"反欺诈调查资料"),

    QUALITY_AUDIT_FILE(-700,"质检抽查审核资料"),

    QUALITY_RECHECK_FILE(-701,"质检抽查复核资料");

    private Integer key;

    private String value;

    FileType(Integer key, String value) {
        this.key = key;
        this.value = value;
    }

    public Integer getKey() {
        return key;
    }

    public void setKey(Integer key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
