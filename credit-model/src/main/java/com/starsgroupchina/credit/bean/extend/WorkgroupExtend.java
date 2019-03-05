package com.starsgroupchina.credit.bean.extend;

import com.starsgroupchina.common.utils.BeanUtil;
import com.starsgroupchina.credit.bean.model.Org;
import com.starsgroupchina.credit.bean.model.Workgroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @describe :
 * @class_name : WorkgroupExtend
 * @author : lishiwen
 * @date : 2018/12/7
 */
@Data
@ApiModel
public class WorkgroupExtend extends Workgroup {

    @ApiModelProperty("机构名称")
    private Org org;


    public WorkgroupExtend(Workgroup workgroup) {
        BeanUtil.copyProperty(workgroup,this);
    }

    public WorkgroupExtend() {
    }
}
