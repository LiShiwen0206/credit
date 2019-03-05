package com.starsgroupchina.credit.bean.extend;

import com.starsgroupchina.common.utils.BeanUtil;
import com.starsgroupchina.credit.bean.model.Member;
import com.starsgroupchina.credit.bean.model.Org;
import com.starsgroupchina.credit.bean.model.Role;
import com.starsgroupchina.credit.bean.model.Workgroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by zhangfeng on 2018-5-21.
 */
@Data
@ApiModel
public class MemberExtend extends Member {

    @ApiModelProperty("机构名称")
    private Org org;

    @ApiModelProperty("角色")
    private Role role;

    @ApiModelProperty("工作组")
    private Workgroup workgroup;

    public MemberExtend(Member member) {
        BeanUtil.copyProperty(member,this);
    }

    public MemberExtend() {
    }
}
