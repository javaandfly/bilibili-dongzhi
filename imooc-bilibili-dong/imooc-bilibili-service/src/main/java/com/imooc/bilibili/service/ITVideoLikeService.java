package com.imooc.bilibili.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.imooc.bilibili.domain.TVideoLike;

/**
 * <p>
 * 视频点赞记录表 服务类
 * </p>
 *
 * @author DongZhi
 * @since 2022-07-07
 */
public interface ITVideoLikeService extends IService<TVideoLike> {

    void deleteVideoLike(Long videoId, Long userId);

    TVideoLike getVideoLikeByUserId(Long userId,Long videoId);

    Integer getVideoLikes(Long videoId);
}
