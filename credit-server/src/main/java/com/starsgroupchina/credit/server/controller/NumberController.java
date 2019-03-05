package com.starsgroupchina.credit.server.controller;

import com.google.common.collect.Lists;
import com.starsgroupchina.common.objects.If;
import com.starsgroupchina.common.response.ListResponse;
import com.starsgroupchina.common.response.SimpleResponse;
import com.starsgroupchina.credit.bean.extend.NumberRuleExtend;
import com.starsgroupchina.credit.bean.model.NumberRule;
import com.starsgroupchina.credit.bean.model.NumberRuleExample;
import com.starsgroupchina.credit.bean.model.Org;
import com.starsgroupchina.credit.server.service.NumberService;
import com.starsgroupchina.credit.server.service.system.OrgService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * 编号规则
 */
@Slf4j
@RestController
@Api(tags = "CREDIT-SWAGGER36",description = "编号规则 - NumberController" )
@RequestMapping(value = "/number", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class NumberController {

    @Autowired
    private NumberService numberRuleService;

    @Autowired
    private OrgService orgService;
    /**
     * 获取编号规则列表
     */
    @ApiOperation("获取编号规则列表")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ListResponse<NumberRuleExtend> query(@RequestParam(value = "index", defaultValue = "1") int index,
                                                @RequestParam(value = "limit", defaultValue = "20") int limit,
                                                @RequestParam(value = "status",required = false) List<Short> status,
                                                @ApiParam("机构id") @RequestParam(value = "orgId", required = false) Integer orgId,
                                                @ApiParam("编号名称") @RequestParam(value = "noName", required = false) String noName) {
        NumberRuleExample example = new NumberRuleExample();
        NumberRuleExample.Criteria criteria = example.createCriteria();
        If.of(!CollectionUtils.isEmpty(status)).isTrue(() -> criteria.andStatusIn(status));
        If.of(!StringUtils.isEmpty(noName)).isTrue(() -> criteria.andNoNameLike("%" + noName.trim() + "%"));
        If.of(orgId != null).isTrue(() -> criteria.andOrgIdEqualTo(orgId));
        long count = numberRuleService.count(example);
        example.setOrderByClause("create_time desc");
        example.setOffset((index - 1) * limit);
        example.setLimit(limit);
        List<NumberRule> list = numberRuleService.query(example).collect(toList());
        List<NumberRuleExtend> result = Lists.newArrayList();
        list.forEach(numberRule -> {
            NumberRuleExtend numberRuleExtend = new NumberRuleExtend(numberRule);
            Org org = orgService.getById(numberRuleExtend.getOrgId());
            numberRuleExtend.setOrg(org);
            result.add(numberRuleExtend);
        });
        return ListResponse.success(result, count, index, limit);
    }

    /**
     * 根据编号规则id获取编号规则
     */
    @ApiOperation("根据id获取编号规则")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public SimpleResponse<NumberRuleExtend> get(@ApiParam("id") @PathVariable("id") Integer id) {
        NumberRule numberRule = numberRuleService.getById(id);
        Org org = orgService.getById(numberRule.getOrgId());
        NumberRuleExtend numberRuleExtend = new NumberRuleExtend(numberRule);
        numberRuleExtend.setOrg(org);
        return SimpleResponse.success(numberRuleExtend);
    }

    /**
     * 新增编号规则
     */
    @ApiOperation("新增编号规则")
    @RequestMapping(value = "", method = RequestMethod.POST)
    public SimpleResponse<NumberRule> create(@RequestBody NumberRule numberRule) {
        NumberRuleExample numberRuleExample = new NumberRuleExample();
        numberRuleExample.createCriteria().andOrgIdEqualTo(numberRule.getOrgId()).andNoNameEqualTo(numberRule.getNoName());
        List<NumberRule> collect = numberRuleService.query(numberRuleExample).collect(toList());
        if (CollectionUtils.isNotEmpty(collect)){
            return SimpleResponse.error(HttpStatus.INTERNAL_SERVER_ERROR.value(),"该规则已存在");
        }
        NumberRule result = numberRuleService.create(numberRule);
        return SimpleResponse.success(result);
    }

    /**
     * 根据ID 删除
     */
    @ApiOperation("根据ID 删除")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public SimpleResponse delete(@PathVariable("id") Integer id) {
        numberRuleService.deleteById(id);
        return SimpleResponse.success();
    }

    /**
     * 修改
     */
    @ApiOperation("修改")
    @RequestMapping(value = "", method = RequestMethod.PUT)
    public SimpleResponse<NumberRule> update(@RequestBody NumberRule numberRule) {
        numberRuleService.update(numberRule);
        return SimpleResponse.success(numberRule);
    }

    @ApiOperation("根据机构id和编号名获取编号")
    @RequestMapping(value = "/{orgId}/code", method = RequestMethod.GET)
    public SimpleResponse<String> getCurrentNumber(
            @ApiParam("编号名称") @RequestParam(value = "name") String name,
            @ApiParam("机构ID") @PathVariable("orgId") Integer orgId) {

        NumberRuleExample example = new NumberRuleExample();
        example.createCriteria().andNoNameEqualTo(name).andOrgIdEqualTo(orgId);
        NumberRule numberRule = com.starsgroupchina.common.utils.Utils.getFirst(numberRuleService.query(example));
        if (numberRule==null) {
            return SimpleResponse.success();
        }
        String number = numberRuleService.getNumber(numberRule,-1);
        return SimpleResponse.success(number);
    }

}
