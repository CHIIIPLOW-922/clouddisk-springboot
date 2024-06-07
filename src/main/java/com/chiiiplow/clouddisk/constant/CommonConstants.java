package com.chiiiplow.clouddisk.constant;

/**
 * 常用常量
 *
 * @author yangzhixiong
 * @date 2024/06/07
 */
public interface CommonConstants {

    long ONE_SECOND = 1000L;

    long ONE_MINUTE = 60 * ONE_SECOND;

    long ONE_HOUR = 60 * ONE_MINUTE;

    long ONE_DAY = 24 * ONE_HOUR;

    long ONE_WEEK = 7 * ONE_DAY;

    long ONE_MONTH = 30 * ONE_DAY;

    long ONE_YEAR = 365 * ONE_DAY;

    String JWT_SECRET_KEY = "chiiiplow";
}
