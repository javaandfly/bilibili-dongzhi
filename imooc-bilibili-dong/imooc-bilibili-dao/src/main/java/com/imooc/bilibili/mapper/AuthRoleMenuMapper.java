package com.imooc.bilibili.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.imooc.bilibili.domain.auth.AuthRoleMenu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

@Mapper
public interface AuthRoleMenuMapper extends BaseMapper<AuthRoleMenu> {
    List<AuthRoleMenu> getAuthRoleMenus(@Param("roleIdSet") Set<Long> roleIdSet);

}
