package com.chiiiplow.clouddisk.aspect;

import com.chiiiplow.clouddisk.component.RedisComponent;
import com.chiiiplow.clouddisk.constant.CommonConstants;
import com.chiiiplow.clouddisk.exception.CustomException;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
@EnableAspectJAutoProxy
public class RequestLimitAspect {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private RedisComponent redisComponent;

    private static final Integer LIMIT_COUNT = 20;



    @Before("execution(* com.chiiiplow.clouddisk.controller.*.*(..))")
    public void requestsLimitCount() throws CustomException {
        String ip = request.getRemoteAddr();
        Long count = redisComponent.incrementIp(ip);
        if (count == 1) {
            redisComponent.expireIpLimit(ip, CommonConstants.ONE_SECOND);
        }
        if (count > LIMIT_COUNT) {
            throw new CustomException("当前IP请求服务过多，请稍后再试！");
        }

    }
}
