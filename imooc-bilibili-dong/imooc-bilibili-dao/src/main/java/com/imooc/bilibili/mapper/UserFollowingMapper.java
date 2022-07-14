package com.imooc.bilibili.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.imooc.bilibili.domain.UserFollowing;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserFollowingMapper extends BaseMapper<UserFollowing> {
}
