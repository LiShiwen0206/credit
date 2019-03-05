package com.starsgroupchina.credit.bean.extend;

import com.starsgroupchina.common.utils.BeanUtil;
import com.starsgroupchina.credit.bean.model.NumberRule;
import com.starsgroupchina.credit.bean.model.Org;
import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel
public class NumberRuleExtend extends NumberRule {

    private Org org;

    public NumberRuleExtend(){

    }

    public NumberRuleExtend(NumberRule numberRule){
        BeanUtil.copyProperty(numberRule,this);
    }
}
