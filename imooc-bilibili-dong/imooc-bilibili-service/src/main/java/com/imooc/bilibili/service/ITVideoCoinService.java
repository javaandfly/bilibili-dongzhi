package com.imooc.bilibili.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.imooc.bilibili.domain.TVideoCoin;

/**
 * <p>
 * 视频硬币表 服务类
 * </p>
 *
 * @author DongZhi
 * @since 2022-07-07
 */
public interface ITVideoCoinService extends IService<TVideoCoin> {


    Integer getAllCoinsByVideoId(Long videoId);

    TVideoCoin getVideoCoinByUserId(Long userId);
}
