package com.chiiiplow.clouddisk;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.Query;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.chiiiplow.clouddisk.dao.AdminMapper;
import com.chiiiplow.clouddisk.dao.UserMapper;
import com.chiiiplow.clouddisk.entity.Admin;
import com.chiiiplow.clouddisk.entity.User;
import com.chiiiplow.clouddisk.utils.MD5Utils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
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
        User user = new User();
        user.setUserName("Test1");
        user.setUserNickname("CHIIIPLOW");
        user.setEmail("q641484973@qq.com");
        String salt = MD5Utils.generateSalt();
        user.setSalt(salt);
        String password = MD5Utils.encode("123456", salt);
        user.setPassword(password);
        System.out.println(userMapper.insert(user));

    }

    @Test
    void test() {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("user_name", "Test1");
        User user = userMapper.selectOne(userQueryWrapper);
        String loginPassword = "12345";
        String salt = user.getSalt();
        String encodeLoginPassword = MD5Utils.encode(loginPassword, salt);
        String dataPassword = user.getPassword();
        System.out.println(StringUtils.equals(dataPassword, encodeLoginPassword));
    }

    @Test
    void test2() {
        Admin admin = new Admin();
        admin.setAdminAccount("123123123");
        admin.setAdminNickname("CHIIIPLOW");
        String salt = MD5Utils.generateSalt();
        admin.setSalt(salt);
        String encodePassword = MD5Utils.encode("123123", salt);
        admin.setAdminPassword(encodePassword);
        admin.setCreateTime(new Date());
        adminMapper.insert(admin);
    }


}
