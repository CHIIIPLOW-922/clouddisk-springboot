package com.chiiiplow.clouddisk.entity.vo;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * 注册 VO
 *
 * @author CHIIIPLOW
 * @date 2024/06/08
 */
@Data
public class RegisterVO {

    @NotBlank(message = "注册账号不能为空")
    @Pattern(regexp = "^[a-zA-Z0-9]{6,25}$", message = "注册账号只能使用数字和字母")
    @Size(min = 6, max = 25, message = "账号长度必须在6~25位")
    private String username;

    @NotBlank(message = "注册密码不能为空")
    @Size(min = 8, max = 25, message = "账号长度必须在8~25位")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,25}$", message = "注册密码必须数字和字母组合")
    private String password;

    @NotBlank(message = "注册密码不能为空")
    @Size(min = 8, max = 25, message = "账号长度必须在8~25位")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,25}$", message = "注册密码必须数字和字母组合")
    private String repassword;


    @NotBlank(message = "注册邮箱不能为空")
    @Email(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "邮箱格式不正确")
    private String email;

    @NotBlank(message = "邮箱验证码不能为空")
    private String emailValidCode;


}
