package com.starsgroupchina.credit.server.service;

import com.starsgroupchina.common.base.AbstractService;
import com.starsgroupchina.credit.bean.model.NumberRule;
import com.starsgroupchina.credit.bean.model.NumberRuleExample;
import com.starsgroupchina.credit.server.conf.RedisConf;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.concurrent.TimeUnit;

/**
 * 编号规则
 */
@Slf4j
@Service
public class NumberService extends AbstractService<NumberRule,NumberRuleExample> {

    @Resource(name = RedisConf.REDIS_TEMPLATE)
    private RedisOperations redisOperations;

    public String getNumber(NumberRule numberRule,int step){
        String key = "number:"+numberRule.getOrgId() + numberRule.getNoName();
        Boolean isRun = redisOperations.opsForValue().setIfAbsent(key, "run");
        if (!isRun) {
            try {
                Thread.sleep(5000);
                return getNumber(numberRule,step);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        redisOperations.expire(key, 10, TimeUnit.SECONDS);
        boolean isFirst = false;
        String number="";
        String currentNumber = numberRule.getCurrentNumber();
        String noPrefix = numberRule.getNoPrefix();
        Integer noNumberLength = numberRule.getNoNumberLength();
        int currentDate=0;
        if(StringUtils.isEmpty(currentNumber)){
            isFirst=true;//是否第一次生成规则，还没有初始值
        }else {
            currentDate = Integer.valueOf(currentNumber);
        };
        currentNumber="";
        for(int i=0;i<noNumberLength;i++){
            currentNumber+=0;
        }
        String now = LocalDate.now().toString().replace("-","");
        String currentDateValue = numberRule.getCurrentDateValue();
        if(now.equals(currentDateValue)&&isFirst==false){
            currentDate+=1;
            currentNumber+=currentDate;
            int truncationIndex = currentNumber.length() - noNumberLength;//多余的长度
            currentNumber = currentNumber.substring(truncationIndex,currentNumber.length());//去掉多余的0
            number=noPrefix+currentDateValue+currentNumber;
            numberRule.setCurrentNumber(currentNumber);
        }else {
            currentNumber+=numberRule.getNoNumberStart();
            int truncationIndex = currentNumber.length() - noNumberLength;  //多余的长度
            currentNumber = currentNumber.substring(truncationIndex, currentNumber.length());//去掉多余的0
            number = noPrefix+now+currentNumber;
            numberRule.setCurrentDateValue(now);
            numberRule.setCurrentNumber(currentNumber);
        }
        if (step>0) {
            currentDate = Integer.valueOf(currentNumber) + step;
            currentNumber="";
            for(int i=0;i<noNumberLength;i++){
                currentNumber+=0;
            }
            currentNumber+= currentDate;
            int truncationIndex = currentNumber.length() - noNumberLength;//多余的长度
            currentNumber = currentNumber.substring(truncationIndex,currentNumber.length());//去掉多余的0
            numberRule.setCurrentNumber(currentNumber);
        }
        update(numberRule);
        redisOperations.delete(key);
        return number;
    }
}
