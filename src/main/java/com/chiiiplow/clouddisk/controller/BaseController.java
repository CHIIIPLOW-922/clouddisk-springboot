package com.chiiiplow.clouddisk.controller;

import com.chiiiplow.clouddisk.common.R;
import com.chiiiplow.clouddisk.constant.MessageConstants;

import javax.servlet.http.HttpServletResponse;

/**
 * 通用控制层
 *
 * @author CHIIIPLOW
 * @date 2024/06/07
 */
public class BaseController {


    public <T> R successResult(T data) {
        R<T> r = new R<>();
        r.setCode(HttpServletResponse.SC_OK);
        r.setMsg(MessageConstants.SUCCESS);
        r.setData(data);
        return r;
    }


    public <T> R successResult() {
        R<T> r = new R<>();
        r.setCode(HttpServletResponse.SC_OK);
        r.setMsg(MessageConstants.SUCCESS);
        return r;
    }

    public <T> R successResult(String msg, T data) {
        R<T> r = new R<>();
        r.setCode(HttpServletResponse.SC_OK);
        r.setMsg(msg);
        r.setData(data);
        return r;
    }
}
