package com.starsgroupchina.credit.bean.extend;

import com.starsgroupchina.common.utils.BeanUtil;
import com.starsgroupchina.common.utils.export.ExcelAnnotation;
import com.starsgroupchina.credit.bean.model.VReportDayAudit;
import lombok.Data;

/**
 * @Author: QinHaoHao
 * @Description:
 * @Date: Created in 15:49 2018/9/4
 * @Modifed By:
 */
@Data
public class ReportDayAuditExcel {

    @ExcelAnnotation(id = 1,name ={"日期"})
    private Object date;

    @ExcelAnnotation(id = 2,name ={"进件数"})
    private Integer entryNum;

    @ExcelAnnotation(id = 3,name ={"审核处理件数"})
    private Integer auditHandleNum;

    @ExcelAnnotation(id = 4,name ={"审核出勤人数"})
    private Integer auditAttendanceNum;

    @ExcelAnnotation(id = 5,name ={"审核人均件数"})
    private Double auditAverageNum;

    @ExcelAnnotation(id = 6,name ={"审核处理率"})
    private Double auditTreatmentRate;

    @ExcelAnnotation(id = 7,name ={"复核处理件数"})
    private Integer recheckHandleNum;

    @ExcelAnnotation(id = 8,name ={"复核出勤人数"})
    private Integer recheckAttendanceNum;

    @ExcelAnnotation(id = 9,name ={"复核人均件数"})
    private Double recheckAverageNum;

    @ExcelAnnotation(id = 10,name ={"复核处理率"})
    private Double recheckTreatmentRate;

    @ExcelAnnotation(id = 11,name ={"待处理"})
    private Integer pending;

    public ReportDayAuditExcel() {
    }

    public ReportDayAuditExcel(VReportDayAudit reportDayAudit) {
        BeanUtil.copyBean(reportDayAudit, this);
    }
}
