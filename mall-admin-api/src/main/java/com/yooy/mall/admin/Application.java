package com.yooy.mall.admin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author: util.youl.com@gmail.com
 * @date: 2019/6/17 23:12
 * @description:
 * @version: 1.0
 * @className: Application
 */
@SpringBootApplication(scanBasePackages = {"com.yooy.mall.db",
"com.yooy.mall.core", "com.yooy.mall.admin"})
@MapperScan("com.yooy.mall.db.dao")
@EnableTransactionManagement
@EnableScheduling
public class Application {

    public static void main(String[] args){

        SpringApplication.run(Application.class, args);
    }
}
