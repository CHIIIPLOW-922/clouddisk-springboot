package com.chiiiplow.clouddisk.common;

import lombok.Data;


/**
 * 接口返回类
 *
 * @author yangzhixiong
 * @date 2024/06/04
 */
@Data
public class R<T> {

    private Integer code;

    private String msg;

    private T data;

    // 构造函数
    public R() {
    }

    // 成功时的构造函数
    public R(T data) {
        this.code = 200;
        this.msg = "操作成功";
        this.data = data;
    }

    // 错误时的构造函数
    public R(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
        this.data = null;
    }

    // 静态工厂方法
    public static <T> R<T> success(T data) {
        return new R<>(data);
    }

    public static <T> R<T> error(Integer code, String msg) {
        return new R<>(code, msg);
    }
}

