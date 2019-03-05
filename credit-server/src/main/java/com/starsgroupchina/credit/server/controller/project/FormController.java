package com.starsgroupchina.credit.server.controller.project;

import com.starsgroupchina.common.response.SimpleResponse;
import com.starsgroupchina.credit.bean.Contact;
import com.starsgroupchina.credit.bean.enums.FormType;
import com.starsgroupchina.credit.bean.model.FormDetailUser;
import com.starsgroupchina.credit.bean.model.ProjectForm;
import com.starsgroupchina.credit.bean.model.ProjectFormExample;
import com.starsgroupchina.credit.server.service.project.ContactService;
import com.starsgroupchina.credit.server.service.project.FormService;
import com.starsgroupchina.credit.server.service.project.RelationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

/**
 * Created by zhangfeng on 2018-6-13.
 */
@Slf4j
@RestController
@Api(tags = "CREDIT-SWAGGER13", description = "【项目】 - 表单 - FormController")
@RequestMapping(value = "/project/form", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class FormController {

    @Autowired
    private FormService formService;
    @Autowired
    private FormService.UserFormDetailService userFormService;
    @Autowired
    private FormService.UserFormDetailHistoryService userFormHistoryService;
    @Autowired
    private FormService.CarFormDetailService carFormService;
    @Autowired
    private FormService.FormFieldService formFieldService;
    @Autowired
    private ContactService contactService;
    @Autowired
    private RelationService relationService;
    @Autowired
    private RelationService.RelationBlacklistService relationBlacklistService;

    /**
     * 根据项目编号 获取 表单
     */
    @ApiOperation("根据项目编号 获取 表单")
    @RequestMapping(value = "/{projectNo}", method = RequestMethod.GET)
    public SimpleResponse<List<ProjectForm>> getProjectForm(
            @PathVariable("projectNo") String projectNo,
            @ApiParam("表单类型:产品表单 FORM_PRODUCT,用户表单 FORM_USER,车辆表单 FORM_CAR") @RequestParam(value = "type", required = false) String type) {

        ProjectFormExample example = new ProjectFormExample();
        example.createCriteria().andProjectNoEqualTo(projectNo);
        Stream<ProjectForm> formStream = formService.query(example);

        if (!StringUtils.isEmpty(type))
            return SimpleResponse.success(
                    formStream.filter(f -> f.getFormType().equals(type)).collect(toList()));

        return SimpleResponse.success(formStream.collect(toList()));
    }

    /**
     * 创建表单（内部调用不对外）
     */
    @ApiOperation("创建表单（内部调用不对外）")
    @RequestMapping(value = "", method = RequestMethod.POST)
    @Transactional
    public SimpleResponse<ProjectForm> createProjectForm(@RequestBody ProjectForm form) {
        if (form.getFormType().equals(FormType.FORM_USER.key())) {
            userFormService.createUserFormDetail(form);

            Stream<Contact> stream = contactService.getContact(form.getProjectNo()).stream();
            contactService.create(stream.collect(toList()));

            FormDetailUser formDetail = userFormService.getUserFormDetail(form.getProjectNo());
            formFieldService.createUserFormField(formDetail);

            relationService.create(relationService.getRelations(form.getProjectNo()));

            relationBlacklistService.createBlacklist(form.getProjectNo());
        }
        if (form.getFormType().equals(FormType.FORM_CAR.key()))
            carFormService.createCarFormDetail(form);

        formService.create(form);
        return SimpleResponse.success(form);
    }

    /**
     * 修改表单
     */
    @ApiOperation("修改表单")
    @RequestMapping(value = "", method = RequestMethod.PUT)
    @Transactional
    public SimpleResponse<ProjectForm> updateProjectForm(@RequestBody ProjectForm form) {
        formService.update(form);

        if (form.getFormType().equals(FormType.FORM_USER.key())) {
            FormDetailUser oldFormDetail = userFormService.getUserFormDetail(form.getProjectNo());
            userFormService.deleteUserFormDetail(form.getProjectNo());
            userFormService.createUserFormDetail(form);
            FormDetailUser newFormDetail = userFormService.getUserFormDetail(form.getProjectNo());

            List<Contact> contacts = contactService.getContact(form.getProjectNo());
            contactService.updateContact(contacts);

            formFieldService.deleteUserFormField(form.getProjectNo());
            FormDetailUser formDetail = userFormService.getUserFormDetail(form.getProjectNo());
            formFieldService.createUserFormField(formDetail);

            relationService.update(relationService.getRelations(form.getProjectNo()));
            relationBlacklistService.createBlacklist(form.getProjectNo());

            userFormHistoryService.createUserFormDetailHistory(oldFormDetail,newFormDetail);
        }

        if (form.getFormType().equals(FormType.FORM_CAR.key())) {
            carFormService.deleteCarFormDetail(form.getProjectNo());
            carFormService.createCarFormDetail(form);
        }
        return SimpleResponse.success(form);
    }




}
