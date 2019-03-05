package com.starsgroupchina.credit.bean.third;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@ApiModel
@Data
public class ThirdCreditQhzxExtend {

    private List<ThirdCreditQhzx8036> thirdCreditQhzx8036;

    private List<ThirdCreditQhzx8107> thirdCreditQhzx8107;

    @ApiModelProperty("是否调用过第三方接口，true为调用过")
    private Boolean isQueryThird = true;
}
