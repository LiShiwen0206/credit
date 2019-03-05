package com.starsgroupchina.credit.server.client.wrapper;

import com.alibaba.fastjson.JSONArray;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.starsgroupchina.common.utils.Utils;
import com.starsgroupchina.credit.bean.UserInfo;
import com.starsgroupchina.credit.bean.enums.ThirdCreditPlatform;
import com.starsgroupchina.credit.bean.enums.ThirdCreditSource;
import com.starsgroupchina.credit.bean.enums.ThirdProductType;
import com.starsgroupchina.credit.bean.model.Project;
import com.starsgroupchina.credit.bean.model.ProjectExample;
import com.starsgroupchina.credit.bean.model.ThirdCreditResult;
import com.starsgroupchina.credit.bean.model.ThirdRequestHistory;
import com.starsgroupchina.credit.bean.third.ThirdCreditQhzx8036;
import com.starsgroupchina.credit.bean.third.ThirdCreditQhzx8107;
import com.starsgroupchina.credit.bean.third.ThirdCreditQhzx8107ErrInfo;
import com.starsgroupchina.credit.bean.third.ThirdRequestParam;
import com.starsgroupchina.credit.server.conf.QhConfig;
import com.starsgroupchina.credit.server.service.project.ContactService;
import com.starsgroupchina.credit.server.service.project.ProjectService;
import com.starsgroupchina.credit.server.service.project.UserService;
import com.starsgroupchina.credit.server.service.third.ThirdCreditResultService;
import com.starsgroupchina.credit.server.service.third.ThirdRequestHistoryService;
import com.starsgroupchina.credit.server.utils.DateUtil;
import com.starsgroupchina.credit.server.utils.SpringContextUtils;
import com.starsgroupchina.credit.server.utils.third.qhzx.DataSecurityUtil;
import com.starsgroupchina.credit.server.utils.third.qhzx.HttpRequestUtil;
import com.starsgroupchina.credit.server.utils.third.qhzx.MessageUtil;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@Component
public class QhzxCreditWrapper {

    @Autowired
    private ThirdRequestHistoryService thirdRequestHistoryService;

    @Autowired
    private QhConfig qhConfig;

    @Autowired
    private UserService userService;

    @Autowired
    private ContactService contactService;

    @Autowired
    private ProjectService projectService;
    @Autowired
    private ThirdCreditResultService thirdCreditResultService;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private static final String orgCode = "91440300360087509M";
    private static final String chnlId = "hxzhicheng";
    private static final String authCode = "OTH1173XX";
    private static final String checkNum = "_xnYvsqxDXnydF2VSD5EE8Z7";
    private static final String userPassword = "FyDc0_45";
    private static final String subProductInc = "0000001000000000";
    /**
     * 证件类型
     * **************************************************
     * 0-身份证；
     * 1-户口簿；
     * 2-护照；
     * 3-军官证；
     * 4-士兵证；
     * 5-港澳居民来往内地通行证；
     * 6-台湾同胞来往内地通行证；
     * 7-临时身份证；
     * 8-外国人居留证；
     * 9-警官证；
     * X-其他证件。
     * *******************************************************
     * 1-1 gt
     *
     * @return
     */
    public String MSC8036(ThirdRequestParam thirdRequestParam) {
        log.info("调用前海征信8036查询接口");
        String result = "";
        try {
            UserInfo userInfo = userService.getUserInfo(thirdRequestParam.getProjectNo());
            if (userInfo == null || userInfo.getProjectNo() == null) {
                log.error("未查询到贷款人信息");
                return result;
            }
            ProjectExample projectExample = new ProjectExample();
            projectExample.createCriteria().andProjectNoEqualTo(thirdRequestParam.getProjectNo());
            Project project = Utils.getFirst(projectService.query(projectExample));
            String url = qhConfig.getUrl() + qhConfig.getMsc8036();
            Map<String, Object> map1 = new LinkedHashMap<String, Object>();
            List<Map<String, String>> list = new ArrayList<Map<String, String>>();
            Map<String, String> map2 = new LinkedHashMap<String, String>();
            map1.put("batchNo", "B0001");
            map2.put("idNo", userInfo.getIdCard() == null ? "" : userInfo.getIdCard());
//            map2.put("idNo", "210202195901160032");
            map2.put("idType", "0");
            map2.put("name", userInfo.getName() == null ? "" : userInfo.getName());
//            map2.put("name", "褚宝刚");
            map2.put("reasonCode", "01");
            map2.put("ips", "");
            map2.put("cardNos", "");
            map2.put("moblieNos", userInfo.getPhone());
            map2.put("entityAuthCode", userInfo.getProjectNo());
            DateFormat sdf = DateUtil.yyyyMMddFormatter.get();
            map2.put("entityAuthDate", sdf.format(project.getApplyDate()));
            map2.put("seqNo", "0001");
            list.add(map2);
            map1.put("records", list);
            JSONObject json = JSONObject.fromObject(map1);
            ThirdRequestHistory thirdRequestHistory = new ThirdRequestHistory();
            thirdRequestHistory.setMethod(url);
            thirdRequestHistory.setParams(json.toString());
            thirdRequestHistory.setProjectNo(thirdRequestParam.getProjectNo());
            thirdRequestHistory.setPlatform(ThirdCreditPlatform.PLATFORM_QHZX_8036.key());
            thirdRequestHistory.setProjectId(thirdRequestParam.getProjectId());
            result = getResult(json.toString(), url);
            thirdRequestHistory.setResult(result);
            thirdRequestHistoryService.create(thirdRequestHistory);
            log.info("前海征信8036查询接口查询结果为{}", result);
            if (StringUtils.isNotEmpty(result)) {
//                ThirdCreditQhzx8036Example example = new ThirdCreditQhzx8036Example();
//                example.createCriteria().andProjectIdEqualTo(thirdRequestParam.getProjectId()).andGroupIdEqualTo(1).andTypeIdEqualTo(2);
//                thirdCreditQhzx8036Mapper.deleteByExample(example);
//
                ThirdCreditQhzx8036 thirdCreditQhzx8036 = new ThirdCreditQhzx8036();
                thirdCreditQhzx8036.setProjectId(thirdRequestParam.getProjectId());
                thirdCreditQhzx8036.setGroupId(1);
                thirdCreditQhzx8036.setTypeId(2);

                com.alibaba.fastjson.JSONObject j = com.alibaba.fastjson.JSONObject.parseObject(result);
                JSONArray arr = j.getJSONArray("records");
                for (int i = 0; i < arr.size(); i++) {
                    com.alibaba.fastjson.JSONObject record = com.alibaba.fastjson.JSONObject.parseObject(arr.get(i) + "");

                    thirdCreditQhzx8036.setName(record.getString("name"));//主体名称
                    if ("0".equals(record.getString("idType"))) { //证件类型
                        if (StringUtils.isNotEmpty(record.getString("idNo"))) {
                            thirdCreditQhzx8036.setCertNo(record.getString("idNo"));//证件号码
                        } else {
                            thirdCreditQhzx8036.setCertNo(userInfo.getIdCard());//证件号码
                        }

                    }
                    /**
                     * 来源代码：A-信贷逾期风险，B-行政负面风险，99-权限不足
                     */
                    thirdCreditQhzx8036.setSrcCode(record.getString("sourceId"));
                    Double credooScore = record.getDouble("credooScore");
                    thirdCreditQhzx8036.setScore(credooScore == null ? 0 : credooScore);
                    /**
                     * 风险得分，仅当sourceId为A时，10-45分。-1-权限不足
                     */
                    Double rskScore = record.getDouble("rskScore");
                    thirdCreditQhzx8036.setRiskScore(rskScore == null ? 0 : rskScore);
                    /**
                     * 风险标记：B2-被执行人，B1-失信被执行人，B3-交通严重违章，99-权限不足
                     */
                    thirdCreditQhzx8036.setRiskFlag(record.getString("rskMark"));
                    /**
                     * 业务发生时间：yyyy-MM-dd，99-权限不足
                     */
                    thirdCreditQhzx8036.setBusiDate(record.getDate("dataBuildTime"));
                    /**
                     * 数据状态：99-权限不足，1-已验证，2-未验证，3-异议中
                     */
                    thirdCreditQhzx8036.setDataStatus(record.getString("dataStatus"));
                    thirdCreditQhzx8036.setSearchDate(new Date());
                    /**
                     * 错误代码：E+{六位数字}，E000000-成功，E000996-未在前海征信记录中发现相关风险行为
                     */
                    thirdCreditQhzx8036.setRetCode(record.getString("erCode"));
                    thirdCreditQhzx8036.setRetResult(record.getString("erMsg"));//错误信息
                    thirdCreditQhzx8036.setBillDate(new Date());

                    ThirdCreditResult thirdCreditResult = new ThirdCreditResult();
                    thirdCreditResult.setProjectId(thirdRequestParam.getProjectId());
                    thirdCreditResult.setProjectNo(thirdRequestParam.getProjectNo());
                    thirdCreditResult.setSource(ThirdCreditSource.SOURCE_QHZX.key());
                    thirdCreditResult.setThirdProductType(ThirdProductType.TYPE_QHZX8036.key());
                    thirdCreditResult.setResult(objectMapper.writeValueAsString(thirdCreditQhzx8036));
                    thirdCreditResultService.create(thirdCreditResult);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String getHeaders() {
        QhConfig config = SpringContextUtils.getBean(QhConfig.class);
        Map<String, Map<String, String>> map1 = new LinkedHashMap<String, Map<String, String>>();
        Map<String, String> map2 = new LinkedHashMap<String, String>();
        map2.put("orgCode", orgCode);
        map2.put("chnlId", chnlId);
        map2.put("transNo", System.currentTimeMillis() + "");
        map2.put("transDate", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        map2.put("authCode", authCode);
        map2.put("authDate", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));

        map1.put("header", map2);
        JSONObject json = JSONObject.fromObject(map1);
        return json.toString().substring(1, json.toString().length() - 1);
    }

    public static String getResult(String json, String url) {
        String result = "";
        QhConfig config = SpringContextUtils.getBean(QhConfig.class);
        try {
            String busidata = DataSecurityUtil.encrypt(json.getBytes("utf-8"),checkNum);
            String busiData = "\"busiData\":\"" + busidata + "\"";
            String sigValue = DataSecurityUtil.signData(busidata);
            String pwd = DataSecurityUtil.digest(userPassword.getBytes());
            String securityInfo = "\"securityInfo\":" + MessageUtil.getSecurityInfo(sigValue, pwd);
            String message = "{" + getHeaders() + "," + busiData + "," + securityInfo + "}";
            String res = HttpRequestUtil.sendJsonWithHttps(url, message);
            JSONObject msgJSON = JSONObject.fromObject(res);
            // 一定要验证签名的合法性！！！！！！！！
            DataSecurityUtil.verifyData(msgJSON.getString("busiData"), msgJSON.getJSONObject("securityInfo").getString("signatureValue"));
            result = DataSecurityUtil.decrypt(msgJSON.getString("busiData"),checkNum);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * @param thirdRequestParam
     * @return
     */
    public String MSC8107(ThirdRequestParam thirdRequestParam) {
        String result = "";
        try {
            UserInfo userInfo = userService.getUserInfo(thirdRequestParam.getProjectNo());
            if (userInfo == null) {
                log.error("未查询到贷款人信息");
                return result;
            }
            log.info("调用前海征信8107查询接口");
            String url = qhConfig.getUrl() + qhConfig.getMsc8107();
            Map<String, Object> map1 = new LinkedHashMap<String, Object>();
            List<Map<String, String>> list = new ArrayList<Map<String, String>>();
            Map<String, String> map2 = new LinkedHashMap<String, String>();
            map1.put("batchNo", "B0002");
            map1.put("subProductInc", subProductInc);
            map2.put("idNo", userInfo.getIdCard());//身份证号码
//            map2.put("idNo", "220701197507141089");
            map2.put("idType", "0");
//            map2.put("name", "汤佳盛");//客户姓名
            map2.put("name", userInfo.getName());
            map2.put("mobileNo", userInfo.getPhone());//电话号码
//            map2.put("mobileNo", "18126098069");//电话号码
            map2.put("company", userInfo.getCompanyName() == null ? "" : userInfo.getCompanyName());//公司
//            map2.put("company", "平安金融科技公司");//公司
            map2.put("refName", userInfo.getSpouseName() == null ? "" : userInfo.getSpouseName());//联系人姓名
            map2.put("refMobileNo", userInfo.getSpousePhone() == null ? "" : userInfo.getSpousePhone());//联系人号码
            map2.put("reasonCode", "01");
            ProjectExample projectExample = new ProjectExample();
            projectExample.createCriteria().andProjectNoEqualTo(thirdRequestParam.getProjectNo());
            Project project = Utils.getFirst(projectService.query(projectExample));
            DateFormat sdf = DateUtil.yyyyMMddFormatter.get();
            map2.put("entityAuthDate", sdf.format(project.getApplyDate()));
            map2.put("entityAuthCode", project.getProjectNo());
            map2.put("seqNo", "0001");
            list.add(map2);
            map1.put("records", list);
            JSONObject json = JSONObject.fromObject(map1);

            ThirdRequestHistory thirdRequestHistory = new ThirdRequestHistory();
            thirdRequestHistory.setMethod(url);
            thirdRequestHistory.setParams(json.toString());
            thirdRequestHistory.setPlatform(ThirdCreditPlatform.PLATFORM_QHZX_8107.key());
            thirdRequestHistory.setProjectId(thirdRequestParam.getProjectId());
            thirdRequestHistory.setProjectNo(thirdRequestParam.getProjectNo());
            result = getResult(json.toString(), url);
            thirdRequestHistory.setResult(result);
            thirdRequestHistoryService.create(thirdRequestHistory);
            log.info("前海征信8107查询接口查询结果为{}", result);
            if (StringUtils.isNotEmpty(result)) {
                ThirdCreditQhzx8107 thirdCreditQhzx8107 = new ThirdCreditQhzx8107();
                thirdCreditQhzx8107.setCompanyName(userInfo.getCompanyName());
                thirdCreditQhzx8107.setProjectId(thirdRequestParam.getProjectId());
                thirdCreditQhzx8107.setContactName(userInfo.getSpouseName());
                thirdCreditQhzx8107.setContactPhone(userInfo.getSpousePhone());
                com.alibaba.fastjson.JSONObject j = com.alibaba.fastjson.JSONObject.parseObject(result);
                JSONArray arr = j.getJSONArray("records");

                for (int i = 0; i < arr.size(); i++) {
                    thirdCreditQhzx8107.setThirdCreditQhzx8107ErrInfos(new ArrayList<>());
                    com.alibaba.fastjson.JSONObject record = com.alibaba.fastjson.JSONObject.parseObject(arr.get(i) + "");
                    if (StringUtils.isNotEmpty(record.getString("name"))) {
                        thirdCreditQhzx8107.setName(record.getString("name"));//主体名称
                    } else {
                        thirdCreditQhzx8107.setName(userInfo.getName());
                    }
                    if (StringUtils.isNotEmpty(record.getString("idType"))) {
                        thirdCreditQhzx8107.setIdType(record.getLong("idType").intValue());//证件类型
                    } else {
                        thirdCreditQhzx8107.setIdType(0);//证件类型
                    }
                    if (StringUtils.isNotEmpty(record.getString("idNo"))) {
                        thirdCreditQhzx8107.setIdNo(record.getString("idNo"));//证件号码
                    } else {
                        thirdCreditQhzx8107.setIdNo(userInfo.getIdCard());
                    }
                    if (StringUtils.isNotEmpty(record.getString("mobileNo"))) {
                        thirdCreditQhzx8107.setMobileNo(record.getString("mobileNo"));//手机号码
                    } else {
                        thirdCreditQhzx8107.setMobileNo(userInfo.getPhone());//手机号码

                    }
                    thirdCreditQhzx8107.setSeqNo(record.getString("seqNo"));//序列号
                    /**
                     *错误信息:
                     erCode
                     E000000:成功
                     E000001:失败
                     E000NON:未覆盖
                     E0000NG:不适用
                     {
                     "0000000000000001":{"erCode":"E000000","errMsg":"成功"}
                     }
                     */
                    com.alibaba.fastjson.JSONObject errorInfo = com.alibaba.fastjson.JSONObject.parseObject(record.getString("errorInfo"));
                    //子产品 工作单位验证
                    com.alibaba.fastjson.JSONObject obj = (com.alibaba.fastjson.JSONObject) errorInfo.get("0000000000000100");
                    if ("E000000".equals(obj.getString("erCode"))) {
                        if (StringUtils.isNotEmpty(record.getString("isRealCompany")))
                            thirdCreditQhzx8107.setIsRealCompany(record.getLong("isRealCompany").intValue());//是否真实工作单位
                        if (StringUtils.isNotEmpty(record.getString("companySimDeg")))
                            thirdCreditQhzx8107.setCompanySimDeg(record.getDouble("companySimDeg").intValue());//单位匹配度分值
                        if (StringUtils.isNotEmpty(record.getString("appear1ThDate")))
                            thirdCreditQhzx8107.setAppearFirstDate(record.getString("appear1ThDate"));//单位最初出现时间
                        if (StringUtils.isNotEmpty(record.getString("appearLastDate")))
                            thirdCreditQhzx8107.setAppearLastDate(record.getString("appearLastDate"));//单位最近一次出现时间
                    } else {
                        thirdCreditQhzx8107.setIsRealCompany(10);
                    }                    //子产品 关系人验证
                    obj = (com.alibaba.fastjson.JSONObject) errorInfo.get("0000000000010000");
                    if ("E000000".equals(obj.getString("erCode"))) {
                        if (StringUtils.isNotEmpty(record.getString("isExistRel")))
                        /**
                         * 认证模式第5位为'1'时返回
                         0：否
                         1：是
                         9：库中无数据
                         */
                            thirdCreditQhzx8107.setIsExistRel(record.getLong("isExistRel").intValue());//是否存在关系
                        if (StringUtils.isNotEmpty(record.getString("relLevel")))
                        /**
                         * 认证模式第5位为'1'时返回
                         3：其他
                         2：强
                         1：中
                         0：弱
                         */
                            thirdCreditQhzx8107.setRelLevel(record.getLong("relLevel").intValue());//关系力度
                    } else {
                        thirdCreditQhzx8107.setIsExistRel(10);//是否存在关系
                    }
                    //子产品 房产验证
                    obj = (com.alibaba.fastjson.JSONObject) errorInfo.get("0000000001000000");
                    if ("E000000".equals(obj.getString("erCode"))) {
                        if (StringUtils.isNotEmpty(record.getString("houseChkResult")))
                            thirdCreditQhzx8107.setHouseChkResult(record.getLong("houseChkResult").intValue());//房屋验证结果1:有房,9:库中无数据
                        if (StringUtils.isNotEmpty(record.getString("houseDataDate")))
                            thirdCreditQhzx8107.setHouseDataDate(record.getString("houseDataDate"));//houseDataDate
                    } else {
                        thirdCreditQhzx8107.setHouseChkResult(10);
                    }
                    //子产品 手机验证II
                    obj = (com.alibaba.fastjson.JSONObject) errorInfo.get("0000001000000000");
                    if ("E000000".equals(obj.getString("erCode"))) {
                        if (StringUtils.isNotEmpty(record.getString("isOwnerMobileII")))
                        /**
                         0：手机号、证件号、姓名均一致(该手机号在运营商有登记，且证件号和手机都登记正确，无风险)
                         1：手机号和证件号一致，姓名不一致(该手机号在运营商有登记，且证件号登记正确但姓名不正确，可能为姓名登记错误的情况，请结合身份验证情况进一步判断风险)
                         2：手机号和证件号一致，姓名不明确(该手机号在运营商有登记，且证件号登记正确但姓名不明确，可能为姓名遗漏登记的情况，请结合身份验证情况进一步判断风险)
                         3：手机号一致，证件号和姓名不一致(该手机号在运营商有登记，但实名登记情况不正确，存在欺诈风险)
                         9：库中无数据(该手机号在运营商没有登记或该手机号登记信息暂时无法获取)
                         */
                            thirdCreditQhzx8107.setIsOwnerMobile2(record.getLong("isOwnerMobileII").intValue());//手机验证II结果
                        if (StringUtils.isNotEmpty(record.getString("ownerMobileStatusII")))
                        /**
                         1：正常
                         2：停机
                         3：不可用
                         4：已销号
                         5：预销号
                         9：不明确
                         */
                            thirdCreditQhzx8107.setOwnerMobileStatus2(record.getLong("ownerMobileStatusII").intValue());//手机状态II
                        if (StringUtils.isNotEmpty(record.getString("useTimeScoreII")))
                        /**
                         -1：不可用
                         1：(0-1]
                         2： (1-2]，
                         3： (2-6]
                         4： (6-12]
                         5： (12-24]
                         6 ：(24-36]
                         7： (36,+]
                         30 ：(0,6]
                         60： （24,+]
                         */
                            thirdCreditQhzx8107.setUseTimeScore2(record.getLong("useTimeScoreII").intValue());//使用时间分数II
                    }
                    thirdCreditQhzx8107.setBillDate(new Date());
//                        zcCreditQhzx8107.setBillerId(userId);
//                    thirdCreditQhzx8107Mapper.insert(thirdCreditQhzx8107);
                    List<ThirdCreditQhzx8107ErrInfo> thirdCreditQhzx8107ErrInfos = thirdCreditQhzx8107.getThirdCreditQhzx8107ErrInfos();
                    //保存工作单位验证返回错误信息
                    obj = (com.alibaba.fastjson.JSONObject) errorInfo.get("0000000000000100");
                    if (obj != null) {
                        ThirdCreditQhzx8107ErrInfo thirdCreditQhzx1ErrInfo = new ThirdCreditQhzx8107ErrInfo();
                        thirdCreditQhzx1ErrInfo.setRetCode(obj.getString("erCode"));
                        thirdCreditQhzx1ErrInfo.setRetMsg(obj.getString("errMsg"));
                        thirdCreditQhzx1ErrInfo.setTypeId(3);
                        thirdCreditQhzx8107ErrInfos.add(thirdCreditQhzx1ErrInfo);
                    }
                    //保存关系人验证返回错误信息
                    obj = (com.alibaba.fastjson.JSONObject) errorInfo.get("0000000000010000");
                    if (obj != null) {
                        ThirdCreditQhzx8107ErrInfo thirdCreditQhzx1ErrInfo = new ThirdCreditQhzx8107ErrInfo();
                        thirdCreditQhzx1ErrInfo.setRetCode(obj.getString("erCode"));
                        thirdCreditQhzx1ErrInfo.setRetMsg(obj.getString("errMsg"));
                        thirdCreditQhzx1ErrInfo.setTypeId(5);
                        thirdCreditQhzx8107ErrInfos.add(thirdCreditQhzx1ErrInfo);
                    }
                    //保存房产验证返回错误信息
                    obj = (com.alibaba.fastjson.JSONObject) errorInfo.get("0000000001000000");
                    if (obj != null) {
                        ThirdCreditQhzx8107ErrInfo thirdCreditQhzx1ErrInfo = new ThirdCreditQhzx8107ErrInfo();
                        thirdCreditQhzx1ErrInfo.setRetCode(obj.getString("erCode"));
                        thirdCreditQhzx1ErrInfo.setRetMsg(obj.getString("errMsg"));
                        thirdCreditQhzx1ErrInfo.setTypeId(7);
                        thirdCreditQhzx8107ErrInfos.add(thirdCreditQhzx1ErrInfo);
                    }

                    //保存手机验证II返回错误信息
                    obj = (com.alibaba.fastjson.JSONObject) errorInfo.get("0000001000000000");
                    if (obj != null) {
                        ThirdCreditQhzx8107ErrInfo thirdCreditQhzx1ErrInfo = new ThirdCreditQhzx8107ErrInfo();
                        thirdCreditQhzx1ErrInfo.setRetCode(obj.getString("erCode"));
                        thirdCreditQhzx1ErrInfo.setRetMsg(obj.getString("errMsg"));
                        thirdCreditQhzx1ErrInfo.setTypeId(10);
                        thirdCreditQhzx8107ErrInfos.add(thirdCreditQhzx1ErrInfo);
                    }
                    ThirdCreditResult thirdCreditResult = new ThirdCreditResult();
                    thirdCreditResult.setProjectId(thirdRequestParam.getProjectId());
                    thirdCreditResult.setSource(ThirdCreditSource.SOURCE_QHZX.key());
                    thirdCreditResult.setThirdProductType(ThirdProductType.TYPE_QHZX8107.key());
                    thirdCreditResult.setProjectNo(thirdRequestParam.getProjectNo());
                    thirdCreditResult.setResult(objectMapper.writeValueAsString(thirdCreditQhzx8107));
                    thirdCreditResultService.create(thirdCreditResult);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


}
