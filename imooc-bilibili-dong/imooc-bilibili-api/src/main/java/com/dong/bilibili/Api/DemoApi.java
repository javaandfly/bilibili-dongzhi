package com.dong.bilibili.Api;

import com.baomidou.mybatisplus.extension.api.R;
import com.imooc.bilibili.domain.JsonResponse;
import com.imooc.bilibili.domain.TVideo;
import com.imooc.bilibili.domain.UserInfo;
import com.imooc.bilibili.service.ElasticSearchService;
import org.opencv.video.Video;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DemoApi {
    @Autowired
    private ElasticSearchService elasticSearchService;


    @RequestMapping("find-userInfo")
    public JsonResponse<UserInfo> findUserInfo(@RequestParam String keyword){
      UserInfo userInfo=  elasticSearchService.getUserInfo(keyword);
      return new JsonResponse<>(userInfo);
    }




    /**
     * 根据es的模糊查询视频内容
     * @param keyword
     * @return
     */
    @RequestMapping("find-video")
    public JsonResponse<TVideo> findVideo(@RequestParam String keyword){
        TVideo videos = elasticSearchService.getVideos(keyword);
        return new JsonResponse<>(videos);
    }
}
