package com.starsgroupchina.credit.server.service.project;

import com.starsgroupchina.common.base.AbstractService;
import com.starsgroupchina.credit.bean.enums.DeclareStatus;
import com.starsgroupchina.credit.bean.model.ProjectRiskDeclare;
import com.starsgroupchina.credit.bean.model.ProjectRiskDeclareRecord;
import com.starsgroupchina.credit.bean.model.ProjectRiskDeclareRecordExample;
import org.springframework.stereotype.Service;

/**
 * @Author: QinHaoHao
 * @Description:
 * @Date: Created in 16:26 2018/8/17
 * @Modifed By:
 */
@Service
public class RiskDeclareRecordService extends AbstractService<ProjectRiskDeclareRecord, ProjectRiskDeclareRecordExample> {

    public void saveRiskRecord(ProjectRiskDeclare riskDeclare, DeclareStatus declareStatus) {
//        if (riskDeclare.getDeclareSource().equals(DeclareSource.AUDIT.key()) || riskDeclare.getDeclareSource().equals(DeclareSource.RECHECK.key())) {
        ProjectRiskDeclareRecord riskDeclareRecord = new ProjectRiskDeclareRecord();
        riskDeclareRecord.setDeclareNo(riskDeclare.getDeclareNo());
        riskDeclareRecord.setProjectNo(riskDeclare.getProjectNo());
        if (declareStatus == DeclareStatus.FIRST_HANDLE_RECOMMIT_TWO_HANDLE || declareStatus == DeclareStatus.DECLARE_TWO_HANDLE) {
            riskDeclareRecord.setSurveyBehavior(riskDeclare.getTwoSurveyBehavior());
            riskDeclareRecord.setSurveyLevel(riskDeclare.getTwoSurveyLevel());
            riskDeclareRecord.setSurveyRemark(riskDeclare.getTwoSurveyRemark());
            riskDeclareRecord.setSurveyResult(riskDeclare.getTwoSurveyResult());
        } else {
            riskDeclareRecord.setSurveyBehavior(riskDeclare.getFirstSurveyBehavior());
            riskDeclareRecord.setSurveyLevel(riskDeclare.getFirstSurveyLevel());
            riskDeclareRecord.setSurveyRemark(riskDeclare.getFirstSurveyRemark());
            riskDeclareRecord.setSurveyResult(riskDeclare.getFirstSurveyResult());
        }
        riskDeclareRecord.setDeclareSource(riskDeclare.getDeclareSource());
        riskDeclareRecord.setStatus(declareStatus.key());
        this.create(riskDeclareRecord);
//        }
    }
}
