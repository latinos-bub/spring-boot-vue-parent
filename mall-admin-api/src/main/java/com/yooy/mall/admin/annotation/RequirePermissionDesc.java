package com.yooy.mall.admin.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author: util.youl.com@gmail.com
 * @date: 2019/6/17 22:18
 * @description:
 * @version: 1.0
 * @className: RequirePermissionDesc
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RequirePermissionDesc {

    String[] menu();

    String button();
}
