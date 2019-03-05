package com.starsgroupchina.credit.bean.extend;

import com.starsgroupchina.common.utils.BeanUtil;
import com.starsgroupchina.credit.bean.model.WebsiteAudit;
import com.starsgroupchina.credit.bean.model.WebsiteAuditPic;
import lombok.Data;

import java.util.List;

@Data
public class WebsiteAuditExtend extends WebsiteAudit{

    private List<WebsiteAuditPic> websiteAuditPic;

    public WebsiteAuditExtend() {
    }

    public WebsiteAuditExtend(WebsiteAudit websiteAudit) {
        BeanUtil.copyProperty(websiteAudit,this);
    }
}
