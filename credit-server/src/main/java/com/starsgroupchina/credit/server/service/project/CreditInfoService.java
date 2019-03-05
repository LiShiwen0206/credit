package com.starsgroupchina.credit.server.service.project;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.starsgroupchina.common.base.AbstractService;
import com.starsgroupchina.common.utils.MapUtil;
import com.starsgroupchina.common.utils.Utils;
import com.starsgroupchina.credit.bean.CreditInfoModel;
import com.starsgroupchina.credit.bean.enums.ScoreType;
import com.starsgroupchina.credit.bean.mapper.CreditInfoMapper;
import com.starsgroupchina.credit.bean.model.CreditInfo;
import com.starsgroupchina.credit.bean.model.CreditInfoExample;
import com.starsgroupchina.credit.bean.model.Project;
import com.starsgroupchina.credit.bean.model.ProjectExample;
import com.starsgroupchina.credit.server.service.RiskModelItem;
import com.starsgroupchina.credit.server.utils.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhangfeng on 2018-6-14.
 */
@Slf4j
@Service("creditInfoService")
public class CreditInfoService extends AbstractService<CreditInfo, CreditInfoExample> implements RiskModelItem {

    @Autowired
    private CreditInfoMapper creditInfoMapper;

    @Autowired
    private ProjectService projectService;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public CreditInfo update(CreditInfo creditInfo) {
        CreditInfoExample example = new CreditInfoExample();
        example.createCriteria()
                .andProjectNoEqualTo(creditInfo.getProjectNo())
                .andInfoTypeEqualTo(creditInfo.getInfoType());
        creditInfoMapper.updateByExampleSelective(creditInfo, example);
        return creditInfo;
    }

    @Override
    public Map<String, String> getRiskModelItem(String projectNo, ScoreType type) {
        CreditInfoExample creditInfoExample = new CreditInfoExample();
        CreditInfo creditInfo = null;
        ProjectExample example = new ProjectExample();
        example.createCriteria().andProjectNoEqualTo(projectNo);
        Project project = Utils.getFirst(projectService.query(example));
        if (type == ScoreType.CREDIT_SIMPE) {
            creditInfoExample.createCriteria().andProjectNoEqualTo(projectNo).andInfoTypeEqualTo(2);
            creditInfo = Utils.getFirst(this.query(creditInfoExample));
            if (creditInfo == null) {
                return new HashMap<>();
            }
            CreditInfoModel creditInfoSimple = new CreditInfoModel();
            this.setValue(creditInfoSimple, creditInfo, project);
            String infoForm = creditInfo.getInfoForm();
            JSONObject jsonObject = JSON.parseObject(infoForm);
            JSONObject recordsSummaryArray = jsonObject.getJSONObject("recordsSummary");
            //近1个月（信用卡+贷款）查询次数
            int oneMonthSum = castStringToInt(recordsSummaryArray.getString("c0")) + castStringToInt(recordsSummaryArray.getString("c4"));
            //近3个月（信用卡+贷款）查询次数
            int threeMonthSum = castStringToInt(recordsSummaryArray.getString("c1")) + castStringToInt(recordsSummaryArray.getString("c5"));
            //5年内贷记卡逾期
            int fiveCardOverdue = castStringToInt(recordsSummaryArray.getString("c2"));
            //五年内贷记卡91+
            int fiveCard91 = castStringToInt(recordsSummaryArray.getString("c6"));
            //5年内贷款逾期
            int fiveLoanOverdue = castStringToInt(recordsSummaryArray.getString("c3"));
            //5年内贷款91+
            int fiveLoan91 = castStringToInt(recordsSummaryArray.getString("c5"));
            creditInfoSimple.setOneMonthSum(oneMonthSum);
            creditInfoSimple.setThreeMonthSum(threeMonthSum);
            creditInfoSimple.setFiveCardOverdue(fiveCardOverdue);
            creditInfoSimple.setFiveCard91(fiveCard91);
            creditInfoSimple.setFiveLoan91(fiveLoan91);
            creditInfoSimple.setFiveLoanOverdue(fiveLoanOverdue);
            log.info(projectNo + "简版征信模型打分" + JSON.toJSONString(creditInfoSimple));
            return MapUtil.objToMap(creditInfoSimple);
        } else {
            creditInfoExample.createCriteria().andProjectNoEqualTo(projectNo).andInfoTypeEqualTo(1);
            creditInfo = Utils.getFirst(this.query(creditInfoExample));
            if (creditInfo == null) {
                return new HashMap<>();
            }
            CreditInfoModel creditInfoSimple = new CreditInfoModel();
            this.setValue(creditInfoSimple, creditInfo, project);
            log.info(projectNo + "详版版征信模型打分" + JSON.toJSONString(creditInfoSimple));
            return MapUtil.objToMap(creditInfoSimple);
        }
    }

    private void setValue(CreditInfoModel creditInfoSimple, CreditInfo creditInfo, Project project) {
        String infoForm = creditInfo.getInfoForm();
        infoForm.replace("", "0");
        JSONObject jsonObject = JSON.parseObject(infoForm);
        JSONArray hsDataArray = jsonObject.getJSONArray("hsData");
        JSONArray tlDataArray = jsonObject.getJSONArray("tlData");
        JSONObject recordsSummaryArray = jsonObject.getJSONObject("recordsSummary");
        Date applyDate = project.getApplyDate();//进件日
        int creditUseYear = 0;
        int accountSum = 0;
        int loanAccountSum = 0;
        int allAccountSum = 0;
        int overdueSum = 0;
        for (int i = 0; i < hsDataArray.size(); i++) {
            JSONObject hsData = hsDataArray.getJSONObject(i);
            String name = hsData.getString("a0");
            if (StringUtils.isEmpty(name)) {
                continue;
            }
            allAccountSum++;
            if ("逾期中".equals(hsData.getString("a1"))) {
                overdueSum++;
            }
            if ("贷记卡".equals(name) || "准贷记卡".equals(name)) {
                accountSum++;
                Date cardStart = castStringToDate(hsData.getString("a5"));
                DateUtil.DayCompare dayCompare = DateUtil.dayCompare(cardStart, applyDate);
                if (creditUseYear < dayCompare.getMonth() / 12) {
                    creditUseYear = dayCompare.getMonth() / 12;
                }
            }
            if ("房贷".equals(name) || "车贷".equals(name) || "消费贷".equals(name) || "无抵押贷款".equals(name) || "其他贷款".equals(name) || "担保贷款".equals(name)) {
                loanAccountSum++;
            }
        }
        if (allAccountSum > 0) {
            creditInfoSimple.setIsHaveValue("true");
        }else {
            return;
        }
        creditInfoSimple.setCreditUseYear(creditUseYear);
        //贷记卡
        JSONObject debitCard = tlDataArray.getJSONObject(0);
        //准贷记卡
        JSONObject semiDebitCard = tlDataArray.getJSONObject(1);
        //贷款
        JSONObject loan = tlDataArray.getJSONObject(2);
        //总计
        JSONObject sum = tlDataArray.getJSONObject(3);
        //贷记卡总额度
        int totalCredit = castStringToInt(debitCard.getString("b1")) + castStringToInt(semiDebitCard.getString("b1"));
        //贷记卡+准贷记卡的剩余本金
        int residualMoney = castStringToInt(debitCard.getString("b2")) + castStringToInt(semiDebitCard.getString("b2"));
        //贷记卡+准贷记卡的负债月供
        int monthlySupply = castStringToInt(debitCard.getString("b3")) + castStringToInt(semiDebitCard.getString("b3"));
        //贷款的授信总额
        int loanTotalCredit = castStringToInt(loan.getString("b1"));
        //贷款剩余本金
        int loanResidualMoney = castStringToInt(loan.getString("b2"));
        //贷款负责月供
        int loanMonthlySupply = castStringToInt(loan.getString("b3"));
        //总计的授信总额
        int allTotalCredit = totalCredit + loanTotalCredit;
        //总计的剩余本金
        int allResidualMoney = residualMoney + loanResidualMoney;
        //总计的负责月供
        int allMonthlySupply = monthlySupply + loanMonthlySupply;
        //评估收入
        int assessmentIncome = castStringToInt(debitCard.getString("b4"));
        //DSR
        double DSR = castStringToDouble(debitCard.getString("b5"));
        //(贷记卡+准贷记卡的负债月供)/评估收入（贷记卡负债比)
        double monthPercent = monthlySupply;
        double loanMonthPercent = loanMonthlySupply;
        if (assessmentIncome != 0) {
            monthPercent = divideResult(monthlySupply, assessmentIncome);
            //(贷款的负债月供)/评估收入(信贷负债比)
            loanMonthPercent = divideResult(loanMonthlySupply, assessmentIncome);
        }

        //近1个月（信用卡+贷款）查询次数
        int oneMonthSum = castStringToInt(recordsSummaryArray.getString("c0")) + castStringToInt(recordsSummaryArray.getString("c5"));
        //近3个月（信用卡+贷款）查询次数
        int threeMonthSum = castStringToInt(recordsSummaryArray.getString("c1")) + castStringToInt(recordsSummaryArray.getString("c6"));
        creditInfoSimple.setAccountSum(accountSum);
        creditInfoSimple.setAllAccountSum(allAccountSum);
        creditInfoSimple.setAllMonthlySupply(allMonthlySupply);
        creditInfoSimple.setAllResidualMoney(allResidualMoney);
        creditInfoSimple.setDSR(DSR);
        creditInfoSimple.setLoanAccountSum(loanAccountSum);
        creditInfoSimple.setLoanMonthlySupply(loanMonthlySupply);
        creditInfoSimple.setLoanMonthPercent(loanMonthPercent);
        creditInfoSimple.setLoanResidualMoney(loanResidualMoney);
        creditInfoSimple.setLoanTotalCredit(loanTotalCredit);
        creditInfoSimple.setAllTotalCredit(allTotalCredit);
        creditInfoSimple.setAssessmentIncome(assessmentIncome);
        creditInfoSimple.setMonthlySupply(monthlySupply);
        creditInfoSimple.setMonthPercent(monthPercent);
        creditInfoSimple.setOneMonthSum(oneMonthSum);
        creditInfoSimple.setOverdueSum(overdueSum);
        creditInfoSimple.setResidualMoney(residualMoney);
        creditInfoSimple.setThreeMonthSum(threeMonthSum);
        creditInfoSimple.setTotalCredit(totalCredit);
    }

    private int castStringToInt(String s) {
        if (StringUtils.isEmpty(s)) {
            return 0;
        } else {
            return Integer.valueOf(s);
        }
    }

    private double castStringToDouble(String s) {

        if (StringUtils.isEmpty(s)) {
            return 0;
        } else {
            return Double.valueOf(s);
        }
    }

    private double divideResult(int a, int b) {
        BigDecimal x = new BigDecimal(a);
        BigDecimal y = new BigDecimal(b);
        return x.divide(y, 2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    private Date castStringToDate(String d) {
        if (StringUtils.isEmpty(d)) {
            return null;
        }
        try {
//            String[] split = d.split("/");
//            d = split[2] + "-" + split[1] + "-" + split[0];
            Date date = DateUtil.yyyyMMddFormatter.get().parse(d);
            return date;
        } catch (ParseException e) {
            return null;
        }
    }
}
