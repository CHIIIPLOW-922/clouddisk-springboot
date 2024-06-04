package com.chiiiplow.clouddisk;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.Query;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.chiiiplow.clouddisk.dao.AdminMapper;
import com.chiiiplow.clouddisk.dao.UserMapper;
import com.chiiiplow.clouddisk.entity.Admin;
import com.chiiiplow.clouddisk.entity.User;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Random;

@SpringBootTest
public class ClouddiskApplicationTests {

    @Resource
    private AdminMapper adminMapper;

    @Resource
    private UserMapper userMapper;



    @Test
    void contextLoads() {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.like("user_name", "123");
        List<User> users = userMapper.selectList(userQueryWrapper);
        System.out.println(users);


    }


}
