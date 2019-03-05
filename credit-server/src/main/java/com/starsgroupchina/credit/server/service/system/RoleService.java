package com.starsgroupchina.credit.server.service.system;

import com.google.common.collect.Lists;
import com.starsgroupchina.common.base.AbstractService;
import com.starsgroupchina.common.utils.JsonUtil;
import com.starsgroupchina.credit.bean.AuthMember;
import com.starsgroupchina.credit.bean.mapper.RoleDataMapper;
import com.starsgroupchina.credit.bean.model.Role;
import com.starsgroupchina.credit.bean.model.RoleData;
import com.starsgroupchina.credit.bean.model.RoleDataExample;
import com.starsgroupchina.credit.bean.model.RoleExample;
import com.starsgroupchina.credit.key.KeyCache;
import com.starsgroupchina.credit.server.conf.RedisConf;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;


/**
 * Created by zhangfeng on 2018-5-10.
 */
@Slf4j
@Service
public class RoleService extends AbstractService<Role, RoleExample> {

    @Resource(name = RedisConf.REDIS_TEMPLATE)
    private RedisOperations redisOperations;

    public AuthMember getAuthOperation(String token) {
        String key = KeyCache.token(token);
        return JsonUtil.toObject((String) redisOperations.opsForValue().get(key), AuthMember.class);
    }

    /**
     * 判断是否是审核组长
     */
    public boolean checkAuditLeader(Integer roleId) {
        RoleExample example = new RoleExample();
        example.createCriteria().andAuditLeaderLike("%," + roleId + ",%");
        return query(example).count() > 0;
    }

    /**
     * 判断是否是复核组长
     */
    public boolean checkRecheckLeader(Integer roleId) {
        RoleExample example = new RoleExample();
        example.createCriteria().andAuditRecheckLeaderLike("%," + roleId + ",%");
        return query(example).count() > 0;
    }


    @Service
    public class RoleDataService extends AbstractService<RoleData, RoleDataExample> {

        @Autowired
        private RoleDataMapper dataMapper;

        public void deleteRoleData(Integer roleId) {
            RoleDataExample example = new RoleDataExample();
            example.createCriteria().andRoleIdEqualTo(roleId);
            dataMapper.deleteByExample(example);
        }

        public void deleteRoleData(Integer roleId, String menuKey) {
            RoleDataExample example = new RoleDataExample();
            example.createCriteria().andRoleIdEqualTo(roleId).andMenuKeyEqualTo(menuKey);
            dataMapper.deleteByExample(example);
        }

        public void deleteRoleData(Integer roleId, String menuKey, Integer childRoldId) {
            RoleDataExample example = new RoleDataExample();
            example.createCriteria()
                    .andRoleIdEqualTo(roleId)
                    .andMenuKeyEqualTo(menuKey)
                    .andChildRoleIdEqualTo(childRoldId);
            dataMapper.deleteByExample(example);
        }

        public List<RoleData> getRoleDatasByRoleId(Integer roleId) {
            RoleDataExample example = new RoleDataExample();
            example.createCriteria().andRoleIdEqualTo(roleId);
            return dataMapper.selectByExample(example);
        }

        //递归查询
        public List<RoleData> recursionRoleDataByRoleId(Integer roleId) {
            List<RoleData> result = Lists.newArrayList();
            RoleDataExample example = new RoleDataExample();
            example.createCriteria().andRoleIdEqualTo(roleId);
            List<RoleData> datas = dataMapper.selectByExample(example);
            Map<String, List<RoleData>> map = datas.stream().collect(Collectors.groupingBy(RoleData::getMenuKey));
            map.forEach((menuKey, roleDatas) -> {
                List<RoleData> chrldren = Lists.newArrayList();
                childRoleDatas(menuKey, roleDatas, chrldren);
                result.addAll(chrldren);
            });
            return result;
        }

        public List<String> parseAuthOperation4MenuKey(String authOperation) {
            List<String> result = Lists.newArrayList();
            Arrays.stream(authOperation.split(",")).forEach(str -> {
                if (str.contains("list")) result.add(str);
            });
            return result;
        }

        private void childRoleDatas(String menuKey, List<RoleData> roleDatas, List<RoleData> result) {
            result.addAll(roleDatas);
            roleDatas.forEach(roleData -> {
                List<RoleData> _roleDatas = childRoleData(roleData.getChildRoleId(), menuKey).stream()
                        .filter(_roleData -> !hasRoleData(result, _roleData))
                        .collect(toList());
                if (!CollectionUtils.isEmpty(_roleDatas))
                    childRoleDatas(menuKey, _roleDatas, result);
            });
        }

        private List<RoleData> childRoleData(Integer roleId, String menuKey) {
            RoleDataExample example = new RoleDataExample();
            example.createCriteria().andRoleIdEqualTo(roleId).andMenuKeyEqualTo(menuKey);
            return dataMapper.selectByExample(example);
        }

        private boolean hasRoleData(List<RoleData> roleDatas, RoleData roleData) {
            return roleDatas.stream().anyMatch(_roleData ->
                    _roleData.getMenuKey().equals(roleData.getMenuKey())
                            && _roleData.getChildRoleId() == roleData.getChildRoleId());
        }

    }


}

