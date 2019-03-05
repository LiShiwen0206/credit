package com.starsgroupchina.credit.server.service.system;

import com.google.common.collect.Sets;
import com.starsgroupchina.common.base.AbstractService;
import com.starsgroupchina.common.utils.JsonUtil;
import com.starsgroupchina.common.utils.Utils;
import com.starsgroupchina.credit.bean.AuthMember;
import com.starsgroupchina.credit.bean.mapper.MemberMapper;
import com.starsgroupchina.credit.bean.model.*;
import com.starsgroupchina.credit.key.KeyCache;
import com.starsgroupchina.credit.server.conf.RedisConf;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static java.util.stream.Collectors.toList;

/**
 * Created by zhangfeng on 2018-5-10.
 */
@Slf4j
@Service
public class MemberService extends AbstractService<Member, MemberExample> {

    //30分钟过期
    final static Integer EXPIRE = 30;
    final static TimeUnit TIME_UNIT = TimeUnit.MINUTES;

    @Resource(name = RedisConf.REDIS_TEMPLATE)
    private RedisOperations redisOperations;

    @Autowired
    private MemberMapper memberMapper;
    @Autowired
    private OrgService orgService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private RoleService.RoleDataService roleDataService;

    @Autowired
    private LoginRecordService loginRecordService;

    @Autowired
    private WorkgroupPermissionService workgroupPermissionService;

    public boolean hasMember(String loginName) {
        MemberExample example = new MemberExample();
        example.setAdditionalWhere("status=0 and BINARY login_name='" + loginName + "'");
        return memberMapper.selectByExample(example).size() != 0 ? true : false;
    }

    public Member checkMember(String loginName, String loginPwd) {
        MemberExample example = new MemberExample();
        example.setAdditionalWhere("status=0 and BINARY login_name='" + loginName + "' and login_pwd='" + loginPwd + "'");
        List<Member> members = memberMapper.selectByExample(example);
        return Utils.getFirst(members);
    }

    public AuthMember cacheAuthMember(String token, Member member) {
        Org org = orgService.getById(member.getOrgId());
        Role role = roleService.getById(member.getRoleId());
        List<RoleData> roleDatas = roleDataService.recursionRoleDataByRoleId(member.getRoleId());
        Set<Integer> set = Sets.newHashSet();
        WorkgroupPermissionExample example = new WorkgroupPermissionExample();
        example.createCriteria().andMemberIdEqualTo(member.getId());
        workgroupPermissionService.query(example).forEach(workgroupPermission -> {
            String workgroupIds = workgroupPermission.getWorkgroupIds();
            String[] split = workgroupIds.split(",");
            for (String s : split) {
                if (StringUtils.isNotEmpty(s)) {
                    set.add(Integer.valueOf(s));
                }
            }
        });
        AuthMember authMember = new AuthMember(token, member, org, role, roleDatas, set.stream().collect(toList()));
        boolean auditLeader = roleService.checkAuditLeader(role.getId());
        boolean recheckLeader = roleService.checkRecheckLeader(role.getId());
        authMember.setIsAuditLeader(auditLeader == true ? 1 : 0);
        authMember.setIsRecheckLeader(recheckLeader == true ? 1 : 0);
        redisOperations.opsForValue().set(KeyCache.token(token), JsonUtil.toJson(authMember), EXPIRE, TIME_UNIT);
        LoginRecord loginRecord = new LoginRecord();
        loginRecord.setMemberId(member.getId());
        loginRecord.setCreateUser(member.getName());
        loginRecord.setCreateUserId(member.getId());
        loginRecordService.create(loginRecord);
        return authMember;
    }

    public AuthMember getAuthMember(String token) {
        Object authMember = redisOperations.opsForValue().get(KeyCache.token(token));
        if (authMember == null)
            return null;
        return JsonUtil.toObject(authMember.toString(), AuthMember.class);
    }

    public void refreshAuthMember(String token) {
        redisOperations.expire(KeyCache.token(token), EXPIRE, TIME_UNIT);

    }

    @Service
    public class MemberEventService extends AbstractService<MemberEvent, MemberEventExample> {

    }
}
