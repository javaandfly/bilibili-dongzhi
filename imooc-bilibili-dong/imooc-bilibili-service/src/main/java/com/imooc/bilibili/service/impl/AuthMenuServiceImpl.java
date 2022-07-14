package com.imooc.bilibili.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.imooc.bilibili.domain.auth.AuthMenu;
import com.imooc.bilibili.mapper.AuthMenuMapper;
import com.imooc.bilibili.service.AuthMenuService;
import org.springframework.stereotype.Service;

@Service
public class AuthMenuServiceImpl extends ServiceImpl<AuthMenuMapper, AuthMenu> implements AuthMenuService {
}
