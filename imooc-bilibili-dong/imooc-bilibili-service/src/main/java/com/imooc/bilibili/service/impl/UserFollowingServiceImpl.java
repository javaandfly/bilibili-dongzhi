package com.imooc.bilibili.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.imooc.bilibili.domain.FollowingGroup;
import com.imooc.bilibili.domain.User;
import com.imooc.bilibili.domain.UserFollowing;
import com.imooc.bilibili.domain.UserInfo;
import com.imooc.bilibili.domain.constant.UserConstant;
import com.imooc.bilibili.domain.exception.ConditionException;
import com.imooc.bilibili.mapper.UserFollowingMapper;
import com.imooc.bilibili.service.FollowingGroupService;
import com.imooc.bilibili.service.UserFollowingService;
import com.imooc.bilibili.service.UserInfoService;
import com.imooc.bilibili.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;
@Service
public class UserFollowingServiceImpl extends ServiceImpl<UserFollowingMapper, UserFollowing> implements UserFollowingService {
    @Autowired
    private UserFollowingMapper userFollowingMapper;
    @Autowired
    private FollowingGroupService followingGroupService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserInfoService userInfoService;
    /**
     * 添加用户
     * @param userFollowing
     */
    @Transactional
    public void addUserFollowings(UserFollowing userFollowing){
        Long groupId = userFollowing.getGroupId();
        if (groupId==null){
            FollowingGroup followingGroup = followingGroupService.getFollowingGroupByType(UserConstant.USER_FOLLOWING_GROUP_TYPE_DEFAULT);
            userFollowing.setGroupId(followingGroup.getUserId());
        }else {
            FollowingGroup followingGroup = followingGroupService.getById(groupId);
            if (followingGroup==null){
                throw new ConditionException("关注的分组不存在");
            }
        }
        Long followingId = userFollowing.getFollowingId();
        User user = userService.getById(followingId);
        if (user==null){
            throw new ConditionException("用户不存在");
        }
        this.deleteUserFollowing(userFollowing.getUserId(),followingId);
        userFollowing.setCreateTime(new Date());
        this.save(userFollowing);
    }

    @Override
    public void deleteUserFollowing(Long userid, Long followingId) {
        LambdaQueryWrapper<UserFollowing> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(UserFollowing::getUserId,userid)
                .eq(UserFollowing::getFollowingId,followingId);
        userFollowingMapper.delete(queryWrapper);
    }

    @Override
    //获取关注列表
    //根据关注用户的id查询关注用户的基本信息
    //将关注用户按照关注分组分类
    public List<FollowingGroup> getUserFollowings(Long userId) {
        //根据token携带的用户信息查询用户关注的人
        List<UserFollowing> userFollowings = this.getFollowingByUserId(userId);
        //遍历抽取出关注人的id 组成一个set
        Set<Long> followingSet = userFollowings.stream().map(UserFollowing::getFollowingId).collect(Collectors.toSet());
        List<UserInfo> userInfoByUserIds=new ArrayList<>();
        //判断set是否为空 不为空就按照id 一个一个查出来之后存入list
        if (followingSet.size()>0) {
             userInfoByUserIds = userInfoService.getUserInfoByUserIds(followingSet);
        }
        //判断id是否相等
        for (UserFollowing userFollowing : userFollowings) {
            for (UserInfo userInfoByUserId : userInfoByUserIds) {
                if (userFollowing.getFollowingId().equals(userInfoByUserId.getUserId())){
                    userFollowing.setUserInfo(userInfoByUserId);
                }
            }
        }
        List<FollowingGroup> followingGroups=followingGroupService.getFollowingGroupByUserId(userId);
        FollowingGroup allGroup=new FollowingGroup();
        allGroup.setName(UserConstant.USER_FOLLOWING_GROUP_TYPE_USER);
        allGroup.setFollowingUserInfoList(userInfoByUserIds);
        ArrayList<FollowingGroup> result = new ArrayList<>();
        result.add(allGroup);
        for (FollowingGroup followingGroup : followingGroups) {
            ArrayList<UserInfo> userInfos = new ArrayList<>();
            for (UserFollowing userFollowing : userFollowings) {
                if (followingGroup.getId().equals(userFollowing.getGroupId())){
                    userInfos.add(userFollowing.getUserInfo());
                }
            }
            followingGroup.setFollowingUserInfoList(userInfos);
            result.add(followingGroup);
        }
        return result;
    }
    //1：获取当前用户的粉丝列表
    //2：根据粉丝用户id查询基本信息
    //3：查询用户是否关注该粉丝
    @Override
    public List<UserFollowing> getUserFan(Long userid) {
        LambdaQueryWrapper<UserFollowing> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserFollowing::getFollowingId,userid);
        List<UserFollowing> fanList = userFollowingMapper.selectList(queryWrapper);
        Set<Long> fanIdSet = fanList.stream().map(UserFollowing::getUserId).collect(Collectors.toSet());
        List<UserInfo> userInfoByUserIds=new ArrayList<>();
        if (fanIdSet.size()>0){
           userInfoByUserIds = userInfoService.getUserInfoByUserIds(fanIdSet);
        }
        List<UserFollowing> userFollowings = this.getFollowingByUserId(userid);
        for (UserFollowing fan : fanList) {
            for (UserInfo userInfoByUserId : userInfoByUserIds) {
                if(fan.getUserId().equals(userInfoByUserId.getUserId())){
                    userInfoByUserId.setFollowed(false);
                    fan.setUserInfo(userInfoByUserId);
                }
            }
            for (UserFollowing following : userFollowings) {
                if (following.getFollowingId().equals(fan.getUserId())){
                    fan.getUserInfo().setFollowed(true);
                }
            }

        }

        return fanList;
    }

    @Override
    public List<UserFollowing> getFollowingByUserId(Long userid) {
        LambdaQueryWrapper<UserFollowing> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserFollowing::getUserId,userid);

        return userFollowingMapper.selectList(queryWrapper);
    }


    public Long addUserFollowingGroups(FollowingGroup followingGroup) {
        followingGroup.setCreateTime(new Date());
        followingGroup.setType(UserConstant.USER_FOLLOWING_GROUP_TYPE_USER);
        followingGroupService.save(followingGroup);
        return followingGroup.getId();
    }

    public List<FollowingGroup> getUserFollowingGroups(Long userId) {
        return followingGroupService.getUserFollowingGroups(userId);
    }

//    public List<UserInfo> checkFollowingStatus(List<UserInfo> userInfoList, Long userId) {
//        List<UserFollowing> userFollowingList = userFollowingDao.getUserFollowings(userId);
//        for(UserInfo userInfo : userInfoList){
//            userInfo.setFollowed(false);
//            for(UserFollowing userFollowing : userFollowingList){
//                if(userFollowing.getFollowingId().equals(userInfo.getUserId())){
//                    userInfo.setFollowed(true);
//                }
//            }
//        }
//        return userInfoList;
//    }
}
