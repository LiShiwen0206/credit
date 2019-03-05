package com.starsgroupchina.credit.bean;

import com.starsgroupchina.credit.bean.model.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zhangfeng on 2018-6-4.
 */
@Data
public class AuthMember implements Serializable {

    private static final long serialVersionUID = 1L;

    private String token;

    private Member member;

    private Org org;

    private Role role;

    private List<RoleData> roleDatas;

    private List<Integer> workGroupIds;

    @ApiModelProperty("是否审核组长：0否，1是")
    private Integer isAuditLeader = 0;

    @ApiModelProperty("是否复核组长：0否，1是")
    private Integer isRecheckLeader =0;

    public AuthMember() {
    }

    public AuthMember(String token, Member member, Org org, Role role, List<RoleData> roleDatas,List<Integer> workGroupIds) {

        this.token = token;
        this.member = member;
        this.org = org;
        this.role = role;
        this.roleDatas = roleDatas;
        this.workGroupIds=workGroupIds;
    }


}
