package com.starsgroupchina.credit.server.controller.project;

import com.starsgroupchina.common.response.SimpleResponse;
import com.starsgroupchina.credit.bean.DetailHistory;
import com.starsgroupchina.credit.server.service.project.FormService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;

/**
 * Created by zhangfeng on 2018/7/10
 */
@Slf4j
@RestController
@Api(tags = "CREDIT-SWAGGER24", description = "【项目】 - 修改历史 - HistoryController")
@RequestMapping(value = "/project/form", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class HistoryController {

    @Autowired
    private FormService.UserFormDetailHistoryService userFormHistoryService;

    @ApiOperation("获取表单修改历史")
    @GetMapping(value = "/{projectNo}/history")
    public SimpleResponse<Map<String, List<DetailHistory>>> getDetailHistory(@PathVariable("projectNo") String projectNo) {
        List<DetailHistory> historys = userFormHistoryService.getUserFormDetailHistorys(projectNo);
        Map<String, List<DetailHistory>> result = historys.stream().sorted().collect(groupingBy(DetailHistory::getFieldName));
        return SimpleResponse.success(result);
    }
}
