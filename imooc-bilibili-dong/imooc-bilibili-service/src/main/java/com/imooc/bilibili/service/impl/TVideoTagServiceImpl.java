package com.imooc.bilibili.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.imooc.bilibili.domain.TVideoTag;
import com.imooc.bilibili.mapper.TVideoMapper;
import com.imooc.bilibili.mapper.TVideoTagMapper;
import com.imooc.bilibili.service.ITVideoTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 视频标签关联表 服务实现类
 * </p>
 *
 * @author DongZhi
 * @since 2022-07-07
 */
@Service
public class TVideoTagServiceImpl extends ServiceImpl<TVideoTagMapper, TVideoTag> implements ITVideoTagService {
    @Autowired
    private TVideoMapper tVideoMapper;

}
