package com.starsgroupchina.credit.server.controller.project;

import com.starsgroupchina.common.response.SimpleResponse;
import com.starsgroupchina.common.utils.Utils;
import com.starsgroupchina.credit.bean.enums.CreditInfoType;
import com.starsgroupchina.credit.bean.extend.OrgFileExtend;
import com.starsgroupchina.credit.bean.model.CreditInfo;
import com.starsgroupchina.credit.bean.model.CreditInfoExample;
import com.starsgroupchina.credit.server.service.project.CreditInfoService;
import com.starsgroupchina.credit.server.service.project.FileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.stream.Collectors.groupingBy;

/**
 * Created by zhangfeng on 2018-6-14.
 */
@Slf4j
@RestController
@Api(tags = "CREDIT-SWAGGER12", description = "【项目】 - 征信信息CreditInfoController")
@RequestMapping(value = "/project/credit-info", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class CreditInfoController {

    @Autowired
    private CreditInfoService creditInfoService;

    @Autowired
    private FileService fileService;

    /**
     * 根据项目ID获取征信表单
     */
    @ApiOperation("根据项目编号 获取 表单")
    @RequestMapping(value = "/{projectNo}", method = RequestMethod.GET)
    public SimpleResponse<CreditInfo> getCreditForm(
            @PathVariable("projectNo") String projectNo,
            @ApiParam("征信表类型：1详版，2简版") @RequestParam(value = "type") Integer type) {
        CreditInfoExample example = new CreditInfoExample();
        example.createCriteria().andInfoTypeEqualTo(type).andProjectNoEqualTo(projectNo);

        Stream<CreditInfo> result = creditInfoService.query(example);
        CreditInfo creditInfo = Utils.getFirst(result);
        return SimpleResponse.success(creditInfo);
    }

//    /**
//     * 保存征信表单
//     */
//    @ApiOperation("创建表单（内部调用不对外）")
//    @RequestMapping(value = "", method = RequestMethod.POST)
//    public SimpleResponse<CreditInfo> createCreditForm(@RequestBody CreditInfo creditInfo) {
//        creditInfoService.create(creditInfo);
//        return SimpleResponse.success(creditInfo);
//    }

    @ApiOperation("修改表单")
    @RequestMapping(value = "", method = RequestMethod.PUT)
    @Transactional
    public SimpleResponse<CreditInfo> updateCreditForm(@RequestBody CreditInfo creditInfo) {
        if (creditInfo.getId() != null)
            creditInfoService.deleteById(creditInfo.getId());
        creditInfoService.create(creditInfo);
        return SimpleResponse.success(creditInfo);
    }

    @ApiOperation("获取详版简版征信表单")
    @RequestMapping(value = "/{projectNo}/{type}", method = RequestMethod.GET)
    public SimpleResponse<OrgFileExtend> getFile(@PathVariable("projectNo") String projectNo, @PathVariable("type") Integer type) {
        List<OrgFileExtend> files = fileService.getFiles(projectNo);
        Map<String, List<OrgFileExtend>> map = files.stream().collect(groupingBy(OrgFileExtend::getFileType));
        List<OrgFileExtend> list = map.get("信用证明");
        if (CollectionUtils.isNotEmpty(list)) {
            Map<String, List<OrgFileExtend>> resultMap = list.stream().collect(groupingBy(OrgFileExtend::getFileName));
            if (type.equals(CreditInfoType.CREDIT_DETAIL.key())) {
                List<OrgFileExtend> orgFileExtends = resultMap.get(CreditInfoType.CREDIT_DETAIL.value());
                if (CollectionUtils.isNotEmpty(orgFileExtends)) {
                    return SimpleResponse.success(orgFileExtends.get(0));
                } else {
                    return SimpleResponse.success();
                }
            } else {
                List<OrgFileExtend> orgFileExtends = resultMap.get(CreditInfoType.CREDIT_SIMPLE.value());
                if (CollectionUtils.isNotEmpty(orgFileExtends)) {
                    return SimpleResponse.success(orgFileExtends.get(0));
                } else {
                    return SimpleResponse.success();
                }
            }
        } else {
            return SimpleResponse.success();
        }
    }
}
