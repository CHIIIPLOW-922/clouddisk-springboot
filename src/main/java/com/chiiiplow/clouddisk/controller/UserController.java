package com.chiiiplow.clouddisk.controller;

import com.chiiiplow.clouddisk.utils.CaptchaUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

@RestController("userController")
@RequestMapping("/user")
public class UserController {


    @GetMapping("/generateCaptcha")
    public void generateCaptcha(HttpServletResponse response) throws IOException {
        // Generate captcha text
        String captchaText = CaptchaUtils.generateCaptchaText(4);


        BufferedImage captchaImage = CaptchaUtils.generateCaptchaImage(captchaText);
        // Set response headers
        response.setContentType("image/png");
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);

        // Write the image to the response output stream
        ImageIO.write(captchaImage, "png", response.getOutputStream());
    }


}
