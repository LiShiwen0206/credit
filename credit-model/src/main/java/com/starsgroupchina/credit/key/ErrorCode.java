package com.starsgroupchina.credit.key;

import com.starsgroupchina.common.exception.ErrorMessage;

public class ErrorCode {

    @ErrorMessage("用户名或密码不正确")
    public static final int AUTH_USER_PWD = 1401;

    @ErrorMessage("获取用户信息失败，请登录后重试")
    public static final int AUTH_ERROR = 1402;

    @ErrorMessage("请修改默认密码")
    public static final int AUTH_USER_PWD_DEFAULT = 1403;

    @ErrorMessage("当前用户名已存在，请更换用户名重试")
    public static final int EXIST_MEMBER = 1404;

    @ErrorMessage("原密码错误")
    public static final int OLD_PWD_ERROR = 1405;

    @ErrorMessage("图片验证码不正确")
    public static final int IMAGE_CAPTCHA_ERROR = 1406;

    @ErrorMessage("图片验证码不存在或已过期")
    public static final int IMAGE_CAPTCHA_EXPIRED = 1407;

    @ErrorMessage("当前用户列表查看权限为空")
    public static final int AUTH_DATA_NULL = 2201;

    @ErrorMessage("当前角色权限为空")
    public static final int AUTH_OPERATION_NULL = 2202;

    @ErrorMessage("项目编号获取失败")
    public static final int PROJECT_NO_EMPTY = 3000;

    @ErrorMessage("项目编号[{0}]的项目不存在")
    public static final int PROJECT_NOT_EXIST = 3001;

    @ErrorMessage("当前环节存在未办完项目，请完成当前项目后获取新项目")
    public static final int PROJECT_DOING = 3002;

    @ErrorMessage("政策验证未通过")
    public static final int PROJECT_POLICY_INVALID_ERROR = 3003;

    @ErrorMessage("条件验证未通过")
    public static final int PROJECT_CONDITION_INVALID_ERROR = 3004;

    @ErrorMessage("条件创建失败,当前机构条件名重复")
    public static final int ORG_CONDITION_NAME_REPEAT = 3005;

    @ErrorMessage("当前联系人已存在")
    public static final int PROJECT_CONTACT_EXIST = 3100;

    @ErrorMessage("黑名单导入出错")
    public static final int BLACKLIST_IMPORT_UNKOWN_ERROR = 4000;

    @ErrorMessage("导入数据为空")
    public static final int BLACKLIST_IMPORT_EMPTY = 4001;

    @ErrorMessage("类型不能为空")
    public static final int BLACKLIST_IMPORT_ILLEGAL_TYPE = 4002;

    @ErrorMessage("身份证号不能为空")
    public static final int BLACKLIST_IMPORT_ILLEGAL_ID_CARD = 4003;

    @ErrorMessage("该记录已存在黑名单中")
    public static final int BLACKLIST_USER_WAS_EXISTS = 4004;

    @ErrorMessage("件池中项目已获取完")
    public static final int NO_PROJECT_WIYHOUT_ASSIGN = 5001;

    @ErrorMessage("当前项目限制申报2次，已超过申报上限")
    public static final int PROJECT_EXCEED_DECLARE_LIMIT = 5002;

    @ErrorMessage("申报编号规则未设置")
    public static final int PROJECT_DECLARE_NO_RULE_NOT_EXITS = 5003;

    @ErrorMessage("删除失败")
    public static final int PROJECT_QUALITY_DELETE_NOT_ALLOW = 5004;

    @ErrorMessage("未设置自动新增上限百分比")
    public static final int PROJECT_QUALITY_PERCENTAGE = 5005;

    @ErrorMessage("当前项目正在调查中或者已确定为风险客户")
    public static final int PROJECT_DECLARE_EXIST_OR_SUCCESS = 5006;

    @ErrorMessage("申报数据导入出错")
    public static final int DECLARE_IMPORT_UNKOWN_ERROR = 6000;

    @ErrorMessage("第三方数据校验失败")
    public static final int THIRD_DATA_VALID_FAIL = 7000;

    @ErrorMessage("第三方数据验证名已存在")
    public static final int THIRD_DATA_VALID_EXITS = 7000;
}
