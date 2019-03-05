package com.starsgroupchina.credit.server.service.project;

import com.starsgroupchina.common.base.AbstractService;
import com.starsgroupchina.credit.bean.Contact;
import com.starsgroupchina.credit.bean.model.ProjectContact;
import com.starsgroupchina.credit.bean.model.ProjectContactExample;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zhangfeng on 2018/6/21
 */
@Slf4j
@Service
public class ContactService extends AbstractService<ProjectContact, ProjectContactExample> {

    @Autowired
    private FormService.UserFormDetailService userFormService;

    public List<Contact> getContact(String projectNo) {
        return Contact.getContact(userFormService.getUserFormDetail(projectNo));
    }

    public List<Contact> updateContact(List<Contact> contacts) {
        contacts.forEach(contact -> {
            ProjectContactExample example = new ProjectContactExample();
            example.createCriteria().andProjectNoEqualTo(contact.getProjectNo()).andIdxEqualTo(contact.getIdx());
            if (this.query(example).count() > 0)
                this.update(contact, example);
            else
                this.create(contact);
        });
        return contacts;
    }

    public boolean existContact(String name, String phone) {
        ProjectContactExample example = new ProjectContactExample();
        example.createCriteria().andNameEqualTo(name).andPhoneEqualTo(phone);
        return query(example).count() > 0;
    }


}
