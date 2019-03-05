package com.starsgroupchina.credit.bean.extend;

import com.starsgroupchina.common.utils.BeanUtil;
import com.starsgroupchina.credit.bean.model.OrgFile;
import com.starsgroupchina.credit.bean.model.ProductFile;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by zhangfeng on 2018-5-24.
 */
@Data
@ApiModel
public class ProductFileExtend extends ProductFile {

    @ApiModelProperty("机构文件")
    private OrgFile orgFile;

    @ApiModelProperty("字段key")
    private String fileKey;

    public ProductFileExtend(ProductFile productFile) {
        BeanUtil.copyProperty(productFile,this);
    }

//    public ProductFile getProductFile() {
//        ProductFile file = new ProductFile();
//        BeanUtil.copy(this, file);
//        return file;
//    }

    public ProductFileExtend() {

    }
}
