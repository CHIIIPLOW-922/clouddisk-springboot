package com.chiiiplow.clouddisk.entity.vo;

import lombok.Data;

/**
 * 注册 VO
 *
 * @author CHIIIPLOW
 * @date 2024/06/08
 */
@Data
public class RegisterVO {


    private String username;

    private String password;

    private String repassword;

    private String email;


}
