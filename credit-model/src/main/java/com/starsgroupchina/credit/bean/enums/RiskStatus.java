package com.starsgroupchina.credit.bean.enums;

/**
  * @Author: QinHaoHao
  * @Description: 风险状态
  * @Date: Created in 15:21 2018/6/26
  * @Modifed By:
  */
public enum  RiskStatus {
    OK(0,"正常"),
    NO(1,"异常");
    private Short key;

    private String value;

    RiskStatus(Integer key, String value) {
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
