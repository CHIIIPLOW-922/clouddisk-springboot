package com.chiiiplow.clouddisk.constant;

/**
 * 响应枚举
 *
 * @author yangzhixiong
 * @date 2024/06/04
 */
public enum ApiResponseEnum {

    OPERATION_SUCCESS(200, "操作成功"),

    OPERATION_FAIL(400, "操作失败"),

    CANNOT_FIND(404, "找不到该资源"),

    ;




    private Integer code;

    private String msg;

    ApiResponseEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

}
