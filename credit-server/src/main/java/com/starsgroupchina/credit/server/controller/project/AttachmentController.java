package com.starsgroupchina.credit.server.controller.project;

import com.google.common.collect.Lists;
import com.starsgroupchina.common.response.SimpleResponse;
import com.starsgroupchina.credit.bean.FileGroup;
import com.starsgroupchina.credit.bean.enums.FileType;
import com.starsgroupchina.credit.bean.enums.FileUploadResource;
import com.starsgroupchina.credit.bean.extend.FileResourceExtend;
import com.starsgroupchina.credit.bean.extend.OrgFileExtend;
import com.starsgroupchina.credit.bean.extend.ProductFileExtend;
import com.starsgroupchina.credit.bean.model.FileResource;
import com.starsgroupchina.credit.bean.model.FileResourceExample;
import com.starsgroupchina.credit.server.service.ProductService;
import com.starsgroupchina.credit.server.service.project.FileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

/**
 * Created by zhangfeng on 2018-6-13.
 */
@Slf4j
@RestController
@Api(tags = "CREDIT-SWAGGER11", description = "【项目】 - 附件 - AttachmentController")
@RequestMapping(value = "/project/files", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class AttachmentController {


    @Value("${image-url}")
    private String imageUrl;

    @Autowired
    private FileService fileService;
    @Autowired
    private ProductService.ProductFileService productFileService;


    @ApiOperation("根据项目编号获取附件（树结构）")
    @GetMapping("/{projectNo}/tree")
    public SimpleResponse<List<FileGroup>> getFiles(
            @ApiParam("项目编号") @PathVariable("projectNo") String projectNo,
            @ApiParam("来源：0、审核附件、1、风险客户申报,2、风险客户调查，3、质检抽查审核资料,4、质检抽查复核资料") @RequestParam(value = "source", required = false) Short source,
            @ApiParam("申报编号") @RequestParam(value = "declareNo", required = false) String declareNo,
            @ApiParam("产品ID") @RequestParam(value = "productId",required = false) Integer productId) {
        FileResourceExample example = new FileResourceExample();
        List<FileResourceExtend> fileResourceExtends = Lists.newArrayList();
        List<FileGroup> fileGroups = Lists.newArrayList();
        if (source == null||FileUploadResource.AUDIT_FILE.key().equals(source)) {
            example.createCriteria().andProjectNoEqualTo(projectNo);
            List<FileResource> files = fileService.query(example).collect(toList());
            files.forEach(fileResource -> {
                FileResourceExtend fileResourceExtend = new FileResourceExtend(fileResource);
                fileResourceExtend.setFileUrl(imageUrl + fileResource.getFileKey());
                fileResourceExtends.add(fileResourceExtend);
            });
            List<ProductFileExtend> productFileExtends = productFileService.queryExtend(productId);
            fileGroups = productFileService.transformationFilesTree(productFileExtends);
            fileGroups.add(getAuditFile("审核附件", new FileType[]{FileType.AUDIT_FILE}));
            if (CollectionUtils.isEmpty(fileResourceExtends)) {
                return SimpleResponse.success(fileGroups);
            }
        }
        if (FileUploadResource.RISK_DECLARE.key().equals(source)) {
            if (StringUtils.isNotEmpty(declareNo)) {
                example.createCriteria().andProjectNoEqualTo(declareNo).andOrgFileIdEqualTo(FileType.DECLARE_FILE.getKey());
                fileService.query(example).forEach(fileResource -> {
                    FileResourceExtend fileResourceExtend = new FileResourceExtend(fileResource);
                    fileResourceExtend.setFileUrl(imageUrl + fileResource.getFileKey());
                    fileResourceExtends.add(fileResourceExtend);
                });
            }
            fileGroups.addAll(getDeclareAndQualityFile(FileUploadResource.RISK_DECLARE));
            if (CollectionUtils.isEmpty(fileResourceExtends)) {
                return SimpleResponse.success(fileGroups);
            }
        }

        if (FileUploadResource.RISK_INVESTIGATION.key().equals(source)) {
            if (StringUtils.isNotEmpty(declareNo)) {
                example.clear();
                List<Integer> params = Arrays.asList(new Integer[]{FileType.INVESTIGATION_FILEA.getKey(), FileType.DECLARE_FILE.getKey()});
                example.createCriteria().andProjectNoEqualTo(declareNo).andOrgFileIdIn(params);
                fileService.query(example).forEach(fileResource -> {
                    FileResourceExtend fileResourceExtend = new FileResourceExtend(fileResource);
                    fileResourceExtend.setFileUrl(imageUrl + fileResource.getFileKey());
                    fileResourceExtends.add(fileResourceExtend);
                });
            }
            fileGroups.addAll(getDeclareAndQualityFile(FileUploadResource.RISK_INVESTIGATION));
            if (CollectionUtils.isEmpty(fileResourceExtends)) {
                return SimpleResponse.success(fileGroups);
            }
        }

        if (FileUploadResource.QUALITY_AUDIT.key().equals(source)) {
            example.clear();
            example.createCriteria().andProjectNoEqualTo(projectNo).andOrgFileIdEqualTo(FileType.QUALITY_AUDIT_FILE.getKey());
            fileService.query(example).forEach(fileResource -> {
                FileResourceExtend fileResourceExtend = new FileResourceExtend(fileResource);
                fileResourceExtend.setFileUrl(imageUrl + fileResource.getFileKey());
                fileResourceExtends.add(fileResourceExtend);
            });
            fileGroups.addAll(getDeclareAndQualityFile(FileUploadResource.QUALITY_AUDIT));
            if (CollectionUtils.isEmpty(fileResourceExtends)) {
                return SimpleResponse.success(fileGroups);
            }
        }

        if (FileUploadResource.QUALITY_RECHECK.key().equals(source)) {
            example.clear();
            example.createCriteria().andProjectNoEqualTo(projectNo).andOrgFileIdEqualTo(FileType.QUALITY_RECHECK_FILE.getKey());
            fileService.query(example).forEach(fileResource -> {
                FileResourceExtend fileResourceExtend = new FileResourceExtend(fileResource);
                fileResourceExtend.setFileUrl(imageUrl + fileResource.getFileKey());
                fileResourceExtends.add(fileResourceExtend);
            });
            fileGroups.addAll(getDeclareAndQualityFile(FileUploadResource.QUALITY_RECHECK));
            if (CollectionUtils.isEmpty(fileResourceExtends)) {
                return SimpleResponse.success(fileGroups);
            }
        }
        Map<Integer, List<FileResourceExtend>> filesMap = fileResourceExtends.stream().collect(groupingBy(fileResource -> fileResource.getOrgFileId()));
        //遍历最外层
        fileGroups.stream().forEach(fileGroup ->
                //遍历内层
                fileGroup.getTypes().stream().forEach(fileType -> fileType.setFlies(filesMap.get(fileType.getOrgFileId())))
        );
        return SimpleResponse.success(fileGroups);
    }

    /**
     * 根据项目编号获取附件（树结构）,进件系统调用
     */
    @ApiOperation("根据项目编号获取附件（树结构）")
    @RequestMapping(value = "/{projectNo}/tree/entry", method = RequestMethod.GET)
    public SimpleResponse<List<FileGroup>> getFilesEntry(
            @ApiParam("项目编号") @PathVariable("projectNo") String projectNo,
            @ApiParam("产品ID") @RequestParam("productId") Integer productId) {
        FileResourceExample fileResourceExample = new FileResourceExample();
        fileResourceExample.createCriteria().andProjectNoEqualTo(projectNo);
        List<FileResource> files = fileService.query(fileResourceExample).collect(toList());
        List<FileResourceExtend> fileResourceExtends = Lists.newArrayList();
        files.forEach(fileResource -> {
            FileResourceExtend fileResourceExtend = new FileResourceExtend(fileResource);
            fileResourceExtend.setFileUrl(imageUrl + fileResource.getFileKey());
            fileResourceExtends.add(fileResourceExtend);
        });
        List<ProductFileExtend> productFileExtendList = productFileService.queryExtend(productId);
        List<FileGroup> fileGroups = productFileService.transformationFilesTree(productFileExtendList);

        if (CollectionUtils.isEmpty(fileResourceExtends)) {
            return SimpleResponse.success(fileGroups);
        }
        Map<Integer, List<FileResourceExtend>> filesMap = fileResourceExtends.stream().collect(groupingBy(fileResourceExtend -> fileResourceExtend.getOrgFileId()));
        //遍历最外层
        fileGroups.stream().forEach(fileGroup ->
                //遍历内层
                fileGroup.getTypes().stream().forEach(fileType -> fileType.setFlies(filesMap.get(fileType.getOrgFileId())))
        );
        return SimpleResponse.success(fileGroups);
    }

    /**
     * 获取审核附件
     *
     * @return
     */
    private FileGroup getAuditFile(String category, FileType[] types) {
        FileGroup tempCroup = new FileGroup();
        tempCroup.setCategory(category);
        List<FileGroup.FileType> tempList = Lists.newArrayList();
        for (int i = 0; i < types.length; i++) {
            FileType fileType = types[i];
            FileGroup.FileType tempType = new FileGroup.FileType();
            tempType.setOrgFileId(fileType.getKey());
            tempType.setName(fileType.getValue());
            tempList.add(tempType);
        }
        tempCroup.setTypes(tempList);
        return tempCroup;
    }

    private List<FileGroup> getDeclareAndQualityFile(FileUploadResource fileUploadResource) {
        List<FileGroup> result = Lists.newArrayList();
        switch (fileUploadResource) {
            case RISK_INVESTIGATION:
                result.add(getAuditFile("反欺诈资料上传", new FileType[]{FileType.DECLARE_FILE, FileType.INVESTIGATION_FILEA}));
                break;
            case RISK_DECLARE:
                result.add(getAuditFile("反欺诈资料上传", new FileType[]{FileType.DECLARE_FILE}));
                break;
            case QUALITY_AUDIT:
                result.add(getAuditFile("质检抽查资料上传", new FileType[]{FileType.QUALITY_AUDIT_FILE}));
                break;
            case QUALITY_RECHECK:
                result.add(getAuditFile("质检抽查资料上传", new FileType[]{FileType.QUALITY_RECHECK_FILE}));
                break;
        }
        return result;
    }

    private void addFileType(List<FileGroup> result, int index, String name, FileType type) {
        FileGroup fileGroup = result.get(index);
        FileGroup.FileType fileType = new FileGroup.FileType();
        fileType.setOrgFileId(type.getKey());
        fileType.setName(name);
        fileGroup.getTypes().add(fileType);
    }

    @ApiOperation("根据项目编号获取文件列表")
    @RequestMapping(value = "/{projectNo}", method = RequestMethod.GET)
    public SimpleResponse<List<OrgFileExtend>> getFiles(
            @ApiParam("项目编号") @PathVariable("projectNo") String projectNo) {
        List<OrgFileExtend> result = fileService.getFiles(projectNo);
        return SimpleResponse.success(result);
    }

    @ApiOperation("批量更新项目文件列表")
    @RequestMapping(value = "", method = RequestMethod.PUT)
    @Transactional
    public SimpleResponse<List<FileResource>> updateFiles(
            @RequestBody List<FileResource> fileResourceList) {
        List<FileResource> files = fileService.update(fileResourceList);
        return SimpleResponse.success(files);
    }
}
