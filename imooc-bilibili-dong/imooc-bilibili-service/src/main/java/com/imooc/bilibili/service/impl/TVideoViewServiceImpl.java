package com.imooc.bilibili.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.imooc.bilibili.domain.TVideoView;
import com.imooc.bilibili.mapper.TVideoViewMapper;
import com.imooc.bilibili.service.ITVideoViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * <p>
 * 视频观看记录表 服务实现类
 * </p>
 *
 * @author DongZhi
 * @since 2022-07-11
 */
@Service
public class TVideoViewServiceImpl extends ServiceImpl<TVideoViewMapper, TVideoView> implements ITVideoViewService {

    @Autowired
    private TVideoViewMapper tVideoViewMapper;

    @Override
    public TVideoView getVideoView(Map<String, Object> params) {

        return  tVideoViewMapper.getVideoView(params);
    }

    @Override
    public Integer getVideoViewCounts(Long videoId) {
        LambdaQueryWrapper<TVideoView> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TVideoView::getVideoId,videoId);
        return tVideoViewMapper.selectCount(queryWrapper);
    }
}
