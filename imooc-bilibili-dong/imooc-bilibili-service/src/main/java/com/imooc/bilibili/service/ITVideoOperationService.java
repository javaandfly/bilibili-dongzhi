package com.imooc.bilibili.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.imooc.bilibili.domain.TVideoOperation;
import com.imooc.bilibili.domain.UserPreference;

import java.util.List;

/**
 * <p>
 * 视频操作表 服务类
 * </p>
 *
 * @author DongZhi
 * @since 2022-07-13
 */
public interface ITVideoOperationService extends IService<TVideoOperation> {

    List<UserPreference> getAllUserPreference();



}
