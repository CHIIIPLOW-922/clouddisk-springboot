package com.chiiiplow.clouddisk.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户
 *
 * @author CHIIIPLOW
 * @date 2024/06/03
 */
@Data
@TableName("user")
public class User implements Serializable {

    /**
     * user id
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * username
     */
    private String username;

    /**
     * nickname
     */
    private String userNickname;

    /**
     * avatar path
     */
    private String userAvatarPath;

    /**
     * email
     */
    private String email;

    /**
     * password
     */
    private String password;

    /**
     * salt
     */
    private String salt;

    /**
     * register time
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime registerTime;

    /**
     * last online time
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastOnlineTime;

    /**
     * used space
     */
    private Long usedDiskSpace;

    /**
     * total space
     */
    private Long totalDiskSpace;
}
