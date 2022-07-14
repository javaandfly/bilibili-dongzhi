package com.imooc.bilibili.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.imooc.bilibili.domain.RefreshToken;
import org.springframework.stereotype.Service;

@Service
public interface RefreshTokenService extends IService<RefreshToken> {
    void deleteRefreshTokenByUserId(Long user1Id);

    RefreshToken selectByRefreshToken(String refreshToken);
}
