package com.starsgroupchina.credit.bean.extend;

import com.starsgroupchina.common.utils.BeanUtil;
import com.starsgroupchina.credit.bean.model.Org;
import com.starsgroupchina.credit.bean.model.OrgCondition;
import com.starsgroupchina.credit.bean.model.OrgConditionField;
import com.starsgroupchina.credit.bean.model.OrgConditionGroup;
import lombok.Data;

import java.util.List;

/**
 * Created by zhangfeng on 2018/7/13
 */
@Data
public class OrgConditionExtend extends OrgCondition {

    private Org org;

    private List<OrgCinditionGroupExtend> groups;

    public OrgConditionExtend(OrgCondition orgCondition) {
        BeanUtil.copyProperty(orgCondition, this);
    }

    public OrgConditionExtend() {
    }

    @Data
    public static class OrgCinditionGroupExtend extends OrgConditionGroup {

        private List<OrgConditionField> fields;

        public OrgCinditionGroupExtend(OrgConditionGroup orgConditionGroup) {
            BeanUtil.copyProperty(orgConditionGroup, this);
        }

        public OrgCinditionGroupExtend() {
        }
    }


}
