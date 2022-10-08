package com.imooc.bilibili.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.imooc.bilibili.domain.PageResult;
import com.imooc.bilibili.domain.UserFollowing;
import com.imooc.bilibili.domain.UserInfo;
import com.imooc.bilibili.mapper.UserInfoMapper;
import com.imooc.bilibili.service.UserFollowingService;
import com.imooc.bilibili.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService {
    @Autowired
    private UserInfoMapper userInfoMapper;
    @Autowired
    private UserFollowingService userFollowingService;

    @Override
    public Integer pageCountUserInfos(Map<String, Object> params) {
        return userInfoMapper.pageCountUserInfo(params);
    }



    @Override
    public List<UserInfo> pageListsUserInfos(JSONObject params) {
        return userInfoMapper.pageListUserInfo(params);
    }

    @Override
    public PageResult<UserInfo> pageListUserInfos(JSONObject params) {
        Integer no = params.getInteger("no");
        Integer size = params.getInteger("size");
        params.put("start", (no-1)*size);
        params.put("limit", size);
        Integer total = this.pageCountUserInfos(params);
        List<UserInfo> list = new ArrayList<>();
        if(total > 0){
            list = this.pageListsUserInfos(params);
        }
        return new PageResult<>(total,list);

    }
    @Override
    public UserInfo getUserInfoByUserId(Long userId) {
        LambdaQueryWrapper<UserInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserInfo::getUserId,userId);
        UserInfo one = this.getOne(queryWrapper);
        return one;
    }

    @Override
    public List<UserInfo> getUserInfoByUserIds(Set<Long> set) {
        Iterator<Long> iterator = set.iterator();
        LambdaQueryWrapper<UserInfo> queryWrapper = new LambdaQueryWrapper<>();

        while (iterator.hasNext()){
            Long next = iterator.next();
            queryWrapper.eq(UserInfo::getUserId,next);
            queryWrapper.or();
        }
        List<UserInfo> userInfos = userInfoMapper.selectList(queryWrapper);
        return userInfos;
    }


    public List<UserInfo> checkFollowingStatus(List<UserInfo> userInfoList, Long userId) {
        List<UserFollowing> userFollowingList = userFollowingService.getFollowingByUserId(userId);
        for(UserInfo userInfo : userInfoList){
            userInfo.setFollowed(false);
            for(UserFollowing userFollowing : userFollowingList){
                if(userFollowing.getFollowingId().equals(userInfo.getUserId())){
                    userInfo.setFollowed(true);
                }
            }
        }
        return userInfoList;
    }

    @Override
    public List<UserInfo> batchGetUserInfoByUserIds(Set<Long> userIdList) {
       return userInfoMapper.batchGetUserInfoByUserIds(userIdList);
    }


}
