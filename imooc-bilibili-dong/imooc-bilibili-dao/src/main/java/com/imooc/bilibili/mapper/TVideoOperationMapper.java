package com.imooc.bilibili.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.imooc.bilibili.domain.TVideoOperation;
import com.imooc.bilibili.domain.UserPreference;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


/**
 * <p>
 * 视频操作表 Mapper 接口
 * </p>
 *
 * @author DongZhi
 * @since 2022-07-13
 */
@Mapper
public interface TVideoOperationMapper extends BaseMapper<TVideoOperation> {

    List<UserPreference> getAllUserPreference();

}
