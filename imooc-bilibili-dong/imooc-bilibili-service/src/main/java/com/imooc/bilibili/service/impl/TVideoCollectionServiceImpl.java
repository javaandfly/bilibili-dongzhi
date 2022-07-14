package com.imooc.bilibili.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.imooc.bilibili.domain.TVideoCollection;
import com.imooc.bilibili.mapper.TVideoCollectionMapper;
import com.imooc.bilibili.service.ITVideoCollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 视频收藏记录表 服务实现类
 * </p>
 *
 * @author DongZhi
 * @since 2022-07-07
 */
@Service
public class TVideoCollectionServiceImpl extends ServiceImpl<TVideoCollectionMapper, TVideoCollection> implements ITVideoCollectionService {

    @Autowired
    private TVideoCollectionMapper tVideoCollectionMapper;

    @Override
    public TVideoCollection getVideoCollectionByUserId(Long userId, Long videoId) {
        LambdaQueryWrapper<TVideoCollection> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TVideoCollection::getVideoId,videoId)
                    .eq(TVideoCollection::getUserId,userId);
        return tVideoCollectionMapper.selectOne(queryWrapper);
    }

    @Override
    public void deleteVideoCollection(Long videoId, Long userId) {
        LambdaQueryWrapper<TVideoCollection> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TVideoCollection::getVideoId,videoId)
                .eq(TVideoCollection::getUserId,userId);
        tVideoCollectionMapper.delete(queryWrapper);
    }

    @Override
    public Integer getVideoCollections(Long videoId) {
        LambdaQueryWrapper<TVideoCollection> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TVideoCollection::getVideoId,videoId);
        Integer count = tVideoCollectionMapper.selectCount(queryWrapper);
        return count;
    }
}
