package com.starsgroupchina.credit.server.controller.project;

import com.google.common.collect.Lists;
import com.starsgroupchina.common.file.FileUploadService;
import com.starsgroupchina.common.response.SimpleResponse;
import com.starsgroupchina.credit.bean.UserInfo;
import com.starsgroupchina.credit.bean.extend.WebsiteAuditExtend;
import com.starsgroupchina.credit.bean.model.*;
import com.starsgroupchina.credit.server.service.project.ContactService;
import com.starsgroupchina.credit.server.service.project.UserService;
import com.starsgroupchina.credit.server.service.project.WebsiteService;
import com.starsgroupchina.credit.server.utils.PropertyUtil;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

@Slf4j
@RestController
@Api(tags = "CREDIT-SWAGGER00", description = "【项目】 - 网查接口 - WebsiteAuditController")
@RequestMapping(value = "/project/website", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class WebsiteController {

    @Autowired
    private WebsiteService websiteAuditService;

    @Autowired
    private WebsiteService.WebsiteAuditPicService websiteAuditPicService;

    @Autowired
    private FileUploadService fileUploadService;

    @Value("${image-url}")
    private String imageUrl;

    @Value("${spring.application.name:spring.application.name}")
    private String appKey;

    @Autowired
    private ContactService contactService;

    @Autowired
    private UserService userService;

    /**
     * 根据projectId获取网查信息(包括网查信息以及附件信息）
     */
    @ApiOperation("projectNo(包括网查信息以及附件信息）")
    @RequestMapping(value = "/{projectNo}", method = RequestMethod.GET)
    public SimpleResponse<List<WebsiteAuditExtend>> query(@ApiParam("项目编号") @PathVariable(value = "projectNo") String projectNo) {
        WebsiteAuditExample example = new WebsiteAuditExample();
        example.createCriteria().andProjectNoEqualTo(projectNo);
        WebsiteAuditPicExample picExample = new WebsiteAuditPicExample();
        picExample.createCriteria().andProjectNoEqualTo(projectNo);
        List<WebsiteAuditExtend> result = Lists.newArrayList();
        List<WebsiteAuditPic> picList = websiteAuditPicService.query(picExample).collect(toList());
        picList.forEach(websiteAuditPic -> {
            websiteAuditPic.setFileUrl(imageUrl + websiteAuditPic.getFileKey());
        });
        Map<String, List<WebsiteAuditPic>> picMap = picList.stream().collect(groupingBy(pic -> pic.getRelation() + pic.getWebsite() + pic.getQueryCondition()));
        Optional.ofNullable(websiteAuditService.query(example)).ifPresent(websiteAuditList ->
                websiteAuditList.forEach(websiteAudit -> {
                    WebsiteAuditExtend websiteAuditExtend = new WebsiteAuditExtend(websiteAudit);
                    websiteAuditExtend.setWebsiteAuditPic(picMap.get(websiteAudit.getRelation() + websiteAudit.getWebsite() + websiteAudit.getQueryCondition()));
                    result.add(websiteAuditExtend);
                })
        );
        return SimpleResponse.success(result);
    }


    /**
     * 批量创建网查信息
     */
    @ApiOperation("批量创建网查信息")
    @RequestMapping(value = "", method = RequestMethod.POST)
    @Transactional
    public SimpleResponse<List<WebsiteAudit>> createWebsite(@RequestBody List<WebsiteAudit> websiteAuditList) {
        if (CollectionUtils.isEmpty(websiteAuditList)) {
            return SimpleResponse.success(websiteAuditList);
        }
        List<WebsiteAudit> result;
        if (websiteAuditList.get(0).getId()!=null){
            result = websiteAuditService.update(websiteAuditList);
        }else {
            result = websiteAuditService.create(websiteAuditList);
        }
        return SimpleResponse.success(result);
    }

    /**
     * 根据id删除网查信息
     */
    @ApiOperation("根据id删除网查信息")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public SimpleResponse deleteWebsite(@PathVariable("id") Integer id) {
        websiteAuditService.deleteById(id);
        return SimpleResponse.success();
    }

    /**
     * 根据projectNo获取基本信息
     */
    @ApiOperation("根据projectNo获取基本信息")
    @RequestMapping(value = "/{projectNo}/info", method = RequestMethod.GET)
    public SimpleResponse<UserInfo> getWebSiteBaseData(@PathVariable("projectNo") String projectNo) {
        UserInfo userInfo = userService.getUserInfo(projectNo);
        ProjectContactExample projectContactExample = new ProjectContactExample();
        if (userInfo.getProjectNo() == null) {
            return SimpleResponse.success(userInfo);
        }
        projectContactExample.createCriteria().andProjectNoEqualTo(userInfo.getProjectNo()).andSourceEqualTo(0);
        StringBuilder tempData = new StringBuilder();
        contactService.query(projectContactExample).forEach(projectContact -> {
            if (projectContact.getPhone() != null) {
                tempData.append(projectContact.getPhone() + "/");
            }
        });
        if (StringUtils.isNotEmpty(tempData.toString())) {
            userInfo.setRelationPhone(tempData.toString().substring(0, tempData.lastIndexOf("/")));
        }
        return SimpleResponse.success(userInfo);
    }


    /*******************************************************/
    /****************   website  audit  picture   *****************/
    /*******************************************************/


    /**
     * 创建网查附件
     */
    @ApiOperation("创建网查附件")
    @RequestMapping(value = "/pic", method = RequestMethod.POST)
    public SimpleResponse<WebsiteAuditPic> create(HttpServletRequest request) {
        MultipartHttpServletRequest mRequest = (MultipartHttpServletRequest) request;
        MultipartFile file = mRequest.getFile("file");
        WebsiteAuditPic websiteAuditPic = new WebsiteAuditPic();
        PropertyUtil.set(websiteAuditPic, mRequest);
        String fileKey = fileUploadService.upload(file, appKey);
        websiteAuditPic.setFileKey(fileKey);
        WebsiteAuditPic result = websiteAuditPicService.create(websiteAuditPic);
        return SimpleResponse.success(result);
    }

    /**
     * 根据ID删除网查附件
     */
    @ApiOperation("根据ID删除网查附件")
    @RequestMapping(value = "/pic/{id}", method = RequestMethod.DELETE)
    public SimpleResponse deleteField(@PathVariable("id") Integer id) {
        WebsiteAuditPic websiteAuditPic = websiteAuditPicService.getById(id);
        websiteAuditPicService.deleteById(id);
        fileUploadService.delete(websiteAuditPic.getFileKey());
        return SimpleResponse.success();
    }

}
