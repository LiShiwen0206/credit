package com.starsgroupchina.credit.server.client.wrapper;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.starsgroupchina.credit.bean.UserInfo;
import com.starsgroupchina.credit.bean.enums.ThirdCreditSource;
import com.starsgroupchina.credit.bean.enums.ThirdProductType;
import com.starsgroupchina.credit.bean.model.ProjectContact;
import com.starsgroupchina.credit.bean.model.ProjectContactExample;
import com.starsgroupchina.credit.bean.model.ThirdCreditResult;
import com.starsgroupchina.credit.bean.third.DfRepsonseData;
import com.starsgroupchina.credit.bean.third.ThirdRequestParam;
import com.starsgroupchina.credit.server.conf.DfConfig;
import com.starsgroupchina.credit.server.conf.RedisConf;
import com.starsgroupchina.credit.server.service.project.ContactService;
import com.starsgroupchina.credit.server.service.project.UserService;
import com.starsgroupchina.credit.server.service.third.ThirdCreditResultService;
import com.starsgroupchina.credit.server.utils.third.df.DfServiceCaller;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import static java.util.stream.Collectors.toList;

/**
 * {
 * ...
 * "b0025": {
 * "description": "身份证基本信息节点",
 * "items": [
 * {
 * "code": 701,
 * "message": "查询成功，有数据",
 * "param": {
 * "id_card_no": "3625********070012"
 * },
 * "result": [
 * {
 * "area": "江西省抚州地区资溪县",
 * "age": "33",
 * "update_time": "2017-06-26 10:25:24"
 * }
 * ]
 * }
 * ]
 * }
 * <p>
 * {
 * ...
 * "b0051": {
 * "description": "互联网地址一致性验证信息节点",
 * "items": [
 * {
 * "code": 701,
 * "message": "查询成功，有数据",
 * "param": {
 * "id_card_no": "340123********126897",
 * "address": "广东省深圳市罗湖区人民北路128大院",
 * "name": "胡太俊"
 * },
 * "result": [
 * {
 * "result": "地址与本人不匹配",
 * "update_time": "2017-06-27 14:16:21"
 * }
 * ]
 * }
 * ]
 * }
 * }
 */
@Slf4j
@Component
public class DfCreditWrapper {

    final static TimeUnit TIME_UNIT = TimeUnit.MINUTES;
    private final static String key = "df_run:";

    @Autowired
    private UserService userService;

    @Autowired
    private DfConfig dfConfig;

    @Autowired
    private ContactService contactService;

    @Autowired
    private ThirdCreditResultService thirdCreditResultService;

    @Resource(name = RedisConf.REDIS_TEMPLATE)
    private RedisOperations redisOperations;

    @Value("${third.expire}")
    private Integer expire;

    private static final String clientId = "starscredit";
    private static final String appCode = "0-9ebQ000sNZM01*";
    private static final String apiVersion = "v2.0.0";
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public DfRepsonseData queryReport(ThirdRequestParam thirdRequestParam) {
        log.info("调用大峰征信接口");
        String dfKey = key+thirdRequestParam.getProjectNo();
        //设置待调查的基本信息
        Boolean isRun = redisOperations.opsForValue().setIfAbsent(dfKey, key);
        if (!isRun) {
            log.info("{}大峰征信正在调用",thirdRequestParam.getProjectNo());
            return null;
        }
        //设置过期时间
        redisOperations.expire(dfKey, expire, TIME_UNIT);
        Map<String, Object> businessParam = new HashMap<String, Object>();
        UserInfo userInfo = userService.getUserInfo(thirdRequestParam.getProjectNo());
        if (userInfo.getProjectNo() == null) {
            log.error("未查询到贷款人信息");
            return new DfRepsonseData();
        }
        businessParam.put("name", userInfo.getName()==null?"":userInfo.getName());
        businessParam.put("id_type", "身份证");
        businessParam.put("id_no", userInfo.getIdCard()==null?"":userInfo.getIdCard());
        //设置待调查的手机号码
        List<String> mobilePhones = new ArrayList<String>();
        mobilePhones.add(userInfo.getPhone()==null?"":userInfo.getPhone());
        businessParam.put("mobile_phones", mobilePhones);//待调查人的手机号
        //设待调查的家庭信息
        List<Map<String, String>> homeContacts = new ArrayList<Map<String, String>>();
        Map<String, String> homeContact = new HashMap<String, String>();
        if (userInfo.getFamilyAddress() != null) {
            homeContact.put("address", userInfo.getFamilyAddress()==null?"":userInfo.getFamilyAddress());//家庭地址
        } else {
            homeContact.put("address", "");
        }
        homeContact.put("telephone",userInfo.getTel()==null?"":userInfo.getPhone());
        homeContacts.add(homeContact);//设置家庭联系方式
        businessParam.put("home_contacts", homeContacts);
        //设置待调查的房产信息
        List<Map<String, String>> realties = new ArrayList<Map<String, String>>();
        Map<String, String> realtie = new HashMap<String, String>();
        if (userInfo.getHouseAddress() != null) {
            realtie.put("city", "");
            realtie.put("name", userInfo.getHouseAddress());
        } else {
            realtie.put("city", "");
            realtie.put("name", "");
        }
        realties.add(realtie);
        businessParam.put("realties", realties);
        //设置待调查的单位信息
        List<Map<String, String>> corporations = new ArrayList<Map<String, String>>();
        Map<String, String> corporation = new HashMap<String, String>();
        if (userInfo.getCompanyName() != null) {
            corporation.put("name", userInfo.getCompanyName());
        } else {
            corporation.put("name", "");
        }
        if (userInfo.getCompanyPhone() != null) {
            corporation.put("telephone", userInfo.getCompanyPhone());
        } else {
            corporation.put("telephone", "");
        }
        if (userInfo.getCompanyAddress() != null) {
            corporation.put("address", userInfo.getCompanyAddress());
        } else {
            corporation.put("address", "");
        }
        if (userInfo.getCompanyJob() != null) {
            corporation.put("position", userInfo.getCompanyJob());
        } else {
            corporation.put("position", "");
        }
        corporations.add(corporation);
        businessParam.put("corporations", corporations);//单位信息
        //设置待调查的联系人信息
        List<Map<String, String>> linkmens = new ArrayList<Map<String, String>>();//联系人信息
        Map<String, String> linkmen = null;
        ProjectContactExample projectContactExample = new ProjectContactExample();
//        projectContactExample.createCriteria().andProjectNoEqualTo(thirdRequestParam.getProjectNo()).andSourceEqualTo(0).andPhoneIsNotNull().andPhoneNotEqualTo("");
        projectContactExample.createCriteria().andProjectNoEqualTo(thirdRequestParam.getProjectNo()).andPhoneIsNotNull().andPhoneNotEqualTo("");
        List<ProjectContact> projectContactList = contactService.query(projectContactExample).collect(toList());
        if (CollectionUtils.isNotEmpty(projectContactList)) {
            for (int i = 0; i < projectContactList.size(); i++) {
                linkmen = new HashMap<>();
                ProjectContact projectContact = projectContactList.get(i);
                linkmen.put("name", projectContact.getName()==null?"":projectContact.getName());
                linkmen.put("relation", projectContact.getRelation()==null?"":projectContact.getRelation());
                linkmen.put("mobile_phone", projectContact.getPhone()==null?"":projectContact.getPhone());
                linkmen.put("telephone", projectContact.getTel()==null?"":projectContact.getTel()       );
                linkmens.add(linkmen);
            }
        } else {
            linkmen = new HashMap<>();
            linkmen.put("name", "");
            linkmen.put("relation", "");
            linkmen.put("mobile_phone", "");
            linkmen.put("telephone", "");
            linkmens.add(linkmen);
        }

        businessParam.put("linkmen", linkmens);
        log.info("大峰接口参数设置完毕");
        try {
            //调用大蜂接口发起请求
            String response = DfServiceCaller.asyncRequest
                    (dfConfig.getBaseUrl(), clientId, appCode, apiVersion, businessParam,
                            3000, 10000, thirdRequestParam);
            log.info("大峰查询结果:" + response);
            boolean status = "processing".equals(JSON.parseObject(response).getJSONObject("header").get("status"));
            if (status) {
                //获取请求报告返回的报告编码
                String reportCode = (String) JSON.parseObject(response).getJSONObject("header").get("transaction_id");

                CompletableFuture.runAsync(
                        () -> {
                            DfRepsonseData dfRepsonseData = null;
                            try {
                                Thread.sleep(5000);
                                dfRepsonseData = DfServiceCaller.getResult
                                        (dfConfig.getBaseUrl(), clientId, appCode, apiVersion, reportCode,
                                                3000, 10000, thirdRequestParam);
                                while ("processing".equals(dfRepsonseData.getHeader().getStatus())) {
//                                    DfRepsonseData.Body.Data data = dfRepsonseData.getBody().getData();
                                    //让线程睡眠5秒再去查询报告结果
                                    Thread.sleep(10000);
                                    dfRepsonseData = DfServiceCaller.getResult
                                            (dfConfig.getBaseUrl(),clientId, appCode, apiVersion, reportCode,
                                                    3000, 10000, thirdRequestParam);
                                }
                                log.info("大蜂返回结果数据为：{}", objectMapper.writeValueAsString(dfRepsonseData));
                                ThirdCreditResult thirdCreditResult = new ThirdCreditResult();
                                thirdCreditResult.setProjectId(thirdRequestParam.getProjectId());
                                thirdCreditResult.setProjectNo(thirdRequestParam.getProjectNo());
                                thirdCreditResult.setSource(ThirdCreditSource.SOURCE_DF.key());
                                thirdCreditResult.setThirdProductType(ThirdProductType.TYPE_DF.key());
                                thirdCreditResult.setResult(objectMapper.writeValueAsString(dfRepsonseData));
                                thirdCreditResultService.create(thirdCreditResult);
                            } catch (Exception e) {
                                e.printStackTrace();
                            } finally {
                                redisOperations.delete(key);
                            }
                        }
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
