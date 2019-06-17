package com.yooy.mall.admin.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.apache.shiro.realm.Realm;

/**
 * @author: util.youl.com@gmail.com
 * @date: 2019/6/17 22:22
 * @description:
 * @version: 1.0
 * @className: ShiroConfig
 */
@Configuration
public class ShiroConfig {

    @Bean
    public Realm realm(){

        return new AdminAuthorizingRealm();
    }
}
