package com.imooc.bilibili.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.imooc.bilibili.domain.TVideoCoin;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * 视频硬币表 Mapper 接口
 * </p>
 *
 * @author DongZhi
 * @since 2022-07-07
 */
@Mapper
public interface TVideoCoinMapper extends BaseMapper<TVideoCoin> {
    @Select("select count(1) from t_video_coin where video_id=#{videoId}")
    Integer getAllCoinsByVideoId(Long videoId);
}
