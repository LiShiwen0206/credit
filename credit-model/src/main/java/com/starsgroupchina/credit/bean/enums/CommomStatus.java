package com.starsgroupchina.credit.bean.enums;

/**
 * @Author: QinHaoHao
 * @Description:
 * @Date: Created in 17:39 2018/7/30
 * @Modifed By:
 */
public enum  CommomStatus {

    DELETE(-1,"删除"),
    NORMAL(0,"正常"),
    DISABLE(1,"禁用");

    private Short key;
    private String value;

    CommomStatus(Integer key,String value){
        this.key = key.shortValue();
        this.value=value;
    }

    public Short key() {
        return key;
    }

    public String value() {
        return value;
    }
}
