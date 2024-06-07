package com.chiiiplow.clouddisk.common;


import lombok.Data;

import java.io.Serializable;

/**
 * 接口返回数据格式
 * @author wyssss
 */
@Data
public class Result<T> implements Serializable {

    private static final long serialVersionUID = 1L;


    private boolean success = true;

    private String message = "操作成功！";


    private Integer code = 200;

    /**
     * 返回数据对象 data
     */
    private T result;

    /**
     * 时间戳
     */
    private long timestamp = System.currentTimeMillis();

    public Result() {

    }

    public Result<T> success(String message) {
        this.message = message;
        this.code = 200;
        this.success = true;
        return this;
    }

    public static Result<Object> ok() {
        Result<Object> r = new Result<Object>();
        r.setSuccess(true);
        r.setCode(200);
        r.setMessage("成功");
        return r;
    }

    public static Result<Object> ok(String msg) {
        Result<Object> r = new Result<Object>();
        r.setSuccess(true);
        r.setCode(200);
        r.setMessage(msg);
        return r;
    }

    public static Result<Object> ok(Object data) {
        Result<Object> r = new Result<Object>();
        r.setSuccess(true);
        r.setCode(200);
        r.setResult(data);
        return r;
    }

    public static Result<Object> ok(String msg, Object data) {
        Result<Object> r = new Result<Object>();
        r.setSuccess(true);
        r.setCode(200);
        r.setResult(data);
        r.setMessage(msg);
        return r;
    }

    public static Result<Object> error(String msg,Object obj) {
        return error(500, msg,obj);
    }

    public static Result<Object> error(String msg) {
        return error(500, msg);
    }


    public static Result<Object> error(int code, String msg) {
        Result<Object> r = new Result<Object>();
        r.setCode(code);
        r.setMessage(msg);
        r.setSuccess(false);
        return r;
    }


    public static Result<Object> error(int code, String msg,Object obj) {
        Result<Object> r = new Result<Object>();
        r.setCode(code);
        r.setMessage(msg);
        r.setSuccess(false);
        r.setResult(obj);
        return r;
    }

    public Result<T> error500(String message) {
        this.message = message;
        this.code = 500;
        this.success = false;
        return this;
    }



}