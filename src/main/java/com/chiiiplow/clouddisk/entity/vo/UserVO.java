package com.chiiiplow.clouddisk.entity.vo;

import lombok.Data;


@Data
public class UserVO {

    /**
     * user id
     */
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
     * used space
     */
    private Long usedDiskSpace;

    /**
     * total space
     */
    private Long totalDiskSpace;

    /**
     * 令 牌
     */
    private String token;
}
