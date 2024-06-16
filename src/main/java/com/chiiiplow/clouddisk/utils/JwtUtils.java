package com.chiiiplow.clouddisk.utils;


import com.chiiiplow.clouddisk.component.RedisComponent;
import com.chiiiplow.clouddisk.constant.CommonConstants;
import com.chiiiplow.clouddisk.entity.vo.UserVO;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


/**
 * JWT 工具类
 *
 * @author yangzhixiong
 * @date 2024/06/07
 */
@Component
public class JwtUtils {

    @Value(value = "${chiiiplow.clouddisk.jwt.secret}")
    private String secret;

    @Value(value = "${chiiiplow.clouddisk.jwt.expire}")
    private int expire;

    @Autowired
    private RedisComponent redisComponent;


    public String generateJwt(UserVO userVO) {
        long now = System.currentTimeMillis();
        String jwtId = UUID.randomUUID().toString();
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("id", userVO.getId());
        resultMap.put("username", userVO.getUsername());
        resultMap.put("userNickname", userVO.getUserNickname());
        resultMap.put("userAvatarPath", userVO.getUserAvatarPath());
        resultMap.put("email", userVO.getEmail());
        resultMap.put("usedDiskSpace", userVO.getUsedDiskSpace());
        resultMap.put("totalDiskSpace", userVO.getTotalDiskSpace());
        return Jwts.builder()
                .setClaims(resultMap)
                .setSubject(userVO.getUsername())
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + CommonConstants.ONE_DAY * expire))
                .setId(jwtId)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public Claims decodedJWT(String token) {
        try {
            Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
            if (isBlackListJwt(claims.getId())) {
                return null;
            }
            return new Date().after(claims.getExpiration()) ? null : claims;
        } catch (Exception e) {
            return null;
        }
    }

    public boolean invalidToken(String token) {
        Claims claims = decodedJWT(token);
        if (ObjectUtils.isEmpty(claims)) {
            return true;
        }
        return false;
    }



    public boolean deleteJwtToken(String uuid, Date expire) {
        if (this.isBlackListJwt(uuid)) {
            return false;
        }
        return redisComponent.saveBlackListJwt(uuid, expire);
    }

    private boolean isBlackListJwt(String jwtId) {
        return redisComponent.hasJwtToken(jwtId);
    }

}
