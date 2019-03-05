package com.starsgroupchina.credit.bean.extend;

import com.starsgroupchina.common.utils.export.ExcelAnnotation;
import lombok.Data;

/**
 * @Author: QinHaoHao
 * @Description:
 * @Date: Created in 15:00 2018/9/17
 * @Modifed By:
 */
@Data
public class ErrImportRiskDeclareExtend {

    @ExcelAnnotation(id = 1, name = {"项目编号"})
    private String projectNo;

    @ExcelAnnotation(id = 2, name = {"申报类别"}, width = 5000)
    private String declareType;

    @ExcelAnnotation(id = 3, name = {"申报原因"}, width = 5000)
    private String declareReason;

    @ExcelAnnotation(id = 4, name = {"错误原因"}, width = 5000)
    private String errReason;
}
