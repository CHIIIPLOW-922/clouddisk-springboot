package com.chiiiplow.clouddisk.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chiiiplow.clouddisk.entity.User;
import com.chiiiplow.clouddisk.entity.vo.LoginVO;
import com.chiiiplow.clouddisk.entity.vo.RegisterVO;
import com.chiiiplow.clouddisk.entity.vo.UserVO;

import javax.servlet.http.HttpServletRequest;

public interface UserService extends IService<User> {

    UserVO login(LoginVO loginVO, HttpServletRequest request);

    void register(RegisterVO registerVO, HttpServletRequest request);

    void sendEmailVerifyCode(String email, String uniqueId);
}
