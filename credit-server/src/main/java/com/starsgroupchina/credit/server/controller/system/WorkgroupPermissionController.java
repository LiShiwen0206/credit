package com.starsgroupchina.credit.server.controller.system;

import com.google.common.collect.Lists;
import com.starsgroupchina.common.objects.If;
import com.starsgroupchina.common.response.ListResponse;
import com.starsgroupchina.common.response.SimpleResponse;
import com.starsgroupchina.credit.bean.extend.WorkgroupPermissionExtend;
import com.starsgroupchina.credit.bean.model.*;
import com.starsgroupchina.credit.server.service.system.MemberService;
import com.starsgroupchina.credit.server.service.system.OrgService;
import com.starsgroupchina.credit.server.service.system.WorkgroupPermissionService;
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

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @describe :
 * @class_name : WorkgroupPermissionController
 * @author : lishiwen
 * @date : 2018/12/7
 */
@Slf4j
@RestController
@Api(tags = "CREDIT-SWAGGER26", description = "【SYS】 - 工作组权限管理 - WorkgroupPermissionController")
@RequestMapping(value = "/workgroupPermission", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class WorkgroupPermissionController {
    @Autowired
    private WorkgroupPermissionService workgroupPermissionService;
    @Autowired
    private WorkgroupPermissionService.VWorkgroupPermissionService vWorkgroupPermissionService;
    @Autowired
    private WorkgroupService workgroupService;
    @Autowired
    private OrgService orgService;
    @Autowired
    private MemberService memberService;


    @ApiOperation("工作组权限列表")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ListResponse<WorkgroupPermissionExtend > query(
            @RequestParam(value = "index", defaultValue = "1") int index,
            @RequestParam(value = "limit", defaultValue = "20") int limit,
            @RequestParam(value = "status", required = false) List<Short> status,
            @ApiParam("人员姓名") @RequestParam(value = "memberName", required = false) String memberName,
            @ApiParam("机构ID") @RequestParam(value = "orgId", required = false) Integer orgId) {
        VWorkgroupPermissionExample example = new VWorkgroupPermissionExample();
        VWorkgroupPermissionExample.Criteria criteria = example.createCriteria();
        If.of(!CollectionUtils.isEmpty(status)).isTrue(() -> criteria.andStatusIn(status));
        If.of(!StringUtils.isEmpty(memberName)).isTrue(() -> criteria.andMemberNameLike("%"+memberName.trim()+"%"));
        If.of(orgId != null).isTrue(() -> {
            List<Integer> orgIds = Lists.newArrayList();
            orgService.getAllChildOrgId(orgId, orgIds);
            criteria.andOrgIdIn(orgIds);
        });
        long count = vWorkgroupPermissionService.count(example);
        example.setOrderByClause("create_time desc");
        example.setOffset((index - 1) * limit);
        example.setLimit(limit);
        List<WorkgroupPermissionExtend> result = Lists.newArrayList();

        vWorkgroupPermissionService.query(example).forEach(vWorkgroupPermission -> {
            WorkgroupPermission workgroupPermission = new WorkgroupPermission(vWorkgroupPermission);
            WorkgroupPermissionExtend workgroupPermissionExtend = new WorkgroupPermissionExtend(workgroupPermission);
            Org org = orgService.getById(workgroupPermission.getOrgId());
            workgroupPermissionExtend.setOrg(org);
            Member member = memberService.getById(workgroupPermission.getMemberId());
            workgroupPermissionExtend.setMember(member);
            String[] workgroupIds =  workgroupPermission.getWorkgroupIds().split(",");
            if(workgroupIds!=null){
                List<Workgroup> workgroups = new ArrayList<>();
                for(String workgroupId : workgroupIds){
                    try {
                        workgroups.add(workgroupService.getById(Integer.valueOf(workgroupId).intValue()));
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                }
                workgroupPermissionExtend.setWorkgroups(workgroups);
            }
            result.add(workgroupPermissionExtend);
        });
        return ListResponse.success(result, count, index, limit);
    }

    @ApiOperation("根据 工作组权限ID 获取工作组权限")
    @RequestMapping(value = "/{workgroupPermissionId}", method = RequestMethod.GET)
    public SimpleResponse<WorkgroupPermissionExtend> get(@ApiParam("工作组权限ID") @PathVariable("workgroupPermissionId") Integer workgroupPermissionId) {
        WorkgroupPermission workgroupPermission = workgroupPermissionService.getById(workgroupPermissionId);

        WorkgroupPermissionExtend workgroupPermissionExtend = new WorkgroupPermissionExtend(workgroupPermission);
        Org org = orgService.getById(workgroupPermission.getOrgId());
        workgroupPermissionExtend.setOrg(org);
        Member member = memberService.getById(workgroupPermission.getMemberId());
        workgroupPermissionExtend.setMember(member);
        String[] workgroupIds =  workgroupPermission.getWorkgroupIds().split(",");
        if(workgroupIds!=null){
            List<Workgroup> workgroups = new ArrayList<>();
            for(String workgroupId : workgroupIds){
                try {
                    workgroups.add(workgroupService.getById(Integer.valueOf(workgroupId).intValue()));
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
            workgroupPermissionExtend.setWorkgroups(workgroups);
        }
        return SimpleResponse.success(workgroupPermissionExtend);
    }

    @ApiOperation("创建 工作组权限")
    @RequestMapping(value = "", method = RequestMethod.POST)
    public SimpleResponse<WorkgroupPermissionExtend> create(@RequestBody WorkgroupPermissionExtend workgroupPermissionExtend) {
        WorkgroupPermission response = workgroupPermissionService.create(workgroupPermissionExtend);
        return SimpleResponse.success(new WorkgroupPermissionExtend(response));
    }

    @ApiOperation("根据 工作组权限ID 删除工作组权限")
    @RequestMapping(value = "/{workgroupPermissionId}", method = RequestMethod.DELETE)
    public SimpleResponse delete(@PathVariable("workgroupPermissionId") Integer workgroupPermissionId) {
        workgroupPermissionService.deleteById(workgroupPermissionId);
        return SimpleResponse.success();
    }

    @ApiOperation("修改 工作组权限")
    @RequestMapping(value = "", method = RequestMethod.PUT)
    public SimpleResponse<WorkgroupPermissionExtend> update(@RequestBody WorkgroupPermissionExtend workgroupPermissionExtend) {
        workgroupPermissionService.update(workgroupPermissionExtend);
        return SimpleResponse.success(new WorkgroupPermissionExtend(workgroupPermissionExtend));
    }

}
