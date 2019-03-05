package com.starsgroupchina.credit.server.service.third;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.googlecode.aviator.AviatorEvaluator;
import com.googlecode.aviator.Expression;
import com.starsgroupchina.common.base.AbstractService;
import com.starsgroupchina.common.exception.AppException;
import com.starsgroupchina.common.utils.JsonUtil;
import com.starsgroupchina.credit.bean.enums.ScoreType;
import com.starsgroupchina.credit.bean.extend.ThirdDataValidFieldExtend;
import com.starsgroupchina.credit.bean.model.*;
import com.starsgroupchina.credit.server.service.ProductService;
import com.starsgroupchina.credit.server.service.RiskModelItem;
import com.starsgroupchina.credit.server.service.project.ProjectService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

/**
 * @Author: QinHaoHao
 * @Description:
 * @Date: Created in 16:43 2018/10/18
 * @Modifed By:
 */
@Slf4j
@Service
public class ThirdDataValidService extends AbstractService<ThirdDataValid, ThirdDataValidExample> {

    @Autowired
    @Qualifier("thirdCreditResultService")
    private RiskModelItem thirdCreditResultService;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ThirdDataValidGroupService thirdDataValidGroupService;

    @Autowired
    private ThirdDataValidFieldService thirdDataValidFieldService;

    @Autowired
    private ThirdDataValidFailRecordService thirdDataValidFailRecordService;

    @Autowired
    private ProductService productService;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public boolean checkValidName(String validName){
        ThirdDataValidExample example = new ThirdDataValidExample();
        example.createCriteria().andValidNameEqualTo(validName);
        List<ThirdDataValid> result = this.query(example).collect(toList());
        return result.size()>0;
    }
    public boolean validThirdData(String projectNo) throws AppException{
        final boolean[] isTrue = {true};
        Map<String, String> allFormFieldMap = Maps.newHashMap();
        Project project = projectService.getProjectByProjectNo(projectNo);
        Product product = productService.getById(project.getProductId());
        List<Integer> thirdValidIdList = Lists.newArrayList();
        if (product.getTdValidDataId()!=null&&product.getTdValidDataId()!=-1){
            thirdValidIdList.add(product.getTdValidDataId());
            Optional.ofNullable(thirdCreditResultService.getRiskModelItem(projectNo, ScoreType.THIRD_TD)).ifPresent(formDetail -> allFormFieldMap.putAll(formDetail));
        }
        if (product.getQhValidDataId()!=null&&product.getQhValidDataId()!=-1){
            thirdValidIdList.add(product.getQhValidDataId());
            Optional.ofNullable(thirdCreditResultService.getRiskModelItem(projectNo, ScoreType.THIRD_QH)).ifPresent(formDetail -> allFormFieldMap.putAll(formDetail));
        }
        Map<String, String> allFormField2LowerKeyMap = allFormFieldMap;
        if (MapUtils.isNotEmpty(allFormFieldMap)) {
            allFormField2LowerKeyMap = convertMapKey2Lower(allFormFieldMap);
        }

        //查询产品对应配置
        Map<String, String> finalAllFormField2LowerKeyMap = allFormField2LowerKeyMap;
        thirdDataValidFailRecordService.deleteByProjectNo(projectNo);
        thirdValidIdList.forEach(validId -> {
            ThirdDataValid thirdDataValid = this.getById(validId);
            thirdDataValidGroupService.getGroupByValidId(thirdDataValid.getId()).forEach(thirdDataValidGroup -> {
                Optional.ofNullable(thirdDataValidFieldService.getFieldByGroupId(thirdDataValidGroup.getId())).ifPresent(thirdDataValidFields -> {
                    Pair<Boolean, Map<ThirdDataValidGroup, List<ThirdDataValidFieldExtend>>> validPair = this.validGroupFields(finalAllFormField2LowerKeyMap, thirdDataValidGroup, thirdDataValidFields, project);
                    if (validPair.getLeft() == false) {
                        isTrue[0] =false;
                        saveFailValid(validPair, projectNo, thirdDataValid);
                    }
                });
            });
        });
        return isTrue[0];
    }

    private void saveFailValid(Pair<Boolean, Map<ThirdDataValidGroup, List<ThirdDataValidFieldExtend>>> validPair, String projectNo, ThirdDataValid thirdDataValid) {
        Map<ThirdDataValidGroup, List<ThirdDataValidFieldExtend>> right = validPair.getRight();
        List<ThirdDataValidFailRecord> result = Lists.newArrayList();
        right.forEach((k, v) -> {
            v.forEach(thirdDataValidField -> {
                ThirdDataValidFailRecord thirdDataValidFailRecord = new ThirdDataValidFailRecord();
                thirdDataValidFailRecord.setFieldKey(thirdDataValidField.getFieldKey());
                thirdDataValidFailRecord.setFieldName(thirdDataValidField.getFieldName());
                thirdDataValidFailRecord.setFieldValue(thirdDataValidField.getFieldValue());
                thirdDataValidFailRecord.setGroupName(k.getGroupName());
                thirdDataValidFailRecord.setOperator(thirdDataValidField.getOperator());
                thirdDataValidFailRecord.setProjectNo(projectNo);
                thirdDataValidFailRecord.setFormData(thirdDataValidField.getFormData());
                thirdDataValidFailRecord.setValidType(thirdDataValid.getValidType());
                result.add(thirdDataValidFailRecord);
            });
        });
        thirdDataValidFailRecordService.create(result);
    }

    public static Map<String, String> convertMapKey2Lower(Map<String, String> oldMap) {
        Map<String, String> newMap = new HashMap<>();
        Iterator<String> iterator = oldMap.keySet().iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            if (org.apache.commons.lang.StringUtils.isNotBlank(key) && oldMap.get(key) != null) {
                newMap.put(key.toLowerCase(), String.valueOf(oldMap.get(key)));
            }
        }
        return newMap;
    }

    private Pair<Boolean, Map<ThirdDataValidGroup, List<ThirdDataValidFieldExtend>>> validGroupFields(Map<String, String> form, ThirdDataValidGroup thirdDataValidGroup, List<ThirdDataValidField> thirdDataValidFields, Project project) {
        //将filed中的filedKey全部转换为小写并进行分组
        Map<String, List<ThirdDataValidField>> group = thirdDataValidFields.stream().collect(Collectors.groupingBy(e -> e.getFieldKey().toLowerCase()));
        Map<ThirdDataValidGroup, List<ThirdDataValidFieldExtend>> illegalPolicyFieldMap = Maps.newHashMap();
        Map<String, Object> env = Maps.newHashMap();
        //获得组的间隔符
        String groupLogicExpression = thirdDataValidGroup.getLogic().replaceAll("且", "&&").replaceAll("或", "||");
        List<String> expressionLists = Lists.newArrayList();
        List<ThirdDataValidFieldExtend> errorList = Lists.newArrayList();
        //遍历每个form项与对应的政策filed校验
        group.forEach((key, value) -> {
            List<String> otherStatments = Lists.newArrayList();
            List<String> equalsStatments = Lists.newArrayList();
//            //其他
//           List<StringBuilder> otherStatments = Lists.newArrayList();
//            //等于
//            List<StringBuilder> equalsStatments = Lists.newArrayList();
            String validValue = form.get(key);
            //如果form中存在该field
            if (StringUtils.isNotBlank(validValue) && StringUtils.isNotBlank(validValue.replaceAll("\\[", "").replaceAll("]", "").trim())) {
                Optional.ofNullable(value).ifPresent(validFields -> validFields.forEach(thirdDataValidField -> {
                    String operator = transferOperator(thirdDataValidField.getOperator());
                    String projectFormValuetrimed = validValue.trim();
                        if (StringUtils.isNotBlank(operator)) {
                            //是否校验表达式，针对配置为数字，而填写字符串做特殊处理
                            boolean isCheckFiled = true;
                            String formKey = "validValue" + thirdDataValidField.getId();
                            String policyKey = "policyFieldValue" + thirdDataValidField.getId();
                            StringBuilder sb = new StringBuilder();
                            generateExpressByOperator(sb, operator, formKey, policyKey, false);
                            //针对身份证号特殊处理
                            if ("sw".equalsIgnoreCase(operator) || "fe".equalsIgnoreCase(operator)) {
                                env.put(formKey, projectFormValuetrimed);
                                env.put(policyKey, thirdDataValidField.getFieldValue());
                            } else if ("ew".equalsIgnoreCase(operator) || "le".equalsIgnoreCase(operator)) {
                                env.put(formKey, projectFormValuetrimed);
                                env.put(policyKey, thirdDataValidField.getFieldValue());
                            } else if ("nc".equalsIgnoreCase(operator) || "yc".equalsIgnoreCase(operator)) {
                                env.put(formKey, projectFormValuetrimed);
                                env.put(policyKey, thirdDataValidField.getFieldValue());
                            } else {//如果不是前面3种情况，其他的如果是数字，就转为double，否则转为string
                                if (NumberUtils.isNumber(thirdDataValidField.getFieldValue())) {
                                    if (NumberUtils.isNumber(projectFormValuetrimed)) {
                                        env.put(formKey, Double.valueOf(projectFormValuetrimed));
                                        env.put(policyKey, Double.valueOf(thirdDataValidField.getFieldValue()));
                                    } else {
                                        isCheckFiled = false;
                                        addErrList(thirdDataValidField,errorList,projectFormValuetrimed);
                                        if ("==".equals(operator)) {
                                            equalsStatments.add(false + "");
                                        } else {
                                            otherStatments.add(false + "");
                                        }
                                    }
                                } else {
                                    env.put(formKey, "'" + projectFormValuetrimed + "'");
                                    env.put(policyKey, "'" + thirdDataValidField.getFieldValue() + "'");
                                }
                            }
                            if (isCheckFiled) {
                                Boolean checkResult = checkFiled(sb, env);
                                if (!checkResult) {
                                    addErrList(thirdDataValidField,errorList,projectFormValuetrimed);
                                }
                                ;
                                if ("==".equals(operator) || "fe".equals(operator) || "le".equals(operator) || "yc".equals(operator)) {
                                    equalsStatments.add(checkResult + "");
                                } else {
                                    otherStatments.add(checkResult + "");
                                }
                            }
                            log.info("field express:  {}, env:{}", sb.toString(), env);
                        }
                }));
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
                expressionLists.add(" 1 == 1 ");
//                value.forEach(thirdDataValidField -> {
//                    addErrList(thirdDataValidField,errorList,null);
//                });
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
            log.info("group:{}, expression:{}, env:{}， result:{}", thirdDataValidGroup.getGroupName(), expressionBuilder.toString(), env.toString(), result);

            if (!result) {
                illegalPolicyFieldMap.put(thirdDataValidGroup, errorList);
            }
        }
        if (MapUtils.isNotEmpty(illegalPolicyFieldMap)) {
            log.info("policy validGroupFields not approve.groupId:{}, detail:{}", thirdDataValidGroup.getGroupName(), JsonUtil.toJson(illegalPolicyFieldMap));
        }
        return Pair.of(result, illegalPolicyFieldMap);
    }
     private void addErrList(ThirdDataValidField thirdDataValidField,List<ThirdDataValidFieldExtend> result,String formData){
         ThirdDataValidFieldExtend thirdDataValidFieldExtend = new ThirdDataValidFieldExtend(thirdDataValidField);
         thirdDataValidFieldExtend.setFormData(formData);
         result.add(thirdDataValidFieldExtend);
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

    @Service
    public static class ThirdDataValidGroupService extends AbstractService<ThirdDataValidGroup, ThirdDataValidGroupExample> {
        public List<ThirdDataValidGroup> getGroupByValidId(int validId) {
            ThirdDataValidGroupExample example = new ThirdDataValidGroupExample();
            example.createCriteria().andThirdDataValidIdEqualTo(validId);
            return query(example).collect(toList());
        }

        public void deleteByValidId(int validId) {
            ThirdDataValidGroupExample example = new ThirdDataValidGroupExample();
            example.createCriteria().andThirdDataValidIdEqualTo(validId);
            deleteByExample(example);
        }
    }

    @Service
    public static class ThirdDataValidFieldService extends AbstractService<ThirdDataValidField, ThirdDataValidFieldExample> {
        public List<ThirdDataValidField> getFieldByGroupId(int groupId) {
            ThirdDataValidFieldExample example = new ThirdDataValidFieldExample();
            example.createCriteria().andThirdDataValidGroupIdEqualTo(groupId);
            return query(example).collect(toList());
        }

        public void deleteByValidId(int validId) {
            ThirdDataValidFieldExample example = new ThirdDataValidFieldExample();
            example.createCriteria().andThirdDataValidIdEqualTo(validId);
            deleteByExample(example);
        }
    }

    @Service
    public static class ThirdDataValidFailRecordService extends AbstractService<ThirdDataValidFailRecord, ThirdDataValidFailRecordExample> {

        public void deleteByProjectNo(String projectNo){
            ThirdDataValidFailRecordExample example = new ThirdDataValidFailRecordExample();
            example.createCriteria().andProjectNoEqualTo(projectNo);
            this.deleteByExample(example);
        }
    }
}
