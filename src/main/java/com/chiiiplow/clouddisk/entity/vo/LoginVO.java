package com.chiiiplow.clouddisk.entity.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * 登录 vo
 *
 * @author CHIIIPLOW
 * @date 2024/06/08
 */
@Data
public class LoginVO implements Serializable {

    @NotBlank(message = "用户账号不能为空")
    @Size(message = "账号长度不能超过6~35范围", min = 6, max = 35)
    private String username;

    @NotBlank(message = "用户密码不能为空")
    @Size(message = "密码长度不能超过6~35位范围", min = 6, max = 35)
    private String password;


    @Size(message = "图片验证码必须为4位", min = 4, max = 4)
    private String captcha;

    @NotNull(message = "是否显示验证码不能为空")
    private Boolean showCaptcha;
}
