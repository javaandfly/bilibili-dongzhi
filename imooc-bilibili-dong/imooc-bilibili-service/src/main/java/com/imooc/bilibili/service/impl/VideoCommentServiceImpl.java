package com.imooc.bilibili.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.imooc.bilibili.domain.VideoComment;
import com.imooc.bilibili.mapper.VideoCommentMapper;
import com.imooc.bilibili.service.VideoCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class VideoCommentServiceImpl extends ServiceImpl<VideoCommentMapper, VideoComment> implements VideoCommentService {

    @Autowired
    private VideoCommentMapper videoCommentMapper;

    @Override
    public Integer pageCountVideoComments(Map<String, Object> params) {

        return videoCommentMapper.pageCountVideoComments(params);
    }

    @Override
    public List<VideoComment> pageListVideoComments(Map<String, Object> params) {
        return videoCommentMapper.pageListVideoComments(params);
    }

    @Override
    public List<VideoComment> batchGetVideoCommentsByRootIds(List<Long> parentIdList) {
      return   videoCommentMapper.batchGetVideoCommentsByRootIds(parentIdList);
    }
}
