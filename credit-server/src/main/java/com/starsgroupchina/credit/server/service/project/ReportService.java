package com.starsgroupchina.credit.server.service.project;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.starsgroupchina.common.base.AbstractService;
import com.starsgroupchina.common.utils.Utils;
import com.starsgroupchina.credit.bean.RiskReport;
import com.starsgroupchina.credit.bean.UserInfo;
import com.starsgroupchina.credit.bean.enums.BuildReportSource;
import com.starsgroupchina.credit.bean.enums.ContactType;
import com.starsgroupchina.credit.bean.enums.RiskModelType;
import com.starsgroupchina.credit.bean.enums.RiskStatus;
import com.starsgroupchina.credit.bean.extend.*;
import com.starsgroupchina.credit.bean.model.*;
import com.starsgroupchina.credit.bean.third.*;
import com.starsgroupchina.credit.server.service.CategoryService;
import com.starsgroupchina.credit.server.service.ModelService;
import com.starsgroupchina.credit.server.service.ProductService;
import com.starsgroupchina.credit.server.service.system.OrgService;
import com.starsgroupchina.credit.server.service.third.ThirdCreditResultService;
import com.starsgroupchina.credit.server.service.third.ThirdDataValidService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

/**
 * @Author: QinHaoHao
 * @Description:
 * @Date: Created in 10:45 2018/7/3
 * @Modifed By:
 */
@Slf4j
@Service
public class ReportService extends AbstractService<ProjectReport, ProjectReportExample> {


    @Autowired
    private ProjectService projectService;

    @Autowired
    private FileInspectService fileInspectService;

    @Autowired
    private OrgService.OrgFileService orgFileService;

    @Autowired
    private WebsiteService websiteService;

    @Autowired
    private CreditInfoService creditInfoService;

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ContactService contactService;

    @Autowired
    private RelationService.RelationBlacklistService relationBlacklistService;

    @Autowired
    private RelationService relationService;

    @Autowired
    private ScoreService scoreService;

    @Autowired
    private ProductLoanConfigService productLoanConfigService;

    @Autowired
    private ThirdCreditResultService thirdCreditResultService;

    @Autowired
    private ModelService modelService;

    @Autowired
    private OrgReportConfigService orgReportConfigService;

    @Autowired
    private ThirdDataValidService.ThirdDataValidFailRecordService thirdDataValidFailRecordService;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * @param projectNo
     * @return
     */
    @Transactional
    public RiskReport buildReport(String projectNo, BuildReportSource buildReportSource) {

        log.info("{} 生成风控报告，途径 {}", projectNo, buildReportSource.value());
        /**
         * 获取资料审查
         */
        ProjectFileInspectExample projectFileInspectExample = new ProjectFileInspectExample();
        projectFileInspectExample.createCriteria().andProjectNoEqualTo(projectNo);
        List<FileInspectExtend> files = Lists.newArrayList();
        fileInspectService.query(projectFileInspectExample).forEach(projectFileInspect -> {
            FileInspectExtend fileInspectExtend = new FileInspectExtend(projectFileInspect);
            OrgFile orgFile = orgFileService.getById(projectFileInspect.getOrgFileId());
            OrgFileExtend orgFileExtend = new OrgFileExtend(orgFile);
            fileInspectExtend.setOrgFileExtend(orgFileExtend);
            files.add(fileInspectExtend);
        });
        RiskReport riskReport = new RiskReport();
        ProjectExample projectExample = new ProjectExample();
        projectExample.createCriteria().andProjectNoEqualTo(projectNo);
        Project project = Utils.getFirst(projectService.query(projectExample));
        if (project == null) {
            return null;
        }
        /**
         * 获取报表样式
         */
        OrgReportConfig orgReportConfig = orgReportConfigService.getConfigByOrgId(project.getOrgId());
        riskReport.setOrgReportConfig(orgReportConfig);
        /**
         * 设置产品类型
         */
        Product product = productService.getById(project.getProductId());

        Category category = categoryService.getById(product.getCategoryId());
        ProductExtend productExtend = new ProductExtend(product);
        productExtend.setCategory(category);
        riskReport.setProduct(productExtend);
        /**
         * 个人基本信息
         */
        UserInfo userInfo = userService.getUserInfo(project.getProjectNo());
        riskReport.setCustomerBase(userInfo);
        setCheckData(files, riskReport);
        setWebsiteInfo(riskReport, userInfo, projectNo);
        setCheckAddress(files, riskReport);
        setCheckCredit(riskReport, projectNo);
        double creditScore = setCheckPhone(riskReport, projectNo);
        setSocialSecurity(files, riskReport);
        setAssets(riskReport, files);
        /**
         * 黑名单
         */
        List<RelationBlacklistExtend> blacklistHit = relationBlacklistService.getBlackListHit(userInfo);
        riskReport.setBlackQuery(blacklistHit);
        /**
         * 关联报告核查
         */
        ProjectRelationExample projectRelationExample = new ProjectRelationExample();
        projectRelationExample.createCriteria().andProjectNoEqualTo(projectNo).andIsOkEqualTo(RiskStatus.NO.key().intValue());
        List<ProjectRelation> relationList = relationService.query(projectRelationExample).collect(toList());
        riskReport.setAssociationReport(relationList);

        RiskReport.CreditResult creditResult = new RiskReport.CreditResult();
        if (project.getModelId() != -1) {
            RiskModel model = modelService.getById(project.getModelId());
            creditResult.setBaseModelScore(model.getBaseScore().doubleValue());
        }else {
            creditResult.setBaseModelScore(0.0);
        }
        if (product.getAuditManual() == 1) {
            creditResult.setApproveAmount(project.getApproveAmount().doubleValue());
            creditResult.setApproveLoanDurationDay(project.getApproveLoanDurationDay());
            //有人工审核可以直接模型打分，获取分数
            double score = scoreService.getScore(projectNo);
            creditResult.setModelScore(score);
        }
        if (product.getAuditManualRecheck() == 1) {
            creditResult.setApproveAmount(project.getAuditRecheckAmount().doubleValue());
            creditResult.setApproveLoanDurationDay(project.getAuditRecheckDurationDay());
        }
        creditResult.setLoanDurationDay(project.getLoanDurationDay());
        creditResult.setLoanAmount(project.getLoanAmount().doubleValue());
        //无人工审核，则电核评分项为1
        if (product.getAuditManual() == 1) {
            creditResult.setCreditScore(creditScore);
        } else {
            creditResult.setCreditScore(1.0);
        }
        //判断是否风险客户
        if (buildReportSource == BuildReportSource.RISK_CUSTOMER) {
            creditResult.setCreditScore(0.0);
            riskReport.setIsRiskCustomer(1);
        } else if (buildReportSource == BuildReportSource.BLACK_HIT) {
            creditResult.setCreditScore(0.0);
            riskReport.setIsBlackCustomer(1);
        } else if (buildReportSource == BuildReportSource.THIRD_DATA_VALID_FAIL) {
            creditResult.setCreditScore(0.0);
            riskReport.setIsThirdDataValidFail(1);
        } else {
            riskReport.setIsRiskCustomer(0);
            riskReport.setIsBlackCustomer(0);
            riskReport.setIsThirdDataValidFail(0);
        }
        riskReport.setCreditResult(creditResult);
        ProjectReport report = new ProjectReport();
        report.setProductId(project.getProductId());
        report.setProjectId(project.getId());
        report.setProjectNo(project.getProjectNo());
        try {
            report.setBody(objectMapper.writeValueAsString(riskReport));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        ProjectReportExample projectReportExample = new ProjectReportExample();
        projectReportExample.createCriteria().andProjectNoEqualTo(projectNo);
        deleteByExample(projectReportExample);
        create(report);
        return riskReport;
    }

    /**
     * 资产类相关设置：保单信息核查、房产信息核查、车辆信息核查
     */
    private void setAssets(RiskReport riskReport, List<FileInspectExtend> files) {
        if (CollectionUtils.isNotEmpty(files)) {
            List<RiskReport.RiskItem> checkPolicyList = Lists.newArrayList();
            List<RiskReport.RiskItem> checkHouseList = Lists.newArrayList();
            List<RiskReport.RiskItem> checkCarList = Lists.newArrayList();
            files.stream().filter(projectFileInspectExtend -> "资产证明".equals(projectFileInspectExtend.getOrgFileExtend().getFileType()))
                    .forEach(projectFileInspectExtend -> {
                        OrgFileExtend orgFileExtend = projectFileInspectExtend.getOrgFileExtend();
                        String fileName = orgFileExtend.getFileName();
                        if ("保单".equals(fileName)) {
                            checkPolicyList.add(getDataForInspect(projectFileInspectExtend, "资产证明-保单"));
                        }
                        if ("房产证".equals(fileName)) {
                            checkHouseList.add(getDataForInspect(projectFileInspectExtend, "资产证明-房产证"));
                        }
                        if ("车辆登记证".equals(fileName)) {
                            checkCarList.add(getDataForInspect(projectFileInspectExtend, "资产证明-车辆登记证核查"));
                        }
                        if ("行驶证".equals(fileName)) {
                            checkCarList.add(getDataForInspect(projectFileInspectExtend, "资产证明-行驶证核查"));
                        }
                    });
            riskReport.setCheckPolicy(checkPolicyList);
            riskReport.setCheckHouse(checkHouseList);
            riskReport.setCheckCar(checkCarList);
        }
    }

    /**
     * 社保公积金核查
     */
    private void setSocialSecurity(List<FileInspectExtend> files, RiskReport riskReport) {
        if (CollectionUtils.isNotEmpty(files)) {
            List<RiskReport.RiskItem> socialSecurityList = Lists.newArrayList();
            files.stream().filter(projectFileInspectExtend -> "收入证明".equals(projectFileInspectExtend.getOrgFileExtend().getFileType()))
                    .forEach(projectFileInspectExtend -> {
                        OrgFileExtend orgFileExtend = projectFileInspectExtend.getOrgFileExtend();
                        if ("社保材料".equals(orgFileExtend.getFileName())) {
                            socialSecurityList.add(getDataForInspect(projectFileInspectExtend, "社保核查"));
                        }
                        if ("公积金材料".equals(orgFileExtend.getFileName())) {
                            socialSecurityList.add(getDataForInspect(projectFileInspectExtend, "公积金核查"));
                        }
                    });
            riskReport.setSocialSecurity(socialSecurityList);
        }
    }

    /**
     * 电话核查
     *
     * @param riskReport
     * @param projectNo
     */
    private double setCheckPhone(RiskReport riskReport, String projectNo) {
        ProjectContactExample projectContactExample = new ProjectContactExample();
        projectContactExample.createCriteria().andProjectNoEqualTo(projectNo);
        List<ProjectContact> contactList = contactService.query(projectContactExample).collect(toList());
        List<RiskReport.RiskItem> checkPhoneList = Lists.newArrayList();
        double creditScore = 0;
        if (CollectionUtils.isNotEmpty(contactList)) {
            final int[] contactRealNameCount = {0, 0};
            final int[] contactStatusCount = {0, 0};
            final int[] companyRealNameCount = {0, 0};
            final int[] companyStatusCount = {0, 0};
            //是否有直系亲属或者公司
            final boolean[] isHave = {false, false};
            contactList.forEach(projectContact -> {
                RiskReport.RiskItem riskItem = new RiskReport.RiskItem();
                riskItem.setTestProject("联系人电话");
                riskItem.setRemark(projectContact.getRemark());
                //审核结果（为汉字）
                riskItem.setTestResult1(projectContact.getPhoneStatus());
                String name = projectContact.getName();
                String relation = projectContact.getRelation();
                if ("父亲".equals(relation) || "母亲".equals(relation) || "子女".equals(relation) || "配偶".equals(relation)) {
                    isHave[0] = true;
                    if (projectContact.getIsRealName().equals(RiskStatus.OK.key())) {
                        contactRealNameCount[0]++;
                    }
                    if (!"正常".equals(projectContact.getPhoneStatus())) {
                        contactStatusCount[0]++;
                    }
                }
                if (projectContact.getType().equals(ContactType.COMPANY.key()) && StringUtils.isNotEmpty(projectContact.getCompanyName())) {
                    isHave[1] = true;
                    if (projectContact.getIsRealName().equals(RiskStatus.OK.key())) {
                        companyRealNameCount[0]++;
                    }
                    if (!"正常".equals(projectContact.getPhoneStatus())) {
                        companyStatusCount[0]++;
                    }
                }
                String phone = projectContact.getPhone();
                String testData = (name == null ? "" : name) + ":" + (relation == null ? "" : relation) + ":" + (phone == null ? "" : phone);
                if (testData.length() > 5) {
                    riskItem.setTestData(testData);
                    checkPhoneList.add(riskItem);
                }
            });
            if (isHave[0] == true) {
                if (contactRealNameCount[0] > 0) {
                    contactRealNameCount[1] = 20 * 1;
                } else {
                    contactRealNameCount[1] = (int) (20 * 0.5);
                }
                if (contactStatusCount[0] == 0) {
                    contactStatusCount[1] = 20 * 1;
                }
            } else {
                contactRealNameCount[1] = 20 * 1;
                contactStatusCount[1] = 20 * 1;
            }
            if (isHave[1] == true) {
                if (companyRealNameCount[0] > 0) {
                    companyRealNameCount[1] = 30 * 1;
                } else {
                    companyRealNameCount[1] = (int) (30 * 0.5);
                }
                if (companyStatusCount[0] == 0) {
                    companyStatusCount[1] = 30 * 1;
                }
            } else {
                companyRealNameCount[1] = 30 * 1;
                companyStatusCount[1] = 30 * 1;
            }
            creditScore = contactRealNameCount[1] + contactStatusCount[1] + companyRealNameCount[1] + companyStatusCount[1];
        } else {
            creditScore = 100;
        }
        riskReport.setCheckPhone(checkPhoneList);
        return creditScore / 100;
    }

    /**
     * 征信信息、负债及收入赋值
     *
     * @param riskReport
     * @param projectNo
     */
    private void setCheckCredit(RiskReport riskReport, String projectNo) {

        CreditInfoExample creditInfoExample = new CreditInfoExample();
        creditInfoExample.createCriteria().andProjectNoEqualTo(projectNo);
        List<RiskReport.RiskItem> checkCreditList = Lists.newArrayList();
        List<RiskReport.RiskItem> incomeList = Lists.newArrayList();
        List<CreditInfo> creditInfoList = creditInfoService.query(creditInfoExample).collect(toList());
        if (CollectionUtils.isNotEmpty(creditInfoList)) {
            creditInfoList.forEach(creditInfo -> {
                RiskReport.RiskItem creditInfoItem = new RiskReport.RiskItem();
                RiskReport.RiskItem incomeItem = new RiskReport.RiskItem();
                if (creditInfo.getInfoType().equals(2)) {
                    creditInfoItem.setTestProject("征信信息核查简版");
                    incomeItem.setTestProject("负债及收入简版");
                } else {
                    creditInfoItem.setTestProject("征信信息核查详版");
                    incomeItem.setTestProject("负债及收入详版");
                }
                creditInfoItem.setTestData(creditInfo.getInfoForm());
                incomeItem.setTestData(creditInfo.getInfoForm());
                checkCreditList.add(creditInfoItem);
                incomeList.add(incomeItem);
            });
            riskReport.setCheckCredit(checkCreditList);
            riskReport.setIncome(incomeList);
        }
    }

    private void setCheckAddress(List<FileInspectExtend> files, RiskReport riskReport) {
        /**
         * 地址核查
         */
        if (CollectionUtils.isNotEmpty(files)) {
            List<RiskReport.RiskItem> checkAddressList = Lists.newArrayList();
            files.stream().filter(projectFileInspectExtend -> "住址证明".equals(projectFileInspectExtend.getOrgFileExtend().getFileType()))
                    .filter(projectFileInspectExtend -> "住址使用单据".equals(projectFileInspectExtend.getOrgFileExtend().getFileName()))
                    .forEach(projectFileInspectExtend -> {
                        checkAddressList.add(getDataForInspect(projectFileInspectExtend, "住址使用单据"));
                    });
            riskReport.setCheckAddress(checkAddressList);
        }
    }

    /**
     * 设置申请数据审查
     */
    private void setCheckData(List<FileInspectExtend> files, RiskReport riskReport) {
        if (CollectionUtils.isNotEmpty(files)) {
            List<RiskReport.RiskItem> checkDataList = Lists.newArrayList();
            files.stream().forEach(projectFileInspectExtend -> {
                RiskReport.RiskItem riskItem = new RiskReport.RiskItem();
                riskItem.setTestProject(projectFileInspectExtend.getOrgFileExtend().getFileName());
                riskItem.setTestResult(projectFileInspectExtend.getInvalid().intValue());
                riskItem.setRemark(projectFileInspectExtend.getRemark());
                checkDataList.add(riskItem);
            });
            riskReport.setCheckData(checkDataList);
        }
    }

    /**
     * 设置网查类相关信息
     */
    public void setWebsiteInfo(RiskReport riskReport, UserInfo userInfo, String projectNo) {
        WebsiteAuditExample websiteAuditExample = new WebsiteAuditExample();
        websiteAuditExample.createCriteria().andProjectNoEqualTo(projectNo);
        List<WebsiteAudit> websiteList = websiteService.query(websiteAuditExample).collect(toList());
        if (CollectionUtils.isNotEmpty(websiteList)) {
            /**
             * 网查信息
             */
            Map<String, List<WebsiteAudit>> webMap = websiteList.stream().collect(groupingBy(WebsiteAudit::getWebsite));
            List<RiskReport.RiskItem> courtNetworkList = getCredit(webMap.get("法院网"), userInfo);
            List<RiskReport.RiskItem> discreditNetworkList = getCredit(webMap.get("失信网"), userInfo);
            List<RiskReport.RiskItem> loanAllianceList = getCredit(webMap.get("贷联盟"), userInfo);
            List<RiskReport.RiskItem> baiDuList = getCredit(webMap.get("百度网"), userInfo);
            List<RiskReport.RiskItem> aliPayList = getCredit(webMap.get("支付宝"), userInfo);
            riskReport.setCourtNetwork(courtNetworkList);
            riskReport.setDiscreditNetwork(discreditNetworkList);
            riskReport.setLoanAlliance(loanAllianceList);
            riskReport.setBaiDu(baiDuList);
            riskReport.setAliPay(aliPayList);
            /**
             * 工商信息查询
             */
            List<WebsiteAudit> companyList = websiteList.stream().filter(websiteAudit -> "客公司".equals(websiteAudit.getRelation())).collect(toList());
            List<RiskReport.RiskItem> busInfoQueryList = getBusData(companyList, userInfo);
            riskReport.setBusInfoQuery(busInfoQueryList);
        }
    }

    /**
     * 从网查中拿信息
     *
     * @param websiteAuditList
     * @return
     */
    private List<RiskReport.RiskItem> getCredit(List<WebsiteAudit> websiteAuditList, UserInfo userInfo) {
        List<RiskReport.RiskItem> list = Lists.newArrayList();
        websiteAuditList.forEach(websiteAudit -> {
            RiskReport.RiskItem riskItem = new RiskReport.RiskItem();
            String queryCondition = websiteAudit.getQueryCondition();
            String relation = websiteAudit.getRelation();
            riskItem.setTestProject(relation + ":" + queryCondition);
            if ("客本人".equals(relation)) {
                if ("身份证号".equals(queryCondition) && StringUtils.isNotEmpty(userInfo.getIdCard())) {
                    riskItem.setTestData(userInfo.getIdCard());
                }
                if (queryCondition.contains("姓名") && StringUtils.isNotEmpty(userInfo.getName())) {
                    riskItem.setTestData(userInfo.getName() + "+借贷+纠纷");
                }
                if ("手机号".equals(queryCondition) && StringUtils.isNotEmpty(userInfo.getPhone())) {
                    riskItem.setTestData(userInfo.getPhone());
                }
            }
            if ("客配偶".equals(relation)) {
                if ("身份证号".equals(queryCondition) && StringUtils.isNotEmpty(userInfo.getSpouseIdCard())) {
                    riskItem.setTestData(userInfo.getSpouseIdCard());
                }
                if (queryCondition.contains("姓名") && StringUtils.isNotEmpty(userInfo.getSpouseName())) {
                    riskItem.setTestData(userInfo.getSpouseName() + "+借贷+纠纷");
                }
                if ("手机号".equals(queryCondition) && StringUtils.isNotEmpty(userInfo.getSpouseName())) {
                    riskItem.setTestData(userInfo.getSpousePhone());
                }
            }
            if ("客公司".equals(relation) && StringUtils.isNotEmpty(userInfo.getCompanyName())) {
                riskItem.setTestData(userInfo.getCompanyName());
            }
            if ("联系人".equals(relation)) {
                ProjectContactExample projectContactExample = new ProjectContactExample();
                projectContactExample.createCriteria().andProjectNoEqualTo(userInfo.getProjectNo()).andSourceEqualTo(0);
                StringBuilder tempData = new StringBuilder();
                contactService.query(projectContactExample).forEach(projectContact -> {
                    if (projectContact.getPhone() != null) {
                        tempData.append(projectContact.getPhone() + "/");
                    }
                });
                if (StringUtils.isNotEmpty(tempData.toString())) {
                    riskItem.setTestData(tempData.substring(0, tempData.lastIndexOf("/")));
                }
            }
            riskItem.setTestResult(websiteAudit.getIsOk().intValue());
            riskItem.setRemark(websiteAudit.getRemark());
            list.add(riskItem);
        });
        return list;
    }

    /**
     * 公工商信息查询
     *
     * @param websiteAuditList
     * @return
     */
    private List<RiskReport.RiskItem> getBusData(List<WebsiteAudit> websiteAuditList, UserInfo userInfo) {
        List<RiskReport.RiskItem> list = Lists.newArrayList();
        websiteAuditList.forEach(websiteAudit -> {
            RiskReport.RiskItem riskItem = new RiskReport.RiskItem();
            String queryCondition = websiteAudit.getQueryCondition();
            riskItem.setTestProject(websiteAudit.getWebsite() + ":" + queryCondition);
            riskItem.setTestData(userInfo.getCompanyName());
            riskItem.setTestResult(websiteAudit.getIsOk().intValue());
            riskItem.setRemark(websiteAudit.getRemark());
            list.add(riskItem);
        });
        return list;
    }

    /**
     * 从资料审核中拿信息
     *
     * @param projectFileInspectExtend
     * @param testProject
     * @return
     */
    private RiskReport.RiskItem getDataForInspect(FileInspectExtend projectFileInspectExtend, String testProject) {
        RiskReport.RiskItem riskItem = new RiskReport.RiskItem();
        riskItem.setTestProject(testProject);
        riskItem.setTestData(projectFileInspectExtend.getFileInfo());
        riskItem.setTestResult(projectFileInspectExtend.getInvalid().intValue());
        riskItem.setRemark(projectFileInspectExtend.getRemark());
        return riskItem;
    }

    public ReportExtend getReport(String projectNo) {
        ProjectReportExample reportExample = new ProjectReportExample();
        reportExample.createCriteria().andProjectNoEqualTo(projectNo);
        ProjectReport report = Utils.getFirst(query(reportExample));
        if (report == null) {
            return null;
        }
        Project project = projectService.getProjectByProjectNo(projectNo);
        String body = report.getBody();
        ReportExtend reportExtend = new ReportExtend(report);
        ThirdDataValidFailRecordExample thirdExample = new ThirdDataValidFailRecordExample();
        thirdExample.createCriteria().andProjectNoEqualTo(projectNo);
        List<ThirdDataValidFailRecord> thirdDataValidFailRecords = thirdDataValidFailRecordService.query(thirdExample).collect(toList());
        Map<String, List<ThirdDataValidFailRecord>> thirdMap = thirdDataValidFailRecords.stream().collect(groupingBy(ThirdDataValidFailRecord::getValidType));
        reportExtend.setQhFailList(thirdMap.get("前海数据"));
        reportExtend.setTdFailList(thirdMap.get("同盾数据"));
        try {
            RiskReport riskReport = objectMapper.readValue(body, RiskReport.class);
            RiskReport.CreditResult creditResult = riskReport.getCreditResult();
            reportExtend.setRiskReport(riskReport);
            DfRespsonseDataExtend dfRepsonseData = thirdCreditResultService.queryDF(projectNo);
            ThirdCreditQhzxExtend thirdCreditQhzxExtend = new ThirdCreditQhzxExtend();
            List<ThirdCreditQhzx8036> list8036 = thirdCreditResultService.queryQhzx8036(projectNo);
            List<ThirdCreditQhzx8107> list8107 = thirdCreditResultService.queryQhzx8107(projectNo);
            thirdCreditQhzxExtend.setThirdCreditQhzx8036(list8036);
            thirdCreditQhzxExtend.setThirdCreditQhzx8107(list8107);
            ThirdCreditTdReport thirdCreditTdReport = thirdCreditResultService.queryTD(projectNo);
            reportExtend.setDfData(dfRepsonseData);
            reportExtend.setQhData(thirdCreditQhzxExtend);
            reportExtend.setTdData(thirdCreditTdReport);
            if (checkReport(riskReport)) {
                creditResult.setCreditScore(0.0);
                creditResult.setApproveLoanDurationDay(0);
                creditResult.setApproveAmount(0.0);
            } else {
                if (creditResult.getModelScore() == null) {
                    double score = scoreService.getScore(projectNo);
                    creditResult.setModelScore(score);
                }
                RiskModel riskModel = modelService.getById(project.getModelId());
                if (riskModel!=null&&riskModel.getType().equals(RiskModelType.DECISION_TREE.key()) && creditResult.getModelScore() == 0) {
                    creditResult.setCreditScore(-1.0);
                    riskReport.getCreditResult().setApproveLoanDurationDay(0);
                    riskReport.getCreditResult().setApproveAmount(0.0);
                } else {
                    double scoreResult = this.handlePoint((creditResult.getModelScore() + creditResult.getBaseModelScore()) * creditResult.getCreditScore());
                    creditResult.setCreditScore(scoreResult);
                    Product product = productService.getById(project.getProductId());
                    if (product.getAdviseAmount() != null && product.getAdviseAmount() != 0) {
                        double adviseAmount = scoreResult * product.getAdviseAmount();
                        ProductLoanConfigExample example = new ProductLoanConfigExample();
                        example.createCriteria().andProductIdEqualTo(product.getId());
                        productLoanConfigService.query(example).forEach(productLoanConfig -> {
                            if ((adviseAmount - productLoanConfig.getLoanAmountBegin().doubleValue() >= 0) && (productLoanConfig.getLoanAmountEnd().doubleValue() - adviseAmount >= 0)) {
                                riskReport.getCreditResult().setApproveLoanDurationDay(productLoanConfig.getLoanDuration() * 30);
                                riskReport.getCreditResult().setApproveAmount(adviseAmount);
                            }
                        });
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return reportExtend;
    }


    private boolean checkReport(RiskReport riskReport) {
        if (riskReport.getIsThirdDataValidFail() != null && riskReport.getIsThirdDataValidFail() == 1) {
            return true;
        }
        if (riskReport.getIsBlackCustomer() != null && riskReport.getIsBlackCustomer() == 1) {
            return true;
        }
        if (riskReport.getIsRiskCustomer() != null && riskReport.getIsRiskCustomer() == 1) {
            return true;
        }
        return false;
    }

    public double handlePoint(double d) {
        BigDecimal b = new BigDecimal(d);
        return b.setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
    }
}
