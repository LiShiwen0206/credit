package com.starsgroupchina.credit.server.controller.system;

import com.google.common.collect.Lists;
import com.starsgroupchina.common.Config;
import com.starsgroupchina.common.context.ContextHolder;
import com.starsgroupchina.common.exception.AppException;
import com.starsgroupchina.common.objects.If;
import com.starsgroupchina.common.response.ListResponse;
import com.starsgroupchina.common.response.SimpleResponse;
import com.starsgroupchina.common.utils.Utils;
import com.starsgroupchina.credit.bean.AuthMember;
import com.starsgroupchina.credit.bean.enums.CommomStatus;
import com.starsgroupchina.credit.bean.extend.MemberExtend;
import com.starsgroupchina.credit.bean.model.*;
import com.starsgroupchina.credit.key.ErrorCode;
import com.starsgroupchina.credit.server.conf.Const;
import com.starsgroupchina.credit.server.service.system.MemberService;
import com.starsgroupchina.credit.server.service.system.OrgService;
import com.starsgroupchina.credit.server.service.system.RoleService;
import com.starsgroupchina.credit.server.service.system.WorkgroupService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

/**
 * Created by zhangfeng on 2018-5-21.
 */
@Slf4j
@RestController
@Api(tags = "CREDIT-SWAGGER21", description = "【SYS】 - 用户管理 - MemberContoller")
@RequestMapping(value = "/member", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class MemberContoller {


    @Autowired
    private MemberService memberService;
    @Autowired
    private OrgService orgService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private WorkgroupService workgroupService;

    @Autowired
    private MemberService.MemberEventService memberEventService;

    /**
     * 获取机构 用户 列表（根据当前登录用户所在机构）
     */
    @ApiOperation("获取机构 用户 列表")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ListResponse<MemberExtend> query(@RequestParam(value = "index", defaultValue = "1") int index,
                                            @RequestParam(value = "limit", defaultValue = "20") int limit,
                                            @RequestParam(value = "status", required = false) List<Short> status,
                                            @RequestParam(value = "roleId", required = false, defaultValue = "-1") Integer roleId,
                                            @ApiParam("姓名") @RequestParam(value = "name", required = false) String name,
                                            @ApiParam("登录名") @RequestParam(value = "loginName", required = false) String loginName,
                                            @ApiParam("联系方式") @RequestParam(value = "phone", required = false) String phone,
                                            @ApiParam("所属机构") @RequestParam(value = "orgId", required = false, defaultValue = "-1") Integer orgId) {

        MemberExample example = new MemberExample();
        MemberExample.Criteria criteria = example.createCriteria();
        If.of(!CollectionUtils.isEmpty(status)).isTrue(() -> criteria.andStatusIn(status));
        If.of(roleId != -1).isTrue(() -> criteria.andRoleIdEqualTo(roleId));
        If.of(orgId != -1).isTrue(() -> criteria.andOrgIdEqualTo(orgId));
        If.of(StringUtils.isNotEmpty(name)).isTrue(() -> criteria.andNameLike("%" + name.trim() + "%"));
        If.of(StringUtils.isNotEmpty(loginName)).isTrue(() -> criteria.andLoginNameLike("%" + loginName.trim() + "%"));
        If.of(StringUtils.isNotEmpty(phone)).isTrue(() -> criteria.andPhoneLike("%" + phone.trim() + "%"));
        AuthMember authMember= (AuthMember) ContextHolder.getContext().getData();
        if (!authMember.getMember().getLoginName().equals(Config.getConfig(Const.CONF_ADMIN)))
            criteria.andHeadOrgIdEqualTo(authMember.getOrg().getHeadOrgId());

        long count = memberService.count(example);

        example.setOrderByClause("create_time desc");
        example.setOffset((index - 1) * limit);
        example.setLimit(limit);

        List<MemberExtend> result = Lists.newArrayList();
        Optional.ofNullable(memberService.query(example)).ifPresent(members ->
                members.forEach(member -> {
                    MemberExtend memberExtension = new MemberExtend(member);
                    Org org = orgService.getById(member.getOrgId());
                    Role role = roleService.getById(member.getRoleId());
                    Workgroup workgroup =workgroupService.getById(member.getWorkgroupId());
                    if (role.getStatus().equals(CommomStatus.NORMAL.key())) {
                        memberExtension.setOrg(org);
                        memberExtension.setRole(role);
                        memberExtension.setWorkgroup(workgroup);
                        result.add(memberExtension);
                    }
                })
        );
        return ListResponse.success(result, count, index, limit);
    }

    /**
     * 根据用户ID 获取 机构用户
     */
    @ApiOperation("根据用户ID 获取 机构用户")
    @RequestMapping(value = "/{memberId}", method = RequestMethod.GET)
    public SimpleResponse<Member> get(@ApiParam("用户ID") @PathVariable("memberId") Integer memberId) {
        Member member = memberService.getById(memberId);
        return SimpleResponse.success(member);
    }

    /**
     * 创建 用户
     */
    @ApiResponses({@ApiResponse(code = ErrorCode.EXIST_MEMBER, message = "当前用户名已存在，请更换用户名重试")})
    @ApiOperation("创建 用户")
    @RequestMapping(value = "", method = RequestMethod.POST)
    public SimpleResponse<MemberExtend> create(@RequestBody MemberExtend member) {
        If.of(memberService.hasMember(member.getLoginName()))
                .isTrueThrow(() -> new AppException(ErrorCode.EXIST_MEMBER));

        Member response = memberService.create(member);
        return SimpleResponse.success(new MemberExtend(response));
    }

    /**
     * 根据 用户ID 删除用户
     */
    @ApiOperation("根据 用户ID 删除用户")
    @RequestMapping(value = "/{memberId}", method = RequestMethod.DELETE)
    public SimpleResponse delete(@PathVariable("memberId") Integer memberId) {
        memberService.deleteById(memberId);
        return SimpleResponse.success();
    }

    /**
     * 修改 用户
     */
    @ApiOperation("修改 用户")
    @RequestMapping(value = "", method = RequestMethod.PUT)
    public SimpleResponse<MemberExtend> update(@RequestBody MemberExtend member) {
        memberService.update(member);
        return SimpleResponse.success(member);
    }

    /**
     * 修改密码
     */
    @ApiOperation("修改密码")
    @RequestMapping(value = "/pwd/reset", method = RequestMethod.PUT)
    public SimpleResponse<Member> resetPwd(@ApiParam("原密码") @RequestParam(value = "oldPwd") String oldPwd,
                                           @ApiParam("新密码") @RequestParam(value = "newPwd") String newPwd) {
        AuthMember authMember= (AuthMember) ContextHolder.getContext().getData();
        Member member = authMember.getMember();
        Member exist = Optional.ofNullable(memberService.checkMember(member.getLoginName().trim(), oldPwd))
                .orElseThrow(() -> new AppException(ErrorCode.OLD_PWD_ERROR));
        exist.setLoginPwd(newPwd);
        memberService.update(exist);
        return SimpleResponse.success(exist);
    }

    @ApiOperation("质检、申报用户列表获取")
    @GetMapping(value = "/{type}/list")
    public SimpleResponse<List<Member>> queryQualityMember(@ApiParam("角色类型：1、质检人员，2、风险申报人员") @PathVariable("type") Integer type) {
        List<Member> result = null;
        RoleExample roleExample = new RoleExample();
        RoleExample.Criteria criteria = roleExample.createCriteria();
        if (type == 1) {
            criteria.andIsQualityEqualTo(1).andStatusEqualTo((short) 0);
        } else {
            criteria.andIsCheatEqualTo(1).andStatusEqualTo((short) 0);
        }
        List<Integer> roles = roleService.query(roleExample).mapToInt(Role::getId).boxed().collect(toList());
        if (CollectionUtils.isNotEmpty(roles)) {
            MemberExample example = new MemberExample();
            example.or().andRoleIdIn(roles).andStatusEqualTo((short) 0);
            result = memberService.query(example).collect(toList());
        }
        return SimpleResponse.success(result);
    }

    /*******************************************************/
    /****************   member    event   *****************/
    /*******************************************************/

    @ApiOperation("根据时间获取事件")
    @GetMapping("/event/list")
    public SimpleResponse<List<MemberEvent>> queryEventList() {
        MemberEventExample example = new MemberEventExample();
        AuthMember authMember= (AuthMember) ContextHolder.getContext().getData();
        example.createCriteria().andCreateUserIdEqualTo(authMember.getMember().getId());
        List<MemberEvent> result = memberEventService.query(example).collect(toList());
        return SimpleResponse.success(result);
    }

    @ApiOperation("根据时间获取事件")
    @GetMapping("/event")
    public SimpleResponse<MemberEvent> queryEvent(@ApiParam("创建事件：yyyy-mm-dd") @RequestParam("eventDate") String eventDate) {
        MemberEventExample example = new MemberEventExample();
        example.createCriteria().andEventDateEqualTo(eventDate);
        MemberEvent event = Utils.getFirst(memberEventService.query(example));
        return SimpleResponse.success(event);
    }

    @ApiOperation("创建用户事件")
    @PostMapping("/event")
    public SimpleResponse<MemberEvent> createEvent(@RequestBody MemberEvent memberEvent) {
        MemberEvent result = memberEventService.create(memberEvent);
        return SimpleResponse.success(result);
    }

    @ApiOperation("更新用户事件")
    @PutMapping("/event")
    public SimpleResponse<MemberEvent> updateEvent(@RequestBody MemberEvent memberEvent) {
        if (StringUtils.isEmpty(memberEvent.getContent())) {
            memberEventService.deleteById(memberEvent.getId());
            return SimpleResponse.success();
        } else {
            MemberEvent result = memberEventService.update(memberEvent);
            return SimpleResponse.success(result);
        }
    }
}
