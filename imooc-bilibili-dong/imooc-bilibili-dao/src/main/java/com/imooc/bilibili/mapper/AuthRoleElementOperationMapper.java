package com.imooc.bilibili.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.imooc.bilibili.domain.auth.AuthRoleElementOperation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

@Mapper
public interface AuthRoleElementOperationMapper extends BaseMapper<AuthRoleElementOperation> {
    List<AuthRoleElementOperation> getElementOperationByRoleId(@Param("roleIdSet") Set<Long> roleIdSet);
}
