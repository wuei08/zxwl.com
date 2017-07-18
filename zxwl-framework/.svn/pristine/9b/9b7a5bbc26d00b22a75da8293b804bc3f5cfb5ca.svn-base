package com.zxwl.web.handler;

import com.zxwl.web.bean.po.BasePo;
import com.zxwl.web.core.utils.WebUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Project: zxwl-framework
 * Author: Sendya <18x@loacg.com>
 * Date: 2017/7/17 11:57
 */

@Aspect
@Order(1)
@Component
public class AopReplaceBeanParameter {

    private static Logger logger = LoggerFactory.getLogger(AopReplaceBeanParameter.class);

    @Pointcut("execution (* com.zxwl..service..*.*(..)) && args(basePo)")
    private void basePoPointcut(BasePo basePo){}

    @Around(value = "basePoPointcut(basePo)")
    public Object beforeSearchBasePojo(ProceedingJoinPoint joinPoint, BasePo basePo) throws Throwable {

        logger.info("AOP Pointcut: {}", basePo);

        Object[] args = joinPoint.getArgs();
        if (basePo.getGmtCreate() == null) {
            basePo.setGmtCreate(new Date());
        }
        basePo.setGmtModify(new Date());
        basePo.setLastChangeUser(WebUtil.getLoginUser().getId());

        args[0] = basePo;

        return joinPoint.proceed(args);
    }

/*    @Before("execution(* com..service..*.insert(..))")
    public void doInsert(JoinPoint joinPoint) {

        logger.info("=========== 切面 insert 执行 ============");
        logger.info("JoinPoint: {}", joinPoint);

    }

    @Before("execution(* com..service..*.update(..))")
    public void doUpdate(JoinPoint joinPoint) {

        logger.info("=========== 切面 update 执行 ============");
        logger.info("JoinPoint: {}", joinPoint);

    }*/

}
