package com.chiiiplow.clouddisk.controller;

import com.chiiiplow.clouddisk.common.R;
import com.chiiiplow.clouddisk.constant.CommonConstants;
import com.chiiiplow.clouddisk.entity.vo.UserVO;
import com.chiiiplow.clouddisk.exception.CustomException;
import com.chiiiplow.clouddisk.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 通用控制层
 *
 * @author CHIIIPLOW
 * @date 2024/06/07
 */
public class BaseController {

    @Autowired
    private JwtUtils jwtUtils;

    private static final String SUCCESS = "请求成功";


//    protected <T> R successResult(T data) {
//        R<T> r = new R<>();
//        r.setCode(HttpServletResponse.SC_OK);
//        r.setMsg(SUCCESS);
//        r.setData(data);
//        return r;
//    }
//
//
//    protected <T> R successResult() {
//        R<T> r = new R<>();
//        r.setCode(HttpServletResponse.SC_OK);
//        r.setMsg(SUCCESS);
//        return r;
//    }
//
//    protected <T> R successResult(String msg, T data) {
//        R<T> r = new R<>();
//        r.setCode(HttpServletResponse.SC_OK);
//        r.setMsg(msg);
//        r.setData(data);
//        return r;
//    }

    protected UserVO getCurrentUser(HttpServletRequest request) {
        String headerJwt = request.getHeader(CommonConstants.HEADER_TOKEN);
        String jwtToken = headerJwt.substring(7);
        Claims claims = jwtUtils.decodedJWT(jwtToken);
        if (ObjectUtils.isEmpty(claims)) {
            throw new CustomException("用户token不正确,获取失败");
        }
        UserVO userVO = new UserVO();
        userVO.setId((Long) claims.get("id"));
        userVO.setUsername((String) claims.get("username"));
        return userVO;
    }
}
