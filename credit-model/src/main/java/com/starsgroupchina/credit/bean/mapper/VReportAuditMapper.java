package com.starsgroupchina.credit.bean.mapper;

import com.starsgroupchina.common.base.BaseMapper;
import com.starsgroupchina.credit.bean.model.VReportAudit;
import com.starsgroupchina.credit.bean.model.VReportAuditExample;

import java.util.List;

/**
 * Created by gexiaoshan on 2018/9/5.
 */
public interface VReportAuditMapper extends BaseMapper<VReportAudit,VReportAuditExample> {

    List<VReportAudit> selectByExampleWithBLOBs(VReportAuditExample example);
}
