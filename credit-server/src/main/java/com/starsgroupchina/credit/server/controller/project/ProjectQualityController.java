package com.starsgroupchina.credit.server.controller.project;

import com.google.common.collect.Lists;
import com.starsgroupchina.common.context.ContextHolder;
import com.starsgroupchina.common.exception.AppException;
import com.starsgroupchina.common.objects.If;
import com.starsgroupchina.common.response.ListResponse;
import com.starsgroupchina.common.response.SimpleResponse;
import com.starsgroupchina.common.utils.DateUtil;
import com.starsgroupchina.common.utils.Utils;
import com.starsgroupchina.common.utils.export.ReportExcel;
import com.starsgroupchina.credit.bean.AuthMember;
import com.starsgroupchina.credit.bean.enums.ProjectQualityStatus;
import com.starsgroupchina.credit.bean.enums.ProjectStatus;
import com.starsgroupchina.credit.bean.extend.ProjectQualityExportExtend;
import com.starsgroupchina.credit.bean.extend.ProjectQualityExtend;
import com.starsgroupchina.credit.bean.model.*;
import com.starsgroupchina.credit.key.ErrorCode;
import com.starsgroupchina.credit.server.conf.Const;
import com.starsgroupchina.credit.server.service.ConfService;
import com.starsgroupchina.credit.server.service.ProductService;
import com.starsgroupchina.credit.server.service.project.ProjectQualityService;
import com.starsgroupchina.credit.server.service.project.ProjectService;
import com.starsgroupchina.credit.server.service.system.OrgService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Created by gexiaoshan on 2018/8/22.
 */
@Slf4j
@RestController
@Api(tags = "CREDIT-SWAGGER43", description = "【项目】 - 合规质检管理-ProjectQualityController")
@RequestMapping(value = "/project/quality", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class ProjectQualityController {

    @Autowired
    private ProjectQualityService projectQualityService;
    @Autowired
    private ProjectService projectService;
    @Autowired
    private ProjectService.VProjectService vProjectService;
    @Autowired
    private OrgService orgService;
    @Autowired
    private ConfService confService;
    @Autowired
    private ProductService productService;

    @ApiResponses({@ApiResponse(code = ErrorCode.AUTH_DATA_NULL, message = "当前用户数据权限为空")})
    @ApiOperation("合规质检列表")
    @GetMapping
    public ListResponse<ProjectQualityExtend> query(
            @RequestParam(value = "index", defaultValue = "1") int index,
            @RequestParam(value = "limit", defaultValue = "20") int limit,
            @ApiParam("机构") @RequestParam(value = "orgId", required = false) Integer orgId,
            @ApiParam("进件编号") @RequestParam(value = "projectNo", required = false) String projectNo,
            @ApiParam("身份证") @RequestParam(value = "userIdCard", required = false) String userIdCard,
            @ApiParam("审核人") @RequestParam(value = "auditUserName", required = false) String auditUserName,
            @ApiParam("审核时间start") @RequestParam(value = "beginAuditStart", defaultValue = "1970-01-01 00:00:00") String beginAuditDateStart,
            @ApiParam("审核时间end") @RequestParam(value = "beginAuditEnd", defaultValue = "2050-12-12 00:00:00") String beginAuditDateEnd,
            @ApiParam("复核人") @RequestParam(value = "auditRecheckUserName", required = false) String auditRecheckUserName,
            @ApiParam("复核时间start") @RequestParam(value = "auditRecheckStart", defaultValue = "1970-01-01 00:00:00") String auditRecheckStart,
            @ApiParam("复核时间end") @RequestParam(value = "auditRecheckEnd", defaultValue = "2050-12-12 00:00:00") String auditRecheckEnd,
            @ApiParam("质检人") @RequestParam(value = "qualityUserName", required = false) String qualityUserName,
            @ApiParam("menu key") @RequestParam(value = "menu") String menu,
            @ApiParam("状态1、未质检,2、质检中,999、已质检") @RequestParam(value = "status", required = false) Short status,
            @ApiParam("错误类型") @RequestParam(value = "auditType", required = false) String auditType,
            @ApiParam("错误等级") @RequestParam(value = "auditLevel", required = false) String auditLevel) {

        VProjectExample example = new VProjectExample();
        VProjectExample.Criteria criteria = example.createCriteria();
        If.of(orgId != null).isTrue(() -> criteria.andOrgIdEqualTo(orgId));
        If.of(StringUtils.isNotEmpty(projectNo)).isTrue(() -> criteria.andProjectNoLike("%" + projectNo.trim() + "%"));
        If.of(StringUtils.isNotEmpty(userIdCard)).isTrue(() -> criteria.andUserIdCardLike("%" + userIdCard.trim() + "%"));
        If.of(StringUtils.isNotEmpty(auditUserName)).isTrue(() -> criteria.andAuditUserNameLike("%" + auditUserName.trim() + "%"));
        Date startTime = DateUtil.parse(beginAuditDateStart);
        Date endTime = DateUtil.parse(beginAuditDateEnd);
        criteria.andBeginAuditDateBetween(startTime, endTime);
        If.of(StringUtils.isNotEmpty(auditRecheckUserName)).isTrue(() -> criteria.andAuditRecheckUserNameLike("%" + auditRecheckUserName.trim() + "%"));
        If.of(StringUtils.isNotEmpty(qualityUserName)).isTrue(() -> criteria.andQualityUserNameLike("%" + qualityUserName.trim() + "%"));
        If.of(StringUtils.isNotEmpty(auditType)).isTrue(() -> criteria.andAuditTypeEqualTo(auditType));
        If.of(StringUtils.isNotEmpty(auditLevel)).isTrue(() -> criteria.andAuditLevelEqualTo(auditLevel));
        example.setOrderByClause("create_time  desc");

        AuthMember authMember = (AuthMember) ContextHolder.getContext().getData();
        Role role = authMember.getRole();
        if ("hgzjgl_zjjc_list".equals(menu)) {
            //质检池
            if (status == null || status == 0) {
                //传0代表取未质检和质检中的数据
                List<Short> qualityStatus = Arrays.asList(ProjectQualityStatus.QUALITY_NOT.key(), ProjectQualityStatus.QUALITY_ING.key());
                criteria.andQualityStatusIn(qualityStatus);
            } else {
                criteria.andQualityStatusEqualTo(status);
            }
        } else if ("hgzjgl_zjjc_handle_add".equals(menu)) {
            //质检件池  手动新增
            criteria.andStatusEqualTo(ProjectStatus.CREDIT_REPORT.key());
            criteria.andQualityStatusIsNull();
            if (!("1970-01-01 00:00:00".equals(auditRecheckStart) && "2050-12-12 00:00:00".equals(auditRecheckEnd))) {
                Date auditRecheckStartTime = DateUtil.parse(auditRecheckStart);
                Date auditRecheckEndTime = DateUtil.parse(auditRecheckEnd);
                //复核时间新加字段，历史数据为空，在不选时间时也查出来
                criteria.andEndRecheckDateBetween(auditRecheckStartTime, auditRecheckEndTime);
            }
        } else if ("hgzjgl_zjcc_list".equals(menu)) {
            //质检抽查
            if (role.getIsQuality() != null && role.getIsQuality() == 1) {
                //质检人员默认看到的质检中的数据
                criteria.andQualityUserIdEqualTo(authMember.getMember().getId());
                if (status == null || status == 0) {
                    criteria.andQualityStatusEqualTo(ProjectQualityStatus.QUALITY_ING.key());
                } else {
                    criteria.andQualityStatusEqualTo(status);
                }
            } else {
                if (status == null || status == 0) {
                    List<Short> qualityStatus = Arrays.asList(ProjectQualityStatus.QUALITY_ING.key(), ProjectQualityStatus.QUALITY_ED.key());
                    criteria.andQualityStatusIn(qualityStatus);
                } else {
                    criteria.andQualityStatusEqualTo(status);
                }
            }
        } else if ("hgzjgl_zjbg_list".equals(menu)) {
            //质检报告
            criteria.andQualityStatusEqualTo(ProjectQualityStatus.QUALITY_ED.key());
            example.setOrderByClause("begin_quality_date  desc");
        }

        long count = vProjectService.count(example);
        List<ProjectQualityExtend> result = Lists.newArrayList();
        If.of(count > 0).isTrue(() -> {

            example.setOffset((index - 1) * limit);
            example.setLimit(limit);
            Optional.ofNullable(vProjectService.query(example)).ifPresent(projects ->
                    projects.forEach(project -> {
                        ProjectQualityExtend projectQualityExtend = new ProjectQualityExtend(project);
                        Org org = orgService.getById(project.getOrgId());
                        Product product = productService.getById(project.getProductId());
                        projectQualityExtend.setIsQuality(role.getIsQuality());
                        projectQualityExtend.setOrg(org);
                        projectQualityExtend.setProduct(product);
                        result.add(projectQualityExtend);
                    })
            );
        });
        return ListResponse.success(result, count, index, limit);
    }

    @ApiOperation("质检池 手动新增")
    @PostMapping(value = "/hand")
    @Transactional
    public SimpleResponse<List<Integer>> createHand(@RequestBody List<Integer> projectIds) {

        Optional.ofNullable(projectIds).ifPresent(ids -> ids.forEach(id -> {
            Project project = new Project();
            project.setId(id);
            project.setQualityStatus(ProjectQualityStatus.QUALITY_NOT.key());
            projectService.updateByPrimaryKeySelective(project);
        }));

        return SimpleResponse.success(projectIds);
    }

    @ApiResponses({@ApiResponse(code = ErrorCode.PROJECT_QUALITY_PERCENTAGE, message = "未设置自动新增上限百分比")})
    @ApiOperation("质检池 自动新增")
    @PostMapping(value = "/automatic")
    public SimpleResponse createAutomatic() {

        ConfExample example = new ConfExample();
        example.or().andConfKeyEqualTo(Const.QUALITY_PERCENTAGE);
        Conf conf = Utils.getFirst(confService.query(example));
        If.of(conf == null).isTrueThrow(() -> new AppException(ErrorCode.PROJECT_QUALITY_PERCENTAGE));

        ProjectExample projectExample = new ProjectExample();
        ProjectExample.Criteria criteria = projectExample.createCriteria();
        criteria.andStatusEqualTo(ProjectStatus.CREDIT_REPORT.key());
        criteria.andQualityStatusIsNull();
        LocalDate date = getPreviousDay();
        criteria.andAuditOverTimeBetween(DateUtil.localDateToDate(date), DateUtil.localDateToDate(date.plusDays(1)));
        long count = projectService.count(projectExample);
        if (count != 0) {
            //抽取百分比
            float percentage = Integer.parseInt(conf.getConfValue()) / 100f;
            int number = (int) Math.floor(count * percentage);
            projectExample.setOrderByClause("project_no desc");
            projectExample.setOffset(0);
            projectExample.setLimit(number);
            Optional.ofNullable(projectService.query(projectExample)).ifPresent(list -> list.forEach(p -> {
                p.setQualityStatus(ProjectQualityStatus.QUALITY_NOT.key());
                projectService.updateByPrimaryKeySelective(p);
            }));
        }
        return SimpleResponse.success();
    }

    @ApiResponses({@ApiResponse(code = ErrorCode.PROJECT_QUALITY_DELETE_NOT_ALLOW, message = "删除失败")})
    @ApiOperation("删除")
    @DeleteMapping(value = "/{projectId}")
    public SimpleResponse delete(@PathVariable("projectId") Integer projectId) {

        ProjectExample example = new ProjectExample();
        example.or().andIdEqualTo(projectId).andQualityStatusIsNotNull();
        Project project = Utils.getFirst(projectService.query(example));
        If.of(project == null).isTrueThrow(() -> new AppException(ErrorCode.PROJECT_QUALITY_DELETE_NOT_ALLOW));
        If.of(project.getQualityUserId() != null).isTrueThrow(() -> new AppException(ErrorCode.PROJECT_QUALITY_DELETE_NOT_ALLOW));

        project.setQualityStatus(null);
        projectService.updateByPrimaryKey(project);
        return SimpleResponse.success(projectId);
    }

    @ApiOperation("指派")
    @PutMapping("/assign")
    public SimpleResponse assign(@RequestBody Project project) {

        project.setBeginQualityDate(new Date());
        projectService.update(project);
        return SimpleResponse.success(project);
    }

    @ApiOperation("质检池 获取自动新增上限百分比")
    @GetMapping(value = "/automatic/percentage")
    public SimpleResponse queryQualityPercentage() {
        ConfExample example = new ConfExample();
        example.or().andConfKeyEqualTo(Const.QUALITY_PERCENTAGE);
        return SimpleResponse.success(Utils.getFirst(confService.query(example)));
    }

    @ApiOperation("质检池 更新自动新增上限百分比")
    @PutMapping(value = "/automatic/percentage")
    public SimpleResponse updateQualityPercentage(@ApiParam("百分比") @RequestParam(value = "percentage") String percentage) {

        ConfExample example = new ConfExample();
        example.or().andConfKeyEqualTo(Const.QUALITY_PERCENTAGE);
        Conf conf = Utils.getFirst(confService.query(example));
        If.of(conf != null).isTrue(() -> {
            conf.setConfValue(percentage);
            confService.update(conf);
        });

        If.of(conf == null).isTrue(() -> {
            Conf c = new Conf();
            c.setConfKey(Const.QUALITY_PERCENTAGE);
            c.setConfValue(percentage);
            c.setDescription("合规质检-质检池-自动新增上限设置");
            confService.create(c);
        });
        return SimpleResponse.success(percentage);
    }

    @ApiOperation("质检结果查看")
    @GetMapping(value = "/{projectNo}/result")
    public SimpleResponse<ProjectQualityResult> queryQualityResult(@PathVariable("projectNo") String projectNo) {

        ProjectQualityResultExample example = new ProjectQualityResultExample();
        example.or().andProjectNoEqualTo(projectNo);
        return SimpleResponse.success(Utils.getFirst(projectQualityService.query(example)));
    }

    @ApiOperation("质检结果保存")
    @PostMapping(value = "/result/save")
    public SimpleResponse<ProjectQualityResult> saveQualityResult(@RequestBody ProjectQualityResult projectQualityResult) {

        ProjectQualityResultExample example = new ProjectQualityResultExample();
        example.or().andProjectNoEqualTo(projectQualityResult.getProjectNo());
        ProjectQualityResult p = Utils.getFirst(projectQualityService.query(example));
        if (p == null) {
            projectQualityService.create(projectQualityResult);
        } else {
            projectQualityService.update(projectQualityResult);
        }
        return SimpleResponse.success(projectQualityResult);
    }

    @ApiOperation("质检结果提交")
    @PostMapping(value = "/result/submit")
    public SimpleResponse<ProjectQualityResult> submitQualityResult(@RequestBody ProjectQualityResult projectQualityResult) {

        saveQualityResult(projectQualityResult);

        Project project = new Project();
        project.setProjectNo(projectQualityResult.getProjectNo());
        project.setQualityStatus(ProjectQualityStatus.QUALITY_ED.key());
        project.setEndQualityDate(new Date());
        projectService.update(project);
        return SimpleResponse.success(projectQualityResult);
    }

    @ApiResponses({@ApiResponse(code = ErrorCode.PROJECT_DOING, message = "当前环节存在未办完项目")})
    @ApiOperation("质检抽查 获取")
    @GetMapping(value = "/receive")
    public SimpleResponse<ProjectQualityResult> qualityReceive() {

        Member member = ((AuthMember) ContextHolder.getContext().getData()).getMember();
        ProjectExample projectExample = new ProjectExample();
        projectExample.createCriteria().andQualityStatusEqualTo(ProjectQualityStatus.QUALITY_ING.key()).andQualityUserIdEqualTo(member.getId());
        If.of(projectService.count(projectExample) > 0).isTrueThrow(() -> new AppException(ErrorCode.PROJECT_DOING));

        projectExample.clear();
        projectExample.or().andQualityStatusEqualTo(ProjectQualityStatus.QUALITY_NOT.key());
        Project project = Utils.getFirst(projectService.query(projectExample));
        If.of(project != null).isTrue(() -> {

            Project p = new Project();
            p.setProjectNo(project.getProjectNo());
            p.setQualityStatus(ProjectQualityStatus.QUALITY_ING.key());
            p.setQualityUserId(member.getId());
            p.setBeginQualityDate(new Date());
            projectService.update(p);
        });

        return SimpleResponse.success();
    }

    @ApiOperation(value = "质检报告导出")
    @GetMapping(value = "/result/export")
    public void resultExport(@ApiParam("机构") @RequestParam(value = "orgId", required = false) Integer orgId,
                             @ApiParam("进件编号") @RequestParam(value = "projectNo", required = false) String projectNo,
                             @ApiParam("身份证") @RequestParam(value = "userIdCard", required = false) String userIdCard,
                             @ApiParam("审核人") @RequestParam(value = "auditUserName", required = false) String auditUserName,
                             @ApiParam("审核时间start,格式:yyyy-MM-dd HH:mm:ss") @RequestParam(value = "beginAuditStart", required = false) String beginAuditDateStart,
                             @ApiParam("审核时间end,格式:yyyy-MM-dd HH:mm:ss") @RequestParam(value = "beginAuditEnd", required = false) String beginAuditDateEnd,
                             @ApiParam("复核人") @RequestParam(value = "auditRecheckUserName", required = false) String auditRecheckUserName,
                             @ApiParam("质检人") @RequestParam(value = "qualityUserName", required = false) String qualityUserName,
                             @ApiParam("错误类型") @RequestParam(value = "auditType", required = false) String auditType,
                             @ApiParam("错误等级") @RequestParam(value = "auditLevel", required = false) String auditLevel,
                             HttpServletResponse response) {

        VProjectExample example = new VProjectExample();
        VProjectExample.Criteria criteria = example.createCriteria();
        If.of(orgId != null).isTrue(() -> criteria.andOrgIdEqualTo(orgId));
        If.of(StringUtils.isNotEmpty(projectNo)).isTrue(() -> criteria.andProjectNoLike("%" + projectNo.trim() + "%"));
        If.of(StringUtils.isNotEmpty(userIdCard)).isTrue(() -> criteria.andUserIdCardLike("%" + userIdCard.trim() + "%"));
        If.of(StringUtils.isNotEmpty(auditUserName)).isTrue(() -> criteria.andAuditUserNameLike("%" + auditUserName.trim() + "%"));
        Date startTime = DateUtil.parse(beginAuditDateStart);
        Date endTime = DateUtil.parse(beginAuditDateEnd);
        If.of(startTime != null).isTrue(() -> criteria.andBeginAuditDateGreaterThanOrEqualTo(startTime));
        If.of(endTime != null).isTrue(() -> criteria.andBeginAuditDateLessThanOrEqualTo(endTime));
        If.of(startTime != null && endTime != null).isTrue(() -> criteria.andBeginAuditDateBetween(startTime, endTime));
        If.of(StringUtils.isNotEmpty(auditRecheckUserName)).isTrue(() -> criteria.andAuditRecheckUserNameLike("%" + auditRecheckUserName.trim() + "%"));
        If.of(StringUtils.isNotEmpty(qualityUserName)).isTrue(() -> criteria.andQualityUserNameLike("%" + qualityUserName.trim() + "%"));
        If.of(StringUtils.isNotEmpty(auditType)).isTrue(() -> criteria.andAuditTypeEqualTo(auditType));
        If.of(StringUtils.isNotEmpty(auditLevel)).isTrue(() -> criteria.andAuditLevelEqualTo(auditLevel));
        criteria.andQualityStatusEqualTo(ProjectQualityStatus.QUALITY_ED.key());
        example.setOrderByClause("begin_quality_date  desc");

        List<ProjectQualityExportExtend> result = Lists.newArrayList();
        Optional.ofNullable(vProjectService.query(example)).ifPresent(ps ->
                ps.forEach(project -> {
                    ProjectQualityExportExtend extend = new ProjectQualityExportExtend(project);
                    Org org = orgService.getById(project.getOrgId());
                    extend.setOrgName(org.getName());
                    result.add(extend);
                })
        );
        String fileName = "质检报告";
        try {
            ReportExcel.excelExport(result,
                    fileName, null, ProjectQualityExportExtend.class, 1, response, null);
        } catch (Exception e) {
            log.error("导出异常：", e);
        }
    }

    @ApiOperation("合规质检代办项目")
    @PostMapping(value = "/backlog")
    public SimpleResponse backlog() {

        ProjectExample projectExample = new ProjectExample();
        projectExample.createCriteria().andQualityStatusEqualTo(ProjectQualityStatus.QUALITY_NOT.key());
        return SimpleResponse.success(projectService.count(projectExample));
    }

    private LocalDate getPreviousDay() {
        LocalDate date = LocalDate.now().minusDays(1);
        while (true) {
            if (date.getDayOfWeek() != DayOfWeek.WEDNESDAY.SATURDAY && date.getDayOfWeek() != DayOfWeek.SUNDAY)
                break;
            date = date.minusDays(1);
        }
        return date;
    }

    public static void main(String[] args) {
        Date startTime = DateUtil.parse("2050-12-12 00:00:00");
        System.out.println(DateUtil.DateToString(startTime));
    }
}
