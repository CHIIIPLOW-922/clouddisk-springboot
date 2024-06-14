package com.chiiiplow.clouddisk.annotation;


import java.lang.annotation.*;

/**
 * @author CHIIIPLOW
 */
@Documented
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface AccessLimit {

    String key() default "unknown";

    int count() default 20;

}
