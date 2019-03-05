package com.starsgroupchina.credit.bean.extend;

import com.starsgroupchina.common.utils.BeanUtil;
import com.starsgroupchina.credit.bean.model.Org;
import com.starsgroupchina.credit.bean.model.Role;
import lombok.Data;

/**
 * @Author: QinHaoHao
 * @Description:
 * @Date: Created in 15:22 2018/7/10
 * @Modifed By:
 */
@Data
public class RoleExtend extends Role {

    private Org org;

    public RoleExtend(Role role) {
        BeanUtil.copyProperty(role, this);
    }

    public RoleExtend() {
    }
}
