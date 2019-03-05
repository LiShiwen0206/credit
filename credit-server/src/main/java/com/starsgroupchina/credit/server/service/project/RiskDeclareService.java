package com.starsgroupchina.credit.server.service.project;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.starsgroupchina.common.base.AbstractService;
import com.starsgroupchina.common.context.ContextHolder;
import com.starsgroupchina.common.exception.AppException;
import com.starsgroupchina.common.objects.If;
import com.starsgroupchina.common.utils.Utils;
import com.starsgroupchina.credit.bean.AuthMember;
import com.starsgroupchina.credit.bean.enums.DeclareSource;
import com.starsgroupchina.credit.bean.enums.DeclareStatus;
import com.starsgroupchina.credit.bean.enums.RiskStatus;
import com.starsgroupchina.credit.bean.extend.ErrImportRiskDeclareExtend;
import com.starsgroupchina.credit.bean.model.*;
import com.starsgroupchina.credit.key.ErrorCode;
import com.starsgroupchina.credit.server.service.NumberService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.starsgroupchina.common.utils.Utils.getFirst;
import static java.util.stream.Collectors.toList;

/**
 * @Author: QinHaoHao
 * @Description:
 * @Date: Created in 16:48 2018/8/16
 * @Modifed By:
 */
@Slf4j
@Service
public class RiskDeclareService extends AbstractService<ProjectRiskDeclare, ProjectRiskDeclareExample> {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private RiskDeclareRecordService riskDeclareRecordService;

    @Autowired
    private NumberService numberRuleService;
    private static Set<String> TYPE_INSTANT = ImmutableSet.of(
            "虚假调查", "贷前调查", "逾期调查", "违规调查","策略调查"
    );
    //待提交
    public Supplier<String> commitWait = () ->
            String.format(" declare_source in (%s,%s) ", DeclareSource.IMPORT.key(), DeclareSource.DIRECT.key());

    //一级待分配
    public Supplier<String> firstAssign = () -> String.format(" status in (%s,%s,%s,%s)", DeclareStatus.DECLARE_FIRST_ASSIGN.key(), DeclareStatus.DECLARE_FIRST_HANDLE.key(),DeclareStatus.RECOMMIT_FIST_HANDLE.key(),DeclareStatus.TWO_BACK_FIRST_HANDLE.key());

    //一级调查
    public Supplier<String> firstInvestigate = () ->
            String.format(" status in (%s,%s,%s) and first_survey_user_id=%s",
                    DeclareStatus.DECLARE_FIRST_HANDLE.key(), DeclareStatus.TWO_BACK_FIRST_HANDLE.key(), DeclareStatus.RECOMMIT_FIST_HANDLE.key(),
                    ((AuthMember) ContextHolder.getContext().getData()).getMember().getId());

    //二级调查
    public Supplier<String> twoInvestigate = () ->
            String.format(" status in (%s,%s)",
                    DeclareStatus.DECLARE_TWO_HANDLE.key(), DeclareStatus.FIRST_HANDLE_RECOMMIT_TWO_HANDLE.key());

    //风险客户调查表
    public Supplier<String> over = () -> String.format(" status = %s", DeclareStatus.DECLARE_OVER.key());

    //检测当前任务
    public Supplier<String> checkHaveWork = () ->
            String.format(" status in (%s,%s,%s) and first_survey_user_id = %s ",
                    DeclareStatus.DECLARE_FIRST_HANDLE.key(), DeclareStatus.TWO_BACK_FIRST_HANDLE.key(), DeclareStatus.RECOMMIT_FIST_HANDLE.key(),
                    ((AuthMember) ContextHolder.getContext().getData()).getMember().getId());

    /**
     * 根据projectNo查询正在申报的风险项目
     *
     * @param projectNo
     * @return
     */
    public ProjectRiskDeclare getRiskDeclare(String projectNo, DeclareSource declareSource) {
        ProjectRiskDeclareExample example = new ProjectRiskDeclareExample();
        example.createCriteria().andProjectNoEqualTo(projectNo).andDeclareSourceEqualTo(declareSource.key()).andStatusNotEqualTo(DeclareStatus.DECLARE_OVER.key());
        return Utils.getFirst(this.query(example));
    }

    public long getRiskDeclareCount(String projectNo, DeclareSource declareSource) {
        ProjectRiskDeclareExample example = new ProjectRiskDeclareExample();
        example.createCriteria().andProjectNoEqualTo(projectNo).andDeclareSourceEqualTo(declareSource.key());
        return this.count(example);
    }

    public List<ErrImportRiskDeclareExtend> importExcel(MultipartFile file) {
        String regExNum = "[^0-9]";
        String regExPro = "[^A-z]";
        Pattern pNum = Pattern.compile(regExNum);
        Pattern pPro = Pattern.compile(regExPro);
        AuthMember authMember= (AuthMember) ContextHolder.getContext().getData();
        Member member = authMember.getMember();
        Integer headOrgId = authMember.getOrg().getHeadOrgId();
        try {
            InputStream is = file.getInputStream();
            XSSFWorkbook workbook = new XSSFWorkbook(is);
            XSSFSheet sheet = workbook.getSheetAt(0);
            XSSFRow row;
            NumberRuleExample example = new NumberRuleExample();
            example.createCriteria().andNoNameEqualTo("申报编号").andOrgIdEqualTo(headOrgId);
            NumberRule numberRule = getFirst(numberRuleService.query(example));
            If.of(numberRule == null).isTrueThrow(() -> new AppException(ErrorCode.PROJECT_DECLARE_NO_RULE_NOT_EXITS));
            String number = numberRuleService.getNumber(numberRule, sheet.getLastRowNum());

            Matcher mNum = pNum.matcher(number);
            Matcher mPro = pPro.matcher(number);
            String pro = mPro.replaceAll("").trim();
            List<ErrImportRiskDeclareExtend> errResult = Lists.newArrayList();
            long num = Long.valueOf(mNum.replaceAll("").trim());
            //sheet.getLastRowNum() 由0开始
            if (sheet == null || sheet.getLastRowNum() < 1) {
                throw new AppException(ErrorCode.BLACKLIST_IMPORT_EMPTY);
            } else {
                log.info("import riskDeclare start");
                if (sheet.getLastRowNum() > 0) {
                    for (int i = 1; i < sheet.getLastRowNum() + 1; i++) {
                        row = sheet.getRow(i);
                        XSSFCell cell0 = row.getCell(0);
                        if (row != null && cell0 != null) {
                            cell0.setCellType(CellType.STRING);
                            String projectNo = cell0.getStringCellValue();
                            if (StringUtils.isBlank(projectNo)) {
                                continue;
                            }
                            ProjectRiskDeclare riskDeclare = new ProjectRiskDeclare();
                            XSSFCell cell1 = row.getCell(1);
                            cell1.setCellType(CellType.STRING);
                            String declareType = cell1.getStringCellValue();
                            XSSFCell cell2 = row.getCell(2);
                            cell2.setCellType(CellType.STRING);
                            String declareReason = cell2.getStringCellValue();

                            Project project = projectService.getProjectByProjectNo(projectNo);
                            if (project == null) {
                                addErrorItem(errResult, projectNo, declareType, declareReason, "项目编号有误");
                                continue;
                            }
                            riskDeclare.setProjectNo(projectNo);
                            if (checkExist(projectNo)){
                                addErrorItem(errResult, projectNo, declareType, declareReason, "项目正在申报或者已申报为风险客户");
                                continue;
                            }
                            if (!TYPE_INSTANT.contains(declareType)) {
                                addErrorItem(errResult, projectNo, declareType, declareReason, "申报类型有误");
                                continue;
                            }
                            riskDeclare.setDeclareType(declareType);
                            riskDeclare.setDeclareReason(declareReason);
                            riskDeclare.setDeclareSource(DeclareSource.IMPORT.key());
                            riskDeclare.setDeclareNo(pro + num);
                            riskDeclare.setStatus(DeclareStatus.DECLARE_COMMIT_NO.key());
                            riskDeclare.setDeclareUserId(member.getId());
                            num++;
                            this.create(riskDeclare);
                            riskDeclare.setFirstSurveyResult("【范本导入】申报调查");
                            riskDeclareRecordService.saveRiskRecord(riskDeclare, DeclareStatus.DECLARE_COMMIT_NO);
                        }
                    }
                }
                log.info("import riskDeclare completed");
            }
            workbook.close();
            is.close();
            return errResult;
        } catch (AppException e) {
            log.error("", e);
            throw e;
        } catch (Exception e) {
            log.error("", e);
            throw new AppException(ErrorCode.DECLARE_IMPORT_UNKOWN_ERROR, e);
        }
    }

    private void addErrorItem(List<ErrImportRiskDeclareExtend> errResult, String projectNo, String declareType, String declareReason, String errReason) {
        ErrImportRiskDeclareExtend err = new ErrImportRiskDeclareExtend();
        err.setProjectNo(projectNo);
        err.setDeclareReason(declareReason);
        err.setDeclareType(declareType);
        err.setErrReason(errReason);
        errResult.add(err);
    }

    public boolean checkExist(String projectNo){
        ProjectRiskDeclareExample example = new ProjectRiskDeclareExample();
        String sql = String.format(" (project_no='%s' and two_survey_result='是风险客户') or ( project_no='%s' and status<> 999 )", projectNo,projectNo);
        example.setAdditionalWhere(sql);
        List<ProjectRiskDeclare> result = this.query(example).collect(toList());
        return result.size()>0;
    }

    @Service
    public class VRiskDeclareService extends AbstractService<VProjectRiskDeclare, VProjectRiskDeclareExample> {

    }
}
