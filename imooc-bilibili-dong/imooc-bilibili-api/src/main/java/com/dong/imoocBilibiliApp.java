package com.dong;


import com.imooc.bilibili.service.websocket.WebSocketService;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
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
        ApplicationContext app = SpringApplication.run(imoocBilibiliApp.class, args);
        //在这里给多例bean WebSocket中的ApplicationContext赋值
        WebSocketService.setApplicationContext(app);
    }
}
