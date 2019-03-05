package com.starsgroupchina.credit.server.service.report;

import com.starsgroupchina.common.base.AbstractService;
import com.starsgroupchina.credit.bean.mapper.VReportCreditMapper;
import com.starsgroupchina.credit.bean.model.VReportCredit;
import com.starsgroupchina.credit.bean.model.VReportCreditExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by gexiaoshan on 2018/9/3.
 */
@Service
public class VReportCreditService extends AbstractService<VReportCredit,VReportCreditExample> {

    @Autowired
    private VReportCreditMapper vReportCreditMapper;

    public List<VReportCredit> queryWithBlob(VReportCreditExample example) {
        return vReportCreditMapper.selectByExampleWithBLOBs(example);
    }
}
