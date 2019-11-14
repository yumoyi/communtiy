package com.cy.community.annotation;

import java.lang.annotation.*;

/**
 * 自定义注解:@RequireLogin
 * @author cy
 * @since 2019-11-10 14:39
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequireLogin {
}
