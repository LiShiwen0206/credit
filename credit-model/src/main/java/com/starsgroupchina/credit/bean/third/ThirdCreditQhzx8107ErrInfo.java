package com.starsgroupchina.credit.bean.third;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@ApiModel
@Data
public class ThirdCreditQhzx8107ErrInfo implements Serializable {

    @ApiModelProperty("返回结果代码")
    private String retCode;

    @ApiModelProperty("返回结果信息")
    private String retMsg;

    @ApiModelProperty("子产品类型(1. 实名验证 2. 地址验证 3.工作单位验证 4.手机验证(请用第十位，为升级版) 5.关系人验证 6.车辆验证 7.房产验证 8.人脸识别 9.学历验证 10.手机验证II 11.关系人验证II)")
    private Integer typeId;


    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table third_credit_qhzx8107_err_info
     *
     * @mbggenerated Wed Jun 13 02:47:31 CST 2018
     */
    private static final long serialVersionUID = 1L;

}