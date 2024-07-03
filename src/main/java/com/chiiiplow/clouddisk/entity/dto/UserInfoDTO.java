package com.chiiiplow.clouddisk.entity.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户 DTO
 *
 * @author yangzhixiong
 * @date 2024/07/03
 */
@Data
public class UserInfoDTO implements Serializable {

    private String username;

    private String userNickname;

    private String userAvatarPath;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastOnlineTime;

    private String email;
}
