package com.starsgroupchina.credit.server.service.project;

import com.starsgroupchina.common.base.AbstractService;
import com.starsgroupchina.common.context.ContextHolder;
import com.starsgroupchina.common.utils.Utils;
import com.starsgroupchina.credit.bean.AuthMember;
import com.starsgroupchina.credit.bean.enums.AuditStatus;
import com.starsgroupchina.credit.bean.enums.ProjectStatus;
import com.starsgroupchina.credit.bean.mapper.ProjectMapper;
import com.starsgroupchina.credit.bean.model.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Supplier;

import static com.starsgroupchina.credit.bean.enums.ProjectStatus.*;

/**
 * Created by zhangfeng on 2018/6/6
 */
@Slf4j
@Service
public class ProjectService extends AbstractService<Project, ProjectExample> {

    @Autowired
    private ProjectMapper projectMapper;

    @Override
    public Project update(Project project) {
        ProjectExample example = new ProjectExample();
        example.createCriteria().andProjectNoEqualTo(project.getProjectNo());
        projectMapper.updateByExampleSelective(project, example);
        return project;
    }

    public Project updateByPrimaryKeySelective(Project project) {
        projectMapper.updateByPrimaryKeySelective(project);
        return project;
    }

    public Project updateByPrimaryKey(Project project) {
        projectMapper.updateByPrimaryKey(project);
        return project;
    }

    public Project getProjectByProjectNo(String projectNo) {
        ProjectExample example = new ProjectExample();
        example.createCriteria().andProjectNoEqualTo(projectNo);
        return Utils.getFirst(projectMapper.selectByExample(example));
    }

    public Project updateStatus(String projectNo, ProjectStatus status) {
        Project project = new Project();
        project.setProjectNo(projectNo);
        project.setStatus(status.key());
        return update(project);
    }

    //设置进入审核件池时间
    public Project updateBeginAuditPoolDate(String projectNo) {
        Project project = new Project();
        project.setProjectNo(projectNo);
        project.setBeginAuditPoolDate(new Date());
        return update(project);
    }
    //设置审核结束时间
    public Project updateEndAuditdDate(String projectNo) {
        Project project = new Project();
        project.setProjectNo(projectNo);
        project.setEndAuditDate(new Date());
        return update(project);
    }

    //设置审核结束时间
    public Project updateEndAuditdDateDefault(String projectNo) {
        Project project = new Project();
        project.setProjectNo(projectNo);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date myDate = null;
        try {
            myDate  = dateFormat.parse("2018-01-01 00:00:00");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        project.setEndAuditDate(myDate);
        return update(project);
    }
    //设置进入审核件池时间
    public void updateBeginAuditPoolDateDefault(String projectNo) {
        Project project = new Project();
        project.setProjectNo(projectNo);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date myDate = null;
        try {
            myDate  = dateFormat.parse("2018-01-01 00:00:00");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        project.setBeginAuditPoolDate(myDate);
        update(project);
    }
    //设置进入复核时间
//    public void updateBeginRecheckdDate(String projectNo) {
//        Project project = new Project();
//        project.setProjectNo(projectNo);
//        project.setBeginRecheckDate(new Date());
//        update(project);
//    }

    //设置复核结束时间
    public void updateEndRecheckdDateDefault(String projectNo) {
        Project project = new Project();
        project.setProjectNo(projectNo);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date myDate = null;
        try {
            myDate  = dateFormat.parse("2018-01-01 00:00:00");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        project.setEndRecheckDate(myDate);
        update(project);
    }

    //设置复核结束时间
    public void updateEndRecheckdDate(String projectNo) {
        Project project = new Project();
        project.setProjectNo(projectNo);
        project.setEndRecheckDate(new Date());
        update(project);
    }
    //审核结束
    public Project updateAuditOverStatus(String projectNo, ProjectStatus status) {
        Project project = new Project();
        project.setProjectNo(projectNo);
        project.setStatus(status.key());
        project.setAuditOverTime(new Date());
        return update(project);
    }

    public Project updateAuditStatus(String projectNo, AuditStatus auditStatus) {
        Project project = new Project();
        project.setProjectNo(projectNo);
        project.setAuditStatus(auditStatus.key());
        return update(project);
    }

    public Project updateAuditAmount(String projectNo, ProjectRecord record) {
        Project project = new Project();
        project.setProjectNo(projectNo);
        project.setApproveAmount(new BigDecimal(record.getApproveAmount()));
        project.setApproveLoanDuration(new BigDecimal(record.getApproveLoanDuration()));
        project.setApproveLoanDurationDay(record.getApproveLoanDurationDay());
        project.setApproveLoanUnit(record.getApproveLoanUnit());
        project.setApproveRemark(record.getAuditRemark());
        return update(project);
    }

    public Project updateAuditRecheckAmount(String projectNo, ProjectRecord record) {
        Project project = new Project();
        project.setProjectNo(projectNo);
        project.setAuditRecheckAmount(new BigDecimal(record.getApproveAmount()));
        project.setAuditRecheckDuration(new BigDecimal(record.getApproveLoanDuration()));
        project.setAuditRecheckDurationDay(record.getApproveLoanDurationDay());
        project.setAuditRecheckUnit(record.getApproveLoanUnit());
        project.setAuditRecheckRemark(record.getAuditRemark());
        return update(project);
    }

    //进件列表
    public Supplier<String> entry = () ->
            String.format(" status in (%s,%s,%s) and create_user_id=%s",
                    ENTRY.key(), CROSS_BACK_ENTRY.key(), AUDIT_LABOR_BACK_ENTRY.key(), ((AuthMember)ContextHolder.getContext().getData()).getMember().getId());
    //交叉互审
    public Supplier<String> cross = () ->
            String.format(" status in (%s,%s) and audit_cross_user_id=%s",
                    CROSS.key(), ENTRY_RECOMMIT_CROSS.key(), ((AuthMember) ContextHolder.getContext().getData()).getMember().getId());
    //进件查询
//    public BiFunction<Integer, List<Integer>, String> entryQuery = (memberId, roleIds) ->
//            String.format(" (create_user_id=%s or audit_cross_user_id=%s or create_user_role in (%s) or cross_user_role in (%s))",
//                    memberId, memberId, StringUtils.join(roleIds.toArray(), ","), StringUtils.join(roleIds.toArray(), ","));
    //进件查询
//    or create_user_role in (%s) or cross_user_role in (%s)
//    StringUtils.join(roleIds.toArray(), ","), StringUtils.join(roleIds.toArray(), ","),
    public BiFunction<List<Integer>, List<Integer>, String> entryQuery = (workGroups, roleIds) ->
            String.format(" (create_user_id=%s or audit_cross_user_id=%s or workgroup_id in (%s))",
                    ((AuthMember) ContextHolder.getContext().getData()).getMember().getId(), ((AuthMember) ContextHolder.getContext().getData()).getMember().getId(), StringUtils.join(workGroups.toArray(), ","));

    //审核件池
    public Supplier<String> auditList = () -> {
        if (((AuthMember) ContextHolder.getContext().getData()).getIsAuditLeader() == 1) {
            return String.format(" status in (%s,%s,%s,%s) and audit_leader like '%s' ",
                    AUDIT_LIST.key(), AUDIT_LABOR.key(), ENTRY_RECOMMIT_AUDIT_LABOR.key(), AUDIT_RECHECK_LABOR_BACK_AUDIT_LABOR.key(),
                    "%," + ((AuthMember) ContextHolder.getContext().getData()).getRole().getId() + ",%");
        } else {
            return String.format(" status in (%s,%s,%s,%s) ",
                    AUDIT_LIST.key(), AUDIT_LABOR.key(), ENTRY_RECOMMIT_AUDIT_LABOR.key(), AUDIT_RECHECK_LABOR_BACK_AUDIT_LABOR.key());
        }
    };

    //人工审核
    public Supplier<String> auditLabor = () ->
            String.format(" status in (%s,%s,%s) and audit_user_id=%s",
                    AUDIT_LABOR.key(), ENTRY_RECOMMIT_AUDIT_LABOR.key(), AUDIT_RECHECK_LABOR_BACK_AUDIT_LABOR.key(),
                    ((AuthMember) ContextHolder.getContext().getData()).getMember().getId());

    //复核件池
    public Supplier<String> auditRecheckList = () -> {
        if (((AuthMember) ContextHolder.getContext().getData()).getIsRecheckLeader() == 1) {
            return String.format(" status in (%s,%s,%s) and audit_recheck_leader like '%s'",
                    AUDIT_RECHECK_LIST.key(), AUDIT_RECHECK_LABOR.key(), AUDIT_LABOR_RECOMMIT_AUDIT_RECHECK_LABOR.key(),
                    "%," + ((AuthMember) ContextHolder.getContext().getData()).getRole().getId() + ",%");
        } else {
            return String.format("status in (%s,%s,%s)", AUDIT_RECHECK_LIST.key(), AUDIT_RECHECK_LABOR.key(), AUDIT_LABOR_RECOMMIT_AUDIT_RECHECK_LABOR.key());
        }
    };

    //人工复核
    public Supplier<String> auditRecheckLabor = () ->
            String.format(" status in (%s,%s) and audit_recheck_user_id=%s",
                    AUDIT_RECHECK_LABOR.key(), AUDIT_LABOR_RECOMMIT_AUDIT_RECHECK_LABOR.key(),
                    ((AuthMember) ContextHolder.getContext().getData()).getMember().getId());

    //风控报告列表
    public Supplier<String> riskReport = () -> String.format(" status = %s  ", CREDIT_REPORT.key());
//    public Supplier<String> riskReportExternal = () -> ;
    public String riskReportExternal(String param){
      return  String.format(" status = %s and (create_user_id=%s or audit_cross_user_id=%s or workgroup_id in %s)",
                CREDIT_REPORT.key(), ((AuthMember) ContextHolder.getContext().getData()).getMember().getId(), ((AuthMember) ContextHolder.getContext().getData()).getMember().getId(),param);
    }
    @Service
    public class VProjectService extends AbstractService<VProject, VProjectExample> {

    }
}
