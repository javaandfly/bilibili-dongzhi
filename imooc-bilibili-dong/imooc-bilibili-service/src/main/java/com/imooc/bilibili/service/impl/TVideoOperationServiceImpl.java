package com.imooc.bilibili.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.imooc.bilibili.domain.TVideoOperation;
import com.imooc.bilibili.domain.UserPreference;
import com.imooc.bilibili.mapper.TVideoOperationMapper;
import com.imooc.bilibili.service.ITVideoOperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 视频操作表 服务实现类
 * </p>
 *
 * @author DongZhi
 * @since 2022-07-13
 */
@Service
public class TVideoOperationServiceImpl extends ServiceImpl<TVideoOperationMapper, TVideoOperation> implements ITVideoOperationService {

    @Autowired
    private TVideoOperationMapper tVideoOperationMapper;

    @Override
    public List<UserPreference> getAllUserPreference() {
        return  tVideoOperationMapper.getAllUserPreference();
    }
}
