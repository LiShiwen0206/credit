package com.starsgroupchina.credit.bean.extend;

import com.starsgroupchina.common.utils.BeanUtil;
import com.starsgroupchina.common.utils.export.ExcelAnnotation;
import com.starsgroupchina.credit.bean.model.VProject;
import lombok.Data;

import java.util.Date;

/**
 * Created by gexiaoshan on 2018/8/28.
 */
@Data
public class ProjectQualityExportExtend {

    @ExcelAnnotation(id = 1, name = {"质检日期(起)"},width = 4000)
    private Date beginQualityDate;

    @ExcelAnnotation(id = 2, name = {"质检日期(末)"},width = 4000)
    private Date endQualityDate;

    @ExcelAnnotation(id = 3, name = {"进件编号"})
    private String projectNo;

    @ExcelAnnotation(id = 4, name = {"客户姓名"})
    private String userName;

    @ExcelAnnotation(id = 5, name = {"身份证号"})
    private String userIdCard;

    @ExcelAnnotation(id = 6, name = {"机构名称"})
    private String orgName;

    @ExcelAnnotation(id = 7, name = {"质检人"})
    private String qualityUserName;

    @ExcelAnnotation(id = 8, name = {"审核错误类别"},width = 5000)
    private String auditType;

    @ExcelAnnotation(id = 9, name = {"审核错误等级"},width = 5000)
    private String auditLevel;

    @ExcelAnnotation(id = 10, name = {"审核人"})
    private String auditUserName;

    @ExcelAnnotation(id = 11, name = {"审核日期"})
    private Date beginAuditDate;

    @ExcelAnnotation(id = 12, name = {"复核人"})
    private String auditRecheckUserName;

    public ProjectQualityExportExtend(VProject project) {
        BeanUtil.copyProperty(project, this);
    }
}





