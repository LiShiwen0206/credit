package com.starsgroupchina.credit.server.service.project;

import com.google.common.collect.Lists;
import com.starsgroupchina.common.base.AbstractService;
import com.starsgroupchina.common.context.ContextHolder;
import com.starsgroupchina.common.utils.BeanUtil;
import com.starsgroupchina.common.utils.MapUtil;
import com.starsgroupchina.common.utils.ReflectUtil;
import com.starsgroupchina.common.utils.Utils;
import com.starsgroupchina.credit.FormParser;
import com.starsgroupchina.credit.bean.AuthMember;
import com.starsgroupchina.credit.bean.DetailHistory;
import com.starsgroupchina.credit.bean.enums.FormType;
import com.starsgroupchina.credit.bean.mapper.*;
import com.starsgroupchina.credit.bean.model.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by zhangfeng on 2018-6-13.
 */
@Slf4j
@Service
public class FormService extends AbstractService<ProjectForm, ProjectFormExample> {

    @Autowired
    private ProjectFormMapper formMapper;
    @Autowired
    private FormDetailUserMapper detailUserMapper;
    @Autowired
    private FormDetailCarMapper detailCarMapper;
    @Autowired
    private FormDetailUserHistoryMapper detailUserHistoryMapper;
    @Autowired
    private FormDetailCarHistoryMapper detailCarHistoryMapper;

    @Override
    public ProjectForm update(ProjectForm form) {
        ProjectFormExample example = new ProjectFormExample();
        example.createCriteria().andProjectNoEqualTo(form.getProjectNo()).andFormTypeEqualTo(form.getFormType());
        formMapper.updateByExampleSelective(form, example);
        return form;
    }

    @Service
    public class UserFormDetailService extends AbstractService<FormDetailUser, FormDetailUserExample> {

        public FormDetailUser getUserFormDetail(String projectNo) {
            FormDetailUserExample example = new FormDetailUserExample();
            example.createCriteria().andProjectNoEqualTo(projectNo);
            return Utils.getFirst(this.query(example));
        }

        public void deleteUserFormDetail(String projectNo) {
            FormDetailUserExample example = new FormDetailUserExample();
            example.createCriteria().andProjectNoEqualTo(projectNo);
            this.deleteByExample(example);
        }

        public void createUserFormDetail(ProjectForm form) {
            AuthMember authMember= (AuthMember) ContextHolder.getContext().getData();
            FormDetailUser formDetail = null;
            if (StringUtils.isEmpty(form.getProjectForm())) {
                formDetail = new FormDetailUser();
            } else {
                Map<String, String> map = FormParser.parse(form.getProjectForm());
                formDetail = MapUtil.mapToObject(map, FormDetailUser.class);
            }
            formDetail.setProjectNo(form.getProjectNo());
            formDetail.setOrgId(authMember.getOrg().getId());
            formDetail.setHeadOrgId(authMember.getOrg().getHeadOrgId());
            detailUserMapper.insertSelective(formDetail);
        }
    }

    @Service
    public class CarFormDetailService extends AbstractService<FormDetailCar, FormDetailCarExample> {

        public FormDetailCar getCarFormDetail(String projectNo) {
            FormDetailCarExample example = new FormDetailCarExample();
            example.createCriteria().andProjectNoEqualTo(projectNo);
            return Utils.getFirst(this.query(example));
        }

        public void deleteCarFormDetail(String projectNo) {
            FormDetailCarExample example = new FormDetailCarExample();
            example.createCriteria().andProjectNoEqualTo(projectNo);
            this.deleteByExample(example);
        }

        public void createCarFormDetail(ProjectForm form) {
            FormDetailCar formDetail = null;
            if (StringUtils.isEmpty(form.getProjectForm()) || form.getProjectForm().equals("[]")) {
                return;
            } else {
                Map<String, String> map = FormParser.parse(form.getProjectForm());
                formDetail = MapUtil.mapToObject(map, FormDetailCar.class);
            }
            formDetail.setProjectNo(form.getProjectNo());
            detailCarMapper.insertSelective(formDetail);
        }

    }

    @Service
    public class UserFormDetailHistoryService extends AbstractService<FormDetailUserHistory, FormDetailUserHistoryExample> {

        public List<DetailHistory> getUserFormDetailHistorys(String projectNo) {
            FormDetailUserHistoryExample example = new FormDetailUserHistoryExample();
            example.createCriteria().andProjectNoEqualTo(projectNo);
            List<DetailHistory> result = Lists.newArrayList();
            this.query(example).forEach(formDetailUserHistory -> {

                Date createTime = formDetailUserHistory.getCreateTime();
                String createUser = formDetailUserHistory.getCreateUser();
                Integer id = formDetailUserHistory.getId();

                Class clazz = formDetailUserHistory.getClass();
                Field[] fields = clazz.getDeclaredFields();
                for (Field f : fields) {
                    try {
                        String name = f.getName();
                        if (!name.equals("serialVersionUID") && !name.equals("id") && !name.equals("projectId") && !name.equals("orgId")
                                && !name.equals("headOrgId") && !name.equals("projectNo") && !name.equals("status")
                                && !name.equals("createTime") && !name.equals("createUser") && !name.equals("createUserId")
                                && !name.equals("modifyTime") && !name.equals("modifyUser") && !name.equals("modifyUserId")) {

                            PropertyDescriptor pd = new PropertyDescriptor(f.getName(), clazz);
                            Method getMethod = pd.getReadMethod();
                            String value = getMethod.invoke(formDetailUserHistory) + "";
                            if (value.equals("") || value.equals("null"))
                                continue;

                            ApiModelProperty annotation = f.getAnnotation(ApiModelProperty.class);
                            DetailHistory detailHistory = new DetailHistory();
                            detailHistory.setFieldName(annotation.value());
                            detailHistory.setFieldValue(value);
                            detailHistory.setCreateTime(createTime);
                            detailHistory.setCreateUser(createUser);
                            detailHistory.setId(id);
                            detailHistory.setProjectNo(projectNo);
                            result.add(detailHistory);
                        }
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            });
            return result;
        }

        public void createUserFormDetailHistory(FormDetailUser oldFormDetail, FormDetailUser newFormDetail) {
            FormDetailUser formDetailUser = new FormDetailUser();
            Class<?> clazz = oldFormDetail.getClass();
            Field[] fields = clazz.getDeclaredFields();
            for (Field f : fields) {
                try {
                    if (f.getName().equals("serialVersionUID"))
                        continue;

                    PropertyDescriptor pd = new PropertyDescriptor(f.getName(), clazz);
                    ApiModelProperty annotation = f.getAnnotation(ApiModelProperty.class);
                    Method setMethod = pd.getWriteMethod();
                    Method getMethod = pd.getReadMethod();
                    String oldValue = getMethod.invoke(oldFormDetail) + "";
                    String newValue = getMethod.invoke(newFormDetail) + "";

                    if (oldValue.equals("") || oldValue.equals("null"))
                        oldValue = "空";
                    if (newValue.equals("") || newValue.equals("null"))
                        newValue = "空";

                    if (oldValue.equals(newValue))
                        continue;

                    if (annotation.value().endsWith("是否知晓贷款")){
                        if (newValue.equals("1")){
                            newValue="是";
                        }else if (newValue.equals("0")){
                            newValue="否";
                        }
                        if (oldValue.equals("1")){
                            oldValue="是";
                        }else if (oldValue.equals("0")){
                            oldValue="否";
                        }
                    }
                    if (f.getType().toString().toUpperCase().contains("STRING"))
                        setMethod.invoke(formDetailUser, oldValue + "->" + newValue);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }

            FormDetailUserHistory formDetailUserHistory = new FormDetailUserHistory();
            BeanUtil.copyProperty(formDetailUser, formDetailUserHistory);
            formDetailUserHistory.setProjectId(newFormDetail.getProjectId());
            formDetailUserHistory.setProjectNo(newFormDetail.getProjectNo());
            formDetailUserHistory.setOrgId(newFormDetail.getOrgId());
            formDetailUserHistory.setHeadOrgId(newFormDetail.getHeadOrgId());
            detailUserHistoryMapper.insertSelective(formDetailUserHistory);
        }
    }

    @Service
    public class CarFormDetailHistoryService extends AbstractService<FormDetailCarHistory, FormDetailCarHistoryExample> {

        public FormDetailCarHistory getCarFormDetailHistory(String projectNo) {
            FormDetailCarHistoryExample example = new FormDetailCarHistoryExample();
            example.createCriteria().andProjectNoEqualTo(projectNo);
            return Utils.getFirst(this.query(example));
        }

        public void createCarFormDetailHistory(ProjectForm form) {
            FormDetailCarHistory formDetail = null;
            if (StringUtils.isEmpty(form.getProjectForm()) || form.getProjectForm().equals("[]")) {
                return;
            } else {
                Map<String, String> map = FormParser.parse(form.getProjectForm());
                formDetail = MapUtil.mapToObject(map, FormDetailCarHistory.class);
            }
            formDetail.setProjectNo(form.getProjectNo());
            detailCarHistoryMapper.insertSelective(formDetail);
        }

    }

    @Service
    public class FormFieldService extends AbstractService<FormField, FormFieldExample> {
        String[][] fields = {
//                {"姓名", "b001", "getB001"},
                {"证件号码", "b005", "getB005"}, {"手机号码", "b016", "getB016"}, {"住宅电话", "b025", "getB025"},
                {"qq账号", "b033", "getB033"}, {"电子邮箱", "b016", "getB034"}, {"微信账号", "b035", "getB035"},
                {"单位全称", "c001", "getC001"}, {"单位地址", "c002", "getC002"}, {"单位电话", "c005", "getC005"},
                {"出租方电话", "d018", "getD018"}, {"房产编号", "e005", "getE005"},
                {"联系人1手机", "pa003", "getPa003"}, {"联系人1固话", "pa004", "getPa004"}, {"联系人1单位", "pa007", "getPa007"}, {"联系人1单位地址", "pa008", "getPa008"},
                {"联系人2手机", "pb003", "getPb003"}, {"联系人2固话", "pb004", "getPb004"}, {"联系人2单位", "pb007", "getPb007"}, {"联系人2单位地址", "pb008", "getPb008"},
                {"联系人3手机", "pc003", "getPc003"}, {"联系人3固话", "pc004", "getPc004"}, {"联系人3单位", "pc007", "getPc007"}, {"联系人3单位地址", "pc008", "getPc008"},
                {"联系人4手机", "pd003", "getPd003"}, {"联系人4固话", "pd004", "getPd004"}, {"联系人4单位", "pd007", "getPd007"}, {"联系人4单位地址", "pd008", "getPd008"}
        };

        public void createUserFormField(FormDetailUser formDetailUser) {
            FormField formField = new FormField();
            formField.setFormType(FormType.FORM_USER.key());
            formField.setProjectId(formDetailUser.getProjectId());
            formField.setProjectNo(formDetailUser.getProjectNo());

            Arrays.stream(fields).forEach(field -> {
                formField.setFieldName(field[0]);
                formField.setFieldKey(field[1]);
                formField.setFieldValue(ReflectUtil.invoke(formDetailUser, FormDetailUser.class, field[2]));
                create(formField);
            });
        }

        public void createCarFormField(FormDetailCar formDetailCar) {

        }

        public void deleteUserFormField(String projectNo) {
            FormFieldExample example = new FormFieldExample();
            example.createCriteria().andProjectNoEqualTo(projectNo);
            this.deleteByExample(example);
        }


    }


}
