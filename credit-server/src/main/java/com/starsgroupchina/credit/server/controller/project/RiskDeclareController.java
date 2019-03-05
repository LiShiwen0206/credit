package com.starsgroupchina.credit.server.controller.project;

import com.google.common.collect.Lists;
import com.starsgroupchina.common.context.ContextHolder;
import com.starsgroupchina.common.exception.AppException;
import com.starsgroupchina.common.objects.If;
import com.starsgroupchina.common.response.ListResponse;
import com.starsgroupchina.common.response.SimpleResponse;
import com.starsgroupchina.common.utils.Utils;
import com.starsgroupchina.common.utils.export.ReportExcel;
import com.starsgroupchina.credit.bean.AuthMember;
import com.starsgroupchina.credit.bean.UserInfo;
import com.starsgroupchina.credit.bean.enums.*;
import com.starsgroupchina.credit.bean.extend.ErrImportRiskDeclareExtend;
import com.starsgroupchina.credit.bean.extend.RiskDeclareExportExtend;
import com.starsgroupchina.credit.bean.extend.RiskDeclareExtend;
import com.starsgroupchina.credit.bean.model.*;
import com.starsgroupchina.credit.key.ErrorCode;
import com.starsgroupchina.credit.key.MessageTemplate;
import com.starsgroupchina.credit.server.service.NumberService;
import com.starsgroupchina.credit.server.service.ProductService;
import com.starsgroupchina.credit.server.service.project.*;
import com.starsgroupchina.credit.server.service.system.MemberService;
import com.starsgroupchina.credit.server.service.system.MessageService;
import com.starsgroupchina.credit.server.service.system.OrgService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.kafka.common.protocol.types.Field;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.Instant;
import java.util.Date;
import java.util.List;

import static com.starsgroupchina.common.utils.Utils.getFirst;

/**
 * @Author: QinHaoHao
 * @Description:
 * @Date: Created in 16:49 2018/8/16
 * @Modifed By:
 */
@Slf4j
@RestController
@Api(tags = "CREDIT-SWAGGER40", description = "【项目】 - 项目风险申报-RiskDeclareController")
@RequestMapping(value = "/project/declare", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class RiskDeclareController {


    @Autowired
    private RiskDeclareService riskDeclareService;
    @Autowired
    private RiskDeclareService.VRiskDeclareService VRiskDeclareService;
    @Autowired
    private NumberService numberRuleService;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ProductService productService;

    @Autowired
    private OrgService orgService;

    @Autowired
    private UserService userService;

    @Autowired
    private ReportService reportService;

    @Autowired
    private RecordService recordService;

    @Autowired
    private RiskDeclareRecordService riskDeclareRecordService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private MessageService messageService;

    /**
     * 项目申报
     */
    @ApiResponses({@ApiResponse(code = ErrorCode.PROJECT_EXCEED_DECLARE_LIMIT, message = "当前项目限制申报2次，已超过申报上限"), @ApiResponse(code = ErrorCode.PROJECT_DECLARE_NO_RULE_NOT_EXITS, message = "申报编号规则未设置"),@ApiResponse(code = ErrorCode.PROJECT_DECLARE_EXIST_OR_SUCCESS, message = "当前项目正在调查中或者已确定为风险客户")})
    @ApiOperation("项目申报")
    @RequestMapping(value = "", method = RequestMethod.POST)
    public SimpleResponse<ProjectRiskDeclare> create(@RequestBody ProjectRiskDeclare riskDeclare) {
        If.of(riskDeclareService.checkExist(riskDeclare.getProjectNo())).isTrueThrow(() -> new AppException(ErrorCode.PROJECT_DECLARE_EXIST_OR_SUCCESS));
        AuthMember authMember= (AuthMember) ContextHolder.getContext().getData();
        Member member = authMember.getMember();
        Integer headOrgId = authMember.getOrg().getHeadOrgId();
        NumberRuleExample example = new NumberRuleExample();
        ProjectRiskDeclareExample declareExample = new ProjectRiskDeclareExample();
        example.createCriteria().andNoNameEqualTo("申报编号").andOrgIdEqualTo(headOrgId);
        NumberRule numberRule = getFirst(numberRuleService.query(example));
        If.of(numberRule == null).isTrueThrow(() -> new AppException(ErrorCode.PROJECT_DECLARE_NO_RULE_NOT_EXITS));
        String number = numberRuleService.getNumber(numberRule, -1);
        riskDeclare.setDeclareNo(number);
        if (riskDeclare.getDeclareSource().equals(DeclareSource.AUDIT.key())) {
            declareExample.createCriteria().andProjectNoEqualTo(riskDeclare.getProjectNo()).andDeclareSourceEqualTo(riskDeclare.getDeclareSource());
            long count = riskDeclareService.count(declareExample);
            If.of(count > 1).isTrueThrow(() -> new AppException(ErrorCode.PROJECT_EXCEED_DECLARE_LIMIT));
            riskDeclare.setStatus(DeclareStatus.DECLARE_FIRST_ASSIGN.key());
            riskDeclare.setFirstSurveyResult("【审核】申报调查");
            riskDeclareRecordService.saveRiskRecord(riskDeclare, DeclareStatus.DECLARE_FIRST_ASSIGN);
        } else if (riskDeclare.getDeclareSource().equals(DeclareSource.RECHECK.key())) {
            declareExample.createCriteria().andProjectNoEqualTo(riskDeclare.getProjectNo()).andDeclareSourceEqualTo(riskDeclare.getDeclareSource());
            long count = riskDeclareService.count(declareExample);
            If.of(count > 1).isTrueThrow(() -> new AppException(ErrorCode.PROJECT_EXCEED_DECLARE_LIMIT));
            riskDeclare.setStatus(DeclareStatus.DECLARE_FIRST_ASSIGN.key());
            riskDeclare.setFirstSurveyResult("【复核】申报调查");
            riskDeclareRecordService.saveRiskRecord(riskDeclare, DeclareStatus.DECLARE_FIRST_ASSIGN);
        } else if (riskDeclare.getDeclareSource().equals(DeclareSource.DIRECT.key())){
            riskDeclare.setStatus(DeclareStatus.DECLARE_COMMIT_NO.key());
            riskDeclare.setFirstSurveyResult("【直接申报】申报调查");
            riskDeclareRecordService.saveRiskRecord(riskDeclare, DeclareStatus.DECLARE_COMMIT_NO);
        }else {
            riskDeclare.setStatus(DeclareStatus.DECLARE_COMMIT_NO.key());
            riskDeclare.setFirstSurveyResult("【范本导入】申报调查");
            riskDeclareRecordService.saveRiskRecord(riskDeclare, DeclareStatus.DECLARE_COMMIT_NO);
        }
        riskDeclare.setDeclareUserId(member.getId());
        riskDeclare.setFirstSurveyResult("");
        ProjectRiskDeclare projectRiskDeclare = riskDeclareService.create(riskDeclare);
        return SimpleResponse.success(projectRiskDeclare);
    }

    /**
     * 获取申报列表
     */
    @ApiOperation("获取申报列表")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ListResponse<RiskDeclareExtend> getProjectForm(
            @RequestParam(value = "index", defaultValue = "1") int index,
            @RequestParam(value = "limit", defaultValue = "20") int limit,
            @ApiParam("申报类型") @RequestParam(value = "declareType", required = false) String declareType,
            @ApiParam("申报人") @RequestParam(value = "declareUserName", required = false) String declareUserName,
            @ApiParam("申报来源") @RequestParam(value = "declareSource", required = false) String declareSource,
            @ApiParam("状态") @RequestParam(value = "status", required = false) List<Short> status,
            @ApiParam("身份证号") @RequestParam(value = "idCard", required = false) String idCard,
            @ApiParam("是否风险客户") @RequestParam(value = "riskCustomer", required = false) String riskCustomer,
            @ApiParam("虚假行为") @RequestParam(value = "declareBehavior", required = false) String declareBehavior,
            @ApiParam("虚假等级") @RequestParam(value = "declareLevel", required = false) String declareLevel,
            @ApiParam("一级调查人") @RequestParam(value = "firstSurveyUser", required = false) String firstSurveyUser,
            @ApiParam("进件编号") @RequestParam(value = "projectNo", required = false) String projectNo,
            @ApiParam("机构id") @RequestParam(value = "orgId", required = false) String orgId,
            @ApiParam("开始申报时间start") @RequestParam(value = "declareDateBegin", defaultValue = "1451630690") Long declareDateBegin,
            @ApiParam("开始申报时间end") @RequestParam(value = "declareDateEnd", defaultValue = "1895538782000") Long declareDateEnd,
            @ApiParam("处理日期start") @RequestParam(value = "handleDateBegin", defaultValue = "1451630690") Long handleDateBegin,
            @ApiParam("处理日期end") @RequestParam(value = "handleDateEnd", defaultValue = "1895538782000") Long handleDateEnd,
            @ApiParam("0、风险客户申报，1、风险客户分配，2、风险客户调查(一级)，3、风险客户调查(二级)，4、风险客户调查表") @RequestParam(value = "menu") Short menu) {
        VProjectRiskDeclareExample example = new VProjectRiskDeclareExample();
        VProjectRiskDeclareExample.Criteria criteria = example.createCriteria();
        If.of(StringUtils.isNotEmpty(declareType)).isTrue(() -> criteria.andDeclareTypeEqualTo(declareType.trim()));
        If.of(StringUtils.isNotEmpty(declareUserName)).isTrue(() -> criteria.andDeclareUserNameEqualTo(declareUserName.trim()));
        If.of(StringUtils.isNotEmpty(declareSource)).isTrue(() -> criteria.andDeclareSourceEqualTo(Short.valueOf(declareSource.trim())));
        If.of(CollectionUtils.isNotEmpty(status)).isTrue(() -> criteria.andStatusIn(status));
        If.of(StringUtils.isNotEmpty(idCard)).isTrue(() -> criteria.andIdCardEqualTo(idCard.trim()));
        If.of(StringUtils.isNotEmpty(orgId)).isTrue(() -> criteria.andOrgIdEqualTo(Integer.valueOf(orgId.trim())));
        If.of(StringUtils.isNotEmpty(projectNo)).isTrue(() -> criteria.andProjectNoEqualTo(projectNo.trim()));
        If.of(StringUtils.isNotEmpty(riskCustomer)).isTrue(() -> criteria.andFirstSurveyResultEqualTo(riskCustomer.trim()));
        If.of(StringUtils.isNotEmpty(declareBehavior)).isTrue(() -> criteria.andFirstSurveyBehaviorEqualTo(declareBehavior.trim()));
        If.of(StringUtils.isNotEmpty(declareLevel)).isTrue(() -> criteria.andFirstSurveyLevelEqualTo(declareLevel.trim()));
        If.of(StringUtils.isNotEmpty(firstSurveyUser)).isTrue(() -> criteria.andFirstSurveyUserNameLike("%" + firstSurveyUser.trim() + "%"));

        Date dateStart = new Date(Instant.ofEpochMilli(declareDateBegin).toEpochMilli());
        Date dateEnd = new Date(Instant.ofEpochMilli(declareDateEnd).toEpochMilli());
        criteria.andDeclareDateBetween(dateStart, dateEnd);
        Date handleStart = new Date(Instant.ofEpochMilli(handleDateBegin).toEpochMilli());
        Date handleEnd = new Date(Instant.ofEpochMilli(handleDateEnd).toEpochMilli());
        criteria.andModifyTimeBetween(handleStart, handleEnd);
        if (menu.equals(DeclareMenu.COMMIT_WAIT.key())) {
            example.setAdditionalWhere(riskDeclareService.commitWait.get());
        }
        if (menu.equals(DeclareMenu.FIRST_ASSIGN.key())) {
            example.setAdditionalWhere(riskDeclareService.firstAssign.get());
        }
        if (menu.equals(DeclareMenu.FIRST_INVESTIGATE.key())) {
            example.setAdditionalWhere(riskDeclareService.firstInvestigate.get());
        }
        if (menu.equals(DeclareMenu.TWO_INVESTIGATE.key())) {
            example.setAdditionalWhere(riskDeclareService.twoInvestigate.get());
        }
        if (menu.equals(DeclareMenu.OVER.key())) {
            example.setAdditionalWhere(riskDeclareService.over.get());
        }
        List<RiskDeclareExtend> result = Lists.newArrayList();
        long count = VRiskDeclareService.count(example);
        example.setOrderByClause("create_time  desc");
        example.setOffset((index - 1) * limit);
        example.setLimit(limit);
        VRiskDeclareService.query(example).forEach(projectRiskDeclare -> {
            Project project = projectService.getProjectByProjectNo(projectRiskDeclare.getProjectNo());
            Org org = orgService.getById(project.getOrgId());
            UserInfo userInfo = userService.getUserInfo(projectRiskDeclare.getProjectNo());
            RiskDeclareExtend riskDeclareExtend = new RiskDeclareExtend(projectRiskDeclare);
            Product product = productService.getById(project.getProductId());
            riskDeclareExtend.setProduct(product);
            riskDeclareExtend.setProject(project);
            riskDeclareExtend.setOrg(org);
            riskDeclareExtend.setUserInfo(userInfo);
            result.add(riskDeclareExtend);
        });
        return ListResponse.success(result, count, index, limit);
    }

    @ApiOperation("修改申报信息")
    @PutMapping()
    public SimpleResponse<ProjectRiskDeclare> updateRelation(@RequestBody ProjectRiskDeclare riskDeclare) {
        ProjectRiskDeclare result = riskDeclareService.update(riskDeclare);
        return SimpleResponse.success(result);
    }


    @ApiOperation("提交申报信息")
    @RequestMapping(value = "/commit", method = RequestMethod.POST)
    public SimpleResponse<List<ProjectRiskDeclare>> commit(@RequestBody List<ProjectRiskDeclare> riskDeclares) {
        AuthMember authMember= (AuthMember) ContextHolder.getContext().getData();
        Member member = authMember.getMember();
        for (ProjectRiskDeclare riskDeclare : riskDeclares) {
            //待提交提交
            if (riskDeclare.getStatus().equals(DeclareStatus.DECLARE_COMMIT_NO.key())) {
                riskDeclare.setStatus(DeclareStatus.DECLARE_FIRST_ASSIGN.key());
                riskDeclareService.update(riskDeclare);
                if (riskDeclare.getDeclareSource().equals(DeclareSource.DIRECT.key())) {
                    riskDeclare.setFirstSurveyResult("【直接申报】提交至待分配");
                } else {
                    riskDeclare.setFirstSurveyResult("【范本导入】提交至待分配");
                }
                riskDeclareRecordService.saveRiskRecord(riskDeclare, DeclareStatus.DECLARE_COMMIT_NO);
                continue;
            }
            //一级处理退回到待提交提交
            if (riskDeclare.getStatus().equals(DeclareStatus.FIRST_HANDLE_BACK_COMMIT.key())) {
                riskDeclare.setStatus(DeclareStatus.RECOMMIT_FIST_HANDLE.key());
                riskDeclareService.update(riskDeclare);
                riskDeclare.setFirstSurveyResult("待提交重新提交至一级处理中");
                riskDeclare.setFirstSurveyRemark("");
                riskDeclareRecordService.saveRiskRecord(riskDeclare, DeclareStatus.DECLARE_COMMIT_NO);
                continue;
            }
            //一级处理退回到复核审核申请阶段
            if (riskDeclare.getStatus().equals(DeclareStatus.FIRST_HANDLE_BACK_APPLY.key())) {
                riskDeclare.setStatus(DeclareStatus.DECLARE_FIRST_HANDLE.key());
                riskDeclareService.update(riskDeclare);
                riskDeclare.setFirstSurveyResult("回退件重新提交至一级处理中");
                riskDeclareRecordService.saveRiskRecord(riskDeclare, DeclareStatus.DECLARE_FIRST_HANDLE);
                continue;
            }
            //一级处理中提交
            if (riskDeclare.getStatus().equals(DeclareStatus.DECLARE_FIRST_HANDLE.key()) || riskDeclare.getStatus().equals(DeclareStatus.RECOMMIT_FIST_HANDLE.key()) || riskDeclare.getStatus().equals(DeclareStatus.TWO_BACK_FIRST_HANDLE.key())) {
                if ("不是风险客户".equals(riskDeclare.getFirstSurveyResult())) {
                    riskDeclare.setStatus(DeclareStatus.DECLARE_OVER.key());
                } else {
                    if (riskDeclare.getStatus().equals(DeclareStatus.TWO_BACK_FIRST_HANDLE.key())) {
                        riskDeclare.setStatus(DeclareStatus.FIRST_HANDLE_RECOMMIT_TWO_HANDLE.key());
                    } else {
                        riskDeclare.setStatus(DeclareStatus.DECLARE_TWO_HANDLE.key());

                    }
                }
                riskDeclareRecordService.saveRiskRecord(riskDeclare, DeclareStatus.DECLARE_FIRST_HANDLE);
                riskDeclare.setFirstSurveyUserId(member.getId());
                riskDeclareService.update(riskDeclare);
                continue;
            }
            //二级处理中提交
            if (riskDeclare.getStatus().equals(DeclareStatus.DECLARE_TWO_HANDLE.key()) || riskDeclare.getStatus().equals(DeclareStatus.FIRST_HANDLE_RECOMMIT_TWO_HANDLE.key())) {
                //当时从审核、复核提交过去的风险客户，判定为风险客户时，直接终止流程
                if ("是风险客户".equals(riskDeclare.getTwoSurveyResult()) && (riskDeclare.getDeclareSource().equals(DeclareSource.AUDIT.key()) || riskDeclare.getDeclareSource().equals(DeclareSource.RECHECK.key()))) {
                    String projectNo = riskDeclare.getProjectNo();
                    projectService.updateAuditOverStatus(projectNo, ProjectStatus.CREDIT_REPORT);
                    projectService.updateAuditStatus(projectNo, AuditStatus.RISK_CUSTOMER);
                    reportService.buildReport(projectNo, BuildReportSource.RISK_CUSTOMER);
                    recordService.record(projectNo, ProjectStatus.CREDIT_REPORT, Strings.EMPTY);

                }
                riskDeclare.setStatus(DeclareStatus.DECLARE_OVER.key());
                riskDeclare.setTwoSurveyUserId(member.getId());
                riskDeclareRecordService.saveRiskRecord(riskDeclare, DeclareStatus.DECLARE_TWO_HANDLE);
            }
            riskDeclareService.update(riskDeclare);
        }
        return SimpleResponse.success(riskDeclares);
    }

    @ApiOperation("回退申报信息")
    @RequestMapping(value = "/rollback", method = RequestMethod.POST)
    public SimpleResponse rollback(@RequestBody ProjectRiskDeclare riskDeclare) {

        AuthMember authMember= (AuthMember) ContextHolder.getContext().getData();
        Member member = authMember.getMember();
        //一级处理中退回
        if (riskDeclare.getStatus().equals(DeclareStatus.DECLARE_FIRST_HANDLE.key()) || riskDeclare.getStatus().equals(DeclareStatus.RECOMMIT_FIST_HANDLE.key()) || riskDeclare.getStatus().equals(DeclareStatus.TWO_BACK_FIRST_HANDLE.key())) {
            if (riskDeclare.getDeclareSource().equals(DeclareSource.AUDIT.key()) || riskDeclare.getDeclareSource().equals(DeclareSource.RECHECK.key())) {
                riskDeclare.setStatus(DeclareStatus.FIRST_HANDLE_BACK_APPLY.key());
                riskDeclare.setFirstSurveyUserId(member.getId());
                riskDeclareRecordService.saveRiskRecord(riskDeclare, DeclareStatus.DECLARE_FIRST_HANDLE);
            } else {
                riskDeclare.setStatus(DeclareStatus.FIRST_HANDLE_BACK_COMMIT.key());
                riskDeclare.setFirstSurveyUserId(member.getId());
                riskDeclareRecordService.saveRiskRecord(riskDeclare, DeclareStatus.DECLARE_FIRST_HANDLE);
            }
        }
        //二级处理中退回
        if (riskDeclare.getStatus().equals(DeclareStatus.DECLARE_TWO_HANDLE.key()) || riskDeclare.getStatus().equals(DeclareStatus.FIRST_HANDLE_RECOMMIT_TWO_HANDLE.key())) {
            riskDeclare.setStatus(DeclareStatus.TWO_BACK_FIRST_HANDLE.key());
            riskDeclare.setTwoSurveyUserId(member.getId());
            riskDeclareRecordService.saveRiskRecord(riskDeclare, DeclareStatus.DECLARE_TWO_HANDLE);
        }
        riskDeclareService.update(riskDeclare);
        return SimpleResponse.success();
    }

    /**
     * 指派
     */
    @ApiOperation("指派")
    @RequestMapping(value = "/{memberId}/assign", method = RequestMethod.PUT)
    public SimpleResponse assign(@RequestBody List<RiskDeclareExtend> declareList,@PathVariable("memberId") Integer memberId) {
        declareList.forEach(riskDeclareExtend -> {
            String declareNo = riskDeclareExtend.getDeclareNo();
            ProjectRiskDeclareExample example = new ProjectRiskDeclareExample();
            example.createCriteria().andDeclareNoEqualTo(declareNo);
            ProjectRiskDeclare riskDeclare = Utils.getFirst(riskDeclareService.query(example));
            //一级待分配
            if (riskDeclare.getStatus().equals(DeclareStatus.DECLARE_FIRST_ASSIGN.key())) {
                riskDeclare.setStatus(DeclareStatus.DECLARE_FIRST_HANDLE.key());
            }
            riskDeclare.setFirstSurveyUserId(memberId);
            riskDeclareService.update(riskDeclare);

            AuthMember authMember= (AuthMember) ContextHolder.getContext().getData();
            messageService.sendMessage(memberId, declareNo, "风险申报新任务提醒", MessageTemplate.AssgnTemplate);
        });
        return SimpleResponse.success();
    }
//    @ApiOperation("指派")
//    @RequestMapping(value = "/{declareNo}/assign", method = RequestMethod.PUT)
//    public SimpleResponse assign(@PathVariable("declareNo") String declareNo, @RequestBody Member member) {
//        ProjectRiskDeclareExample example = new ProjectRiskDeclareExample();
//        example.createCriteria().andDeclareNoEqualTo(declareNo);
//        ProjectRiskDeclare riskDeclare = Utils.getFirst(riskDeclareService.query(example));
//        //一级待分配
//        if (riskDeclare.getStatus().equals(DeclareStatus.DECLARE_FIRST_ASSIGN.key())) {
//            riskDeclare.setStatus(DeclareStatus.DECLARE_FIRST_HANDLE.key());
//        }
//        riskDeclare.setFirstSurveyUserId(member.getId());
//        riskDeclareService.update(riskDeclare);
//
//        AuthMember authMember= (AuthMember) ContextHolder.getContext().getData();
//        messageService.sendMessage(authMember.getMember().getId(), declareNo, "风险申报新任务提醒", MessageTemplate.AssgnTemplate);
//        return SimpleResponse.success();
//    }

    /**
     * 获取
     */
    @ApiResponses({@ApiResponse(code = ErrorCode.NO_PROJECT_WIYHOUT_ASSIGN, message = "件池中项目已获取完")})
    @ApiOperation("获取")
    @RequestMapping(value = "/receive", method = RequestMethod.GET)
    public SimpleResponse receive() {
        AuthMember authMember= (AuthMember) ContextHolder.getContext().getData();
        Member member = authMember.getMember();
        ProjectRiskDeclareExample example = new ProjectRiskDeclareExample();
        example.setAdditionalWhere(riskDeclareService.checkHaveWork.get());
        If.of(riskDeclareService.count(example) > 0).isTrueThrow(() -> new AppException(ErrorCode.PROJECT_DOING));
        example.setAdditionalWhere(" 1=1");
        example.createCriteria().andStatusEqualTo(DeclareStatus.DECLARE_FIRST_ASSIGN.key());
        example.setOrderByClause("create_time asc");
        ProjectRiskDeclare riskDeclare = getFirst(riskDeclareService.query(example));
        if (riskDeclare == null) {
            If.of(riskDeclare == null).isTrueThrow(() -> new AppException(ErrorCode.NO_PROJECT_WIYHOUT_ASSIGN));
        }
        riskDeclare.setStatus(DeclareStatus.DECLARE_FIRST_HANDLE.key());
        riskDeclare.setFirstSurveyUserId(member.getId());
        riskDeclareService.update(riskDeclare);
        return SimpleResponse.success();
    }

    /**
     * 导入excel
     *
     * @param file
     * @param request
     * @param response
     * @return
     */
    @ApiOperation("风险客户申报导入")
    @RequestMapping(value = "/import", method = RequestMethod.POST)
    @ResponseBody
    public SimpleResponse<List<ErrImportRiskDeclareExtend>> importExcel(@RequestParam(value = "file", required = false) MultipartFile file, HttpServletRequest request, HttpServletResponse response) {

        List<ErrImportRiskDeclareExtend> errResult = riskDeclareService.importExcel(file);
//        if (CollectionUtils.isNotEmpty(errResult)) {
//            String fileName = "风险申报导入失败记录";
//            try {
//                response.setContentType("multipart/form-data");
//                ReportExcel.excelExport(errResult,
//                        fileName, null, ErrImportRiskDeclareExtend.class, 1, response, null);
//            } catch (Exception e) {
//                log.error("风险客户调查报告导出异常：", e);
//            }
//        }
        return SimpleResponse.success(errResult);
    }

    @ApiOperation("风险客户调查表导出")
    @RequestMapping(value = "/export", method = RequestMethod.GET)
    public void resultExport(
            @ApiParam("申报类型") @RequestParam(value = "declareType", required = false) String declareType,
            @ApiParam("申报人") @RequestParam(value = "declareUserName", required = false) String declareUserName,
            @ApiParam("申报来源") @RequestParam(value = "declareSource", required = false) String declareSource,
            @ApiParam("状态") @RequestParam(value = "status", required = false) List<Short> status,
            @ApiParam("身份证号") @RequestParam(value = "idCard", required = false) String idCard,
            @ApiParam("是否风险客户") @RequestParam(value = "riskCustomer", required = false) String riskCustomer,
            @ApiParam("虚假行为") @RequestParam(value = "declareBehavior", required = false) String declareBehavior,
            @ApiParam("虚假等级") @RequestParam(value = "declareLevel", required = false) String declareLevel,
            @ApiParam("一级调查人") @RequestParam(value = "firstSurveyUser", required = false) String firstSurveyUser,
            @ApiParam("进件编号") @RequestParam(value = "projectNo", required = false) String projectNo,
            @ApiParam("机构id") @RequestParam(value = "orgId", required = false) String orgId,
            @ApiParam("开始审核时间start") @RequestParam(value = "declareDateBegin", defaultValue = "1451630690") Long declareDateBegin,
            @ApiParam("开始审核时间end") @RequestParam(value = "declareDateEnd", defaultValue = "1895538782000") Long declareDateEnd,
            @ApiParam("处理日期start") @RequestParam(value = "handleDateBegin", defaultValue = "1451630690") Long handleDateBegin,
            @ApiParam("处理日期end") @RequestParam(value = "handleDateEnd", defaultValue = "1895538782000") Long handleDateEnd,
            HttpServletResponse response) {
        VProjectRiskDeclareExample example = new VProjectRiskDeclareExample();
        VProjectRiskDeclareExample.Criteria criteria = example.createCriteria();
        If.of(StringUtils.isNotEmpty(declareType)).isTrue(() -> criteria.andDeclareTypeEqualTo(declareType.trim()));
        If.of(StringUtils.isNotEmpty(declareUserName)).isTrue(() -> criteria.andDeclareUserNameEqualTo(declareUserName.trim()));
        If.of(StringUtils.isNotEmpty(declareSource)).isTrue(() -> criteria.andDeclareSourceEqualTo(Short.valueOf(declareSource.trim())));
        If.of(CollectionUtils.isNotEmpty(status)).isTrue(() -> criteria.andStatusIn(status));
        If.of(StringUtils.isNotEmpty(idCard)).isTrue(() -> criteria.andIdCardEqualTo(idCard.trim()));
        If.of(StringUtils.isNotEmpty(projectNo)).isTrue(() -> criteria.andProjectNoEqualTo(projectNo.trim()));
        If.of(StringUtils.isNotEmpty(riskCustomer)).isTrue(() -> criteria.andFirstSurveyResultEqualTo(riskCustomer.trim()));
        If.of(StringUtils.isNotEmpty(declareBehavior)).isTrue(() -> criteria.andFirstSurveyBehaviorEqualTo(declareBehavior.trim()));
        If.of(StringUtils.isNotEmpty(declareLevel)).isTrue(() -> criteria.andFirstSurveyLevelEqualTo(declareLevel.trim()));
        If.of(StringUtils.isNotEmpty(firstSurveyUser)).isTrue(() -> criteria.andFirstSurveyUserNameLike("%" + firstSurveyUser.trim() + "%"));
        If.of(StringUtils.isNotEmpty(orgId)).isTrue(() -> criteria.andOrgIdEqualTo(Integer.valueOf(orgId.trim())));
        Date dateStart = new Date(Instant.ofEpochMilli(declareDateBegin).toEpochMilli());
        Date dateEnd = new Date(Instant.ofEpochMilli(declareDateEnd).toEpochMilli());
        criteria.andDeclareDateBetween(dateStart, dateEnd);
        Date handleStart = new Date(Instant.ofEpochMilli(handleDateBegin).toEpochMilli());
        Date handleEnd = new Date(Instant.ofEpochMilli(handleDateEnd).toEpochMilli());
        criteria.andModifyTimeBetween(handleStart, handleEnd);
        example.setAdditionalWhere(riskDeclareService.over.get());
        List<RiskDeclareExportExtend> result = Lists.newArrayList();
        example.setOrderByClause("create_time  desc");
        VRiskDeclareService.query(example).forEach(projectRiskDeclare -> {
            RiskDeclareExportExtend riskDeclareExportExtend = new RiskDeclareExportExtend(projectRiskDeclare);
            if ("不是风险客户".equals(projectRiskDeclare.getFirstSurveyResult())) {
                riskDeclareExportExtend.setDeclareDepth("一级调查");
            } else {
                riskDeclareExportExtend.setDeclareDepth("二级调查");
            }
            result.add(riskDeclareExportExtend);
        });
        String fileName = "风险客户调查报告";
        try {
            ReportExcel.excelExport(result,
                    fileName, null, RiskDeclareExportExtend.class, 1, response, null);
        } catch (Exception e) {
            log.error("风险客户调查报告导出异常：", e);
        }
    }

    @ApiOperation("待办项目")
    @GetMapping(value = "/backlog")
    public SimpleResponse<Long> backlog() {
        ProjectRiskDeclareExample example = new ProjectRiskDeclareExample();
        example.createCriteria().andStatusEqualTo(DeclareStatus.DECLARE_FIRST_ASSIGN.key());
        long count = riskDeclareService.count(example);
        return SimpleResponse.success(count);
    }
}
