package com.starsgroupchina.credit.server.aspect;

import com.starsgroupchina.common.annotation.AuthIgnore;
import com.starsgroupchina.common.context.Context;
import com.starsgroupchina.common.context.ContextHolder;
import com.starsgroupchina.common.exception.AppException;
import com.starsgroupchina.credit.bean.AuthMember;
import com.starsgroupchina.credit.key.ErrorCode;
import com.starsgroupchina.credit.server.service.system.MemberService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * Created by zhangfeng on 2018-6-5.
 */
@Aspect
@Component
public class AuthAspect {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private MemberService memberService;

    @Before("execution(* com.starsgroupchina.credit.server.controller..*.*(..))")
    public void authorization(JoinPoint point) throws Throwable {
        for (Object arg : ((MethodSignature) point.getSignature()).getMethod().getAnnotations()) {
            if (arg instanceof AuthIgnore)
                return;
        }
        setContext();
    }

    private void setContext() {
        String token = request.getHeader("token");
        AuthMember authMember = Optional.ofNullable(memberService.getAuthMember(token))
                .orElseThrow(() -> new AppException(ErrorCode.AUTH_ERROR));

        Context context = new Context(authMember);
        context.setUserId(authMember.getMember().getId());
        context.setUserName(authMember.getMember().getName());
        ContextHolder.setContext(context);

        memberService.refreshAuthMember(token);
    }

}
