package com.starsgroupchina.credit.bean.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.starsgroupchina.common.base.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

@ApiModel
public class FormDetailUserHistory extends BaseModel implements Serializable {
    @ApiModelProperty("主键Id")
    private Integer id;

    @ApiModelProperty("")
    private Integer projectId;

    @ApiModelProperty("")
    private Integer orgId;

    @ApiModelProperty("")
    private Integer headOrgId;

    @ApiModelProperty("")
    private String projectNo;

    @ApiModelProperty("")
    private Short status;

    @ApiModelProperty("个人信息-姓名")
    private String b001;

    @ApiModelProperty("个人信息-性别")
    private String b002;

    @ApiModelProperty("个人信息-年龄")
    private String b003;

    @ApiModelProperty("个人信息-证件类型")
    private String b004;

    @ApiModelProperty("个人信息-证件号码")
    private String b005;

    @ApiModelProperty("个人信息-证件有效期")
    private String b006;

    @ApiModelProperty("个人信息-发证机关")
    private String b007;

    @ApiModelProperty("个人信息-户口类型")
    private String b008;

    @ApiModelProperty("个人信息-户口所在地")
    private String b009;

    @ApiModelProperty("个人信息-户籍地址")
    private String b010;

    @ApiModelProperty("个人信息-户籍地址邮编")
    private String b011;

    @ApiModelProperty("个人信息-籍贯")
    private String b012;

    @ApiModelProperty("个人信息-出生日期")
    private String b013;

    @ApiModelProperty("个人信息-最高学历")
    private String b014;

    @ApiModelProperty("个人信息-毕业学校")
    private String b015;

    @ApiModelProperty("个人信息-手机号码")
    private String b016;

    @ApiModelProperty("个人信息-婚姻状况")
    private String b017;

    @ApiModelProperty("个人信息-子女数目")
    private String b018;

    @ApiModelProperty("个人信息-最小子女年龄")
    private String b019;

    @ApiModelProperty("个人信息-最小子女性别")
    private String b020;

    @ApiModelProperty("个人信息-供养亲属数")
    private String b021;

    @ApiModelProperty("个人信息-现家庭地址")
    private String b022;

    @ApiModelProperty("个人信息-现家庭地址邮编")
    private String b023;

    @ApiModelProperty("个人信息-居住城市")
    private String b024;

    @ApiModelProperty("个人信息-住宅电话")
    private String b025;

    @ApiModelProperty("个人信息-住宅电话登记人")
    private String b026;

    @ApiModelProperty("个人信息-居住类型")
    private String b027;

    @ApiModelProperty("个人信息-共同居住人数")
    private String b028;

    @ApiModelProperty("个人信息-共同居住成员")
    private String b029;

    @ApiModelProperty("个人信息-现居住开始时间")
    private String b030;

    @ApiModelProperty("个人信息-来本市的年份")
    private String b031;

    @ApiModelProperty("个人信息-每月家庭支出")
    private String b032;

    @ApiModelProperty("个人信息-qq账号")
    private String b033;

    @ApiModelProperty("个人信息-电子邮箱")
    private String b034;

    @ApiModelProperty("个人信息-微信账号")
    private String b035;

    @ApiModelProperty("个人信息-微博账号")
    private String b036;

    @ApiModelProperty("单位信息-单位全称")
    private String c001;

    @ApiModelProperty("单位信息-单位地址")
    private String c002;

    @ApiModelProperty("单位信息-单位地址邮编")
    private String c003;

    @ApiModelProperty("单位信息-工作城市")
    private String c004;

    @ApiModelProperty("单位信息-单位电话")
    private String c005;

    @ApiModelProperty("单位信息-单位传真")
    private String c006;

    @ApiModelProperty("单位信息-单位性质")
    private String c007;

    @ApiModelProperty("单位信息-行业类型")
    private String c008;

    @ApiModelProperty("单位信息-任职部门")
    private String c009;

    @ApiModelProperty("单位信息-职务")
    private String c010;

    @ApiModelProperty("单位信息-工作级别")
    private String c011;

    @ApiModelProperty("单位信息-工作性质")
    private String c012;

    @ApiModelProperty("单位信息-现单位入职时间")
    private String c013;

    @ApiModelProperty("单位信息-月收入")
    private String c014;

    @ApiModelProperty("单位信息-发薪日")
    private String c015;

    @ApiModelProperty("单位信息-发薪方式")
    private String c016;

    @ApiModelProperty("单位信息-前单位名称")
    private String c017;

    @ApiModelProperty("单位信息-其他收入")
    private String c018;

    @ApiModelProperty("单位信息-每月总收入")
    private String c019;

    @ApiModelProperty("单位信息-首次参加工作时间")
    private String c020;

    @ApiModelProperty("单位信息-客户类别")
    private String c021;

    @ApiModelProperty("单位信息-工作职务")
    private String c023;

    @ApiModelProperty("工商信息-成立时间")
    private String d001;

    @ApiModelProperty("工商信息-注册时间")
    private String d002;

    @ApiModelProperty("工商信息-注册资金")
    private String d003;

    @ApiModelProperty("工商信息-实收资金")
    private String d004;

    @ApiModelProperty("工商信息-法人代表")
    private String d005;

    @ApiModelProperty("工商信息-客户身份")
    private String d006;

    @ApiModelProperty("工商信息-营业执照注册号")
    private String d007;

    @ApiModelProperty("工商信息-主营业务")
    private String d008;

    @ApiModelProperty("工商信息-年营业额")
    private String d009;

    @ApiModelProperty("工商信息-月营业额")
    private String d010;

    @ApiModelProperty("工商信息-毛利率")
    private String d011;

    @ApiModelProperty("工商信息-净利率")
    private String d012;

    @ApiModelProperty("工商信息-每月净利润额")
    private String d013;

    @ApiModelProperty("工商信息-月开销金额")
    private String d014;

    @ApiModelProperty("工商信息-员工人数")
    private String d015;

    @ApiModelProperty("工商信息-占股比例")
    private String d016;

    @ApiModelProperty("工商信息-经营场所性质")
    private String d017;

    @ApiModelProperty("工商信息-出租方电话")
    private String d018;

    @ApiModelProperty("工商信息-租金（元/月）")
    private String d019;

    @ApiModelProperty("工商信息-公司网址")
    private String d020;

    @ApiModelProperty("工商信息-上年度营业收入")
    private String d021;

    @ApiModelProperty("工商信息-上年度营业利润")
    private String d022;

    @ApiModelProperty("工商信息-其他收入")
    private String d023;

    @ApiModelProperty("工商信息-营业执照")
    private String d024;

    @ApiModelProperty("是否有对公流水")
    private String d025;

    @ApiModelProperty("是否有抵押房产")
    private String d026;

    @ApiModelProperty("房产信息-房产数量")
    private String e001;

    @ApiModelProperty("房产信息-房产地址")
    private String e002;

    @ApiModelProperty("房产信息-房产地址邮编")
    private String e003;

    @ApiModelProperty("房产信息-房产所属区域")
    private String e004;

    @ApiModelProperty("房产信息-房产编号")
    private String e005;

    @ApiModelProperty("房产信息-房产类型")
    private String e006;

    @ApiModelProperty("房产信息-产权比例")
    private String e007;

    @ApiModelProperty("房产信息-产权人")
    private String e008;

    @ApiModelProperty("房产信息-房产状态")
    private String e009;

    @ApiModelProperty("房产信息-购买方式")
    private String e010;

    @ApiModelProperty("房产信息-购买时间")
    private String e011;

    @ApiModelProperty("房产信息-购买价格")
    private String e012;

    @ApiModelProperty("房产信息-购买单价")
    private String e013;

    @ApiModelProperty("房产信息-建筑年份")
    private String e014;

    @ApiModelProperty("房产信息-建筑面积")
    private String e015;

    @ApiModelProperty("房产信息-现评估价格")
    private String e016;

    @ApiModelProperty("房产信息-有无贷款")
    private String e017;

    @ApiModelProperty("房产信息-贷款银行")
    private String e018;

    @ApiModelProperty("房产信息-贷款金额")
    private String e019;

    @ApiModelProperty("房产信息-贷款期限")
    private String e020;

    @ApiModelProperty("房产信息-月还款额")
    private String e021;

    @ApiModelProperty("房产信息-每月还款日")
    private String e022;

    @ApiModelProperty("房产信息-其他房产")
    private String e023;

    @ApiModelProperty("房产信息-其他房产地址")
    private String e024;

    @ApiModelProperty("房产信息-其他房产地址邮编")
    private String e025;

    @ApiModelProperty("房产信息-其他房产所属区域")
    private String e026;

    @ApiModelProperty("房产信息-其他房产编号")
    private String e027;

    @ApiModelProperty("房产信息-其他房产类型")
    private String e028;

    @ApiModelProperty("房产信息-其他房产产权比例")
    private String e029;

    @ApiModelProperty("房产信息-其他房产产权人")
    private String e030;

    @ApiModelProperty("房产信息-其他房产购买方式")
    private String e031;

    @ApiModelProperty("房产信息-其他房产购买时间")
    private String e032;

    @ApiModelProperty("房产信息-其他房产购买价格")
    private String e033;

    @ApiModelProperty("房产信息-其他房产购买单价")
    private String e034;

    @ApiModelProperty("房产信息-其他房产建筑年份")
    private String e035;

    @ApiModelProperty("房产信息-其他房产建筑面积")
    private String e036;

    @ApiModelProperty("房产信息-其他房产现实际评估价格")
    private String e037;

    @ApiModelProperty("房产信息-其他房产有无贷款")
    private String e038;

    @ApiModelProperty("房产信息-其他房产贷款银行")
    private String e039;

    @ApiModelProperty("房产信息-其他房产贷款金额")
    private String e040;

    @ApiModelProperty("房产信息-其他房产贷款期限")
    private String e041;

    @ApiModelProperty("房产信息-其他房产月还款额")
    private String e042;

    @ApiModelProperty("房产信息-其他房产每月还款日")
    private String e043;

    @ApiModelProperty("房产信息-已还期数")
    private String e045;

    @ApiModelProperty("负债信息-信用卡张数")
    private String f001;

    @ApiModelProperty("负债信息-信用卡当前欠款")
    private String f002;

    @ApiModelProperty("负债信息-单张信用卡最高额度")
    private String f003;

    @ApiModelProperty("负债信息-信用卡月还款额")
    private String f004;

    @ApiModelProperty("负债信息-信用贷款总额度")
    private String f005;

    @ApiModelProperty("负债信息-信用贷款笔数")
    private String f006;

    @ApiModelProperty("负债信息-信用贷款欠款")
    private String f007;

    @ApiModelProperty("负债信息-信用贷款月还款额")
    private String f008;

    @ApiModelProperty("负债信息-贷款总额度")
    private String f009;

    @ApiModelProperty("负债信息-贷款笔数")
    private String f010;

    @ApiModelProperty("负债信息-贷款欠款")
    private String f011;

    @ApiModelProperty("负债信息-贷款月还款额")
    private String f012;

    @ApiModelProperty("芝麻评分")
    private String f013;

    @ApiModelProperty("负债信息-信用卡总额")
    private String f0012;

    @ApiModelProperty("车辆信息-车牌号码")
    private String g001;

    @ApiModelProperty("车辆信息-品牌型号")
    private String g002;

    @ApiModelProperty("车辆信息-车架号")
    private String g003;

    @ApiModelProperty("车辆信息-行驶里程")
    private String g004;

    @ApiModelProperty("车辆信息-购车类型")
    private String g005;

    @ApiModelProperty("车辆信息-购买时间")
    private String g006;

    @ApiModelProperty("车辆信息-购买价格")
    private String g007;

    @ApiModelProperty("车辆信息-购买方式")
    private String g008;

    @ApiModelProperty("车辆信息-评估价格")
    private String g009;

    @ApiModelProperty("车辆信息-保险单")
    private String g010;

    @ApiModelProperty("车辆信息-是否有车辆贷款历史")
    private String g011;

    @ApiModelProperty("车辆信息-贷款机构")
    private String g012;

    @ApiModelProperty("车辆信息-贷款金额")
    private String g013;

    @ApiModelProperty("车辆信息-贷款日期")
    private String g014;

    @ApiModelProperty("车辆信息-结清日期")
    private String g015;

    @ApiModelProperty("车辆信息-月还款额")
    private String g016;

    @ApiModelProperty("车辆信息-是否有逾期")
    private String g017;

    @ApiModelProperty("车辆信息-家庭共有几辆车")
    private String g018;

    @ApiModelProperty("同行业信息-产品名称")
    private String h001;

    @ApiModelProperty("同行业信息-公司名称")
    private String h002;

    @ApiModelProperty("同行业信息-借款金额")
    private String h003;

    @ApiModelProperty("同行业信息-借款期限")
    private String h004;

    @ApiModelProperty("同行业信息-月还款额")
    private String h005;

    @ApiModelProperty("同行业信息-已还款期数")
    private String h006;

    @ApiModelProperty("同行业信息-已还款金额")
    private String h007;

    @ApiModelProperty("同行业信息-行业类型")
    private String h008;

    @ApiModelProperty("务农人员信息-从业年限")
    private String i001;

    @ApiModelProperty("务农人员信息-生产工具")
    private String i002;

    @ApiModelProperty("务农人员信息-主营产品")
    private String i003;

    @ApiModelProperty("务农人员信息-月收入")
    private String i004;

    @ApiModelProperty("务农人员信息-学校名称")
    private String i005;

    @ApiModelProperty("学生信息-学校类型")
    private String j001;

    @ApiModelProperty("学生信息-专业名称")
    private String j002;

    @ApiModelProperty("学生信息-入学时间")
    private String j003;

    @ApiModelProperty("学生信息-预计毕业时间")
    private String j004;

    @ApiModelProperty("学生信息-生活费/月")
    private String j005;

    @ApiModelProperty("社保信息-是否有社保卡")
    private String k001;

    @ApiModelProperty("社保信息-参保单位")
    private String k002;

    @ApiModelProperty("社保信息-个人社保电脑编号")
    private String k003;

    @ApiModelProperty("社保信息-现单位缴纳起始日")
    private String k004;

    @ApiModelProperty("社保信息-社保缴纳基数")
    private String k005;

    @ApiModelProperty("社保信息-公积金缴纳基数")
    private String k006;

    @ApiModelProperty("社保信息-社保累计缴纳月数")
    private String k007;

    @ApiModelProperty("保单信息-保险公司")
    private String l001;

    @ApiModelProperty("保单信息-保单号")
    private String l002;

    @ApiModelProperty("保单信息-险种名称")
    private String l003;

    @ApiModelProperty("保单信息-约定缴费年限")
    private String l004;

    @ApiModelProperty("保单信息-首次投保时间")
    private String l005;

    @ApiModelProperty("保单信息-到期时间")
    private String l006;

    @ApiModelProperty("保单信息-缴费金额")
    private String l007;

    @ApiModelProperty("保单信息-总保额")
    private String l008;

    @ApiModelProperty("保单信息-缴费方式")
    private String l009;

    @ApiModelProperty("保单信息-受益人姓名")
    private String l010;

    @ApiModelProperty("保单信息-受益人身份证")
    private String l011;

    @ApiModelProperty("保单信息-与受益人关系")
    private String l012;

    @ApiModelProperty("保单信息-受益人手机号码")
    private String l013;

    @ApiModelProperty("保单信息-被保险人姓名")
    private String l014;

    @ApiModelProperty("保单信息-被保险人身份证")
    private String l015;

    @ApiModelProperty("保单信息-与被保险人关系")
    private String l016;

    @ApiModelProperty("保单信息-被保险人手机号码")
    private String l017;

    @ApiModelProperty("保单信息-已缴期数")
    private String l018;

    @ApiModelProperty("担保人信息-担保人与客户关系")
    private String m001;

    @ApiModelProperty("担保人信息-担保人姓名")
    private String m002;

    @ApiModelProperty("担保人信息-担保人手机号码")
    private String m003;

    @ApiModelProperty("担保人信息-担保人居住地址")
    private String m004;

    @ApiModelProperty("担保人信息-担保人公司名称")
    private String m005;

    @ApiModelProperty("担保人信息-担保人职位")
    private String m006;

    @ApiModelProperty("担保人信息-担保人公司电话")
    private String m007;

    @ApiModelProperty("担保人信息-担保人身份证号")
    private String m008;

    @ApiModelProperty("担保人信息-担保人婚姻状况")
    private String m009;

    @ApiModelProperty("担保人信息-担保人公司部门")
    private String m010;

    @ApiModelProperty("担保人信息-担保人公司地址")
    private String m011;

    @ApiModelProperty("担保人信息-担保人公司分机")
    private String m012;

    @ApiModelProperty("配偶信息-姓名")
    private String n001;

    @ApiModelProperty("配偶信息-手机")
    private String n002;

    @ApiModelProperty("配偶信息-身份证号码")
    private String n003;

    @ApiModelProperty("配偶信息-固话")
    private String n004;

    @ApiModelProperty("配偶信息-住址电话")
    private String n005;

    @ApiModelProperty("配偶信息-单位名称")
    private String n006;

    @ApiModelProperty("配偶信息-单位地址")
    private String n007;

    @ApiModelProperty("配偶信息-单位电话")
    private String n008;

    @ApiModelProperty("配偶信息-是否知晓贷款")
    private String n009;

    @ApiModelProperty("联系人1-姓名")
    private String pa001;

    @ApiModelProperty("联系人1-关系")
    private String pa002;

    @ApiModelProperty("联系人1-手机")
    private String pa003;

    @ApiModelProperty("联系人1-固话")
    private String pa004;

    @ApiModelProperty("联系人1-住址")
    private String pa005;

    @ApiModelProperty("联系人1-是否知晓贷款")
    private String pa006;

    @ApiModelProperty("联系人1-单位名称")
    private String pa007;

    @ApiModelProperty("联系人1-单位地址")
    private String pa008;

    @ApiModelProperty("联系人1-单位电话")
    private String pa009;

    @ApiModelProperty("联系人2-姓名")
    private String pb001;

    @ApiModelProperty("联系人2-关系")
    private String pb002;

    @ApiModelProperty("联系人2-手机")
    private String pb003;

    @ApiModelProperty("联系人2-固话")
    private String pb004;

    @ApiModelProperty("联系人2-住址")
    private String pb005;

    @ApiModelProperty("联系人2-是否知晓贷款")
    private String pb006;

    @ApiModelProperty("联系人2-单位名称")
    private String pb007;

    @ApiModelProperty("联系人2-单位地址")
    private String pb008;

    @ApiModelProperty("联系人2-单位电话")
    private String pb009;

    @ApiModelProperty("联系人3-姓名")
    private String pc001;

    @ApiModelProperty("联系人3-关系")
    private String pc002;

    @ApiModelProperty("联系人3-手机")
    private String pc003;

    @ApiModelProperty("联系人3-固话")
    private String pc004;

    @ApiModelProperty("联系人3-住址")
    private String pc005;

    @ApiModelProperty("联系人3-是否知晓贷款")
    private String pc006;

    @ApiModelProperty("联系人3-单位名称")
    private String pc007;

    @ApiModelProperty("联系人3-单位地址")
    private String pc008;

    @ApiModelProperty("联系人3-单位电话")
    private String pc009;

    @ApiModelProperty("联系人4-姓名")
    private String pd001;

    @ApiModelProperty("联系人4-关系")
    private String pd002;

    @ApiModelProperty("联系人4-手机")
    private String pd003;

    @ApiModelProperty("联系人4-固话")
    private String pd004;

    @ApiModelProperty("联系人4-住址")
    private String pd005;

    @ApiModelProperty("联系人4-是否知晓贷款")
    private String pd006;

    @ApiModelProperty("联系人4-单位名称")
    private String pd007;

    @ApiModelProperty("联系人4-单位地址")
    private String pd008;

    @ApiModelProperty("联系人4-单位电话")
    private String pd009;

    @ApiModelProperty("业务信息-营业部")
    private String q001;

    @ApiModelProperty("业务信息-营业部地址")
    private String q002;

    @ApiModelProperty("业务信息-所属区域")
    private String q003;

    @ApiModelProperty("业务信息-业务团队")
    private String q004;

    @ApiModelProperty("业务信息-渠道名称")
    private String q005;

    @ApiModelProperty("业务信息-渠道代码")
    private String q006;

    @ApiModelProperty("业务信息-客户经理")
    private String q007;

    @ApiModelProperty("业务信息-联系电话")
    private String q008;

    @ApiModelProperty("业务信息-团队主任")
    private String q009;

    @ApiModelProperty("业务信息-员工编号")
    private String q010;

    @ApiModelProperty("业务信息-对本公司的了解渠道")
    private String q011;

    @ApiModelProperty("业务信息-客户来源")
    private String q012;

    @ApiModelProperty("资料清单-基本资料")
    private String r001;

    @ApiModelProperty("腾讯/阿里/京东-微粒贷授信金额")
    private String s001;

    @ApiModelProperty("腾讯/阿里/京东-花呗/借呗授信金额")
    private String s002;

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

    @ApiModelProperty("产品：腾讯/阿里/京东")
    private String s010;

    @ApiModelProperty("授信金额")
    private String s011;

    @ApiModelProperty("附件上传-附件上传方式")
    private String t001;

    @ApiModelProperty("征信信息-近7天征信查询次数")
    private String u001;

    @ApiModelProperty("征信信息-近1个月征信查询次数")
    private String u002;

    @ApiModelProperty("营业部所在城市")
    private String q0022;

    @ApiModelProperty("征信信息-近3个月征信查询次数")
    private String u003;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table form_detail_user_history
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

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
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

    public String getProjectNo() {
        return projectNo;
    }

    public void setProjectNo(String projectNo) {
        this.projectNo = projectNo == null ? null : projectNo.trim();
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public String getB001() {
        return b001;
    }

    public void setB001(String b001) {
        this.b001 = b001 == null ? null : b001.trim();
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

    public String getB004() {
        return b004;
    }

    public void setB004(String b004) {
        this.b004 = b004 == null ? null : b004.trim();
    }

    public String getB005() {
        return b005;
    }

    public void setB005(String b005) {
        this.b005 = b005 == null ? null : b005.trim();
    }

    public String getB006() {
        return b006;
    }

    public void setB006(String b006) {
        this.b006 = b006 == null ? null : b006.trim();
    }

    public String getB007() {
        return b007;
    }

    public void setB007(String b007) {
        this.b007 = b007 == null ? null : b007.trim();
    }

    public String getB008() {
        return b008;
    }

    public void setB008(String b008) {
        this.b008 = b008 == null ? null : b008.trim();
    }

    public String getB009() {
        return b009;
    }

    public void setB009(String b009) {
        this.b009 = b009 == null ? null : b009.trim();
    }

    public String getB010() {
        return b010;
    }

    public void setB010(String b010) {
        this.b010 = b010 == null ? null : b010.trim();
    }

    public String getB011() {
        return b011;
    }

    public void setB011(String b011) {
        this.b011 = b011 == null ? null : b011.trim();
    }

    public String getB012() {
        return b012;
    }

    public void setB012(String b012) {
        this.b012 = b012 == null ? null : b012.trim();
    }

    public String getB013() {
        return b013;
    }

    public void setB013(String b013) {
        this.b013 = b013 == null ? null : b013.trim();
    }

    public String getB014() {
        return b014;
    }

    public void setB014(String b014) {
        this.b014 = b014 == null ? null : b014.trim();
    }

    public String getB015() {
        return b015;
    }

    public void setB015(String b015) {
        this.b015 = b015 == null ? null : b015.trim();
    }

    public String getB016() {
        return b016;
    }

    public void setB016(String b016) {
        this.b016 = b016 == null ? null : b016.trim();
    }

    public String getB017() {
        return b017;
    }

    public void setB017(String b017) {
        this.b017 = b017 == null ? null : b017.trim();
    }

    public String getB018() {
        return b018;
    }

    public void setB018(String b018) {
        this.b018 = b018 == null ? null : b018.trim();
    }

    public String getB019() {
        return b019;
    }

    public void setB019(String b019) {
        this.b019 = b019 == null ? null : b019.trim();
    }

    public String getB020() {
        return b020;
    }

    public void setB020(String b020) {
        this.b020 = b020 == null ? null : b020.trim();
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

    public String getB023() {
        return b023;
    }

    public void setB023(String b023) {
        this.b023 = b023 == null ? null : b023.trim();
    }

    public String getB024() {
        return b024;
    }

    public void setB024(String b024) {
        this.b024 = b024 == null ? null : b024.trim();
    }

    public String getB025() {
        return b025;
    }

    public void setB025(String b025) {
        this.b025 = b025 == null ? null : b025.trim();
    }

    public String getB026() {
        return b026;
    }

    public void setB026(String b026) {
        this.b026 = b026 == null ? null : b026.trim();
    }

    public String getB027() {
        return b027;
    }

    public void setB027(String b027) {
        this.b027 = b027 == null ? null : b027.trim();
    }

    public String getB028() {
        return b028;
    }

    public void setB028(String b028) {
        this.b028 = b028 == null ? null : b028.trim();
    }

    public String getB029() {
        return b029;
    }

    public void setB029(String b029) {
        this.b029 = b029 == null ? null : b029.trim();
    }

    public String getB030() {
        return b030;
    }

    public void setB030(String b030) {
        this.b030 = b030 == null ? null : b030.trim();
    }

    public String getB031() {
        return b031;
    }

    public void setB031(String b031) {
        this.b031 = b031 == null ? null : b031.trim();
    }

    public String getB032() {
        return b032;
    }

    public void setB032(String b032) {
        this.b032 = b032 == null ? null : b032.trim();
    }

    public String getB033() {
        return b033;
    }

    public void setB033(String b033) {
        this.b033 = b033 == null ? null : b033.trim();
    }

    public String getB034() {
        return b034;
    }

    public void setB034(String b034) {
        this.b034 = b034 == null ? null : b034.trim();
    }

    public String getB035() {
        return b035;
    }

    public void setB035(String b035) {
        this.b035 = b035 == null ? null : b035.trim();
    }

    public String getB036() {
        return b036;
    }

    public void setB036(String b036) {
        this.b036 = b036 == null ? null : b036.trim();
    }

    public String getC001() {
        return c001;
    }

    public void setC001(String c001) {
        this.c001 = c001 == null ? null : c001.trim();
    }

    public String getC002() {
        return c002;
    }

    public void setC002(String c002) {
        this.c002 = c002 == null ? null : c002.trim();
    }

    public String getC003() {
        return c003;
    }

    public void setC003(String c003) {
        this.c003 = c003 == null ? null : c003.trim();
    }

    public String getC004() {
        return c004;
    }

    public void setC004(String c004) {
        this.c004 = c004 == null ? null : c004.trim();
    }

    public String getC005() {
        return c005;
    }

    public void setC005(String c005) {
        this.c005 = c005 == null ? null : c005.trim();
    }

    public String getC006() {
        return c006;
    }

    public void setC006(String c006) {
        this.c006 = c006 == null ? null : c006.trim();
    }

    public String getC007() {
        return c007;
    }

    public void setC007(String c007) {
        this.c007 = c007 == null ? null : c007.trim();
    }

    public String getC008() {
        return c008;
    }

    public void setC008(String c008) {
        this.c008 = c008 == null ? null : c008.trim();
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

    public String getC011() {
        return c011;
    }

    public void setC011(String c011) {
        this.c011 = c011 == null ? null : c011.trim();
    }

    public String getC012() {
        return c012;
    }

    public void setC012(String c012) {
        this.c012 = c012 == null ? null : c012.trim();
    }

    public String getC013() {
        return c013;
    }

    public void setC013(String c013) {
        this.c013 = c013 == null ? null : c013.trim();
    }

    public String getC014() {
        return c014;
    }

    public void setC014(String c014) {
        this.c014 = c014 == null ? null : c014.trim();
    }

    public String getC015() {
        return c015;
    }

    public void setC015(String c015) {
        this.c015 = c015 == null ? null : c015.trim();
    }

    public String getC016() {
        return c016;
    }

    public void setC016(String c016) {
        this.c016 = c016 == null ? null : c016.trim();
    }

    public String getC017() {
        return c017;
    }

    public void setC017(String c017) {
        this.c017 = c017 == null ? null : c017.trim();
    }

    public String getC018() {
        return c018;
    }

    public void setC018(String c018) {
        this.c018 = c018 == null ? null : c018.trim();
    }

    public String getC019() {
        return c019;
    }

    public void setC019(String c019) {
        this.c019 = c019 == null ? null : c019.trim();
    }

    public String getC020() {
        return c020;
    }

    public void setC020(String c020) {
        this.c020 = c020 == null ? null : c020.trim();
    }

    public String getC021() {
        return c021;
    }

    public void setC021(String c021) {
        this.c021 = c021 == null ? null : c021.trim();
    }

    public String getC023() {
        return c023;
    }

    public void setC023(String c023) {
        this.c023 = c023 == null ? null : c023.trim();
    }

    public String getD001() {
        return d001;
    }

    public void setD001(String d001) {
        this.d001 = d001 == null ? null : d001.trim();
    }

    public String getD002() {
        return d002;
    }

    public void setD002(String d002) {
        this.d002 = d002 == null ? null : d002.trim();
    }

    public String getD003() {
        return d003;
    }

    public void setD003(String d003) {
        this.d003 = d003 == null ? null : d003.trim();
    }

    public String getD004() {
        return d004;
    }

    public void setD004(String d004) {
        this.d004 = d004 == null ? null : d004.trim();
    }

    public String getD005() {
        return d005;
    }

    public void setD005(String d005) {
        this.d005 = d005 == null ? null : d005.trim();
    }

    public String getD006() {
        return d006;
    }

    public void setD006(String d006) {
        this.d006 = d006 == null ? null : d006.trim();
    }

    public String getD007() {
        return d007;
    }

    public void setD007(String d007) {
        this.d007 = d007 == null ? null : d007.trim();
    }

    public String getD008() {
        return d008;
    }

    public void setD008(String d008) {
        this.d008 = d008 == null ? null : d008.trim();
    }

    public String getD009() {
        return d009;
    }

    public void setD009(String d009) {
        this.d009 = d009 == null ? null : d009.trim();
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

    public String getD012() {
        return d012;
    }

    public void setD012(String d012) {
        this.d012 = d012 == null ? null : d012.trim();
    }

    public String getD013() {
        return d013;
    }

    public void setD013(String d013) {
        this.d013 = d013 == null ? null : d013.trim();
    }

    public String getD014() {
        return d014;
    }

    public void setD014(String d014) {
        this.d014 = d014 == null ? null : d014.trim();
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

    public String getD017() {
        return d017;
    }

    public void setD017(String d017) {
        this.d017 = d017 == null ? null : d017.trim();
    }

    public String getD018() {
        return d018;
    }

    public void setD018(String d018) {
        this.d018 = d018 == null ? null : d018.trim();
    }

    public String getD019() {
        return d019;
    }

    public void setD019(String d019) {
        this.d019 = d019 == null ? null : d019.trim();
    }

    public String getD020() {
        return d020;
    }

    public void setD020(String d020) {
        this.d020 = d020 == null ? null : d020.trim();
    }

    public String getD021() {
        return d021;
    }

    public void setD021(String d021) {
        this.d021 = d021 == null ? null : d021.trim();
    }

    public String getD022() {
        return d022;
    }

    public void setD022(String d022) {
        this.d022 = d022 == null ? null : d022.trim();
    }

    public String getD023() {
        return d023;
    }

    public void setD023(String d023) {
        this.d023 = d023 == null ? null : d023.trim();
    }

    public String getD024() {
        return d024;
    }

    public void setD024(String d024) {
        this.d024 = d024 == null ? null : d024.trim();
    }

    public String getD025() {
        return d025;
    }

    public void setD025(String d025) {
        this.d025 = d025 == null ? null : d025.trim();
    }

    public String getD026() {
        return d026;
    }

    public void setD026(String d026) {
        this.d026 = d026 == null ? null : d026.trim();
    }

    public String getE001() {
        return e001;
    }

    public void setE001(String e001) {
        this.e001 = e001 == null ? null : e001.trim();
    }

    public String getE002() {
        return e002;
    }

    public void setE002(String e002) {
        this.e002 = e002 == null ? null : e002.trim();
    }

    public String getE003() {
        return e003;
    }

    public void setE003(String e003) {
        this.e003 = e003 == null ? null : e003.trim();
    }

    public String getE004() {
        return e004;
    }

    public void setE004(String e004) {
        this.e004 = e004 == null ? null : e004.trim();
    }

    public String getE005() {
        return e005;
    }

    public void setE005(String e005) {
        this.e005 = e005 == null ? null : e005.trim();
    }

    public String getE006() {
        return e006;
    }

    public void setE006(String e006) {
        this.e006 = e006 == null ? null : e006.trim();
    }

    public String getE007() {
        return e007;
    }

    public void setE007(String e007) {
        this.e007 = e007 == null ? null : e007.trim();
    }

    public String getE008() {
        return e008;
    }

    public void setE008(String e008) {
        this.e008 = e008 == null ? null : e008.trim();
    }

    public String getE009() {
        return e009;
    }

    public void setE009(String e009) {
        this.e009 = e009 == null ? null : e009.trim();
    }

    public String getE010() {
        return e010;
    }

    public void setE010(String e010) {
        this.e010 = e010 == null ? null : e010.trim();
    }

    public String getE011() {
        return e011;
    }

    public void setE011(String e011) {
        this.e011 = e011 == null ? null : e011.trim();
    }

    public String getE012() {
        return e012;
    }

    public void setE012(String e012) {
        this.e012 = e012 == null ? null : e012.trim();
    }

    public String getE013() {
        return e013;
    }

    public void setE013(String e013) {
        this.e013 = e013 == null ? null : e013.trim();
    }

    public String getE014() {
        return e014;
    }

    public void setE014(String e014) {
        this.e014 = e014 == null ? null : e014.trim();
    }

    public String getE015() {
        return e015;
    }

    public void setE015(String e015) {
        this.e015 = e015 == null ? null : e015.trim();
    }

    public String getE016() {
        return e016;
    }

    public void setE016(String e016) {
        this.e016 = e016 == null ? null : e016.trim();
    }

    public String getE017() {
        return e017;
    }

    public void setE017(String e017) {
        this.e017 = e017 == null ? null : e017.trim();
    }

    public String getE018() {
        return e018;
    }

    public void setE018(String e018) {
        this.e018 = e018 == null ? null : e018.trim();
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

    public String getE022() {
        return e022;
    }

    public void setE022(String e022) {
        this.e022 = e022 == null ? null : e022.trim();
    }

    public String getE023() {
        return e023;
    }

    public void setE023(String e023) {
        this.e023 = e023 == null ? null : e023.trim();
    }

    public String getE024() {
        return e024;
    }

    public void setE024(String e024) {
        this.e024 = e024 == null ? null : e024.trim();
    }

    public String getE025() {
        return e025;
    }

    public void setE025(String e025) {
        this.e025 = e025 == null ? null : e025.trim();
    }

    public String getE026() {
        return e026;
    }

    public void setE026(String e026) {
        this.e026 = e026 == null ? null : e026.trim();
    }

    public String getE027() {
        return e027;
    }

    public void setE027(String e027) {
        this.e027 = e027 == null ? null : e027.trim();
    }

    public String getE028() {
        return e028;
    }

    public void setE028(String e028) {
        this.e028 = e028 == null ? null : e028.trim();
    }

    public String getE029() {
        return e029;
    }

    public void setE029(String e029) {
        this.e029 = e029 == null ? null : e029.trim();
    }

    public String getE030() {
        return e030;
    }

    public void setE030(String e030) {
        this.e030 = e030 == null ? null : e030.trim();
    }

    public String getE031() {
        return e031;
    }

    public void setE031(String e031) {
        this.e031 = e031 == null ? null : e031.trim();
    }

    public String getE032() {
        return e032;
    }

    public void setE032(String e032) {
        this.e032 = e032 == null ? null : e032.trim();
    }

    public String getE033() {
        return e033;
    }

    public void setE033(String e033) {
        this.e033 = e033 == null ? null : e033.trim();
    }

    public String getE034() {
        return e034;
    }

    public void setE034(String e034) {
        this.e034 = e034 == null ? null : e034.trim();
    }

    public String getE035() {
        return e035;
    }

    public void setE035(String e035) {
        this.e035 = e035 == null ? null : e035.trim();
    }

    public String getE036() {
        return e036;
    }

    public void setE036(String e036) {
        this.e036 = e036 == null ? null : e036.trim();
    }

    public String getE037() {
        return e037;
    }

    public void setE037(String e037) {
        this.e037 = e037 == null ? null : e037.trim();
    }

    public String getE038() {
        return e038;
    }

    public void setE038(String e038) {
        this.e038 = e038 == null ? null : e038.trim();
    }

    public String getE039() {
        return e039;
    }

    public void setE039(String e039) {
        this.e039 = e039 == null ? null : e039.trim();
    }

    public String getE040() {
        return e040;
    }

    public void setE040(String e040) {
        this.e040 = e040 == null ? null : e040.trim();
    }

    public String getE041() {
        return e041;
    }

    public void setE041(String e041) {
        this.e041 = e041 == null ? null : e041.trim();
    }

    public String getE042() {
        return e042;
    }

    public void setE042(String e042) {
        this.e042 = e042 == null ? null : e042.trim();
    }

    public String getE043() {
        return e043;
    }

    public void setE043(String e043) {
        this.e043 = e043 == null ? null : e043.trim();
    }

    public String getE045() {
        return e045;
    }

    public void setE045(String e045) {
        this.e045 = e045 == null ? null : e045.trim();
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

    public String getF003() {
        return f003;
    }

    public void setF003(String f003) {
        this.f003 = f003 == null ? null : f003.trim();
    }

    public String getF004() {
        return f004;
    }

    public void setF004(String f004) {
        this.f004 = f004 == null ? null : f004.trim();
    }

    public String getF005() {
        return f005;
    }

    public void setF005(String f005) {
        this.f005 = f005 == null ? null : f005.trim();
    }

    public String getF006() {
        return f006;
    }

    public void setF006(String f006) {
        this.f006 = f006 == null ? null : f006.trim();
    }

    public String getF007() {
        return f007;
    }

    public void setF007(String f007) {
        this.f007 = f007 == null ? null : f007.trim();
    }

    public String getF008() {
        return f008;
    }

    public void setF008(String f008) {
        this.f008 = f008 == null ? null : f008.trim();
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

    public String getF012() {
        return f012;
    }

    public void setF012(String f012) {
        this.f012 = f012 == null ? null : f012.trim();
    }

    public String getF013() {
        return f013;
    }

    public void setF013(String f013) {
        this.f013 = f013 == null ? null : f013.trim();
    }

    public String getF0012() {
        return f0012;
    }

    public void setF0012(String f0012) {
        this.f0012 = f0012 == null ? null : f0012.trim();
    }

    public String getG001() {
        return g001;
    }

    public void setG001(String g001) {
        this.g001 = g001 == null ? null : g001.trim();
    }

    public String getG002() {
        return g002;
    }

    public void setG002(String g002) {
        this.g002 = g002 == null ? null : g002.trim();
    }

    public String getG003() {
        return g003;
    }

    public void setG003(String g003) {
        this.g003 = g003 == null ? null : g003.trim();
    }

    public String getG004() {
        return g004;
    }

    public void setG004(String g004) {
        this.g004 = g004 == null ? null : g004.trim();
    }

    public String getG005() {
        return g005;
    }

    public void setG005(String g005) {
        this.g005 = g005 == null ? null : g005.trim();
    }

    public String getG006() {
        return g006;
    }

    public void setG006(String g006) {
        this.g006 = g006 == null ? null : g006.trim();
    }

    public String getG007() {
        return g007;
    }

    public void setG007(String g007) {
        this.g007 = g007 == null ? null : g007.trim();
    }

    public String getG008() {
        return g008;
    }

    public void setG008(String g008) {
        this.g008 = g008 == null ? null : g008.trim();
    }

    public String getG009() {
        return g009;
    }

    public void setG009(String g009) {
        this.g009 = g009 == null ? null : g009.trim();
    }

    public String getG010() {
        return g010;
    }

    public void setG010(String g010) {
        this.g010 = g010 == null ? null : g010.trim();
    }

    public String getG011() {
        return g011;
    }

    public void setG011(String g011) {
        this.g011 = g011 == null ? null : g011.trim();
    }

    public String getG012() {
        return g012;
    }

    public void setG012(String g012) {
        this.g012 = g012 == null ? null : g012.trim();
    }

    public String getG013() {
        return g013;
    }

    public void setG013(String g013) {
        this.g013 = g013 == null ? null : g013.trim();
    }

    public String getG014() {
        return g014;
    }

    public void setG014(String g014) {
        this.g014 = g014 == null ? null : g014.trim();
    }

    public String getG015() {
        return g015;
    }

    public void setG015(String g015) {
        this.g015 = g015 == null ? null : g015.trim();
    }

    public String getG016() {
        return g016;
    }

    public void setG016(String g016) {
        this.g016 = g016 == null ? null : g016.trim();
    }

    public String getG017() {
        return g017;
    }

    public void setG017(String g017) {
        this.g017 = g017 == null ? null : g017.trim();
    }

    public String getG018() {
        return g018;
    }

    public void setG018(String g018) {
        this.g018 = g018 == null ? null : g018.trim();
    }

    public String getH001() {
        return h001;
    }

    public void setH001(String h001) {
        this.h001 = h001 == null ? null : h001.trim();
    }

    public String getH002() {
        return h002;
    }

    public void setH002(String h002) {
        this.h002 = h002 == null ? null : h002.trim();
    }

    public String getH003() {
        return h003;
    }

    public void setH003(String h003) {
        this.h003 = h003 == null ? null : h003.trim();
    }

    public String getH004() {
        return h004;
    }

    public void setH004(String h004) {
        this.h004 = h004 == null ? null : h004.trim();
    }

    public String getH005() {
        return h005;
    }

    public void setH005(String h005) {
        this.h005 = h005 == null ? null : h005.trim();
    }

    public String getH006() {
        return h006;
    }

    public void setH006(String h006) {
        this.h006 = h006 == null ? null : h006.trim();
    }

    public String getH007() {
        return h007;
    }

    public void setH007(String h007) {
        this.h007 = h007 == null ? null : h007.trim();
    }

    public String getH008() {
        return h008;
    }

    public void setH008(String h008) {
        this.h008 = h008 == null ? null : h008.trim();
    }

    public String getI001() {
        return i001;
    }

    public void setI001(String i001) {
        this.i001 = i001 == null ? null : i001.trim();
    }

    public String getI002() {
        return i002;
    }

    public void setI002(String i002) {
        this.i002 = i002 == null ? null : i002.trim();
    }

    public String getI003() {
        return i003;
    }

    public void setI003(String i003) {
        this.i003 = i003 == null ? null : i003.trim();
    }

    public String getI004() {
        return i004;
    }

    public void setI004(String i004) {
        this.i004 = i004 == null ? null : i004.trim();
    }

    public String getI005() {
        return i005;
    }

    public void setI005(String i005) {
        this.i005 = i005 == null ? null : i005.trim();
    }

    public String getJ001() {
        return j001;
    }

    public void setJ001(String j001) {
        this.j001 = j001 == null ? null : j001.trim();
    }

    public String getJ002() {
        return j002;
    }

    public void setJ002(String j002) {
        this.j002 = j002 == null ? null : j002.trim();
    }

    public String getJ003() {
        return j003;
    }

    public void setJ003(String j003) {
        this.j003 = j003 == null ? null : j003.trim();
    }

    public String getJ004() {
        return j004;
    }

    public void setJ004(String j004) {
        this.j004 = j004 == null ? null : j004.trim();
    }

    public String getJ005() {
        return j005;
    }

    public void setJ005(String j005) {
        this.j005 = j005 == null ? null : j005.trim();
    }

    public String getK001() {
        return k001;
    }

    public void setK001(String k001) {
        this.k001 = k001 == null ? null : k001.trim();
    }

    public String getK002() {
        return k002;
    }

    public void setK002(String k002) {
        this.k002 = k002 == null ? null : k002.trim();
    }

    public String getK003() {
        return k003;
    }

    public void setK003(String k003) {
        this.k003 = k003 == null ? null : k003.trim();
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

    public String getL001() {
        return l001;
    }

    public void setL001(String l001) {
        this.l001 = l001 == null ? null : l001.trim();
    }

    public String getL002() {
        return l002;
    }

    public void setL002(String l002) {
        this.l002 = l002 == null ? null : l002.trim();
    }

    public String getL003() {
        return l003;
    }

    public void setL003(String l003) {
        this.l003 = l003 == null ? null : l003.trim();
    }

    public String getL004() {
        return l004;
    }

    public void setL004(String l004) {
        this.l004 = l004 == null ? null : l004.trim();
    }

    public String getL005() {
        return l005;
    }

    public void setL005(String l005) {
        this.l005 = l005 == null ? null : l005.trim();
    }

    public String getL006() {
        return l006;
    }

    public void setL006(String l006) {
        this.l006 = l006 == null ? null : l006.trim();
    }

    public String getL007() {
        return l007;
    }

    public void setL007(String l007) {
        this.l007 = l007 == null ? null : l007.trim();
    }

    public String getL008() {
        return l008;
    }

    public void setL008(String l008) {
        this.l008 = l008 == null ? null : l008.trim();
    }

    public String getL009() {
        return l009;
    }

    public void setL009(String l009) {
        this.l009 = l009 == null ? null : l009.trim();
    }

    public String getL010() {
        return l010;
    }

    public void setL010(String l010) {
        this.l010 = l010 == null ? null : l010.trim();
    }

    public String getL011() {
        return l011;
    }

    public void setL011(String l011) {
        this.l011 = l011 == null ? null : l011.trim();
    }

    public String getL012() {
        return l012;
    }

    public void setL012(String l012) {
        this.l012 = l012 == null ? null : l012.trim();
    }

    public String getL013() {
        return l013;
    }

    public void setL013(String l013) {
        this.l013 = l013 == null ? null : l013.trim();
    }

    public String getL014() {
        return l014;
    }

    public void setL014(String l014) {
        this.l014 = l014 == null ? null : l014.trim();
    }

    public String getL015() {
        return l015;
    }

    public void setL015(String l015) {
        this.l015 = l015 == null ? null : l015.trim();
    }

    public String getL016() {
        return l016;
    }

    public void setL016(String l016) {
        this.l016 = l016 == null ? null : l016.trim();
    }

    public String getL017() {
        return l017;
    }

    public void setL017(String l017) {
        this.l017 = l017 == null ? null : l017.trim();
    }

    public String getL018() {
        return l018;
    }

    public void setL018(String l018) {
        this.l018 = l018 == null ? null : l018.trim();
    }

    public String getM001() {
        return m001;
    }

    public void setM001(String m001) {
        this.m001 = m001 == null ? null : m001.trim();
    }

    public String getM002() {
        return m002;
    }

    public void setM002(String m002) {
        this.m002 = m002 == null ? null : m002.trim();
    }

    public String getM003() {
        return m003;
    }

    public void setM003(String m003) {
        this.m003 = m003 == null ? null : m003.trim();
    }

    public String getM004() {
        return m004;
    }

    public void setM004(String m004) {
        this.m004 = m004 == null ? null : m004.trim();
    }

    public String getM005() {
        return m005;
    }

    public void setM005(String m005) {
        this.m005 = m005 == null ? null : m005.trim();
    }

    public String getM006() {
        return m006;
    }

    public void setM006(String m006) {
        this.m006 = m006 == null ? null : m006.trim();
    }

    public String getM007() {
        return m007;
    }

    public void setM007(String m007) {
        this.m007 = m007 == null ? null : m007.trim();
    }

    public String getM008() {
        return m008;
    }

    public void setM008(String m008) {
        this.m008 = m008 == null ? null : m008.trim();
    }

    public String getM009() {
        return m009;
    }

    public void setM009(String m009) {
        this.m009 = m009 == null ? null : m009.trim();
    }

    public String getM010() {
        return m010;
    }

    public void setM010(String m010) {
        this.m010 = m010 == null ? null : m010.trim();
    }

    public String getM011() {
        return m011;
    }

    public void setM011(String m011) {
        this.m011 = m011 == null ? null : m011.trim();
    }

    public String getM012() {
        return m012;
    }

    public void setM012(String m012) {
        this.m012 = m012 == null ? null : m012.trim();
    }

    public String getN001() {
        return n001;
    }

    public void setN001(String n001) {
        this.n001 = n001 == null ? null : n001.trim();
    }

    public String getN002() {
        return n002;
    }

    public void setN002(String n002) {
        this.n002 = n002 == null ? null : n002.trim();
    }

    public String getN003() {
        return n003;
    }

    public void setN003(String n003) {
        this.n003 = n003 == null ? null : n003.trim();
    }

    public String getN004() {
        return n004;
    }

    public void setN004(String n004) {
        this.n004 = n004 == null ? null : n004.trim();
    }

    public String getN005() {
        return n005;
    }

    public void setN005(String n005) {
        this.n005 = n005 == null ? null : n005.trim();
    }

    public String getN006() {
        return n006;
    }

    public void setN006(String n006) {
        this.n006 = n006 == null ? null : n006.trim();
    }

    public String getN007() {
        return n007;
    }

    public void setN007(String n007) {
        this.n007 = n007 == null ? null : n007.trim();
    }

    public String getN008() {
        return n008;
    }

    public void setN008(String n008) {
        this.n008 = n008 == null ? null : n008.trim();
    }

    public String getN009() {
        return n009;
    }

    public void setN009(String n009) {
        this.n009 = n009 == null ? null : n009.trim();
    }

    public String getPa001() {
        return pa001;
    }

    public void setPa001(String pa001) {
        this.pa001 = pa001 == null ? null : pa001.trim();
    }

    public String getPa002() {
        return pa002;
    }

    public void setPa002(String pa002) {
        this.pa002 = pa002 == null ? null : pa002.trim();
    }

    public String getPa003() {
        return pa003;
    }

    public void setPa003(String pa003) {
        this.pa003 = pa003 == null ? null : pa003.trim();
    }

    public String getPa004() {
        return pa004;
    }

    public void setPa004(String pa004) {
        this.pa004 = pa004 == null ? null : pa004.trim();
    }

    public String getPa005() {
        return pa005;
    }

    public void setPa005(String pa005) {
        this.pa005 = pa005 == null ? null : pa005.trim();
    }

    public String getPa006() {
        return pa006;
    }

    public void setPa006(String pa006) {
        this.pa006 = pa006 == null ? null : pa006.trim();
    }

    public String getPa007() {
        return pa007;
    }

    public void setPa007(String pa007) {
        this.pa007 = pa007 == null ? null : pa007.trim();
    }

    public String getPa008() {
        return pa008;
    }

    public void setPa008(String pa008) {
        this.pa008 = pa008 == null ? null : pa008.trim();
    }

    public String getPa009() {
        return pa009;
    }

    public void setPa009(String pa009) {
        this.pa009 = pa009 == null ? null : pa009.trim();
    }

    public String getPb001() {
        return pb001;
    }

    public void setPb001(String pb001) {
        this.pb001 = pb001 == null ? null : pb001.trim();
    }

    public String getPb002() {
        return pb002;
    }

    public void setPb002(String pb002) {
        this.pb002 = pb002 == null ? null : pb002.trim();
    }

    public String getPb003() {
        return pb003;
    }

    public void setPb003(String pb003) {
        this.pb003 = pb003 == null ? null : pb003.trim();
    }

    public String getPb004() {
        return pb004;
    }

    public void setPb004(String pb004) {
        this.pb004 = pb004 == null ? null : pb004.trim();
    }

    public String getPb005() {
        return pb005;
    }

    public void setPb005(String pb005) {
        this.pb005 = pb005 == null ? null : pb005.trim();
    }

    public String getPb006() {
        return pb006;
    }

    public void setPb006(String pb006) {
        this.pb006 = pb006 == null ? null : pb006.trim();
    }

    public String getPb007() {
        return pb007;
    }

    public void setPb007(String pb007) {
        this.pb007 = pb007 == null ? null : pb007.trim();
    }

    public String getPb008() {
        return pb008;
    }

    public void setPb008(String pb008) {
        this.pb008 = pb008 == null ? null : pb008.trim();
    }

    public String getPb009() {
        return pb009;
    }

    public void setPb009(String pb009) {
        this.pb009 = pb009 == null ? null : pb009.trim();
    }

    public String getPc001() {
        return pc001;
    }

    public void setPc001(String pc001) {
        this.pc001 = pc001 == null ? null : pc001.trim();
    }

    public String getPc002() {
        return pc002;
    }

    public void setPc002(String pc002) {
        this.pc002 = pc002 == null ? null : pc002.trim();
    }

    public String getPc003() {
        return pc003;
    }

    public void setPc003(String pc003) {
        this.pc003 = pc003 == null ? null : pc003.trim();
    }

    public String getPc004() {
        return pc004;
    }

    public void setPc004(String pc004) {
        this.pc004 = pc004 == null ? null : pc004.trim();
    }

    public String getPc005() {
        return pc005;
    }

    public void setPc005(String pc005) {
        this.pc005 = pc005 == null ? null : pc005.trim();
    }

    public String getPc006() {
        return pc006;
    }

    public void setPc006(String pc006) {
        this.pc006 = pc006 == null ? null : pc006.trim();
    }

    public String getPc007() {
        return pc007;
    }

    public void setPc007(String pc007) {
        this.pc007 = pc007 == null ? null : pc007.trim();
    }

    public String getPc008() {
        return pc008;
    }

    public void setPc008(String pc008) {
        this.pc008 = pc008 == null ? null : pc008.trim();
    }

    public String getPc009() {
        return pc009;
    }

    public void setPc009(String pc009) {
        this.pc009 = pc009 == null ? null : pc009.trim();
    }

    public String getPd001() {
        return pd001;
    }

    public void setPd001(String pd001) {
        this.pd001 = pd001 == null ? null : pd001.trim();
    }

    public String getPd002() {
        return pd002;
    }

    public void setPd002(String pd002) {
        this.pd002 = pd002 == null ? null : pd002.trim();
    }

    public String getPd003() {
        return pd003;
    }

    public void setPd003(String pd003) {
        this.pd003 = pd003 == null ? null : pd003.trim();
    }

    public String getPd004() {
        return pd004;
    }

    public void setPd004(String pd004) {
        this.pd004 = pd004 == null ? null : pd004.trim();
    }

    public String getPd005() {
        return pd005;
    }

    public void setPd005(String pd005) {
        this.pd005 = pd005 == null ? null : pd005.trim();
    }

    public String getPd006() {
        return pd006;
    }

    public void setPd006(String pd006) {
        this.pd006 = pd006 == null ? null : pd006.trim();
    }

    public String getPd007() {
        return pd007;
    }

    public void setPd007(String pd007) {
        this.pd007 = pd007 == null ? null : pd007.trim();
    }

    public String getPd008() {
        return pd008;
    }

    public void setPd008(String pd008) {
        this.pd008 = pd008 == null ? null : pd008.trim();
    }

    public String getPd009() {
        return pd009;
    }

    public void setPd009(String pd009) {
        this.pd009 = pd009 == null ? null : pd009.trim();
    }

    public String getQ001() {
        return q001;
    }

    public void setQ001(String q001) {
        this.q001 = q001 == null ? null : q001.trim();
    }

    public String getQ002() {
        return q002;
    }

    public void setQ002(String q002) {
        this.q002 = q002 == null ? null : q002.trim();
    }

    public String getQ003() {
        return q003;
    }

    public void setQ003(String q003) {
        this.q003 = q003 == null ? null : q003.trim();
    }

    public String getQ004() {
        return q004;
    }

    public void setQ004(String q004) {
        this.q004 = q004 == null ? null : q004.trim();
    }

    public String getQ005() {
        return q005;
    }

    public void setQ005(String q005) {
        this.q005 = q005 == null ? null : q005.trim();
    }

    public String getQ006() {
        return q006;
    }

    public void setQ006(String q006) {
        this.q006 = q006 == null ? null : q006.trim();
    }

    public String getQ007() {
        return q007;
    }

    public void setQ007(String q007) {
        this.q007 = q007 == null ? null : q007.trim();
    }

    public String getQ008() {
        return q008;
    }

    public void setQ008(String q008) {
        this.q008 = q008 == null ? null : q008.trim();
    }

    public String getQ009() {
        return q009;
    }

    public void setQ009(String q009) {
        this.q009 = q009 == null ? null : q009.trim();
    }

    public String getQ010() {
        return q010;
    }

    public void setQ010(String q010) {
        this.q010 = q010 == null ? null : q010.trim();
    }

    public String getQ011() {
        return q011;
    }

    public void setQ011(String q011) {
        this.q011 = q011 == null ? null : q011.trim();
    }

    public String getQ012() {
        return q012;
    }

    public void setQ012(String q012) {
        this.q012 = q012 == null ? null : q012.trim();
    }

    public String getR001() {
        return r001;
    }

    public void setR001(String r001) {
        this.r001 = r001 == null ? null : r001.trim();
    }

    public String getS001() {
        return s001;
    }

    public void setS001(String s001) {
        this.s001 = s001 == null ? null : s001.trim();
    }

    public String getS002() {
        return s002;
    }

    public void setS002(String s002) {
        this.s002 = s002 == null ? null : s002.trim();
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

    public String getS010() {
        return s010;
    }

    public void setS010(String s010) {
        this.s010 = s010 == null ? null : s010.trim();
    }

    public String getS011() {
        return s011;
    }

    public void setS011(String s011) {
        this.s011 = s011 == null ? null : s011.trim();
    }

    public String getT001() {
        return t001;
    }

    public void setT001(String t001) {
        this.t001 = t001 == null ? null : t001.trim();
    }

    public String getU001() {
        return u001;
    }

    public void setU001(String u001) {
        this.u001 = u001 == null ? null : u001.trim();
    }

    public String getU002() {
        return u002;
    }

    public void setU002(String u002) {
        this.u002 = u002 == null ? null : u002.trim();
    }

    public String getQ0022() {
        return q0022;
    }

    public void setQ0022(String q0022) {
        this.q0022 = q0022 == null ? null : q0022.trim();
    }

    public String getU003() {
        return u003;
    }

    public void setU003(String u003) {
        this.u003 = u003 == null ? null : u003.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_detail_user_history
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
        FormDetailUserHistory other = (FormDetailUserHistory) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getProjectId() == null ? other.getProjectId() == null : this.getProjectId().equals(other.getProjectId()))
            && (this.getOrgId() == null ? other.getOrgId() == null : this.getOrgId().equals(other.getOrgId()))
            && (this.getHeadOrgId() == null ? other.getHeadOrgId() == null : this.getHeadOrgId().equals(other.getHeadOrgId()))
            && (this.getProjectNo() == null ? other.getProjectNo() == null : this.getProjectNo().equals(other.getProjectNo()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getCreateUser() == null ? other.getCreateUser() == null : this.getCreateUser().equals(other.getCreateUser()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getModifyTime() == null ? other.getModifyTime() == null : this.getModifyTime().equals(other.getModifyTime()))
            && (this.getModifyUser() == null ? other.getModifyUser() == null : this.getModifyUser().equals(other.getModifyUser()))
            && (this.getCreateUserId() == null ? other.getCreateUserId() == null : this.getCreateUserId().equals(other.getCreateUserId()))
            && (this.getModifyUserId() == null ? other.getModifyUserId() == null : this.getModifyUserId().equals(other.getModifyUserId()))
            && (this.getB001() == null ? other.getB001() == null : this.getB001().equals(other.getB001()))
            && (this.getB002() == null ? other.getB002() == null : this.getB002().equals(other.getB002()))
            && (this.getB003() == null ? other.getB003() == null : this.getB003().equals(other.getB003()))
            && (this.getB004() == null ? other.getB004() == null : this.getB004().equals(other.getB004()))
            && (this.getB005() == null ? other.getB005() == null : this.getB005().equals(other.getB005()))
            && (this.getB006() == null ? other.getB006() == null : this.getB006().equals(other.getB006()))
            && (this.getB007() == null ? other.getB007() == null : this.getB007().equals(other.getB007()))
            && (this.getB008() == null ? other.getB008() == null : this.getB008().equals(other.getB008()))
            && (this.getB009() == null ? other.getB009() == null : this.getB009().equals(other.getB009()))
            && (this.getB010() == null ? other.getB010() == null : this.getB010().equals(other.getB010()))
            && (this.getB011() == null ? other.getB011() == null : this.getB011().equals(other.getB011()))
            && (this.getB012() == null ? other.getB012() == null : this.getB012().equals(other.getB012()))
            && (this.getB013() == null ? other.getB013() == null : this.getB013().equals(other.getB013()))
            && (this.getB014() == null ? other.getB014() == null : this.getB014().equals(other.getB014()))
            && (this.getB015() == null ? other.getB015() == null : this.getB015().equals(other.getB015()))
            && (this.getB016() == null ? other.getB016() == null : this.getB016().equals(other.getB016()))
            && (this.getB017() == null ? other.getB017() == null : this.getB017().equals(other.getB017()))
            && (this.getB018() == null ? other.getB018() == null : this.getB018().equals(other.getB018()))
            && (this.getB019() == null ? other.getB019() == null : this.getB019().equals(other.getB019()))
            && (this.getB020() == null ? other.getB020() == null : this.getB020().equals(other.getB020()))
            && (this.getB021() == null ? other.getB021() == null : this.getB021().equals(other.getB021()))
            && (this.getB022() == null ? other.getB022() == null : this.getB022().equals(other.getB022()))
            && (this.getB023() == null ? other.getB023() == null : this.getB023().equals(other.getB023()))
            && (this.getB024() == null ? other.getB024() == null : this.getB024().equals(other.getB024()))
            && (this.getB025() == null ? other.getB025() == null : this.getB025().equals(other.getB025()))
            && (this.getB026() == null ? other.getB026() == null : this.getB026().equals(other.getB026()))
            && (this.getB027() == null ? other.getB027() == null : this.getB027().equals(other.getB027()))
            && (this.getB028() == null ? other.getB028() == null : this.getB028().equals(other.getB028()))
            && (this.getB029() == null ? other.getB029() == null : this.getB029().equals(other.getB029()))
            && (this.getB030() == null ? other.getB030() == null : this.getB030().equals(other.getB030()))
            && (this.getB031() == null ? other.getB031() == null : this.getB031().equals(other.getB031()))
            && (this.getB032() == null ? other.getB032() == null : this.getB032().equals(other.getB032()))
            && (this.getB033() == null ? other.getB033() == null : this.getB033().equals(other.getB033()))
            && (this.getB034() == null ? other.getB034() == null : this.getB034().equals(other.getB034()))
            && (this.getB035() == null ? other.getB035() == null : this.getB035().equals(other.getB035()))
            && (this.getB036() == null ? other.getB036() == null : this.getB036().equals(other.getB036()))
            && (this.getC001() == null ? other.getC001() == null : this.getC001().equals(other.getC001()))
            && (this.getC002() == null ? other.getC002() == null : this.getC002().equals(other.getC002()))
            && (this.getC003() == null ? other.getC003() == null : this.getC003().equals(other.getC003()))
            && (this.getC004() == null ? other.getC004() == null : this.getC004().equals(other.getC004()))
            && (this.getC005() == null ? other.getC005() == null : this.getC005().equals(other.getC005()))
            && (this.getC006() == null ? other.getC006() == null : this.getC006().equals(other.getC006()))
            && (this.getC007() == null ? other.getC007() == null : this.getC007().equals(other.getC007()))
            && (this.getC008() == null ? other.getC008() == null : this.getC008().equals(other.getC008()))
            && (this.getC009() == null ? other.getC009() == null : this.getC009().equals(other.getC009()))
            && (this.getC010() == null ? other.getC010() == null : this.getC010().equals(other.getC010()))
            && (this.getC011() == null ? other.getC011() == null : this.getC011().equals(other.getC011()))
            && (this.getC012() == null ? other.getC012() == null : this.getC012().equals(other.getC012()))
            && (this.getC013() == null ? other.getC013() == null : this.getC013().equals(other.getC013()))
            && (this.getC014() == null ? other.getC014() == null : this.getC014().equals(other.getC014()))
            && (this.getC015() == null ? other.getC015() == null : this.getC015().equals(other.getC015()))
            && (this.getC016() == null ? other.getC016() == null : this.getC016().equals(other.getC016()))
            && (this.getC017() == null ? other.getC017() == null : this.getC017().equals(other.getC017()))
            && (this.getC018() == null ? other.getC018() == null : this.getC018().equals(other.getC018()))
            && (this.getC019() == null ? other.getC019() == null : this.getC019().equals(other.getC019()))
            && (this.getC020() == null ? other.getC020() == null : this.getC020().equals(other.getC020()))
            && (this.getC021() == null ? other.getC021() == null : this.getC021().equals(other.getC021()))
            && (this.getC023() == null ? other.getC023() == null : this.getC023().equals(other.getC023()))
            && (this.getD001() == null ? other.getD001() == null : this.getD001().equals(other.getD001()))
            && (this.getD002() == null ? other.getD002() == null : this.getD002().equals(other.getD002()))
            && (this.getD003() == null ? other.getD003() == null : this.getD003().equals(other.getD003()))
            && (this.getD004() == null ? other.getD004() == null : this.getD004().equals(other.getD004()))
            && (this.getD005() == null ? other.getD005() == null : this.getD005().equals(other.getD005()))
            && (this.getD006() == null ? other.getD006() == null : this.getD006().equals(other.getD006()))
            && (this.getD007() == null ? other.getD007() == null : this.getD007().equals(other.getD007()))
            && (this.getD008() == null ? other.getD008() == null : this.getD008().equals(other.getD008()))
            && (this.getD009() == null ? other.getD009() == null : this.getD009().equals(other.getD009()))
            && (this.getD010() == null ? other.getD010() == null : this.getD010().equals(other.getD010()))
            && (this.getD011() == null ? other.getD011() == null : this.getD011().equals(other.getD011()))
            && (this.getD012() == null ? other.getD012() == null : this.getD012().equals(other.getD012()))
            && (this.getD013() == null ? other.getD013() == null : this.getD013().equals(other.getD013()))
            && (this.getD014() == null ? other.getD014() == null : this.getD014().equals(other.getD014()))
            && (this.getD015() == null ? other.getD015() == null : this.getD015().equals(other.getD015()))
            && (this.getD016() == null ? other.getD016() == null : this.getD016().equals(other.getD016()))
            && (this.getD017() == null ? other.getD017() == null : this.getD017().equals(other.getD017()))
            && (this.getD018() == null ? other.getD018() == null : this.getD018().equals(other.getD018()))
            && (this.getD019() == null ? other.getD019() == null : this.getD019().equals(other.getD019()))
            && (this.getD020() == null ? other.getD020() == null : this.getD020().equals(other.getD020()))
            && (this.getD021() == null ? other.getD021() == null : this.getD021().equals(other.getD021()))
            && (this.getD022() == null ? other.getD022() == null : this.getD022().equals(other.getD022()))
            && (this.getD023() == null ? other.getD023() == null : this.getD023().equals(other.getD023()))
            && (this.getD024() == null ? other.getD024() == null : this.getD024().equals(other.getD024()))
            && (this.getD025() == null ? other.getD025() == null : this.getD025().equals(other.getD025()))
            && (this.getD026() == null ? other.getD026() == null : this.getD026().equals(other.getD026()))
            && (this.getE001() == null ? other.getE001() == null : this.getE001().equals(other.getE001()))
            && (this.getE002() == null ? other.getE002() == null : this.getE002().equals(other.getE002()))
            && (this.getE003() == null ? other.getE003() == null : this.getE003().equals(other.getE003()))
            && (this.getE004() == null ? other.getE004() == null : this.getE004().equals(other.getE004()))
            && (this.getE005() == null ? other.getE005() == null : this.getE005().equals(other.getE005()))
            && (this.getE006() == null ? other.getE006() == null : this.getE006().equals(other.getE006()))
            && (this.getE007() == null ? other.getE007() == null : this.getE007().equals(other.getE007()))
            && (this.getE008() == null ? other.getE008() == null : this.getE008().equals(other.getE008()))
            && (this.getE009() == null ? other.getE009() == null : this.getE009().equals(other.getE009()))
            && (this.getE010() == null ? other.getE010() == null : this.getE010().equals(other.getE010()))
            && (this.getE011() == null ? other.getE011() == null : this.getE011().equals(other.getE011()))
            && (this.getE012() == null ? other.getE012() == null : this.getE012().equals(other.getE012()))
            && (this.getE013() == null ? other.getE013() == null : this.getE013().equals(other.getE013()))
            && (this.getE014() == null ? other.getE014() == null : this.getE014().equals(other.getE014()))
            && (this.getE015() == null ? other.getE015() == null : this.getE015().equals(other.getE015()))
            && (this.getE016() == null ? other.getE016() == null : this.getE016().equals(other.getE016()))
            && (this.getE017() == null ? other.getE017() == null : this.getE017().equals(other.getE017()))
            && (this.getE018() == null ? other.getE018() == null : this.getE018().equals(other.getE018()))
            && (this.getE019() == null ? other.getE019() == null : this.getE019().equals(other.getE019()))
            && (this.getE020() == null ? other.getE020() == null : this.getE020().equals(other.getE020()))
            && (this.getE021() == null ? other.getE021() == null : this.getE021().equals(other.getE021()))
            && (this.getE022() == null ? other.getE022() == null : this.getE022().equals(other.getE022()))
            && (this.getE023() == null ? other.getE023() == null : this.getE023().equals(other.getE023()))
            && (this.getE024() == null ? other.getE024() == null : this.getE024().equals(other.getE024()))
            && (this.getE025() == null ? other.getE025() == null : this.getE025().equals(other.getE025()))
            && (this.getE026() == null ? other.getE026() == null : this.getE026().equals(other.getE026()))
            && (this.getE027() == null ? other.getE027() == null : this.getE027().equals(other.getE027()))
            && (this.getE028() == null ? other.getE028() == null : this.getE028().equals(other.getE028()))
            && (this.getE029() == null ? other.getE029() == null : this.getE029().equals(other.getE029()))
            && (this.getE030() == null ? other.getE030() == null : this.getE030().equals(other.getE030()))
            && (this.getE031() == null ? other.getE031() == null : this.getE031().equals(other.getE031()))
            && (this.getE032() == null ? other.getE032() == null : this.getE032().equals(other.getE032()))
            && (this.getE033() == null ? other.getE033() == null : this.getE033().equals(other.getE033()))
            && (this.getE034() == null ? other.getE034() == null : this.getE034().equals(other.getE034()))
            && (this.getE035() == null ? other.getE035() == null : this.getE035().equals(other.getE035()))
            && (this.getE036() == null ? other.getE036() == null : this.getE036().equals(other.getE036()))
            && (this.getE037() == null ? other.getE037() == null : this.getE037().equals(other.getE037()))
            && (this.getE038() == null ? other.getE038() == null : this.getE038().equals(other.getE038()))
            && (this.getE039() == null ? other.getE039() == null : this.getE039().equals(other.getE039()))
            && (this.getE040() == null ? other.getE040() == null : this.getE040().equals(other.getE040()))
            && (this.getE041() == null ? other.getE041() == null : this.getE041().equals(other.getE041()))
            && (this.getE042() == null ? other.getE042() == null : this.getE042().equals(other.getE042()))
            && (this.getE043() == null ? other.getE043() == null : this.getE043().equals(other.getE043()))
            && (this.getE045() == null ? other.getE045() == null : this.getE045().equals(other.getE045()))
            && (this.getF001() == null ? other.getF001() == null : this.getF001().equals(other.getF001()))
            && (this.getF002() == null ? other.getF002() == null : this.getF002().equals(other.getF002()))
            && (this.getF003() == null ? other.getF003() == null : this.getF003().equals(other.getF003()))
            && (this.getF004() == null ? other.getF004() == null : this.getF004().equals(other.getF004()))
            && (this.getF005() == null ? other.getF005() == null : this.getF005().equals(other.getF005()))
            && (this.getF006() == null ? other.getF006() == null : this.getF006().equals(other.getF006()))
            && (this.getF007() == null ? other.getF007() == null : this.getF007().equals(other.getF007()))
            && (this.getF008() == null ? other.getF008() == null : this.getF008().equals(other.getF008()))
            && (this.getF009() == null ? other.getF009() == null : this.getF009().equals(other.getF009()))
            && (this.getF010() == null ? other.getF010() == null : this.getF010().equals(other.getF010()))
            && (this.getF011() == null ? other.getF011() == null : this.getF011().equals(other.getF011()))
            && (this.getF012() == null ? other.getF012() == null : this.getF012().equals(other.getF012()))
            && (this.getF013() == null ? other.getF013() == null : this.getF013().equals(other.getF013()))
            && (this.getF0012() == null ? other.getF0012() == null : this.getF0012().equals(other.getF0012()))
            && (this.getG001() == null ? other.getG001() == null : this.getG001().equals(other.getG001()))
            && (this.getG002() == null ? other.getG002() == null : this.getG002().equals(other.getG002()))
            && (this.getG003() == null ? other.getG003() == null : this.getG003().equals(other.getG003()))
            && (this.getG004() == null ? other.getG004() == null : this.getG004().equals(other.getG004()))
            && (this.getG005() == null ? other.getG005() == null : this.getG005().equals(other.getG005()))
            && (this.getG006() == null ? other.getG006() == null : this.getG006().equals(other.getG006()))
            && (this.getG007() == null ? other.getG007() == null : this.getG007().equals(other.getG007()))
            && (this.getG008() == null ? other.getG008() == null : this.getG008().equals(other.getG008()))
            && (this.getG009() == null ? other.getG009() == null : this.getG009().equals(other.getG009()))
            && (this.getG010() == null ? other.getG010() == null : this.getG010().equals(other.getG010()))
            && (this.getG011() == null ? other.getG011() == null : this.getG011().equals(other.getG011()))
            && (this.getG012() == null ? other.getG012() == null : this.getG012().equals(other.getG012()))
            && (this.getG013() == null ? other.getG013() == null : this.getG013().equals(other.getG013()))
            && (this.getG014() == null ? other.getG014() == null : this.getG014().equals(other.getG014()))
            && (this.getG015() == null ? other.getG015() == null : this.getG015().equals(other.getG015()))
            && (this.getG016() == null ? other.getG016() == null : this.getG016().equals(other.getG016()))
            && (this.getG017() == null ? other.getG017() == null : this.getG017().equals(other.getG017()))
            && (this.getG018() == null ? other.getG018() == null : this.getG018().equals(other.getG018()))
            && (this.getH001() == null ? other.getH001() == null : this.getH001().equals(other.getH001()))
            && (this.getH002() == null ? other.getH002() == null : this.getH002().equals(other.getH002()))
            && (this.getH003() == null ? other.getH003() == null : this.getH003().equals(other.getH003()))
            && (this.getH004() == null ? other.getH004() == null : this.getH004().equals(other.getH004()))
            && (this.getH005() == null ? other.getH005() == null : this.getH005().equals(other.getH005()))
            && (this.getH006() == null ? other.getH006() == null : this.getH006().equals(other.getH006()))
            && (this.getH007() == null ? other.getH007() == null : this.getH007().equals(other.getH007()))
            && (this.getH008() == null ? other.getH008() == null : this.getH008().equals(other.getH008()))
            && (this.getI001() == null ? other.getI001() == null : this.getI001().equals(other.getI001()))
            && (this.getI002() == null ? other.getI002() == null : this.getI002().equals(other.getI002()))
            && (this.getI003() == null ? other.getI003() == null : this.getI003().equals(other.getI003()))
            && (this.getI004() == null ? other.getI004() == null : this.getI004().equals(other.getI004()))
            && (this.getI005() == null ? other.getI005() == null : this.getI005().equals(other.getI005()))
            && (this.getJ001() == null ? other.getJ001() == null : this.getJ001().equals(other.getJ001()))
            && (this.getJ002() == null ? other.getJ002() == null : this.getJ002().equals(other.getJ002()))
            && (this.getJ003() == null ? other.getJ003() == null : this.getJ003().equals(other.getJ003()))
            && (this.getJ004() == null ? other.getJ004() == null : this.getJ004().equals(other.getJ004()))
            && (this.getJ005() == null ? other.getJ005() == null : this.getJ005().equals(other.getJ005()))
            && (this.getK001() == null ? other.getK001() == null : this.getK001().equals(other.getK001()))
            && (this.getK002() == null ? other.getK002() == null : this.getK002().equals(other.getK002()))
            && (this.getK003() == null ? other.getK003() == null : this.getK003().equals(other.getK003()))
            && (this.getK004() == null ? other.getK004() == null : this.getK004().equals(other.getK004()))
            && (this.getK005() == null ? other.getK005() == null : this.getK005().equals(other.getK005()))
            && (this.getK006() == null ? other.getK006() == null : this.getK006().equals(other.getK006()))
            && (this.getK007() == null ? other.getK007() == null : this.getK007().equals(other.getK007()))
            && (this.getL001() == null ? other.getL001() == null : this.getL001().equals(other.getL001()))
            && (this.getL002() == null ? other.getL002() == null : this.getL002().equals(other.getL002()))
            && (this.getL003() == null ? other.getL003() == null : this.getL003().equals(other.getL003()))
            && (this.getL004() == null ? other.getL004() == null : this.getL004().equals(other.getL004()))
            && (this.getL005() == null ? other.getL005() == null : this.getL005().equals(other.getL005()))
            && (this.getL006() == null ? other.getL006() == null : this.getL006().equals(other.getL006()))
            && (this.getL007() == null ? other.getL007() == null : this.getL007().equals(other.getL007()))
            && (this.getL008() == null ? other.getL008() == null : this.getL008().equals(other.getL008()))
            && (this.getL009() == null ? other.getL009() == null : this.getL009().equals(other.getL009()))
            && (this.getL010() == null ? other.getL010() == null : this.getL010().equals(other.getL010()))
            && (this.getL011() == null ? other.getL011() == null : this.getL011().equals(other.getL011()))
            && (this.getL012() == null ? other.getL012() == null : this.getL012().equals(other.getL012()))
            && (this.getL013() == null ? other.getL013() == null : this.getL013().equals(other.getL013()))
            && (this.getL014() == null ? other.getL014() == null : this.getL014().equals(other.getL014()))
            && (this.getL015() == null ? other.getL015() == null : this.getL015().equals(other.getL015()))
            && (this.getL016() == null ? other.getL016() == null : this.getL016().equals(other.getL016()))
            && (this.getL017() == null ? other.getL017() == null : this.getL017().equals(other.getL017()))
            && (this.getL018() == null ? other.getL018() == null : this.getL018().equals(other.getL018()))
            && (this.getM001() == null ? other.getM001() == null : this.getM001().equals(other.getM001()))
            && (this.getM002() == null ? other.getM002() == null : this.getM002().equals(other.getM002()))
            && (this.getM003() == null ? other.getM003() == null : this.getM003().equals(other.getM003()))
            && (this.getM004() == null ? other.getM004() == null : this.getM004().equals(other.getM004()))
            && (this.getM005() == null ? other.getM005() == null : this.getM005().equals(other.getM005()))
            && (this.getM006() == null ? other.getM006() == null : this.getM006().equals(other.getM006()))
            && (this.getM007() == null ? other.getM007() == null : this.getM007().equals(other.getM007()))
            && (this.getM008() == null ? other.getM008() == null : this.getM008().equals(other.getM008()))
            && (this.getM009() == null ? other.getM009() == null : this.getM009().equals(other.getM009()))
            && (this.getM010() == null ? other.getM010() == null : this.getM010().equals(other.getM010()))
            && (this.getM011() == null ? other.getM011() == null : this.getM011().equals(other.getM011()))
            && (this.getM012() == null ? other.getM012() == null : this.getM012().equals(other.getM012()))
            && (this.getN001() == null ? other.getN001() == null : this.getN001().equals(other.getN001()))
            && (this.getN002() == null ? other.getN002() == null : this.getN002().equals(other.getN002()))
            && (this.getN003() == null ? other.getN003() == null : this.getN003().equals(other.getN003()))
            && (this.getN004() == null ? other.getN004() == null : this.getN004().equals(other.getN004()))
            && (this.getN005() == null ? other.getN005() == null : this.getN005().equals(other.getN005()))
            && (this.getN006() == null ? other.getN006() == null : this.getN006().equals(other.getN006()))
            && (this.getN007() == null ? other.getN007() == null : this.getN007().equals(other.getN007()))
            && (this.getN008() == null ? other.getN008() == null : this.getN008().equals(other.getN008()))
            && (this.getN009() == null ? other.getN009() == null : this.getN009().equals(other.getN009()))
            && (this.getPa001() == null ? other.getPa001() == null : this.getPa001().equals(other.getPa001()))
            && (this.getPa002() == null ? other.getPa002() == null : this.getPa002().equals(other.getPa002()))
            && (this.getPa003() == null ? other.getPa003() == null : this.getPa003().equals(other.getPa003()))
            && (this.getPa004() == null ? other.getPa004() == null : this.getPa004().equals(other.getPa004()))
            && (this.getPa005() == null ? other.getPa005() == null : this.getPa005().equals(other.getPa005()))
            && (this.getPa006() == null ? other.getPa006() == null : this.getPa006().equals(other.getPa006()))
            && (this.getPa007() == null ? other.getPa007() == null : this.getPa007().equals(other.getPa007()))
            && (this.getPa008() == null ? other.getPa008() == null : this.getPa008().equals(other.getPa008()))
            && (this.getPa009() == null ? other.getPa009() == null : this.getPa009().equals(other.getPa009()))
            && (this.getPb001() == null ? other.getPb001() == null : this.getPb001().equals(other.getPb001()))
            && (this.getPb002() == null ? other.getPb002() == null : this.getPb002().equals(other.getPb002()))
            && (this.getPb003() == null ? other.getPb003() == null : this.getPb003().equals(other.getPb003()))
            && (this.getPb004() == null ? other.getPb004() == null : this.getPb004().equals(other.getPb004()))
            && (this.getPb005() == null ? other.getPb005() == null : this.getPb005().equals(other.getPb005()))
            && (this.getPb006() == null ? other.getPb006() == null : this.getPb006().equals(other.getPb006()))
            && (this.getPb007() == null ? other.getPb007() == null : this.getPb007().equals(other.getPb007()))
            && (this.getPb008() == null ? other.getPb008() == null : this.getPb008().equals(other.getPb008()))
            && (this.getPb009() == null ? other.getPb009() == null : this.getPb009().equals(other.getPb009()))
            && (this.getPc001() == null ? other.getPc001() == null : this.getPc001().equals(other.getPc001()))
            && (this.getPc002() == null ? other.getPc002() == null : this.getPc002().equals(other.getPc002()))
            && (this.getPc003() == null ? other.getPc003() == null : this.getPc003().equals(other.getPc003()))
            && (this.getPc004() == null ? other.getPc004() == null : this.getPc004().equals(other.getPc004()))
            && (this.getPc005() == null ? other.getPc005() == null : this.getPc005().equals(other.getPc005()))
            && (this.getPc006() == null ? other.getPc006() == null : this.getPc006().equals(other.getPc006()))
            && (this.getPc007() == null ? other.getPc007() == null : this.getPc007().equals(other.getPc007()))
            && (this.getPc008() == null ? other.getPc008() == null : this.getPc008().equals(other.getPc008()))
            && (this.getPc009() == null ? other.getPc009() == null : this.getPc009().equals(other.getPc009()))
            && (this.getPd001() == null ? other.getPd001() == null : this.getPd001().equals(other.getPd001()))
            && (this.getPd002() == null ? other.getPd002() == null : this.getPd002().equals(other.getPd002()))
            && (this.getPd003() == null ? other.getPd003() == null : this.getPd003().equals(other.getPd003()))
            && (this.getPd004() == null ? other.getPd004() == null : this.getPd004().equals(other.getPd004()))
            && (this.getPd005() == null ? other.getPd005() == null : this.getPd005().equals(other.getPd005()))
            && (this.getPd006() == null ? other.getPd006() == null : this.getPd006().equals(other.getPd006()))
            && (this.getPd007() == null ? other.getPd007() == null : this.getPd007().equals(other.getPd007()))
            && (this.getPd008() == null ? other.getPd008() == null : this.getPd008().equals(other.getPd008()))
            && (this.getPd009() == null ? other.getPd009() == null : this.getPd009().equals(other.getPd009()))
            && (this.getQ001() == null ? other.getQ001() == null : this.getQ001().equals(other.getQ001()))
            && (this.getQ002() == null ? other.getQ002() == null : this.getQ002().equals(other.getQ002()))
            && (this.getQ003() == null ? other.getQ003() == null : this.getQ003().equals(other.getQ003()))
            && (this.getQ004() == null ? other.getQ004() == null : this.getQ004().equals(other.getQ004()))
            && (this.getQ005() == null ? other.getQ005() == null : this.getQ005().equals(other.getQ005()))
            && (this.getQ006() == null ? other.getQ006() == null : this.getQ006().equals(other.getQ006()))
            && (this.getQ007() == null ? other.getQ007() == null : this.getQ007().equals(other.getQ007()))
            && (this.getQ008() == null ? other.getQ008() == null : this.getQ008().equals(other.getQ008()))
            && (this.getQ009() == null ? other.getQ009() == null : this.getQ009().equals(other.getQ009()))
            && (this.getQ010() == null ? other.getQ010() == null : this.getQ010().equals(other.getQ010()))
            && (this.getQ011() == null ? other.getQ011() == null : this.getQ011().equals(other.getQ011()))
            && (this.getQ012() == null ? other.getQ012() == null : this.getQ012().equals(other.getQ012()))
            && (this.getR001() == null ? other.getR001() == null : this.getR001().equals(other.getR001()))
            && (this.getS001() == null ? other.getS001() == null : this.getS001().equals(other.getS001()))
            && (this.getS002() == null ? other.getS002() == null : this.getS002().equals(other.getS002()))
            && (this.getS003() == null ? other.getS003() == null : this.getS003().equals(other.getS003()))
            && (this.getS004() == null ? other.getS004() == null : this.getS004().equals(other.getS004()))
            && (this.getS005() == null ? other.getS005() == null : this.getS005().equals(other.getS005()))
            && (this.getS006() == null ? other.getS006() == null : this.getS006().equals(other.getS006()))
            && (this.getS007() == null ? other.getS007() == null : this.getS007().equals(other.getS007()))
            && (this.getS008() == null ? other.getS008() == null : this.getS008().equals(other.getS008()))
            && (this.getS010() == null ? other.getS010() == null : this.getS010().equals(other.getS010()))
            && (this.getS011() == null ? other.getS011() == null : this.getS011().equals(other.getS011()))
            && (this.getT001() == null ? other.getT001() == null : this.getT001().equals(other.getT001()))
            && (this.getU001() == null ? other.getU001() == null : this.getU001().equals(other.getU001()))
            && (this.getU002() == null ? other.getU002() == null : this.getU002().equals(other.getU002()))
            && (this.getQ0022() == null ? other.getQ0022() == null : this.getQ0022().equals(other.getQ0022()))
            && (this.getU003() == null ? other.getU003() == null : this.getU003().equals(other.getU003()));
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_detail_user_history
     *
     * @mbg.generated Wed Dec 19 16:26:10 CST 2018
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getProjectId() == null) ? 0 : getProjectId().hashCode());
        result = prime * result + ((getOrgId() == null) ? 0 : getOrgId().hashCode());
        result = prime * result + ((getHeadOrgId() == null) ? 0 : getHeadOrgId().hashCode());
        result = prime * result + ((getProjectNo() == null) ? 0 : getProjectNo().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getCreateUser() == null) ? 0 : getCreateUser().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getModifyTime() == null) ? 0 : getModifyTime().hashCode());
        result = prime * result + ((getModifyUser() == null) ? 0 : getModifyUser().hashCode());
        result = prime * result + ((getCreateUserId() == null) ? 0 : getCreateUserId().hashCode());
        result = prime * result + ((getModifyUserId() == null) ? 0 : getModifyUserId().hashCode());
        result = prime * result + ((getB001() == null) ? 0 : getB001().hashCode());
        result = prime * result + ((getB002() == null) ? 0 : getB002().hashCode());
        result = prime * result + ((getB003() == null) ? 0 : getB003().hashCode());
        result = prime * result + ((getB004() == null) ? 0 : getB004().hashCode());
        result = prime * result + ((getB005() == null) ? 0 : getB005().hashCode());
        result = prime * result + ((getB006() == null) ? 0 : getB006().hashCode());
        result = prime * result + ((getB007() == null) ? 0 : getB007().hashCode());
        result = prime * result + ((getB008() == null) ? 0 : getB008().hashCode());
        result = prime * result + ((getB009() == null) ? 0 : getB009().hashCode());
        result = prime * result + ((getB010() == null) ? 0 : getB010().hashCode());
        result = prime * result + ((getB011() == null) ? 0 : getB011().hashCode());
        result = prime * result + ((getB012() == null) ? 0 : getB012().hashCode());
        result = prime * result + ((getB013() == null) ? 0 : getB013().hashCode());
        result = prime * result + ((getB014() == null) ? 0 : getB014().hashCode());
        result = prime * result + ((getB015() == null) ? 0 : getB015().hashCode());
        result = prime * result + ((getB016() == null) ? 0 : getB016().hashCode());
        result = prime * result + ((getB017() == null) ? 0 : getB017().hashCode());
        result = prime * result + ((getB018() == null) ? 0 : getB018().hashCode());
        result = prime * result + ((getB019() == null) ? 0 : getB019().hashCode());
        result = prime * result + ((getB020() == null) ? 0 : getB020().hashCode());
        result = prime * result + ((getB021() == null) ? 0 : getB021().hashCode());
        result = prime * result + ((getB022() == null) ? 0 : getB022().hashCode());
        result = prime * result + ((getB023() == null) ? 0 : getB023().hashCode());
        result = prime * result + ((getB024() == null) ? 0 : getB024().hashCode());
        result = prime * result + ((getB025() == null) ? 0 : getB025().hashCode());
        result = prime * result + ((getB026() == null) ? 0 : getB026().hashCode());
        result = prime * result + ((getB027() == null) ? 0 : getB027().hashCode());
        result = prime * result + ((getB028() == null) ? 0 : getB028().hashCode());
        result = prime * result + ((getB029() == null) ? 0 : getB029().hashCode());
        result = prime * result + ((getB030() == null) ? 0 : getB030().hashCode());
        result = prime * result + ((getB031() == null) ? 0 : getB031().hashCode());
        result = prime * result + ((getB032() == null) ? 0 : getB032().hashCode());
        result = prime * result + ((getB033() == null) ? 0 : getB033().hashCode());
        result = prime * result + ((getB034() == null) ? 0 : getB034().hashCode());
        result = prime * result + ((getB035() == null) ? 0 : getB035().hashCode());
        result = prime * result + ((getB036() == null) ? 0 : getB036().hashCode());
        result = prime * result + ((getC001() == null) ? 0 : getC001().hashCode());
        result = prime * result + ((getC002() == null) ? 0 : getC002().hashCode());
        result = prime * result + ((getC003() == null) ? 0 : getC003().hashCode());
        result = prime * result + ((getC004() == null) ? 0 : getC004().hashCode());
        result = prime * result + ((getC005() == null) ? 0 : getC005().hashCode());
        result = prime * result + ((getC006() == null) ? 0 : getC006().hashCode());
        result = prime * result + ((getC007() == null) ? 0 : getC007().hashCode());
        result = prime * result + ((getC008() == null) ? 0 : getC008().hashCode());
        result = prime * result + ((getC009() == null) ? 0 : getC009().hashCode());
        result = prime * result + ((getC010() == null) ? 0 : getC010().hashCode());
        result = prime * result + ((getC011() == null) ? 0 : getC011().hashCode());
        result = prime * result + ((getC012() == null) ? 0 : getC012().hashCode());
        result = prime * result + ((getC013() == null) ? 0 : getC013().hashCode());
        result = prime * result + ((getC014() == null) ? 0 : getC014().hashCode());
        result = prime * result + ((getC015() == null) ? 0 : getC015().hashCode());
        result = prime * result + ((getC016() == null) ? 0 : getC016().hashCode());
        result = prime * result + ((getC017() == null) ? 0 : getC017().hashCode());
        result = prime * result + ((getC018() == null) ? 0 : getC018().hashCode());
        result = prime * result + ((getC019() == null) ? 0 : getC019().hashCode());
        result = prime * result + ((getC020() == null) ? 0 : getC020().hashCode());
        result = prime * result + ((getC021() == null) ? 0 : getC021().hashCode());
        result = prime * result + ((getC023() == null) ? 0 : getC023().hashCode());
        result = prime * result + ((getD001() == null) ? 0 : getD001().hashCode());
        result = prime * result + ((getD002() == null) ? 0 : getD002().hashCode());
        result = prime * result + ((getD003() == null) ? 0 : getD003().hashCode());
        result = prime * result + ((getD004() == null) ? 0 : getD004().hashCode());
        result = prime * result + ((getD005() == null) ? 0 : getD005().hashCode());
        result = prime * result + ((getD006() == null) ? 0 : getD006().hashCode());
        result = prime * result + ((getD007() == null) ? 0 : getD007().hashCode());
        result = prime * result + ((getD008() == null) ? 0 : getD008().hashCode());
        result = prime * result + ((getD009() == null) ? 0 : getD009().hashCode());
        result = prime * result + ((getD010() == null) ? 0 : getD010().hashCode());
        result = prime * result + ((getD011() == null) ? 0 : getD011().hashCode());
        result = prime * result + ((getD012() == null) ? 0 : getD012().hashCode());
        result = prime * result + ((getD013() == null) ? 0 : getD013().hashCode());
        result = prime * result + ((getD014() == null) ? 0 : getD014().hashCode());
        result = prime * result + ((getD015() == null) ? 0 : getD015().hashCode());
        result = prime * result + ((getD016() == null) ? 0 : getD016().hashCode());
        result = prime * result + ((getD017() == null) ? 0 : getD017().hashCode());
        result = prime * result + ((getD018() == null) ? 0 : getD018().hashCode());
        result = prime * result + ((getD019() == null) ? 0 : getD019().hashCode());
        result = prime * result + ((getD020() == null) ? 0 : getD020().hashCode());
        result = prime * result + ((getD021() == null) ? 0 : getD021().hashCode());
        result = prime * result + ((getD022() == null) ? 0 : getD022().hashCode());
        result = prime * result + ((getD023() == null) ? 0 : getD023().hashCode());
        result = prime * result + ((getD024() == null) ? 0 : getD024().hashCode());
        result = prime * result + ((getD025() == null) ? 0 : getD025().hashCode());
        result = prime * result + ((getD026() == null) ? 0 : getD026().hashCode());
        result = prime * result + ((getE001() == null) ? 0 : getE001().hashCode());
        result = prime * result + ((getE002() == null) ? 0 : getE002().hashCode());
        result = prime * result + ((getE003() == null) ? 0 : getE003().hashCode());
        result = prime * result + ((getE004() == null) ? 0 : getE004().hashCode());
        result = prime * result + ((getE005() == null) ? 0 : getE005().hashCode());
        result = prime * result + ((getE006() == null) ? 0 : getE006().hashCode());
        result = prime * result + ((getE007() == null) ? 0 : getE007().hashCode());
        result = prime * result + ((getE008() == null) ? 0 : getE008().hashCode());
        result = prime * result + ((getE009() == null) ? 0 : getE009().hashCode());
        result = prime * result + ((getE010() == null) ? 0 : getE010().hashCode());
        result = prime * result + ((getE011() == null) ? 0 : getE011().hashCode());
        result = prime * result + ((getE012() == null) ? 0 : getE012().hashCode());
        result = prime * result + ((getE013() == null) ? 0 : getE013().hashCode());
        result = prime * result + ((getE014() == null) ? 0 : getE014().hashCode());
        result = prime * result + ((getE015() == null) ? 0 : getE015().hashCode());
        result = prime * result + ((getE016() == null) ? 0 : getE016().hashCode());
        result = prime * result + ((getE017() == null) ? 0 : getE017().hashCode());
        result = prime * result + ((getE018() == null) ? 0 : getE018().hashCode());
        result = prime * result + ((getE019() == null) ? 0 : getE019().hashCode());
        result = prime * result + ((getE020() == null) ? 0 : getE020().hashCode());
        result = prime * result + ((getE021() == null) ? 0 : getE021().hashCode());
        result = prime * result + ((getE022() == null) ? 0 : getE022().hashCode());
        result = prime * result + ((getE023() == null) ? 0 : getE023().hashCode());
        result = prime * result + ((getE024() == null) ? 0 : getE024().hashCode());
        result = prime * result + ((getE025() == null) ? 0 : getE025().hashCode());
        result = prime * result + ((getE026() == null) ? 0 : getE026().hashCode());
        result = prime * result + ((getE027() == null) ? 0 : getE027().hashCode());
        result = prime * result + ((getE028() == null) ? 0 : getE028().hashCode());
        result = prime * result + ((getE029() == null) ? 0 : getE029().hashCode());
        result = prime * result + ((getE030() == null) ? 0 : getE030().hashCode());
        result = prime * result + ((getE031() == null) ? 0 : getE031().hashCode());
        result = prime * result + ((getE032() == null) ? 0 : getE032().hashCode());
        result = prime * result + ((getE033() == null) ? 0 : getE033().hashCode());
        result = prime * result + ((getE034() == null) ? 0 : getE034().hashCode());
        result = prime * result + ((getE035() == null) ? 0 : getE035().hashCode());
        result = prime * result + ((getE036() == null) ? 0 : getE036().hashCode());
        result = prime * result + ((getE037() == null) ? 0 : getE037().hashCode());
        result = prime * result + ((getE038() == null) ? 0 : getE038().hashCode());
        result = prime * result + ((getE039() == null) ? 0 : getE039().hashCode());
        result = prime * result + ((getE040() == null) ? 0 : getE040().hashCode());
        result = prime * result + ((getE041() == null) ? 0 : getE041().hashCode());
        result = prime * result + ((getE042() == null) ? 0 : getE042().hashCode());
        result = prime * result + ((getE043() == null) ? 0 : getE043().hashCode());
        result = prime * result + ((getE045() == null) ? 0 : getE045().hashCode());
        result = prime * result + ((getF001() == null) ? 0 : getF001().hashCode());
        result = prime * result + ((getF002() == null) ? 0 : getF002().hashCode());
        result = prime * result + ((getF003() == null) ? 0 : getF003().hashCode());
        result = prime * result + ((getF004() == null) ? 0 : getF004().hashCode());
        result = prime * result + ((getF005() == null) ? 0 : getF005().hashCode());
        result = prime * result + ((getF006() == null) ? 0 : getF006().hashCode());
        result = prime * result + ((getF007() == null) ? 0 : getF007().hashCode());
        result = prime * result + ((getF008() == null) ? 0 : getF008().hashCode());
        result = prime * result + ((getF009() == null) ? 0 : getF009().hashCode());
        result = prime * result + ((getF010() == null) ? 0 : getF010().hashCode());
        result = prime * result + ((getF011() == null) ? 0 : getF011().hashCode());
        result = prime * result + ((getF012() == null) ? 0 : getF012().hashCode());
        result = prime * result + ((getF013() == null) ? 0 : getF013().hashCode());
        result = prime * result + ((getF0012() == null) ? 0 : getF0012().hashCode());
        result = prime * result + ((getG001() == null) ? 0 : getG001().hashCode());
        result = prime * result + ((getG002() == null) ? 0 : getG002().hashCode());
        result = prime * result + ((getG003() == null) ? 0 : getG003().hashCode());
        result = prime * result + ((getG004() == null) ? 0 : getG004().hashCode());
        result = prime * result + ((getG005() == null) ? 0 : getG005().hashCode());
        result = prime * result + ((getG006() == null) ? 0 : getG006().hashCode());
        result = prime * result + ((getG007() == null) ? 0 : getG007().hashCode());
        result = prime * result + ((getG008() == null) ? 0 : getG008().hashCode());
        result = prime * result + ((getG009() == null) ? 0 : getG009().hashCode());
        result = prime * result + ((getG010() == null) ? 0 : getG010().hashCode());
        result = prime * result + ((getG011() == null) ? 0 : getG011().hashCode());
        result = prime * result + ((getG012() == null) ? 0 : getG012().hashCode());
        result = prime * result + ((getG013() == null) ? 0 : getG013().hashCode());
        result = prime * result + ((getG014() == null) ? 0 : getG014().hashCode());
        result = prime * result + ((getG015() == null) ? 0 : getG015().hashCode());
        result = prime * result + ((getG016() == null) ? 0 : getG016().hashCode());
        result = prime * result + ((getG017() == null) ? 0 : getG017().hashCode());
        result = prime * result + ((getG018() == null) ? 0 : getG018().hashCode());
        result = prime * result + ((getH001() == null) ? 0 : getH001().hashCode());
        result = prime * result + ((getH002() == null) ? 0 : getH002().hashCode());
        result = prime * result + ((getH003() == null) ? 0 : getH003().hashCode());
        result = prime * result + ((getH004() == null) ? 0 : getH004().hashCode());
        result = prime * result + ((getH005() == null) ? 0 : getH005().hashCode());
        result = prime * result + ((getH006() == null) ? 0 : getH006().hashCode());
        result = prime * result + ((getH007() == null) ? 0 : getH007().hashCode());
        result = prime * result + ((getH008() == null) ? 0 : getH008().hashCode());
        result = prime * result + ((getI001() == null) ? 0 : getI001().hashCode());
        result = prime * result + ((getI002() == null) ? 0 : getI002().hashCode());
        result = prime * result + ((getI003() == null) ? 0 : getI003().hashCode());
        result = prime * result + ((getI004() == null) ? 0 : getI004().hashCode());
        result = prime * result + ((getI005() == null) ? 0 : getI005().hashCode());
        result = prime * result + ((getJ001() == null) ? 0 : getJ001().hashCode());
        result = prime * result + ((getJ002() == null) ? 0 : getJ002().hashCode());
        result = prime * result + ((getJ003() == null) ? 0 : getJ003().hashCode());
        result = prime * result + ((getJ004() == null) ? 0 : getJ004().hashCode());
        result = prime * result + ((getJ005() == null) ? 0 : getJ005().hashCode());
        result = prime * result + ((getK001() == null) ? 0 : getK001().hashCode());
        result = prime * result + ((getK002() == null) ? 0 : getK002().hashCode());
        result = prime * result + ((getK003() == null) ? 0 : getK003().hashCode());
        result = prime * result + ((getK004() == null) ? 0 : getK004().hashCode());
        result = prime * result + ((getK005() == null) ? 0 : getK005().hashCode());
        result = prime * result + ((getK006() == null) ? 0 : getK006().hashCode());
        result = prime * result + ((getK007() == null) ? 0 : getK007().hashCode());
        result = prime * result + ((getL001() == null) ? 0 : getL001().hashCode());
        result = prime * result + ((getL002() == null) ? 0 : getL002().hashCode());
        result = prime * result + ((getL003() == null) ? 0 : getL003().hashCode());
        result = prime * result + ((getL004() == null) ? 0 : getL004().hashCode());
        result = prime * result + ((getL005() == null) ? 0 : getL005().hashCode());
        result = prime * result + ((getL006() == null) ? 0 : getL006().hashCode());
        result = prime * result + ((getL007() == null) ? 0 : getL007().hashCode());
        result = prime * result + ((getL008() == null) ? 0 : getL008().hashCode());
        result = prime * result + ((getL009() == null) ? 0 : getL009().hashCode());
        result = prime * result + ((getL010() == null) ? 0 : getL010().hashCode());
        result = prime * result + ((getL011() == null) ? 0 : getL011().hashCode());
        result = prime * result + ((getL012() == null) ? 0 : getL012().hashCode());
        result = prime * result + ((getL013() == null) ? 0 : getL013().hashCode());
        result = prime * result + ((getL014() == null) ? 0 : getL014().hashCode());
        result = prime * result + ((getL015() == null) ? 0 : getL015().hashCode());
        result = prime * result + ((getL016() == null) ? 0 : getL016().hashCode());
        result = prime * result + ((getL017() == null) ? 0 : getL017().hashCode());
        result = prime * result + ((getL018() == null) ? 0 : getL018().hashCode());
        result = prime * result + ((getM001() == null) ? 0 : getM001().hashCode());
        result = prime * result + ((getM002() == null) ? 0 : getM002().hashCode());
        result = prime * result + ((getM003() == null) ? 0 : getM003().hashCode());
        result = prime * result + ((getM004() == null) ? 0 : getM004().hashCode());
        result = prime * result + ((getM005() == null) ? 0 : getM005().hashCode());
        result = prime * result + ((getM006() == null) ? 0 : getM006().hashCode());
        result = prime * result + ((getM007() == null) ? 0 : getM007().hashCode());
        result = prime * result + ((getM008() == null) ? 0 : getM008().hashCode());
        result = prime * result + ((getM009() == null) ? 0 : getM009().hashCode());
        result = prime * result + ((getM010() == null) ? 0 : getM010().hashCode());
        result = prime * result + ((getM011() == null) ? 0 : getM011().hashCode());
        result = prime * result + ((getM012() == null) ? 0 : getM012().hashCode());
        result = prime * result + ((getN001() == null) ? 0 : getN001().hashCode());
        result = prime * result + ((getN002() == null) ? 0 : getN002().hashCode());
        result = prime * result + ((getN003() == null) ? 0 : getN003().hashCode());
        result = prime * result + ((getN004() == null) ? 0 : getN004().hashCode());
        result = prime * result + ((getN005() == null) ? 0 : getN005().hashCode());
        result = prime * result + ((getN006() == null) ? 0 : getN006().hashCode());
        result = prime * result + ((getN007() == null) ? 0 : getN007().hashCode());
        result = prime * result + ((getN008() == null) ? 0 : getN008().hashCode());
        result = prime * result + ((getN009() == null) ? 0 : getN009().hashCode());
        result = prime * result + ((getPa001() == null) ? 0 : getPa001().hashCode());
        result = prime * result + ((getPa002() == null) ? 0 : getPa002().hashCode());
        result = prime * result + ((getPa003() == null) ? 0 : getPa003().hashCode());
        result = prime * result + ((getPa004() == null) ? 0 : getPa004().hashCode());
        result = prime * result + ((getPa005() == null) ? 0 : getPa005().hashCode());
        result = prime * result + ((getPa006() == null) ? 0 : getPa006().hashCode());
        result = prime * result + ((getPa007() == null) ? 0 : getPa007().hashCode());
        result = prime * result + ((getPa008() == null) ? 0 : getPa008().hashCode());
        result = prime * result + ((getPa009() == null) ? 0 : getPa009().hashCode());
        result = prime * result + ((getPb001() == null) ? 0 : getPb001().hashCode());
        result = prime * result + ((getPb002() == null) ? 0 : getPb002().hashCode());
        result = prime * result + ((getPb003() == null) ? 0 : getPb003().hashCode());
        result = prime * result + ((getPb004() == null) ? 0 : getPb004().hashCode());
        result = prime * result + ((getPb005() == null) ? 0 : getPb005().hashCode());
        result = prime * result + ((getPb006() == null) ? 0 : getPb006().hashCode());
        result = prime * result + ((getPb007() == null) ? 0 : getPb007().hashCode());
        result = prime * result + ((getPb008() == null) ? 0 : getPb008().hashCode());
        result = prime * result + ((getPb009() == null) ? 0 : getPb009().hashCode());
        result = prime * result + ((getPc001() == null) ? 0 : getPc001().hashCode());
        result = prime * result + ((getPc002() == null) ? 0 : getPc002().hashCode());
        result = prime * result + ((getPc003() == null) ? 0 : getPc003().hashCode());
        result = prime * result + ((getPc004() == null) ? 0 : getPc004().hashCode());
        result = prime * result + ((getPc005() == null) ? 0 : getPc005().hashCode());
        result = prime * result + ((getPc006() == null) ? 0 : getPc006().hashCode());
        result = prime * result + ((getPc007() == null) ? 0 : getPc007().hashCode());
        result = prime * result + ((getPc008() == null) ? 0 : getPc008().hashCode());
        result = prime * result + ((getPc009() == null) ? 0 : getPc009().hashCode());
        result = prime * result + ((getPd001() == null) ? 0 : getPd001().hashCode());
        result = prime * result + ((getPd002() == null) ? 0 : getPd002().hashCode());
        result = prime * result + ((getPd003() == null) ? 0 : getPd003().hashCode());
        result = prime * result + ((getPd004() == null) ? 0 : getPd004().hashCode());
        result = prime * result + ((getPd005() == null) ? 0 : getPd005().hashCode());
        result = prime * result + ((getPd006() == null) ? 0 : getPd006().hashCode());
        result = prime * result + ((getPd007() == null) ? 0 : getPd007().hashCode());
        result = prime * result + ((getPd008() == null) ? 0 : getPd008().hashCode());
        result = prime * result + ((getPd009() == null) ? 0 : getPd009().hashCode());
        result = prime * result + ((getQ001() == null) ? 0 : getQ001().hashCode());
        result = prime * result + ((getQ002() == null) ? 0 : getQ002().hashCode());
        result = prime * result + ((getQ003() == null) ? 0 : getQ003().hashCode());
        result = prime * result + ((getQ004() == null) ? 0 : getQ004().hashCode());
        result = prime * result + ((getQ005() == null) ? 0 : getQ005().hashCode());
        result = prime * result + ((getQ006() == null) ? 0 : getQ006().hashCode());
        result = prime * result + ((getQ007() == null) ? 0 : getQ007().hashCode());
        result = prime * result + ((getQ008() == null) ? 0 : getQ008().hashCode());
        result = prime * result + ((getQ009() == null) ? 0 : getQ009().hashCode());
        result = prime * result + ((getQ010() == null) ? 0 : getQ010().hashCode());
        result = prime * result + ((getQ011() == null) ? 0 : getQ011().hashCode());
        result = prime * result + ((getQ012() == null) ? 0 : getQ012().hashCode());
        result = prime * result + ((getR001() == null) ? 0 : getR001().hashCode());
        result = prime * result + ((getS001() == null) ? 0 : getS001().hashCode());
        result = prime * result + ((getS002() == null) ? 0 : getS002().hashCode());
        result = prime * result + ((getS003() == null) ? 0 : getS003().hashCode());
        result = prime * result + ((getS004() == null) ? 0 : getS004().hashCode());
        result = prime * result + ((getS005() == null) ? 0 : getS005().hashCode());
        result = prime * result + ((getS006() == null) ? 0 : getS006().hashCode());
        result = prime * result + ((getS007() == null) ? 0 : getS007().hashCode());
        result = prime * result + ((getS008() == null) ? 0 : getS008().hashCode());
        result = prime * result + ((getS010() == null) ? 0 : getS010().hashCode());
        result = prime * result + ((getS011() == null) ? 0 : getS011().hashCode());
        result = prime * result + ((getT001() == null) ? 0 : getT001().hashCode());
        result = prime * result + ((getU001() == null) ? 0 : getU001().hashCode());
        result = prime * result + ((getU002() == null) ? 0 : getU002().hashCode());
        result = prime * result + ((getQ0022() == null) ? 0 : getQ0022().hashCode());
        result = prime * result + ((getU003() == null) ? 0 : getU003().hashCode());
        return result;
    }
}