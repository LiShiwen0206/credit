package com.starsgroupchina.credit.server.controller;

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
import com.starsgroupchina.credit.bean.extend.PolicyExtend;
import com.starsgroupchina.credit.bean.model.*;
import com.starsgroupchina.credit.key.ErrorCode;
import com.starsgroupchina.credit.server.service.PolicyService;
import com.starsgroupchina.credit.server.service.project.FormService;
import com.starsgroupchina.credit.server.service.project.ProjectService;
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
@Api(tags = "CREDIT-SWAGGER37", description = "政策管理 - PolicyController")
@RequestMapping(value = "/policy", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class PolicyController {

    @Autowired
    private ProjectService projectService;
    @Autowired
    private FormService formService;
    @Autowired
    private PolicyService policyService;
    @Autowired
    private PolicyService.PolicyFieldService fieldService;
    @Autowired
    private PolicyService.PolicyGroupService groupService;

    /**
     * 政策列表
     */
    @ApiOperation("政策列表")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ListResponse<Policy> query(@RequestParam(value = "index", defaultValue = "1") int index,
                                      @RequestParam(value = "limit", defaultValue = "20") int limit,
                                      @RequestParam(value = "status", required = false) List<Short> status,
                                      @ApiParam("政策名称") @RequestParam(value = "name", required = false) String name) {

        PolicyExample example = new PolicyExample();
        PolicyExample.Criteria criteria = example.createCriteria();
        If.of(!CollectionUtils.isEmpty(status)).isTrue(() -> criteria.andStatusIn(status));
        If.of(!StringUtils.isEmpty(name)).isTrue(() -> criteria.andPolicyNameLike("%" + name.trim() + "%"));

        long count = policyService.count(example);

        example.setOrderByClause("create_time desc");
        example.setOffset((index - 1) * limit);
        example.setLimit(limit);
        List<Policy> result = policyService.query(example).collect(toList());

        return ListResponse.success(result, count, index, limit);
    }

    @ApiOperation("根据政策ID 查询政策")
    @RequestMapping(value = "/{policyId}", method = RequestMethod.GET)
    public SimpleResponse<PolicyExtend> get(@ApiParam("政策ID") @PathVariable("policyId") Integer policyId) {
        Policy policy = policyService.getById(policyId);
        PolicyExtend result = new PolicyExtend(policy);
        result.setGroups(Lists.newArrayList());

        List<PolicyGroup> groups = groupService.getGroupByPolicyId(policyId);
        groups.forEach(group -> {
            PolicyExtend.PolicyGroupExtend groupExtend = new PolicyExtend.PolicyGroupExtend(group);
            List<PolicyField> fields = fieldService.getFieldByGroupId(group.getId());
            groupExtend.setFields(fields);
            result.getGroups().add(groupExtend);
        });
        return SimpleResponse.success(result);
    }

    @ApiOperation("创建 政策")
    @PostMapping("")
    @Transactional
    public SimpleResponse<PolicyExtend> create(@RequestBody PolicyExtend policyExtend) {
        long start = System.currentTimeMillis();
        Policy policy = policyService.create(policyExtend);
        policyExtend.getGroups().forEach(policyGroup -> {
            policyGroup.setPolicyId(policy.getId());
            PolicyGroup group = groupService.create(policyGroup);
            List<PolicyField> fields = policyGroup.getFields().stream()
                    .peek(field -> field.setPolicyGroupId(group.getId()))
                    .peek(field -> field.setPolicyId(policy.getId()))
                    .collect(Collectors.toList());
            fieldService.create(fields);
        });
        log.info("新建政策耗时：{}",System.currentTimeMillis()-start);
        return SimpleResponse.success(policyExtend);
    }

    @ApiOperation("修改 政策")
    @PutMapping("")
    @Transactional
    public SimpleResponse<PolicyExtend> update(@RequestBody PolicyExtend policyExtend) {
        long start = System.currentTimeMillis();
        policyService.update(policyExtend);
        if (policyExtend.getGroups() == null)
            return SimpleResponse.success(policyExtend);

        groupService.deleteByPolicyId(policyExtend.getId());
        fieldService.deleteByPolicyId(policyExtend.getId());
        policyExtend.getGroups().forEach(policyGroup -> {
            policyGroup.setPolicyId(policyExtend.getId());
            PolicyGroup group = groupService.create(policyGroup);
            List<PolicyField> fields = policyGroup.getFields().stream()
                    .peek(field -> field.setPolicyGroupId(group.getId()))
                    .peek(field -> field.setPolicyId(policyExtend.getId()))
                    .collect(Collectors.toList());
            fieldService.create(fields);
        });
        log.info("修改政策耗时：{}",System.currentTimeMillis()-start);
        return SimpleResponse.success(policyExtend);
    }

    /**
     * 查询政策 field
     */
    @ApiOperation("根据政策ID 查询政策 field")
    @RequestMapping(value = "/field/{policyId}", method = RequestMethod.GET)
    public SimpleResponse<List<PolicyField>> getFields(
            @ApiParam("政策ID") @PathVariable("policyId") Integer policyId,
            @ApiParam("字段类型:产品PRODUCT,用户 USER,车辆 CAR") @RequestParam(value = "type", required = false) String type) {
        PolicyFieldExample example = new PolicyFieldExample();
        example.createCriteria().andPolicyIdEqualTo(policyId).andItemTypeEqualTo(type);
        Stream<PolicyField> fieldStream = fieldService.query(example);
        return SimpleResponse.success(fieldStream.collect(Collectors.toList()));
    }

    /**
     * 验证政策
     */
    @ApiResponses({@ApiResponse(code = ErrorCode.PROJECT_NOT_EXIST, message = "项目不存在")})
    @ApiOperation("验证政策")
    @RequestMapping(value = "/{projectNo}/valid", method = RequestMethod.GET)
    public SimpleResponse<Set<String>> validProject(@ApiParam("项目编号") @PathVariable("projectNo") String projectNo) {
        Project project = projectService.getProjectByProjectNo(projectNo);
        If.of(project == null).isTrueThrow(() -> new AppException(ErrorCode.PROJECT_NOT_EXIST, projectNo));
        if (project.getPolicyId() == -1) {
            project.setAuditStatus(AuditStatus.POLICY_ALLOW.key());
            projectService.update(project);
            return SimpleResponse.success(null);
        }
        ProjectFormExample projectFormExample = new ProjectFormExample();
        projectFormExample.createCriteria().andProjectNoEqualTo(projectNo);
        List<ProjectForm> forms = formService.query(projectFormExample).collect(Collectors.toList());
        Map<PolicyGroup, List<PolicyField>> result = Maps.newHashMap();
        AtomicReference<Boolean> validResult = new AtomicReference<>(Boolean.FALSE);
        Set<String> invalidPolicyFields = Sets.newHashSet();
        Map<String, String> keyValue=Maps.newHashMap();
        forms.forEach(form -> {
            Map<String, String> tempValue = FormParser.parse(form.getProjectForm());
            if (MapUtils.isNotEmpty(tempValue)){
                keyValue.putAll(tempValue);
            }

        });
        Tuple<Boolean, Map<PolicyGroup, List<PolicyField>>> formValidResult =
                policyService.validByPolicyGroup(keyValue, project.getPolicyId(),project);
        if (MapUtils.isNotEmpty(formValidResult.snd)) {
            //把验证结果放入到集合中
            formValidResult.snd.forEach(((policyGroup, policyFields) -> {
                invalidPolicyFields.addAll(policyFields.stream().map(PolicyField::getFieldName).collect(toList()));
            }));
        }
//        if (formValidResult.fst) {
//            validResult.set(Boolean.TRUE);
//        }
        if (formValidResult.fst) {
            project.setAuditStatus(AuditStatus.POLICY_ALLOW.key());
        } else {
            project.setAuditStatus(AuditStatus.POLICY_REJECT.key());
        }

        projectService.update(project);
        if (!formValidResult.fst) {
            throw new AppException(ErrorCode.PROJECT_POLICY_INVALID_ERROR, invalidPolicyFields, null);
        }
        return SimpleResponse.success(invalidPolicyFields);
    }
}
