package com.imooc.bilibili.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.imooc.bilibili.domain.UserMoment;

import java.util.List;

public interface UserMomentsService extends IService<UserMoment> {
    void addUserMoments(UserMoment userMoment);

    List<UserMoment> getUserSubscribedMoments(Long userId);

}
