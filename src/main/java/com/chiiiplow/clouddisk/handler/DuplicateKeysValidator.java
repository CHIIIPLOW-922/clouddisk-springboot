package com.chiiiplow.clouddisk.handler;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chiiiplow.clouddisk.annotation.CheckDuplicateKey;
import com.chiiiplow.clouddisk.dao.AdminMapper;
import com.chiiiplow.clouddisk.dao.UserMapper;
import com.chiiiplow.clouddisk.entity.Admin;
import com.chiiiplow.clouddisk.entity.User;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

/**
 * 重复密钥验证程序
 *
 * @author yangzhixiong
 * @date 2024/06/13
 */
public class DuplicateKeysValidator implements ConstraintValidator<CheckDuplicateKey, String> {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private AdminMapper adminMapper;

    @Override
    public void initialize(CheckDuplicateKey constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        QueryWrapper<Admin> adminQueryWrapper = new QueryWrapper<>();
        String field = value;
        userQueryWrapper.eq("username", field).or().eq("email", field);
        adminQueryWrapper.eq("admin_account", field);
        User user = userMapper.selectOne(userQueryWrapper);
        Admin admin = adminMapper.selectOne(adminQueryWrapper);
        if (Objects.isNull(user) && Objects.isNull(admin)) {
            return true;
        }
        return false;
    }
}
