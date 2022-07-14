package com.imooc.bilibili.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.imooc.bilibili.domain.TVideoCollection;

/**
 * <p>
 * 视频收藏记录表 服务类
 * </p>
 *
 * @author DongZhi
 * @since 2022-07-07
 */
public interface ITVideoCollectionService extends IService<TVideoCollection> {

    TVideoCollection getVideoCollectionByUserId(Long userId, Long videoId);

    void deleteVideoCollection(Long videoId, Long userId);

    Integer getVideoCollections(Long videoId);
}
