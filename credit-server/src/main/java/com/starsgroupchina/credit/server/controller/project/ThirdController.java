package com.starsgroupchina.credit.server.controller.project;

import com.starsgroupchina.common.response.SimpleResponse;
import com.starsgroupchina.credit.bean.third.*;
import com.starsgroupchina.credit.server.client.wrapper.DfCreditWrapper;
import com.starsgroupchina.credit.server.client.wrapper.QhzxCreditWrapper;
import com.starsgroupchina.credit.server.client.wrapper.TdCreditWrapper;
import com.starsgroupchina.credit.server.service.third.ThirdCreditResultService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhangfeng on 2018/6/8
 */
@Slf4j
@RestController
@Api(tags = "CREDIT-SWAGGER3",description = "第三方征信 - ThirdController" )
@RequestMapping(value = "/third", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class ThirdController {

    /**
     * 是否查询过第三方征信
     */
    private static final String isQueryThird = "isQueryThird";

    private static final String data = "record";
    @Autowired
    private DfCreditWrapper dfCreditWrapper;

    @Autowired
    private QhzxCreditWrapper qhzxCreditWrapper;

    @Autowired
    private TdCreditWrapper tdCreditWrapper;

    @Autowired
    private ThirdCreditResultService thirdCreditResultService;
    /*******************************************************/
    /****************   df    field   *****************/
    /*******************************************************/

    @ApiOperation("更新大蜂数据")
    @RequestMapping(value = "/df",method = RequestMethod.GET)
    public SimpleResponse<Map<String,Object>> getDfCredit(ThirdRequestParam thirdRequestParam){
        Map<String,Object> map = new HashMap<>();
        DfRespsonseDataExtend dfRepsonseData = thirdCreditResultService.queryDFClear(thirdRequestParam.getProjectNo());
       if (dfRepsonseData==null){
           dfCreditWrapper.queryReport(thirdRequestParam);
           map.put(data,null);
           map.put("createTime",null);
       }else {
           map.put(data, dfRepsonseData.getDfRepsonseData());
           map.put("createTime",dfRepsonseData.getCreateTime());
       }
        //连续调用第三方时，通过锁控制
        return SimpleResponse.success(map);
    }

    @ApiOperation("根据项目id获取大蜂征信结果")
    @RequestMapping(value = "/df/{projectNo}",method = RequestMethod.GET)
    public SimpleResponse<Map<String,Object>> queryDfCredit(@PathVariable("projectNo") String projectNo){
        Map<String,Object> map = new HashMap<>();
        map.put(isQueryThird,true);
        DfRespsonseDataExtend dfRepsonseData = thirdCreditResultService.queryDF(projectNo);
        if(dfRepsonseData==null){
            return SimpleResponse.success(map);
        }
        map.put(data,dfRepsonseData.getDfRepsonseData());
        map.put("createTime",dfRepsonseData.getCreateTime());
        return SimpleResponse.success(map);
    }
    /*******************************************************/
    /****************   qhzx    field   *****************/
    /*******************************************************/
    @ApiOperation("更新前海征信")
    @RequestMapping(value = "/qhzx",method = RequestMethod.GET)
    public SimpleResponse<ThirdCreditQhzxExtend> getQhzxCredit(ThirdRequestParam thirdRequestParam){
        //是否已经查询过数据，是，则直接返回
        List<ThirdCreditQhzx8036> list8036 = thirdCreditResultService.queryQhzx8036Clear(thirdRequestParam.getProjectNo());
        List<ThirdCreditQhzx8107> list8107 = thirdCreditResultService.queryQhzx8107Clear(thirdRequestParam.getProjectNo());
        if (CollectionUtils.isEmpty(list8036)) {
            qhzxCreditWrapper.MSC8036(thirdRequestParam);
        }
        if (CollectionUtils.isEmpty(list8107)) {
            qhzxCreditWrapper.MSC8107(thirdRequestParam);
        }
        //前海征信一鉴通查询,同步接口，直接再次查询库
        ThirdCreditQhzxExtend data = queryQhzxCredit(thirdRequestParam.getProjectNo()).getData();
        return SimpleResponse.success(data);
    }

    @ApiOperation("根据项目id获取前海征信结果")
    @RequestMapping(value = "/qhzx/{projectNo}",method = RequestMethod.GET)
    public SimpleResponse<ThirdCreditQhzxExtend> queryQhzxCredit(@PathVariable("projectNo") String projectNo){
        ThirdCreditQhzxExtend thirdCreditQhzxExtend = new ThirdCreditQhzxExtend();
        List<ThirdCreditQhzx8036> list8036 = thirdCreditResultService.queryQhzx8036(projectNo);
        List<ThirdCreditQhzx8107> list8107 = thirdCreditResultService.queryQhzx8107(projectNo);
        thirdCreditQhzxExtend.setThirdCreditQhzx8036(list8036);
        thirdCreditQhzxExtend.setThirdCreditQhzx8107(list8107);
        return SimpleResponse.success(thirdCreditQhzxExtend);
    }

    /*******************************************************/
    /****************   td    field   *****************/
    /*******************************************************/

    @ApiOperation("更新同盾")
    @RequestMapping(value = "/td",method = RequestMethod.GET)
    public SimpleResponse<ThirdCreditTdReport> getTdCredit(ThirdRequestParam thirdRequestParam){
        log.info("调用同盾征信接口");
        ThirdCreditTdReport thirdCreditTdReport = thirdCreditResultService.queryTDClear(thirdRequestParam.getProjectNo());
        if(thirdCreditTdReport==null) {
            tdCreditWrapper.queryAndSaveReport(thirdRequestParam);
        }
        return SimpleResponse.success(thirdCreditTdReport);
    }

    @ApiOperation("根据项目id获取同盾征信结果")
    @RequestMapping(value = "/td/{projectNo}",method = RequestMethod.GET)
    public SimpleResponse<ThirdCreditTdReport> queryTdCredit(@PathVariable("projectNo") String projectNo){
        ThirdCreditTdReport thirdCreditTdReport = thirdCreditResultService.queryTD(projectNo);
        if(thirdCreditTdReport==null) {
            thirdCreditTdReport = new ThirdCreditTdReport();
            //表示没有调用过第三方征信
            thirdCreditTdReport.setIsQueryThird(false);
        }
        return SimpleResponse.success(thirdCreditTdReport);
    }
}
