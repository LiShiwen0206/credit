package com.starsgroupchina.credit.server.controller.project;

import com.starsgroupchina.common.response.SimpleResponse;
import com.starsgroupchina.credit.bean.extend.FileInspectExtend;
import com.starsgroupchina.credit.bean.model.ProjectFileInspect;
import com.starsgroupchina.credit.server.service.project.FileInspectService;
import com.starsgroupchina.credit.server.service.project.FileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: QinHaoHao
 * @Description:
 * @Date: Created in 13:53 2018/7/2 资料审查
 * @Modifed By:
 */
@Slf4j
@RestController
@Api(tags = "CREDIT-SWAGGER19", description = "【项目】 - 资料审查接口 - FileInspectController")
@RequestMapping(value = "/project/inspect", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class FileInspectController {

    @Autowired
    private FileInspectService fileInspectService;

    @Autowired
    private FileService fileService;


    /**
     * 根据projectId获取网查信息(包括网查信息以及附件信息）
     */
    @ApiOperation("根据projectNo获取资料审查列表")
    @RequestMapping(value = "/{projectNo}", method = RequestMethod.GET)
    public SimpleResponse<List<FileInspectExtend>> query(@ApiParam("项目编号") @PathVariable(value = "projectNo") String projectNo) {

        List<FileInspectExtend> result = fileService.getInspectFiles(projectNo);
        return SimpleResponse.success(result);
    }


    /**
     * 批量资料审查接口
     */
    @ApiOperation("批量创建资料审查接口")
    @RequestMapping(value = "", method = RequestMethod.POST)
    @Transactional
    public SimpleResponse<List<ProjectFileInspect>> create(@RequestBody List<ProjectFileInspect> authAttachmentList) {
        if (CollectionUtils.isEmpty(authAttachmentList)) {
            return SimpleResponse.success();
        }
        ProjectFileInspect authAttachment = authAttachmentList.get(0);
        List<ProjectFileInspect> result;
        if (authAttachment.getId() != null) {
           result = fileInspectService.update(authAttachmentList);
        } else {
            result = fileInspectService.create(authAttachmentList);
        }
        return SimpleResponse.success(result);
    }


}
