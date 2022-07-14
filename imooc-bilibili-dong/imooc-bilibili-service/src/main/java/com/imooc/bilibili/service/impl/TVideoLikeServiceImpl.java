package com.imooc.bilibili.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.imooc.bilibili.domain.TVideoLike;
import com.imooc.bilibili.mapper.TVideoLikeMapper;
import com.imooc.bilibili.service.ITVideoLikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 视频点赞记录表 服务实现类
 * </p>
 *
 * @author DongZhi
 * @since 2022-07-07
 */
@Service
public class TVideoLikeServiceImpl extends ServiceImpl<TVideoLikeMapper, TVideoLike> implements ITVideoLikeService {

    @Autowired
    private TVideoLikeMapper tVideoLikeMapper;

    @Override
    public void deleteVideoLike(Long videoId, Long userId) {
        LambdaQueryWrapper<TVideoLike> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TVideoLike::getVideoId,videoId)
                    .eq(TVideoLike::getUserId,userId);
        tVideoLikeMapper.delete(queryWrapper);
    }

    @Override
    public TVideoLike getVideoLikeByUserId(Long userId,Long videoId) {
        LambdaQueryWrapper<TVideoLike> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TVideoLike::getVideoId,videoId)
                    .eq(TVideoLike::getUserId,userId);
        return tVideoLikeMapper.selectOne(queryWrapper);
    }

    @Override
    public Integer getVideoLikes(Long videoId) {
        LambdaQueryWrapper<TVideoLike> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TVideoLike::getVideoId,videoId);
        return tVideoLikeMapper.selectCount(queryWrapper);
    }
}
