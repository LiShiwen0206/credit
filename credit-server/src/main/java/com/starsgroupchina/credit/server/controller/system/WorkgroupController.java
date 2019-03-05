package com.starsgroupchina.credit.server.controller.system;

import com.google.common.collect.Lists;
import com.starsgroupchina.common.objects.If;
import com.starsgroupchina.common.response.ListResponse;
import com.starsgroupchina.common.response.SimpleResponse;
import com.starsgroupchina.credit.bean.extend.WorkgroupExtend;
import com.starsgroupchina.credit.bean.model.*;

import com.starsgroupchina.credit.server.service.system.OrgService;
import com.starsgroupchina.credit.server.service.system.WorkgroupService;
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

/**
 * @describe : 
 * @class_name : WorkgroupController
 * @author : lishiwen
 * @date : 2018/12/7 
 */
@Slf4j
@RestController
@Api(tags = "CREDIT-SWAGGER25", description = "【SYS】 - 工作组管理 - WorkgroupController")
@RequestMapping(value = "/workgroup", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class WorkgroupController {
    @Autowired
    private WorkgroupService workgroupService;
    @Autowired
    private OrgService orgService;


    @ApiOperation("工作组列表")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ListResponse<WorkgroupExtend> query(
            @RequestParam(value = "index", defaultValue = "1") int index,
            @RequestParam(value = "limit", defaultValue = "20") int limit,
            @RequestParam(value = "status", required = false) List<Short> status,
            @ApiParam("工作组名称") @RequestParam(value = "name", required = false) String name,
            @ApiParam("机构ID") @RequestParam(value = "orgId", required = false) Integer orgId,
            @ApiParam("是否包含子机构，0，包含，1，不包含") @RequestParam(value = "isOrgChild", required = false,  defaultValue = "0") Integer isOrgChild) {

        WorkgroupExample example = new WorkgroupExample();
        WorkgroupExample.Criteria criteria = example.createCriteria();
        If.of(!CollectionUtils.isEmpty(status)).isTrue(() -> criteria.andStatusIn(status));
        If.of(!StringUtils.isEmpty(name)).isTrue(() -> criteria.andNameLike("%" + name.trim() + "%"));
        If.of(orgId != null).isTrue(() -> {
            if(isOrgChild.equals(0)){
                List<Integer> orgIds = Lists.newArrayList();
                orgService.getAllChildOrgId(orgId, orgIds);
                criteria.andOrgIdIn(orgIds);
            }else if(isOrgChild.equals(1)){
                criteria.andOrgIdEqualTo(orgId);
            }
        });
        long count = workgroupService.count(example);
        example.setOrderByClause("create_time desc");
        example.setOffset((index - 1) * limit);
        example.setLimit(limit);
        List<WorkgroupExtend> result = Lists.newArrayList();
        workgroupService.query(example).forEach(workgroup -> {
            WorkgroupExtend workgroupExtend = new WorkgroupExtend(workgroup);
            Org org = orgService.getById(workgroup.getOrgId());
            workgroupExtend.setOrg(org);
            result.add(workgroupExtend);
        });
        return ListResponse.success(result, count, index, limit);
    }

    @ApiOperation("根据 工作组ID 获取工作组")
    @RequestMapping(value = "/{workgroupId}", method = RequestMethod.GET)
    public SimpleResponse<WorkgroupExtend> get(@ApiParam("工作组ID") @PathVariable("workgroupId") Integer workgroupId) {
        Workgroup workgroup = workgroupService.getById(workgroupId);
        return SimpleResponse.success(new WorkgroupExtend(workgroup));
    }

    @ApiOperation("创建 工作组")
    @RequestMapping(value = "", method = RequestMethod.POST)
    public SimpleResponse<WorkgroupExtend> create(@RequestBody WorkgroupExtend workgroupExtend) {
        Workgroup response = workgroupService.create(workgroupExtend);
        return SimpleResponse.success(new WorkgroupExtend(response));
    }

    @ApiOperation("根据 工作组ID 删除工作组")
    @RequestMapping(value = "/{workgroupId}", method = RequestMethod.DELETE)
    public SimpleResponse delete(@PathVariable("workgroupId") Integer workgroupId) {
        workgroupService.deleteById(workgroupId);
        return SimpleResponse.success();
    }

    @ApiOperation("修改 工作组")
    @RequestMapping(value = "", method = RequestMethod.PUT)
    public SimpleResponse<WorkgroupExtend> update(@RequestBody WorkgroupExtend workgroupExtend) {
        workgroupService.update(workgroupExtend);
        return SimpleResponse.success(new WorkgroupExtend(workgroupExtend));
    }

}
