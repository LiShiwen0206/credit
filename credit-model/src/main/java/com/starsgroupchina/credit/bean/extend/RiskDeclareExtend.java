package com.starsgroupchina.credit.bean.extend;

import com.starsgroupchina.common.utils.BeanUtil;
import com.starsgroupchina.credit.bean.UserInfo;
import com.starsgroupchina.credit.bean.model.Org;
import com.starsgroupchina.credit.bean.model.Product;
import com.starsgroupchina.credit.bean.model.Project;
import com.starsgroupchina.credit.bean.model.VProjectRiskDeclare;
import lombok.Data;

/**
 * @Author: QinHaoHao
 * @Description:
 * @Date: Created in 17:31 2018/8/16
 * @Modifed By:
 */
@Data
public class RiskDeclareExtend extends VProjectRiskDeclare {

    private Project project;

    private Org org;

    private UserInfo userInfo;

    private Product product;

    public RiskDeclareExtend(VProjectRiskDeclare riskDeclare) {
        BeanUtil.copyProperty(riskDeclare, this);
    }

    public RiskDeclareExtend() {
    }
}
