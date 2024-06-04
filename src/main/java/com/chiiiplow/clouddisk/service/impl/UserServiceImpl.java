package com.chiiiplow.clouddisk.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chiiiplow.clouddisk.dao.UserMapper;
import com.chiiiplow.clouddisk.entity.User;
import com.chiiiplow.clouddisk.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户服务实现层
 *
 * @author yangzhixiong
 * @date 2024/06/04
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    public void register(User registerUser) {

    }


    @Override
    public List<User> getUsers() {
        return baseMapper.selectList(null);
    }
}
