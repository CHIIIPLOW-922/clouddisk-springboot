package com.chiiiplow.clouddisk.handler;


import com.chiiiplow.clouddisk.common.R;
import com.chiiiplow.clouddisk.exception.CustomException;
import jdk.nashorn.internal.runtime.options.Option;
import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * 自定义异常处理
 *
 * @author yangzhixiong
 * @date 2024/07/04
 */
@RestControllerAdvice
public class CustomExceptionHandler {


    @ExceptionHandler(Exception.class)
    public <T> R<T> throwCommonError(Exception e) {
        R<T> r = new R();
        String message = null;
        Integer code = null;
        r.setCode(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        if (e instanceof CustomException) {
            Integer custCode = Objects.isNull(((CustomException) e).getCode()) ? null : ((CustomException) e).getCode();
            code = custCode;
            message = e.getMessage();
        } else if (e instanceof HttpMessageNotReadableException || e instanceof HttpRequestMethodNotSupportedException) {
            message = "请求参数或请求方式有错误";
        } else if (e instanceof NoHandlerFoundException) {
            code = HttpServletResponse.SC_NOT_FOUND;
            message = "请求接口不存在";
        } else if (e instanceof DuplicateKeyException) {
            message = "请求参数中，有字段与数据库冲突！";
        }
        String error = StringUtils.isEmpty(message) ? "未知错误" : message;
        int errorCode = ObjectUtils.isEmpty(code) ? HttpServletResponse.SC_INTERNAL_SERVER_ERROR : code;
        r.setCode(errorCode);
        r.setMsg(error);
        return r;

    }

    @ExceptionHandler(value = {BindException.class, ValidationException.class, MethodArgumentNotValidException.class})
    public <T> R<T> throwValidationErrors(Exception e) {
        R<T> r = new R<>();
        r.setCode(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        String message = "字段校验错误";
        if (e instanceof MethodArgumentNotValidException) {
            // BeanValidation exception
            MethodArgumentNotValidException ex = (MethodArgumentNotValidException) e;
            ObjectError objectError = ex.getBindingResult().getAllErrors().get(0);
            if (!ObjectUtils.isEmpty(objectError)) {
                message = objectError.getDefaultMessage();
            }
        } else if (e instanceof ConstraintViolationException) {
            // BeanValidation GET simple param
            Set<ConstraintViolation<?>> constraintViolations = ((ConstraintViolationException) e).getConstraintViolations();
            String constraintMessage = constraintViolations.stream().map(ConstraintViolation::getMessage).findFirst().orElse(null);
            if (!StringUtils.isEmpty(constraintMessage)) {
                message = constraintMessage;
            }
        } else if (e instanceof BindException) {
            // BeanValidation GET object param
            BindException ex = (BindException) e;
            String bindMessage = ex.getAllErrors().stream().map(item -> item.getDefaultMessage()).collect(Collectors.toList()).get(0);
            if (!StringUtils.isEmpty(bindMessage)) {
                message = bindMessage;
            }

        }
        r.setMsg(message);
        return r;
    }


}
