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


}

