package com.starsgroupchina.credit.server.controller;

import com.starsgroupchina.common.annotation.AuthIgnore;
import com.starsgroupchina.common.exception.AppException;
import com.starsgroupchina.common.response.SimpleResponse;
import com.starsgroupchina.common.utils.Utils;
import com.starsgroupchina.credit.bean.AuthMember;
import com.starsgroupchina.credit.bean.LoginMember;
import com.starsgroupchina.credit.bean.model.Member;
import com.starsgroupchina.credit.key.ErrorCode;
import com.starsgroupchina.credit.key.KeyCache;
import com.starsgroupchina.credit.server.conf.RedisConf;
import com.starsgroupchina.credit.server.service.system.MemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * Created by zhangfeng on 2018-5-31.
 */
@Slf4j
@RestController
@Api(tags = "CREDIT-SWAGGER34", description = "登录 - LoginController")
@RequestMapping(value = "", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class LoginController {

    @Autowired
    private MemberService memberService;

    @Resource(name = RedisConf.REDIS_TEMPLATE)
    private RedisOperations redisOperations;

    /**
     * 登录
     */
    @ApiResponses({@ApiResponse(code = ErrorCode.AUTH_USER_PWD, message = "用户名或密码不正确")})
    @AuthIgnore
    @ApiOperation("登录")
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public SimpleResponse<LoginMember> login(
            @RequestParam(value = "name", defaultValue = "admin") String name,
            @RequestParam(value = "pwd", defaultValue = "E10ADC3949BA59ABBE56E057F20F883E") String pwd) {
        Member member = Optional.ofNullable(memberService.checkMember(name.trim(), pwd))
                .orElseThrow(() -> new AppException(ErrorCode.AUTH_USER_PWD));
        String token = Utils.getUUID();
        AuthMember authMember = memberService.cacheAuthMember(token, member);
        LoginMember loginMember = new LoginMember();
        loginMember.setToken(token);
        loginMember.setMemberId(member.getId());
        loginMember.setName(member.getName());
        loginMember.setLoginName(member.getLoginName());
        loginMember.setOrgId(authMember.getOrg().getId());
        loginMember.setHeadOrgId(authMember.getOrg().getHeadOrgId());
        Optional.ofNullable(authMember.getOrg()).ifPresent(org -> loginMember.setOrgName(org.getName()));
        Optional.ofNullable(authMember.getRole()).ifPresent(role -> loginMember.setRoleName(role.getRoleName()));
        return SimpleResponse.success(loginMember);
    }

    /**
     * 退出登录
     */
    @ApiOperation("退出登录")
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public SimpleResponse<String> logout(HttpServletRequest request) {
        String token = request.getHeader("token");
        redisOperations.delete(KeyCache.token(token));
        return SimpleResponse.success("success");
    }


}
