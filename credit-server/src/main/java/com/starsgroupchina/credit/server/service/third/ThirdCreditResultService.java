package com.starsgroupchina.credit.server.service.third;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.starsgroupchina.common.base.AbstractService;
import com.starsgroupchina.common.objects.If;
import com.starsgroupchina.common.utils.BeanUtil;
import com.starsgroupchina.common.utils.MapUtil;
import com.starsgroupchina.common.utils.Utils;
import com.starsgroupchina.credit.bean.ThirdModel;
import com.starsgroupchina.credit.bean.enums.ScoreType;
import com.starsgroupchina.credit.bean.enums.ThirdCreditSource;
import com.starsgroupchina.credit.bean.enums.ThirdProductType;
import com.starsgroupchina.credit.bean.mapper.ProductMapper;
import com.starsgroupchina.credit.bean.mapper.ProjectMapper;
import com.starsgroupchina.credit.bean.model.*;
import com.starsgroupchina.credit.bean.third.*;
import com.starsgroupchina.credit.server.client.wrapper.DfCreditWrapper;
import com.starsgroupchina.credit.server.client.wrapper.QhzxCreditWrapper;
import com.starsgroupchina.credit.server.client.wrapper.TdCreditWrapper;
import com.starsgroupchina.credit.server.service.RiskModelItem;
import com.starsgroupchina.credit.server.utils.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.*;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

/**
 * @Author: QinHaoHao
 * @Description: 保存第三方调用结果
 * @Date: Created in 15:56 2018/7/2
 * @Modifed By:
 */
@Slf4j
@Service("thirdCreditResultService")
public class ThirdCreditResultService extends AbstractService<ThirdCreditResult, ThirdCreditResultExample> implements RiskModelItem {

    @Autowired
    private ProjectMapper projectMapper;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private DfCreditWrapper dfCreditWrapper;

    @Autowired
    private TdCreditWrapper tdCreditWrapper;

    @Autowired
    private QhzxCreditWrapper qhzxCreditWrapper;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 会检测是否已经过期超时
     *
     * @param projectNo
     * @return
     */
    public List<ThirdCreditQhzx8036> queryQhzx8036Clear(String projectNo) {
        ThirdCreditResultExample example = new ThirdCreditResultExample();
        example.createCriteria().andProjectNoEqualTo(projectNo).andThirdProductTypeEqualTo(ThirdProductType.TYPE_QHZX8036.key()).andSourceEqualTo(ThirdCreditSource.SOURCE_QHZX.key());
        List<ThirdCreditQhzx8036> list = Lists.newArrayList();
        query(example).forEach(thirdCreditResult -> {
            if (isValid(thirdCreditResult)) {
                String result = thirdCreditResult.getResult();
                try {
                    ThirdCreditQhzx8036 thirdCreditQhzx8036 = objectMapper.readValue(result, ThirdCreditQhzx8036.class);
                    list.add(thirdCreditQhzx8036);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                deleteById(thirdCreditResult.getId());
            }
        });
        return list;
    }

    public List<ThirdCreditQhzx8036> queryQhzx8036(String projectNo) {
        ThirdCreditResultExample example = new ThirdCreditResultExample();
        example.createCriteria().andProjectNoEqualTo(projectNo).andThirdProductTypeEqualTo(ThirdProductType.TYPE_QHZX8036.key()).andSourceEqualTo(ThirdCreditSource.SOURCE_QHZX.key());
        List<ThirdCreditQhzx8036> list = Lists.newArrayList();
        query(example).forEach(thirdCreditResult -> {
            String result = thirdCreditResult.getResult();
            try {
                ThirdCreditQhzx8036 thirdCreditQhzx8036 = objectMapper.readValue(result, ThirdCreditQhzx8036.class);
                list.add(thirdCreditQhzx8036);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        return list;
    }

    /**
     * 会检测是否已经过期超时
     *
     * @param projectNo
     * @return
     */
    public DfRespsonseDataExtend queryDFClear(String projectNo) {
        ThirdCreditResultExample example = new ThirdCreditResultExample();
        example.createCriteria().andProjectNoEqualTo(projectNo).andThirdProductTypeEqualTo(ThirdProductType.TYPE_DF.key()).andSourceEqualTo(ThirdCreditSource.SOURCE_DF.key());
        DfRespsonseDataExtend dfRespsonseDataExtend = null;
        ThirdCreditResult thirdCreditResult = Utils.getFirst(query(example));
        if (thirdCreditResult != null) {
            if (isValid(thirdCreditResult)) {
                dfRespsonseDataExtend = setDfRespsonseDataExtend(dfRespsonseDataExtend, thirdCreditResult);
            } else {
                deleteById(thirdCreditResult.getId());
            }
        }
        return dfRespsonseDataExtend;
    }

    private DfRespsonseDataExtend setDfRespsonseDataExtend(DfRespsonseDataExtend dfRespsonseDataExtend, ThirdCreditResult thirdCreditResult) {
        DfRepsonseData dfRepsonseData;
        String result = thirdCreditResult.getResult();
        try {
            dfRepsonseData = objectMapper.readValue(result, DfRepsonseData.class);
            dfRespsonseDataExtend = new DfRespsonseDataExtend();
            dfRespsonseDataExtend.setDfRepsonseData(dfRepsonseData);
            dfRespsonseDataExtend.setCreateTime(thirdCreditResult.getCreateTime());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dfRespsonseDataExtend;
    }

    public DfRespsonseDataExtend queryDF(String projectNo) {
        ThirdCreditResultExample example = new ThirdCreditResultExample();
        example.createCriteria().andProjectNoEqualTo(projectNo).andThirdProductTypeEqualTo(ThirdProductType.TYPE_DF.key()).andSourceEqualTo(ThirdCreditSource.SOURCE_DF.key());
        DfRespsonseDataExtend dfRespsonseDataExtend = null;
        ThirdCreditResult thirdCreditResult = Utils.getFirst(query(example));
        if (thirdCreditResult != null) {
            dfRespsonseDataExtend = setDfRespsonseDataExtend(dfRespsonseDataExtend, thirdCreditResult);
        }
        return dfRespsonseDataExtend;
    }

    /**
     * 会检测是否已经过期超时
     *
     * @param projectNo
     * @return
     */
    public List<ThirdCreditQhzx8107> queryQhzx8107Clear(String projectNo) {
        ThirdCreditResultExample example = new ThirdCreditResultExample();
        example.createCriteria().andProjectNoEqualTo(projectNo).andThirdProductTypeEqualTo(ThirdProductType.TYPE_QHZX8107.key()).andSourceEqualTo(ThirdCreditSource.SOURCE_QHZX.key());
        List<ThirdCreditQhzx8107> list = Lists.newArrayList();
        query(example).forEach(thirdCreditResult -> {
            if (isValid(thirdCreditResult)) {
                String result = thirdCreditResult.getResult();
                try {
                    ThirdCreditQhzx8107 thirdCreditQhzx8107 = objectMapper.readValue(result, ThirdCreditQhzx8107.class);
                    list.add(thirdCreditQhzx8107);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                deleteById(thirdCreditResult.getId());
            }
        });
        return list;
    }

    public List<ThirdCreditQhzx8107> queryQhzx8107(String projectNo) {
        ThirdCreditResultExample example = new ThirdCreditResultExample();
        example.createCriteria().andProjectNoEqualTo(projectNo).andThirdProductTypeEqualTo(ThirdProductType.TYPE_QHZX8107.key()).andSourceEqualTo(ThirdCreditSource.SOURCE_QHZX.key());
        List<ThirdCreditQhzx8107> list = Lists.newArrayList();
        query(example).forEach(thirdCreditResult -> {
            String result = thirdCreditResult.getResult();
            try {
                ThirdCreditQhzx8107 thirdCreditQhzx8107 = objectMapper.readValue(result, ThirdCreditQhzx8107.class);
                list.add(thirdCreditQhzx8107);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        return list;
    }

    /**
     * 会检测是否已经过期超时
     *
     * @param projectNo
     * @return
     */
    public ThirdCreditTdReport queryTDClear(String projectNo) {
        ThirdCreditResultExample example = new ThirdCreditResultExample();
        example.createCriteria().andProjectNoEqualTo(projectNo).andThirdProductTypeEqualTo(ThirdProductType.TYPE_TD.key()).andSourceEqualTo(ThirdCreditSource.SOURCE_TD.key());
        ThirdCreditResult thirdCreditResult = Utils.getFirst(query(example));
        ThirdCreditTdReport thirdCreditTdReport = null;
        if (thirdCreditResult != null) {
            if (isValid(thirdCreditResult)) {
                String result = thirdCreditResult.getResult();
                try {
                    thirdCreditTdReport = objectMapper.readValue(result, ThirdCreditTdReport.class);
                    thirdCreditTdReport.setIsQueryThird(true);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                deleteById(thirdCreditResult.getId());
            }
        }
        return thirdCreditTdReport;
    }

    public ThirdCreditTdReport queryTD(String projectNo) {
        ThirdCreditResultExample example = new ThirdCreditResultExample();
        example.createCriteria().andProjectNoEqualTo(projectNo).andThirdProductTypeEqualTo(ThirdProductType.TYPE_TD.key()).andSourceEqualTo(ThirdCreditSource.SOURCE_TD.key());
        ThirdCreditResult thirdCreditResult = Utils.getFirst(query(example));
        ThirdCreditTdReport thirdCreditTdReport = null;
        if (thirdCreditResult != null) {
            String result = thirdCreditResult.getResult();
            try {
                thirdCreditTdReport = objectMapper.readValue(result, ThirdCreditTdReport.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return thirdCreditTdReport;
    }

    /**
     * 检测当前第三方查询结果是否已经过期，且只有在人工审核点击第三方查询才会计算是否过期
     *
     * @param thirdCreditResult
     * @return
     */
    private boolean isValid(ThirdCreditResult thirdCreditResult) {
        Integer projectId = thirdCreditResult.getProjectId();
        Project project = projectMapper.selectByPrimaryKey(projectId);
        Integer productId = project.getProductId();
        Product product = productMapper.selectByPrimaryKey(productId);
        Integer source = thirdCreditResult.getSource();
        Integer time;
        Date createTime = thirdCreditResult.getCreateTime();
        if (ThirdCreditSource.SOURCE_DF.key().equals(source)) {
            time = product.getDfTime();
        } else if (ThirdCreditSource.SOURCE_TD.key().equals(source)) {
            time = product.getTdTime();
        } else {
            time = product.getQhzxTime();
        }
        Instant instant = createTime.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDateTime localDateTime = instant.atZone(zoneId).toLocalDateTime();
        LocalDateTime endDay = localDateTime.plus(time, ChronoUnit.DAYS);
        LocalDateTime now = LocalDateTime.now();
        Duration between = Duration.between(now, endDay);
        return between.toMinutes() > 0;
    }

    @Override
    public Map<String, String> getRiskModelItem(String projectNo, ScoreType type) {

        ThirdModel thirdModel = new ThirdModel();
        if (type == ScoreType.THIRD_QH) {
            ProjectExample example = new ProjectExample();
            example.createCriteria().andProjectNoEqualTo(projectNo);
            Project project = Utils.getFirst(projectMapper.selectByExample(example));
            Date applyDate = project.getApplyDate();
            ThirdModel.QHModel qhModel = thirdModel.new QHModel();
            ThirdCreditQhzx8107 thirdCreditQhzx8107 = Utils.getFirst(queryQhzx8107(projectNo));
            //获取查询成功的数据
            List<ThirdCreditQhzx8036> thirdCreditQhzx8036s = queryQhzx8036(projectNo).stream().filter(thirdCreditQhzx8036 -> thirdCreditQhzx8036.getRetCode().equals("E000000")).collect(toList());
//            List<ThirdCreditQhzx8036> thirdCreditQhzx8036NoRisk = queryQhzx8036(projectNo).stream().filter(thirdCreditQhzx8036 -> thirdCreditQhzx8036.getRetCode().equals("E000996")).collect(toList());
            if (CollectionUtils.isEmpty(thirdCreditQhzx8036s)&&thirdCreditQhzx8107==null){
                return MapUtil.objToMap(qhModel);
            }
            if (thirdCreditQhzx8107!=null) {
                BeanUtil.copyProperty(thirdCreditQhzx8107, qhModel);
            }
//            if (CollectionUtils.isNotEmpty(thirdCreditQhzx8036NoRisk)){
//                qhModel.setRiskScoreCount(0.0);
//                qhModel.setRiskItemCount(0);
//                qhModel.setRiskItemCount(0);
//                qhModel.setRiskScoreMax(0.0);
//                qhModel.setRiskScoreMaxYear(0);
//                qhModel.setRecentRiskScore(0.0);
//                qhModel.setRecentRiskScoreYear(0);
//                qhModel.setRiskNumThreeYear(0);
//                qhModel.setRiskScoreThreeYear(0.0);
//            }
            if (CollectionUtils.isNotEmpty(thirdCreditQhzx8036s)){
                qhModel.setRiskItemCount(thirdCreditQhzx8036s.size());
                OptionalDouble max = thirdCreditQhzx8036s.stream().mapToDouble(thirdCreditQhzx8036 -> thirdCreditQhzx8036.getRiskScore()).max();
                qhModel.setRiskScoreMax(max.getAsDouble());
                double sum = thirdCreditQhzx8036s.stream().mapToDouble(thirdCreditQhzx8036 -> thirdCreditQhzx8036.getRiskScore()).sum();
                qhModel.setRiskScoreCount(sum);
                ThirdCreditQhzx8036 maxScore = Utils.getFirst(thirdCreditQhzx8036s.stream().filter(thirdCreditQhzx8036 -> thirdCreditQhzx8036.getRiskScore().equals(max.getAsDouble())));
                Date maxBusiDate = maxScore.getBusiDate();
                DateUtil.DayCompare maxCompare = DateUtil.dayCompare(maxBusiDate, applyDate);
                qhModel.setRiskScoreMaxYear(maxCompare.getMonth());
                List<ThirdCreditQhzx8036> sortList = thirdCreditQhzx8036s.stream().sorted((o1, o2) -> o2.getBusiDate().compareTo(o1.getBusiDate())).collect(toList());
                ThirdCreditQhzx8036 recent = sortList.get(0);
                qhModel.setRecentRiskScore(recent.getRiskScore());
                Date recentBusiDate = recent.getBusiDate();
                DateUtil.DayCompare dayCompare = DateUtil.dayCompare(recentBusiDate, applyDate);
                qhModel.setRecentRiskScoreYear(dayCompare.getMonth());
                Date date = new Date(System.currentTimeMillis());
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                calendar.add(Calendar.YEAR, -3);
                List<ThirdCreditQhzx8036> threeYearList = thirdCreditQhzx8036s.stream().filter(thirdCreditQhzx8036 -> thirdCreditQhzx8036.getBusiDate().compareTo(calendar.getTime()) == 1).collect(toList());
                qhModel.setRiskNumThreeYear(threeYearList.size());
                if (CollectionUtils.isNotEmpty(threeYearList)) {
                    OptionalDouble maxThree = threeYearList.stream().mapToDouble(ThirdCreditQhzx8036::getRiskScore).max();
                    qhModel.setRiskScoreThreeYear(maxThree.getAsDouble());
                }
                //add 最近半年，最近一年，最近2年数据
                calendar.setTime(date);
                calendar.add(Calendar.MONTH, -6);
                List<ThirdCreditQhzx8036> halfYearList = thirdCreditQhzx8036s.stream().filter(thirdCreditQhzx8036 -> thirdCreditQhzx8036.getBusiDate().compareTo(calendar.getTime()) == 1).collect(toList());
                qhModel.setRiskNumHalfYear(halfYearList.size());
                if (CollectionUtils.isNotEmpty(halfYearList)) {
                    OptionalDouble maxThree = halfYearList.stream().mapToDouble(ThirdCreditQhzx8036::getRiskScore).max();
                    qhModel.setRiskScoreHalfYear(maxThree.getAsDouble());
                }

                calendar.setTime(date);
                calendar.add(Calendar.YEAR, -1);
                List<ThirdCreditQhzx8036> oneYearList = thirdCreditQhzx8036s.stream().filter(thirdCreditQhzx8036 -> thirdCreditQhzx8036.getBusiDate().compareTo(calendar.getTime()) == 1).collect(toList());
                qhModel.setRiskNumOneYear(oneYearList.size());
                if (CollectionUtils.isNotEmpty(oneYearList)) {
                    OptionalDouble maxThree = oneYearList.stream().mapToDouble(ThirdCreditQhzx8036::getRiskScore).max();
                    qhModel.setRiskScoreOneYear(maxThree.getAsDouble());
                }

                calendar.setTime(date);
                calendar.add(Calendar.YEAR, -2);
                List<ThirdCreditQhzx8036> twoYearList = thirdCreditQhzx8036s.stream().filter(thirdCreditQhzx8036 -> thirdCreditQhzx8036.getBusiDate().compareTo(calendar.getTime()) == 1).collect(toList());
                qhModel.setRiskNumTwoYear(twoYearList.size());
                if (CollectionUtils.isNotEmpty(twoYearList)) {
                    OptionalDouble maxThree = twoYearList.stream().mapToDouble(ThirdCreditQhzx8036::getRiskScore).max();
                    qhModel.setRiskScoreTwoYear(maxThree.getAsDouble());
                }

            }
            log.info(projectNo+"前海风控模型结果:"+ JSON.toJSONString(qhModel));
            return MapUtil.objToMap(qhModel);
        } else {
            ThirdCreditTdReport thirdCreditTdReport = queryTD(projectNo);
            if (thirdCreditTdReport==null){
                return null;
            }
            List<ThirdCreditTdItem> itemList = thirdCreditTdReport.getThirdCreditTdItems();
            if (CollectionUtils.isEmpty(itemList)){
                return null;
            }
            Map<String, List<ThirdCreditTdItem>> map = itemList.stream().collect(groupingBy(ThirdCreditTdItem::getItemGroup));
            List<ThirdCreditTdItem> duoDebit = map.get("多平台借贷申请检测");
            List<ThirdCreditTdItem> riskInfoList = map.get("风险信息扫描");
            if (CollectionUtils.isEmpty(duoDebit)&&CollectionUtils.isEmpty(riskInfoList)){
                return null;
            }
            ThirdModel.TDModel tdModel = thirdModel.new TDModel();
            tdModel.setRiskScore(thirdCreditTdReport.getFinalScore().intValue());
            if (CollectionUtils.isNotEmpty(duoDebit)) {
                Map<String, List<ThirdCreditTdItem>> duoDebitMap = duoDebit.stream().collect(groupingBy(ThirdCreditTdItem::getItemName));
                List<ThirdCreditTdItem> sevenHeadLoanList = duoDebitMap.get("7天内申请人在多个平台申请借款");
                List<ThirdCreditTdItem> oneMonthHeadLoanList = duoDebitMap.get("1个月内申请人在多个平台申请借款");
                List<ThirdCreditTdItem> threeMonthHeadLoanList = duoDebitMap.get("3个月内申请人在多个平台申请借款");
                List<ThirdCreditTdItem> threeMonthHeadLoanNList = duoDebitMap.get("3个月内申请人在多个平台被放款_不包含本合作方");
                if (CollectionUtils.isNotEmpty(sevenHeadLoanList)){
                    String details = sevenHeadLoanList.get(0).getDetails();
                    tdModel.setSevenHeadLoan(getCountDuoDebit(details));
                }
                if (CollectionUtils.isNotEmpty(oneMonthHeadLoanList)){
                    String details = oneMonthHeadLoanList.get(0).getDetails();
                    tdModel.setOneMonthHeadLoan(getCountDuoDebit(details));
                }
                if (CollectionUtils.isNotEmpty(threeMonthHeadLoanList)){
                    String details = threeMonthHeadLoanList.get(0).getDetails();
                    tdModel.setThreeMonthHeadLoan(getCountDuoDebit(details));
                }
                if (CollectionUtils.isNotEmpty(threeMonthHeadLoanNList)){
                    String details = threeMonthHeadLoanNList.get(0).getDetails();
                    tdModel.setThreeMonthHeadLoanN(getCountDuoDebit(details));
                }
            }
            if (CollectionUtils.isNotEmpty(riskInfoList)){
                Map<String, List<ThirdCreditTdItem>> riskInfoMap = riskInfoList.stream().collect(groupingBy(ThirdCreditTdItem::getItemName));
                List<ThirdCreditTdItem> idCardCreditOverdueList = riskInfoMap.get("身份证命中信贷逾期名单");
                List<ThirdCreditTdItem> phoneCreditOverdueList = riskInfoMap.get("手机号命中信贷逾期名单");
                List<ThirdCreditTdItem> idCardCurtDiscreditList = riskInfoMap.get("身份证命中法院失信名单");
                List<ThirdCreditTdItem> idCardCurtExecuteList = riskInfoMap.get("身份证命中法院执行名单");
                List<ThirdCreditTdItem> idCardNameCurtDiscreditList = riskInfoMap.get("身份证_姓名命中法院失信模糊名单");
                List<ThirdCreditTdItem> idCardNameCurtExecuteList = riskInfoMap.get("身份证_姓名命中法院执行模糊名单");
                List<ThirdCreditTdItem> taxCompanyCorporationList = riskInfoMap.get("身份证命中欠税公司法人代表名单");
                if (CollectionUtils.isNotEmpty(idCardCreditOverdueList)){
                    String details = idCardCreditOverdueList.get(0).getDetails();
                    tdModel.setIdCardCreditOverdue(getCountRiskInfo(details));
                }
                if (CollectionUtils.isNotEmpty(phoneCreditOverdueList)){
                    tdModel.setPhoneCreditOverdue(1);
                }
                if (CollectionUtils.isNotEmpty(idCardCurtDiscreditList)){
                    tdModel.setIdCardCurtDiscredit(1);
                }
                if (CollectionUtils.isNotEmpty(idCardCurtExecuteList)){
                    tdModel.setIdCardCurtExecute(1);
                }
                if (CollectionUtils.isNotEmpty(idCardNameCurtDiscreditList)){
                    tdModel.setIdCardNameCurtDiscredit(1);
                }
                if (CollectionUtils.isNotEmpty(idCardNameCurtExecuteList)){
                    tdModel.setIdCardNameCurtExecute(1);
                }
                if (CollectionUtils.isNotEmpty(taxCompanyCorporationList)){
                    tdModel.setTaxCompanyCorporation(1);
                }
            }
            log.info(projectNo+"同盾风控模型结果分数:"+JSON.toJSONString(tdModel));
            return MapUtil.objToMap(tdModel);
        }
    }

    public Integer getCountRiskInfo(String details){
//        int start = details.lastIndexOf("<font style='color:red;'>逾期次数:");
        int start = details.lastIndexOf("<font style='color:red;'>逾期次数:")+"<font style='color:red;'>逾期次数:".length();
        int end = details.indexOf("</font><br/>");
        String count = details.substring(start, end);
        return Integer.valueOf(count);
    }
    public Integer getCountDuoDebit(String details){
        int start = details.lastIndexOf("多头借贷次数:")+"多头借贷次数:".length();
        int end = details.lastIndexOf("<br/>多头借贷平台详情");
        String count = details.substring(start, end);
        return Integer.valueOf(count);
    }
    /**
     * 执行第三方征信查询
     */
    public void thirdCredit(String projectNo, VProject project) {
        ThirdRequestParam thirdRequestParam = new ThirdRequestParam();
        thirdRequestParam.setProjectNo(projectNo);
        thirdRequestParam.setProjectId(project.getId());
        Product product = productMapper.selectByPrimaryKey(project.getProductId());
        //判断该产品是否需要查询该项征信，0表示为需要
        Short isNeed = Short.valueOf((short) 0);
        if (product.getDfReport().equals(isNeed)) {
            DfRespsonseDataExtend dfRespsonseDataExtend = this.queryDF(projectNo);
            If.of(dfRespsonseDataExtend == null).isTrue(() -> dfCreditWrapper.queryReport(thirdRequestParam));
        }
        if (product.getQhzxReport().equals(isNeed)) {
            List<ThirdCreditQhzx8036> qhzx8036s = queryQhzx8036(projectNo);
            List<ThirdCreditQhzx8107> qhzx8107s = queryQhzx8107(projectNo);
            If.of(CollectionUtils.isEmpty(qhzx8036s)).isTrue(() -> qhzxCreditWrapper.MSC8036(thirdRequestParam));
            If.of(CollectionUtils.isEmpty(qhzx8107s)).isTrue(() -> qhzxCreditWrapper.MSC8107(thirdRequestParam));
        }
        if (product.getTdReport().equals(isNeed)) {
            ThirdCreditTdReport tdReport = queryTD(projectNo);
            If.of(tdReport == null).isTrue(() -> tdCreditWrapper.queryAndSaveReport(thirdRequestParam));
        }
    }


    public Integer getProjectId(String projectNo){
        ProjectExample example = new ProjectExample();
        example.createCriteria().andProjectNoEqualTo(projectNo);
        return projectMapper.selectByExample(example).get(0).getId();
    }
}
