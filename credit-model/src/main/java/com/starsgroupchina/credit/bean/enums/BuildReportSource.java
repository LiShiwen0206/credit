package com.starsgroupchina.credit.bean.enums;

/**
 * @Author: QinHaoHao
 * @Description: 风控报告来源 1、正常结束产生风控报告 2、风险客户生成风控报告
 * @Date: Created in 15:15 2018/8/17
 * @Modifed By:
 */
public enum BuildReportSource {
    NORMAL(0,"正常流程生成风控报告"),
    RISK_CUSTOMER(1,"为风险客户生成风控报告"),
    BLACK_HIT(2,"黑名单命中风控报告"),
    THIRD_DATA_VALID_FAIL(3,"第三方数据验证未通过风控报告");
    private Short key;

    private String value;

    BuildReportSource(Integer key, String value) {
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
