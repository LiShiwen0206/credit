package com.starsgroupchina.credit.bean.extend;

import com.starsgroupchina.common.utils.BeanUtil;
import com.starsgroupchina.common.utils.export.ExcelAnnotation;
import com.starsgroupchina.common.utils.export.ExcelMergeColumn;
import com.starsgroupchina.credit.bean.model.VReportAudit;
import lombok.Data;

import java.util.Date;

/**
 * Created by gexiaoshan on 2018/9/5.
 */
@Data
public class VReportAuditExtend {

    //第一行标题
    @ExcelMergeColumn(id =1,name ="贷款信息",number = 7)
    private String dkInfo;
    @ExcelMergeColumn(id =2,name ="个人信息",number = 11)
    private String grInfo;
    @ExcelMergeColumn(id =3,name ="单位信息",number = 13)
    private String zyInfo;
    @ExcelMergeColumn(id =4,name ="工商信息",number = 9)
    private String gsInfo;
    @ExcelMergeColumn(id =5,name ="社保信息",number = 6)
    private String fcInfo;
    @ExcelMergeColumn(id =6,name ="房产信息",number = 9)
    private String fzInfo;
    @ExcelMergeColumn(id =7,name ="车辆信息",number = 3)
    private String clInfo;
    @ExcelMergeColumn(id =8,name ="负债信息",number = 6)
    private String thyInfo;
    @ExcelMergeColumn(id =10,name ="保单信息",number = 6)
    private String wnryInfo;
    @ExcelMergeColumn(id =11,name ="阿里/腾讯/京东贷款",number = 8)
    private String xsInfo;
    @ExcelMergeColumn(id =12,name ="简版征信",number = 22)
    private String sbInfo;
    @ExcelMergeColumn(id =13,name ="详版征信",number = 24)
    private String bdInfo;
    @ExcelMergeColumn(id =14,name ="时间截点",number = 7)
    private String dbrInfo;
    @ExcelMergeColumn(id =15,name ="审核/复核处理结果",number = 15)
    private String poInfo;
    @ExcelMergeColumn(id =16,name ="处理人员",number = 3)
    private String lxr1Info;
    @ExcelMergeColumn(id =17,name ="贷后状态",number = 7)
    private String lxr2Info;


    //第二行标题
    //贷款信息
    @ExcelAnnotation(id = 1, name = {"序号"})
    private Integer rowId;

    @ExcelAnnotation(id = 2, name = {"进件编号"})
    private String projectNo;

    @ExcelAnnotation(id = 3, name = {"姓名"})
    private String b001;

    @ExcelAnnotation(id = 4, name = {"证件号码"})
    private String b005;

    @ExcelAnnotation(id = 5, name = {"公司名称"})
    private String orgName;

    @ExcelAnnotation(id = 6, name = {"产品名称"})
    private String title;

    @ExcelAnnotation(id = 7, name = {"产品类型"})
    private String categoryName;

    //个人信息
    @ExcelAnnotation(id = 8, name = {"性别"})
    private String b002;

    @ExcelAnnotation(id = 9, name = {"年龄"})
    private String b003;

    @ExcelAnnotation(id = 10, name = {"手机号码"})
    private String b016;

    @ExcelAnnotation(id = 12, name = {"居住类型"})
    private String b027;

    @ExcelAnnotation(id = 13, name = {"最高学历"})
    private String b014;

    @ExcelAnnotation(id = 14, name = {"婚姻状况"})
    private String b017;

    @ExcelAnnotation(id = 15, name = {"供养亲属数"})
    private String b021;

    @ExcelAnnotation(id = 16, name = {"现家庭地址"})
    private String b022;

    @ExcelAnnotation(id = 17, name = {"户籍地址"})
    private String b010;

    @ExcelAnnotation(id = 18, name = {"每月家庭支出"})
    private String b032;

    @ExcelAnnotation(id = 19, name = {"共同居住成员"})
    private String b029;

    //单位信息
    @ExcelAnnotation(id = 20, name = {"工作性质"})
    private String c012;

    @ExcelAnnotation(id = 22, name = {"单位全称"})
    private String c001;

    @ExcelAnnotation(id = 23, name = {"单位电话"})
    private String c005;

    @ExcelAnnotation(id = 24, name = {"单位地址"})
    private String c002;

    @ExcelAnnotation(id = 25, name = {"现单位入职时间"})
    private String c013;

    @ExcelAnnotation(id = 25, name = {"现单位入职时长（月）"})
    private String c013Month;

    @ExcelAnnotation(id = 26, name = {"单位性质"})
    private String c007;

    @ExcelAnnotation(id = 27, name = {"任职部门"})
    private String c009;

    @ExcelAnnotation(id = 28, name = {"职务"})
    private String c010;

    @ExcelAnnotation(id = 29, name = {"行业类型"})
    private String c008;

    @ExcelAnnotation(id = 30, name = {"月收入"})
    private String c014;

    @ExcelAnnotation(id = 32, name = {"发薪方式"})
    private String c016;

    @ExcelAnnotation(id = 33, name = {"发薪日"})
    private String c015;

    //工商信息
    @ExcelAnnotation(id = 34, name = {"注册时间"})
    private String d002;

    @ExcelAnnotation(id = 34, name = {"注册时长（月）"})
    private String d002Month;

    @ExcelAnnotation(id = 35, name = {"月营业额"})
    private String d010;

    @ExcelAnnotation(id = 36, name = {"毛利率"})
    private String d011;

    @ExcelAnnotation(id = 37, name = {"每月净利润额"})
    private String d013;

    @ExcelAnnotation(id = 38, name = {"员工人数"})
    private String d015;

    @ExcelAnnotation(id = 39, name = {"占股比例"})
    private String d016;

    @ExcelAnnotation(id = 40, name = {"租金（元/月）"})
    private String d019;

    @ExcelAnnotation(id = 42, name = {"出租方电话"})
    private String d018;

    //社保信息
    @ExcelAnnotation(id = 43, name = {"参保单位"})
    private String k002;

    @ExcelAnnotation(id = 44, name = {"现单位缴纳起始日"})
    private String k004;

    @ExcelAnnotation(id = 44, name = {"现单位社保缴纳时长（月）"})
    private String k004Month;

    @ExcelAnnotation(id = 45, name = {"社保缴纳基数"})
    private String k005;

    @ExcelAnnotation(id = 46, name = {"公积金缴纳基数"})
    private String k006;

    @ExcelAnnotation(id = 47, name = {"社保累计缴纳月数"})
    private String k007;

    //房产信息
    @ExcelAnnotation(id = 48, name = {"房产地址"})
    private String e002;

    @ExcelAnnotation(id = 49, name = {"购买方式"})
    private String e010;

    @ExcelAnnotation(id = 50, name = {"产权比例"})
    private String e007;

    @ExcelAnnotation(id = 52, name = {"建筑面积"})
    private String e015;

    @ExcelAnnotation(id = 53, name = {"贷款金额"})
    private String e019;

    @ExcelAnnotation(id = 54, name = {"贷款期限"})
    private String e020;

    @ExcelAnnotation(id = 55, name = {"月还款额"})
    private String e021;

    @ExcelAnnotation(id = 56, name = {"其他房产贷款金额"})
    private String e040;

    @ExcelAnnotation(id = 57, name = {"其他房产月还款额"})
    private String e042;

    //车辆信息
    @ExcelAnnotation(id = 58, name = {"品牌型号"})
    private String g002;

    @ExcelAnnotation(id = 59, name = {"贷款金额"})
    private String g013;

    @ExcelAnnotation(id = 60, name = {"月还款额"})
    private String g016;

    //负债信息
    @ExcelAnnotation(id = 62, name = {"信用卡总额"})
    private String f0012;

    @ExcelAnnotation(id = 63, name = {"信用卡张数"})
    private String f001;

    @ExcelAnnotation(id = 64, name = {"信用卡当前欠款"})
    private String f002;

    @ExcelAnnotation(id = 65, name = {"贷款总额度"})
    private String f009;

    @ExcelAnnotation(id = 66, name = {"贷款笔数"})
    private String f010;

    @ExcelAnnotation(id = 67, name = {"贷款欠款"})
    private String f011;

    //保单信息
    @ExcelAnnotation(id = 68, name = {"保险公司"})
    private String l001;

    @ExcelAnnotation(id = 69, name = {"险种名称"})
    private String l003;

    @ExcelAnnotation(id = 70, name = {"缴费方式"})
    private String l009;

    @ExcelAnnotation(id = 72, name = {"约定缴费年限"})
    private String l004;

    @ExcelAnnotation(id = 73, name = {"缴费金额"})
    private String l007;

    @ExcelAnnotation(id = 74, name = {"已缴期数"})
    private String l018;

    //阿里/腾讯/京东贷款
    @ExcelAnnotation(id = 75, name = {"花呗/借呗授信金额"})
    private String s002;

    @ExcelAnnotation(id = 76, name = {"微粒贷授信金额"})
    private String s001;

    @ExcelAnnotation(id = 77, name = {"白条/金条授信金额"})
    private String s003;

    @ExcelAnnotation(id =78, name = {"借款金额"})
    private String s004;

    @ExcelAnnotation(id = 79, name = {"借款期限"})
    private String s005;

    @ExcelAnnotation(id = 80, name = {"月还款额"})
    private String s006;

    @ExcelAnnotation(id = 82, name = {"已还款期数"})
    private String s007;

    @ExcelAnnotation(id = 83, name = {"已还款金额"})
    private String s008;

    //简版征信
    @ExcelAnnotation(id = 85, name = {"贷记卡-授信总额（元）"})
    private Object a111;

    @ExcelAnnotation(id = 86, name = {"贷记卡-剩余本金（元）"})
    private Object a222;

    @ExcelAnnotation(id = 87, name = {"贷记卡-负债月供（元）"})
    private Object a333;

    @ExcelAnnotation(id = 88, name = {"准贷记卡-授信总额（元）"})
    private Object b111;

    @ExcelAnnotation(id = 89, name = {"准贷记卡-剩余本金（元）"})
    private Object b222;

    @ExcelAnnotation(id = 90, name = {"准贷记卡-负债月供（元）"})
    private Object b333;

    @ExcelAnnotation(id = 91, name = {"贷款-授信总额（元）"})
    private Object c111;

    @ExcelAnnotation(id = 92, name = {"贷款-剩余本金（元）"})
    private Object c222;

    @ExcelAnnotation(id = 93, name = {"贷款-负债月供（元）"})
    private Object c333;

    @ExcelAnnotation(id = 94, name = {"总计-授信总额（元）"})
    private Object d111;

    @ExcelAnnotation(id = 95, name = {"总计-剩余本金（元）"})
    private Object d222;

    @ExcelAnnotation(id = 96, name = {"总计-负债月供（元）"})
    private Object d333;

    @ExcelAnnotation(id = 97, name = {"评估收入（元）"})
    private Object eeee;

    @ExcelAnnotation(id = 98, name = {"DSR（%"})
    private Object ffff;

    @ExcelAnnotation(id = 99, name = {"近1个月信用卡查询"})
    private Object g111;

    @ExcelAnnotation(id = 100, name = {"近3个月信用卡查询"})
    private Object g112;

    @ExcelAnnotation(id = 101, name = {"近1个月贷款查询"})
    private Object g113;

    @ExcelAnnotation(id = 102, name = {"近3个月贷款查询"})
    private Object g114;
    @ExcelAnnotation(id = 103, name = {"5年内贷记卡逾期"})
    private Object g115;
    @ExcelAnnotation(id = 104, name = {"5年内贷款逾期"})
    private Object g116;
    @ExcelAnnotation(id = 105, name = {"5年内贷记卡91+"})
    private Object g117;
    @ExcelAnnotation(id = 106, name = {"5年内贷款91+"})
    private Object g118;

    //详版征信
    @ExcelAnnotation(id = 110, name = {"贷记卡-授信总额（元）"})
    private Object aa111;

    @ExcelAnnotation(id = 111, name = {"贷记卡-剩余本金（元）"})
    private Object aa222;

    @ExcelAnnotation(id = 112, name = {"贷记卡-负债月供（元）"})
    private Object aa333;

    @ExcelAnnotation(id = 113, name = {"准贷记卡-授信总额（元）"})
    private Object bb111;

    @ExcelAnnotation(id = 114, name = {"准贷记卡-剩余本金（元）"})
    private Object bb222;

    @ExcelAnnotation(id = 115, name = {"准贷记卡-负债月供（元）"})
    private Object bb333;

    @ExcelAnnotation(id = 116, name = {"贷款-授信总额（元）"})
    private Object cc111;

    @ExcelAnnotation(id = 117, name = {"贷款-剩余本金（元）"})
    private Object cc222;

    @ExcelAnnotation(id = 118, name = {"贷款-负债月供（元）"})
    private Object cc333;

    @ExcelAnnotation(id = 119, name = {"总计-授信总额（元）"})
    private Object dd111;

    @ExcelAnnotation(id = 120, name = {"总计-剩余本金（元）"})
    private Object dd222;

    @ExcelAnnotation(id = 121, name = {"总计-负债月供（元）"})
    private Object dd333;

    @ExcelAnnotation(id = 122, name = {"评估收入（元）"})
    private Object eeee2;

    @ExcelAnnotation(id = 123, name = {"DSR（%"})
    private Object ffff2;

    @ExcelAnnotation(id = 124, name = {"近1个月信用卡查询"})
    private Object gg111;

    @ExcelAnnotation(id = 125, name = {"近3个月信用卡查询"})
    private Object gg112;

    @ExcelAnnotation(id = 126, name = {"近1个月贷款查询"})
    private Object gg113;

    @ExcelAnnotation(id = 127, name = {"近3个月贷款查询"})
    private Object gg114;

    @ExcelAnnotation(id = 128, name = {"3个月内31+DPD"})
    private Object gg115;

    @ExcelAnnotation(id = 129, name = {"3个月内61+DPD"})
    private Object gg116;

    @ExcelAnnotation(id = 130, name = {"6个月内31+DPD"})
    private Object gg117;
    @ExcelAnnotation(id = 131, name = {"6个月内61+DPD"})
    private Object gg118;
    @ExcelAnnotation(id = 132, name = {"12个月内1+DPD"})
    private Object gg119;
    @ExcelAnnotation(id = 133, name = {"12个月内91+DPD"})
    private Object gg1110;

    //时间截点
    @ExcelAnnotation(id = 140, name = {"录入日期"})
    private Date createTime;
    @ExcelAnnotation(id = 141, name = {"进件日期"})
    private Date beginAuditDate;
    @ExcelAnnotation(id = 142, name = {"审核日期"})
    private Date endAuditDate;
    @ExcelAnnotation(id = 143, name = {"复核日期"})
    private Date endRecheckDate;
    @ExcelAnnotation(id = 144, name = {"签约日期"})
    private Date qyDate;
    @ExcelAnnotation(id = 145, name = {"放款日期"})
    private Date fdDate;
    @ExcelAnnotation(id = 146, name = {"首期还款日"})
    private Date sqhkDate;


    //审核/复核处理结果
    @ExcelAnnotation(id = 147, name = {"审核结果"})
    private String approveResult;
    @ExcelAnnotation(id = 148, name = {"审核额度"})
    private Double approveAmount;
    @ExcelAnnotation(id = 149, name = {"复核结果"})
    private String auditRecheckResult;
    @ExcelAnnotation(id = 150, name = {"复核额度"})
    private Double auditRecheckAmount;
    @ExcelAnnotation(id = 151, name = {"审批期限"})
    private Double auditRecheckDuration;
    @ExcelAnnotation(id = 152, name = {"签约金额"})
    private String qyje;
    @ExcelAnnotation(id = 153, name = {"签约期限"})
    private String qyqx;
    @ExcelAnnotation(id = 154, name = {"放款金额"})
    private String fkje;
    @ExcelAnnotation(id = 155, name = {"放款期限"})
    private String fkqx;
    @ExcelAnnotation(id = 156, name = {"首期还款日"})
    private String sqhkr;
    @ExcelAnnotation(id = 157, name = {"处理时间"})
    private String clsj;
    @ExcelAnnotation(id = 158, name = {"原因"})
    private String yy;
    @ExcelAnnotation(id = 159, name = {"一级拒绝原因"})
    private String resion1;
    @ExcelAnnotation(id = 160, name = {"二级拒绝原因"})
    private String resion2;
    @ExcelAnnotation(id = 161, name = {"三级拒绝原因"})
    private String resion3;

    //处理人员
    @ExcelAnnotation(id = 165, name = {"审核专员"})
    private String shzy;
    @ExcelAnnotation(id = 166, name = {"审核主管"})
    private String shzg;
    @ExcelAnnotation(id = 167, name = {"审批专员"})
    private String spzy;

    //贷后状态
    @ExcelAnnotation(id = 211, name = {"当前状态"})
    private String dqzt;
    @ExcelAnnotation(id = 212, name = {"已还期数"})
    private String yhqs;
    @ExcelAnnotation(id = 213, name = {"已还本金"})
    private String yhbj;
    @ExcelAnnotation(id = 214, name = {"剩余期数"})
    private String syqs;
    @ExcelAnnotation(id = 215, name = {"剩余本金"})
    private String sybj;
    @ExcelAnnotation(id = 216, name = {"逾期天数"})
    private String yqts;
    @ExcelAnnotation(id = 167, name = {"逾期金额"})
    private String yqje;

    public VReportAuditExtend() {
    }

    public VReportAuditExtend(VReportAudit vReportAudit) {
        BeanUtil.copyProperty(vReportAudit, this);
    }
}
