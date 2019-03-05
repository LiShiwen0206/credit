package com.starsgroupchina.credit.server.service;

import com.google.common.collect.Lists;
import com.starsgroupchina.common.XStatus;
import com.starsgroupchina.common.base.AbstractService;
import com.starsgroupchina.common.exception.AppException;
import com.starsgroupchina.credit.bean.UserInfo;
import com.starsgroupchina.credit.bean.mapper.BlacklistMapper;
import com.starsgroupchina.credit.bean.model.Blacklist;
import com.starsgroupchina.credit.bean.model.BlacklistExample;
import com.starsgroupchina.credit.key.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zhangfeng on 2018-5-9.
 */
@Slf4j
@Service
public class BlacklistService extends AbstractService<Blacklist,BlacklistExample> {

    @Autowired
    private BlacklistMapper blacklistMapper;

    @Override
    public Blacklist create(Blacklist blacklist) {
        BlacklistExample blacklistExample = new BlacklistExample();
        BlacklistExample.Criteria criteria = blacklistExample.createCriteria();
        criteria.andUserTypeEqualTo(blacklist.getUserType());
        criteria.andStatusEqualTo(XStatus.FORBIDDEN.key());
        criteria.andNameEqualTo(blacklist.getName());
        if ("个人".equals(blacklist.getUserType())) {
            criteria.andIdCardEqualTo(blacklist.getIdCard());
        }
        if (count(blacklistExample) > 0) {
            throw new AppException(ErrorCode.BLACKLIST_USER_WAS_EXISTS);
        }
        blacklistMapper.insertSelective(blacklist);
        return blacklist;
    }

    public List<Blacklist> getBlacklistHit(UserInfo userInfo) {
        List<Blacklist> result = Lists.newArrayList();
        BlacklistExample blacklistExample = new BlacklistExample();
        blacklistExample.setOrderByClause("create_time desc");
        String companyName = userInfo.getCompanyName();
        String idCard = userInfo.getIdCard();
        if(StringUtils.isEmpty(companyName)&&StringUtils.isEmpty(idCard)){
            return result;
        }
        if(StringUtils.isNotEmpty(companyName)) {
            blacklistExample.createCriteria().andNameEqualTo(companyName);
            List<Blacklist> blacklists = blacklistMapper.selectByExample(blacklistExample);
            if(CollectionUtils.isNotEmpty(blacklists)){
                result.addAll(blacklists);
            }
        }
        if(StringUtils.isNotEmpty(idCard)) {
            blacklistExample.clear();
            blacklistExample.createCriteria().andIdCardEqualTo(idCard);
            List<Blacklist> blacklists = blacklistMapper.selectByExample(blacklistExample);
            if(CollectionUtils.isNotEmpty(blacklists)){
                result.addAll(blacklists);
            }
        }
        return result;
    }

    public boolean validBlacklist(String idCard) {
        BlacklistExample blacklistExample = new BlacklistExample();
        if(StringUtils.isNotEmpty(idCard)) {
            blacklistExample.createCriteria().andIdCardEqualTo(idCard);
            List<Blacklist> blacklists = blacklistMapper.selectByExample(blacklistExample);
            return CollectionUtils.isEmpty(blacklists);
        }
        return true;
    }
}
