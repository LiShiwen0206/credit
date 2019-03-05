package com.starsgroupchina.credit.server.conf;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 加密配置信息
 *
 * @author wangpengfei
 * @date 2018/8/30 19:04
 */
@Component
@ConfigurationProperties(prefix = "encrypt")
@Data
public class EncryptConfig {

    /**
     * 盐值
     */
    private String secretKey;

    /**
     * 需要加密的model的相关字段
     * 例: <'com.starsgroupchina.credit.bean.model.Org', 'name,layer'>
     */
    private Map<String, String> model;

}
