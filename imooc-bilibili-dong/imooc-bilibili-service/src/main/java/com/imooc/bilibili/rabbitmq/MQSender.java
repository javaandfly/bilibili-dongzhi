package com.imooc.bilibili.rabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@Slf4j
public class MQSender {
    @Autowired
    RabbitTemplate rabbitTemplate;

    /**
     * 发送广播信息 更新视频
     *
     * @param message
     */
    public void sendMessage(String message) {
        log.info("发送消息：" + message);
        rabbitTemplate.convertAndSend("bilibiliExchange", "bilibili.message", message);
    }
    public void sendDanmuMessage(String message) {
        log.info("发送消息：" + message);
        rabbitTemplate.convertAndSend("DanmuExchange", "bilibiliDanmu.message", message);
    }


}
