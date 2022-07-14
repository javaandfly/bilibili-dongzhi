package com.imooc.bilibili.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.imooc.bilibili.domain.VideoComment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface VideoCommentMapper extends BaseMapper<VideoComment> {

    Integer pageCountVideoComments(Map<String, Object> params);

    List<VideoComment> pageListVideoComments(Map<String, Object> params);

    List<VideoComment> batchGetVideoCommentsByRootIds(List<Long> parentIdList);
}
