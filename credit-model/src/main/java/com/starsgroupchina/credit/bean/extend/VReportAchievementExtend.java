package com.starsgroupchina.credit.bean.extend;

import com.starsgroupchina.common.utils.BeanUtil;
import com.starsgroupchina.common.utils.export.ExcelAnnotation;
import com.starsgroupchina.credit.bean.model.VReportAchievement;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: QinHaoHao
 * @Description:
 * @Date: Created in 18:03 2018/9/4
 * @Modifed By:
 */
@Data
@ApiModel
public class VReportAchievementExtend implements Serializable {

    private static final long serialVersionUID = 1L;

    @ExcelAnnotation(id = 1, name = {"姓名"})
    @ApiModelProperty("姓名")
    private String name;

    @ExcelAnnotation(id = 2, name = {"工号"})
    @ApiModelProperty("工号")
    private String loginName;

    @ExcelAnnotation(id = 3, name = {"岗位"})
    @ApiModelProperty("岗位")
    private String roleName;

    @ExcelAnnotation(id = 4, name = {"mob月数"})
    @ApiModelProperty("mob月数")
    private Integer mobMonth;

    @ExcelAnnotation(id = 5, name = {"处理件数"})
    @ApiModelProperty("处理件数")
    private Integer handleNum;

    @ExcelAnnotation(id = 6, name = {"出勤天数"})
    @ApiModelProperty("出勤天数")
    private Integer dayNum;

    @ExcelAnnotation(id = 7, name = {"日均处理件数"})
    @ApiModelProperty("日均处理件数")
    private Double dayHandle;

    @ExcelAnnotation(id = 8, name = {"差错率"})
    @ApiModelProperty("差错率")
    private Double errorRate;

    public VReportAchievementExtend(VReportAchievement vReportAchievement) {
        BeanUtil.copyProperty(vReportAchievement, this);
    }

    public VReportAchievementExtend() {
    }
}
