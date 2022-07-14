package com.imooc.bilibili.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.imooc.bilibili.domain.TVideoView;

import java.util.Map;

/**
 * <p>
 * 视频观看记录表 服务类
 * </p>
 *
 * @author DongZhi
 * @since 2022-07-11
 */
public interface ITVideoViewService extends IService<TVideoView> {

    TVideoView getVideoView(Map<String, Object> params);

    Integer getVideoViewCounts(Long videoId);
}
