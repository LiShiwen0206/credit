package com.starsgroupchina.credit.bean.extend;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: QinHaoHao
 * @Description:
 * @Date: Created in 15:59 2018/9/13
 * @Modifed By:
 */
@ApiModel("统计信息")
@Data
public class PortalSummaryExtend {

    @ApiModelProperty("机构数")
    private Long orgNum;

    @ApiModelProperty("今日新增产品数")
    private Long todayProductNum;

    @ApiModelProperty("当前有效产品数量")
    private Long mothProductNum;

    @ApiModelProperty("黑名单数量")
    private Long blackNum;

    public PortalSummaryExtend(Long orgNum, Long todayProductNum, Long mothProductNum, Long blackNum) {
        this.orgNum = orgNum;
        this.todayProductNum = todayProductNum;
        this.mothProductNum = mothProductNum;
        this.blackNum = blackNum;
    }

    public PortalSummaryExtend() {
    }
}
