package com.chiiiplow.clouddisk.utils;

import org.apache.commons.codec.digest.DigestUtils;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.Random;

/**
 * SHA256工具类
 *
 * @author yangzhixiong
 * @date 2024/06/04
 */
public class SHA256Utils {



    public static String generateSalt() {
        // 创建一个 SecureRandom 对象
        SecureRandom random = new SecureRandom();
        // 生成一个足够长的盐值，通常至少 16 个字节
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        // 将盐值转换为十六进制字符串
        return Base64.getEncoder().encodeToString(salt);
    }

    public static String encode(String originalPassword, String salt) {
        String saltedPassword = originalPassword + salt;
        return DigestUtils.sha256Hex(saltedPassword);
    }


}
