package com.starsgroupchina.credit.server.controller.system;

import com.starsgroupchina.common.annotation.AuthIgnore;
import com.starsgroupchina.common.response.ListResponse;
import com.starsgroupchina.common.response.SimpleResponse;
import com.starsgroupchina.credit.bean.enums.MessageType;
import com.starsgroupchina.credit.bean.model.Message;
import com.starsgroupchina.credit.bean.model.MessageExample;
import com.starsgroupchina.credit.server.service.system.MessageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * @Author: QinHaoHao
 * @Description:
 * @Date: Created in 14:49 2018/7/30
 * @Modifed By:
 */
@Slf4j
@RestController
@Api(tags = "CREDIT-SWAGGER39", description = "消息管理 - MessageController")
@RequestMapping(value = "/message", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class MessageController {


    @Autowired
    private MessageService messageService;

    @ApiOperation("根据用户id取消息列表:type:0,未读 1、已读")
    @RequestMapping(value = "/{memberId}", method = RequestMethod.GET)
    @AuthIgnore
    public ListResponse<Message> query(@RequestParam(value = "index", defaultValue = "1") int index,
                                       @RequestParam(value = "limit", defaultValue = "20") int limit,
                                       @PathVariable("memberId") String memberId) {

        if (StringUtils.isEmpty(memberId)||"null".equals(memberId)){
            return ListResponse.success(new ArrayList<>(), 0, index, limit);
        }
        MessageExample example = new MessageExample();
        MessageExample.Criteria criteria = example.createCriteria();
        criteria.andReceiveUserIdEqualTo(Integer.valueOf(memberId));
        long count = messageService.count(example);
        example.setOrderByClause("create_time desc");
        example.setOffset((index - 1) * limit);
        example.setLimit(limit);
        List<Message> result = messageService.query(example).collect(toList());
        return ListResponse.success(result, count, index, limit);
    }

    @ApiOperation("根据用户id获取未读消息数量")
    @RequestMapping(value = "/{memberId}/unread", method = RequestMethod.GET)
    @AuthIgnore
    public SimpleResponse<Long> getUnreadMessage(@PathVariable("memberId") Integer memberId) {
        MessageExample example = new MessageExample();
        MessageExample.Criteria criteria = example.createCriteria();
        criteria.andReceiveUserIdEqualTo(memberId).andTypeEqualTo(MessageType.UNREAD.key());
        long result = messageService.count(example);
        return SimpleResponse.success(result);
    }

    @ApiOperation("更新消息")
    @RequestMapping(value = "", method = RequestMethod.PUT)
    @Transactional
    public SimpleResponse<List<Message>> update(@RequestBody List<Message> messages) {
        List<Message> result = messageService.update(messages);
        return SimpleResponse.success(result);
    }

}
