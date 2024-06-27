package com.chiiiplow.clouddisk.aspect;

import com.chiiiplow.clouddisk.annotation.AccessLimit;
import com.chiiiplow.clouddisk.component.RedisComponent;
import com.chiiiplow.clouddisk.constant.CommonConstants;
import com.chiiiplow.clouddisk.exception.CustomException;
import com.chiiiplow.clouddisk.utils.IPUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 请求限制切面
 *
 * @author yangzhixiong
 * @date 2024/06/14
 */
@Aspect
@Component
@EnableAspectJAutoProxy
@Order(1)
public class RequestLimitAspect {


    @Autowired
    private RedisComponent redisComponent;

    private static final Integer LIMIT_COUNT = 20;


    @Pointcut("@annotation(com.chiiiplow.clouddisk.annotation.AccessLimit)")
    private void requestLimitAspect() {
    }


    @Before("requestLimitAspect()")
    public void requestsLimitCount(JoinPoint joinPoint) throws CustomException {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        AccessLimit accessLimit = methodSignature.getMethod().getAnnotation(AccessLimit.class);
        String key = accessLimit.key();
        int countLimit = accessLimit.count();
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        String ip = IPUtils.getIpAddress(request);
        String ipKey = ip + ":" + key;
        Long count = redisComponent.incrementIp(ipKey);
        if (count == 1) {
            redisComponent.expireIpLimit(ipKey, CommonConstants.ONE_SECOND);
        }
        if (count > countLimit) {
            throw new CustomException("当前IP请求服务过多，请稍后再试！");
        }

    }

    @Before("execution(* com.chiiiplow.clouddisk.controller.*.*(..)) && !execution(* com.chiiiplow.clouddisk.controller.BaseController.*(..))")
    public void wholeControllerLimit() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        String ip = IPUtils.getIpAddress(request);
        String ipKey = ip;
        Long count = redisComponent.incrementIp(ipKey);
        if (count == 1) {
            redisComponent.expireIpLimit(ipKey, CommonConstants.ONE_SECOND);
        }
        if (count > LIMIT_COUNT) {
            throw new CustomException("当前IP请求服务过多，请稍后再试！");
        }
    }
}
