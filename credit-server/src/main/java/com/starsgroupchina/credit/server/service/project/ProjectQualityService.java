package com.starsgroupchina.credit.server.service.project;

import com.starsgroupchina.common.base.AbstractService;
import com.starsgroupchina.credit.bean.mapper.ProjectQualityResultMapper;
import com.starsgroupchina.credit.bean.model.ProjectQualityResult;
import com.starsgroupchina.credit.bean.model.ProjectQualityResultExample;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by gexiaoshan on 2018/8/22.
 */
@Slf4j
@Service
public class ProjectQualityService extends AbstractService<ProjectQualityResult,ProjectQualityResultExample> {

    @Autowired
    private ProjectQualityResultMapper projectQualityResultMapper;

    @Override
    public ProjectQualityResult update(ProjectQualityResult project) {
        ProjectQualityResultExample example = new ProjectQualityResultExample();
        example.createCriteria().andProjectNoEqualTo(project.getProjectNo());
        projectQualityResultMapper.updateByExampleSelective(project, example);
        return project;
    }

}
