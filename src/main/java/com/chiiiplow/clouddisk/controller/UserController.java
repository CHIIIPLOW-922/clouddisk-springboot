package com.chiiiplow.clouddisk.controller;

import com.chiiiplow.clouddisk.common.R;
import com.chiiiplow.clouddisk.common.Result;
import com.chiiiplow.clouddisk.component.RedisComponent;
import com.chiiiplow.clouddisk.exception.CustomException;
import com.chiiiplow.clouddisk.utils.CaptchaUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

@RestController("userController")
@RequestMapping("/user")
public class UserController {

    private static final String X_UNIQUE_ID = "X-Unique-ID";

    @Autowired
    private RedisComponent redisComponent;


    @GetMapping("/generateCaptcha")
    public Result generateCaptcha(HttpServletResponse response, HttpServletRequest request) throws IOException {
        // Generate captcha text
        String captchaText = CaptchaUtils.generateCaptchaText(4);


        BufferedImage captchaImage = CaptchaUtils.generateCaptchaImage(captchaText);


        byte[] imageBytes = null;
        // 将 BufferedImage 转换为字节数组
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()){
            ImageIO.write(captchaImage, "png", byteArrayOutputStream);
            imageBytes = byteArrayOutputStream.toByteArray();
        }
        // Write the image to the response output stream

        // 将字节数组编码为 Base64 字符串
        String base64Image = Base64.getEncoder().encodeToString(imageBytes);

        // 构建 data:image 格式字符串
        String dataImage = "data:image/png;base64," + base64Image;
        return Result.ok("操作成功", dataImage);

    }



}
