package com.imooc.bilibili.service;

import com.imooc.bilibili.domain.TVideo;
import com.imooc.bilibili.domain.UserInfo;
import com.imooc.bilibili.mapper.repository.UserInfoRepository;
import com.imooc.bilibili.mapper.repository.VideoRepository;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class ElasticSearchService {

    @Autowired
    private VideoRepository videoRepository;

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Qualifier("elasticsearchClient")
    @Autowired
    private RestHighLevelClient restHighLevelClient;

    public void addUserInfo(UserInfo userInfo){
        userInfoRepository.save(userInfo);
    }

    public void addVideo(TVideo video){
        videoRepository.save(video);
    }

    //获取用户列表
    public List<Map<String, Object>> getContents(String keyword,
                                                 Integer pageNo,
                                                 Integer pageSize) throws IOException {
        String[] indices = {"videos", "user-infos"};
        SearchRequest searchRequest = new SearchRequest(indices);
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        //分页
        sourceBuilder.from(pageNo - 1);
        sourceBuilder.size(pageSize);
        //根据三个值判断 包括视频类中的标题title 用户表里的名称nick 视频里的简介description
        MultiMatchQueryBuilder matchQueryBuilder = QueryBuilders.multiMatchQuery(keyword, "title", "nick", "description");
        //类似于mybatis-plus的构造器
        sourceBuilder.query(matchQueryBuilder);
        searchRequest.source(sourceBuilder);
        sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
        //高亮显示
        String[] array = {"title", "nick", "description"};
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        for(String key : array){
            highlightBuilder.fields().add(new HighlightBuilder.Field(key));
        }
        highlightBuilder.requireFieldMatch(false); //如果要多个字段进行高亮，要为false
        highlightBuilder.preTags("<span style=\"color:red\">");//在标签之前
        highlightBuilder.postTags("</span>");//在标签之后
        sourceBuilder.highlighter(highlightBuilder);
        //执行搜索
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        List<Map<String, Object>> arrayList = new ArrayList<>();
        for(SearchHit hit : searchResponse.getHits()){
            //处理高亮字段
            Map<String, HighlightField> highLightBuilderFields = hit.getHighlightFields();
            Map<String, Object> sourceMap = hit.getSourceAsMap();
            for(String key : array){
                HighlightField field = highLightBuilderFields.get(key);
                if(field != null){
                    Text[] fragments = field.fragments();
                    String str = Arrays.toString(fragments);
                    str = str.substring(1, str.length()-1);
                    sourceMap.put(key, str);
                }
            }
            arrayList.add(sourceMap);
        }
        return arrayList;
    }

    public TVideo getVideos(String keyword){
       return videoRepository.findByTitleLike(keyword);
    }

    public void deleteAllVideos(){
        videoRepository.deleteAll();
    }

    public UserInfo getUserInfo(String keyword) {

        return userInfoRepository.findByNickLike(keyword);
    }
}
