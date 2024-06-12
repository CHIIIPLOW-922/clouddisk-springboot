package com.chiiiplow.clouddisk.handler;

import com.chiiiplow.clouddisk.annotation.PasswordMatches;
import com.chiiiplow.clouddisk.entity.vo.RegisterVO;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {

    @Override
    public void initialize(PasswordMatches constraintAnnotation) {
    }

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context) {
        RegisterVO registerVO = (RegisterVO) obj;
        return registerVO.getPassword().equals(registerVO.getRepassword());
    }
}