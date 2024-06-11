package com.chiiiplow.clouddisk.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chiiiplow.clouddisk.component.RedisComponent;
import com.chiiiplow.clouddisk.constant.CommonConstants;
import com.chiiiplow.clouddisk.constant.RedisConstants;
import com.chiiiplow.clouddisk.dao.UserMapper;
import com.chiiiplow.clouddisk.entity.User;
import com.chiiiplow.clouddisk.entity.vo.LoginVO;
import com.chiiiplow.clouddisk.entity.vo.RegisterVO;
import com.chiiiplow.clouddisk.entity.vo.UserVO;
import com.chiiiplow.clouddisk.exception.CustomException;
import com.chiiiplow.clouddisk.service.UserService;
import com.chiiiplow.clouddisk.utils.JwtUtils;
import com.chiiiplow.clouddisk.utils.SHA256Utils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户服务实现层
 *
 * @author yangzhixiong
 * @date 2024/06/04
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private RedisComponent redisComponent;



    @Override
    public UserVO login(LoginVO loginVO, HttpServletRequest request) {
        if (loginVO.getShowCaptcha()) {
            String uniqueId = request.getHeader(CommonConstants.X_UNIQUE_ID);
            String redisCaptcha = StringUtils.isEmpty(redisComponent.getCaptchaKey(uniqueId)) ?
                    "1" : redisComponent.getCaptchaKey(uniqueId);
            if (!StringUtils.equalsIgnoreCase(loginVO.getCaptcha(), redisCaptcha)) {
                throw new CustomException("验证码不正确或已过期，请重试！");
            }
        }
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("user_name", loginVO.getUsername());
        User user = userMapper.selectOne(userQueryWrapper);
        if (ObjectUtils.isEmpty(user)) {
            throw new CustomException("该用户不存在，请重试！");
        }
        String loginEncodePassword = SHA256Utils.encode(loginVO.getPassword(), user.getSalt());
        if (!StringUtils.equals(user.getPassword(), loginEncodePassword)) {
            throw new CustomException("密码错误，请重试！");
        }
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);
        String token = jwtUtils.generateJwt(userVO);
        userVO.setToken(token);
        return userVO;
    }

    @Override
    public void register(RegisterVO registerVO) {
        return;
    }
}
