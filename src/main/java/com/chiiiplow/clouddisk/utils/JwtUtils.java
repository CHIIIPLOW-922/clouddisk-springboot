package com.chiiiplow.clouddisk.utils;

import com.chiiiplow.clouddisk.constant.CommonConstants;
import com.chiiiplow.clouddisk.constant.RedisConstants;
import com.chiiiplow.clouddisk.entity.User;
import com.chiiiplow.clouddisk.exception.CustomException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * JWT 工具类
 *
 * @author yangzhixiong
 * @date 2024/06/07
 */
@Component
public class JwtUtils {

    @Autowired
    private RedisUtils redisUtils;


    public String generateToken(User user) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String userJson = objectMapper.writeValueAsString(user);
        Date now = new Date();
        String token = Jwts.builder()
                .setSubject(user.getUserName())
                .setIssuedAt(now)
                .signWith(SignatureAlgorithm.HS512, CommonConstants.JWT_SECRET_KEY)
                .compact();
        if (!redisUtils.setex(RedisConstants.JWT_KEY + token, userJson, CommonConstants.ONE_WEEK)) {
            throw new CustomException("生成token失败");
        }
        return token;
    }

    public boolean validateToken(String token) {
        Boolean valid = redisUtils.hasKey(RedisConstants.JWT_KEY + token);
        return valid != null && valid;
    }


}
