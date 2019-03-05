package com.starsgroupchina.credit.bean.extend;

import com.starsgroupchina.common.utils.BeanUtil;
import com.starsgroupchina.credit.bean.model.FileResource;
import com.starsgroupchina.credit.bean.model.OrgFile;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel
@Data
public class FileResourceExtend extends FileResource{

    @ApiModelProperty("图片地址链接")
    private String fileUrl;

    @ApiModelProperty("机构文件")
    private OrgFile orgFile;
    public FileResourceExtend(FileResource fileResource) {
        BeanUtil.copyProperty(fileResource,this);
    }

    public FileResourceExtend() {
    }
}
