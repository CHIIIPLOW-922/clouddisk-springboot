package com.chiiiplow.clouddisk.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.util.IdGenerator;

import java.io.Serializable;
import java.util.Date;

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
    private String userName;

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
     * register time
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date registerTime;

    /**
     * last online time
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lastOnlineTime;

    /**
     * used space
     */
    private Long usedDiskSpace;

    /**
     * total space
     */
    private Long totalDiskSpace;
}
