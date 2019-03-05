package com.starsgroupchina.credit.server.controller.project;

import com.starsgroupchina.common.response.SimpleResponse;
import com.starsgroupchina.credit.bean.extend.SummaryExtend;
import com.starsgroupchina.credit.bean.model.ProjectSummary;
import com.starsgroupchina.credit.bean.model.ProjectSummaryExample;
import com.starsgroupchina.credit.server.service.project.SummaryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@Api(tags = "CREDIT-SWAGGER20", description = "【项目】 - 客户综述接口 - SummaryController")
@RequestMapping(value = "/project/summary", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class SummaryController {

    @Autowired
    private SummaryService summaryService;


    /**
     * 根据projectNo获取客户综述
     */
    @ApiOperation("根据projectNo获取客户综述")
    @RequestMapping(value = "/{projectNo}", method = RequestMethod.GET)
    public SimpleResponse<List<SummaryExtend>> query(@ApiParam("项目编号") @PathVariable(value = "projectNo") String projectNo) {
        List<SummaryExtend> result = summaryService.getSummary(projectNo);
        return SimpleResponse.success(result);
    }



    /**
     * 批量创建客户综述接口
     */
    @ApiOperation("批量创建客户综述")
    @RequestMapping(value = "", method = RequestMethod.POST)
    @Transactional
    public SimpleResponse<List<ProjectSummary>> create(@RequestBody List<ProjectSummary> projectSummaryList) {
        ProjectSummary projectSummary = projectSummaryList.get(0);
        if (projectSummary != null) {
            ProjectSummaryExample projectSummaryExample = new ProjectSummaryExample();
            projectSummaryExample.createCriteria().andProjectNoEqualTo(projectSummary.getProjectNo());
            summaryService.deleteByExample(projectSummaryExample);
        }
        List<ProjectSummary> result = summaryService.create(projectSummaryList);
        return SimpleResponse.success(result);
    }


}
