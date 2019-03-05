package com.starsgroupchina.credit.server.service.project;

import com.starsgroupchina.credit.bean.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by zhangfeng on 2018/7/5
 */
@Slf4j
@Service
public class UserService {

    @Autowired
    private FormService.UserFormDetailService userFormService;

    public UserInfo getUserInfo(String projectNo) {
        return new UserInfo(userFormService.getUserFormDetail(projectNo));
    }

}
