package com.starsgroupchina.credit.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: QinHaoHao
 * @Description:
 * @Date: Created in 16:03 2018/7/11
 * @Modifed By:
 */
@Data
@ApiModel
public class CreditInfoModel {

    @ApiModelProperty("是否有值")
    private String isHaveValue="false";

    @ApiModelProperty("贷记卡账户数(贷记卡+准贷记卡的账户数)")
    private Integer accountSum;

    @ApiModelProperty("贷记卡总额度(贷记卡+准贷记卡的授信总额)")
    private Integer totalCredit;

    @ApiModelProperty("贷记卡剩余本金(贷记卡+准贷记卡的剩余本金)")
    private Integer residualMoney;

    @ApiModelProperty("贷记卡月供金额(贷记卡+准贷记卡的负债月供)")
    private Integer monthlySupply;

    @ApiModelProperty("贷记卡最长使用年限")
    private Integer creditUseYear;

    @ApiModelProperty("信用贷款笔数(贷款的账户数)")
    private Integer loanAccountSum;

    @ApiModelProperty("信用贷款总额度(贷款的授信总额)")
    private Integer loanTotalCredit;

    @ApiModelProperty("信用贷款剩余本金(贷款剩余本金)")
    private Integer loanResidualMoney;

    @ApiModelProperty("信用贷款月供金额(贷款负责月供)")
    private Integer loanMonthlySupply;

    @ApiModelProperty("贷款笔数(所有账户数)")
    private Integer allAccountSum;

    @ApiModelProperty("贷款总额(总计的授信总额)")
    private Integer allTotalCredit;

    @ApiModelProperty("剩余本金(总计的剩余本金)")
    private Integer allResidualMoney;

    @ApiModelProperty("月供金额(总计的负责月供)")
    private Integer allMonthlySupply;

    @ApiModelProperty("评估收入")
    private Integer assessmentIncome;

    @ApiModelProperty("DSR")
    private Double DSR;

    @ApiModelProperty("贷记卡负债比((贷记卡+准贷记卡的负债月供)/评估收入)")
    private Double monthPercent;

    @ApiModelProperty("信贷负债比((贷款的负债月供)/评估收入)")
    private Double loanMonthPercent;

    @ApiModelProperty("近1个月征信报告查询次数(近1个月（信用卡+贷款）查询次数)")
    private Integer oneMonthSum;

    @ApiModelProperty("近3个月征信报告查询次数(近3个月（信用卡+贷款）查询次数)")
    private Integer threeMonthSum;

    @ApiModelProperty("逾期账户数")
    private Integer overdueSum;

    @ApiModelProperty("五年内贷记卡逾期")
    private Integer fiveCardOverdue;

    @ApiModelProperty("五年内贷记卡91+")
    private Integer fiveCard91;

    @ApiModelProperty("五年内贷款逾期")
    private Integer fiveLoanOverdue;

    @ApiModelProperty("五年内贷款91+")
    private Integer fiveLoan91;

}
