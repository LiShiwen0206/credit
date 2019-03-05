package com.starsgroupchina.credit.server;

import com.starsgroupchina.common.Config;
import com.starsgroupchina.credit.bean.mapper.ConfMapper;
import com.starsgroupchina.credit.bean.model.Conf;
import com.starsgroupchina.credit.bean.model.ConfExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by zf on 2016-9-29.
 */
@Component
public class ConfSchedule {

    @Autowired
    private ConfMapper confMapper;

    @Scheduled(fixedDelay = 60 * 1000)
    public void run() {
        ConfExample example = new ConfExample();
        example.createCriteria().andConfKeyIsNotNull();
        Map<String, String> map = confMapper.selectByExample(example).stream()
                .collect(Collectors.toMap(Conf::getConfKey, Conf::getConfValue));
        Config.reset(map);
    }
}
