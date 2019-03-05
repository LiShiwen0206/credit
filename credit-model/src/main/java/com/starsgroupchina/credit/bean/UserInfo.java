package com.starsgroupchina.credit.bean;

import com.starsgroupchina.credit.bean.model.FormDetailUser;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Optional;

@Data
@ApiModel
public class UserInfo implements Serializable {

    @ApiModelProperty("id")
    private Integer id;

    @ApiModelProperty("project_no")
    private String projectNo;

    @ApiModelProperty("name")
    private String name;

    @ApiModelProperty("性别")
    private String gender;

    @ApiModelProperty("身份证")
    private String idCard;

    @ApiModelProperty("年龄")
    private String age;

    @ApiModelProperty("学历")
    private String education;

    @ApiModelProperty("手机号")
    private String phone;

    @ApiModelProperty("座机")
    private String tel;

    @ApiModelProperty("住址")
    private String familyAddress;

    @ApiModelProperty("户籍地")
    private String domicileAddress;

    @ApiModelProperty("籍贯")
    private String nativePlace;

    @ApiModelProperty("机构ID")
    private Integer orgId;

    @ApiModelProperty("总公司(机构)ID")
    private Integer headOrgId;

    @ApiModelProperty("婚姻状态")
    private String maritalStatus;

    @ApiModelProperty("公司名")
    private String companyName;

    @ApiModelProperty("公司地址")
    private String companyAddress;

    @ApiModelProperty("公司性质")
    private String companyType;

    @ApiModelProperty("单位电话")
    private String companyPhone;

    @ApiModelProperty("公司注册号")
    private String companyRegister;

    @ApiModelProperty("公司职位")
    private String companyJob;

    @ApiModelProperty("居住城市")
    private String residentialCity;

    @ApiModelProperty("居住类型")
    private String residentialType;

    @ApiModelProperty("工作城市")
    private String workCity;

    @ApiModelProperty("房产地址")
    private String houseAddress;

    @ApiModelProperty("月收入")
    private String monthIncome;

    @ApiModelProperty("配偶身份证号")
    private String spouseIdCard;

    @ApiModelProperty("配偶电话号码")
    private String spousePhone;

    @ApiModelProperty("配偶姓名")
    private String spouseName;

    @ApiModelProperty("联系人电话号码：/号隔开")
    private String relationPhone;

    private static final long serialVersionUID = 1L;

    public UserInfo() {
    }

    public UserInfo(FormDetailUser detail) {
        if (detail == null) return;
        this.projectNo = detail.getProjectNo();
        this.maritalStatus = detail.getB017();
        this.companyName = detail.getC001();
        this.companyAddress = detail.getC002();
        this.residentialCity = detail.getB024();
        this.residentialType = detail.getB027();
        this.workCity = detail.getC004();
        this.companyPhone = detail.getC005();
        this.companyJob = detail.getC010();
        this.companyType = detail.getC007();
        this.monthIncome = detail.getC019();
        this.name = detail.getB001();
        this.gender = detail.getB002();
        this.age = detail.getB003();
        this.idCard = detail.getB005();
        this.education = detail.getB014();
        this.phone = detail.getB016();
        this.tel = detail.getB025();
        this.orgId = detail.getOrgId();
        this.headOrgId = detail.getHeadOrgId();

        this.familyAddress = Optional.ofNullable(detail.getB022()).orElse("")
                .replace("[", "").replace("]", "").replace(",", "").replace(" ", "");
        this.domicileAddress = Optional.ofNullable(detail.getB010()).orElse("")
                .replace("[", "").replace("]", "").replace(",", "").replace(" ", "");
        this.spouseName = detail.getN001();
        this.spousePhone = detail.getN002();
        this.spouseIdCard = detail.getN003();
        this.nativePlace = detail.getB012();
        this.companyRegister = detail.getD007();
    }


}