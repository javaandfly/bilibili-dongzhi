package com.imooc.bilibili.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.imooc.bilibili.domain.TVideoLike;
import org.apache.ibatis.annotations.Mapper;


/**
 * <p>
 * 视频点赞记录表 Mapper 接口
 * </p>
 *
 * @author DongZhi
 * @since 2022-07-07
 */
@Mapper
public interface TVideoLikeMapper extends BaseMapper<TVideoLike> {

}
