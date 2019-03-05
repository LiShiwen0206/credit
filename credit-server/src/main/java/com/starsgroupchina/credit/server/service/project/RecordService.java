package com.starsgroupchina.credit.server.service.project;

import com.starsgroupchina.common.base.AbstractService;
import com.starsgroupchina.common.context.ContextHolder;
import com.starsgroupchina.credit.bean.AuthMember;
import com.starsgroupchina.credit.bean.enums.ProjectStatus;
import com.starsgroupchina.credit.bean.model.ProjectRecord;
import com.starsgroupchina.credit.bean.model.ProjectRecordExample;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by zhangfeng on 2018-6-13.
 */
@Slf4j
@Service
public class RecordService extends AbstractService<ProjectRecord, ProjectRecordExample> {

    public void record(String projectNo, ProjectStatus status, String result) {
        record(projectNo, status, result, Strings.EMPTY);
    }

    public void record(String projectNo, ProjectStatus status, String result, String remark) {
        record(projectNo, status, result, 0.0, remark);
    }

    public void record(String projectNo, ProjectStatus point, String result, Double amount, String remark) {
        AuthMember authMember= (AuthMember) ContextHolder.getContext().getData();
        ProjectRecord record = new ProjectRecord();
        record.setProjectNo(projectNo);
        record.setAuditMemberId(authMember.getMember().getId());
        record.setAuditResult(result);
        record.setApproveAmount(amount);
        record.setAuditRemark(remark);
        record.setAuditDate(new Date());
        record.setAuditNode(point.value());
        this.create(record);
    }

}
