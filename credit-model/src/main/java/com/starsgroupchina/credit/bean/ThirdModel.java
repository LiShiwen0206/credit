package com.starsgroupchina.credit.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by zhangfeng on 2018/7/13
 */
@ApiModel
@Data
public class ThirdModel {

    private TDModel TD;

    private QHModel QH;

    @Data
    @ApiModel
    public class TDModel {

        @ApiModelProperty("风险得分")
        private Integer riskScore;

        @ApiModelProperty("7天内申请人在多个平台申请借款")
        private Integer sevenHeadLoan;

        @ApiModelProperty("1个月内申请人在多个平台申请借款")
        private Integer oneMonthHeadLoan;

        @ApiModelProperty("3个月内申请人在多个平台申请借款")
        private Integer threeMonthHeadLoan;

        @ApiModelProperty("3个月内申请人在多个平台被放款_不包含本合作方")
        private Integer threeMonthHeadLoanN;

        @ApiModelProperty("身份证命中信贷逾期名单")
        private Integer idCardCreditOverdue=-1;

        @ApiModelProperty("手机号命中信贷逾期名单")
        private Integer phoneCreditOverdue=0;

        @ApiModelProperty("身份证命中法院失信名单")
        private Integer idCardCurtDiscredit=0;

        @ApiModelProperty("身份证命中法院执行名单")
        private Integer idCardCurtExecute=0;

        @ApiModelProperty("身份证_姓名命中法院失信模糊名单")
        private Integer idCardNameCurtDiscredit=0;

        @ApiModelProperty("身份证_姓名命中法院执行模糊名单")
        private Integer idCardNameCurtExecute=0;

        @ApiModelProperty("身份证命中欠税公司法人代表名单")
        private Integer taxCompanyCorporation=0;
    }


    @Data
    @ApiModel
    public class QHModel {

        @ApiModelProperty("是否真实工作单位(0：否 1：是  9：库中无数据 -1、不适用)")
        private Integer isRealCompany=-1;

        @ApiModelProperty("是否存在关系(0：否 1：是  9：库中无数据 -1、不适用)")
        private Integer isExistRel=-1;

        @ApiModelProperty("房屋验证结果(1:有房9:库中无数据 -1:不适用)")
        private Integer houseChkResult=-1;

        @ApiModelProperty("手机验证结果(0：手机号、证件号、姓名均一致(该手机号在运营商有登记，且证件号和手机都登记正确，无风险)1：手机号和证件号一致，姓名不一致(该手机号在运营商有登记，且证件号登记正确但姓名不正确，可能为姓名登记错误的情况，请结合身份验证情况进一步判断风险)2：手机号和证件号一致，姓名不明确(该手机号在运营商有登记，且证件号登记正确但姓名不明确，可能为姓名遗漏登记的情况，请结合身份验证情况进一步判断风险)3：手机号一致，证件号和姓名不一致(该手机号在运营商有登记，但实名登记情况不正确，存在欺诈风险)9：库中无数据(该手机号在运营商没有登记或该手机号登记信息暂时无法获取)99：手机号T-1月前已离网)-1、不适用")
        private Integer isOwnerMobile2=-1;

        @ApiModelProperty("手机状态(1：正常2：停机3：不可用4：已销号5：预销号9：不明确 -1、不适用)")
        private Integer ownerMobileStatus2=-1;

        @ApiModelProperty("使用时间分数II(-1：不可用 1：(0-1]2： (1-2]，3： (2-6]4： (6-12]5： (12-24]6 ：(24-36]7： (36,+]30 ：(0,6]60： （24,+]99：手机号T-1月前已离网)区段的单位为月。如：(2,6] 表示大于2个月且小于等于6个月 -1、不适用")
        private Integer useTimeScore2=-1;

        @ApiModelProperty("总风险得分(-1：查询失败)")
        private Double riskScoreCount=-1.0;

        @ApiModelProperty("总逾期次数(-1：查询失败)")
        private Integer riskItemCount=-1;

        @ApiModelProperty("最高风险得分(-1：查询失败)")
        private Double riskScoreMax=-1.0;

        @ApiModelProperty("最高风险得分业务发生时间(-1：查询失败)")
        private Integer riskScoreMaxYear=-1;

        @ApiModelProperty("最近一次风险得分(-1：查询失败)")
        private Double recentRiskScore=-1.0;

        @ApiModelProperty("最近一次风险得分业务发生日期(-1：查询失败)")
        private Integer recentRiskScoreYear=-1;

        @ApiModelProperty("最近3年逾期次数(最近3年内的风险验证个数)(-1：查询失败)")
        private Integer riskNumThreeYear=-1;

        @ApiModelProperty("最近3年最高风险得分(最近3年内的最高风险得分)(-1：查询失败)")
        private Double riskScoreThreeYear=-1.0;

        @ApiModelProperty("最近半年最高风险得分(-1：查询失败)")
        private Double riskScoreHalfYear=-1.0;

        @ApiModelProperty("最近半年逾期次数(-1：查询失败)")
        private Integer riskNumHalfYear=-1;

        @ApiModelProperty("最近1年最高风险得分(-1：查询失败)")
        private Double riskScoreOneYear=-1.0;

        @ApiModelProperty("最近1年逾期次数(-1：查询失败)")
        private Integer riskNumOneYear=-1;

        @ApiModelProperty("最近2年最高风险得分(-1：查询失败)")
        private Double riskScoreTwoYear=-1.0;

        @ApiModelProperty("最近2年逾期次数(-1：查询失败)")
        private Integer riskNumTwoYear=-1;
    }
}
