package com.starsgroupchina.credit.bean.extend;

import com.starsgroupchina.common.utils.BeanUtil;
import com.starsgroupchina.credit.bean.RiskReport;
import com.starsgroupchina.credit.bean.model.ProjectReport;
import com.starsgroupchina.credit.bean.model.ThirdDataValidFailRecord;
import com.starsgroupchina.credit.bean.third.DfRespsonseDataExtend;
import com.starsgroupchina.credit.bean.third.ThirdCreditQhzxExtend;
import com.starsgroupchina.credit.bean.third.ThirdCreditTdReport;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @Author: QinHaoHao
 * @Description:
 * @Date: Created in 15:34 2018/7/5
 * @Modifed By:
 */
@Data
public class ReportExtend extends ProjectReport{

    @ApiModelProperty("大峰")
    private DfRespsonseDataExtend dfData;

    @ApiModelProperty("前海")
    private ThirdCreditQhzxExtend qhData;

    @ApiModelProperty("同盾")
    private ThirdCreditTdReport tdData;

    private RiskReport riskReport;

    @ApiModelProperty("同盾数据验证失败字段")
    private List<ThirdDataValidFailRecord> tdFailList;

    @ApiModelProperty("前海数据验证失败字段")
    private List<ThirdDataValidFailRecord> qhFailList;

    public ReportExtend(ProjectReport projectReport) {
        BeanUtil.copyProperty(projectReport, this);
    }

    public ReportExtend() {
    }
}
