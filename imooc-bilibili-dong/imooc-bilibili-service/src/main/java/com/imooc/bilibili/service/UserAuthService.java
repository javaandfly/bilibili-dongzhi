package com.imooc.bilibili.service;

import com.imooc.bilibili.domain.auth.UserAuthorities;

public interface UserAuthService {
    UserAuthorities getUserAuthorities(Long userId);

    void addUserDefaultRole(Long id);
}
