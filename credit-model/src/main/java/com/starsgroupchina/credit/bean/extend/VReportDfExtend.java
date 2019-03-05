package com.starsgroupchina.credit.bean.extend;

import com.starsgroupchina.common.utils.BeanUtil;
import com.starsgroupchina.common.utils.export.ExcelAnnotation;
import com.starsgroupchina.credit.bean.model.VReportCredit;
import lombok.Data;

import java.util.Date;

/**
 * Created by gexiaoshan on 2018/9/3.
 */
@Data
public class VReportDfExtend {

    @ExcelAnnotation(id = 1,name ={"序号"})
    private Integer rowId;

    @ExcelAnnotation(id = 2, name = {"客户名称"})
    private String userName;

    @ExcelAnnotation(id = 3, name = {"身份证号码"},width = 3500)
    private String userPhone;

    @ExcelAnnotation(id = 4, name = {"进件编号"})
    private String projectNo;

    @ExcelAnnotation(id = 5, name = {"产品名称"})
    private String prodectName;

    @ExcelAnnotation(id = 6, name = {"产品类别"})
    private String categoryName;

    @ExcelAnnotation(id = 7, name = {"分公司名称"},width = 3500)
    private String orgName;

    @ExcelAnnotation(id = 8, name = {"查询时间"})
    private Date createTime;

    public VReportDfExtend() {
    }

    public VReportDfExtend(VReportCredit vReportDf) {
        BeanUtil.copyProperty(vReportDf, this);
    }
}
