package com.imooc.bilibili.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.imooc.bilibili.domain.auth.AuthRoleElementOperation;
import com.imooc.bilibili.mapper.AuthRoleElementOperationMapper;
import com.imooc.bilibili.service.AuthRoleElementOperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class AuthRoleElementOperationServiceImpl extends ServiceImpl<AuthRoleElementOperationMapper, AuthRoleElementOperation> implements AuthRoleElementOperationService {
    @Autowired
    private AuthRoleElementOperationMapper authRoleElementOperationMapper;
    @Override
    public List<AuthRoleElementOperation> getElementOperationByRoleId(Set<Long> roleIdSet) {
        return authRoleElementOperationMapper.getElementOperationByRoleId(roleIdSet);
    }
}
