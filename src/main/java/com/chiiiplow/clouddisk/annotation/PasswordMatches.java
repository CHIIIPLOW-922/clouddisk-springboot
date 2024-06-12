package com.chiiiplow.clouddisk.annotation;

import com.chiiiplow.clouddisk.handler.PasswordMatchesValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PasswordMatchesValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface PasswordMatches {
    String message() default "二次确认密码与密码不一致!";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
