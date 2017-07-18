package com.zxwl.web.controller;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import com.zxwl.web.core.authorize.AopAuthorizeValidator;
import com.zxwl.web.core.exception.AuthorizeForbiddenException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

@Configuration
public class AopAuthorizeValidatorAutoConfiguration {

    @Bean
    public ControllerAuthorizeValidator controllerAuthorizeValidator() {
        return new ControllerAuthorizeValidator();
    }

    @Aspect
    @Order(1)
    static class ControllerAuthorizeValidator extends AopAuthorizeValidator {
        @Around(value = "execution(* com.zxwl.web..controller..*Controller..*(..))||@annotation(com.zxwl.web.core.authorize.annotation.Authorize)")
        public Object around(ProceedingJoinPoint pjp) throws Throwable {
            boolean access = super.validate(pjp);
            if (!access) throw new AuthorizeForbiddenException("无权限", 403);
            return pjp.proceed();
        }
    }
}
