package com.chiiiplow.clouddisk.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 管理
 *
 * @author CHIIIPLOW
 * @date 2024/06/03
 */
@Data
@TableName("admin")
public class Admin implements Serializable {

    /**
     * admin id
     */
    //雪花算法生成ID
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * admin account
     */
    private String adminAccount;

    /**
     * admin password
     */
    private String adminPassword;

    /**
     * salt
     */
    private String salt;

    /**
     * admin nickname
     */
    private String adminNickname;

    /**
     * create time
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * modify time
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date modifyTime;
}
