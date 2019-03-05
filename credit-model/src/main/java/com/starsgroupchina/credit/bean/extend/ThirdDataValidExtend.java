package com.starsgroupchina.credit.bean.extend;

import com.starsgroupchina.common.utils.BeanUtil;
import com.starsgroupchina.credit.bean.model.PolicyField;
import com.starsgroupchina.credit.bean.model.ThirdDataValid;
import com.starsgroupchina.credit.bean.model.ThirdDataValidField;
import com.starsgroupchina.credit.bean.model.ThirdDataValidGroup;
import lombok.Data;

import java.util.List;

/**
 * Created by zhangfeng on 2018/7/13
 */
@Data
public class ThirdDataValidExtend extends ThirdDataValid {

    private List<ThirdDataValidGroupExtend> groups;

    public ThirdDataValidExtend(ThirdDataValid thirdDataValid) {
        BeanUtil.copyProperty(thirdDataValid, this);
    }

    public ThirdDataValidExtend() {
    }

    @Data
    public static class ThirdDataValidGroupExtend extends ThirdDataValidGroup {

        private List<ThirdDataValidField> fields;

        public ThirdDataValidGroupExtend(ThirdDataValidGroup thirdDataValidGroup) {
            BeanUtil.copyProperty(thirdDataValidGroup, this);
        }

        public ThirdDataValidGroupExtend() {
        }
    }


}
