package com.starsgroupchina.credit.bean.extend;

import com.starsgroupchina.common.utils.BeanUtil;
import com.starsgroupchina.credit.bean.model.ProjectFileInspect;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class FileInspectExtend extends ProjectFileInspect {

    @ApiModelProperty("机构文件")
    private OrgFileExtend orgFileExtend;

    public FileInspectExtend(ProjectFileInspect authAttachment){
        BeanUtil.copyProperty(authAttachment,this);
    }

    public FileInspectExtend() {
    }
}
