package com.starsgroupchina.credit.bean.extend;

import com.starsgroupchina.common.utils.BeanUtil;
import com.starsgroupchina.credit.bean.UserInfo;
import com.starsgroupchina.credit.bean.model.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * Created by zhangfeng on 2018/6/6
 */
@Data
public class ProjectExtend extends VProject {

    private Product product;

    private UserInfo userInfo;

    private Org org;

    private Category category;

    private Policy policy;

    private List<ProjectForm> forms;

    private ProjectRiskDeclare riskDeclare;

    @ApiModelProperty("申报次数")
    private long riskDeclareCount;

    public ProjectExtend(VProject project) {
        BeanUtil.copyProperty(project, this);
    }

    public ProjectExtend() {
    }

    public Project getProject() {
        Project project = new Project();
        BeanUtil.copyProperty(this, project);
        return project;
    }
}
