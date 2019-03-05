package com.starsgroupchina.credit.bean.mapper;

import com.starsgroupchina.common.base.BaseMapper;
import com.starsgroupchina.credit.bean.model.VReportCredit;
import com.starsgroupchina.credit.bean.model.VReportCreditExample;

import java.util.List;

/**
 * Created by gexiaoshan on 2018/9/3.
 */
public interface VReportCreditMapper extends BaseMapper<VReportCredit, VReportCreditExample> {

    List<VReportCredit> selectByExampleWithBLOBs(VReportCreditExample example);
}
