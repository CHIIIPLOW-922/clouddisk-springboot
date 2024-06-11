package com.chiiiplow.clouddisk.constant;

import java.util.regex.Pattern;

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


    String X_UNIQUE_ID = "X-Unique-Id";

    String HEADER_TOKEN = "Authorization";

    String EMAIL_PATTERN = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

}
