package com.starsgroupchina.credit.bean.extend;

import com.starsgroupchina.common.utils.BeanUtil;
import com.starsgroupchina.credit.bean.model.Member;
import com.starsgroupchina.credit.bean.model.ProjectRecord;
import lombok.Data;

/**
 * Created by zhangfeng on 2018/7/3
 */
@Data
public class RecordExtend extends ProjectRecord {

    private Member member;

    public RecordExtend(ProjectRecord record) {
        BeanUtil.copyProperty(record, this);
    }

    public RecordExtend() {
    }

}
