package com.chiiiplow.clouddisk;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.chiiiplow.clouddisk.constant.MinioProperties;
import com.chiiiplow.clouddisk.dao.AdminMapper;
import com.chiiiplow.clouddisk.dao.UserMapper;
import com.chiiiplow.clouddisk.entity.Admin;
import com.chiiiplow.clouddisk.entity.User;
import com.chiiiplow.clouddisk.entity.vo.UserVO;
import com.chiiiplow.clouddisk.utils.JwtUtils;
import com.chiiiplow.clouddisk.utils.SHA256Utils;
import com.chiiiplow.clouddisk.utils.EmailUtils;
import io.minio.MinioClient;
import io.minio.errors.*;
import io.minio.messages.Bucket;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;
import java.util.Set;

@SpringBootTest
public class ClouddiskApplicationTests {

    @Resource
    private AdminMapper adminMapper;

    @Resource
    private UserMapper userMapper;

    @Autowired
    private MinioProperties minioProperties;

    @Autowired
    private MinioClient minioClient;

    @Autowired
    private JwtUtils jwtUtils;

    /**
     * Redis 模板
     */
    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private EmailUtils sendEmailUtils;



    @Test
    void contextLoads() {
        User user = new User();
        user.setUsername("Test2");
        user.setUserNickname("CHIIIPLOW");
        user.setEmail("1641484973@qq.com");
        String salt = SHA256Utils.generateSalt();
        user.setSalt(salt);
        String password = SHA256Utils.encode("353906413", salt);
        user.setPassword(password);
        System.out.println(userMapper.insert(user));

    }

    @Test
    void test() {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("user_name", "Test2");
        User user = userMapper.selectOne(userQueryWrapper);
        String loginPassword = "123";
        String salt = user.getSalt();
        String encodeLoginPassword = SHA256Utils.encode(loginPassword, salt);
        String dataPassword = user.getPassword();
        System.out.println(StringUtils.equals(dataPassword, encodeLoginPassword));
    }

    @Test
    void test2() {
        Admin admin = new Admin();
        admin.setAdminAccount("641484973");
        admin.setAdminNickname("Administrator");
        String salt = SHA256Utils.generateSalt();
        admin.setSalt(salt);
        String encodePassword = SHA256Utils.encode("353906413", salt);
        admin.setAdminPassword(encodePassword);
        admin.setCreateTime(new Date());
        adminMapper.insert(admin);
    }

    @Test
    void test3() throws InvalidBucketNameException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException, RegionConflictException {
        System.out.println(minioClient.bucketExists(minioProperties.getBucketName()));
        if (!minioClient.bucketExists(minioProperties.getBucketName())) {
            System.out.println("bucket不存在");
            minioClient.makeBucket(minioProperties.getBucketName());
            System.out.println("创建bucket成功");
        } else {
            List<Bucket> buckets = minioClient.listBuckets();
            Bucket bucket = buckets.stream().filter(item -> item.name().equals(minioProperties.getBucketName())).findFirst().orElse(null);
            System.out.println(bucket);
        }
    }

    @Test
    void test4() {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("user_name", "123123");
        User user = userMapper.selectOne(userQueryWrapper);
    }

    @Test
    void test5() {
        Admin admin = new Admin();
        admin.setAdminAccount("Admin2");
        admin.setAdminNickname("CHIIIPLOW-922");
        String salt = SHA256Utils.generateSalt();
        admin.setSalt(salt);
        String encode = SHA256Utils.encode("123456", salt);
        admin.setAdminPassword(encode);
        System.out.println(adminMapper.insert(admin));

    }

    @Test
    void test6() {
        QueryWrapper<Admin> adminQueryWrapper = new QueryWrapper<>();
        adminQueryWrapper.eq("admin_account", "Admin2");
        Admin admin = adminMapper.selectOne(adminQueryWrapper);
        if (!ObjectUtils.isEmpty(admin)) {
            admin.setAdminAccount("Admin4");
            admin.setAdminNickname("CHIIIPLOW");
            String salt = SHA256Utils.generateSalt();
            admin.setSalt(salt);
            String encode = SHA256Utils.encode("456456", salt);
            admin.setAdminPassword(encode);
            System.out.println(adminMapper.updateById(admin));
        }
    }

//    @Test
//    void test7() throws JsonProcessingException {
//        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
//        userQueryWrapper.eq("user_name", "Test1");
//        User user = userMapper.selectOne(userQueryWrapper);
//        UserJwt userJwt = new UserJwt();
//        BeanUtils.copyProperties(user, userJwt);
//
//        String token = jwtUtils.generateToken(userJwt);
//        System.out.println(jwtUtils.validateToken(token));
//    }

    @Test
    void test8() {
        Set<String> keys = redisTemplate.keys("*");
        if (!keys.isEmpty()) {
            for (String key : keys) {
                System.out.println(key + " : " + redisTemplate.opsForValue().get(key) + " : " + redisTemplate.getExpire(key));
            }
        }

    }


    @Test
    void test9() {
        UserVO userVO = new UserVO();
        userVO.setId(IdWorker.getId());
        userVO.setUsername("123");
        String s = jwtUtils.generateJwt(userVO);
        System.out.println(s);
    }


    @Test
    void sendEmail() {
        sendEmailUtils.sendEmail("q641484973@gmail.com", "测试测试", "123123123");
    }
}
