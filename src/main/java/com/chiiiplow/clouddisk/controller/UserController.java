package com.chiiiplow.clouddisk.controller;


import com.chiiiplow.clouddisk.common.R;
import com.chiiiplow.clouddisk.component.RedisComponent;
import com.chiiiplow.clouddisk.constant.CommonConstants;
import com.chiiiplow.clouddisk.entity.vo.LoginVO;
import com.chiiiplow.clouddisk.entity.vo.RegisterVO;
import com.chiiiplow.clouddisk.entity.vo.UserVO;
import com.chiiiplow.clouddisk.exception.CustomException;
import com.chiiiplow.clouddisk.service.UserService;
import com.chiiiplow.clouddisk.utils.CaptchaUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController("userController")
@RequestMapping("/user")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;


    @Autowired
    private RedisComponent redisComponent;


    @GetMapping("/generateCaptcha")
    public R generateCaptcha(HttpServletRequest request) throws IOException {
        String uniqueId = request.getHeader(CommonConstants.X_UNIQUE_ID);
        // Generate captcha text
        String captchaText = CaptchaUtils.generateCaptchaText(4);

        redisComponent.saveCaptchaKey(uniqueId, captchaText);

        String captchaImage = CaptchaUtils.generateCaptchaImage(captchaText);

        return successResult(captchaImage);

    }


    @PostMapping("/register")
    public R register(@RequestBody @Validated RegisterVO registerVO, HttpServletRequest request) {
        return new R();

    }

    @PostMapping("/login")
    public R login(@RequestBody @Validated LoginVO loginVO, HttpServletRequest request) {
        UserVO userVO = userService.login(loginVO, request);
        return successResult(userVO);
    }


    @PostMapping("/sendEmail")
    public R sendEmail(@RequestParam("email") String email, HttpServletRequest request) {

        String uniqueId = request.getHeader(CommonConstants.X_UNIQUE_ID);
        if (StringUtils.isEmpty(uniqueId)) {
            throw new CustomException("请求头不存在唯一ID");
        }
        if (StringUtils.isEmpty(email) || !email.matches(CommonConstants.EMAIL_PATTERN)) {
            throw new CustomException("\"邮箱为空\"或\"邮箱格式不正确\"");
        }
        userService.sendEmailVerifyCode(email, uniqueId);
        return successResult(null);

    }



}
