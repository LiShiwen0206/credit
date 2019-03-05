package com.starsgroupchina.credit.bean.mapper;

import com.starsgroupchina.common.base.BaseMapper;
import com.starsgroupchina.credit.bean.model.Project;
import com.starsgroupchina.credit.bean.model.ProjectExample;

public interface ProjectMapper extends BaseMapper<Project,ProjectExample> {

    int updateByPrimaryKey(Project record);
}