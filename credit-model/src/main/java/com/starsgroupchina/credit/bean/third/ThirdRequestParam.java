package com.starsgroupchina.credit.bean.third;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@ApiModel
@Data
public class ThirdRequestParam implements Serializable {

    @ApiModelProperty(value = "id",required = true)
    private Integer projectId;

    @ApiModelProperty(value = "id",required = true)
    private String projectNo;
}
