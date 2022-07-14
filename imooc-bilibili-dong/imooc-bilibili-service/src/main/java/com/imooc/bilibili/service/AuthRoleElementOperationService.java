package com.imooc.bilibili.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.imooc.bilibili.domain.auth.AuthRoleElementOperation;

import java.util.List;
import java.util.Set;

public interface AuthRoleElementOperationService extends IService<AuthRoleElementOperation> {
    List<AuthRoleElementOperation> getElementOperationByRoleId(Set<Long> roleIdSet);

}
