package com.starsgroupchina.credit.bean.extend;

import com.starsgroupchina.common.utils.BeanUtil;
import com.starsgroupchina.credit.bean.model.Member;
import com.starsgroupchina.credit.bean.model.Org;
import com.starsgroupchina.credit.bean.model.Workgroup;
import com.starsgroupchina.credit.bean.model.WorkgroupPermission;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @describe :
 * @class_name : WorkgroupPermissionExtend
 * @author : lishiwen
 * @date : 2018/12/7
 */
@Data
@ApiModel
public class WorkgroupPermissionExtend extends WorkgroupPermission {

    @ApiModelProperty("员工姓名")
    private Member member;

    @ApiModelProperty("工作组名称")
    private List<Workgroup> workgroups;

    @ApiModelProperty("机构名称")
    private Org org;

    public WorkgroupPermissionExtend(WorkgroupPermission workgroupPermission) {
        BeanUtil.copyProperty(workgroupPermission,this);
    }

    public WorkgroupPermissionExtend() {
    }
}
