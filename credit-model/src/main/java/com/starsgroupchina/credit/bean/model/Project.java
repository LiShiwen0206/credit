package com.starsgroupchina.credit.bean.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.starsgroupchina.common.base.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@ApiModel
public class Project extends BaseModel implements Serializable {
    @ApiModelProperty("id")
    private Integer id;

    @ApiModelProperty("项目编号")
    private String projectNo;

    @ApiModelProperty("机构ID")
    private Integer orgId;

    @ApiModelProperty("总公司(机构)ID")
    private Integer headOrgId;

    @ApiModelProperty("用户表ID")
    private Integer userId;

    @ApiModelProperty("产品ID")
    private Integer productId;

    @ApiModelProperty("政策id")
    private Integer policyId;

    @ApiModelProperty("模型ID")
    private Integer modelId;

    @ApiModelProperty("大类ID")
    private Integer categoryId;

    @ApiModelProperty("数据来源：区分自有系统，和 外部系统")
    private String source;

    @ApiModelProperty("外部系统进件编号")
    private String extProjectNo;

    @ApiModelProperty("申请贷款金额")
    private BigDecimal loanAmount;

    @ApiModelProperty("贷款期限")
    private Integer loanDuration;

    @ApiModelProperty("贷款期限（天）")
    private Integer loanDurationDay;

    @ApiModelProperty("贷款期限单位")
    private String loanUnit;

    @ApiModelProperty("贷款方式")
    private String loanMethod;

    @ApiModelProperty("贷款用途")
    private String loanUse;

    @ApiModelProperty("还款期间是否出省")
    private String loanOutProvince;

    @ApiModelProperty("可接受的最高月还款额")
    private BigDecimal loanMonthRepay;

    @ApiModelProperty("是否接受共同还款")
    private String loanCommonRepay;

    @ApiModelProperty("贷款账号")
    private String loanAccount;

    @ApiModelProperty("借款人银行账户户名")
    private String loanAccountName;

    @ApiModelProperty("开户行")
    private String loanAccountBank;

    @ApiModelProperty("审批贷款额度")
    private BigDecimal approveAmount;

    @ApiModelProperty("审批贷款期限")
    private BigDecimal approveLoanDuration;

    @ApiModelProperty("审批贷款期限单位")
    private String approveLoanUnit;

    @ApiModelProperty("审批贷款期限（天）")
    private Integer approveLoanDurationDay;

    @ApiModelProperty("审批意见")
    private String approveRemark;

    @ApiModelProperty("复核贷款额度")
    private BigDecimal auditRecheckAmount;

    @ApiModelProperty("复核贷款期限")
    private BigDecimal auditRecheckDuration;

    @ApiModelProperty("复核贷款期限单位")
    private String auditRecheckUnit;

    @ApiModelProperty("复核贷款期限天")
    private Integer auditRecheckDurationDay;

    @ApiModelProperty("复核意见")
    private String auditRecheckRemark;

    @ApiModelProperty("还款方式: 1等本等息")
    private Integer repayment;

    @ApiModelProperty("进入审核件池时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date beginAuditPoolDate;

    @ApiModelProperty("进入审核时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date beginAuditDate;

    @ApiModelProperty("审核结束时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date endAuditDate;

    @ApiModelProperty("复核开始时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date beginRecheckDate;

    @ApiModelProperty("复核结束时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date endRecheckDate;

    @ApiModelProperty("审核结束时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date auditOverTime;

    @ApiModelProperty("申请日期")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date applyDate;

    @ApiModelProperty("0未验证，1政策验证通过，2政策拒绝")
    private Short policyStatus;

    @ApiModelProperty("交叉互审人")
    private Integer auditCrossUserId;

    @ApiModelProperty("审核状态：0未提交， 1人工审核-未审核，2人工审核-已审核，3人工复审-未审核，4人工复审-已审核，100政策通过，101政策拒绝，")
    private Short auditStatus;

    @ApiModelProperty("审核人： 未指定或未拉取  则为空")
    private Integer auditUserId;

    @ApiModelProperty("复审人: 未指定或未拉取  则为空")
    private Integer auditRecheckUserId;

    @ApiModelProperty("质检状态（1、未质检[质检件池],2、质检中[质检抽查],999、已质检[质检报告]）")
    private Short qualityStatus;

    @ApiModelProperty("质检人id")
    private Integer qualityUserId;

    @ApiModelProperty("质检开始时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date beginQualityDate;

    @ApiModelProperty("质检结束时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date endQualityDate;

    @ApiModelProperty("-1删除，1进件录入，2交叉质检，3进件查询，4,自动审核，5审核件池，6人工审核，7复核件池，8人工复核，21交叉质检回退至进件录入，12交叉质检回退件重新提交，61人工审核回退至进件录入，16人工审核回退件重新提交，86人工复核回退至人工审核，68人工复核退回件重新提交，999信用报告")
    private Short status;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table project
     *
     * @mbg.generated Wed Dec 19 16:26:10 CST 2018
     */
    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProjectNo() {
        return projectNo;
    }

    public void setProjectNo(String projectNo) {
        this.projectNo = projectNo == null ? null : projectNo.trim();
    }

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    public Integer getHeadOrgId() {
        return headOrgId;
    }

    public void setHeadOrgId(Integer headOrgId) {
        this.headOrgId = headOrgId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getPolicyId() {
        return policyId;
    }

    public void setPolicyId(Integer policyId) {
        this.policyId = policyId;
    }

    public Integer getModelId() {
        return modelId;
    }

    public void setModelId(Integer modelId) {
        this.modelId = modelId;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source == null ? null : source.trim();
    }

    public String getExtProjectNo() {
        return extProjectNo;
    }

    public void setExtProjectNo(String extProjectNo) {
        this.extProjectNo = extProjectNo == null ? null : extProjectNo.trim();
    }

    public BigDecimal getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(BigDecimal loanAmount) {
        this.loanAmount = loanAmount;
    }

    public Integer getLoanDuration() {
        return loanDuration;
    }

    public void setLoanDuration(Integer loanDuration) {
        this.loanDuration = loanDuration;
    }

    public Integer getLoanDurationDay() {
        return loanDurationDay;
    }

    public void setLoanDurationDay(Integer loanDurationDay) {
        this.loanDurationDay = loanDurationDay;
    }

    public String getLoanUnit() {
        return loanUnit;
    }

    public void setLoanUnit(String loanUnit) {
        this.loanUnit = loanUnit == null ? null : loanUnit.trim();
    }

    public String getLoanMethod() {
        return loanMethod;
    }

    public void setLoanMethod(String loanMethod) {
        this.loanMethod = loanMethod == null ? null : loanMethod.trim();
    }

    public String getLoanUse() {
        return loanUse;
    }

    public void setLoanUse(String loanUse) {
        this.loanUse = loanUse == null ? null : loanUse.trim();
    }

    public String getLoanOutProvince() {
        return loanOutProvince;
    }

    public void setLoanOutProvince(String loanOutProvince) {
        this.loanOutProvince = loanOutProvince == null ? null : loanOutProvince.trim();
    }

    public BigDecimal getLoanMonthRepay() {
        return loanMonthRepay;
    }

    public void setLoanMonthRepay(BigDecimal loanMonthRepay) {
        this.loanMonthRepay = loanMonthRepay;
    }

    public String getLoanCommonRepay() {
        return loanCommonRepay;
    }

    public void setLoanCommonRepay(String loanCommonRepay) {
        this.loanCommonRepay = loanCommonRepay == null ? null : loanCommonRepay.trim();
    }

    public String getLoanAccount() {
        return loanAccount;
    }

    public void setLoanAccount(String loanAccount) {
        this.loanAccount = loanAccount == null ? null : loanAccount.trim();
    }

    public String getLoanAccountName() {
        return loanAccountName;
    }

    public void setLoanAccountName(String loanAccountName) {
        this.loanAccountName = loanAccountName == null ? null : loanAccountName.trim();
    }

    public String getLoanAccountBank() {
        return loanAccountBank;
    }

    public void setLoanAccountBank(String loanAccountBank) {
        this.loanAccountBank = loanAccountBank == null ? null : loanAccountBank.trim();
    }

    public BigDecimal getApproveAmount() {
        return approveAmount;
    }

    public void setApproveAmount(BigDecimal approveAmount) {
        this.approveAmount = approveAmount;
    }

    public BigDecimal getApproveLoanDuration() {
        return approveLoanDuration;
    }

    public void setApproveLoanDuration(BigDecimal approveLoanDuration) {
        this.approveLoanDuration = approveLoanDuration;
    }

    public String getApproveLoanUnit() {
        return approveLoanUnit;
    }

    public void setApproveLoanUnit(String approveLoanUnit) {
        this.approveLoanUnit = approveLoanUnit == null ? null : approveLoanUnit.trim();
    }

    public Integer getApproveLoanDurationDay() {
        return approveLoanDurationDay;
    }

    public void setApproveLoanDurationDay(Integer approveLoanDurationDay) {
        this.approveLoanDurationDay = approveLoanDurationDay;
    }

    public String getApproveRemark() {
        return approveRemark;
    }

    public void setApproveRemark(String approveRemark) {
        this.approveRemark = approveRemark == null ? null : approveRemark.trim();
    }

    public BigDecimal getAuditRecheckAmount() {
        return auditRecheckAmount;
    }

    public void setAuditRecheckAmount(BigDecimal auditRecheckAmount) {
        this.auditRecheckAmount = auditRecheckAmount;
    }

    public BigDecimal getAuditRecheckDuration() {
        return auditRecheckDuration;
    }

    public void setAuditRecheckDuration(BigDecimal auditRecheckDuration) {
        this.auditRecheckDuration = auditRecheckDuration;
    }

    public String getAuditRecheckUnit() {
        return auditRecheckUnit;
    }

    public void setAuditRecheckUnit(String auditRecheckUnit) {
        this.auditRecheckUnit = auditRecheckUnit == null ? null : auditRecheckUnit.trim();
    }

    public Integer getAuditRecheckDurationDay() {
        return auditRecheckDurationDay;
    }

    public void setAuditRecheckDurationDay(Integer auditRecheckDurationDay) {
        this.auditRecheckDurationDay = auditRecheckDurationDay;
    }

    public String getAuditRecheckRemark() {
        return auditRecheckRemark;
    }

    public void setAuditRecheckRemark(String auditRecheckRemark) {
        this.auditRecheckRemark = auditRecheckRemark == null ? null : auditRecheckRemark.trim();
    }

    public Integer getRepayment() {
        return repayment;
    }

    public void setRepayment(Integer repayment) {
        this.repayment = repayment;
    }

    public Date getBeginAuditPoolDate() {
        return beginAuditPoolDate;
    }

    public void setBeginAuditPoolDate(Date beginAuditPoolDate) {
        this.beginAuditPoolDate = beginAuditPoolDate;
    }

    public Date getBeginAuditDate() {
        return beginAuditDate;
    }

    public void setBeginAuditDate(Date beginAuditDate) {
        this.beginAuditDate = beginAuditDate;
    }

    public Date getEndAuditDate() {
        return endAuditDate;
    }

    public void setEndAuditDate(Date endAuditDate) {
        this.endAuditDate = endAuditDate;
    }

    public Date getBeginRecheckDate() {
        return beginRecheckDate;
    }

    public void setBeginRecheckDate(Date beginRecheckDate) {
        this.beginRecheckDate = beginRecheckDate;
    }

    public Date getEndRecheckDate() {
        return endRecheckDate;
    }

    public void setEndRecheckDate(Date endRecheckDate) {
        this.endRecheckDate = endRecheckDate;
    }

    public Date getAuditOverTime() {
        return auditOverTime;
    }

    public void setAuditOverTime(Date auditOverTime) {
        this.auditOverTime = auditOverTime;
    }

    public Date getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(Date applyDate) {
        this.applyDate = applyDate;
    }

    public Short getPolicyStatus() {
        return policyStatus;
    }

    public void setPolicyStatus(Short policyStatus) {
        this.policyStatus = policyStatus;
    }

    public Integer getAuditCrossUserId() {
        return auditCrossUserId;
    }

    public void setAuditCrossUserId(Integer auditCrossUserId) {
        this.auditCrossUserId = auditCrossUserId;
    }

    public Short getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(Short auditStatus) {
        this.auditStatus = auditStatus;
    }

    public Integer getAuditUserId() {
        return auditUserId;
    }

    public void setAuditUserId(Integer auditUserId) {
        this.auditUserId = auditUserId;
    }

    public Integer getAuditRecheckUserId() {
        return auditRecheckUserId;
    }

    public void setAuditRecheckUserId(Integer auditRecheckUserId) {
        this.auditRecheckUserId = auditRecheckUserId;
    }

    public Short getQualityStatus() {
        return qualityStatus;
    }

    public void setQualityStatus(Short qualityStatus) {
        this.qualityStatus = qualityStatus;
    }

    public Integer getQualityUserId() {
        return qualityUserId;
    }

    public void setQualityUserId(Integer qualityUserId) {
        this.qualityUserId = qualityUserId;
    }

    public Date getBeginQualityDate() {
        return beginQualityDate;
    }

    public void setBeginQualityDate(Date beginQualityDate) {
        this.beginQualityDate = beginQualityDate;
    }

    public Date getEndQualityDate() {
        return endQualityDate;
    }

    public void setEndQualityDate(Date endQualityDate) {
        this.endQualityDate = endQualityDate;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table project
     *
     * @mbg.generated Wed Dec 19 16:26:10 CST 2018
     */
    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        Project other = (Project) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getProjectNo() == null ? other.getProjectNo() == null : this.getProjectNo().equals(other.getProjectNo()))
            && (this.getOrgId() == null ? other.getOrgId() == null : this.getOrgId().equals(other.getOrgId()))
            && (this.getHeadOrgId() == null ? other.getHeadOrgId() == null : this.getHeadOrgId().equals(other.getHeadOrgId()))
            && (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
            && (this.getProductId() == null ? other.getProductId() == null : this.getProductId().equals(other.getProductId()))
            && (this.getPolicyId() == null ? other.getPolicyId() == null : this.getPolicyId().equals(other.getPolicyId()))
            && (this.getModelId() == null ? other.getModelId() == null : this.getModelId().equals(other.getModelId()))
            && (this.getCategoryId() == null ? other.getCategoryId() == null : this.getCategoryId().equals(other.getCategoryId()))
            && (this.getSource() == null ? other.getSource() == null : this.getSource().equals(other.getSource()))
            && (this.getExtProjectNo() == null ? other.getExtProjectNo() == null : this.getExtProjectNo().equals(other.getExtProjectNo()))
            && (this.getLoanAmount() == null ? other.getLoanAmount() == null : this.getLoanAmount().equals(other.getLoanAmount()))
            && (this.getLoanDuration() == null ? other.getLoanDuration() == null : this.getLoanDuration().equals(other.getLoanDuration()))
            && (this.getLoanDurationDay() == null ? other.getLoanDurationDay() == null : this.getLoanDurationDay().equals(other.getLoanDurationDay()))
            && (this.getLoanUnit() == null ? other.getLoanUnit() == null : this.getLoanUnit().equals(other.getLoanUnit()))
            && (this.getLoanMethod() == null ? other.getLoanMethod() == null : this.getLoanMethod().equals(other.getLoanMethod()))
            && (this.getLoanUse() == null ? other.getLoanUse() == null : this.getLoanUse().equals(other.getLoanUse()))
            && (this.getLoanOutProvince() == null ? other.getLoanOutProvince() == null : this.getLoanOutProvince().equals(other.getLoanOutProvince()))
            && (this.getLoanMonthRepay() == null ? other.getLoanMonthRepay() == null : this.getLoanMonthRepay().equals(other.getLoanMonthRepay()))
            && (this.getLoanCommonRepay() == null ? other.getLoanCommonRepay() == null : this.getLoanCommonRepay().equals(other.getLoanCommonRepay()))
            && (this.getLoanAccount() == null ? other.getLoanAccount() == null : this.getLoanAccount().equals(other.getLoanAccount()))
            && (this.getLoanAccountName() == null ? other.getLoanAccountName() == null : this.getLoanAccountName().equals(other.getLoanAccountName()))
            && (this.getLoanAccountBank() == null ? other.getLoanAccountBank() == null : this.getLoanAccountBank().equals(other.getLoanAccountBank()))
            && (this.getApproveAmount() == null ? other.getApproveAmount() == null : this.getApproveAmount().equals(other.getApproveAmount()))
            && (this.getApproveLoanDuration() == null ? other.getApproveLoanDuration() == null : this.getApproveLoanDuration().equals(other.getApproveLoanDuration()))
            && (this.getApproveLoanUnit() == null ? other.getApproveLoanUnit() == null : this.getApproveLoanUnit().equals(other.getApproveLoanUnit()))
            && (this.getApproveLoanDurationDay() == null ? other.getApproveLoanDurationDay() == null : this.getApproveLoanDurationDay().equals(other.getApproveLoanDurationDay()))
            && (this.getApproveRemark() == null ? other.getApproveRemark() == null : this.getApproveRemark().equals(other.getApproveRemark()))
            && (this.getAuditRecheckAmount() == null ? other.getAuditRecheckAmount() == null : this.getAuditRecheckAmount().equals(other.getAuditRecheckAmount()))
            && (this.getAuditRecheckDuration() == null ? other.getAuditRecheckDuration() == null : this.getAuditRecheckDuration().equals(other.getAuditRecheckDuration()))
            && (this.getAuditRecheckUnit() == null ? other.getAuditRecheckUnit() == null : this.getAuditRecheckUnit().equals(other.getAuditRecheckUnit()))
            && (this.getAuditRecheckDurationDay() == null ? other.getAuditRecheckDurationDay() == null : this.getAuditRecheckDurationDay().equals(other.getAuditRecheckDurationDay()))
            && (this.getAuditRecheckRemark() == null ? other.getAuditRecheckRemark() == null : this.getAuditRecheckRemark().equals(other.getAuditRecheckRemark()))
            && (this.getRepayment() == null ? other.getRepayment() == null : this.getRepayment().equals(other.getRepayment()))
            && (this.getBeginAuditPoolDate() == null ? other.getBeginAuditPoolDate() == null : this.getBeginAuditPoolDate().equals(other.getBeginAuditPoolDate()))
            && (this.getBeginAuditDate() == null ? other.getBeginAuditDate() == null : this.getBeginAuditDate().equals(other.getBeginAuditDate()))
            && (this.getEndAuditDate() == null ? other.getEndAuditDate() == null : this.getEndAuditDate().equals(other.getEndAuditDate()))
            && (this.getBeginRecheckDate() == null ? other.getBeginRecheckDate() == null : this.getBeginRecheckDate().equals(other.getBeginRecheckDate()))
            && (this.getEndRecheckDate() == null ? other.getEndRecheckDate() == null : this.getEndRecheckDate().equals(other.getEndRecheckDate()))
            && (this.getAuditOverTime() == null ? other.getAuditOverTime() == null : this.getAuditOverTime().equals(other.getAuditOverTime()))
            && (this.getApplyDate() == null ? other.getApplyDate() == null : this.getApplyDate().equals(other.getApplyDate()))
            && (this.getPolicyStatus() == null ? other.getPolicyStatus() == null : this.getPolicyStatus().equals(other.getPolicyStatus()))
            && (this.getAuditCrossUserId() == null ? other.getAuditCrossUserId() == null : this.getAuditCrossUserId().equals(other.getAuditCrossUserId()))
            && (this.getAuditStatus() == null ? other.getAuditStatus() == null : this.getAuditStatus().equals(other.getAuditStatus()))
            && (this.getAuditUserId() == null ? other.getAuditUserId() == null : this.getAuditUserId().equals(other.getAuditUserId()))
            && (this.getAuditRecheckUserId() == null ? other.getAuditRecheckUserId() == null : this.getAuditRecheckUserId().equals(other.getAuditRecheckUserId()))
            && (this.getQualityStatus() == null ? other.getQualityStatus() == null : this.getQualityStatus().equals(other.getQualityStatus()))
            && (this.getQualityUserId() == null ? other.getQualityUserId() == null : this.getQualityUserId().equals(other.getQualityUserId()))
            && (this.getBeginQualityDate() == null ? other.getBeginQualityDate() == null : this.getBeginQualityDate().equals(other.getBeginQualityDate()))
            && (this.getEndQualityDate() == null ? other.getEndQualityDate() == null : this.getEndQualityDate().equals(other.getEndQualityDate()))
            && (this.getCreateUser() == null ? other.getCreateUser() == null : this.getCreateUser().equals(other.getCreateUser()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getCreateUserId() == null ? other.getCreateUserId() == null : this.getCreateUserId().equals(other.getCreateUserId()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getModifyUser() == null ? other.getModifyUser() == null : this.getModifyUser().equals(other.getModifyUser()))
            && (this.getModifyUserId() == null ? other.getModifyUserId() == null : this.getModifyUserId().equals(other.getModifyUserId()))
            && (this.getModifyTime() == null ? other.getModifyTime() == null : this.getModifyTime().equals(other.getModifyTime()));
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table project
     *
     * @mbg.generated Wed Dec 19 16:26:10 CST 2018
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getProjectNo() == null) ? 0 : getProjectNo().hashCode());
        result = prime * result + ((getOrgId() == null) ? 0 : getOrgId().hashCode());
        result = prime * result + ((getHeadOrgId() == null) ? 0 : getHeadOrgId().hashCode());
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getProductId() == null) ? 0 : getProductId().hashCode());
        result = prime * result + ((getPolicyId() == null) ? 0 : getPolicyId().hashCode());
        result = prime * result + ((getModelId() == null) ? 0 : getModelId().hashCode());
        result = prime * result + ((getCategoryId() == null) ? 0 : getCategoryId().hashCode());
        result = prime * result + ((getSource() == null) ? 0 : getSource().hashCode());
        result = prime * result + ((getExtProjectNo() == null) ? 0 : getExtProjectNo().hashCode());
        result = prime * result + ((getLoanAmount() == null) ? 0 : getLoanAmount().hashCode());
        result = prime * result + ((getLoanDuration() == null) ? 0 : getLoanDuration().hashCode());
        result = prime * result + ((getLoanDurationDay() == null) ? 0 : getLoanDurationDay().hashCode());
        result = prime * result + ((getLoanUnit() == null) ? 0 : getLoanUnit().hashCode());
        result = prime * result + ((getLoanMethod() == null) ? 0 : getLoanMethod().hashCode());
        result = prime * result + ((getLoanUse() == null) ? 0 : getLoanUse().hashCode());
        result = prime * result + ((getLoanOutProvince() == null) ? 0 : getLoanOutProvince().hashCode());
        result = prime * result + ((getLoanMonthRepay() == null) ? 0 : getLoanMonthRepay().hashCode());
        result = prime * result + ((getLoanCommonRepay() == null) ? 0 : getLoanCommonRepay().hashCode());
        result = prime * result + ((getLoanAccount() == null) ? 0 : getLoanAccount().hashCode());
        result = prime * result + ((getLoanAccountName() == null) ? 0 : getLoanAccountName().hashCode());
        result = prime * result + ((getLoanAccountBank() == null) ? 0 : getLoanAccountBank().hashCode());
        result = prime * result + ((getApproveAmount() == null) ? 0 : getApproveAmount().hashCode());
        result = prime * result + ((getApproveLoanDuration() == null) ? 0 : getApproveLoanDuration().hashCode());
        result = prime * result + ((getApproveLoanUnit() == null) ? 0 : getApproveLoanUnit().hashCode());
        result = prime * result + ((getApproveLoanDurationDay() == null) ? 0 : getApproveLoanDurationDay().hashCode());
        result = prime * result + ((getApproveRemark() == null) ? 0 : getApproveRemark().hashCode());
        result = prime * result + ((getAuditRecheckAmount() == null) ? 0 : getAuditRecheckAmount().hashCode());
        result = prime * result + ((getAuditRecheckDuration() == null) ? 0 : getAuditRecheckDuration().hashCode());
        result = prime * result + ((getAuditRecheckUnit() == null) ? 0 : getAuditRecheckUnit().hashCode());
        result = prime * result + ((getAuditRecheckDurationDay() == null) ? 0 : getAuditRecheckDurationDay().hashCode());
        result = prime * result + ((getAuditRecheckRemark() == null) ? 0 : getAuditRecheckRemark().hashCode());
        result = prime * result + ((getRepayment() == null) ? 0 : getRepayment().hashCode());
        result = prime * result + ((getBeginAuditPoolDate() == null) ? 0 : getBeginAuditPoolDate().hashCode());
        result = prime * result + ((getBeginAuditDate() == null) ? 0 : getBeginAuditDate().hashCode());
        result = prime * result + ((getEndAuditDate() == null) ? 0 : getEndAuditDate().hashCode());
        result = prime * result + ((getBeginRecheckDate() == null) ? 0 : getBeginRecheckDate().hashCode());
        result = prime * result + ((getEndRecheckDate() == null) ? 0 : getEndRecheckDate().hashCode());
        result = prime * result + ((getAuditOverTime() == null) ? 0 : getAuditOverTime().hashCode());
        result = prime * result + ((getApplyDate() == null) ? 0 : getApplyDate().hashCode());
        result = prime * result + ((getPolicyStatus() == null) ? 0 : getPolicyStatus().hashCode());
        result = prime * result + ((getAuditCrossUserId() == null) ? 0 : getAuditCrossUserId().hashCode());
        result = prime * result + ((getAuditStatus() == null) ? 0 : getAuditStatus().hashCode());
        result = prime * result + ((getAuditUserId() == null) ? 0 : getAuditUserId().hashCode());
        result = prime * result + ((getAuditRecheckUserId() == null) ? 0 : getAuditRecheckUserId().hashCode());
        result = prime * result + ((getQualityStatus() == null) ? 0 : getQualityStatus().hashCode());
        result = prime * result + ((getQualityUserId() == null) ? 0 : getQualityUserId().hashCode());
        result = prime * result + ((getBeginQualityDate() == null) ? 0 : getBeginQualityDate().hashCode());
        result = prime * result + ((getEndQualityDate() == null) ? 0 : getEndQualityDate().hashCode());
        result = prime * result + ((getCreateUser() == null) ? 0 : getCreateUser().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getCreateUserId() == null) ? 0 : getCreateUserId().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getModifyUser() == null) ? 0 : getModifyUser().hashCode());
        result = prime * result + ((getModifyUserId() == null) ? 0 : getModifyUserId().hashCode());
        result = prime * result + ((getModifyTime() == null) ? 0 : getModifyTime().hashCode());
        return result;
    }
}