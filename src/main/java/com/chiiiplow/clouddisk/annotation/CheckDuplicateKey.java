package com.chiiiplow.clouddisk.annotation;

import com.chiiiplow.clouddisk.handler.DuplicateKeysValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;


@Documented
@Constraint(validatedBy = DuplicateKeysValidator.class)
@Target({ ElementType.TYPE, ElementType.FIELD, ElementType.PARAMETER, ElementType.TYPE_PARAMETER, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface CheckDuplicateKey {

    String message() default "字段冲突";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
