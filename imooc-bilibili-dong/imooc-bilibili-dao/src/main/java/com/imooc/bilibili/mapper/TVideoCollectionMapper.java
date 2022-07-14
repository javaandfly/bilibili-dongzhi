package com.imooc.bilibili.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.imooc.bilibili.domain.TVideoCollection;
import org.apache.ibatis.annotations.Mapper;


/**
 * <p>
 * 视频收藏记录表 Mapper 接口
 * </p>
 *
 * @author DongZhi
 * @since 2022-07-07
 */
@Mapper
public interface TVideoCollectionMapper extends BaseMapper<TVideoCollection> {

}
