package com.imooc.bilibili.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.imooc.bilibili.domain.TVideoCoin;
import com.imooc.bilibili.mapper.TVideoCoinMapper;
import com.imooc.bilibili.mapper.TVideoMapper;
import com.imooc.bilibili.service.ITVideoCoinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 视频硬币表 服务实现类
 * </p>
 *
 * @author DongZhi
 * @since 2022-07-07
 */
@Service
public class TVideoCoinServiceImpl extends ServiceImpl<TVideoCoinMapper, TVideoCoin> implements ITVideoCoinService {

    @Autowired
    private TVideoCoinMapper videoCoinMapper;

    @Override
    public Integer getAllCoinsByVideoId(Long videoId) {

        return videoCoinMapper.getAllCoinsByVideoId(videoId);
    }

    @Override
    public TVideoCoin getVideoCoinByUserId(Long userId) {
        LambdaQueryWrapper<TVideoCoin> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TVideoCoin::getUserId,userId);
        return videoCoinMapper.selectOne(queryWrapper);
    }
}
