package com.imooc.bilibili.domain.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;
//在运行阶段
@Retention(RetentionPolicy.RUNTIME)
//放在方法上面
@Target({ElementType.METHOD})
@Documented
@Component
public @interface ApiLimitedRole {

    String[] limitedRoleCodeList() default {};
}
