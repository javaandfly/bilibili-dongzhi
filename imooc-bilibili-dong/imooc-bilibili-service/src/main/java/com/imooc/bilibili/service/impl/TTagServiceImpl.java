package com.imooc.bilibili.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.imooc.bilibili.domain.TTag;
import com.imooc.bilibili.mapper.TTagMapper;
import com.imooc.bilibili.service.ITTagService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 标签表 服务实现类
 * </p>
 *
 * @author DongZhi
 * @since 2022-07-07
 */
@Service
public class TTagServiceImpl extends ServiceImpl<TTagMapper, TTag> implements ITTagService {

}
