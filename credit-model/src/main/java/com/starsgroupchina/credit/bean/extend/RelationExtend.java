package com.starsgroupchina.credit.bean.extend;

import com.starsgroupchina.common.utils.BeanUtil;
import com.starsgroupchina.credit.bean.model.Product;
import com.starsgroupchina.credit.bean.model.Project;
import com.starsgroupchina.credit.bean.model.ProjectRelation;
import lombok.Data;

/**
 * Created by zhangfeng on 2018/7/6
 */
@Data
public class RelationExtend extends ProjectRelation {

    private RelationExtend relation;

    private Product product;

    private Project project;

    public RelationExtend(ProjectRelation relation) {
        BeanUtil.copyProperty(relation,this);
    }

    public RelationExtend() {

    }
}
