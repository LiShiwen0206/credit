package com.starsgroupchina.credit.server.controller.project;

import com.google.common.collect.Lists;
import com.starsgroupchina.common.response.SimpleResponse;
import com.starsgroupchina.credit.bean.extend.RecordExtend;
import com.starsgroupchina.credit.bean.model.Member;
import com.starsgroupchina.credit.bean.model.ProjectRecordExample;
import com.starsgroupchina.credit.server.service.project.RecordService;
import com.starsgroupchina.credit.server.service.system.MemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

/**
 * Created by zhangfeng on 2018-6-13.
 */
@Slf4j
@RestController
@Api(tags = "CREDIT-SWAGGER15", description = "【项目】 - 流转记录 - RecordController")
@RequestMapping(value = "/project/record", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class RecordController {

    @Autowired
    private RecordService recordService;
    @Autowired
    private MemberService memberService;

    @ApiOperation("项目流转记录列表")
    @RequestMapping(value = "/{projectNo}", method = RequestMethod.GET)
    public SimpleResponse<List<RecordExtend>> query(@PathVariable("projectNo") String projectNo) {
        List<RecordExtend> result = Lists.newArrayList();
        ProjectRecordExample example = new ProjectRecordExample();
        example.createCriteria().andProjectNoEqualTo(projectNo);
        example.setOrderByClause(" id  desc");
        Optional.ofNullable(recordService.query(example)).ifPresent(recordStream ->
                recordStream.forEach(record -> {
                    RecordExtend recordExtend = new RecordExtend(record);
                    Member member = memberService.getById(record.getAuditMemberId());
                    recordExtend.setMember(member);
                    result.add(recordExtend);
                })
        );
        return SimpleResponse.success(result);
    }

}
