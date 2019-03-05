package com.starsgroupchina.credit.bean.extend;

import com.starsgroupchina.common.utils.BeanUtil;
import com.starsgroupchina.common.utils.export.ExcelAnnotation;
import com.starsgroupchina.common.utils.export.ExcelMergeColumn;
import com.starsgroupchina.credit.bean.model.VReportEntry;
import lombok.Data;

import java.util.Date;

/**
 * Created by gexiaoshan on 2018/9/4.
 */
@Data
public class VReportEntryExtend {

    //第一行标题
    @ExcelMergeColumn(id =1,name ="贷款信息",number = 16)
    private String dkInfo;
    @ExcelMergeColumn(id =2,name ="个人信息",number = 38)
    private String grInfo;
    @ExcelMergeColumn(id =3,name ="职业信息",number = 22)
    private String zyInfo;
    @ExcelMergeColumn(id =4,name ="工商信息",number = 24)
    private String gsInfo;
    @ExcelMergeColumn(id =5,name ="房产信息",number = 45)
    private String fcInfo;
    @ExcelMergeColumn(id =6,name ="负债信息",number = 13)
    private String fzInfo;
    @ExcelMergeColumn(id =7,name ="车辆信息",number = 18)
    private String clInfo;
    @ExcelMergeColumn(id =8,name ="同行业信息",number = 8)
    private String thyInfo;
    @ExcelMergeColumn(id =10,name ="务农人员信息",number = 5)
    private String wnryInfo;
    @ExcelMergeColumn(id =11,name ="学生信息",number = 6)
    private String xsInfo;
    @ExcelMergeColumn(id =12,name ="社保信息",number = 7)
    private String sbInfo;
    @ExcelMergeColumn(id =13,name ="保单信息",number = 18)
    private String bdInfo;
    @ExcelMergeColumn(id =14,name ="担保人信息",number = 12)
    private String dbrInfo;
    @ExcelMergeColumn(id =15,name ="配偶信息",number = 9)
    private String poInfo;
    @ExcelMergeColumn(id =16,name ="联系人1信息",number = 9)
    private String lxr1Info;
    @ExcelMergeColumn(id =17,name ="联系人2信息",number = 9)
    private String lxr2Info;
    @ExcelMergeColumn(id =18,name ="联系人3信息",number = 9)
    private String lxr3Info;
    @ExcelMergeColumn(id =19,name ="联系人4信息",number = 9)
    private String lxr4Info;
    @ExcelMergeColumn(id =19,name ="业务信息",number = 12)
    private String ywInfo;
    @ExcelMergeColumn(id =19,name ="腾讯/阿里/京东",number = 8)
    private String txInfo;
    @ExcelMergeColumn(id =19,name ="征信信息",number = 3)
    private String zxInfo;
    @ExcelMergeColumn(id =20,name ="时间截点",number = 7)
    private String sjInfo;
    @ExcelMergeColumn(id =21,name ="资料清单",number = 2)
    private String zlInfo;



    //第二行标题
    //贷款信息
    @ExcelAnnotation(id = 1, name = {"序号"})
    private Integer rowId;

    @ExcelAnnotation(id = 2, name = {"进件编号"})
    private String projectNo;

    @ExcelAnnotation(id = 3, name = {"申请金额"})
    private Double loanAmount;

    @ExcelAnnotation(id = 4, name = {"申请期限"})
    private String loanDurationName;

    @ExcelAnnotation(id = 5, name = {"公司名称"})
    private String orgName;

    @ExcelAnnotation(id = 6, name = {"产品名称"})
    private String title;

    @ExcelAnnotation(id = 7, name = {"产品类型"})
    private String categoryName;

    @ExcelAnnotation(id = 8, name = {"贷款方式"})
    private String loanMethod;

    @ExcelAnnotation(id = 9, name = {"还款方式"})
    private String repaymentName;

    @ExcelAnnotation(id = 10, name = {"贷款用途"})
    private String loanUse;

    @ExcelAnnotation(id = 11, name = {"还款期间是否出省"})
    private String loanOutProvince;

    @ExcelAnnotation(id = 12, name = {"可接受的最高月还款额"})
    private Long loanMonthRepay;

    @ExcelAnnotation(id = 13, name = {"是否接受共同还款"})
    private String loanCommonRepay;

    @ExcelAnnotation(id = 14, name = {"借款人银行账户户名"})
    private String loanAccountName;

    @ExcelAnnotation(id = 15, name = {"账号"})
    private String loanAccount;

    @ExcelAnnotation(id = 16, name = {"开户行"})
    private String loanAccountBank;

    //个人信息
    @ExcelAnnotation(id = 17, name = {"姓名"})
    private String b001;

    @ExcelAnnotation(id = 18, name = {"性别"})
    private String b002;

    @ExcelAnnotation(id = 19, name = {"年龄"})
    private String b003;

    @ExcelAnnotation(id = 20, name = {"证件类型"})
    private String b004;

    @ExcelAnnotation(id = 21, name = {"证件号码"})
    private String b005;

    @ExcelAnnotation(id = 22, name = {"证件有效期"})
    private String b006;

    @ExcelAnnotation(id = 23, name = {"发证机关"})
    private String b007;

    @ExcelAnnotation(id = 24, name = {"户口类型"})
    private String b008;

    @ExcelAnnotation(id = 25, name = {"户口所在地"})
    private String b009;

    @ExcelAnnotation(id = 26, name = {"户籍地址"})
    private String b010;

    @ExcelAnnotation(id = 27, name = {"户籍地址邮编"})
    private String b011;

    @ExcelAnnotation(id = 28, name = {"籍贯"})
    private String b012;

    @ExcelAnnotation(id = 29, name = {"出生日期"})
    private String b013;

    @ExcelAnnotation(id = 30, name = {"最高学历"})
    private String b014;

    @ExcelAnnotation(id = 31, name = {"毕业学校"})
    private String b015;

    @ExcelAnnotation(id = 31, name = {"手机号码"})
    private String b016;

    @ExcelAnnotation(id = 32, name = {"婚姻状况"})
    private String b017;

    @ExcelAnnotation(id = 33, name = {"子女数目"})
    private String b018;

    @ExcelAnnotation(id = 34, name = {"最小子女年龄"})
    private String b019;

    @ExcelAnnotation(id = 35, name = {"最小子女性别"})
    private String b020;

    @ExcelAnnotation(id = 36, name = {"供养亲属数"})
    private String b021;

    @ExcelAnnotation(id = 37, name = {"现家庭地址"})
    private String b022;

    @ExcelAnnotation(id = 38, name = {"现家庭地址邮编"})
    private String b023;

    @ExcelAnnotation(id = 39, name = {"居住城市"})
    private String b024;

    @ExcelAnnotation(id = 40, name = {"住宅电话"})
    private String b025;

    @ExcelAnnotation(id = 41, name = {"住宅电话登记人"})
    private String b026;

    @ExcelAnnotation(id = 42, name = {"居住类型"})
    private String b027;

    @ExcelAnnotation(id = 43, name = {"共同居住人数"})
    private String b028;

    @ExcelAnnotation(id = 44, name = {"共同居住成员"})
    private String b029;

    @ExcelAnnotation(id = 45, name = {"现居住开始时间"})
    private String b030;

    @ExcelAnnotation(id = 46, name = {"来本市的年份"})
    private String b031;

    @ExcelAnnotation(id = 47, name = {"每月家庭支出"})
    private String b032;

    @ExcelAnnotation(id = 48, name = {"qq账号"})
    private String b033;

    @ExcelAnnotation(id = 49, name = {"电子邮箱"})
    private String b034;

    @ExcelAnnotation(id = 50, name = {"微信账号"})
    private String b035;

    @ExcelAnnotation(id = 51, name = {"微博账号"})
    private String b036;

    @ExcelAnnotation(id = 51, name = {"本异地"})
    private String b037;

    @ExcelAnnotation(id = 51, name = {"网内外"})
    private String b038;

    //职业信息
    @ExcelAnnotation(id = 52, name = {"单位全称"})
    private String c001;

    @ExcelAnnotation(id = 53, name = {"单位地址"})
    private String c002;

    @ExcelAnnotation(id = 54, name = {"单位地址邮编"})
    private String c003;

    @ExcelAnnotation(id = 55, name = {"工作城市"})
    private String c004;

    @ExcelAnnotation(id = 56, name = {"单位电话"})
    private String c005;

    @ExcelAnnotation(id = 57, name = {"单位传真"})
    private String c006;

    @ExcelAnnotation(id = 58, name = {"单位性质"})
    private String c007;

    @ExcelAnnotation(id = 59, name = {"行业类型"})
    private String c008;

    @ExcelAnnotation(id = 60, name = {"任职部门"})
    private String c009;

    @ExcelAnnotation(id = 61, name = {"职务"})
    private String c010;

    @ExcelAnnotation(id = 62, name = {"工作级别"})
    private String c011;

    @ExcelAnnotation(id = 63, name = {"工作性质"})
    private String c012;

    @ExcelAnnotation(id = 64, name = {"现单位入职时间"})
    private String c013;

    @ExcelAnnotation(id = 65, name = {"月收入"})
    private String c014;

    @ExcelAnnotation(id = 66, name = {"发薪日"})
    private String c015;

    @ExcelAnnotation(id = 67, name = {"发薪方式"})
    private String c016;

    @ExcelAnnotation(id = 68, name = {"前单位名称"})
    private String c017;

    @ExcelAnnotation(id = 69, name = {"其他收入"})
    private String c018;

    @ExcelAnnotation(id = 70, name = {"每月总收入"})
    private String c019;

    @ExcelAnnotation(id = 71, name = {"首次参加工作时间"})
    private String c020;

    @ExcelAnnotation(id = 72, name = {"客户类别"})
    private String c021;

    @ExcelAnnotation(id = 72, name = {"现单位入职时长（月）"})
    private String c013Month;

    //工商信息
    @ExcelAnnotation(id = 73, name = {"成立时间"})
    private String d001;

    @ExcelAnnotation(id = 74, name = {"注册时间"})
    private String d002;

    @ExcelAnnotation(id = 75, name = {"注册资金"})
    private String d003;

    @ExcelAnnotation(id = 76, name = {"实收资金"})
    private String d004;

    @ExcelAnnotation(id = 77, name = {"法人代表"})
    private String d005;

    @ExcelAnnotation(id = 78, name = {"客户身份"})
    private String d006;

    @ExcelAnnotation(id = 79, name = {"营业执照注册号"})
    private String d007;

    @ExcelAnnotation(id = 80, name = {"主营业务"})
    private String d008;

    @ExcelAnnotation(id = 81, name = {"年营业额"})
    private String d009;

    @ExcelAnnotation(id = 82, name = {"月营业额"})
    private String d010;

    @ExcelAnnotation(id = 83, name = {"毛利率"})
    private String d011;

    @ExcelAnnotation(id = 84, name = {"净利率"})
    private String d012;

    @ExcelAnnotation(id = 85, name = {"每月净利润额"})
    private String d013;

    @ExcelAnnotation(id = 86, name = {"月开销金额"})
    private String d014;

    @ExcelAnnotation(id = 87, name = {"员工人数"})
    private String d015;

    @ExcelAnnotation(id = 88, name = {"占股比例"})
    private String d016;

    @ExcelAnnotation(id = 89, name = {"经营场所性质"})
    private String d017;

    @ExcelAnnotation(id = 90, name = {"出租方电话"})
    private String d018;

    @ExcelAnnotation(id = 91, name = {"租金（元/月）"})
    private String d019;

    @ExcelAnnotation(id = 92, name = {"公司网址"})
    private String d020;

    @ExcelAnnotation(id = 93, name = {"上年度营业收入"})
    private String d021;

    @ExcelAnnotation(id = 94, name = {"上年度营业利润"})
    private String d022;

    @ExcelAnnotation(id = 95, name = {"其他收入"})
    private String d023;

    @ExcelAnnotation(id = 96, name = {"营业执照"})
    private String d024;

    //房产信息
    @ExcelAnnotation(id = 97, name = {"房产数量"})
    private String e001;

    @ExcelAnnotation(id = 98, name = {"房产地址"})
    private String e002;

    @ExcelAnnotation(id = 99, name = {"房产地址邮编"})
    private String e003;

    @ExcelAnnotation(id = 100, name = {"房产所属区域"})
    private String e004;

    @ExcelAnnotation(id = 101, name = {"房产编号"})
    private String e005;

    @ExcelAnnotation(id = 102, name = {"房产类型"})
    private String e006;

    @ExcelAnnotation(id = 103, name = {"产权比例"})
    private String e007;

    @ExcelAnnotation(id = 104, name = {"产权人"})
    private String e008;

    @ExcelAnnotation(id = 105, name = {"房产状态"})
    private String e009;

    @ExcelAnnotation(id = 106, name = {"购买方式"})
    private String e010;

    @ExcelAnnotation(id = 107, name = {"购买时间"})
    private String e011;

    @ExcelAnnotation(id = 108, name = {"购买价格"})
    private String e012;

    @ExcelAnnotation(id = 109, name = {"购买单价"})
    private String e013;

    @ExcelAnnotation(id = 110, name = {"建筑年份"})
    private String e014;

    @ExcelAnnotation(id = 111, name = {"建筑面积"})
    private String e015;

    @ExcelAnnotation(id = 112, name = {"现评估价格"})
    private String e016;

    @ExcelAnnotation(id = 113, name = {"有无贷款"})
    private String e017;

    @ExcelAnnotation(id = 114, name = {"贷款银行"})
    private String e018;

    @ExcelAnnotation(id = 115, name = {"贷款金额"})
    private String e019;

    @ExcelAnnotation(id = 116, name = {"贷款期限"})
    private String e020;

    @ExcelAnnotation(id = 117, name = {"月还款额"})
    private String e021;

    @ExcelAnnotation(id = 118, name = {"每月还款日"})
    private String e022;

    @ExcelAnnotation(id = 119, name = {"其他房产"})
    private String e023;

    @ExcelAnnotation(id = 120, name = {"其他房产地址"})
    private String e024;

    @ExcelAnnotation(id = 121, name = {"其他房产地址邮编"})
    private String e025;

    @ExcelAnnotation(id = 122, name = {"其他房产所属区域"})
    private String e026;

    @ExcelAnnotation(id = 123, name = {"其他房产编号"})
    private String e027;

    @ExcelAnnotation(id = 124, name = {"其他房产类型"})
    private String e028;

    @ExcelAnnotation(id = 125, name = {"其他房产产权比例"})
    private String e029;

    @ExcelAnnotation(id = 126, name = {"其他房产产权人"})
    private String e030;

    @ExcelAnnotation(id = 126, name = {"其他房产属性"})
    private String e046;

    @ExcelAnnotation(id = 127, name = {"其他房产购买方式"})
    private String e031;

    @ExcelAnnotation(id = 128, name = {"其他房产购买时间"})
    private String e032;

    @ExcelAnnotation(id = 129, name = {"其他房产购买价格"})
    private String e033;

    @ExcelAnnotation(id = 130, name = {"其他房产购买单价"})
    private String e034;

    @ExcelAnnotation(id = 131, name = {"其他房产建筑年份"})
    private String e035;

    @ExcelAnnotation(id = 132, name = {"其他房产建筑面积"})
    private String e036;

    @ExcelAnnotation(id = 133, name = {"其他房产现实际评估价格"})
    private String e037;

    @ExcelAnnotation(id = 134, name = {"其他房产有无贷款"})
    private String e038;

    @ExcelAnnotation(id = 135, name = {"其他房产贷款银行"})
    private String e039;

    @ExcelAnnotation(id = 136, name = {"其他房产贷款金额"})
    private String e040;

    @ExcelAnnotation(id = 137, name = {"其他房产贷款期限"})
    private String e041;

    @ExcelAnnotation(id = 138, name = {"其他房产月还款额"})
    private String e042;

    @ExcelAnnotation(id = 139, name = {"其他房产每月还款日"})
    private String e043;

    @ExcelAnnotation(id = 140, name = {"已还期数"})
    private String e045;

    //负债信息
    @ExcelAnnotation(id = 141, name = {"信用卡张数"})
    private String f001;

    @ExcelAnnotation(id = 142, name = {"信用卡当前欠款"})
    private String f002;

    @ExcelAnnotation(id = 143, name = {"单张信用卡最高额度"})
    private String f003;

    @ExcelAnnotation(id = 144, name = {"信用卡月还款额"})
    private String f004;

    @ExcelAnnotation(id = 145, name = {"信用贷款总额度"})
    private String f005;

    @ExcelAnnotation(id = 146, name = {"信用贷款笔数"})
    private String f006;

    @ExcelAnnotation(id = 147, name = {"信用贷款欠款"})
    private String f007;

    @ExcelAnnotation(id = 148, name = {"信用贷款月还款额"})
    private String f008;

    @ExcelAnnotation(id = 149, name = {"贷款总额度"})
    private String f009;

    @ExcelAnnotation(id = 150, name = {"贷款笔数"})
    private String f010;

    @ExcelAnnotation(id = 151, name = {"贷款欠款"})
    private String f011;

    @ExcelAnnotation(id = 152, name = {"贷款月还款额"})
    private String f012;

    @ExcelAnnotation(id = 153, name = {"信用卡总额"})
    private String f0012;

    //车辆信息
    @ExcelAnnotation(id = 154, name = {"车牌号码"})
    private String g001;

    @ExcelAnnotation(id = 155, name = {"品牌型号"})
    private String g002;

    @ExcelAnnotation(id = 156, name = {"车架号"})
    private String g003;

    @ExcelAnnotation(id = 157, name = {"行驶里程"})
    private String g004;

    @ExcelAnnotation(id = 158, name = {"购车类型"})
    private String g005;

    @ExcelAnnotation(id = 159, name = {"购买时间"})
    private String g006;

    @ExcelAnnotation(id = 160, name = {"购买价格"})
    private String g007;

    @ExcelAnnotation(id = 161, name = {"购买方式"})
    private String g008;

    @ExcelAnnotation(id = 162, name = {"评估价格"})
    private String g009;

    @ExcelAnnotation(id = 163, name = {"保险单"})
    private String g010;

    @ExcelAnnotation(id = 164, name = {"是否有车辆贷款历史"})
    private String g011;

    @ExcelAnnotation(id = 165, name = {"贷款机构"})
    private String g012;

    @ExcelAnnotation(id = 166, name = {"贷款金额"})
    private String g013;

    @ExcelAnnotation(id = 167, name = {"贷款日期"})
    private String g014;

    @ExcelAnnotation(id = 168, name = {"结清日期"})
    private String g015;

    @ExcelAnnotation(id = 169, name = {"月还款额"})
    private String g016;

    @ExcelAnnotation(id = 170, name = {"是否有逾期"})
    private String g017;

    @ExcelAnnotation(id = 171, name = {"家庭共有几辆车"})
    private String g018;

    //同行业信息
    @ExcelAnnotation(id = 173, name = {"产品名称"})
    private String h001;

    @ExcelAnnotation(id = 174, name = {"公司名称"})
    private String h002;

    @ExcelAnnotation(id = 175, name = {"借款金额"})
    private String h003;

    @ExcelAnnotation(id = 176, name = {"借款期限"})
    private String h004;

    @ExcelAnnotation(id = 177, name = {"月还款额"})
    private String h005;

    @ExcelAnnotation(id = 178, name = {"已还款期数"})
    private String h006;

    @ExcelAnnotation(id = 179, name = {"已还款金额"})
    private String h007;

    @ExcelAnnotation(id = 180, name = {"行业类型"})
    private String h008;

    //务农人员信息
    @ExcelAnnotation(id = 182, name = {"行业类型"})
    private String i000;

    @ExcelAnnotation(id = 182, name = {"从业年限"})
    private String i001;

    @ExcelAnnotation(id = 183, name = {"生产工具"})
    private String i002;

    @ExcelAnnotation(id = 184, name = {"主营产品"})
    private String i003;

    @ExcelAnnotation(id = 185, name = {"月收入"})
    private String i004;

    //学生信息
    @ExcelAnnotation(id = 187, name = {"学校名称"})
    private String i005;

    @ExcelAnnotation(id = 188, name = {"学校类型"})
    private String j001;

    @ExcelAnnotation(id = 189, name = {"专业名称"})
    private String j002;

    @ExcelAnnotation(id = 190, name = {"入学时间"})
    private String j003;

    @ExcelAnnotation(id = 191, name = {"预计毕业时间"})
    private String j004;

    @ExcelAnnotation(id = 192, name = {"生活费/月"})
    private String j005;

    //社保信息
    @ExcelAnnotation(id = 194, name = {"是否有社保卡"})
    private String k001;

    @ExcelAnnotation(id = 195, name = {"参保单位"})
    private String k002;

    @ExcelAnnotation(id = 196, name = {"个人社保电脑编号"})
    private String k003;

    @ExcelAnnotation(id = 197, name = {"现单位缴纳起始日"})
    private String k004;

    @ExcelAnnotation(id = 198, name = {"社保缴纳基数"})
    private String k005;

    @ExcelAnnotation(id = 199, name = {"公积金缴纳基数"})
    private String k006;

    @ExcelAnnotation(id = 200, name = {"社保累计缴纳月数"})
    private String k007;

    //保单信息
    @ExcelAnnotation(id = 202, name = {"保险公司"})
    private String l001;

    @ExcelAnnotation(id = 203, name = {"保单号"})
    private String l002;

    @ExcelAnnotation(id = 204, name = {"险种名称"})
    private String l003;

    @ExcelAnnotation(id = 205, name = {"约定缴费年限"})
    private String l004;

    @ExcelAnnotation(id = 206, name = {"首次投保时间"})
    private String l005;

    @ExcelAnnotation(id = 207, name = {"到期时间"})
    private String l006;

    @ExcelAnnotation(id = 208, name = {"缴费金额"})
    private String l007;

    @ExcelAnnotation(id = 209, name = {"总保额"})
    private String l008;

    @ExcelAnnotation(id = 210, name = {"缴费方式"})
    private String l009;

    @ExcelAnnotation(id = 211, name = {"受益人姓名"})
    private String l010;

    @ExcelAnnotation(id = 212, name = {"受益人身份证"})
    private String l011;

    @ExcelAnnotation(id = 213, name = {"与受益人关系"})
    private String l012;

    @ExcelAnnotation(id = 214, name = {"受益人手机号码"})
    private String l013;

    @ExcelAnnotation(id = 215, name = {"被保险人姓名"})
    private String l014;

    @ExcelAnnotation(id = 216, name = {"被保险人身份证"})
    private String l015;

    @ExcelAnnotation(id = 217, name = {"与被保险人关系"})
    private String l016;

    @ExcelAnnotation(id = 218, name = {"被保险人手机号码"})
    private String l017;

    @ExcelAnnotation(id = 219, name = {"已缴期数"})
    private String l018;

    //担保人信息
    @ExcelAnnotation(id = 221, name = {"担保人与客户关系"})
    private String m001;

    @ExcelAnnotation(id = 222, name = {"担保人姓名"})
    private String m002;

    @ExcelAnnotation(id = 223, name = {"担保人手机号码"})
    private String m003;

    @ExcelAnnotation(id = 224, name = {"担保人居住地址"})
    private String m004;

    @ExcelAnnotation(id = 225, name = {"担保人公司名称"})
    private String m005;

    @ExcelAnnotation(id = 226, name = {"担保人职位"})
    private String m006;

    @ExcelAnnotation(id = 227, name = {"担保人公司电话"})
    private String m007;

    @ExcelAnnotation(id = 228, name = {"担保人身份证号"})
    private String m008;

    @ExcelAnnotation(id = 229, name = {"担保人婚姻状况"})
    private String m009;

    @ExcelAnnotation(id = 230, name = {"担保人公司部门"})
    private String m010;

    @ExcelAnnotation(id = 231, name = {"担保人公司地址"})
    private String m011;

    @ExcelAnnotation(id = 232, name = {"担保人公司分机"})
    private String m012;

    //配偶信息
    @ExcelAnnotation(id = 234, name = {"姓名"})
    private String n001;

    @ExcelAnnotation(id = 235, name = {"手机"})
    private String n002;

    @ExcelAnnotation(id = 236, name = {"身份证号码"})
    private String n003;

    @ExcelAnnotation(id = 237, name = {"固话"})
    private String n004;

    @ExcelAnnotation(id = 238, name = {"住址电话"})
    private String n005;

    @ExcelAnnotation(id = 239, name = {"单位名称"})
    private String n006;

    @ExcelAnnotation(id = 240, name = {"单位地址"})
    private String n007;

    @ExcelAnnotation(id = 242, name = {"单位电话"})
    private String n008;

    @ExcelAnnotation(id = 243, name = {"是否知晓贷款"})
    private String n009;

    //联系人1信息
    @ExcelAnnotation(id = 245, name = {"姓名"})
    private String pa001;

    @ExcelAnnotation(id = 246, name = {"关系"})
    private String pa002;

    @ExcelAnnotation(id = 247, name = {"手机"})
    private String pa003;

    @ExcelAnnotation(id = 248, name = {"固话"})
    private String pa004;

    @ExcelAnnotation(id = 249, name = {"住址"})
    private String pa005;

    @ExcelAnnotation(id = 250, name = {"是否知晓贷款"})
    private String pa006;

    @ExcelAnnotation(id = 251, name = {"单位名称"})
    private String pa007;

    @ExcelAnnotation(id = 252, name = {"单位地址"})
    private String pa008;

    @ExcelAnnotation(id = 253, name = {"单位电话"})
    private String pa009;

    //联系人2信息
    @ExcelAnnotation(id = 255, name = {"姓名"})
    private String pb001;

    @ExcelAnnotation(id = 256, name = {"关系"})
    private String pb002;

    @ExcelAnnotation(id = 257, name = {"手机"})
    private String pb003;

    @ExcelAnnotation(id = 258, name = {"固话"})
    private String pb004;

    @ExcelAnnotation(id = 259, name = {"住址"})
    private String pb005;

    @ExcelAnnotation(id = 260, name = {"是否知晓贷款"})
    private String pb006;

    @ExcelAnnotation(id = 261, name = {"单位名称"})
    private String pb007;

    @ExcelAnnotation(id = 262, name = {"单位地址"})
    private String pb008;

    @ExcelAnnotation(id = 263, name = {"单位电话"})
    private String pb009;

    //联系人3信息
    @ExcelAnnotation(id = 265, name = {"姓名"})
    private String pc001;

    @ExcelAnnotation(id = 266, name = {"关系"})
    private String pc002;

    @ExcelAnnotation(id = 267, name = {"手机"})
    private String pc003;

    @ExcelAnnotation(id = 268, name = {"固话"})
    private String pc004;

    @ExcelAnnotation(id = 269, name = {"住址"})
    private String pc005;

    @ExcelAnnotation(id = 270, name = {"是否知晓贷款"})
    private String pc006;

    @ExcelAnnotation(id = 271, name = {"单位名称"})
    private String pc007;

    @ExcelAnnotation(id = 272, name = {"单位地址"})
    private String pc008;

    @ExcelAnnotation(id = 273, name = {"单位电话"})
    private String pc009;

    //联系人4信息
    @ExcelAnnotation(id = 275, name = {"姓名"})
    private String pd001;

    @ExcelAnnotation(id = 276, name = {"关系"})
    private String pd002;

    @ExcelAnnotation(id = 277, name = {"手机"})
    private String pd003;

    @ExcelAnnotation(id = 278, name = {"固话"})
    private String pd004;

    @ExcelAnnotation(id = 279, name = {"住址"})
    private String pd005;

    @ExcelAnnotation(id = 280, name = {"是否知晓贷款"})
    private String pd006;

    @ExcelAnnotation(id = 281, name = {"单位名称"})
    private String pd007;

    @ExcelAnnotation(id = 282, name = {"单位地址"})
    private String pd008;

    @ExcelAnnotation(id = 283, name = {"单位电话"})
    private String pd009;

    //业务信息
    @ExcelAnnotation(id = 285, name = {"营业部"})
    private String q001;

    @ExcelAnnotation(id = 286, name = {"营业部地址"})
    private String q002;

    @ExcelAnnotation(id = 287, name = {"所属区域"})
    private String q003;

    @ExcelAnnotation(id = 288, name = {"业务团队"})
    private String q004;

    @ExcelAnnotation(id = 289, name = {"渠道名称"})
    private String q005;

    @ExcelAnnotation(id = 290, name = {"渠道代码"})
    private String q006;

    @ExcelAnnotation(id = 291, name = {"客户经理"})
    private String q007;

    @ExcelAnnotation(id = 292, name = {"联系电话"})
    private String q008;

    @ExcelAnnotation(id = 293, name = {"团队主任"})
    private String q009;

    @ExcelAnnotation(id = 294, name = {"员工编号"})
    private String q010;

    @ExcelAnnotation(id = 295, name = {"对本公司的了解渠道"})
    private String q011;

    @ExcelAnnotation(id = 296, name = {"客户来源"})
    private String q012;

    //腾讯/阿里/京东
    @ExcelAnnotation(id = 298, name = {"微粒贷授信金额"})
    private String s001;

    @ExcelAnnotation(id = 299, name = {"花呗/借呗授信金额"})
    private String s002;

    @ExcelAnnotation(id = 300, name = {"白条/金条授信金额"})
    private String s003;

    @ExcelAnnotation(id = 301, name = {"借款金额"})
    private String s004;

    @ExcelAnnotation(id = 302, name = {"借款期限"})
    private String s005;

    @ExcelAnnotation(id = 303, name = {"月还款额"})
    private String s006;

    @ExcelAnnotation(id = 304, name = {"已还款期数"})
    private String s007;

    @ExcelAnnotation(id = 305, name = {"已还款金额"})
    private String s008;

    //征信信息
    @ExcelAnnotation(id = 307, name = {"近7天征信查询次数"})
    private String u001;

    @ExcelAnnotation(id = 308, name = {"近1个月征信查询次数"})
    private String u002;

    @ExcelAnnotation(id = 309, name = {"近3个月征信查询次数"})
    private String u003;

    /*@ExcelAnnotation(id = 310, name = {"营业部所在城市"})
    private String q0022;*/

    //时间截点
    @ExcelAnnotation(id = 312, name = {"录入日期"})
    private Date createTime;

    @ExcelAnnotation(id = 313, name = {"进件日期"})
    private Date beginAuditPoolDate;

    @ExcelAnnotation(id = 313, name = {"审核日期"})
    private Date endAuditDate;

    @ExcelAnnotation(id = 314, name = {"复核日期"})
    private Date endRecheckDate;

    @ExcelAnnotation(id = 315, name = {"签约日期"})
    private Date signTime;

    @ExcelAnnotation(id = 316, name = {"放款日期"})
    private Date fkTime;

    @ExcelAnnotation(id = 317, name = {"首期还款日"})
    private Date schkTime;

    //资料清单
    @ExcelAnnotation(id = 318, name = {"基本资料"})
    private String r001;
    @ExcelAnnotation(id = 318, name = {"辅助资料"})
    private String r002;

    public VReportEntryExtend() {
    }

    public VReportEntryExtend(VReportEntry vReportEntry) {
        BeanUtil.copyProperty(vReportEntry, this);
    }
}
