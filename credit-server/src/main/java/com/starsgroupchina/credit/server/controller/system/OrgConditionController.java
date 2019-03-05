package com.starsgroupchina.credit.server.controller.system;

import com.google.common.collect.Lists;
import com.starsgroupchina.common.exception.AppException;
import com.starsgroupchina.common.objects.If;
import com.starsgroupchina.common.response.ListResponse;
import com.starsgroupchina.common.response.SimpleResponse;
import com.starsgroupchina.credit.bean.extend.OrgConditionExtend;
import com.starsgroupchina.credit.bean.extend.ProjectExtend;
import com.starsgroupchina.credit.bean.model.*;
import com.starsgroupchina.credit.key.ErrorCode;
import com.starsgroupchina.credit.server.service.OrgConditionService;
import com.starsgroupchina.credit.server.service.system.OrgService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

/**
 * @Author: QinHaoHao
 * @Description:
 * @Date: Created in 11:14 2018/8/21
 * @Modifed By:
 */
@Slf4j
@RestController
@Api(tags = "CREDIT-SWAGGER42", description = "机构条件管理 - OrgConditionController")
@RequestMapping(value = "/org/condition", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class OrgConditionController {


    @Autowired
    private OrgConditionService orgConditionService;
    @Autowired
    private OrgConditionService.OrgConditionGroupService orgConditionGroupService;
    @Autowired
    private OrgConditionService.OrgConditionFieldService orgConditionFieldService;
    @Autowired
    private OrgService orgService;


    /**
     * 机构条件列表
     */
    @ApiOperation("机构条件列表")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ListResponse<OrgConditionExtend> query(@RequestParam(value = "index", defaultValue = "1") int index,
                                                  @RequestParam(value = "limit", defaultValue = "20") int limit,
                                                  @RequestParam(value = "status", required = false) List<Short> status,
                                                  @ApiParam("条件名称") @RequestParam(value = "name", required = false) String name,
                                                  @ApiParam("机构id") @RequestParam(value = "orgIds", required = false) List<Integer> orgIds,
                                                  @ApiParam("来源：下拉框抽取的数据，orgIds为空不返回数据")@RequestParam(value = "source",required = false) String source) {

        OrgConditionExample example = new OrgConditionExample();
        OrgConditionExample.Criteria criteria = example.createCriteria();
        If.of(!CollectionUtils.isEmpty(status)).isTrue(() -> criteria.andStatusIn(status));
        If.of(!StringUtils.isEmpty(name)).isTrue(() -> criteria.andConditionNameLike("%" + name.trim() + "%"));

        if (CollectionUtils.isEmpty(orgIds)&&StringUtils.isNotEmpty(source)){
            return ListResponse.success(Lists.newArrayList(), 0, index, limit);
        }else {
            If.of(!CollectionUtils.isEmpty(orgIds)).isTrue(() ->  criteria.andOrgIdIn(orgIds));
        }
        long count = orgConditionService.count(example);
        example.setOrderByClause("create_time desc");
        example.setOffset((index - 1) * limit);
        example.setLimit(limit);
        List<OrgConditionExtend> result = Lists.newArrayList();
        orgConditionService.query(example).forEach(orgCondition -> {
            OrgConditionExtend orgConditionExtend = new OrgConditionExtend(orgCondition);
            Org org = orgService.getById(orgCondition.getOrgId());
            orgConditionExtend.setOrg(org);
            result.add(orgConditionExtend);
        });
        return ListResponse.success(result, count, index, limit);
    }

    @ApiOperation("根据条件id查询机构条件")
    @RequestMapping(value = "/{conditionId}", method = RequestMethod.GET)
    public SimpleResponse<OrgConditionExtend> get(@ApiParam("条件id") @PathVariable("conditionId") Integer conditionId) {
        OrgCondition orgCondition = orgConditionService.getById(conditionId);
        OrgConditionExtend result = new OrgConditionExtend(orgCondition);
        Org org = orgService.getById(orgCondition.getOrgId());
        result.setOrg(org);
        result.setGroups(Lists.newArrayList());

        List<OrgConditionGroup> groups = orgConditionGroupService.getGroupByConditionId(conditionId);
        groups.forEach(group -> {
            OrgConditionExtend.OrgCinditionGroupExtend groupExtend = new OrgConditionExtend.OrgCinditionGroupExtend(group);
            List<OrgConditionField> fields = orgConditionFieldService.getFieldByGroupId(group.getId());
            groupExtend.setFields(fields);
            result.getGroups().add(groupExtend);
        });
        return SimpleResponse.success(result);
    }

    @ApiResponses({@ApiResponse(code = ErrorCode.ORG_CONDITION_NAME_REPEAT, message = "条件创建失败,当前机构条件名重复")})
    @ApiOperation("创建机构条件")
    @PostMapping("")
    @Transactional
    public SimpleResponse<OrgConditionExtend> create(@RequestBody OrgConditionExtend orgConditionExtend) {

        OrgConditionExample example = new OrgConditionExample();
        example.createCriteria().andConditionNameEqualTo(orgConditionExtend.getConditionName()).andOrgIdEqualTo(orgConditionExtend.getOrgId());
        long count = orgConditionService.count(example);
        If.of(count>0).isTrueThrow(()->new AppException(ErrorCode.ORG_CONDITION_NAME_REPEAT));
        OrgCondition orgCondition = orgConditionService.create(orgConditionExtend);
        orgConditionExtend.getGroups().forEach(orgCinditionGroup -> {
            orgCinditionGroup.setConditionId(orgCondition.getId());
            orgConditionGroupService.create(orgCinditionGroup);
            List<OrgConditionField> fields = orgCinditionGroup.getFields().stream()
                    .peek(field -> field.setConditionGroupId(orgCinditionGroup.getId()))
                    .peek(field -> field.setConditionId(orgCondition.getId()))
                    .collect(Collectors.toList());
            orgConditionFieldService.create(fields);
        });
        return SimpleResponse.success(orgConditionExtend);
    }

    @ApiOperation("修改机构条件")
    @PutMapping("")
    @Transactional
    public SimpleResponse<OrgConditionExtend> update(@RequestBody OrgConditionExtend orgConditionExtend) {
//        OrgConditionExample example = new OrgConditionExample();
//        example.createCriteria().andConditionNameEqualTo(orgConditionExtend.getConditionName()).andOrgIdEqualTo(orgConditionExtend.getOrgId());
//        long count = orgConditionService.count(example);
//        If.of(count>0).isTrueThrow(()->new AppException(ErrorCode.ORG_CONDITION_NAME_REPEAT));
        orgConditionService.update(orgConditionExtend);
        if (orgConditionExtend.getGroups() == null) {
            return SimpleResponse.success(orgConditionExtend);
        }
        orgConditionGroupService.deleteByConditionId(orgConditionExtend.getId());
        orgConditionFieldService.deleteByConditionId(orgConditionExtend.getId());
        orgConditionExtend.getGroups().forEach(orgCinditionGroup -> {
            orgCinditionGroup.setConditionId(orgConditionExtend.getId());
            orgConditionGroupService.create(orgCinditionGroup);
            List<OrgConditionField> fields = orgCinditionGroup.getFields().stream()
                    .peek(field -> field.setConditionGroupId(orgCinditionGroup.getId()))
                    .peek(field -> field.setConditionId(orgConditionExtend.getId()))
                    .collect(Collectors.toList());
            orgConditionFieldService.create(fields);
        });
        return SimpleResponse.success(orgConditionExtend);
    }

    /**
     * 查询条件field
     */
    @ApiOperation("根据机构条件ID 查询条件field")
    @RequestMapping(value = "/field/{conditionId}", method = RequestMethod.GET)
    public SimpleResponse<List<OrgConditionField>> getFields(
            @ApiParam("条件ID") @PathVariable("conditionId") Integer conditionId) {
        OrgConditionFieldExample example = new OrgConditionFieldExample();
        example.createCriteria().andConditionIdEqualTo(conditionId);
        List<OrgConditionField> result = orgConditionFieldService.query(example).collect(toList());
        return SimpleResponse.success(result);
    }

    /**
     * 验证条件是否通过
     */
    @ApiResponses({@ApiResponse(code = ErrorCode.PROJECT_CONDITION_INVALID_ERROR, message = "条件验证未通过")})
    @ApiOperation("验证条件是否通过")
    @RequestMapping(value = "/valid", method = RequestMethod.POST)
    public SimpleResponse validProject(@RequestBody ProjectExtend projectExtend) {
        log.info("/condition/valid进行条件验证：{}", projectExtend);
        String result = orgConditionService.validProject(projectExtend);
        if (StringUtils.isEmpty(result)){
            return SimpleResponse.success();
        }
        return SimpleResponse.error(ErrorCode.PROJECT_CONDITION_INVALID_ERROR,result);

    }
}
