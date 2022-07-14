package com.imooc.bilibili.rabbitmq;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.imooc.bilibili.domain.UserFollowing;
import com.imooc.bilibili.domain.UserMoment;
import com.imooc.bilibili.service.UserFollowingService;
import com.imooc.bilibili.service.websocket.WebSocketService;
import io.netty.util.internal.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class MQReceiver {
    @Autowired
    private UserFollowingService userFollowingService;
    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    /**
     * 通知操作
     */
    @RabbitListener(queues = "bilibiliQueue")
    public void receive(String message) {
        log.info("接收的消息：" + message);
        UserMoment userMoment = JSONObject.parseObject(message, UserMoment.class);
        Long userId = userMoment.getUserId();
        List<UserFollowing> userFan = userFollowingService.getFollowingByUserId(userId);
        for (UserFollowing fan : userFan) {
            String key="subscribed-"+fan.getFollowingId();
            String subscribedListStr = redisTemplate.opsForValue().get(key);
            List<UserMoment> subscribedList;
            if (StringUtil.isNullOrEmpty(subscribedListStr)){
                subscribedList=new ArrayList<>();
            }else {
                subscribedList= JSONArray.parseArray(subscribedListStr,UserMoment.class);
            }
            subscribedList.add(userMoment);
            redisTemplate.opsForValue().set(key,JSONObject.toJSONString(subscribedList),1L,TimeUnit.DAYS );
        }

      }
    @RabbitListener(queues = "DanmuQueue")
    public void danmuReceive(String message) {
        log.info("接收的消息：" + message);
        JSONObject jsonObject = JSONObject.parseObject(message, JSONObject.class);
        String sessionId = jsonObject.getString("sessionId");
        String messageDanmu = jsonObject.getString("message");
        WebSocketService webSocketService = WebSocketService.WEBSOCKET_MAP.get(sessionId);
        if (webSocketService.getSession().isOpen()){
            try {
                webSocketService.sendMessage(messageDanmu);
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}
