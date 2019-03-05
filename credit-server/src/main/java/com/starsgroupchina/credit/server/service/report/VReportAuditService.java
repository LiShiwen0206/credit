package com.starsgroupchina.credit.server.service.report;

import com.starsgroupchina.common.base.AbstractService;
import com.starsgroupchina.credit.bean.mapper.VReportAuditMapper;
import com.starsgroupchina.credit.bean.model.VReportAudit;
import com.starsgroupchina.credit.bean.model.VReportAuditExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by gexiaoshan on 2018/9/5.
 */
@Service
public class VReportAuditService extends AbstractService<VReportAudit,VReportAuditExample> {

    @Autowired
    private VReportAuditMapper vReportAuditMapper;

    public List<VReportAudit> queryWithBlob(VReportAuditExample example) {
        return vReportAuditMapper.selectByExampleWithBLOBs(example);
    }
}
