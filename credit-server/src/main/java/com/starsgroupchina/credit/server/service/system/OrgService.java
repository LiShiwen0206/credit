package com.starsgroupchina.credit.server.service.system;

import com.google.common.collect.Lists;
import com.starsgroupchina.common.base.AbstractService;
import com.starsgroupchina.credit.bean.model.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


/**
 * Created by zhangfeng
 */
@Slf4j
@Service
public class OrgService extends AbstractService<Org, OrgExample> {

    public String getOrgLayer(Integer id, String layer) {
        if (id == 0) return layer.substring(0, layer.length() - 1);
        Org org = this.getById(id);
        if (org != null) {
            layer = org.getName() + "&" + layer;
            return getOrgLayer(org.getParentId(), layer);
        }
        return Strings.EMPTY;
    }

    /**
     * @Describe : 根据orgId获得所有下级机构得id
     * @Author : lishiwen
     * @Date : 2018/12/19
     */
    public void getAllChildOrgId(Integer orgId, List<Integer> orgIds){
        orgIds.add(orgId);
        OrgExample orgExample = new OrgExample();
        OrgExample.Criteria orgCriteria = orgExample.createCriteria();
        orgCriteria.andParentIdEqualTo(orgId);
        List<Org> orgs= this.query(orgExample).collect(Collectors.toList());
        if(orgs!=null){
            orgs.forEach(org -> getAllChildOrgId(org.getId(), orgIds));
        }
    }


    /*******************************************************/
    /***************** org  file  service  *****************/
    /*******************************************************/
    @Service
    public class OrgFileService extends AbstractService<OrgFile,OrgFileExample> {

    }

    @Service
    public class OrgReportConfigService extends AbstractService<OrgReportConfig,OrgReportConfigExample>{

    }

}
