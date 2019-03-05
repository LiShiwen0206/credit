package com.starsgroupchina.credit.server.controller.project;

import com.starsgroupchina.common.exception.AppException;
import com.starsgroupchina.common.response.SimpleResponse;
import com.starsgroupchina.credit.bean.model.ProjectContact;
import com.starsgroupchina.credit.bean.model.ProjectContactExample;
import com.starsgroupchina.credit.key.ErrorCode;
import com.starsgroupchina.credit.server.service.project.ContactService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by zhangfeng on 2018/6/21
 */
@Slf4j
@RestController
@Api(tags = "CREDIT-SWAGGER16", description = "【项目】 - 电核 - ContactController")
@RequestMapping(value = "/project/contact", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class ContactController {

    @Autowired
    private ContactService contactService;

    /**
     * 获取电核联系人
     */
    @ApiOperation("获取联系人")
    @RequestMapping(value = "/{projectNo}", method = RequestMethod.GET)
    public SimpleResponse<List<ProjectContact>> query(@PathVariable("projectNo") String projectNo) {
        ProjectContactExample example = new ProjectContactExample();
        example.createCriteria().andProjectNoEqualTo(projectNo);
        example.setOrderByClause("if(isnull(idx),1000,idx),create_time desc");
        List<ProjectContact> result = contactService.query(example).collect(Collectors.toList());
        return SimpleResponse.success(result);
    }

    /**
     * 新增电核联系人
     */
    @ApiResponses({@ApiResponse(code = ErrorCode.PROJECT_CONTACT_EXIST, message = "当前联系人已存在")})
    @ApiOperation("新增联系人")
    @RequestMapping(value = "", method = RequestMethod.POST)
    public SimpleResponse<ProjectContact> create(@RequestBody ProjectContact contact) {
        if (contactService.existContact(contact.getName(), contact.getPhone()))
            throw new AppException(ErrorCode.PROJECT_CONTACT_EXIST);

        contact.setSource(1);
        contactService.create(contact);
        return SimpleResponse.success(contact);
    }

    /**
     * 修改联系人
     */
    @ApiOperation("修改联系人")
    @RequestMapping(value = "/{projectNo}", method = RequestMethod.PUT)
    @Transactional
    public SimpleResponse<List<ProjectContact>> update(
            @RequestBody List<ProjectContact> contacts, @PathVariable("projectNo") String projectNo) {
        if (CollectionUtils.isEmpty(contacts)) {
            return SimpleResponse.success(contacts);
        }
        List<ProjectContact> result;
        if (contacts.get(0).getId() != null) {
            result = contactService.update(contacts);
        } else {
            result = contactService.create(contacts);
        }
        return SimpleResponse.success(result);
    }

}
