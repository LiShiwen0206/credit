package com.starsgroupchina.credit.server.service.project;

import com.starsgroupchina.common.base.AbstractService;
import com.starsgroupchina.common.utils.Utils;
import com.starsgroupchina.credit.bean.model.OrgReportConfig;
import com.starsgroupchina.credit.bean.model.OrgReportConfigExample;
import org.springframework.stereotype.Service;

/**
 * @Author: QinHaoHao
 * @Description:
 * @Date: Created in 16:25 2018/8/17
 * @Modifed By:
 */
@Service
public class OrgReportConfigService extends AbstractService<OrgReportConfig, OrgReportConfigExample> {

    public OrgReportConfig getConfigByOrgId(Integer orgId){
        OrgReportConfigExample example = new OrgReportConfigExample();
        example.createCriteria().andOrgIdEqualTo(orgId);
       return Utils.getFirst(this.query(example));
    }
}
