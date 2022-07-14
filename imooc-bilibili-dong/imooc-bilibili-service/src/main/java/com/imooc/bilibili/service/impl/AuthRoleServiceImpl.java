package com.imooc.bilibili.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.imooc.bilibili.domain.auth.AuthRole;
import com.imooc.bilibili.domain.auth.AuthRoleElementOperation;
import com.imooc.bilibili.domain.auth.AuthRoleMenu;
import com.imooc.bilibili.mapper.AuthRoleMapper;
import com.imooc.bilibili.service.AuthRoleElementOperationService;
import com.imooc.bilibili.service.AuthRoleMenuService;
import com.imooc.bilibili.service.AuthRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class AuthRoleServiceImpl extends ServiceImpl<AuthRoleMapper, AuthRole> implements AuthRoleService {
    @Autowired
    private AuthRoleElementOperationService authRoleElementOperationService;
    @Autowired
    private AuthRoleMenuService authRoleMenuService;
    @Override
    public List<AuthRoleElementOperation> getElementOperationByRoleId(Set<Long> roleIdSet) {
        return  authRoleElementOperationService.getElementOperationByRoleId(roleIdSet);
    }

    @Override
    public List<AuthRoleMenu> getAuthRoleMenus(Set<Long> roleIdSet) {
        return authRoleMenuService.getAuthRoleMenus(roleIdSet);
    }
}
