package com.starsgroupchina.credit.server.controller.report;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.starsgroupchina.common.context.Context;
import com.starsgroupchina.common.context.ContextHolder;
import com.starsgroupchina.common.file.FileUploadService;
import com.starsgroupchina.common.objects.If;
import com.starsgroupchina.common.response.ListResponse;
import com.starsgroupchina.common.response.SimpleResponse;
import com.starsgroupchina.common.utils.BeanUtil;
import com.starsgroupchina.common.utils.DateUtil;
import com.starsgroupchina.common.utils.Utils;
import com.starsgroupchina.common.utils.export.ReportExcel;
import com.starsgroupchina.credit.bean.AuthMember;
import com.starsgroupchina.credit.bean.enums.AchievementType;
import com.starsgroupchina.credit.bean.enums.ReportFormsStatus;
import com.starsgroupchina.credit.bean.enums.ThirdCreditSource;
import com.starsgroupchina.credit.bean.enums.ThirdProductType;
import com.starsgroupchina.credit.bean.extend.*;
import com.starsgroupchina.credit.bean.model.*;
import com.starsgroupchina.credit.bean.third.ThirdCreditQhzx8036;
import com.starsgroupchina.credit.bean.third.ThirdCreditQhzx8107;
import com.starsgroupchina.credit.bean.third.ThirdCreditTdItem;
import com.starsgroupchina.credit.bean.third.ThirdCreditTdReport;
import com.starsgroupchina.credit.server.service.report.*;
import com.starsgroupchina.credit.server.service.third.ThirdCreditResultService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import static com.starsgroupchina.credit.server.utils.DateUtil.yyyyMMddFormatter;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

/**
 * Created by gexiaoshan on 2018/9/3.
 */
@Slf4j
@RestController
@Api(tags = "CREDIT-SWAGGER44", description = "报表 - ReportFormsController")
@RequestMapping(value = "/report/forms", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class ReportFormsController {

    ObjectMapper objectMapper = new ObjectMapper();
    private ExecutorService fixedThreadPool = Executors.newFixedThreadPool(50);
    @Value("${spring.application.name:}")
    private String appKey;
    @Value("${image-url}")
    private String imageUrl;
    @Autowired
    private FileUploadService fileUploadService;
    @Autowired
    private ReportScheduleService reportScheduleService;
    @Autowired
    private VReportCreditService vReportCreditService;
    @Autowired
    private VReportEntryService vReportEntryService;
    @Autowired
    private VReportAchievementService vReportAchievementService;
    @Autowired
    private VReportAuditService vReportAuditService;
    @Autowired
    private ThirdCreditResultService thirdCreditResultService;
    @Autowired
    private VReportDayAuditService vReportDayAuditService;

    @ApiOperation("报表任务列表")
    @GetMapping
    public ListResponse<ReportSchedule> query(@RequestParam(value = "index", defaultValue = "1") int index,
                                              @RequestParam(value = "limit", defaultValue = "20") int limit) {

        ReportScheduleExample example = new ReportScheduleExample();

        long count = reportScheduleService.count(example);
        example.setOrderByClause("create_time desc");
        example.setOffset((index - 1) * limit);
        example.setLimit(limit);
        List<ReportSchedule> result = reportScheduleService.query(example).collect(toList());

        return ListResponse.success(result, count, index, limit);
    }

    @ApiOperation("进件信息导出")
    @GetMapping("/entry/info")
    public SimpleResponse exportEntryInfo(@RequestParam(value = "begin", defaultValue = "1451630690") Long begin,
                                          @RequestParam(value = "end", defaultValue = "1895538782000") Long end,
                                          @ApiParam("公司名称") @RequestParam(value = "companyName", required = false) String companyName,
                                          @ApiParam("产品名称") @RequestParam(value = "productName", required = false) String productName,
                                          @ApiParam("产品类型") @RequestParam(value = "productType", required = false) String productType
    ) {

        Context context = ContextHolder.getContext();
        ReportSchedule reportSchedule1 = createReportSchedule("进件信息", queryCriteriaAppend1(companyName, productName, productType, begin, end));
        fixedThreadPool.execute(new exportEntry(reportSchedule1.getId(), context, begin, end, companyName, productName, productType));
        return SimpleResponse.success();
    }

    @ApiOperation("大蜂数据导出")
    @GetMapping("/df")
    public SimpleResponse exportDf(@RequestParam(value = "begin", defaultValue = "1451630690") Long begin,
                                   @RequestParam(value = "end", defaultValue = "1895538782000") Long end) {

        Context context = ContextHolder.getContext();
        ReportSchedule reportSchedule1 = createReportSchedule("大峰数据", queryCriteriaAppend2(begin, end));
        fixedThreadPool.execute(new exportDfRunable(reportSchedule1.getId(), context, begin, end));
        return SimpleResponse.success();
    }

    @ApiOperation("日审核表报")
    @GetMapping("/day")
    public ListResponse<VReportDayAudit> queryDayAudit(
            @RequestParam(value = "index", defaultValue = "1") int index,
            @RequestParam(value = "limit", defaultValue = "20") int limit,
            @RequestParam(value = "begin", defaultValue = "1451630690") Long begin,
            @RequestParam(value = "end", defaultValue = "1895538782000") Long end) {

        VReportDayAuditExample example = new VReportDayAuditExample();
        VReportDayAuditExample.Criteria criteria = example.createCriteria();
        Date dateStart = new Date(Instant.ofEpochMilli(begin).toEpochMilli());
        Date dateEnd = new Date(Instant.ofEpochMilli(end).toEpochMilli());
        criteria.andDateBetween(dateStart, dateEnd);
        long count = vReportDayAuditService.count(example);
        example.setOrderByClause("date desc");
        example.setOffset((index - 1) * limit);
        example.setLimit(limit);
        List<VReportDayAudit> result = vReportDayAuditService.query(example).collect(toList());
        return ListResponse.success(result, count, index, limit);
    }


    @ApiOperation("日审核表报导出")
    @GetMapping("/export/day")
    public void exportDayAudit(
            @RequestParam(value = "begin", defaultValue = "1451630690") Long begin,
            @RequestParam(value = "end", defaultValue = "1895538782000") Long end,
            HttpServletResponse response) {

        List<ReportDayAuditExcel> result = Lists.newArrayList();
        VReportDayAuditExample example = new VReportDayAuditExample();
        VReportDayAuditExample.Criteria criteria = example.createCriteria();
        Date dateStart = new Date(Instant.ofEpochMilli(begin).toEpochMilli());
        Date dateEnd = new Date(Instant.ofEpochMilli(end).toEpochMilli());
        criteria.andDateBetween(dateStart, dateEnd);
        example.setOrderByClause("date desc");
        vReportDayAuditService.query(example).forEach(vReportDayAudit -> {
            ReportDayAuditExcel reportDayAuditExcel = new ReportDayAuditExcel(vReportDayAudit);
            String format = yyyyMMddFormatter.get().format(reportDayAuditExcel.getDate());
            reportDayAuditExcel.setDate(format);
            result.add(reportDayAuditExcel);
        });
        ReportDayAuditExcel reportDayAuditExcel = new ReportDayAuditExcel();
        int allEntryNum = result.stream().mapToInt(value -> value.getEntryNum()).sum();
        int allAuditHandleNum = result.stream().mapToInt(value -> value.getAuditHandleNum()).sum();
        int allAuditAttendanceNum = result.stream().mapToInt(value -> value.getAuditAttendanceNum()).sum();
        Double allAuditAverageNum = twoDecimal(allAuditHandleNum, allAuditAttendanceNum);
        Double allAuditTreatmentRate = twoDecimal(allAuditHandleNum, allEntryNum);
        int allRecheckHandleNum = result.stream().mapToInt(value -> value.getRecheckHandleNum()).sum();
        int allRecheckAttendanceNum = result.stream().mapToInt(value -> value.getRecheckAttendanceNum()).sum();
        Double allRecheckAverageNum = twoDecimal(allRecheckHandleNum, allRecheckAttendanceNum);
        Double allRecheckTreatmentRate = twoDecimal(allRecheckHandleNum, allAuditHandleNum);
        int allPending = result.stream().mapToInt(value -> value.getPending()).sum();
        reportDayAuditExcel.setDate("总计");
        reportDayAuditExcel.setEntryNum(allEntryNum);
        reportDayAuditExcel.setAuditHandleNum(allAuditHandleNum);
        reportDayAuditExcel.setAuditAttendanceNum(allAuditAttendanceNum);
        reportDayAuditExcel.setAuditAverageNum(allAuditAverageNum);
        reportDayAuditExcel.setAuditTreatmentRate(allAuditTreatmentRate);
        reportDayAuditExcel.setRecheckAttendanceNum(allRecheckAttendanceNum);
        reportDayAuditExcel.setRecheckAverageNum(allRecheckAverageNum);
        reportDayAuditExcel.setRecheckHandleNum(allRecheckHandleNum);
        reportDayAuditExcel.setRecheckTreatmentRate(allRecheckTreatmentRate);
        reportDayAuditExcel.setPending(allPending);
        result.add(reportDayAuditExcel);
        String fileName = "日审核报表";
        try {
            ReportExcel.excelExport(result,
                    fileName, null, ReportDayAuditExcel.class, 1, response, null);
        } catch (Exception e) {
            log.error("日审核报表报告导出异常：", e);
        }
//        fixedThreadPool.execute(new exportDayAudit(reportSchedule1.getId(), context, begin, end));
//        return SimpleResponse.success();
    }

    @ApiOperation("流程绩效报表")
    @GetMapping("/achievement")
//    @Cacheable(Const.ACHIEVEMENT)
    public ListResponse<VReportAchievementExtend> queryAchievement(
            @ApiParam("类型:0、全部,1、审核,2、复核") @RequestParam(value = "type", required = false) Short type,
            @RequestParam(value = "begin", defaultValue = "1451630690") Long begin,
            @RequestParam(value = "end", defaultValue = "1895538782000") Long end,
            @RequestParam(value = "index", defaultValue = "1") int index,
            @RequestParam(value = "limit", defaultValue = "20") int limit,
            @RequestParam(value = "roleId", required = false) String roleId,
            @RequestParam(value = "memberIds", required = false) List<Integer> memberIds) {

        VReportAchievementExample example = new VReportAchievementExample();
        VReportAchievementExample.Criteria criteria = example.createCriteria();
        If.of(StringUtils.isNotEmpty(roleId)).isTrue(() -> criteria.andRoleIdEqualTo(Integer.valueOf(roleId)));
        If.of(CollectionUtils.isNotEmpty(memberIds)).isTrue(() -> criteria.andIdIn(memberIds));
        Date dateStart = new Date(Instant.ofEpochMilli(begin).toEpochMilli());
        Date dateEnd = new Date(Instant.ofEpochMilli(end).toEpochMilli());
        criteria.andLoginDateBetween(dateStart, dateEnd);
        if (AchievementType.AUDIT.key().equals(type)) {
            criteria.andIsAuditEqualTo(1);
        } else if (AchievementType.RECHECK.key().equals(type)) {
            criteria.andIsAuditRecheckEqualTo(1);
        } else {
            example.setAdditionalWhere(" (is_audit=1 or is_audit_recheck=1)");
        }
        example.setOrderByClause("id ");
        List<VReportAchievement> temp = vReportAchievementService.query(example).collect(toList());
        List<VReportAchievementExtend> result = getvReportAchievementResult(temp, type);
        long count = result.size();
        if (index * limit < count) {
            result = new ArrayList<>(result.subList((index - 1) * limit, index * limit));
        } else {
            result = new ArrayList<>(result.subList((index - 1) * limit, result.size()));
        }

        return ListResponse.success(result, count, index, limit);
    }

    @ApiOperation("流程绩效报表")
    @GetMapping("/export/achievement")
    public void exportAchievement(
            @ApiParam("类型:0、全部,1、审核,2、复核") @RequestParam(value = "type", required = false) Short type,
            @RequestParam(value = "roleId", required = false) String roleId,
            @RequestParam(value = "begin", defaultValue = "1451630690") Long begin,
            @RequestParam(value = "end", defaultValue = "1895538782000") Long end,
            @RequestParam(value = "memberIds", required = false) List<Integer> memberIds,
            HttpServletResponse response) {

        VReportAchievementExample example = new VReportAchievementExample();
        VReportAchievementExample.Criteria criteria = example.createCriteria();
        If.of(StringUtils.isNotEmpty(roleId)).isTrue(() -> criteria.andRoleIdEqualTo(Integer.valueOf(roleId)));
        If.of(CollectionUtils.isNotEmpty(memberIds)).isTrue(() -> criteria.andIdIn(memberIds));
        Date dateStart = new Date(Instant.ofEpochMilli(begin).toEpochMilli());
        Date dateEnd = new Date(Instant.ofEpochMilli(end).toEpochMilli());
        criteria.andLoginDateBetween(dateStart, dateEnd);
        if (AchievementType.AUDIT.key().equals(type)) {
            criteria.andIsAuditEqualTo(1);
        } else if (AchievementType.RECHECK.key().equals(type)) {
            criteria.andIsAuditRecheckEqualTo(1);
        } else {
            example.setAdditionalWhere(" (is_audit=1 or is_audit_recheck=1)");
        }
        List<VReportAchievement> temp = vReportAchievementService.query(example).collect(toList());
        List<VReportAchievementExtend> result = getvReportAchievementResult(temp, type);
        String fileName = "流程绩效报表";
        try {
            ReportExcel.excelExport(result,
                    fileName, null, VReportAchievementExtend.class, 1, response, null);
        } catch (Exception e) {
            log.error("流程绩效报表报告导出异常：", e);
        }
//        fixedThreadPool.execute(new exportAchievement(reportSchedule1.getId(), context, roleId, memberIds, begin, end, type));
    }

    @ApiOperation("前海数据导出")
    @GetMapping("/qhzx")
    public SimpleResponse exportQhzx(@RequestParam(value = "begin", defaultValue = "1451630690") Long begin,
                                     @RequestParam(value = "end", defaultValue = "1895538782000") Long end) {

        Context context = ContextHolder.getContext();
        ReportSchedule reportSchedule1 = createReportSchedule("前海数据", queryCriteriaAppend2(begin, end));
        fixedThreadPool.execute(new exportQhzxRunnable(reportSchedule1.getId(), context, begin, end));
        return SimpleResponse.success();
    }

    @ApiOperation("同盾数据导出")
    @GetMapping("/td")
    public SimpleResponse exportTd(@RequestParam(value = "begin", defaultValue = "1451630690") Long begin,
                                   @RequestParam(value = "end", defaultValue = "1895538782000") Long end) {

        Context context = ContextHolder.getContext();
        ReportSchedule reportSchedule1 = createReportSchedule("同盾数据", queryCriteriaAppend2(begin, end));
        fixedThreadPool.execute(new exportTdRunable(reportSchedule1.getId(), context, begin, end));
        return SimpleResponse.success();
    }


    @ApiOperation("审核信息导出")
    @GetMapping("/audit/info")
    public SimpleResponse exportAuditInfo(@RequestParam(value = "begin", defaultValue = "1451630690") Long begin,
                                          @RequestParam(value = "end", defaultValue = "1895538782000") Long end,
                                          @ApiParam("公司名称") @RequestParam(value = "companyName", required = false) String companyName,
                                          @ApiParam("产品名称") @RequestParam(value = "productName", required = false) String productName,
                                          @ApiParam("产品类型") @RequestParam(value = "productType", required = false) String productType
    ) {

        Context context = ContextHolder.getContext();
        ReportSchedule reportSchedule1 = createReportSchedule("审核信息", queryCriteriaAppend1(companyName, productName, productType, begin, end));
        fixedThreadPool.execute(new exportAudit(reportSchedule1.getId(), context, begin, end, companyName, productName, productType));
        return SimpleResponse.success();
    }

    private List<VReportAchievementExtend> getvReportAchievementResult(List<VReportAchievement> temp, Short type) {
        List<VReportAchievementExtend> result = Lists.newArrayList();
        Map<Integer, List<VReportAchievement>> collect = temp.stream().collect(groupingBy(VReportAchievement::getId));
        final int[] allHandleNumArr = {0};
        final int[] allDayNumArr = {0};
        final int[] allQuaNumArr = {0};
        Double allDayHandle = 0.00;
        Double allErrorRate = 0.00;
        collect.forEach((key, value) -> {
            VReportAchievementExtend vReportAchievementExtend = new VReportAchievementExtend(value.get(0));
            int handleNum = 0;
            int quaNum = 0;
            if (AchievementType.AUDIT.key().equals(type)) {
                handleNum = (int) value.stream().mapToLong(a -> a.getAuditCount()).sum();
                quaNum = (int) value.stream().mapToLong(a -> a.getAuditQuaCount()).sum();
            } else if (AchievementType.RECHECK.key().equals(type)) {
                handleNum = (int) value.stream().mapToLong(a -> a.getAuditRecheckCount()).sum();
                quaNum = (int) value.stream().mapToLong(a -> a.getRecheckQuaCount()).sum();
            } else {
                handleNum = (int) value.stream().mapToLong(a -> (a.getAuditRecheckCount() + a.getAuditCount())).sum();
                quaNum = (int) value.stream().mapToLong(a -> (a.getRecheckQuaCount() + a.getAuditQuaCount())).sum();
            }
            int dayNum = value.size();
            allHandleNumArr[0] += handleNum;
            allDayNumArr[0] += dayNum;
            allQuaNumArr[0] += quaNum;
            Double dayHandle = 0.00;
            Double errorRate = 0.00;
            if (handleNum > 0) {
                if (dayNum > 0) {
                    dayHandle = twoDecimal(handleNum, dayNum);
                }
                if (quaNum > 0) {
                    errorRate = twoDecimal(quaNum, handleNum);
                }
            }
            vReportAchievementExtend.setDayHandle(dayHandle);
            vReportAchievementExtend.setHandleNum(handleNum);
            vReportAchievementExtend.setDayNum(dayNum);
            vReportAchievementExtend.setErrorRate(errorRate);
            result.add(vReportAchievementExtend);
        });
        int sum = result.stream().mapToInt(vReportAchievementExtend -> vReportAchievementExtend.getDayNum()).sum();
        VReportAchievementExtend all = new VReportAchievementExtend();
        int allHandleNum = allHandleNumArr[0];
        int allDayNum = allDayNumArr[0];
        int allQuaNum = allQuaNumArr[0];
        all.setDayNum(allDayNum);
        all.setHandleNum(allHandleNum);
        if (allHandleNum > 0) {
            if (allDayNum > 0) {
                allDayHandle = twoDecimal(allHandleNum, allDayNum);
            }
            if (allQuaNum > 0) {
                allErrorRate = twoDecimal(allQuaNum, allHandleNum);
            }
        }
        all.setName("总计");
        all.setDayHandle(allDayHandle);
        all.setErrorRate(allErrorRate);
        result.add(all);
        return result;
    }


    //进件信息数据导出
    private class exportEntry implements Runnable {
        private Integer id;
        private Context context;
        private Long begin;
        private Long end;
        private String companyName;
        private String productName;
        private String productType;

        private exportEntry(Integer id, Context context, Long begin, Long end, String companyName,
                            String productName, String productType) {
            this.id = id;
            this.context = context;
            this.begin = begin;
            this.end = end;
            this.companyName = companyName;
            this.productName = productName;
            this.productType = productType;
        }

        public void run() {
            ContextHolder.setContext(context);
            ReportSchedule rs = new ReportSchedule();
            rs.setId(id);
            List<VReportEntryExtend> result = Lists.newArrayList();
            Date dateStart = new Date(Instant.ofEpochMilli(begin).toEpochMilli());
            Date dateEnd = new Date(Instant.ofEpochMilli(end).toEpochMilli());
            VReportEntryExample example = new VReportEntryExample();
            VReportEntryExample.Criteria criteria1 = initVReportEntryExample(example, begin, end, companyName, productName);
            If.of(StringUtils.isNotEmpty(productType)).isTrue(() -> {
                if ("无".equals(productType)) {
                    criteria1.andCategoryNameIsNull();
                } else {
                    List categoryList = Arrays.asList(productType.split(","));
                    criteria1.andCategoryNameIn(categoryList);
                    if (categoryList.contains("无")) {
                        VReportEntryExample.Criteria criteria2 = initVReportEntryExample(example, begin, end, companyName, productName);
                        criteria2.andCategoryNameIsNull();
                        example.or(criteria2);
                    }
                }
            });
            Integer rowId = 1;
            try {
                AuthMember authMember = (AuthMember) ContextHolder.getContext().getData();
                List<VReportEntry> list = vReportEntryService.query(example).collect(Collectors.toList());
                if (list != null) {
                    for (VReportEntry v : list) {
                        VReportEntryExtend vReportEntryExtend = new VReportEntryExtend(v);
                        vReportEntryExtend.setRowId(rowId++);
                        if (StringUtils.isNotEmpty(v.getC013())) {
                            Date c013 = com.starsgroupchina.credit.server.utils.DateUtil.parseCurrentDate(v.getC013());
                            vReportEntryExtend.setC013Month(com.starsgroupchina.credit.server.utils.DateUtil.dayCompare(c013, v.getApplyDate()).getMonth() + "");
                        }
                        if (v.getRepayment() == 1) {
                            vReportEntryExtend.setRepaymentName("等本等息");
                        }
                        vReportEntryExtend.setLoanDurationName(v.getLoanDuration() + v.getLoanUnit());

                        //本异地
                        //现家庭地址
                        String liveCityValue = v.getB022();
                        //个人信息-户籍地址
                        String registeredPermanentCity = v.getB010();
                        if (StringUtils.isNotEmpty(liveCityValue) && StringUtils.isNotEmpty(registeredPermanentCity)) {
                            if (compareCity(liveCityValue, registeredPermanentCity)) {
                                vReportEntryExtend.setB037("本地");
                            } else {
                                vReportEntryExtend.setB037("异地");
                            }
                        }
                        //网内外
                        //分公司所在城市
                        registeredPermanentCity = authMember.getOrg().getProvince() + " " + authMember.getOrg().getCity();
                        if (StringUtils.isNotEmpty(liveCityValue) && StringUtils.isNotEmpty(registeredPermanentCity)) {
                            if (compareCity(liveCityValue, registeredPermanentCity)) {
                                vReportEntryExtend.setB038("网内");
                            } else {
                                vReportEntryExtend.setB038("网外");
                            }
                        }
                        //是否知晓贷款
                        if ("0".equals(v.getPa006())) {
                            vReportEntryExtend.setPa006("不知道");
                        } else if ("1".equals(v.getPa006())) {
                            vReportEntryExtend.setPa006("知道");
                        }
                        if ("0".equals(v.getPb006())) {
                            vReportEntryExtend.setPb006("不知道");
                        } else if ("1".equals(v.getPb006())) {
                            vReportEntryExtend.setPb006("知道");
                        }
                        if ("0".equals(v.getPc006())) {
                            vReportEntryExtend.setPc006("不知道");
                        } else if ("1".equals(v.getPc006())) {
                            vReportEntryExtend.setPc006("知道");
                        }
                        if ("0".equals(v.getPd006())) {
                            vReportEntryExtend.setPd006("不知道");
                        } else if ("1".equals(v.getPd006())) {
                            vReportEntryExtend.setPd006("知道");
                        }
                        result.add(vReportEntryExtend);
                    }
                }
                //获得导出文件字节流
                byte[] content = ReportExcel.excelExportToInputStream(result, null, VReportEntryExtend.class, null);

                String fileId = fileUploadService.uploadByByte(content, "进件信息数据提取.xlsx", appKey);
                updateReportSchedule(rs, fileId);
            } catch (Exception e) {
                rs.setStatus(ReportFormsStatus.EXPORT_FAIL.key());
                reportScheduleService.update(rs);
                log.error("进件信息数据导出异常：", e);
            }
        }
    }

    //大蜂数据导出
    private class exportDfRunable implements Runnable {
        private Integer id;
        private Context context;
        private Long begin;
        private Long end;

        private exportDfRunable(Integer id, Context context, Long begin, Long end) {
            this.id = id;
            this.context = context;
            this.begin = begin;
            this.end = end;
        }

        @Override
        public void run() {
            ContextHolder.setContext(context);
            ReportSchedule rs = new ReportSchedule();
            rs.setId(id);
            List<VReportDfExtend> result = Lists.newArrayList();
            Integer rowId = 1;
            try {
                List<VReportCredit> list = queryVReportCredit(ThirdCreditSource.SOURCE_DF.key(), begin, end);
                if (list != null) {
                    for (VReportCredit v : list) {
                        VReportDfExtend vReportDfExtend = new VReportDfExtend(v);
                        vReportDfExtend.setRowId(rowId++);
                        result.add(vReportDfExtend);
                    }
                }
                //获得导出文件字节流
                byte[] content = ReportExcel.excelExportToInputStream(result, null, VReportDfExtend.class, null);

                String fileId = fileUploadService.uploadByByte(content, "大峰数据提取.xlsx", appKey);
                updateReportSchedule(rs, fileId);
            } catch (Exception e) {
                rs.setStatus(ReportFormsStatus.EXPORT_FAIL.key());
                reportScheduleService.update(rs);
                log.error("大蜂数据导出异常：", e);
            }
        }
    }

    //前海数据导出
    private class exportQhzxRunnable implements Runnable {
        private Integer id;
        private Context context;
        private Long begin;
        private Long end;

        private exportQhzxRunnable(Integer id, Context context, Long begin, Long end) {
            this.id = id;
            this.context = context;
            this.begin = begin;
            this.end = end;
        }

        public void run() {
            ContextHolder.setContext(context);
            ReportSchedule rs = new ReportSchedule();
            rs.setId(id);
            List<VReportQhzxExtend> result = Lists.newArrayList();
            final Integer[] rowId = {1};
            try {
                List<VReportCredit> list = queryVReportCredit(ThirdCreditSource.SOURCE_QHZX.key(), begin, end);
                //因一个用户可能会有多条征信数据,根据进件编号分组
                Map<String, List<VReportCredit>> map = list.stream().collect(groupingBy(VReportCredit::getProjectNo));
                if (list != null) {
                    map.forEach((k, v) -> {
                        //前海有两种数据
                        List<VReportCredit> list8036 = v.stream().filter(x -> x.getThirdProductType().equals(ThirdProductType.TYPE_QHZX8036.key())).collect(toList());
                        List<VReportCredit> list8107 = v.stream().filter(x -> x.getThirdProductType().equals(ThirdProductType.TYPE_QHZX8107.key())).collect(toList());
                        VReportQhzxExtend vReportDfExtend = new VReportQhzxExtend();
                        //前海8036数据
                        if (CollectionUtils.isNotEmpty(list8036)) {
                            BeanUtil.copyProperty(list8036.get(0), vReportDfExtend);
                            List<ThirdCreditQhzx8036> list8036Result = Lists.newArrayList();
                            list8036.forEach(x -> {
                                try {
                                    ThirdCreditQhzx8036 thirdCreditQhzx8036 = objectMapper.readValue(x.getResult(), ThirdCreditQhzx8036.class);
                                    list8036Result.add(thirdCreditQhzx8036);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            });
                            List<ThirdCreditQhzx8036> list8036ResultSuccess = list8036Result.stream().filter(x -> x.getRetCode().equals("E000000")).collect(toList());
                            if (CollectionUtils.isNotEmpty(list8036ResultSuccess)) {
                                vReportDfExtend.setRiskItemCount(list8036ResultSuccess.size());
                                OptionalDouble max = list8036ResultSuccess.stream().mapToDouble(x -> x.getRiskScore()).max();
                                vReportDfExtend.setRiskScoreMax(max.getAsDouble());
                                double sum = list8036ResultSuccess.stream().mapToDouble(x -> x.getRiskScore()).sum();
                                vReportDfExtend.setRiskScoreCount(sum);
                                ThirdCreditQhzx8036 maxScore = Utils.getFirst(list8036ResultSuccess.stream().filter(x -> x.getRiskScore().equals(max.getAsDouble())));
                                vReportDfExtend.setRiskScoreMaxDate(maxScore.getBusiDate());
                                List<ThirdCreditQhzx8036> sortList = list8036ResultSuccess.stream().sorted((o1, o2) -> o2.getBusiDate().compareTo(o1.getBusiDate())).collect(toList());
                                ThirdCreditQhzx8036 recent = sortList.get(0);
                                vReportDfExtend.setRecentRiskScore(recent.getRiskScore());
                                vReportDfExtend.setRecentRiskScoreDate(recent.getBusiDate());
                            }
                        }

                        if (CollectionUtils.isNotEmpty(list8107)) {
                            BeanUtil.copyProperty(list8107.get(0), vReportDfExtend);
                            List<ThirdCreditQhzx8107> list8107Result = Lists.newArrayList();
                            list8107.forEach(x -> {
                                try {
                                    ThirdCreditQhzx8107 thirdCreditQhzx8107 = objectMapper.readValue(x.getResult(), ThirdCreditQhzx8107.class);
                                    list8107Result.add(thirdCreditQhzx8107);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            });
                            list8107Result.forEach(x -> {
                                VReportQhzxExtend vr = vReportDfExtend;
                                Integer irc = x.getIsRealCompany();
                                vr.setIsRealCompany(irc == null ? "" : (irc == 0 ? "否" : (irc == 1 ? "是" : (irc == 9 ? "库中无数据" : "不适用"))));
                                vr.setCompanySimDeg(x.getCompanySimDeg());
                                Integer ier = x.getIsExistRel();
                                vr.setIsExistRel(ier == null ? "" : (ier == 0 ? "否" : (ier == 1 ? "是" : (ier == 9 ? "库中无数据" : "不适用"))));
                                Integer rl = x.getRelLevel();
                                vr.setRelLevel(rl == null ? "" : (rl == 0 ? "弱" : (rl == 1 ? "中" : (rl == 2 ? "强" : "其他"))));
                                Integer hcr = x.getHouseChkResult();
                                vr.setHouseChkResult(hcr == null ? "" : (hcr == 1 ? "有房" : "库中无数据"));
                                Integer iom = x.getIsOwnerMobile2();
                                if (iom == null) {
                                } else if (iom == 0) {
                                    vr.setIsOwnerMobile("手机号、证件号、姓名均一致(该手机号在运营商有登记，且证件号和手机都登记正确，无风险)");
                                } else if (iom == 1) {
                                    vr.setIsOwnerMobile("手机号和证件号一致，姓名不一致(该手机号在运营商有登记，且证件号登记正确但姓名不正确，可能为姓名登记错误的情况，请结合身份验证情况进一步判断风险)");
                                } else if (iom == 2) {
                                    vr.setIsOwnerMobile("手机号和证件号一致，姓名不明确(该手机号在运营商有登记，且证件号登记正确但姓名不明确，可能为姓名遗漏登记的情况，请结合身份验证情况进一步判断风险)");
                                } else if (iom == 3) {
                                    vr.setIsOwnerMobile("手机号一致，证件号和姓名不一致(该手机号在运营商有登记，但实名登记情况不正确，存在欺诈风险)");
                                } else if (iom == 9) {
                                    vr.setIsOwnerMobile("库中无数据(该手机号在运营商没有登记或该手机号登记信息暂时无法获取)");
                                } else if (iom == 99) {
                                    vr.setIsOwnerMobile("手机号T-1月前已离网");
                                }
                                Integer oms = x.getOwnerMobileStatus2();
                                vr.setOwnerMobileStatus(oms == null ? "" : (oms == 1 ? "正常" : (oms == 2 ? "停机" : (oms == 3 ? "不可用" : (oms == 4 ? "已销号" : (oms == 5 ? "预销号" : "不明确"))))));
                                Integer uts = x.getUseTimeScore2();
                                if (uts == null) {
                                } else if (uts == -1) {
                                    vr.setUseTimeScore("不可用");
                                } else if (uts == 1) {
                                    vr.setUseTimeScore("小于等于1个月");
                                } else if (uts == 2) {
                                    vr.setUseTimeScore("大于1个月且小于等于2个月");
                                } else if (uts == 3) {
                                    vr.setUseTimeScore("大于2个月且小于等于6个月");
                                } else if (uts == 4) {
                                    vr.setUseTimeScore("大于6个月且小于等于12个月");
                                } else if (uts == 5) {
                                    vr.setUseTimeScore("大于1年且小于等于2年");
                                } else if (uts == 6) {
                                    vr.setUseTimeScore("大于2年且小于等于3年");
                                } else if (uts == 7) {
                                    vr.setUseTimeScore("大于3年");
                                } else if (uts == 30) {
                                    vr.setUseTimeScore("小于等于6个月");
                                } else if (uts == 60) {
                                    vr.setUseTimeScore("大于2年");
                                } else if (uts == 99) {
                                    vr.setUseTimeScore("手机号T-1月前已离网");
                                }
                                vReportDfExtend.setRowId(rowId[0]);
                                result.add(vReportDfExtend);
                                rowId[0] = rowId[0] + 1;
                            });
                        } else {
                            vReportDfExtend.setRowId(rowId[0]);
                            result.add(vReportDfExtend);
                            rowId[0] = rowId[0] + 1;
                        }
                    });
                }
                //获得导出文件字节流
                byte[] content = ReportExcel.excelExportToInputStream(result, null, VReportQhzxExtend.class, null);

                String fileId = fileUploadService.uploadByByte(content, "前海数据提取.xlsx", appKey);
                updateReportSchedule(rs, fileId);
            } catch (Exception e) {
                rs.setStatus(ReportFormsStatus.EXPORT_FAIL.key());
                reportScheduleService.update(rs);
                log.error("前海数据导出异常：", e);
            }
        }
    }

    //审核信息数据导出
    private class exportAudit implements Runnable {
        private Integer id;
        private Context context;
        private Long begin;
        private Long end;
        private String companyName;
        private String productName;
        private String productType;

        private exportAudit(Integer id, Context context, Long begin, Long end, String companyName,
                            String productName, String productType) {
            this.id = id;
            this.context = context;
            this.begin = begin;
            this.end = end;
            this.companyName = companyName;
            this.productName = productName;
            this.productType = productType;
        }

        public void run() {
            ContextHolder.setContext(context);
            ReportSchedule rs = new ReportSchedule();
            rs.setId(id);
            List<VReportAuditExtend> result = Lists.newArrayList();
            VReportAuditExample example = new VReportAuditExample();
            VReportAuditExample.Criteria criteria1 = initVReportAuditExample(example, begin, end, companyName, productName);
            If.of(StringUtils.isNotEmpty(productType)).isTrue(() -> {
                if ("无".equals(productType)) {
                    criteria1.andCategoryNameIsNull();
                } else {
                    List categoryList = Arrays.asList(productType.split(","));
                    criteria1.andCategoryNameIn(categoryList);
                    if (categoryList.contains("无")) {
                        VReportAuditExample.Criteria criteria2 = initVReportAuditExample(example, begin, end, companyName, productName);
                        criteria2.andCategoryNameIsNull();
                        example.or(criteria2);
                    }
                }
            });
            Integer rowId = 1;
            try {
                List<VReportAudit> list = vReportAuditService.queryWithBlob(example);
                if (list != null) {
                    for (VReportAudit v : list) {
                        VReportAuditExtend vReportAuditExtend = new VReportAuditExtend(v);
                        vReportAuditExtend.setRowId(rowId++);
                        if (StringUtils.isNotEmpty(v.getC013())) {
                            Date c013 = com.starsgroupchina.credit.server.utils.DateUtil.parseCurrentDate(v.getC013());
                            vReportAuditExtend.setC013Month(com.starsgroupchina.credit.server.utils.DateUtil.dayCompare(c013, v.getApplyDate()).getMonth() + "");
                        }
                        if (StringUtils.isNotEmpty(v.getD002())) {
                            Date d002 = com.starsgroupchina.credit.server.utils.DateUtil.parseCurrentDate(v.getD002());
                            vReportAuditExtend.setD002Month(com.starsgroupchina.credit.server.utils.DateUtil.dayCompare(d002, v.getApplyDate()).getMonth() + "");
                        }
                        if (StringUtils.isNotEmpty(v.getK004())) {
                            Date k004 = com.starsgroupchina.credit.server.utils.DateUtil.parseCurrentDate(v.getK004());
                            vReportAuditExtend.setK004Month(com.starsgroupchina.credit.server.utils.DateUtil.dayCompare(k004, v.getApplyDate()).getMonth() + "");
                        }

                        setCreditSimple(vReportAuditExtend, v.getCreditSimple());
                        setCreditDetail(vReportAuditExtend, v.getCreditDetail());

                        //
                        String approveResult = v.getApproveresult();
                        If.of(StringUtils.isNotEmpty(approveResult)).isTrue(() -> {
                            vReportAuditExtend.setApproveResult(approveResult.split(",")[0]);
                        });
                        String auditRecheckResult = v.getAuditrecheckresult();
                        If.of(StringUtils.isNotEmpty(auditRecheckResult)).isTrue(() -> {
                            vReportAuditExtend.setAuditRecheckResult(auditRecheckResult.split(",")[0]);
                        });

                        result.add(vReportAuditExtend);
                    }
                }
                //获得导出文件字节流
                byte[] content = ReportExcel.excelExportToInputStream(result, null, VReportAuditExtend.class, null);

                String fileId = fileUploadService.uploadByByte(content, "审核信息数据提取.xlsx", appKey);
                updateReportSchedule(rs, fileId);
            } catch (Exception e) {
                rs.setStatus(ReportFormsStatus.EXPORT_FAIL.key());
                reportScheduleService.update(rs);
                log.error("审核信息数据导出异常：", e);
            }
        }
    }

    //同盾数据导出
    private class exportTdRunable implements Runnable {
        private Integer id;
        private Context context;
        private Long begin;
        private Long end;

        private exportTdRunable(Integer id, Context context, Long begin, Long end) {
            this.id = id;
            this.context = context;
            this.begin = begin;
            this.end = end;
        }

        public void run() {
            ContextHolder.setContext(context);
            ReportSchedule rs = new ReportSchedule();
            rs.setId(id);
            List<VReportTdExtend> result = Lists.newArrayList();
            Integer rowId = 1;
            try {
                List<VReportCredit> list = queryVReportCredit(ThirdCreditSource.SOURCE_TD.key(), begin, end);
                if (list != null) {
                    for (VReportCredit t : list) {
                        VReportTdExtend vReportDfExtend = new VReportTdExtend(t);
                        vReportDfExtend.setRowId(rowId++);
                        setVReportTd(vReportDfExtend, t);
                        result.add(vReportDfExtend);
                    }
                }
                //获得导出文件字节流
                byte[] content = ReportExcel.excelExportToInputStream(result, null, VReportTdExtend.class, null);

                String fileId = fileUploadService.uploadByByte(content, "同盾数据提取.xlsx", appKey);
                updateReportSchedule(rs, fileId);
            } catch (Exception e) {
                rs.setStatus(ReportFormsStatus.EXPORT_FAIL.key());
                reportScheduleService.update(rs);
                log.error("同盾数据导出异常：", e);
            }
        }
    }

    private ReportSchedule createReportSchedule(String name, String queryCriteria) {
        ReportSchedule reportSchedule = new ReportSchedule();
        reportSchedule.setName(name);
        reportSchedule.setRemark(queryCriteria);
        reportSchedule.setStatus(ReportFormsStatus.EXPORT_ING.key());
        return reportScheduleService.create(reportSchedule);
    }

    private void updateReportSchedule(ReportSchedule rs, String fileId) throws Exception {
        If.of(StringUtils.isEmpty(fileId)).isTrueThrow(() -> new Exception());
        rs.setStatus(ReportFormsStatus.EXPORT_SUCCESS.key());
        rs.setUrl(imageUrl + fileId + "/download");
        reportScheduleService.update(rs);
    }

    private List<VReportCredit> queryVReportCredit(Integer source, Long begin, Long end) {
        Date dateStart = new Date(Instant.ofEpochMilli(begin).toEpochMilli());
        Date dateEnd = new Date(Instant.ofEpochMilli(end).toEpochMilli());
        VReportCreditExample example = new VReportCreditExample();
        example.createCriteria().andCreateTimeBetween(dateStart, dateEnd).andSourceEqualTo(source);
        return vReportCreditService.queryWithBlob(example);
    }

    private String queryCriteriaAppend1(String companyName, String productName, String productType, Long begin, Long end) {
        StringBuilder sb = new StringBuilder();
        If.of(StringUtils.isNotEmpty(companyName)).isTrue(() -> {
            sb.append("公司名称:").append(companyName).append(",");
        });
        If.of(StringUtils.isNotEmpty(productName)).isTrue(() -> {
            sb.append("产品名称:").append(productName).append(",");
        });
        If.of(StringUtils.isNotEmpty(productType)).isTrue(() -> {
            sb.append("产品类型:").append(productType).append(",");
        });
        String beginTime = (begin == 1451630690l ? "" : DateUtil.DateToString(new Date(Instant.ofEpochMilli(begin).toEpochMilli())));
        String endTime = (end == 1895538782000l ? "" : DateUtil.DateToString(new Date(Instant.ofEpochMilli(end).toEpochMilli())));
        if (StringUtils.isNotEmpty(beginTime) && StringUtils.isEmpty(endTime))
            sb.append("查询时间:" + beginTime + " -,");
        if (StringUtils.isEmpty(beginTime) && StringUtils.isNotEmpty(endTime))
            sb.append("查询时间: -" + endTime + ",");
        if (StringUtils.isNotEmpty(beginTime) && StringUtils.isNotEmpty(endTime))
            sb.append("查询时间:" + beginTime + " - " + endTime + ",");
        return (StringUtils.isNotEmpty(sb.toString()) ? sb.substring(0, sb.length() - 1) : "");
    }

    private String queryCriteriaAppend2(Long begin, Long end) {
        String beginTime = (begin == 1451630690l ? "" : DateUtil.DateToString(new Date(Instant.ofEpochMilli(begin).toEpochMilli())));
        String endTime = (end == 1895538782000l ? "" : DateUtil.DateToString(new Date(Instant.ofEpochMilli(end).toEpochMilli())));
        if (StringUtils.isNotEmpty(beginTime) && StringUtils.isEmpty(endTime))
            return "查询时间:" + beginTime + " -";
        if (StringUtils.isEmpty(beginTime) && StringUtils.isNotEmpty(endTime))
            return "查询时间: -" + endTime;
        if (StringUtils.isNotEmpty(beginTime) && StringUtils.isNotEmpty(endTime))
            return "查询时间:" + beginTime + " - " + endTime;
        return null;
    }

    //审核信息数据提取报表，解析征信简版
    private void setCreditSimple(VReportAuditExtend vReportEntryExtend, String creditSimple) {
        if (StringUtils.isNotEmpty(creditSimple)) {
            try {
                List<Map> l = objectMapper.readValue(JSON.parseObject(creditSimple).getJSONArray("tlData").toJSONString(), List.class);
                Map<String, Object> c = objectMapper.readValue(JSON.parseObject(creditSimple).getJSONObject("recordsSummary").toJSONString(), Map.class);
                vReportEntryExtend.setG111(c.get("c0"));
                vReportEntryExtend.setG112(c.get("c1"));
                vReportEntryExtend.setG113(c.get("c2"));
                vReportEntryExtend.setG114(c.get("c3"));
                vReportEntryExtend.setG115(c.get("c4"));
                vReportEntryExtend.setG116(c.get("c5"));
                vReportEntryExtend.setG117(c.get("c6"));
                vReportEntryExtend.setG118(c.get("c7"));
                If.of(l != null && l.size() >= 1).isTrue(() -> {
                    Map<String, Object> c1 = l.get(0);
                    vReportEntryExtend.setA111(c1.get("b1"));
                    vReportEntryExtend.setA222(c1.get("b2"));
                    vReportEntryExtend.setA333(c1.get("b3"));
                    vReportEntryExtend.setEeee(c1.get("b4"));
                    vReportEntryExtend.setFfff(c1.get("b5"));
                });
                If.of(l != null && l.size() >= 2).isTrue(() -> {
                    Map<String, Object> c1 = l.get(1);
                    vReportEntryExtend.setB111(c1.get("b1"));
                    vReportEntryExtend.setB222(c1.get("b2"));
                    vReportEntryExtend.setB333(c1.get("b3"));
                });
                If.of(l != null && l.size() >= 3).isTrue(() -> {
                    Map<String, Object> c1 = l.get(2);
                    vReportEntryExtend.setC111(c1.get("b1"));
                    vReportEntryExtend.setC222(c1.get("b2"));
                    vReportEntryExtend.setC333(c1.get("b3"));
                });
                If.of(l != null && l.size() >= 4).isTrue(() -> {
                    Map<String, Object> c1 = l.get(3);
                    vReportEntryExtend.setD111(c1.get("b1"));
                    vReportEntryExtend.setD222(c1.get("b2"));
                    vReportEntryExtend.setD333(c1.get("b3"));
                });
            } catch (Exception e) {
                log.error("审核信息报表，解析征信简版异常：", e);
            }
        }
    }

    //审核信息数据提取报表，解析征信详版
    private void setCreditDetail(VReportAuditExtend vReportEntryExtend, String creditDetail) {
        if (StringUtils.isNotEmpty(creditDetail)) {
            try {
                List<Map> l = objectMapper.readValue(JSON.parseObject(creditDetail).getJSONArray("tlData").toJSONString(), List.class);
                Map<String, Object> c = objectMapper.readValue(JSON.parseObject(creditDetail).getJSONObject("recordsSummary").toJSONString(), Map.class);
                vReportEntryExtend.setGg111(c.get("c0"));
                vReportEntryExtend.setGg112(c.get("c1"));
                vReportEntryExtend.setGg113(c.get("c2"));
                vReportEntryExtend.setGg114(c.get("c3"));
                vReportEntryExtend.setGg115(c.get("c4"));
                vReportEntryExtend.setGg116(c.get("c5"));
                vReportEntryExtend.setGg117(c.get("c6"));
                vReportEntryExtend.setGg118(c.get("c7"));
                vReportEntryExtend.setGg119(c.get("c8"));
                vReportEntryExtend.setGg1110(c.get("c9"));
                If.of(l != null && l.size() >= 1).isTrue(() -> {
                    Map<String, Object> c1 = l.get(0);
                    vReportEntryExtend.setAa111(c1.get("b1"));
                    vReportEntryExtend.setAa222(c1.get("b2"));
                    vReportEntryExtend.setAa333(c1.get("b3"));
                    vReportEntryExtend.setEeee2(c1.get("b4"));
                    vReportEntryExtend.setFfff2(c1.get("b5"));
                });
                If.of(l != null && l.size() >= 2).isTrue(() -> {
                    Map<String, Object> c1 = l.get(1);
                    vReportEntryExtend.setBb111(c1.get("b1"));
                    vReportEntryExtend.setBb222(c1.get("b2"));
                    vReportEntryExtend.setBb333(c1.get("b3"));
                });
                If.of(l != null && l.size() >= 3).isTrue(() -> {
                    Map<String, Object> c1 = l.get(2);
                    vReportEntryExtend.setCc111(c1.get("b1"));
                    vReportEntryExtend.setCc222(c1.get("b2"));
                    vReportEntryExtend.setCc333(c1.get("b3"));
                });
                If.of(l != null && l.size() >= 4).isTrue(() -> {
                    Map<String, Object> c1 = l.get(3);
                    vReportEntryExtend.setDd111(c1.get("b1"));
                    vReportEntryExtend.setDd222(c1.get("b2"));
                    vReportEntryExtend.setDd333(c1.get("b3"));
                });
            } catch (Exception e) {
                log.error("审核信息报表，解析征信详版异常：", e);
            }
        }
    }

    //解析同盾数据
    private void setVReportTd(VReportTdExtend v, VReportCredit t) {
        String result = t.getResult();
        try {
            ThirdCreditTdReport thirdCreditTdReport = objectMapper.readValue(result, ThirdCreditTdReport.class);
            if (thirdCreditTdReport != null) {
                v.setScore(thirdCreditTdReport.getFinalScore());
                List<ThirdCreditTdItem> itemList = thirdCreditTdReport.getThirdCreditTdItems();
                if (itemList != null) {
                    Map<String, List<ThirdCreditTdItem>> map = itemList.stream().collect(groupingBy(ThirdCreditTdItem::getItemGroup));
                    List<ThirdCreditTdItem> duoDebit = map.get("多平台借贷申请检测");
                    List<ThirdCreditTdItem> riskInfoList = map.get("风险信息扫描");
                    if (CollectionUtils.isNotEmpty(duoDebit)) {
                        Map<String, List<ThirdCreditTdItem>> duoDebitMap = duoDebit.stream().collect(groupingBy(ThirdCreditTdItem::getItemName));
                        List<ThirdCreditTdItem> sevenHeadLoanList = duoDebitMap.get("7天内申请人在多个平台申请借款");
                        List<ThirdCreditTdItem> oneMonthHeadLoanList = duoDebitMap.get("1个月内申请人在多个平台申请借款");
                        List<ThirdCreditTdItem> threeMonthHeadLoanList = duoDebitMap.get("3个月内申请人在多个平台申请借款");
                        List<ThirdCreditTdItem> threeMonthHeadLoanNList = duoDebitMap.get("3个月内申请人在多个平台被放款_不包含本合作方");
                        if (CollectionUtils.isNotEmpty(sevenHeadLoanList)) {
                            String details = sevenHeadLoanList.get(0).getDetails();
                            v.setE1(thirdCreditResultService.getCountDuoDebit(details));
                        }
                        if (CollectionUtils.isNotEmpty(oneMonthHeadLoanList)) {
                            String details = oneMonthHeadLoanList.get(0).getDetails();
                            v.setE2(thirdCreditResultService.getCountDuoDebit(details));
                        }
                        if (CollectionUtils.isNotEmpty(threeMonthHeadLoanList)) {
                            String details = threeMonthHeadLoanList.get(0).getDetails();
                            v.setE3(thirdCreditResultService.getCountDuoDebit(details));
                        }
                        if (CollectionUtils.isNotEmpty(threeMonthHeadLoanNList)) {
                            String details = threeMonthHeadLoanNList.get(0).getDetails();
                            v.setE4(thirdCreditResultService.getCountDuoDebit(details));
                        }
                    }
                    if (CollectionUtils.isNotEmpty(riskInfoList)) {
                        Map<String, List<ThirdCreditTdItem>> riskInfoMap = riskInfoList.stream().collect(groupingBy(ThirdCreditTdItem::getItemName));
                        List<ThirdCreditTdItem> d1List = riskInfoMap.get("手机号命中中风险关注名单");
                        List<ThirdCreditTdItem> d2List = riskInfoMap.get("手机号命中低风险关注名单");
                        List<ThirdCreditTdItem> d3List = riskInfoMap.get("身份证命中低风险关注名单");
                        List<ThirdCreditTdItem> d4List = riskInfoMap.get("身份证_姓名命中法院执行模糊名单");
                        List<ThirdCreditTdItem> d5List = riskInfoMap.get("手机号命中高风险关注名单");
                        List<ThirdCreditTdItem> d6List = riskInfoMap.get("身份证命中中风险关注名单");
                        List<ThirdCreditTdItem> d7Lidt = riskInfoMap.get("身份证命中法院结案名单");
                        List<ThirdCreditTdItem> d8Lidt = riskInfoMap.get("身份证命中信贷逾期后还款名单");
                        List<ThirdCreditTdItem> d9List = riskInfoMap.get("身份证命中信贷逾期名单");
                        List<ThirdCreditTdItem> d10List = riskInfoMap.get("身份证_姓名命中法院结案模糊名单");
                        List<ThirdCreditTdItem> d11List = riskInfoMap.get("身份证命中法院执行名单");
                        List<ThirdCreditTdItem> d12List = riskInfoMap.get("身份证命中高风险关注名单");
                        List<ThirdCreditTdItem> d13List = riskInfoMap.get("手机号命中信贷逾期名单");
                        List<ThirdCreditTdItem> d14List = riskInfoMap.get("身份证命中法院失信名单");
                        List<ThirdCreditTdItem> d15List = riskInfoMap.get("身份证_姓名命中法院失信模糊名单");
                        List<ThirdCreditTdItem> d16List = riskInfoMap.get("身份证命中欠税公司法人代表名单");

                        if (CollectionUtils.isNotEmpty(d1List)) {
                            String details = d1List.get(0).getDetails();
                            v.setD1(thirdCreditResultService.getCountRiskInfo(details));
                        }
                        if (CollectionUtils.isNotEmpty(d2List)) {
                            String details = d2List.get(0).getDetails();
                            v.setD2(thirdCreditResultService.getCountRiskInfo(details));
                        }
                        if (CollectionUtils.isNotEmpty(d3List)) {
                            String details = d3List.get(0).getDetails();
                            v.setD3(thirdCreditResultService.getCountRiskInfo(details));
                        }
                        if (CollectionUtils.isNotEmpty(d4List)) {
                            String details = d4List.get(0).getDetails();
                            v.setD4(thirdCreditResultService.getCountRiskInfo(details));
                        }
                        if (CollectionUtils.isNotEmpty(d5List)) {
                            String details = d5List.get(0).getDetails();
                            v.setD5(thirdCreditResultService.getCountRiskInfo(details));
                        }
                        if (CollectionUtils.isNotEmpty(d6List)) {
                            String details = d6List.get(0).getDetails();
                            v.setD6(thirdCreditResultService.getCountRiskInfo(details));
                        }
                        if (CollectionUtils.isNotEmpty(d7Lidt)) {
                            String details = d7Lidt.get(0).getDetails();
                            v.setD7(thirdCreditResultService.getCountRiskInfo(details));
                        }
                        if (CollectionUtils.isNotEmpty(d8Lidt)) {
                            String details = d8Lidt.get(0).getDetails();
                            v.setD6(thirdCreditResultService.getCountRiskInfo(details));
                        }
                        if (CollectionUtils.isNotEmpty(d9List)) {
                            String details = d9List.get(0).getDetails();
                            v.setD6(thirdCreditResultService.getCountRiskInfo(details));
                        }
                        if (CollectionUtils.isNotEmpty(d10List)) {
                            String details = d10List.get(0).getDetails();
                            v.setD6(thirdCreditResultService.getCountRiskInfo(details));
                        }
                        if (CollectionUtils.isNotEmpty(d11List)) {
                            String details = d11List.get(0).getDetails();
                            v.setD6(thirdCreditResultService.getCountRiskInfo(details));
                        }
                        if (CollectionUtils.isNotEmpty(d12List)) {
                            String details = d12List.get(0).getDetails();
                            v.setD6(thirdCreditResultService.getCountRiskInfo(details));
                        }
                        if (CollectionUtils.isNotEmpty(d13List)) {
                            String details = d13List.get(0).getDetails();
                            v.setD6(thirdCreditResultService.getCountRiskInfo(details));
                        }
                        if (CollectionUtils.isNotEmpty(d14List)) {
                            String details = d14List.get(0).getDetails();
                            v.setD6(thirdCreditResultService.getCountRiskInfo(details));
                        }
                        if (CollectionUtils.isNotEmpty(d15List)) {
                            String details = d15List.get(0).getDetails();
                            v.setD6(thirdCreditResultService.getCountRiskInfo(details));
                        }
                        if (CollectionUtils.isNotEmpty(d16List)) {
                            String details = d16List.get(0).getDetails();
                            v.setD6(thirdCreditResultService.getCountRiskInfo(details));
                        }
                    }
                }
            }

        } catch (Exception e) {
            log.error("同盾数据导出,解析数据异常：" + t.getProjectNo(), e);
        }
    }

    //初始化查詢条件
    private VReportEntryExample.Criteria initVReportEntryExample(VReportEntryExample example, Long begin, Long end, String companyName,
                                                                 String productName) {

        VReportEntryExample.Criteria criteria = example.createCriteria();
        Date dateStart = new Date(Instant.ofEpochMilli(begin).toEpochMilli());
        Date dateEnd = new Date(Instant.ofEpochMilli(end).toEpochMilli());
        if (!(begin == 1451630690l && end == 1895538782000l)) {
            criteria.andCreateTimeBetween(dateStart, dateEnd);
        }
        If.of(StringUtils.isNotEmpty(companyName)).isTrue(() -> {
            criteria.andOrgNameIn(Arrays.asList(companyName.split(",")));
        });
        If.of(StringUtils.isNotEmpty(productName)).isTrue(() -> {
            criteria.andTitleIn(Arrays.asList(productName.split(",")));
        });
        return criteria;
    }

    //初始化查詢条件
    private VReportAuditExample.Criteria initVReportAuditExample(VReportAuditExample example, Long begin, Long end, String companyName,
                                                                 String productName) {

        VReportAuditExample.Criteria criteria = example.createCriteria();
        Date dateStart = new Date(Instant.ofEpochMilli(begin).toEpochMilli());
        Date dateEnd = new Date(Instant.ofEpochMilli(end).toEpochMilli());
        if (!(begin == 1451630690l && end == 1895538782000l)) {
            criteria.andAuditOverTimeBetween(dateStart, dateEnd);
        }
        If.of(StringUtils.isNotEmpty(companyName)).isTrue(() -> {
            criteria.andOrgNameIn(Arrays.asList(companyName.split(",")));
        });
        If.of(StringUtils.isNotEmpty(productName)).isTrue(() -> {
            criteria.andTitleIn(Arrays.asList(productName.split(",")));
        });
        return criteria;
    }

    private boolean compareCity(String left, String right) {
        if (org.apache.commons.lang3.StringUtils.isBlank(left) || org.apache.commons.lang3.StringUtils.isBlank(right)) {
            return Boolean.FALSE;
        }
        left = left.trim();
        right = right.trim();
        List<String> lefts = Splitter.on(" ").splitToList(left);
        List<String> rights = Splitter.on(" ").splitToList(right);
        boolean compareResult = Boolean.TRUE;
        int len = Math.min(lefts.size(), rights.size());
        len = len > 2 ? 2 : len;
        for (int i = 0; i < len; i++) {
            boolean tempResult = lefts.get(i).startsWith(rights.get(i));
            if (!tempResult) {
                compareResult = tempResult;
                break;
            }
        }
        return compareResult;
    }

    private Double twoDecimal(int molecule, int denominator) {
        if (molecule == 0 || denominator == 0) {
            return 0.00;
        }
        return new BigDecimal((float) molecule / denominator).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }
}
