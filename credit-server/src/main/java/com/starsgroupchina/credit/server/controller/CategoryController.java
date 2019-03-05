package com.starsgroupchina.credit.server.controller;

import com.google.common.collect.Lists;
import com.starsgroupchina.common.objects.If;
import com.starsgroupchina.common.response.ListResponse;
import com.starsgroupchina.common.response.SimpleResponse;
import com.starsgroupchina.credit.bean.extend.CategoryExtend;
import com.starsgroupchina.credit.bean.model.Category;
import com.starsgroupchina.credit.bean.model.CategoryExample;
import com.starsgroupchina.credit.bean.model.Policy;
import com.starsgroupchina.credit.server.service.CategoryService;
import com.starsgroupchina.credit.server.service.PolicyService;
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
import java.util.Optional;

/**
 * Created by zhangfeng on 2018-5-9.
 */
@Slf4j
@RestController
@Api(tags = "CREDIT-SWAGGER32",description = "大类管理 - CategoryController" )
@RequestMapping(value = "/category", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private PolicyService policyService;

    /**
     * 获取大类列表
     */
    @ApiOperation("获取机构列表")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ListResponse<CategoryExtend> query(@RequestParam(value = "index", defaultValue = "1") int index,
                                              @RequestParam(value = "limit", defaultValue = "20") int limit,
                                              @RequestParam(value = "status",required = false) List<Short> status,
                                              @ApiParam("名称") @RequestParam(value = "name", required = false) String name) {

        CategoryExample example = new CategoryExample();
        CategoryExample.Criteria criteria = example.createCriteria();
        If.of(!CollectionUtils.isEmpty(status)).isTrue(() -> criteria.andStatusIn(status));
        If.of(!StringUtils.isEmpty(name)).isTrue(() -> criteria.andNameLike("%" + name.trim() + "%"));

        long count = categoryService.count(example);
        
        example.setOrderByClause("create_time desc");
        example.setOffset((index - 1) * limit);
        example.setLimit(limit);

        List<CategoryExtend> result = Lists.newArrayList();
        Optional.ofNullable(categoryService.query(example)).ifPresent(categorys ->
                categorys.forEach(category -> {
                    CategoryExtend categoryExtend = new CategoryExtend(category);
                    Policy policy = policyService.getById(category.getPolicyId());
                    categoryExtend.setPolicy(policy);
                    result.add(categoryExtend);
                })
        );
        return ListResponse.success(result, count, index, limit);
    }

    /**
     * 根据大类ID获取大类
     */
    @ApiOperation("根据大类ID获取大类")
    @RequestMapping(value = "/{categoryId}", method = RequestMethod.GET)
    public SimpleResponse<Category> get(@ApiParam("大类ID") @PathVariable("categoryId") Integer categoryId) {
        Category response = categoryService.getById(categoryId);
        return SimpleResponse.success(response);
    }

    /**
     * 创建 大类
     */
    @ApiOperation("创建 大类")
    @RequestMapping(value = "", method = RequestMethod.POST)
    public SimpleResponse<Category> create(@RequestBody Category category) {
        Category response = categoryService.create(category);
        return SimpleResponse.success(response);
    }

    /**
     * 根据 大类ID 删除
     */
    @ApiOperation("根据 机构ID 删除机构")
    @RequestMapping(value = "/{categoryId}", method = RequestMethod.DELETE)
    public SimpleResponse delete(@PathVariable("categoryId") Integer categoryId) {
        categoryService.deleteById(categoryId);
        return SimpleResponse.success();
    }

    /**
     * 修改 大类
     */
    @ApiOperation("修改 大类")
    @RequestMapping(value = "", method = RequestMethod.PUT)
    public SimpleResponse<Category> update(@RequestBody Category category) {
        categoryService.update(category);
        return SimpleResponse.success(category);
    }


}
