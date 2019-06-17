package com.yooy.mall.admin.config;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.AuthorizationException;
import com.yooy.mall.core.util.ResponseUtil;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author: util.youl.com@gmail.com
 * @date: 2019/6/17 22:28
 * @description:
 * @version: 1.0
 * @className: ShiroExceptionHandler
 */
@ControllerAdvice
@Order(value = Ordered.HIGHEST_PRECEDENCE)
public class ShiroExceptionHandler {

    @ExceptionHandler(AuthenticationException.class)
    @ResponseBody
    public Object unAuthenticatedHandler(AuthenticationException e){

        e.printStackTrace();
        return ResponseUtil.unlogin();
    }



    @ExceptionHandler(AuthorizationException.class)
    @ResponseBody
    public Object unauthorizedHandler(AuthorizationException e) {
        e.printStackTrace();
        return ResponseUtil.unauthz();
    }

}
