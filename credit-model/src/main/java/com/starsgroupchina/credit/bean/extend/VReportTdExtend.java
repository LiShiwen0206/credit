package com.starsgroupchina.credit.bean.extend;

import com.starsgroupchina.common.utils.BeanUtil;
import com.starsgroupchina.common.utils.export.ExcelAnnotation;
import com.starsgroupchina.credit.bean.model.VReportCredit;
import lombok.Data;

import java.util.Date;

/**
 * Created by gexiaoshan on 2018/9/6.
 */
@Data
public class VReportTdExtend {

    @ExcelAnnotation(id = 1,name ={"序号"})
    private Integer rowId;

    @ExcelAnnotation(id = 2, name = {"客户名称"})
    private String userName;

    @ExcelAnnotation(id = 3, name = {"身份证号码"},width = 4000)
    private String userPhone;

    @ExcelAnnotation(id = 4, name = {"进件编号"})
    private String projectNo;

    @ExcelAnnotation(id = 5, name = {"产品名称"})
    private String prodectName;

    @ExcelAnnotation(id = 6, name = {"产品类别"})
    private String categoryName;

    @ExcelAnnotation(id = 7, name = {"分公司名称"},width = 4000)
    private String orgName;

    @ExcelAnnotation(id = 8, name = {"查询时间"})
    private Date createTime;

    @ExcelAnnotation(id = 9, name = {"手机号命中中风险关注名单"},width = 5000)
    private Integer d1;

    @ExcelAnnotation(id = 10, name = {"手机号命中低风险关注名单"},width = 5000)
    private Integer d2;

    @ExcelAnnotation(id = 11, name = {"身份证命中低风险关注名单"},width = 5000)
    private Integer d3;

    @ExcelAnnotation(id = 12, name = {"身份证_姓名命中法院执行模糊名单"},width = 5000)
    private Integer d4;

    @ExcelAnnotation(id = 13, name = {"手机号命中高风险关注名单"},width = 5500)
    private Integer d5;

    @ExcelAnnotation(id = 14, name = {"身份证命中中风险关注名单"},width = 5500)
    private Integer d6;

    @ExcelAnnotation(id = 14, name = {"身份证命中法院结案名单"},width = 5500)
    private Integer d7;

    @ExcelAnnotation(id = 15, name = {"身份证命中信贷逾期后还款名单"},width = 5500)
    private Integer d8;

    @ExcelAnnotation(id = 16, name = {"身份证命中信贷逾期名单"},width = 5500)
    private Integer d9;

    @ExcelAnnotation(id = 17, name = {"身份证_姓名命中法院结案模糊名单"},width = 5500)
    private Integer d10;

    @ExcelAnnotation(id = 18, name = {"身份证命中法院执行名单"},width = 5500)
    private Integer d11;

    @ExcelAnnotation(id = 19, name = {"身份证命中高风险关注名单"},width = 5500)
    private Integer d12;

    @ExcelAnnotation(id = 20, name = {"手机号命中信贷逾期名单"},width = 5500)
    private Integer d13;

    @ExcelAnnotation(id = 21, name = {"身份证命中法院失信名单"},width = 5500)
    private Integer d14;

    @ExcelAnnotation(id = 22, name = {"身份证_姓名命中法院失信模糊名单"},width = 5500)
    private Integer d15;

    @ExcelAnnotation(id = 23, name = {"身份证命中欠税公司法人代表名单"},width = 5500)
    private Integer d16;


    @ExcelAnnotation(id = 30, name = {"7天内申请人在多个平台申请借款"},width = 5500)
    private Integer e1;
    @ExcelAnnotation(id = 31, name = {"1个月内申请人在多个平台申请借款"},width = 5500)
    private Integer e2;
    @ExcelAnnotation(id = 32, name = {"3个月内申请人在多个平台申请借款"},width = 5500)
    private Integer e3;
    @ExcelAnnotation(id = 33, name = {"3个月内申请人在多个平台被放款_不包含本合作方"},width = 7500)
    private Integer e4;

    @ExcelAnnotation(id = 34, name = {"风险得分"})
    private Long score;

    public VReportTdExtend() {
    }

    public VReportTdExtend(VReportCredit vReportCredit) {
        BeanUtil.copyProperty(vReportCredit,this);
    }

}
