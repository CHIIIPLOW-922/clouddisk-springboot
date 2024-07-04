package com.chiiiplow.clouddisk.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chiiiplow.clouddisk.component.RedisComponent;
import com.chiiiplow.clouddisk.constant.CommonConstants;
import com.chiiiplow.clouddisk.dao.UserMapper;
import com.chiiiplow.clouddisk.entity.User;
import com.chiiiplow.clouddisk.entity.dto.UsedDiskSpaceDTO;
import com.chiiiplow.clouddisk.entity.dto.UserInfoDTO;
import com.chiiiplow.clouddisk.entity.vo.LoginVO;
import com.chiiiplow.clouddisk.entity.vo.RegisterVO;
import com.chiiiplow.clouddisk.entity.vo.UserVO;
import com.chiiiplow.clouddisk.exception.CustomException;
import com.chiiiplow.clouddisk.service.UserService;
import com.chiiiplow.clouddisk.utils.FileSizeConverter;
import com.chiiiplow.clouddisk.utils.JwtUtils;
import com.chiiiplow.clouddisk.utils.SHA256Utils;
import com.chiiiplow.clouddisk.utils.EmailUtils;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

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

    @Autowired
    private EmailUtils emailUtils;





    @Override
    public String login(LoginVO loginVO, HttpServletRequest request) {
        if (loginVO.getShowCaptcha()) {
            if (StringUtils.isBlank(loginVO.getCaptcha())) {
                throw new CustomException("请输入验证码！");
            }
            String uniqueId = request.getHeader(CommonConstants.X_UNIQUE_ID);
            String redisCaptcha = StringUtils.isEmpty(redisComponent.getCaptchaKey(uniqueId)) ?
                    "1" : redisComponent.getCaptchaKey(uniqueId);
            if (!StringUtils.equalsIgnoreCase(loginVO.getCaptcha(), redisCaptcha)) {
                throw new CustomException("验证码不正确或已过期，请重试！");
            }
        }
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("username", loginVO.getUsername());
        User user = userMapper.selectOne(userQueryWrapper);
        if (ObjectUtils.isEmpty(user)) {
            throw new CustomException("该用户不存在，请重试！");
        }
        String loginEncodePassword = SHA256Utils.encode(loginVO.getPassword(), user.getSalt());
        if (!StringUtils.equals(user.getPassword(), loginEncodePassword)) {
            throw new CustomException("密码错误，请重试！");
        }
//        user.setLastOnlineTime(LocalDateTime.now());
//        userMapper.updateById(user);
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);
        String token = jwtUtils.generateJwt(userVO);
//        Cookie cookie = new Cookie("JWT-TOKEN", token);
//        cookie.setSecure(true);
//        cookie.setHttpOnly(true);
//        cookie.setMaxAge(7 * 60 * 60 * 24);
//        cookie.setPath("/");
//        response.addCookie(cookie);
//        userVO.setToken(token);
        return token;
    }

    @Override
    public void register(RegisterVO registerVO, HttpServletRequest request) {
        String uniqueId = request.getHeader(CommonConstants.X_UNIQUE_ID);
        String redisEmailCode = StringUtils.isEmpty(redisComponent.getEmailCode(uniqueId)) ? "1" : redisComponent.getEmailCode(uniqueId);
        if (!StringUtils.equals(registerVO.getEmailValidCode(), redisEmailCode)) {
            throw new CustomException("邮箱验证码不正确或已过期，请重试！");
        }
        if (!StringUtils.equals(registerVO.getPassword(), registerVO.getRepassword())) {
            throw new CustomException("两次密码输入不一致！");
        }
        User registerUser = new User();
        BeanUtils.copyProperties(registerVO, registerUser);
        String password = registerUser.getPassword();
        String salt = SHA256Utils.generateSalt();
        registerUser.setSalt(salt);
        String encodePassword = SHA256Utils.encode(password, salt);
        registerUser.setPassword(encodePassword);
        //默认先给用户昵称为账号
        registerUser.setUserNickname(registerUser.getUsername());
        //用户注册初始给1GB
        registerUser.setTotalDiskSpace(CommonConstants.ONE_GB);
        try {
            userMapper.insert(registerUser);
        } catch (DuplicateKeyException ex) {
            throw new CustomException("注册用户(账号或邮箱)已存在！");
        } catch (Exception e) {
            throw new CustomException("注册用户失败,请重试!");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void logout(HttpServletRequest request) {
        String jwtToken = request.getHeader(CommonConstants.HEADER_TOKEN).substring(7);
        Claims claims = jwtUtils.decodedJWT(jwtToken);
        String jwtUuid = claims.getId();
        Date expiresAt = claims.getExpiration();
        jwtUtils.deleteJwtToken(jwtUuid, expiresAt);
        Long userId =(Long) claims.get("id");
        User user = userMapper.selectById(userId);
        if (ObjectUtils.isEmpty(user)) {
            throw new CustomException("该用户不存在！");
        }
        user.setLastOnlineTime(LocalDateTime.now());
        userMapper.updateById(user);
    }

    @Override
    public UsedDiskSpaceDTO usedDiskSpace(Long userId) {
        User user = userMapper.selectById(userId);
        if (ObjectUtils.isEmpty(user)) {
            throw new CustomException("该用户不存在！");
        }
        Long usedDiskSpace = user.getUsedDiskSpace();
        Long totalDiskSpace = user.getTotalDiskSpace();
        float rate = FileSizeConverter.calculateUsedSpaceRate(usedDiskSpace, totalDiskSpace);
        String usedSpace = FileSizeConverter.convertBytesToReadableSize(usedDiskSpace);
        String totalSpace = FileSizeConverter.convertBytesToReadableSize(totalDiskSpace);
        UsedDiskSpaceDTO usedDiskSpaceDTO = new UsedDiskSpaceDTO();
        usedDiskSpaceDTO.setUsedSpaceRate(rate);
        usedDiskSpaceDTO.setUsedDiskSpace(usedSpace);
        usedDiskSpaceDTO.setTotalDiskSpace(totalSpace);
        return usedDiskSpaceDTO;
    }

    @Override
    public void sendEmailVerifyCode(String email, String uniqueId) {
        User user = userMapper.selectOne(new QueryWrapper<User>().eq("email", email));
        if (!Objects.isNull(user)) {
            throw new CustomException("邮箱已被注册");
        }
        String verifyCode = String.format("%06d", (int) (Math.random() * 1000000));
        redisComponent.saveEmailCode(uniqueId, verifyCode);
        emailUtils.sendEmail(email, "[CloudDisk网盘系统]短信验证", buildEmailContent(verifyCode));
    }


    @Override
    public UserInfoDTO getUserInfo(Long userId) {
        User user = userMapper.selectById(userId);
        if (ObjectUtils.isEmpty(user)) {
            throw new CustomException("该用户不存在");
        }
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        BeanUtils.copyProperties(user, userInfoDTO);
        return userInfoDTO;
    }


    private String buildEmailContent(String verifyCode) {
        StringBuilder builder = new StringBuilder();
        builder.append("[CloudDisk网盘系统] 亲爱的用户，您的邮箱验证码为: ");
        builder.append(verifyCode);
        builder.append("。请在10分钟内，输入此验证码进行验证。如果您没有请求该系统邮箱验证码，请忽略此邮件，谢谢！[CHIIIPLOW]");
        return builder.toString();
    }
}
