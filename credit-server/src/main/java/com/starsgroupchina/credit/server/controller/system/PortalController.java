package com.starsgroupchina.credit.server.controller.system;

import com.starsgroupchina.common.response.SimpleResponse;
import com.starsgroupchina.credit.bean.extend.PortalSummaryExtend;
import com.starsgroupchina.credit.bean.model.BlacklistExample;
import com.starsgroupchina.credit.bean.model.OrgExample;
import com.starsgroupchina.credit.bean.model.ProductExample;
import com.starsgroupchina.credit.server.service.BlacklistService;
import com.starsgroupchina.credit.server.service.ProductService;
import com.starsgroupchina.credit.server.service.system.OrgService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: QinHaoHao
 * @Description:
 * @Date: Created in 17:38 2018/9/13
 * @Modifed By:
 */

@RestController
@RequestMapping("/portal")
@Api(tags = "CREDIT-SWAGGER45", description = "【系统】 - potal页接口-PortalController")
public class PortalController {

    @Autowired
    private OrgService orgService;

    @Autowired
    private ProductService productService;

    @Autowired
    private BlacklistService blacklistService;

    @ApiOperation("获取统计信息")
    @GetMapping("/summary")
    public SimpleResponse<PortalSummaryExtend> getSummary() {
        OrgExample orgExample = new OrgExample();
        orgExample.createCriteria().andStatusEqualTo(Short.valueOf("0"));
        long orgNum = orgService.count(orgExample);
        ProductExample productExample = new ProductExample();
        productExample.setAdditionalWhere(productService.queryNewProduct.get());
        long todayProductNum = productService.count(productExample);
        productExample.setAdditionalWhere(productService.queryEffectiveProduct.get());
        long mothProductNum = productService.count(productExample);
        BlacklistExample blacklistExample = new BlacklistExample();
        blacklistExample.createCriteria().andStatusEqualTo(Short.valueOf("1"));
        long blackNum = blacklistService.count(blacklistExample);
        PortalSummaryExtend result = new PortalSummaryExtend(orgNum, todayProductNum, mothProductNum, blackNum);
        return SimpleResponse.success(result);
    }
}
