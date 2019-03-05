package com.starsgroupchina.credit.bean.extend;

import com.starsgroupchina.common.utils.BeanUtil;
import com.starsgroupchina.credit.bean.model.Policy;
import com.starsgroupchina.credit.bean.model.PolicyField;
import com.starsgroupchina.credit.bean.model.PolicyGroup;
import lombok.Data;

import java.util.List;

/**
 * Created by zhangfeng on 2018/7/13
 */
@Data
public class PolicyExtend extends Policy {

    private List<PolicyGroupExtend> groups;

    public PolicyExtend(Policy policy) {
        BeanUtil.copyProperty(policy, this);
    }

    public PolicyExtend() {
    }

    @Data
    public static class PolicyGroupExtend extends PolicyGroup {

        private List<PolicyField> fields;

        public PolicyGroupExtend(PolicyGroup policyGroup) {
            BeanUtil.copyProperty(policyGroup, this);
        }

        public PolicyGroupExtend() {
        }
    }


}
