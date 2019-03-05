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
public class VReportQhzxExtend {

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

    @ExcelAnnotation(id = 9, name = {"是否真实工作单位"})
    private String isRealCompany;

    @ExcelAnnotation(id = 10, name = {"单位匹配度分值"})
    private Integer companySimDeg;

    @ExcelAnnotation(id = 11, name = {"是否存在关系"})
    private String isExistRel;

    @ExcelAnnotation(id = 12, name = {"关系力度"})
    private String relLevel;

    @ExcelAnnotation(id = 13, name = {"房屋验证结果"})
    private String houseChkResult;

    @ExcelAnnotation(id = 14, name = {"手机验证结果"})
    private String isOwnerMobile;

    @ExcelAnnotation(id = 15, name = {"手机状态"})
    private String ownerMobileStatus;

    @ExcelAnnotation(id = 16, name = {"使用时间分数"})
    private String useTimeScore;

    @ExcelAnnotation(id = 17, name = {"总风险得分"})
    private Double riskScoreCount;

    @ExcelAnnotation(id = 18, name = {"总逾期次数"})
    private Integer riskItemCount;

    @ExcelAnnotation(id = 19, name = {"最高风险得分"})
    private Double riskScoreMax;

    @ExcelAnnotation(id = 20, name = {"最高风险得分业务发生日期"},width = 4000)
    private Date riskScoreMaxDate;

    @ExcelAnnotation(id = 21, name = {"最近一次风险得分"})
    private Double recentRiskScore;

    @ExcelAnnotation(id = 22, name = {"最近一次风险得分业务发生日期"},width = 4500)
    private Date recentRiskScoreDate;

    public VReportQhzxExtend() {
    }

    public VReportQhzxExtend(VReportCredit vReportDf) {
        BeanUtil.copyProperty(vReportDf, this);
    }
}
