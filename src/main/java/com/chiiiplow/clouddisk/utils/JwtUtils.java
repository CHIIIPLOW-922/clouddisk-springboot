package com.chiiiplow.clouddisk.utils;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.chiiiplow.clouddisk.component.RedisComponent;
import com.chiiiplow.clouddisk.constant.CommonConstants;
import com.chiiiplow.clouddisk.entity.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.Date;
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
        long now = new Date().getTime();
        Algorithm algorithm = Algorithm.HMAC512(secret);
        String jwtUuid = UUID.randomUUID().toString();
        String jwt = JWT.create()
                .withJWTId(jwtUuid)
                .withClaim("id", userVO.getId())
                .withClaim("username", userVO.getUserName())
                .withClaim("email", userVO.getEmail())
                .withClaim("userNickname", userVO.getUserNickname())
                .withClaim("userAvatarPath", userVO.getUserAvatarPath())
                .withClaim("usedDiskSpace", userVO.getUsedDiskSpace())
                .withClaim("totalDiskSpace", userVO.getTotalDiskSpace())
                .withExpiresAt(new Date(now + CommonConstants.ONE_DAY * expire))
                .withIssuedAt(new Date())
                .sign(algorithm);
        return jwt;
    }

    public DecodedJWT decodedJWT(String token) {
        Algorithm algorithm = Algorithm.HMAC512(secret);
        JWTVerifier verifier = JWT.require(algorithm).build();
        try {
            DecodedJWT verify = verifier.verify(token);
            if (isBlackListJwt(verify.getId())) {
                return null;
            }
            return new Date().after(verify.getExpiresAt()) ? null : verify;
        } catch (JWTVerificationException e) {
            return null;
        }
    }

    public boolean invalidToken(String token) {
        DecodedJWT decodedJWT = decodedJWT(token);
        if (ObjectUtils.isEmpty(decodedJWT)) {
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
