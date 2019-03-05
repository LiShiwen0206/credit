package com.starsgroupchina.credit.server.service;

import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.googlecode.aviator.AviatorEvaluator;
import com.googlecode.aviator.Expression;
import com.starsgroupchina.common.base.AbstractService;
import com.starsgroupchina.common.context.ContextHolder;
import com.starsgroupchina.common.objects.Tuple;
import com.starsgroupchina.common.utils.JsonUtil;
import com.starsgroupchina.credit.bean.AuthMember;
import com.starsgroupchina.credit.bean.model.*;
import com.starsgroupchina.credit.server.utils.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

/**
 * Created by zhangfeng on 2018-5-8.
 */
@Slf4j
@Service
public class PolicyService extends AbstractService<Policy, PolicyExample> {

    @Autowired
    private PolicyService.PolicyGroupService policyGroupService;

    @Autowired
    private PolicyService.PolicyFieldService policyFieldService;

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

    public Tuple<Boolean, Map<PolicyGroup, List<PolicyField>>> validByPolicyGroup(Map<String, String> form, Integer policyId, Project project) {

        Map<PolicyGroup, List<PolicyField>> finalIllegalPolicyGroupFieldMap = Maps.newHashMap();
        //政策
        Policy policy = getById(policyId);
        String policyExpression = policy.getLogicExpression();

        if (StringUtils.isBlank(policyExpression)) {
            return new Tuple(Boolean.TRUE, Collections.emptyMap());
        }
        policyExpression = policyExpression.replaceAll("（", "(").replaceAll("）", ")").replaceAll("或", "||").replaceAll("且", "&&");
        if (policyExpression.endsWith("&&")) {
            policyExpression = policyExpression.substring(0, policyExpression.lastIndexOf("&&"));
        }
        if (policyExpression.endsWith("||")) {
            policyExpression = policyExpression.substring(0, policyExpression.lastIndexOf("||"));
        }
        Map<String, Object> tempPolicyGroupExpressionEnv = Maps.newHashMap();
        Map<String, Object> policyGroupExpressionEnv = Maps.newHashMap();

        Set<String> groupNames = Sets.newHashSet();
        //根据policyId查询出当前policyId对应的组，一对多，同时组与组之间是有表达式且或者或的关系。
        Optional.ofNullable(policyGroupService.getGroupByPolicyId(policyId)).ifPresent(policyGroups -> {
            policyGroups.forEach(policyGroup -> {
                groupNames.add(policyGroup.getGroupName());
                //根据组查询出当前组下字段，组和政策字段存在一对多的关系，针对政策下的各个字段进行校验，同时将校验结果放入到以上定义的两个map中
                Optional.ofNullable(policyFieldService.getFieldByGroupId(policyGroup.getId())).ifPresent(policyFields -> {
                    Pair<Boolean, Map<PolicyGroup, List<PolicyField>>> validPair = this.validGroupFields(form, policyGroup, policyFields, project);
                    tempPolicyGroupExpressionEnv.put(policyGroup.getGroupName(), validPair.getLeft());
                    finalIllegalPolicyGroupFieldMap.putAll(validPair.getRight());
                });
            });
        });

        boolean result = Boolean.FALSE;

        if (MapUtils.isNotEmpty(tempPolicyGroupExpressionEnv)) {
            List<String> expressParamNames = extractExpressParamNames(policyExpression);
            expressParamNames.forEach(paramName -> {
                Object groupResult = tempPolicyGroupExpressionEnv.get(paramName);
                if (groupResult != null) {
                    policyGroupExpressionEnv.put(paramName, groupResult);
                } else {
                    policyGroupExpressionEnv.put(paramName, Boolean.TRUE);
                }
            });

            log.info("final expression:{}, env:{}", policyExpression, policyGroupExpressionEnv);
            try {
                Expression compile = AviatorEvaluator.compile(policyExpression);
                result = (Boolean) compile.execute(policyGroupExpressionEnv);
            } catch (Exception e) {
                log.error("policy validate faild.", e);
                result = Boolean.FALSE;
            }
            log.info("policy validate:{}", result);
        }

        return new Tuple(result, finalIllegalPolicyGroupFieldMap);
    }

    private List<String> extractExpressParamNames(String policyExpression) {
        String str = policyExpression.replaceAll("\\(|\\)", "");
        List<String> paramNames = Lists.newArrayList();
        String[] ands = str.split("&&");
        for (String and : ands) {
            if (StringUtils.isNotBlank(and)) {
                String[] ors = and.split("\\|\\|");
                for (String or : ors) {
                    if (StringUtils.isNotBlank(or)) {
                        paramNames.add(or.trim());
                    }
                }
            }
        }
        return paramNames;
    }

    /**
     * 校验每个group下面的fields
     *
     * @param form
     * @param policyGroup
     * @param policies
     * @return
     */
    private Pair<Boolean, Map<PolicyGroup, List<PolicyField>>> validGroupFields(Map<String, String> form, PolicyGroup policyGroup, List<PolicyField> policies, Project project) {
        //将filed中的filedKey全部转换为小写并进行分组
        Map<String, List<PolicyField>> group = policies.stream().collect(Collectors.groupingBy(e -> e.getFieldKey().toLowerCase()));
        Map<PolicyGroup, List<PolicyField>> illegalPolicyFieldMap = Maps.newHashMap();
        Map<String, Object> env = Maps.newHashMap();
        //获得组的间隔符
        String groupLogicExpression = policyGroup.getLogic().replaceAll("且", "&&").replaceAll("或", "||");
        List<String> expressionLists = Lists.newArrayList();
        List<PolicyField> errorList = Lists.newArrayList();
        //遍历每个form项与对应的政策filed校验
        group.forEach((key, value) -> {
            List<String> otherStatments = Lists.newArrayList();
            List<String> equalsStatments = Lists.newArrayList();
//            //其他
//           List<StringBuilder> otherStatments = Lists.newArrayList();
//            //等于
//            List<StringBuilder> equalsStatments = Lists.newArrayList();
            //处理需要经过特殊处理的字段，从表单中获取当前key填入的值
            String projectFormValue = preHandleFormFieldValueIfRequired(key, form, project);

            //如果form中存在该field
            if (StringUtils.isNotBlank(projectFormValue) && StringUtils.isNotBlank(projectFormValue.replaceAll("\\[", "").replaceAll("]", "").trim())) {
                if (projectFormValue.trim().startsWith("[") && projectFormValue.trim().endsWith("]")) {
                    Optional.ofNullable(value).ifPresent(policyFields -> {
                        String operator = transferOperator(policyFields.get(0).getOperator());
                        List<String> result = value.stream().map(policyField -> policyField.getFieldValue()).collect(toList());
                        String[] arrayItems = projectFormValue.replaceAll("\\[", "").replaceAll("]", "").replaceAll(" ","").trim().split(",");
                        if (operator.equals("==")||operator.equals("yc")) {
                            boolean isTrue = true;
                            for (int i = 0; i < arrayItems.length; i++) {
                                if (!result.contains(arrayItems[i])) {
                                    isTrue = false;
                                    break;
                                }
                            }
                            log.info("进件编号:{},group:{},数组判断值{},规则{},operator:{}",project.getProjectNo(), policyGroup.getGroupName(), projectFormValue, result.toString(),operator);
                            otherStatments.add(isTrue + "");
                        }else {
                            boolean isTrue = true;
                            for (int i = 0; i < arrayItems.length; i++) {
                                if (result.contains(arrayItems[i])) {
                                    isTrue = false;
                                    break;
                                }
                            }
                            log.info("进件编号:{},group:{},数组判断值{},规则{},operator:{}",project.getProjectNo(), policyGroup.getGroupName(), projectFormValue, result.toString(),operator);
                            otherStatments.add(isTrue + "");
                        }
                    });

                }else {
                    Optional.ofNullable(value).ifPresent(policyFields -> policyFields.forEach(policyField -> {
                        String operator = transferOperator(policyField.getOperator());
                        String projectFormValuetrimed = projectFormValue.trim();
                        //处理数组，保存格式为[a,b,c,d]
                        if (projectFormValuetrimed.startsWith("[") && projectFormValuetrimed.endsWith("]")) {
                            String policyKey = "policyFieldValue" + policyField.getId();
                            env.put(policyKey, policyField.getFieldValue());
                            String arrayProjectFormValue = projectFormValuetrimed.replaceAll("\\[", "").replaceAll("]", "");
                            String[] arrayItems = arrayProjectFormValue.split(",");
                            StringBuilder sb = new StringBuilder();
                            for (int i = 0; i < arrayItems.length; i++) {
                                generateExpressByOperator(sb, operator, policyKey, arrayItems[i].trim(), true);
                                if (i < arrayItems.length - 1) {
                                    //"否"的意思，逻辑运算符均为&&
                                    if (operator.equalsIgnoreCase("!=") || operator.equalsIgnoreCase("sw") ||
                                            operator.equalsIgnoreCase("ew") || operator.equalsIgnoreCase("nc") ||
                                            operator.equalsIgnoreCase("fe") || operator.equalsIgnoreCase("le") ||
                                            operator.equalsIgnoreCase("yc")) {
                                        sb.append(" && ");
                                    } else {
                                        sb.append(" || ");
                                    }
                                }
                            }

                            Boolean checkResult = checkFiled(sb, env);
                            if (!checkResult) {
                                errorList.add(policyField);
                            }
//                        if ("==".equals(operator) || "fe".equals(operator) || "le".equals(operator) || "yc".equals(operator)) {
//                            equalsStatments.add(checkResult + "");
//                        } else {
                            otherStatments.add(checkResult + "");
//                        }
                        } else {
                            if (StringUtils.isNotBlank(operator)) {
                                //是否校验表达式，针对配置为数字，而填写字符串做特殊处理
                                boolean isCheckFiled = true;
                                String formKey = "projectFormValue" + policyField.getId();
                                String policyKey = "policyFieldValue" + policyField.getId();
                                StringBuilder sb = new StringBuilder();
                                generateExpressByOperator(sb, operator, formKey, policyKey, false);
                                //针对身份证号特殊处理
                                if ("sw".equalsIgnoreCase(operator) || "fe".equalsIgnoreCase(operator)) {
                                    env.put(formKey, projectFormValuetrimed);
                                    env.put(policyKey, policyField.getFieldValue());
                                } else if ("ew".equalsIgnoreCase(operator) || "le".equalsIgnoreCase(operator)) {
                                    env.put(formKey, projectFormValuetrimed);
                                    env.put(policyKey, policyField.getFieldValue());
                                } else if ("nc".equalsIgnoreCase(operator) || "yc".equalsIgnoreCase(operator)) {
                                    env.put(formKey, projectFormValuetrimed);
                                    env.put(policyKey, policyField.getFieldValue());
                                } else {//如果不是前面3种情况，其他的如果是数字，就转为double，否则转为string
                                    if (NumberUtils.isNumber(policyField.getFieldValue())) {
                                        if (NumberUtils.isNumber(projectFormValuetrimed)) {
                                            env.put(formKey, Double.valueOf(projectFormValuetrimed));
                                            env.put(policyKey, Double.valueOf(policyField.getFieldValue()));
                                        } else {
                                            isCheckFiled = false;
                                            errorList.add(policyField);
                                            if ("==".equals(operator)) {
                                                equalsStatments.add(false + "");
                                            } else {
                                                otherStatments.add(false + "");
                                            }
                                        }
                                    } else {
                                        env.put(formKey, "'" + projectFormValuetrimed + "'");
                                        env.put(policyKey, "'" + policyField.getFieldValue() + "'");
                                    }
                                }
//                            if ("==".equals(operator)) {
//                                equalsStatments.add(sb);
//                            } else {
//                                otherStatments.add(sb);
//                            }
                                if (isCheckFiled) {
                                    Boolean checkResult = checkFiled(sb, env);
                                    if (!checkResult) {
                                        errorList.add(policyField);
                                    }
                                    if ("==".equals(operator) || "fe".equals(operator) || "le".equals(operator) || "yc".equals(operator)) {
                                        equalsStatments.add(checkResult + "");
                                    } else {
                                        otherStatments.add(checkResult + "");
                                    }
                                }
                                log.info("field express:  {}, env:{}", sb.toString(), env);
                            }
                        }

                    }));
                }
                //拼接||操作符的表达式,目前只要数组数据
                StringBuilder express = new StringBuilder();
                //拼接非==操作符的表达式
                if (CollectionUtils.isNotEmpty(otherStatments)) {
                    express.append("(");
                    for (int i = 0; i < otherStatments.size(); i++) {
                        express.append(otherStatments.get(i));
                        if (i < otherStatments.size() - 1) {
                            express.append(" && ");
                        }
                    }
                    express.append(")");
                }

                if (CollectionUtils.isNotEmpty(equalsStatments)) {
                    //如果前面有非===操作符的，并且有==操作符的，  两部分需要用||连接
                    if (express.length() > 0) {
                        express.append(" || ");
                    }
                    express.append("(");
                    for (int i = 0; i < equalsStatments.size(); i++) {
                        express.append(equalsStatments.get(i));
                        if (i < equalsStatments.size() - 1) {
                            express.append(" || ");
                        }
                    }
                    express.append(")");
                }

                if (express.length() > 0) {
                    expressionLists.add(express.toString());
                }
            } else {//如果政策配置了，但表单没配，为false
                expressionLists.add(" 1 != 1 ");
                errorList.addAll(value);
            }
        });
        Boolean result = Boolean.TRUE;
        if (CollectionUtils.isNotEmpty(expressionLists)) {
            StringBuilder expressionBuilder = new StringBuilder();
            for (int i = 0; i < expressionLists.size(); i++) {
                expressionBuilder.append("(").append(expressionLists.get(i)).append(")");
                if (i < expressionLists.size() - 1) {
                    expressionBuilder.append(groupLogicExpression);
                }
            }

            try {
                Expression compile = AviatorEvaluator.compile(expressionBuilder.toString());
                result = (Boolean) compile.execute();
//                if (MapUtils.isNotEmpty(env)) {
//                    result = (Boolean) compile.execute(env);
//                } else {
//                    result = (Boolean) compile.execute();
//                }
            } catch (Exception e) {
                log.error("execute express error.", e);
                result = Boolean.FALSE;
            }
            log.info("projectNo:{},group:{}, expression:{}, env:{}， result:{}",project.getProjectNo(), policyGroup.getGroupName(), expressionBuilder.toString(), env.toString(), result);

            if (!result) {
                illegalPolicyFieldMap.put(policyGroup, errorList);
            }
        }
        if (MapUtils.isNotEmpty(illegalPolicyFieldMap)) {
            log.info("projectNo {} policy validGroupFields not approve.groupId:{}, detail:{}",project.getProjectNo(), policyGroup.getGroupName(), JsonUtil.toJson(illegalPolicyFieldMap));
        }
        return Pair.of(result, illegalPolicyFieldMap);
    }

    private Boolean checkFiled(StringBuilder expressionBuilder, Map env) {
        Boolean result = Boolean.FALSE;
        try {
            Expression compile = AviatorEvaluator.compile(expressionBuilder.toString());
            if (MapUtils.isNotEmpty(env)) {
                result = (Boolean) compile.execute(env);
            } else {
                result = (Boolean) compile.execute();
            }
        } catch (Exception e) {
            log.error("execute express error.", e);
        }
        return result;
    }

    private void generateExpressByOperator(StringBuilder sb, String operator, String left, String right, boolean isArray) {

        if (isArray) {
            right = "'" + right + "'";
        }

        if ("!=".equalsIgnoreCase(operator)) {
            sb.append("(").append(left).append(operator).append(right).append(")");
        } else if ("sw".equalsIgnoreCase(operator)) {
            sb.append("!string.startsWith(").append(left).append(",").append(right).append(")");
        } else if ("ew".equalsIgnoreCase(operator)) {
            sb.append("!string.endsWith(").append(left).append(",").append(right).append(")");
        } else if ("nc".equalsIgnoreCase(operator)) {
            sb.append("!string.contains(").append(left).append(",").append(right).append(")");
        } else if ("fe".equalsIgnoreCase(operator)) {
            sb.append("string.startsWith(").append(left).append(",").append(right).append(")");
        } else if ("le".equalsIgnoreCase(operator)) {
            sb.append("string.endsWith(").append(left).append(",").append(right).append(")");
        } else if ("yc".equalsIgnoreCase(operator)) {
            sb.append("string.contains(").append(left).append(",").append(right).append(")");
        } else {
            sb.append(left).append(operator).append(right);
        }
    }

    private boolean compareCity(String left, String right) {
        if (StringUtils.isBlank(left) || StringUtils.isBlank(right)) {
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

    public static void main(String[] args) {
        LocalDate date = LocalDate.parse("2018-03-31", DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalDate current = LocalDate.parse("2018-06-31", DateTimeFormatter.ofPattern("yyyy-MM-dd"));
//        LocalDate current = LocalDate.now();

        System.out.println(DateUtil.betweenMonth(date, current));
        System.out.println(DateUtil.betweenMonth(current, date));
    }

    /**
     * 判断该field是否需要特殊处理,如果返回null，说明该filed直接continue
     *
     * @param policyFieldKey
     * @param form
     * @return
     */
    public String preHandleFormFieldValueIfRequired(String policyFieldKey, Map<String, String> form, Project project) {
        //判断field key是不是需要换算成月份的
        if (DATE_VALID_FIELD_KEYS.containsKey(policyFieldKey)) {
            String datePolicyFieldKey = DATE_VALID_FIELD_KEYS.get(policyFieldKey);
            String datePolicyFieldValue = form.get(datePolicyFieldKey);
            if (StringUtils.isBlank(datePolicyFieldValue)) {
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
            return String.valueOf(DateUtil.betweenMonth(date, LocalDateTime.ofInstant(instant, zoneId).toLocalDate()));

        } else {//本异地: 本地：居住城市=户籍所在城市；异地：居住城市≠户籍所在城市
            if (B037.equalsIgnoreCase(policyFieldKey)) {
                //现家庭地址
                String liveCityValue = form.get(B022);
                //个人信息-户籍地址
                String registeredPermanentCity = form.get(B010);
                log.info("B022:{},b010:{}", liveCityValue, registeredPermanentCity);
                //如果其中一个为空, 则忽略
                if (StringUtils.isBlank(liveCityValue) || StringUtils.isBlank(registeredPermanentCity)) {
                    return null;
                }
                if (compareCity(liveCityValue, registeredPermanentCity)) {
                    return "本地";
                }
                return "异地";
            } else if (B038.equalsIgnoreCase(policyFieldKey)) {
                AuthMember authMember = (AuthMember) ContextHolder.getContext().getData();
                //现家庭地址
                String liveCityValue = form.get(B022);
                //分公司所在城市
                String registeredPermanentCity = authMember.getOrg().getProvince() + " " + authMember.getOrg().getCity();
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
                if (StringUtils.isBlank(socialSecurityBase) && StringUtils.isBlank(accumulationFundBase)) {
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
        }

        return form.get(policyFieldKey);
    }

    private String transferOperator(String operatorName) {
        if (operatorName.trim().equals("等于")) return "==";
        if (operatorName.trim().equals("小于")) return "<";
        if (operatorName.trim().equals("大于")) return ">";
        if (operatorName.trim().equals("小于等于")) return "<=";
        if (operatorName.trim().equals("大于等于")) return ">=";
        if (operatorName.trim().equals("(前)不等于")) return "sw";
        if (operatorName.trim().equals("不包含")) return "nc";
        if (operatorName.trim().equals("(后)不等于")) return "ew";
        if (operatorName.trim().equals("(前)等于")) return "fe";
        if (operatorName.trim().equals("(后)等于")) return "le";
        if (operatorName.trim().equals("包含")) return "yc";
        return "!=";
    }


    @Service
    public static class PolicyFieldService extends AbstractService<PolicyField, PolicyFieldExample> {

        public List<PolicyField> getFieldByGroupId(int groupId) {
            PolicyFieldExample example = new PolicyFieldExample();
            example.createCriteria().andPolicyGroupIdEqualTo(groupId);
            return query(example).collect(toList());
        }

        public void deleteByPolicyId(int policyId) {
            PolicyFieldExample example = new PolicyFieldExample();
            example.createCriteria().andPolicyIdEqualTo(policyId);
            deleteByExample(example);
        }
    }

    @Service
    public static class PolicyGroupService extends AbstractService<PolicyGroup, PolicyGroupExample> {

        public List<PolicyGroup> getGroupByPolicyId(int policyId) {
            PolicyGroupExample example = new PolicyGroupExample();
            example.createCriteria().andPolicyIdEqualTo(policyId);
            return query(example).collect(toList());
        }

        public void deleteByPolicyId(int policyId) {
            PolicyGroupExample example = new PolicyGroupExample();
            example.createCriteria().andPolicyIdEqualTo(policyId);
            deleteByExample(example);
        }
    }

}
