package com.starsgroupchina.credit.bean.extend;

import com.starsgroupchina.common.utils.BeanUtil;
import com.starsgroupchina.credit.bean.model.OrgFile;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Api
@Data
public class OrgFileExtend extends OrgFile {

    @ApiModelProperty("机构名称")
    private String orgName;

    @ApiModelProperty("文件列表")
    private List<FileResourceExtend> fileResourceList;

    public OrgFileExtend() {
    }

    public OrgFileExtend(OrgFile orgFile) {
        BeanUtil.copyProperty(orgFile,this);
    }
}
