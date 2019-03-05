package com.starsgroupchina.credit.server.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.googlecode.aviator.AviatorEvaluator;
import com.googlecode.aviator.Expression;
import com.starsgroupchina.common.base.AbstractService;
import com.starsgroupchina.common.objects.Tuple;
import com.starsgroupchina.common.utils.JsonUtil;
import com.starsgroupchina.common.utils.Utils;
import com.starsgroupchina.credit.FormParser;
import com.starsgroupchina.credit.bean.enums.FormType;
import com.starsgroupchina.credit.bean.extend.ProjectExtend;
import com.starsgroupchina.credit.bean.model.*;
import com.starsgroupchina.credit.server.utils.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

/**
 * @Author: QinHaoHao
 * @Description:
 * @Date: Created in 11:06 2018/8/21
 * @Modifed By:
 */
@Slf4j
@Service
public class OrgConditionService extends AbstractService<OrgCondition, OrgConditionExample> {


    @Autowired
    private OrgConditionService orgConditionService;

    @Autowired
    private OrgConditionService.OrgConditionGroupService orgConditionGroupService;

    @Autowired
    private OrgConditionService.OrgConditionFieldService orgConditionFieldService;


    @Autowired
    private ProductService.ProductConditionConfigService productConditionConfigService;

    private static  final ObjectMapper objectMapper = new ObjectMapper();



    public String validProject(ProjectExtend projectExtend) {
        log.info("/condition/valid进行条件验证：{}", projectExtend);
        List<ProjectForm> forms = projectExtend.getForms();
        Integer productId = projectExtend.getProductId();
        Integer orgId = projectExtend.getOrgId();
        final ProjectForm[] form = {null};
        forms.forEach(projectForm -> {
            if (projectForm.getFormType().equalsIgnoreCase(FormType.FORM_USER.key())) {
                form[0] = projectForm;
            }
        });
        if (form[0] == null) {
            return null;
        }
        //条件验证通过和不通过
        ProductConditionConfigExample example = new ProductConditionConfigExample();
        example.createCriteria().andProductIdEqualTo(productId).andOrgIdEqualTo(orgId);
        ProductConditionConfig config = Utils.getFirst(productConditionConfigService.query(example));
        if (config == null) {
            return null;
        }
        AtomicReference<Boolean> validResult = new AtomicReference<>(Boolean.FALSE);
        Set<String> invalidOrgConditionFields = Sets.newHashSet();
        Map<String, String> keyValue = FormParser.parse(form[0].getProjectForm());
        Tuple<Boolean, Map<OrgConditionGroup, List<OrgConditionField>>> formValidResult =
                this.validByConditonGroup(keyValue, config.getConditionId(),projectExtend);
        log.info("/condition/valid条件验证结果：{}", formValidResult);
        if (MapUtils.isNotEmpty(formValidResult.snd)) {
            //把验证结果放入到集合中
            formValidResult.snd.forEach(((OrgConditionGroup, orgConditionFields) -> {
                invalidOrgConditionFields.addAll(orgConditionFields.stream().map(OrgConditionField::getFieldName).collect(toList()));
            }));
        }
        if (formValidResult.fst) {
            validResult.set(Boolean.TRUE);
        }
        log.info("条件验证结果为:"+validResult.get());
        if (!validResult.get()) {
            try {
                String s = objectMapper.writeValueAsString(invalidOrgConditionFields);
                return "以下信息不满足机构条件:"+ s;
            } catch (JsonProcessingException e) {
                return "error";
            }
        }
        return null;
    }


    public Tuple<Boolean, Map<OrgConditionGroup, List<OrgConditionField>>> validByConditonGroup(Map<String, String> form, Integer conditionId,ProjectExtend projectExtend) {
        if (conditionId==null){
                return new Tuple(Boolean.TRUE, Collections.emptyMap());
        }
        Map<OrgConditionGroup, List<OrgConditionField>> finalIllegalPolicyGroupFieldMap = Maps.newHashMap();
        OrgCondition orgCondition = orgConditionService.getById(conditionId);

        String conditionExpression = orgCondition.getLogicExpression();

        if (StringUtils.isBlank(conditionExpression)) {
            return new Tuple(Boolean.TRUE, Collections.emptyMap());
        }

        conditionExpression = conditionExpression.replaceAll("（", "(").replaceAll("）", ")").replaceAll("或", "||").replaceAll("且", "&&");

        if(conditionExpression.endsWith("&&")){
            conditionExpression = conditionExpression.substring(0,conditionExpression.lastIndexOf("&&"));
        }
        if (conditionExpression.endsWith("||")){
            conditionExpression = conditionExpression.substring(0,conditionExpression.lastIndexOf("||"));
        }
        Map<String, Object> tempPolicyGroupExpressionEnv = Maps.newHashMap();
        Map<String, Object> policyGroupExpressionEnv = Maps.newHashMap();

        Set<String> groupNames = Sets.newHashSet();
        //根据policyId查询出当前policyId对应的组，一对多，同时组与组之间是有表达式且或者或的关系。
        Optional.ofNullable(orgConditionGroupService.getGroupByConditionId(conditionId)).ifPresent(orgConditionGroups -> {
            orgConditionGroups.forEach(orgConditionGroup -> {
                groupNames.add(orgConditionGroup.getGroupName());
                //根据组查询出当前组下字段，组和政策字段存在一对多的关系，针对政策下的各个字段进行校验，同时将校验结果放入到以上定义的两个map中
                Optional.ofNullable(orgConditionFieldService.getFieldByGroupId(orgConditionGroup.getId())).ifPresent(conditionFields -> {
                    Pair<Boolean, Map<OrgConditionGroup, List<OrgConditionField>>> validPair = this.validGroupFields(form, orgConditionGroup, conditionFields,projectExtend);
                    tempPolicyGroupExpressionEnv.put(orgConditionGroup.getGroupName(), validPair.getLeft());
                    finalIllegalPolicyGroupFieldMap.putAll(validPair.getRight());
                });
            });
        });

        boolean result = Boolean.FALSE;

        if (MapUtils.isNotEmpty(tempPolicyGroupExpressionEnv)) {
            List<String> expressParamNames = extractExpressParamNames(conditionExpression);
            expressParamNames.forEach(paramName -> {
                Object groupResult = tempPolicyGroupExpressionEnv.get(paramName);
                if (groupResult != null) {
                    policyGroupExpressionEnv.put(paramName, groupResult);
                } else {
                    policyGroupExpressionEnv.put(paramName, Boolean.TRUE);
                }
            });

            log.info("condition final expression:{}, env:{}", conditionExpression, policyGroupExpressionEnv);
            try {
                Expression compile = AviatorEvaluator.compile(conditionExpression);
                result = (Boolean) compile.execute(policyGroupExpressionEnv);
            } catch (Exception e) {
                log.error("condition validate faild.", e);
                result = Boolean.FALSE;
            }
            log.info("condition validate:{}", result);
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
     * @param policies
     * @return
     */
    private Pair<Boolean, Map<OrgConditionGroup, List<OrgConditionField>>> validGroupFields(Map<String, String> form, OrgConditionGroup orgConditionGroup, List<OrgConditionField> policies,ProjectExtend projectExtend) {
        //将filed中的filedKey全部转换为小写并进行分组
        Map<String, List<OrgConditionField>> group = policies.stream().collect(Collectors.groupingBy(e -> e.getFieldKey().toLowerCase()));
        Map<OrgConditionGroup, List<OrgConditionField>> illegalPolicyFieldMap = Maps.newHashMap();
        Map<String, Object> env = Maps.newHashMap();
        //获得组的间隔符
        String groupLogicExpression = orgConditionGroup.getLogic().replaceAll("且", "&&").replaceAll("或", "||");
        List<String> expressionLists = Lists.newArrayList();
        List<OrgConditionField> errorList = Lists.newArrayList();
        //遍历每个form项与对应的政策filed校验
        group.forEach((key, value) -> {
            List<String> otherStatments = Lists.newArrayList();
            List<String> equalsStatments = Lists.newArrayList();
            String projectFormValue = form.get(key);
            //如果form中存在该field
            if (StringUtils.isNotBlank(projectFormValue) && StringUtils.isNotBlank(projectFormValue.replaceAll("\\[", "").replaceAll("]", "").trim())) {
                if (projectFormValue.trim().startsWith("[") && projectFormValue.trim().endsWith("]")) {
                    Optional.ofNullable(value).ifPresent(orgConditionFields -> {
                        String operator = transferOperator(orgConditionFields.get(0).getOperator());
                        List<String> result = value.stream().map(orgConditionField -> orgConditionField.getFieldValue()).collect(toList());
                        String[] arrayItems = projectFormValue.replaceAll("\\[", "").replaceAll("]", "").trim().split(",");
                        if (operator.equals("==")||operator.equals("yc")) {
                            boolean isTrue = true;
                            for (int i = 0; i < arrayItems.length; i++) {
                                if (!result.contains(arrayItems[i])) {
                                    isTrue = false;
                                    break;
                                }
                            }
                            log.info("进件编号:{},group:{},数组判断值{},规则{},operator:{}",projectExtend.getProjectNo(), orgConditionGroup.getGroupName(), projectFormValue, result.toString(),operator);
                            otherStatments.add(isTrue + "");
                        }else {
                            boolean isTrue = true;
                            for (int i = 0; i < arrayItems.length; i++) {
                                if (result.contains(arrayItems[i])) {
                                    isTrue = false;
                                    break;
                                }
                            }
                            log.info("进件编号:{},group:{},数组判断值{},规则{},operator:{}",projectExtend.getProjectNo(), orgConditionGroup.getGroupName(), projectFormValue, result.toString(),operator);
                            otherStatments.add(isTrue + "");
                        }
                    });

                }else {
                    Optional.ofNullable(value).ifPresent(orgConditionFields -> orgConditionFields.forEach(orgConditionField -> {
                        String operator = transferOperator(orgConditionField.getOperator());
                        String projectFormValuetrimed = projectFormValue.trim();
                        //处理数组，保存格式为[a,b,c,d]
                        if (projectFormValuetrimed.startsWith("[") && projectFormValuetrimed.endsWith("]")) {
                            String conditionKey = "conditionFieldValue" + orgConditionField.getId();
                            env.put(conditionKey, orgConditionField.getFieldValue());
                            String arrayProjectFormValue = projectFormValuetrimed.replaceAll("\\[", "").replaceAll("]", "");
                            String[] arrayItems = arrayProjectFormValue.split(",");
                            StringBuilder sb = new StringBuilder();
                            for (int i = 0; i < arrayItems.length; i++) {
                                generateExpressByOperator(sb, operator, conditionKey, arrayItems[i].trim(), true);
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
                                errorList.add(orgConditionField);
                            }
                            otherStatments.add(checkResult + "");
                        } else {
                            if (StringUtils.isNotBlank(operator)) {
                                //是否校验表达式，针对配置为数字，而填写字符串做特殊处理
                                boolean isCheckFiled = true;
                                String formKey = "projectFormValue" + orgConditionField.getId();
                                String policyKey = "policyFieldValue" + orgConditionField.getId();
                                StringBuilder sb = new StringBuilder();
                                generateExpressByOperator(sb, operator, formKey, policyKey, false);
                                //针对身份证号特殊处理
                                if ("sw".equalsIgnoreCase(operator) || "fe".equalsIgnoreCase(operator)) {
                                    env.put(formKey, projectFormValuetrimed);
                                    env.put(policyKey, orgConditionField.getFieldValue());
                                } else if ("ew".equalsIgnoreCase(operator) || "le".equalsIgnoreCase(operator)) {
                                    env.put(formKey, projectFormValuetrimed);
                                    env.put(policyKey, orgConditionField.getFieldValue());
                                } else if ("nc".equalsIgnoreCase(operator) || "yc".equalsIgnoreCase(operator)) {
                                    env.put(formKey, projectFormValuetrimed);
                                    env.put(policyKey, orgConditionField.getFieldValue());
                                } else {//如果不是前面3种情况，其他的如果是数字，就转为double，否则转为string
                                    {
                                        env.put(formKey, "'" + projectFormValuetrimed + "'");
                                        env.put(policyKey, "'" + orgConditionField.getFieldValue() + "'");
                                    }
                                }
                                if (isCheckFiled) {
                                    Boolean checkResult = checkFiled(sb, env);
                                    if (!checkResult) {
                                        errorList.add(orgConditionField);
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
            } else {
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
            } catch (Exception e) {
                log.error("execute express error.", e);
                result = Boolean.FALSE;
            }
            log.info("condition projectNo {},group:{}, expression:{}, env:{}， result:{}",projectExtend.getProjectNo(), orgConditionGroup.getGroupName(), expressionBuilder.toString(), env.toString(), result);

            if (!result) {
                illegalPolicyFieldMap.put(orgConditionGroup, errorList);
            }
        }
        if (MapUtils.isNotEmpty(illegalPolicyFieldMap)) {
            log.info("condition projectNo {} validGroupFields not approve.groupId:{}, detail:{}",projectExtend.getProjectNo(), orgConditionGroup.getGroupName(), JsonUtil.toJson(illegalPolicyFieldMap));
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

    /**
     * @Author: QinHaoHao
     * @Description:
     * @Date: Created in 11:06 2018/8/21
     * @Modifed By:
     */
    @Service
    public static class OrgConditionGroupService extends AbstractService<OrgConditionGroup, OrgConditionGroupExample> {
        public List<OrgConditionGroup> getGroupByConditionId(int conditionId) {
            OrgConditionGroupExample example = new OrgConditionGroupExample();
            example.createCriteria().andConditionIdEqualTo(conditionId);
            return query(example).collect(toList());
        }

        public void deleteByConditionId(int conditionId) {
            OrgConditionGroupExample example = new OrgConditionGroupExample();
            example.createCriteria().andConditionIdEqualTo(conditionId);
            deleteByExample(example);
        }
    }

    /**
     * @Author: QinHaoHao
     * @Description:
     * @Date: Created in 11:06 2018/8/21
     * @Modifed By:
     */
    @Service
    public static class OrgConditionFieldService extends AbstractService<OrgConditionField, OrgConditionFieldExample> {

        public List<OrgConditionField> getFieldByGroupId(int groupId) {
            OrgConditionFieldExample example = new OrgConditionFieldExample();
            example.createCriteria().andConditionGroupIdEqualTo(groupId);
            return query(example).collect(toList());
        }

        public void deleteByConditionId(int conditionId) {
            OrgConditionFieldExample example = new OrgConditionFieldExample();
            example.createCriteria().andConditionIdEqualTo(conditionId);
            deleteByExample(example);
        }
    }

}
