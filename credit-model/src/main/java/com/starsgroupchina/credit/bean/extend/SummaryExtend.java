package com.starsgroupchina.credit.bean.extend;

import com.starsgroupchina.common.utils.BeanUtil;
import com.starsgroupchina.credit.bean.model.ProjectSummary;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel
@Data
public class SummaryExtend extends ProjectSummary{

    @ApiModelProperty("类目1:网查关系（本人或者配偶），资料审查大类，电核人姓名")
    private String item1;

    @ApiModelProperty("类目2:网查网站，资料审查二类，电核电话")
    private String item2;

    @ApiModelProperty("类目3：网查查询条件，电核人关系")
    private String item3;

    @ApiModelProperty("类目4")
    private String item4;

    @ApiModelProperty("风险点")
    private String riskContent;

    @ApiModelProperty("来源")
    private String source;

    public SummaryExtend() {
    }

    public SummaryExtend(ProjectSummary projectSummary) {
        BeanUtil.copyProperty(projectSummary,this);
    }
}
