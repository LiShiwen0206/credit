package com.starsgroupchina.credit.server.service.project;

import com.starsgroupchina.common.base.AbstractService;
import com.starsgroupchina.credit.bean.model.WebsiteAudit;
import com.starsgroupchina.credit.bean.model.WebsiteAuditExample;
import com.starsgroupchina.credit.bean.model.WebsiteAuditPic;
import com.starsgroupchina.credit.bean.model.WebsiteAuditPicExample;
import org.springframework.stereotype.Service;

@Service
public class WebsiteService extends AbstractService<WebsiteAudit, WebsiteAuditExample> {

//    @Override
//    public long count(WebsiteAuditExample example) {
//        example.getOredCriteria().stream()
//                .findFirst()
//                .ifPresent(criteria -> criteria.andStatusNotEqualTo(XStatus.DELETE.key()));
//        return websiteAuditMapper.countByExample(example);
//    }
//
//    @Override
//    public Stream<WebsiteAudit> query(WebsiteAuditExample example) {
//        example.getOredCriteria().stream()
//                .findFirst()
//                .ifPresent(criteria -> criteria.andStatusNotEqualTo(XStatus.DELETE.key()));
//        return websiteAuditMapper.selectByExample(example).stream();
//    }

    /*******************************************************/
    /********** website audit picture *****************/
    /*******************************************************/
    @Service
    public class WebsiteAuditPicService extends AbstractService<WebsiteAuditPic, WebsiteAuditPicExample> {

    }
}
