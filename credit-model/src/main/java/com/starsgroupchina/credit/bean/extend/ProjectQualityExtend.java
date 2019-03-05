package com.starsgroupchina.credit.bean.extend;

import com.starsgroupchina.common.utils.BeanUtil;
import com.starsgroupchina.credit.bean.model.Org;
import com.starsgroupchina.credit.bean.model.Product;
import com.starsgroupchina.credit.bean.model.Project;
import com.starsgroupchina.credit.bean.model.VProject;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by zhangfeng on 2018/6/6
 */
@Data
public class ProjectQualityExtend extends VProject {

    private Org org;

    @ApiModelProperty("是否质检人员0:否，1:是")
    private Integer isQuality;

    private Product product;

    public ProjectQualityExtend(VProject project) {
        BeanUtil.copyProperty(project, this);
    }

    public ProjectQualityExtend() {
    }

    public Project getProject(){
        Project project=new Project();
        BeanUtil.copyProperty(this, project);
        return project;
    }

}
