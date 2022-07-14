package com.imooc.bilibili.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.imooc.bilibili.domain.TVideoView;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

/**
 * <p>
 * 视频观看记录表 Mapper 接口
 * </p>
 *
 * @author DongZhi
 * @since 2022-07-11
 */
@Mapper
public interface TVideoViewMapper extends BaseMapper<TVideoView> {

    TVideoView getVideoView(Map<String, Object> params);

}
