package com.starsgroupchina.credit.bean.extend;

import com.starsgroupchina.common.utils.BeanUtil;
import com.starsgroupchina.credit.bean.model.RiskModel;
import com.starsgroupchina.credit.bean.model.RiskModelField;
import com.starsgroupchina.credit.bean.model.RiskModelGroup;
import lombok.Data;

import java.util.List;

/**
 * Created by zhangfeng on 2018/7/17
 */
@Data
public class ModelExtend extends RiskModel {

    private List<ModelGroupExtend> groups;

    public ModelExtend(RiskModel model) {
        BeanUtil.copyProperty(model, this);
    }

    public ModelExtend() {
    }

    @Data
    public static class ModelGroupExtend extends RiskModelGroup {

        private List<RiskModelField> fields;

        public ModelGroupExtend(RiskModelGroup modelGroup) {
            BeanUtil.copyProperty(modelGroup, this);
        }

        public ModelGroupExtend() {
        }
    }

}
