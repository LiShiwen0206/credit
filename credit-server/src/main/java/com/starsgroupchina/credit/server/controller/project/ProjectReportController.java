package com.starsgroupchina.credit.server.controller.project;

import com.starsgroupchina.common.response.SimpleResponse;
import com.starsgroupchina.credit.bean.extend.ReportExtend;
import com.starsgroupchina.credit.server.service.project.ReportService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: QinHaoHao
 * @Description: 风控报告controller
 * @Date: Created in 13:59 2018/7/3
 * @Modifed By:
 */
@Slf4j
@RestController
@Api(tags = "CREDIT-SWAGGER1", description = "【项目】 - 风控报告接口-ReportController")
@RequestMapping(value = "/project/report", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class ProjectReportController {

    @Autowired
    private ReportService reportService;

    @ApiOperation("获取风控报告")
    @RequestMapping(value = "/{projectNo}", method = RequestMethod.GET)
    public SimpleResponse<ReportExtend> getReport(@PathVariable("projectNo") String projectNo) {
        ReportExtend reportExtend = reportService.getReport(projectNo);
        if (reportExtend == null) {
            return SimpleResponse.success(null);
        }
        return SimpleResponse.success(reportExtend);
    }
}
