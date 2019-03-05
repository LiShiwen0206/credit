package com.starsgroupchina.credit.server.controller.project;

import com.google.common.collect.Lists;
import com.starsgroupchina.common.response.SimpleResponse;
import com.starsgroupchina.credit.bean.extend.RelationBlacklistExtend;
import com.starsgroupchina.credit.bean.extend.RelationExtend;
import com.starsgroupchina.credit.bean.model.Project;
import com.starsgroupchina.credit.bean.model.ProjectRelation;
import com.starsgroupchina.credit.bean.model.ProjectRelationExample;
import com.starsgroupchina.credit.server.service.ProductService;
import com.starsgroupchina.credit.server.service.project.ProjectService;
import com.starsgroupchina.credit.server.service.project.RelationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by zhangfeng on 2018/7/4
 */
@Slf4j
@RestController
@Api(tags = "CREDIT-SWAGGER18", description = "【项目】 - 关联报告 - RelationController")
@RequestMapping(value = "/project/relation", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class RelationController {

    @Autowired
    private RelationService relationService;
    @Autowired
    private ProductService productService;
    @Autowired
    private ProjectService projectService;
    @Autowired
    private RelationService.RelationBlacklistService relationBlacklistService;

    @ApiOperation("获取关联关系")
    @GetMapping("/{projectNo}")
    public SimpleResponse<List<RelationExtend>> getRelation(@PathVariable("projectNo") String projectNo) {
        List<RelationExtend> result = Lists.newArrayList();
        ProjectRelationExample example = new ProjectRelationExample();
        example.createCriteria().andProjectNoEqualTo(projectNo);
        example.setOrderByClause("create_time  desc");
        relationService.query(example).forEach(item -> {
            RelationExtend relationExtend = new RelationExtend(item);
            RelationExtend relation =new RelationExtend();

            Project project = projectService.getProjectByProjectNo(projectNo);
            relationExtend.setProject(project);
            if (project != null)
                relationExtend.setProduct(productService.getById(project.getProductId()));

            Project relationProject = projectService.getProjectByProjectNo(item.getRelationProjectNo());
            relation.setProject(relationProject);
            if (relationProject != null)
                relation.setProduct(productService.getById(relationProject.getProductId()));
            relationExtend.setRelation(relation);

            result.add(relationExtend);
        });
        return SimpleResponse.success(result);
    }

    @ApiOperation("修改关联关系")
    @PutMapping()
    @Transactional
    public SimpleResponse<List<ProjectRelation>> updateRelation(@RequestBody List<ProjectRelation> contacts) {
        relationService.update(contacts);
        return SimpleResponse.success(contacts);
    }

    @ApiOperation("获取关联报告黑名单信息")
    @RequestMapping(value = "/{projectNo}/blacklist",method = RequestMethod.GET)
    public SimpleResponse<List<RelationBlacklistExtend>> getRelationBlacklist(@PathVariable("projectNo") String projectNo) {
        List<RelationBlacklistExtend> result = relationBlacklistService.getRelationBlacks(projectNo);
        return SimpleResponse.success(result);
    }
}
