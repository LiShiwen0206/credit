package credit.server.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.util.concurrent.ShutdownEnabledTimer;
import com.starsgroupchina.common.utils.BeanUtil;
import com.starsgroupchina.common.utils.MapUtil;
import com.starsgroupchina.credit.bean.CreditInfoModel;
import com.starsgroupchina.credit.bean.ThirdModel;
import com.starsgroupchina.credit.bean.model.FormDetailUser;
import com.starsgroupchina.credit.bean.model.FormDetailUserHistory;
import com.starsgroupchina.credit.server.utils.DateUtil;
import lombok.Data;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Map;

/**
 * @Author: QinHaoHao
 * @Description:
 * @Date: Created in 14:17 2018/7/9
 * @Modifed By:
 */
public class OtherTest {

private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void testString(){
//        String a = "55&&";
//        System.out.println(a.substring(0,a.lastIndexOf("&&")));
//        String a="ABCSlove23next234csdn3423javaeye";
//        String regEx="[^A-z]";
//        Pattern p = Pattern.compile(regEx);
//        Matcher m = p.matcher(a);
//        System.out.println(m.replaceAll("").trim());
//        Org org = new Org();
//        if(org.getHeadOrgId()<0){
//            System.out.println(org);
//        }
//        Long aLong = Long.valueOf("2018082900011111111");
//        System.out.println(aLong);
//        OptionalDouble d = OptionalDouble.of(3);
//        System.out.println(d.orElse(1));
//        System.out.println(ContentType.TEXT_XML.toString());
//
//        Short m = 1;
//        System.out.println(m==1);
//        String format = String.format(" DATE_FORMAT(p.create_time, '%%Y-%%m-%%d')=%s and create_user_id=%s", "2018-09-13", '2');
//        System.out.println(format);
//        Double a = 0.0;
//
//        List<String> s = Lists.newArrayList();
//        s.add("dasa");
//        s.add("bbbb");
//        try {
//            String s1 = objectMapper.writeValueAsString(s);
//            System.out.println("以下条件不再满足："+s1);
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        }
//        System.out.println(a==0);
//        LocalDate localDate = LocalDateTime.parse("2017-04-03 18:17:53", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")).toLocalDate();
//        LocalDate localDate2 = LocalDateTime.parse("2018-08-03 18:17:53", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")).toLocalDate();
//        float v = DateUtil.betweenMonth(localDate, localDate2);
//        System.out.println(v);

        System.out.println("1".equals("1"));
    }
    @Test
    public void testMonth(){
        LocalDate s=LocalDate.of(2017,8,29);
        LocalDate now=LocalDate.of(2018,8,10);
        float v = DateUtil.betweenMonth(s, now);
        Period be= Period.between(s,now);
        System.out.println(be.getYears());
        System.out.println(be.getMonths());
        System.out.println(be.getDays());
    }

    @Test
    public void testKK(){

        ThirdModel thirdModel = new ThirdModel();
        ThirdModel.QHModel qhModel = thirdModel.new QHModel();
        qhModel.setHouseChkResult(10);
        qhModel.setIsExistRel(0);
        qhModel.setIsOwnerMobile2(10);
        qhModel.setIsRealCompany(10);
        qhModel.setOwnerMobileStatus2(10);
        qhModel.setRecentRiskScore(0.0);
        qhModel.setRecentRiskScoreYear(0);
        qhModel.setRiskItemCount(0);
        qhModel.setRiskNumThreeYear(0);
        qhModel.setRiskScoreCount(0.0);
        qhModel.setRiskScoreMax(0.0);
        qhModel.setRiskScoreMaxYear(0);
        qhModel.setRiskScoreThreeYear(0.0);
        qhModel.setUseTimeScore2(10);
        CreditInfoModel creditInfoModel = new CreditInfoModel();
        Map map = MapUtil.objToMap(creditInfoModel);
        System.out.println(map.get("isHaveValue")!=null);
        Object isHaveValue = map.get("isHaveValue");
        System.out.println(Boolean.valueOf("true"));

    }

    @Test
    public void testAdd(){
//        List<Good> goodList = Lists.newArrayList();
//        goodList.add(new Good(10.0));
//        goodList.add(new Good(20.0));
//        goodList.add(new Good(-10.0));
//        double sum = goodList.stream().mapToDouble(Good::getPrice).sum();
//        System.out.println(sum);
        int i=1;
        int a= 3;
        Double c = Double.valueOf(i/a);
        Double d = Double.valueOf(1/3);
        BigDecimal x = new BigDecimal("1");//10 或者13 结果精度是相同的
        BigDecimal y = new BigDecimal("3");
        System.out.println(x.divide(y,2,BigDecimal.ROUND_HALF_UP).doubleValue());
        System.out.println(i/a);
    }

    @Data
    public static class Good{
        private double price;
        public Good(double price){
            this.price = price;
        }

    }

    @Test
    public void testDate() throws ParseException {

        DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date myDate1 = dateFormat1.parse("2009-06-01 10:08:32");
        System.out.println(myDate1);


//获得2010年9月13日22点36分01秒 的Date对象
        DateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date myDate2 = dateFormat2.parse("2010-09-13 10:08:32");
        System.out.println(myDate2);
        System.out.println(DateUtil.dayCompare(myDate2,myDate1));

    }

    @Test
    public void test() {

        FormDetailUser formDetailUser = new FormDetailUser();
        FormDetailUser formDetailUserOld = new FormDetailUser();
        formDetailUser.setB001("bool");
        formDetailUser.setB002("boo2");
        formDetailUserOld.setB001("bool");
        formDetailUserOld.setB002("boo3");
        formDetailUser.setB003("boo3");
        set(formDetailUserOld, formDetailUser);
        FormDetailUserHistory formDetailUserHistory = new FormDetailUserHistory();
        BeanUtil.copyProperty(formDetailUserOld, formDetailUserHistory);
        String b001 = formDetailUserHistory.getB001();
        String b002 = formDetailUserHistory.getB002();
        String b003 = formDetailUserHistory.getB003();
        System.out.println(b001 + b002 + b003);
    }

    public void set(Object oldObject, Object newObject) {
        Class<?> clazz = oldObject.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field f : fields) {
            PropertyDescriptor pd = null;
            try {
                String name = f.getName();
                if (name.contains("0")) {
                    pd = new PropertyDescriptor(f.getName(), clazz);
                    Method setMethod = pd.getWriteMethod();//获得写方法
                    Method getMethod = pd.getReadMethod();
                    getValue(getMethod, setMethod, oldObject, newObject);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void getValue(Method getMethod, Method setMethod, Object oldObject, Object newObject) throws Exception {
        Object oldValue = getMethod.invoke(oldObject);
        Object newValue = getMethod.invoke(newObject);
        if (oldValue == null && newValue == null) {
            return;
        }
        if (oldValue == null && newValue != null) {
            setMethod.invoke(oldObject, "未填写->" + newValue);
            return;
        }
        if (oldValue != null && newValue == null) {
            setMethod.invoke(oldObject, oldValue + "->删除原值");
            return;
        }
        if (oldValue.equals(newValue)) {
            setMethod.invoke(oldObject, "");
        } else {
            setMethod.invoke(oldObject, oldValue + "->" + newValue);
        }
    }


    @Test
    public void testParse() {
        String infoForm = "{\n" +
                "\t\"hsData\": [{\n" +
                "\t\t\"a0\": \"贷记卡\",\n" +
                "\t\t\"a1\": \"正常\",\n" +
                "\t\t\"a2\": \"工商银行\",\n" +
                "\t\t\"a3\": \"40000\",\n" +
                "\t\t\"a4\": \"4000\",\n" +
                "\t\t\"a5\": \"20\",\n" +
                "\t\t\"a6\": \"\",\n" +
                "\t\t\"a7\": \"14\",\n" +
                "\t\t\"a8\": \"1000\",\n" +
                "\t\t\"a9\": \"0\",\n" +
                "\t\t\"a10\": \"\",\n" +
                "\t\t\"a11\": \"\",\n" +
                "\t\t\"a12\": \"\",\n" +
                "\t\t\"a13\": \"\",\n" +
                "\t\t\"a14\": \"\",\n" +
                "\t\t\"a15\": \"\"\n" +
                "\t}, {\n" +
                "\t\t\"a0\": \"无抵押贷款\",\n" +
                "\t\t\"a1\": \"逾期中\",\n" +
                "\t\t\"a2\": \"招商银行\",\n" +
                "\t\t\"a3\": \"10000\",\n" +
                "\t\t\"a4\": \"1000\",\n" +
                "\t\t\"a5\": \"10\",\n" +
                "\t\t\"a6\": null,\n" +
                "\t\t\"a7\": \"5\",\n" +
                "\t\t\"a8\": \"500\",\n" +
                "\t\t\"a9\": \"100\",\n" +
                "\t\t\"a10\": \"34\",\n" +
                "\t\t\"a11\": \"40\",\n" +
                "\t\t\"a12\": \"79\",\n" +
                "\t\t\"a13\": \"89\",\n" +
                "\t\t\"a14\": \"89\",\n" +
                "\t\t\"a15\": \"89\"\n" +
                "\t}, {\n" +
                "\t\t\"a0\": \"贷记卡\",\n" +
                "\t\t\"a1\": null,\n" +
                "\t\t\"a2\": null,\n" +
                "\t\t\"a3\": null,\n" +
                "\t\t\"a4\": null,\n" +
                "\t\t\"a5\": null,\n" +
                "\t\t\"a6\": null,\n" +
                "\t\t\"a7\": null,\n" +
                "\t\t\"a8\": null,\n" +
                "\t\t\"a9\": null,\n" +
                "\t\t\"a10\": null,\n" +
                "\t\t\"a11\": null,\n" +
                "\t\t\"a12\": null,\n" +
                "\t\t\"a13\": null,\n" +
                "\t\t\"a14\": null,\n" +
                "\t\t\"a15\": null\n" +
                "\t}, {\n" +
                "\t\t\"a0\": null,\n" +
                "\t\t\"a1\": null,\n" +
                "\t\t\"a2\": null,\n" +
                "\t\t\"a3\": null,\n" +
                "\t\t\"a4\": null,\n" +
                "\t\t\"a5\": null,\n" +
                "\t\t\"a6\": null,\n" +
                "\t\t\"a7\": null,\n" +
                "\t\t\"a8\": null,\n" +
                "\t\t\"a9\": null,\n" +
                "\t\t\"a10\": null,\n" +
                "\t\t\"a11\": null,\n" +
                "\t\t\"a12\": null,\n" +
                "\t\t\"a13\": null,\n" +
                "\t\t\"a14\": null,\n" +
                "\t\t\"a15\": null\n" +
                "\t}, {\n" +
                "\t\t\"a0\": null,\n" +
                "\t\t\"a1\": null,\n" +
                "\t\t\"a2\": null,\n" +
                "\t\t\"a3\": null,\n" +
                "\t\t\"a4\": null,\n" +
                "\t\t\"a5\": null,\n" +
                "\t\t\"a6\": null,\n" +
                "\t\t\"a7\": null,\n" +
                "\t\t\"a8\": null,\n" +
                "\t\t\"a9\": null,\n" +
                "\t\t\"a10\": null,\n" +
                "\t\t\"a11\": null,\n" +
                "\t\t\"a12\": null,\n" +
                "\t\t\"a13\": null,\n" +
                "\t\t\"a14\": null,\n" +
                "\t\t\"a15\": null\n" +
                "\t}, {\n" +
                "\t\t\"a0\": null,\n" +
                "\t\t\"a1\": null,\n" +
                "\t\t\"a2\": null,\n" +
                "\t\t\"a3\": null,\n" +
                "\t\t\"a4\": null,\n" +
                "\t\t\"a5\": null,\n" +
                "\t\t\"a6\": null,\n" +
                "\t\t\"a7\": null,\n" +
                "\t\t\"a8\": null,\n" +
                "\t\t\"a9\": null,\n" +
                "\t\t\"a10\": null,\n" +
                "\t\t\"a11\": null,\n" +
                "\t\t\"a12\": null,\n" +
                "\t\t\"a13\": null,\n" +
                "\t\t\"a14\": null,\n" +
                "\t\t\"a15\": null\n" +
                "\t}, {\n" +
                "\t\t\"a0\": null,\n" +
                "\t\t\"a1\": null,\n" +
                "\t\t\"a2\": null,\n" +
                "\t\t\"a3\": null,\n" +
                "\t\t\"a4\": null,\n" +
                "\t\t\"a5\": null,\n" +
                "\t\t\"a6\": null,\n" +
                "\t\t\"a7\": null,\n" +
                "\t\t\"a8\": null,\n" +
                "\t\t\"a9\": null,\n" +
                "\t\t\"a10\": null,\n" +
                "\t\t\"a11\": null,\n" +
                "\t\t\"a12\": null,\n" +
                "\t\t\"a13\": null,\n" +
                "\t\t\"a14\": null,\n" +
                "\t\t\"a15\": null\n" +
                "\t}],\n" +
                "\t\"tlData\": [{\n" +
                "\t\t\"b1\": 40000,\n" +
                "\t\t\"b2\": 34000,\n" +
                "\t\t\"b3\": 1000,\n" +
                "\t\t\"b4\": \"12303\",\n" +
                "\t\t\"b5\": \"12.19\"\n" +
                "\t}, {\n" +
                "\t\t\"b1\": 0,\n" +
                "\t\t\"b2\": 0,\n" +
                "\t\t\"b3\": 0,\n" +
                "\t\t\"b4\": null,\n" +
                "\t\t\"b5\": \"\"\n" +
                "\t}, {\n" +
                "\t\t\"b1\": 10000,\n" +
                "\t\t\"b2\": 7500,\n" +
                "\t\t\"b3\": 500,\n" +
                "\t\t\"b4\": null,\n" +
                "\t\t\"b5\": \"\"\n" +
                "\t}, {\n" +
                "\t\t\"b1\": 50000,\n" +
                "\t\t\"b2\": 41500,\n" +
                "\t\t\"b3\": 1500,\n" +
                "\t\t\"b4\": null,\n" +
                "\t\t\"b5\": null\n" +
                "\t}],\n" +
                "\t\"recordsSummary\": {\n" +
                "\t\t\"c0\": \"3\",\n" +
                "\t\t\"c1\": \"3\",\n" +
                "\t\t\"c2\": 34,\n" +
                "\t\t\"c3\": 79,\n" +
                "\t\t\"c4\": 89,\n" +
                "\t\t\"c5\": \"3\",\n" +
                "\t\t\"c6\": \"3\",\n" +
                "\t\t\"c7\": 40,\n" +
                "\t\t\"c8\": 89,\n" +
                "\t\t\"c9\": 89,\n" +
                "\t},\n" +
                "\t\"deVal\": \"4.06\"\n" +
                "}";
        JSONObject jsonObject = JSON.parseObject(infoForm);
        JSONArray hsDataArray = jsonObject.getJSONArray("hsData");
        JSONArray tlDataArray = jsonObject.getJSONArray("tlData");
        JSONObject recordsSummaryArray = jsonObject.getJSONObject("recordsSummary");
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
            if ("逾期中".equals(hsData.getString("a1"))) {
                overdueSum++;
            }
            accountSum++;
            if ("贷记卡".equals(name) || "准贷记卡".equals(name)) {
                accountSum++;
            }
            if ("贷款".equals(name) || "车贷".equals(name) || "消费贷".equals(name) || "无抵押贷款".equals(name) || "其他贷款".equals(name) || "担保贷款".equals(name)) {
                loanAccountSum++;
            }
        }
        //贷记卡
        JSONObject debitCard = tlDataArray.getJSONObject(0);
        //准贷记卡
        JSONObject semiDebitCard = tlDataArray.getJSONObject(1);
        //贷款
        JSONObject loan = tlDataArray.getJSONObject(2);
        //总计
        JSONObject sum = tlDataArray.getJSONObject(3);
        int totalCredit = debitCard.getIntValue("b1") + semiDebitCard.getIntValue("b1");
        int residualMoney = debitCard.getIntValue("b2") + semiDebitCard.getIntValue("b2");
        int monthlySupply = debitCard.getIntValue("b3") + semiDebitCard.getIntValue("b3");
        int loanTotalCredit = loan.getIntValue("b1");
        int loanResidualMoney = loan.getIntValue("b2");
        int loanMonthlySupply = loan.getIntValue("b3");
        int allTotalCredit = totalCredit + loanTotalCredit;
        int allResidualMoney = residualMoney + loanResidualMoney;
        int allMonthlySupply = monthlySupply + loanMonthlySupply;
        int assessmentIncome = debitCard.getIntValue("b4");
        double DSR = debitCard.getDoubleValue("b5");
        double monthPercent = totalCredit / assessmentIncome;
        double loanMonthPercent = loanTotalCredit / assessmentIncome;
        int oneMonthSum = recordsSummaryArray.getIntValue("c0") + recordsSummaryArray.getIntValue("c5");
        int threeMonthSum = recordsSummaryArray.getIntValue("c1") + recordsSummaryArray.getIntValue("c6");
        CreditInfoModel creditInfoDetail = new CreditInfoModel();
        creditInfoDetail.setAccountSum(accountSum);
        creditInfoDetail.setAllAccountSum(allAccountSum);
        creditInfoDetail.setAllMonthlySupply(allMonthlySupply);
        creditInfoDetail.setAllResidualMoney(allResidualMoney);
        creditInfoDetail.setDSR(DSR);
        creditInfoDetail.setLoanAccountSum(loanAccountSum);
        creditInfoDetail.setLoanMonthlySupply(loanMonthlySupply);
        creditInfoDetail.setLoanMonthPercent(loanMonthPercent);
        creditInfoDetail.setLoanResidualMoney(loanResidualMoney);
        creditInfoDetail.setLoanTotalCredit(loanTotalCredit);
        creditInfoDetail.setMonthlySupply(monthlySupply);
        creditInfoDetail.setMonthPercent(monthPercent);
        creditInfoDetail.setOneMonthSum(oneMonthSum);
        creditInfoDetail.setOverdueSum(overdueSum);
        creditInfoDetail.setResidualMoney(residualMoney);
        creditInfoDetail.setThreeMonthSum(threeMonthSum);
        creditInfoDetail.setTotalCredit(totalCredit);
        System.out.println(creditInfoDetail);
    }
}
