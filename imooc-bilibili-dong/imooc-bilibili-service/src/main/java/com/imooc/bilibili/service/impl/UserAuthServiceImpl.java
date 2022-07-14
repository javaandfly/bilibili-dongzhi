package com.imooc.bilibili.service.impl;

import com.imooc.bilibili.domain.auth.*;
import com.imooc.bilibili.domain.constant.UserConstant;
import com.imooc.bilibili.service.AuthRoleService;
import com.imooc.bilibili.service.UserAuthService;
import com.imooc.bilibili.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserAuthServiceImpl implements UserAuthService {
    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private AuthRoleService authRoleService;
    @Override
    public UserAuthorities getUserAuthorities(Long userId) {
        List<UserRole> userRoles=userRoleService.getUserRoleByUserId(userId);
        Set<Long> roleIdSet = userRoles.stream().map(UserRole::getRoleId).collect(Collectors.toSet());
       List<AuthRoleElementOperation> authRoleElementOperations= authRoleService.getElementOperationByRoleId(roleIdSet);
       List<AuthRoleMenu> authRoleMenus=authRoleService.getAuthRoleMenus(roleIdSet);
        UserAuthorities userAuthorities = new UserAuthorities();
        userAuthorities.setRoleMenuList(authRoleMenus);
        userAuthorities.setRoleElementOperationList(authRoleElementOperations);
        return userAuthorities;
    }

    @Override
    public void addUserDefaultRole(Long id) {
        UserRole userRole = new UserRole();
        userRole.setUserId(id);
        //这里写死了 默认为Lv0
        userRole.setRoleId(1L);
        userRole.setCreateTime(new Date());
        userRoleService.save(userRole);
    }
}
