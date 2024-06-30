package com.chiiiplow.clouddisk.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chiiiplow.clouddisk.entity.User;
import com.chiiiplow.clouddisk.entity.dto.UsedDiskSpaceDTO;
import com.chiiiplow.clouddisk.entity.vo.LoginVO;
import com.chiiiplow.clouddisk.entity.vo.RegisterVO;
import com.chiiiplow.clouddisk.entity.vo.UserVO;

import javax.servlet.http.HttpServletRequest;

public interface UserService extends IService<User> {

    /**
     * 登录
     *
     * @param loginVO 登录 vo
     * @param request 请求
     * @return {@link UserVO}
     */
    String login(LoginVO loginVO, HttpServletRequest request);

    /**
     * 注册
     *
     * @param registerVO 注册 VO
     * @param request    请求
     */
    void register(RegisterVO registerVO, HttpServletRequest request);

    /**
     * 发送电子邮件验证码
     *
     * @param email    电子邮件
     * @param uniqueId 唯一 ID
     */
    void sendEmailVerifyCode(String email, String uniqueId);

    /**
     * 注销
     *
     * @param request 请求
     */
    void logout(HttpServletRequest request);

    UsedDiskSpaceDTO usedDiskSpace(Long userId);
}
