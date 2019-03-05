package com.starsgroupchina.credit.bean;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by zhangfeng on 2018-6-5.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginMember {

    private String token;

    @ApiModelProperty("用户名")
    private String name;

    @ApiModelProperty("用户id")
    private Integer memberId;

    @ApiModelProperty("登录名")
    private String loginName;

    @ApiModelProperty("所属机构名称")
    private String orgName;

    @ApiModelProperty("orgId")
    private Integer orgId;

    @ApiModelProperty("headOrgId")
    private Integer headOrgId;

    @ApiModelProperty("角色名称")
    private String roleName;

}
