package com.starsgroupchina.credit.server.controller.project;

import com.google.common.collect.Lists;
import com.starsgroupchina.common.context.ContextHolder;
import com.starsgroupchina.common.exception.AppException;
import com.starsgroupchina.common.objects.If;
import com.starsgroupchina.common.response.ListResponse;
import com.starsgroupchina.common.response.SimpleResponse;
import com.starsgroupchina.common.utils.Utils;
import com.starsgroupchina.credit.bean.AuthMember;
import com.starsgroupchina.credit.bean.UserInfo;
import com.starsgroupchina.credit.bean.enums.AuditStatus;
import com.starsgroupchina.credit.bean.enums.BuildReportSource;
import com.starsgroupchina.credit.bean.enums.DeclareSource;
import com.starsgroupchina.credit.bean.enums.ProjectStatus;
import com.starsgroupchina.credit.bean.extend.ProjectExtend;
import com.starsgroupchina.credit.bean.model.*;
import com.starsgroupchina.credit.key.ErrorCode;
import com.starsgroupchina.credit.key.MessageTemplate;
import com.starsgroupchina.credit.server.service.BlacklistService;
import com.starsgroupchina.credit.server.service.CategoryService;
import com.starsgroupchina.credit.server.service.PolicyService;
import com.starsgroupchina.credit.server.service.ProductService;
import com.starsgroupchina.credit.server.service.project.*;
import com.starsgroupchina.credit.server.service.system.MessageService;
import com.starsgroupchina.credit.server.service.system.OrgService;
import com.starsgroupchina.credit.server.service.third.ThirdCreditResultService;
import com.starsgroupchina.credit.server.service.third.ThirdDataValidService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

/**
 * Created by zhangfeng on 2018/6/6
 */
@Slf4j
@RestController
@Api(tags = "CREDIT-SWAGGER14", description = "【项目】 - ProjectController")
@RequestMapping(value = "/project", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class ProjectController {

    final String SUBMIT = "提交";
    final String ROLLBACK = "回退";
    final String ALLOW = "通过";

    @Autowired
    private ReportService reportService;
    @Autowired
    private RecordService recordService;
    @Autowired
    private ProjectService projectService;
    @Autowired
    private ProjectService.VProjectService vProjectService;
    @Autowired
    private ProductService productService;
    @Autowired
    private OrgService orgService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private PolicyService policyService;
    @Autowired
    private UserService userService;
    @Autowired
    private ThirdCreditResultService thirdService;
    @Autowired
    private ScoreService scoreService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private RiskDeclareService riskDeclareService;

    @Autowired
    private BlacklistService blacklistService;

    @Autowired
    private ThirdDataValidService thirdDataValidService;

    @ApiResponses({@ApiResponse(code = ErrorCode.AUTH_DATA_NULL, message = "当前用户数据权限为空")})
    @ApiOperation("项目列表")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ListResponse<ProjectExtend> query(
            @RequestParam(value = "index", defaultValue = "1") int index,
            @RequestParam(value = "limit", defaultValue = "20") int limit,
            @ApiParam("进件编号") @RequestParam(value = "projectNo", required = false) String projectNo,
            @ApiParam("客户姓名") @RequestParam(value = "userName", required = false) String userName,
            @ApiParam("身份证") @RequestParam(value = "userIdCard", required = false) String userIdCard,
            @ApiParam("产品id") @RequestParam(value = "productId", required = false) String productId,
            @ApiParam("机构") @RequestParam(value = "orgId", required = false) String orgId,
            @ApiParam("申请贷款金额start") @RequestParam(value = "loanAmountStart", defaultValue = "1") Double loanAmountStart,
            @ApiParam("申请贷款金额end") @RequestParam(value = "loanAmountEnd", defaultValue = "10000000") Double loanAmountEnd,
            @ApiParam("开始审核时间start") @RequestParam(value = "beginAuditStart", defaultValue = "1451630690") Long beginAuditStart,
            @ApiParam("开始审核时间end") @RequestParam(value = "beginAuditEnd", defaultValue = "1895538782000") Long beginAuditEnd,
            @ApiParam("所属机构ID（总公司ID）") @RequestParam(value = "headOrgId", required = false) String headOrgId,
            @ApiParam("menu key") @RequestParam(value = "menu") String menu,
            @ApiParam("1进件录入，2交叉质检，3进件查询，5审核件池，6人工审核，7复核件池，8人工复核,999志诚报告")
            @RequestParam(value = "status") Short status,
            HttpServletRequest request) {
        AuthMember authMember = (AuthMember) ContextHolder.getContext().getData();
        List<Integer> roleDatas = authMember.getRoleDatas().stream()
                .filter(roleData -> roleData.getMenuKey().equals(menu))
                .map(RoleData::getChildRoleId).collect(toList());

        If.of(CollectionUtils.isEmpty(roleDatas)).isTrueThrow(() -> new AppException(ErrorCode.AUTH_DATA_NULL));

        //如果只有一个数据角色，说明不是继承关系，则清除角色数据域， 只用不同环节的user_id判断
        if (roleDatas.size() == 1) {
            roleDatas.clear();
            roleDatas.add(-1);
        }

        VProjectExample example = new VProjectExample();
        VProjectExample.Criteria criteria = example.createCriteria();
        If.of(StringUtils.isNotEmpty(projectNo)).isTrue(() -> criteria.andProjectNoLike("%" + projectNo.trim() + "%"));
        If.of(StringUtils.isNotEmpty(userName)).isTrue(() -> criteria.andUserNameLike("%" + userName.trim() + "%"));
        If.of(StringUtils.isNotEmpty(userIdCard)).isTrue(() -> criteria.andUserIdCardLike("%" + userIdCard.trim() + "%"));
        If.of(StringUtils.isNotEmpty(productId)).isTrue(() -> criteria.andProductIdEqualTo(Integer.valueOf(productId.trim())));
        If.of(StringUtils.isNotEmpty(orgId)).isTrue(() -> criteria.andOrgIdEqualTo(Integer.valueOf(orgId.trim())));
        If.of(StringUtils.isNotEmpty(headOrgId)).isTrue(() -> criteria.andHeadOrgIdEqualTo(Integer.valueOf(headOrgId.trim())));
        criteria.andLoanAmountBetween(new BigDecimal(loanAmountStart), new BigDecimal(loanAmountEnd));
        if (beginAuditStart != 1451630690L || beginAuditEnd != 1895538782000L) {
            Date dateStart = new Date(Instant.ofEpochMilli(beginAuditStart).toEpochMilli());
            Date dateEnd = new Date(Instant.ofEpochMilli(beginAuditEnd).toEpochMilli());
            criteria.andBeginAuditDateBetween(dateStart, dateEnd);
        }
        //进件列表
        if (ProjectStatus.ENTRY.key().equals(status))
            example.setAdditionalWhere(projectService.entry.get());

        //交叉互审
        if (ProjectStatus.CROSS.key().equals(status))
            example.setAdditionalWhere(projectService.cross.get());

        //进件查询
        if (ProjectStatus.ENTRY_LIST.key().equals(status)) {
            List<Integer> workGroupIds = authMember.getWorkGroupIds();
            if (CollectionUtils.isEmpty(workGroupIds)) {
            workGroupIds.add(-1);
            }
            example.setAdditionalWhere(projectService.entryQuery.apply(workGroupIds, roleDatas));
        }

        //自动审核
        if (ProjectStatus.AUDIT_AUTO.key().equals(status)) {
        }

        //审核件池
        if (ProjectStatus.AUDIT_LIST.key().equals(status))
            example.setAdditionalWhere(projectService.auditList.get());

        //人工审核
        if (ProjectStatus.AUDIT_LABOR.key().equals(status))
            example.setAdditionalWhere(projectService.auditLabor.get());

        //复核件池
        if (ProjectStatus.AUDIT_RECHECK_LIST.key().equals(status))
            example.setAdditionalWhere(projectService.auditRecheckList.get());

        //人工复核
        if (ProjectStatus.AUDIT_RECHECK_LABOR.key().equals(status))
            example.setAdditionalWhere(projectService.auditRecheckLabor.get());

        //志诚报告
        String inner = request.getHeader("inner");
        if (ProjectStatus.CREDIT_REPORT.key().equals(status)){
            if("1".equals(inner)){//代表是志诚审核系统
                example.setAdditionalWhere(projectService.riskReport.get());
            } else {//外部的
                List<Integer> workGroupIds = authMember.getWorkGroupIds();
                    StringBuilder param=new StringBuilder();
                    param.append("( ");
                    for (Integer workGroupId : workGroupIds) {
                        param.append(workGroupId+",");
                    }
                    param.append("-1 )");
                    example.setAdditionalWhere(projectService.riskReportExternal(param.toString()));
            }
        }

        long count = vProjectService.count(example);

        example.setOrderByClause("create_time  desc");
        example.setOffset((index - 1) * limit);
        example.setLimit(limit);

        List<ProjectExtend> result = Lists.newArrayList();
        Optional.ofNullable(vProjectService.query(example)).ifPresent(projects ->
                        projects.forEach(project -> {
                            ProjectExtend projectExtend = new ProjectExtend(project);
                            Product product = productService.getById(project.getProductId());
                            UserInfo userInfo = userService.getUserInfo(project.getProjectNo());
                            Org org = orgService.getById(project.getOrgId());
                            Category category = categoryService.getById(project.getCategoryId());
                            Policy policy = policyService.getById(project.getPolicyId());
                            ProjectRiskDeclare riskDeclare = null;
                            long riskDeclareCount = 0;
                            if (ProjectStatus.AUDIT_LABOR.key().equals(status)) {
                                riskDeclare = riskDeclareService.getRiskDeclare(project.getProjectNo(), DeclareSource.AUDIT);
                                riskDeclareCount = riskDeclareService.getRiskDeclareCount(project.getProjectNo(), DeclareSource.AUDIT);
                            }
                            if (ProjectStatus.AUDIT_RECHECK_LABOR.key().equals(status)) {
                                riskDeclare = riskDeclareService.getRiskDeclare(project.getProjectNo(), DeclareSource.RECHECK);
                                riskDeclareCount = riskDeclareService.getRiskDeclareCount(project.getProjectNo(), DeclareSource.RECHECK);
                            }
                            projectExtend.setRiskDeclareCount(riskDeclareCount);
                            projectExtend.setProduct(product);
                            projectExtend.setUserInfo(userInfo);
                            projectExtend.setOrg(org);
                            projectExtend.setCategory(category);
                            projectExtend.setPolicy(policy);
                            projectExtend.setRiskDeclare(riskDeclare);
//                    checkAudit(projectExtend);
                            result.add(projectExtend);
                        })
        );
        return ListResponse.success(result, count, index, limit);
    }

//    private void checkAudit(ProjectExtend projectExtend) {
//        if (DateUtil.yyyyMMddFormatter.get().format(projectExtend.getBeginAuditDate()).equals("2018-01-01")) {
//            projectExtend.setBeginAuditDate(null);
//        }
//    }

    /**
     * 风险客户申报中获取项目
     *
     * @param index
     * @param limit
     * @param projectNo
     * @param userName
     * @param userIdCard
     * @param orgId
     * @return
     */
    @ApiOperation("风险申报获取项目列表")
    @RequestMapping(value = "/declare/list", method = RequestMethod.GET)
    public ListResponse<ProjectExtend> query(
            @RequestParam(value = "index", defaultValue = "1") int index,
            @RequestParam(value = "limit", defaultValue = "20") int limit,
            @ApiParam("进件编号") @RequestParam(value = "projectNo", required = false) String projectNo,
            @ApiParam("客户姓名") @RequestParam(value = "userName", required = false) String userName,
            @ApiParam("身份证") @RequestParam(value = "userIdCard", required = false) String userIdCard,
            @ApiParam("机构") @RequestParam(value = "orgId", required = false) String orgId,
            @ApiParam("进件时间start") @RequestParam(value = "applyDateStart", defaultValue = "1451630690") Long applyDateStart,
            @ApiParam("进件时间end") @RequestParam(value = "applyDateEnd", defaultValue = "1895538782000") Long applyDateEnd) {

        VProjectExample example = new VProjectExample();
        VProjectExample.Criteria criteria = example.createCriteria();
        ProjectRiskDeclareExample riskDeclareExample = new ProjectRiskDeclareExample();
        riskDeclareExample.setAdditionalWhere(" two_survey_result='是风险客户' or status<>999");
        List<ProjectRiskDeclare> riskDeclares = riskDeclareService.query(riskDeclareExample).collect(toList());
        List<String> projectNos = riskDeclares.stream().map(ProjectRiskDeclare::getProjectNo).distinct().collect(toList());
        If.of(CollectionUtils.isNotEmpty(projectNos)).isTrue(() -> criteria.andProjectNoNotIn(projectNos));
        If.of(StringUtils.isNotEmpty(projectNo)).isTrue(() -> criteria.andProjectNoLike("%" + projectNo.trim() + "%"));
        If.of(StringUtils.isNotEmpty(userName)).isTrue(() -> criteria.andUserNameLike("%" + userName.trim() + "%"));
        If.of(StringUtils.isNotEmpty(userIdCard)).isTrue(() -> criteria.andUserIdCardLike("%" + userIdCard.trim() + "%"));
        If.of(StringUtils.isNotEmpty(orgId)).isTrue(() -> criteria.andOrgIdEqualTo(Integer.valueOf(orgId.trim())));
        Date dateStart = new Date(Instant.ofEpochMilli(applyDateStart).toEpochMilli());
        Date dateEnd = new Date(Instant.ofEpochMilli(applyDateEnd).toEpochMilli());
        criteria.andApplyDateBetween(dateStart, dateEnd);
        long count = vProjectService.count(example);
        example.setOrderByClause("create_time  desc");
        example.setOffset((index - 1) * limit);
        example.setLimit(limit);
        List<ProjectExtend> result = Lists.newArrayList();
        Optional.ofNullable(vProjectService.query(example)).ifPresent(projects ->
                projects.forEach(project -> {
                    ProjectExtend projectExtend = new ProjectExtend(project);
                    UserInfo userInfo = userService.getUserInfo(project.getProjectNo());
                    projectExtend.setUserInfo(userInfo);
                    result.add(projectExtend);
                })
        );
        return ListResponse.success(result, count, index, limit);
    }

    @ApiOperation("根据项目编号 获取 项目")
    @RequestMapping(value = "/{projectNo}", method = RequestMethod.GET)
    public SimpleResponse<ProjectExtend> getProject(@PathVariable("projectNo") String projectNo) {
//        Project project = projectService.getProjectByProjectNo(projectNo);
        VProjectExample example = new VProjectExample();
        example.createCriteria().andProjectNoEqualTo(projectNo);
        VProject vProject = Utils.getFirst(vProjectService.query(example));
        ProjectExtend result = null;
        if (vProject != null) {
            result = new ProjectExtend(vProject);
            Product product = productService.getById(result.getProductId());
            result.setProduct(product);
        }
        return SimpleResponse.success(result);
    }

    /**
     * 创建项目（内部调用不对外）
     */
    @ApiOperation("创建项目（内部调用不对外）")
    @RequestMapping(value = "", method = RequestMethod.POST)
    @Transactional
    public SimpleResponse<Project> createProject(@RequestBody Project project) {
        project.setAuditStatus(AuditStatus.AUDIT_COMMIT_NO.key());
        projectService.create(project);
        recordService.record(project.getProjectNo(), ProjectStatus.ENTRY, ProjectStatus.ENTRY.value());
        return SimpleResponse.success(project);
    }

    /**
     * 修改项目（内部调用不对外）
     */
    @ApiResponses({@ApiResponse(code = ErrorCode.PROJECT_NOT_EXIST, message = "项目不存在")})
    @ApiOperation("修改项目（内部调用不对外）")
    @RequestMapping(value = "", method = RequestMethod.PUT)
    @Transactional
    public SimpleResponse<Project> updateProject(@RequestBody Project project) {
        Project _project = projectService.getProjectByProjectNo(project.getProjectNo());
        If.of(_project == null).isTrueThrow(() -> new AppException(ErrorCode.PROJECT_NOT_EXIST));
        Short status = _project.getStatus();
        if (ProjectStatus.ENTRY.key().equals(status)
                || ProjectStatus.CROSS_BACK_ENTRY.key().equals(status)
                || ProjectStatus.AUDIT_LABOR_BACK_ENTRY.key().equals(status)) {
            project.setAuditStatus(AuditStatus.AUDIT_COMMIT_NO.key());
        }
        projectService.update(project);
        return SimpleResponse.success(project);
    }

    @ApiResponses({@ApiResponse(code = ErrorCode.PROJECT_NOT_EXIST, message = "项目不存在")})
    @ApiOperation("项目提交")
    @RequestMapping(value = "/submit/{projectNo}", method = RequestMethod.POST)
    @Transactional
    public SimpleResponse submit(@PathVariable("projectNo") String projectNo, @RequestBody ProjectRecord record) {
        VProjectExample example = new VProjectExample();
        example.createCriteria().andProjectNoEqualTo(projectNo);
        VProject project = Utils.getFirst(vProjectService.query(example));
        If.of(project == null).isTrueThrow(() -> new AppException(ErrorCode.PROJECT_NOT_EXIST));

        Short status = project.getStatus();
        //进件
        if (ProjectStatus.ENTRY.key().equals(status) || AuditStatus.POLICY_ALLOW.key().equals(status)) {
            if (project.getAuditCross() == 1) { //是否交叉互审：1是；0否
                projectService.updateStatus(projectNo, ProjectStatus.CROSS);
                projectService.updateAuditStatus(projectNo, AuditStatus.AUDIT_LABOR_NO);
                recordService.record(projectNo, ProjectStatus.ENTRY, SUBMIT);
            } else {
                this.checkAuditManual(projectNo, project);
            }
        }
        //交叉互审
        if (ProjectStatus.CROSS.key().equals(status) || ProjectStatus.ENTRY_RECOMMIT_CROSS.key().equals(status)) {
            recordService.record(projectNo, ProjectStatus.CROSS, ALLOW);
            this.checkAuditManual(projectNo, project);
        }
        //人工审核
        if (ProjectStatus.AUDIT_LABOR.key().equals(status) || ProjectStatus.ENTRY_RECOMMIT_AUDIT_LABOR.key().equals(status)) {
            if (project.getAuditManualRecheck() != null && project.getAuditManualRecheck() == 1) {
                projectService.updateStatus(projectNo, ProjectStatus.AUDIT_RECHECK_LIST);
                projectService.updateAuditStatus(projectNo, AuditStatus.AUDIT_RECHECK_LABOR_NO);
                projectService.updateAuditAmount(projectNo, record);
                recordService.record(projectNo, ProjectStatus.AUDIT_LABOR, ALLOW, record.getApproveAmount(), record.getAuditRemark());
                projectService.updateEndAuditdDate(projectNo);
            } else {
                projectService.updateAuditStatus(projectNo, AuditStatus.AUDIT_LABOR_YES);
                projectService.updateAuditAmount(projectNo, record);
                projectService.updateEndAuditdDate(projectNo);
                recordService.record(projectNo, ProjectStatus.AUDIT_LABOR, ALLOW, record.getApproveAmount(), record.getAuditRemark());
                this.buildAuditReport(projectNo, project);
            }
        }
        //人工复核
        if (ProjectStatus.AUDIT_RECHECK_LABOR.key().equals(status)) {
            projectService.updateAuditStatus(projectNo, AuditStatus.AUDIT_RECHECK_LABOR_YES);
            projectService.updateAuditRecheckAmount(projectNo, record);
            recordService.record(projectNo, ProjectStatus.AUDIT_RECHECK_LABOR, ALLOW, record.getApproveAmount(), record.getAuditRemark());
            this.buildAuditReport(projectNo, project);
            projectService.updateEndRecheckdDate(projectNo);
        }
        /** 回退 —— 提交  */
        //交叉质检回退至进件录入   -  再次提交
        if (ProjectStatus.CROSS_BACK_ENTRY.key().equals(status)) {
            projectService.updateStatus(projectNo, ProjectStatus.ENTRY_RECOMMIT_CROSS);
            recordService.record(projectNo, ProjectStatus.ENTRY, SUBMIT);
        }
        //人工审核回退至进件录入   -  再次提交
        if (ProjectStatus.AUDIT_LABOR_BACK_ENTRY.key().equals(status)) {
            projectService.updateStatus(projectNo, ProjectStatus.ENTRY_RECOMMIT_AUDIT_LABOR);
            projectService.updateAuditStatus(projectNo, AuditStatus.AUDIT_COMMIT_NO);
            recordService.record(projectNo, ProjectStatus.ENTRY, SUBMIT);
        }
        //人工复核回退至人工审核   -  再次提交
        if (ProjectStatus.AUDIT_RECHECK_LABOR_BACK_AUDIT_LABOR.key().equals(status)) {
            projectService.updateStatus(projectNo, ProjectStatus.AUDIT_LABOR_RECOMMIT_AUDIT_RECHECK_LABOR);
            projectService.updateAuditStatus(projectNo, AuditStatus.AUDIT_RECHECK_LABOR_NO);
            projectService.updateAuditAmount(projectNo, record);
            projectService.updateEndAuditdDate(projectNo);
            recordService.record(projectNo, ProjectStatus.AUDIT_LABOR, ALLOW, record.getApproveAmount(), record.getAuditRemark());
        }
        //人工复核退回件重新提交 - 风控报告
        if (ProjectStatus.AUDIT_LABOR_RECOMMIT_AUDIT_RECHECK_LABOR.key().equals(status)) {
            recordService.record(projectNo, ProjectStatus.AUDIT_RECHECK_LABOR, ALLOW, record.getApproveAmount(), record.getAuditRemark());
            projectService.updateAuditRecheckAmount(projectNo, record);
            projectService.updateEndRecheckdDate(projectNo);
            this.buildAuditReport(projectNo, project);
        }
        return SimpleResponse.success("success");
    }

    @ApiResponses({@ApiResponse(code = ErrorCode.PROJECT_NOT_EXIST, message = "项目不存在")})
    @ApiOperation("项目回退")
    @RequestMapping(value = "/rollback/{projectNo}", method = RequestMethod.POST)
    @Transactional
    public SimpleResponse rollback(
            @ApiParam("项目编号") @PathVariable("projectNo") String projectNo,
            @RequestBody ProjectRecord record) {
        VProjectExample example = new VProjectExample();
        example.createCriteria().andProjectNoEqualTo(projectNo);
        VProject project = Utils.getFirst(vProjectService.query(example));
        If.of(project == null).isTrueThrow(() -> new AppException(ErrorCode.PROJECT_NOT_EXIST));

        Short status = project.getStatus();
        if (ProjectStatus.CROSS.key().equals(status) || ProjectStatus.ENTRY_RECOMMIT_CROSS.key().equals(status)) {
            projectService.updateStatus(projectNo, ProjectStatus.CROSS_BACK_ENTRY);
            recordService.record(projectNo, ProjectStatus.CROSS, ROLLBACK);
        }
        if (ProjectStatus.AUDIT_LABOR.key().equals(status) ||
                ProjectStatus.ENTRY_RECOMMIT_AUDIT_LABOR.key().equals(status) ||
                ProjectStatus.AUDIT_RECHECK_LABOR_BACK_AUDIT_LABOR.key().equals(status)) {
            projectService.updateStatus(projectNo, ProjectStatus.AUDIT_LABOR_BACK_ENTRY);
            projectService.updateAuditStatus(projectNo, AuditStatus.AUDIT_COMMIT_NO);
            projectService.updateAuditAmount(projectNo, record);
            projectService.updateBeginAuditPoolDateDefault(projectNo);
            recordService.record(projectNo, ProjectStatus.AUDIT_LABOR, ROLLBACK, record.getAuditRemark());
        }
        if (ProjectStatus.AUDIT_RECHECK_LABOR.key().equals(status) || ProjectStatus.AUDIT_LABOR_RECOMMIT_AUDIT_RECHECK_LABOR.key().equals(status)) {
            projectService.updateStatus(projectNo, ProjectStatus.AUDIT_RECHECK_LABOR_BACK_AUDIT_LABOR);
            projectService.updateAuditStatus(projectNo, AuditStatus.AUDIT_LABOR_NO);
            projectService.updateAuditRecheckAmount(projectNo, record);
            projectService.updateEndAuditdDateDefault(projectNo);
            recordService.record(projectNo, ProjectStatus.AUDIT_RECHECK_LABOR, ROLLBACK, record.getAuditRemark());
        }
        return SimpleResponse.success("success");
    }

    @ApiResponses({@ApiResponse(code = ErrorCode.PROJECT_NOT_EXIST, message = "项目不存在")})
    @ApiOperation("项目指派")
    @PutMapping(value = "/assign/{projectNo}/auditer/{memberId}")
    @Transactional
    public SimpleResponse assign(@PathVariable("projectNo") String projectNo,
                                 @PathVariable("memberId") Integer memberId) {
        VProjectExample example = new VProjectExample();
        example.createCriteria().andProjectNoEqualTo(projectNo);
        VProject project = Utils.getFirst(vProjectService.query(example));
        If.of(project == null).isTrueThrow(() -> new AppException(ErrorCode.PROJECT_NOT_EXIST));

        Short status = project.getStatus();

        if (ProjectStatus.AUDIT_LIST.key().equals(status) || ProjectStatus.AUDIT_LABOR.key().equals(status)) {
            Project _project = new Project();
            _project.setProjectNo(projectNo);
            _project.setAuditUserId(memberId);
            _project.setAuditStatus(AuditStatus.AUDIT_LABOR_NO.key());
            _project.setStatus(ProjectStatus.AUDIT_LABOR.key());
            _project.setBeginAuditDate(new Date());
            projectService.update(_project);
        }
        if (ProjectStatus.AUDIT_RECHECK_LIST.key().equals(status) || ProjectStatus.AUDIT_RECHECK_LABOR.key().equals(status)) {
            Project _project = new Project();
            _project.setProjectNo(projectNo);
            _project.setAuditRecheckUserId(memberId);
            _project.setAuditStatus(AuditStatus.AUDIT_RECHECK_LABOR_NO.key());
            _project.setStatus(ProjectStatus.AUDIT_RECHECK_LABOR.key());
            _project.setBeginRecheckDate(new Date());
            projectService.update(_project);
        }

        messageService.sendMessage(memberId, projectNo, "新任务提醒", MessageTemplate.AssgnTemplate);
        return SimpleResponse.success("success");
    }

    @ApiOperation("项目获取")
    @RequestMapping(value = "/receive", method = RequestMethod.GET)
    public SimpleResponse receive(@ApiParam("2交叉互审,6人工审核,8人工复核") @RequestParam(value = "status") Short status) {
        AuthMember authMember = (AuthMember) ContextHolder.getContext().getData();
        ProjectExample _example = new ProjectExample();
        //交叉互审
        if (ProjectStatus.CROSS.key().equals(status))
            _example.setAdditionalWhere(projectService.cross.get());
        //人工审核
        if (ProjectStatus.AUDIT_LABOR.key().equals(status))
            _example.setAdditionalWhere(projectService.auditLabor.get());
        //人工复核
        if (ProjectStatus.AUDIT_RECHECK_LABOR.key().equals(status))
            _example.setAdditionalWhere(projectService.auditRecheckLabor.get());

        If.of(projectService.query(_example).count() > 0).isTrueThrow(() -> new AppException(ErrorCode.PROJECT_DOING));

        VProjectExample example = new VProjectExample();
        example.setOrderByClause("create_time asc");
        if (status == ProjectStatus.CROSS.key()) {
            example.createCriteria()
                    .andOrgIdEqualTo(authMember.getOrg().getId())
                    .andAuditCrossUserIdIsNull()
                    .andStatusEqualTo(ProjectStatus.CROSS.key());
            Stream<VProject> projectStream = vProjectService.query(example);
            Optional.ofNullable(Utils.getFirst(projectStream))
                    .ifPresent(project -> {
                        Project _project = new Project();
                        _project.setId(project.getId());
                        _project.setProjectNo(project.getProjectNo());
                        _project.setAuditCrossUserId(authMember.getMember().getId());
                        projectService.update(_project);
                    });
        }
        if (ProjectStatus.AUDIT_LABOR.key().equals(status)) {
            example.createCriteria()
                    .andAuditRoleIdEqualTo(authMember.getMember().getRoleId())
                    .andAuditUserIdIsNull()
                    .andStatusEqualTo(ProjectStatus.AUDIT_LIST.key());
            Stream<VProject> projectStream = vProjectService.query(example);
            Optional.ofNullable(Utils.getFirst(projectStream))
                    .ifPresent(project -> {
                        Project _project = new Project();
                        _project.setId(project.getId());
                        _project.setProjectNo(project.getProjectNo());
                        _project.setAuditUserId(authMember.getMember().getId());
                        _project.setStatus(ProjectStatus.AUDIT_LABOR.key());
                        _project.setBeginAuditDate(new Date());
                        projectService.update(_project);
                    });
        }
        if (ProjectStatus.AUDIT_RECHECK_LABOR.key().equals(status)) {
            example.createCriteria()
                    .andAuditRecheckRoleIdEqualTo(authMember.getMember().getRoleId())
                    .andAuditRecheckUserIdIsNull()
                    .andStatusEqualTo(ProjectStatus.AUDIT_RECHECK_LIST.key());
            Stream<VProject> projectStream = vProjectService.query(example);
            Optional.ofNullable(Utils.getFirst(projectStream))
                    .ifPresent(project -> {
                        Project _project = new Project();
                        _project.setId(project.getId());
                        _project.setProjectNo(project.getProjectNo());
                        _project.setAuditRecheckUserId(authMember.getMember().getId());
                        _project.setStatus(ProjectStatus.AUDIT_RECHECK_LABOR.key());
                        _project.setBeginRecheckDate(new Date());
                        projectService.update(_project);
                    });
        }
        return SimpleResponse.success("success");
    }

    @ApiOperation("待办项目")
    @GetMapping(value = "/backlog")
    public SimpleResponse<Long> backlog(@ApiParam("2交叉互审,6人工审核,8人工复核") @RequestParam(value = "status") Short status) {
        AuthMember authMember = (AuthMember) ContextHolder.getContext().getData();
        VProjectExample example = new VProjectExample();
        //交叉互审
        if (ProjectStatus.CROSS.key().equals(status))
            example.createCriteria()
                    .andOrgIdEqualTo(authMember.getOrg().getId())
                    .andAuditCrossUserIdIsNull()
                    .andStatusEqualTo(ProjectStatus.CROSS.key());
        //人工审核
        if (ProjectStatus.AUDIT_LABOR.key().equals(status))
            example.createCriteria()
                    .andAuditRoleIdEqualTo(authMember.getMember().getRoleId())
                    .andAuditUserIdIsNull()
                    .andStatusEqualTo(ProjectStatus.AUDIT_LIST.key());
        //人工复核
        if (ProjectStatus.AUDIT_RECHECK_LABOR.key().equals(status))
            example.createCriteria()
                    .andAuditRecheckRoleIdEqualTo(authMember.getMember().getRoleId())
                    .andAuditRecheckUserIdIsNull()
                    .andStatusEqualTo(ProjectStatus.AUDIT_RECHECK_LIST.key());
        long count = vProjectService.count(example);
        return SimpleResponse.success(count);
    }

    private void buildAuditReport(String projectNo, VProject project) {
        //自动审核
        projectService.updateStatus(projectNo, ProjectStatus.AUDIT_AUTO);
        projectService.updateStatus(projectNo, ProjectStatus.CREDIT_REPORT);
        projectService.updateAuditOverStatus(projectNo, ProjectStatus.CREDIT_REPORT);


        //读取黑名y单，如果在黑名单中，出报告，评分置为0，第三方不再调用
        UserInfo userInfo = userService.getUserInfo(projectNo);
        boolean validBlacklist = blacklistService.validBlacklist(userInfo.getIdCard());
        if (!validBlacklist) {
            reportService.buildReport(projectNo, BuildReportSource.BLACK_HIT);
            recordService.record(projectNo, ProjectStatus.CREDIT_REPORT, Strings.EMPTY);
            projectService.updateAuditStatus(projectNo, AuditStatus.AUDIT_AUTO_BLACKLIST);
            return;
        }

        boolean validThirdData = thirdDataValidService.validThirdData(projectNo);
        if (!validThirdData) {
            reportService.buildReport(projectNo, BuildReportSource.THIRD_DATA_VALID_FAIL);
            recordService.record(projectNo, ProjectStatus.CREDIT_REPORT, Strings.EMPTY);
            projectService.updateAuditStatus(projectNo, AuditStatus.AUDIT_AUTO_THIRD);
            return;
        }

        projectService.updateAuditStatus(projectNo, AuditStatus.AUDIT_AUTO);
        scoreService.computeScore(projectNo);
        recordService.record(projectNo, ProjectStatus.AUDIT_AUTO, Strings.EMPTY);
        reportService.buildReport(projectNo, BuildReportSource.NORMAL);
        recordService.record(projectNo, ProjectStatus.CREDIT_REPORT, Strings.EMPTY);
    }

    /**
     * 判断是否需要人工审核
     */
    private void checkAuditManual(String projectNo, VProject project) {
        //是否人工审核 1 是；0 否
        if (project.getAuditManual() == 1) {
            projectService.updateStatus(projectNo, ProjectStatus.AUDIT_LIST);
            projectService.updateAuditStatus(projectNo, AuditStatus.AUDIT_LABOR_NO);
            projectService.updateBeginAuditPoolDate(projectNo);
        } else {
            thirdService.thirdCredit(project.getProjectNo(), project);
            this.buildAuditReport(projectNo, project);
        }
    }

}
