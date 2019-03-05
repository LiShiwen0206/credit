package com.starsgroupchina.credit.bean.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.starsgroupchina.common.base.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;

@ApiModel
public class Blacklist extends BaseModel implements Serializable {
    @ApiModelProperty("id")
    private Integer id;

    @ApiModelProperty("姓名")
    private String name;

    @ApiModelProperty("证件号")
    private String idCard;

    @ApiModelProperty("电话")
    private String tel;

    @ApiModelProperty("解除人")
    private String relieveUser;

    @ApiModelProperty("解除时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date relieveTime;

    @ApiModelProperty("用户类型  :个人 ;企业")
    private String userType;

    @ApiModelProperty("解除原因")
    private String relieveReason;

    @ApiModelProperty("提交人")
    private String submitter;

    @ApiModelProperty("提交原因")
    private String submitReason;

    @ApiModelProperty("0脱黑，1黑名单，-1删除")
    private Short status;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table blacklist
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard == null ? null : idCard.trim();
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel == null ? null : tel.trim();
    }

    public String getRelieveUser() {
        return relieveUser;
    }

    public void setRelieveUser(String relieveUser) {
        this.relieveUser = relieveUser == null ? null : relieveUser.trim();
    }

    public Date getRelieveTime() {
        return relieveTime;
    }

    public void setRelieveTime(Date relieveTime) {
        this.relieveTime = relieveTime;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType == null ? null : userType.trim();
    }

    public String getRelieveReason() {
        return relieveReason;
    }

    public void setRelieveReason(String relieveReason) {
        this.relieveReason = relieveReason == null ? null : relieveReason.trim();
    }

    public String getSubmitter() {
        return submitter;
    }

    public void setSubmitter(String submitter) {
        this.submitter = submitter == null ? null : submitter.trim();
    }

    public String getSubmitReason() {
        return submitReason;
    }

    public void setSubmitReason(String submitReason) {
        this.submitReason = submitReason == null ? null : submitReason.trim();
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table blacklist
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
        Blacklist other = (Blacklist) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getIdCard() == null ? other.getIdCard() == null : this.getIdCard().equals(other.getIdCard()))
            && (this.getTel() == null ? other.getTel() == null : this.getTel().equals(other.getTel()))
            && (this.getRelieveUser() == null ? other.getRelieveUser() == null : this.getRelieveUser().equals(other.getRelieveUser()))
            && (this.getRelieveTime() == null ? other.getRelieveTime() == null : this.getRelieveTime().equals(other.getRelieveTime()))
            && (this.getUserType() == null ? other.getUserType() == null : this.getUserType().equals(other.getUserType()))
            && (this.getRelieveReason() == null ? other.getRelieveReason() == null : this.getRelieveReason().equals(other.getRelieveReason()))
            && (this.getSubmitter() == null ? other.getSubmitter() == null : this.getSubmitter().equals(other.getSubmitter()))
            && (this.getSubmitReason() == null ? other.getSubmitReason() == null : this.getSubmitReason().equals(other.getSubmitReason()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getCreateUser() == null ? other.getCreateUser() == null : this.getCreateUser().equals(other.getCreateUser()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getModifyTime() == null ? other.getModifyTime() == null : this.getModifyTime().equals(other.getModifyTime()))
            && (this.getModifyUser() == null ? other.getModifyUser() == null : this.getModifyUser().equals(other.getModifyUser()))
            && (this.getCreateUserId() == null ? other.getCreateUserId() == null : this.getCreateUserId().equals(other.getCreateUserId()))
            && (this.getModifyUserId() == null ? other.getModifyUserId() == null : this.getModifyUserId().equals(other.getModifyUserId()));
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table blacklist
     *
     * @mbg.generated Wed Dec 19 16:26:10 CST 2018
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getIdCard() == null) ? 0 : getIdCard().hashCode());
        result = prime * result + ((getTel() == null) ? 0 : getTel().hashCode());
        result = prime * result + ((getRelieveUser() == null) ? 0 : getRelieveUser().hashCode());
        result = prime * result + ((getRelieveTime() == null) ? 0 : getRelieveTime().hashCode());
        result = prime * result + ((getUserType() == null) ? 0 : getUserType().hashCode());
        result = prime * result + ((getRelieveReason() == null) ? 0 : getRelieveReason().hashCode());
        result = prime * result + ((getSubmitter() == null) ? 0 : getSubmitter().hashCode());
        result = prime * result + ((getSubmitReason() == null) ? 0 : getSubmitReason().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getCreateUser() == null) ? 0 : getCreateUser().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getModifyTime() == null) ? 0 : getModifyTime().hashCode());
        result = prime * result + ((getModifyUser() == null) ? 0 : getModifyUser().hashCode());
        result = prime * result + ((getCreateUserId() == null) ? 0 : getCreateUserId().hashCode());
        result = prime * result + ((getModifyUserId() == null) ? 0 : getModifyUserId().hashCode());
        return result;
    }
}