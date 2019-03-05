package com.starsgroupchina.credit.bean.extend;

import com.starsgroupchina.common.utils.BeanUtil;
import com.starsgroupchina.credit.bean.model.OrgCondition;
import com.starsgroupchina.credit.bean.model.ProductConditionConfig;
import lombok.Data;

import java.util.List;

/**
 * @Author: QinHaoHao
 * @Description:
 * @Date: Created in 16:28 2018/8/23
 * @Modifed By:
 */
@Data
public class ProductConditionConfigExtend extends ProductConditionConfig{

    private List<OrgCondition> conditionList;

    public ProductConditionConfigExtend(ProductConditionConfig product) {
        BeanUtil.copyProperty(product,this);
    }

    public ProductConditionConfigExtend() {

    }

    public ProductConditionConfig getConditionConfig(){
        ProductConditionConfig conditionConfig=new ProductConditionConfig();
        BeanUtil.copyProperty(this, conditionConfig);
        return conditionConfig;
    }
}
