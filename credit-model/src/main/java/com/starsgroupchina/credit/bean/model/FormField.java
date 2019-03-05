package com.starsgroupchina.credit.bean.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.starsgroupchina.common.base.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

@ApiModel
public class FormField extends BaseModel implements Serializable {
    @ApiModelProperty("id")
    private Integer id;

    @ApiModelProperty("project_id")
    private Integer projectId;

    @ApiModelProperty("project_no")
    private String projectNo;

    @ApiModelProperty("field_name")
    private String fieldName;

    @ApiModelProperty("field_key")
    private String fieldKey;

    @ApiModelProperty("field_value")
    private String fieldValue;

    @ApiModelProperty("表单类型:产品表单（FORM_PRODUCT）,用户表单（FORM_USER）,车辆表单（FORM_CAR）")
    private String formType;

    @ApiModelProperty("0,正常，-1删除")
    private Short status;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table form_field
     *
     * @mbg.generated Wed Dec 19 16:26:10 CST 2018
     */
    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getProjectNo() {
        return projectNo;
    }

    public void setProjectNo(String projectNo) {
        this.projectNo = projectNo == null ? null : projectNo.trim();
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName == null ? null : fieldName.trim();
    }

    public String getFieldKey() {
        return fieldKey;
    }

    public void setFieldKey(String fieldKey) {
        this.fieldKey = fieldKey == null ? null : fieldKey.trim();
    }

    public String getFieldValue() {
        return fieldValue;
    }

    public void setFieldValue(String fieldValue) {
        this.fieldValue = fieldValue == null ? null : fieldValue.trim();
    }

    public String getFormType() {
        return formType;
    }

    public void setFormType(String formType) {
        this.formType = formType == null ? null : formType.trim();
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_field
     *
     * @mbg.generated Wed Dec 19 16:26:10 CST 2018
     */
    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        FormField other = (FormField) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getProjectId() == null ? other.getProjectId() == null : this.getProjectId().equals(other.getProjectId()))
            && (this.getProjectNo() == null ? other.getProjectNo() == null : this.getProjectNo().equals(other.getProjectNo()))
            && (this.getFieldName() == null ? other.getFieldName() == null : this.getFieldName().equals(other.getFieldName()))
            && (this.getFieldKey() == null ? other.getFieldKey() == null : this.getFieldKey().equals(other.getFieldKey()))
            && (this.getFieldValue() == null ? other.getFieldValue() == null : this.getFieldValue().equals(other.getFieldValue()))
            && (this.getFormType() == null ? other.getFormType() == null : this.getFormType().equals(other.getFormType()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getCreateUserId() == null ? other.getCreateUserId() == null : this.getCreateUserId().equals(other.getCreateUserId()))
            && (this.getCreateUser() == null ? other.getCreateUser() == null : this.getCreateUser().equals(other.getCreateUser()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getModifyUserId() == null ? other.getModifyUserId() == null : this.getModifyUserId().equals(other.getModifyUserId()))
            && (this.getModifyTime() == null ? other.getModifyTime() == null : this.getModifyTime().equals(other.getModifyTime()))
            && (this.getModifyUser() == null ? other.getModifyUser() == null : this.getModifyUser().equals(other.getModifyUser()));
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_field
     *
     * @mbg.generated Wed Dec 19 16:26:10 CST 2018
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getProjectId() == null) ? 0 : getProjectId().hashCode());
        result = prime * result + ((getProjectNo() == null) ? 0 : getProjectNo().hashCode());
        result = prime * result + ((getFieldName() == null) ? 0 : getFieldName().hashCode());
        result = prime * result + ((getFieldKey() == null) ? 0 : getFieldKey().hashCode());
        result = prime * result + ((getFieldValue() == null) ? 0 : getFieldValue().hashCode());
        result = prime * result + ((getFormType() == null) ? 0 : getFormType().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getCreateUserId() == null) ? 0 : getCreateUserId().hashCode());
        result = prime * result + ((getCreateUser() == null) ? 0 : getCreateUser().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getModifyUserId() == null) ? 0 : getModifyUserId().hashCode());
        result = prime * result + ((getModifyTime() == null) ? 0 : getModifyTime().hashCode());
        result = prime * result + ((getModifyUser() == null) ? 0 : getModifyUser().hashCode());
        return result;
    }
}