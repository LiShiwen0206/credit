package com.starsgroupchina.credit.server.controller.system;

import com.google.common.collect.Lists;
import com.starsgroupchina.common.Config;
import com.starsgroupchina.common.context.ContextHolder;
import com.starsgroupchina.common.objects.If;
import com.starsgroupchina.common.response.ListResponse;
import com.starsgroupchina.common.response.SimpleResponse;
import com.starsgroupchina.credit.bean.AuthMember;
import com.starsgroupchina.credit.bean.extend.RoleExtend;
import com.starsgroupchina.credit.bean.model.*;
import com.starsgroupchina.credit.server.conf.Const;
import com.starsgroupchina.credit.server.service.system.OrgService;
import com.starsgroupchina.credit.server.service.system.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

/**
 * Created by zhangfeng on 2018-5-21.
 */
@Slf4j
@RestController
@Api(tags = "CREDIT-SWAGGER23", description = "【SYS】 - 角色管理 - RoleController")
@RequestMapping(value = "/role", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class RoleController {

    @Autowired
    private RoleService roleService;
    @Autowired
    private RoleService.RoleDataService roleDataService;
    @Autowired
    private OrgService orgService;

    @ApiOperation("角色列表")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ListResponse<RoleExtend> query(
            @RequestParam(value = "index", defaultValue = "1") int index,
            @RequestParam(value = "limit", defaultValue = "20") int limit,
            @RequestParam(value = "status", required = false) List<Short> status,
            @ApiParam("角色名称") @RequestParam(value = "roleName", required = false) String roleName,
            @ApiParam("是否是审核小组:0否，1是") @RequestParam(value = "isAudit", defaultValue = "-1") Integer isAudit,
            @ApiParam("是否是复核小组:0否，1是") @RequestParam(value = "isRecheck", defaultValue = "-1") Integer isRecheck) {

        RoleExample example = new RoleExample();
        RoleExample.Criteria criteria = example.createCriteria();

        If.of(!CollectionUtils.isEmpty(status)).isTrue(() -> criteria.andStatusIn(status));
        If.of(!StringUtils.isEmpty(roleName)).isTrue(() -> criteria.andRoleNameLike("%" + roleName.trim() + "%"));
        If.of(isAudit != -1).isTrue(() -> criteria.andIsAuditEqualTo(isAudit));
        If.of(isRecheck != -1).isTrue(() -> criteria.andIsAuditRecheckEqualTo(isRecheck));

        AuthMember authMember= (AuthMember) ContextHolder.getContext().getData();
        if (!authMember.getMember().getLoginName().equals(Config.getConfig(Const.CONF_ADMIN)))
            criteria.andHeadOrgIdEqualTo(authMember.getOrg().getHeadOrgId());

        long count = roleService.count(example);

        example.setOrderByClause("create_time desc");
        example.setOffset((index - 1) * limit);
        example.setLimit(limit);
        List<RoleExtend> result = Lists.newArrayList();
        roleService.query(example).forEach(role -> {
            RoleExtend roleExtend = new RoleExtend(role);
            Org org = orgService.getById(role.getHeadOrgId());
            roleExtend.setOrg(org);
            result.add(roleExtend);
        });
        return ListResponse.success(result, count, index, limit);
    }

    @ApiOperation("根据 角色ID 获取角色")
    @RequestMapping(value = "/{roleId}", method = RequestMethod.GET)
    public SimpleResponse<Role> getRole(@ApiParam("角色ID") @PathVariable("roleId") Integer roleId) {
        Role role = roleService.getById(roleId);
        return SimpleResponse.success(role);
    }

    @ApiOperation("创建 机构 角色")
    @RequestMapping(value = "", method = RequestMethod.POST)
    public SimpleResponse<Role> createRole(@RequestBody Role role) {
        Role response = roleService.create(role);
        return SimpleResponse.success(response);
    }

    @ApiOperation("根据 角色ID 删除角色")
    @RequestMapping(value = "/{roleId}", method = RequestMethod.DELETE)
    public SimpleResponse deleteRole(@PathVariable("roleId") Integer roleId) {
        roleService.deleteById(roleId);
        roleDataService.deleteRoleData(roleId);
        return SimpleResponse.success();
    }


    @ApiOperation("修改 角色")
    @RequestMapping(value = "", method = RequestMethod.PUT)
    public SimpleResponse<Role> updateRole(@RequestBody Role role) {
        String authOperation = role.getAuthOperation();
        if (StringUtils.isNotEmpty(authOperation)) {
            List<String> menus = roleDataService.parseAuthOperation4MenuKey(authOperation);
            List<RoleData> roleDatas = roleDataService.getRoleDatasByRoleId(role.getId());

            menus.forEach(menuKey -> {
                roleDataService.deleteRoleData(role.getId(), menuKey, role.getId());
                RoleData roleData = new RoleData();
                roleData.setMenuKey(menuKey);
                roleData.setChildRoleId(role.getId());
                roleData.setRoleId(role.getId());
                roleDataService.create(roleData);
                roleDatas.removeIf(item -> item.getMenuKey().equals(menuKey));
            });
            roleDatas.forEach(roleData -> roleDataService.deleteById(roleData.getId()));
        }
        roleService.update(role);
        return SimpleResponse.success(role);
    }

    @ApiOperation("获取操作权限")
    @RequestMapping(value = "/auth", method = RequestMethod.GET)
    public SimpleResponse<String> getAuthOperation(@RequestParam(value = "token") String token) {
        final String[] result = {Strings.EMPTY};
        AuthMember authMember = roleService.getAuthOperation(token);
        Optional.ofNullable(authMember).ifPresent(auth ->
                Optional.ofNullable(auth.getRole()).ifPresent(role ->
                        result[0] = role.getAuthOperation()
                )
        );
        return SimpleResponse.success(result[0]);
    }

    @ApiOperation("获取数据权限")
    @RequestMapping(value = "/data", method = RequestMethod.GET)
    public SimpleResponse<List<RoleData>> getRoleData(
            @ApiParam("当前roleId") @RequestParam("roleId") Integer roleId,
            @ApiParam("菜单KEY") @RequestParam("menuKey") String menuKey) {
        RoleDataExample example = new RoleDataExample();
        example.createCriteria().andRoleIdEqualTo(roleId).andMenuKeyEqualTo(menuKey);
        List<RoleData> result = roleDataService.query(example).collect(toList());
        return SimpleResponse.success(result);
    }

    @ApiOperation("创建 数据 权限")
    @RequestMapping(value = "/data", method = RequestMethod.POST)
    @Transactional
    public SimpleResponse<List<RoleData>> createRoleData(@ApiParam("当前roleId") @RequestParam("roleId") Integer roleId,
                                                         @ApiParam("菜单KEY") @RequestParam("menuKey") String menuKey,
                                                         @RequestBody List<RoleData> roleDatas) {
        RoleData roleData = new RoleData();
        roleData.setMenuKey(menuKey);
        roleData.setChildRoleId(roleId);
        roleData.setRoleId(roleId);
        roleDatas.add(roleData);

        roleDataService.deleteRoleData(roleId, menuKey);
        roleDataService.create(roleDatas);
        return SimpleResponse.success(roleDatas);
    }


}
