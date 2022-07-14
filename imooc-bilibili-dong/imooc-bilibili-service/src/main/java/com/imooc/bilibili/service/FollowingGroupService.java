package com.imooc.bilibili.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.imooc.bilibili.domain.FollowingGroup;
import com.imooc.bilibili.mapper.FollowingGroupMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface FollowingGroupService extends IService<FollowingGroup> {
    /**
     * 根据类型查
     * @param type
     * @return
     */
     FollowingGroup getFollowingGroupByType(String type);

    /**
     * 根据id查
     * @param userId
     * @return
     */
    List<FollowingGroup> getFollowingGroupByUserId(Long userId);


    List<FollowingGroup> getUserFollowingGroups(Long userId);
}
