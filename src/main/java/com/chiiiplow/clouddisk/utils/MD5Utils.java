package com.chiiiplow.clouddisk.utils;

import org.apache.commons.codec.digest.DigestUtils;

import java.util.Random;

/**
 * MD5工具类
 *
 * @author yangzhixiong
 * @date 2024/06/04
 */
public class MD5Utils {

    public static String generateSalt() {
        Random random = new Random();
        StringBuilder salt = new StringBuilder();
        for (int i = 0; i < 16; i++) {
            salt.append(Integer.toHexString(random.nextInt(16)));
        }
        return salt.toString();
    }

    public static String encode(String originalPassword, String salt) {
        String saltedPassword = originalPassword + salt;
        return DigestUtils.md5Hex(saltedPassword);
    }


}
