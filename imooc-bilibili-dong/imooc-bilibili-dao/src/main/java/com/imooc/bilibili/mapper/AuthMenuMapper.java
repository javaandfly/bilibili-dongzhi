package com.imooc.bilibili.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.imooc.bilibili.domain.auth.AuthMenu;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AuthMenuMapper extends BaseMapper<AuthMenu> {
}
