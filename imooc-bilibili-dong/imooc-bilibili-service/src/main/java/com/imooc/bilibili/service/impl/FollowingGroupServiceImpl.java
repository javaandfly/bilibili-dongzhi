package com.imooc.bilibili.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.imooc.bilibili.domain.FollowingGroup;
import com.imooc.bilibili.mapper.FollowingGroupMapper;
import com.imooc.bilibili.service.FollowingGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class FollowingGroupServiceImpl extends ServiceImpl<FollowingGroupMapper,FollowingGroup> implements FollowingGroupService {
    @Autowired
    private FollowingGroupMapper followingGroupMapper;

    /**
     * 根据type查
     * @param type
     * @return
     */
    public FollowingGroup getFollowingGroupByType(String type){
        LambdaQueryWrapper<FollowingGroup> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(FollowingGroup::getType,type);
        return followingGroupMapper.selectOne(queryWrapper);
    }

    /**
     * 根据用户id查
     * @param userId
     * @return
     */
    @Override
    public List<FollowingGroup> getFollowingGroupByUserId(Long userId) {
        LambdaQueryWrapper<FollowingGroup> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(FollowingGroup::getUserId,userId);
        queryWrapper.or().in(FollowingGroup::getType,"1,2,3");
        return followingGroupMapper.selectList(queryWrapper);
    }


    @Override
    public List<FollowingGroup> getUserFollowingGroups(Long userId) {
        LambdaQueryWrapper<FollowingGroup> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(FollowingGroup::getUserId,userId);
        return followingGroupMapper.selectList(queryWrapper);
    }

}
