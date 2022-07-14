package com.imooc.bilibili.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.imooc.bilibili.domain.UserInfo;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public interface UserInfoService extends IService<UserInfo> {
    UserInfo getUserInfoByUserId(Long userId);

    /**
     * 根据很多id查
     * @return
     */
    List<UserInfo> getUserInfoByUserIds(Set<Long> set);

    List<UserInfo> checkFollowingStatus(List<UserInfo> list, Long userId);

    List<UserInfo> batchGetUserInfoByUserIds(Set<Long> userIdList);

}
