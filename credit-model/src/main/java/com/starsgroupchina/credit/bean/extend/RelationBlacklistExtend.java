package com.starsgroupchina.credit.bean.extend;

import com.starsgroupchina.common.utils.BeanUtil;
import com.starsgroupchina.credit.bean.model.Blacklist;
import com.starsgroupchina.credit.bean.model.ProjectRelationBlacklist;
import lombok.Data;

/**
 * @Author: QinHaoHao
 * @Description:
 * @Date: Created in 14:42 2018/7/10
 * @Modifed By:
 */
@Data
public class RelationBlacklistExtend extends ProjectRelationBlacklist{

    private Blacklist blacklist;

    public RelationBlacklistExtend(ProjectRelationBlacklist relationBlacklist) {
        BeanUtil.copyProperty(relationBlacklist,this);
    }

    public RelationBlacklistExtend() {

    }
}
