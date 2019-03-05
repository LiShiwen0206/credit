package com.starsgroupchina.credit.bean.extend;

import com.starsgroupchina.common.utils.BeanUtil;
import com.starsgroupchina.credit.bean.model.Org;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by zhangfeng on 2018-5-21.
 */
@Data
@ApiModel
public class OrgExtend extends Org {

    @ApiModelProperty("所属机构名称")
    private String headOrg;

    @ApiModelProperty("是否是叶子节点：true是，false否")
    private Boolean leaf = true;

    @ApiModelProperty("机构层级:合星总公司-合星南京分公司-合星南京营业一部")
    private String layer;

    public OrgExtend(Org org) {
        BeanUtil.copyProperty(org,this);

    }

    public OrgExtend() {
    }

}
