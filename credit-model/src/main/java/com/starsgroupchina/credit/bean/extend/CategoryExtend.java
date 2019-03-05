package com.starsgroupchina.credit.bean.extend;

import com.starsgroupchina.common.utils.BeanUtil;
import com.starsgroupchina.credit.bean.model.Category;
import com.starsgroupchina.credit.bean.model.Policy;
import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel
public class CategoryExtend extends Category {
    private Policy policy;

    public CategoryExtend(Category category) {
        BeanUtil.copyProperty(category,this);
    }

    public CategoryExtend() {
    }
}
