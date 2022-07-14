package com.imooc.bilibili.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQTopicConfig {
    private static final String QUEUE = "bilibiliQueue";
    private static final String EXCHANGE = "bilibiliExchange";
    private static final String DANMUQUEUE="DanmuQueue";
    private static final String DANMUEXCHANGE="DanmuExchange";
    @Bean
    public Queue queue() {
        return new Queue(QUEUE);
    }

    @Bean
    public Queue Danmuqueue() {
        return new Queue(DANMUQUEUE);
    }

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(EXCHANGE);
    }

    @Bean
    public TopicExchange topicDanmuExchange() {
        return new TopicExchange(DANMUEXCHANGE);
    }

    @Bean
    public Binding binding() {
        return BindingBuilder.bind(queue()).to(topicExchange()).with("bilibili.#");
    }

    @Bean
    public Binding danmuDinding() {
        return BindingBuilder.bind(queue()).to(topicExchange()).with("bilibiliDanmu.#");
    }
}

