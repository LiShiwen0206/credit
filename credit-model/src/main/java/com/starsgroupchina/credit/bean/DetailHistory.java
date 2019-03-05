package com.starsgroupchina.credit.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @Author: QinHaoHao
 * @Description:
 * @Date: Created in 15:03 2018/7/9
 * @Modifed By:
 */
@Data
@ApiModel
public class DetailHistory implements Comparable<DetailHistory>{

    @ApiModelProperty("id")
    private Integer id;

    @ApiModelProperty("项目编号")
    private String projectNo;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("创建人")
    private String createUser;

    @ApiModelProperty("字段名")
    private String fieldName;

    @ApiModelProperty("字段值")
    private String fieldValue;

    @Override
    public int compareTo(DetailHistory o) {
        return createTime.compareTo(o.getCreateTime());
    }
}
