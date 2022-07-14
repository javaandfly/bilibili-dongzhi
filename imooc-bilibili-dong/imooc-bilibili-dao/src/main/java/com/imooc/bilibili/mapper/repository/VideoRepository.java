package com.imooc.bilibili.mapper.repository;

import com.imooc.bilibili.domain.TVideo;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface VideoRepository extends ElasticsearchRepository<TVideo, Long> {

    TVideo findByTitleLike(String keyword);
}
