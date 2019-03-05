package com.starsgroupchina.credit.bean.enums;

/**
 * @Author: QinHaoHao
 * @Description:
 * @Date: Created in 17:25 2018/7/30
 * @Modifed By:
 */
public enum MessageType {
    UNREAD(0,"未读"),
    READ(1,"已读");
    private Short key;
    private String value;

    MessageType(Integer key,String value){
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
