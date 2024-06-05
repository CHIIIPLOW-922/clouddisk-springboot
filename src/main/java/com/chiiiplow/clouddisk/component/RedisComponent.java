package com.chiiiplow.clouddisk.component;

import com.chiiiplow.clouddisk.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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


}
