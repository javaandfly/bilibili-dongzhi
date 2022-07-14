package com.imooc.bilibili.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.imooc.bilibili.domain.FollowingGroup;
import com.imooc.bilibili.domain.UserFollowing;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserFollowingService extends IService<UserFollowing> {
    /**
     * 添加用户关注列表
     * @param userFollowing
     */
    void addUserFollowings(UserFollowing userFollowing);

    /**
     * 根据userid和followingid删除
     * @param userid
     * @param followingId
     */
    void deleteUserFollowing(Long userid, Long followingId);

    /**
     * 根据id获取用户关注列表
     * @param userId
     * @return
     */
    List<FollowingGroup> getUserFollowings(Long userId);

    /**
     * 获取粉丝列表
     * @param userid
     * @return
     */
    List<UserFollowing> getUserFan(Long userid);

    List<UserFollowing> getFollowingByUserId(Long userid);

    Long addUserFollowingGroups(FollowingGroup followingGroup);
}
