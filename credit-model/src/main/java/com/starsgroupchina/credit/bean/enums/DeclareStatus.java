package com.starsgroupchina.credit.bean.enums;

/**
 * @Author: QinHaoHao
 * @Description:
 * @Date: Created in 17:12 2018/8/16
 * @Modifed By:
 */
public enum  DeclareStatus {

    DECLARE_COMMIT_NO(1,"待提交"),

    DECLARE_FIRST_ASSIGN(2, "一级待分配"),

    DECLARE_FIRST_HANDLE(3, "一级处理中"),

    FIRST_HANDLE_BACK_COMMIT(31, "一级处理退回到待提交"),

    FIRST_HANDLE_BACK_APPLY(30, "一级处理退回到复核审核申请阶段"),

    RECOMMIT_FIST_HANDLE(13,"待提交重新提交至一级处理中"),

    DECLARE_TWO_HANDLE(4,"二级处理中"),

    TWO_BACK_FIRST_HANDLE(43, "二级处理中回退到一级处理中"),

    FIRST_HANDLE_RECOMMIT_TWO_HANDLE(34, "一级处理中重新提交到二级处理中"),

    DECLARE_OVER(999, "处理完毕");

    private Short key;
    private String value;

    DeclareStatus(Integer key, String value) {
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
