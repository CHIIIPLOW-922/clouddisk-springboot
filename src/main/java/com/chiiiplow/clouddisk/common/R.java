package com.chiiiplow.clouddisk.common;

import com.chiiiplow.clouddisk.common.enums.HttpCodeEnum;
import lombok.Data;


/**
 * 接口返回类
 *
 * @author yangzhixiong
 * @date 2024/06/04
 */
@Data
public class R<T> {

    private int code;

    private String msg;

    private T data;

    public static final int SUCCESS = HttpCodeEnum.SUCCESS.getCode();
    public static final String SUCCESS_MSG = HttpCodeEnum.SUCCESS.getMsg();

    public static final int FAIL = HttpCodeEnum.FAIL.getCode();
    public static final String FAIL_MSG = HttpCodeEnum.FAIL.getMsg();


    public static <T> R<T> ok() {
        return restResult(null, SUCCESS, SUCCESS_MSG);
    }

    public static <T> R<T> ok(T data) {
        return restResult(data, SUCCESS, SUCCESS_MSG);
    }

    public static <T> R<T> ok(T data, String msg) {
        return restResult(data, SUCCESS, msg);
    }

    public static <T> R<T> ok(int code, T data, String msg) {
        return restResult(data, code, msg);
    }

    public static <T> R<T> http(HttpCodeEnum httpCodeEnum, T data) {
        return restResult(data, httpCodeEnum.getCode(), httpCodeEnum.getMsg());
    }

    public static <T> R<T> fail() {
        return restResult(null, FAIL, FAIL_MSG);
    }

    public static <T> R<T> fail(String msg) {
        return restResult(null, FAIL, msg);
    }

    public static <T> R<T> fail(T data) {
        return restResult(data, FAIL, FAIL_MSG);
    }

    public static <T> R<T> fail(T data, String msg) {
        return restResult(data, FAIL, msg);
    }

    public static <T> R<T> fail(int code, String msg) {
        return restResult(null, code, msg);
    }

    public static <T> R<T> fail(int code, T data, String msg) {
        return restResult(data, code, msg);
    }

    private static <T> R<T> restResult(T data, int code, String msg) {
        R<T> apiResult = new R<>();
        apiResult.setCode(code);
        apiResult.setData(data);
        apiResult.setMsg(msg);
        return apiResult;
    }


}

