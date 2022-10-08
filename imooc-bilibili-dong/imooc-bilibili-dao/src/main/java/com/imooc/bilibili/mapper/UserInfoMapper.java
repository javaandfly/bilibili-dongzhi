package com.imooc.bilibili.mapper;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.imooc.bilibili.domain.PageResult;
import com.imooc.bilibili.domain.UserInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Mapper
public interface UserInfoMapper extends BaseMapper<UserInfo> {

    Integer pageCountUserInfo(Map<String, Object> params);


    List<UserInfo> batchGetUserInfoByUserIds(Set<Long> userIdList);


    List<UserInfo> pageListUserInfo(JSONObject params);
}
