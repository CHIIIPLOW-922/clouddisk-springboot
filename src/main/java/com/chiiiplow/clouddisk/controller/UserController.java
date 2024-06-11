package com.chiiiplow.clouddisk.controller;


import com.chiiiplow.clouddisk.common.R;
import com.chiiiplow.clouddisk.component.RedisComponent;
import com.chiiiplow.clouddisk.constant.CommonConstants;
import com.chiiiplow.clouddisk.entity.vo.LoginVO;
import com.chiiiplow.clouddisk.entity.vo.RegisterVO;
import com.chiiiplow.clouddisk.entity.vo.UserVO;
import com.chiiiplow.clouddisk.service.UserService;
import com.chiiiplow.clouddisk.utils.CaptchaUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

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



}
