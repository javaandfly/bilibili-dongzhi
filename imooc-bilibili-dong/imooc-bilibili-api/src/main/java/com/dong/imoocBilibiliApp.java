package com.dong;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@MapperScan("com.imooc.bilibili.mapper")
@ComponentScan("com.imooc.bilibili")
@ComponentScan("com.dong.bilibili.Api")
@EnableAsync
@EnableScheduling
@EnableTransactionManagement
@EnableEurekaClient
@EnableFeignClients(basePackages = "com.imooc.bilibili.service.feign")
public class imoocBilibiliApp {
    public static void main(String[] args) {
        SpringApplication.run(imoocBilibiliApp.class,args);
    }
}
