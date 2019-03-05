package com.starsgroupchina.credit.server.controller.project;

import com.google.common.collect.Lists;
import com.starsgroupchina.common.response.SimpleResponse;
import com.starsgroupchina.credit.bean.extend.ProjectRiskDeclareRecordExtend;
import com.starsgroupchina.credit.bean.model.Member;
import com.starsgroupchina.credit.bean.model.ProjectRiskDeclareRecordExample;
import com.starsgroupchina.credit.server.service.project.RiskDeclareRecordService;
import com.starsgroupchina.credit.server.service.system.MemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
 * @Author: QinHaoHao
 * @Description:
 * @Date: Created in 16:30 2018/8/17
 * @Modifed By:
 */
@Slf4j
@RestController
@Api(tags = "CREDIT-SWAGGER41", description = "【项目】 - 项目风险扭转-RiskDeclareRecordController")
@RequestMapping(value = "/project/declare/record", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class RiskDeclareRecordController {

    @Autowired
    private RiskDeclareRecordService riskDeclareRecordService;

    @Autowired
    private MemberService memberService;

    /**
     * 根据申报编号获取申报监控
     */
    @ApiOperation("根据申报编号获取申报监控")
    @RequestMapping(value = "/{declareNo}", method = RequestMethod.GET)
    public SimpleResponse<List<ProjectRiskDeclareRecordExtend>> getRecordForDeclareNo(@PathVariable("declareNo") String declareNo) {
        ProjectRiskDeclareRecordExample example = new ProjectRiskDeclareRecordExample();
        example.createCriteria().andDeclareNoEqualTo(declareNo);
        example.setOrderByClause("create_time  desc");
        List<ProjectRiskDeclareRecordExtend> result = Lists.newArrayList();
        getResult(example, result);
        return SimpleResponse.success(result);
    }

    @ApiOperation("根据进件编号获取申报监控")
    @RequestMapping(value = "/{projectNo}/{source}", method = RequestMethod.GET)
    public SimpleResponse<List<ProjectRiskDeclareRecordExtend>> getRecordForProjectNo(@PathVariable("projectNo") String projectNo, @ApiParam("来源(1、范本导入，2、直接申报，3、审核申报，4、复核申报)") @PathVariable("source") Short source) {
        ProjectRiskDeclareRecordExample example = new ProjectRiskDeclareRecordExample();
        example.createCriteria().andProjectNoEqualTo(projectNo).andDeclareSourceEqualTo(source);
        example.setOrderByClause("create_time  desc");
        List<ProjectRiskDeclareRecordExtend> result = Lists.newArrayList();
        getResult(example, result);
        return SimpleResponse.success(result);
    }

    private void getResult(ProjectRiskDeclareRecordExample example, List<ProjectRiskDeclareRecordExtend> result) {
        Optional.ofNullable(riskDeclareRecordService.query(example)).ifPresent(recordStream ->
                recordStream.forEach(record -> {
                    ProjectRiskDeclareRecordExtend recordExtend = new ProjectRiskDeclareRecordExtend(record);
                    Member member = memberService.getById(record.getCreateUserId());
                    recordExtend.setMember(member);
                    result.add(recordExtend);
                })
        );
    }
}
