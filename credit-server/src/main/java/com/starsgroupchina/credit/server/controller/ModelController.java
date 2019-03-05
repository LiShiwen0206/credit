package com.starsgroupchina.credit.server.controller;

import com.google.common.collect.Lists;
import com.starsgroupchina.common.objects.If;
import com.starsgroupchina.common.response.ListResponse;
import com.starsgroupchina.common.response.SimpleResponse;
import com.starsgroupchina.credit.bean.extend.ModelExtend;
import com.starsgroupchina.credit.bean.model.*;
import com.starsgroupchina.credit.server.service.ModelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

/**
 * Created by zhangfeng on 2018-5-8.
 */
@Slf4j
@RestController
@Api(tags = "CREDIT-SWAGGER35", description = "风控模型管理 - ModelController")
@RequestMapping(value = "/model", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class ModelController {

    @Autowired
    private ModelService modelService;
    @Autowired
    private ModelService.ModelFieldService fieldService;
    @Autowired
    private ModelService.ModelGroupService groupService;

    @ApiOperation("风控模型列表")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ListResponse<RiskModel> query(@RequestParam(value = "index", defaultValue = "1") int index,
                                         @RequestParam(value = "limit", defaultValue = "20") int limit,
                                         @RequestParam(value = "status", required = false) List<Short> status,
                                         @ApiParam("类型0、逻辑回归，1、决策树") @RequestParam(value = "type", required = false) String type,
                                         @ApiParam("模型名称") @RequestParam(value = "name", required = false) String name) {

        RiskModelExample example = new RiskModelExample();
        RiskModelExample.Criteria criteria = example.createCriteria();
        If.of(StringUtils.isNotEmpty(type)).isTrue(() -> criteria.andTypeEqualTo(Short.valueOf(type)));
        If.of(!CollectionUtils.isEmpty(status)).isTrue(() -> criteria.andStatusIn(status));
        If.of(!StringUtils.isEmpty(name)).isTrue(() -> criteria.andModelNameLike("%" + name.trim() + "%"));

        long count = modelService.count(example);

        example.setOrderByClause("create_time desc");
        example.setOffset((index - 1) * limit);
        example.setLimit(limit);
        List<RiskModel> result = modelService.query(example).collect(toList());

        return ListResponse.success(result, count, index, limit);
    }

    @ApiOperation("根据模型ID获取模型")
    @GetMapping("/{modelId}")
    public SimpleResponse<ModelExtend> get(@ApiParam("模型ID") @PathVariable("modelId") Integer modelId) {
        RiskModel model = modelService.getById(modelId);
        ModelExtend result = new ModelExtend(model);
        result.setGroups(Lists.newArrayList());

        List<RiskModelGroup> groups = groupService.getGroupByModelId(modelId);
        groups.forEach(group -> {
            ModelExtend.ModelGroupExtend groupExtend = new ModelExtend.ModelGroupExtend(group);
            List<RiskModelField> fields = fieldService.getFieldByGroupId(group.getId());
            groupExtend.setFields(fields);
            result.getGroups().add(groupExtend);
        });
        return SimpleResponse.success(result);
    }

    @ApiOperation("创建模型")
    @PostMapping("")
    public SimpleResponse<ModelExtend> create(@RequestBody ModelExtend modelExtend) {
        RiskModel model = modelService.create(modelExtend);
        modelExtend.getGroups().forEach(modelGroup -> {
            modelGroup.setModelId(model.getId());
            RiskModelGroup group = groupService.create(modelGroup);
            List<RiskModelField> fields = modelGroup.getFields().stream()
                    .peek(field -> field.setGroupId(group.getId()))
                    .peek(field -> field.setModelId(model.getId()))
                    .peek(riskModelField -> riskModelField.setExpression(
                            (riskModelField.getExpression() + "")
                                    .replaceAll("！", "!")
                                    .replaceAll("（", "(")
                                    .replaceAll("）", ")")
                                    .replaceAll("，", ",")
                                    .replaceAll("【", "[")
                                    .replaceAll("】", "]")))
                    .collect(Collectors.toList());
            fieldService.create(fields);
        });
        return SimpleResponse.success(modelExtend);
    }

    @ApiOperation("修改模型")
    @RequestMapping(value = "", method = RequestMethod.PUT)
    public SimpleResponse<ModelExtend> update(@RequestBody ModelExtend modelExtend) {
        modelService.update(modelExtend);

        if(modelExtend.getGroups()==null)
            return SimpleResponse.success(modelExtend);

        groupService.deleteByModelId(modelExtend.getId());
        fieldService.deleteByModelId(modelExtend.getId());
        modelExtend.getGroups().forEach(modelGroup -> {
            modelGroup.setModelId(modelExtend.getId());
            RiskModelGroup group = groupService.create(modelGroup);
            List<RiskModelField> fields = modelGroup.getFields().stream()
                    .peek(field -> field.setGroupId(group.getId()))
                    .peek(field -> field.setModelId(modelExtend.getId()))
                    .peek(riskModelField -> riskModelField.setExpression(
                            (riskModelField.getExpression() + "")
                                    .replaceAll("！", "!")
                                    .replaceAll("（", "(")
                                    .replaceAll("）", ")")
                                    .replaceAll("，", ",")
                                    .replaceAll("【", "[")
                                    .replaceAll("】", "]")))
                    .collect(Collectors.toList());
            fieldService.create(fields);
        });
        return SimpleResponse.success(modelExtend);
    }


    @ApiOperation("根据modleId 获取 field")
    @GetMapping("/field/{policyId}")
    public SimpleResponse<List<RiskModelField>> getFields(
            @ApiParam("政策ID") @PathVariable("policyId") Integer modelId,
            @ApiParam("字段类型:产品PRODUCT,用户 USER,车辆 CAR") @RequestParam(value = "type", required = false) String type) {
        RiskModelFieldExample example = new RiskModelFieldExample();
        example.createCriteria().andModelIdEqualTo(modelId).andItemTypeEqualTo(type);
        Stream<RiskModelField> fieldStream = fieldService.query(example);
        return SimpleResponse.success(fieldStream.collect(Collectors.toList()));
    }

//    /**
//     * 批量创建 模型 field
//     */
//    @ApiOperation("批量创建 模型 field")
//    @RequestMapping(value = "/field", method = RequestMethod.POST)
//    public SimpleResponse<List<RiskModelField>> create(@RequestBody List<RiskModelField> fields) {
//        fields.stream().peek(riskModelField ->
//                riskModelField.setExpression(
//                        (riskModelField.getExpression() + "")
//                                .replace("！", "!")
//                                .replace("，", ",")
//                                .replace("【", "[")
//                                .replace("】", "]"))
//        ).collect(toList());
//        List<RiskModelField> response = fieldService.create(fields);
//        return SimpleResponse.success(response);
//    }

//    /**
//     * 根据模型明细ID 删除field
//     */
//    @ApiOperation("根据模型明细ID 删除field")
//    @RequestMapping(value = "/field/{fieldId}", method = RequestMethod.DELETE)
//    public SimpleResponse deleteField(@PathVariable("fieldId") Integer fieldId) {
//        fieldService.getById(fieldId);
//        return SimpleResponse.success();
//    }

//    /**
//     * 批量更新 模型 field
//     */
//    @ApiOperation("修改 field")
//    @RequestMapping(value = "/field", method = RequestMethod.PUT)
//    public SimpleResponse<List<RiskModelField>> updateField(
//            @ApiParam("模型id") @RequestParam("modelId") Integer modelId,
//            @ApiParam("字段类型:产品PRODUCT,用户 USER,车辆 CAR") @RequestParam("type") String type,
//            @RequestBody List<RiskModelField> fields) {
//        RiskModelFieldExample example = new RiskModelFieldExample();
//        example.createCriteria()
//                .andModelIdEqualTo(modelId)
//                .andItemTypeEqualTo(type);
//        fieldService.deleteByExample(example);
//        if (CollectionUtils.isEmpty(fields))
//            return SimpleResponse.success();
//        fields.stream().peek(
//                riskModelField -> riskModelField.setExpression(
//                        (riskModelField.getExpression() + "")
//                                .replace("！", "!")
//                                .replace("，", ",")
//                                .replace("【", "[")
//                                .replace("】", "]"))
//        ).collect(toList());
//        fieldService.create(fields);
//        return SimpleResponse.success(fields);
//    }
}


//        表达式类型：
//        1 ，闭区间“[ ]” : 例如  “[2,5]” 等同于 “2≤x≤5”
//        2 ，开区间“( )” : 例如  “(2,5)” 等同于 “2＜x＜5”
//        3 ，半开半闭区间  : 例如  “2≤x<5” 等同于 “[2,5)”  ，“(2,5]” 等同于 “2＜x≤5”
//        4 ，大于 或 小于  : 例如  “(-,5]”等同于“x≤5” ，“（2,+）”等同于“2<x”
//        5 ，包含“={}”   : 例如  “={上海，北京，深圳}”  等同于 “ 包含：上海，北京，深圳”
//        6 ，不包含“!={}”: 例如  “!={上海，北京，深圳}”  等同于“不包含：上海，北京，深圳”
