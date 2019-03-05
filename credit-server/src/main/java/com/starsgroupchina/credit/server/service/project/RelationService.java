package com.starsgroupchina.credit.server.service.project;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.starsgroupchina.common.base.AbstractService;
import com.starsgroupchina.common.objects.If;
import com.starsgroupchina.common.utils.Utils;
import com.starsgroupchina.credit.bean.UserInfo;
import com.starsgroupchina.credit.bean.enums.FormType;
import com.starsgroupchina.credit.bean.extend.RelationBlacklistExtend;
import com.starsgroupchina.credit.bean.model.*;
import com.starsgroupchina.credit.server.service.BlacklistService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

/**
 * Created by zhangfeng on 2018/7/3
 */
@Slf4j
@Service
public class RelationService extends AbstractService<ProjectRelation, ProjectRelationExample> {

    @Autowired
    private UserService userService;
    @Autowired
    private FormService.FormFieldService fieldService;
    @Autowired
    private FormService.UserFormDetailService userFormService;

    public ProjectRelation getRelation(ProjectRelation source) {
        ProjectRelationExample example = new ProjectRelationExample();
        example.createCriteria()
                .andProjectNoEqualTo(source.getProjectNo())
                .andRelationProjectNoEqualTo(source.getRelationProjectNo());
        return Utils.getFirst(query(example));
    }

    public List<ProjectRelation> getRelations(String projectNo) {
        List<ProjectRelation> relations = Lists.newArrayList();
        FormDetailUser formDetail = userFormService.getUserFormDetail(projectNo);

        FormFieldExample example = new FormFieldExample();
        example.createCriteria()
                .andFormTypeEqualTo(FormType.FORM_USER.key())
                .andProjectNoNotEqualTo(projectNo);
        example.setAdditionalWhere(where(formDetail));

        Map<String, List<FormField>> listMap =
                fieldService.query(example).collect(Collectors.groupingBy(FormField::getProjectNo));

        listMap.forEach((_projectNo, formFields) -> {
            UserInfo userInfo = userService.getUserInfo(_projectNo);
            if (userInfo != null) {
                ProjectRelation relation = new ProjectRelation();
                relation.setProjectId(formDetail.getProjectId());
                relation.setProjectNo(projectNo);
                relation.setRelationProjectNo(_projectNo);
                relation.setUserName(userInfo.getName());

                String point = formFields.stream()
                        .map(formField -> "[" + formField.getFieldName() + ":" + formField.getFieldValue() + "]")
                        .collect(Collectors.joining(","));
                relation.setRelationPoint(point);
                relations.add(relation);
            }
        });
        return relations;
    }

    @Override
    public List<ProjectRelation> update(List<ProjectRelation> relations) {
        relations.forEach(relation -> {
            ProjectRelation projectRelation = this.getRelation(relation);
            if (projectRelation == null) {
                this.create(relation);
            } else {
//                projectRelation.setRelationPoint(relation.getRelationPoint());
                this.update(relation);
            }
        });
        return relations;
    }

    private String where(FormDetailUser form) {
        StringBuffer sb = new StringBuffer();
        sb.append(String.format("( form_type='FORM_USER' and project_no<>'%s') and (", form.getProjectNo()));
        If.of(StringUtils.isNotEmpty(form.getB005())).isTrue(() -> sb.append("field_value = '" + form.getB005() + "' or "));
        If.of(StringUtils.isNotEmpty(form.getB016())).isTrue(() -> sb.append("field_value = '" + form.getB016() + "' or "));
        If.of(StringUtils.isNotEmpty(form.getB025())).isTrue(() -> sb.append("field_value = '" + form.getB025() + "' or "));
        If.of(StringUtils.isNotEmpty(form.getB033())).isTrue(() -> sb.append("field_value = '" + form.getB033() + "' or "));
        If.of(StringUtils.isNotEmpty(form.getB034())).isTrue(() -> sb.append("field_value = '" + form.getB034() + "' or "));
        If.of(StringUtils.isNotEmpty(form.getB035())).isTrue(() -> sb.append("field_value = '" + form.getB035() + "' or "));
        If.of(StringUtils.isNotEmpty(form.getC001())).isTrue(() -> sb.append("field_value = '" + form.getC001() + "' or "));
        If.of(StringUtils.isNotEmpty(form.getC002())).isTrue(() -> sb.append("field_value = '" + form.getC002() + "' or "));
        If.of(StringUtils.isNotEmpty(form.getC005())).isTrue(() -> sb.append("field_value = '" + form.getC005() + "' or "));
        If.of(StringUtils.isNotEmpty(form.getD018())).isTrue(() -> sb.append("field_value = '" + form.getD018() + "' or "));
        If.of(StringUtils.isNotEmpty(form.getE005())).isTrue(() -> sb.append("field_value = '" + form.getE005() + "' or "));

        If.of(StringUtils.isNotEmpty(form.getPa003())).isTrue(() -> sb.append("field_value = '" + form.getPa003() + "' or "));
        If.of(StringUtils.isNotEmpty(form.getPa004())).isTrue(() -> sb.append("field_value = '" + form.getPa004() + "' or "));
        If.of(StringUtils.isNotEmpty(form.getPa007())).isTrue(() -> sb.append("field_value = '" + form.getPa007() + "' or "));
        If.of(StringUtils.isNotEmpty(form.getPa008())).isTrue(() -> sb.append("field_value = '" + form.getPa008() + "' or "));

        If.of(StringUtils.isNotEmpty(form.getPb003())).isTrue(() -> sb.append("field_value = '" + form.getPb003() + "' or "));
        If.of(StringUtils.isNotEmpty(form.getPb004())).isTrue(() -> sb.append("field_value = '" + form.getPb004() + "' or "));
        If.of(StringUtils.isNotEmpty(form.getPb007())).isTrue(() -> sb.append("field_value = '" + form.getPb007() + "' or "));
        If.of(StringUtils.isNotEmpty(form.getPb008())).isTrue(() -> sb.append("field_value = '" + form.getPb008() + "' or "));

        If.of(StringUtils.isNotEmpty(form.getPc003())).isTrue(() -> sb.append("field_value = '" + form.getPc003() + "' or "));
        If.of(StringUtils.isNotEmpty(form.getPc004())).isTrue(() -> sb.append("field_value = '" + form.getPc004() + "' or "));
        If.of(StringUtils.isNotEmpty(form.getPc007())).isTrue(() -> sb.append("field_value = '" + form.getPc007() + "' or "));
        If.of(StringUtils.isNotEmpty(form.getPc008())).isTrue(() -> sb.append("field_value = '" + form.getPc008() + "' or "));

        If.of(StringUtils.isNotEmpty(form.getPd003())).isTrue(() -> sb.append("field_value = '" + form.getPd003() + "' or "));
        If.of(StringUtils.isNotEmpty(form.getPd004())).isTrue(() -> sb.append("field_value = '" + form.getPd004() + "' or "));
        If.of(StringUtils.isNotEmpty(form.getPd007())).isTrue(() -> sb.append("field_value = '" + form.getPd007() + "' or "));
        If.of(StringUtils.isNotEmpty(form.getPd008())).isTrue(() -> sb.append("field_value = '" + form.getPd008() + "' or "));
        sb.append(" 1<>1 )");
        return sb.toString();
    }
    @Service
    public class RelationBlacklistService extends AbstractService<ProjectRelationBlacklist, ProjectRelationBlacklistExample> {

        @Autowired
        private RelationService relationService;

        @Autowired
        private UserService userService;

        @Autowired
        private BlacklistService blacklistService;

        public void createBlacklist(String projectNo) {
            //先删后插
            ProjectRelationBlacklistExample blacklistExample = new ProjectRelationBlacklistExample();
            blacklistExample.createCriteria().andProjectNoEqualTo(projectNo);
            this.deleteByExample(blacklistExample);

            ProjectRelationExample relationExample = new ProjectRelationExample();
            relationExample.createCriteria().andProjectNoEqualTo(projectNo);
            Set<ProjectRelationBlacklist> blacklistSet = Sets.newHashSet();
            relationService.query(relationExample).forEach(projectRelation -> {
                UserInfo userInfo = userService.getUserInfo(projectRelation.getRelationProjectNo());
                List<Blacklist> blacklistHit = blacklistService.getBlacklistHit(userInfo);
                blacklistHit.forEach(blacklist -> {
                    ProjectRelationBlacklist projectRelationBlacklist = new ProjectRelationBlacklist();
                    projectRelationBlacklist.setBlacklistId(blacklist.getId());
                    projectRelationBlacklist.setProjectNo(projectNo);
                    blacklistSet.add(projectRelationBlacklist);
                });
            });
            //加上自己黑名单信息
            UserInfo userInfo = userService.getUserInfo(projectNo);
            blacklistService.getBlacklistHit(userInfo).forEach(blacklist -> {
                ProjectRelationBlacklist projectRelationBlacklist = new ProjectRelationBlacklist();
                projectRelationBlacklist.setBlacklistId(blacklist.getId());
                projectRelationBlacklist.setProjectNo(projectNo);
                blacklistSet.add(projectRelationBlacklist);
            });
            List<ProjectRelationBlacklist> blacklists = blacklistSet.stream().collect(toList());
            this.create(blacklists);
        }

        public List<RelationBlacklistExtend> getRelationBlacks(String projectNo){
            ProjectRelationBlacklistExample relationBlacklistExample = new ProjectRelationBlacklistExample();
            relationBlacklistExample.createCriteria().andProjectNoEqualTo(projectNo);
            List<RelationBlacklistExtend> result = Lists.newArrayList();
            this.query(relationBlacklistExample).forEach(projectRelationBlacklist -> {
                RelationBlacklistExtend relationBlacklistExtend = new RelationBlacklistExtend(projectRelationBlacklist);
                Blacklist blacklist = blacklistService.getById(projectRelationBlacklist.getBlacklistId());
                relationBlacklistExtend.setBlacklist(blacklist);
                result.add(relationBlacklistExtend);
            });
            return result;
        }

        /**
         * 查询黑名单命中，风控报告
         * @param userInfo
         * @return
         */
        public List<RelationBlacklistExtend> getBlackListHit(UserInfo userInfo){
            List<RelationBlacklistExtend> list = getRelationBlacks(userInfo.getProjectNo());
            //先查询出来黑名单，然后看是否命中
            return list.stream().filter(relationBlacklistExtend -> {
                String idCard = relationBlacklistExtend.getBlacklist().getIdCard();
                String name = relationBlacklistExtend.getBlacklist().getName();
                //如果黑名单对应的idCard等于身份证号或者公司注册号，则表示命中
                return idCard.equals(userInfo.getIdCard())||name.equals(userInfo.getCompanyName());
            }).collect(toList());
        }
    }
}
