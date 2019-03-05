package com.starsgroupchina.credit.server.client.wrapper;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.starsgroupchina.common.utils.Utils;
import com.starsgroupchina.credit.bean.UserInfo;
import com.starsgroupchina.credit.bean.enums.*;
import com.starsgroupchina.credit.bean.model.*;
import com.starsgroupchina.credit.bean.third.ThirdCreditTdAddress;
import com.starsgroupchina.credit.bean.third.ThirdCreditTdItem;
import com.starsgroupchina.credit.bean.third.ThirdCreditTdReport;
import com.starsgroupchina.credit.bean.third.ThirdRequestParam;
import com.starsgroupchina.credit.server.conf.RedisConf;
import com.starsgroupchina.credit.server.conf.TdConfig;
import com.starsgroupchina.credit.server.service.project.*;
import com.starsgroupchina.credit.server.service.third.ThirdCreditResultService;
import com.starsgroupchina.credit.server.service.third.ThirdDataValidService;
import com.starsgroupchina.credit.server.utils.DateUtil;
import com.starsgroupchina.credit.server.utils.third.td.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import static java.util.stream.Collectors.toList;

@Component
@Slf4j
public class TdCreditWrapper {

    @Autowired
    private ThirdCreditResultService thirdCreditResultService;

    @Autowired
    private UserService userService;

    @Autowired
    private ContactService contactService;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ScoreService scoreService;

    @Resource(name = RedisConf.REDIS_TEMPLATE)
    private RedisOperations redisOperations;

    @Value("${third.expire}")
    private Integer expire;
    @Autowired
    private ThirdDataValidService thirdDataValidService;
    @Autowired
    private ReportService reportService;
    private static final int waitTime = 5000;
    final static TimeUnit TIME_UNIT = TimeUnit.MINUTES;
    private final static String key = "td_run:";
    private static final ObjectMapper objectMapper = new ObjectMapper();

    private TdPreloanSubmitResponse applyReport(TdPreloanSubmitRequest tdPreloanSubmitRequest, ThirdRequestParam thirdRequestParam) {
        Map<String, Object> params = new HashMap<String, Object>();
        try {
            params = CommonUtil.parseBean2Map(tdPreloanSubmitRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return HttpRequestUtil.apply(params, thirdRequestParam);
    }

    private TdPreloanQueryResponse queryReport(String reportCode, ThirdRequestParam thirdRequestParam) {
        return HttpRequestUtil.query(reportCode, thirdRequestParam);
    }

    private String getRelation(String relation) {
        String relatedName = "other";
        if ("配偶".equals(relation)) {
            relatedName = "spouse";
        }
        if ("父亲".equals(relation)) {
            relatedName = "father";
        }
        if ("母亲".equals(relation)) {
            relatedName = "mother";
        }
        if ("子女".equals(relation)){
            relatedName = "child";
        }
        if ("同事".equals(relation)){
            relatedName = "coworker";
        }//同事
        if ("朋友".equals(relation)){
            relatedName = "friend";
        }//朋友
        if ("兄弟".equals(relation) || "姐妹".equals(relation) || "亲戚".equals(relation)) {
            relatedName = "other_relative";
        }//其他亲属
        return relatedName;
    }

    public TdPreloanQueryResponse queryAndSaveReport(ThirdRequestParam thirdRequestParam) {
        log.info("同盾征信接口请求==============");
        String tdKey = key+thirdRequestParam.getProjectNo();
        Boolean isRun = redisOperations.opsForValue().setIfAbsent(tdKey, key);
        if (!isRun) {
            log.info("{}同盾征信接口正在调用",thirdRequestParam.getProjectNo());
            return null;
        }
        //设置过期时间
        redisOperations.expire(tdKey, expire, TIME_UNIT);

        UserInfo userInfo = userService.getUserInfo(thirdRequestParam.getProjectNo());
        if (userInfo.getProjectNo()==null) {
            log.error("未查询到贷款人信息");
            return new TdPreloanQueryResponse();
        }
        log.info("同盾征信接口调用===================");
        ProjectExample projectExample = new ProjectExample();
        projectExample.createCriteria().andProjectNoEqualTo(thirdRequestParam.getProjectNo());
        Project project = Utils.getFirst(projectService.query(projectExample));
        TdPreloanSubmitRequest tdPreloanSubmitRequest = new TdPreloanSubmitRequest();
        DateFormat sdf = DateUtil.yyyyMMddFormatter.get();
        tdPreloanSubmitRequest.setLoan_date(sdf.format(project.getApplyDate())); // 申请借款日期
        tdPreloanSubmitRequest.setName(userInfo.getName()); //姓名
        tdPreloanSubmitRequest.setId_number(userInfo.getIdCard());// 身份证号
        tdPreloanSubmitRequest.setMobile(userInfo.getPhone() == null ? "" : userInfo.getPhone());// 手机号
        tdPreloanSubmitRequest.setHome_phone(userInfo.getTel() == null ? "" : userInfo.getTel()); // 家庭座机
        tdPreloanSubmitRequest.setWork_phone(userInfo.getCompanyPhone() == null ? "" : userInfo.getCompanyPhone());//单位座机
        tdPreloanSubmitRequest.setRegistered_address(userInfo.getDomicileAddress() == null ? "" : userInfo.getDomicileAddress());// 户籍地址 xx省xx市xx县xx镇xx村xx组xx号
        tdPreloanSubmitRequest.setCompany_name(userInfo.getCompanyName() == null ? "" : userInfo.getCompanyName()); // 工作单位
        tdPreloanSubmitRequest.setCompany_address(userInfo.getCompanyAddress() == null ? "" : userInfo.getCompanyAddress()); // 单位地址
        if (userInfo.getCompanyType() != null) {
            String companyType = userInfo.getCompanyType();
            String compPropName = "其他";
            if ("事业单位".equals(companyType)) compPropName = "国有股份";
            if ("政府机构".equals(companyType)) compPropName = "机关事业";
            if ("私营".equals(companyType)) compPropName = "私营";
            tdPreloanSubmitRequest.setCompany_type(compPropName);// 公司性质
        }
        tdPreloanSubmitRequest.setApplyer_type("在职"); // 申请人类别
        if (userInfo.getMonthIncome() != null) {
            double yearIncome = Double.valueOf(userInfo.getMonthIncome()) * 12;
            String yearIncomeName = "10000以下";
            if (yearIncome > 10000 && yearIncome <= 50000) yearIncomeName = "10000-50000";
            if (yearIncome > 50000 && yearIncome <= 100000) yearIncomeName = "50000-100000";
            if (yearIncome > 100000 && yearIncome <= 200000) yearIncomeName = "100000-200000";
            if (yearIncome > 200000) yearIncomeName = "200000以上";
            tdPreloanSubmitRequest.setAnnual_income(yearIncomeName); // 年收入
        }
        if (userInfo.getEducation() != null) {
            String education = userInfo.getEducation();
            String degreeName = "PRE_HIGH_SCHOOL";
            if ("高中".equals(education)) degreeName = "HIGH_SCHOOL";
            if ("大专".equals(education)) degreeName = "JUNIOR_COLLEGE";
            if ("本科".equals(education)) degreeName = "UNDER_GRADUATE";
            if ("研究生".equals(education)) degreeName = "POST_GRADUATE";
            tdPreloanSubmitRequest.setDiploma(degreeName);// 学历
        }
        if(userInfo.getMaritalStatus() != null){
            String maritalStatus = userInfo.getMaritalStatus();
            String marriageName = "SPINSTERHOOD";
        		if("已婚".equals(maritalStatus)) marriageName = "MARRIED";
        		if("离异".equals(maritalStatus)) marriageName = "DIVORCED";
        		if("丧偶".equals(maritalStatus)) marriageName = "WIDOWED";
        		tdPreloanSubmitRequest.setMarriage(marriageName); // 婚姻
        }
        tdPreloanSubmitRequest.setContact_address(userInfo.getFamilyAddress());// 通讯地址
        tdPreloanSubmitRequest.setOccupation("");
        ProjectContactExample projectContactExample = new ProjectContactExample();
        projectContactExample.createCriteria().andProjectNoEqualTo(thirdRequestParam.getProjectNo()).andSourceEqualTo(0).andPhoneNotEqualTo("").andPhoneIsNotNull();
        List<ProjectContact> contactList = contactService.query(projectContactExample).sorted(Comparator.comparingInt(ProjectContact::getIdx)).collect(toList());
        if (CollectionUtils.isNotEmpty(contactList)) {
            ProjectContact concat = contactList.stream().filter(projectContact -> projectContact.getIdx()==1).collect(toList()).get(0);
            String related = concat.getRelation();
            if (StringUtils.isNotEmpty(related)) {
                tdPreloanSubmitRequest.setContact1_relation(getRelation(related));// 第一联系人社会关系
            }
            if (StringUtils.isNotEmpty(concat.getName())) {
                tdPreloanSubmitRequest.setConcatc1_name(concat.getName()); // 第一联系人姓名
            }
            if (StringUtils.isNotEmpty(concat.getPhone())) {
                tdPreloanSubmitRequest.setContact1_mobile(concat.getPhone());// 第一联系人手机号
            }
            if (StringUtils.isNotEmpty(concat.getAddress())) {
                tdPreloanSubmitRequest.setContact1_addr(concat.getAddress()); // 第一联系人家庭地址
            }
            if (StringUtils.isNotEmpty(concat.getCompanyName())) {
                tdPreloanSubmitRequest.setContact1_com_name(concat.getCompanyName());// 第一联系人工作单位
            }
            if (contactList.size() > 2) {
                concat = contactList.get(1);
                related = concat.getRelation();
                if (StringUtils.isNotEmpty(related)) {
                    tdPreloanSubmitRequest.setContact2_relation(getRelation(related));// 第二联系人社会关系
                }
                if (StringUtils.isNotEmpty(concat.getName())) {
                    tdPreloanSubmitRequest.setContact2_name(concat.getName()); // 第二联系人姓名
                }
                if (StringUtils.isNotEmpty(concat.getPhone())) {
                    tdPreloanSubmitRequest.setContact2_mobile(concat.getPhone());// 第二联系人手机号
                }
                if (StringUtils.isNotEmpty(concat.getAddress())) {
                    tdPreloanSubmitRequest.setContact2_addr(concat.getAddress());// 第二联系人家庭地址
                }
                if (StringUtils.isNotEmpty(concat.getCompanyName())) {
                    tdPreloanSubmitRequest.setContact2_com_name(concat.getCompanyName());// 第二联系人工作单位
                }
            }
        }
        log.info("同盾征信参数设置完成===================");
        TdPreloanSubmitResponse tdPreloanSubmitResponse = applyReport(tdPreloanSubmitRequest, thirdRequestParam);
//        TdPreloanQueryResponse tdPreloanQueryResponse = null;
        log.info("同盾征信请求结果"+tdPreloanSubmitResponse);
        if (tdPreloanSubmitResponse.getSuccess()) {
            CompletableFuture.runAsync(() -> {
                try {
                    Thread.sleep(waitTime);
                    log.info("同盾征信接口调用===================");
                    TdPreloanQueryResponse tdPreloanQueryResponse = queryReport(tdPreloanSubmitResponse.getReport_id(),thirdRequestParam);
                    System.out.println(tdPreloanQueryResponse);
                    while (!tdPreloanQueryResponse.getSuccess()) {
                        Thread.sleep(waitTime);
                        tdPreloanQueryResponse = queryReport(tdPreloanSubmitResponse.getReport_id(), thirdRequestParam);
                    }
                    ThirdCreditTdReport thirdCreditTdReport = new ThirdCreditTdReport();
                    thirdCreditTdReport.setIsQueryThird(true);
                    thirdCreditTdReport.setProjectId(thirdRequestParam.getProjectId());
                    thirdCreditTdReport.setReportCode(tdPreloanQueryResponse.getReport_id());
                    thirdCreditTdReport.setApplicationCode(tdPreloanQueryResponse.getApplication_id());
                    thirdCreditTdReport.setApplyTime(new Date(tdPreloanQueryResponse.getApply_time()));
                    thirdCreditTdReport.setFinalDecision(tdPreloanQueryResponse.getFinal_decision());
                    thirdCreditTdReport.setFinalScore(Long.valueOf(tdPreloanQueryResponse.getFinal_score()));
                    thirdCreditTdReport.setReportTime(new Date(tdPreloanQueryResponse.getReport_time()));
//            		maCreditTdReport.setBillerId(userId);
                    thirdCreditTdReport.setBillDate(new Date());
                    thirdCreditTdReport.setSuccess(1);
                    ArrayList<ThirdCreditTdAddress> addressList = Lists.newArrayList();
                    ArrayList<ThirdCreditTdItem> itemList = Lists.newArrayList();
                    JSONObject address = tdPreloanQueryResponse.getAddress_detect();
                    if (address != null && address.size() > 0) {
                        ThirdCreditTdAddress thirdCreditTdAddress = new ThirdCreditTdAddress();
                        thirdCreditTdAddress.setIdCardAddress(address.getString("id_card_address"));
                        thirdCreditTdAddress.setTrueIpAddress(address.getString("true_ip_address"));
                        thirdCreditTdAddress.setWifiAddress(address.getString("wifi_address"));
                        thirdCreditTdAddress.setCellAddress(address.getString("cell_address"));
                        thirdCreditTdAddress.setBankCardAddress(address.getString("bank_card_address"));
                        thirdCreditTdAddress.setMobileAddress(address.getString("mobile_address"));
                        addressList.add(thirdCreditTdAddress);
                    }
                    thirdCreditTdReport.setThirdCreditTdAddresses(addressList);
//            		//保存检测项目信息
                    JSONArray items = tdPreloanQueryResponse.getRisk_items();
                    StringBuilder sb = new StringBuilder();
                    if (items != null && items.size() > 0) {
//            			MaCreditTdItem item = new MaCreditTdItem();
                        JSONObject jsonItem = null;
                        String itemDetail = null;
                        for (Object object : items) {
                            ThirdCreditTdItem item = new ThirdCreditTdItem();
                            sb.delete(0, sb.length());
                            jsonItem = JSON.parseObject(object.toString());
                            item.setItemName(jsonItem.getString("item_name"));
                            item.setItemCode(Long.valueOf(jsonItem.getString("item_id")));
                            item.setItemGroup(jsonItem.getString("group"));
                            item.setRiskLevel(jsonItem.getString("risk_level"));
                            itemDetail = jsonItem.getString("item_detail");
                            //保存项目风险详情
                            if (StringUtils.isNotEmpty(itemDetail)) {
                                item.setDetails(parseItemDetail(itemDetail, sb));
                            }
                            itemList.add(item);
                        }
                        thirdCreditTdReport.setThirdCreditTdItems(itemList);
                    }
                    ThirdCreditResult thirdCreditResult = new ThirdCreditResult();
                    thirdCreditResult.setProjectId(thirdRequestParam.getProjectId());
                    thirdCreditResult.setProjectNo(thirdRequestParam.getProjectNo());
                    thirdCreditResult.setSource(ThirdCreditSource.SOURCE_TD.key());
                    thirdCreditResult.setThirdProductType(ThirdProductType.TYPE_TD.key());
                    thirdCreditResult.setResult(objectMapper.writeValueAsString(thirdCreditTdReport));
                    thirdCreditResultService.create(thirdCreditResult);
                    if (project.getStatus().equals(ProjectStatus.CREDIT_REPORT.key())) {
                        scoreService.computeScore(thirdRequestParam.getProjectNo());
                        boolean isTure = thirdDataValidService.validThirdData(thirdRequestParam.getProjectNo());
                        if (!isTure) {
                            reportService.buildReport(thirdRequestParam.getProjectNo(), BuildReportSource.THIRD_DATA_VALID_FAIL);
                        }else {
                            reportService.buildReport(thirdRequestParam.getProjectNo(), BuildReportSource.NORMAL);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    redisOperations.delete(key);
                }

            });
        }
        return null;
    }

    private String parseItemDetail(String itemDetail, StringBuilder sb) {
        JSONObject jsonItemDetail = JSON.parseObject(itemDetail);
        //信贷逾期次数
        if ( StringUtils.isNotEmpty(jsonItemDetail.getString("discredit_times"))) {
            sb.append("<font style='color:red;'>逾期次数:" + jsonItemDetail.getString("discredit_times") + "</font><br/>");
        }
        //逾期详情
        if (StringUtils.isNotEmpty(jsonItemDetail.getString("overdue_details"))) {
            JSONArray overdue_details = JSONArray.parseArray(jsonItemDetail.getString("overdue_details"));
            JSONObject overdueDetail = null;
            for (Object overdue_detail : overdue_details) {
                overdueDetail = JSON.parseObject(overdue_detail.toString());
                sb.append(overdueDetail.getString("overdue_amount_range") != null ? "<font style='color:red;'>逾期金额:" + overdueDetail.getString("overdue_amount_range") + "</font><br/>" : "" +
                        overdueDetail.getString("overdue_count") != null ? "<font style='color:red;'>逾期笔数:" + overdueDetail.getString("overdue_count") + "</font><br/>" : "" +
                        overdueDetail.getString("overdue_day_range") != null ? "<font style='color:red;'>逾期天数:" + overdueDetail.getString("overdue_day_range") + "</font><br/>" : "" +
                        overdueDetail.getString("overdue_time") != null ? "<font style='color:red;'>逾期入库时间:" + overdueDetail.getString("overdue_time") + "</font><br/>" : ""
                );
            }
        }
        //多头借贷
        if ( StringUtils.isNotEmpty(jsonItemDetail.getString("platform_count"))) {
            sb.append("多头借贷次数:" + jsonItemDetail.getString("platform_count") + "<br/>");
        }
        //借贷详情
        if ( StringUtils.isNotEmpty(jsonItemDetail.getString("platform_detail"))) {
            sb.append("多头借贷平台详情:" + jsonItemDetail.getString("platform_count") + "<br/>");
        }
        //多平台细分维度详情
        if ( StringUtils.isNotEmpty(jsonItemDetail.getString("platform_detail_dimension"))) {
            JSONArray platformDetailDimensions = JSONArray.parseArray(jsonItemDetail.getString("platform_detail_dimension"));
            sb.append("各维度多头详情:<br/>");
            JSONObject pdd = null;
            for (Object platformDetailDimension : platformDetailDimensions) {
                pdd = JSON.parseObject(platformDetailDimension.toString());
                sb.append(pdd.getString("dimension") != null ? "&nbsp;&nbsp;" + pdd.getString("dimension") + ": <br/>" : "");
                sb.append(pdd.getString("count") != null ? "&nbsp;&nbsp;&nbsp;&nbsp;维度命中多头个数:" + pdd.getString("count") + "<br/>" : "");
                sb.append(pdd.getString("detail") != null ? "&nbsp;&nbsp;&nbsp;&nbsp;维度命中多头详情:" + pdd.getString("detail") + "<br/>" : "");
            }
        }
        //	高风险区域
        if (StringUtils.isNotEmpty(jsonItemDetail.getString("high_risk_areas"))) {
            sb.append("<font style='color:red;'>高风险较为集中地区: " + jsonItemDetail.getString("high_risk_areas") + "</font><br/>");
        }
        //列表数据
        if ( StringUtils.isNotEmpty(jsonItemDetail.getString("hit_list_datas"))) {
            sb.append("命中列表:" + jsonItemDetail.getString("hit_list_datas") + "<br/>");
        }
        //频度规则详情
        if ( StringUtils.isNotEmpty(jsonItemDetail.getString("frequency_detail_list"))) {
            JSONArray frequency_detail_list = JSONArray.parseArray(jsonItemDetail.getString("frequency_detail_list"));
            sb.append("频度规则详情: <br/>");
            JSONObject frequencyDetail = null;
            for (Object frequency_detail : frequency_detail_list) {
                frequencyDetail = JSON.parseObject(frequency_detail.toString());
                sb.append(frequencyDetail.getString("detail") != null ? "&nbsp;&nbsp;" + frequencyDetail.getString("detail") + "<br/>" : "");
                sb.append(frequencyDetail.getString("data") != null ? "&nbsp;&nbsp;&nbsp;&nbsp;" + frequencyDetail.getString("data") + "<br/>" : "");
            }
        }
        //命中名单详情列表
        if (StringUtils.isNotEmpty(jsonItemDetail.getString("namelist_hit_details"))) {
            JSONArray namelist_hit_details = JSONArray.parseArray(jsonItemDetail.getString("namelist_hit_details"));
            JSONObject namelistHitDetail = null;
            for (Object namelist_hit_detail : namelist_hit_details) {
                namelistHitDetail = JSON.parseObject(namelist_hit_detail.toString());
                sb.append(namelistHitDetail.getString("description") != null ? "规则描述:" + namelistHitDetail.getString("description") + "<br/>" : "");
                //关注名单
                if ("black_list".equals(namelistHitDetail.getString("type"))) {
                    sb.append(namelistHitDetail.getString("fraud_type") != null ? "&nbsp;&nbsp;<font style='color:red;'>风险类型:</font>" + namelistHitDetail.getString("fraud_type") + "<br/>" : "");
                    sb.append(namelistHitDetail.getString("hit_type_displayname") != null ? "&nbsp;&nbsp;<font style='color:red;'>匹配字段:</font>" + namelistHitDetail.getString("hit_type_displayname") + "<br/>" : "");
                }
                //风险名单
                if ("grey_list".equals(namelistHitDetail.getString("type"))) {
                    sb.append(namelistHitDetail.getString("fraud_type") != null ? "&nbsp;&nbsp;<font style='color:red;'>风险类型:</font>" + namelistHitDetail.getString("fraud_type") + "<br/>" : "");
                    sb.append(namelistHitDetail.getString("hit_type_displayname") != null ? "&nbsp;&nbsp;<font style='color:red;'>匹配字段:</font>" + namelistHitDetail.getString("hit_type_displayname") + "<br/>" : "");
                    JSONArray court_details = JSONArray.parseArray(namelistHitDetail.getString("court_details"));
                    if (court_details != null) {
                        JSONObject courtDetail = null;
                        sb.append("&nbsp;&nbsp;<font style='color:bule;'>法院详情:</font><br/>");
                        for (Object court_detail : court_details) {
                            courtDetail = JSON.parseObject(court_detail.toString());
                            sb.append("&nbsp;&nbsp;[<br/>");
                            sb.append(courtDetail.getString("fraud_type") != null ? "&nbsp;&nbsp;&nbsp;&nbsp;类型:" + courtDetail.getString("fraud_type") + "<br/>" : "");
                            sb.append(courtDetail.getString("name") != null ? "&nbsp;&nbsp;&nbsp;&nbsp;被执行人姓名:" + courtDetail.getString("name") + "<br/>" : "");
                            sb.append(courtDetail.getString("age") != null ? "&nbsp;&nbsp;&nbsp;&nbsp;被执行人年龄:" + courtDetail.getString("age") + "<br/>" : "");
                            sb.append(courtDetail.getString("gender") != null ? "&nbsp;&nbsp;&nbsp;&nbsp;被执行人性别:" + courtDetail.getString("gender") + "<br/>" : "");
                            sb.append(courtDetail.getString("province") != null ? "&nbsp;&nbsp;&nbsp;&nbsp;省份:" + courtDetail.getString("province") + "<br/>" : "");
                            sb.append(courtDetail.getString("court_name") != null ? "&nbsp;&nbsp;&nbsp;&nbsp;执行法院:" + courtDetail.getString("court_name") + "<br/>" : "");
                            sb.append(courtDetail.getString("filing_time") != null ? "&nbsp;&nbsp;&nbsp;&nbsp;立案时间:" + courtDetail.getString("filing_time") + "<br/>" : "");
                            sb.append(courtDetail.getString("execution_department") != null ? "&nbsp;&nbsp;&nbsp;&nbsp;做出执行依据单位:" + courtDetail.getString("execution_department") + "<br/>" : "");
                            sb.append(courtDetail.getString("duty") != null ? "&nbsp;&nbsp;&nbsp;&nbsp;生效法律文书确定的义务:" + courtDetail.getString("duty") + "<br/>" : "");
                            sb.append(courtDetail.getString("situation") != null ? "&nbsp;&nbsp;&nbsp;&nbsp;被执行人的履行情况:" + courtDetail.getString("situation") + "<br/>" : "");
                            sb.append(courtDetail.getString("discredit_detail") != null ? "&nbsp;&nbsp;&nbsp;&nbsp;失信被执行人行为具体情形	:" + courtDetail.getString("discredit_detail") + "<br/>" : "");
                            sb.append(courtDetail.getString("execution_base") != null ? "&nbsp;&nbsp;&nbsp;&nbsp;	执行依据文号:" + courtDetail.getString("execution_base") + "<br/>" : "");
                            sb.append(courtDetail.getString("case_number") != null ? "&nbsp;&nbsp;&nbsp;&nbsp;案号:" + courtDetail.getString("case_number") + "<br/>" : "");
                            sb.append(courtDetail.getString("execution_number") != null ? "&nbsp;&nbsp;&nbsp;&nbsp;	执行标的:" + courtDetail.getString("execution_number") + "<br/>" : "");
                            sb.append(courtDetail.getString("execution_status") != null ? "&nbsp;&nbsp;&nbsp;&nbsp;执行状态:" + courtDetail.getString("execution_status") + "<br/>" : "");
                            sb.append("&nbsp;&nbsp;]<br/>");
                        }
                    }
                }
                //模糊名单
                if ("fuzzy_list".equals(namelistHitDetail.getString("type"))) {
                    JSONArray fuzzy_detail_hits = JSONArray.parseArray(namelistHitDetail.getString("fuzzy_detail_hits"));
                    if (fuzzy_detail_hits != null) {
                        JSONObject fuzzyDetailHit = null;
                        for (Object fuzzy_detail_hit : fuzzy_detail_hits) {
                            fuzzyDetailHit = JSON.parseObject(fuzzy_detail_hit.toString());
                            sb.append(fuzzyDetailHit.getString("fraud_type") != null ? "&nbsp;&nbsp;<font style='color:red;'>风险类型:</font>" + fuzzyDetailHit.getString("fraud_type") + "<br/>" : "");
                            sb.append(fuzzyDetailHit.getString("fuzzy_id_number") != null ? "&nbsp;&nbsp;<font style='color:red;'>模糊身份证:</font>" + fuzzyDetailHit.getString("fuzzy_id_number") + "<br/>" : "");
                            sb.append(fuzzyDetailHit.getString("fuzzy_name") != null ? "&nbsp;&nbsp;<font style='color:red;'>姓名:</font>" + fuzzyDetailHit.getString("fuzzy_name") + "<br/>" : "");
                        }
                    }
                }
            }
        }

        //复杂网络风险详情
        if (StringUtils.isNotEmpty(jsonItemDetail.getString("suspect_team_detail_list"))) {
            JSONArray suspect_team_detail_list = JSONArray.parseArray(jsonItemDetail.getString("suspect_team_detail_list"));
            JSONObject suspectTeamDetail = null;
            sb.append("复杂网络风险详情:<br/>");
            for (Object suspect_team_detail : suspect_team_detail_list) {
                suspectTeamDetail = JSON.parseObject(suspect_team_detail.toString());
                sb.append("&nbsp;&nbsp;[<br/>");
                sb.append(suspectTeamDetail.getString("dim_value") != null ? "&nbsp;&nbsp;&nbsp;&nbsp;匹配字段值:" +
                        suspectTeamDetail.getString("dim_value") + "<br/>" : "");
                sb.append(suspectTeamDetail.getString("group_id") != null ? "&nbsp;&nbsp;&nbsp;&nbsp;风险群体编号:" +
                        suspectTeamDetail.getString("group_id") + "<br/>" : "");
                sb.append(suspectTeamDetail.getString("total_cnt") != null ? "&nbsp;&nbsp;&nbsp;&nbsp;风险群体的节点个数:" +
                        suspectTeamDetail.getString("total_cnt") + "<br/>" : "");
                sb.append(suspectTeamDetail.getString("node_dist") != null ? "&nbsp;&nbsp;&nbsp;&nbsp;风险群体的节点分布:" +
                        suspectTeamDetail.getString("node_dist") + "<br/>" : "");
                sb.append(suspectTeamDetail.getString("black_cnt") != null ? "&nbsp;&nbsp;&nbsp;&nbsp;风险名单个数:" +
                        suspectTeamDetail.getString("black_cnt") + "<br/>" : "");
                sb.append(suspectTeamDetail.getString("black_rat") != null ? "&nbsp;&nbsp;&nbsp;&nbsp;风险名单占比:" +
                        suspectTeamDetail.getString("black_rat") + "<br/>" : "");
                sb.append(suspectTeamDetail.getString("fraud_dist") != null ? "&nbsp;&nbsp;&nbsp;&nbsp;风险名单分布:" +
                        suspectTeamDetail.getString("fraud_dist") + "<br/>" : "");
                sb.append(suspectTeamDetail.getString("grey_cnt") != null ? "&nbsp;&nbsp;&nbsp;&nbsp;关注名单个数:" +
                        suspectTeamDetail.getString("grey_cnt") + "<br/>" : "");
                sb.append(suspectTeamDetail.getString("grey_rat") != null ? "&nbsp;&nbsp;&nbsp;&nbsp;关注名单分布:" +
                        suspectTeamDetail.getString("grey_rat") + "<br/>" : "");
                sb.append(suspectTeamDetail.getString("degree") != null ? "&nbsp;&nbsp;&nbsp;&nbsp;一层关联节点个数:" +
                        suspectTeamDetail.getString("degree") + "<br/>" : "");
                sb.append(suspectTeamDetail.getString("total_cnt_two") != null ? "&nbsp;&nbsp;&nbsp;&nbsp;二层关联节点个数:" +
                        suspectTeamDetail.getString("total_cnt_two") + "<br/>" : "");
                sb.append(suspectTeamDetail.getString("black_cnt_one") != null ? "&nbsp;&nbsp;&nbsp;&nbsp;一层风险名单个数:" +
                        suspectTeamDetail.getString("black_cnt_one") + "<br/>" : "");
                sb.append(suspectTeamDetail.getString("fraud_dist_one") != null ? "&nbsp;&nbsp;&nbsp;&nbsp;一层风险名单分布:" +
                        suspectTeamDetail.getString("fraud_dist_one") + "<br/>" : "");
                sb.append(suspectTeamDetail.getString("black_cnt_two") != null ? "&nbsp;&nbsp;&nbsp;&nbsp;二层风险名单个数:" +
                        suspectTeamDetail.getString("black_cnt_two") + "<br/>" : "");
                sb.append(suspectTeamDetail.getString("fraud_dist_two") != null ? "&nbsp;&nbsp;&nbsp;&nbsp;二层风险名单分布:" +
                        suspectTeamDetail.getString("fraud_dist_two") + "<br/>" : "");
                sb.append(suspectTeamDetail.getString("black_dst") != null ? "&nbsp;&nbsp;&nbsp;&nbsp;风险名单距离:" +
                        suspectTeamDetail.getString("black_dst") + "<br/>" : "");
                sb.append(suspectTeamDetail.getString("core_dst") != null ? "&nbsp;&nbsp;&nbsp;&nbsp;核心节点距离:" +
                        suspectTeamDetail.getString("core_dst") + "<br/>" : "");
                sb.append(suspectTeamDetail.getString("node_score") != null ? "&nbsp;&nbsp;&nbsp;&nbsp;节点风险分:" +
                        suspectTeamDetail.getString("node_score") + "<br/>" : "");
                sb.append(suspectTeamDetail.getString("group_score") != null ? "&nbsp;&nbsp;&nbsp;&nbsp;群体风险分:" +
                        suspectTeamDetail.getString("group_score") + "<br/>" : "");
                sb.append("&nbsp;&nbsp;]<br/>");
            }
        }
        return sb.toString();
    }
}