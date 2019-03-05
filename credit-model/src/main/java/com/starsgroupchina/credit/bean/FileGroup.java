package com.starsgroupchina.credit.bean;

import com.starsgroupchina.credit.bean.extend.FileResourceExtend;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zhangfeng on 2018-5-22.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileGroup implements Serializable{

    @ApiModelProperty("文件大类  例：个人信息")
    private String category;

    private List<FileType> types;

    private static final long serialVersionUID = 1L;
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FileType implements Serializable{
        @ApiModelProperty("文件二级类目 例： 身份证")
        private String name;

        @ApiModelProperty("机构文件id")
        private Integer orgFileId;

        private static final long serialVersionUID = 1L;
        @ApiModelProperty("文件")
        private List<FileResourceExtend> flies;
    }

}

