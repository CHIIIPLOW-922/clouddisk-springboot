package com.chiiiplow.clouddisk.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chiiiplow.clouddisk.entity.User;

import java.util.List;

public interface UserService extends IService<User> {

    public List<User > getUsers();
}
