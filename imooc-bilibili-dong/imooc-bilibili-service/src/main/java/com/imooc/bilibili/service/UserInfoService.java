package com.imooc.bilibili.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.imooc.bilibili.domain.PageResult;
import com.imooc.bilibili.domain.UserInfo;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public interface UserInfoService extends IService<UserInfo> {
    Integer pageCountUserInfos(Map<String, Object> params);

    UserInfo getUserInfoByUserId(Long userId);
    PageResult<UserInfo> pageListUserInfos(JSONObject params);


    List<UserInfo> pageListsUserInfos(JSONObject params);
    /**
     * 根据很多id查
     * @return
     */
    List<UserInfo> getUserInfoByUserIds(Set<Long> set);

    List<UserInfo> checkFollowingStatus(List<UserInfo> list, Long userId);

    List<UserInfo> batchGetUserInfoByUserIds(Set<Long> userIdList);


}
