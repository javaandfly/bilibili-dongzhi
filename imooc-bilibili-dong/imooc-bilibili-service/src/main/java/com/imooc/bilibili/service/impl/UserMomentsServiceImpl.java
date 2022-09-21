package com.imooc.bilibili.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.imooc.bilibili.domain.UserMoment;
import com.imooc.bilibili.mapper.UserMomentsMapper;

import com.imooc.bilibili.rabbitmq.MQSender;
import com.imooc.bilibili.service.UserMomentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class UserMomentsServiceImpl extends ServiceImpl<UserMomentsMapper, UserMoment> implements UserMomentsService {
    @Autowired
   private  UserMomentsMapper           userMomentsMapper;
    @Autowired
    @Lazy
    private MQSender                    mqSender;
    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    @Override
    public void addUserMoments(UserMoment userMoment) {
    userMoment.setCreateTime(new Date());
    this.save(userMoment);
    //只要有动态就通过MQ发个每个用户
    mqSender.sendMessage(JSONObject.toJSONString(userMoment));
    }

    @Override
    public List<UserMoment> getUserSubscribedMoments(Long userId) {
        String key="subscribed-"+userId;
        //从redis中拿数据
        String s = redisTemplate.opsForValue().get(key);
        redisTemplate.delete(key);
        return JSONArray.parseArray(s, UserMoment.class);
    }
}
