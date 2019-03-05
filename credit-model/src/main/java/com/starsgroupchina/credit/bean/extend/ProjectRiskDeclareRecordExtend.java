package com.starsgroupchina.credit.bean.extend;

import com.starsgroupchina.common.utils.BeanUtil;
import com.starsgroupchina.credit.bean.model.Member;
import com.starsgroupchina.credit.bean.model.ProjectRiskDeclareRecord;
import lombok.Data;

/**
 * @Author: QinHaoHao
 * @Description:
 * @Date: Created in 17:12 2018/9/11
 * @Modifed By:
 */
@Data
public class ProjectRiskDeclareRecordExtend extends ProjectRiskDeclareRecord{
    private Member member;

    public ProjectRiskDeclareRecordExtend(ProjectRiskDeclareRecord record) {
        BeanUtil.copyProperty(record, this);
    }

    public ProjectRiskDeclareRecordExtend() {
    }
}
