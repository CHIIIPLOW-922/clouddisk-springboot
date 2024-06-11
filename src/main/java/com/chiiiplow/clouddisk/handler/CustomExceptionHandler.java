package com.chiiiplow.clouddisk.handler;


import com.chiiiplow.clouddisk.common.R;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.util.Set;
import java.util.stream.Collectors;


@RestControllerAdvice
public class CustomExceptionHandler {


    @ExceptionHandler(Exception.class)
    public <T> R<T> throwCommonError(Exception e) {
        R<T> r = new R();
        r.setCode(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        r.setMsg(e.getMessage());

        return r;
    }

    @ExceptionHandler(value = {BindException.class, ValidationException.class, MethodArgumentNotValidException.class})
    public <T> R<T> throwValidationErrors(Exception e) {
        R<T> r = new R<>();
        r.setCode(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        String message = "未知错误";
        if (e instanceof MethodArgumentNotValidException) {
            // BeanValidation exception
            MethodArgumentNotValidException ex = (MethodArgumentNotValidException) e;
            FieldError fieldError = ex.getBindingResult().getFieldErrors().get(0);
            if (!ObjectUtils.isEmpty(fieldError)) {
                message = fieldError.getDefaultMessage();
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
