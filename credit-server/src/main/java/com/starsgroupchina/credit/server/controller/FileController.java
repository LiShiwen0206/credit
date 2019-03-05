package com.starsgroupchina.credit.server.controller;/**
 * @author ：qinhaohao
 * @description ：
 * @copyright ：	Copyright 2018 yowits Corporation. All rights reserved.
 * @create ：2018/5/21 14:32
 */

import com.starsgroupchina.common.file.FileUploadService;
import com.starsgroupchina.common.response.SimpleResponse;
import com.starsgroupchina.credit.bean.enums.FileType;
import com.starsgroupchina.credit.bean.enums.FileUploadResource;
import com.starsgroupchina.credit.bean.model.FileResource;
import com.starsgroupchina.credit.bean.model.FileResourceExample;
import com.starsgroupchina.credit.bean.model.Product;
import com.starsgroupchina.credit.server.service.ProductService;
import com.starsgroupchina.credit.server.service.project.FileService;
import com.starsgroupchina.credit.server.service.system.OrgService;
import com.starsgroupchina.credit.server.utils.PropertyUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
@RestController
@Api(tags = "CREDIT-SWAGGER33", description = "文件管理 - FileController")
@RequestMapping(value = "/file")
public class FileController {


    @Autowired
    private FileUploadService fileUploadService;

    @Value("${spring.application.name:spring.application.name}")
    private String appKey;

    @Value("${image-url}")
    private String fileServer;

    @Autowired
    private FileService fileService;

    @Autowired
    private ProductService productService;


    private static final SimpleDateFormat FORMAT = new SimpleDateFormat("MMdd-HHmm");


    @ApiOperation("上传文件")
    @RequestMapping(value = "", method = RequestMethod.POST)
    public SimpleResponse<FileResource> create(HttpServletRequest request) {
        MultipartHttpServletRequest mRequest = (MultipartHttpServletRequest) request;
        MultipartFile file = mRequest.getFile("file");
        FileResource fileResource = new FileResource();
        //利用反射获取请求中的值，并设置值
        PropertyUtil.set(fileResource, mRequest);
        Integer productId = fileResource.getProductId();
        Product product = productService.getById(productId);
        Integer orgId = product.getOrgId();
        fileResource.setOrgId(orgId);
        String format = FORMAT.format(new Date());
        String realFileName = file.getOriginalFilename();
        String name = realFileName.substring(0, realFileName.lastIndexOf(".")) + "^" + format + realFileName.substring(realFileName.lastIndexOf("."), realFileName.length());
        fileResource.setFileName(name);
        String source = request.getParameter("source");
        fileResource.setOrgFileId(FileType.AUDIT_FILE.getKey());
        setOrgFileId(source, fileResource);
        String fileKey = fileUploadService.upload(file, appKey);
        if (StringUtils.isEmpty(fileKey)) {
            log.error(realFileName + "上传失败");
            return SimpleResponse.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), realFileName + "上传失败");
        }
        fileResource.setFileKey(fileKey);
        fileService.create(fileResource);
        return SimpleResponse.success(fileResource);
    }

    private void setOrgFileId(String source, FileResource fileResource) {
        if (StringUtils.isEmpty(source)) {
            return;
        }
        Short type = Short.valueOf(source);
        if (type.equals(FileUploadResource.RISK_DECLARE.key())) {
            fileResource.setOrgFileId(FileType.DECLARE_FILE.getKey());
        }
        if (type.equals(FileUploadResource.RISK_INVESTIGATION.key())) {
            fileResource.setOrgFileId(FileType.INVESTIGATION_FILEA.getKey());
        }
        if (type.equals(FileUploadResource.QUALITY_AUDIT.key())) {
            fileResource.setOrgFileId(FileType.QUALITY_AUDIT_FILE.getKey());
        }
        if (type.equals(FileUploadResource.QUALITY_RECHECK.key())) {
            fileResource.setOrgFileId(FileType.QUALITY_RECHECK_FILE.getKey());
        }
    }

    @ApiOperation("根据resourceid删除文件")
    @RequestMapping(value = "/{resourceId}", method = RequestMethod.DELETE)
    public SimpleResponse delete(@ApiParam("resourceid") @PathVariable("resourceId") Integer resourceId) {
        FileResource fileResource = fileService.getById(resourceId);
        fileService.deleteById(resourceId);
//        fileServerWrapper.delete(fileResource.getFileKey());
        return SimpleResponse.success();
    }

    /**
     * 进件系统同步调用删除文件
     *
     * @param fileKey
     * @return
     */
    @ApiOperation("根据fileKey删除文件")
    @RequestMapping(value = "/{fileKey}/entry", method = RequestMethod.DELETE)
    public SimpleResponse deleteByFileKey(@ApiParam("fileKey") @PathVariable("fileKey") String fileKey) {
        FileResourceExample fileResourceExample = new FileResourceExample();
        fileResourceExample.createCriteria().andFileKeyEqualTo(fileKey);
        fileService.deleteByExample(fileResourceExample);
        return SimpleResponse.success();
    }

    @ApiOperation("同步进件系统数据")
    @RequestMapping(value = "/sync", method = RequestMethod.POST)
    public SimpleResponse<FileResource> synFileResource(@RequestBody FileResource fileResource) {
        fileService.create(fileResource);
        return SimpleResponse.success(fileResource);
    }

    @ApiOperation("上传附件")
    @PostMapping(value = "/logo")
    public SimpleResponse<String> logoCreate(@RequestParam("file") MultipartFile file) {

        String filePreviewUrl = null;
        try {
            String fileKey = fileUploadService.upload(file, appKey);
            if (StringUtils.isNotEmpty(fileKey)) {
                filePreviewUrl = new StringBuilder(fileServer).append("/").append(fileKey).toString();
            }
            return SimpleResponse.success(filePreviewUrl);
        } catch (Exception e) {
            log.error("Faild to uplaod file error:", e);
            return SimpleResponse.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
        }
    }
}
