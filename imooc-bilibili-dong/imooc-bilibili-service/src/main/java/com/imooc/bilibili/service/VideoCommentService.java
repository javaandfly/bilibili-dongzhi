package com.imooc.bilibili.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.imooc.bilibili.domain.VideoComment;

import java.util.List;
import java.util.Map;

public interface VideoCommentService extends IService<VideoComment> {
    Integer pageCountVideoComments(Map<String, Object> params);

    List<VideoComment> pageListVideoComments(Map<String, Object> params);

    List<VideoComment> batchGetVideoCommentsByRootIds(List<Long> parentIdList);
}
