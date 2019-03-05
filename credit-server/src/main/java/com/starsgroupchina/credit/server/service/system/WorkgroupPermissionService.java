package com.starsgroupchina.credit.server.service.system;

import com.starsgroupchina.common.base.AbstractService;
import com.starsgroupchina.credit.bean.model.VWorkgroupPermission;
import com.starsgroupchina.credit.bean.model.VWorkgroupPermissionExample;
import com.starsgroupchina.credit.bean.model.WorkgroupPermission;
import com.starsgroupchina.credit.bean.model.WorkgroupPermissionExample;
import org.springframework.stereotype.Service;

/**
 * @describe :
 * @class_name : WorkgroupPermissionService
 * @author : lishiwen
 * @date : 2018/12/7
 */
@Service
public class WorkgroupPermissionService extends AbstractService<WorkgroupPermission, WorkgroupPermissionExample>{

    @Service
    public class VWorkgroupPermissionService extends AbstractService<VWorkgroupPermission, VWorkgroupPermissionExample> {

    }
}
