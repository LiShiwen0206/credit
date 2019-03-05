package com.starsgroupchina.credit.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * Created by zhangfeng on 2018-5-13.
 */
@Data
@ApiModel
public class Field {

    @ApiModelProperty("id")
    private Integer fieldId;

    @ApiModelProperty("字段KEY")
    private String key;

    @ApiModelProperty("字段名称")
    private String name;

    @ApiModelProperty("字段值")
    private String value;

    @ApiModelProperty("操作符（中文）：大于，小于，等于...")
    private String operator;

    private List<Field> fields;

    public Field(Integer id, String key, String name) {
        this.fieldId = id;
        this.key = key;
        this.name = name;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Field) {
            Field field = (Field) obj;
            if (field.getKey().equals(this.getKey()))
                return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return  key.hashCode();

    }


}
