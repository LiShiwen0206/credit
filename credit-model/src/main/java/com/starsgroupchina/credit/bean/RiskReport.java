package com.starsgroupchina.credit.bean;

import com.starsgroupchina.credit.bean.extend.ProductExtend;
import com.starsgroupchina.credit.bean.extend.RelationBlacklistExtend;
import com.starsgroupchina.credit.bean.model.OrgReportConfig;
import com.starsgroupchina.credit.bean.model.ProjectRelation;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @Author: QinHaoHao
 * @Description: 风控报告
 * @Date: Created in 10:53 2018/7/3
 * @Modifed By:
 */
@ApiModel
@Data
public class RiskReport {

    @ApiModelProperty("报表样式配置")
    private OrgReportConfig orgReportConfig;

    @ApiModelProperty("是否风险客户：0、正常，1、风险客户")
    private Integer isRiskCustomer;

    @ApiModelProperty("是否黑名单客户：0、正常，1、风险客户")
    private Integer isBlackCustomer;

    @ApiModelProperty("是否第三方数据验证失败客户：0、正常，1、风险客户")
    private Integer isThirdDataValidFail;
    @ApiModelProperty("产品")
    private ProductExtend product;

    @ApiModelProperty("授信结果")
    private CreditResult creditResult;

    @ApiModelProperty("客户基本信息")
    private UserInfo customerBase;

    @ApiModelProperty("申请数据核查")
    private List<RiskItem> checkData;

    @ApiModelProperty("网查：法院网")
    private List<RiskItem> courtNetwork;

    @ApiModelProperty("网查：失信网")
    private List<RiskItem> discreditNetwork;

    @ApiModelProperty("网查：贷联盟")
    private List<RiskItem> loanAlliance;

    @ApiModelProperty("网查：百度网")
    private List<RiskItem> baiDu;

    @ApiModelProperty("网查：支付宝")
    private List<RiskItem> aliPay;

    @ApiModelProperty("黑名单查询")
    private List<RelationBlacklistExtend> blackQuery;

    @ApiModelProperty("工商信息查询")
    private List<RiskItem> busInfoQuery;

    @ApiModelProperty("地址核查")
    private List<RiskItem> checkAddress;

    @ApiModelProperty("关联报告核查")
    private List<ProjectRelation> associationReport;

    @ApiModelProperty("征信信息核查")
    private List<RiskItem> checkCredit;

    @ApiModelProperty("电话核查")
    private List<RiskItem> checkPhone;

    @ApiModelProperty("负债及收入")
    private List<RiskItem> income;

    @ApiModelProperty("社保公积金核查")
    private List<RiskItem> socialSecurity;

    @ApiModelProperty("保单信息核查")
    private List<RiskItem> checkPolicy;

    @ApiModelProperty("房产信息核查")
    private List<RiskItem> checkHouse;

    @ApiModelProperty("车辆信息核查")
    private List<RiskItem> checkCar;

    @ApiModel
    @Data
    public static class RiskItem {

        @ApiModelProperty("检测项目")
        private String testProject;

        @ApiModelProperty("检测值")
        private String testData;

        @ApiModelProperty("结果：0、正常，1、异常")
        private Integer testResult;

        @ApiModelProperty("结果：电核情况下使用")
        private String testResult1;

        @ApiModelProperty("备注")
        private String remark;

    }

    @ApiModel
    @Data
    public static class CreditResult {

        @ApiModelProperty("授信评分")
        private Double creditScore;

        @ApiModelProperty("模型打分")
        private Double modelScore;

        @ApiModelProperty("模型打分基础分")
        private Double baseModelScore = 0.0;

        @ApiModelProperty("申请额度:元")
        private Double loanAmount;

        @ApiModelProperty("审批额度:元")
        private Double approveAmount;

        @ApiModelProperty("申请贷款期限")
        private Integer loanDurationDay;

        @ApiModelProperty("审批期限:天")
        private Integer approveLoanDurationDay;
    }
}
