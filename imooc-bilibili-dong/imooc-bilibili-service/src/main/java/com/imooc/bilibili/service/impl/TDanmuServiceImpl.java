package com.imooc.bilibili.service.impl;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.imooc.bilibili.domain.TDanmu;
import com.imooc.bilibili.domain.exception.ConditionException;
import com.imooc.bilibili.mapper.TDanmuMapper;
import com.imooc.bilibili.service.ITDanmuService;
import com.sun.org.apache.bcel.internal.generic.NEW;
import io.netty.util.internal.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <p>
 * 弹幕记录表 服务实现类
 * </p>
 *
 * @author DongZhi
 * @since 2022-07-07
 */
@Service
public class TDanmuServiceImpl extends ServiceImpl<TDanmuMapper, TDanmu> implements ITDanmuService {

    private static final String DANMU_KEY = "dm-video-";

    @Autowired
    private TDanmuMapper tDanmuMapper;
    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Override
    public void addDanmu(TDanmu danmu) {
        this.save(danmu);
    }

    @Override
    //异步保存弹幕到数据库 这个注解
    @Async
    public void asyncAddDanmu(TDanmu danmu) {
        this.save(danmu);
    }

    /**
     * 查询策略是优先查redis中的弹幕数据，
     * 如果没有的话查询数据库，然后把查询的数据写入redis当中
     */
    public List<TDanmu> getDanmus(Long videoId,
                                 String startTime, String endTime) throws Exception {

        String key = DANMU_KEY + videoId;
        String value = redisTemplate.opsForValue().get(key);
        List<TDanmu> list;
        //redis里有从redis那 没有就更新缓存
        if(!StringUtil.isNullOrEmpty(value)){
            list = JSONArray.parseArray(value, TDanmu.class);
            if(!StringUtil.isNullOrEmpty(startTime)
                    && !StringUtil.isNullOrEmpty(endTime)){
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                //特定字符串转为日期对象
                Date startDate = sdf.parse(startTime);
                Date endDate = sdf.parse(endTime);
                List<TDanmu> childList = new ArrayList<>();
                for(TDanmu danmu : list){
                    Date createTime = danmu.getCreateTime();
                    if(createTime.after(startDate) && createTime.before(endDate)){
                        childList.add(danmu);
                    }
                }
                list = childList;
            }
        }else{
            LambdaQueryWrapper<TDanmu> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(TDanmu::getVideoId,videoId);
            queryWrapper.ge(TDanmu::getCreateTime,startTime);
            queryWrapper.le(TDanmu::getCreateTime,endTime);
            list = tDanmuMapper.selectList(queryWrapper);
            //保存弹幕到redis
            redisTemplate.opsForValue().set(key, JSONObject.toJSONString(list));
        }
        return list;
    }

    public void addDanmusToRedis(TDanmu danmu) {
        String key = DANMU_KEY + danmu.getVideoId();
        String value = redisTemplate.opsForValue().get(key);
        List<TDanmu> list = new ArrayList<>();
        if(!StringUtil.isNullOrEmpty(value)){
            list = JSONArray.parseArray(value, TDanmu.class);
        }
        list.add(danmu);
        redisTemplate.opsForValue().set(key, JSONObject.toJSONString(list));
    }
}
