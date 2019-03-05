package com.starsgroupchina.credit.bean.extend;

import com.starsgroupchina.common.utils.BeanUtil;
import com.starsgroupchina.credit.bean.model.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * Created by zhangfeng on 2018-5-28.
 */
@Data
@ApiModel
public class ProductExtend extends Product {

    @ApiModelProperty("审核小组-角色")
    private Role roleAudit;

    @ApiModelProperty("复核小组-角色")
    private Role roleAuditRecheck;

    private Org org;

    private Category category;

    private List<ProductConditionConfigExtend> conditionConfigList;

    public ProductExtend(Product product) {
        BeanUtil.copyProperty(product,this);
    }

    public ProductExtend() {

    }


}
