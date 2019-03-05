package com.starsgroupchina.credit.bean;

import com.google.common.collect.Lists;
import com.starsgroupchina.credit.bean.enums.ContactType;
import com.starsgroupchina.credit.bean.model.FormDetailUser;
import com.starsgroupchina.credit.bean.model.ProjectContact;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.apache.commons.lang.StringUtils;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zhangfeng on 2018/6/25
 */
@Data
@ApiModel
public class Contact extends ProjectContact implements Serializable {

    public Contact() {
    }

    public static List<Contact> getContact(FormDetailUser detail) {
        if (detail == null) return Lists.newArrayList();
        List<Contact> result = Lists.newArrayList();
        Contact contact11 = new Contact();
        Contact contact1 = new Contact();
        Contact contact2 = new Contact();
        Contact contact3 = new Contact();
        Contact contact4 = new Contact();

        contact11.setStatus((short) 0);
        contact11.setIdx(11);
        contact11.setProjectNo(detail.getProjectNo());
        contact11.setProjectId(detail.getProjectId());
        contact11.setCompanyName(detail.getC001());
        contact11.setCompanyAddress(detail.getC002());
        contact11.setCompanyTel(detail.getC005());
        contact11.setType(ContactType.COMPANY.key());



        contact1.setStatus((short) 0);
        contact1.setIdx(1);
        contact1.setProjectNo(detail.getProjectNo());
        contact1.setProjectId(detail.getProjectId());
        contact1.setName(detail.getPa001());
        contact1.setRelation(detail.getPa002());
        contact1.setPhone(detail.getPa003());
        contact1.setTel(detail.getPa004());
        contact1.setAddress(detail.getPa005());
        if(StringUtils.isNotEmpty(detail.getPa006())){
            contact1.setIsknow(Integer.valueOf(detail.getPa006()));
        }
        contact1.setCompanyName(detail.getPa007());
        contact1.setCompanyAddress(detail.getPa008());
        contact1.setCompanyTel(detail.getPa009());


        contact2.setStatus((short) 0);
        contact2.setIdx(2);
        contact2.setProjectNo(detail.getProjectNo());
        contact2.setProjectId(detail.getProjectId());
        contact2.setName(detail.getPb001());
        contact2.setRelation(detail.getPb002());
        contact2.setPhone(detail.getPb003());
        contact2.setTel(detail.getPb004());
        contact2.setAddress(detail.getPb005());
        if(StringUtils.isNotEmpty(detail.getPb006())){
            contact2.setIsknow(Integer.valueOf(detail.getPb006()));
        }
        contact2.setCompanyName(detail.getPb007());
        contact2.setCompanyAddress(detail.getPb008());
        contact2.setCompanyTel(detail.getPb009());

        contact3.setStatus((short) 0);
        contact3.setIdx(3);
        contact3.setProjectNo(detail.getProjectNo());
        contact3.setProjectId(detail.getProjectId());
        contact3.setName(detail.getPc001());
        contact3.setRelation(detail.getPc002());
        contact3.setPhone(detail.getPc003());
        contact3.setTel(detail.getPc004());
        contact3.setAddress(detail.getPc005());
        if(StringUtils.isNotEmpty(detail.getPc006())){
            contact3.setIsknow(Integer.valueOf(detail.getPc006()));
        }
        contact3.setCompanyName(detail.getPc007());
        contact3.setCompanyAddress(detail.getPc008());
        contact3.setCompanyTel(detail.getPc009());

        contact4.setStatus((short) 0);
        contact4.setIdx(4);
        contact4.setProjectNo(detail.getProjectNo());
        contact4.setProjectId(detail.getProjectId());
        contact4.setName(detail.getPd001());
        contact4.setRelation(detail.getPd002());
        contact4.setPhone(detail.getPd003());
        contact4.setTel(detail.getPd004());
        contact4.setAddress(detail.getPd005());
        if(StringUtils.isNotEmpty(detail.getPd006())){
            contact4.setIsknow(Integer.valueOf(detail.getPd006()));
        }
        contact4.setCompanyName(detail.getPd007());
        contact4.setCompanyAddress(detail.getPd008());
        contact4.setCompanyTel(detail.getPd009());

        result.add(contact1);
        result.add(contact2);
        result.add(contact3);
        result.add(contact4);
        result.add(contact11);
        return result;

    }

}
