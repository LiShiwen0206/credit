package com.starsgroupchina.credit.bean.extend;

import com.starsgroupchina.common.utils.BeanUtil;
import com.starsgroupchina.credit.bean.model.ThirdDataValidField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: QinHaoHao
 * @Description:
 * @Date: Created in 17:26 2018/10/29
 * @Modifed By:
 */
@Data
@ApiModel
public class ThirdDataValidFieldExtend extends ThirdDataValidField {

    @ApiModelProperty("表单值")
    private String formData;

    public ThirdDataValidFieldExtend() {
    }

    public ThirdDataValidFieldExtend(ThirdDataValidField thirdDataValidGroup) {
        BeanUtil.copyProperty(thirdDataValidGroup, this);
    }
}
