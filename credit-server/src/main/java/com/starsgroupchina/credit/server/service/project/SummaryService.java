package com.starsgroupchina.credit.server.service.project;

import com.google.common.collect.Lists;
import com.starsgroupchina.common.base.AbstractService;
import com.starsgroupchina.credit.bean.Contact;
import com.starsgroupchina.credit.bean.UserInfo;
import com.starsgroupchina.credit.bean.enums.RiskStatus;
import com.starsgroupchina.credit.bean.enums.RiskType;
import com.starsgroupchina.credit.bean.extend.SummaryExtend;
import com.starsgroupchina.credit.bean.model.*;
import com.starsgroupchina.credit.server.service.system.OrgService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;
/**
  * @Author: QinHaoHao
  * @Description: 客户综述
  * @Date: Created in 14:38 2018/7/3
  * @Modifed By:
  */
@Slf4j
@Service
public class SummaryService extends AbstractService<ProjectSummary, ProjectSummaryExample> {
    @Autowired
    private WebsiteService websiteService;

    @Autowired
    private ContactService contactService;

    @Autowired
    private FileInspectService fileInspectService;

    @Autowired
    private OrgService.OrgFileService orgFileService;

    @Autowired
    private UserService userService;

    @Autowired
    private RelationService relationService;

    /**
     * 获取客户综述
     * @param projectNo
     * @return
     */
    public List<SummaryExtend> getSummary(String projectNo){
        ProjectSummaryExample projectSummaryExample = new ProjectSummaryExample();
        projectSummaryExample.createCriteria().andProjectNoEqualTo(projectNo);
        List<ProjectSummary> summaryList = query(projectSummaryExample).collect(toList());
        /**
         * 网查
         */
        final Map<Integer, List<ProjectSummary>> websiteMap = new HashMap<>();
        /**
         * 电核类
         */
        final Map<Integer, List<ProjectSummary>> phoneMap = new HashMap<>();
        /**
         * 资料审查
         */
        final Map<Integer, List<ProjectSummary>> fileMap = new HashMap<>();
        /**
         * 关联报告
         */
        final Map<Integer, List<ProjectSummary>> relevanceMap = new HashMap<>();

        if (CollectionUtils.isNotEmpty(summaryList)) {
            putMap(summaryList, RiskType.RISK_WEBSITE.key(), websiteMap);
            putMap(summaryList, RiskType.RISK_PHONE.key(), phoneMap);
            putMap(summaryList, RiskType.RISK_FILE.key(), fileMap);
            putMap(summaryList, RiskType.RISK_RELEVANCE.key(), relevanceMap);
        }
        /**
         * 获取网查信息
         */
        WebsiteAuditExample websiteAuditExample = new WebsiteAuditExample();
        websiteAuditExample.createCriteria().andProjectNoEqualTo(projectNo).andIsOkEqualTo(RiskStatus.NO.key());
        List<SummaryExtend> result = Lists.newArrayList();
        Optional.ofNullable(websiteService.query(websiteAuditExample)).ifPresent(websiteAuditStream -> {
            websiteAuditStream.forEach(
                    websiteAudit -> {
                        result.add(getInstance(websiteMap, websiteAudit));
                    }
            );
        });
        /**
         * 获取数据审查信息
         */
        ProjectFileInspectExample projectFileInspectExample = new ProjectFileInspectExample();
        projectFileInspectExample.createCriteria().andProjectNoEqualTo(projectNo).andInvalidEqualTo(RiskStatus.NO.key());
        Optional.ofNullable(fileInspectService.query(projectFileInspectExample)).ifPresent(fileInspectStream -> {
            fileInspectStream.forEach(
                    fileInspect -> {
                        result.add(getInstance(fileMap, fileInspect));
                    }
            );
        });

        /**
         * 获取电核信息
         */
        ProjectContactExample projectContactExample = new ProjectContactExample();
        projectContactExample.createCriteria().andProjectNoEqualTo(projectNo).andIsOkEqualTo(RiskStatus.NO.key()).andPhoneIsNotNull();
        Optional.ofNullable(contactService.query(projectContactExample)).ifPresent(projectContactStream -> {
            projectContactStream.forEach(
                    projectContact -> {
                        result.add(getInstance(phoneMap, projectContact));
                    }
            );
        });
        /**
         * 关联报告
         */
        ProjectRelationExample projectRelationExample = new ProjectRelationExample();
        projectRelationExample.createCriteria().andProjectNoEqualTo(projectNo).andIsOkEqualTo(RiskStatus.NO.key().intValue());
        Optional.ofNullable(relationService.query(projectRelationExample)).ifPresent(projectRelationStream -> {
            projectRelationStream.forEach(projectRelation -> {
                result.add(getInstance(relevanceMap,projectRelation));
            });
        });
        return result;
    }
    /**
     * 获取对象
     *
     * @param map    格式为<riskId,ProjectSummary/> 正常是一个  id+riskType 对应一条数据,两次分组之后当前map为1-1对应
     * @param object
     * @return
     */
    private SummaryExtend getInstance(Map<Integer, List<ProjectSummary>> map, Object object) {
        SummaryExtend summaryExtend = null;
        if (map.size() != 0) {
            List<ProjectSummary> list = map.get(getId(object));
            if (CollectionUtils.isNotEmpty(list)) {
                ProjectSummary projectSummary = list.get(0);
                summaryExtend = new SummaryExtend(projectSummary);
                setSummary(object, summaryExtend);
            } else {
                summaryExtend = newInstance(object);
            }
        } else {
            summaryExtend = newInstance(object);
        }
        return summaryExtend;
    }

    /**
     * 统一设置风险点 item1、item2、item3、riskContent
     *
     * @param object
     * @param summaryExtend
     */
    private void setSummary(Object object, SummaryExtend summaryExtend) {
        if (object instanceof WebsiteAudit) {
            WebsiteAudit websiteAudit = (WebsiteAudit) object;
            String projectNo = websiteAudit.getProjectNo();
            UserInfo userInfo = userService.getUserInfo(projectNo);
            List<Contact> contact = contactService.getContact(projectNo);
            summaryExtend.setRiskContent(websiteAudit.getRemark());
            String relation = websiteAudit.getRelation();
            summaryExtend.setItem1(relation);
            summaryExtend.setItem2(websiteAudit.getWebsite());
            String queryCondition = websiteAudit.getQueryCondition();
            summaryExtend.setItem3(queryCondition);
            if ("客本人".equals(relation)) {
                if (queryCondition.contains("身份证号")) {
                    summaryExtend.setItem4(userInfo.getIdCard());
                } else if (queryCondition.contains("手机号")) {
                    summaryExtend.setItem4(userInfo.getPhone());
                } else if (queryCondition.contains("姓名")) {
                    summaryExtend.setItem4(userInfo.getName());
                }
            }else if("客配偶".equals(relation)){
                if (queryCondition.contains("身份证号")) {
                    summaryExtend.setItem4(userInfo.getSpouseIdCard());
                } else if (queryCondition.contains("手机号")) {
                    summaryExtend.setItem4(userInfo.getSpousePhone());
                } else if (queryCondition.contains("姓名")) {
                    summaryExtend.setItem4(userInfo.getSpouseName());
                }
            }else if("客公司".equals(relation)){
                summaryExtend.setItem4(userInfo.getCompanyName());
            }else if("联系人".equals(relation)){
                StringBuilder stringBuilder = new StringBuilder();
                contact.forEach(contact1->{
                    stringBuilder.append(contact1.getPhone()+"/");
                });
                String result = stringBuilder.toString();
                if (result.length()>0){
                    summaryExtend.setItem4(result.toString());
                }
            }
            summaryExtend.setSource("网查");
        }
        if (object instanceof ProjectFileInspect) {
            ProjectFileInspect projectFileInspect = (ProjectFileInspect) object;
            OrgFile orgFile = orgFileService.getById(projectFileInspect.getOrgFileId());
            summaryExtend.setItem1(orgFile.getFileType());
            summaryExtend.setItem2(orgFile.getFileName());
            summaryExtend.setRiskContent(projectFileInspect.getRemark());
            summaryExtend.setSource("资料审查");
        }
        if (object instanceof ProjectContact) {
            ProjectContact projectContact = (ProjectContact) object;
            summaryExtend.setItem1(projectContact.getName());
            summaryExtend.setItem2(projectContact.getPhone());
            if (StringUtils.isNotEmpty(projectContact.getRelation())) {
                summaryExtend.setItem3(projectContact.getRelation());
            }
            summaryExtend.setRiskContent(projectContact.getRemark());
            summaryExtend.setSource("电核");
        }
        if(object instanceof ProjectRelation){
            ProjectRelation projectRelation = (ProjectRelation) object;
            summaryExtend.setItem1(projectRelation.getRelationProjectNo());
            summaryExtend.setItem2(projectRelation.getUserName());
            summaryExtend.setItem3(projectRelation.getRelationPoint());
            summaryExtend.setRiskContent(projectRelation.getRemark());
            summaryExtend.setSource("关联报告");
        }
    }
    /**
     * 获取id
     *
     * @param object
     * @return
     */

    private Integer getId(Object object) {
        Integer id = null;
        if (object instanceof WebsiteAudit) {
            WebsiteAudit websiteAudit = (WebsiteAudit) object;
            id = websiteAudit.getId();
        }
        if (object instanceof ProjectFileInspect) {
            ProjectFileInspect projectFileInspect = (ProjectFileInspect) object;
            id = projectFileInspect.getId();
        }
        if (object instanceof ProjectContact) {
            ProjectContact projectContact = (ProjectContact) object;
            id = projectContact.getId();
        }
        if (object instanceof ProjectRelation) {
            ProjectRelation projectRelation = (ProjectRelation) object;
            id = projectRelation.getId();
        }
        return id;
    }
    /**
     * 新建对象,设置风险点、风险类型、项目id、项目编号
     *
     * @param object
     * @return
     */
    private SummaryExtend newInstance(Object object) {
        SummaryExtend summaryExtend = new SummaryExtend();
        if (object instanceof WebsiteAudit) {
            WebsiteAudit websiteAudit = (WebsiteAudit) object;
            summaryExtend.setRiskId(websiteAudit.getId());
            summaryExtend.setRiskType(RiskType.RISK_WEBSITE.key());
            summaryExtend.setProjectId(websiteAudit.getProjectId());
            summaryExtend.setProjectNo(websiteAudit.getProjectNo());
            setSummary(websiteAudit, summaryExtend);
        }
        if (object instanceof ProjectFileInspect) {
            ProjectFileInspect projectFileInspect = (ProjectFileInspect) object;
            summaryExtend.setRiskId(projectFileInspect.getId());
            summaryExtend.setRiskType(RiskType.RISK_FILE.key());
            summaryExtend.setProjectId(projectFileInspect.getProjectId());
            summaryExtend.setProjectNo(projectFileInspect.getProjectNo());
            setSummary(projectFileInspect, summaryExtend);
        }
        if (object instanceof ProjectContact) {
            ProjectContact projectContact = (ProjectContact) object;
            summaryExtend.setRiskId(projectContact.getId());
            summaryExtend.setRiskType(RiskType.RISK_PHONE.key());
            summaryExtend.setProjectId(projectContact.getProjectId());
            summaryExtend.setProjectNo(projectContact.getProjectNo());
            setSummary(projectContact, summaryExtend);
        }
        if(object instanceof ProjectRelation){
            ProjectRelation projectRelation = (ProjectRelation) object;
            summaryExtend.setRiskId(projectRelation.getId());
            summaryExtend.setRiskType(RiskType.RISK_RELEVANCE.key());
            summaryExtend.setProjectId(projectRelation.getProjectId());
            summaryExtend.setProjectNo(projectRelation.getProjectNo());
            setSummary(projectRelation,summaryExtend);

        }
        return summaryExtend;
    }
    /**
     * 获取当前类当前id分组
     *
     * @param summaryList
     * @param riskType
     * @return
     */
    private void putMap(List<ProjectSummary> summaryList, String riskType, Map<Integer, List<ProjectSummary>> map) {
        Map<String, List<ProjectSummary>> summaryMap = summaryList.stream().collect(groupingBy(ProjectSummary::getRiskType));
        Map<Integer, List<ProjectSummary>> resultMap = null;
        if (summaryMap != null && summaryMap.size() != 0) {
            List<ProjectSummary> resultList = summaryMap.get(riskType);
            if (CollectionUtils.isNotEmpty(resultList)) {
                resultMap = resultList.stream().collect(groupingBy(ProjectSummary::getRiskId));
            }
        }
        if (resultMap != null) {
            map.putAll(resultMap);
        }
    }

}
