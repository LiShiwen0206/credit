package com.starsgroupchina.credit.server.controller.system;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.starsgroupchina.common.exception.AppException;
import com.starsgroupchina.common.objects.If;
import com.starsgroupchina.common.objects.Tuple;
import com.starsgroupchina.common.response.ListResponse;
import com.starsgroupchina.common.response.SimpleResponse;
import com.starsgroupchina.credit.FormParser;
import com.starsgroupchina.credit.bean.enums.AuditStatus;
import com.starsgroupchina.credit.bean.extend.ThirdDataValidExtend;
import com.starsgroupchina.credit.bean.model.*;
import com.starsgroupchina.credit.key.ErrorCode;
import com.starsgroupchina.credit.server.service.PolicyService;
import com.starsgroupchina.credit.server.service.project.FormService;
import com.starsgroupchina.credit.server.service.project.ProjectService;
import com.starsgroupchina.credit.server.service.third.ThirdDataValidService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

/**
 * Created by zhangfeng on 2018-5-8.
 */
@Slf4j
@RestController
@Api(tags = "CREDIT-SWAGGER46", description = "第三方数据验证 - ThirdDataValidController")
@RequestMapping(value = "/third/valid", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class ThirdDataValidController {

    @Autowired
    private ThirdDataValidService thirdDataValidService;
    @Autowired
    private ThirdDataValidService.ThirdDataValidGroupService thirdDataValidGroupService;
    @Autowired
    private ThirdDataValidService.ThirdDataValidFieldService thirdDataValidFieldService;

    @ApiOperation("三方数据验证列表")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ListResponse<ThirdDataValid> query(@RequestParam(value = "index", defaultValue = "1") int index,
                                              @RequestParam(value = "limit", defaultValue = "20") int limit,
                                              @RequestParam(value = "status", required = false) List<Short> status,
                                              @ApiParam("三方验证类别(前海、同盾)") @RequestParam(value = "validType", required = false) String validType,
                                              @ApiParam("数据验证名称") @RequestParam(value = "name", required = false) String name) {

        ThirdDataValidExample example = new ThirdDataValidExample();
        ThirdDataValidExample.Criteria criteria = example.createCriteria();
        If.of(!CollectionUtils.isEmpty(status)).isTrue(() -> criteria.andStatusIn(status));
        If.of(!StringUtils.isEmpty(name)).isTrue(() -> criteria.andValidNameLike("%" + name.trim() + "%"));
        If.of(!StringUtils.isEmpty(validType)).isTrue(() -> criteria.andValidTypeEqualTo(validType.trim()));
        long count = thirdDataValidService.count(example);
        example.setOrderByClause("create_time desc");
        example.setOffset((index - 1) * limit);
        example.setLimit(limit);
        List<ThirdDataValid> result = thirdDataValidService.query(example).collect(toList());
        return ListResponse.success(result, count, index, limit);
    }

    @ApiOperation("根据三方数据验证ID 查询数据验证")
    @RequestMapping(value = "/{validId}", method = RequestMethod.GET)
    public SimpleResponse<ThirdDataValidExtend> get(@ApiParam("三方数据验证id") @PathVariable("validId") Integer validId) {
        ThirdDataValid thirdDataValid = thirdDataValidService.getById(validId);
        ThirdDataValidExtend result = new ThirdDataValidExtend(thirdDataValid);
        result.setGroups(Lists.newArrayList());
        List<ThirdDataValidGroup> groups = thirdDataValidGroupService.getGroupByValidId(validId);
        groups.forEach(group -> {
            ThirdDataValidExtend.ThirdDataValidGroupExtend groupExtend = new ThirdDataValidExtend.ThirdDataValidGroupExtend(group);
            List<ThirdDataValidField> fields = thirdDataValidFieldService.getFieldByGroupId(group.getId());
            groupExtend.setFields(fields);
            result.getGroups().add(groupExtend);
        });
        return SimpleResponse.success(result);
    }

    @ApiOperation("创建三方条件验证")
    @PostMapping("")
    @Transactional
    public SimpleResponse<ThirdDataValidExtend> create(@RequestBody ThirdDataValidExtend thirdDataValidExtend) {
        long start = System.currentTimeMillis();
        ThirdDataValid thirdDataValid = thirdDataValidService.create(thirdDataValidExtend);
        thirdDataValidExtend.getGroups().forEach(thirdDataValidGroup -> {
            thirdDataValidGroup.setThirdDataValidId(thirdDataValid.getId());
            ThirdDataValidGroup group = thirdDataValidGroupService.create(thirdDataValidGroup);
            List<ThirdDataValidField> fields = thirdDataValidGroup.getFields().stream()
                    .peek(field -> field.setThirdDataValidGroupId(group.getId()))
                    .peek(field -> field.setThirdDataValidId(thirdDataValid.getId()))
                    .collect(Collectors.toList());
            thirdDataValidFieldService.create(fields);
        });
        log.info("创建三方条件验证：{}", System.currentTimeMillis() - start);
        return SimpleResponse.success(thirdDataValidExtend);
    }

    @ApiOperation("修改三方条件验证")
    @PutMapping("")
    @Transactional
    public SimpleResponse<ThirdDataValidExtend> update(@RequestBody ThirdDataValidExtend thirdDataValidExtend) {
        long start = System.currentTimeMillis();
        thirdDataValidService.update(thirdDataValidExtend);
        if (thirdDataValidExtend.getGroups() == null) {
            return SimpleResponse.success(thirdDataValidExtend);
        }
        thirdDataValidGroupService.deleteByValidId(thirdDataValidExtend.getId());
        thirdDataValidFieldService.deleteByValidId(thirdDataValidExtend.getId());
        thirdDataValidExtend.getGroups().forEach(thirdDataValidGroup -> {
            thirdDataValidGroup.setThirdDataValidId(thirdDataValidExtend.getId());
            ThirdDataValidGroup group = thirdDataValidGroupService.create(thirdDataValidGroup);
            List<ThirdDataValidField> fields = thirdDataValidGroup.getFields().stream()
                    .peek(field -> field.setThirdDataValidGroupId(group.getId()))
                    .peek(field -> field.setThirdDataValidId(thirdDataValidExtend.getId()))
                    .collect(Collectors.toList());
            thirdDataValidFieldService.create(fields);
        });
        log.info("修改三方条件验证：{}", System.currentTimeMillis() - start);
        return SimpleResponse.success(thirdDataValidExtend);
    }

    /**
     * 查询政策 field
     */
    @ApiOperation("根据第三方数据验证id 查询验证 field")
    @RequestMapping(value = "/field/{validId}", method = RequestMethod.GET)
    public SimpleResponse<List<ThirdDataValidField>> getFields(
            @ApiParam("第三方数据验证id") @PathVariable("validId") Integer validId,
            @ApiParam("字段类型:产品PRODUCT,用户 USER,车辆 CAR") @RequestParam(value = "type", required = false) String type) {
        ThirdDataValidFieldExample example = new ThirdDataValidFieldExample();
        ThirdDataValidFieldExample.Criteria criteria = example.createCriteria();
        criteria.andThirdDataValidIdEqualTo(validId);
        If.of(StringUtils.isNotEmpty(type)).isTrue(()->criteria.andItemTypeEqualTo(type));
        List<ThirdDataValidField> result = thirdDataValidFieldService.query(example).collect(toList());
        return SimpleResponse.success(result);
    }
}
