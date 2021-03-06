package com.starsgroupchina.credit.bean.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.starsgroupchina.common.base.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

@ApiModel
public class NumberRule extends BaseModel implements Serializable {
    @ApiModelProperty("id")
    private Integer id;

    @ApiModelProperty("机构ID")
    private Integer orgId;

    @ApiModelProperty("编号名称")
    private String noName;

    @ApiModelProperty("编号前缀")
    private String noPrefix;

    @ApiModelProperty("日期格式")
    private String noDateFormat;

    @ApiModelProperty("序号起始值")
    private Integer noNumberStart;

    @ApiModelProperty("序号长度")
    private Integer noNumberLength;

    @ApiModelProperty("当前日期值")
    private String currentDateValue;

    @ApiModelProperty("当前序号")
    private String currentNumber;

    @ApiModelProperty("状态：0正常，-1删除")
    private Short status;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table number_rule
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

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    public String getNoName() {
        return noName;
    }

    public void setNoName(String noName) {
        this.noName = noName == null ? null : noName.trim();
    }

    public String getNoPrefix() {
        return noPrefix;
    }

    public void setNoPrefix(String noPrefix) {
        this.noPrefix = noPrefix == null ? null : noPrefix.trim();
    }

    public String getNoDateFormat() {
        return noDateFormat;
    }

    public void setNoDateFormat(String noDateFormat) {
        this.noDateFormat = noDateFormat == null ? null : noDateFormat.trim();
    }

    public Integer getNoNumberStart() {
        return noNumberStart;
    }

    public void setNoNumberStart(Integer noNumberStart) {
        this.noNumberStart = noNumberStart;
    }

    public Integer getNoNumberLength() {
        return noNumberLength;
    }

    public void setNoNumberLength(Integer noNumberLength) {
        this.noNumberLength = noNumberLength;
    }

    public String getCurrentDateValue() {
        return currentDateValue;
    }

    public void setCurrentDateValue(String currentDateValue) {
        this.currentDateValue = currentDateValue == null ? null : currentDateValue.trim();
    }

    public String getCurrentNumber() {
        return currentNumber;
    }

    public void setCurrentNumber(String currentNumber) {
        this.currentNumber = currentNumber == null ? null : currentNumber.trim();
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table number_rule
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
        NumberRule other = (NumberRule) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getOrgId() == null ? other.getOrgId() == null : this.getOrgId().equals(other.getOrgId()))
            && (this.getNoName() == null ? other.getNoName() == null : this.getNoName().equals(other.getNoName()))
            && (this.getNoPrefix() == null ? other.getNoPrefix() == null : this.getNoPrefix().equals(other.getNoPrefix()))
            && (this.getNoDateFormat() == null ? other.getNoDateFormat() == null : this.getNoDateFormat().equals(other.getNoDateFormat()))
            && (this.getNoNumberStart() == null ? other.getNoNumberStart() == null : this.getNoNumberStart().equals(other.getNoNumberStart()))
            && (this.getNoNumberLength() == null ? other.getNoNumberLength() == null : this.getNoNumberLength().equals(other.getNoNumberLength()))
            && (this.getCurrentDateValue() == null ? other.getCurrentDateValue() == null : this.getCurrentDateValue().equals(other.getCurrentDateValue()))
            && (this.getCurrentNumber() == null ? other.getCurrentNumber() == null : this.getCurrentNumber().equals(other.getCurrentNumber()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getCreateUserId() == null ? other.getCreateUserId() == null : this.getCreateUserId().equals(other.getCreateUserId()))
            && (this.getCreateUser() == null ? other.getCreateUser() == null : this.getCreateUser().equals(other.getCreateUser()))
            && (this.getModifyUserId() == null ? other.getModifyUserId() == null : this.getModifyUserId().equals(other.getModifyUserId()))
            && (this.getModifyTime() == null ? other.getModifyTime() == null : this.getModifyTime().equals(other.getModifyTime()))
            && (this.getModifyUser() == null ? other.getModifyUser() == null : this.getModifyUser().equals(other.getModifyUser()));
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table number_rule
     *
     * @mbg.generated Wed Dec 19 16:26:10 CST 2018
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getOrgId() == null) ? 0 : getOrgId().hashCode());
        result = prime * result + ((getNoName() == null) ? 0 : getNoName().hashCode());
        result = prime * result + ((getNoPrefix() == null) ? 0 : getNoPrefix().hashCode());
        result = prime * result + ((getNoDateFormat() == null) ? 0 : getNoDateFormat().hashCode());
        result = prime * result + ((getNoNumberStart() == null) ? 0 : getNoNumberStart().hashCode());
        result = prime * result + ((getNoNumberLength() == null) ? 0 : getNoNumberLength().hashCode());
        result = prime * result + ((getCurrentDateValue() == null) ? 0 : getCurrentDateValue().hashCode());
        result = prime * result + ((getCurrentNumber() == null) ? 0 : getCurrentNumber().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getCreateUserId() == null) ? 0 : getCreateUserId().hashCode());
        result = prime * result + ((getCreateUser() == null) ? 0 : getCreateUser().hashCode());
        result = prime * result + ((getModifyUserId() == null) ? 0 : getModifyUserId().hashCode());
        result = prime * result + ((getModifyTime() == null) ? 0 : getModifyTime().hashCode());
        result = prime * result + ((getModifyUser() == null) ? 0 : getModifyUser().hashCode());
        return result;
    }
}