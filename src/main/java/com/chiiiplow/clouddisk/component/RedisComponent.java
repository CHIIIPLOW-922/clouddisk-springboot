package com.chiiiplow.clouddisk.component;

import com.chiiiplow.clouddisk.constant.CommonConstants;
import com.chiiiplow.clouddisk.constant.RedisConstants;
import com.chiiiplow.clouddisk.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;


/**
 * Redis 组件
 *
 * @author yangzhixiong
 * @date 2024/06/05
 */
@Component
public class RedisComponent {

    @Autowired
    private RedisUtils redisUtils;


    public void saveCaptchaKey(String key, String captcha) {
        redisUtils.setex(RedisConstants.CAPTCHA_KEY + key, captcha, CommonConstants.ONE_MINUTE * 5);
    }

    public String getCaptchaKey(String key) {
        return (String) redisUtils.get(RedisConstants.CAPTCHA_KEY + key);
    }

    public boolean saveBlackListJwt(String jwtId, Date expire) {
        long countExpire = Math.max(expire.getTime() - new Date().getTime(), 0);
        return redisUtils.setex(RedisConstants.JWT_BLACK_LIST + jwtId, "", countExpire);
    }

    public boolean hasJwtToken(String jwtId) {
        return redisUtils.hasKey(RedisConstants.JWT_BLACK_LIST + jwtId);
    }


    public boolean saveEmailCode(String key, String code) {
        return redisUtils.setex(RedisConstants.EMAIL_KEY + key, code, CommonConstants.ONE_MINUTE * 10);
    }


}
