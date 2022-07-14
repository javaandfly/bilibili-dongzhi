package com.imooc.bilibili.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.imooc.bilibili.domain.UserCoin;
import com.imooc.bilibili.mapper.UserCoinMapper;
import com.imooc.bilibili.service.UserCoinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class UserCoinServiceImpl extends ServiceImpl<UserCoinMapper, UserCoin> implements UserCoinService {

    @Autowired
    private UserCoinMapper userCoinMapper;

    @Override
    public UserCoin getAmountByUserId(Long userId) {
        LambdaQueryWrapper<UserCoin> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(UserCoin::getAmount);
        queryWrapper.eq(UserCoin::getUserId,userId);
        return userCoinMapper.selectOne(queryWrapper);
    }
}
