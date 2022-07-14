package com.imooc.bilibili.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.imooc.bilibili.domain.RefreshToken;
import com.imooc.bilibili.mapper.RefreshTokenMapper;
import com.imooc.bilibili.service.RefreshTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RefreshTokenServiceImpl extends ServiceImpl<RefreshTokenMapper,RefreshToken> implements RefreshTokenService {
    @Autowired
    private RefreshTokenMapper refreshTokenMapper;

    @Override
    public void deleteRefreshTokenByUserId(Long user1Id) {
        LambdaQueryWrapper<RefreshToken> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(RefreshToken::getUserId,user1Id);
        refreshTokenMapper.delete(queryWrapper);
    }

    @Override
    public RefreshToken selectByRefreshToken(String refreshToken) {
        LambdaQueryWrapper<RefreshToken> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(RefreshToken::getRefreshToken,refreshToken);
        return refreshTokenMapper.selectOne(queryWrapper);
    }
}
