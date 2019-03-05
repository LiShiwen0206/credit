package com.starsgroupchina.credit.bean.extend;

import com.starsgroupchina.common.utils.BeanUtil;
import com.starsgroupchina.common.utils.export.ExcelAnnotation;
import com.starsgroupchina.credit.bean.enums.DeclareSource;
import com.starsgroupchina.credit.bean.model.VProjectRiskDeclare;
import lombok.Data;

import java.util.Date;

/**
 * @Author: QinHaoHao
 * @Description:
 * @Date: Created in 17:02 2018/8/30
 * @Modifed By:
 */
@Data
public class RiskDeclareExportExtend {

    @ExcelAnnotation(id = 1, name = {"申报编号"})
    private String declareNo;

    @ExcelAnnotation(id = 2, name = {"进件编号"})
    private String projectNo;

    @ExcelAnnotation(id = 3, name = {"客户姓名"})
    private String customerName;

    @ExcelAnnotation(id = 4, name = {"身份证号"})
    private String idCard;

    @ExcelAnnotation(id = 5, name = {"风险客户"})
    private String firstSurveyResult;

    @ExcelAnnotation(id = 6, name = {"虚假行为"})
    private String firstSurveyBehavior;

    @ExcelAnnotation(id = 7, name = {"虚假等级"}, width = 5000)
    private String firstSurveyLevel;

    @ExcelAnnotation(id = 8, name = {"调查深度"})
    private String declareDepth;

    @ExcelAnnotation(id = 9, name = {"申报类别"}, width = 5000)
    private String declareType;

    @ExcelAnnotation(id = 10, name = {"申报来源"}, width = 4000)
    private String declareSource;

    @ExcelAnnotation(id = 11, name = {"机构名称"})
    private String orgName;

    @ExcelAnnotation(id = 12, name = {"申报人"})
    private String declareUserName;

    @ExcelAnnotation(id = 13, name = {"申报日期"}, width = 4000)
    private Date declareDate;

    @ExcelAnnotation(id = 14, name = {"调查人"})
    private String firstSurveyUserName;

    @ExcelAnnotation(id = 15, name = {"处理日期"}, width = 4000)
    private Date modifyTime;

    public RiskDeclareExportExtend(VProjectRiskDeclare project) {
//        BeanUtil.copyProperty(project, this);
        BeanUtil.copyBean(project,this);
//       this.setCustomerName(project.getCustomerName());
//        this.setDeclareDate(project.getDeclareDate());
//        this.setDeclareNo(project.getDeclareNo());
        if (project.getDeclareSource().equals(DeclareSource.RECHECK.key())){
            this.setDeclareSource(DeclareSource.RECHECK.value());
        }
        if (project.getDeclareSource().equals(DeclareSource.DIRECT.key())){
            this.setDeclareSource(DeclareSource.DIRECT.value());
        }
        if (project.getDeclareSource().equals(DeclareSource.IMPORT.key())){
            this.setDeclareSource(DeclareSource.IMPORT.value());
        }
        if (project.getDeclareSource().equals(DeclareSource.AUDIT.key())){
            this.setDeclareSource(DeclareSource.AUDIT.value());
        }
//        this.setDeclareType(project.getDeclareType());
//        this.setDeclareUserName(project.getDeclareUserName());
//        this.setFirstSurveyBehavior(project.getFirstSurveyBehavior());
//        this.setFirstSurveyLevel(project.getFirstSurveyLevel());
//        this.setFirstSurveyResult(project.getFirstSurveyResult());
//        this.setFirstSurveyUserName(project.getFirstSurveyUserName());
//        this.setIdCard(project.getIdCard());
//        this.setModifyTime(project.getModifyTime());
//        this.setOrgName(project.getOrgName());
//        this.setProjectNo(project.getProjectNo());
    }
}





