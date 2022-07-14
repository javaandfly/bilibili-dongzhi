package com.imooc.bilibili.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.imooc.bilibili.domain.auth.AuthRoleMenu;
import com.imooc.bilibili.mapper.AuthRoleMenuMapper;
import com.imooc.bilibili.service.AuthRoleMenuService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class AuthRoleMenuServiceImpl extends ServiceImpl<AuthRoleMenuMapper, AuthRoleMenu> implements AuthRoleMenuService {
    @Autowired
    private AuthRoleMenuMapper authRoleMenuMapper;
    @Override
    public List<AuthRoleMenu> getAuthRoleMenus( Set<Long> roleIdSet) {

        return authRoleMenuMapper.getAuthRoleMenus(roleIdSet);
    }
}
