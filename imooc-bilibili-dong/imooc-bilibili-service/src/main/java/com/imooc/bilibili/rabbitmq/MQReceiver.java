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
     * 更新动态时 通知所有关注的用户
     * @param message
     */
    @RabbitListener(queues = "bilibiliQueue")
    public void receive(String message) {
        log.info("接收的消息：" + message);
        //把json数据流转为对象
        UserMoment userMoment = JSONObject.parseObject(message, UserMoment.class);
        Long userId = userMoment.getUserId();
        //查询出所有的粉丝
        List<UserFollowing> userFan = userFollowingService.getFollowingByUserId(userId);
        for (UserFollowing fan : userFan) {
            //对于每一个粉丝都在redis中存入
            String key="subscribed-"+fan.getFollowingId();

            String subscribedListStr = redisTemplate.opsForValue().get(key);
            List<UserMoment> subscribedList;
            //如果这个用户已经存在redis 就将这个UserMoment对象转为Json存入list
            if (StringUtil.isNullOrEmpty(subscribedListStr)){
                subscribedList=new ArrayList<>();
            }else {
                subscribedList= JSONArray.parseArray(subscribedListStr,UserMoment.class);
            }
            subscribedList.add(userMoment);
            //把list存到redis里面
            redisTemplate.opsForValue().set(key,JSONObject.toJSONString(subscribedList),1L,TimeUnit.DAYS );
        }

      }
    @RabbitListener(queues = "DanmuQueue")
    public void danmuReceive(String message) {
        log.info("接收的消息：" + message);
        JSONObject jsonObject = JSONObject.parseObject(message, JSONObject.class);
        String sessionId = jsonObject.getString("sessionId");
        String messageDanmu = jsonObject.getString("message");
        //调用全局的map 来拿出来属于这个用户的长连接
        WebSocketService webSocketService = WebSocketService.WEBSOCKET_MAP.get(sessionId);
        //如果已经建立连接
        if (webSocketService.getSession().isOpen()){
            try {
                //发送信息到前端
                webSocketService.sendMessage(messageDanmu);
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}
