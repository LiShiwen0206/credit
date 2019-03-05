package com.starsgroupchina.credit.server.service.project;

import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.googlecode.aviator.AviatorEvaluator;
import com.googlecode.aviator.Expression;
import com.googlecode.aviator.Options;
import com.starsgroupchina.common.XStatus;
import com.starsgroupchina.common.base.AbstractService;
import com.starsgroupchina.common.context.ContextHolder;
import com.starsgroupchina.common.utils.MapUtil;
import com.starsgroupchina.credit.bean.AuthMember;
import com.starsgroupchina.credit.bean.enums.RiskModelType;
import com.starsgroupchina.credit.bean.enums.ScoreType;
import com.starsgroupchina.credit.bean.mapper.ProjectScoreMapper;
import com.starsgroupchina.credit.bean.model.*;
import com.starsgroupchina.credit.server.service.ModelService;
import com.starsgroupchina.credit.server.service.RiskModelItem;
import com.starsgroupchina.credit.server.service.system.MemberService;
import com.starsgroupchina.credit.server.service.system.OrgService;
import com.starsgroupchina.credit.server.utils.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Stream;

/**
 * Created by zhangfeng on 2018/6/25
 */
@Slf4j
@Service
public class ScoreService extends AbstractService<ProjectScore, ProjectScoreExample> {

    @Autowired
    private ProjectScoreMapper projectScoreMapper;
    @Autowired
    private ProjectService projectService;
    @Autowired
    private FormService.UserFormDetailService userFormService;
    @Autowired
    private ModelService modelService;
    @Autowired
    private ModelService.ModelFieldService modelFieldService;
    @Autowired
    @Qualifier("thirdCreditResultService")
    private RiskModelItem thirdCreditResultService;
    @Autowired
    @Qualifier("creditInfoService")
    private RiskModelItem creditInfoService;
    @Autowired
    private ModelService.ModelGroupService modelGroupService;
    @Autowired
    private MemberService memberService;
    @Autowired
    private OrgService orgService;
    /**
     * 需要根据配置的日期换算成月数的field key
     * Map<需要处理的字段key,取日期的字段key>
     */
    private static Map<String, String> DATE_VALID_FIELD_KEYS = ImmutableMap.of(
            "c022", "c013",
            "d0012", "d001",
            "d0022", "d002",
            "e0112", "e011",
            "k0042", "k004"
    );

    private static final String B037 = "b037";
    private static final String B022 = "b022";
    private static final String B038 = "b038";
    private static final String B010 = "b010";
    private static final String K0022 = "k0022";
    private static final String K005 = "k005";
    private static final String K006 = "k006";

    //（0、否，1、是，9、无数据，10、查询失败，不适用）
    private static final String isRealCompany = "isRealCompany".toLowerCase();
    private static Map<String, String> isRealCompanyValuesMapping = ImmutableMap.of(
            "1", "1",
            "0", "0",
            "9", "0",
            "10", "0"
    );

    //（0、否，1、是，9、无数据，10、查询失败，不适用）
    private static final String isExistRel = "isExistRel".toLowerCase();
    private static Map<String, String> isExistRelValuesMapping = ImmutableMap.of(
            "1", "1",
            "0", "0",
            "9", "0",
            "10", "0"
    );

    //（1:有房9:库中无数据 10:查询失败、不适用）
    private static final String houseChkResult = "houseChkResult".toLowerCase();
    private static Map<String, String> houseChkResultValuesMapping = ImmutableMap.of(
            "1", "1",
            "0", "0",
            "9", "0",
            "10", "0"
    );

    //（0：手机号、证件号、姓名均一致，1：手机号和证件号一致，姓名不一致，2：手机号和证件号一致，姓名不明确，3：手机号一致，证件号和姓名不一致，9：库中无数据，10、查询失败，不适用）
    private static final String isOwnerMobile2 = "isOwnerMobile2".toLowerCase();
    private static Map<String, String> isOwnerMobile2ValuesMapping = new ImmutableMap.Builder<String, String>()
            .put("0", "1")
            .put("1", "0")
            .put("2", "0")
            .put("3", "0")
            .put("9", "0")
            .put("10", "0").build();

    //(1：正常2：停机3：不可用4：已销号5：预销号9：不明确 10、查询失败，不适用)
    private static final String ownerMobileStatus2 = "ownerMobileStatus2".toLowerCase();
    private static Set<String> ownerMobileStatus2ValidValues = ImmutableSet.of(
            "1", "2", "3", "4"
    );

    private static Set<String> needTranslateFieldKeys = new ImmutableSet.Builder<String>()
            .add(isRealCompany)
            .add(isExistRel)
            .add(houseChkResult)
            .add(isOwnerMobile2).build();


    private static Map<String, Map<String, String>> translateValueMap = ImmutableMap.of(
            isRealCompany, isRealCompanyValuesMapping,
            isExistRel, isExistRelValuesMapping,
            houseChkResult, houseChkResultValuesMapping,
            isOwnerMobile2, isOwnerMobile2ValuesMapping
    );

    @Override
    public ProjectScore update(ProjectScore projectScore) {
        ProjectScoreExample example = new ProjectScoreExample();
        example.createCriteria().andProjectNoEqualTo(projectScore.getProjectNo());
        projectScoreMapper.deleteByExample(example);
        return create(projectScore);
    }

    public double getScore(String projectNo) {
        Project project = projectService.getProjectByProjectNo(projectNo);
        if (project.getModelId() == -1) return 0;
        RiskModel model = modelService.getById(project.getModelId());
        ProjectScoreExample example = new ProjectScoreExample();
        example.createCriteria().andProjectNoEqualTo(projectNo);
        return this.query(example).mapToDouble(projectScore->projectScore.getModelGroupScore().doubleValue()).sum();
    }

    public void deleteScore(String projectNo) {
        ProjectScoreExample example = new ProjectScoreExample();
        example.createCriteria().andProjectNoEqualTo(projectNo);
        deleteByExample(example);
    }

    //        表达式类型：
//        1 ，闭区间“[ ]” : 例如  “[2,5]” 等同于 “2≤x≤5”
//        2 ，开区间“( )” : 例如  “(2,5)” 等同于 “2＜x＜5”
//        3 ，半开半闭区间  : 例如  “2≤x<5” 等同于 “[2,5)”  ，“(2,5]” 等同于 “2＜x≤5”
//        4 ，大于 或 小于  : 例如  “(-,5]”等同于“x≤5” ，“（2,+）”等同于“2<x”
//        5 ，包含“非其他字符开头的”   : 例如  “上海，北京，深圳”  等同于 “ 包含：上海，北京，深圳”
//        6 ，字符不包含“!{}”: 例如  “!{上海，北京，深圳}”  等同于“不包含：上海，北京，深圳”
    public void computeScore(String projectNo) {
        AviatorEvaluator.setOption(Options.OPTIMIZE_LEVEL, AviatorEvaluator.EVAL);
        Project project = projectService.getProjectByProjectNo(projectNo);

        Map<String, String> allFormFieldMap = Maps.newHashMap();
        FormDetailUser userFormDetail = userFormService.getUserFormDetail(projectNo);
        Optional.ofNullable(MapUtil.objToMap(userFormDetail)).ifPresent(formDetail -> allFormFieldMap.putAll(formDetail));

        //如果isHaveValue为true，说明该详单添加过数据
        Optional.ofNullable(creditInfoService.getRiskModelItem(projectNo, ScoreType.CREDIT_SIMPE)).ifPresent(formDetail -> {
            if (formDetail.get("isHaveValue") != null && Boolean.valueOf(formDetail.get("isHaveValue"))) {
                allFormFieldMap.putAll(formDetail);
            }

        });
        //如果isHaveValue为true，说明该详单添加过数据
        Optional.ofNullable(creditInfoService.getRiskModelItem(projectNo, ScoreType.CREDIT_DETAIL)).ifPresent(formDetail -> {
            if (formDetail.get("isHaveValue") != null && Boolean.valueOf(formDetail.get("isHaveValue"))) {
                allFormFieldMap.putAll(formDetail);
            }

        });
        Optional.ofNullable(thirdCreditResultService.getRiskModelItem(projectNo, ScoreType.THIRD_QH)).ifPresent(formDetail -> allFormFieldMap.putAll(formDetail));
        Optional.ofNullable(thirdCreditResultService.getRiskModelItem(projectNo, ScoreType.THIRD_TD)).ifPresent(formDetail -> allFormFieldMap.putAll(formDetail));

        log.info("projectNo:{}, allFormFieldMap-->{}", projectNo, allFormFieldMap);

        Map<String, String> allFormField2LowerKeyMap = allFormFieldMap;
        if (MapUtils.isNotEmpty(allFormFieldMap)) {
            allFormField2LowerKeyMap = convertMapKey2Lower(allFormFieldMap);
        }

        List<ProjectScore> scores = Lists.newArrayList();
        RiskModel riskModel = modelService.getById(project.getModelId());
        List<RiskModelGroup> riskModelGroups = modelGroupService.getGroupByModelId(project.getModelId());

        if (CollectionUtils.isNotEmpty(riskModelGroups)) {
            Map<String, String> finalAllFormField2LowerKeyMap = allFormField2LowerKeyMap;
            for (int i = 0; i < riskModelGroups.size(); i++) {
                RiskModelGroup riskModelGroup = riskModelGroups.get(i);
                Boolean groupFieldResult = this.calcModelFieldScoresAndSave(project, riskModelGroup, finalAllFormField2LowerKeyMap);
                if (groupFieldResult) {
                    ProjectScore score = new ProjectScore();
                    score.setProjectNo(projectNo);
                    score.setModelGroupId(riskModelGroup.getId());
                    score.setModelId(riskModelGroup.getModelId());
                    score.setStatus(XStatus.NORMAL.key());
                    score.setModelGroupScore(riskModelGroup.getScore());
                    scores.add(score);
                    if (riskModel.getType().equals(RiskModelType.DECISION_TREE.key())){
                        break;
                    }
                }
            }
        }

        this.deleteScore(projectNo);
        this.create(scores);
    }

    private Map<String, String> removeNullValue(Map<String, String> formDetail) {
        Map<String, String> newMap = Maps.newHashMap();
        formDetail.forEach((k, v) -> {
            if (v != null) {
                newMap.put(k.toLowerCase(), String.valueOf(v));
            }
        });
        return newMap;
    }

    private boolean isNeedScore(String policyFieldKey, Map<String, String> form) {
        //验证手机状态是否需要打分
        if (ownerMobileStatus2.equalsIgnoreCase(policyFieldKey)) {
            if (!ownerMobileStatus2ValidValues.contains(form.get(policyFieldKey))) {
                return Boolean.FALSE;
            }
        }

        return Boolean.TRUE;
    }

    private boolean calcModelFieldScoresAndSave(Project project, RiskModelGroup modelGroup, Map<String, String> allFormFieldMap) {

        Map<String, Object> fieldResultMap = Maps.newHashMap();

        RiskModelFieldExample example = new RiskModelFieldExample();
        example.createCriteria().andGroupIdEqualTo(modelGroup.getId());
        Stream<RiskModelField> fieldStream = modelFieldService.query(example);
        fieldStream.forEach(riskModelField -> {
            Map<String, Object> env = Maps.newHashMap();
            String fieldKey = riskModelField.getFieldKey().toLowerCase();
            if (!isNeedScore(fieldKey, allFormFieldMap)) {
                return;
            }
//            log.info("riskModelFieldId:{},riskModelField.getFieldKey().toLowerCase()-->{}, value:{}", riskModelField.getId(), fieldKey, allFormFieldMap.get(fieldKey));

            String fieldValue = preHandleFormFieldValueIfRequired(fieldKey, allFormFieldMap, project);
            if (StringUtils.isNotBlank(fieldValue)) {
                String expression = transferExpression(fieldKey, fieldValue, riskModelField.getExpression());
                if (StringUtils.isNotBlank(expression)) {
                    try {
                        Expression expressionCompile = AviatorEvaluator.compile(expression);
                        if (NumberUtils.isNumber(fieldValue)) {
                            env.put(fieldKey, Double.valueOf(fieldValue));
                        } else {
                            env.put(fieldKey, fieldValue);
                        }
                        log.info("project[{}] group[{}]risk model field expression:{}, env:{}", project.getProjectNo(), modelGroup.getGroupName(), expression, env);
                        Boolean result = (Boolean) expressionCompile.execute(env);
                        log.info("project[{}] group[{}]risk model field expression:{}, env:{}, result:{}", project.getProjectNo(), modelGroup.getGroupName(), expression, env, result);
                        fieldResultMap.put(riskModelField.getFieldKey() + "_" + riskModelField.getId(), result);
                        env.clear();
                    } catch (Exception e) {
                        log.error("", e);
                    }
                }
            } else {
                fieldResultMap.put(riskModelField.getFieldKey() + "_" + riskModelField.getId(), Boolean.FALSE);
            }
        });
        if (MapUtils.isNotEmpty(fieldResultMap)) {
            if (fieldResultMap.size() == 1) {
                String key = fieldResultMap.keySet().iterator().next();
                return (boolean) fieldResultMap.get(key);
            } else {
                StringBuilder expressionBuilder = new StringBuilder();
                fieldResultMap.forEach((key, value) -> {
                    expressionBuilder.append(key).append(" && ");
                });
                expressionBuilder.append(" 1 == 1 ");
                Boolean result = (Boolean) AviatorEvaluator.compile(expressionBuilder.toString()).execute(fieldResultMap);
                log.info("final project[{}] group[{}] field result expression:{}, env:{}, result:{}", project.getProjectNo(), modelGroup.getGroupName(), expressionBuilder.toString(), fieldResultMap, result);
                return result;
            }
        }
        return Boolean.FALSE;
    }

    /**
     * 判断该field是否需要特殊处理,如果返回null，说明该filed直接continue
     *
     * @param policyFieldKey
     * @param form
     * @return
     */
    private String preHandleFormFieldValueIfRequired(String policyFieldKey, Map<String, String> form, Project project) {
        //判断field key是不是需要换算成月份的
        if (DATE_VALID_FIELD_KEYS.containsKey(policyFieldKey)) {
            String datePolicyFieldKey = DATE_VALID_FIELD_KEYS.get(policyFieldKey);
            String datePolicyFieldValue = form.get(datePolicyFieldKey);
            if (org.apache.commons.lang3.StringUtils.isBlank(datePolicyFieldValue)) {
                return null;
            }

            LocalDate date;
            Date applyDate = project.getApplyDate();
            Instant instant = applyDate.toInstant();
            ZoneId zoneId = ZoneId.systemDefault();
            if (datePolicyFieldValue.indexOf(" ") > 0) {
                date = LocalDateTime.parse(datePolicyFieldValue, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")).toLocalDate();
            } else {
                date = LocalDate.parse(datePolicyFieldValue, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            }
            return String.valueOf(DateUtil.betweenMonth(date, instant.atZone(zoneId).toLocalDate()));

        } else if (B037.equalsIgnoreCase(policyFieldKey)) {//本异地: 本地：居住城市=户籍所在城市；异地：居住城市≠户籍所在城市
            //现家庭地址
            String liveCityValue = form.get(B022);
            //个人信息-户籍地址
            String registeredPermanentCity = form.get(B010);
            log.info("B022:{},b010:{}", liveCityValue, registeredPermanentCity);
            //如果其中一个为空, 则忽略
            if (org.apache.commons.lang3.StringUtils.isBlank(liveCityValue) || org.apache.commons.lang3.StringUtils.isBlank(registeredPermanentCity)) {
                return null;
            }
            if (compareCity(liveCityValue, registeredPermanentCity)) {
                return "本地";
            }
            return "异地";
        } else if (B038.equalsIgnoreCase(policyFieldKey)) {
            Integer createUserId = project.getCreateUserId();
            Member member = memberService.getById(createUserId);
            Org org = orgService.getById(member.getOrgId());
            //现家庭地址
            String liveCityValue = form.get(B022);
            //分公司所在城市
            String registeredPermanentCity = org.getProvince() + " " + org.getCity();
            //如果其中一个为空, 则忽略
            log.info("B022:{},registeredPermanentCity:{}", liveCityValue, registeredPermanentCity);
            if (StringUtils.isBlank(liveCityValue) || StringUtils.isBlank(registeredPermanentCity)) {
                return null;
            }
            if (compareCity(liveCityValue, registeredPermanentCity)) {
                return "网内";
            }
            return "网外";
        } else if (K0022.equalsIgnoreCase(policyFieldKey)) {
            //社保缴纳基数
            String socialSecurityBase = form.get(K005);
            //公积金缴纳基数
            String accumulationFundBase = form.get(K006);
            //如果其中一个为空, 则忽略
            if (org.apache.commons.lang3.StringUtils.isBlank(socialSecurityBase) && org.apache.commons.lang3.StringUtils.isBlank(accumulationFundBase)) {
                return null;
            }
            if (org.apache.commons.lang3.StringUtils.isBlank(socialSecurityBase)){
                return accumulationFundBase;
            }
            if (org.apache.commons.lang3.StringUtils.isBlank(accumulationFundBase)){
                return socialSecurityBase;
            }
            return String.valueOf(Math.max(Double.valueOf(socialSecurityBase), Double.valueOf(accumulationFundBase)));
        }
        if (needTranslateFieldKeys.contains(policyFieldKey)) {
            return translateValueMap.get(policyFieldKey).get(form.get(policyFieldKey));
        }

        return form.get(policyFieldKey);
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

    private static String transferExpression(String fieldName, String fieldValue, String expressStr) {

        if (StringUtils.isEmpty(expressStr)) {
            return null;
        }
        try {
            StringBuilder sb = new StringBuilder();
            expressStr = expressStr
                    .replaceAll("！", "!")
                    .replaceAll("（", "(")
                    .replaceAll("）", ")")
                    .replaceAll("，", ",")
                    .replaceAll("【", "[")
                    .replaceAll("】", "]");
            log.info("EXPRESS " + expressStr);
            if (expressStr.startsWith("[")) {
                //是否没有前面的数字，默认没有
                boolean haveFirst = false;
                String realValue = expressStr.substring(1, expressStr.length() - 1);
                String[] valueArr = realValue.split(",");
                if (!"-".equals(valueArr[0])) {
                    sb.append(valueArr[0]).append("<=").append(fieldName);
                    haveFirst = true;
                }
                if (!"+".equals(valueArr[1])) {
                    if (haveFirst) {
                        sb.append(" && ");
                    }
                    sb.append(fieldName);
                    if (expressStr.endsWith("]")) {
                        sb.append("<=").append(valueArr[1]);
                    } else {
                        sb.append("<").append(valueArr[1]);
                    }
                }
            } else if (expressStr.startsWith("(")) {
                String realValue = expressStr.substring(1, expressStr.length() - 1);
                String[] valueArr = realValue.split(",");
                boolean haveFirst = false;
                if (!"-".equals(valueArr[0])) {
                    sb.append(valueArr[0]).append("<").append(fieldName);
                    haveFirst = true;
                }
                if (!"+".equals(valueArr[1])) {
                    if (haveFirst) {
                        sb.append(" && ");
                    }
                    sb.append(fieldName);
                    if (expressStr.endsWith("]")) {
                        sb.append("<= ").append(valueArr[1]);
                    } else {
                        sb.append("< ").append(valueArr[1]);
                    }
                }
            } else if (expressStr.startsWith("!{")) {//不包含
                String realValue = expressStr.substring(2, expressStr.length() - 1);
                String[] valueArr = realValue.split(",");
                sb.append("(");
                for (int i = 0; i < valueArr.length; i++) {
                    if (NumberUtils.isNumber(fieldValue) && NumberUtils.isNumber(valueArr[i])) {
                        sb.append(fieldName).append("!=").append(valueArr[i]);
                    } else {
                        sb.append("!string.contains(").append(fieldName).append(",'").append(valueArr[i]).append("') ");
                    }
                    ;
                    if (i < valueArr.length - 1) {
                        sb.append(" && ");
                    }
                }
                sb.append(")");
            } else {//绝对等于
                String realValue = expressStr;

                if (fieldValue.startsWith("[")){
                    String[] split = fieldValue.replaceAll("\\[", "").replaceAll("]", "").replaceAll(" ","").split(",");
                   boolean isTrue=true;
                    for (int i = 0; i < split.length; i++) {
                        if (!realValue.contains(split[i])){
                            sb.append("string.contains('").append(expressStr).append("',").append(fieldName).append(") ");
                            isTrue=false;
                            break;
                        }
                    }
                    if (isTrue==true){
                        sb.append("string.contains('").append(fieldValue).append("',").append(fieldName).append(") ");
//                        sb.append("1==1");
                    }
                    //[配偶,父母]处理这种数组型字段
//                    sb.append("string.contains('").append(expressStr).append("',").append(fieldName).append(") ");
                }else {
                    String[] valueArr = realValue.split(",");
                    sb.append("(");
                    for (int i = 0; i < valueArr.length; i++) {
                        if (NumberUtils.isNumber(valueArr[i]) && NumberUtils.isNumber(fieldValue)) {
                            sb.append(fieldName).append("==").append(Double.valueOf(valueArr[i]));
                        } else {
                            sb.append(fieldName).append("=='").append(String.valueOf(valueArr[i])).append("'");
                        }
                        if (i < valueArr.length - 1) {
                            sb.append(" || ");
                        }
                    }
                    sb.append(")");
                }
            }
            return sb.toString();
        } catch (Exception e) {
            log.error("transferExpression error.fieldName:" + fieldName + ",expressStr:" + expressStr, e);
            return null;
        }
    }

    public static Map<String, String> convertMapKey2Lower(Map<String, String> oldMap) {
        Map<String, String> newMap = new HashMap<>();
        Iterator<String> iterator = oldMap.keySet().iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            if (StringUtils.isNotBlank(key) && oldMap.get(key) != null) {
                newMap.put(key.toLowerCase(), String.valueOf(oldMap.get(key)));
            }
        }
        return newMap;
    }

}
