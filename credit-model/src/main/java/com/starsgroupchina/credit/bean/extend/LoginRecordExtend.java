package com.starsgroupchina.credit.bean.extend;

import com.starsgroupchina.common.utils.BeanUtil;
import com.starsgroupchina.credit.bean.model.LoginRecord;
import com.starsgroupchina.credit.bean.model.Member;
import com.starsgroupchina.credit.bean.model.Role;
import lombok.Data;

/**
 * @Author: QinHaoHao
 * @Description:
 * @Date: Created in 14:30 2018/9/4
 * @Modifed By:
 */
@Data
public class LoginRecordExtend extends LoginRecord {

    private Member member;

    private Role role;

    public LoginRecordExtend(LoginRecord loginRecord) {
        BeanUtil.copyProperty(loginRecord, this);
    }

    public LoginRecordExtend() {
    }
}
