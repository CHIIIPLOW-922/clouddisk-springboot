package com.chiiiplow.clouddisk.component;

import com.chiiiplow.clouddisk.constant.RedisConstants;
import com.chiiiplow.clouddisk.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

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
        redisUtils.setex(RedisConstants.CAPTCHA_KEY + key, captcha, TimeUnit.SECONDS.toSeconds(120L));
    }


}
