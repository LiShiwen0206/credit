package com.starsgroupchina.credit.bean.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.starsgroupchina.common.base.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@ApiModel
public class VReportAudit extends BaseModel implements Serializable {
    @ApiModelProperty("项目编号")
    private String projectNo;

    @ApiModelProperty("个人信息-姓名")
    private String b001;

    @ApiModelProperty("个人信息-证件号码")
    private String b005;

    @ApiModelProperty("机构名称")
    private String orgName;

    @ApiModelProperty("产品名称")
    private String title;

    @ApiModelProperty("名称")
    private String categoryName;

    @ApiModelProperty("个人信息-性别")
    private String b002;

    @ApiModelProperty("个人信息-年龄")
    private String b003;

    @ApiModelProperty("个人信息-手机号码")
    private String b016;

    @ApiModelProperty("个人信息-居住类型")
    private String b027;

    @ApiModelProperty("个人信息-最高学历")
    private String b014;

    @ApiModelProperty("个人信息-婚姻状况")
    private String b017;

    @ApiModelProperty("个人信息-供养亲属数")
    private String b021;

    @ApiModelProperty("个人信息-现家庭地址")
    private String b022;

    @ApiModelProperty("个人信息-户籍地址")
    private String b010;

    @ApiModelProperty("个人信息-每月家庭支出")
    private String b032;

    @ApiModelProperty("个人信息-共同居住成员")
    private String b029;

    @ApiModelProperty("单位信息-工作性质")
    private String c012;

    @ApiModelProperty("单位信息-单位全称")
    private String c001;

    @ApiModelProperty("单位信息-单位电话")
    private String c005;

    @ApiModelProperty("单位信息-单位地址")
    private String c002;

    @ApiModelProperty("单位信息-现单位入职时间")
    private String c013;

    @ApiModelProperty("单位信息-单位性质")
    private String c007;

    @ApiModelProperty("单位信息-任职部门")
    private String c009;

    @ApiModelProperty("单位信息-职务")
    private String c010;

    @ApiModelProperty("单位信息-行业类型")
    private String c008;

    @ApiModelProperty("单位信息-月收入")
    private String c014;

    @ApiModelProperty("单位信息-发薪方式")
    private String c016;

    @ApiModelProperty("单位信息-发薪日")
    private String c015;

    @ApiModelProperty("工商信息-注册时间")
    private String d002;

    @ApiModelProperty("工商信息-月营业额")
    private String d010;

    @ApiModelProperty("工商信息-毛利率")
    private String d011;

    @ApiModelProperty("工商信息-每月净利润额")
    private String d013;

    @ApiModelProperty("工商信息-员工人数")
    private String d015;

    @ApiModelProperty("工商信息-占股比例")
    private String d016;

    @ApiModelProperty("工商信息-租金（元/月）")
    private String d019;

    @ApiModelProperty("工商信息-出租方电话")
    private String d018;

    @ApiModelProperty("社保信息-参保单位")
    private String k002;

    @ApiModelProperty("社保信息-现单位缴纳起始日")
    private String k004;

    @ApiModelProperty("社保信息-社保缴纳基数")
    private String k005;

    @ApiModelProperty("社保信息-公积金缴纳基数")
    private String k006;

    @ApiModelProperty("社保信息-社保累计缴纳月数")
    private String k007;

    @ApiModelProperty("房产信息-房产地址")
    private String e002;

    @ApiModelProperty("房产信息-购买方式")
    private String e010;

    @ApiModelProperty("房产信息-产权比例")
    private String e007;

    @ApiModelProperty("房产信息-建筑面积")
    private String e015;

    @ApiModelProperty("房产信息-贷款金额")
    private String e019;

    @ApiModelProperty("房产信息-贷款期限")
    private String e020;

    @ApiModelProperty("房产信息-月还款额")
    private String e021;

    @ApiModelProperty("房产信息-其他房产贷款金额")
    private String e040;

    @ApiModelProperty("房产信息-其他房产月还款额")
    private String e042;

    @ApiModelProperty("车辆信息-品牌型号")
    private String g002;

    @ApiModelProperty("车辆信息-贷款金额")
    private String g013;

    @ApiModelProperty("车辆信息-月还款额")
    private String g016;

    @ApiModelProperty("负债信息-信用卡总额")
    private String f0012;

    @ApiModelProperty("负债信息-信用卡张数")
    private String f001;

    @ApiModelProperty("负债信息-信用卡当前欠款")
    private String f002;

    @ApiModelProperty("负债信息-贷款总额度")
    private String f009;

    @ApiModelProperty("负债信息-贷款笔数")
    private String f010;

    @ApiModelProperty("负债信息-贷款欠款")
    private String f011;

    @ApiModelProperty("保单信息-保险公司")
    private String l001;

    @ApiModelProperty("保单信息-险种名称")
    private String l003;

    @ApiModelProperty("保单信息-缴费方式")
    private String l009;

    @ApiModelProperty("保单信息-约定缴费年限")
    private String l004;

    @ApiModelProperty("保单信息-缴费金额")
    private String l007;

    @ApiModelProperty("保单信息-已缴期数")
    private String l018;

    @ApiModelProperty("腾讯/阿里/京东-花呗/借呗授信金额")
    private String s002;

    @ApiModelProperty("腾讯/阿里/京东-微粒贷授信金额")
    private String s001;

    @ApiModelProperty("腾讯/阿里/京东-白条/金条授信金额")
    private String s003;

    @ApiModelProperty("腾讯/阿里/京东-借款金额")
    private String s004;

    @ApiModelProperty("腾讯/阿里/京东-借款期限")
    private String s005;

    @ApiModelProperty("腾讯/阿里/京东-月还款额")
    private String s006;

    @ApiModelProperty("腾讯/阿里/京东-已还款期数")
    private String s007;

    @ApiModelProperty("腾讯/阿里/京东-已还款金额")
    private String s008;

    @ApiModelProperty("进入审核时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date beginAuditDate;

    @ApiModelProperty("审核结束时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date endAuditDate;

    @ApiModelProperty("复核结束时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date endRecheckDate;

    @ApiModelProperty("审批贷款额度")
    private BigDecimal approveAmount;

    @ApiModelProperty("审批意见")
    private String approveRemark;

    @ApiModelProperty("复核贷款额度")
    private BigDecimal auditRecheckAmount;

    @ApiModelProperty("复核贷款期限")
    private BigDecimal auditRecheckDuration;

    @ApiModelProperty("复核意见")
    private String auditRecheckRemark;

    @ApiModelProperty("审核结束时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date auditOverTime;

    @ApiModelProperty("申请日期")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date applyDate;

    @ApiModelProperty("征信信息表单")
    private String creditDetail;

    @ApiModelProperty("征信信息表单")
    private String creditSimple;

    @ApiModelProperty("")
    private String approveresult;

    @ApiModelProperty("")
    private String auditrecheckresult;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table v_report_audit
     *
     * @mbg.generated Wed Dec 19 16:26:10 CST 2018
     */
    private static final long serialVersionUID = 1L;

    public String getProjectNo() {
        return projectNo;
    }

    public void setProjectNo(String projectNo) {
        this.projectNo = projectNo == null ? null : projectNo.trim();
    }

    public String getB001() {
        return b001;
    }

    public void setB001(String b001) {
        this.b001 = b001 == null ? null : b001.trim();
    }

    public String getB005() {
        return b005;
    }

    public void setB005(String b005) {
        this.b005 = b005 == null ? null : b005.trim();
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName == null ? null : orgName.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName == null ? null : categoryName.trim();
    }

    public String getB002() {
        return b002;
    }

    public void setB002(String b002) {
        this.b002 = b002 == null ? null : b002.trim();
    }

    public String getB003() {
        return b003;
    }

    public void setB003(String b003) {
        this.b003 = b003 == null ? null : b003.trim();
    }

    public String getB016() {
        return b016;
    }

    public void setB016(String b016) {
        this.b016 = b016 == null ? null : b016.trim();
    }

    public String getB027() {
        return b027;
    }

    public void setB027(String b027) {
        this.b027 = b027 == null ? null : b027.trim();
    }

    public String getB014() {
        return b014;
    }

    public void setB014(String b014) {
        this.b014 = b014 == null ? null : b014.trim();
    }

    public String getB017() {
        return b017;
    }

    public void setB017(String b017) {
        this.b017 = b017 == null ? null : b017.trim();
    }

    public String getB021() {
        return b021;
    }

    public void setB021(String b021) {
        this.b021 = b021 == null ? null : b021.trim();
    }

    public String getB022() {
        return b022;
    }

    public void setB022(String b022) {
        this.b022 = b022 == null ? null : b022.trim();
    }

    public String getB010() {
        return b010;
    }

    public void setB010(String b010) {
        this.b010 = b010 == null ? null : b010.trim();
    }

    public String getB032() {
        return b032;
    }

    public void setB032(String b032) {
        this.b032 = b032 == null ? null : b032.trim();
    }

    public String getB029() {
        return b029;
    }

    public void setB029(String b029) {
        this.b029 = b029 == null ? null : b029.trim();
    }

    public String getC012() {
        return c012;
    }

    public void setC012(String c012) {
        this.c012 = c012 == null ? null : c012.trim();
    }

    public String getC001() {
        return c001;
    }

    public void setC001(String c001) {
        this.c001 = c001 == null ? null : c001.trim();
    }

    public String getC005() {
        return c005;
    }

    public void setC005(String c005) {
        this.c005 = c005 == null ? null : c005.trim();
    }

    public String getC002() {
        return c002;
    }

    public void setC002(String c002) {
        this.c002 = c002 == null ? null : c002.trim();
    }

    public String getC013() {
        return c013;
    }

    public void setC013(String c013) {
        this.c013 = c013 == null ? null : c013.trim();
    }

    public String getC007() {
        return c007;
    }

    public void setC007(String c007) {
        this.c007 = c007 == null ? null : c007.trim();
    }

    public String getC009() {
        return c009;
    }

    public void setC009(String c009) {
        this.c009 = c009 == null ? null : c009.trim();
    }

    public String getC010() {
        return c010;
    }

    public void setC010(String c010) {
        this.c010 = c010 == null ? null : c010.trim();
    }

    public String getC008() {
        return c008;
    }

    public void setC008(String c008) {
        this.c008 = c008 == null ? null : c008.trim();
    }

    public String getC014() {
        return c014;
    }

    public void setC014(String c014) {
        this.c014 = c014 == null ? null : c014.trim();
    }

    public String getC016() {
        return c016;
    }

    public void setC016(String c016) {
        this.c016 = c016 == null ? null : c016.trim();
    }

    public String getC015() {
        return c015;
    }

    public void setC015(String c015) {
        this.c015 = c015 == null ? null : c015.trim();
    }

    public String getD002() {
        return d002;
    }

    public void setD002(String d002) {
        this.d002 = d002 == null ? null : d002.trim();
    }

    public String getD010() {
        return d010;
    }

    public void setD010(String d010) {
        this.d010 = d010 == null ? null : d010.trim();
    }

    public String getD011() {
        return d011;
    }

    public void setD011(String d011) {
        this.d011 = d011 == null ? null : d011.trim();
    }

    public String getD013() {
        return d013;
    }

    public void setD013(String d013) {
        this.d013 = d013 == null ? null : d013.trim();
    }

    public String getD015() {
        return d015;
    }

    public void setD015(String d015) {
        this.d015 = d015 == null ? null : d015.trim();
    }

    public String getD016() {
        return d016;
    }

    public void setD016(String d016) {
        this.d016 = d016 == null ? null : d016.trim();
    }

    public String getD019() {
        return d019;
    }

    public void setD019(String d019) {
        this.d019 = d019 == null ? null : d019.trim();
    }

    public String getD018() {
        return d018;
    }

    public void setD018(String d018) {
        this.d018 = d018 == null ? null : d018.trim();
    }

    public String getK002() {
        return k002;
    }

    public void setK002(String k002) {
        this.k002 = k002 == null ? null : k002.trim();
    }

    public String getK004() {
        return k004;
    }

    public void setK004(String k004) {
        this.k004 = k004 == null ? null : k004.trim();
    }

    public String getK005() {
        return k005;
    }

    public void setK005(String k005) {
        this.k005 = k005 == null ? null : k005.trim();
    }

    public String getK006() {
        return k006;
    }

    public void setK006(String k006) {
        this.k006 = k006 == null ? null : k006.trim();
    }

    public String getK007() {
        return k007;
    }

    public void setK007(String k007) {
        this.k007 = k007 == null ? null : k007.trim();
    }

    public String getE002() {
        return e002;
    }

    public void setE002(String e002) {
        this.e002 = e002 == null ? null : e002.trim();
    }

    public String getE010() {
        return e010;
    }

    public void setE010(String e010) {
        this.e010 = e010 == null ? null : e010.trim();
    }

    public String getE007() {
        return e007;
    }

    public void setE007(String e007) {
        this.e007 = e007 == null ? null : e007.trim();
    }

    public String getE015() {
        return e015;
    }

    public void setE015(String e015) {
        this.e015 = e015 == null ? null : e015.trim();
    }

    public String getE019() {
        return e019;
    }

    public void setE019(String e019) {
        this.e019 = e019 == null ? null : e019.trim();
    }

    public String getE020() {
        return e020;
    }

    public void setE020(String e020) {
        this.e020 = e020 == null ? null : e020.trim();
    }

    public String getE021() {
        return e021;
    }

    public void setE021(String e021) {
        this.e021 = e021 == null ? null : e021.trim();
    }

    public String getE040() {
        return e040;
    }

    public void setE040(String e040) {
        this.e040 = e040 == null ? null : e040.trim();
    }

    public String getE042() {
        return e042;
    }

    public void setE042(String e042) {
        this.e042 = e042 == null ? null : e042.trim();
    }

    public String getG002() {
        return g002;
    }

    public void setG002(String g002) {
        this.g002 = g002 == null ? null : g002.trim();
    }

    public String getG013() {
        return g013;
    }

    public void setG013(String g013) {
        this.g013 = g013 == null ? null : g013.trim();
    }

    public String getG016() {
        return g016;
    }

    public void setG016(String g016) {
        this.g016 = g016 == null ? null : g016.trim();
    }

    public String getF0012() {
        return f0012;
    }

    public void setF0012(String f0012) {
        this.f0012 = f0012 == null ? null : f0012.trim();
    }

    public String getF001() {
        return f001;
    }

    public void setF001(String f001) {
        this.f001 = f001 == null ? null : f001.trim();
    }

    public String getF002() {
        return f002;
    }

    public void setF002(String f002) {
        this.f002 = f002 == null ? null : f002.trim();
    }

    public String getF009() {
        return f009;
    }

    public void setF009(String f009) {
        this.f009 = f009 == null ? null : f009.trim();
    }

    public String getF010() {
        return f010;
    }

    public void setF010(String f010) {
        this.f010 = f010 == null ? null : f010.trim();
    }

    public String getF011() {
        return f011;
    }

    public void setF011(String f011) {
        this.f011 = f011 == null ? null : f011.trim();
    }

    public String getL001() {
        return l001;
    }

    public void setL001(String l001) {
        this.l001 = l001 == null ? null : l001.trim();
    }

    public String getL003() {
        return l003;
    }

    public void setL003(String l003) {
        this.l003 = l003 == null ? null : l003.trim();
    }

    public String getL009() {
        return l009;
    }

    public void setL009(String l009) {
        this.l009 = l009 == null ? null : l009.trim();
    }

    public String getL004() {
        return l004;
    }

    public void setL004(String l004) {
        this.l004 = l004 == null ? null : l004.trim();
    }

    public String getL007() {
        return l007;
    }

    public void setL007(String l007) {
        this.l007 = l007 == null ? null : l007.trim();
    }

    public String getL018() {
        return l018;
    }

    public void setL018(String l018) {
        this.l018 = l018 == null ? null : l018.trim();
    }

    public String getS002() {
        return s002;
    }

    public void setS002(String s002) {
        this.s002 = s002 == null ? null : s002.trim();
    }

    public String getS001() {
        return s001;
    }

    public void setS001(String s001) {
        this.s001 = s001 == null ? null : s001.trim();
    }

    public String getS003() {
        return s003;
    }

    public void setS003(String s003) {
        this.s003 = s003 == null ? null : s003.trim();
    }

    public String getS004() {
        return s004;
    }

    public void setS004(String s004) {
        this.s004 = s004 == null ? null : s004.trim();
    }

    public String getS005() {
        return s005;
    }

    public void setS005(String s005) {
        this.s005 = s005 == null ? null : s005.trim();
    }

    public String getS006() {
        return s006;
    }

    public void setS006(String s006) {
        this.s006 = s006 == null ? null : s006.trim();
    }

    public String getS007() {
        return s007;
    }

    public void setS007(String s007) {
        this.s007 = s007 == null ? null : s007.trim();
    }

    public String getS008() {
        return s008;
    }

    public void setS008(String s008) {
        this.s008 = s008 == null ? null : s008.trim();
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

    public Date getEndRecheckDate() {
        return endRecheckDate;
    }

    public void setEndRecheckDate(Date endRecheckDate) {
        this.endRecheckDate = endRecheckDate;
    }

    public BigDecimal getApproveAmount() {
        return approveAmount;
    }

    public void setApproveAmount(BigDecimal approveAmount) {
        this.approveAmount = approveAmount;
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

    public String getAuditRecheckRemark() {
        return auditRecheckRemark;
    }

    public void setAuditRecheckRemark(String auditRecheckRemark) {
        this.auditRecheckRemark = auditRecheckRemark == null ? null : auditRecheckRemark.trim();
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

    public String getCreditDetail() {
        return creditDetail;
    }

    public void setCreditDetail(String creditDetail) {
        this.creditDetail = creditDetail == null ? null : creditDetail.trim();
    }

    public String getCreditSimple() {
        return creditSimple;
    }

    public void setCreditSimple(String creditSimple) {
        this.creditSimple = creditSimple == null ? null : creditSimple.trim();
    }

    public String getApproveresult() {
        return approveresult;
    }

    public void setApproveresult(String approveresult) {
        this.approveresult = approveresult == null ? null : approveresult.trim();
    }

    public String getAuditrecheckresult() {
        return auditrecheckresult;
    }

    public void setAuditrecheckresult(String auditrecheckresult) {
        this.auditrecheckresult = auditrecheckresult == null ? null : auditrecheckresult.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table v_report_audit
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
        VReportAudit other = (VReportAudit) that;
        return (this.getProjectNo() == null ? other.getProjectNo() == null : this.getProjectNo().equals(other.getProjectNo()))
            && (this.getB001() == null ? other.getB001() == null : this.getB001().equals(other.getB001()))
            && (this.getB005() == null ? other.getB005() == null : this.getB005().equals(other.getB005()))
            && (this.getOrgName() == null ? other.getOrgName() == null : this.getOrgName().equals(other.getOrgName()))
            && (this.getTitle() == null ? other.getTitle() == null : this.getTitle().equals(other.getTitle()))
            && (this.getCategoryName() == null ? other.getCategoryName() == null : this.getCategoryName().equals(other.getCategoryName()))
            && (this.getB002() == null ? other.getB002() == null : this.getB002().equals(other.getB002()))
            && (this.getB003() == null ? other.getB003() == null : this.getB003().equals(other.getB003()))
            && (this.getB016() == null ? other.getB016() == null : this.getB016().equals(other.getB016()))
            && (this.getB027() == null ? other.getB027() == null : this.getB027().equals(other.getB027()))
            && (this.getB014() == null ? other.getB014() == null : this.getB014().equals(other.getB014()))
            && (this.getB017() == null ? other.getB017() == null : this.getB017().equals(other.getB017()))
            && (this.getB021() == null ? other.getB021() == null : this.getB021().equals(other.getB021()))
            && (this.getB022() == null ? other.getB022() == null : this.getB022().equals(other.getB022()))
            && (this.getB010() == null ? other.getB010() == null : this.getB010().equals(other.getB010()))
            && (this.getB032() == null ? other.getB032() == null : this.getB032().equals(other.getB032()))
            && (this.getB029() == null ? other.getB029() == null : this.getB029().equals(other.getB029()))
            && (this.getC012() == null ? other.getC012() == null : this.getC012().equals(other.getC012()))
            && (this.getC001() == null ? other.getC001() == null : this.getC001().equals(other.getC001()))
            && (this.getC005() == null ? other.getC005() == null : this.getC005().equals(other.getC005()))
            && (this.getC002() == null ? other.getC002() == null : this.getC002().equals(other.getC002()))
            && (this.getC013() == null ? other.getC013() == null : this.getC013().equals(other.getC013()))
            && (this.getC007() == null ? other.getC007() == null : this.getC007().equals(other.getC007()))
            && (this.getC009() == null ? other.getC009() == null : this.getC009().equals(other.getC009()))
            && (this.getC010() == null ? other.getC010() == null : this.getC010().equals(other.getC010()))
            && (this.getC008() == null ? other.getC008() == null : this.getC008().equals(other.getC008()))
            && (this.getC014() == null ? other.getC014() == null : this.getC014().equals(other.getC014()))
            && (this.getC016() == null ? other.getC016() == null : this.getC016().equals(other.getC016()))
            && (this.getC015() == null ? other.getC015() == null : this.getC015().equals(other.getC015()))
            && (this.getD002() == null ? other.getD002() == null : this.getD002().equals(other.getD002()))
            && (this.getD010() == null ? other.getD010() == null : this.getD010().equals(other.getD010()))
            && (this.getD011() == null ? other.getD011() == null : this.getD011().equals(other.getD011()))
            && (this.getD013() == null ? other.getD013() == null : this.getD013().equals(other.getD013()))
            && (this.getD015() == null ? other.getD015() == null : this.getD015().equals(other.getD015()))
            && (this.getD016() == null ? other.getD016() == null : this.getD016().equals(other.getD016()))
            && (this.getD019() == null ? other.getD019() == null : this.getD019().equals(other.getD019()))
            && (this.getD018() == null ? other.getD018() == null : this.getD018().equals(other.getD018()))
            && (this.getK002() == null ? other.getK002() == null : this.getK002().equals(other.getK002()))
            && (this.getK004() == null ? other.getK004() == null : this.getK004().equals(other.getK004()))
            && (this.getK005() == null ? other.getK005() == null : this.getK005().equals(other.getK005()))
            && (this.getK006() == null ? other.getK006() == null : this.getK006().equals(other.getK006()))
            && (this.getK007() == null ? other.getK007() == null : this.getK007().equals(other.getK007()))
            && (this.getE002() == null ? other.getE002() == null : this.getE002().equals(other.getE002()))
            && (this.getE010() == null ? other.getE010() == null : this.getE010().equals(other.getE010()))
            && (this.getE007() == null ? other.getE007() == null : this.getE007().equals(other.getE007()))
            && (this.getE015() == null ? other.getE015() == null : this.getE015().equals(other.getE015()))
            && (this.getE019() == null ? other.getE019() == null : this.getE019().equals(other.getE019()))
            && (this.getE020() == null ? other.getE020() == null : this.getE020().equals(other.getE020()))
            && (this.getE021() == null ? other.getE021() == null : this.getE021().equals(other.getE021()))
            && (this.getE040() == null ? other.getE040() == null : this.getE040().equals(other.getE040()))
            && (this.getE042() == null ? other.getE042() == null : this.getE042().equals(other.getE042()))
            && (this.getG002() == null ? other.getG002() == null : this.getG002().equals(other.getG002()))
            && (this.getG013() == null ? other.getG013() == null : this.getG013().equals(other.getG013()))
            && (this.getG016() == null ? other.getG016() == null : this.getG016().equals(other.getG016()))
            && (this.getF0012() == null ? other.getF0012() == null : this.getF0012().equals(other.getF0012()))
            && (this.getF001() == null ? other.getF001() == null : this.getF001().equals(other.getF001()))
            && (this.getF002() == null ? other.getF002() == null : this.getF002().equals(other.getF002()))
            && (this.getF009() == null ? other.getF009() == null : this.getF009().equals(other.getF009()))
            && (this.getF010() == null ? other.getF010() == null : this.getF010().equals(other.getF010()))
            && (this.getF011() == null ? other.getF011() == null : this.getF011().equals(other.getF011()))
            && (this.getL001() == null ? other.getL001() == null : this.getL001().equals(other.getL001()))
            && (this.getL003() == null ? other.getL003() == null : this.getL003().equals(other.getL003()))
            && (this.getL009() == null ? other.getL009() == null : this.getL009().equals(other.getL009()))
            && (this.getL004() == null ? other.getL004() == null : this.getL004().equals(other.getL004()))
            && (this.getL007() == null ? other.getL007() == null : this.getL007().equals(other.getL007()))
            && (this.getL018() == null ? other.getL018() == null : this.getL018().equals(other.getL018()))
            && (this.getS002() == null ? other.getS002() == null : this.getS002().equals(other.getS002()))
            && (this.getS001() == null ? other.getS001() == null : this.getS001().equals(other.getS001()))
            && (this.getS003() == null ? other.getS003() == null : this.getS003().equals(other.getS003()))
            && (this.getS004() == null ? other.getS004() == null : this.getS004().equals(other.getS004()))
            && (this.getS005() == null ? other.getS005() == null : this.getS005().equals(other.getS005()))
            && (this.getS006() == null ? other.getS006() == null : this.getS006().equals(other.getS006()))
            && (this.getS007() == null ? other.getS007() == null : this.getS007().equals(other.getS007()))
            && (this.getS008() == null ? other.getS008() == null : this.getS008().equals(other.getS008()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getBeginAuditDate() == null ? other.getBeginAuditDate() == null : this.getBeginAuditDate().equals(other.getBeginAuditDate()))
            && (this.getEndAuditDate() == null ? other.getEndAuditDate() == null : this.getEndAuditDate().equals(other.getEndAuditDate()))
            && (this.getEndRecheckDate() == null ? other.getEndRecheckDate() == null : this.getEndRecheckDate().equals(other.getEndRecheckDate()))
            && (this.getApproveAmount() == null ? other.getApproveAmount() == null : this.getApproveAmount().equals(other.getApproveAmount()))
            && (this.getApproveRemark() == null ? other.getApproveRemark() == null : this.getApproveRemark().equals(other.getApproveRemark()))
            && (this.getAuditRecheckAmount() == null ? other.getAuditRecheckAmount() == null : this.getAuditRecheckAmount().equals(other.getAuditRecheckAmount()))
            && (this.getAuditRecheckDuration() == null ? other.getAuditRecheckDuration() == null : this.getAuditRecheckDuration().equals(other.getAuditRecheckDuration()))
            && (this.getAuditRecheckRemark() == null ? other.getAuditRecheckRemark() == null : this.getAuditRecheckRemark().equals(other.getAuditRecheckRemark()))
            && (this.getAuditOverTime() == null ? other.getAuditOverTime() == null : this.getAuditOverTime().equals(other.getAuditOverTime()))
            && (this.getApplyDate() == null ? other.getApplyDate() == null : this.getApplyDate().equals(other.getApplyDate()))
            && (this.getCreditDetail() == null ? other.getCreditDetail() == null : this.getCreditDetail().equals(other.getCreditDetail()))
            && (this.getCreditSimple() == null ? other.getCreditSimple() == null : this.getCreditSimple().equals(other.getCreditSimple()))
            && (this.getApproveresult() == null ? other.getApproveresult() == null : this.getApproveresult().equals(other.getApproveresult()))
            && (this.getAuditrecheckresult() == null ? other.getAuditrecheckresult() == null : this.getAuditrecheckresult().equals(other.getAuditrecheckresult()));
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table v_report_audit
     *
     * @mbg.generated Wed Dec 19 16:26:10 CST 2018
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getProjectNo() == null) ? 0 : getProjectNo().hashCode());
        result = prime * result + ((getB001() == null) ? 0 : getB001().hashCode());
        result = prime * result + ((getB005() == null) ? 0 : getB005().hashCode());
        result = prime * result + ((getOrgName() == null) ? 0 : getOrgName().hashCode());
        result = prime * result + ((getTitle() == null) ? 0 : getTitle().hashCode());
        result = prime * result + ((getCategoryName() == null) ? 0 : getCategoryName().hashCode());
        result = prime * result + ((getB002() == null) ? 0 : getB002().hashCode());
        result = prime * result + ((getB003() == null) ? 0 : getB003().hashCode());
        result = prime * result + ((getB016() == null) ? 0 : getB016().hashCode());
        result = prime * result + ((getB027() == null) ? 0 : getB027().hashCode());
        result = prime * result + ((getB014() == null) ? 0 : getB014().hashCode());
        result = prime * result + ((getB017() == null) ? 0 : getB017().hashCode());
        result = prime * result + ((getB021() == null) ? 0 : getB021().hashCode());
        result = prime * result + ((getB022() == null) ? 0 : getB022().hashCode());
        result = prime * result + ((getB010() == null) ? 0 : getB010().hashCode());
        result = prime * result + ((getB032() == null) ? 0 : getB032().hashCode());
        result = prime * result + ((getB029() == null) ? 0 : getB029().hashCode());
        result = prime * result + ((getC012() == null) ? 0 : getC012().hashCode());
        result = prime * result + ((getC001() == null) ? 0 : getC001().hashCode());
        result = prime * result + ((getC005() == null) ? 0 : getC005().hashCode());
        result = prime * result + ((getC002() == null) ? 0 : getC002().hashCode());
        result = prime * result + ((getC013() == null) ? 0 : getC013().hashCode());
        result = prime * result + ((getC007() == null) ? 0 : getC007().hashCode());
        result = prime * result + ((getC009() == null) ? 0 : getC009().hashCode());
        result = prime * result + ((getC010() == null) ? 0 : getC010().hashCode());
        result = prime * result + ((getC008() == null) ? 0 : getC008().hashCode());
        result = prime * result + ((getC014() == null) ? 0 : getC014().hashCode());
        result = prime * result + ((getC016() == null) ? 0 : getC016().hashCode());
        result = prime * result + ((getC015() == null) ? 0 : getC015().hashCode());
        result = prime * result + ((getD002() == null) ? 0 : getD002().hashCode());
        result = prime * result + ((getD010() == null) ? 0 : getD010().hashCode());
        result = prime * result + ((getD011() == null) ? 0 : getD011().hashCode());
        result = prime * result + ((getD013() == null) ? 0 : getD013().hashCode());
        result = prime * result + ((getD015() == null) ? 0 : getD015().hashCode());
        result = prime * result + ((getD016() == null) ? 0 : getD016().hashCode());
        result = prime * result + ((getD019() == null) ? 0 : getD019().hashCode());
        result = prime * result + ((getD018() == null) ? 0 : getD018().hashCode());
        result = prime * result + ((getK002() == null) ? 0 : getK002().hashCode());
        result = prime * result + ((getK004() == null) ? 0 : getK004().hashCode());
        result = prime * result + ((getK005() == null) ? 0 : getK005().hashCode());
        result = prime * result + ((getK006() == null) ? 0 : getK006().hashCode());
        result = prime * result + ((getK007() == null) ? 0 : getK007().hashCode());
        result = prime * result + ((getE002() == null) ? 0 : getE002().hashCode());
        result = prime * result + ((getE010() == null) ? 0 : getE010().hashCode());
        result = prime * result + ((getE007() == null) ? 0 : getE007().hashCode());
        result = prime * result + ((getE015() == null) ? 0 : getE015().hashCode());
        result = prime * result + ((getE019() == null) ? 0 : getE019().hashCode());
        result = prime * result + ((getE020() == null) ? 0 : getE020().hashCode());
        result = prime * result + ((getE021() == null) ? 0 : getE021().hashCode());
        result = prime * result + ((getE040() == null) ? 0 : getE040().hashCode());
        result = prime * result + ((getE042() == null) ? 0 : getE042().hashCode());
        result = prime * result + ((getG002() == null) ? 0 : getG002().hashCode());
        result = prime * result + ((getG013() == null) ? 0 : getG013().hashCode());
        result = prime * result + ((getG016() == null) ? 0 : getG016().hashCode());
        result = prime * result + ((getF0012() == null) ? 0 : getF0012().hashCode());
        result = prime * result + ((getF001() == null) ? 0 : getF001().hashCode());
        result = prime * result + ((getF002() == null) ? 0 : getF002().hashCode());
        result = prime * result + ((getF009() == null) ? 0 : getF009().hashCode());
        result = prime * result + ((getF010() == null) ? 0 : getF010().hashCode());
        result = prime * result + ((getF011() == null) ? 0 : getF011().hashCode());
        result = prime * result + ((getL001() == null) ? 0 : getL001().hashCode());
        result = prime * result + ((getL003() == null) ? 0 : getL003().hashCode());
        result = prime * result + ((getL009() == null) ? 0 : getL009().hashCode());
        result = prime * result + ((getL004() == null) ? 0 : getL004().hashCode());
        result = prime * result + ((getL007() == null) ? 0 : getL007().hashCode());
        result = prime * result + ((getL018() == null) ? 0 : getL018().hashCode());
        result = prime * result + ((getS002() == null) ? 0 : getS002().hashCode());
        result = prime * result + ((getS001() == null) ? 0 : getS001().hashCode());
        result = prime * result + ((getS003() == null) ? 0 : getS003().hashCode());
        result = prime * result + ((getS004() == null) ? 0 : getS004().hashCode());
        result = prime * result + ((getS005() == null) ? 0 : getS005().hashCode());
        result = prime * result + ((getS006() == null) ? 0 : getS006().hashCode());
        result = prime * result + ((getS007() == null) ? 0 : getS007().hashCode());
        result = prime * result + ((getS008() == null) ? 0 : getS008().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getBeginAuditDate() == null) ? 0 : getBeginAuditDate().hashCode());
        result = prime * result + ((getEndAuditDate() == null) ? 0 : getEndAuditDate().hashCode());
        result = prime * result + ((getEndRecheckDate() == null) ? 0 : getEndRecheckDate().hashCode());
        result = prime * result + ((getApproveAmount() == null) ? 0 : getApproveAmount().hashCode());
        result = prime * result + ((getApproveRemark() == null) ? 0 : getApproveRemark().hashCode());
        result = prime * result + ((getAuditRecheckAmount() == null) ? 0 : getAuditRecheckAmount().hashCode());
        result = prime * result + ((getAuditRecheckDuration() == null) ? 0 : getAuditRecheckDuration().hashCode());
        result = prime * result + ((getAuditRecheckRemark() == null) ? 0 : getAuditRecheckRemark().hashCode());
        result = prime * result + ((getAuditOverTime() == null) ? 0 : getAuditOverTime().hashCode());
        result = prime * result + ((getApplyDate() == null) ? 0 : getApplyDate().hashCode());
        result = prime * result + ((getCreditDetail() == null) ? 0 : getCreditDetail().hashCode());
        result = prime * result + ((getCreditSimple() == null) ? 0 : getCreditSimple().hashCode());
        result = prime * result + ((getApproveresult() == null) ? 0 : getApproveresult().hashCode());
        result = prime * result + ((getAuditrecheckresult() == null) ? 0 : getAuditrecheckresult().hashCode());
        return result;
    }
}