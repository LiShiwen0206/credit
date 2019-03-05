package com.starsgroupchina.credit.bean.enums;

/**
 * @Author: QinHaoHao
 * @Description: 联系人类型，区分联系人，公司
 * @Date: Created in 17:24 2018/7/17
 * @Modifed By:
 */
public enum  ContactType {
    CONTACT(0,"联系人"),
    COMPANY(1,"公司");
    private Short key;

    private String value;

    ContactType(Integer key, String value) {
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
