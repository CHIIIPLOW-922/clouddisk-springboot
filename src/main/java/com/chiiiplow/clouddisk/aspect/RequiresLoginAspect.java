package com.chiiiplow.clouddisk.aspect;

import com.chiiiplow.clouddisk.constant.CommonConstants;
import com.chiiiplow.clouddisk.exception.CustomException;
import com.chiiiplow.clouddisk.utils.JwtUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


import javax.servlet.http.HttpServletRequest;

/**
 * 需要登录切面
 *
 * @author CHIIIPLOW
 * @date 2024/06/08
 */
@Aspect
@Component
@EnableAspectJAutoProxy
public class RequiresLoginAspect {


    @Autowired
    private JwtUtils jwtUtils;


    private static final String BEARER = "Bearer ";


    @Pointcut("@annotation(com.chiiiplow.clouddisk.annotation.RequiresLogin)")
    private void requiresLoginAspect() {

    }

    @Before(value = "requiresLoginAspect()")
    public void needLogin() throws CustomException {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        String jwtToken = request.getHeader(CommonConstants.HEADER_TOKEN);
        CustomException customException = new CustomException();
        customException.setCode(555);
        if (StringUtils.isEmpty(jwtToken) || !jwtToken.startsWith(BEARER)) {
            customException.setMessage("请登录后操作！");
            throw customException;
        }
        String subJwtToken = jwtToken.substring(7);
        if (jwtUtils.invalidToken(subJwtToken)) {
            customException.setMessage("用户Token或已过期，请重新登录！");
            throw customException;
        }
    }


//    private String getJwtFromCookies(HttpServletRequest request) {
//        Cookie[] cookies = request.getCookies();
//        if (cookies != null) {
//            for (Cookie cookie : cookies) {
//                if ("JWT-TOKEN".equals(cookie.getName())) {
//                    return cookie.getValue();
//                }
//            }
//        }
//        return null;
//    }
}
