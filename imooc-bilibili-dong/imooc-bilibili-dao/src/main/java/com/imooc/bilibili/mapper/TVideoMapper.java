package com.imooc.bilibili.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.imooc.bilibili.domain.TVideo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;


/**
 * <p>
 * 视频投稿记录表 Mapper 接口
 * </p>
 *
 * @author DongZhi
 * @since 2022-07-07
 */
@Mapper
public interface TVideoMapper extends BaseMapper<TVideo> {

    Integer pageCountVideos(Map<String, Object> map);

    List<TVideo> pageListVideos(Map<String, Object> map);

}
