package com.starsgroupchina.credit.server.conf;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author: QinHaoHao
 * @Description:
 * @Date: Created in 10:16 2018/7/11
 * @Modifed By:
 */
@Component
@ConfigurationProperties(prefix = "td")
@Data
public class TdConfig {

    private String submitUrl;

    private String queryUrl;

    private String partnerKey;

}
